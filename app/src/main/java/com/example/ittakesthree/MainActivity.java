package com.example.ittakesthree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.ittakesthree.luggage.ClassifierActivity;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

import java.io.IOException;
import java.text.BreakIterator;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://restapi.amap.com/v5/place/").client(client).build();
    PoiSearch poiSearch;
    PoiSearch.Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceSettings.updatePrivacyShow(this,true,true);
        ServiceSettings.updatePrivacyAgree(this,true);

    }



    public void get(View view) throws AMapException {
        ServiceSettings.updatePrivacyShow(this,true,true);
        ServiceSettings.updatePrivacyAgree(this,true);
        WeatherSearchQuery mquery = new WeatherSearchQuery("北京", WeatherSearchQuery.WEATHER_TYPE_LIVE);
        WeatherSearch mweathersearch = new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
            @Override
            public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
                if (rCode == 1000) {
                    if (weatherLiveResult != null&&weatherLiveResult.getLiveResult() != null) {
                        LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                        Log.e("INFO","report" + weatherlive.getReportTime() + weatherlive.getWeather());
                        /*
                        reporttime1.setText(weatherlive.getReportTime()+"发布");
                        BreakIterator weather = null;
                        weather.setText(weatherlive.getWeather());
                        Temperature.setText(weatherlive.getTemperature()+"°");
                        wind.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
                        humidity.setText("湿度         "+weatherlive.getHumidity()+"%");

                         */
                    }else {
                        //ToastUtil.show(WeatherSearchActivity.this, R.string.no_result);
                    }
                }else {

                }
            }

            @Override
            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

            }
        });
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    public void poi(View view) throws IOException, AMapException {

        query = new PoiSearch.Query("八达岭长城", "风景名胜", "");
        query.setPageNum(1);
        query.setPageSize(10);
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new POIListener());
        poiSearch.searchPOIAsyn();

    }

    public void openCamera(View view)
    {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void openLuggage(View view)
    {
        Intent intent = new Intent(this, ClassifierActivity.class);
        startActivity(intent);
    }
}