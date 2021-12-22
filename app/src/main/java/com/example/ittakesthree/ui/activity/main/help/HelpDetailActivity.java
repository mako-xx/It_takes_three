package com.example.ittakesthree.ui.activity.main.help;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.AnswerBean;
import com.example.ittakesthree.data.HelpBean;
import com.example.ittakesthree.ui.activity.base.BaseActivity;
import com.example.ittakesthree.ui.adapter.AnswerListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HelpDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleTv;
    private ListView listView;
    private TextView but;
    private TextView nameTv;
    private TextView contentTv;
    private TextView ctimeTv;
    private int id;
    private HelpBean helpBean;
    private AnswerListAdapter adapter;
    private ArrayList<AnswerBean> beans = new ArrayList<>();

    @Override
    public int initLayout() {
        return R.layout.activity_traveldetail;
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("id", 0);
        titleTv = findViewById(R.id.titleTv);
        titleTv.setText("求助详情");

        View header = LayoutInflater.from(this).inflate(R.layout.header_helpdetail, null);
        nameTv = header.findViewById(R.id.nameTv);
        ctimeTv = header.findViewById(R.id.ctimeTv);
        contentTv = header.findViewById(R.id.contentTv);


        listView = findViewById(R.id.listView);
        adapter = new AnswerListAdapter(this, beans);
        listView.setAdapter(adapter);
        listView.addHeaderView(header);
        but = findViewById(R.id.but);
        but.setText("我要解答");


        but.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but:
                Intent intent = new Intent(this, PostAnswerActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 1) {
            initData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initData() {
//        HttpParams params = new HttpParams();
//        params.put("id", id);
//        HttpTool.postObject(UrlConfig.getHelpDetail, params, HelpResonseBean.class, new HttpTool.HttpListener() {
//            @Override
//            public void onComplected(Object... result) {
//                HelpResonseBean bean = (HelpResonseBean) result[0];
//                helpBean = bean.data;
//                bindData();
//                getAnswer();
//            }

//            @Override
//            public void onFailed(String msg) {
//                CommonTool.showToast(msg);
            //}
        //});
    }

    protected void getAnswer() {
//        HttpParams params = new HttpParams();
//        params.put("hid", id);
//        HttpTool.postList(UrlConfig.listByHid, params, new TypeToken<List<AnswerBean>>() {
//        }.getType(), new HttpTool.HttpListener() {
//            @Override
//            public void onComplected(Object... result) {
//                List<AnswerBean> temp = (List<AnswerBean>) result[0];
//                beans.clear();
//                beans.addAll(temp);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                CommonTool.showToast(msg);
//            }
//        });
    }

    private void bindData() {
        if (helpBean == null) return;
        contentTv.setText(helpBean.getContent());
        nameTv.setText("求助者:" + helpBean.getUname());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ctime = simpleDateFormat.format(new Date(helpBean.getCreatetime()));
        ctimeTv.setText(ctime);
    }




}