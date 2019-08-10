package senac.macariocalcadosadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.models.SelecaoSapato;

public class SelecaoSapatoAdapter extends RecyclerView.Adapter<SelecaoSapatoAdapter.SelacaoSapatoViewHolder> {

    private List<SelecaoSapato> selecaoSapatoList;
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
        this.selecaoSapatoList = selecaoSapatoList;
        this.context = context;
    }

    @NonNull
    @Override
    public SelacaoSapatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sapato_item,parent,false);
        SelacaoSapatoViewHolder holder = new SelacaoSapatoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelacaoSapatoViewHolder holder, int position) {
        SelecaoSapato sapato = selecaoSapatoList.get(position);
        if(sapato.isSelecionado())
            holder.card.setBackground(context.getDrawable(R.drawable.cardbg_red));
        else if(selecaoSapatoList.get(position).isPromocao())
            holder.card.setBackground(context.getDrawable(R.drawable.cardbg_gold));
        else
            holder.card.setBackground(context.getDrawable(R.drawable.cardbg_silver));

        Picasso.get().load(sapato.getFotos().get(0).getUrl())
                .into(holder.foto);

        holder.nome.setText(sapato.getNome());
        holder.modelo.setText(sapato.getModelo());
        holder.valor.setText("R$ " + String.format("%.2f",sapato.getValor()));
    }

    @Override
    public int getItemCount() {
        return selecaoSapatoList.size();
    }

    public class SelacaoSapatoViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout card;
        private ImageView foto;
        private TextView nome, modelo, valor;

        public SelacaoSapatoViewHolder(@NonNull View itemView) {
            super(itemView);
            card   = itemView.findViewById(R.id.card_sapato);
            foto   = itemView.findViewById(R.id.iv_sapato);
            nome   = itemView.findViewById(R.id.tv_nome);
            modelo = itemView.findViewById(R.id.tv_modelo);
            valor  = itemView.findViewById(R.id.tv_preco);
            itemView.setOnLongClickListener(marcarSapato);
            itemView.setOnClickListener(verSapato);
        }
    }
}
