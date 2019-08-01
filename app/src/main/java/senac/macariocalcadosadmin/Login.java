package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import senac.macariocalcadosadmin.firebase.Conexao;

public class Login extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin, btnForgetPassword;
    private TextInputLayout tilPassword;
    private TextInputLayout tilLogin;
    protected static FirebaseAuth auth;
    private RelativeLayout rellay1;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        splashscreen();
        eventClick();
    }

    private void bindView(){
        etLogin           = findViewById(R.id.et_login);
        etPassword        = findViewById(R.id.et_password);
        btnLogin          = findViewById(R.id.btn_login);
        btnForgetPassword = findViewById(R.id.btn_forgotpassword);
        tilPassword       = findViewById(R.id.til_password);
        tilLogin          = findViewById(R.id.til_login);
        rellay1 = findViewById(R.id.rellay1);
    }

    private void splashscreen(){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                rellay1.setVisibility(View.VISIBLE);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    private void eventClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login    = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(!login.isEmpty() && !password.isEmpty()) {
                    login(login, password);
                }
                else{
                    if(login.isEmpty()) {
                        etLogin.setError("Digite seu email");
                        /*tilLogin.setError("Digite seu email");
                        tilLogin.setErrorTextColor(
                                ColorStateList.valueOf(
                                        getResources().getColor(R.color.colorError)
                                )
                        );
                        */
                    }
                    if(password.isEmpty()) {
                        etPassword.setError("Digite sua senha");
                        /*tilPassword.setError("Digite seu email");
                        tilPassword.setErrorTextColor(
                                ColorStateList.valueOf(
                                        getResources().getColor(R.color.colorError)
                                )
                        );
                        */

                    }
                }
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetPassword = new Intent(Login.this,ResetSenha.class);
                startActivity(resetPassword);
            }
        });
    }

    private void login(String login, String password){
        auth.signInWithEmailAndPassword(login,password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainActivity = new Intent(Login.this,MainActivity.class);
                            startActivity(mainActivity);
                        }
                        else{
                            alert(R.string.login_erro);
                        }
                    }
                });
    }

    private void alert(int msg){
        Toast.makeText(Login.this,msg,Toast.LENGTH_SHORT).show();
    }

}

