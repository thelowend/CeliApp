package celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites;

import java.io.Serializable;

public class UserReviewQuestion implements Serializable {

    public ReviewQuestion reviewQuestion;
    public float puntaje;

    public UserReviewQuestion() {
    }

    public UserReviewQuestion(ReviewQuestion reviewQuestion, float puntaje) {
        this.reviewQuestion = reviewQuestion;
        this.puntaje = puntaje;
    }

    public ReviewQuestion getReviewQuestion() {
        return reviewQuestion;
    }

    public void setReviewQuestion(ReviewQuestion reviewQuestion) {
        this.reviewQuestion = reviewQuestion;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }
}
