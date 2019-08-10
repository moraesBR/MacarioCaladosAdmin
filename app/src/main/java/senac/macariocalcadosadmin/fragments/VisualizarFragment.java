package senac.macariocalcadosadmin.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.adapters.SelecaoSapatoAdapter;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoSapato;

import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class VisualizarFragment extends Fragment {

    //private List<SelecaoSapato> listaSapatos = new ArrayList<>();
    private SelecaoSapatoAdapter sapatoAdapter;
    private RecyclerView rvSapatos;
    private RecyclerView.LayoutManager lmSapatos;
    private Parcelable salvoRecyclerLayout;

    public VisualizarFragment() {
    }

    /*public void setListaSapatos(List<Sapato> listaSapatos){
        this.listaSapatos = new ArrayList<>((Collection<? extends SelecaoSapato>) listaSapatos);
    }*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.visualizar_fragment,container,false);

        dataBinding(view);
        setAdapters(view, savedInstanceState);

        return view;
    }


    private final String BUNDLE_RECYCLER_LAYOUT = "Grid layout";

    /* ---------------------- SALVAR CONTEXTO ---------------------- */
    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_SHOES_STATE, (Serializable) sapatoAdapter);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, rvSapatos.getLayoutManager().onSaveInstanceState());
    }*/

    /*@Override
    public void onActivityCreated(@Nullable Bundle inState) {
        super.onActivityCreated(inState);
        if (inState != null) {
            sapatoAdapter = (SelecaoSapatoAdapter) inState.getSerializable(LIST_SHOES_STATE);
            salvoRecyclerLayout = inState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }
    }*/

    private void dataBinding(View view) {
        rvSapatos = view.findViewById(R.id.rv_sapatos);
    }

    private void setAdapters(View view, Bundle savedInstanceState){
        /*if(savedInstanceState != null) {
            sapatoAdapter = (SelecaoSapatoAdapter) savedInstanceState.getSerializable(LIST_SHOES_STATE);
        }else{*/
            if(sapatoAdapter == null)
                sapatoAdapter = new SelecaoSapatoAdapter(listaSapatos,view.getContext());
            lmSapatos = new GridLayoutManager(view.getContext(),2);
            rvSapatos.setLayoutManager(lmSapatos);
            rvSapatos.setHasFixedSize(true);
        /*}*/

        rvSapatos.setAdapter(sapatoAdapter);

       /* if (salvoRecyclerLayout != null){
            rvSapatos.getLayoutManager().onRestoreInstanceState(salvoRecyclerLayout);
        }*/
    }
}
