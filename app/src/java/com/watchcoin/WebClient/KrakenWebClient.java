package com.watchcoin.WebClient;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.watchcoin.Data.DataPolling;
import com.watchcoin.Data.ErrorMessageEvent;
import com.watchcoin.Data.UpdateMarketDataEvent;
import com.watchcoin.IHM.IHMConsole;
import com.watchcoin.Json.CurrencyData;
import com.watchcoin.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import static com.watchcoin.MainActivity.preferencesApp;

/**
 * KrakenWebClient is a background task executed at regular intervals for provide the market data
 * for each cryptocurrency selected (settings menu)
 */
public class KrakenWebClient extends AsyncTask<String, String, String> {


    private static final String TAG = "KrakenWebClient";

    private HashMap<String, String> cryptoCurrencyDataMap = new HashMap<String, String>();

    private IHMConsole ihmConsole;
    private DataPolling dataPolling;
    private CurrencyData currencyData;
    private Gson gson;
    private SharedPreferences sharedPreferencesApp = preferencesApp;


    public KrakenWebClient (IHMConsole ihmConsole) {

        this.ihmConsole = ihmConsole;

        dataPolling = new DataPolling();

        setDataPollingConfiguration();

        gson = new Gson();

        // Initialisation of cryptoCurrencyDataMap
        cryptoCurrencyDataMap.clear();
    }


