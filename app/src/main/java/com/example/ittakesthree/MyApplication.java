package com.example.ittakesthree;

import android.app.Application;

import com.amap.api.maps.MapsInitializer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {


    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static final String key = "fcace4432b6ae24ebf047b06d2fce1ee";


    public static File currentImageFile = null;
    public static String currentImageFileName = null;

    public static ArrayList<String> luggageItemList = null;

    public static File RETURNFILE;

    public static final String SOCKETIP = "192.168.50.82";

}
