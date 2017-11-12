package com.advansys.weather.WebServices;

/**
 * Created by adv on 11/7/2017.
 */

public class Urls {

    public static final String CELISUS = "&units=metric";
    public static final String APP_ID = "&appid=97e12655493e5fd3e76667fe8b7d68b2";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String GETTING_CITIES_BY_RECTANGLE_ZONE = BASE_URL + "box/city?bbox=37.3,21.3,24.2,32.1,10" + CELISUS + APP_ID;
    public static String getSpecificCountryInfoUrl(Double lat, Double lon) {
        return BASE_URL + "weather?" + "lat=" + lat + "&lon=" + lon + CELISUS + APP_ID;
    }

}
