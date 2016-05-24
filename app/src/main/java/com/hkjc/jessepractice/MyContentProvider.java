package com.hkjc.jessepractice;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    public static final String TAG = "MyContentProvider";

    private SQLiteDatabase database;
    private MainDatabaseHelper  mDatabaseHelper;
    private static UriMatcher mUriMatcher;

    public static final String AUTHORITIES = "com.hkjc.jessepractice";
    public static final String DB_NAME = "jesse_db";
    public static final int DB_VERSION = 1;
    public static final String TNAME = "table_name";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String SEX = "sex";

    public static final String CONTENT_TYPE = "";
    public static final String CONTEENT_ITEM_TYPE = "";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/" + TNAME);

    public static final int ITEM = 1;
    public static final int ITME_ID = 2;


    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITIES,TNAME,ITEM);
        mUriMatcher.addURI(AUTHORITIES,TNAME + "/#", ITME_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new MainDatabaseHelper(this.getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "object:" + hashCode() + "uri:" + uri + " values:" + values);
        database = mDatabaseHelper.getWritableDatabase();
        if(mUriMatcher.match(uri) != ITEM){
            throw new UnsupportedOperationException("uri type not supported");
        }
        long rowID = database.insert(TNAME,ID,values);
        if(rowID >0){
            Uri noteUri = ContentUris.withAppendedId(CONTENT_URI,rowID);
            getContext().getContentResolver().notifyChange(noteUri,null);
            return noteUri;
        }
        throw new UnsupportedOperationException("unknown uri" + uri);

    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        database = mDatabaseHelper.getReadableDatabase();
        int count ;
        switch (mUriMatcher.match(uri)){
            case ITEM:
                count = database.delete(TNAME,selection,selectionArgs);
                break;
            case ITME_ID:
                count = database.delete(TNAME,ID + "=" + ContentUris.parseId(uri) +
                        "and" +
                        (TextUtils.isEmpty(selection) ? "" : "(" + selection + ")"),
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private class MainDatabaseHelper extends SQLiteOpenHelper{
        public MainDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "MainDatabaseHelper-onCreate()");
                    db.execSQL("CREATE TABLE " +
                                    TNAME + "(" +
                                    ID + " integer primary key autoincrement not null," +
                                    NAME + " text not null," +
                                    DESCRIPTION + " text not null," +
                                    DATE + " text not null," +
                                    SEX + " text not null" + ")"
                    );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG,"MainDatabaseHelper-onUpgrade() oldVersion:"+oldVersion + " newVersion:"+newVersion);
        }
    }
}
