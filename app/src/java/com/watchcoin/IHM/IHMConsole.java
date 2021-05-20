package com.watchcoin.IHM;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.watchcoin.Data.ErrorMessageEvent;
import com.watchcoin.Data.UpdateMarketDataEvent;
import com.watchcoin.Json.CurrencyData;
import com.watchcoin.R;
import com.watchcoin.WebClient.KrakenWebClient;
import com.watchcoin.WebClient.KrakenWebClientListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static com.watchcoin.MainActivity.preferencesApp;

/**
 * The main frame where the market data is displayed and give access to application functionalities
 */
public class IHMConsole extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener, View.OnClickListener {

    private static final String TAG = "IHMConsole";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle navigationViewToggle;

    private Spinner  eCurrency;

    private int intervalTime = 60000;  // 60000 = 1 min

    private Timer refreshDataTimer;
    private TimerTask timerTask;

    private Toolbar toolbar;
    private LinearLayout toolbarLayout;
    private TextView intervalLabel;

    private KrakenWebClient krakenWebClient;

    private CurrencyData currencyData;
    private HashMap<String, String> currencyDataMap;

    private MarketDataFragment marketDataFragment;
    private PreferencesMenuFragment preferencesMenuFragment;

    private final SharedPreferences sharedPreferencesApp = preferencesApp;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ihm_console);


        // Toolbar actions
        toolbar = findViewById(R.id.toolbar);
        toolbarLayout = findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);

        intervalLabel = findViewById(R.id.IntervalLabel);

        // Navigation drawer
        drawerLayout = findViewById(R.id.drawerlayout);

        navigationViewToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close);

        drawerLayout.addDrawerListener(navigationViewToggle);
        navigationViewToggle.syncState();
        navigationViewToggle.setToolbarNavigationClickListener(this);

        // Navigation view
        navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {

            marketDataFragment = new MarketDataFragment();
            preferencesMenuFragment = new PreferencesMenuFragment();
            displayFragment(marketDataFragment);
        }

        // KrakenWebClient instance
        krakenWebClient = new KrakenWebClient(this);

        // Refresh time values
        Spinner refreshTime = findViewById(R.id.IntervalValue);
        refreshTime.setOnItemSelectedListener(this);

        // Ecurrency values
        eCurrency = findViewById(R.id.Ecurrency);

        // Populate the currency spinner
        setCurrencySpinnerValues();

        //eCurrency.setAdapter(eCurrencyValues);
        eCurrency.setOnItemSelectedListener(this);

        // If internet connection is available
        if (isNetworkAvailable()) {

            getMarketData();
        }
        else {

            // Error message displayed
            EventBus.getDefault().post(new ErrorMessageEvent("networkKO"));
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (refreshDataTimer == null) {

            // Initialisation of execution timer
            initTimerExecution(intervalTime);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Cancellation of the refresh data timer
        refreshDataTimer.cancel();
    }


    /**
     * Press detection of the back button
     */
    @Override
    public void onBackPressed() {

        boolean configChanged = sharedPreferencesApp.getBoolean("configChanged", Boolean.FALSE);
        drawerLayout = findViewById(R.id.drawerlayout);

        // If the navigation drawer is displayed
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            // Navigation drawer closing
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {

            // Background application (onDestroy method called)
            super.onBackPressed();
        }

        // If the application configuration was changed
        if (configChanged) {

            // Update data
            updateAllDataDisplayed();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()) {


            case R.id.Ecurrency :
                    krakenWebClient.getDataOfCurrency(parent.getItemAtPosition(position).toString());
                break;


            case R.id.IntervalValue :

                // Refresh time value selected
                String refreshTimeValue = parent.getItemAtPosition(position).toString();

                // Conversion the refresh time value in milliseconds
                if (position == 0) {

                    // Seconds value input
                    intervalTime = Integer.parseInt(refreshTimeValue.split("[^\\d]")[0])*1000;
                }
                else {

                    // Minutes value input
                    intervalTime = Integer.parseInt(refreshTimeValue.split("[^\\d]")[0])*60000;
                }

                // If the refreshDataTimer is created and internet connection is available
                if (refreshDataTimer != null && isNetworkAvailable()) {

                    // Restart refresh data timer
                    refreshDataTimer.cancel();
                    refreshDataTimer = new Timer();
                    initTimerTask();
                    refreshDataTimer.schedule(timerTask, intervalTime, intervalTime);
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case  R.id.MarketData_Menu :
                toolbarLayout.setVisibility(View.VISIBLE);
                displayFragment(marketDataFragment);
                break;

            case  R.id.Settings_Menu :
                toolbarLayout.setVisibility(View.GONE);
                toolbar.setTitle(R.string.Toolbar_settings_title);
                displayFragment(preferencesMenuFragment);
                break;

            default :
                return false;
        }

        // Close navigation view
        drawerLayout = findViewById(R.id.drawerlayout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    /**
     * Check if an internet connection is available or not
     * Return boolean value
     */
    public boolean isNetworkAvailable() {

        ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager != null ? connectManager.getActiveNetworkInfo() : null;

        return (networkInfo != null && networkInfo.isConnected());
    }


    /**
     * Initialisation execution timer method
     * Param intervalTime :  time in milliseconds between successive task executions
     */
    private void initTimerExecution(int intervalTime) {

        refreshDataTimer = new Timer();

        initTimerTask();

        refreshDataTimer.schedule(timerTask, intervalTime, intervalTime);
    }


    /**
     * Initialize the job of the TimerTask
     */
    private void initTimerTask() {

        timerTask = new TimerTask() {

            @Override
            public void run() {

                getMarketData();
            }
        };
    }


    /**
     * Define the values for the ecurrency spinner (toolbar)
     * according to the application settings (sharedpreferences)
     * @return values for the ecurrency spinner (toolbar)
     */
    public void setCurrencySpinnerValues() {

        ArrayList<String> CurrencyValues = new ArrayList<>();

        // If the cryptocurrency configuration is present
        if ((sharedPreferencesApp.contains("kraken_assets_list") &&
                !sharedPreferencesApp.getStringSet("kraken_assets_list", null).isEmpty()) ||
                (sharedPreferencesApp.contains("defi_kraken_assets_list") &&
                        !sharedPreferencesApp.getStringSet("defi_kraken_assets_list", null).isEmpty())) {

            // Fiat currencies configuration
            boolean australianDollarSelected = sharedPreferencesApp.getBoolean("australian_dollar_checkbox", Boolean.FALSE);
            boolean americanDollarSelected = sharedPreferencesApp.getBoolean("us_dollar_checkbox", Boolean.FALSE);
            boolean britishPoundsSelected = sharedPreferencesApp.getBoolean("british_pounds_checkbox", Boolean.FALSE);
            boolean canadianDollarSelected = sharedPreferencesApp.getBoolean("canadian_dollar_checkbox", Boolean.FALSE);
            boolean eurosSelected = sharedPreferencesApp.getBoolean("euros_checkbox", Boolean.FALSE);
            boolean swissFrancSelected = sharedPreferencesApp.getBoolean("swiss_franc_checkbox", Boolean.FALSE);
            boolean japaneseYenSelected = sharedPreferencesApp.getBoolean("japanese_yen_checkbox", Boolean.FALSE);

            // If any fiat currencies are not selected
            if (!australianDollarSelected && !americanDollarSelected && !britishPoundsSelected && !canadianDollarSelected && !eurosSelected && !japaneseYenSelected
                && !swissFrancSelected) {

                // US dollar activated by default
                SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
                preferencesEditor.putBoolean("us_dollar_checkbox", Boolean.TRUE);
                preferencesEditor.apply();

                americanDollarSelected = true;
            }

            // Cryptocurrency configuration
            Set<String> krakenAssetsList = new HashSet<>();

            krakenAssetsList.addAll(sharedPreferencesApp.getStringSet("kraken_assets_list", null));
            krakenAssetsList.addAll(sharedPreferencesApp.getStringSet("defi_kraken_assets_list", null));

            // Datas initialisation for the ecurrency spinner
            for (String krakenAsset : krakenAssetsList) {

                switch (krakenAsset) {

                    // Aragon (ANT) | Augur (REP) | Balancer (BAL) | Basic Attention Token (BAT) | Compound (COMP)
                    // Curve (CRV) | Dai (DAI) | DASH (DASH) | Decentraland (MANA) | EOS (EOS) | Ether Classic (ETC) | Gnosis (GNO)
                    // Kava (KAVA) | Keep (KEEP) | Kyber Network (KNC) | Lisk (LSK) | Monero (XMR) | Nano (NANO) | OmiseGO (OMG) | Orchid (OXT) | Pax Gold (PAXG)
                    // Quantum (QTUM) | Siacoin (SC) | Storj (STORJ) | tBTC (TBTC) | Tron (TRX) | Uniswap (UNI) |
                    // Waves (WAVES) | Zcash (ZEC)
                    case "ANT" :
                    case "REP":
                    case "BAL" :
                    case "BAT":
                    case "COMP" :
                    case "CRV" :
                    case "DAI" :
                    case "DASH" :
                    case "MANA" :
                    case "EOS" :
                    case "ETC" :
                    case "GNO" :
                    case "KAVA" :
                    case "KEEP" :
                    case "KNC" :
                    case "LSK" :
                    case "XMR" :
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
                    case "ZEC" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.US_dollar)));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Euro)));
                        }

                        break;

                    // Algorand (ALGO) | Flow (FLOW)
                    case "ALGO" :
                    case "FLOW" :

                        // EUR, GBP, USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.US_dollar)));
                        }

                        if (britishPoundsSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Pounds)));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Euro)));
                        }
                        break;


                    // Bitcoin (XBT) | Ether (ETH)
                    case "XBT" :
                    case "ETH" :

                        // AUD, CAD, EUR, CHF, GBP, JPY and USD
                        if (australianDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Australian_dollar)));
                        }

                        if (americanDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.US_dollar)));
                        }

                        if (britishPoundsSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Pounds)));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Canadian_dollar)));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Euro)));
                        }

                        if (swissFrancSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Swiss_Franc_symbol)));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Yen)));
                        }

                        break;

                    //  Aave (AAVE) | Bitcoin Cash (BCH) | Cardano (ADA) | Chainlink (LINK) | Cosmos (ATOM) |  Filecoin (FIL) | Graph (GRT) |  Kusama (KSM) | Litecoin (LTC) | Polkdot (DOT) |
                    // Stellar Lumens (XLM) | Synthetix (SNX) | Tezos (XTZ) | USD Coin (USDC) | Yearn (YFI)
                    case "AAVE" :
                    case "BCH" :
                    case "ADA" :
                    case "LINK" :
                    case "ATOM" :
                    case "FIL" :
                    case "GRT" :
                    case "LTC" :
                    case "KSM" :
                    case "DOT":
                    case "XLM" :
                    case "SNX" :
                    case "XTZ" :
                    case "USDC" :
                    case "YFI" :

                        // AUD, EUR, GBP and USD
                        if (australianDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Australian_dollar)));
                        }

                        if (americanDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.US_dollar)));
                        }

                        if (britishPoundsSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Pounds)));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Euro)));
                        }

                        break;

                    // Ripple (XRP)
                    case "XRP" :

                        // AUD, CAD, GBP,  EUR, JPY and USD
                        if (australianDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Australian_dollar)));
                        }

                        if (americanDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.US_dollar)));
                        }

                        if (britishPoundsSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Pounds)));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Canadian_dollar)));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Euro)));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Yen)));
                        }

                        break;

                    // Tether (USDT)
                    case "USDT" :

                        // AUD, EUR, CHF, JPY and USD
                        if (australianDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Australian_dollar)));
                        }

                        if (americanDollarSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.US_dollar)));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Euro)));
                        }

                        if (swissFrancSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Swiss_Franc_symbol)));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), krakenAsset, getString(R.string.Yen)));
                        }

                        break;
                }
            }

        }
        // Default cryptocurrency configuration application
        else {

            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), "XBT", getString(R.string.US_dollar)));
            CurrencyValues.add(String.format(getString(R.string.Kraken_asset), "ETH", getString(R.string.US_dollar)));

            // US dollar activated by default
            SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
            preferencesEditor.putBoolean("us_dollar_checkbox", Boolean.TRUE);

            // Bitcoin and Ether default configuration
