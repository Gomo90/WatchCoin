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

            case "AAVE/EUR" :
            case "AAVE/GBP" :
            case "AAVE/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getAaveData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAaveData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAaveData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAaveData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAaveData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAaveData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAaveData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAaveData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAaveData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAaveData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAaveData().getTradesArray().get(1)));

                break;


            case "ALGO/EUR" :
            case "ALGO/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getAlgorandData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAlgorandData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAlgorandData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAlgorandData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAlgorandData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAlgorandData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAlgorandData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAlgorandData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAlgorandData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAlgorandData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAlgorandData().getTradesArray().get(1)));

                break;

            case "ANT/EUR" :
            case "ANT/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getAragonData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAragonData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAragonData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAragonData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAragonData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAragonData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAragonData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAragonData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAragonData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAragonData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAragonData().getTradesArray().get(1)));

                break;

            case "REP/EUR" :
            case "REP/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getAugurData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getAugurData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getAugurData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getAugurData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getAugurData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getAugurData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getAugurData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getAugurData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getAugurData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getAugurData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getAugurData().getTradesArray().get(1)));

                break;

            case "BAL/EUR" :
            case "BAL/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getBalancerData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBalancerData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBalancerData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBalancerData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBalancerData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBalancerData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBalancerData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBalancerData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBalancerData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBalancerData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBalancerData().getTradesArray().get(1)));

                break;

            case "BAT/EUR" :
            case "BAT/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getBasicAttentionTokenData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBasicAttentionTokenData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBasicAttentionTokenData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBasicAttentionTokenData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBasicAttentionTokenData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBasicAttentionTokenData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBasicAttentionTokenData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBasicAttentionTokenData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBasicAttentionTokenData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBasicAttentionTokenData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBasicAttentionTokenData().getTradesArray().get(1)));

                break;

            case "XBT/AUD" :
            case "XBT/CAD" :
            case "XBT/EUR" :
            case "XBT/CHF" :
            case "XBT/GBP" :
            case "XBT/USD" :
            case "XBT/JPY" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinData().getTradesArray().get(1)));

                break;

            case "BCH/AUD" :
            case "BCH/EUR" :
            case "BCH/GBP" :
            case "BCH/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getBitcoinCashData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getBitcoinCashData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getBitcoinCashData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getBitcoinCashData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getBitcoinCashData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getBitcoinCashData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getBitcoinCashData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getBitcoinCashData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getBitcoinCashData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getBitcoinCashData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getBitcoinCashData().getTradesArray().get(1)));

                break;

            case "ATOM/EUR" :
            case "ATOM/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getCosmosData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCosmosData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCosmosData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCosmosData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCosmosData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCosmosData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCosmosData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCosmosData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCosmosData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCosmosData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCosmosData().getTradesArray().get(1)));

                break;

            case "ADA/EUR" :
            case "ADA/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getCardanoData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCardanoData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCardanoData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCardanoData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCardanoData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCardanoData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCardanoData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCardanoData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCardanoData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCardanoData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCardanoData().getTradesArray().get(1)));

                break;

            case "COMP/EUR" :
            case "COMP/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getCompoundData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCompoundData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCompoundData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCompoundData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCompoundData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCompoundData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCompoundData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCompoundData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCompoundData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCompoundData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCompoundData().getTradesArray().get(1)));

                break;

            case "CRV/EUR" :
            case "CRV/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getCurveData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getCurveData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getCurveData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getCurveData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getCurveData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getCurveData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getCurveData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getCurveData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getCurveData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getCurveData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getCurveData().getTradesArray().get(1)));

                break;

            case "DAI/EUR" :
            case "DAI/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getDaiData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDaiData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDaiData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDaiData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDaiData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDaiData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDaiData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDaiData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDaiData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDaiData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDaiData().getTradesArray().get(1)));

                break;

            case "DASH/EUR" :
            case "DASH/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getDashData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDashData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDashData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDashData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDashData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDashData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDashData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDashData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDashData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDashData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDashData().getTradesArray().get(1)));

                break;

            case "MANA/EUR" :
            case "MANA/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getDecentralandData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getDecentralandData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getDecentralandData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getDecentralandData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getDecentralandData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getDecentralandData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getDecentralandData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getDecentralandData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getDecentralandData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getDecentralandData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getDecentralandData().getTradesArray().get(1)));

                break;

            case "EOS/EUR" :
            case "EOS/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getEosData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEosData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEosData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEosData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEosData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEosData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEosData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEosData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEosData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEosData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEosData().getTradesArray().get(1)));

                break;

            case "ETH/AUD" :
            case "ETH/CAD" :
            case "ETH/EUR" :
            case "ETH/CHF" :
            case "ETH/GBP" :
            case "ETH/JPY" :
            case "ETH/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getEthereumData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEthereumData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEthereumData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEthereumData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEthereumData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEthereumData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEthereumData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEthereumData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEthereumData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEthereumData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEthereumData().getTradesArray().get(1)));

                break;

            case "ETC/EUR" :
            case "ETC/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getEthereumClassicData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getEthereumClassicData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getEthereumClassicData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getEthereumClassicData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getEthereumClassicData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getEthereumClassicData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getEthereumClassicData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getEthereumClassicData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getEthereumClassicData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getEthereumClassicData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getEthereumClassicData().getTradesArray().get(1)));

                break;

            case "FIL/EUR" :
            case "FIL/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getFilecoinData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getFilecoinData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getFilecoinData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getFilecoinData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getFilecoinData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getFilecoinData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getFilecoinData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getFilecoinData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getFilecoinData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getFilecoinData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getFilecoinData().getTradesArray().get(1)));

                break;

            case "GNO/EUR" :
            case "GNO/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getGnosisData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getGnosisData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getGnosisData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getGnosisData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getGnosisData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getGnosisData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getGnosisData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getGnosisData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getGnosisData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getGnosisData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getGnosisData().getTradesArray().get(1)));

                break;

            case "GRT/EUR" :
            case "GRT/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getGraphData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getGraphData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getGraphData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getGraphData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getGraphData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getGraphData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getGraphData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getGraphData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getGraphData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getGraphData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getGraphData().getTradesArray().get(1)));

                break;

            case "KAVA/USD" :
            case "KAVA/EUR" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getKavaData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKavaData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKavaData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKavaData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKavaData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKavaData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKavaData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKavaData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKavaData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKavaData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKavaData().getTradesArray().get(1)));

                break;

            case "KEEP/USD" :
            case "KEEP/EUR" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getKeepData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKeepData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKeepData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKeepData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKeepData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKeepData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKeepData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKeepData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKeepData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKeepData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKeepData().getTradesArray().get(1)));

                break;

            case "KNC/USD" :
            case "KNC/EUR" :
                
                cryptoCurrencyDataMap.put("askPriceValue", result.getKyberNetworkData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKyberNetworkData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKyberNetworkData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKyberNetworkData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKyberNetworkData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKyberNetworkData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKyberNetworkData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKyberNetworkData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKyberNetworkData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKyberNetworkData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKyberNetworkData().getTradesArray().get(1)));

                break;

            case "KSM/USD" :
            case "KSM/EUR" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getKusamaData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getKusamaData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getKusamaData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getKusamaData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getKusamaData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getKusamaData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getKusamaData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getKusamaData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getKusamaData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getKusamaData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getKusamaData().getTradesArray().get(1)));

                break;

            case "LINK/USD" :
            case "LINK/EUR" :

                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Chainlink_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Chainlink_Market_data_title), resources.getString(R.string.Euro_symbol)));
                cryptoCurrencyDataMap.put("askPriceValue", result.getChainlinkData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getChainlinkData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getChainlinkData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getChainlinkData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getChainlinkData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getChainlinkData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getChainlinkData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getChainlinkData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getChainlinkData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getChainlinkData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getChainlinkData().getTradesArray().get(1)));

                break;

            case "LSK/EUR" :
            case "LSK/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getLiskData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLiskData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLiskData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLiskData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLiskData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLiskData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLiskData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLiskData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLiskData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLiskData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLiskData().getTradesArray().get(1)));

                break;

            case "LTC/AUD" :
            case "LTC/EUR" :
            case "LTC/GBP" :
            case "LTC/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getLitecoinData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getLitecoinData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getLitecoinData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getLitecoinData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getLitecoinData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getLitecoinData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getLitecoinData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getLitecoinData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getLitecoinData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getLitecoinData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getLitecoinData().getTradesArray().get(1)));

                break;

            case "USDT/AUD" :
            case "USDT/CHF" :
            case "USDT/EUR" :
            case "USDT/JPY" :
            case "USDT/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getTetherData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTetherData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTetherData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTetherData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTetherData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTetherData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTetherData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTetherData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTetherData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTetherData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTetherData().getTradesArray().get(1)));

                break;

            case "XMR/EUR" :
            case "XMR/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getMoneroData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getMoneroData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getMoneroData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getMoneroData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getMoneroData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getMoneroData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getMoneroData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getMoneroData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getMoneroData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getMoneroData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getMoneroData().getTradesArray().get(1)));

                break;

            case "NANO/EUR" :
            case "NANO/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getNanoData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getNanoData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getNanoData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getNanoData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getNanoData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getNanoData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getNanoData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getNanoData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getNanoData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getNanoData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getNanoData().getTradesArray().get(1)));

                break;

            case "OMG/EUR" :
            case "OMG/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getOmiseGOData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getOmiseGOData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getOmiseGOData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getOmiseGOData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getOmiseGOData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getOmiseGOData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getOmiseGOData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getOmiseGOData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getOmiseGOData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getOmiseGOData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getOmiseGOData().getTradesArray().get(1)));

                break;

            case "OXT/EUR" :
            case "OXT/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getOrchidData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getOrchidData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getOrchidData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getOrchidData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getOrchidData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getOrchidData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getOrchidData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getOrchidData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getOrchidData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getOrchidData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getOrchidData().getTradesArray().get(1)));

                break;

            case "PAXG/EUR" :
            case "PAXG/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getPaxGoldData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getPaxGoldData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getPaxGoldData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getPaxGoldData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getPaxGoldData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getPaxGoldData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getPaxGoldData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getPaxGoldData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getPaxGoldData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getPaxGoldData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getPaxGoldData().getTradesArray().get(1)));

                break;

            case "DOT/EUR":
            case "DOT/USD":

                cryptoCurrencyDataMap.put("askPriceValue", result.getPolkadotData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getPolkadotData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getPolkadotData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getPolkadotData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getPolkadotData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getPolkadotData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getPolkadotData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getPolkadotData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getPolkadotData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getPolkadotData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getPolkadotData().getTradesArray().get(1)));

                break;

            case "QTUM/EUR" :
            case "QTUM/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getQuantumData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getQuantumData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getQuantumData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getQuantumData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getQuantumData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getQuantumData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getQuantumData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getQuantumData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getQuantumData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getQuantumData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getQuantumData().getTradesArray().get(1)));

                break;

            case "XRP/AUD" :
            case "XRP/CAD" :
            case "XRP/EUR" :
            case "XRP/GBP" :
            case "XRP/JPY" :
            case "XRP/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getRippleData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getRippleData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getRippleData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getRippleData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getRippleData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getRippleData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getRippleData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getRippleData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getRippleData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getRippleData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getRippleData().getTradesArray().get(1)));

                break;

            case "SC/EUR" :
            case "SC/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getSiacoinData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getSiacoinData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getSiacoinData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getSiacoinData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getSiacoinData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getSiacoinData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getSiacoinData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getSiacoinData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getSiacoinData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getSiacoinData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getSiacoinData().getTradesArray().get(1)));

                break;

            case "XLM/EUR" :
            case "XLM/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getStellarLumensData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getStellarLumensData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getStellarLumensData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getStellarLumensData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getStellarLumensData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getStellarLumensData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getStellarLumensData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getStellarLumensData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getStellarLumensData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getStellarLumensData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getStellarLumensData().getTradesArray().get(1)));

                break;

            case "STORJ/EUR" :
            case "STORJ/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getStorjData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getStorjData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getStorjData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getStorjData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getStorjData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getStorjData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getStorjData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getStorjData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getStorjData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getStorjData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getStorjData().getTradesArray().get(1)));

                break;

            case "SNX/EUR":
            case "SNX/USD" :
                cryptoCurrencyDataMap.put("askPriceValue", result.getSynthetixData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getSynthetixData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getSynthetixData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getSynthetixData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getSynthetixData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getSynthetixData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getSynthetixData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getSynthetixData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getSynthetixData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getSynthetixData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getSynthetixData().getTradesArray().get(1)));
                break;

            case "TBTC/EUR":
            case "TBTC/USD" :
                cryptoCurrencyDataMap.put("askPriceValue", result.gettBTCData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.gettBTCData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.gettBTCData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.gettBTCData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.gettBTCData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.gettBTCData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.gettBTCData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.gettBTCData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.gettBTCData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.gettBTCData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.gettBTCData().getTradesArray().get(1)));
                break;

            case "XTZ/EUR" :
            case "XTZ/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getTezosData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTezosData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTezosData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTezosData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTezosData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTezosData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTezosData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTezosData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTezosData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTezosData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTezosData().getTradesArray().get(1)));

                break;

            case "TRX/EUR" :
            case "TRX/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getTronData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getTronData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getTronData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getTronData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getTronData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getTronData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getTronData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getTronData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getTronData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getTronData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getTronData().getTradesArray().get(1)));

                break;

            case "UNI/EUR" :
            case "UNI/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getUniswapData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getUniswapData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getUniswapData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getUniswapData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getUniswapData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getUniswapData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getUniswapData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getUniswapData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getUniswapData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getUniswapData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getUniswapData().getTradesArray().get(1)));

                break;

            case "USDC/EUR" :
            case "USDC/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getUsdcTokenData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getUsdcTokenData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getUsdcTokenData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getUsdcTokenData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getUsdcTokenData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getUsdcTokenData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getUsdcTokenData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getUsdcTokenData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getUsdcTokenData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getUsdcTokenData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getUsdcTokenData().getTradesArray().get(1)));

                break;

            case "WAVES/EUR" :
            case "WAVES/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getWavesData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getWavesData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getWavesData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getWavesData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getWavesData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getWavesData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getWavesData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getWavesData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getWavesData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getWavesData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getWavesData().getTradesArray().get(1)));

                break;

            case "YFI/EUR" :
            case "YFI/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getYearnData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getYearnData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getYearnData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getYearnData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getYearnData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getYearnData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getYearnData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getYearnData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getYearnData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getYearnData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getYearnData().getTradesArray().get(1)));

                break;

            case "ZEC/EUR" :
            case "ZEC/USD" :

                cryptoCurrencyDataMap.put("askPriceValue", result.getZcashData().getAskArray().get(0));
                cryptoCurrencyDataMap.put("bidPriceValue", result.getZcashData().getBidArray().get(0));
                cryptoCurrencyDataMap.put("lastPriceValue", result.getZcashData().getLastPriceArray().get(0));
                cryptoCurrencyDataMap.put("highPriceValue", result.getZcashData().getHighPriceArray().get(0));
                cryptoCurrencyDataMap.put("lowPriceValue", result.getZcashData().getLowPriceArray().get(0));
                cryptoCurrencyDataMap.put("avgPriceValue", result.getZcashData().getAvgPriceArray().get(0));
                cryptoCurrencyDataMap.put("openPriceValue", result.getZcashData().getOpeningPrice());
                cryptoCurrencyDataMap.put("volumeValue", result.getZcashData().getVolumeArray().get(0));
                cryptoCurrencyDataMap.put("volume24Value", result.getZcashData().getVolumeArray().get(1));
                cryptoCurrencyDataMap.put("tradeVolumeValue", String.valueOf(result.getZcashData().getTradesArray().get(0)));
                cryptoCurrencyDataMap.put("tradeVolume24Value", String.valueOf(result.getZcashData().getTradesArray().get(1)));

                break;
        }

        DefineCurrencyTitle (currency,  cryptoCurrencyDataMap, resources);

        return cryptoCurrencyDataMap;
    }

    public void DefineCurrencyTitle (String currency, HashMap<String, String> cryptoCurrencyDataMap, Resources resources) {

        switch (currency) {

            case "AAVE/EUR":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Aave_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "AAVE/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Aave_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "AAVE/GBP" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Aave_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                break;

            case "ALGO/EUR":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Algorand_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ALGO/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Algorand_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "ANT/EUR":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Aragon_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ANT/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Aragon_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "REP/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Augur_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "REP/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Augur_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "BAL/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Balancer_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "BAL/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Balancer_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "BAT/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.BAT_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "BAT/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.BAT_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "XBT/AUD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                break;

            case "XBT/CAD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Canadian_dollar_symbol)));
                break;

            case "XBT/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "XBT/CHF" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Swiss_Franc_symbol)));
                break;

            case "XBT/GBP" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                break;

            case "XBT/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "XBT/JPY" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Market_data_title), resources.getString(R.string.Yen_symbol)));
                break;

            case "BCH/AUD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                break;

            case "BCH/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "BCH/GBP" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                break;

            case "BCH/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Bitcoin_Cash_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "COMP/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Compound_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "COMP/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Compound_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "ATOM/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cosmos_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ATOM/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cosmos_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "ADA/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cardano_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ADA/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Cardano_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "CRV/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Curve_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "CRV/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Curve_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "DAI/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dai_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "DAI/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dai_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "DASH/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dash_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "DASH/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Dash_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "MANA/EUR":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Decentraland_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "MANA/USD":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Decentraland_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "ETH/AUD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                break;

            case "ETH/CAD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Canadian_dollar_symbol)));
                break;

            case "ETH/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ETH/CHF" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Swiss_Franc_symbol)));
                break;

            case "ETH/GBP" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                break;

            case "ETH/JPY" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.Yen_symbol)));
                break;

            case "ETH/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ether_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "ETC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Etherc_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ETC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Etherc_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "EOS/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Eos_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "EOS/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Eos_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "FIL/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Filecoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "FIL/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Filecoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "GRT/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Graph_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "GRT/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Graph_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "GNO/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Gnosis_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "GNO/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Gnosis_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "KAVA/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kava_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "KAVA/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kava_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "KEEP/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Keep_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "KEEP/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Keep_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "KNC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kyber_Network_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "KNC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kyber_Network_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "KSM/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kusama_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "KSM/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Kusama_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "LINK/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Chainlink_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "LINK/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Chainlink_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "LSK/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Lisk_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "LSK/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Lisk_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "LTC/AUD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                break;

            case "LTC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "LTC/GBP" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                break;

            case "LTC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Litcoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "XMR/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Monero_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "XMR/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Monero_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "NANO/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Nano_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "NANO/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Nano_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "OMG/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.OmiseGO_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "OMG/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.OmiseGO_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "OXT/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Orchid_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "OXT/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Orchid_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "PAXG/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Paxg_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "PAXG/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Paxg_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "DOT/EUR":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Polkadot_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "DOT/USD":
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Polkadot_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "QTUM/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Quantum_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "QTUM/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Quantum_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "XRP/AUD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                break;

            case "XRP/CAD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Canadian_dollar_symbol)));
                break;

            case "XRP/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "XRP/GBP" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Pounds_symbol)));
                break;

            case "XRP/JPY" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.Yen_symbol)));
                break;

            case "XRP/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Ripple_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "SC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Siacoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "SC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Siacoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "XLM/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Stellar_Lumens_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "XLM/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Stellar_Lumens_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "STORJ/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Storj_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "STORJ/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Storj_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "SNX/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Synthetix_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "SNX/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Synthetix_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "TBTC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.tBTC_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "TBTC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.tBTC_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "USDT/AUD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Australian_dollar_symbol)));
                break;

            case "USDT/CHF" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Swiss_Franc_symbol)));
                break;

            case "USDT/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "USDT/JPY" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.Yen_symbol)));
                break;

            case "USDT/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tether_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "XTZ/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tezos_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "XTZ/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tezos_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "TRX/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tron_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "TRX/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Tron_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "UNI/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Uniswap_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "UNI/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Uniswap_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "USDC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.USDCoin_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "USDC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.USDCoin_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "WAVES/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Waves_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "WAVES/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Waves_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "YFI/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Yearn_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "YFI/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Yearn_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

            case "ZEC/EUR" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Zcash_Market_data_title), resources.getString(R.string.Euro_symbol)));
                break;

            case "ZEC/USD" :
                cryptoCurrencyDataMap.put("marketDataCurrency", String.format(resources.getString(R.string.Zcash_Market_data_title), resources.getString(R.string.US_dollar_symbol)));
                break;

        }
    }
}
