package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;

public class ChooserActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Establecimiento mainEstablecimiento;

    private static final Class[] CLASSES = new Class[]{
            MainActivity.class,
            LoginActivity.class,
            SplashScreen.class,
            EstablecimientoActivity.class,
            AgregarReviewActivity.class,
            ReviewActivity.class,
            GalleryActivity.class
    };

    private static final int[] DESCRIPTION_IDS = new int[] {
            R.string.desc_main_activity,
            R.string.desc_login_activity,
            R.string.desc_splashscreen_activity,
            R.string.title_activity_establecimiento,
            R.string.agregar_review_activity,
            R.string.review_activity,
            R.string.gallery_activity
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        mainEstablecimiento = (Establecimiento) this.getIntent().getSerializableExtra("ESTABLECIMIENTO");

        // Set up ListView and Adapter
        ListView listView = findViewById(R.id.list_view);

        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_2, CLASSES);
        adapter.setDescriptionIds(DESCRIPTION_IDS);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class clicked = CLASSES[position];

        Intent intent = new Intent(this, clicked);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ESTABLECIMIENTO", mainEstablecimiento);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static class MyArrayAdapter extends ArrayAdapter<Class> {

        private Context mContext;
        private Class[] mClasses;
        private int[] mDescriptionIds;

        public MyArrayAdapter(Context context, int resource, Class[] objects) {
            super(context, resource, objects);

            mContext = context;
            mClasses = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(android.R.layout.simple_list_item_2, null);
            }

            ((TextView) view.findViewById(android.R.id.text1)).setText(mClasses[position].getSimpleName());
            ((TextView) view.findViewById(android.R.id.text2)).setText(mDescriptionIds[position]);

            return view;
        }

        public void setDescriptionIds(int[] descriptionIds) {
            mDescriptionIds = descriptionIds;
        }
    }
}
