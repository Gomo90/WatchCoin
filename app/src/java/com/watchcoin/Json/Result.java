package com.watchcoin.Json;

import com.google.gson.annotations.SerializedName;

/**
 * Class used for JSON data deserialization received by the Kraken API
 */
public class Result {

    @SerializedName(value = "AAVEEUR", alternate = {"AAVEAUD", "AAVEUSD", "AAVEGBP"})
    private Aave aaveData;

    @SerializedName(value = "ALGOEUR", alternate = {"ALGOGBP", "ALGOUSD"})
    private Algorand algorandData;

    @SerializedName(value = "ANTEUR", alternate = {"ANTUSD"})
    private Aragon aragonData;

    @SerializedName(value = "XREPZEUR", alternate = {"XREPZUSD"})
    private Augur augurData;

    @SerializedName(value = "BALEUR", alternate = {"BALUSD"})
    private Balancer balancerData;

    @SerializedName(value = "BATEUR", alternate = {"BATUSD"})
    private BasicAttentionToken basicAttentionTokenData;

    @SerializedName(value ="XBTAUD", alternate = {"XXBTZCAD", "XXBTZEUR", "XBTCHF", "XXBTZGBP", "XXBTZUSD", "XXBTZJPY"})
    private Bitcoin bitcoinData;

    @SerializedName(value = "BCHAUD", alternate = {"BCHEUR", "BCHGBP", "BCHUSD"})
    private BitcoinCash bitcoinCashData;

    @SerializedName(value = "COMPEUR", alternate = {"COMPUSD"})
    private Compound compoundData;

    @SerializedName(value = "ATOMEUR", alternate = {"ATOMAUD, ATOMGBP, ATOMUSD"})
    private Cosmos cosmosData;

    @SerializedName(value = "ADAEUR", alternate = {"ADAAUD, ADAGBP, ADAUSD"})
    private Cardano cardanoData;

    @SerializedName(value = "CRVEUR", alternate = {"CRVUSD"})
    private Curve curveData;

    @SerializedName(value = "DAIEUR", alternate = {"DAIUSD"})
    private DAI daiData;

    @SerializedName(value = "DASHEUR", alternate = {"DASHUSD"})
    private Dash dashData;

    @SerializedName(value = "MANAEUR", alternate = {"MANAUSD"})
    private Decentraland decentralandData;

    @SerializedName(value = "EOSEUR", alternate = {"EOSUSD"})
    private EOS eosData;

    @SerializedName(value ="ETHAUD", alternate = {"XETHZCAD", "XETHZEUR", "ETHCHF", "XETHZGBP", "XETHZJPY", "XETHZUSD"})
    private Ethereum ethereumData;

    @SerializedName(value = "XETCZEUR", alternate = {"XETCZUSD"})
    private EthereumClassic ethereumClassicData;

    @SerializedName(value = "FILEUR", alternate = {"FILAUD", "FILGBP", "FILUSD"})
    private Filecoin filecoinData;

    @SerializedName(value = "GRTEUR", alternate = {"GRTAUD", "GRTGBP", "GRTUSD"})
    private Graph graphData;

    @SerializedName(value = "FLOWEUR", alternate = {"FLOWUSD", "FLOWGBP"})
    private Flow flowData;

    @SerializedName(value = "GNOEUR", alternate = {"GNOUSD"})
    private Gnosis gnosisData;

    @SerializedName(value = "KAVAEUR", alternate = {"KAVAUSD"})
    private KAVA kavaData;

    @SerializedName(value = "KEEPEUR", alternate = {"KEEPUSD"})
    private Keep keepData;

    @SerializedName(value = "KNCEUR", alternate = {"KNCUSD"})
    private KyberNetworkToken kyberNetworkData;

    @SerializedName(value = "KSMEUR", alternate = {"KSMAUD", "KSMGBP", "KSMUSD"})
    private Kusama kusamaData;

    @SerializedName(value = "LINKEUR", alternate =  {"LINKAUD", "LINKGBP", "LINKUSD"})
    private Chainlink chainlinkData;

