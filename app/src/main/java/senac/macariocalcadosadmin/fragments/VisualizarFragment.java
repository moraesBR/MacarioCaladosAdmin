package senac.macariocalcadosadmin.fragments;

import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import senac.macariocalcadosadmin.EditarSapato;
import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.adapters.SelecaoSapatoAdapter;

import static senac.macariocalcadosadmin.MainActivity.database;
import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class VisualizarFragment extends Fragment {

    private int qtdFoto;

    private SelecaoSapatoAdapter sapatoAdapter;
    private RecyclerView rvSapatos;
    private FloatingActionButton apagarSapato;
    private ProgressBar progressBar;
    private SearchView searchView;

    private final String POSICAO_ARRAY = "posição sapato arraylist";

    public VisualizarFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visualizar_fragment, container, false);

        dataBinding(view);
        setAdapters(view);
        setListener();

        return view;
    }

    private void dataBinding(View view) {
        rvSapatos = view.findViewById(R.id.rv_sapatos);
        apagarSapato = view.findViewById(R.id.fab_apaga_sapato);
        progressBar = view.findViewById(R.id.progressbar);
        searchView = view.findViewById(R.id.visualizar_busca);
    }


    private void setAdapters(View view) {
        sapatoAdapter = new SelecaoSapatoAdapter(listaSapatos, view.getContext());
        rvSapatos.setAdapter(sapatoAdapter);
        RecyclerView.LayoutManager lmSapatos = new GridLayoutManager(view.getContext(), 2,
                RecyclerView.VERTICAL,false);
        rvSapatos.setLayoutManager(lmSapatos);
        rvSapatos.setHasFixedSize(true);


        database.read(sapatoAdapter,progressBar);
        //database.read(sapatoAdapter,database.filtro(Campo.VALOR));
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
                if (!sapatoAdapter.getSelecaoSapatoList().get(position).isSelecionado()) {
                    qtdFoto++;
                } else
                    qtdFoto--;

                /* Altera o estado de marcação (boolean) */
                sapatoAdapter.getSelecaoSapatoList().get(position).setSelecionado();
                /* Informa ao adapter que houve ateração nos dados */
                sapatoAdapter.notifyDataSetChanged();

                setSapatoView();

                return qtdFoto > 0;
            }
        };
        sapatoAdapter.setMarcarSapato(marcarCard);

        /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
        /* Captura a posição correspondente no adapter */
        View.OnClickListener verSapato = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                /* Captura a posição correspondente no adapter */
                int position = viewHolder.getAdapterPosition();
                Intent editarSapato = new Intent(getActivity(), EditarSapato.class);

                editarSapato.putExtra(POSICAO_ARRAY, sapatoAdapter.getSelecaoSapatoList().get(position).getSapato());
                startActivity(editarSapato);
            }
        };
        sapatoAdapter.setVerSapato(verSapato);

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
                                    for (int i = 0; i < sapatoAdapter.getSelecaoSapatoList().size(); i++)
                                        if (sapatoAdapter.getSelecaoSapatoList().get(i).isSelecionado()) {
                                            database.delete(sapatoAdapter.getSelecaoSapatoList().get(i).getSapato());
                                            sapatoAdapter.getSelecaoSapatoList().remove(i--);
                                        }
                                }

                                qtdFoto = 0;
                                sapatoAdapter.notifyDataSetChanged();
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
                sapatoAdapter.getFilter().filter(s);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sapatoAdapter.getFilter().filter(s);
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
