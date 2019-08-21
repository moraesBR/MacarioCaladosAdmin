package senac.macariocalcadosadmin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import senac.macariocalcadosadmin.R;
import senac.macariocalcadosadmin.models.SelecaoFoto;

public class SelecaoFotoAdapter extends PagerAdapter {

    private List<SelecaoFoto> listaFotos;
    private Context context;
    private int dotSelected = 0;
    private ImageView[] dots;

    /*private void initiateShowDots(){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_editar_sapato, null);
        LinearLayout sliderDotspanel = view.findViewById(R.id.slider_dots);
        sliderDotspanel.setBackground(context.getDrawable(R.drawable.layout_bg));

        int dotsCount = this.listaFotos.size();
        dots = new ImageView[dotsCount];
        for(int i = 0; i < dotsCount; i++){

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat
                    .getDrawable(context,R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }
        dots[dotSelected].setImageDrawable(ContextCompat
                .getDrawable(context, R.drawable.active_dot));
    }

    private void selectDot(int position){
        int oldDotSelected = dotSelected;
        dotSelected = position;
        dots[oldDotSelected].setImageDrawable(ContextCompat
                .getDrawable(context, R.drawable.non_active_dot));
        dots[dotSelected].setImageDrawable(ContextCompat
                .getDrawable(context, R.drawable.active_dot));
    }*/

    public SelecaoFotoAdapter(List<SelecaoFoto> listaFotos, Context context) {
        this.listaFotos = listaFotos;
        this.context = context;
        //initiateShowDots();
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
        Picasso.get().load(listaFotos.get(position).getFoto().getUrl()).into(imageView);
        container.addView(imageView, 0);
        //selectDot(position);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
