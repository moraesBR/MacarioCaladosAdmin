package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import senac.macariocalcadosadmin.firebase.Conexao;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        setView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
    }

    private void bindView() {
        toolbar = findViewById(R.id.toolbar_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
    }

    private void setView() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_main);
        getSupportActionBar().setTitle("");
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu_visualizar:{
                Toast.makeText(MainActivity.this,
                        getResources().getText(R.string.menu_visualizar),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
            }
            case R.id.menu_buscar:{
                Toast.makeText(MainActivity.this,
                        getResources().getText(R.string.menu_buscar),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
            }
            case R.id.menu_inserir:{
                Toast.makeText(MainActivity.this,
                        getResources().getText(R.string.menu_inserir),
                        Toast.LENGTH_SHORT)
                        .show();
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sair:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Conexao.logOut();
        finish();
    }
}
