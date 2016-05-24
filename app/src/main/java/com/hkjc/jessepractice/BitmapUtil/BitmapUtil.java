package com.hkjc.jessepractice.BitmapUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hkjc.jessepractice.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jesse on 23/11/15.
 */
public class BitmapUtil {


    // compress bitmap ,  lose quality
    public static Bitmap compressBitmap(Bitmap bitmap, long size){
        if(bitmap == null || size <= 0) return bitmap;
        int quality = 100;
        ByteArrayOutputStream osbm = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, osbm);
        while(osbm.size() > size && quality > 50){
            osbm.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, osbm);
            Log.d("Bitmap util", " size :" + osbm.size());
        }
        Bitmap compressedBM = BitmapFactory.decodeByteArray(osbm.toByteArray(), 0, osbm.toByteArray().length);
        return compressedBM;
    }

    //scale bitmap
    public static Bitmap sampleSizeBitmap(Bitmap bitmap, int width, int height){
        if(bitmap == null || width <=0 || height <=0) return bitmap;
        int originalW = bitmap.getWidth();
        int originalH = bitmap.getHeight();
        int scaleH = originalH/height;
        int scaleW = originalW/width;
        int scale = scaleH < scaleW ? scaleH : scaleW;
        if(scale < 1) scale = 1;

        ByteArrayOutputStream osbm = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, osbm);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;

        Bitmap scaledBitmap = BitmapFactory.decodeByteArray(osbm.toByteArray(),0,osbm.size(),options);
        return scaledBitmap;
    }


    public static void saveBitmapToDisk(Bitmap bitmap, String path,String name) throws IOException {
        if(bitmap == null || path == null || name ==null) return;
        File file = new File(path,name);
        if(!file.exists()) file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        fos.close();
    }
}
