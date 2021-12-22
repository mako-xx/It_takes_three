package com.example.ittakesthree.map;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.example.ittakesthree.R;

public class LocationActivity extends AppCompatActivity {

    AMapLocationClient locationClient = null;
    AMapLocationListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        new Thread(){
            @Override
            public void run() {
                //locationListener =
            }
        }.start();
    }
}