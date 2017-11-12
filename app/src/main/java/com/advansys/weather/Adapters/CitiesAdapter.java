package com.advansys.weather.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.advansys.weather.Models.City;
import com.advansys.weather.R;
import com.advansys.weather.Utils;
import com.advansys.weather.WebServices.APIs;

import java.util.List;

/**
 * Created by Moham on 11/6/2017.
 */


public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityHolder> {

    private Context context;
    public static List<City> cityList;


    public CitiesAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
    }


    @Override
    public CitiesAdapter.CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        CityHolder holder = new CityHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(CitiesAdapter.CityHolder holder, int position) {
        City currentCity = cityList.get(position);
        holder.cityName.setText(currentCity.getCityName());
        holder.cityTemperature.setText(String.valueOf(currentCity.getTemp()) + " " + context.getString(R.string.celisus));
        holder.cityHumidity.setText(String.valueOf(currentCity.getHumidity()) + " " + context.getString(R.string.hPa));
        holder.cityWindSpeed.setText(String.valueOf(currentCity.getWindSpeed()) + " " + context.getString(R.string.meter_sec));
        holder.cityWindDegree.setText(String.valueOf(currentCity.getWindDegree()) + context.getString(R.string.degree));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public class CityHolder extends RecyclerView.ViewHolder {

        private TextView cityName;
        private TextView cityTemperature;
        private TextView cityHumidity;
        private TextView cityWindSpeed;
        private TextView cityWindDegree;

        public CityHolder(View itemView) {
            super(itemView);

            cityName = (TextView) itemView.findViewById(R.id.city_name_recycler);
            cityTemperature = (TextView) itemView.findViewById(R.id.city_temperature_recycler);
            cityHumidity = (TextView) itemView.findViewById(R.id.city_humidity_recycler);
            cityWindSpeed = (TextView) itemView.findViewById(R.id.city_wind_speed_recycler);
            cityWindDegree = (TextView) itemView.findViewById(R.id.city_wind_degree_recycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    City city = cityList.get(getAdapterPosition());
                    Utils.popUpMenu(context , city);
                }
            });
        }


    }




}
