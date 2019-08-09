package senac.macariocalcadosadmin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.models.Sapato;

public class VisualizarFragment extends Fragment {
    public VisualizarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.visualizar_fragment,container,false);
    }
}
