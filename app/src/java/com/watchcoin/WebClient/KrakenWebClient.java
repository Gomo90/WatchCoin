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
        boolean japaneseYenSelected = sharedPreferencesApp.getBoolean("japanese_yen_checkbox", Boolean.FALSE);

        // Set kraken assets selected

        Set<String> krakenAssetsList = sharedPreferencesApp.getStringSet("kraken_assets_list", null);

        // Build the asset list chain for the data polling instance
        assetsList.append("pair=");

        // Datas initialisation for the ecurrency spinner
        for (String krakenAsset : krakenAssetsList) {

            switch (krakenAsset) {

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

                // Basic Attention Token (BAT)
                case "BAT" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.BAT_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.BAT_EUR_pair).concat(","));
                    }

                    break;

                // Bitcoin (XBT)
                case "XBT" :

                    // CAD, EUR, GBP, JPY and USD
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

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_JPY_pair).concat(","));
                    }

                    break;

                // Bitcoin Cash (BCH)
                case "BCH" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_Cash_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_Cash_EUR_pair).concat(","));
                    }

                    break;


                // Cardano (ADA)
                case "ADA" :

                    // CAD, EUR and USD
                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Cardano_CAD_pair).concat(","));
                    }

                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Cardano_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Cardano_EUR_pair).concat(","));
                    }

                    break;

                // Cosmos (ATOM)
                case "ATOM" :

                    //CAD, EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Cosmos_USD_pair).concat(","));
                    }

                    if(canadianDollarSelected){
                        assetsList.append(ihmConsole.getString(R.string.Cosmos_CAD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Cosmos_EUR_pair).concat(","));
                    }

                    break;


                // DAI (DIA)
                case "DAI" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Dai_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Dai_EUR_pair).concat(","));
                    }

                    break;

                // DASH (DASH)
                case "DASH" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Dash_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Dash_EUR_pair).concat(","));
                    }

                    break;

                // EOS (EOS)
                case "EOS" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Eos_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Eos_EUR_pair).concat(","));
                    }

                    break;

                // Ether (ETH)
                case "ETH" :

                    // CAD, EUR, GBP, JPY and USD
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


                // Gnosis (GNO)
                case "GNO" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Gnosis_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Gnosis_EUR_pair).concat(","));
                    }

                    break;


                // Chainlink (LINK)
                case "LINK" :
                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Chainlink_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Chainlink_EUR_pair).concat(","));
                    }
                    break;

                // Litecoin (LTC)
                case "LTC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_USD_pair).concat(","));
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

                // Nano (NANO)
                case "NANO" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Nano_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Nano_EUR_pair).concat(","));
                    }

                    break;

                // OmiseGO (OMG)
                case "OMG" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.OmiseGO_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.OmiseGO_EUR_pair).concat(","));
                    }

                    break;

                // Pax Gold (PAXG)
                case "PAXG" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Paxg_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Paxg_EUR_pair).concat(","));
                    }

                    break;

                // Quantum (QTUM)
                case "QTUM" :

                    // CAD, EUR and USD
                    if(canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Quantum_CAD_pair).concat(","));
                    }

                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Quantum_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Quantum_EUR_pair).concat(","));
                    }

                    break;

                // Ripple (XRP)
                case "XRP" :

                    // CAD, EUR, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_USD_pair).concat(","));
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

                // Siacoin (SC)
                case "SC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Siacoin_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Siacoin_EUR_pair).concat(","));
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

                    // USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_USD_pair).concat(","));
                    }

                    break;

                // Tezos (XTZ)
                case "XTZ" :

                    // CAD, EUR and USD
                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tezos_CAD_pair).concat(","));
                    }

                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tezos_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tezos_EUR_pair).concat(","));
                    }

                    break;

                // Waves (WAVES)
                case "WAVES" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Waves_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Waves_EUR_pair).concat(","));
                    }

                    break;

                // Zcash (ZEC)
                case "ZEC" :

                    // EUR, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_USD_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_EUR_pair).concat(","));
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_JPY_pair).concat(","));
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

            switch(currencySelected) {

                case "REP/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Augur_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getAugurEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getAugurEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getAugurEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getAugurEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getAugurEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getAugurEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getAugurEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getAugurEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getAugurEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getAugurEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getAugurEuroData().getTradesArray().get(1)));

                    break;

                case "REP/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Augur_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getAugurUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getAugurUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getAugurUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getAugurUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getAugurUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getAugurUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getAugurUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getAugurUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getAugurUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getAugurUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getAugurUSdollarData().getTradesArray().get(1)));

                    break;

                case "BAT/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.BAT_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBatUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBatUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBatUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBatUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBatUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBatUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBatUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBatUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBatUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBatUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBatUSdollarData().getTradesArray().get(1)));

                    break;

                case "BAT/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.BAT_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBatEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBatEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBatEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBatEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBatEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBatEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBatEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBatEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBatEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBatEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBatEuroData().getTradesArray().get(1)));

                    break;

                case "XBT/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Market_data_title), ihmConsole.getString(R.string.Canadian_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "XBT/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinEuroData().getTradesArray().get(1)));

                    break;

                case "XBT/GBP" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Market_data_title), ihmConsole.getString(R.string.Pounds_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinPoundData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinPoundData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinPoundData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinPoundData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinPoundData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinPoundData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinPoundData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinPoundData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinPoundData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinPoundData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinPoundData().getTradesArray().get(1)));

                    break;

                case "XBT/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinUSdollarData().getTradesArray().get(1)));

                    break;

                case "XBT/JPY" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Market_data_title), ihmConsole.getString(R.string.Yen_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinYenData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinYenData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinYenData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinYenData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinYenData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinYenData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinYenData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinYenData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinYenData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinYenData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinYenData().getTradesArray().get(1)));

                    break;

                case "BCH/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Cash_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinCashUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinCashUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinCashUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinCashUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinCashUSdollarData().getTradesArray().get(1)));

                    break;

                case "BCH/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Bitcoin_Cash_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getBitcoinCashEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getBitcoinCashEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getBitcoinCashEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getBitcoinCashEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getBitcoinCashEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getBitcoinCashEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getBitcoinCashEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getBitcoinCashEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getBitcoinCashEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getBitcoinCashEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getBitcoinCashEuroData().getTradesArray().get(1)));

                    break;

                case "ATOM/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Cosmos_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getCosmosUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getCosmosUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getCosmosUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getCosmosUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getCosmosUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getCosmosUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getCosmosUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getCosmosUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getCosmosUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getCosmosUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getCosmosUSdollarData().getTradesArray().get(1)));

                    break;

                case "ATOM/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Cosmos_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getCosmosEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getCosmosEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getCosmosEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getCosmosEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getCosmosEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getCosmosEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getCosmosEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getCosmosEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getCosmosEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getCosmosEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getCosmosEuroData().getTradesArray().get(1)));

                    break;

                case "ATOM/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Cosmos_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getCosmosCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getCosmosCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getCosmosCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getCosmosCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getCosmosCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "ADA/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Cardano_Market_data_title), ihmConsole.getString(R.string.Canadian_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getCardanoCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getCardanoCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getCardanoCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getCardanoCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getCardanoCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "ADA/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Cardano_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getCardanoEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getCardanoEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getCardanoEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getCardanoEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getCardanoEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getCardanoEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getCardanoEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getCardanoEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getCardanoEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getCardanoEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getCardanoEuroData().getTradesArray().get(1)));

                    break;

                case "ADA/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Cardano_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getCardanoUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getCardanoUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getCardanoUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getCardanoUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getCardanoUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getCardanoUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getCardanoUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getCardanoUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getCardanoUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getCardanoUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getCardanoUSdollarData().getTradesArray().get(1)));

                    break;

                case "DAI/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Dai_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getDaiUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getDaiUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getDaiUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getDaiUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getDaiUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getDaiUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getDaiUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getDaiUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getDaiUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getDaiUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getDaiUSdollarData().getTradesArray().get(1)));

                    break;

                case "DAI/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Dai_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getDaiEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getDaiEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getDaiEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getDaiEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getDaiEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getDaiEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getDaiEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getDaiEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getDaiEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getDaiEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getDaiEuroData().getTradesArray().get(1)));

                    break;

                case "DASH/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Dash_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getDashEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getDashEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getDashEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getDashEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getDashEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getDashEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getDashEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getDashEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getDashEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getDashEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getDashEuroData().getTradesArray().get(1)));

                    break;

                case "DASH/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Dash_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getDashUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getDashUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getDashUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getDashUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getDashUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getDashUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getDashUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getDashUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getDashUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getDashUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getDashUSdollarData().getTradesArray().get(1)));

                    break;

                case "EOS/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Eos_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEosEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEosEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEosEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEosEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEosEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEosEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEosEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEosEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEosEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEosEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEosEuroData().getTradesArray().get(1)));

                    break;

                case "EOS/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Eos_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEosUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEosUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEosUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEosUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEosUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEosUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEosUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEosUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEosUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEosUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEosUSdollarData().getTradesArray().get(1)));

                    break;

                case "ETH/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ether_Market_data_title), ihmConsole.getString(R.string.Canadian_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "ETH/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ether_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherEuroData().getTradesArray().get(1)));

                    break;

                case "ETH/GBP" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ether_Market_data_title), ihmConsole.getString(R.string.Pounds_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherPoundData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherPoundData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherPoundData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherPoundData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherPoundData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherPoundData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherPoundData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherPoundData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherPoundData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherPoundData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherPoundData().getTradesArray().get(1)));

                    break;

                case "ETH/JPY" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ether_Market_data_title), ihmConsole.getString(R.string.Yen_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherYenData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherYenData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherYenData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherYenData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherYenData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherYenData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherYenData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherYenData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherYenData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherYenData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherYenData().getTradesArray().get(1)));

                    break;

                case "ETH/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ether_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherUSdollarData().getTradesArray().get(1)));

                    break;

                case "ETC/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Etherc_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherClassicEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherClassicEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherClassicEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherClassicEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherClassicEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherClassicEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherClassicEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherClassicEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherClassicEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherClassicEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherClassicEuroData().getTradesArray().get(1)));

                    break;

                case "ETC/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Etherc_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getEtherClassicUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getEtherClassicUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getEtherClassicUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getEtherClassicUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getEtherClassicUSdollarData().getTradesArray().get(1)));

                    break;

                case "GNO/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Gnosis_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getGnosisEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getGnosisEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getGnosisEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getGnosisEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getGnosisEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getGnosisEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getGnosisEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getGnosisEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getGnosisEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getGnosisEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getGnosisEuroData().getTradesArray().get(1)));

                    break;

                case "GNO/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Gnosis_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getGnosisUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getGnosisUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getGnosisUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getGnosisUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getGnosisUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getGnosisUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getGnosisUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getGnosisUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getGnosisUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getGnosisUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getGnosisUSdollarData().getTradesArray().get(1)));

                    break;

                case "LINK/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Chainlink_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getLinkUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getLinkUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getLinkUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getLinkUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getLinkUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getLinkUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getLinkUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getLinkUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getLinkUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getLinkUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getLinkUSdollarData().getTradesArray().get(1)));

                    break;

                case "LINK/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Chainlink_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getLinkEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getLinkEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getLinkEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getLinkEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getLinkEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getLinkEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getLinkEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getLinkEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getLinkEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getLinkEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getLinkEuroData().getTradesArray().get(1)));

                    break;

                case "LTC/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Litcoin_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getLitecoinEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getLitecoinEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getLitecoinEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getLitecoinEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getLitecoinEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getLitecoinEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getLitecoinEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getLitecoinEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getLitecoinEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getLitecoinEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getLitecoinEuroData().getTradesArray().get(1)));

                    break;

                case "LTC/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Litcoin_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getLitecoinUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getLitecoinUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getLitecoinUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getLitecoinUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getLitecoinUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getLitecoinUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getLitecoinUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getLitecoinUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getLitecoinUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getLitecoinUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getLitecoinUSdollarData().getTradesArray().get(1)));

                    break;

                case "USDT/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Tether_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getTetherUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getTetherUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getTetherUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getTetherUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getTetherUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getTetherUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getTetherUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getTetherUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getTetherUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getTetherUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getTetherUSdollarData().getTradesArray().get(1)));

                    break;

                case "XMR/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Monero_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getMoneroEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getMoneroEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getMoneroEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getMoneroEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getMoneroEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getMoneroEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getMoneroEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getMoneroEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getMoneroEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getMoneroEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getMoneroEuroData().getTradesArray().get(1)));

                    break;

                case "XMR/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Monero_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getMoneroUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getMoneroUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getMoneroUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getMoneroUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getMoneroUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getMoneroUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getMoneroUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getMoneroUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getMoneroUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getMoneroUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getMoneroUSdollarData().getTradesArray().get(1)));

                    break;

                case "NANO/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Nano_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getNanoEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getNanoEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getNanoEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getNanoEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getNanoEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getNanoEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getNanoEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getNanoEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getNanoEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getNanoEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getNanoEuroData().getTradesArray().get(1)));

                    break;

                case "NANO/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Nano_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getNanoUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getNanoUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getNanoUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getNanoUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getNanoUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getNanoUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getNanoUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getNanoUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getNanoUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getNanoUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getNanoUSdollarData().getTradesArray().get(1)));

                    break;

                case "OMG/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.OmiseGO_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getOmiseGOEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getOmiseGOEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getOmiseGOEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getOmiseGOEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getOmiseGOEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getOmiseGOEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getOmiseGOEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getOmiseGOEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getOmiseGOEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getOmiseGOEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getOmiseGOEuroData().getTradesArray().get(1)));

                    break;

                case "OMG/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.OmiseGO_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getOmiseGOUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getOmiseGOUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getOmiseGOUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getOmiseGOUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getOmiseGOUSdollarData().getTradesArray().get(1)));

                    break;

                case "PAXG/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Paxg_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getPaxgEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getPaxgEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getPaxgEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getPaxgEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getPaxgEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getPaxgEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getPaxgEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getPaxgEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getPaxgEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getPaxgEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getPaxgEuroData().getTradesArray().get(1)));

                    break;

                case "PAXG/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Paxg_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getPaxgUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getPaxgUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getPaxgUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getPaxgUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getPaxgUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getPaxgUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getPaxgUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getPaxgUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getPaxgUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getPaxgUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getPaxgUSdollarData().getTradesArray().get(1)));

                    break;

                case "QTUM/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Quantum_Market_data_title), ihmConsole.getString(R.string.Canadian_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getQuantumCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getQuantumCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getQuantumCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getQuantumCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getQuantumCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "QTUM/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Quantum_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getQuantumEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getQuantumEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getQuantumEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getQuantumEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getQuantumEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getQuantumEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getQuantumEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getQuantumEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getQuantumEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getQuantumEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getQuantumEuroData().getTradesArray().get(1)));

                    break;

                case "QTUM/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Quantum_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getQuantumUSollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getQuantumUSollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getQuantumUSollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getQuantumUSollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getQuantumUSollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getQuantumUSollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getQuantumUSollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getQuantumUSollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getQuantumUSollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getQuantumUSollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getQuantumUSollarData().getTradesArray().get(1)));

                    break;

                case "XRP/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ripple_Market_data_title), ihmConsole.getString(R.string.Canadian_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getRippleCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getRippleCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getRippleCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getRippleCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getRippleCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getRippleCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getRippleCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getRippleCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getRippleCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getRippleCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getRippleCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "XRP/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ripple_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getRippleEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getRippleEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getRippleEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getRippleEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getRippleEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getRippleEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getRippleEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getRippleEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getRippleEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getRippleEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getRippleEuroData().getTradesArray().get(1)));

                    break;

                case "XRP/JPY" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ripple_Market_data_title), ihmConsole.getString(R.string.Yen_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getRippleYenData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getRippleYenData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getRippleYenData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getRippleYenData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getRippleYenData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getRippleYenData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getRippleYenData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getRippleYenData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getRippleYenData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getRippleYenData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getRippleYenData().getTradesArray().get(1)));

                    break;

                case "XRP/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Ripple_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getRippleUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getRippleUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getRippleUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getRippleUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getRippleUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getRippleUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getRippleUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getRippleUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getRippleUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getRippleUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getRippleUSdollarData().getTradesArray().get(1)));

                    break;

                case "SC/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Siacoin_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getSiacoinUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getSiacoinUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getSiacoinUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getSiacoinUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getSiacoinUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getSiacoinUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getSiacoinUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getSiacoinUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getSiacoinUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getSiacoinUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getSiacoinUSdollarData().getTradesArray().get(1)));

                    break;

                case "SC/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Siacoin_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getSiacoinEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getSiacoinEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getSiacoinEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getSiacoinEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getSiacoinEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getSiacoinEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getSiacoinEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getSiacoinEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getSiacoinEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getSiacoinEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getSiacoinEuroData().getTradesArray().get(1)));

                    break;

                case "XLM/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Stellar_Lumens_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getStellarLumensEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getStellarLumensEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getStellarLumensEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getStellarLumensEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getStellarLumensEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getStellarLumensEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getStellarLumensEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getStellarLumensEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getStellarLumensEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getStellarLumensEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getStellarLumensEuroData().getTradesArray().get(1)));

                    break;

                case "XLM/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Stellar_Lumens_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getStellarLumensUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getStellarLumensUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getStellarLumensUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getStellarLumensUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getStellarLumensUSdollarData().getTradesArray().get(1)));

                    break;

                case "XTZ/CAD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Tezos_Market_data_title), ihmConsole.getString(R.string.Canadian_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getTezosCanadiandollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getTezosCanadiandollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getTezosCanadiandollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getTezosCanadiandollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getTezosCanadiandollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getTezosCanadiandollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getTezosCanadiandollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getTezosCanadiandollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getTezosCanadiandollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getTezosCanadiandollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getTezosCanadiandollarData().getTradesArray().get(1)));

                    break;

                case "XTZ/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Tezos_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getTezosEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getTezosEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getTezosEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getTezosEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getTezosEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getTezosEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getTezosEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getTezosEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getTezosEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getTezosEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getTezosEuroData().getTradesArray().get(1)));

                    break;

                case "XTZ/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Tezos_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getTezosUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getTezosUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getTezosUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getTezosUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getTezosUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getTezosUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getTezosUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getTezosUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getTezosUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getTezosUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getTezosUSdollarData().getTradesArray().get(1)));

                    break;

                case "WAVES/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Waves_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getWavesUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getWavesUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getWavesUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getWavesUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getWavesUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getWavesUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getWavesUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getWavesUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getWavesUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getWavesUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getWavesUSdollarData().getTradesArray().get(1)));

                    break;

                case "WAVES/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Waves_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getWavesEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getWavesEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getWavesEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getWavesEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getWavesEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getWavesEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getWavesEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getWavesEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getWavesEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getWavesEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getWavesEuroData().getTradesArray().get(1)));

                    break;

                case "ZEC/EUR" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Zcash_Market_data_title), ihmConsole.getString(R.string.Euro_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getZcashEuroData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getZcashEuroData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getZcashEuroData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getZcashEuroData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getZcashEuroData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getZcashEuroData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getZcashEuroData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getZcashEuroData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getZcashEuroData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getZcashEuroData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getZcashEuroData().getTradesArray().get(1)));

                    break;

                case "ZEC/JPY" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Zcash_Market_data_title), ihmConsole.getString(R.string.Yen_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getZcashYenData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getZcashYenData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getZcashYenData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getZcashYenData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getZcashYenData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getZcashYenData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getZcashYenData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getZcashYenData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getZcashYenData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getZcashYenData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getZcashYenData().getTradesArray().get(1)));

                    break;

                case "ZEC/USD" :

                    cryptoCurrencyDataMap.put("marketDataCurrency", String.format(ihmConsole.getString(R.string.Zcash_Market_data_title), ihmConsole.getString(R.string.US_dollar_symbol)));
                    cryptoCurrencyDataMap.put("askPriceValue", currencyData.getResult().getZcashUSdollarData().getAskArray().get(0));
                    cryptoCurrencyDataMap.put("bidPriceValue", currencyData.getResult().getZcashUSdollarData().getBidArray().get(0));
                    cryptoCurrencyDataMap.put("lastPriceValue", currencyData.getResult().getZcashUSdollarData().getLastPriceArray().get(0));
                    cryptoCurrencyDataMap.put("highPriceValue", currencyData.getResult().getZcashUSdollarData().getHighPriceArray().get(0));
                    cryptoCurrencyDataMap.put("lowPriceValue", currencyData.getResult().getZcashUSdollarData().getLowPriceArray().get(0));
                    cryptoCurrencyDataMap.put("avgPriceValue", currencyData.getResult().getZcashUSdollarData().getAvgPriceArray().get(0));
                    cryptoCurrencyDataMap.put("openPriceValue", currencyData.getResult().getZcashUSdollarData().getOpeningPrice());
                    cryptoCurrencyDataMap.put("volumeValue", currencyData.getResult().getZcashUSdollarData().getVolumeArray().get(0));
                    cryptoCurrencyDataMap.put("volume24Value", currencyData.getResult().getZcashUSdollarData().getVolumeArray().get(1));
                    cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(currencyData.getResult().getZcashUSdollarData().getTradesArray().get(0)));
                    cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(currencyData.getResult().getZcashUSdollarData().getTradesArray().get(1)));

                    break;
            }
        }
    }
}
