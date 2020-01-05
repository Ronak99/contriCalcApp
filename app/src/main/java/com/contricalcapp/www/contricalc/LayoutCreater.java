package com.contricalcapp.www.contricalc;

import android.graphics.ColorSpace;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LayoutCreater {

    private static final String TAG = "LayoutCreater";

    public LayoutCreater() {
    }


    public void createLinearLayout(LinearLayout parentLayout, int padding, int width, int height, int orientation, int gravity) {

        LinearLayout.LayoutParams parentDimensions = new LinearLayout.LayoutParams(width,height);

        parentLayout.setPadding(padding, padding, padding, padding);
        parentLayout.setLayoutParams(parentDimensions);
        parentLayout.setOrientation(orientation);
        parentLayout.setGravity(gravity);

    }

    public  void createTextView(TextView textView, int width, int height, int style, float textSize, String tvVal){
        Log.d(TAG, "createTextView: ye to challa hai");
        LinearLayout.LayoutParams tvDimensions = new LinearLayout.LayoutParams(width,height);

        textView.setLayoutParams(tvDimensions);
        textView.setTypeface(null,style);
        textView.setTextSize(textSize);
        textView.setText(tvVal);

    }
}
