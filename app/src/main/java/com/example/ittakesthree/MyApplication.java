package com.example.ittakesthree;

import android.app.Activity;
import android.app.Application;
import java.io.File;
import java.util.ArrayList;
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
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static final String key = "fcace4432b6ae24ebf047b06d2fce1ee";


    public static File currentImageFile = null;
    public static String currentImageFileName = null;

    public static ArrayList<String> luggageItemList = null;

    public static File RETURNFILE;

    public static final String SOCKETIP = "192.168.50.82";
}
