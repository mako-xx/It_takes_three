package com.example.ittakesthree.ui.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ittakesthree.R;

import java.util.ArrayList;

public class DialogListViewAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private LayoutInflater inflater;
    private Context context;
    private int choice = -1;

    public DialogListViewAdapter(Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_dialog_listitem, null);
            holder.text = convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String bean = list.get(position);
        holder.text.setText(bean);
        if (choice != -1) {
            if (position == choice) {
                holder.text.setTextColor(context.getResources().getColor(R.color.black));
                holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            } else {
                holder.text.setTextColor(context.getResources().getColor(R.color.text_color_999));
                holder.text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            }
        }
        return convertView;
    }

    public void setChoice(int choice) {
        this.choice = choice;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView text;
    }
}
