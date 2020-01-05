package com.contricalcapp.www.contricalc.notworkingon;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.FrameLayout;

import com.contricalcapp.www.contricalc.R;


public class NotMainActivity extends AppCompatActivity implements GetDataFragment.OnNextClickedListener {

    private static final String TAG = "MainActivity";

    private FrameLayout fragmentContainer;


    @Override
    public void onNextClicked(int priceOfService, int noOfPeople, Context mContext) {

        Log.d(TAG, "onNextClicked: toggling the fragment");

        //create an object of the fragment that you want to go to
        AfterNextFragment afterNextFragment = new AfterNextFragment();
        afterNextFragment.init(priceOfService, noOfPeople, mContext);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, afterNextFragment);
        transaction.addToBackStack(getString(R.string.after_next_fragment));
        transaction.commit();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_activity_main);

        fragmentContainer = findViewById(R.id.fragment_container);

        GetDataFragment getDataFragment = new GetDataFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, getDataFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
