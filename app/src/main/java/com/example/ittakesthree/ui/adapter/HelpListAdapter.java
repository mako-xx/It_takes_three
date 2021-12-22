package com.example.ittakesthree.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.HelpBean;

import java.util.List;

public class HelpListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<HelpBean> beans;
    public HelpListAdapter(Activity context, List<HelpBean> beans) {
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
            convertView = inflater.inflate(R.layout.adapter_help_listitem, null);
            viewHolder.contentTv = convertView.findViewById(R.id.contentTv);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HelpBean bean = beans.get(position);

        viewHolder.nameTv.setText("求助者:"+bean.getUname());
        viewHolder.contentTv.setText(bean.getContent());
        return convertView;
    }


    private class ViewHolder {
        private TextView contentTv;
        private TextView nameTv;
    }


}
