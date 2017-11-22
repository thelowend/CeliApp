package celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites;


import java.io.Serializable;
import java.util.ArrayList;

public class UserReview implements Serializable {

    private String establecimientoId;
    //private ArrayList<UserReviewQuestion> questionsReviews;
    private String comentario;
    private String userId;
    private String fecha;
    private String puntaje;

    public UserReview() {
    }

    public UserReview(String userReviewId, ArrayList<UserReviewQuestion> questionsReviews, String comentario, String userId, String fecha, String puntaje) {
        this.establecimientoId = establecimientoId;
        //this.questionsReviews = questionsReviews;
        this.comentario = comentario;
        this.userId = userId;
        this.fecha = fecha;
        this.puntaje = puntaje;
    }

    public String getEstablecimientoId() {
        return establecimientoId;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    /*
    public ArrayList<UserReviewQuestion> getQuestionsReviews() {
        return questionsReviews;
    }

    public void setQuestionsReviews(ArrayList<UserReviewQuestion> questionsReviews) {
        this.questionsReviews = questionsReviews;
    }
    */
    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }
}