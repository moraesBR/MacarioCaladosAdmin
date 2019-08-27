package senac.macariocalcadosadmin.view;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.fragments.SapatoSettingsFragment;

public class SettingsSapato extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_sapato);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setActionBar(toolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(R.string.menu_configuracoes);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_settings, new SapatoSettingsFragment())
                .commit();
    }
}
