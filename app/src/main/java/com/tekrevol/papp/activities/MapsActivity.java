package com.tekrevol.papp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tekrevol.papp.R;
import com.tekrevol.papp.helperclasses.GooglePlaceHelper;
import com.tekrevol.papp.helperclasses.ui.helper.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    private GoogleMap mMap;
    GooglePlaceHelper googlePlaceHelper;

    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        googlePlaceHelper = new GooglePlaceHelper(MapsActivity.this, GooglePlaceHelper.PLACE_AUTOCOMPLETE, new GooglePlaceHelper.GooglePlaceDataInterface() {
            @Override
            public void onPlaceActivityResult(double longitude, double latitude, String locationName) {
                latLng = new LatLng(latitude, longitude);
                if (mMap != null) {
                    MarkerOptions marker = new MarkerOptions().title("Select Location");
                    marker.position(latLng);
                    mMap.clear();
                    mMap.addMarker(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                }
            }

            @Override
            public void onError(String error) {

            }
        }, null);

        imgSearch.setOnClickListener(v -> googlePlaceHelper.openAutocompleteActivity());


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        googlePlaceHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            UIHelper.showToast(this, "Kindly turn on Location");
            return;
        }

        mMap.setMyLocationEnabled(true);

        MarkerOptions marker = new MarkerOptions().title("Select Location");


        if (latLng != null) {
            marker.position(latLng);
            mMap.clear();
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        }

        mMap.setOnMapClickListener(point -> {
            mMap.clear();
            marker.position(point);
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        });


        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.getPosition();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
        });
    }
}
