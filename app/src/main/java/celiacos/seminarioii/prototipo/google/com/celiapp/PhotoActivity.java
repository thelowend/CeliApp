package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import celiacos.seminarioii.prototipo.google.com.celiapp.photos.Photo;

@SuppressWarnings("unchecked")
public class PhotoActivity extends AppCompatActivity {


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    private EditText textImageDescription;
    private Uri imgUri;
    private FirebaseAuth mAuth;
    String establecimientoId;

    public static final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get("establecimiento") != null){
            establecimientoId = (String)bundle.get("establecimientoId");
        }else{
            establecimientoId = "-KzQFVf9bVYnLGJiYOZI";
        }

        mStorageRef = FirebaseStorage.getInstance().getReference().child(establecimientoId);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Establecimientos").child(establecimientoId).child("photos");
        mAuth = FirebaseAuth.getInstance();

        imageView = findViewById(R.id.imagenUpload);
        textImageDescription = findViewById(R.id.descripcionImagen);
    }


    public void btnUpload(View view){
        if(imgUri != null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Subiendo foto...");
            dialog.show();
            StorageReference ref = mStorageRef.child("photos").child("image" + System.currentTimeMillis() + "." + getImageExt(imgUri));

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Imagen subida exitosamente", Toast.LENGTH_SHORT).show();
                    Photo photo = new Photo();
                    photo.setDescripcion(textImageDescription.getText().toString());
                    photo.setUrl(taskSnapshot.getDownloadUrl().toString());
                    if(mAuth != null && mAuth.getCurrentUser() != null)
                        photo.setUsuario(mAuth.getCurrentUser().getEmail());

                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(photo);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    dialog.setMessage("Subiendo " + (int) progress + "%");
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnBrowse(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
