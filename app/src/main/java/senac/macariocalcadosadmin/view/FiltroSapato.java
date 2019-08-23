package senac.macariocalcadosadmin.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.adapters.SelecaoSapatoAdapter;
import senac.macariocalcadosadmin.models.Campo;
import senac.macariocalcadosadmin.models.Genero;
import senac.macariocalcadosadmin.models.Idade;
import senac.macariocalcadosadmin.models.SelecaoSapato;
import senac.macariocalcadosadmin.models.Tipo;

import static senac.macariocalcadosadmin.MainActivity.database;
import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class FiltroSapato extends AppCompatActivity {



    private List<SelecaoSapato> lista;
    private SelecaoSapatoAdapter listaAdapter;
    private RecyclerView rvSapatos;
    private FloatingActionButton apagarSapato;
    private SearchView searchView;
    private ProgressBar progressBar;

    private int qtdFoto;
    private Campo campo;
    private String sValor;

    private final String CAMPO = "campo de filtragem";
    private final String CAMPO_INFO = "info campo de filtragem";
    private final String POSICAO_ARRAY = "posição sapato arraylist";
    private final String RESULTADO_FILTRAGEM = "resultado filtragem";


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(RESULTADO_FILTRAGEM, (ArrayList<? extends Parcelable>) lista);
        outState.putSerializable(CAMPO,campo);
        if(campo != Campo.PROMOCAO)
            outState.putString(CAMPO_INFO,sValor);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lista = savedInstanceState.getParcelableArrayList(RESULTADO_FILTRAGEM);
        campo = (Campo) savedInstanceState.getSerializable(CAMPO);
        if(campo != Campo.PROMOCAO)
            savedInstanceState.getString(CAMPO_INFO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_sapato);

        dataBinding();
        restoreData(savedInstanceState);
        setAdapters();
        setListener();
        filtro();
    }

    private void restoreData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            campo = (Campo)bundle.getSerializable(CAMPO);
            if(campo != Campo.PROMOCAO)
                sValor = bundle.getSerializable(CAMPO_INFO).toString();
        }
        if (savedInstanceState != null) {
            lista = savedInstanceState.getParcelableArrayList(RESULTADO_FILTRAGEM);
            campo = (Campo) savedInstanceState.getSerializable(CAMPO);
            if(campo != Campo.PROMOCAO)
                sValor = savedInstanceState.getString(CAMPO_INFO);
        }
    }

    private void dataBinding() {
        rvSapatos = findViewById(R.id.rv_sapatos);
        apagarSapato = findViewById(R.id.fab_apaga_sapato);
        searchView = findViewById(R.id.visualizar_busca);
        progressBar = findViewById(R.id.progressbar);
    }

    private void setAdapters() {
        lista = new ArrayList<>();
        listaAdapter = new SelecaoSapatoAdapter(lista,getBaseContext());
        rvSapatos.setAdapter(listaAdapter);
        RecyclerView.LayoutManager lmSapatos = new GridLayoutManager(getBaseContext(), 2,
                RecyclerView.VERTICAL,false);
        rvSapatos.setLayoutManager(lmSapatos);
        rvSapatos.setHasFixedSize(true);
        listaAdapter.notifyDataSetChanged();
    }

    private void filtro(){
        switch (campo){
            case GENERO:{
                Genero genero = Genero.valueOf(sValor);
                database.read(listaAdapter,
                        database.filtro(campo, genero.toString()),
                        progressBar);
                break;
            }
            case IDADE:{
                Idade idade = Idade.valueOf(sValor);
                database.read(listaAdapter,
                        database.filtro(campo,idade.toString()),
                        progressBar);
                break;
            }
            case TIPO:{
                Tipo tipo = Tipo.valueOf(sValor.toUpperCase());
                database.read(listaAdapter,
                        database.filtro(campo,tipo.toString()),
                        progressBar);
                break;
            }
            case PROMOCAO:{
                database.read(listaAdapter,
                        database.filtro(true),
                        progressBar);
                break;
            }
        }
    }

    private void setListener() {
        /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
        /* Captura a posição correspondente no adapter */
        /*
         * Se não estiver sido selecionado, então adicione no contador de imagens
         * selecionadas; senão, retire do contador.
         */
        /* Altera o estado de marcação (boolean) */
        /* Informa ao adapter que houve ateração nos dados */
        /* Clicks Listener */
        View.OnLongClickListener marcarCard = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                /* Captura a posição correspondente no adapter */
                int position = viewHolder.getAdapterPosition();

                /*
                 * Se não estiver sido selecionado, então adicione no contador de imagens
                 * selecionadas; senão, retire do contador.
                 */
                if (!listaAdapter.getSelecaoSapatoList().get(position).isSelecionado()) {
                    qtdFoto++;
                } else
                    qtdFoto--;

                /* Altera o estado de marcação (boolean) */
                listaAdapter.getSelecaoSapatoList().get(position).setSelecionado();
                /* Informa ao adapter que houve ateração nos dados */
                listaAdapter.notifyDataSetChanged();

                setSapatoView();

                return qtdFoto > 0;
            }
        };
        listaAdapter.setMarcarSapato(marcarCard);

        /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
        /* Captura a posição correspondente no adapter */
        View.OnClickListener verSapato = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                /* Captura a posição correspondente no adapter */
                int position = viewHolder.getAdapterPosition();
                Intent editarSapato = new Intent(getBaseContext(), EditarSapato.class);

                editarSapato.putExtra(POSICAO_ARRAY, listaAdapter.getSelecaoSapatoList().get(position).getSapato());
                startActivity(editarSapato);
            }
        };
        listaAdapter.setVerSapato(verSapato);

        apagarSapato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.AlertDialogStyle);
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("REMOVENDO...")
                        .setMessage("Você tem certeza que deseja excluir estes sapatos?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Apaga as fotos se houver pelo menos uma foto selecionada */
                                if (qtdFoto > 0) {
                                    for (int i = 0; i < listaAdapter.getSelecaoSapatoList().size(); i++)
                                        if (listaAdapter.getSelecaoSapatoList().get(i).isSelecionado()) {
                                            database.delete(listaAdapter.getSelecaoSapatoList().get(i).getSapato());
                                            listaAdapter.getSelecaoSapatoList().remove(i--);
                                        }
                                }

                                qtdFoto = 0;
                                listaAdapter.notifyDataSetChanged();
                                setSapatoView();
                            }

                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                listaAdapter.getFilter().filter(s);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listaAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    private void setSapatoView() {
        if (qtdFoto > 0)
            apagarSapato.setVisibility(View.VISIBLE);
        else
            apagarSapato.setVisibility(View.GONE);
    }
}
