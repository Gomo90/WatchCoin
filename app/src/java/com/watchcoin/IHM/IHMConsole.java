package com.watchcoin.IHM;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.watchcoin.Data.ErrorMessageEvent;
import com.watchcoin.Data.UpdateMarketDataEvent;
import com.watchcoin.Json.CurrencyData;
import com.watchcoin.R;
import com.watchcoin.WebClient.KrakenWebClient;

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

    private Spinner  refreshTime;
    private Spinner  eCurrency;

    private int intervalTime = 60000;  // 60000 = 1 min

    private Timer refreshDataTimer;
    private TimerTask timerTask;

    private Toolbar toolbar;
    private LinearLayout toolbarLayout;
    private TextView intervalLabel;

    private KrakenWebClient krakenWebClient, krakenWebClient2;

    private IHMConsole ihmConsoleInstance = this;

    private CurrencyData currencyData;
    private HashMap<String, String> currencyDataMap;

    private MarketDataFragment marketDataFragment;
    private PreferencesMenuFragment preferencesMenuFragment;

    private SharedPreferences sharedPreferencesApp = preferencesApp;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ihm_console);


        // Toolbar actions
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarLayout = (LinearLayout) findViewById(R.id.toolbarLayout);
        setSupportActionBar(toolbar);

        intervalLabel = (TextView) findViewById(R.id.IntervalLabel);

        // Navigation drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        navigationViewToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Navigation_drawer_open, R.string.Navigation_drawer_close);

        drawerLayout.addDrawerListener(navigationViewToggle);
        navigationViewToggle.syncState();
        navigationViewToggle.setToolbarNavigationClickListener(this);

        // Navigation view
        navigationView = (NavigationView) findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {

            marketDataFragment = new MarketDataFragment();
            preferencesMenuFragment = new PreferencesMenuFragment();
            displayFragment(marketDataFragment);
        }

        // KrakenWebClient instance
        krakenWebClient = new KrakenWebClient(this);

        // Refresh time values
        refreshTime = (Spinner) findViewById(R.id.IntervalValue);
        refreshTime.setOnItemSelectedListener(this);

        // Ecurrency values
        eCurrency = (Spinner) findViewById(R.id.Ecurrency);

        // Populate the currency spinner
        setCurrencySpinnerValues();

        //eCurrency.setAdapter(eCurrencyValues);
        eCurrency.setOnItemSelectedListener(this);

        // If internet connection is available
        if (isNetworkAvailable()) {

            // Market data request
            krakenWebClient.launchPublicQuery();
            krakenWebClient = null;
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

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

                    krakenWebClient = null;
                    krakenWebClient = new KrakenWebClient(ihmConsoleInstance);
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
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    /**
     * Check if an internet connection is available or not
     * Return boolean value
     */
    public boolean isNetworkAvailable() {

        ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();

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

                krakenWebClient = new KrakenWebClient(ihmConsoleInstance);
                krakenWebClient.launchPublicQuery();
            }
        };
    }


    /**
     * Define the values for the ecurrency spinner (toolbar)
     * according to the application settings (sharedpreferences)
     * @return values for the ecurrency spinner (toolbar)
     */
    public void setCurrencySpinnerValues() {

        ArrayList<String> CurrencyValues = new ArrayList<String>();

        // If the cryptocurrency configuration is present
        if (sharedPreferencesApp.contains("kraken_assets_list") &&
                !sharedPreferencesApp.getStringSet("kraken_assets_list", null).isEmpty()) {

            // Fiat currencies configuration
            boolean americanDollarSelected = sharedPreferencesApp.getBoolean("us_dollar_checkbox", Boolean.FALSE);
            boolean britishPoundsSelected = sharedPreferencesApp.getBoolean("british_pounds_checkbox", Boolean.FALSE);
            boolean canadianDollarSelected = sharedPreferencesApp.getBoolean("canadian_dollar_checkbox", Boolean.FALSE);
            boolean eurosSelected = sharedPreferencesApp.getBoolean("euros_checkbox", Boolean.FALSE);
            boolean swissFrancSelected = sharedPreferencesApp.getBoolean("swiss_franc_checkbox", Boolean.FALSE);
            boolean japaneseYenSelected = sharedPreferencesApp.getBoolean("japanese_yen_checkbox", Boolean.FALSE);

            // If any fiat currencies are not selected
            if (!americanDollarSelected && !britishPoundsSelected && !canadianDollarSelected && !eurosSelected && !japaneseYenSelected
                && !swissFrancSelected) {

                // US dollar activated by default
                SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
                preferencesEditor.putBoolean("us_dollar_checkbox", Boolean.TRUE);
                preferencesEditor.commit();

                americanDollarSelected = true;
            }

            // Cryptocurrency configuration
            Set<String> krakenAssetsList = sharedPreferencesApp.getStringSet("kraken_assets_list", null);

            // Datas initialisation for the ecurrency spinner
            for (String krakenAsset : krakenAssetsList) {

                switch (krakenAsset) {

                    // Algorand (ALGO) | Augur (REP) | Basic Attention Token (BAT) | Cardano (ADA) | Cosmos (ATOM)
                    // Dai (DAI) | DASH (DASH) | EOS (EOS) | Ether Classic (ETC) | Gnosis (GNO) | Chainlink (LINK)
                    // Lisk(LSK) | Monero (XMR) | Nano (NANO) | OmiseGO (OMG) | Orchid (OXT) | Pax Gold (PAXG) | Quantum (QTUM)
                    // Siacoin (SC) | Stellar Lumens (XLM) | Tezos (XTZ) | Tron (TRX) | USD Coin (USDC)
                    // Waves (WAVES) | Zcash (ZEC)
                    case "ALGO":
                    case "REP":
                    case "BAT":
                    case "ADA" :
                    case "ATOM" :
                    case "DAI" :
                    case "DASH" :
                    case "EOS" :
                    case "ETC" :
                    case "GNO" :
                    case "LINK" :
                    case "LSK" :
                    case "XMR" :
                    case "NANO" :
                    case "OMG" :
                    case "OXT" :
                    case "PAXG" :
                    case "QTUM" :
                    case "SC" :
                    case "XLM" :
                    case "XTZ" :
                    case "TRX" :
                    case "USDC" :
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

                    // Bitcoin (XBT) | Ether (ETH)
                    case "XBT" :
                    case "ETH" :

                        // CAD, EUR, CHF, GBP, JPY and USD
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

                    // Bitcoin Cash (BCH) | Litecoin (LTC)
                    case "BCH" :
                    case "LTC" :

                        // EUR, GBP and USD
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

                        // CAD, GBP,  EUR, JPY and USD
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

                        // EUR, CHF, JPY and USD
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

            preferencesEditor.commit();
        }

        ArrayAdapter<String> eCurrencyValues = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,
                CurrencyValues);

        eCurrency.setAdapter(eCurrencyValues);

    }


    /**
     * Get market data manually with a other krakenWebClient instance
     * (in parallel of refreshDataTimer instance)
     */
    public void getCurrencyMarketData() {

        krakenWebClient2 = new KrakenWebClient(this);
        krakenWebClient2.launchPublicQuery();
        krakenWebClient2 = null;
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
        getCurrencyMarketData();

        // Set configChanged = false
        SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
        preferencesEditor.putBoolean("configChanged", Boolean.FALSE);
        preferencesEditor.commit();
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

                case "ALGO/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Algorand_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "ALGO/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Algorand_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "REP/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Augur_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "REP/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Augur_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "BAT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.BAT_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "BAT/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.BAT_Market_data_title), getString(R.string.Euro_symbol)));
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

                case "ATOM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "ATOM/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.Euro_symbol)));
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

                case "EOS/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Eos_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "EOS/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Eos_Market_data_title), getString(R.string.US_dollar_symbol)));
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

                case "GNO/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Gnosis_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "GNO/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Gnosis_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "LINK/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Chainlink_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "LINK/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Chainlink_Market_data_title), getString(R.string.Euro_symbol)));
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

                case "QTUM/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Quantum_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "QTUM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Quantum_Market_data_title), getString(R.string.US_dollar_symbol)));
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

                case "XLM/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Stellar_Lumens_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XLM/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Stellar_Lumens_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "XTZ/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.Euro_symbol)));
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

                case "USD/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.USDCoin_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "USD/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.USDCoin_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "WAVES/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Waves_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "WAVES/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Waves_Market_data_title), getString(R.string.Euro_symbol)));
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
                preferencesEditor.commit();

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