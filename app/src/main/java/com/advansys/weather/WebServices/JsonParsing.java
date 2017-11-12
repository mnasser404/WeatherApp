package com.advansys.weather.WebServices;

import android.content.Context;

import com.advansys.weather.Models.City;
import com.advansys.weather.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;

/**
 * Created by adv on 11/7/2017.
 */

public class JsonParsing {

    public static void parsingJsonOfAllCities(String JSON, List<City> cities) {


        try {

            JSONObject _object = new JSONObject(JSON);
            JSONArray array = _object.getJSONArray("list");

            for (int i = 0; i < array.length(); i++) {

                parsingJsonProcessOfAllCities(array, i, false, cities, null);

            }


        } catch (Exception ex) {

        }


    }

    public static void parsingJsonOfAllCitiesWithDatabaseSaving(String JSON, List<City> cities, Realm realm) {

        realm.beginTransaction();

        try {

            JSONObject _object = new JSONObject(JSON);
            JSONArray array = _object.getJSONArray("list");

            for (int i = 0; i < array.length(); i++) {

                parsingJsonProcessOfAllCities(array, i, true, cities, realm);

            }

            realm.commitTransaction();

        } catch (Exception ex) {

        }


    }

    public static void parsingJsonOfSpecificCity(String JSON, Context context) {

        try {

            JSONObject object = new JSONObject(JSON);

            String cityName = object.getString("name");

            JSONArray weather = object.getJSONArray("weather");
            JSONObject currentObject = weather.getJSONObject(0);
            String weatherDecsription = currentObject.getString("description");


            JSONObject main = object.getJSONObject("main");
            int temp = main.getInt("temp");
            double temp_min = main.getDouble("temp_min");
            double temp_max = main.getDouble("temp_max");
            double pressure = main.getDouble("pressure");
            double humidity = main.getDouble("humidity");

            JSONObject wind = object.getJSONObject("wind");
            double speed = wind.getDouble("speed");
            double deg = wind.getDouble("deg");

            City city = new City();
            city.setCityName(cityName);
            city.setWeatherDescription(weatherDecsription);
            city.setTemp(temp);
            city.setTemp_min(temp_min);
            city.setTemp_max(temp_max);
            city.setPressure(pressure);
            city.setHumidity(humidity);
            city.setWindSpeed(speed);
            city.setWindDegree(deg);

            Utils.popUpMenu(context, city);

        } catch (Exception ex) {

        }
    }

    private static void parsingJsonProcessOfAllCities(JSONArray array, int index, boolean saveItInDataBase, List<City> cities, Realm realm) {


        try {

            JSONObject currentJSONObject = array.getJSONObject(index);

            String cityName = currentJSONObject.getString("name");

            JSONArray weather = currentJSONObject.getJSONArray("weather");
            JSONObject currentObject = weather.getJSONObject(0);
            String weatherDecsription = currentObject.getString("description");

            JSONObject cordinates = currentJSONObject.getJSONObject("coord");
            double Lat = cordinates.getDouble("Lat");
            double Lon = cordinates.getDouble("Lon");

            JSONObject main = currentJSONObject.getJSONObject("main");
            int temp = main.getInt("temp");
            double temp_min = main.getDouble("temp_min");
            double temp_max = main.getDouble("temp_max");
            double pressure = main.getDouble("pressure");
            double humidity = main.getDouble("humidity");

            JSONObject wind = currentJSONObject.getJSONObject("wind");
            double speed = wind.getDouble("speed");
            double deg = wind.getDouble("deg");

            City city = new City();
            city.setCityName(cityName);
            city.setWeatherDescription(weatherDecsription);
            city.setLat(Lat);
            city.setLon(Lon);
            city.setTemp(temp);
            city.setTemp_min(temp_min);
            city.setTemp_max(temp_max);
            city.setPressure(pressure);
            city.setHumidity(humidity);
            city.setWindSpeed(speed);
            city.setWindDegree(deg);

            cities.add(city);

            if (saveItInDataBase) {
                City _city = realm.createObject(City.class);
                _city.setCityName(cityName);
                _city.setWeatherDescription(weatherDecsription);
                _city.setLat(Lat);
                _city.setLon(Lon);
                _city.setTemp(temp);
                _city.setTemp_min(temp_min);
                _city.setTemp_max(temp_max);
                _city.setPressure(pressure);
                _city.setHumidity(humidity);
                _city.setWindSpeed(speed);
                _city.setWindDegree(deg);

                cities.add(city);
            }

        } catch (Exception ex) {

        }
    }

}
