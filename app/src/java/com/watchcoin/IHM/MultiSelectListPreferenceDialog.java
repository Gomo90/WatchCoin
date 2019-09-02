package com.watchcoin.IHM;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import androidx.preference.MultiSelectListPreference;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.DialogPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceDialogFragmentCompat;

import java.util.HashSet;
import java.util.Set;

/**
 * This class build a custom dialog with a list of entries multi-selectable
 */
public class MultiSelectListPreferenceDialog extends PreferenceDialogFragmentCompat
        implements DialogPreference.TargetFragment, OnMultiChoiceClickListener {


    private static final String TAG = "MultiSelectListPreferenceDialog";

    private final Set<String> newPreferencesChecked = new HashSet<>();
    private boolean preferencesChanged;


    public MultiSelectListPreferenceDialog(){}


    public static MultiSelectListPreferenceDialog newInstance(String key) {

        MultiSelectListPreferenceDialog multiSelectListPreferenceDialog = new MultiSelectListPreferenceDialog();
        Bundle b = new Bundle(1);
        b.putString("key", key);
        multiSelectListPreferenceDialog.setArguments(b);

        return multiSelectListPreferenceDialog;
    }


    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);

        final MultiSelectListPreference multiSelectListPreference = getListPreference();

        // If an entries array and an entryValues array are attached
        if (multiSelectListPreference.getEntries() != null && multiSelectListPreference.getEntryValues() != null) {

            boolean[] preferencesChecked = getCheckedPreferences();

            builder.setMultiChoiceItems(multiSelectListPreference.getEntries(), preferencesChecked, this);

            newPreferencesChecked.clear();
            newPreferencesChecked.addAll(multiSelectListPreference.getValues());
        }

    }

    // Return the checked preferences
    private boolean[] getCheckedPreferences() {

        MultiSelectListPreference multiSelectListPreference = getListPreference();

        CharSequence[] entries = multiSelectListPreference.getEntryValues();

        Set<String> values = multiSelectListPreference.getValues();

        boolean[] preferencesChecked = new boolean[entries.length];

        for (int i = 0; i < entries.length; i++) {

            preferencesChecked[i] = values.contains(entries[i].toString());
        }

        return preferencesChecked;
    }

    @Override
    // Called when the dialog is dismissed and should be used to save data to the sharedpreferences
    public void onDialogClosed(boolean positiveButton) {

        MultiSelectListPreference multiSelectListPreference = getListPreference();

        // If new preferences are entered
        if (positiveButton && preferencesChanged) {

            // Store new preferences
            Set<String> values = newPreferencesChecked;

            if (multiSelectListPreference.callChangeListener(values)) {

                multiSelectListPreference.setValues(values);
            }
        }

        preferencesChanged = false;
    }


    private MultiSelectListPreference getListPreference() {

        return (MultiSelectListPreference)this.getPreference();
    }


    @Override
    public Preference findPreference(CharSequence charSequence) {

        return getPreference();
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        MultiSelectListPreference multiSelectListPreference = getListPreference();
        preferencesChanged = true;

        // New preferences selected of not
        if (isChecked) {

            newPreferencesChecked.add(multiSelectListPreference.getEntryValues()[which].toString());
        }
        else {

            newPreferencesChecked.remove(multiSelectListPreference.getEntryValues()[which].toString());
        }
    }
}
