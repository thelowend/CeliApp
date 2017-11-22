package celiacos.seminarioii.prototipo.google.com.celiapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import celiacos.seminarioii.prototipo.google.com.celiapp.R;
import celiacos.seminarioii.prototipo.google.com.celiapp.establecimiento.entities.Establecimiento;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.AdapterReview;
import celiacos.seminarioii.prototipo.google.com.celiapp.reviews.entitites.UserReview;


public class InformationFragment extends Fragment {

    Establecimiento mainEstablecimiento;

    public InformationFragment() {}

    public static InformationFragment newInstance(Establecimiento es) {
        InformationFragment fragment = new InformationFragment();

        Bundle args = new Bundle();
        args.putSerializable("ESTABLECIMIENTO", es);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_establecimiento_information, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainEstablecimiento = (Establecimiento) getArguments().getSerializable("ESTABLECIMIENTO");

        TextView txtRating = getActivity().findViewById(R.id.txtRating);

        TextView txtDireccion = getActivity().findViewById(R.id.txtDireccion);
        TextView txtHorario = getActivity().findViewById(R.id.txtHorario);
        TextView txtTelefono = getActivity().findViewById(R.id.txtTelefono);

        RatingBar rtgGeneral = getActivity().findViewById(R.id.rtgGeneral);

        ArrayList<UserReview> userReviewsList = mainEstablecimiento.getReviews();

        float puntajeSumatoria = 0;
        int totalReviews = userReviewsList.size();
        for (UserReview review: userReviewsList) {
            puntajeSumatoria += Float.parseFloat(review.getPuntaje());
        }
        float result = puntajeSumatoria / userReviewsList.size();

        //Muestro datos
        txtRating.setText(String.format("%.01f", result) + " / 5");
        txtDireccion.setText(mainEstablecimiento.getDireccion());
        txtHorario.setText(mainEstablecimiento.getHorario());
        txtTelefono.setText(mainEstablecimiento.getTelefono());

        rtgGeneral.setRating(result);

        ImageButton imgbtnEscribirOpinion = this.getActivity().findViewById(R.id.imgbtnEscribirOpinion);
        //imgbtnEscribirOpinion


    }
}
