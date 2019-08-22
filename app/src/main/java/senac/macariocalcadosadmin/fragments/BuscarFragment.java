package senac.macariocalcadosadmin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

import senac.macariocalcadosadmin.view.FiltroSapato;
import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.adapters.SelecaoSapatoAdapter;
import senac.macariocalcadosadmin.models.Campo;
import senac.macariocalcadosadmin.models.Genero;
import senac.macariocalcadosadmin.models.Idade;
import senac.macariocalcadosadmin.models.SelecaoSapato;
import senac.macariocalcadosadmin.models.Tipo;

import static senac.macariocalcadosadmin.MainActivity.database;

public class BuscarFragment extends Fragment {

    private RelativeLayout rlCasual;
    private RelativeLayout rlEsportivo;
    private RelativeLayout rlSocial;
    private RelativeLayout rlInfantil;
    private RelativeLayout rlAdulto;
    private RelativeLayout rlFeminino;
    private RelativeLayout rlMasculino;
    private RelativeLayout rlPromocao;

    private SelecaoSapatoAdapter filtroSapato;


    private final String CAMPO = "campo de filtragem";
    private final String CAMPO_INFO = "info campo de filtragem";

    public BuscarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buscar_fragment,container,false);

        dataBiding(view);
        setAdapter();
        setListener();

        return view;
    }

    private void dataBiding(View view){
        rlCasual    = view.findViewById(R.id.categoria_casual);
        rlEsportivo = view.findViewById(R.id.categoria_esporte);
        rlSocial    = view.findViewById(R.id.categoria_social);
        rlInfantil  = view.findViewById(R.id.categoria_infantil);
        rlAdulto    = view.findViewById(R.id.categoria_adulto);
        rlFeminino  = view.findViewById(R.id.categoria_feminino);
        rlMasculino = view.findViewById(R.id.categoria_masculino);
        rlPromocao  = view.findViewById(R.id.categoria_promocao);
    }

    private void setAdapter(){
        filtroSapato = new SelecaoSapatoAdapter(new ArrayList<SelecaoSapato>(),getContext());
    }

    private void setListener(){
        rlCasual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*database.read(filtroSapato,
                            database.filtro(Campo.TIPO, Tipo.CASUAL.toString().toUpperCase()));*/

                    Intent intent = new Intent(getActivity(), FiltroSapato.class);

                    intent.putExtra(CAMPO, Campo.TIPO);
                    intent.putExtra(CAMPO_INFO, Tipo.CASUAL);

                    startActivity(intent);
                }
            }
        );

        rlEsportivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*database.read(filtroSapato,
                        database.filtro(Campo.TIPO, Tipo.ESPORTIVO.toString().toUpperCase()));*/

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.TIPO);
                intent.putExtra(CAMPO_INFO, Tipo.ESPORTIVO);

                startActivity(intent);
            }
        });

        rlSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*database.read(filtroSapato,
                        database.filtro(Campo.TIPO, Tipo.SOCIAL.toString().toUpperCase()));*/

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.TIPO);
                intent.putExtra(CAMPO_INFO, Tipo.SOCIAL);

                startActivity(intent);
            }
        });

        rlInfantil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*database.read(filtroSapato,
                        database.filtro(Campo.IDADE, Idade.INFANTIL.toString().toUpperCase()));*/

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.IDADE);
                intent.putExtra(CAMPO_INFO, Idade.INFANTIL);

                startActivity(intent);
            }
        });

        rlAdulto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*database.read(filtroSapato,
                        database.filtro(Campo.IDADE, Idade.ADULTO.toString().toUpperCase()));*/

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.IDADE);
                intent.putExtra(CAMPO_INFO, Idade.ADULTO);

                startActivity(intent);
            }
        });

        rlFeminino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*database.read(filtroSapato,
                        database.filtro(Campo.GENERO, Genero.FEMININO.toString().toUpperCase()));*/

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.GENERO);
                intent.putExtra(CAMPO_INFO, Genero.FEMININO);

                startActivity(intent);
            }
        });

        rlMasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*database.read(filtroSapato,
                        database.filtro(Campo.GENERO, Genero.MASCULINO.toString().toUpperCase()));*/

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.GENERO);
                intent.putExtra(CAMPO_INFO, Genero.MASCULINO);

                startActivity(intent);
            }
        });

        rlPromocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FiltroSapato.class);

                intent.putExtra(CAMPO, Campo.PROMOCAO);

                startActivity(intent);
            }
        });
    }
}
