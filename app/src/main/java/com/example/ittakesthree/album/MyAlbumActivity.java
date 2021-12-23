package com.example.ittakesthree.album;

import static com.example.ittakesthree.MyApplication.CURRENTLOCATE;
import static com.example.ittakesthree.MyApplication.FILELIST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ittakesthree.R;

import java.io.File;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyAlbumActivity extends AppCompatActivity {

    Context context;
    private ImageView image;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        btn =(Button) findViewById(R.id.button);
        image = (ImageView) findViewById(R.id.imageView);
        File f = FILELIST.get(CURRENTLOCATE);
        String filename =f.getName();
        Glide.with(this).load(f).into(image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                ToSocket toSocket = new ToSocket(filename,f);
                toSocket.work();
                Order.setOnChangeListener(new Order.OnChangeListener() {
                    @Override
                    public void onChange() {
                        File file= Order.getReturnFile();
                        Glide.with(getApplicationContext()).load(file).into(image);
                        // 值改变需要进行的事务
                    }
                });
            }
        });
    }

}