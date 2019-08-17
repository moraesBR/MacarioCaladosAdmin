package senac.macariocalcadosadmin;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import senac.macariocalcadosadmin.adapters.SelecaoFotoAdapter;
import senac.macariocalcadosadmin.models.Foto;
import senac.macariocalcadosadmin.models.Genero;
import senac.macariocalcadosadmin.models.Idade;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoFoto;
import senac.macariocalcadosadmin.models.Tipo;

import static senac.macariocalcadosadmin.MainActivity.database;
import static senac.macariocalcadosadmin.MainActivity.listaSapatos;

public class EditarSapato extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private AppCompatTextView nome, modelo, genero, idade, tipo, valor, quantidade;
    private LinearLayout linlay_nome, linlay_modelo, linlay_genero, linlay_idade, linlay_tipo, linlay_quantidade;


    private List<SelecaoFoto> lista;
    private Sapato sapato = new Sapato();
    private Sapato origSapato;
    private int posicao;
    private final String FOTO_ARRAYLIST = "foto arraylist";

    private TabLayout tabLayout;

    private FloatingActionButton fab;

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
            origSapato = listaSapatos.get(posicao).getSapato();
            Sapato.copiar(origSapato, sapato);
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

        linlay_nome = findViewById(R.id.linlay_sapato_nome);
        linlay_modelo = findViewById(R.id.linlay_sapato_modelo);
        linlay_genero = findViewById(R.id.linlay_sapato_genero);
        linlay_idade = findViewById(R.id.linlay_sapato_idade);
        linlay_tipo = findViewById(R.id.linlay_sapato_tipo);
        linlay_quantidade = findViewById(R.id.linlay_sapato_quantidade);

        fab = findViewById(R.id.fab_update);
    }

    private void setView() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        dadosSapato();
    }

    private void dadosSapato() {
        nome.setText(sapato.getNome());
        modelo.setText(sapato.getModelo());
        genero.setText(sapato.getGenero().toString());
        idade.setText(sapato.getIdade().toString());
        tipo.setText(sapato.getTipo().toString());
        if (sapato.isPromocao())
            valor.setText(String.format(new Locale("pt","BR"),
                    "R$ %.2f por R$ %.2f", sapato.getAntigoValor(), sapato.getValor()));
        else
            valor.setText(String.format(new Locale("pt","BR"),
                    "R$ %.2f", sapato.getValor()));
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
        linlay_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog
                        .Builder(EditarSapato.this,R.style.AlertDialogStyle);
                LayoutInflater inflater = EditarSapato.this.getLayoutInflater();

                final View mView = inflater.inflate(getResources()
                                .getLayout(R.layout.dialog_nome),null);
                final EditText etNome = mView.findViewById(R.id.acet_nome);

                builder.setView(mView)
                        .setTitle("NOME")
                        .setPositiveButton(getResources().getString(R.string.confirma), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                sapato.setNome(etNome.getText().toString());
                                nome.setText(sapato.getNome());
                                fab.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton(R.string.cancela, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        linlay_modelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarSapato.this,
                        R.style.AlertDialogStyle);
                LayoutInflater inflater = EditarSapato.this.getLayoutInflater();
                final View mView = inflater.inflate(getResources()
                        .getLayout(R.layout.dialog_nome),null);
                final EditText etNome = mView.findViewById(R.id.acet_nome);

                builder.setView(mView)
                        .setTitle("MODELO")
                        .setPositiveButton(getResources().getString(R.string.confirma), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                sapato.setModelo(etNome.getText().toString());
                                modelo.setText(sapato.getModelo());
                                fab.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton(R.string.cancela, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        linlay_genero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarSapato.this);
                builder.setTitle(getResources().getString(R.string.genero))
                        .setItems(getResources().getStringArray(R.array.sp_genero),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                sapato.setGenero(Genero.valueOf("FEMININO"));
                                                break;
                                            case 1:
                                                sapato.setGenero(Genero.valueOf("MASCULINO"));
                                                break;
                                        }
                                        genero.setText(sapato.getGenero().toString());
                                        fab.setVisibility(View.VISIBLE);
                                    }
                                });
                builder.create().show();
            }
        });

        linlay_idade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarSapato.this);
                builder.setTitle(getResources().getString(R.string.idade))
                        .setItems(getResources().getStringArray(R.array.sp_idade),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                sapato.setIdade(Idade.valueOf("ADULTO"));
                                                break;
                                            case 1:
                                                sapato.setIdade(Idade.valueOf("INFANTIL"));
                                                break;
                                        }
                                        idade.setText(sapato.getIdade().toString());
                                        fab.setVisibility(View.VISIBLE);
                                    }
                                });
                builder.create().show();
            }
        });

        linlay_tipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarSapato.this);
                builder.setTitle(getResources().getString(R.string.tipo))
                        .setItems(getResources().getStringArray(R.array.sp_tipo2),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case 0:
                                                sapato.setTipo(Tipo.valueOf("CASUAL"));
                                                break;
                                            case 1:
                                                sapato.setTipo(Tipo.valueOf("ESPORTIVO"));
                                                break;
                                            case 2:
                                                sapato.setTipo(Tipo.valueOf("SOCIAL"));
                                                break;
                                        }
                                        tipo.setText(sapato.getTipo().toString());
                                        fab.setVisibility(View.VISIBLE);
                                    }
                                });
                builder.create().show();
            }
        });

        valor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog
                        .Builder(EditarSapato.this, R.style.AlertDialogStyle);
                LayoutInflater inflater = EditarSapato.this.getLayoutInflater();

                final View mView = inflater.inflate(getResources()
                        .getLayout(R.layout.dialog_valor), null);
                final EditText etValor = mView.findViewById(R.id.acet_valor);

                builder.setView(mView)
                        .setTitle("PREÇO")
                        .setPositiveButton(getResources().getString(R.string.confirma), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                sapato.setValor(Double.parseDouble(etValor.getText().toString()));
                                if (sapato.isPromocao())
                                    valor.setText(String.format(new Locale("pt","BR"),
                                            "R$ %.2f por R$ %.2f",
                                            sapato.getAntigoValor(), sapato.getValor()));
                                else
                                    valor.setText(String.format(new Locale("pt","BR"),
                                            "R$ %.2f", sapato.getValor()));
                                fab.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton(R.string.cancela, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        linlay_quantidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog
                        .Builder(EditarSapato.this, R.style.AlertDialogStyle);
                LayoutInflater inflater = EditarSapato.this.getLayoutInflater();

                final View mView = inflater.inflate(getResources()
                        .getLayout(R.layout.dialog_qtd), null);
                final EditText etQtd = mView.findViewById(R.id.acet_qtd);

                builder.setView(mView)
                        .setTitle("QUANTIDADE")
                        .setPositiveButton(getResources().getString(R.string.confirma), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                sapato.setQuantidade(Integer.parseInt(etQtd.getText().toString()));
                                quantidade.setText(String.valueOf(sapato.getQuantidade()));
                                fab.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton(R.string.cancela, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.update(sapato);
                finish();
            }
        });
    }
}
