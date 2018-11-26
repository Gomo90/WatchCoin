package com.watchcoin.Json;

import com.google.gson.annotations.SerializedName;

/**
 * Class used for JSON data deserialization received by the Kraken API
 */
public class Result {

    @SerializedName("XREPZEUR")
    private XREPZEUR augurEuroData;

    @SerializedName("XREPZUSD")
    private XREPZUSD augurUSdollarData;

    @SerializedName("XXBTZCAD")
    private XXBTZCAD bitcoinCanadiandollarData;

    @SerializedName("XXBTZEUR")
    private XXBTZEUR bitcoinEuroData;

    @SerializedName("XXBTZGBP")
    private XXBTZGBP bitcoinPoundData;

    @SerializedName("XXBTZUSD")
    private XXBTZUSD bitcoinUSdollarData;

    @SerializedName("XXBTZJPY")
    private XXBTZJPY bitcoinYenData;

    @SerializedName("BCHEUR")
    private BCHEUR bitcoinCashEuroData;

    @SerializedName("BCHUSD")
    private BCHUSD bitcoinCashUSdollarData;

    @SerializedName("BSVEUR")
    private BSVEUR bitcoinCashSVEuroData;

    @SerializedName("BSVUSD")
    private BSVUSD bitcoinCashSVUSdollarData;

    @SerializedName("ADACAD")
    private ADACAD cardanoCanadiandollarData;

    @SerializedName("ADAEUR")
    private ADAEUR cardanoEuroData;

    @SerializedName("ADAUSD")
    private ADAUSD cardanoUSdollarData;

    @SerializedName("DASHEUR")
    private DASHEUR dashEuroData;

    @SerializedName("DASHUSD")
    private DASHUSD dashUSdollarData;

    @SerializedName("EOSEUR")
    private EOSEUR eosEuroData;

    @SerializedName("EOSUSD")
    private EOSUSD eosUSdollarData;

    @SerializedName("XETHZCAD")
    private XETHZCAD etherCanadiandollarData;

    @SerializedName("XETHZEUR")
    private XETHZEUR etherEuroData;

    @SerializedName("XETHZGBP")
    private XETHZGBP etherPoundData;

    @SerializedName("XETHZJPY")
    private XETHZJPY etherYenData;

    @SerializedName("XETHZUSD")
    private XETHZUSD etherUSdollarData;

    @SerializedName("XETCZEUR")
    private XETCZEUR etherClassicEuroData;

    @SerializedName("XETCZUSD")
    private XETCZUSD etherClassicUSdollarData;

    @SerializedName("GNOEUR")
    private GNOEUR gnosisEuroData;

    @SerializedName("GNOUSD")
    private GNOUSD gnosisUSdollarData;

    @SerializedName("XLTCZEUR")
    private XLTCZEUR litcoinEuroData;

    @SerializedName("XLTCZUSD")
    private XLTCZUSD litcoinUSdollarData;

    @SerializedName("XXMRZEUR")
    private XXMRZEUR moneroEuroData;

    @SerializedName("XXMRZUSD")
    private XXMRZUSD moneroUSdollarData;

    @SerializedName("QTUMCAD")
    private QTUMCAD quantumCanadiandollarData;

    @SerializedName("QTUMEUR")
    private QTUMEUR quantumEuroData;

    @SerializedName("QTUMUSD")
    private QTUMUSD quantumUSollarData;

    @SerializedName("XXRPZCAD")
    private XXRPZCAD rippleCanadiandollarData;

    @SerializedName("XXRPZEUR")
    private XXRPZEUR rippleEuroData;

    @SerializedName("XXRPZJPY")
    private XXRPZJPY rippleYenData;

    @SerializedName("XXRPZUSD")
    private XXRPZUSD rippleUSdollarData;

    @SerializedName("XXLMZEUR")
    private XXLMZEUR stellarLumensEuroData;

    @SerializedName("XXLMZUSD")
    private XXLMZUSD stellarLumensUSdollarData;

    @SerializedName("USDTZUSD")
    private USDTZUSD tetherUSdollarData;

