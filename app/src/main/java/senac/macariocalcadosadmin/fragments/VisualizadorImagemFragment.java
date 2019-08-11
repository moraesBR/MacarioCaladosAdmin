package senac.macariocalcadosadmin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.adapters.SelecaoUploadAdapter;
import senac.macariocalcadosadmin.models.SelecaoUpload;

import static android.app.Activity.RESULT_OK;
/*
 * --------------------- NÃO USADO ---------------------
 */
public class VisualizadorImagemFragment extends Fragment {
    /* ------------------------- Variáveis -------------------------- */
    /* Contador de fotos para deletar do RecyclerView */
    public int qtdFoto;
    /* Armazena a configuração prévia do layout do RecycleView */
    private Parcelable salvoRecyclerLayout;

    /* Models */
    private SelecaoUpload principalFoto;
    private List<SelecaoUpload> listaFoto;
    /*private SelecaoFotoListener selecaoFotoListener;*/

    /* RecyclerView, LayoutManager e Adapter */
    private SelecaoUploadAdapter fotoAdapter;
    private RecyclerView rvFoto;
    private RecyclerView.LayoutManager lmPhotos;

    /* Buttons */
    private ImageButton btnNovaFoto;
    private ImageButton btnAdicioneFoto;
    private ImageButton btnApagueFoto;

    /* Clicks Listener */
    private View.OnLongClickListener marcarFoto;
    private View.OnClickListener selecionarFoto;

    /* Itens do layout activity */
    private LinearLayout linlay1;
    private TextView tvSelecionadaFoto;
    private ImageView ivPrincipalFoto;

    /* ------------------------- Identificadores -------------------------- */
    /* Solicitação de inicialização de atividade */
    private static final int PICK_IMAGE_REQUEST = 1;
    /* Referência ao conteúdo do Array List de Photos */
    private static final String LIST_STATE = "list_state";
    /* Referência à foto principal do layout */
    private static final String MAIN_PHOTO = "main_photo";
    /* Referência à configuração de layout do RecyclerView */
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    /* Referência à quantidade de fotos marcadas para exclusão */
    private static final String NUMBER_PHOTOS = "number photos";


    public VisualizadorImagemFragment() {
    }

    /*public interface SelecaoFotoListener{
        void onInputFotoImagemSent(List<SelecaoUpload> listaFoto);
    }*/

    public void atualizaSelecaoFoto(List<SelecaoUpload> listaFoto){
        this.listaFoto = listaFoto;
        this.fotoAdapter.notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.visualizador_imagem,container,false);
        /*selecaoFotoListener.onInputFotoImagemSent(listaFoto);*/
        dataBindView(view);
        setAdapter(view, savedInstanceState);
        clickable();

