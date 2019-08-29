package senac.macariocalcadosadmin.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.fragments.ConfiguracoesFragment;

public class SettingsSapato extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_sapato);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindView();
    }

    private void bindView(){
        toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_configuracoes);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_settings, new ConfiguracoesFragment())
                .commit();
    }
}
