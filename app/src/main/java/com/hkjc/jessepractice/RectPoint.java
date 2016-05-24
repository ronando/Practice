package com.hkjc.jessepractice;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jesse on 30/12/15.
 */
public class RectPoint implements Parcelable ,Serializable{
    private int x;
    private int y;
    private int z;

    public RectPoint(){

    }

    protected RectPoint(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<RectPoint> CREATOR = new Creator<RectPoint>() {
        @Override
        public RectPoint createFromParcel(Parcel in) {
            return new RectPoint(in);
        }

        @Override
        public RectPoint[] newArray(int size) {
            return new RectPoint[size];
        }
    };


    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(z);
    }

    public void readFromParcel(Parcel in){
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    public void setRect(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

}