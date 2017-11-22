package celiacos.seminarioii.prototipo.google.com.celiapp.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.Utils.Utils;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchResultHolder> {

    private List<Establecimiento> mEstablecimientos;
    private Context context;

    public interface SearchListAdapterListener {
        void onEstablecimientoSelected(Establecimiento establecimiento);
    }

    private SearchListAdapterListener mListener;

    public SearchListAdapter(List<Establecimiento> establecimientos, SearchListAdapterListener listener, Context con) {
        context = con;
        mEstablecimientos = establecimientos;
        mListener = listener;
    }

    @Override
    public SearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_item, parent, false);
        return new SearchResultHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchResultHolder holder, int position) {
        holder.bind(mEstablecimientos.get(position));
    }

    @Override
    public int getItemCount() {
        return mEstablecimientos.size();
    }

    public void setEstablecimientos(List<Establecimiento> establecimientos) {
        mEstablecimientos = establecimientos;
        notifyDataSetChanged();
    }

    public class SearchResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView establecimientoImage;
        TextView establecimientoName;
        TextView establecimientoDesc;

        public SearchResultHolder(View itemView) {
            super(itemView);
            establecimientoImage = (ImageView) itemView.findViewById(R.id.establecimiento_image);
            establecimientoName = (TextView) itemView.findViewById(R.id.establecimiento_nombre);
            establecimientoDesc = (TextView) itemView.findViewById(R.id.establecimiento_descripcion);
            itemView.setOnClickListener(this);
        }

        public void bind(Establecimiento establecimiento) {
            establecimientoImage.setImageBitmap(Utils.getInstance().getIcon(establecimiento.getTipo(), context));
            establecimientoName.setText(establecimiento.getNombre());
            establecimientoDesc.setText(establecimiento.getDescripcion());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (mListener != null) {
                mListener.onEstablecimientoSelected(mEstablecimientos.get(position));
            }
        }
    }
}