package senac.macariocalcadosadmin.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import senac.macariocalcadosadmin.R;

public class SapatoSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
