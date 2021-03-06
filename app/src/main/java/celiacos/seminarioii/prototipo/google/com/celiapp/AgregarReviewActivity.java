package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.ReviewQuestion;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReviewQuestion;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.enums.TiposOpciones;

public class AgregarReviewActivity extends AppCompatActivity {

    private String establecimientoId;
    private int contador;
    private TextView pregunta;
    private TextView paginas;
    FirebaseAuth mAuth;

    private RatingBar ratingBar;

    private float promedio;

    private Button boton_finalizar;
    private Button botonSi;
    private Button botonNo;
    private Button boton_siguiente;

    private EditText comentario;
    private TextView tvComentario;
    private List<ReviewQuestion> reviews = new ArrayList<>();

    private ArrayList<UserReviewQuestion> userReviewQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_review);

        comentario = findViewById(R.id.texto_usuario);
        tvComentario = findViewById(R.id.tvComentario);
        botonSi = findViewById(R.id.botonSi);
        botonNo = findViewById(R.id.botonNo);
        boton_siguiente = findViewById(R.id.boton_siguiente);

        paginas = findViewById(R.id.paginas);
        pregunta = findViewById(R.id.pregunta);
        ratingBar = findViewById(R.id.ratingBar);
        boton_finalizar = findViewById(R.id.boton_finalizar);
        TextView establecimientoNombre = findViewById(R.id.establecimiento);

        if (getIntent().getExtras() != null) {
            establecimientoNombre.setText((String) getIntent().getExtras().getSerializable("establecimientoNombre"));
            establecimientoId = (String) getIntent().getExtras().getSerializable("establecimientoId");
        }
        comentario.setVisibility(View.INVISIBLE);
        boton_finalizar.setVisibility(View.INVISIBLE);
        tvComentario.setVisibility(View.INVISIBLE);

        getReviewsMock();
        contador = 0;
        paginas.setText(String.format("Pagina %s de %s", contador +1, reviews.size()));
        calcular();
    }

    private void calcular(){
        if(contador == reviews.size()) {
            boton_finalizar.setVisibility(View.VISIBLE);
            comentario.setVisibility(View.VISIBLE);
            tvComentario.setVisibility(View.VISIBLE);

            boton_siguiente.setVisibility(View.INVISIBLE);
            pregunta.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
            botonNo.setVisibility(View.INVISIBLE);
            botonSi.setVisibility(View.INVISIBLE);
            paginas.setVisibility(View.INVISIBLE);

            pregunta.setText(R.string.label_gracias_review);
            ratingBar.setRating(promedio);
        }else {

            pregunta.setText(reviews.get(contador).getEnunciado());
            if (reviews.get(contador).getTipo() == TiposOpciones.ESTRELLAS) {
                ratingBar.setVisibility(View.VISIBLE);
                botonNo.setVisibility(View.INVISIBLE);
                botonSi.setVisibility(View.INVISIBLE);
                boton_siguiente.setVisibility(View.VISIBLE);
            } else {
                ratingBar.setVisibility(View.INVISIBLE);
                boton_siguiente.setVisibility(View.INVISIBLE);
                botonNo.setVisibility(View.VISIBLE);
                botonSi.setVisibility(View.VISIBLE);
            }
            getPregunta().setText(reviews.get(contador).getEnunciado());
            ratingBar.setRating(0);

            contador = contador + 1;
            paginas.setText(String.format("Pagina %s de %s", contador, reviews.size()));
        }
    }

    public void finish(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Establecimientos").child(establecimientoId).child("userReviews");
        mAuth = FirebaseAuth.getInstance();

        UserReview userReview = new UserReview();
        userReview.setComentario(comentario.getText().toString());
        userReview.setEstablecimientoId(establecimientoId);
        userReview.setPuntaje(String.valueOf(promedio / userReviewQuestions.size()));
        if(mAuth != null && mAuth.getCurrentUser() != null)
            userReview.setUserId(mAuth.getCurrentUser().getEmail());
        userReview.setQuestionsReviews(userReviewQuestions);
        userReview.setFecha(System.currentTimeMillis());

        myRef.push().setValue(userReview);

        Log.i("Creando user review", userReview.toString());

        finish();
    }



    public void rateNo(View view){
        float puntaje;
        if(reviews.get(contador-1).getTipo() == TiposOpciones.SINO)
            puntaje = 1;
        else
            puntaje = 5;

        promedio += puntaje;

        userReviewQuestions.add(new UserReviewQuestion(reviews.get(contador-1), puntaje));
        calcular();
    }

    public void rateSi(View view){
        float puntaje;
        if(reviews.get(contador-1).getTipo() == TiposOpciones.SINO)
            puntaje = 5;
        else
            puntaje = 1;

        promedio += puntaje;

        userReviewQuestions.add(new UserReviewQuestion(reviews.get(contador-1), puntaje));
        calcular();
    }

    public void rateRatingBar(View view){
        float puntaje = getRatingBar().getRating();
        promedio += puntaje;

        userReviewQuestions.add(new UserReviewQuestion(reviews.get(contador-1), puntaje));
        calcular();
    }

    private void getReviewsMock(){
        HashMap<String, Integer> mapEstrella = new HashMap<>();
        mapEstrella.put("1",1);
        mapEstrella.put("2",2);
        mapEstrella.put("3",3);
        mapEstrella.put("4",4);
        mapEstrella.put("5",5);

        HashMap<String, Integer> mapSiNo = new HashMap<>();
        mapSiNo.put("No",1);
        mapSiNo.put("Si",5);

        HashMap<String, Integer> mapSiNoInvertido = new HashMap<>();
        mapSiNo.put("No",5);
        mapSiNo.put("Si",1);

        reviews.add(new ReviewQuestion("¿El lugar está libre de contaminación cruzada de gluten?", TiposOpciones.SINO, mapSiNoInvertido));
        reviews.add(new ReviewQuestion("¿Posee cervezas libres de gluten?", TiposOpciones.SINO, mapSiNo));
        reviews.add(new ReviewQuestion("¿Posee variedad de platos libres de gluten?", TiposOpciones.SINO, mapSiNo));
        reviews.add(new ReviewQuestion("¿Posee postres sin gluten?", TiposOpciones.SINO, mapSiNo));
        reviews.add(new ReviewQuestion("Calidad de la atencion", TiposOpciones.ESTRELLAS, mapEstrella));
        reviews.add(new ReviewQuestion("Calidad de los platos", TiposOpciones.ESTRELLAS, mapEstrella));
        reviews.add(new ReviewQuestion("Limpieza del lugar", TiposOpciones.ESTRELLAS, mapEstrella));
        reviews.add(new ReviewQuestion("Ambiente del lugar", TiposOpciones.ESTRELLAS, mapEstrella));
    }

    public TextView getPregunta() {
        return pregunta;
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, AgregarReviewActivity.class);
    }
}