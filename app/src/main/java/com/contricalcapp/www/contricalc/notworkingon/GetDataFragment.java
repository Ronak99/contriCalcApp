package com.contricalcapp.www.contricalc.notworkingon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.contricalcapp.www.contricalc.R;

import java.util.ArrayList;

public class GetDataFragment extends Fragment {

    private static final String TAG = "GetDataFragment";


    public interface OnNextClickedListener{

        public void onNextClicked(int priceOfService, int noOfPeople, Context mContext);

    }

    OnNextClickedListener mOnNextClickedListener;


    EditText etPriceOfService;
    EditText etNumberOfPeople;

    Button nextButton;

    CardView cardViewLayout;

    LinearLayout peopleContainerLayout;

    String numberOfPeople_STRING;
    String priceOfService_STRING;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_getdata, container, false);


        etPriceOfService = view.findViewById(R.id.etAmount);
        etNumberOfPeople = view.findViewById(R.id.etNumberOfPeople);

        nextButton = view.findViewById(R.id.next);

        cardViewLayout = view.findViewById(R.id.cardViewLayout);

        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnNextClickedListener){
            mOnNextClickedListener = (OnNextClickedListener) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement OnNextClickedListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnNextClickedListener = null;
    }

    private void init(){

        Log.d(TAG, "init: function is called");
        //next button functionality

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: next button is clicked");

                numberOfPeople_STRING = etNumberOfPeople.getText().toString();
                priceOfService_STRING = etPriceOfService.getText().toString();

                if(numberOfPeople_STRING.matches("") || priceOfService_STRING.matches("")){
                    Toast.makeText(getActivity(), "None of the fields can be left empty", Toast.LENGTH_SHORT).show();
                }else{
                    int priceOfService = Integer.parseInt(etPriceOfService.getText().toString());
                    int noOfPeople = Integer.parseInt(etNumberOfPeople.getText().toString());

                    mOnNextClickedListener.onNextClicked(priceOfService, noOfPeople, getActivity());
                }


            }
        });

    }



}
