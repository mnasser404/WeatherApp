package com.advansys.weather;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.advansys.weather.Models.City;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by adv on 11/6/2017.
 */

public class Utils {

    public static void setupToolbar(Toolbar toolbar, Context context) {
        toolbar = ((AppCompatActivity) context).findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
    }

    public static Realm realmInitilization(Context context) {
        Realm realm = null;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
        return realm;
    }

    public static void popUpMenu(Context context, City city) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((FragmentActivity) context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.city_detail_dialog, null);

        TextView cityName = (TextView) dialogView.findViewById(R.id.tv_city_name);
        TextView weatherDescription = (TextView) dialogView.findViewById(R.id.tv_weather_desc);
        TextView humidity = (TextView) dialogView.findViewById(R.id.tv_humidity);
        TextView pressure = (TextView) dialogView.findViewById(R.id.tv_pressure);
        TextView windSpeed = (TextView) dialogView.findViewById(R.id.tv_wind_speed);
        TextView windDegree = (TextView) dialogView.findViewById(R.id.tv_wind_degree);
        TextView currentTemp = (TextView) dialogView.findViewById(R.id.tv_current_temp);
        TextView maxTemp = (TextView) dialogView.findViewById(R.id.tv_max_temp);
        TextView minTemp = (TextView) dialogView.findViewById(R.id.tv_min_temp);

        cityName.setText(city.getCityName());
        weatherDescription.setText(city.getWeatherDescription());
        humidity.setText(city.getHumidity() + " " + context.getString(R.string.hPa));
        pressure.setText(city.getPressure() + " " + context.getString(R.string.percent));
        windSpeed.setText(city.getWindSpeed() + " " + context.getString(R.string.meter_sec));
        windDegree.setText(city.getWindDegree() + " " + context.getString(R.string.degree));
        currentTemp.setText(city.getTemp() + " " + context.getString(R.string.celisus));
        maxTemp.setText(city.getTemp_max() + " " + context.getString(R.string.celisus));
        minTemp.setText(city.getTemp_min() + " " + context.getString(R.string.celisus));

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    public static void showProgress(ProgressDialog dialog, Context context) {
        dialog.setMessage(context.getResources().getString(R.string.please_wait));
        dialog.setCancelable(false);
        dialog.setMax(100);
        dialog.show();
    }

    public static void dismissProgress(ProgressDialog dialog, Context context) {
        dialog.setMessage(context.getResources().getString(R.string.please_wait));
        dialog.setCancelable(false);
        dialog.dismiss();
    }

    public static Typeface get_toolbar_TypeFace(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"toolbar_font.ttf");
        return typeface ;
    }

    public static boolean checkConnection(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            }
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

}








