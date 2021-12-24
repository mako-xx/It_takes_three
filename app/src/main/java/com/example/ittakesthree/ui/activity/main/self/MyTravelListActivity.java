package com.example.ittakesthree.ui.activity.main.self;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ittakesthree.MainActivity;
import com.example.ittakesthree.R;
import com.example.ittakesthree.dao.UserDao;
import com.example.ittakesthree.database.AppDatabase;
import com.example.ittakesthree.pojo.Comment;
import com.example.ittakesthree.ui.adapter.CommentListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTravelListActivity extends AppCompatActivity {

    private static List<Comment> comments = new ArrayList<>();
    private CommentListAdapter adapter;
    private ListView listView;
    private RelativeLayout bannerLayout;
    private ViewPager vp;
    private LinearLayout dot;
    private TextView nameTv;
    private TextView contentTv;
    private TextView ctimeTv;
    private TextView biaotiTv;
    private AppDatabase db = AppDatabase.getInstance(this);
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_travel_list);
        View header = LayoutInflater.from(this).inflate(R.layout.header_traveldetail, null);
        bannerLayout = header.findViewById(R.id.bannerLayout);

        vp = header.findViewById(R.id.vp);
        dot = header.findViewById(R.id.dot);
        biaotiTv = header.findViewById(R.id.biaotiTv);
        nameTv = header.findViewById(R.id.nameTv);
        ctimeTv = header.findViewById(R.id.ctimeTv);
        contentTv = header.findViewById(R.id.contentTv);
        listView = findViewById(R.id.strategyListView);

        bindData();
        listView.setAdapter(adapter);
    }

    private void bindData() {
        List<Comment> comments = db.commentDao().loadStrategy(MainActivity.uid);
        adapter = new CommentListAdapter(this, comments);
    }
}