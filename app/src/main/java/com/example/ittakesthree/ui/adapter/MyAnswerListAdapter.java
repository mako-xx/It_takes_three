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

public class MyAnswerListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<AnswerBean> beans;

    public MyAnswerListAdapter(Activity context, List<AnswerBean> beans) {
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
            convertView = inflater.inflate(R.layout.adapter_myanswer_listitem, null);
            viewHolder.contentTv = convertView.findViewById(R.id.contentTv);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);
            viewHolder.timeTv = convertView.findViewById(R.id.timeTv);
            viewHolder.statusTv = convertView.findViewById(R.id.statusTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AnswerBean bean = beans.get(position);

        viewHolder.nameTv.setText("问题:"+bean.getHcontent());
        viewHolder.contentTv.setText("回答:"+bean.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(new Date(bean.getCreatetime()));
        viewHolder.timeTv.setText(time);
        if(bean.getStatus()==0){
            viewHolder.statusTv.setText("审核状态:待审核");
        }else if(bean.getStatus()==1){
            viewHolder.statusTv.setText("审核状态:审核通过");
        }
        else if(bean.getStatus()==2){
            viewHolder.statusTv.setText("审核状态:审核不通过");
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView timeTv;
        private TextView contentTv;
        private TextView nameTv,statusTv;
    }


}
