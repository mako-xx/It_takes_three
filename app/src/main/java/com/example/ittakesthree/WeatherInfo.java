package com.example.ittakesthree;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.WeatherSearch;

public class WeatherInfo extends WeatherSearch {


    public WeatherInfo(Context context) throws AMapException {
        super(context);
    }
}
