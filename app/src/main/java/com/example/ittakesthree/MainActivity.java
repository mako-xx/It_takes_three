package com.example.ittakesthree;

import android.content.Intent;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.ittakesthree.ui.activity.base.BaseActivity;
import com.example.ittakesthree.ui.activity.login.LoginActivity;
import com.example.ittakesthree.ui.activity.main.travel.TravelDetailActivity;
import com.example.ittakesthree.ui.fragment.GoodsFragment;
import com.example.ittakesthree.ui.fragment.HelpFragment;
import com.example.ittakesthree.ui.fragment.MainFragment;
import com.example.ittakesthree.ui.fragment.SelfFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener, AMapLocationListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static String uid;

    private View button1, button2, button3, button4, button5;
    private TextView[] tvs = new TextView[5];
    private ImageView[] ivs = new ImageView[5];
    private int index = 0;
    private int[] checkId={R.drawable.tab_home2,R.drawable.tab_xiaox2,R.drawable.tab_qiand,R.drawable.tab_dongt2,R.drawable.tab_mine2};
    private int[] uncheckId={R.mipmap.tab_home,R.drawable.tab_xiaox,R.drawable.tab_qiand,R.drawable.tab_dongt,R.drawable.tab_mine};
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        setCurrentFragment();
    }

    @Override
    protected void initView() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button1);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button3);
        button5 = findViewById(R.id.button5);
        tvs[0] = findViewById(R.id.tv1);
        tvs[1] = findViewById(R.id.tv1);
        tvs[3] = findViewById(R.id.tv5);
        tvs[4] = findViewById(R.id.tv5);
        ivs[0] = findViewById(R.id.iv1);
        ivs[1] = findViewById(R.id.iv1);
        ivs[3] = findViewById(R.id.iv5);
        ivs[4] = findViewById(R.id.iv5);

        uid = getIntent().getStringExtra("LOGIN");

        if(uid == null)
        {
            Toast.makeText(this, "Please log in", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        MainFragment fragment1 = new MainFragment();
        mFragments.add(fragment1);
        HelpFragment fragment2 = new HelpFragment();
        mFragments.add(fragment2);
        SelfFragment fragment3 = new SelfFragment();
        mFragments.add(fragment3);
        GoodsFragment fragment4 = new GoodsFragment();
        mFragments.add(fragment4);
        SelfFragment fragment5 = new SelfFragment();
        mFragments.add(fragment5);
        setListener();
    }

    protected void setListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    private void setCurrentFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            if (mFragments.get(i) != null) {
                transaction.hide(mFragments.get(i));
            }
            if(i!=2){
                if (i == index) {
                    tvs[i].setTextColor(getResources().getColor(R.color.mainText));
                    tvs[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    TextPaint tp = tvs[i].getPaint();
                    tp.setFakeBoldText(true);
                    ivs[i].setImageResource(checkId[i]);
                } else {
                    tvs[i].setTextColor(getResources().getColor(R.color.bar_grey));
                    tvs[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    TextPaint tp = tvs[i].getPaint();
                    tp.setFakeBoldText(false);
                    ivs[i].setImageResource(uncheckId[i]);
                }
            }

        }
        //
        if (!mFragments.get(index).isAdded()) {
            transaction.add(R.id.container, mFragments.get(index));
        }
        transaction.show(mFragments.get(index));
        transaction.commit();

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
            index = 0;
            setCurrentFragment();
        }
//        else if (view.getId() == R.id.button2) {
//            index = 1;
//            setCurrentFragment();
//
//        }
        else if (view.getId() == R.id.button3) {


            /**
             * graph
             */

        }
//        else if (view.getId() == R.id.button4) {
//            index = 3;
//            setCurrentFragment();
//        }
        else if (view.getId() == R.id.button5) {
            index = 4;
            setCurrentFragment();
        }

    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }
}