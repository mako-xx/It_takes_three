package com.example.ittakesthree.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.example.ittakesthree.R;
import com.example.ittakesthree.pojo.Contentlist;

import java.util.List;

@GlideModule
public class SpotListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity context;
    private List<Contentlist> spots;

    public SpotListAdapter(Activity context, List<Contentlist> spots) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.spots = spots;
    }

    @Override
    public int getCount() {
        return spots.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_travel_listitem, null);
            viewHolder.iv = convertView.findViewById(R.id.iv);
            viewHolder.titleTv = convertView.findViewById(R.id.titleTv);
            viewHolder.contentTv = convertView.findViewById(R.id.contentTv);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contentlist spot = spots.get(position);
        if(spot.getPicList() != null && spot.getPicList().size() != 0) {
            String picUrl = spot.getPicList().get(0).getPicUrl();
            //viewHolder.iv.setImageResource();
            Glide.with(context).load(picUrl).into(viewHolder.iv);
        }
        else
            viewHolder.iv.setImageResource(R.drawable.banner1);
        viewHolder.titleTv.setText(spot.getName());
        viewHolder.contentTv.setText(spot.getSummary());
        viewHolder.nameTv.setText(spot.getAddress());

        return convertView;
    }

    private class ViewHolder {
        private ImageView iv,zanIv;
        private TextView titleTv;
        private TextView contentTv;
        private TextView nameTv,timeTv;
    }

    public interface ZanIF{
        void zan(int position);
    }
}