    @SerializedName(value ="LTCAUD", alternate = {"LTCGBP", "XLTCZEUR", "XLTCZUSD"})
    private Litecoin litecoinData;

    @SerializedName(value = "LSKEUR", alternate = {"LSKUSD"})
    private Lisk liskData;

    @SerializedName(value = "XXMRZEUR", alternate = {"XXMRZUSD"})
    private Monero moneroData;

    @SerializedName(value = "NANOEUR", alternate = {"NANOUSD"})
    private Nano nanoData;

    @SerializedName(value = "OMGEUR", alternate = {"OMGUSD"})
    private OmiseGO omisegoData;

    @SerializedName(value = "OXTEUR", alternate = {"OXTUSD"})
    private Orchid orchidData;

    @SerializedName(value = "PAXGEUR", alternate = {"PAXGUSD"})
    private PaxGold paxGoldData;

    @SerializedName(value = "DOTEUR", alternate = {"DOTAUD", "DOTGBP", "DOTUSD"})
    private Polkadot polkadotData;

    @SerializedName(value = "QTUMEUR", alternate = {"QTUMUSD"})
    private Quantum quantumData;

    @SerializedName(value = "XRPAUD", alternate = {"XXRPZCAD", "XXRPZEUR", "XRPGBP", "XXRPZUSD", "XXRPZJPY"})
    private Ripple rippleData;

    @SerializedName(value = "SCEUR", alternate = {"SCUSD"})
    private Siacoin siacoinData;

    @SerializedName(value = "XXLMZEUR", alternate = {"XXLMZAUD", "XXLMZGBP", "XXLMZUSD"})
    private StellarLumens stellarLumensData;

    @SerializedName(value = "STORJEUR", alternate = {"STORJUSD"})
    private Storj storjData;

    @SerializedName(value = "SNXEUR", alternate = {"SNXAUD", "SNXGBP", "SNXUSD"})
    private Synthetix synthetixData;

    @SerializedName(value = "TBTCEUR", alternate = {"TBTCUSD"})
    private tBTC tBTCData;

    @SerializedName(value = "USDTAUD", alternate = {"USDTCHF", "USDTEUR", "USDTUSD", "USDTJPY"})
    private Tether tetherData;

    @SerializedName(value = "XTZEUR", alternate = {"XTZAUD", "XTZGBP", "XTZUSD"})
    private Tezos tezosData;

    @SerializedName(value = "TRXEUR", alternate = {"TRXUSD"})
    private Tron tronData;

    @SerializedName(value = "UNIEUR", alternate = {"UNIUSD"})
    private Uniswap uniswapData;

    @SerializedName(value = "USDCEUR", alternate = {"USDCAUD", "USDCGBP", "USDCUSD"})
    private USDCToken usdcData;

    @SerializedName(value = "WAVESEUR", alternate = {"WAVESUSD"})
    private Waves wavesData;

    @SerializedName(value = "YFIEUR", alternate = {"YFIAUD", "YFIGBP", "YFIUSD"})
    private Yearn yearnData;

    @SerializedName(value = "XZECZEUR", alternate = {"XZECZUSD"})
    private Zcash zcashData;


    public Aave getAaveData() {

        return this.aaveData;
    }

    public void setAaveData(Aave aaveData) {

        this.aaveData = aaveData;
    }

    public Algorand getAlgorandData() {

        return this.algorandData;
    }

    public void setAlgorandData(Algorand algorandData) {

        this.algorandData = algorandData;
    }

    public Aragon getAragonData() {

        return this.aragonData;
    }

    public void setAragonData(Aragon aragonData) {

        this.aragonData = aragonData;
    }

    public Augur getAugurData() {

        return this.augurData;
    }

    public void setAugurData(Augur augurData) {

        this.augurData = augurData;
    }

    public Balancer getBalancerData() {

        return this.balancerData;
    }

    public void setBalancerData(Balancer balancerData) {

        this.balancerData = balancerData;
    }

