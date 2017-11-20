package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;

import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private FirebaseDatabase db;

    private FirebaseAuth mAuth;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location usrLocation;
    private View mapView;
    private View locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //
        setSupportActionBar(toolbar);

        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        db = FirebaseDatabase.getInstance();
        //DatabaseReference dbRef = db.getReference();

        //Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //mAuth.getCurrentUser().getDisplayName();
        //updateUI(currentUser);
    }

    private void loadMarkers () {

        //Crea datos para la base (lo mude al menú de arriba a la derecha)
        //new CreateData();

        //Carga datos de la base
        DatabaseReference myRef = db.getReference("Establecimientos");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                //String a = "a";
                HashMap<String, Establecimiento> estab = (HashMap<String, Establecimiento>) value;

                //ArrayList<Establecimiento> estab = (ArrayList<Establecimiento>) value;
                //dataSnapshot.getKey()
                //String b = "b";
                //Establecimiento es = (Establecimiento) estab.get();
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        //mMap
        IconGenerator icnGeneratorRG = new IconGenerator(this);
        IconGenerator icnGeneratorRP = new IconGenerator(this);
        IconGenerator icnGeneratorGG = new IconGenerator(this);


        final BitmapDrawable restaurantGreen = (BitmapDrawable) this.getResources()
                .getDrawable(R.mipmap.ic_restaurant_green);

        final BitmapDrawable restaurantPink = (BitmapDrawable) this.getResources()
                .getDrawable(R.mipmap.ic_restaurant_pink);

        final BitmapDrawable glutenFreeGreen = (BitmapDrawable) this.getResources()
                .getDrawable(R.mipmap.ic_gluten_free);

        icnGeneratorRG.setBackground(restaurantGreen);
        icnGeneratorRP.setBackground(restaurantPink);
        icnGeneratorGG.setBackground(glutenFreeGreen);

        Bitmap restaurantGreenBitmap = icnGeneratorRG.makeIcon();
        Bitmap restaurantPinkBitmap = icnGeneratorRP.makeIcon();
        Bitmap glutenFreeGreenBitmap = icnGeneratorGG.makeIcon();

        //UADE
        mMap.addMarker(new MarkerOptions().position(new LatLng(-34.616877, -58.381783))
                .icon(BitmapDescriptorFactory.fromBitmap(glutenFreeGreenBitmap)).anchor(0.5f, 0.6f));

        //CID CAMPEADOR
        mMap.addMarker(new MarkerOptions().position(new LatLng(-34.607568, -58.445721))
                .icon(BitmapDescriptorFactory.fromBitmap(restaurantPinkBitmap)).anchor(0.5f, 0.6f));

        //Random cerca de UADE
        mMap.addMarker(new MarkerOptions().position(new LatLng(-34.614825, -58.381750))
                .icon(BitmapDescriptorFactory.fromBitmap(restaurantGreenBitmap)).anchor(0.5f, 0.6f));

        //Random cerca de UADE 2
        mMap.addMarker(new MarkerOptions().position(new LatLng(-34.617774, -58.385140))
                .icon(BitmapDescriptorFactory.fromBitmap(restaurantPinkBitmap)).anchor(0.5f, 0.6f));

        //Random cerca de UADE 3
        mMap.addMarker(new MarkerOptions().position(new LatLng(-34.615011, -58.376750))
                .icon(BitmapDescriptorFactory.fromBitmap(restaurantGreenBitmap)).anchor(0.5f, 0.6f));


        //myPlaceItemMarkers.add();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Centra en el usuario al inicio
                if (locationButton != null) {
                    locationButton.performClick();
                }
            }
        });

        // Muevo el botón de locación abajo a la derecha.
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {

            locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);

            } else {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

        }

        loadMarkers();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        /*
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        */

        //return super.onCreateOptionsMenu(menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_log_in) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_chooser) {
            Intent intent = new Intent(getApplicationContext(), ChooserActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.action_resetdb) {
            new CreateData();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_locaciones) {

        } else if (id == R.id.nav_contribuciones) {

        } else if (id == R.id.nav_medallas) {

        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_compartir) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
