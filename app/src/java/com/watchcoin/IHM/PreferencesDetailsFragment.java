package com.watchcoin.IHM;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.fragment.app.DialogFragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.watchcoin.MainActivity;
import com.watchcoin.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * PreferencesDetailsFragment shows the different settings menu depending of the choices made by the user
 */
public class PreferencesDetailsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    private static final String TAG = "PreferencesDetailsFrag";

    private static final String FRAGMENT_TAG_DIALOG = "android.support.v7.preference.PreferenceFragment.DIALOG";

    private IHMConsole ihmConsoleActivity;

    private final SharedPreferences preferencesApp = MainActivity.preferencesApp;

    private final List<String> MultiSelectLists = Arrays.asList("kraken_assets_list", "defi_kraken_assets_list");


    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        if (preference instanceof MultiSelectListPreference) {

            DialogFragment dialogFragment = MultiSelectListPreferenceDialog.newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this,0);
            dialogFragment.show(this.getParentFragmentManager(), FRAGMENT_TAG_DIALOG);

        }
        else {

            super.onDisplayPreferenceDialog(preference);
        }
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Settings type to display
        final String preferencesDetailsType = getArguments().getString("settings");

        switch(preferencesDetailsType) {

            case "coin_exchange_configuration":

                // Update toolbar title
                ihmConsoleActivity.getToolbar().setTitle(R.string.Coin_exchange_toolbar_title);

                addPreferencesFromResource(R.xml.coin_exchange_platforms_details);

                EditTextPreference krakenUrlAPIValue = findPreference("kraken_url_api");
                krakenUrlAPIValue.setText(MainActivity.preferencesApp.getString("kraken_url_api", getString(R.string.Kraken_api_default_value)));
                krakenUrlAPIValue.setSummary(MainActivity.preferencesApp.getString("kraken_url_api", getString(R.string.Kraken_api_default_value)));

                break;

            case "crypto_currency_configuration":

                // Update toolbar title
                ihmConsoleActivity.getToolbar().setTitle(R.string.Crypto_currency_toolbar_title);

                addPreferencesFromResource(R.xml.currencies_details);

                // Update number of assets selected (summary)
                for (String list : MultiSelectLists) {

                    setNumberAssetsSelected(list);
                }

                break;

            case "language_configuration":

                // Update toolbar title
                ihmConsoleActivity.getToolbar().setTitle(R.string.Language_toolbar_title);

                addPreferencesFromResource(R.xml.language_details);

                break;
        }
    }


    @Override
    public void onPause() {
        super.onPause();

        // Unregister preference listener
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register preference listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Recuperation of IHMConsole activity
        if (context instanceof IHMConsole) {

            ihmConsoleActivity = (IHMConsole) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();

        ihmConsoleActivity = null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        SharedPreferences.Editor preferencesEditor = preferencesApp.edit();

        if (findPreference(key) instanceof EditTextPreference) {

            // If the change concern the Kraken API address
            if (key.equals("kraken_url_api")) {

                findPreference(key).setSummary(((EditTextPreference) findPreference(key)).getText());

                // Set configChanged = true
                preferencesEditor.putBoolean("configChanged", Boolean.TRUE);
                preferencesEditor.apply();
            }
        }

        if (findPreference(key) instanceof CheckBoxPreference) {

            // If the change concern the fiat currencies configuration
            if (key.equals("australian_dollar_checkbox") || key.equals("british_pounds_checkbox") || key.equals("canadian_dollar_checkbox") || key.equals("euros_checkbox")
                    || key.equals("japanese_yen_checkbox") || key.equals("us_dollar_checkbox") || key.equals("swiss_franc_checkbox")) {

                // Set configChanged = true
                preferencesEditor.putBoolean("configChanged", Boolean.TRUE);
                preferencesEditor.commit();

                // Update the values for the ecurrency spinner (toolbar)
                ihmConsoleActivity.setCurrencySpinnerValues();
            }
        }

        if (findPreference(key) instanceof MultiSelectListPreference) {

            // If the change concern the kraken assets
            //if (key.equals("kraken_assets_list")) {

                // Update number of assets selected (summary)
                setNumberAssetsSelected(key);

                // Set configChanged = true
                preferencesEditor.putBoolean("configChanged", Boolean.TRUE);
                preferencesEditor.commit();
            //}
        }

        if (findPreference(key) instanceof ListPreference) {

            // If the change concern the language configuration
            if (key.equals("language_configuration")) {

                // Set configLanguageChanged = true
                preferencesEditor.putBoolean("configLanguageChanged", Boolean.TRUE);
                preferencesEditor.commit();

                String languageSelected = sharedPreferences.getString("language_configuration", "English");

                switch (languageSelected) {

                    case "English" :
                        updateView("en");
                        break;

                    case "Español" :
                        updateView("es");
                        break;

                    case "Français" :
                        updateView("fr");
                        break;

                    case "Português" :
                        updateView("pt");
                        break;
                }
            }
        }
    }


    /**
     * Update user interface according to the language selected
     * @param languageCode : Language selected
     */
    private void updateView(String languageCode) {

        Resources res = this.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        conf.setLocale(new Locale(languageCode));
        res.updateConfiguration(conf, dm);

        setPreferencesFromResource(R.xml.language_details, this.getTag());

        ihmConsoleActivity.updateViewLanguage();
    }


    // Set number of assets selected for each platform
    private void setNumberAssetsSelected(String assetsList) {

        MultiSelectListPreference krakenAssetMultiSelectListPreference = findPreference(assetsList);

        // Default value if asset list don't exist in shared preferences
        Set<String> defaultValue = new HashSet<>();

        krakenAssetMultiSelectListPreference.setSummary(String.format(ihmConsoleActivity.getString(R.string.Number_kraken_assets_selected),
                MainActivity.preferencesApp.getStringSet(assetsList, defaultValue).size()));
        
        }
}
