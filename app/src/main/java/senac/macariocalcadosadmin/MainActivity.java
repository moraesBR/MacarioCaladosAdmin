package senac.macariocalcadosadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import senac.macariocalcadosadmin.firebase.Conexao;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        if (user == null) {
            finish();
        } else {
            Toast.makeText(MainActivity.this, user.getEmail() + "\n" + user.getUid(), Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
