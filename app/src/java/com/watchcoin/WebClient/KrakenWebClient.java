package com.watchcoin.WebClient;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

                    // EUR

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Augur_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // Bitcoin (XBT)
                case "XBT" :

                    // CAD, EUR, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_USD_pair));
                        assetsList.append(",");
                    }

                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_CAD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_EUR_pair));
                        assetsList.append(",");
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_JPY_pair));
                        assetsList.append(",");
                    }

                    break;

                // Bitcoin Cash (BCH)
                case "BCH" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_Cash_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_Cash_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // DASH (DASH)
                case "DASH" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Dash_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Dash_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // Ether (ETH)
                case "ETH" :

                    // CAD, EUR, JPY and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_USD_pair));
                        assetsList.append(",");
                    }

                    if (canadianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_CAD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_EUR_pair));
                        assetsList.append(",");
                    }

                    if (japaneseYenSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_JPY_pair));
                        assetsList.append(",");
                    }

                    break;


                // Ether Classic (ETC)
                case "ETC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Etherclassic_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Etherclassic_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // Litecoin (LTC)
                case "LTC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // Monero (XMR)
                case "XMR" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Monero_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Monero_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // Ripple (XRP)
                case "XRP" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_EUR_pair));
                        assetsList.append(",");
                    }

                    break;

                // Tether (USDT)
                case "USDT" :

                    // USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_USD_pair));
                        assetsList.append(",");
                    }

                    break;

                // Zcash (ZEC)
                case "ZEC" :

                    // EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_USD_pair));
                        assetsList.append(",");
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Zcash_EUR_pair));
                        assetsList.append(",");
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

        String temp, temp2;
        String [] tabTemp;
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
