package com.contricalcapp.www.contricalc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.contricalcapp.www.contricalc.models.SetupNavDrawer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etPriceOfService;
    EditText etNumberOfPeople;

    Button nextButton;
    Button calculateButton;
    ImageButton goBackButton;

    LinearLayout peopleContainerLayout;

    RelativeLayout relLayout2;
    RelativeLayout relLayout3;

    ArrayList<EditText> personAmtsWidgetList = new ArrayList<>();
    ArrayList<EditText> personNameWidgetList = new ArrayList<>();

    String numberOfPeople_STRING;
    String  priceOfService_STRING;

    ArrayList<String> personNameValueList = new ArrayList<>();
    ArrayList<Integer> personAmtValueList = new ArrayList<>();

    private DrawerLayout mDrawerLayout;


    int noOfPeople;
    int priceOfService;
    int totalAmountContributed = 0;

    int PERSONNAME = 0;
    int PERSONAMT = 1;

    public boolean SECONDVIEWHIDDEN = true;

    DataClass dataClass;

    private Context mContext = MainActivity.this;

    private static final String TAG = "MainActivity";

    private FrameLayout fragmentContainer;

    NavigationView navigationView;
    SetupNavDrawer setupNavDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPriceOfService = findViewById(R.id.etAmount);
        etNumberOfPeople = findViewById(R.id.etNumberOfPeople);

        nextButton = findViewById(R.id.next);
        calculateButton = findViewById(R.id.calculateBtn);
        goBackButton = findViewById(R.id.goBackButton);

        relLayout2 = findViewById(R.id.relLayout2);
        relLayout3 = findViewById(R.id.relLayout3);

        peopleContainerLayout = findViewById(R.id.peopleContainerLayout);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

         navigationView = findViewById(R.id.nav_view);

         setupNavDrawer = new SetupNavDrawer();

        setupNextButtonClick();
        goBackButtonClick();
        setupCalculateButtonListener();

        drawerFunctions();

        CalculatorFragment fragment = new CalculatorFragment();
        FrameLayout calculatorContainer = findViewById(R.id.calculatorContainer);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.calculatorContainer, fragment);
        transaction.commit();

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

                                    Toast.makeText(mContext, "Make sure to include screenshots of the screen which showed inaccuracy or a bug", Toast.LENGTH_LONG).show();

                                    Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                            "mailto","punase.ronak99@gmail.com", null));
                                    feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, "Bug report for ContriCalc App");

                                    startActivity(Intent.createChooser(feedbackIntent, "Report a bug..."));
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

    public void hideViews(){
        SECONDVIEWHIDDEN = true;

        relLayout2.setVisibility(View.VISIBLE);
        relLayout3.setVisibility(View.GONE); //second view is hidden

        //remove everything from both widget lists
        for(int i = (noOfPeople - 1); i>=0; i--){
            personAmtsWidgetList.remove(i);
            personNameWidgetList.remove(i);
        }

        peopleContainerLayout.removeAllViews();
    }

    public void showViews(){
        SECONDVIEWHIDDEN = false;
        relLayout2.setVisibility(View.GONE);
        relLayout3.setVisibility(View.VISIBLE); //second view is showm
    }

    public void goBackButtonClick(){

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideViews();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(!SECONDVIEWHIDDEN){
            hideViews();
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void setupNextButtonClick(){
        Log.d(TAG, "init: function is called");
        //next button functionality

        nextButton.setOnClickListener(new View.OnClickListener() {//just a comment in

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: next button is clicked");
                numberOfPeople_STRING = etNumberOfPeople.getText().toString();
                priceOfService_STRING = etPriceOfService.getText().toString();

                if(!personNameValueList.isEmpty() && !personAmtValueList.isEmpty()){
                    clearArrayLists(0, (noOfPeople - 1));
                }

                if(numberOfPeople_STRING.matches("") || priceOfService_STRING.matches("")){
                    Toast.makeText(mContext, "None of the fields can be left empty", Toast.LENGTH_SHORT).show();
                }
                else if(numberOfPeople_STRING.matches("0") || priceOfService_STRING.matches("0")){
                    Toast.makeText(mContext, "0 as a value is not accepted in either of the fields", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(numberOfPeople_STRING) > 35){
                    //check to make sure that only 35 number of people are allowed
                    Toast.makeText(mContext, "Value more than 35 is not allowed", Toast.LENGTH_LONG).show();
                }
                else{

                    priceOfService = Integer.parseInt(priceOfService_STRING);
                    noOfPeople = Integer.parseInt(numberOfPeople_STRING);

                    showViews();
                    createDynamicEditText();

                }


            }
        });
    }

    private void createDynamicEditText(){
        Log.d(TAG, "createDynamicEditText: trying to add editexts " + priceOfService + ", " + noOfPeople);

        for(int i=0; i<noOfPeople; i++){
            Log.d(TAG, "createDynamicEditText: so this is textview number " + i);

            int personNameID = PERSONNAME+i;
            int personAmtID = PERSONAMT+i;

            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams (
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3.0f
            );

            //setting up the linear layout
            LinearLayout layout = new LinearLayout(mContext);
            layout.setLayoutParams(linearLayoutParams);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.setWeightSum(5);

            LinearLayout.LayoutParams paramPersonName = new LinearLayout.LayoutParams (
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    2.0f
            );

            LinearLayout.LayoutParams paramPersonAmt = new LinearLayout.LayoutParams (
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    3.0f
            );

            paramPersonName.setMargins(5,10,0,10);
            paramPersonAmt.setMargins(-5,10,0,10);

            float scale = getResources().getDisplayMetrics().density;
            int padding = (int) (15*scale + 0.5f);

            //setting up person name edittext
            EditText etPersonName = new EditText(mContext);
            etPersonName.setPadding(padding, padding, padding, padding);
            etPersonName.setLayoutParams(paramPersonName);
            etPersonName.setId(personNameID);
            etPersonName.setHint("Name");
            etPersonName.setSingleLine(true);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                etPersonName.setBackgroundResource(R.drawable.input_name_styles);
            }

            etPersonName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
            personNameWidgetList.add(etPersonName);
            layout.addView(etPersonName);


            EditText etPersonAmt = new EditText(mContext);
            etPersonAmt.setPadding(padding, padding, padding, padding);
            etPersonAmt.setLayoutParams(paramPersonAmt);
            etPersonAmt.setId(personAmtID);
            etPersonAmt.setHint("amt conributed");
            etPersonAmt.setInputType(InputType.TYPE_CLASS_NUMBER);
            etPersonAmt.setSingleLine(true);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
                etPersonAmt.setBackgroundResource(R.drawable.input_amt_styles);
            }

            etPersonAmt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(5)});
            personAmtsWidgetList.add(etPersonAmt);
            layout.addView(etPersonAmt);

            peopleContainerLayout.addView(layout);


        }


    }

    public void clearArrayLists(int startingIndex, int lastIndex){

        if(lastIndex > 0){
            for(int i = lastIndex; i>=startingIndex; i--){
                personNameValueList.remove(i);
                personAmtValueList.remove(i);
            }
        }
        else{
            Toast.makeText(mContext,"No field can be left empty", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean populateArrayLists(){
        String nameVal;
        String amtVal;
        for(int i=0; i<noOfPeople; i++) {

            nameVal = personNameWidgetList.get(i).getText().toString();
            amtVal = personAmtsWidgetList.get(i).getText().toString();

            if (nameVal.matches("") || amtVal.matches("")) {
                clearArrayLists(0, (i-1));
                return false;
            } else {
                personNameValueList.add(nameVal);
                personAmtValueList.add(Integer.parseInt(amtVal));
            }
        }
        return true;
    }

    public void setupCalculateButtonListener(){

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataClass = new DataClass(noOfPeople, priceOfService);

                if(!personNameValueList.isEmpty() && !personAmtValueList.isEmpty()){
                    clearArrayLists(0, (noOfPeople - 1));
                }
                 //if those lists are not empty
                    if(populateArrayLists()){

                        if(priceAmountNotExceeded() == 0){

                            Intent intent = new Intent(mContext, CalculateResult.class);

                            intent.putExtra(getString(R.string.intentKey), dataClass);
                            intent.putStringArrayListExtra(getString(R.string.personNameArraylist), personNameValueList);
                            intent.putIntegerArrayListExtra(getString(R.string.personAmtArraylist), personAmtValueList);

                            startActivity(intent);

                        }


                        else if(priceAmountNotExceeded() == 1){
                            Toast.makeText(mContext, "You cannot have paid more than the service amount "+priceOfService+", Wrong input", Toast.LENGTH_SHORT).show();
                        }
                        else if(priceAmountNotExceeded() == -1){
                            Toast.makeText(mContext, "You cannot have paid less than the service amount "+priceOfService+",Wrong input", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(mContext, "Enter valid values", Toast.LENGTH_SHORT).show();
                    }





            }//end click
        });
    }

    private int priceAmountNotExceeded(){
        totalAmountContributed = 0;
        for(int i=0; i<noOfPeople; i++){

        totalAmountContributed += Integer.parseInt(personAmtsWidgetList.get(i).getText().toString());

        }//end for
        Log.d(TAG, "priceAmountNotExceeded: total amount contributed " + totalAmountContributed);
        if(totalAmountContributed > priceOfService){
            return 1;
        }
        if(totalAmountContributed < priceOfService){
            return -1;
        }
        return 0;
    }


}
