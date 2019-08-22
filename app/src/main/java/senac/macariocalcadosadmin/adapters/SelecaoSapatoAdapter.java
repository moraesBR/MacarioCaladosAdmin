package senac.macariocalcadosadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.models.Sapato;
import senac.macariocalcadosadmin.models.SelecaoSapato;

public class SelecaoSapatoAdapter extends RecyclerView.Adapter<SelecaoSapatoAdapter.SelacaoSapatoViewHolder> implements Filterable {
    private List<SelecaoSapato> selecaoSapatoList;
    private List<SelecaoSapato> backup;
    private Context context;
    private View.OnClickListener verSapato;
    private View.OnLongClickListener marcarSapato;

    public void setVerSapato(View.OnClickListener verSapato) {
        this.verSapato = verSapato;
    }

    public void setMarcarSapato(View.OnLongClickListener marcarSapato) {
        this.marcarSapato = marcarSapato;
    }

    public SelecaoSapatoAdapter(List<SelecaoSapato> selecaoSapatoList, Context context) {
        super();
        this.selecaoSapatoList = selecaoSapatoList;
        this.backup = selecaoSapatoList;
        this.context = context;
    }

    public List<SelecaoSapato> getSelecaoSapatoList() {
        return selecaoSapatoList;
    }

    @NonNull
    @Override
    public SelacaoSapatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sapato_item, parent, false);
        return new SelacaoSapatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelacaoSapatoViewHolder holder, int position) {
        Sapato sapato = selecaoSapatoList.get(position).getSapato();
        if (selecaoSapatoList.get(position).isSelecionado())
            holder.card.setBackground(context.getDrawable(R.drawable.cardbg_red));
        else if (sapato.isPromocao())
            holder.card.setBackground(context.getDrawable(R.drawable.cardbg_gold));
        else
            holder.card.setBackground(context.getDrawable(R.drawable.cardbg_silver));

        if (sapato.getFotos() == null || sapato.getFotos().isEmpty()) {
            Picasso.get().load(R.drawable.sem_imagem)
                    .into(holder.foto);
            holder.foto.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
        }
        else {
            Picasso.get()
                    .load(sapato.getFotos().get(0).getUrl())
                    .fit()
                    .centerCrop()
                    .into(holder.foto);

            holder.foto.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.nome.setText(sapato.getNome());
        holder.modelo.setText(sapato.getModelo());
        holder.valor.setText(String.format(new Locale("pt","BR"),"R$ %.2f", sapato.getValor()));
    }

    @Override
    public int getItemCount() {
        return selecaoSapatoList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                if(query.isEmpty())
                {
                    selecaoSapatoList = backup;
                }
                else
                {
                    List<SelecaoSapato> filtro = new ArrayList<>();
                    for(SelecaoSapato s : backup)
                    {
                        if(s.getSapato().getNome().toLowerCase().startsWith(query) ||
                            s.getSapato().getModelo().toLowerCase().startsWith(query))
                        {
                            filtro.add(s);
                        }
                    }
                    selecaoSapatoList = filtro;
                }


                FilterResults resultado = new FilterResults();
                resultado.values = selecaoSapatoList;
                return resultado;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                selecaoSapatoList = (List<SelecaoSapato>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class SelacaoSapatoViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout card;
        private ImageView foto;
        private TextView nome, modelo, valor;
        private ProgressBar progressBar;

        SelacaoSapatoViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_sapato);
            foto = itemView.findViewById(R.id.iv_sapato);
            nome = itemView.findViewById(R.id.tv_nome);
            modelo = itemView.findViewById(R.id.tv_modelo);
            valor = itemView.findViewById(R.id.tv_preco);
            progressBar = itemView.findViewById(R.id.pbarImagem);
            itemView.setTag(this);
            itemView.setOnLongClickListener(marcarSapato);
            itemView.setOnClickListener(verSapato);
        }
    }


}
