package com.watchcoin.Data;

import java.util.HashMap;

/**
 * Class define the event of market data update to display in the main frame
 * (Used by EventBus process : http://greenrobot.org/eventbus/)
 */
public class UpdateMarketDataEvent {

    private final HashMap<String, String> cryptoCurrencyDataMap;


    public UpdateMarketDataEvent(HashMap<String, String> cryptoCurrencyDataMap) {

        this.cryptoCurrencyDataMap = cryptoCurrencyDataMap;
    }


    public String getMarketDataCurrency() {

        return cryptoCurrencyDataMap.get("marketDataCurrency");
    }

    public String getMarketDateRefresh() {

        return cryptoCurrencyDataMap.get("marketDateRefresh");
    }

    public String getAskPriceValue() {

        return cryptoCurrencyDataMap.get("askPriceValue");
    }

    public String getBidPriceValue() {

        return cryptoCurrencyDataMap.get("bidPriceValue");
    }

    public String getLastPriceValue() {

        return cryptoCurrencyDataMap.get("lastPriceValue");
    }

    public String getHighPriceValue() {

        return cryptoCurrencyDataMap.get("highPriceValue");
    }

    public String getLowPriceValue() {

        return cryptoCurrencyDataMap.get("lowPriceValue");
    }

    public String getAvgPriceValue() {

        return cryptoCurrencyDataMap.get("avgPriceValue");
    }

    public String getOpenPriceValue() {

        return cryptoCurrencyDataMap.get("openPriceValue");
    }

    public String getVolumeValue() {

        return cryptoCurrencyDataMap.get("volumeValue");
    }

    public String getVolume24Value() {

        return cryptoCurrencyDataMap.get("volume24Value");
    }

    public String getTradeVolumeValue() {

        return cryptoCurrencyDataMap.get("tradeVolumeValue");
    }

    public String getTradeVolume24Value() {

        return cryptoCurrencyDataMap.get("tradeVolume24Value");
    }
}
