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

    private void pushAndSaveKey(Establecimiento establecimiento, DatabaseReference dbRef){
        establecimiento.setEstablecimientoID(dbRef.getKey());
        dbRef.setValue(establecimiento);
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
        uade.setLocalidad("Capital Federal");
        uade.setHorario("7:30 a 22:30");
        uade.setTelefono("011 4000-7600");
        uade.setFotoUrl("https://www.uade.edu.ar/Dinamico/Themes/Default/img/uade-redes.jpg");
        uade.setLocation(-34.616877,-58.381783);

        pushAndSaveKey(uade, dbRef.push());

        Establecimiento cid = new Establecimiento();
        cid.setNombre("Cid Campeador");
        cid.setDescripcion("Monumento al Cid Campeador en Caballito");
        cid.setTipo("restaurant-celiaco");
        cid.setDireccion("Cid Campeador");
        cid.setLocalidad("Capital Federal");
        cid.setHorario("00:00 a 23:59");
        cid.setTelefono("Es una estatua");
        cid.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        cid.setLocation(-34.607568, -58.445721);
        pushAndSaveKey(cid, dbRef.push());

        Establecimiento hotelDauria = new Establecimiento();
        hotelDauria.setNombre("Hotel D'auria");
        hotelDauria.setDescripcion("Hotel D'auria");
        hotelDauria.setTipo("dietetica");
        hotelDauria.setDireccion("Calle falsa 123");
        hotelDauria.setLocalidad("Capital Federal");
        hotelDauria.setHorario("06:00 a 23:00");
        hotelDauria.setTelefono("011 1234-5678");
        hotelDauria.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        hotelDauria.setLocation(-0.43, 0.43);
        pushAndSaveKey(hotelDauria, dbRef.push());

        Establecimiento random1 = new Establecimiento();
        random1.setNombre("Restaurant Random 1");
        random1.setDescripcion("Descripción Restaurant Random 1");
        random1.setTipo("restaurant");
        random1.setDireccion("Dirección Random 1");
        random1.setLocalidad("Capital Federal");
        random1.setHorario("09:00 a 18:00");
        random1.setTelefono("011 5678-1234");
        random1.setFotoUrl("https://www.uade.edu.ar/Dinamico/Themes/Default/img/uade-redes.jpg");
        random1.setLocation(-34.614825,-58.381750);
        pushAndSaveKey(random1, dbRef.push());

        Establecimiento random2 = new Establecimiento();
        random2.setNombre("Restaurant Random 2");
        random2.setDescripcion("Descripción Restaurant Random 2");
        random2.setTipo("restaurant-celiaco");
        random2.setDireccion("Dirección Random 2");
        random2.setLocalidad("Capital Federal");
        random2.setHorario("10:00 a 21:30");
        random2.setTelefono("011 0303-456");
        random2.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        random2.setLocation(-34.617774, -58.385140);
        pushAndSaveKey(random2, dbRef.push());

        Establecimiento random3 = new Establecimiento();
        random3.setNombre("Restaurant Random 3");
        random3.setDescripcion("Descripción Restaurant Random 3");
        random3.setTipo("restaurant-celiaco");
        random3.setDireccion("Dirección Random 3");
        random3.setLocalidad("Capital Federal");
        random3.setHorario("10:00 a 21:30");
        random3.setTelefono("011 0303-456");
        random3.setFotoUrl("http://www.endlessmile.com/images/201105/200408D63b.jpg");
        random3.setLocation(-34.615011, -58.376750);
        pushAndSaveKey(random3, dbRef.push());
    }

    public void createDbData () {
        db = FirebaseDatabase.getInstance();
        createEstablecimientos();
    }
}
