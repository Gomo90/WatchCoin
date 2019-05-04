package com.watchcoin.Json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Abstract class used for JSON data deserialization received by the Kraken API
 * about each Kraken currency
 */
public abstract class CurrencyElements {

    // a = ask array(<price>, <whole lot volume>, <lot volume>)
    @SerializedName("a")
    protected List<String> askArray;

    // b = bid array(<price>, <whole lot volume>, <lot volume>)
    @SerializedName("b")
    protected List<String> bidArray;

    // c = last trade closed array(<price>, <lot volume>) (lastPrice)
    @SerializedName("c")
    protected List<String> lastPriceArray;

    // v = volume array(<today>, <last 24 hours>)  (volume, volume24)
    @SerializedName("v")
    protected List<String> volumeArray;

    // p = volume weighted average price array(<today>, <last 24 hours>) (avgPrice)
    @SerializedName("p")
    protected List<String> avgPriceArray;

    // t = number of trades array(<today>, <last 24 hours>)
    @SerializedName("t")
    protected List<Integer> tradesArray;

    // l = low array(<today>, <last 24 hours>) (lowPrice)
    @SerializedName("l")
    protected List<String> lowPriceArray;

    // h = high array(<today>, <last 24 hours>) (highPrice)
    @SerializedName("h")
    protected List<String> highPriceArray;

    // o = today's opening price
    @SerializedName("o")
    protected String openingPrice;


    public List<String> getAskArray() {

        return this.askArray;
    }

    public void setAskArray(List<String> askArray) {

        this.askArray = askArray;
    }

    public List<String> getBidArray() {

        return this.bidArray;
    }

    public void setBidArray(List<String> bidArray) {

        this.bidArray = bidArray;
    }

    public List<String> getLastPriceArray() {

        return this.lastPriceArray;
    }

    public void setLastPriceArray(List<String> lastPriceArray) {

        this.lastPriceArray = lastPriceArray;
    }

    public List<String> getVolumeArray() {

        return this.volumeArray;
    }

    public void setVolumeArray(List<String> volumeArray) {

        this.volumeArray = volumeArray;
    }

    public List<String> getAvgPriceArray() {

        return this.avgPriceArray;
    }

    public void setAvgPriceArray(List<String> avgPriceArray) {

        this.avgPriceArray = avgPriceArray;
    }

    public List<Integer> getTradesArray() {

        return this.tradesArray;
    }

    public void setTradesArray(List<Integer> tradesArray) {

        this.tradesArray = tradesArray;
    }

    public List<String> getLowPriceArray() {

        return this.lowPriceArray;
    }

    public void setLowPriceArray(List<String> lowPriceArray) {

        this.lowPriceArray = lowPriceArray;
    }

    public List<String> getHighPriceArray() {

        return this.highPriceArray;
    }

    public void setHighPriceArray(List<String> highPriceArray) {

        this.highPriceArray = highPriceArray;
    }

    public String getOpeningPrice() {

        return this.openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {

        this.openingPrice = openingPrice;
    }
}
