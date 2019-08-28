package senac.macariocalcadosadmin.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import senac.macariocalcadosadmin.R;

import static android.content.Context.MODE_PRIVATE;
import static senac.macariocalcadosadmin.Login.login;
import static senac.macariocalcadosadmin.Login.password;

public class SapatoSettingsFragment extends PreferenceFragmentCompat {

    private Preference.OnPreferenceChangeListener onPreferenceChangeListener;
    private Preference autenticacao;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        bindView();
        listener();

    }

    private void bindView(){
        autenticacao = findPreference(getString(R.string.credenciais));
    }

    private void listener(){

        onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String sValor = newValue.toString();

                if(preference instanceof SwitchPreference){
                    if(preference.getKey().equalsIgnoreCase(getResources().getString(R.string.credenciais))
                            && !((SwitchPreference) preference).isChecked()){

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                                getResources().getString(R.string.pref_data),MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(getResources().getString(R.string.pref_login),login);
                        editor.putString(getResources().getString(R.string.pref_password),password);
                        editor.commit();

                    }
                    else{

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                                getResources().getString(R.string.pref_data),MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString(getResources().getString(R.string.pref_login),"");
                        editor.putString(getResources().getString(R.string.pref_password),"");
                        editor.commit();

                    }
                }

                return true;
            }
        };

        autenticacao.setOnPreferenceChangeListener(onPreferenceChangeListener);
    }

}
