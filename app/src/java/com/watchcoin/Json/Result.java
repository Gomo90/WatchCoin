package com.watchcoin.Json;

import com.google.gson.annotations.SerializedName;

/**
 * Class used for JSON data deserialization received by the Kraken API
 */
public class Result {

    @SerializedName("XREPZEUR")
    private XREPZEUR augurEuroData;

    @SerializedName("XXBTZCAD")
    private XXBTZCAD bitcoinCanadiandollarData;

    @SerializedName("XXBTZEUR")
    private XXBTZEUR bitcoinEuroData;

    @SerializedName("XXBTZUSD")
    private XXBTZUSD bitcoinUSdollarData;

    @SerializedName("XXBTZJPY")
    private XXBTZJPY bitcoinYenData;

    @SerializedName("BCHEUR")
    private BCHEUR bitcoinCashEuroData;

    @SerializedName("BCHUSD")
    private BCHUSD bitcoinCashUSdollarData;

    @SerializedName("DASHEUR")
    private DASHEUR dashEuroData;

    @SerializedName("DASHUSD")
    private DASHUSD dashUSdollarData;

    @SerializedName("XETHZCAD")
    private XETHZCAD etherCanadiandollarData;

    @SerializedName("XETHZEUR")
    private XETHZEUR etherEuroData;

    @SerializedName("XETHZJPY")
    private XETHZJPY etherYenData;

    @SerializedName("XETHZUSD")
    private XETHZUSD etherUSdollarData;

    @SerializedName("XETCZEUR")
    private XETCZEUR etherClassicEuroData;

    @SerializedName("XETCZUSD")
    private XETCZUSD etherClassicUSdollarData;

    @SerializedName("XLTCZEUR")
    private XLTCZEUR litcoinEuroData;

    @SerializedName("XLTCZUSD")
    private XLTCZUSD litcoinUSdollarData;

    @SerializedName("XXMRZEUR")
    private XXMRZEUR moneroEuroData;

    @SerializedName("XXMRZUSD")
    private XXMRZUSD moneroUSdollarData;

    @SerializedName("XXRPZEUR")
    private XXRPZEUR rippleEuroData;

    @SerializedName("XXRPZUSD")
    private XXRPZUSD rippleUSdollarData;

    @SerializedName("USDTZUSD")
    private USDTZUSD tetherUSdollarData;

    @SerializedName("XZECZEUR")
    private XZECZEUR zcashEuroData;

    @SerializedName("XZECZUSD")
    private XZECZUSD zcashUSdollarData;


    public XREPZEUR getAugurEuroData() {

        return this.augurEuroData;
    }

    public XXBTZCAD getBitcoinCanadiandollarData() {

        return this.bitcoinCanadiandollarData;
    }

    public XXBTZEUR getBitcoinEuroData() {

        return this.bitcoinEuroData;
    }

    public XXBTZUSD getBitcoinUSdollarData() {

        return this.bitcoinUSdollarData;
    }

    public XXBTZJPY getBitcoinYenData() {

        return this.bitcoinYenData;
    }

    public BCHEUR getBitcoinCashEuroData() {

        return this.bitcoinCashEuroData;
    }

    public BCHUSD getBitcoinCashUSdollarData() {

        return this.bitcoinCashUSdollarData;
    }

    public DASHEUR getDashEuroData() {

        return this.dashEuroData;
    }

    public DASHUSD getDashUSdollarData() {

        return this.dashUSdollarData;
    }

    public XETHZEUR getEtherEuroData() {

        return this.etherEuroData;
    }

    public XETHZCAD getEtherCanadiandollarData() {

        return this.etherCanadiandollarData;
    }

    public XETHZJPY getEtherYenData() {

        return this.etherYenData;
    }

    public XETHZUSD getEtherUSdollarData() {

        return this.etherUSdollarData;
    }

    public XETCZEUR getEtherClassicEuroData() {

        return this.etherClassicEuroData;
    }

    public XETCZUSD getEtherClassicUSdollarData() {

        return this.etherClassicUSdollarData;
    }

    public XLTCZEUR getLitecoinEuroData() {

        return this.litcoinEuroData;
    }

    public XLTCZUSD getLitecoinUSdollarData() {

        return this.litcoinUSdollarData;
    }

    public XXMRZEUR getMoneroEuroData() {

        return this.moneroEuroData;
    }

    public XXMRZUSD getMoneroUSdollarData() {

        return this.moneroUSdollarData;
    }

    public XXRPZEUR getRippleEuroData() {

        return this.rippleEuroData;
    }

    public XXRPZUSD getRippleUSdollarData() {

        return this.rippleUSdollarData;
    }

    public USDTZUSD getTetherUSdollarData() {

        return this.tetherUSdollarData;
    }

    public XZECZEUR getZcashEuroData() {

        return this.zcashEuroData;
    }

    public XZECZUSD getZcashUSdollarData() {

        return this.zcashUSdollarData;
    }
}
