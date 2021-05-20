package com.watchcoin.WebClient;

import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.watchcoin.Data.MySingleton;
import com.watchcoin.Data.UpdateMarketDataEvent;
import com.watchcoin.IHM.IHMConsole;
import com.watchcoin.Json.Aave;
import com.watchcoin.Json.Algorand;
import com.watchcoin.Json.Aragon;
import com.watchcoin.Json.Augur;
import com.watchcoin.Json.Balancer;
import com.watchcoin.Json.BasicAttentionToken;
import com.watchcoin.Json.Bitcoin;
import com.watchcoin.Json.BitcoinCash;
import com.watchcoin.Json.Cardano;
import com.watchcoin.Json.Chainlink;
import com.watchcoin.Json.Compound;
import com.watchcoin.Json.Cosmos;
import com.watchcoin.Json.CurrencyData;
import com.watchcoin.Json.Curve;
import com.watchcoin.Json.DAI;
import com.watchcoin.Json.Dash;
import com.watchcoin.Json.Decentraland;
import com.watchcoin.Json.EOS;
import com.watchcoin.Json.Ethereum;
import com.watchcoin.Json.EthereumClassic;
import com.watchcoin.Json.Filecoin;
import com.watchcoin.Json.Flow;
import com.watchcoin.Json.Gnosis;
import com.watchcoin.Json.Graph;
import com.watchcoin.Json.KAVA;
import com.watchcoin.Json.Keep;
import com.watchcoin.Json.Kusama;
import com.watchcoin.Json.KyberNetworkToken;
import com.watchcoin.Json.Lisk;
import com.watchcoin.Json.Litecoin;
import com.watchcoin.Json.Monero;
import com.watchcoin.Json.Nano;
import com.watchcoin.Json.OmiseGO;
import com.watchcoin.Json.Orchid;
import com.watchcoin.Json.PaxGold;
import com.watchcoin.Json.Polkadot;
import com.watchcoin.Json.Quantum;
import com.watchcoin.Json.Ripple;
import com.watchcoin.Json.Siacoin;
import com.watchcoin.Json.StellarLumens;
import com.watchcoin.Json.Storj;
import com.watchcoin.Json.Synthetix;
import com.watchcoin.Json.Tether;
import com.watchcoin.Json.Tezos;
import com.watchcoin.Json.Tron;
import com.watchcoin.Json.USDCToken;
import com.watchcoin.Json.Uniswap;
import com.watchcoin.Json.Waves;
import com.watchcoin.Json.Yearn;
import com.watchcoin.Json.Zcash;
import com.watchcoin.Json.tBTC;
import com.watchcoin.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.watchcoin.MainActivity.preferencesApp;

/**
 * KrakenWebClient is a background task executed at regular intervals for provide the market data
 * for each cryptocurrency selected (settings menu)
 */
public class KrakenWebClient {

    private static final String TAG = "KrakenWebClient";

    private String krakenApiAddress;
    private String krakenAssets;
    private final int krakenApiVersion = 0;

    private HashMap<String, String> cryptoCurrencyDataMap = new HashMap<>();

    private IHMConsole ihmConsole;
    JSONObject dataMarket;
    private CurrencyData currencyData;
    private Gson gson;
    private final SharedPreferences sharedPreferencesApp = preferencesApp;


    public KrakenWebClient (IHMConsole ihmConsole) {

        this.ihmConsole = ihmConsole;

        dataMarket = new JSONObject();

        setDataPollingConfiguration();

        gson = new Gson();

        // Initialisation of cryptoCurrencyDataMap
        cryptoCurrencyDataMap.clear();
    }

