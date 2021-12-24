package com.example.ittakesthree.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.dao.UserDao;
import com.example.ittakesthree.data.CommentBean;
import com.example.ittakesthree.database.AppDatabase;
import com.example.ittakesthree.pojo.Comment;
import com.example.ittakesthree.pojo.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<Comment> comments;
    private AppDatabase appDatabase;
    private UserDao userDao;

    public CommentListAdapter(Activity context, List<Comment> comments) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.comments = comments;
        appDatabase = AppDatabase.getInstance(context);
        userDao = appDatabase.userDao();
    }

    @Override
    public int getCount() {
        return comments.size();
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
            viewHolder.contentTv = convertView.findViewById(R.id.nameTv);
            viewHolder.nameTv = convertView.findViewById(R.id.contentTv);
            viewHolder.timeTv = convertView.findViewById(R.id.timeTv);
            viewHolder.scoreTv = convertView.findViewById(R.id.scoreTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Comment comment = comments.get(position);

        User user = userDao.loadUserByUid(comment.getAuthor());
        viewHolder.nameTv.setText(user.getUsername());
        viewHolder.contentTv.setText(comment.getContent());
        viewHolder.scoreTv.setText(comment.getScore() + "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(comment.getPublish());
        viewHolder.timeTv.setText(time);
        return convertView;
    }


    private class ViewHolder {
        private TextView timeTv;
        private TextView contentTv;
        private TextView nameTv;
        private TextView scoreTv;
    }
}
