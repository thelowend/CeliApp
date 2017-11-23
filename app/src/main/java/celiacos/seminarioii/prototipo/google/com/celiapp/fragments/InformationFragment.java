package celiacos.seminarioii.prototipo.google.com.celiapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import celiacos.seminarioii.prototipo.google.com.celiapp.AgregarReviewActivity;
import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.ReviewActivity;
import celiacos.seminarioii.prototipo.google.com.celiapp.Utils.Utils;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;


public class InformationFragment extends Fragment {

    Establecimiento mainEstablecimiento;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private RatingBar rtgCriterio1;
    private TextView txtCriterio1rtg;
    private RatingBar rtgCriterio2;
    private TextView txtCriterio2rtg;
    private RatingBar rtgCriterio3;
    private TextView txtCriterio3rtg;
    private RatingBar rtgCriterio4;
    private TextView txtCriterio4rtg;
    private RatingBar rtgCriterio5;
    private TextView txtCriterio5rtg;
    private RatingBar rtgCriterio6;
    private TextView txtCriterio6rtg;
    private RatingBar rtgCriterio7;
    private TextView txtCriterio7rtg;
    private RatingBar rtgCriterio8;
    private TextView txtCriterio8rtg;
    private ArrayList<UserReview> userReviewsList;

    ImageButton imgbtnFavorito;
    ImageButton imgbtnLlamar;
    ImageButton imgbtnEscribirOpinion;

    public InformationFragment() {}

    public static InformationFragment newInstance(Establecimiento es) {
        InformationFragment fragment = new InformationFragment();

        Bundle args = new Bundle();
        args.putSerializable("ESTABLECIMIENTO", es);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_establecimiento_information, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mainEstablecimiento = (Establecimiento) getArguments().getSerializable("ESTABLECIMIENTO");
        userReviewsList = mainEstablecimiento.getReviews();

        TextView txtDireccion = getActivity().findViewById(R.id.txtDireccion);
        TextView txtHorario = getActivity().findViewById(R.id.txtHorario);
        TextView txtTelefono = getActivity().findViewById(R.id.txtTelefono);

        rtgCriterio1 = getActivity().findViewById(R.id.rtgCriterio1);
        txtCriterio1rtg = getActivity().findViewById(R.id.txtCriterio1rtg);
        rtgCriterio2 = getActivity().findViewById(R.id.rtgCriterio2);
        txtCriterio2rtg = getActivity().findViewById(R.id.txtCriterio2rtg);
        rtgCriterio3 = getActivity().findViewById(R.id.rtgCriterio3);
        txtCriterio3rtg = getActivity().findViewById(R.id.txtCriterio3rtg);
        rtgCriterio4 = getActivity().findViewById(R.id.rtgCriterio4);
        txtCriterio4rtg = getActivity().findViewById(R.id.txtCriterio4rtg);
        rtgCriterio5 = getActivity().findViewById(R.id.rtgCriterio5);
        txtCriterio5rtg = getActivity().findViewById(R.id.txtCriterio5rtg);
        rtgCriterio6 = getActivity().findViewById(R.id.rtgCriterio6);
        txtCriterio6rtg = getActivity().findViewById(R.id.txtCriterio6rtg);
        rtgCriterio7 = getActivity().findViewById(R.id.rtgCriterio7);
        txtCriterio7rtg = getActivity().findViewById(R.id.txtCriterio7rtg);
        rtgCriterio8 = getActivity().findViewById(R.id.rtgCriterio8);
        txtCriterio8rtg = getActivity().findViewById(R.id.txtCriterio8rtg);


        //Creo el mapa de evaluaciones sumando los puntajes de cada pregunta
        final ArrayList<Float> sumatoriaPuntajes = new ArrayList<>(8);
        for (int j = 0; j < 8; j++) {
            sumatoriaPuntajes.add(j, (float)0);
        };

        //Carga las user reviews
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Establecimientos").child(mainEstablecimiento.getEstablecimientoID()).child("userReviews");

        //Escucha en tiempo real los datos
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                   ArrayList<HashMap<String, Object>> questionReviews = (ArrayList<HashMap<String, Object>>) messageSnapshot.child("questionsReviews").getValue();

                   for (int i = 0; i < questionReviews.size(); i++) {

                       //--- MUERTE A LOS LENGUAJES TIPADOS ---//
                       float val = Float.parseFloat(questionReviews.get(i).get("puntaje").toString());
                       //--- MUERTE A LOS LENGUAJES TIPADOS ---//

                       sumatoriaPuntajes.set(i, sumatoriaPuntajes.get(i) + val);
                   }
                }

