package com.advansys.weather.Activities;

import static com.advansys.weather.WebServices.APIs.callingAllCities;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.advansys.weather.Adapters.CitiesAdapter;
import com.advansys.weather.Models.City;
import com.advansys.weather.R;
import com.advansys.weather.Utils;
import com.advansys.weather.WebServices.APIs;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class CitiesActivity extends AppCompatActivity {

    private SearchView searchView;
    private List<City> cityList;
    private Realm realm;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citites);

        realm = Utils.realmInitilization(this);

        cityList = new ArrayList<>();

        // Setup UI
        Utils.setupToolbar(toolbar, this);
        searchView = findViewById(R.id.search_view);
        RecyclerView citiesRecyclerView =  findViewById(R.id.citiesRecyclerView);
        citiesRecyclerView.setLayoutManager(new LinearLayoutManager(CitiesActivity.this));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                callingAllCities(CitiesActivity.this, cityList, realm);

                realm.beginTransaction();

                RealmResults<City> results = realm.where(City.class).contains("cityName", query, Case.INSENSITIVE).findAll();

                cityList = new ArrayList<>();

                cityList.addAll(results);

                realm.commitTransaction();

                citiesRecyclerView.setAdapter(new CitiesAdapter(CitiesActivity.this, cityList));

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    cityList = new ArrayList<>();
                    if(Utils.checkConnection(CitiesActivity.this)) {
                        delete_rows();
                        callingAllCities(CitiesActivity.this, cityList, realm);
                    }else {
                        Toast.makeText(CitiesActivity.this, getResources().getString(R.string.check_ur_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(getResources().getString(R.string.cities_list));
//        if(searchView.isSearchOpen()) {
//            searchView.closeSearch();
//        }
        if(Utils.checkConnection(CitiesActivity.this)) {
            callingAllCities(this, cityList, realm);
        }else {
            Toast.makeText(CitiesActivity.this, getResources().getString(R.string.check_ur_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        delete_rows();
    }

    private void delete_rows() {
        realm.executeTransaction(realm -> realm.delete(City.class));
    }


}