        return view;
    }

    /* ---------------------- SALVAR CONTEXTO ---------------------- */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE, (ArrayList<? extends Parcelable>) listaFoto);
        outState.putParcelable(MAIN_PHOTO, principalFoto);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, rvFoto.getLayoutManager().onSaveInstanceState());
        outState.putInt(NUMBER_PHOTOS,qtdFoto);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle inState) {
        super.onActivityCreated(inState);
        if (inState != null) {
            listaFoto = inState.getParcelableArrayList(LIST_STATE);
            principalFoto = inState.getParcelable(MAIN_PHOTO);
            salvoRecyclerLayout = inState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            qtdFoto = inState.getInt(NUMBER_PHOTOS);
        }
    }

    /* ---------------------------------- Métodos Data Binding -------------------------------------- */
    /*
     *  Métodos
     *      dataBindView(): Realiza a ligação dos elementos do layout com variáveis programáveis.
     *      setAdapter(): Configura os dados e o layout do RecyclerView de fotos.
     */

    private void dataBindView(View view){
        ivPrincipalFoto = view.findViewById(R.id.visualizador_iv_photo);
        rvFoto = view.findViewById(R.id.visualizador_recycler);
        btnNovaFoto = view.findViewById(R.id.btn_new_photo);
        btnAdicioneFoto = view.findViewById(R.id.btn_add_photo);
        btnApagueFoto = view.findViewById(R.id.btn_delete_photo);
        linlay1        = view.findViewById(R.id.visualizador_linlay1);
        tvSelecionadaFoto = view.findViewById(R.id.tv_img_selec);
    }


    private void setAdapter(View view, Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            listaFoto = savedInstanceState.getParcelableArrayList(LIST_STATE);
            principalFoto = savedInstanceState.getParcelable(MAIN_PHOTO);
            qtdFoto = savedInstanceState.getInt(NUMBER_PHOTOS);
        }else{
            listaFoto = new ArrayList<>();
            principalFoto = null;
            qtdFoto = 0;
        }

        fotoAdapter = new SelecaoUploadAdapter(listaFoto, view.getContext());
        lmPhotos = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        rvFoto.setLayoutManager(lmPhotos);
        rvFoto.setHasFixedSize(true);
        rvFoto.setAdapter(fotoAdapter);

        if (salvoRecyclerLayout != null){
            rvFoto.getLayoutManager().onRestoreInstanceState(salvoRecyclerLayout);
        }

        setPhotosView();
    }

    /* ---------------------------------- Métodos de Controles -------------------------------------- */
    /*
     *  Métodos
     *      setPhotosView(): controla a visibilidade dos elementos responsáveis pela apresentação
     *          das imagens.
     *      clickable(): Controla as ações de cliques dos botões e afins
     */


    /*  setPhotosView(): controla a visibilidade dos elementos responsáveis pela apresentação das
     *      imagens. Se não houver fotos no adaptador de fotos, então tais elementos são escondidos;
     *      caso contrário, serão apresentados.
     */
    private void setPhotosView(){
        if(listaFoto.isEmpty()) {
            principalFoto = null;
            ivPrincipalFoto.setVisibility(View.GONE);
            linlay1.setVisibility(View.GONE);
            tvSelecionadaFoto.setVisibility(View.VISIBLE);
        }else{
            ivPrincipalFoto.setVisibility(View.VISIBLE);
            linlay1.setVisibility(View.VISIBLE);
            tvSelecionadaFoto.setVisibility(View.GONE);
            if (listaFoto.size() == 1)
                principalFoto = listaFoto.get(0);
            Picasso.get().load(principalFoto.getUrl())
                    .resize(360, 200)
                    .into(ivPrincipalFoto);
        }
        if(qtdFoto > 0){
            btnApagueFoto.setVisibility(View.VISIBLE);
        }
        else {
            btnApagueFoto.setVisibility(View.GONE);
        }
    }


    /* clickable(): Controla as ações de cliques dos botões e afins */
    private void clickable() {
        /* ---------------------- Clicks no RecyclerView ---------------------- */
        /* Marca fotos para a exclusão */
        marcarFoto = new View.OnLongClickListener() {
            @Override
            /* Após o click longo na imagem dentro do RecyclerView */
            public boolean onLongClick(View view) {
                /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                /* Captura a posição correspondente no adapter */
                int position = viewHolder.getAdapterPosition();

                /*
                 * Se não estiver sido selecionado, então adicione no contador de imagens
                 * selecionadas; senão, retire do contador.
                 */
                if(!listaFoto.get(position).isSelecionada()){
                    qtdFoto++;
                }else
                    qtdFoto--;

                /* Altera o estado de marcação (boolean) */
                listaFoto.get(position).setSelecionada();

                /* Informa ao adapter que houve ateração nos dados */
                fotoAdapter.notifyDataSetChanged();

                /* Determina se o botão de excluir fotos será apresentado ou não no layout.
                 * Se a quantidade de fotos no contador for maior que 0, então apresente-o;
                 * Senão, esconda-o*/
                setPhotosView();
                if(qtdFoto > 0){
                    btnApagueFoto.setVisibility(View.VISIBLE);
                    return true;
                }
                else {
                    btnApagueFoto.setVisibility(View.GONE);
                    return false;
                }
            }
        };
        fotoAdapter.setMarcarItem(marcarFoto);

        /* Seleciona a foto no RecyclerView para apresentar no layout de imagem principal */
        selecionarFoto = new View.OnClickListener() {
            @Override
            /* Após o click curto na imagem dentro do RecyclerView */
            public void onClick(View view) {
                /* view.getTag(): captura o viewholder clicado atrelado ao RecyclerView */
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

                /* Captura a posição correspondente no adapter */
                int position = viewHolder.getAdapterPosition();

                /* Atualiza a foto principal */
                principalFoto = listaFoto.get(position);

                /* Apresenta a foto no layout main */
                Picasso.get().load(principalFoto.getUrl())
                        .resize(360,200)
                        .into(ivPrincipalFoto);
            }
        };
        fotoAdapter.setSelecionarItem(selecionarFoto);

        /* Adiciona uma foto ao RecyclerView via câmera */
        btnNovaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* ---------------------- Clicks em Buttons ---------------------- */
        /* Adciona fotos ao RecyclerView via External Storage */
        btnAdicioneFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        /* Deleta as fotos selecionadas no RecyclerView */
        btnApagueFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Apaga as fotos se houver pelo menos uma foto selecionada */
                if(qtdFoto > 0){
                    /* Percorre o ArrayList de Photos */
                    for(int i = 0; i < listaFoto.size(); i++){
                        /*
                         *  Se a foto estiver selecionada, então remova-a e determine qual foto será
                         *  a foto principal
                         */
                        if(listaFoto.get(i).isSelecionada()) {
                            if(listaFoto.get(i).equals(principalFoto)) {
                                listaFoto.remove(i);
                                if(!listaFoto.isEmpty())
                                    principalFoto = listaFoto.get(0);
                            }else
                                listaFoto.remove(i);
                            i--;
                        }
                    }

                    /* Zera o contado de fotos selecionadas */
                    qtdFoto = 0;

                    /* notifica o adapter sobre a modificação no List associado a ele */
                    fotoAdapter.notifyDataSetChanged();

                    /* Determina se será apresentado os layouts de foto e RecycleView */
                    setPhotosView();
                }
            }
        });
    }


    /* ---------------------------------- Métodos auxiliares ---------------------------------------- */
    /*
     *  Métodos
     *      openFileChooser(): Busca uma imagem através de uma activity padrão
     *      onActivityResult():  Recebe e trata o resultado obtido pelo método openFileChooser.
     */

    /* ----------------- Gerar resultados a partir de uma activity ----------------- */
    /*
     *  openFileChooser(): Busca uma imagem através de uma activity padrão e retorna o resultado via
     *      identificado (PICK_IMAGE_REQUEST). Quando o usuário terminar esta atividade, o sistema
     *      chamará o método onActivityResult()
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    /*
     *  onActivityResult(): Recebe e trata o resultado obtido pelo método openFileChooser. Possui
     *      três argumentos. O requestCode indica o código de solicitação passado ao método
     *      startActivityForResult(); resultCode informa o código de resultado pela atividade gerada
     *      em openFileChooser; data que é o Intent com os dados coletados, que no caso é aquela um
     *      arquivo de foto qualquer.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*  Se o código de requisição for PICK_IMAGE_REQUEST, resultado ok, houve dados não nulos,
         *      então, adicione uma foto ao RecyclerView.
         */
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            SelecaoUpload photo = new SelecaoUpload(data.getData());
            listaFoto.add(photo);
            fotoAdapter.notifyDataSetChanged();

            /* Determina se será apresentado os layouts de foto e RecycleView */
            setPhotosView();
        }
    }
}
