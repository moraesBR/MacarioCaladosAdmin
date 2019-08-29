package senac.macariocalcadosadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import senac.macariocalcadosadmin.firebase.Conexao;

public class ResetSenha extends AppCompatActivity {

    private Button btnResetPassword;
    private EditText etEmail;
    private Toolbar toolbar;
    private FirebaseAuth auth;

/* ---------------------------------- Ciclo de Vida do App -------------------------------------- */
    /*
     *  Métodos
     *      onStart(): Restaura a comunicação com o banco de autenticação
     *      onCreate(): Inicializa o app (data binding e controle)
     *      onSaveInstanceState():
     *      onRestoreInstanceState():
     */


    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindView();
        eventClick();
    }

/* ---------------------------------- Métodos Data Binding -------------------------------------- */
    /*
     *   Métodos:
     *       bindView(): Realiza a ligação dos elementos do layout com variáveis programáveis.
     *       splashScreen(): Apresenta a tela de apresentação e faz a transição para a tela
     *           inicial do app.
     */

    private void bindView(){
        etEmail = findViewById(R.id.et_reset_senha);
        btnResetPassword = findViewById(R.id.btn_reset_senha);
        toolbar = findViewById(R.id.toolbar_reset_senha);
        setActionBar(toolbar);
        try {
            Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setTitle("");
        }catch (NullPointerException ignored){}
    }

/* ---------------------------------- Métodos de Controles -------------------------------------- */
    /*
     *  Métodos
     *      eventClick(): Controla as ações de cliques dos botões e afins
     *      resetPassword(): Envia um email para o usuário com a solicitação de reconfiguração de
     *          email.
     */

    private void eventClick() {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                if(!email.isEmpty()) {
                    resetPassword(email);
                }
                else{
                    etEmail.setError(getResources().getString(R.string.email_alerta));
                }
                cleanFields();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
     *  resetPassword(): tenta-se enviar um email de reconfiguração de senha para o endereço contido
     *      no objeto String, passado como parâmetro do método sendPasswordResetEmail(). Aguarda-se
     *      a resposta, que será obtida pelo método addOnCompleteListener, sendo que este a obtém
     *      via Listener OnCompleteListener e o contexto de pacote (ResetSenha.this). Este Listener
     *      contém a resposta esperada no objeto Task<AuthResult>. Neste caso, se obteve-se sucesso
     *      no envio do email (task.isSuccessful()), então emite-se um alerta positivo; caso
     *      contrário, emite-se um alerta negativo.
     */
    private void resetPassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetSenha.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            alert(R.string.email_sucesso);
                            finish();
                        }
                        else{
                            alert(R.string.email_erro);
                        }
                    }
                });
    }

/* ---------------------------------- Métodos auxiliares ------------------------------------- */
    /*
     *   Métodos:
     *       alert(): Apresenta uma String de alerta na tela.
     *       cleanFields(): limpa os campos editáveis existentes na tela.
     */

    private void alert(int msg){
        Toast.makeText(ResetSenha.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void cleanFields(){
        etEmail.setText("");
    }

}