    public BasicAttentionToken getBasicAttentionTokenData() {

        return this.basicAttentionTokenData;
    }

    public void setBasicAttentionTokenData(BasicAttentionToken basicAttentionTokenData) {

        this.basicAttentionTokenData = basicAttentionTokenData;
    }

    public Bitcoin getBitcoinData() {

        return this.bitcoinData;
    }

    public void setBitcoinData(Bitcoin bitcoinData) {

        this.bitcoinData = bitcoinData;
    }

    public BitcoinCash getBitcoinCashData() {

        return this.bitcoinCashData;
    }

    public void setBitcoinCashData(BitcoinCash bitcoinCashData) {

        this.bitcoinCashData = bitcoinCashData;
    }

    public Compound getCompoundData() {

        return this.compoundData;
    }

    public void setCompoundData(Compound compoundData) {

        this.compoundData = compoundData;
    }

    public Cosmos getCosmosData() {

        return this.cosmosData;
    }

    public void setCosmosData(Cosmos cosmosData) {

        this.cosmosData = cosmosData;
    }

    public Cardano getCardanoData() {

        return this.cardanoData;
    }

    public Curve getCurveData() {

        return this.curveData;
    }

    public void setCurveData(Curve curveData) {

        this.curveData = curveData;
    }

    public void setCardanoData(Cardano cardanoData) {

        this.cardanoData = cardanoData;
    }

    public DAI getDaiData() {

        return this.daiData;
    }

    public void setDaiData(DAI daiData) {

        this.daiData = daiData;
    }

    public Dash getDashData() {

        return this.dashData;
    }

    public void setDashData(Dash dashData) {

        this.dashData = dashData;
    }

    public Decentraland getDecentralandData() {

        return this.decentralandData;
    }

    public void setDecentralandData(Decentraland decentralandData) {

        this.decentralandData = decentralandData;
    }

    public EOS getEosData() {

        return this.eosData;
    }

    public void setEosData(EOS eosData) {

        this.eosData = eosData;
    }

    public Ethereum getEthereumData() {

        return this.ethereumData;
    }

    public void setEthereumData(Ethereum ethereumData) {

        this.ethereumData = ethereumData;
    }

    public EthereumClassic getEthereumClassicData() {

        return this.ethereumClassicData;
    }

    public void setEthereumClassicData(EthereumClassic ethereumClassicData) {

        this.ethereumClassicData = ethereumClassicData;
    }

    public Filecoin getFilecoinData() {

        return this.filecoinData;
    }

    public void setFilecoinData(Filecoin filecoinData) {

        this.filecoinData = filecoinData;
    }

    public Flow getFlowData() {

        return this.flowData;
    }

    public void setFlowData(Flow flowData) {

        this.flowData = flowData;
    }

    public Gnosis getGnosisData() {

        return this.gnosisData;
    }

    public void setGnosisData(Gnosis gnosisData) {

        this.gnosisData = gnosisData;
    }

    public Graph getGraphData() {

        return this.graphData;
    }

    public void setGraphData(Graph graphData) {

        this.graphData = graphData;
    }

    public KAVA getKavaData() {

        return this.kavaData;
    }

    public void setKavaData(KAVA kavaData) {

        this.kavaData =  kavaData;
    }

    public Keep getKeepData() {

        return this.keepData;
    }

    public void setKeepData(Keep keepData) {

        this.keepData =  keepData;
    }

    public KyberNetworkToken getKyberNetworkData() {

        return this.kyberNetworkData;
    }

    public void setKyberNetworkData(KyberNetworkToken kyberNetworkData) {

        this.kyberNetworkData = kyberNetworkData;
    }

    public Kusama getKusamaData() {

        return this.kusamaData;
    }

    public void setKusamaData(Kusama kusamaData) {

        this.kusamaData = kusamaData;
    }

    public Chainlink getChainlinkData() {

        return this.chainlinkData;
    }

    public void setChainlinkData(Chainlink chainlinkData) {

        this.chainlinkData = chainlinkData;
    }

