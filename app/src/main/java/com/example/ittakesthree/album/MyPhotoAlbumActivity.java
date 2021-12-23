package com.example.ittakesthree.album;

import static com.example.ittakesthree.MyApplication.CURRENTLOCATE;
import static com.example.ittakesthree.MyApplication.FILELIST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import com.example.ittakesthree.R;

public class MyPhotoAlbumActivity extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo_album);

        //设置裁剪比例
        CameraActivity.setClipRatio(1, 1);


        // setOutputFormat()  设置图片输出格式
        // setClipRatio()  设置裁剪比例
        // setClipPixel()  设置裁剪像素
        // setScales()  裁剪时是否可以缩放
        // setNoFaceDetections()  是否检测人脸
    }

    private class GridViewAdapter extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        GridViewAdapter(Context context){
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(i<FILELIST.size()){
                view = layoutInflater.inflate(R.layout.gridview_item,null);
                ImageView imageView = view.findViewById(R.id.iv1);
                File f = FILELIST.get(i);
                Glide.with(this.context).load(f).into(imageView);
                return view;
            }else{
                view = layoutInflater.inflate(R.layout.gridview_item,null);
                ImageView imageView = view.findViewById(R.id.iv1);
                Glide.with(this.context)
                        .load("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/wAALCAN5AfQBAREA/8QAGgABAAMBAQEAAAAAAAAAAAAAAAECAwQFB//EACcQAQACAgICAQQBBQAAAAAAAAABAgMRBBIhMUETInGBURRCobHB/9oACAEBAAA/APv4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAje0gAAAAAAAAAAAAAAAAAAAK33rwmI1CQAAAAAAAAAAAAAAAAAAAET5SAAAAAAAAAAAAAAAAAAAAHyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAbAAAAAAAAAAAAAAAAAAB5/Iy5a5rTXNhx17xTczE9Y1MzMxv+dE8jP1zZIyVmtMlaViK+99fO/2vmtWOTEV5F+3n7K3jUTEfMe3PxtfXw/UzXtNaxaZvn8TefGoiGuWJnnzPXLMRWNTGTrWPfqN+WPHm1P6bxlrNrxFpvl7Tb7Z9xvw25WeacqcduVGGnSJisU7TO5nf+oRg5MzyMWKnInNFpntvHrURH4/l6IAAAAAAAAAAAAAAAAAEuLJjyZuVbWGkY6VmYm9YnvefX6j/quTj57TbHSlK0vet7Wm3zuPER+m3JrWmO2SvG+pkn11rG9/k43Fx4uPii2HHGStY3PWPaleL2zZ82WlbXtOsfzqIjUfhjh4l8VuLSuClIx6tkyRb3PWY1r58y6sGO8Z8+XJGpvMVrG/7Y9f5mXQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD/2Q==").into(imageView);
                return view;
            }
        }
    }

    //从相机获取图片
    public void TestBtn01(View view) {
        startActivity(new Intent(this, CameraActivity.class)
                .putExtra(CameraActivity.ExtraType, CameraActivity.CAMERA));
    }

    //从相册获取图片
    public void TestBtn02(View view) {
        startActivity(new Intent(this, CameraActivity.class)
                .putExtra(CameraActivity.ExtraType, CameraActivity.PHOTO));
    }



    @Override
    protected void onResume() {
        super.onResume();
        //获得相册、相机返回的结果，并显示
        if (CameraActivity.LISTENING) {
            Log.e("TAG", "返回的Uri结果：" + CameraActivity.IMG_URI);
            Log.e("TAG", "返回的File结果：" + CameraActivity.IMG_File.getPath());
            CameraActivity.LISTENING = false;   //关闭获取结果
//            IMG.setImageURI(CameraActivity.IMG_URI);  //显示图片到控件
        }

        gridView = findViewById(R.id.gv1);
        gridView.setAdapter(new GridViewAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CURRENTLOCATE = i;

                Intent intent= new Intent();
                intent.setClass(MyPhotoAlbumActivity.this, MyAlbumActivity.class);
                startActivity(intent);
            }

        });

    }

}