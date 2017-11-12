package com.advansys.weather.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.advansys.weather.Models.City;
import com.advansys.weather.R;
import com.advansys.weather.Utils;
import com.advansys.weather.WebServices.APIs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final int ZOOM_LEVEL = 6;
    private static final double LATITUDE = 26.82;
    private static final double LONGITUDE = 30.80;

    private FloatingActionButton hintButton;
    private List<City> cities;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        cities = new ArrayList<>();
        hintButton = (FloatingActionButton) findViewById(R.id.floatingActionButton_hint);
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this , CitiesActivity.class));
            }
        });

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng Egypt = new LatLng(LATITUDE , LONGITUDE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Egypt, ZOOM_LEVEL));

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            mMap.setMyLocationEnabled(true);
        }

        if(Utils.checkConnection(this)) {
            APIs.callingAllCities(MapsActivity.this, cities, mMap);
        }else{
            Toast.makeText(this, getResources().getString(R.string.check_ur_connection), Toast.LENGTH_SHORT).show();
        }
        markerClickListener();
    }


    private void markerClickListener() {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(Utils.checkConnection(MapsActivity.this)) {
                APIs.callingSpecificCityData(marker.getPosition().latitude, marker.getPosition().longitude, MapsActivity.this);
                }else{
                    Toast.makeText(MapsActivity.this, getResources().getString(R.string.check_ur_connection), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }
            }
        }
    }







}


