package com.example.ittakesthree.album;

import static com.example.ittakesthree.MyApplication.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bumptech.glide.Glide;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Current project HaoRuiDoctor
 * Annotations ：相机相册裁剪图片操作
 */

public class CameraActivity extends AppCompatActivity {

    public static String TAG = "CameraActivity:";
    public static final String ExtraType = "CameraExtra";   //Activity传值key
    public static final int CAMERA = 99001;
    public static final int PHOTO = 99002;

    public static Uri IMG_URI = null;
    public static File IMG_File = null;
    public static boolean LISTENING = false;


    private static int aspectX = 0;  //设置裁剪区域的宽高比例
    private static int aspectY = 0;  //设置裁剪区域的宽高比例
    private static int outputX = 0; //设置裁剪区域的宽度和高度
    private static int outputY = 0; //设置裁剪区域的宽度和高度
    private static boolean scale = true;      //裁剪时是否可以缩放
    private static boolean noFaceDetection = false;  //是否检测人脸
    private static String outputFormat = Bitmap.CompressFormat.JPEG.toString();  //图片输出格式

    //设置图片输出格式
    public static void setOutputFormat(String outputFormats) {
        outputFormat = outputFormats;
    }

    //设置裁剪比例
    public static void setClipRatio(int aspectXs, int aspectYs) {
        aspectX = aspectXs;
        aspectY = aspectYs;
    }

    //设置裁剪像素
    public static void setClipPixel(int outputXs, int outputYs) {
        outputX = outputXs;
        outputY = outputYs;
    }

    //裁剪时是否可以缩放
    public static void setScales(boolean scales) {
        scale = scales;
    }

    //是否检测人脸
    public static void setNoFaceDetections(boolean noFaceDetections) {
        noFaceDetection = noFaceDetections;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int CameraExtra = intent.getIntExtra(ExtraType, 0);

        //动态获取权限
        boolean WRITE2 = RequestPermissions(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean READ2 = RequestPermissions(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean CAMERA2 = RequestPermissions(CameraActivity.this, Manifest.permission.CAMERA);

        if (WRITE2 && READ2 && CAMERA2) {
            switch (CameraExtra) {
                case CAMERA:
                    openCamera();  //相机操作
                    break;
                case PHOTO:
                    openGallery();  //相册操作
                    break;
            }
        } else {
            CameraActivity.this.finish();
        }
    }

    // 申请相机权限的requestCode
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;
    private static final int REQUEST_TAKE_PHOTO = 0;// 拍照
    private static final int REQUEST_CROP = 1;// 裁剪
    private static final int SCAN_OPEN_PHONE = 2;// 相册
    private Uri mCameraUri;//拍照时返回的uri
    private String mCameraImagePath;// 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;// 是否是Android 10以上手机
    private Uri mCutUri;// 图片裁剪时返回的uri
    private File imgFile;// 拍照保存的图片文件
    private File mCutFile;

    //从相册获取图片
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SCAN_OPEN_PHONE);
    }

