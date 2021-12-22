package com.example.ittakesthree.tools;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ittakesthree.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTool {
    public static SharedPreferences sp;

    public static void showLog(String msg) {
        if (UrlConfig.LOG_FLAG && !TextUtils.isEmpty(msg)) {
            Log.e("test", msg);
        }
    }
    public static void showToast(String text){
        if(!TextUtils.isEmpty(text)){
            Toast.makeText(MyApplication.getContext(),text,Toast.LENGTH_SHORT).show();
        }
    }
    public static String timeToString(long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }
    private static SharedPreferences getSp(){
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences("sports", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void spPutString(String key, String value) {
        getSp().edit().putString(key,value).commit();
    }

    public static String spGetString(String key) {
        return getSp().getString(key,null);
    }
    /**
     * 设置对话框
     *
     * @param activity
     * @param dialog
     * @param position 位置 Gravity.CENTER等
     * @param animId   -1表示没有动画
     * @param scaleW   {0~1}对话框的宽度=屏幕宽度*scaleW -1表示包裹内容
     * @param scaleH   {0~1}对话框的高度=屏幕高度*scaleH -1表示包裹内容
     */
    public static void setDialog(Activity activity, Dialog dialog, int position, int animId, double scaleW, double scaleH) {

        Window window = dialog.getWindow();
        window.setGravity(position);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        layoutParams.dimAmount = 0.2f;
        window.setAttributes(layoutParams);

        // 此处可以设置dialog显示的位置
        if (animId != -1) {
            window.setWindowAnimations(animId);
            // 添加动画
        }
        dialog.show();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * scaleW);
        // 设置宽度
        if (scaleH > 0) {
            lp.height = (int) (display.getHeight() * scaleH);
        }
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(true);

    }
    public static void toPermission(Activity context) {
        String brand = android.os.Build.BRAND;
        if (TextUtils.isEmpty(brand)) return;
        if (brand.equalsIgnoreCase("Xiaomi")) {
            gotoMiuiPermission(context);
        } else if (brand.equalsIgnoreCase("HUAWEI")) {
            gotoHuaweiPermission(context);
        } else if (brand.equalsIgnoreCase("Meizu")) {
            gotoMeizuPermission(context);
        } else {
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    public static int dp2px(double dp) {
        DisplayMetrics dm = new DisplayMetrics();
//        WindowManager wm = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(dm);
        final float scale = dm.density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 跳转到miui的权限管理页面
     */
    public static void gotoMiuiPermission(Activity context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    public static void gotoMeizuPermission(Activity context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName",  "com.travel");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 华为的权限管理页面
     */
    public static void gotoHuaweiPermission(Activity context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
