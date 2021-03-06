// IToastAIDLInterface.aidl
package com.hkjc.jessepractice;
import com.hkjc.jessepractice.RectPoint;


// Declare any non-default types here with import statements

interface IToastAIDLInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int getPID(String processTag);

    RectPoint getRectPoint();

}
