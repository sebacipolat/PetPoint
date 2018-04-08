package com.cipolat.petpoint.UI.Detail.Location;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FusedLocationHelper {

    private FusedLocationProviderClient mFusedLocationClient;
    private final long  UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private final long FASTEST_INTERVAL = 2000; /* 2 sec */
    private static final int REQUEST_CHECK_SETTINGS = 100;

    private Context mCtx;
    private Activity mActv;
    private LocationRequest mLocationRequest;
    private LocationListener mCallback;
    private LocationCallback mLoctnCllbk;
    private boolean locateUserOnce = false;

    public FusedLocationHelper(Context ctx, Activity actvt) {
        mCtx = ctx;
        mActv = actvt;
        if (ctx == null || actvt == null)
            throw new NullPointerException("NOT NULL CONTEXT OR ACTIVITY ALLOWED");
        else
            creatLocationRequest();
    }

    /**
     * set callback listener
     * @param mCallback location resutls listener
     */
    public void setmCallback(LocationListener mCallback) {
        this.mCallback = mCallback;
    }

    /**
     * Initiate fused location request
     * set intervals and priority
     */
    private void creatLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

    /**
     * Check device location settings
     * Enabled location automatically or by user prompt
     * start userlocation if all is ok
     */
    public void initLocationParameters() {
        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mCtx);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(mCtx).checkLocationSettings(locationSettingsRequest);
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    /**
                     * Check if Settings->Location is enabled/disabled
                     * Not app specific permission (location)
                     * Here I am talking of the scenario where Settings->Location is disabled and user runs the app.
                     */
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    getLocation();
                } catch (ApiException exception) {
                    /**
                     * Go in exception because Settings->Location is disabled.
                     * First it will Enable Location Services (GPS) then check for run time permission to app.
                     */
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                /**
                                 * Display enable Enable Location Services (GPS) dialog like Google Map and then
                                 * check for run time permission to app.
                                 */
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(mActv, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        });
    }

    /**
     * Start Location updates
     *
     */
    @SuppressWarnings("MissingPermission")
    public void getLocation() {
        mLoctnCllbk = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    if (mCallback != null)
                        mCallback.onLocationUpdated(location);

                    //Stop update if just want locate the user once
                    if (locateUserOnce)
                        onDestroy();
                }
            }

        };
        if (mLocationRequest != null)
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLoctnCllbk, null);
    }

    /**
     * Retrieve last location
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        try {
                            if (task.isSuccessful() && task.getResult() != null) {
                                Location mLastLocation = task.getResult();
                                if (mCallback != null)
                                    mCallback.onLasLocation(mLastLocation);
                            } else {

                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
    }

    /**
     * Allow locate the user just once.
     * @param locateUserOnce true located once and disable location
     *                       false location will be repeat by interval
     */
    public void setLocateUserOnce(boolean locateUserOnce) {
        this.locateUserOnce = locateUserOnce;
    }

    /**
     * Request code coming from DetailActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    /**
     * disable location updates
     */
    public void onDestroy() {
        if (mFusedLocationClient != null && mLoctnCllbk != null)
            mFusedLocationClient.removeLocationUpdates(mLoctnCllbk);
    }

    public interface LocationListener {
        void onLasLocation(Location lastLoc);

        void onLocationUpdated(Location locationNow);
    }

}
