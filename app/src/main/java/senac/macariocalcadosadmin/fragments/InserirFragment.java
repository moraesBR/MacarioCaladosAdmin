package senac.macariocalcadosadmin.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.models.SelecaoFoto;

public class InserirFragment extends Fragment {

    private List<SelecaoFoto> listaFotos;
    private Fragment visualizadorImagemFragment;
    private FragmentTransaction transaction;
    private FrameLayout frameLayout;

    public InserirFragment() {
    }

/* ---------------------------------- Ciclo de Vida do App -------------------------------------- */
    /*
     *  Métodos
     *      onCreateView(): Inicializa o app (data binding e controle).
     *      onSaveInstanceState(): Salva os estados do arraylist de fotos, da foto principal e layout
     *          do RecyclerView.
     *      onActivityCreated(): Restaura os estados do arraylist de fotos, da foto principal e
     *          layout do RecyclerView.
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inserir_fragment,container,false);

        bindView(view);
        setView(savedInstanceState);

        return view;
    }

    private void bindView(View view){
        frameLayout = view.findViewById(R.id.frame_fragment_visualizador);
    }

    public void setView(Bundle savedInstanceState){
        if(visualizadorImagemFragment == null) {
            visualizadorImagemFragment = new VisualizadorImagemFragment();
            transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.frame_fragment_visualizador, visualizadorImagemFragment).
                    addToBackStack(FRAGMENT_VISUALIZADOR_IMAGEM).commit();
            Log.e("NULO","NULO");
        }
        else{
            transaction.replace(R.id.frame_fragment_visualizador,visualizadorImagemFragment)
                    .addToBackStack(FRAGMENT_VISUALIZADOR_IMAGEM)
                    .commit();
            Log.e("EXISTE","EXISTE");
        }
    }

    private final String FRAGMENT_VISUALIZADOR_IMAGEM = "visualizadorImagemFragment";
}
