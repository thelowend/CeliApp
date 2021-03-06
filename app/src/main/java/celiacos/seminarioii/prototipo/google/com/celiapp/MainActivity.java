package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import celiacos.seminarioii.prototipo.google.com.celiapp.Utils.Utils;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.search.SearchListAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback,
        SearchListAdapter.SearchListAdapterListener {

    private FirebaseDatabase db;

    private FirebaseAuth mAuth;

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location usrLocation;
    private View mapView;
    private View locationButton;
    private RecyclerView searchRecyclerView;
    private SearchView searchView;

    private ImageView imgCajonUserPic;
    private TextView txtCajonUsername;
    private TextView txtCajonMail;


    private SearchListAdapter mSearchAdapter;

    //Establecimientos ArrayList iniciación
    private ArrayList<Establecimiento> establecimientos = new ArrayList<>();

    //private ArrayList<Marker> mapMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imgCajonUserPic = navigationView.getHeaderView(0).findViewById(R.id.imgCajonUserPic);
        txtCajonUsername = navigationView.getHeaderView(0).findViewById(R.id.txtCajonUsername);
        txtCajonMail  = navigationView.getHeaderView(0).findViewById(R.id.txtCajonMail);

        mSearchAdapter = new SearchListAdapter(new ArrayList<Establecimiento>(), this, this);
        searchRecyclerView = findViewById(R.id.recycler_search);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchRecyclerView.setAdapter(mSearchAdapter);

        //Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Firebase Database
        db = FirebaseDatabase.getInstance();

        //Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            imgCajonUserPic.setImageURI(currentUser.getPhotoUrl());
            txtCajonUsername.setText(currentUser.getDisplayName());
            txtCajonMail.setText(currentUser.getEmail());
        } else {
            imgCajonUserPic.setImageResource(R.drawable.ic_user_anon);
            txtCajonUsername.setText(R.string.anonimo);
            txtCajonMail.setText(R.string.empty);
        }

    }

    private void loadMarkers () {

        //Carga la referencia a la base
        DatabaseReference myRef = db.getReference("Establecimientos");

        //Guardo el contexto para pasarselo al singleton de Utils y usarlo con getIcon().
        final Context context = this;

        //Escucha en tiempo real los datos
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Establecimiento es = new Establecimiento(messageSnapshot);

                    //Guarda el establecimiento en memoria
                    //TODO: Verificar que no este ya en memoria el establecimiento nuevo antes de agregarlo acá.
                    establecimientos.add(es);

                    //Añade el marcador al mapa
                    Marker marker = mMap.addMarker(new MarkerOptions().position(es.getLocation()).title(es.getNombre()).snippet(es.getDescripcion())
                            .icon(BitmapDescriptorFactory.fromBitmap( Utils.getInstance().getIcon(es.getTipo(), context) )).anchor(0.5f, 0.6f));

                    //Guardo el establecimiento en el marcador
                    marker.setTag(es);

                    //Guardo los markers en memoria
                    //mapMarkers.add(marker);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("Establecimiento", "Falla al leer valor.", databaseError.toException());
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);

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

        //Cargo los marcadores en el mapa
        loadMarkers();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Obtengo el establecimiento definido en el Marker que se clickeó
        Establecimiento markerEs = (Establecimiento) marker.getTag();

        //Llamo al intent de la landing del establecimiento
        Intent intent = new Intent(getApplicationContext(), EstablecimientoActivity.class);
        //Creo un bundle para pasar datos de una Activity a otra
        Bundle bundle = new Bundle();
        //Guardo el Establecimiento en el bundle como Serializable
        bundle.putSerializable("ESTABLECIMIENTO", markerEs);
        //Guardo el bundle en el intent que se va a abrir
        intent.putExtras(bundle);
        //Abro el intent
        startActivity(intent);
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
    MenuItem searchMenuItem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchMenuItem = (MenuItem) menu.findItem(R.id.action_search);
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                searchRecyclerView.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                searchRecyclerView.setVisibility(View.GONE);
                return true;
            }
        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchRecyclerView.setVisibility(View.GONE);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String filterText) {

                if(filterText.isEmpty()) {
                    mSearchAdapter.setEstablecimientos(establecimientos);
                } else {
                    ArrayList<Establecimiento> filteredList = new ArrayList<Establecimiento>();

                    for (Establecimiento es : establecimientos) {
                        if(es.getNombre().toLowerCase().contains(filterText.toLowerCase()))
                            filteredList.add(es);
                    }

                    mSearchAdapter.setEstablecimientos(filteredList);
                }

                return true;
            }
        });

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

            Bundle bundle = new Bundle();
            bundle.putSerializable("ESTABLECIMIENTO", establecimientos.get(2));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.action_resetdb) {
            Utils.getInstance().createDbData();
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

    @Override
    public void onEstablecimientoSelected(Establecimiento es) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(es.getLocation(), 18));
        searchRecyclerView.setVisibility(View.GONE);
        searchMenuItem.collapseActionView();
        /*
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
        */
    }
}
