package senac.macariocalcadosadmin.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import senac.macariocalcadosadmin.models.SelecaoFoto;

public class SelecaoFotoAdapter extends PagerAdapter {

    private List<SelecaoFoto> listaFotos;
    private Context context;

    public SelecaoFotoAdapter(List<SelecaoFoto> listaFotos, Context context) {
        this.listaFotos = listaFotos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaFotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(listaFotos.get(position).getFoto().getUrl()).fit().into(imageView);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
