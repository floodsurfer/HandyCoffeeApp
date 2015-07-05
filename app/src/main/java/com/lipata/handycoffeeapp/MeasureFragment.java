package com.lipata.handycoffeeapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeasureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeasureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasureFragment extends Fragment {

    float COFFEE_STRENGTH_COEFFICIENT = 17.0f; //TODO this should be user definable

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeasureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeasureFragment newInstance(String param1, String param2) {
        MeasureFragment fragment = new MeasureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MeasureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_measure, container, false);

        //Button click listeners
        Button buttonMetric = (Button) fragmentView.findViewById(R.id.button_click_metric);

        //Retired function
        //Button buttonOz = (Button) fragmentView.findViewById(R.id.button_click_oz);

        buttonMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCoffeeAmount(v);
            }
        });

        /*  Retired function
        buttonOz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage_oz(v);
            }
        });  */

        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    //Button event handlers
    public void calculateCoffeeAmount(View view) {
        final EditText myEditField = (EditText) getView().findViewById(R.id.finalVolMetric);
        String x = myEditField.getText().toString();

        if(!x.equals("")) {         //if statement to catch blank user input
            float coffee = Float.parseFloat(x) / COFFEE_STRENGTH_COEFFICIENT;
            DecimalFormat decimalFormat = new DecimalFormat("0.#");
            final TextView myTextView = (TextView) getView().findViewById(R.id.youWillNeed_metric);
            myTextView.setText("You'll need " + decimalFormat.format(coffee) + " grams of coffee grounds");
            mListener.onFragmentInteraction(Uri.parse(decimalFormat.format(coffee))); //Updates NumberPicker in BrewFragment
        }
    }

    /*  Retired function
    public void sendMessage_oz(View view) {
        // Do something in response to button
        final EditText myEditField = (EditText) getView().findViewById(R.id.finalVolOz);
        String x = myEditField.getText().toString();
        int intOfx = Integer.parseInt(x);
        float coffee = ( (float) intOfx/4)*7;
        final TextView myTextView = (TextView) getView().findViewById(R.id.youWillNeed_oz);
        myTextView.setText("You'll need "+coffee+" grams of coffee grounds");

    }*/

}
