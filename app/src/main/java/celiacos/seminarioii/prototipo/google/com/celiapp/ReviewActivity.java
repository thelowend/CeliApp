package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.AdapterReview;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;

@SuppressWarnings("unchecked")
public class ReviewActivity extends AppCompatActivity {

    RatingBar ratingGral;
    TextView nombreResto;
    TextView cantidadReviews;

    int contador;
    float punt;

    List<UserReview> reviews = new ArrayList<>();
    ListView userReviews;

    String establecimientoId;
    String establecimientoNombre;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null && bundle.get("establecimiento") != null){
                establecimientoId = (String)bundle.get("establecimientoId");
                establecimientoNombre = (String)bundle.get("establecimientoNombre");
            }else{
                establecimientoNombre = "Hotel DAuria";
                establecimientoId = "-KzQFVf9bVYnLGJiYOZI";
            }

            userReviews = findViewById(R.id.userReviews);
            ratingGral = findViewById(R.id.rtgGeneral);
            nombreResto = findViewById(R.id.txtNombreResto);
            cantidadReviews = findViewById(R.id.txtCantidadReviews);

            nombreResto.setText(establecimientoNombre);


            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Establecimientos").child(establecimientoId).child("userReviews");
            myRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    reviews.clear();
                    for (final DataSnapshot child : dataSnapshot.getChildren()) {
                        UserReview userReview = new UserReview();
                        userReview.setEstablecimientoId((String) child.child("establecimientoId").getValue());
                        userReview.setComentario((String) child.child("comentario").getValue());
                        userReview.setFecha((String) child.child("fecha").getValue());
                        userReview.setUserId((String) child.child("userId").getValue());
                        userReview.setPuntaje((String) child.child("puntaje").getValue());
                        reviews.add(userReview);
                        contador = contador + 1;
                        punt = punt + Float.parseFloat(userReview.getPuntaje());
                    }
                    ratingGral.setRating(punt / contador);
                    if(contador == 1)
                        cantidadReviews.setText(String.valueOf(contador) + " reseña");
                    else
                        cantidadReviews.setText(String.valueOf(contador) + " reseñas");

                    ListAdapter adapter = new AdapterReview(ReviewActivity.this, reviews);
                    userReviews.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("Nueva user review", "Failed to read value.", databaseError.toException());
                }
            });

            ListAdapter adapter = new AdapterReview(this, reviews);
            userReviews.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick (View v)
    {
        Intent intent = AgregarReviewActivity.makeIntent(ReviewActivity.this);
        intent.putExtra("establecimientoNombre", establecimientoNombre);
        intent.putExtra("establecimientoId", establecimientoId);
        startActivity(intent);
    }

}
