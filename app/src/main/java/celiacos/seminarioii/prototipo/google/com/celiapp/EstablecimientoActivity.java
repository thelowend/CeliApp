package celiacos.seminarioii.prototipo.google.com.celiapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.fragments.FoodsFragment;
import celiacos.seminarioii.prototipo.google.com.celiapp.fragments.InformationFragment;
import celiacos.seminarioii.prototipo.google.com.celiapp.fragments.ReviewFragment;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;

public class EstablecimientoActivity extends AppCompatActivity {

    private Establecimiento mainEstablecimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establecimiento);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //Obtengo Establecimiento del bundle para cargar los datos
        mainEstablecimiento = (Establecimiento) this.getIntent().getSerializableExtra("ESTABLECIMIENTO");

        TextView txtNombre = this.findViewById(R.id.txtNombreEstablecimiento);
        TextView txtDescripcion = this.findViewById(R.id.txtDescripcionEstablecimiento);
        RatingBar rtgEstablecimiento = this.findViewById(R.id.rtgEstablecimiento);
        TextView txtReseñaEstablecimiento = this.findViewById(R.id.txtReseñaEstablecimiento);
        ArrayList<UserReview> userReviewsList = mainEstablecimiento.getReviews();

        //Muestro datos del Establecimiento:
        txtNombre.setText(mainEstablecimiento.getNombre());
        txtDescripcion.setText(mainEstablecimiento.getDescripcion());

        int totalReviews = userReviewsList.size();

        if (totalReviews > 0) {
            float puntajeSumatoria = 0;
            for (UserReview review: userReviewsList) {
                puntajeSumatoria += Float.parseFloat(review.getPuntaje());
            }
            float result = puntajeSumatoria / totalReviews;
            rtgEstablecimiento.setRating(result);
            txtReseñaEstablecimiento.setVisibility(View.GONE);
        } else {
            rtgEstablecimiento.setVisibility(View.GONE);
            txtReseñaEstablecimiento.setText(R.string.reseñas_no + " " + R.string.reseñas_seelprimero);
            txtReseñaEstablecimiento.setVisibility(View.VISIBLE);
        }
    }


    public void goToUploadPhoto(View view) {
        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("establecimientoId", mainEstablecimiento.getEstablecimientoID());
        startActivity(intent);
    }

    public void goToAgregarReview(View v)
    {
        Intent intent = AgregarReviewActivity.makeIntent(this);
        intent.putExtra("establecimientoNombre", mainEstablecimiento.getNombre());
        intent.putExtra("establecimientoId", mainEstablecimiento.getEstablecimientoID());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_establecimiento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return InformationFragment.newInstance(mainEstablecimiento);
                case 1:
                    return FoodsFragment.newInstance(mainEstablecimiento);
                case 2:
                    return ReviewFragment.newInstance(mainEstablecimiento);
                default:
                    return InformationFragment.newInstance(mainEstablecimiento);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
