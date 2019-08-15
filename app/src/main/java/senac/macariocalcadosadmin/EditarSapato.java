package senac.macariocalcadosadmin;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import senac.macariocalcadosadmin.adapters.SelecaoFotoAdapter;
import senac.macariocalcadosadmin.models.Foto;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoFoto;

import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class EditarSapato extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private AppCompatTextView nome, modelo, genero, idade, tipo, valor, quantidade;


    private List<SelecaoFoto> lista;
    private Sapato sapato;
    private int posicao;
    private final String FOTO_ARRAYLIST = "foto arraylist";

    private TabLayout tabLayout;

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
        if (bundle != null) {
            String POSICAO_ARRAY = "posição sapato arraylist";
            posicao = bundle.getInt(POSICAO_ARRAY);
            sapato = listaSapatos.get(posicao).getSapato();
        }
        if (savedInstanceState != null) {
            lista = savedInstanceState.getParcelableArrayList(FOTO_ARRAYLIST);
        }
    }

    private void dataBinding() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.slider_image);
        tabLayout = findViewById(R.id.tabDots);
        nome = findViewById(R.id.sapato_nome);
        modelo = findViewById(R.id.sapato_modelo);
        genero = findViewById(R.id.sapato_genero);
        idade = findViewById(R.id.sapato_idade);
        tipo = findViewById(R.id.sapato_tipo);
        valor = findViewById(R.id.sapato_valor);
        quantidade = findViewById(R.id.sapato_quantidade);
    }

    private void setView() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        dadosSapato();
    }

    private void dadosSapato(){
        nome.setText(sapato.getNome());
        modelo.setText(sapato.getModelo());
        genero.setText(sapato.getGenero().toString());
        idade.setText(sapato.getIdade().toString());
        tipo.setText(sapato.getTipo().toString());
        if (sapato.isPromocao())
            valor.setText(String.format("R$ %.2f por R$ %.2f",
                    sapato.getAntigoValor(), sapato.getValor()));
        else
            valor.setText(String.format("R$ %.2f", sapato.getValor()));
        quantidade.setText(String.valueOf(sapato.getQuantidade()));
    }

    private void setAdapters() {
        lista = new ArrayList<>();
        for (Foto f : listaSapatos.get(posicao).getSapato().getFotos()) {
            lista.add(new SelecaoFoto(f));
        }
        SelecaoFotoAdapter adapter = new SelecaoFotoAdapter(lista, this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private void setListener() {
    }
}
