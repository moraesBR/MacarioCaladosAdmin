package senac.macariocalcadosadmin.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Conexao {

    /*
        - FirebaseAuth: É o ponto de entrada do SDK de autenticação do Firebase.
            Primeiro, obtem-se uma instância da classe chamando getInstance().
            Em seguida, realiza-se o sign in ou sign up do usuário via:
                createUserWithEmailAndPassword(String, String);
                signInWithEmailAndPassword(String, String);
                signInWithCredential(AuthCredential);
                signInAnonymously();
                signInWithCustomToken(String).
            Por fim, chama-se getCurrentUser() para acessar o obejo FirebaseUser, cujo contém
            informações sobre o usuário que realizou o sign-in.

        - FirebaseAuth.AuthStateListener: É um Listener de eventos que é chamando quando há uma
            mudança no estado de autenticação. Para registrar ou desregistrar um Listener, usa-se,
            respectivamente, addAuthStateListener(AuthStateListener) e
            removeAuthStateListener(AuthStateListener).

        - onAuthStateChanged (FirebaseAuth auth): É um método abstrato chamado quando há mudança no
            estado de autenticação via alguma interação com o usuário, por exemplo: logo após um
            Listener ter sido registrado; quando um usuário realiza um sign in; quando o usuário
            corrente realiza um sign out; ou quando o usuário corrente muda.

        - FirebaseUser: Representa o conjunto de informações de perfil de dado usuário registrado no
            banco de dado Firebase atrelado ao projeto. Também contém métodos de ajuda para alterar
            ou recuperar informações do perfil, bem como para gerenciar o estado de autenticação do
            usuário.
    */


    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;

    private Conexao(){}

    public static FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null){
            inicializarFirebaseAuth();
        }
        return firebaseAuth;
    }

    private static void inicializarFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    firebaseUser = user;
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    public static void logOut(){
        firebaseAuth.signOut();
    }
}
