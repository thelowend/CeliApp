package celiacos.seminarioii.prototipo.google.com.celiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.AdapterReview;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;


public class ReviewFragment extends Fragment {

    Establecimiento mainEstablecimiento;
    private DatabaseReference mDatabaseRef;
    private ArrayList<UserReview> userReviewsList;
    RatingBar ratingGral;
    ListView lstUserReviews;
    int contador;
    float punt;


    public ReviewFragment() {}

    public static ReviewFragment newInstance(Establecimiento es) {
        ReviewFragment fragment = new ReviewFragment();

        Bundle args = new Bundle();
        args.putSerializable("ESTABLECIMIENTO", es);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_establecimiento_review, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainEstablecimiento = (Establecimiento) getArguments().getSerializable("ESTABLECIMIENTO");

        userReviewsList = mainEstablecimiento.getReviews();
        lstUserReviews = getActivity().findViewById(R.id.lstUserReviews);
        ratingGral = getActivity().findViewById(R.id.rtgCriterio2);
        final Fragment frag = this;

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Establecimientos").child(mainEstablecimiento.getEstablecimientoID()).child("userReviews");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userReviewsList.clear();
                contador = 0;
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    UserReview userReview = new UserReview(child);
                    userReviewsList.add(userReview);
                    contador = contador + 1;
                    punt = punt + Float.parseFloat(userReview.getPuntaje());
                }
                ratingGral.setRating(punt / contador);
                ListAdapter adapter = new AdapterReview(frag.getActivity(), userReviewsList);
                lstUserReviews.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ListAdapter adapter = new AdapterReview(this.getActivity(), userReviewsList);
        lstUserReviews.setAdapter(adapter);

    }
}
