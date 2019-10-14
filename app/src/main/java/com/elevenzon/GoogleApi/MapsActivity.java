package com.elevenzon.GoogleApi;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText search;
    String place;
    List<Address> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        search = (EditText) findViewById(R.id.search);

        // get address from previous activity
        place = getIntent().getStringExtra("place");
        search.setText(place);
        search.setFocusable(false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                intent.putExtra("searchValue", search.getText().toString());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    /**
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            addressList = geocoder.getFromLocationName(place, 5);
            Address location = addressList.get(0);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(place));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }
}