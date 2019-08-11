package senac.macariocalcadosadmin.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
    private RecyclerView.LayoutManager lmSapatos;
    private FloatingActionButton apagarSapato;
    private ProgressBar progressBar;

    /* Clicks Listener */
    private View.OnLongClickListener marcarCard;
    private View.OnClickListener verSapato;
    private final String POSICAO_ARRAY = "posição sapato arraylist";

    public VisualizarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visualizar_fragment, container, false);

        dataBinding(view);
        setAdapters(view, savedInstanceState);
        setListener();

        return view;
    }

    private void dataBinding(View view) {
        rvSapatos = view.findViewById(R.id.rv_sapatos);
        apagarSapato = view.findViewById(R.id.fab_apaga_sapato);
        progressBar = view.findViewById(R.id.progressbar);
    }


    private void setAdapters(View view, Bundle savedInstanceState) {
        sapatoAdapter = new SelecaoSapatoAdapter(listaSapatos, view.getContext());
        lmSapatos = new GridLayoutManager(view.getContext(), 2);
        rvSapatos.setLayoutManager(lmSapatos);
        rvSapatos.setHasFixedSize(true);
        rvSapatos.setAdapter(sapatoAdapter);

        database.read(sapatoAdapter,listaSapatos,progressBar);
    }

    private void setListener() {
        marcarCard = new View.OnLongClickListener() {
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
                if (!listaSapatos.get(position).isSelecionado()) {
                    qtdFoto++;
                } else
                    qtdFoto--;

                /* Altera o estado de marcação (boolean) */
                listaSapatos.get(position).setSelecionado();
                /* Informa ao adapter que houve ateração nos dados */
                sapatoAdapter.notifyDataSetChanged();

                setSapatoView();

                return qtdFoto > 0;
            }
        };
        sapatoAdapter.setMarcarSapato(marcarCard);

        verSapato = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                /* Captura a posição correspondente no adapter */
                int position = viewHolder.getAdapterPosition();
                Intent editarSapato = new Intent(getActivity(), EditarSapato.class);

                editarSapato.putExtra(POSICAO_ARRAY,position);
                startActivity(editarSapato);
            }
        };
        sapatoAdapter.setVerSapato(verSapato);

        apagarSapato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("REMOVENDO...")
                        .setMessage("Você tem certeza que deseja excluir estes sapatos?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Apaga as fotos se houver pelo menos uma foto selecionada */
                                if (qtdFoto > 0) {
                                    for (int i = 0; i < listaSapatos.size(); i++)
                                        if (listaSapatos.get(i).isSelecionado())
                                            listaSapatos.remove(i--);
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
    }

    public void setSapatoView() {
        if (qtdFoto > 0)
            apagarSapato.setVisibility(View.VISIBLE);
        else
            apagarSapato.setVisibility(View.GONE);
    }

}
