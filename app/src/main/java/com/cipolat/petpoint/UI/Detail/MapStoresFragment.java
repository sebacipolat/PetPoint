package com.cipolat.petpoint.UI.Detail;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cipolat.petpoint.R;
import com.cipolat.petpoint.UI.Detail.Location.FusedLocationHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MapStoresFragment extends Fragment implements FusedLocationHelper.LocationListener {

    private Unbinder unbinder;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private SupportMapFragment mapView;
    private FusedLocationHelper mFusedHelper;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_stores_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragmnt);
        mFusedHelper = new FusedLocationHelper(getContext(), getActivity());
        mFusedHelper.setmCallback(this);
        loadMap();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /***
     * Load Google Maps and load the statics store location
     */
    private void loadMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                //Add prefix locations stores when map is ready
                LatLng store1 = new LatLng(-34.6188126, -58.3677217);
                LatLng store2 = new LatLng(-34.9208142, -57.9518059);
                addMapMarker(googleMap, store1.latitude, store1.longitude, getString(R.string.store_lbl), BitmapDescriptorFactory.HUE_RED);
                addMapMarker(googleMap, store2.latitude, store2.longitude, getString(R.string.store_lbl), BitmapDescriptorFactory.HUE_RED);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(store1));
                zoomCamera(googleMap, store1, 9.0f);
                //when map load finish get user location
                verifyPermission();
            }
        });
    }

    /**
     * Add a marker to a google map
     * @param googleMap GoogleMap reference
     * @param lat       latitude
     * @param longit    longitude
     * @param label     marker title ext
     * @param bmpDscr   Color marker
     */
    private void addMapMarker(GoogleMap googleMap, double lat, double longit, String label, float bmpDscr) {
        MarkerOptions markOptn = new MarkerOptions();
        markOptn.position(new LatLng(lat, longit));
        markOptn.icon(BitmapDescriptorFactory.defaultMarker(bmpDscr));
        markOptn.title(label);
        googleMap.addMarker(markOptn);
    }

    /**
     * Add the user location marker to the map
     * @param googleMap GoogleMap reference
     * @param ltn       Location user data
     */
    private void addMyLocation(GoogleMap googleMap, Location ltn) {
        addMapMarker(googleMap, ltn.getLatitude(), ltn.getLongitude(), getString(R.string.your_location_lbl), BitmapDescriptorFactory.HUE_GREEN);
        LatLng myPos = new LatLng(ltn.getLatitude(), ltn.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myPos));
        zoomCamera(googleMap, myPos, 15.0f);
    }

    /**
     * Set zoom camera to an a marker
     * @param googleMap GoogleMap reference
     * @param ltlng    Location user data
     * @param zoom    Zoom level
     */
    private void zoomCamera(GoogleMap googleMap, LatLng ltlng, float zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ltlng).zoom(zoom).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
    }

    /**
     * Check permission
     * if all enabled wil start location else request permission
     */
    private void verifyPermission() {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            mFusedHelper.setLocateUserOnce(true);
            mFusedHelper.initLocationParameters();
        }
    }

    /**
     * Check runtime permission to location
     * @return permission allow status
     */
    private boolean checkPermissions() {
        int permissionState = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request android system the location access.
     */
    private void startLocationPermissionRequest() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    /**
     * Check if cancel permission was selected
     */
    private void requestPermissions() {
        boolean shouldProvideRationale = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            showPermissionDialog(getString(R.string.permission_location_denied));
        } else {
            showPermissionDialog(getString(R.string.permission_location));
        }
    }


    /**
     * Runtime Permission result data
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("requestPermissions", "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                if (mFusedHelper != null)
                    mFusedHelper.initLocationParameters();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFusedHelper != null)
            mFusedHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Show permission location dialog
     * @param text dialog text
     */
    private void showPermissionDialog(String text) {
        ContextThemeWrapper themedContext = new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom);
        AlertDialog alertDialog = new AlertDialog.Builder(themedContext).create();
        alertDialog.setIcon(android.R.drawable.ic_menu_mylocation);
        alertDialog.setTitle(getString(R.string.permission_location_title));
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.permission_location_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startLocationPermissionRequest();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.permission_location_cancelar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mFusedHelper != null)
            mFusedHelper.onDestroy();
    }

    @Override
    public void onLasLocation(Location lastLoc) {
    }

    /**
     * Location update incoming data
     * @param locationNow now location latlng
     */
    @Override
    public void onLocationUpdated(Location locationNow) {
        if (mMap != null)
            addMyLocation(mMap, locationNow);
    }
}
