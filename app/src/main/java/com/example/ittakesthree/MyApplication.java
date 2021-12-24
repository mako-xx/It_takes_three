package com.example.ittakesthree;

import android.app.Activity;
import android.app.Application;

import com.amap.api.maps.MapsInitializer;
import com.example.ittakesthree.database.AppDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private static MyApplication context;
    private static Stack<Activity> activityStack = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static MyApplication getContext() {
        context =  new MyApplication();
        return context;
    }
    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static final String key = "fcace4432b6ae24ebf047b06d2fce1ee";

    //public static final String SOCKETIP = "139.196.6.145";

    //public static final String SOCKETIP = "192.168.50.82";

    //public static final String SOCKETIP = "192.168.50.82";

    public static final String SOCKETIP = "192.168.43.82";

    public static List<File> FILELIST = new ArrayList<>(9);

    public static int CURRENTLOCATE;


    public static File currentImageFile = null;
    public static String currentImageFileName = null;

    public static ArrayList<String> luggageItemList = null;

    public static File RETURNFILE;

    public static final String appid = "870197";

    public static final String appkey = "b5509c065bbd4134b5517eafce11fd9f";

}
