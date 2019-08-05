package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import senac.macariocalcadosadmin.firebase.Conexao;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Toolbar toolbar;

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

    private void bindView(){
        toolbar = findViewById(R.id.toolbar_main);
    }

    private void setView(){
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar_main);
        getSupportActionBar().setTitle("");
        //getSupportActionBar().setIcon(R.drawable.ic_macario_logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                logout(); break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Conexao.logOut();
        finish();
    }
}
