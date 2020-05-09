package com.watchcoin.Json;

import com.google.gson.annotations.SerializedName;

/**
 * Class used for JSON data deserialization received by the Kraken API
 */
public class Result {

    @SerializedName("ALGOEUR")
    private ALGOEUR algoEuroData;

    @SerializedName("ALGOUSD")
    private ALGOUSD algoUSdollarData;

    @SerializedName("XREPZEUR")
    private XREPZEUR augurEuroData;

    @SerializedName("XREPZUSD")
    private XREPZUSD augurUSdollarData;

    @SerializedName("BATUSD")
    private BATUSD batUSdollarData;

    @SerializedName("BATEUR")
    private BATEUR batEuroData;

    @SerializedName("XXBTZCAD")
    private XXBTZCAD bitcoinCanadiandollarData;

    @SerializedName("XXBTZEUR")
    private XXBTZEUR bitcoinEuroData;

    @SerializedName("XBTCHF")
    private XBTCHF bitcoinSwissFrancData;

    @SerializedName("XXBTZGBP")
    private XXBTZGBP bitcoinPoundData;

    @SerializedName("XXBTZUSD")
    private XXBTZUSD bitcoinUSdollarData;

    @SerializedName("XXBTZJPY")
    private XXBTZJPY bitcoinYenData;

    @SerializedName("BCHEUR")
    private BCHEUR bitcoinCashEuroData;

    @SerializedName("BCHGBP")
    private BCHGBP bitcoinCashPoundData;

    @SerializedName("BCHUSD")
    private BCHUSD bitcoinCashUSdollarData;

    @SerializedName("ATOMEUR")
    private ATOMEUR cosmosEuroData;

    @SerializedName("ATOMUSD")
    private ATOMUSD cosmosUSdollarData;

    @SerializedName("ADAEUR")
    private ADAEUR cardanoEuroData;

    @SerializedName("ADAUSD")
    private ADAUSD cardanoUSdollarData;

    @SerializedName("DAIEUR")
    private DAIEUR daiEuroData;

    @SerializedName("DAIUSD")
    private DAIUSD daiUSdollarData;

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

    @SerializedName("ETHCHF")
    private ETHCHF etherSwissFrancData;

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

    @SerializedName("LINKEUR")
    private LINKEUR linkEuroData;

    @SerializedName("LINKUSD")
    private LINKUSD linkUSdollarData;

    @SerializedName("XLTCZEUR")
    private XLTCZEUR litcoinEuroData;

    @SerializedName("LTCGBP")
    private LTCGBP litcoinPoundData;

    @SerializedName("XLTCZUSD")
    private XLTCZUSD litcoinUSdollarData;

    @SerializedName("LSKEUR")
    private LSKEUR liskEuroData;

    @SerializedName("LSKUSD")
    private LSKUSD liskUSdollarData;

    @SerializedName("XXMRZEUR")
    private XXMRZEUR moneroEuroData;

    @SerializedName("XXMRZUSD")
    private XXMRZUSD moneroUSdollarData;

    @SerializedName("NANOEUR")
    private NANOEUR nanoEuroData;

    @SerializedName("NANOUSD")
    private NANOUSD nanoUSdollarData;

    @SerializedName("OMGEUR")
    private OMGEUR omisegoEuroData;

    @SerializedName("OMGUSD")
    private OMGUSD omisegoUSdollarData;

    @SerializedName("PAXGEUR")
    private PAXGEUR paxgEuroData;

    @SerializedName("PAXGUSD")
    private PAXGUSD paxgUSdollarData;

    @SerializedName("QTUMEUR")
    private QTUMEUR quantumEuroData;

    @SerializedName("QTUMUSD")
    private QTUMUSD quantumUSollarData;

    @SerializedName("XXRPZCAD")
    private XXRPZCAD rippleCanadiandollarData;

    @SerializedName("XXRPZEUR")
    private XXRPZEUR rippleEuroData;

    @SerializedName("XRPGBP")
    private XRPGBP ripplePoundData;

    @SerializedName("XXRPZUSD")
    private XXRPZUSD rippleUSdollarData;

    @SerializedName("XXRPZJPY")
    private XXRPZJPY rippleYenData;

    @SerializedName("SCEUR")
    private SCEUR siacoinEuroData;

    @SerializedName("SCUSD")
    private SCUSD siacoinUDdollarData;

    @SerializedName("XXLMZEUR")
    private XXLMZEUR stellarLumensEuroData;

    @SerializedName("XXLMZUSD")
    private XXLMZUSD stellarLumensUSdollarData;

    @SerializedName("USDTCHF")
    private USDTCHF tetherSwissFrancData;

    @SerializedName("USDTEUR")
    private USDTEUR tetherEuroData;

    @SerializedName("USDTZUSD")
    private USDTZUSD tetherUSdollarData;

    @SerializedName("USDTJPY")
    private USDTJPY tetherYenData;

    @SerializedName("XTZEUR")
    private XTZEUR tezosEuroData;

    @SerializedName("XTZUSD")
    private XTZUSD tezosUSdollarData;

    @SerializedName("TRXEUR")
    private TRXEUR tronEuroData;

    @SerializedName("TRXUSD")
    private TRXUSD tronUSdollarData;

    @SerializedName("USDCEUR")
    private USDCEUR usdcEuroData;

    @SerializedName("USDCUSD")
    private USDCUSD usdcUSdollarData;