    /**
     * Request for get the public market data of cryptocurrency
     */
    public void launchPublicQuery(KrakenWebClientListener krakenWebClientListener) {

        boolean configChanged = sharedPreferencesApp.getBoolean("configChanged", Boolean.FALSE);

        // If the application configuration was changed
        if (configChanged) {

            // update data polling
            setDataPollingConfiguration();
        }

        try {

            String krakenAddress = String.format("%s/%s/public/%s", krakenApiAddress, krakenApiVersion, "Ticker");

            // https://developer.android.com/training/volley/request#java
            // https://www.youtube.com/watch?v=xPi-z3nOcn8
            JSONObject body = new JSONObject();
            body.put("pair", krakenAssets);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, krakenAddress, body,
                    response -> {
                        dataMarket = new JSONObject();
                        dataMarket = response;

                        if (dataMarket != null && dataMarket.length() > 0) {

                            currencyData = gson.fromJson(dataMarket.toString(), CurrencyData.class);
                        }
                        else {
                            krakenWebClientListener.onResponse(response);
                            return;
                        }

                        // If currency datas are not recupered
                        if (currencyData.getResult() == null) {

                            krakenWebClientListener.onResponse("requestExecutionError");
                            return;
                        }

                        // Save currency data market (when currency selected manually)
                        ihmConsole.setCurrencyData(currencyData);

                        // Extract the data of the currency selected
                        extractCurrencyData(ihmConsole.getCurrencySelected());

                        // Save currency data selected (data restoration onResume method MarketDataFragment)
                        ihmConsole.setCurrencyDataMap(cryptoCurrencyDataMap);

                        updateData("Data market");

                        krakenWebClientListener.onResponse(response);
                    }, error -> krakenWebClientListener.onError(error.getMessage()));

            MySingleton.getInstance(ihmConsole.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getDataOfCurrency(String currencySelected) {

        // Extract the data of the currency selected
        extractCurrencyData(currencySelected);

        // Save currency data selected (data restoration onResume method MarketDataFragment)
        ihmConsole.setCurrencyDataMap(cryptoCurrencyDataMap);

        updateData("getDataOfCurrency");
    }


    /**
     * Read the data polling configuration in the SharedPreferences
     */
    private void setDataPollingConfiguration() {

        StringBuilder assetsList = new StringBuilder();

        // Set kraken API address
        setKrakenApiAddress(preferencesApp.getString("kraken_url_api", ihmConsole.getDefaultKrakenApiAddress()));

        // Fiat currencies configuration
        boolean australianDollarSelected = sharedPreferencesApp.getBoolean("australian_dollar_checkbox", Boolean.FALSE);
        boolean americanDollarSelected = sharedPreferencesApp.getBoolean("us_dollar_checkbox", Boolean.FALSE);
        boolean britishPoundsSelected = sharedPreferencesApp.getBoolean("british_pounds_checkbox", Boolean.FALSE);
        boolean canadianDollarSelected = sharedPreferencesApp.getBoolean("canadian_dollar_checkbox", Boolean.FALSE);
        boolean eurosSelected = sharedPreferencesApp.getBoolean("euros_checkbox", Boolean.FALSE);
        boolean swissFrancSelected = sharedPreferencesApp.getBoolean("swiss_franc_checkbox", Boolean.FALSE);
        boolean japaneseYenSelected = sharedPreferencesApp.getBoolean("japanese_yen_checkbox", Boolean.FALSE);

        // Set kraken assets selected
        Set<String> krakenAssetsList = new HashSet<>();

        krakenAssetsList.addAll(sharedPreferencesApp.getStringSet("kraken_assets_list", null));
        krakenAssetsList.addAll(sharedPreferencesApp.getStringSet("defi_kraken_assets_list", null));

        // Datas initialisation for the ecurrency spinner
        for (String krakenAsset : krakenAssetsList) {

            switch (krakenAsset) {

                // Aragon (ANT) | Augur (REP) | Balancer (BAL) | Basic Attention Token (BAT) | Compound (COMP)
                // Curve (CRV) | Dai (DAI) | DASH (DASH) | Decentraland (MANA) | EOS (EOS) | Ether Classic (ETC) | Gnosis (GNO)
                // Kava (KAVA) | Keep (KEEP) | Kyber Network (KNC) | Lisk (LSK) | Monero (XMR) | Nano (NANO) | OmiseGO (OMG) | Orchid (OXT) | Pax Gold (PAXG)
                // Quantum (QTUM) | Siacoin (SC) | Storj (STORJ) | tBTC (TBTC) |  Tron (TRX) | Uniswap (UNI) |
                // Waves (WAVES) | Zcash (ZEC)
                case "ANT" :
                case "BAL" :
                case "BAT" :
                case "COMP":
                case "CRV" :
                case "DAI" :
                case "DASH" :
                case "MANA" :
                case "EOS" :
                case "GNO" :
                case "KAVA" :
                case "KEEP" :
                case "KNC" :
                case "LSK" :
                case "NANO" :
                case "OMG" :
                case "OXT" :
                case "PAXG" :
                case "QTUM" :
                case "SC" :
                case "STORJ" :
                case "TBTC" :
                case "TRX" :
                case "UNI" :
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


                // Algorand (ALGO) | Flow (FLOW)
                case "ALGO" :
                case "FLOW" :

                    // EUR, GBP and USD
                    if (americanDollarSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.US_dollar)).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.Euro)).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.Pounds)).concat(","));
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

                    // AUD, CAD, EUR, CHF, GBP, JPY and USD
                    if (australianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Bitcoin_AUD_pair).concat(","));
                    }

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

                // Aave (AAVE) | Bitcoin Cash (BCH) | Cardano (ADA) | Cosmos (ATOM) | Filecoin (FIL) | Graph (GRT) | Kusama (KSM) | Chainlink (LINK) | Polkadot (DOT) |
                // Synthetix (SNX) | Tezos (XTZ) | USD Coin (USDC) | Yearn (YFI)
                case "AAVE" :
                case "BCH" :
                case "ADA" :
                case "ATOM" :
                case "FIL" :
                case "GRT" :
                case "KSM" :
                case "LINK" :
                case "DOT" :
                case "SNX" :
                case "XTZ" :
                case "USDC" :
                case "YFI" :

                    // AUD, EUR, GBP and USD
                    if (australianDollarSelected) {
                        assetsList.append(String.format(ihmConsole.getString(R.string.Kraken_pair), krakenAsset,
                                ihmConsole.getString(R.string.Australian_dollar)).concat(","));
                    }

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

                    // AUD, CAD, EUR, CHF, GBP, JPY and USD
                    if (australianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ether_AUD_pair).concat(","));
                    }

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

                    // AUD, EUR, GBP and USD
                    if (australianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Litecoin_AUD_pair).concat(","));
                    }

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

                    // AUD, CAD, EUR, GBP, JPY and USD
                    if (australianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Ripple_AUD_pair).concat(","));
                    }

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

                    // AUD, GBP, EUR and USD
                    if (americanDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Stellarlumens_USD_pair).concat(","));
                    }

                    if (australianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Stellarlumens_AUD_pair).concat(","));
                    }

                    if (britishPoundsSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Stellarlumens_GBP_pair).concat(","));
                    }

                    if (eurosSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Stellarlumens_EUR_pair).concat(","));
                    }

                    break;

                // Tether (USDT)
                case "USDT" :

                    // AUD, EUR, CHF, JPY and USD
                    if (australianDollarSelected) {
                        assetsList.append(ihmConsole.getString(R.string.Tether_AUD_pair).concat(","));
                    }

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

        // Build the asset list chain for the data polling instance
        assetsList.delete(assetsList.length()-1, assetsList.length());
        setKrakenAssets(assetsList.toString());
    }


    /**
     * Update data displayed in the user interface
     * @param requestType : The type of request
     */
    public void updateData(String requestType) {


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

            String currencySelectedFormat = currencySelected.replace("/","");

            // Specific pair name
            switch (currencySelectedFormat) {

                // Augur pair name
                case "REPEUR" :
                    currencySelectedFormat = "XREPZEUR";
                    break;

                case "REPUSD" :
                    currencySelectedFormat = "XREPZUSD";
                    break;

                // Bitcoin pair name
                case "XBTCAD":
                    currencySelectedFormat = "XXBTZCAD";
                    break;

                case "XBTEUR":
                    currencySelectedFormat = "XXBTZEUR";
                    break;

                 case "XBTGBP":
                     currencySelectedFormat = "XXBTZGBP";
                     break;

                 case "XBTJPY":
                     currencySelectedFormat = "XXBTZJPY";
                     break;

                 case "XBTUSD":
                     currencySelectedFormat = "XXBTZUSD";
                     break;

                 // Etherum pair name
                 case "ETHCAD":
                     currencySelectedFormat = "XETHZCAD";
                     break;

                 case "ETHEUR":
                     currencySelectedFormat = "XETHZEUR";
                     break;

                 case "ETHGBP":
                     currencySelectedFormat = "XETHZGBP";
                     break;

                 case "ETHJPY":
                     currencySelectedFormat = "XETHZJPY";
                     break;

                 case "ETHUSD":
                     currencySelectedFormat = "XETHZUSD";
                     break;

                // Etherum Classic pair name
                case "ETCEUR":
                    currencySelectedFormat = "XETCZEUR";
                    break;

                case "ETCUSD":
                    currencySelectedFormat = "XETCZUSD";
                    break;

                // Litecoin pair name
                case "LTCEUR" :
                    currencySelectedFormat = "XLTCZEUR";
                    break;

                case "LTCUSD" :
                    currencySelectedFormat = "XLTCZUSD";
                    break;

                // Monero pair name
                case "XMREUR" :
                    currencySelectedFormat = "XXMRZEUR";
                    break;

                case "XMRUSD" :
                    currencySelectedFormat = "XXMRZUSD";
                    break;

                // Ripple pair name
                case "XRPCAD" :
                    currencySelectedFormat = "XXRPZCAD";
                    break;

                case "XRPEUR" :
                    currencySelectedFormat = "XXRPZEUR";
                    break;

                case "XRPJPY" :
                    currencySelectedFormat = "XXRPZJPY";
                    break;

                case "XRPUSD" :
                    currencySelectedFormat = "XXRPZUSD";
                    break;

                // Stellar lumens pair
                case "XLMAUD" :
                    currencySelectedFormat = "XXLMZAUD";
                    break;

                case "XLMEUR" :
                    currencySelectedFormat = "XXLMZEUR";
                    break;

                case "XLMGBP" :
                    currencySelectedFormat = "XXLMZGBP";
                    break;

                case "XLMUSD" :
                    currencySelectedFormat = "XXLMZUSD";
                    break;

                // Tether pair name
                case "USDTUSD" :
                    currencySelectedFormat = "USDTZUSD";
                    break;

                // Zcash pair name
                case "ZECEUR" :
                    currencySelectedFormat = "XZECZEUR";
                    break;

                case "ZECUSD" :
                    currencySelectedFormat = "XZECZUSD";
                    break;

            }

            try {

                JSONObject extractCcurrencySelected = dataMarket.getJSONObject("result").getJSONObject(currencySelectedFormat);

                switch (currencySelectedFormat.substring(0,3)) {

                    case "AAV" :
                        Aave aaveData = gson.fromJson(extractCcurrencySelected.toString(), Aave.class);
                        currencyData.getResult().setAaveData(aaveData);
                        break;

                    case "ALG" :
                        Algorand algorandData = gson.fromJson(extractCcurrencySelected.toString(), Algorand.class);
                        currencyData.getResult().setAlgorandData(algorandData);
                        break;

                    case "ANT" :
                        Aragon aragonData = gson.fromJson(extractCcurrencySelected.toString(), Aragon.class);
                        currencyData.getResult().setAragonData(aragonData);
                        break;

                    case "BAL" :
                        Balancer balancerData = gson.fromJson(extractCcurrencySelected.toString(), Balancer.class);
                        currencyData.getResult().setBalancerData(balancerData);
                        break;

                    case "BAT" :
                        BasicAttentionToken basicAttentionToken = gson.fromJson(extractCcurrencySelected.toString(), BasicAttentionToken.class);
                        currencyData.getResult().setBasicAttentionTokenData(basicAttentionToken);
                        break;

                    case "LIN" :
                        Chainlink chainlinkData = gson.fromJson(extractCcurrencySelected.toString(), Chainlink.class);
                        currencyData.getResult().setChainlinkData(chainlinkData);
                        break;

                    case "COM":
                        Compound compoundData = gson.fromJson(extractCcurrencySelected.toString(), Compound.class);
                        currencyData.getResult().setCompoundData(compoundData);
                        break;

                    case "ATO":
                        Cosmos cosmosData = gson.fromJson(extractCcurrencySelected.toString(), Cosmos.class);
                        currencyData.getResult().setCosmosData(cosmosData);
                        break;

                    case "XRE" :
                        Augur augurData = gson.fromJson(extractCcurrencySelected.toString(), Augur.class);
                        currencyData.getResult().setAugurData(augurData);
                        break;

                    case "XBT":
                    case "XXB":
                        Bitcoin bitcoinData = gson.fromJson(extractCcurrencySelected.toString(), Bitcoin.class);
                        currencyData.getResult().setBitcoinData(bitcoinData);
                        break;

                    case "BCH" :
                        BitcoinCash bitcoinCashData = gson.fromJson(extractCcurrencySelected.toString(), BitcoinCash.class);
                        currencyData.getResult().setBitcoinCashData(bitcoinCashData);
                        break;

                    case "ADA" :
                        Cardano cardanoData = gson.fromJson(extractCcurrencySelected.toString(), Cardano.class);
                        currencyData.getResult().setCardanoData(cardanoData);
                        break;

                    case "CRV" :
                        Curve curveData = gson.fromJson(extractCcurrencySelected.toString(), Curve.class);
                        currencyData.getResult().setCurveData(curveData);
                        break;

                    case "DAI" :
                        DAI daiData = gson.fromJson(extractCcurrencySelected.toString(), DAI.class);
                        currencyData.getResult().setDaiData(daiData);
                        break;

                    case "DAS" :
                        Dash dashData = gson.fromJson(extractCcurrencySelected.toString(), Dash.class);
                        currencyData.getResult().setDashData(dashData);
                        break;

                    case "MAN" :
                        Decentraland dcentralandData = gson.fromJson(extractCcurrencySelected.toString(), Decentraland.class);
                        currencyData.getResult().setDecentralandData(dcentralandData);
                        break;

                    case "ETH":
                    case "XET" :

                        // Same prefix "XET" between Ethereum and Ethereum Classic
                        if(currencySelectedFormat.startsWith("XETC")) {

                            EthereumClassic ethereumClassicData = gson.fromJson(extractCcurrencySelected.toString(), EthereumClassic.class);
                            currencyData.getResult().setEthereumClassicData(ethereumClassicData);
                        }
                        else {

                            Ethereum ethereumData = gson.fromJson(extractCcurrencySelected.toString(), Ethereum.class);
                            currencyData.getResult().setEthereumData(ethereumData);
                        }

                        break;

                    case "EOS" :
                        EOS eosData = gson.fromJson(extractCcurrencySelected.toString(), EOS.class);
                        currencyData.getResult().setEosData(eosData);
                        break;

                    case "FIL" :
                        Filecoin filecoinData = gson.fromJson(extractCcurrencySelected.toString(), Filecoin.class);
                        currencyData.getResult().setFilecoinData(filecoinData);
                        break;

                    case "FLO" :
                        Flow flowData = gson.fromJson(extractCcurrencySelected.toString(), Flow.class);
                        currencyData.getResult().setFlowData(flowData);
                        break;

                    case "GNO" :
                        Gnosis gnosisData = gson.fromJson(extractCcurrencySelected.toString(), Gnosis.class);
                        currencyData.getResult().setGnosisData(gnosisData);
                        break;

                    case "GRT" :
                        Graph graphData = gson.fromJson(extractCcurrencySelected.toString(), Graph.class);
                        currencyData.getResult().setGraphData(graphData);
                        break;

                    case "KAV" :
                        KAVA kavaData = gson.fromJson(extractCcurrencySelected.toString(), KAVA.class);
                        currencyData.getResult().setKavaData(kavaData);
                        break;

                    case "KEE" :
                        Keep keepData = gson.fromJson(extractCcurrencySelected.toString(), Keep.class);
                        currencyData.getResult().setKeepData(keepData);
                        break;

                    case "KNC" :
                        KyberNetworkToken kyberNetworkTokenData = gson.fromJson(extractCcurrencySelected.toString(), KyberNetworkToken.class);
                        currencyData.getResult().setKyberNetworkData(kyberNetworkTokenData);
                        break;

                    case "KSM" :
                        Kusama kusamaData = gson.fromJson(extractCcurrencySelected.toString(), Kusama.class);
                        currencyData.getResult().setKusamaData(kusamaData);
                        break;

                    case "LSK" :
                        Lisk liskData = gson.fromJson(extractCcurrencySelected.toString(), Lisk.class);
                        currencyData.getResult().setLiskData(liskData);
                        break;

                    case "LTC" :
                    case "XLT" :
                        Litecoin litecoinData = gson.fromJson(extractCcurrencySelected.toString(), Litecoin.class);
                        currencyData.getResult().setLitecoinData(litecoinData);
                        break;

                    case "XXM":
                        Monero moneroData = gson.fromJson(extractCcurrencySelected.toString(), Monero.class);
                        currencyData.getResult().setMoneroData(moneroData);
                        break;

                    case "NAN":
                        Nano nanoData = gson.fromJson(extractCcurrencySelected.toString(), Nano.class);
                        currencyData.getResult().setNanoData(nanoData);
                        break;

                    case "OMG" :
                        OmiseGO omiseGOData = gson.fromJson(extractCcurrencySelected.toString(), OmiseGO.class);
                        currencyData.getResult().setOmiseGOData(omiseGOData);
                        break;

                    case "OXT" :
                        Orchid orchidData = gson.fromJson(extractCcurrencySelected.toString(), Orchid.class);
                        currencyData.getResult().setOrchidData(orchidData);
                        break;

                    case "PAX" :
                        PaxGold paxGoldData = gson.fromJson(extractCcurrencySelected.toString(), PaxGold.class);
                        currencyData.getResult().setPaxGoldData(paxGoldData);
                        break;

                    case "DOT" :
                        Polkadot polkadotData = gson.fromJson(extractCcurrencySelected.toString(), Polkadot.class);
                        currencyData.getResult().setPolkadotData(polkadotData);
                        break;

                    case "QTU" :
                        Quantum quantumData = gson.fromJson(extractCcurrencySelected.toString(), Quantum.class);
                        currencyData.getResult().setQuantumData(quantumData);
                        break;

                    case "XRP":
                    case "XXR" :
                        Ripple rippleData = gson.fromJson(extractCcurrencySelected.toString(), Ripple.class);
                        currencyData.getResult().setRippleData(rippleData);
                        break;

                    case "SCE" :
                    case "SCU" :
                        Siacoin siacoinData = gson.fromJson(extractCcurrencySelected.toString(), Siacoin.class);
                        currencyData.getResult().setSiacoinData(siacoinData);
                        break;

                    case "SNX" :
                        Synthetix synthetixData = gson.fromJson(extractCcurrencySelected.toString(), Synthetix.class);
                        currencyData.getResult().setSynthetixData(synthetixData);
                        break;

                    case "XXL" :
                        StellarLumens stellarLumensData = gson.fromJson(extractCcurrencySelected.toString(), StellarLumens.class);
                        currencyData.getResult().setStellarLumensData(stellarLumensData);
                        break;

                    case "STO" :
                        Storj storjData = gson.fromJson(extractCcurrencySelected.toString(), Storj.class);
                        currencyData.getResult().setStorjData(storjData);
                        break;

                    case "TBT" :
                        tBTC tBTCData = gson.fromJson(extractCcurrencySelected.toString(), tBTC.class);
                        currencyData.getResult().settBTCData(tBTCData);
                        break;

                    case "TRX" :
                        Tron tronData = gson.fromJson(extractCcurrencySelected.toString(), Tron.class);
                        currencyData.getResult().setTronData(tronData);
                        break;

                    case "UNI" :
                        Uniswap uniswapData = gson.fromJson(extractCcurrencySelected.toString(), Uniswap.class);
                        currencyData.getResult().setUniswapData(uniswapData);
                        break;

                    case "USD" :

                        // Same prefix "USD" between Tether and USD Coin
                        if(currencySelectedFormat.startsWith("USDC")) {

                            USDCToken usdcTokenData = gson.fromJson(extractCcurrencySelected.toString(), USDCToken.class);
                            currencyData.getResult().setUsdcTokenData(usdcTokenData);
                        }
                        else {

                            Tether tetherData = gson.fromJson(extractCcurrencySelected.toString(), Tether.class);
                            currencyData.getResult().setTetherData(tetherData);
                        }
                        break;

                    case "WAV" :
                        Waves wavesData = gson.fromJson(extractCcurrencySelected.toString(), Waves.class);
                        currencyData.getResult().setWavesData(wavesData);
                        break;

                    case "XTZ" :
                        Tezos tezosData = gson.fromJson(extractCcurrencySelected.toString(), Tezos.class);
                        currencyData.getResult().setTezosData(tezosData);
                        break;

                    case "YFI" :
                        Yearn yearnData = gson.fromJson(extractCcurrencySelected.toString(), Yearn.class);
                        currencyData.getResult().setYearnData(yearnData);
                        break;

                    case "XZE" :
                        Zcash zcashData = gson.fromJson(extractCcurrencySelected.toString(), Zcash.class);
                        currencyData.getResult().setZcashData(zcashData);
                        break;
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            cryptoCurrencyDataMap = currencyData.extractCurrencyData(currencySelected, ihmConsole.getResources());
        }
    }

    /*** Getter/Setter data ***/

    public String getKrakenApiAddress() {

        return this.krakenApiAddress;
    }

    public void setKrakenApiAddress (String krakenApiAddress) {

        this.krakenApiAddress = krakenApiAddress;
    }

    public String getKrakenAssets() {

        return this.krakenAssets;
    }

    public void setKrakenAssets (String krakenAssets) {

        this.krakenAssets = krakenAssets;
    }
}
