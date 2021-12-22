package com.example.ittakesthree.ui.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.GoodsBean;

import java.util.List;

public class GoodsListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<GoodsBean> beans;
    public GoodsListAdapter(Activity context, List<GoodsBean> beans) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_goods_listitem, null);
            viewHolder.iv = convertView.findViewById(R.id.iv);
            viewHolder.priceTv = convertView.findViewById(R.id.priceTv);
            viewHolder.contentTv = convertView.findViewById(R.id.contentTv);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);
            viewHolder.numberTv = convertView.findViewById(R.id.numberTv);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GoodsBean bean = beans.get(position);
        if(TextUtils.isEmpty(bean.getPic())){
            viewHolder.iv.setVisibility(View.GONE);
        }else{
            String[] pic=bean.getPic().split(",");
            viewHolder.iv.setVisibility(View.VISIBLE);
//            GlideLoadUtils imageLoader = new GlideLoadUtils((Activity) context);
//            imageLoader.loadImage(UrlConfig.BaseURL+pic[0], viewHolder.iv,true);
        }
        viewHolder.nameTv.setText(bean.getName());
        viewHolder.priceTv.setText("¥"+bean.getPrice());
        viewHolder.numberTv.setText("库存:"+bean.getNumber());
        viewHolder.contentTv.setText(bean.getContent());
        return convertView;
    }


    private class ViewHolder {
        private ImageView iv;
        private TextView priceTv,numberTv;
        private TextView contentTv;
        private TextView nameTv;
    }

    public interface ZanIF{
        void zan(int position);
    }
}
