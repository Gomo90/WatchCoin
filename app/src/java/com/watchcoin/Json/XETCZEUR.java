package com.watchcoin.Json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class used for JSON data deserialization received by the Kraken API
 * concerning the Ether Classic euro datas
 */

public class XETCZEUR {

    // a = ask array(<price>, <whole lot volume>, <lot volume>)
    @SerializedName("a")
    private List<String> askArray;

    // b = bid array(<price>, <whole lot volume>, <lot volume>)
    @SerializedName("b")
    private List<String> bidArray;

    // c = last trade closed array(<price>, <lot volume>) (lastPrice)
    @SerializedName("c")
    private List<String> lastPriceArray;

    // v = volume array(<today>, <last 24 hours>)  (volume, volume24)
    @SerializedName("v")
    private List<String> volumeArray;

    // p = volume weighted average price array(<today>, <last 24 hours>) (avgPrice)
    @SerializedName("p")
    private List<String> avgPriceArray;

    // t = number of trades array(<today>, <last 24 hours>)
    @SerializedName("t")
    private List<Integer> tradesArray;

    // l = low array(<today>, <last 24 hours>) (lowPrice)
    @SerializedName("l")
    private List<String> lowPriceArray;

    // h = high array(<today>, <last 24 hours>) (highPrice)
    @SerializedName("h")
    private List<String> highPriceArray;

    // o = today's opening price
    @SerializedName("o")
    private String openingPrice;



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
