package com.advansys.weather.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.advansys.weather.Adapters.CitiesAdapter;
import com.advansys.weather.Models.City;
import com.advansys.weather.R;
import com.advansys.weather.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.realm.Realm;

/**
 * Created by adv on 11/7/2017.
 */

public class APIs {

    private static ProgressDialog progressDialog ;

    public static void callingAllCities(final Context context, final List<City> cities, final GoogleMap mMap) {

        progressDialog = new ProgressDialog(context);

        Utils.showProgress(progressDialog , context);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GETTING_CITIES_BY_RECTANGLE_ZONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                JsonParsing.parsingJsonOfAllCities(response, cities);

                for (int i = 0; i < cities.size(); i++) {

                    LatLng city = new LatLng(cities.get(i).getLat(), cities.get(i).getLon());
                    mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(createStoreMarker(context , String.valueOf(cities.get(i).getTemp())))).position(city));


                }

                Utils.dismissProgress(progressDialog , context);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.dismissProgress(progressDialog , context);
            }
        });



        queue.add(stringRequest);
    }

    public static void callingAllCities(final Context context, final List<City> cities , final Realm realm) {

        progressDialog = new ProgressDialog(context);

        Utils.showProgress(progressDialog , context);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.GETTING_CITIES_BY_RECTANGLE_ZONE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParsing.parsingJsonOfAllCitiesWithDatabaseSaving(response, cities , realm);
                RecyclerView citiesRecyclerView = (RecyclerView) ((AppCompatActivity) context).findViewById(R.id.citiesRecyclerView);
                citiesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                citiesRecyclerView.setAdapter(new CitiesAdapter(context, cities));

                Utils.dismissProgress(progressDialog ,context);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.dismissProgress(progressDialog ,context);
            }
        });


        queue.add(stringRequest);

    }

    public static void callingSpecificCityData(Double lat, Double lon, final Context context) {

        progressDialog = new ProgressDialog(context);
        Utils.showProgress(progressDialog , context);

        String url = Urls.getSpecificCountryInfoUrl(lat, lon);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JsonParsing.parsingJsonOfSpecificCity(response, context);
                Utils.dismissProgress(progressDialog , context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.dismissProgress(progressDialog , context);
            }
        });

        queue.add(stringRequest);
    }

    public static Bitmap createStoreMarker(Context context , String cityName) {
        View markerLayout = ((AppCompatActivity)context).getLayoutInflater().inflate(R.layout.marker_layout, null);

        TextView markerRating = (TextView) markerLayout.findViewById(R.id.marker_text);
        markerRating.setText(cityName + context.getResources().getString(R.string.celisus));

        markerLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerLayout.layout(0, 0, markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight());

        final Bitmap bitmap = Bitmap.createBitmap(markerLayout.getMeasuredWidth(), markerLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        markerLayout.draw(canvas);
        return bitmap;
    }

}