//            Set<String> setDefaultCryptocurrency = new HashSet<>();
//            setDefaultCryptocurrency.add("XBT");
//            setDefaultCryptocurrency.add("ETH");

//            preferencesEditor.putStringSet("kraken_assets_list", setDefaultCryptocurrency);

            preferencesEditor.apply();
        }

        ArrayAdapter<String> eCurrencyValues = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                CurrencyValues);

        eCurrency.setAdapter(eCurrencyValues);
    }


    /**
     * Display the fragment in the main container
     * @param fragment : The fragment to display
     */
    private void displayFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.ContainerMain, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    /**
     * Update the currency spinner values and  market data
     */
    private void updateAllDataDisplayed () {

        // Update the currency spinner values
        setCurrencySpinnerValues();

        // Update market data with the new fiat currencies configuration
        getMarketData();

        // Set configChanged = false
        SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
        preferencesEditor.putBoolean("configChanged", Boolean.FALSE);
        preferencesEditor.apply();
    }

    public void updateViewLanguage() {

        intervalLabel.setText(R.string.Interval_label);
        toolbar.setTitle(R.string.Language_toolbar_title);

        // Update navigation drawer menus
        navigationView.getMenu().findItem(R.id.MarketData_Menu).setTitle(R.string.Navigation_drawer_menu1);
        navigationView.getMenu().findItem(R.id.Settings_Menu).setTitle(R.string.Navigation_drawer_menu2);

        if (currencyDataMap != null) {

            // Update market data title
            switch (eCurrency.getSelectedItem().toString()) {

                case "AAVE/AUD" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Aave_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "AAVE/USD" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Aave_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "AAVE/EUR" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Aave_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "AAVE/GBP" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Aave_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "ALGO/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Algorand_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "ALGO/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Algorand_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "ALGO/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Algorand_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ANT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Aragon_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "ANT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Aragon_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "REP/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Augur_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "REP/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Augur_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "BAL/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Balancer_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "BAL/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Balancer_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "BAT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.BAT_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "BAT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.BAT_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XBT/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "XBT/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
                    break;

                case "XBT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XBT/CHF":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.Swiss_Franc_symbol)));
                    break;

                case "XBT/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "XBT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "XBT/JPY":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Market_data_title), getString(R.string.Yen_symbol)));
                    break;

                case "BCH/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Cash_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "BCH/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Cash_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "BCH/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Cash_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "BCH/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Cash_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ADA/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cardano_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ADA/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cardano_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "COMP/USD" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Compound_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "COMP/EUR" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Compound_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ATOM/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "ATOM/EUR" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ATOM/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "ATOM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "CRV/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Curve_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "CRV/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Curve_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "DAI/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Dai_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "DAI/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Dai_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "DASH/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Dash_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "DASH/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Dash_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "MANA/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Decentraland_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "MANA/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Decentraland_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "EOS/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Eos_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "EOS/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Eos_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "ETH/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "ETH/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
                    break;

                case "ETH/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ETH/CHF":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.Swiss_Franc_symbol)));
                    break;

                case "ETH/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "ETH/JPY":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.Yen_symbol)));
                    break;

                case "ETH/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ether_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "ETC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Etherc_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ETC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Etherc_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "FIL/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Filecoin_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "FIL/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Filecoin_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "FIL/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Filecoin_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "FIL/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Filecoin_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "GRT/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Graph_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "GRT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Graph_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "GRT/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Graph_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "GRT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Graph_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "FLOW/EUR":
                    //currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Flow_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "FLOW/GBP":
                    //currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Flow_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "FLOW/USD":
                    //currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Flow_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "GNO/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Gnosis_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "GNO/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Gnosis_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "KAVA/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kava_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "KAVA/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kava_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "KEEP/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Keep_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "KEEP/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Keep_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "KNC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kyber_Network_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "KNC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kyber_Network_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "KSM/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kusama_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "KSM/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kusama_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "KSM/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kusama_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "KSM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Kusama_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "LINK/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Chainlink_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "LINK/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Chainlink_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "LINK/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Chainlink_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "LINK/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Chainlink_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "LTC/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Litcoin_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "LTC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Litcoin_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "LTC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Litcoin_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "LTC/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Litcoin_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "LSK/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Lisk_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "LSK/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Lisk_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "TBTC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.tBTC_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "TBTC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.tBTC_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "USDT/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tether_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "USDT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tether_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "USDT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tether_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "USDT/CHF":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tether_Market_data_title), getString(R.string.Swiss_Franc_symbol)));
                    break;

                case "USDT/JPY":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tether_Market_data_title), getString(R.string.Yen_symbol)));
                    break;

                case "XMR/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Monero_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XMR/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Monero_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "NANO/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Nano_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "NANO/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Nano_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "OMG/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.OmiseGO_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "OMG/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.OmiseGO_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "OXT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Orchid_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "OXT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Orchid_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "PAXG/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Paxg_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "PAXG/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Paxg_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "DOT/AUD" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Polkadot_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "DOT/EUR" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Polkadot_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "DOT/GBP" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Polkadot_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "DOT/USD" :
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Polkadot_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "QTUM/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Quantum_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "QTUM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Quantum_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "XRP/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ripple_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "XRP/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ripple_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
                    break;

                case "XRP/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ripple_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XRP/JPY":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ripple_Market_data_title), getString(R.string.Yen_symbol)));
                    break;

                case "XRP/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ripple_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "XRP/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Ripple_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "SC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Siacoin_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "SC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Siacoin_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XLM/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Stellar_Lumens_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "XLM/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Stellar_Lumens_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XLM/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Stellar_Lumens_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "XLM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Stellar_Lumens_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "STORJ/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Storj_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "STORJ/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Storj_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "SNX/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Synthetix_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "SNX/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Synthetix_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "SNX/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Synthetix_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "SNX/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Synthetix_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XTZ/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "XTZ/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XTZ/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "XTZ/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "TRX/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tron_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "TRX/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tron_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "UNI/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Uniswap_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "UNI/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Uniswap_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "USDC/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.USDCoin_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "USDC/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.USDCoin_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "USDC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.USDCoin_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "USDC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.USDCoin_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "WAVES/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Waves_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "WAVES/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Waves_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "YFI/AUD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Yearn_Market_data_title), getString(R.string.Australian_dollar_symbol)));
                    break;

                case "YFI/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Yearn_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "YFI/GBP":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Yearn_Market_data_title), getString(R.string.Pounds_symbol)));
                    break;

                case "YFI/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Yearn_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ZEC/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Zcash_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ZEC/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Zcash_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;
            }
        }
        else {

            // Error message displayed
            EventBus.getDefault().post(new ErrorMessageEvent("networkKO"));
        }
    }


    /**
     * This method is only called whenever the contents of the back stack change.
     * (transaction add to back stack PreferencesMenuFragment -> displayFragment method)
     */
    @Override
    public void onBackStackChanged() {

        // If a element is present in the back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            // Show the arrow icon in the toolbar
            navigationViewToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else {

            // Show the hamburger icon in the toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            navigationViewToggle.setDrawerIndicatorEnabled(true);
            toolbar.setTitle(R.string.Toolbar_settings_title);

            boolean configLanguageChanged = sharedPreferencesApp.getBoolean("configLanguageChanged", Boolean.FALSE);

            // If the language configuration was changed
            if (configLanguageChanged) {

                // Update views of preferences menu fragment
                preferencesMenuFragment.updateView();

                // Update views of market data fragment (title market data more precisely)
                EventBus.getDefault().post(new UpdateMarketDataEvent(currencyDataMap));

                // Set configChanged = false
                SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
                preferencesEditor.putBoolean("configLanguageChanged", Boolean.FALSE);
                preferencesEditor.apply();

            }
        }
    }


    /**
     * This method is only called if the Arrow icon is shown
     * @param v
     */
    @Override
    public void onClick(View v) {

        boolean configChanged = sharedPreferencesApp.getBoolean("configChanged", Boolean.FALSE);

        // If the application configuration was changed
        if (configChanged) {

            // Update data displayed
            updateAllDataDisplayed();
        }

        // Pop the top state off the back stack (PreferencesMenuFragment instance theoretically)
        getSupportFragmentManager().popBackStack();
    }

    private void getMarketData() {

        // Market data request
        krakenWebClient.launchPublicQuery(new KrakenWebClientListener() {
            @Override
            public void onError(String message) {
                EventBus.getDefault().post(new ErrorMessageEvent(message));
            }

            @Override
            public void onResponse(Object response) {

                if (response == null) {
                    // Display error message
                    EventBus.getDefault().post(new ErrorMessageEvent("noMarketData"));
                }
                else if (response instanceof String) {

                    EventBus.getDefault().post(new ErrorMessageEvent(response.toString()));
                }
            }
        });
    }

    /*** Getter/Setter currency data ***/

    public CurrencyData getCurrencyData() {

        return this.currencyData;
    }


    public void setCurrencyData(CurrencyData currencyData) {

        this.currencyData = currencyData;
    }


    public HashMap<String, String> getCurrencyDataMap() {

        return this.currencyDataMap;
    }

    public void setCurrencyDataMap(HashMap<String, String> currencyDataMap) {

        this.currencyDataMap = currencyDataMap;
    }


    /*** Getter toolbar ***/

    public Toolbar getToolbar() {

        return this.toolbar;
    }


    /*** Getter kraken API default address ***/

    public String getDefaultKrakenApiAddress () {

        return getString(R.string.Kraken_api_default_value);
    }

    /*** Getter currency selected ***/

    public String getCurrencySelected() {

        return this.eCurrency.getSelectedItem().toString();
    }
}