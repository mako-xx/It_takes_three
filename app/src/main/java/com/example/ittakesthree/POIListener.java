package com.example.ittakesthree;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.Photo;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

public class POIListener implements PoiSearch.OnPoiSearchListener {


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e("INFO", "aa");
        final ArrayList<PoiItem> pois = poiResult.getPois();
        for (PoiItem poiItem : pois) {
            final String adName = poiItem.getAdName();
            final LatLonPoint latLonPoint = poiItem.getLatLonPoint();
            Log.e("INFO", adName);
            final List<Photo> photos = poiItem.getPhotos();
            for (Photo photo : photos) {
                Log.e("INFO", photo.getUrl());
            }
            final String website = poiItem.getWebsite();
            Log.e("INFO", website);

        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
