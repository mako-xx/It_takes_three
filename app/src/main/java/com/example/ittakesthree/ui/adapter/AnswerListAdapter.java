package com.example.ittakesthree.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.AnswerBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnswerListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<AnswerBean> beans;

    public AnswerListAdapter(Activity context, List<AnswerBean> beans) {
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
            convertView = inflater.inflate(R.layout.adapter_comment_listitem, null);
            viewHolder.contentTv = convertView.findViewById(R.id.contentTv);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);
            viewHolder.timeTv = convertView.findViewById(R.id.timeTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AnswerBean bean = beans.get(position);

        viewHolder.nameTv.setText(bean.getUname());
        viewHolder.contentTv.setText(bean.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date(bean.getCreatetime()));
        viewHolder.timeTv.setText(time);
        return convertView;
    }


    private class ViewHolder {
        private TextView timeTv;
        private TextView contentTv;
        private TextView nameTv;
    }


}
