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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import senac.macariocalcadosadmin.firebase.Conexao;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toogle;

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
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
    }

    private void setView() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_main);
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setIcon(R.drawable.ic_macario_logo);
        toogle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_adulto:
                Toast.makeText(this, "Menu adulto", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_infantil:
                Toast.makeText(this, "Menu infantil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_esportivo:
                Toast.makeText(this, "Menu esportivo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_casual:
                Toast.makeText(this, "Menu casual", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_social:
                Toast.makeText(this, "Menu social", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_feminino:
                Toast.makeText(this, "Menu feminino", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_masculino:
                Toast.makeText(this, "Menu masculino", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
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
