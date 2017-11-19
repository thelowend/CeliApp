package celiacos.seminarioii.prototipo.google.com.celiapp;

/**
 * Created by Diego on 19/11/2017.
 */

public class Location {
    private int id;
    private String nombre;
    private String tipo;
    private Location loc;

    public Location() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Location(String nombre, String tipo, Location loc) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
