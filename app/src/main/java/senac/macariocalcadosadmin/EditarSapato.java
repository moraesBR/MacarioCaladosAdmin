package senac.macariocalcadosadmin;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class EditarSapato extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private final String POSICAO_ARRAY = "posição sapato arraylist";
    private int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sapato);

        dataBinding();
        setView();
        setListener();

    }

    private void extraData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            posicao = bundle.getInt(POSICAO_ARRAY);
        }
    }

    private void dataBinding(){
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
    }

    private void setView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