    public Litecoin getLitecoinData() {

        return this.litecoinData;
    }

    public void setLitecoinData(Litecoin litecoinData) {

        this.litecoinData = litecoinData;
    }

    public Lisk getLiskData() {

        return this.liskData;
    }

    public void setLiskData(Lisk liskData) {

        this.liskData = liskData;
    }

    public Monero getMoneroData() {

        return this.moneroData;
    }

    public void setMoneroData(Monero moneroData) {

        this.moneroData = moneroData;
    }

    public Nano getNanoData() {

        return this.nanoData;
    }

    public void setNanoData(Nano nanoData) {

        this.nanoData = nanoData;
    }

    public OmiseGO getOmiseGOData() {

        return this.omisegoData;
    }

    public void setOmiseGOData(OmiseGO omiseGOData) {

        this.omisegoData = omiseGOData;
    }

    public Orchid getOrchidData() {

        return this.orchidData;
    }

    public void setOrchidData(Orchid orchidData) {

        this.orchidData = orchidData;
    }

    public PaxGold getPaxGoldData() {

        return this.paxGoldData;
    }

    public void setPaxGoldData(PaxGold paxGoldData) {

        this.paxGoldData = paxGoldData;
    }

    public Polkadot getPolkadotData() {

        return this.polkadotData;
    }

    public void setPolkadotData(Polkadot polkadotData) {

        this.polkadotData = polkadotData;
    }

    public Quantum getQuantumData() {

        return this.quantumData;
    }

    public void setQuantumData(Quantum quantumData) {

        this.quantumData = quantumData;
    }

    public Ripple getRippleData() {

        return this.rippleData;
    }

    public void setRippleData(Ripple rippleData) {

        this.rippleData = rippleData;
    }

    public Siacoin getSiacoinData() {

        return this.siacoinData;
    }

    public void setSiacoinData(Siacoin siacoinData) {

        this.siacoinData = siacoinData;
    }

    public StellarLumens getStellarLumensData() {

        return this.stellarLumensData;
    }

    public void setStellarLumensData(StellarLumens stellarLumensData) {

        this.stellarLumensData = stellarLumensData;
    }

    public Storj getStorjData() {

        return this.storjData;
    }

    public void setStorjData(Storj storjData) {

        this.storjData = storjData;
    }

    public Synthetix getSynthetixData() {

        return this.synthetixData;
    }

    public void setSynthetixData(Synthetix synthetixData) {

        this.synthetixData = synthetixData;
    }

    public tBTC gettBTCData() {

        return this.tBTCData;
    }

    public void settBTCData(tBTC tBTCData) {

        this.tBTCData = tBTCData;
    }

    public Tether getTetherData() {

        return this.tetherData;
    }

    public void setTetherData(Tether tetherData) {

        this.tetherData = tetherData;
    }

    public Tezos getTezosData() {

        return this.tezosData;
    }

    public void setTezosData(Tezos tezosData) {

        this.tezosData = tezosData;
    }

    public Tron getTronData() {

        return this.tronData;
    }

    public void setTronData(Tron tronData) {

        this.tronData = tronData;
    }

    public Uniswap getUniswapData() {

        return this.uniswapData;
    }

    public void setUniswapData(Uniswap uniswapData) {

        this.uniswapData = uniswapData;
    }

    public USDCToken getUsdcTokenData() {

        return this.usdcData;
    }

    public void setUsdcTokenData(USDCToken UsdcTokenData) {

        this.usdcData = UsdcTokenData;
    }

    public Waves getWavesData() {

        return this.wavesData;
    }

    public void setWavesData(Waves wavesData) {

        this.wavesData = wavesData;
    }

    public Yearn getYearnData() {

        return this.yearnData;
    }

    public void setYearnData(Yearn yearnData) {

        this.yearnData = yearnData;
    }

    public Zcash getZcashData() {

        return this.zcashData;
    }

    public void setZcashData(Zcash zCashData) {

        this.zcashData = zCashData;
    }
}
