package celiacos.seminarioii.prototipo.google.com.celiapp.reviews;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;

public class AdapterReview extends BaseAdapter{

    protected Activity activity;
    private List<UserReview> items;

    public AdapterReview(Activity activity, List<UserReview> userReviews) {
        this.activity = activity;
        this.items = userReviews;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inf != null) {
                v = inf.inflate(R.layout.review, null);
            }
        }

        UserReview userReview = items.get(position);

        TextView nombre = v.findViewById(R.id.nombre);
        nombre.setText(userReview.getUserId());

        TextView comentario = v.findViewById(R.id.comentario);
        comentario.setText(userReview.getComentario());
        comentario.setEnabled(false);
        RatingBar puntaje = v.findViewById(R.id.puntaje_review);
        if(userReview.getPuntaje() !=null)
            puntaje.setRating(Float.parseFloat(userReview.getPuntaje()));

        TextView fecha = v.findViewById(R.id.fecha);
        PrettyTime p = new PrettyTime(new Locale("es"));
        fecha.setText(p.format(new Date(userReview.getFecha())));

        return v;
    }
}