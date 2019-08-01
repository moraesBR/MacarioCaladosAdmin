package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import senac.macariocalcadosadmin.firebase.Conexao;

public class ResetSenha extends AppCompatActivity {

    private Button btnResetPassword;
    private EditText etEmail;
    private Toolbar toolbar;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        bindView();
        eventClick();
    }

    private void bindView(){
        etEmail = findViewById(R.id.et_reset_senha);
        btnResetPassword = findViewById(R.id.btn_reset_senha);
        toolbar = findViewById(R.id.toolbar_reset_senha);
        setActionBar(toolbar);
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setTitle("");
        }catch (NullPointerException e){}
    }

    private void eventClick() {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                if(!email.isEmpty()) {
                    resetPassword(email);
                }
                else{
                    etEmail.setError(getResources().getString(R.string.login_email_vazio));
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetSenha.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            alert(R.string.login_reset_sucesso);
                            finish();
                        }
                        else{
                            alert(R.string.login_reset_erro);
                        }
                    }
                });
    }

    private void alert(int msg){
        Toast.makeText(ResetSenha.this,msg,Toast.LENGTH_SHORT).show();
    }

}
