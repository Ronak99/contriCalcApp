package com.contricalcapp.www.contricalc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.contricalcapp.www.contricalc.models.SetupNavDrawer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CalculateResult extends AppCompatActivity {

    private static final String TAG = "CalculateResult";

    DataClass data;
    ArrayList<String> nameList;
    ArrayList<Integer> amtList;

    LinearLayout resolveContriContainer;
    LinearLayout owesContainer;

    LayoutCreater creater;

    TextView avgValue;
    TextView noOfPeopleValue;
    TextView totalContributedAmountvalue;
    TextView reportLink;

    private DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    SetupNavDrawer setupNavDrawer;


    private Context mContext;

    int noOfPeople;
    int totalPrice;
    int avg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        Intent intent = getIntent();
        data = intent.getParcelableExtra(getString(R.string.intentKey));
        nameList = intent.getStringArrayListExtra(getString(R.string.personNameArraylist));
        amtList = intent.getIntegerArrayListExtra(getString(R.string.personAmtArraylist));

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView = findViewById(R.id.nav_view);

        resolveContriContainer = findViewById(R.id.resolveContriContainer);
        owesContainer = findViewById(R.id.owesLinearLayoutContainer);

        mContext = CalculateResult.this;

        creater = new LayoutCreater();

        noOfPeople = data.contributers;
        totalPrice = data.totalPrice;
        avg = (totalPrice / noOfPeople) ;

        avgValue = findViewById(R.id.tvAverageValue);
        totalContributedAmountvalue = findViewById(R.id.tvContributedAmount);
        noOfPeopleValue = findViewById(R.id.tvNoOfPeople);
        reportLink = findViewById(R.id.tvReportBug);

        //thats how u set an integer value over textview
        avgValue.setText(String.valueOf(avg));
        totalContributedAmountvalue.setText(String.valueOf(totalPrice));
        noOfPeopleValue.setText(String.valueOf(noOfPeople));

        init();
        drawerFunctions();

    }

    public void init(){

        Log.d(TAG, "init: number of contributers " + data.contributers + " data total price " + data.totalPrice);

        reportLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportBug();
            }
        });

        resolveContri();
        whoOwesWhom();


    }


    public void whoOwesWhom(){

        for(int i=0; i<noOfPeople; i++){

            String iPersonName = nameList.get(i);
            int iPersonAmt = amtList.get(i);

            int iLoss = 0;
            int iGain = 0;

            if(iPersonAmt > avg){
                //i has paid more than the average and therefore he is in loss
                iLoss = (iPersonAmt - avg);
                iGain = 0;

            }

            if(avg > iPersonAmt){
                //if iPerson has paid lesser than avg then he has gained
                iGain = (avg - iPersonAmt);
                iLoss = 0;

            }

            for(int j = i+1; j<noOfPeople; j++){
              //  endJLoop(i,j,avg, iPersonAmt, iPersonName, iGain, iLoss);

                int jLoss = 0;
                int jGain = 0;
                String jPersonName = nameList.get(j);
                int jPersonAmt = amtList.get(j);

                if (jPersonAmt > avg) {
                    jLoss = (jPersonAmt - avg);
                    jGain = 0;

                }
                if (jPersonAmt < avg) {
                    jGain = (avg - jPersonAmt);
                    jLoss = 0;
                }

                if (iGain > 0 && jLoss > 0) {
                    //if i is gained, then we want to search when j is lost
                    //since i is gained and j is lost
                    //therefore i should pay money to j
                    int amountToGive = 0;

                    if (iGain < jLoss) {
                        amountToGive = iGain;
                        iGain = iGain - amountToGive;
                        jLoss = (jLoss - amountToGive);

                    }
                    if (iGain > jLoss) {
                        amountToGive = jLoss;
                        iGain = (iGain - jLoss);
                        jLoss = 0;
                    }
                    if (iGain == jLoss) {
                        amountToGive = jLoss;
                        iLoss = iLoss - amountToGive;
                        jGain = jGain - amountToGive;
                    }

                    payStatement(iPersonName, amountToGive, jPersonName);
                    //update amtlist for i and j
                    amtList.set(i,(iPersonAmt + amountToGive));
                    amtList.set(j,(jPersonAmt - amountToGive));

                    if(iGain == 0){
                        break;
                    }

                }//endif

                if(iLoss > 0 && jGain > 0){
                    //when i is in the loss and j is gained
                    //therefore i should pay money to j
                    int amountToGive = 0;
                    if(iLoss > jGain){
                        amountToGive = jGain;
                        //since jGain cant give more than that
                        iLoss = (iLoss - jGain);
                        jGain = 0;


                    }
                    if(jGain > iLoss){
                        //since jgain is greater than iLoss, which means
                        //iLoss can be recieved from jGain
                        amountToGive = iLoss;
                        //now that the amount is given
                        //substract the amountGiven from jGain
                        jGain = (jGain - amountToGive);

                        iLoss = 0;

                    }
                    if(jGain == iLoss){
                        amountToGive = iLoss;
                        iLoss = iLoss - amountToGive;
                        jGain = jGain - amountToGive;
                    }
                    payStatement(jPersonName,amountToGive,iPersonName);
                    amtList.set(i,(iPersonAmt - amountToGive));
                    amtList.set(j,(jPersonAmt + amountToGive));

                    if(iLoss == 0){
                        break;
                    }

                    if(iLoss==0 && jLoss==0){

                    }if(iGain ==0 && jGain ==0){

                    }

                }


            }

        }//end for


    }//end whooweswhom


    public void endJLoop(int i,int j,int avg, int iPersonAmt, String iPersonName, int iGain, int iLoss) {
    }

    public void resolveContri(){

        for(int i=0; i<noOfPeople; i++){

            String iPersonName = nameList.get(i);
            int iPersonAmt = amtList.get(i);

            int iGain = 0;
            int iLoss = 0;

            //if the amount paid by the person is lesser than the average
            //which means that he is in gain
            if(iPersonAmt < avg){
                iGain = (avg - iPersonAmt);
                //iLoss = 0;

                payStatement(iPersonName, iGain);
            }

            if(iPersonAmt > avg){
                iLoss = (iPersonAmt - avg);
               // iGain = 0;

                receiveStatement(iPersonName, iLoss);
            }

            if(iPersonAmt == avg){
                //nothing is to be done in this case
                equalStatement(iPersonName);
            }

        }

    }

    public void payStatement(String iPersonName, int amount, String jPersonName){
        //payment statement of whooweswhom
        Log.d(TAG, "payStatement: " + iPersonName + " has to pay " + amount + " to " + jPersonName);

        createWidgets(iPersonName, String.valueOf(amount), " has to pay ", jPersonName);

    }

    public void payStatement(String iPersonName, int amount){

        //payment statement of resolvecontri
        Log.d(TAG, "payStatement: " + iPersonName + " has to pay " + amount + " rs " );
        //create a dynamic textview and write a statement over it
        //create owes conitainer widgets
        createWidgets(iPersonName, String.valueOf(amount), " has to pay ");

    }

    public void equalStatement(String iPersonName){

        createWidgets(iPersonName, null, "'s contributions are settled ");

    }

    public void receiveStatement(String iPersonName, int amount){

        Log.d(TAG, "receiveStatement: " + iPersonName + " has to receive " + amount + " rs " );

        createWidgets(iPersonName, String.valueOf(amount), " has to receive ");

    }

    public void createWidgets(String nameVal, String amt, String connectiveText){

        //creating the parent linear layout
        LinearLayout parentLayout = new LinearLayout(mContext);
        resolveContriContainer.addView(parentLayout);

        int parentLayoutHeight = LinearLayout.LayoutParams.MATCH_PARENT;
        int parentLayoutWidth = LinearLayout.LayoutParams.MATCH_PARENT;
        int orientation = LinearLayout.HORIZONTAL;
        int gravity = Gravity.CENTER_HORIZONTAL;

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (18*scale + 0.5f);

        TextView personName = new TextView(mContext);
        TextView connective = new TextView(mContext);
        TextView amountToGive = new TextView(mContext);

        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;

        creater.createLinearLayout(parentLayout,padding,parentLayoutWidth,parentLayoutHeight,orientation,gravity);

        creater.createTextView(personName, width, height, Typeface.BOLD, 18, nameVal);
        creater.createTextView(connective, width, height, Typeface.NORMAL, 18, connectiveText);
        creater.createTextView(amountToGive, width, height, Typeface.BOLD, 18, amt);

        parentLayout.addView(personName);
        parentLayout.addView(connective);
        parentLayout.addView(amountToGive);
        //creating a textview


    }

    public void createWidgets(String fromPersonName, String amt,String connectiveText, String toPersonName){

        //creating the parent linear layout
        //create widgets for whooweswhom
        LinearLayout parentLayout = new LinearLayout(mContext);
        owesContainer.addView(parentLayout);

        int parentLayoutHeight = LinearLayout.LayoutParams.MATCH_PARENT;
        int parentLayoutWidth = LinearLayout.LayoutParams.MATCH_PARENT;
        int orientation = LinearLayout.HORIZONTAL;
        int gravity = Gravity.CENTER_HORIZONTAL;

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (18*scale + 0.5f);

        TextView tvFromPersonName = new TextView(mContext);
        TextView connective = new TextView(mContext);
        TextView amountToGive = new TextView(mContext);
        TextView connective_two = new TextView(mContext);
        TextView tvToPersonName = new TextView(mContext);

        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;

        creater.createLinearLayout(parentLayout,padding,parentLayoutWidth,parentLayoutHeight,orientation,gravity);

        creater.createTextView(tvFromPersonName, width, height, Typeface.BOLD, 18, fromPersonName);
        creater.createTextView(connective, width, height, Typeface.NORMAL, 18, connectiveText);
        creater.createTextView(amountToGive, width, height, Typeface.BOLD, 18, amt);
        creater.createTextView(connective_two, width, height, Typeface.NORMAL, 18, " to ");
        creater.createTextView(tvToPersonName, width, height, Typeface.BOLD, 18, toPersonName);

        parentLayout.addView(tvFromPersonName);
        parentLayout.addView(connective);
        parentLayout.addView(amountToGive);
        parentLayout.addView(connective_two);
        parentLayout.addView(tvToPersonName);
        //creating a textview


    }


    public void reportBug(){


        Toast.makeText(mContext, "Make sure to include screenshots of the screen which showed inaccuracy or a bug", Toast.LENGTH_LONG).show();

        Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","punase.ronak99@gmail.com", null));
        feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug report for ContriCalc App");

        startActivity(Intent.createChooser(feedbackIntent, "Report a bug..."));


    }


    public void drawerFunctions(){

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(false);

                        switch (menuItem.getItemId()){

                            case R.id.nav_share:
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TITLE, "Download contri calc");
                                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.appDescription));
                                intent.setType("text/plain");
                                Intent chooserIntent = Intent.createChooser(intent, "Share the app");
                                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chooserIntent);
                                break;

                            case R.id.nav_feedback:
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto","punase.ronak99@gmail.com", null));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for ContriCalc App");

                                startActivity(Intent.createChooser(emailIntent, "Provide feedback..."));

                                break;

                            case R.id.nav_report:

                                reportBug();
                                break;



                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


    }

}
