package com.watchcoin.Json;

import android.content.res.Resources;

import com.watchcoin.R;

import java.util.HashMap;
import java.util.List;

/**
 * Class used for JSON data deserialization received by the Kraken API
 */
public class CurrencyData {

    private List<String> error;
    private Result result;

    public List<String> getError() {

        return this.error;
    }

    public Result getResult() {

        return this.result;
    }

    public HashMap<String, String> extractCurrencyData(String currency, Resources resources) {

        HashMap<String, String> cryptoCurrencyDataMap = new HashMap<String, String>();

        switch(currency) {

            case "ALGO/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Algorand_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getAlgorandEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAlgorandEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAlgorandEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAlgorandEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAlgorandEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAlgorandEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAlgorandEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAlgorandEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAlgorandEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAlgorandEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAlgorandEuroData().getTradesArray().get(1)));

                break;

            case "ALGO/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Algorand_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getAlgorandUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAlgorandUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAlgorandUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAlgorandUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAlgorandUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAlgorandUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAlgorandUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAlgorandUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAlgorandUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAlgorandUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAlgorandUSdollarData().getTradesArray().get(1)));

                break;

            case "REP/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Augur_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getAugurEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAugurEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAugurEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAugurEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAugurEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAugurEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAugurEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAugurEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAugurEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAugurEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAugurEuroData().getTradesArray().get(1)));

                break;

            case "REP/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Augur_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getAugurUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAugurUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAugurUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAugurUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAugurUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAugurUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAugurUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAugurUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAugurUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAugurUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAugurUSdollarData().getTradesArray().get(1)));

                break;

            case "BAT/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.BAT_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBatUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBatUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBatUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBatUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBatUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBatUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBatUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBatUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBatUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBatUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBatUSdollarData().getTradesArray().get(1)));

                break;

            case "BAT/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.BAT_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBatEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBatEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBatEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBatEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBatEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBatEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBatEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBatEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBatEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBatEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBatEuroData().getTradesArray().get(1)));

                break;

            case "XBT/AUD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinAustralianDollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinAustralianDollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinAustralianDollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinAustralianDollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinAustralianDollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinAustralianDollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinAustralianDollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinAustralianDollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinAustralianDollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinAustralianDollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinAustralianDollarData().getTradesArray().get(1)));

                break;

            case "XBT/CAD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Canadian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinCanadiandollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinCanadiandollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinCanadiandollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinCanadiandollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinCanadiandollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinCanadiandollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinCanadiandollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinCanadiandollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinCanadiandollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinCanadiandollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinCanadiandollarData().getTradesArray().get(1)));

                break;

            case "XBT/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinEuroData().getTradesArray().get(1)));

                break;

            case "XBT/CHF" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Swiss_Franc_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinSwissFrancData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinSwissFrancData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinSwissFrancData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinSwissFrancData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinSwissFrancData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinSwissFrancData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinSwissFrancData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinSwissFrancData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinSwissFrancData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinSwissFrancData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinSwissFrancData().getTradesArray().get(1)));

                break;

