package com.example.ittakesthree.ui.activity.base;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;
import com.example.ittakesthree.R;
import com.example.ittakesthree.MyApplication;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.tools.StatusBarUtil;
import com.example.ittakesthree.ui.view.dialog.CustomDialog;
import com.example.ittakesthree.ui.view.dialog.DialogLoading;

import java.util.Objects;

/**
 * 文件描述：Activity基类
 * Create By：LJY
 * Time:2021/12/16
 * Remember to smile
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static Bundle savedInstanceState1;
    public static String IsInit = "IsInit";
    private DialogLoading loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState1 = savedInstanceState;
        MapsInitializer.updatePrivacyShow(getApplicationContext(),true,true);
        MapsInitializer.updatePrivacyAgree(getApplicationContext(),true);
        ServiceSettings.updatePrivacyShow(getApplicationContext(),true,true);
        ServiceSettings.updatePrivacyAgree(getApplicationContext(),true);
        MyApplication.getContext().addActivity(this);

        setContentView(initLayout());
        //根据状态栏颜色来决定状态栏文字用黑色还是白色
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
        initView();
        initData();
    }

    public abstract int initLayout();

    protected abstract void initData();

    protected abstract void initView();


    public Bundle getSavedInstanceState1() {
        return savedInstanceState1;
    }

    /**
     * 初始化标题
     *
     * @param mToolbar
     * @param mTitle
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void initToorbar(Toolbar mToolbar, String mTitle) {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();

    }


    public void showLoadingDialog() {
        if (loading == null) {
            loading = new DialogLoading(this);
        }
//        loading.setDialogLabel(Msg);
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    public void hideLoadingDialog() {
        try {
            if (loading != null) {
                if (loading.isShowing()) {
                    loading.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backTo(View view) {
        finish();
    }

    /*******权限*******/
    public static final int REQUEST_PERMISSON = 1;
    private PermissionResultIF permissionResultIF;
    private boolean isShowDialog = true;
    private String[] PERMISSIONS;

    public boolean verifyStoragePermissions(String[] PERMISSIONS, PermissionResultIF permissionResultIF) {
        isShowDialog = true;
        this.permissionResultIF = permissionResultIF;
        this.PERMISSIONS = PERMISSIONS;
        return verifyStoragePermissions();
    }

    private boolean verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT < 23) {
            permissionResultIF.permissionResult(true);
            return true;
        }
        int permission = 0;
        for (int i = 0; i < PERMISSIONS.length; i++) {
            permission += ActivityCompat.checkSelfPermission(this, PERMISSIONS[i]);
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSON);
            return false;
        } else {
            permissionResultIF.permissionResult(true);
            return true;
        }
    }


    /**
     *  * 请求权限回调
     *  *
     *  * @param requestCode
     *  * @param permissions
     *  * @param grantResults
     *  
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSON) {
            if (grantResults.length == 0) return;
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissionResultIF != null) {
                    permissionResultIF.permissionResult(true);
                }
            } else {
                if (isShowDialog) {
                    String strs = "";
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            strs += "定位、";
                        } else if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            strs += "文件存储、";
                        } else if (permissions[i].equals(Manifest.permission.CAMERA)) {
                            strs += "调用摄像头、";
                        } else if (permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                            strs += "录制视频/音频、";
                        } else if (permissions[i].equals(Manifest.permission.CALL_PHONE)) {
                            strs += "拨打电话、";
                        }
                    }
                    strs = strs.substring(0, strs.length() - 1);
                    showPermissionTipDialog("需要获取" + strs + "权限，去获取？");
                } else {
                    if (permissionResultIF != null) {
                        permissionResultIF.permissionResult(false);
                    }
                }

            }

        }
    }

    public void showPermissionTipDialog(String tip) {
        CustomDialog dialog = new CustomDialog(this);
        dialog.bindCommonTipLayout("提示", tip, "取消", "确认", new CustomDialog.DialogCommonFinishIF() {
            @Override
            public void onDialogFinished(Object... objects) {
                int flag = Integer.parseInt(objects[0].toString());
                if (flag == 2) {//去获取
                    isToPermisson = true;
                    CommonTool.toPermission(BaseActivity.this);
                } else {
                    if (permissionResultIF != null) {
                        permissionResultIF.permissionResult(false);
                    }
                }
            }
        });
        CommonTool.setDialog(this, dialog, Gravity.CENTER, R.style.dialogWindowAnimButtomToTop, 1, 1);

    }

    protected boolean isToPermisson = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (isToPermisson) {
            isToPermisson = false;
            isShowDialog = false;
            verifyStoragePermissions();
        }
    }


    public interface PermissionResultIF {
        public void permissionResult(boolean suc);
    }

}
