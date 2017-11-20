package celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities;

import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

public class Establecimiento {
    private int establecimientoID;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String direccion;
    private String fotoUrl;
    private double lat;
    private double lon;
    private ArrayList<Integer> reviews;
    //private BitmapDrawable icon;

    //Constructor
    @SuppressWarnings("unused")
    public Establecimiento() {
        setEstablecimientoID();
        reviews = new ArrayList<Integer>();
    }

    private void setEstablecimientoID() {
        Random randomNumberGenerator = new Random();
        establecimientoID = randomNumberGenerator.nextInt(1000000);
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
    public void addReview(int reviewID) {
        reviews.add(reviewID);
    }

    //Getters
    public int getEstablecimientoID(){
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
    public ArrayList<Integer> getReviews(){
        return reviews;
    }

    /*
    public BitmapDrawable getIcon(){

        (BitmapDrawable) getResources()
                .getDrawable(R.mipmap.ic_restaurant_green);
    }
    */

}
