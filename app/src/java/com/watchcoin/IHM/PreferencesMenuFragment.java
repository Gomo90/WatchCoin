package com.watchcoin.IHM;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.watchcoin.R;

/**
 * PreferencesMenuFragment shows the different headings of the settings menu that the user can select
 */
public class PreferencesMenuFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {


    private static final String TAG = "PreferencesMenuFragment";

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        addPreferencesFromResource(R.xml.preferences_menu);

        findPreference("coin_exchange_platform").setOnPreferenceClickListener(this);
        findPreference("crypto_currency").setOnPreferenceClickListener(this);
        findPreference("language").setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        PreferencesDetailsFragment preferencesDetailsFragment = new PreferencesDetailsFragment();

        // Send settings type to display in the PreferencesDetailsFragment
        preferencesDetailsFragment.setArguments(preference.getExtras());

        displayFragment(preferencesDetailsFragment);

        return false;
    }


    /**
     * Display the fragment in the main container
     * @param fragment : The fragment to display
     */
    private void displayFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.ContainerMain, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(fragment.getTag());
        fragmentTransaction.commit();
    }


    /**
     * Update views of fragment
     * Call when the language configuration is changed
     */
    public void updateView() {

        setPreferencesFromResource(R.xml.preferences_menu, this.getTag());

        findPreference("coin_exchange_platform").setOnPreferenceClickListener(this);
        findPreference("crypto_currency").setOnPreferenceClickListener(this);
        findPreference("language").setOnPreferenceClickListener(this);
    }
}
