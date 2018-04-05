package com.cipolat.petpoint.UI.Detail;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cipolat.petpoint.Data.Model.Pet;
import com.cipolat.petpoint.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sebastian on 04/04/18.
 */

public class DetailActivity extends AppCompatActivity implements LoaderCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.petNameLbl)
    TextView petNameLbl;
    @BindView(R.id.progress_bar_search)
    ProgressBar progressBar;
    private PetDetailsFragment petDetailFrg;

    public static final String PET_KEY = "PET_KEY";
    private int PERMISSION_LOCATION_ID = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        petDetailFrg = (PetDetailsFragment) getFragmentManager().findFragmentById(R.id.detailpet_fragmnt);
        petDetailFrg.setListener(this);
        //get pet selected and setTitle
        if (getIntent().getSerializableExtra(PET_KEY) != null) {
            Pet petSelected = (Pet) getIntent().getSerializableExtra(PET_KEY);
            petNameLbl.setText(petSelected.getName());
            if (petDetailFrg != null)
                petDetailFrg.setPetID(petSelected.getId());
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragmnt);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng store1 = new LatLng(-34.6188126, -58.3677217);
                LatLng store2 = new LatLng(-34.9208142, -57.9518059);

                googleMap.addMarker(new MarkerOptions().position(store1).title(getString(R.string.store_lbl)));
                googleMap.addMarker(new MarkerOptions().position(store2).title(getString(R.string.store_lbl)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(store1));

                CameraPosition cameraPosition = new CameraPosition.Builder().target(store1).zoom(9.0f).build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                googleMap.moveCamera(cameraUpdate);

            }
        });
    }


    @Override
    public void onLoading(boolean value) {
        progressBar.setVisibility(View.GONE);
    }

    private void requestPermissionLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                if (Build.VERSION.SDK_INT >= 23)
                    showPermissionDialog();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_LOCATION_ID);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

        }
    }

    private void showPermissionDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.permission_location_title));
        alertDialog.setMessage(getString(R.string.permission_location));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.permission_location_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
