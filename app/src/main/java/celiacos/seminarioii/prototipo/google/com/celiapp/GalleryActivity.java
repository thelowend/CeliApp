package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import celiacos.seminarioii.prototipo.google.com.celiapp.photos.GalleryAdapter;
import celiacos.seminarioii.prototipo.google.com.celiapp.photos.Photo;

@SuppressWarnings("unchecked")
public class GalleryActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRef;
    private List<Photo> photos;
    private ListView listView;
    private GalleryAdapter adapter;
    private ProgressDialog progressDialog;
    private String establecimientoId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        photos = new ArrayList<>();
        listView = findViewById(R.id.listViewGallery);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get("establecimiento") != null){
            establecimientoId = (String)bundle.get("establecimientoId");
        }else{
            establecimientoId = "-KzQFVf9bVYnLGJiYOZI";
        }


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando imagenes...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Establecimientos").child(establecimientoId).child("photos");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Photo photo = snapshot.getValue(Photo.class);
                    photos.add(photo);
                }

                adapter = new GalleryAdapter(GalleryActivity.this, R.layout.image_item, photos);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public void goToUpload(View view){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }
}
