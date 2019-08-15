package senac.macariocalcadosadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.models.SelecaoUpload;


public class SelecaoUploadAdapter extends RecyclerView.Adapter<SelecaoUploadAdapter.SelecaoFotoViewHolder> {

    private List<SelecaoUpload> fotos;
    private Context context;
    private View.OnClickListener selecionadoItem;
    private View.OnLongClickListener marcadoItem;

    public SelecaoUploadAdapter(List<SelecaoUpload> photos, Context context) {
        this.fotos = photos;
        this.context = context;
    }

    public void setFotos(List<SelecaoUpload> fotos){
        this.fotos = fotos;
    }

    public void setSelecionarItem(View.OnClickListener selectItem) {
        this.selecionadoItem = selectItem;
    }

    public void setMarcarItem(View.OnLongClickListener checkedItem){
        this.marcadoItem = checkedItem;
    }

    @NonNull
    @Override
    public SelecaoFotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.foto_item,parent,false);
        SelecaoFotoViewHolder holder = new SelecaoFotoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelecaoFotoViewHolder holder, int position) {
        if(fotos.get(position).isSelecionada())
            holder.image.setBackgroundResource(R.color.colorError);
        else
            holder.image.setBackgroundResource(R.color.black);

        Picasso.get().load(fotos.get(position).getUrl())
                .resize(80,80)
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    class SelecaoFotoViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;

        public SelecaoFotoViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_item_view);
            itemView.setTag(this);
            itemView.setOnClickListener(selecionadoItem);
            itemView.setOnLongClickListener(marcadoItem);
        }
    }
}

