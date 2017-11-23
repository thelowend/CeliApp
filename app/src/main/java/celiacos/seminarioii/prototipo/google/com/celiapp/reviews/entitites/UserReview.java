package celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites;

import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;
import java.util.ArrayList;

public class UserReview implements Serializable {

    private String establecimientoId;
    private ArrayList<UserReviewQuestion> questionsReviews;
    private String comentario;
    private String userId;
    private long fecha;
    private String puntaje;

    public UserReview() {
    }

    public UserReview(DataSnapshot dataSnapshot){
        establecimientoId = (String) dataSnapshot.child("establecimientoId").getValue();
        comentario = (String) dataSnapshot.child("comentario").getValue();
        fecha = (long) dataSnapshot.child("fecha").getValue();
        userId = (String) dataSnapshot.child("userId").getValue();
        puntaje = (String) dataSnapshot.child("puntaje").getValue();
    }

    public UserReview(String userReviewId, ArrayList<UserReviewQuestion> questionsReviews, String comentario, String userId, long fecha, String puntaje) {
        this.establecimientoId = establecimientoId;
        this.questionsReviews = questionsReviews;
        this.comentario = comentario;
        this.userId = userId;
        this.fecha = fecha;
        this.puntaje = puntaje;
    }


    public void setEstablecimientoId(String establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public ArrayList<UserReviewQuestion> getQuestionsReviews() {
        return questionsReviews;
    }

    public void setQuestionsReviews(ArrayList<UserReviewQuestion> questionsReviews) {
        this.questionsReviews = questionsReviews;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }
}