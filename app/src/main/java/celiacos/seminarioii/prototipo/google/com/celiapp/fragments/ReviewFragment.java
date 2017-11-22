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

    RatingBar rtgGeneral;
    TextView txtNombreResto;
    TextView txtCantidadReviews;
    ListView lstUserReviews;
    ImageView imgView;
    Button btnSiguiente;

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

        lstUserReviews = getActivity().findViewById(R.id.lstUserReviews);
        rtgGeneral = getActivity().findViewById(R.id.rtgGeneral);
        txtNombreResto = getActivity().findViewById(R.id.txtNombreResto);
        txtCantidadReviews = getActivity().findViewById(R.id.txtCantidadReviews);

        ArrayList<UserReview> userReviewsList = mainEstablecimiento.getReviews();

        ListAdapter adapter = new AdapterReview(this.getActivity(), userReviewsList);
        lstUserReviews.setAdapter(adapter);

        float puntajeSumatoria = 0;
        int totalReviews = userReviewsList.size();
        for (UserReview review: userReviewsList) {
            puntajeSumatoria += Float.parseFloat(review.getPuntaje());
        }

        //Muestro datos
        txtNombreResto.setText(mainEstablecimiento.getNombre());
        if (userReviewsList.size() != 0) {
            if(userReviewsList.size() == 1)
                txtCantidadReviews.setText(String.valueOf(totalReviews) + " reseña");
            else
                txtCantidadReviews.setText(String.valueOf(totalReviews) + " reseñas");
            rtgGeneral.setRating(puntajeSumatoria / userReviewsList.size());
        }

    }
}