    /**
     * 处理权限申请的回调。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_LONG).show();
            }
        }
    }


    // 图片裁剪
    private void cropPhoto(Uri uri, boolean fromCapture) {
        Intent intent = new Intent("com.android.camera.action.CROP"); //打开系统自带的裁剪图片的intent
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");

        // 注意一定要添加该项权限，否则会提示无法裁剪
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("scale", scale);

        if (aspectX != 0)
            intent.putExtra("aspectX", aspectX);
        if (aspectY != 0)
            intent.putExtra("aspectY", aspectY);
        if (outputX != 0)
            intent.putExtra("outputX", outputX);
        if (outputY != 0)
            intent.putExtra("outputY", outputY);
        // 取消人脸识别
        intent.putExtra("noFaceDetection", noFaceDetection);
        // 图片输出格式
        intent.putExtra("outputFormat", outputFormat);
        // 若为false则表示不返回数据
        intent.putExtra("return-data", false);

        // 如果是使用拍照，那么原先的uri和最终目标的uri一致,注意这里的uri必须是Uri.fromFile生成的
        if (isAndroidQ) {
            // 适配android 10
            mCutUri = uri;
            mCutFile = uri2File(CameraActivity.this, mCutUri);
        } else {
            Log.e(TAG, "android 10 以下版本");
            mCutUri = Uri.fromFile(imgFile);
            mCutFile = imgFile;
        }

        // 裁剪的图片保存在take_photo中
        String time = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
        String fileName = time;   //文件命名
        mCutFile = new File(Environment.getExternalStorageDirectory() + "/take_photo/", fileName + ".png");
        if (!mCutFile.getParentFile().exists()) {
            mCutFile.getParentFile().mkdirs();
        }
        FILELIST.add(mCutFile);

        mCutUri = Uri.fromFile(mCutFile);
//        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutUri);
        Toast.makeText(this, "剪裁图片", Toast.LENGTH_SHORT).show();
        // 以广播方式刷新系统相册，以便能够在相册中找到刚刚所拍摄和裁剪的照片
        Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentBc.setData(uri);
        this.sendBroadcast(intentBc);
        startActivityForResult(intent, REQUEST_CROP); //设置裁剪参数显示图片至ImageVie
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (isAndroidQ) {  // 适配android 10
                photoUri = createImageUri();
            } else {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    imgFile = photoFile;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, REQUEST_TAKE_PHOTO);
            }
        } else {
            Toast.makeText(CameraActivity.this, "当前系统没有可用相机", Toast.LENGTH_LONG).show();
            CameraActivity.this.finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照并进行裁剪
                case REQUEST_TAKE_PHOTO:
                    Log.e(TAG, "onActivityResult: imgUri:REQUEST_TAKE_PHOTO:" + mCameraUri.toString());
                    cropPhoto(mCameraUri, true);
                    break;
                // 裁剪后设置图片
                case REQUEST_CROP:

                    File file = uriToFileApiQ(mCutUri);
                    IMG_URI = mCutUri;
                    IMG_File = file;

                    Log.i(TAG, "IMG_URI:" + IMG_URI);
                    Log.i(TAG, "IMG_File:" + IMG_File);
                    Log.i(TAG, "getPath:" + file.getPath());
                    Log.i(TAG, "getName:" + file.getName());

                    CameraActivity.LISTENING = true;
                    CameraActivity.this.finish();
                    break;
                // 打开图库获取图片并进行裁剪
                case SCAN_OPEN_PHONE:
                    Log.e(TAG, "onActivityResult: SCAN_OPEN_PHONE:" + data.getData().toString());
                    cropPhoto(data.getData(), false);
                    break;
            }
        } else {
            //Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
            CameraActivity.this.finish();
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName + ".png");
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public File uriToFileApiQ(Uri uri) {
        File file = null;
        //android10以上转换
        if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            file = new File(uri.getPath());
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //把文件复制到沙盒目录
            ContentResolver contentResolver = CameraActivity.this.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                try {
                    InputStream is = contentResolver.openInputStream(uri);
                    File cache = new File(CameraActivity.this.getExternalCacheDir().getAbsolutePath(), Math.round((Math.random() + 1) * 1000) + displayName);
                    FileOutputStream fos = new FileOutputStream(cache);
                    FileUtils.copy(is, fos);
                    file = cache;
                    fos.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    /**
     * 动态申请权限
     *
     * @param context    上下文
     * @param permission 要申请的一个权限，列如写的权限：Manifest.permission.WRITE_EXTERNAL_STORAGE
     * @return 是否有当前权限
     */
    private boolean RequestPermissions(@NonNull Context context, @NonNull String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.i("requestMyPermissions", ": 【 " + permission + " 】没有授权，申请权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 100);
            return false;
        } else {
            Log.i("requestMyPermissions", ": 【 " + permission + " 】有权限");
            return true;
        }
    }

    /**
     * user转换为file文件
     * 返回值为file类型
     *
     * @param uri
     * @return
     */
    private File uri2File(Activity activity, Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor.getString(actual_image_column_index);
        }
        File file = new File(img_path + ".png");
        return file;
    }

}