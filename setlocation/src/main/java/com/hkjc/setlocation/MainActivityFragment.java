package com.hkjc.setlocation;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Iterator;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final String TAG = "MainActivityFragment";

    private Thread mChangeLocationWorker;
    private Button mStartBtn;
    private Button mStopBtn;
    private GoogleApiClient mGoogleApiClient;


    public MainActivityFragment() {
        mChangeLocationWorker = new Thread(new ChangeLocationRunner());
    }


    public void onBtnClick(View view) {
        Log.d(TAG, "onBtnCliek()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangeLocationWorker.start();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        mStartBtn = (Button) rootview.findViewById(R.id.btn_start);
        mStopBtn = (Button) rootview.findViewById(R.id.btn_stop);
        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChangeLocationWorker != null) {
            mChangeLocationWorker.interrupt();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (mChangeLocationWorker == null) {
                    mChangeLocationWorker = new Thread(new ChangeLocationRunner());
                    mChangeLocationWorker.start();
                }
                break;
            case R.id.btn_stop:
                if (mChangeLocationWorker != null) {
                    mChangeLocationWorker.interrupt();
                    mChangeLocationWorker = null;
                }
                break;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Log.d(TAG, "google api client location: " + location.getLatitude() + "  " + location.getLongitude());
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(), this);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "google api client , location changed:" + location.getLatitude() + "  " + location.getLongitude());
    }


    private class ChangeLocationRunner implements Runnable {

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
            LocationManager locationMgr = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setTime(System.currentTimeMillis());
            // weixin
//            location.setLatitude(22.5332160000);
//            location.setLongitude(113.9559970000);

            // shahe
            location.setLatitude(22.6032160011);
            location.setLongitude(113.9659970011);

            location.setAccuracy(Criteria.ACCURACY_FINE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            }
            List<String> providers = locationMgr.getAllProviders();
            if (providers == null) {
                Log.w(TAG, "no any provider!!!");
                return;
            }
            Iterator<String> iterator;
            String provider;
            Location lastLocation;
            try {
                while (true) {
                    iterator = providers.iterator();
                    while (iterator.hasNext()) {
                        provider = iterator.next();
                        lastLocation = locationMgr.getLastKnownLocation(provider);
                        if (lastLocation != null) {
                            Log.d(TAG, "provider:" + provider + " location:" + lastLocation.getLatitude() + "  " + lastLocation.getLongitude());
                        }
                    }
                    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                        Location googleLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (googleLocation != null) {
                            Log.d(TAG, "google api client location: " + location.getLatitude() + "  " + location.getLongitude());
                        }
                    }
                    locationMgr.addTestProvider(LocationManager.GPS_PROVIDER, false, false, false, false, false, false, false, 0, 5);
                    locationMgr.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
                    locationMgr.setTestProviderStatus(LocationManager.GPS_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
                    locationMgr.setTestProviderLocation(LocationManager.GPS_PROVIDER, location);

                    locationMgr.addTestProvider(LocationManager.NETWORK_PROVIDER, false, false, false, false, false, false, false, 0, 5);
                    locationMgr.setTestProviderEnabled(LocationManager.NETWORK_PROVIDER, true);
                    locationMgr.setTestProviderStatus(LocationManager.NETWORK_PROVIDER, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
                    locationMgr.setTestProviderLocation(LocationManager.NETWORK_PROVIDER, location);
                    if (mGoogleApiClient.isConnected()) {
                        LocationServices.FusedLocationApi.setMockMode(mGoogleApiClient, true);
                        LocationServices.FusedLocationApi.setMockLocation(mGoogleApiClient, location);
                    }

                    long time = 300;
                    Thread.sleep(time);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
