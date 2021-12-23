package com.example.ittakesthree.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.ittakesthree.R;
import com.example.ittakesthree.util.ToastUtil;

public class LocationActivity extends AppCompatActivity implements AMapLocationListener {

    AMapLocationClient locationClient = null;
    AMapLocationClientOption locationClientOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        try {
            locationClient = new AMapLocationClient(getApplicationContext());
            locationClient.setLocationListener(this);
            locationClientOption = new AMapLocationClientOption();
            locationClientOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClientOption.setNeedAddress(true);
            locationClient.setLocationOption(locationClientOption);
            locationClient.stopLocation();
            locationClient.startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e("TAG", "su");
        if(aMapLocation != null && aMapLocation.getErrorCode() == 0)
        {
            ToastUtil.show(this, "s");
            double latitude = aMapLocation.getLatitude();
            double longtitude = aMapLocation.getLongitude();
            ToastUtil.show(this, latitude + " " + longtitude);
            //ToastUtil.show(this, longtitude + " ");
        }
        else
        {
            ToastUtil.show(this, "对不起，定位失败");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.startLocation();
        locationClient.onDestroy();
    }
}