package com.contricalcapp.www.contricalc.notworkingon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.contricalcapp.www.contricalc.R;

import java.util.ArrayList;




public class AfterNextFragment extends Fragment {

    int totalAmountContributed = 0;

    int PERSONNAME = 0;
    int PERSONAMT = 1;

    Button calculateButton;
    LinearLayout peopleContainerLayout;

    ArrayList<EditText> personAmtsWidgetList = new ArrayList<>();
    ArrayList<EditText> personNameWidgetList = new ArrayList<>();


    private static final String TAG = "AfterNextFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created the view");
        View view = inflater.inflate(R.layout.fragment_after_next, container, false);





        calculateButton = view.findViewById(R.id.calculateBtn);
        peopleContainerLayout = view.findViewById(R.id.peopleContainerLayout);


        return view;
    }


    public void init(int priceOfService , int noOfPeople, Context mContext){
        Log.d(TAG, "init: called and values in init are " + priceOfService + ", " + noOfPeople);

        createDynamicEditText(priceOfService, noOfPeople, mContext);
    }

    private void createDynamicEditText(int priceOfService, int noOfPeople, Context mContext){
        Log.d(TAG, "createDynamicEditText: trying to add editexts " + priceOfService + ", " + noOfPeople);

        for(int i=0; i<noOfPeople; i++){
            Log.d(TAG, "createDynamicEditText: so this is textview number " + i);

           // int personNameID = PERSONNAME+i;
            //int personAmtID = PERSONAMT+i;

            //setting up the linear layout
            LinearLayout layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setWeightSum(5);

            LinearLayout.LayoutParams paramPersonName = new LinearLayout.LayoutParams (
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2.0f
            );

            LinearLayout.LayoutParams paramPersonAmt = new LinearLayout.LayoutParams (
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3.0f
            );

            paramPersonName.setMargins(0,10,0,10);
            paramPersonAmt.setMargins(0,10,0,10);

            //setting up person name edittext
            EditText etPersonName = new EditText(getActivity());
            etPersonName.setLayoutParams(paramPersonName);
           // etPersonName.setId(personNameID);
            etPersonName.setHint("Name");
            etPersonName.setSingleLine(true);
            //personNameWidgetList.add(etPersonName);
            layout.addView(etPersonName);


            EditText etPersonAmt = new EditText(getActivity());
            etPersonAmt.setLayoutParams(paramPersonAmt);
           // etPersonAmt.setId(personAmtID);
            etPersonAmt.setHint("amt conributed");
            etPersonAmt.setSingleLine(true);
            //personAmtsWidgetList.add(etPersonAmt);
            layout.addView(etPersonAmt);

           // peopleContainerLayout.addView(layout);

        }


    }
/*
    private boolean priceAmountNotExceeded(int priceOfService, int numberOfPeople){

        for(int i=0; i<numberOfPeople; i++){

            totalAmountContributed += Integer.parseInt(personAmtsWidgetList.get(i).getText().toString());

        }//end for

        if(totalAmountContributed > priceOfService){
            return false;
        }

        return true;
    }
*/

}
