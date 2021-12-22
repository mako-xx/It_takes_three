package com.example.ittakesthree.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.LocalMedia;

import java.util.ArrayList;

public class MyPostGridAdapter2 extends BaseAdapter {
    private Context context;
    private ArrayList<LocalMedia> selectionMedia;
    private DeletePicImp deletePicImp;

    public MyPostGridAdapter2(Context context, ArrayList<LocalMedia> selectionMedia, DeletePicImp deletePicImp) {
        super();
        this.context = context;
        this.selectionMedia = selectionMedia;
        this.deletePicImp = deletePicImp;
    }

    @Override
    public int getCount() {
        if (selectionMedia.size() == 6) return selectionMedia.size();
        return selectionMedia.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.adapter_post_griditem_pic, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.closeIv = (ImageView) convertView.findViewById(R.id.closeIv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectionMedia.size()) {
            //Glide.with(context).load("").placeholder(R.mipmap.party_addpic).into(holder.img);
            holder.closeIv.setVisibility(View.GONE);
        } else {
            holder.closeIv.setVisibility(View.VISIBLE);
            LocalMedia media = selectionMedia.get(position);
            if (media != null) {
                holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                GlideLoadUtils imageLoader = new GlideLoadUtils((Activity) context);
//                imageLoader.loadImage(media.getPath(), holder.img,true);
            }
        }
        holder.closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePicImp.deletePic(position);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public ImageView img;
        public ImageView closeIv;

    }

    public interface DeletePicImp {
        public void deletePic(int position);
    }


}
