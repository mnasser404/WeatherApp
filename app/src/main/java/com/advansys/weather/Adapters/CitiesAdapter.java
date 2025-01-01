package com.advansys.weather.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.advansys.weather.Models.City;
import com.advansys.weather.R;
import com.advansys.weather.Utils;

import java.util.List;

/**
 * Created by Moham on 11/6/2017.
 */


public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityHolder> {

    private final Context context;
    public static List<City> cityList;


    public CitiesAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
    }


    @NonNull
    @Override
    public CitiesAdapter.CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new CityHolder(row);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.CityHolder holder, int position) {
        City currentCity = cityList.get(position);
        holder.cityName.setText(currentCity.getCityName());
        holder.cityTemperature.setText(currentCity.getTemp() + " " + context.getString(R.string.celisus));
        holder.cityHumidity.setText(currentCity.getHumidity() + " " + context.getString(R.string.hPa));
        holder.cityWindSpeed.setText(currentCity.getWindSpeed() + " " + context.getString(R.string.meter_sec));
        holder.cityWindDegree.setText(currentCity.getWindDegree() + context.getString(R.string.degree));
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

            cityName = itemView.findViewById(R.id.city_name_recycler);
            cityTemperature = itemView.findViewById(R.id.city_temperature_recycler);
            cityHumidity = itemView.findViewById(R.id.city_humidity_recycler);
            cityWindSpeed = itemView.findViewById(R.id.city_wind_speed_recycler);
            cityWindDegree = itemView.findViewById(R.id.city_wind_degree_recycler);

            itemView.setOnClickListener(view -> {
                City city = cityList.get(getAbsoluteAdapterPosition());
                Utils.popUpMenu(context , city);
            });
        }


    }

}