    @Override
    protected String doInBackground(String... params) {

        // If internet connection is not available
        if (!ihmConsole.isNetworkAvailable()) {

            // Display error message (onPostExecute)
            return "networkKO";
        }

        // According to the type of request
        switch (params[0]) {

            // (TimerTask process)
            case "Data market":

                //String cryptoCurrencyData = dataPolling.PublicDataQuery();
                JSONObject cryptoCurrencyData = null;
                try {
                    cryptoCurrencyData = dataPolling.PublicDataQuery();
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                if (cryptoCurrencyData != null) {

                    currencyData = gson.fromJson(cryptoCurrencyData.toString(), CurrencyData.class);
                }
                else {

                    return "noMarketData";
                }

                // If currency datas are not recupered
                if (currencyData.getResult() == null) {

                    return "requestExecutionError";
                }

                // Save currency data market (when currency selected manually)
                ihmConsole.setCurrencyData(currencyData);

                // Extract the data of the currency selected
                extractCurrencyData(ihmConsole.getCurrencySelected());

                // Save currency data selected (data restoration onResume method MarketDataFragment)
                ihmConsole.setCurrencyDataMap(cryptoCurrencyDataMap);

                // return dataPolling.PublicDataQuery();
                return "Data market";


            // (Currency selected manually)
            default :

                // Extract the data of the currency selected
                extractCurrencyData(ihmConsole.getCurrencySelected());

                // Save currency data selected (data restoration onResume method MarketDataFragment)
                ihmConsole.setCurrencyDataMap(cryptoCurrencyDataMap);

                return "Select currency data";
        }
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // According the result of background process
        switch (s) {

            // If internet connection is not available
            case "networkKO" :

                // Display error message
                EventBus.getDefault().post(new ErrorMessageEvent("networkKO"));

                break;

            // If currency datas are not recupered
            case "noMarketData" :

                // Display error message
                EventBus.getDefault().post(new ErrorMessageEvent("noMarketData"));

                break;

            // If request error is occured
            case "requestExecutionError" :

                // Display error message
                EventBus.getDefault().post(new ErrorMessageEvent("requestExecutionError"));

                break;

            default:

                // Update data displayed
                updateData(s);

                break;
        }
    }

    public void launchPublicQuery() {

        this.execute("Data market");
    }


    public void getDataOfCurrency(String currencySelected) {

        this.execute(currencySelected);
    }


    /**
     * Read the data polling configuration in the SharedPreferences
     */
    private void setDataPollingConfiguration() {

        StringBuilder assetsList = new StringBuilder();

        // Set kraken API address
        dataPolling.setKrakenApiAddress(preferencesApp.getString("kraken_url_api", ihmConsole.getDefaultKrakenApiAddress()));

        // Fiat currencies configuration
        boolean americanDollarSelected = sharedPreferencesApp.getBoolean("us_dollar_checkbox", Boolean.FALSE);
        boolean britishPoundsSelected = sharedPreferencesApp.getBoolean("british_pounds_checkbox", Boolean.FALSE);
        boolean canadianDollarSelected = sharedPreferencesApp.getBoolean("canadian_dollar_checkbox", Boolean.FALSE);
        boolean eurosSelected = sharedPreferencesApp.getBoolean("euros_checkbox", Boolean.FALSE);
        boolean swissFrancSelected = sharedPreferencesApp.getBoolean("swiss_franc_checkbox", Boolean.FALSE);
        boolean japaneseYenSelected = sharedPreferencesApp.getBoolean("japanese_yen_checkbox", Boolean.FALSE);

        // Set kraken assets selected

        Set<String> krakenAssetsList = sharedPreferencesApp.getStringSet("kraken_assets_list", null);

        // Build the asset list chain for the data polling instance
        assetsList.append("pair=");

        // Datas initialisation for the ecurrency spinner
        for (String krakenAsset : krakenAssetsList) {

            switch (krakenAsset) {

                // Algorand (ALGO) | Basic Attention Token (BAT) | Cardano (ADA) | Cosmos (ATOM) | DAI (DIA)
                // DASH (DASH) | EOS (EOS) | Gnosis (GNO) | Chainlink (LINK) | Lisk (LSK) | Nano (NANO)
                // OmiseGO (OMG) | Pax Gold (PAXG) | Quantum (QTUM) | Siacoin (SC) | Tezos (XTZ) | Tron (TRX)
                // USDC (USD Coin) | Waves (WAVES)
                case "ALGO":
                case "BAT" :
                case "ADA" :
                case "ATOM" :
                case "DAI" :
                case "DASH" :
                case "EOS" :
                case "GNO" :
                case "LINK" :
                case "LSK" :
                case "NANO" :
                case "OMG" :
                case "PAXG" :
                case "QTUM" :
                case "SC" :
                case "XTZ" :
                case "TRX" :
                case "USDC" :
                case "WAVES" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.US_dollar)).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.Euro)).concat(","));
                    }

                    break;

                // Augur (REP)
                case "REP":

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Augur_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Augur_EUR_pair).concat(","));
                    }

                    break;

                // Bitcoin (XBT)
                case "XBT" :

                    // CAD, EUR, CHF, GBP, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_USD_pair).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_GBP_pair).concat(","));
                    }

                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_CAD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_EUR_pair).concat(","));
                    }

                    if (swissFrancSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_CHF_pair).concat(","));
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_JPY_pair).concat(","));
                    }

                    break;

                // Bitcoin Cash (BCH)
                case "BCH" :

                    // EUR, GBP and USD
                    if (americanDollarSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.US_dollar)).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.Pounds)).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.Euro)).concat(","));
                    }

                    break;

                // Ether (ETH)
                case "ETH" :

                    // CAD, EUR, CHF, GBP, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_USD_pair).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_GBP_pair).concat(","));
                    }

                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_CAD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_EUR_pair).concat(","));
                    }

                    if(swissFrancSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_CHF_pair).concat(","));
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_JPY_pair).concat(","));
                    }

                    break;

                // Ether Classic (ETC)
                case "ETC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Etherclassic_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Etherclassic_EUR_pair).concat(","));
                    }

                    break;

                // Litecoin (LTC)
                case "LTC" :

                    // EUR, GBP and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_USD_pair).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_GBP_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_EUR_pair).concat(","));
                    }

                    break;

                // Monero (XMR)
                case "XMR" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Monero_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Monero_EUR_pair).concat(","));
                    }

                    break;

                // Ripple (XRP)
                case "XRP" :

                    // CAD, EUR, GBP, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_USD_pair).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_GBP_pair).concat(","));
                    }

                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_CAD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_EUR_pair).concat(","));
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_JPY_pair).concat(","));
                    }

                    break;

                // Stellar Lumens (XLM)
                case "XLM" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Stellatlumens_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Stellarlumens_EUR_pair).concat(","));
                    }

                    break;

                // Tether (USDT)
                case "USDT" :

                    // EUR, CHF, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_EUR_pair).concat(","));
                    }

                    if (swissFrancSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_CHF_pair).concat(","));
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_JPY_pair).concat(","));
                    }

                    break;

                // Zcash (ZEC)
                case "ZEC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_EUR_pair).concat(","));
                    }

                    break;

            }
        }

        assetsList.delete(assetsList.length()-1, assetsList.length());

        dataPolling.setKrakenAssets(assetsList.toString());
    }


    /**
     * Update data displayed in the user interface
     * @param requestType : The type of request
     */
    private void updateData(String requestType) {


        // If the type of request concern the update of market data
        if (requestType.equals("Data market")) {

            // dateRefreshLabel
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            cryptoCurrencyDataMap.put("marketDateRefresh", formatDate.format(new Date()));
        }

        // Send update market data event to MarketDataFragment (EventBus process)
        EventBus.getDefault().post(new UpdateMarketDataEvent(cryptoCurrencyDataMap));

    }


    /**
     * Extract the data of the currency selected
     * @param currencySelected : The currency selected
     */
    private void extractCurrencyData(String currencySelected) {

        CurrencyData currencyData;

        // If currency datas are recupered
        if (ihmConsole.getCurrencyData() != null) {

            currencyData = ihmConsole.getCurrencyData();

            cryptoCurrencyDataMap = currencyData.extractCurrencyData(currencySelected, ihmConsole.getResources());
        }
    }
}
