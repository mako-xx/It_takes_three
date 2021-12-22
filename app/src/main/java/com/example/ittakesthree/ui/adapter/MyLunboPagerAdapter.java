package com.example.ittakesthree.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class MyLunboPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> viewlist;

    public MyLunboPagerAdapter(ArrayList<ImageView> viewlist) {
        this.viewlist = viewlist;
    }

    @Override
    public int getCount() {
        return viewlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (viewlist.size() > position)
            container.removeView(viewlist.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView v = viewlist.get(position);
        container.addView(v);
        return v;
    }
}