    @SerializedName("XTZCAD")
    private XTZCAD tezosCanadiandollarData;

    @SerializedName("XTZEUR")
    private XTZEUR tezosEuroData;

    @SerializedName("XTZUSD")
    private XTZUSD tezosUSdollarData;

    @SerializedName("XZECZEUR")
    private XZECZEUR zcashEuroData;

    @SerializedName("XZECZJPY")
    private XZECZJPY zcashYenData;

    @SerializedName("XZECZUSD")
    private XZECZUSD zcashUSdollarData;


    public XREPZEUR getAugurEuroData() {

        return this.augurEuroData;
    }

    public XREPZUSD getAugurUSdollarData() {

        return this.augurUSdollarData;
    }

    public XXBTZCAD getBitcoinCanadiandollarData() {

        return this.bitcoinCanadiandollarData;
    }

    public XXBTZEUR getBitcoinEuroData() {

        return this.bitcoinEuroData;
    }

    public XXBTZGBP getBitcoinPoundData() {

        return this.bitcoinPoundData;
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

    public BSVEUR getBitcoinCashSVEuroData() {

        return this.bitcoinCashSVEuroData;
    }

    public BSVUSD getBitcoinCashSVUSdollarData() {

        return this.bitcoinCashSVUSdollarData;
    }

    public ADACAD getCardanoCanadiandollarData(){

        return this.cardanoCanadiandollarData;
    }

    public ADAEUR getCardanoEuroData() {

        return this.cardanoEuroData;
    }

    public ADAUSD getCardanoUSdollarData() {

        return this.cardanoUSdollarData;
    }

    public DASHEUR getDashEuroData() {

        return this.dashEuroData;
    }

    public DASHUSD getDashUSdollarData() {

        return this.dashUSdollarData;
    }

    public EOSEUR getEosEuroData() {

        return this.eosEuroData;
    }

    public EOSUSD getEosUSdollarData() {

        return this.eosUSdollarData;
    }

    public XETHZEUR getEtherEuroData() {

        return this.etherEuroData;
    }

    public XETHZCAD getEtherCanadiandollarData() {

        return this.etherCanadiandollarData;
    }

    public XETHZGBP getEtherPoundData() {

        return this.etherPoundData;
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

    public GNOEUR getGnosisEuroData() {

        return this.gnosisEuroData;
    }

    public GNOUSD getGnosisUSdollarData() {

        return this.gnosisUSdollarData;
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

    public QTUMCAD getQuantumCanadiandollarData() {

        return this.quantumCanadiandollarData;
    }

    public QTUMEUR getQuantumEuroData() {

        return this.quantumEuroData;
    }

    public QTUMUSD getQuantumUSollarData() {

        return this.quantumUSollarData;
    }

    public XXRPZEUR getRippleEuroData() {

        return this.rippleEuroData;
    }

    public XXRPZUSD getRippleUSdollarData() {

        return this.rippleUSdollarData;
    }

    public XXRPZCAD getRippleCanadiandollarData() {

        return this.rippleCanadiandollarData;
    }

    public XXRPZJPY getRippleYenData() {

        return this.rippleYenData;
    }

    public XXLMZEUR getStellarLumensEuroData() {

        return this.stellarLumensEuroData;
    }

    public XXLMZUSD getStellarLumensUSdollarData() {

        return this.stellarLumensUSdollarData;
    }

    public USDTZUSD getTetherUSdollarData() {

        return this.tetherUSdollarData;
    }

    public XTZCAD getTezosCanadiandollarData() {

        return this.tezosCanadiandollarData;
    }

    public XTZEUR getTezosEuroData() {

        return this.tezosEuroData;
    }

    public XTZUSD getTezosUSdollarData() {

        return this.tezosUSdollarData;
    }

    public XZECZEUR getZcashEuroData() {

        return this.zcashEuroData;
    }

    public XZECZUSD getZcashUSdollarData() {

        return this.zcashUSdollarData;
    }

    public XZECZJPY getZcashYenData() {

        return this.zcashYenData;
    }
}
