package com.example.ittakesthree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

    }


    public void onOpenCameraListener(View view)
    {
        if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_OPEN_CAMERA);

        } else {
            // 启动相机程序
            openCamera();
        }
    }

    public void onOpenAlbumsListener(View view)
    {
        if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_OPEN_ALBUMS);
        } else {
            //打开album的界面
            openAlbums();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults
    )
    {
        switch (requestCode)
        {
            case REQUEST_OPEN_CAMERA:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    openCamera();
                }
                else {
                    showRequestDeniedDialog(REQUEST_OPEN_CAMERA);
                }
                break;
            case REQUEST_OPEN_ALBUMS:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbums();
                }
                else showRequestDeniedDialog(REQUEST_OPEN_ALBUMS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_OPEN_CAMERA && resultCode == RESULT_OK && null != data)
        {
            pictureViewIntent = new Intent(this, CameraActivity.class);
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为Bitmap图片格式，这是缩略图
            Bitmap bitmap = (Bitmap) bundle.get("data");
            System.out.println("获取图像成功");
            pictureViewIntent.putExtra("", bitmap);
            startActivity(pictureViewIntent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    private void showRequestDeniedDialog(int requestCode)
    {
        switch (requestCode) {
            case REQUEST_OPEN_CAMERA:
                new AlertDialog.Builder(this)
                        .setTitle("打开相机的请求被拒绝")
                        .setMessage("请在设置中打开权限后重试")
                        .setPositiveButton("确定", null)
                        .show();
                break;
            case REQUEST_OPEN_ALBUMS:
                new AlertDialog.Builder(this)
                        .setTitle("打开相册的请求被拒绝")
                        .setMessage("请在设置中打开权限后重试")
                        .setPositiveButton("确定", null)
                        .show();
                break;
        }
    }


    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_OPEN_CAMERA);
            }
        }
        //galleryAddPic();
    }

    private void openAlbums()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(gallery, 1);
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //-------------------get-image-file


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }



    private Intent cameraOpenIntent;
    private Intent albumsOpenIntent;

    private String currentPhotoPath;
    private Intent pictureViewIntent;

    static final int REQUEST_OPEN_CAMERA = 1001;
    static final int REQUEST_OPEN_ALBUMS = 1002;
}