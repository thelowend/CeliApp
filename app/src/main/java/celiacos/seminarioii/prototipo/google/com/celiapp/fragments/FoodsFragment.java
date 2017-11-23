package celiacos.seminarioii.prototipo.google.com.celiapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import celiacos.seminarioii.prototipo.google.com.celiapp.GalleryActivity;
import celiacos.seminarioii.prototipo.google.com.celiapp.PhotoActivity;
import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.photos.GalleryAdapter;
import celiacos.seminarioii.prototipo.google.com.celiapp.photos.Photo;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.AdapterReview;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;


public class FoodsFragment extends Fragment {

    private Establecimiento mainEstablecimiento;
    private DatabaseReference mDatabaseRef;
    private List<Photo> photos;
    private ListView listView;
    private GalleryAdapter adapter;
    private ProgressDialog progressDialog;

    public FoodsFragment() {}

    public static FoodsFragment newInstance(Establecimiento es) {
        FoodsFragment fragment = new FoodsFragment();

        Bundle args = new Bundle();
        args.putSerializable("ESTABLECIMIENTO", es);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_establecimiento_foods, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainEstablecimiento = (Establecimiento) getArguments().getSerializable("ESTABLECIMIENTO");

        final Fragment frag = this;

        photos = new ArrayList<>();
        listView = getActivity().findViewById(R.id.listViewGallery);

        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMessage("Cargando imágenes...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Establecimientos").child(mainEstablecimiento.getEstablecimientoID()).child("photos");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                photos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Photo photo = snapshot.getValue(Photo.class);
                    photos.add(photo);
                }



                adapter = new GalleryAdapter(frag.getActivity(), R.layout.image_item, photos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }


}
