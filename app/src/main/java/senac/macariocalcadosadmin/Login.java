package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import senac.macariocalcadosadmin.firebase.Conexao;

public class Login extends AppCompatActivity {

    private RelativeLayout rellay1;
    private EditText etLogin, etPassword;
    private Button btnLogin, btnForgetPassword;

    private Handler handler;
    private Runnable runnable;

    protected static FirebaseAuth auth;

/* ---------------------------------- Ciclo de Vida do App -------------------------------------- */

    /*
     *  Métodos
     *      onStart(): Configura a comunicação com o banco de autenticação
     *      onCreate(): Inicializa o app (data binding, splash screen e controle)
     *      onSaveInstanceState():
     *      onRestoreInstanceState():
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        splashscreen();
        eventClick();
    }

    @Override
    /* Ao iniciar esta atividade, é realizado a conexão com o banco de autenticação de usuários. */
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

/* ---------------------------------- Métodos Data Binding -------------------------------------- */

    /*
    *   Métodos:
    *       bindView(): Realiza a ligação dos elementos do layout com variáveis programáveis.
    *       splashScreen(): Apresenta a tela de apresentação e faz a transição para a tela
    *           inicial do app.
    */

    private void bindView(){
        etLogin           = findViewById(R.id.et_login);
        etPassword        = findViewById(R.id.et_password);
        btnLogin          = findViewById(R.id.btn_login);
        btnForgetPassword = findViewById(R.id.btn_forgotpassword);
        rellay1 = findViewById(R.id.rellay1);
    }

    /*
     *  splashscreen(): Método responsável por apresentar a splash screen e alternar para a tela de
     *      login. Inicialmente a tela apresentado por /res/layout/activity_login.xml consiste num
     *      logo centralizado, escondendo, assim, a tela de login. O Runnable, quando acionado, é
     *      responsável por tornar visível a parte de login de usuário. Isto é feito, quando o
     *      Runnable é associado ao handler, que pede àquele que espere por determinado tempo
     *      (no caso, 2000 ms) para realizar a ação.
     */
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

/* ---------------------------------- Métodos de Controles -------------------------------------- */

    /*
     *   Métodos:
     *       eventClick(): Lida com os eventos disparados por cliques de botões e afins
     *       login(): Autenticação de usuário.
     */

    /*
     *   eventClick(): Método que lida com os eventos disparados por clicks de botões e afins.
     *      btnLogin trata do login do usuário, enquanto o btnForgetPassword encaminha o processo
     *      de execução à atividade responsável pelo esquecimento de senha ou primeiro acesso.
     */
    private void eventClick(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login    = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                login(login,password);
                cleanFields();
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

    /*
     *  login(String, String): Tenta-se autenticar o login e senha passado para o método
     *      signInWithEmailAndPassword(), esperando a resposta conseguida pelo addOnCompleteListener,
     *      que este obtém do Listener OnCompleteListener passando o contexto de pacote (Login.this).
     *      O Listener OnCompleteListener contém a resposta esperada no objeto Task<AuthResult>.
     *      Neste caso, se obteve-se sucesso na autenticação (task.isSuccessful()), então permite-se
     *      acesso ao aplicativo, que é a atividade MainActivity; Senão, emite-se um alerta de erro.
     */
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

/* ---------------------------------- Métodos de auxiliares ------------------------------------- */
    /*
     *   Métodos:
     *       alert(): Apresenta uma String de alerta na tela.
     *       cleanFields(): limpa os campos editáveis existentes na tela.
     */

    private void alert(int msg){
        Toast.makeText(Login.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void cleanFields(){
        etPassword.setText("");
        etLogin.setText("");
    }

}

