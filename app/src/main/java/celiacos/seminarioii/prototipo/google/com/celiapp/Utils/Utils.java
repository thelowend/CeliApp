package celiacos.seminarioii.prototipo.google.com.celiapp.Utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.ui.IconGenerator;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;

public class Utils {

    private static Utils instance;

    private FirebaseDatabase db;

    private Utils(){}

    public static Utils getInstance(){
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public Bitmap getIcon(String tipo, Context context) {

        IconGenerator icnGenerator = new IconGenerator(context);
        final BitmapDrawable bitmapdrawable;

        switch (tipo.toLowerCase()) {
            case "restaurant":
                bitmapdrawable = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_restaurant_pink);
                break;
            case "restaurant-celiaco":
                bitmapdrawable = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_restaurant_green);
                break;
            case "dietetica":
                bitmapdrawable = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_gluten_free);
                break;
            default:
                bitmapdrawable = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_gluten_free);
                break;
        }

        icnGenerator.setBackground(bitmapdrawable);

        return icnGenerator.makeIcon();
    }

    private void createEstablecimientos() {
        DatabaseReference dbRef = db.getReference("Establecimientos");

        //Resetea Establecimientos
        dbRef.setValue("");

        Establecimiento uade = new Establecimiento();
        uade.setNombre("UADE");
        uade.setDescripcion("Universidad Argentina de la Empresa");
        uade.setTipo("restaurant");
        uade.setDireccion("Lima 717");
        uade.setFotoUrl("https://www.uade.edu.ar/Dinamico/Themes/Default/img/uade-redes.jpg");
        uade.setLocation(-34.616877,-58.381783);
        dbRef.push().setValue(uade);

        Establecimiento cid = new Establecimiento();
        cid.setNombre("Cid Campeador");
        cid.setDescripcion("Monumento al Cid Campeador en Caballito");
        cid.setTipo("restaurant-celiaco");
        cid.setDireccion("Cid Campeador");
        cid.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        cid.setLocation(-34.607568, -58.445721);
        dbRef.push().setValue(cid);

        Establecimiento hotelDauria = new Establecimiento();
        hotelDauria.setNombre("Hotel D'auria");
        hotelDauria.setDescripcion("Hotel D'auria");
        hotelDauria.setTipo("dietetica");
        hotelDauria.setDireccion("Calle falsa 123");
        hotelDauria.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        hotelDauria.setLocation(-0.43, 0.43);
        dbRef.push().setValue(hotelDauria);

        Establecimiento random1 = new Establecimiento();
        random1.setNombre("Restaurant Random 1");
        random1.setDescripcion("Descripción Restaurant Random 1");
        random1.setTipo("restaurant");
        random1.setDireccion("Dirección Random 1");
        random1.setFotoUrl("https://www.uade.edu.ar/Dinamico/Themes/Default/img/uade-redes.jpg");
        random1.setLocation(-34.614825,-58.381750);
        dbRef.push().setValue(random1);

        Establecimiento random2 = new Establecimiento();
        random2.setNombre("Restaurant Random 2");
        random2.setDescripcion("Descripción Restaurant Random 2");
        random2.setTipo("restaurant-celiaco");
        random2.setDireccion("Dirección Random 2");
        random2.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        random2.setLocation(-34.617774, -58.385140);
        dbRef.push().setValue(random2);

        Establecimiento random3 = new Establecimiento();
        random2.setNombre("Restaurant Random 3");
        random2.setDescripcion("Descripción Restaurant Random 3");
        random2.setTipo("restaurant-celiaco");
        random2.setDireccion("Dirección Random 3");
        random2.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        random2.setLocation(-34.615011, -58.376750);
        dbRef.push().setValue(random2);

    }

    public void createDbData () {
        db = FirebaseDatabase.getInstance();
        createEstablecimientos();
    }
}