            case "XBT/GBP" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinPoundData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinPoundData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinPoundData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinPoundData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinPoundData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinPoundData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinPoundData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinPoundData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinPoundData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinPoundData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinPoundData().getTradesArray().get(1)));

                break;

            case "XBT/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinUSdollarData().getTradesArray().get(1)));

                break;

            case "XBT/JPY" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Yen_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinYenData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinYenData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinYenData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinYenData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinYenData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinYenData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinYenData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinYenData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinYenData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinYenData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinYenData().getTradesArray().get(1)));

                break;

            case "BCH/AUD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinCashAustralianDollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinCashAustralianDollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinCashAustralianDollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinCashAustralianDollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinCashAustralianDollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinCashAustralianDollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinCashAustralianDollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinCashAustralianDollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinCashAustralianDollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinCashAustralianDollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinCashAustralianDollarData().getTradesArray().get(1)));

                break;

            case "BCH/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinCashUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinCashUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinCashUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinCashUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinCashUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinCashUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinCashUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinCashUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinCashUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinCashUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinCashUSdollarData().getTradesArray().get(1)));

                break;

            case "BCH/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinCashEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinCashEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinCashEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinCashEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinCashEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinCashEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinCashEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinCashEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinCashEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinCashEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinCashEuroData().getTradesArray().get(1)));

                break;

            case "BCH/GBP" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinCashPoundData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinCashPoundData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinCashPoundData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinCashPoundData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinCashPoundData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinCashPoundData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinCashPoundData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinCashPoundData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinCashPoundData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinCashPoundData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinCashPoundData().getTradesArray().get(1)));

                break;

            case "ATOM/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cosmos_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getCosmosUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCosmosUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCosmosUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCosmosUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCosmosUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCosmosUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCosmosUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCosmosUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCosmosUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCosmosUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCosmosUSdollarData().getTradesArray().get(1)));

                break;

            case "ATOM/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cosmos_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getCosmosEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCosmosEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCosmosEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCosmosEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCosmosEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCosmosEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCosmosEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCosmosEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCosmosEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCosmosEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCosmosEuroData().getTradesArray().get(1)));

                break;

            case "ADA/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cardano_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getCardanoEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCardanoEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCardanoEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCardanoEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCardanoEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCardanoEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCardanoEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCardanoEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCardanoEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCardanoEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCardanoEuroData().getTradesArray().get(1)));

                break;

            case "ADA/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cardano_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getCardanoUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCardanoUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCardanoUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCardanoUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCardanoUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCardanoUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCardanoUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCardanoUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCardanoUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCardanoUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCardanoUSdollarData().getTradesArray().get(1)));

                break;

            case "COMP/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Compound_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getCompoundEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCompoundEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCompoundEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCompoundEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCompoundEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCompoundEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCompoundEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCompoundEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCompoundEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCompoundEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCompoundEuroData().getTradesArray().get(1)));

                break;

            case "COMP/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Compound_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getCompoundUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCompoundUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCompoundUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCompoundUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCompoundUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCompoundUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCompoundUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCompoundUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCompoundUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCompoundUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCompoundUSdollarData().getTradesArray().get(1)));

                break;

            case "DAI/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dai_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getDaiUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDaiUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDaiUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDaiUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDaiUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDaiUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDaiUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDaiUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDaiUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDaiUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDaiUSdollarData().getTradesArray().get(1)));

                break;

            case "DAI/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dai_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getDaiEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDaiEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDaiEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDaiEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDaiEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDaiEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDaiEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDaiEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDaiEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDaiEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDaiEuroData().getTradesArray().get(1)));

                break;

            case "DASH/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dash_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getDashEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDashEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDashEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDashEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDashEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDashEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDashEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDashEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDashEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDashEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDashEuroData().getTradesArray().get(1)));

                break;

            case "DASH/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dash_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getDashUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDashUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDashUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDashUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDashUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDashUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDashUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDashUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDashUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDashUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDashUSdollarData().getTradesArray().get(1)));

                break;

            case "EOS/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Eos_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEosEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEosEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEosEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEosEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEosEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEosEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEosEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEosEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEosEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEosEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEosEuroData().getTradesArray().get(1)));

                break;

            case "EOS/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Eos_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEosUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEosUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEosUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEosUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEosUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEosUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEosUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEosUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEosUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEosUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEosUSdollarData().getTradesArray().get(1)));

                break;

            case "ETH/AUD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherAustralianDollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherAustralianDollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherAustralianDollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherAustralianDollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherAustralianDollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherAustralianDollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherAustralianDollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherAustralianDollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherAustralianDollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherAustralianDollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherAustralianDollarData().getTradesArray().get(1)));

                break;

            case "ETH/CAD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Canadian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherCanadiandollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherCanadiandollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherCanadiandollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherCanadiandollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherCanadiandollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherCanadiandollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherCanadiandollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherCanadiandollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherCanadiandollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherCanadiandollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherCanadiandollarData().getTradesArray().get(1)));

                break;

            case "ETH/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherEuroData().getTradesArray().get(1)));

                break;

            case "ETH/CHF" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Swiss_Franc_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherSwissFrancData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherSwissFrancData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherSwissFrancData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherSwissFrancData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherSwissFrancData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherSwissFrancData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherSwissFrancData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherSwissFrancData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherSwissFrancData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherSwissFrancData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherSwissFrancData().getTradesArray().get(1)));

                break;

            case "ETH/GBP" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherPoundData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherPoundData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherPoundData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherPoundData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherPoundData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherPoundData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherPoundData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherPoundData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherPoundData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherPoundData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherPoundData().getTradesArray().get(1)));

                break;

            case "ETH/JPY" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Yen_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherYenData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherYenData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherYenData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherYenData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherYenData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherYenData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherYenData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherYenData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherYenData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherYenData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherYenData().getTradesArray().get(1)));

                break;

            case "ETH/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherUSdollarData().getTradesArray().get(1)));

                break;

            case "ETC/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Etherc_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherClassicEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherClassicEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherClassicEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherClassicEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherClassicEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherClassicEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherClassicEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherClassicEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherClassicEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherClassicEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherClassicEuroData().getTradesArray().get(1)));

                break;

            case "ETC/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Etherc_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getEtherClassicUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEtherClassicUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEtherClassicUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEtherClassicUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEtherClassicUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEtherClassicUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEtherClassicUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEtherClassicUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEtherClassicUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEtherClassicUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEtherClassicUSdollarData().getTradesArray().get(1)));

                break;

            case "GNO/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Gnosis_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getGnosisEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getGnosisEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getGnosisEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getGnosisEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getGnosisEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getGnosisEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getGnosisEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getGnosisEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getGnosisEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getGnosisEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getGnosisEuroData().getTradesArray().get(1)));

                break;

            case "GNO/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Gnosis_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getGnosisUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getGnosisUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getGnosisUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getGnosisUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getGnosisUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getGnosisUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getGnosisUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getGnosisUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getGnosisUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getGnosisUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getGnosisUSdollarData().getTradesArray().get(1)));

                break;

            case "KAVA/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kava_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getKavaUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKavaUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKavaUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKavaUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKavaUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKavaUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKavaUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKavaUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKavaUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKavaUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKavaUSdollarData().getTradesArray().get(1)));

                break;

            case "KAVA/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kava_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getKavaEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKavaEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKavaEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKavaEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKavaEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKavaEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKavaEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKavaEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKavaEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKavaEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKavaEuroData().getTradesArray().get(1)));

                break;

            case "KNC/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kyber_Network_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getKyberNetworkUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKyberNetworkUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKyberNetworkUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKyberNetworkUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKyberNetworkUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKyberNetworkUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKyberNetworkUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKyberNetworkUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKyberNetworkUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKyberNetworkUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKyberNetworkUSdollarData().getTradesArray().get(1)));

                break;

            case "KNC/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kyber_Network_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getKyberNetworkEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKyberNetworkEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKyberNetworkEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKyberNetworkEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKyberNetworkEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKyberNetworkEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKyberNetworkEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKyberNetworkEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKyberNetworkEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKyberNetworkEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKyberNetworkEuroData().getTradesArray().get(1)));

                break;

            case "LINK/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Chainlink_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLinkUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLinkUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLinkUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLinkUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLinkUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLinkUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLinkUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLinkUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLinkUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLinkUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLinkUSdollarData().getTradesArray().get(1)));

                break;

            case "LINK/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Chainlink_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLinkEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLinkEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLinkEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLinkEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLinkEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLinkEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLinkEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLinkEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLinkEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLinkEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLinkEuroData().getTradesArray().get(1)));

                break;

            case "LSK/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Lisk_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLiskEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLiskEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLiskEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLiskEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLiskEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLiskEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLiskEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLiskEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLiskEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLiskEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLiskEuroData().getTradesArray().get(1)));

                break;

            case "LSK/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Lisk_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLiskUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLiskUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLiskUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLiskUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLiskUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLiskUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLiskUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLiskUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLiskUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLiskUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLiskUSdollarData().getTradesArray().get(1)));

                break;

            case "LTC/AUD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLitecoinAustralianDollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLitecoinAustralianDollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLitecoinAustralianDollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLitecoinAustralianDollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLitecoinAustralianDollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLitecoinAustralianDollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLitecoinAustralianDollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLitecoinAustralianDollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLitecoinAustralianDollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLitecoinAustralianDollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLitecoinAustralianDollarData().getTradesArray().get(1)));

                break;

            case "LTC/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLitecoinEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLitecoinEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLitecoinEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLitecoinEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLitecoinEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLitecoinEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLitecoinEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLitecoinEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLitecoinEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLitecoinEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLitecoinEuroData().getTradesArray().get(1)));

                break;

            case "LTC/GBP" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLitecoinPoundData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLitecoinPoundData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLitecoinPoundData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLitecoinPoundData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLitecoinPoundData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLitecoinPoundData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLitecoinPoundData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLitecoinPoundData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLitecoinPoundData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLitecoinPoundData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLitecoinPoundData().getTradesArray().get(1)));

                break;

            case "LTC/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getLitecoinUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLitecoinUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLitecoinUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLitecoinUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLitecoinUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLitecoinUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLitecoinUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLitecoinUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLitecoinUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLitecoinUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLitecoinUSdollarData().getTradesArray().get(1)));

                break;

            case "USDT/AUD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTetherAustralianDollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTetherAustralianDollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTetherAustralianDollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTetherAustralianDollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTetherAustralianDollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTetherAustralianDollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTetherAustralianDollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTetherAustralianDollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTetherAustralianDollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTetherAustralianDollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTetherAustralianDollarData().getTradesArray().get(1)));

                break;

            case "USDT/CHF" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Swiss_Franc_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTetherSwissFrancData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTetherSwissFrancData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTetherSwissFrancData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTetherSwissFrancData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTetherSwissFrancData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTetherSwissFrancData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTetherSwissFrancData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTetherSwissFrancData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTetherSwissFrancData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTetherSwissFrancData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTetherSwissFrancData().getTradesArray().get(1)));

                break;

            case "USDT/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTetherEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTetherEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTetherEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTetherEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTetherEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTetherEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTetherEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTetherEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTetherEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTetherEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTetherEuroData().getTradesArray().get(1)));

                break;

            case "USDT/JPY" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Yen_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTetherYenData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTetherYenData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTetherYenData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTetherYenData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTetherYenData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTetherYenData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTetherYenData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTetherYenData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTetherYenData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTetherYenData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTetherYenData().getTradesArray().get(1)));

                break;

            case "USDT/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTetherUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTetherUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTetherUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTetherUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTetherUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTetherUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTetherUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTetherUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTetherUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTetherUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTetherUSdollarData().getTradesArray().get(1)));

                break;

            case "XMR/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Monero_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getMoneroEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getMoneroEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getMoneroEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getMoneroEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getMoneroEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getMoneroEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getMoneroEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getMoneroEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getMoneroEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getMoneroEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getMoneroEuroData().getTradesArray().get(1)));

                break;

            case "XMR/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Monero_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getMoneroUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getMoneroUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getMoneroUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getMoneroUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getMoneroUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getMoneroUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getMoneroUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getMoneroUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getMoneroUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getMoneroUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getMoneroUSdollarData().getTradesArray().get(1)));

                break;

            case "NANO/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Nano_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getNanoEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getNanoEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getNanoEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getNanoEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getNanoEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getNanoEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getNanoEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getNanoEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getNanoEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getNanoEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getNanoEuroData().getTradesArray().get(1)));

                break;

            case "NANO/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Nano_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getNanoUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getNanoUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getNanoUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getNanoUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getNanoUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getNanoUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getNanoUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getNanoUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getNanoUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getNanoUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getNanoUSdollarData().getTradesArray().get(1)));

                break;

            case "OMG/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.OmiseGO_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getOmiseGOEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getOmiseGOEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getOmiseGOEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getOmiseGOEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getOmiseGOEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getOmiseGOEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getOmiseGOEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getOmiseGOEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getOmiseGOEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getOmiseGOEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getOmiseGOEuroData().getTradesArray().get(1)));

                break;

            case "OMG/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.OmiseGO_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getOmiseGOUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getOmiseGOUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getOmiseGOUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getOmiseGOUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getOmiseGOUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getOmiseGOUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getOmiseGOUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getOmiseGOUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getOmiseGOUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getOmiseGOUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getOmiseGOUSdollarData().getTradesArray().get(1)));

                break;

            case "OXT/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Orchid_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getOrchidEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getOrchidEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getOrchidEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getOrchidEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getOrchidEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getOrchidEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getOrchidEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getOrchidEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getOrchidEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getOrchidEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getOrchidEuroData().getTradesArray().get(1)));

                break;

            case "OXT/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Orchid_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getOrchidUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getOrchidUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getOrchidUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getOrchidUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getOrchidUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getOrchidUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getOrchidUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getOrchidUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getOrchidUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getOrchidUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getOrchidUSdollarData().getTradesArray().get(1)));

                break;

            case "PAXG/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Paxg_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getPaxgEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getPaxgEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getPaxgEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getPaxgEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getPaxgEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getPaxgEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getPaxgEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getPaxgEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getPaxgEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getPaxgEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getPaxgEuroData().getTradesArray().get(1)));

                break;

            case "PAXG/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Paxg_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getPaxgUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getPaxgUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getPaxgUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getPaxgUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getPaxgUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getPaxgUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getPaxgUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getPaxgUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getPaxgUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getPaxgUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getPaxgUSdollarData().getTradesArray().get(1)));

                break;

            case "QTUM/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Quantum_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getQuantumEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getQuantumEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getQuantumEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getQuantumEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getQuantumEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getQuantumEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getQuantumEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getQuantumEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getQuantumEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getQuantumEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getQuantumEuroData().getTradesArray().get(1)));

                break;

            case "QTUM/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Quantum_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getQuantumUSollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getQuantumUSollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getQuantumUSollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getQuantumUSollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getQuantumUSollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getQuantumUSollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getQuantumUSollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getQuantumUSollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getQuantumUSollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getQuantumUSollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getQuantumUSollarData().getTradesArray().get(1)));

                break;

            case "XRP/AUD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getRippleAustralianDollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRippleAustralianDollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRippleAustralianDollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRippleAustralianDollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRippleAustralianDollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRippleAustralianDollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRippleAustralianDollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRippleAustralianDollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRippleAustralianDollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRippleAustralianDollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRippleAustralianDollarData().getTradesArray().get(1)));

                break;

            case "XRP/CAD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Canadian_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getRippleCanadiandollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRippleCanadiandollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRippleCanadiandollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRippleCanadiandollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRippleCanadiandollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRippleCanadiandollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRippleCanadiandollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRippleCanadiandollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRippleCanadiandollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRippleCanadiandollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRippleCanadiandollarData().getTradesArray().get(1)));

                break;

            case "XRP/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getRippleEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRippleEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRippleEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRippleEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRippleEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRippleEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRippleEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRippleEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRippleEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRippleEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRippleEuroData().getTradesArray().get(1)));

                break;

            case "XRP/GBP" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getRipplePoundData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRipplePoundData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRipplePoundData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRipplePoundData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRipplePoundData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRipplePoundData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRipplePoundData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRipplePoundData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRipplePoundData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRipplePoundData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRipplePoundData().getTradesArray().get(1)));

                break;

            case "XRP/JPY" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Yen_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getRippleYenData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRippleYenData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRippleYenData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRippleYenData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRippleYenData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRippleYenData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRippleYenData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRippleYenData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRippleYenData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRippleYenData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRippleYenData().getTradesArray().get(1)));

                break;

            case "XRP/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getRippleUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRippleUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRippleUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRippleUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRippleUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRippleUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRippleUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRippleUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRippleUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRippleUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRippleUSdollarData().getTradesArray().get(1)));

                break;

            case "SC/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Siacoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getSiacoinUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getSiacoinUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getSiacoinUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getSiacoinUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getSiacoinUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getSiacoinUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getSiacoinUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getSiacoinUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getSiacoinUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getSiacoinUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getSiacoinUSdollarData().getTradesArray().get(1)));

                break;

            case "SC/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Siacoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getSiacoinEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getSiacoinEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getSiacoinEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getSiacoinEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getSiacoinEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getSiacoinEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getSiacoinEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getSiacoinEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getSiacoinEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getSiacoinEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getSiacoinEuroData().getTradesArray().get(1)));

                break;

            case "XLM/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Stellar_Lumens_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getStellarLumensEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getStellarLumensEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getStellarLumensEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getStellarLumensEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getStellarLumensEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getStellarLumensEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getStellarLumensEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getStellarLumensEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getStellarLumensEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getStellarLumensEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getStellarLumensEuroData().getTradesArray().get(1)));

                break;

            case "XLM/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Stellar_Lumens_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getStellarLumensUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getStellarLumensUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getStellarLumensUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getStellarLumensUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getStellarLumensUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getStellarLumensUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getStellarLumensUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getStellarLumensUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getStellarLumensUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getStellarLumensUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getStellarLumensUSdollarData().getTradesArray().get(1)));

                break;

            case "STORJ/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Storj_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getStorjUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getStorjUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getStorjUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getStorjUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getStorjUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getStorjUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getStorjUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getStorjUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getStorjUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getStorjUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getStorjUSdollarData().getTradesArray().get(1)));

                break;

            case "STORJ/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Storj_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getStorjEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getStorjEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getStorjEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getStorjEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getStorjEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getStorjEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getStorjEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getStorjEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getStorjEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getStorjEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getStorjEuroData().getTradesArray().get(1)));

                break;

            case "XTZ/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tezos_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTezosEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTezosEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTezosEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTezosEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTezosEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTezosEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTezosEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTezosEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTezosEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTezosEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTezosEuroData().getTradesArray().get(1)));

                break;

            case "XTZ/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tezos_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTezosUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTezosUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTezosUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTezosUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTezosUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTezosUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTezosUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTezosUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTezosUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTezosUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTezosUSdollarData().getTradesArray().get(1)));

                break;

            case "TRX/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tron_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTronUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTronUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTronUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTronUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTronUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTronUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTronUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTronUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTronUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTronUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTronUSdollarData().getTradesArray().get(1)));

                break;

            case "USDC/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.USDCoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getUsdcEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getUsdcEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getUsdcEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getUsdcEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getUsdcEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getUsdcEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getUsdcEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getUsdcEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getUsdcEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getUsdcEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getUsdcEuroData().getTradesArray().get(1)));

                break;

            case "USDC/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.USDCoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getUsdcUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getUsdcUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getUsdcUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getUsdcUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getUsdcUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getUsdcUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getUsdcUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getUsdcUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getUsdcUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getUsdcUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getUsdcUSdollarData().getTradesArray().get(1)));

                break;

            case "TRX/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tron_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getTronEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTronEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTronEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTronEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTronEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTronEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTronEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTronEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTronEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTronEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTronEuroData().getTradesArray().get(1)));

                break;

            case "WAVES/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Waves_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getWavesUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getWavesUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getWavesUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getWavesUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getWavesUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getWavesUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getWavesUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getWavesUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getWavesUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getWavesUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getWavesUSdollarData().getTradesArray().get(1)));

                break;

            case "WAVES/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Waves_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getWavesEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getWavesEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getWavesEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getWavesEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getWavesEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getWavesEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getWavesEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getWavesEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getWavesEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getWavesEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getWavesEuroData().getTradesArray().get(1)));

                break;

            case "ZEC/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Zcash_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getZcashEuroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getZcashEuroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getZcashEuroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getZcashEuroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getZcashEuroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getZcashEuroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getZcashEuroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getZcashEuroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getZcashEuroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getZcashEuroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getZcashEuroData().getTradesArray().get(1)));

                break;

            case "ZEC/USD" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Zcash_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getZcashUSdollarData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getZcashUSdollarData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getZcashUSdollarData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getZcashUSdollarData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getZcashUSdollarData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getZcashUSdollarData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getZcashUSdollarData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getZcashUSdollarData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getZcashUSdollarData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getZcashUSdollarData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getZcashUSdollarData().getTradesArray().get(1)));

                break;
        }

        return cryptoCurrencyDataMap;
    }
}