                //seteo los puntajes de cada criterio
                int amountOfReviews = userReviewsList.size();
                if (amountOfReviews > 0) {

                    float rating1 = sumatoriaPuntajes.get(0) / amountOfReviews;
                    rtgCriterio1.setRating(rating1);
                    rtgCriterio1.setVisibility(View.INVISIBLE);
                    txtCriterio1rtg.setVisibility(View.VISIBLE);
                    if (rating1 < 5) {
                        txtCriterio1rtg.setText("NO");
                    } else {
                        txtCriterio1rtg.setText("SI");
                    }

                    float rating2 = sumatoriaPuntajes.get(1) / amountOfReviews;
                    rtgCriterio2.setRating(rating2);
                    rtgCriterio2.setVisibility(View.INVISIBLE);
                    txtCriterio2rtg.setVisibility(View.VISIBLE);
                    if (rating2 == 5) {
                        txtCriterio2rtg.setText("NO");
                    } else {
                        txtCriterio2rtg.setText("SI");
                    }

                    float rating3 = sumatoriaPuntajes.get(2) / amountOfReviews;
                    rtgCriterio3.setRating(rating3);
                    rtgCriterio3.setVisibility(View.INVISIBLE);
                    txtCriterio3rtg.setVisibility(View.VISIBLE);
                    if (rating3 == 5) {
                        txtCriterio3rtg.setText("NO");
                    } else {
                        txtCriterio3rtg.setText("SI");
                    }

                    float rating4 = sumatoriaPuntajes.get(3) / amountOfReviews;
                    rtgCriterio4.setRating(rating4);
                    rtgCriterio4.setVisibility(View.INVISIBLE);
                    txtCriterio4rtg.setVisibility(View.VISIBLE);
                    if (rating4 == 5) {
                        txtCriterio4rtg.setText("NO");
                    } else {
                        txtCriterio4rtg.setText("SI");
                    }

                    rtgCriterio5.setRating(sumatoriaPuntajes.get(4) / amountOfReviews);
                    rtgCriterio5.setVisibility(View.VISIBLE);
                    txtCriterio5rtg.setVisibility(View.INVISIBLE);

                    rtgCriterio6.setRating(sumatoriaPuntajes.get(5) / amountOfReviews);
                    rtgCriterio6.setVisibility(View.VISIBLE);
                    txtCriterio6rtg.setVisibility(View.INVISIBLE);

                    rtgCriterio7.setRating(sumatoriaPuntajes.get(6) / amountOfReviews);
                    rtgCriterio7.setVisibility(View.VISIBLE);
                    txtCriterio7rtg.setVisibility(View.INVISIBLE);

                    rtgCriterio8.setRating(sumatoriaPuntajes.get(7) / amountOfReviews);
                    rtgCriterio8.setVisibility(View.VISIBLE);
                    txtCriterio8rtg.setVisibility(View.INVISIBLE);

                } else {
                    rtgCriterio1.setVisibility(View.GONE);
                    txtCriterio1rtg.setVisibility(View.VISIBLE);
                    txtCriterio1rtg.setText(R.string.reseñas_noinfo);

                    rtgCriterio2.setVisibility(View.GONE);
                    txtCriterio2rtg.setVisibility(View.VISIBLE);
                    txtCriterio2rtg.setText(R.string.reseñas_noinfo);

                    rtgCriterio3.setVisibility(View.GONE);
                    txtCriterio3rtg.setVisibility(View.VISIBLE);
                    txtCriterio3rtg.setText(R.string.reseñas_noinfo);

                    rtgCriterio4.setVisibility(View.GONE);
                    txtCriterio4rtg.setVisibility(View.VISIBLE);
                    txtCriterio4rtg.setText(R.string.reseñas_noinfo);

                    rtgCriterio5.setVisibility(View.GONE);
                    txtCriterio5rtg.setVisibility(View.VISIBLE);
                    txtCriterio5rtg.setText(R.string.reseñas_no);

                    rtgCriterio6.setVisibility(View.GONE);
                    txtCriterio6rtg.setVisibility(View.VISIBLE);
                    txtCriterio6rtg.setText(R.string.reseñas_no);

                    rtgCriterio7.setVisibility(View.GONE);
                    txtCriterio7rtg.setVisibility(View.VISIBLE);
                    txtCriterio7rtg.setText(R.string.reseñas_no);

                    rtgCriterio8.setVisibility(View.GONE);
                    txtCriterio8rtg.setVisibility(View.VISIBLE);
                    txtCriterio8rtg.setText(R.string.reseñas_no);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("Reviews", "Falla al leer valor.", databaseError.toException());
            }
        });

        //Muestro datos
        txtDireccion.setText(mainEstablecimiento.getDireccion());
        txtHorario.setText(mainEstablecimiento.getHorario());
        txtTelefono.setText(mainEstablecimiento.getTelefono());
        final Activity activity = getActivity();

        imgbtnEscribirOpinion = this.getActivity().findViewById(R.id.imgbtnEscribirOpinion);
        imgbtnEscribirOpinion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try{
                    if(mUser != null) {
                        Intent intent = AgregarReviewActivity.makeIntent(activity);

                        intent.putExtra("establecimientoNombre", mainEstablecimiento.getNombre());
                        intent.putExtra("establecimientoId", mainEstablecimiento.getEstablecimientoID());
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, "¡Necesitás estar logueado para dejar una reseña!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        imgbtnLlamar = this.getActivity().findViewById(R.id.imgbtnLlamar);
        imgbtnLlamar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(Utils.getInstance().parseTelefono(mainEstablecimiento.getTelefono())));
                    startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        imgbtnFavorito = this.getActivity().findViewById(R.id.imgbtnFavorito);
        imgbtnFavorito.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    if(mUser != null) {
                        Toast.makeText(activity, "¡Guardado en favoritos! (No realmente)", Toast.LENGTH_SHORT).show();
                        imgbtnFavorito.setImageResource(R.drawable.ic_favorito_guardado);
                    } else {
                        Toast.makeText(activity, "¡Necesitás estar logueado para guardar favoritos!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
