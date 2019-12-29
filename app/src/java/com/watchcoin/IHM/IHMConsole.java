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

                    // Augur (REP)
                    case "REP":

                        // EUR and USD

                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Augur_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Augur_EUR_asset));
                        }

                        break;

                    // Basic Attention Token (BAT)
                    case "BAT":

                        // EUR and USD

                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.BAT_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.BAT_EUR_asset));
                        }

                        break;

                    // Bitcoin (XBT)
                    case "XBT" :

                        // CAD, EUR, CHF, GBP, JPY and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_USD_asset));
                        }

                        if (britishPoundsSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_GBP_asset));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_EUR_asset));
                        }

                        if (swissFrancSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_CHF_asset));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_JPY_asset));
                        }

                        break;

                    // Bitcoin Cash (BCH)
                    case "BCH" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_Cash_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Bitcoin_Cash_EUR_asset));
                        }

                        break;


                    // Cardano (ADA)
                    case "ADA" :

                        // CAD, EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Cardano_USD_asset));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Cardano_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Cardano_EUR_asset));
                        }

                        break;

                    // Cosmos (ATOM)
                    case "ATOM" :

                        // CAD, EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Cosmos_USD_asset));
                        }

                        if(canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Cosmos_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Cosmos_EUR_asset));
                        }

                        break;

                    // Dai (DAI)
                    case "DAI" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Dai_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Dai_EUR_asset));
                        }

                        break;

                    // DASH (DASH)
                    case "DASH" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Dash_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Dash_EUR_asset));
                        }

                        break;

                    // EOS (EOS)
                    case "EOS" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Eos_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Eos_EUR_asset));
                        }

                        break;

                    // Ether (ETH)
                    case "ETH" :

                        // CAD, EUR, CHF, GBP, JPY and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Ether_USD_asset));
                        }

                        if (britishPoundsSelected) {
                            CurrencyValues.add(getString(R.string.Ether_GBP_asset));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Ether_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Ether_EUR_asset));
                        }

                        if (swissFrancSelected) {
                            CurrencyValues.add(getString(R.string.Ether_CHF_asset));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(getString(R.string.Ether_JPY_asset));
                        }

                        break;

                    // Ether Classic (ETC)
                    case "ETC" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Etherclassic_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Etherclassic_EUR_asset));
                        }

                        break;

                    // Gnosis (GNO)
                    case "GNO" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Gnosis_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Gnosis_EUR_asset));
                        }

                        break;

                    // Chainlink (LINK)
                    case "LINK" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Chainlink_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Chainlink_EUR_asset));
                        }

                        break;

                    // Litecoin (LTC)
                    case "LTC" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Litecoin_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Litecoin_EUR_asset));
                        }

                        break;

                    // Lisk(LSK)
                    case "LSK" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Lisk_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Lisk_EUR_asset));
                        }

                        break;

                    // Monero (XMR)
                    case "XMR" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Monero_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Monero_EUR_asset));
                        }

                        break;

                    // Nano (NANO)
                    case "NANO" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Nano_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Nano_EUR_asset));
                        }

                        break;

                    // OmiseGO (OMG)
                    case "OMG" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.OmiseGO_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.OmiseGO_EUR_asset));
                        }

                        break;

                    // Pax Gold (PAXG)
                    case "PAXG" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Paxg_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Paxg_EUR_asset));
                        }

                        break;

                    // Quantum (QTUM)
                    case "QTUM" :

                        // CAD, EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Quantum_USD_asset));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Quantum_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Quantum_EUR_asset));
                        }

                        break;

                    // Ripple (XRP)
                    case "XRP" :

                        // CAD, EUR, JPY and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Ripple_USD_asset));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Ripple_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Ripple_EUR_asset));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(getString(R.string.Ripple_JPY_asset));
                        }

                        break;

                    // Siacoin (SC)
                    case "SC" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Siacoin_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Siacoin_EUR_asset));
                        }

                        break;

                    // Stellar Lumens (XLM)
                    case "XLM" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Stellatlumens_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Stellarlumens_EUR_asset));
                        }

                        break;

                    // Tether (USDT)
                    case "USDT" :

                        // USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Tether_USD_asset));
                        }

                        break;

                    // Tezos (XTZ)
                    case "XTZ" :

                        // CAD, EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Tezos_USD_asset));
                        }

                        if (canadianDollarSelected) {
                            CurrencyValues.add(getString(R.string.Tezos_CAD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Tezos_EUR_asset));
                        }

                        break;

                    // Waves (WAVES)
                    case "WAVES" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Waves_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Waves_EUR_asset));
                        }

                        break;

                    // Zcash (ZEC)
                    case "ZEC" :

                        // EUR and USD
                        if (americanDollarSelected) {
                            CurrencyValues.add(getString(R.string.Zcash_USD_asset));
                        }

                        if (eurosSelected) {
                            CurrencyValues.add(getString(R.string.Zcash_EUR_asset));
                        }

                        if (japaneseYenSelected) {
                            CurrencyValues.add(getString(R.string.Zcash_JPY_asset));
                        }

                        break;
                }
            }

        }
        // Default cryptocurrency configuration application
        else {

            CurrencyValues.add(getString(R.string.Bitcoin_USD_asset));
            CurrencyValues.add(getString(R.string.Ether_USD_asset));

            // US dollar activated by default
            SharedPreferences.Editor preferencesEditor = sharedPreferencesApp.edit();
            preferencesEditor.putBoolean("us_dollar_checkbox", Boolean.TRUE);
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

                case "BCH/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Bitcoin_Cash_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "ADA/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cardano_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
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

                case "ATOM/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Cosmos_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
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

                case "LSK/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Lisk_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "LSK/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Lisk_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "USDT/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tether_Market_data_title), getString(R.string.US_dollar_symbol)));
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

                case "PAXG/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Paxg_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "PAXG/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Paxg_Market_data_title), getString(R.string.US_dollar_symbol)));
                    break;

                case "QTUM/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Quantum_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
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

                case "XTZ/CAD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.Canadian_dollar_symbol)));
                    break;

                case "XTZ/EUR":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.Euro_symbol)));
                    break;

                case "XTZ/USD":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Tezos_Market_data_title), getString(R.string.US_dollar_symbol)));
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

                case "ZEC/JPY":
                    currencyDataMap.put("marketDataCurrency", String.format(getString(R.string.Zcash_Market_data_title), getString(R.string.Yen_symbol)));
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