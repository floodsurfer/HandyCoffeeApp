
package com.lipata.handycoffeeapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.DecimalFormat;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BrewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mGroundCoffee";
    private static final String ARG_PARAM2 = "param2";

    DecimalFormat mDecimalFormat = new DecimalFormat("#.0");
    View mFragmentView;
    NumberPicker mNumberPicker;
    String[] mRange = new String[21];
   // float mGroundCoffee = 20.0f;   // Unused
    final String LOG_TAG = "CoffeeApp-Brew";
    float mCoffeeStrengthCoefficient;

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
     * @return A new instance of fragment BrewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrewFragment newInstance(String param1, String param2) {
        BrewFragment fragment = new BrewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BrewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.v(LOG_TAG, mParam1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_brew, container, false);

        for (int i=0; i<mRange.length; i++) {
            mRange[i]="-";
        }

        mNumberPicker = initializeNumberPicker(mFragmentView);
        return mFragmentView;
    }

    public NumberPicker initializeNumberPicker(View v){

        //setNumberPickerRange(); //Helper method to set up array mRange based on mGroundCoffee value

        //Create NumberPicker with mRange array
        NumberPicker np= (NumberPicker) v.findViewById(R.id.number_picker);
        np.setMaxValue(mRange.length - 1);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(mRange);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setValue(mRange.length / 2);

        np.setOnScrollListener(new NumberPicker.OnScrollListener() {

            private int npPosition;  //You need to init this value.

            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {

                    //if statement to verify that the NumberPicker has been populated with numbers before attempting to do any math
                   // String numberPickerDisplayedValue = numberPicker.getDisplayedValues()[0];

                    if(!numberPicker.getDisplayedValues()[0].equals("-")) {
                        //Update npPosition to the new value for the next scroll
                        npPosition = numberPicker.getValue(); // This gives you the POSITION of the NumberPicker
                        Log.d(LOG_TAG, "npPosition = " + Integer.toString(npPosition));

                        float groundCoffeeFloat = Float.parseFloat(numberPicker.getDisplayedValues()[npPosition]);
                        Log.d(LOG_TAG, "npPosition value in mRange = " + Float.toString(groundCoffeeFloat));

                        //Update TextView to show how much water is needed
                        float waterNeeded;
                        mCoffeeStrengthCoefficient = ((MainActivity) getActivity()).getCoffeeStrengthCoefficient();
                        waterNeeded = groundCoffeeFloat * mCoffeeStrengthCoefficient;
                        Log.d(LOG_TAG, "mCoffeeStrengthCoefficient = " + mCoffeeStrengthCoefficient);
                        Log.d(LOG_TAG, "waterNeeded = " + Float.toString(waterNeeded));

                        final TextView waterNeededTextView = (TextView) mFragmentView.findViewById(R.id.water_needed);
                        waterNeededTextView.setText(mDecimalFormat.format(waterNeeded));
                    }
                }
            }
        });

        return np;
    }

    /*public void setNumberPickerRange(){

        //The member array mRange will be populated with values -10 and +10 places from mGroundCoffee in 0.1 increments
        for (int i=0; i< mRange.length; i++ ){
            float startPoint= mGroundCoffee -1;
            mRange[i]=Float.toString(startPoint+((float) i/10));
            Log.d(LOG_TAG, "mRange "+Integer.toString(i)+" "+mRange[i]);   //TODO remove for production
        }

    }*/

/*
    //Update Number Picker for new groundCoffee value
    public void updateGroundCoffee(String data){

        Log.d(LOG_TAG, "Method called: updateGroundCoffee() " + data);
        mGroundCoffee = Float.parseFloat(data); //Update mGroundCoffee
        setNumberPickerRange(); //Update range array with new mGroundCoffee
        mNumberPicker.setDisplayedValues(mRange);


    }

    public NumberPicker getNumberPicker(){
        return mNumberPicker;
    }

*/
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

    public void updateCoffeeStrengthCoefficient(float coffeeStrength){
        Log.d(LOG_TAG, "updateCoffeeStrengthCoefficient() called");
        mCoffeeStrengthCoefficient = coffeeStrength;
        Log.d(LOG_TAG, "Updated mCoffeeStrengthCoefficient = " + mCoffeeStrengthCoefficient);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
