package com.contricalcapp.www.contricalc;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.util.ArrayList;

public class DataClass implements Parcelable {

    int contributers;
    int totalPrice;


    public DataClass(){}

    public DataClass(int contributers, int totalPrice) {
        this.contributers = contributers;
        this.totalPrice = totalPrice;
    }

    protected DataClass(Parcel in) {
        contributers = in.readInt();
        totalPrice = in.readInt();
    }

    public static final Creator<DataClass> CREATOR = new Creator<DataClass>() {
        @Override
        public DataClass createFromParcel(Parcel in) {
            return new DataClass(in);
        }

        @Override
        public DataClass[] newArray(int size) {
            return new DataClass[size];
        }
    };

    public int getContributers() {
        return contributers;
    }

    public void setContributers(int contributers) {
        this.contributers = contributers;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(contributers);
        parcel.writeInt(totalPrice);
    }
}
