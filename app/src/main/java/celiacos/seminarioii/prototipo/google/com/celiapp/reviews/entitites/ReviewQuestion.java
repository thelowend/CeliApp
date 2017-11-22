package celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.enums.TiposOpciones;

public class ReviewQuestion implements Serializable {

    private String enunciado;
    private TiposOpciones tipo;
    private HashMap<String, Integer> valores;

    public ReviewQuestion() {
    }

    public ReviewQuestion(String enunciado, TiposOpciones tipo, HashMap<String, Integer> valores) {
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.valores = valores;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public TiposOpciones getTipo() {
        return tipo;
    }

    public void setTipo(TiposOpciones tipo) {
        this.tipo = tipo;
    }

    public HashMap<String, Integer> getValores() {
        return valores;
    }

    public void setValores(HashMap<String, Integer> valores) {
        this.valores = valores;
    }
}


