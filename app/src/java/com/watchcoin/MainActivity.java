package com.watchcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.watchcoin.IHM.IHMConsole;

import java.util.Locale;

/**
 * MainActivity shows splashscreen and check the app configuration stored in the sharedPreference
 */
public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private final static int splashScreenTime = 3000;

    public static SharedPreferences preferencesApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Put the application default preferences
        PreferenceManager.setDefaultValues(this, R.xml.coin_exchange_platforms_details, false);

        // readAgain = true for apply the default configuration cryptocurrencies
        // (passing true as ignored otherwise because of above call)
        PreferenceManager.setDefaultValues(this, R.xml.currencies_details, true);

        // Preferences application
        preferencesApp = PreferenceManager.getDefaultSharedPreferences(this);

        Editor preferencesEditor = preferencesApp.edit();

        // Set configChanged flag = false (first execution)
        preferencesEditor.putBoolean("configChanged", Boolean.FALSE);

        // Set configLanguageChanged flag = false (first execution)
        preferencesEditor.putBoolean("configLanguageChanged", Boolean.FALSE);

        // Check if the language configuration exist
        String languageApp = preferencesApp.getString("language_configuration", "NoLanguage");

        if (!languageApp.equals("NoLanguage")) {

            switch (languageApp) {

                case "English":
                    setApplicationLanguage("en");
                    break;

                case "Español":
                    setApplicationLanguage("es");
                    break;

                case "Français":
                    setApplicationLanguage("fr");
                    break;

                case "Português":
                    setApplicationLanguage("pt");
                    break;
            }
        }

        preferencesEditor.apply();

        new Handler().postDelayed(() -> {

            Intent i = new Intent(MainActivity.this, IHMConsole.class);
            startActivity(i);

            finish();
        }, splashScreenTime);
    }


    /**
     * Define the language of the application when the first execution
     * @param languageCode : Language code used
     */
    private void setApplicationLanguage(String languageCode) {

        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        conf.setLocale(new Locale(languageCode));
        res.updateConfiguration(conf, dm);
    }

}
