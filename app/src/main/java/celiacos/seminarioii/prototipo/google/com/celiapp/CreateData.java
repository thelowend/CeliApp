package celiacos.seminarioii.prototipo.google.com.celiapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;

/**
 * Created by Diego on 19/11/2017.
 */

public class CreateData {
    private FirebaseDatabase db;

    private void createEstablecimientos() {
        DatabaseReference dbRef = db.getReference("Establecimientos");

        dbRef.setValue("");

        ArrayList<Establecimiento> establecimientos = new ArrayList<Establecimiento>();

        Establecimiento uade = new Establecimiento();
        uade.setNombre("UADE");
        uade.setDescripcion("Universidad Argentina de la Empresa");
        uade.setTipo("Restaurant");
        uade.setDireccion("Lima 717");
        uade.setFotoUrl("https://www.uade.edu.ar/Dinamico/Themes/Default/img/uade-redes.jpg");
        uade.setLocation(-34.616877,-58.381783);
        dbRef.push().setValue(uade);

        Establecimiento cid = new Establecimiento();
        cid.setNombre("Cid Campeador");
        cid.setDescripcion("Monumento al Cid Campeador en Caballito");
        cid.setTipo("Restaurant");
        cid.setDireccion("Cid Campeador");
        cid.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        cid.setLocation(-34.607568, -58.445721);
        dbRef.push().setValue(cid);


        //establecimientos.add(uade);
        //establecimientos.add(cid);

        //dbRef.setValue(establecimientos);
    }
    public CreateData(){
        db = FirebaseDatabase.getInstance();
        createEstablecimientos();
    }
}
