package celiacos.seminarioii.prototipo.google.com.celiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.AdapterReview;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;


public class ReviewFragment extends Fragment {

    Establecimiento mainEstablecimiento;

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
        View rootView = inflater.inflate(R.layout.fragment_establecimiento_review, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainEstablecimiento = (Establecimiento) getArguments().getSerializable("ESTABLECIMIENTO");

        ArrayList<UserReview> userReviewsList = mainEstablecimiento.getReviews();
        ListView lstUserReviews = (ListView) getActivity().findViewById(R.id.lstUserReviews);

        ListAdapter adapter = new AdapterReview(this.getActivity(), userReviewsList);
        lstUserReviews.setAdapter(adapter);


    }
}
