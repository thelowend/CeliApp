package celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Random;

import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;

public class Establecimiento {
    private String establecimientoID;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String direccion;
    private String fotoUrl;
    private double lat;
    private double lon;
    private ArrayList<UserReview> reviews = new ArrayList<>();
    //private BitmapDrawable icon;

    //Constructor
    public Establecimiento() {
    }

    public Establecimiento(DataSnapshot dataSnapshot) {
        establecimientoID = (String) dataSnapshot.child("establecimientoID").getValue();
        nombre = (String) dataSnapshot.child("nombre").getValue();
        descripcion = (String) dataSnapshot.child("descripcion").getValue();
        tipo = (String) dataSnapshot.child("tipo").getValue();
        direccion = (String) dataSnapshot.child("direccion").getValue();
        fotoUrl = (String) dataSnapshot.child("fotoUrl").getValue();
        lat = (double) dataSnapshot.child("location").child("latitude").getValue();
        lon = (double) dataSnapshot.child("location").child("longitude").getValue();
    }

    public void setNombre(String nom){
        nombre = nom;
    }
    public void setDescripcion(String desc){
        descripcion = desc;
    }
    public void setTipo(String tip){
        tipo = tip;
    }
    public void setDireccion(String direcc){
        direccion = direcc;
    }
    public void setFotoUrl(String foto){
        fotoUrl = foto;
    }
    public void setLocation(double la, double lo){
        lat = la;
        lon = lo;
    }
    public void addReview(UserReview userReview) {
        reviews.add(userReview);
    }

    //Getters
    public String getEstablecimientoID(){
        return establecimientoID;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public String getTipo(){
        return tipo;
    }
    public String getDireccion(){
        return direccion;
    }
    public String getFotoUrl(){
        return fotoUrl;
    }
    public LatLng getLocation(){
        return new LatLng(lat, lon);
    }
    public ArrayList<UserReview> getReviews(){
        return reviews;
    }

    public void setEstablecimientoID(String establecimientoID) {
        this.establecimientoID = establecimientoID;
    }
}