    @SerializedName("WAVESUSD")
    private WAVESUSD wavesUSdollarData;

    @SerializedName("WAVESEUR")
    private WAVESEUR wavesEuroData;

    @SerializedName("XZECZEUR")
    private XZECZEUR zcashEuroData;

    @SerializedName("XZECZUSD")
    private XZECZUSD zcashUSdollarData;


    public ALGOEUR getAlgorandEuroData() {

        return this.algoEuroData;
    }

    public ALGOUSD getAlgorandUSdollarData() {

        return this.algoUSdollarData;
    }

    public XREPZEUR getAugurEuroData() {

        return this.augurEuroData;
    }

    public XREPZUSD getAugurUSdollarData() {

        return this.augurUSdollarData;
    }

    public BATUSD getBatUSdollarData() {

        return this.batUSdollarData;
    }

    public BATEUR getBatEuroData() {

        return this.batEuroData;
    }

    public XXBTZCAD getBitcoinCanadiandollarData() {

        return this.bitcoinCanadiandollarData;
    }

    public XXBTZEUR getBitcoinEuroData() {

        return this.bitcoinEuroData;
    }

    public XBTCHF getBitcoinSwissFrancData() {

        return this.bitcoinSwissFrancData;
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

    public BCHGBP getBitcoinCashPoundData() {

        return this.bitcoinCashPoundData;
    }

    public BCHUSD getBitcoinCashUSdollarData() {

        return this.bitcoinCashUSdollarData;
    }

    public ATOMEUR getCosmosEuroData() {

        return this.cosmosEuroData;
    }

    public ATOMUSD getCosmosUSdollarData() {

        return this.cosmosUSdollarData;
    }

    public ADAEUR getCardanoEuroData() {

        return this.cardanoEuroData;
    }

    public ADAUSD getCardanoUSdollarData() {

        return this.cardanoUSdollarData;
    }

    public DAIEUR getDaiEuroData() {

        return this.daiEuroData;
    }

    public DAIUSD getDaiUSdollarData() {

        return this.daiUSdollarData;
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

    public ETHCHF getEtherSwissFrancData() {

        return this.etherSwissFrancData;
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

    public LINKEUR getLinkEuroData() {

        return this.linkEuroData;
    }

    public LINKUSD getLinkUSdollarData() {

        return this.linkUSdollarData;
    }

    public XLTCZEUR getLitecoinEuroData() {

        return this.litcoinEuroData;
    }

    public LTCGBP getLitecoinPoundData() {

        return this.litcoinPoundData;
    }

    public XLTCZUSD getLitecoinUSdollarData() {

        return this.litcoinUSdollarData;
    }

    public LSKEUR getLiskEuroData() {

        return this.liskEuroData;
    }

    public LSKUSD getLiskUSdollarData() {

        return this.liskUSdollarData;
    }

    public XXMRZEUR getMoneroEuroData() {

        return this.moneroEuroData;
    }

    public XXMRZUSD getMoneroUSdollarData() {

        return this.moneroUSdollarData;
    }

    public NANOEUR getNanoEuroData() {

        return this.nanoEuroData;
    }

    public NANOUSD getNanoUSdollarData() {

        return this.nanoUSdollarData;
    }

    public OMGEUR getOmiseGOEuroData() {

        return this.omisegoEuroData;
    }

    public OMGUSD getOmiseGOUSdollarData() {

        return this.omisegoUSdollarData;
    }

    public PAXGEUR getPaxgEuroData() {

        return this.paxgEuroData;
    }

    public PAXGUSD getPaxgUSdollarData() {

        return this.paxgUSdollarData;
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

    public XRPGBP getRipplePoundData() {

        return this.ripplePoundData;
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

    public SCEUR getSiacoinEuroData() {

        return this.siacoinEuroData;
    }

    public SCUSD getSiacoinUSdollarData() {

        return this.siacoinUDdollarData;
    }

    public XXLMZEUR getStellarLumensEuroData() {

        return this.stellarLumensEuroData;
    }

    public XXLMZUSD getStellarLumensUSdollarData() {

        return this.stellarLumensUSdollarData;
    }

    public USDTCHF getTetherSwissFrancData() {

        return this.tetherSwissFrancData;
    }

    public USDTEUR getTetherEuroData() {

        return this.tetherEuroData;
    }

    public USDTZUSD getTetherUSdollarData() {

        return this.tetherUSdollarData;
    }

    public USDTJPY getTetherYenData() {

        return this.tetherYenData;
    }

    public XTZEUR getTezosEuroData() {

        return this.tezosEuroData;
    }

    public XTZUSD getTezosUSdollarData() {

        return this.tezosUSdollarData;
    }

    public TRXEUR getTronEuroData() {

        return this.tronEuroData;
    }

    public TRXUSD getTronUSdollarData() {

        return this.tronUSdollarData;
    }

    public USDCEUR getUsdcEuroData() {

        return this.usdcEuroData;
    }

    public USDCUSD getUsdcUSdollarData() {

        return this.usdcUSdollarData;
    }

    public WAVESUSD getWavesUSdollarData() {

        return this.wavesUSdollarData;
    }

    public WAVESEUR getWavesEuroData() {

        return this.wavesEuroData;
    }

    public XZECZEUR getZcashEuroData() {

        return this.zcashEuroData;
    }

    public XZECZUSD getZcashUSdollarData() {

        return this.zcashUSdollarData;
    }
}
