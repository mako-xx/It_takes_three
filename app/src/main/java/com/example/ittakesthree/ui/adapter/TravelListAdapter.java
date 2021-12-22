package com.example.ittakesthree.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.TravelBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TravelListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<TravelBean> beans;
    private  ZanIF zanIF;
    public TravelListAdapter(Activity context, List<TravelBean> beans,ZanIF zanIF) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.beans = beans;
        this.zanIF=zanIF;
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
            convertView = inflater.inflate(R.layout.adapter_travel_listitem, null);
            viewHolder.iv = convertView.findViewById(R.id.iv);
            viewHolder.titleTv = convertView.findViewById(R.id.titleTv);
            viewHolder.contentTv = convertView.findViewById(R.id.contentTv);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);
            viewHolder.zanIv = convertView.findViewById(R.id.zanIv);
            viewHolder.timeTv = convertView.findViewById(R.id.timeTv);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TravelBean bean = beans.get(position);
        /*if(TextUtils.isEmpty(bean.getPic())){
            viewHolder.iv.setVisibility(View.GONE);
        }else{
            String[] pic=bean.getPic().split(",");
            viewHolder.iv.setVisibility(View.VISIBLE);
//            GlideLoadUtils imageLoader = new GlideLoadUtils((Activity) context);
//            imageLoader.loadImage(UrlConfig.BaseURL+pic[0], viewHolder.iv,true);
        }

        if(zanIF==null){
            viewHolder.zanIv.setVisibility(View.GONE);
            if(bean.getStatus()==0){
                viewHolder.nameTv.setText("审核状态:待审核");
            }else if(bean.getStatus()==1){
                viewHolder.nameTv.setText("审核状态:审核通过");
            }
            else if(bean.getStatus()==2){
                viewHolder.nameTv.setText("审核状态:审核不通过");
            }

        }else{
            viewHolder.zanIv.setVisibility(View.VISIBLE);
            if(bean.getIszan()>0){
                viewHolder.zanIv.setImageResource(R.mipmap.party_zan);
            }else{
                viewHolder.zanIv.setImageResource(R.mipmap.party_nozan);
            }
            viewHolder.zanIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    zanIF.zan(position);
                }
            });
            viewHolder.nameTv.setText("作者:"+bean.getUname());
        }*/
        viewHolder.iv.setImageResource(bean.getPic());
        viewHolder.titleTv.setText(bean.getTitle() );
        viewHolder.contentTv.setText(bean.getContent());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String time=simpleDateFormat.format(new Date(bean.getCreatetime()));
        viewHolder.timeTv.setText("时间:"+time);

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
