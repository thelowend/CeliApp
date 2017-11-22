package celiacos.seminarioii.prototipo.google.com.celiapp.photos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;

public class GalleryAdapter extends ArrayAdapter<Photo> {
    private List<Photo> photos;
    private int resource;
    private Activity context;

    public GalleryAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Photo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.photos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(resource, null);
        TextView tvDescripcionImagen = v.findViewById(R.id.tvDescripcionImagen);
        TextView tvUsuario = v.findViewById(R.id.tvUsuarioImagen);
        ImageView img = v.findViewById(R.id.imgView);

        tvDescripcionImagen.setText(photos.get(position).getDescripcion());
        tvUsuario.setText(photos.get(position).getUsuario());
        Glide.with(context).load(photos.get(position).getUrl()).into(img);

        return v;
    }

    public class ViewHolder {
        ImageView imageView;
    }

}