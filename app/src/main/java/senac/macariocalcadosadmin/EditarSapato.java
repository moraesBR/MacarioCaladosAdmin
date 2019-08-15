package senac.macariocalcadosadmin;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import senac.macariocalcadosadmin.adapters.SelecaoFotoAdapter;
import senac.macariocalcadosadmin.models.Foto;
import senac.macariocalcadosadmin.models.SelecaoFoto;

import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class EditarSapato extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ViewPager viewPager;

    private List<SelecaoFoto> lista;
    private int posicao;
    private final String FOTO_ARRAYLIST = "foto arraylist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sapato);

        restoreData(savedInstanceState);
        dataBinding();
        setView();
        setListener();
        setAdapters();

    }

    private void restoreData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String POSICAO_ARRAY = "posição sapato arraylist";
            posicao = bundle.getInt(POSICAO_ARRAY);
        }
        if(savedInstanceState != null){
            lista = savedInstanceState.getParcelableArrayList(FOTO_ARRAYLIST);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(FOTO_ARRAYLIST, (ArrayList<? extends Parcelable>) lista);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lista = savedInstanceState.getParcelableArrayList(FOTO_ARRAYLIST);
    }

    private void dataBinding(){
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        viewPager = findViewById(R.id.viewPager);
    }

    private void setView(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void setAdapters(){
        lista = new ArrayList<>();
        for(Foto f : listaSapatos.get(posicao).getSapato().getFotos()){
            lista.add(new SelecaoFoto(f));
        }
        SelecaoFotoAdapter adapter = new SelecaoFotoAdapter(lista,this);
        viewPager.setAdapter(adapter);
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
