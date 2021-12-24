package com.example.ittakesthree.ui.activity.main.travel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ittakesthree.R;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.base.BaseActivity;

public class PostCommentActivity extends BaseActivity implements View.OnClickListener {

    private TextView common_title;
    private EditText contentEt;
    private EditText scoreEt;
    private TextView isanoy;
    private TextView noanoy;
    private TextView isstrategy;
    private TextView nostrategy;
    private TextView commitBtn;
    private boolean anonymous;
    private boolean strategy;

    @Override
    public int initLayout() {
        return R.layout.activity_post_comment;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initView() {
        common_title = (TextView) findViewById(R.id.titleTv);
        common_title.setText("评论");
        contentEt = (EditText) findViewById(R.id.contentEt);
        commitBtn = (TextView) findViewById(R.id.commitBtn);
        scoreEt = (EditText) findViewById(R.id.scoreEt);
        isanoy = (TextView) findViewById(R.id.yesHideTv);
        noanoy = (TextView) findViewById(R.id.noHideTv);
        isstrategy = findViewById(R.id.yes_strategy);
        nostrategy = findViewById(R.id.no_strategy);
        setListener();
    }


    public void setListener() {
        commitBtn.setOnClickListener(this);
        isanoy.setOnClickListener(this);
        noanoy.setOnClickListener(this);
        isstrategy.setOnClickListener(this);
        nostrategy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.yesHideTv)
        {
            isanoy.setBackgroundResource(R.drawable.corner_choice);
            noanoy.setBackgroundResource(R.drawable.corner_unchoice);
            anonymous = true;
        }
        else if(view.getId() == R.id.noHideTv)
        {
            isanoy.setBackgroundResource(R.drawable.corner_unchoice);
            noanoy.setBackgroundResource(R.drawable.corner_choice);
            anonymous = false;
        }

        else if(view.getId() == R.id.yes_strategy)
        {
            isstrategy.setBackgroundResource(R.drawable.corner_choice);
            nostrategy.setBackgroundResource(R.drawable.corner_unchoice);
            strategy = true;
        }

        else if(view.getId() == R.id.no_strategy)
        {
            isstrategy.setBackgroundResource(R.drawable.corner_unchoice);
            nostrategy.setBackgroundResource(R.drawable.corner_choice);
            strategy = false;
        }

        else if(view.getId() == R.id.commitBtn)
        {
            String content = contentEt.getText().toString();
            String score = scoreEt.getText().toString();
            if(TextUtils.isEmpty(content)){
                Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(score)){
                Toast.makeText(this,"请输入打分",Toast.LENGTH_SHORT).show();
                return;
            }
            if(Double.parseDouble(score)>5 || Double.parseDouble(score) < 0){
                Toast.makeText(this,"评分范围是0-5",Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = getIntent();
            intent.putExtra("content", content);
            intent.putExtra("score", score);
            intent.putExtra("anonymous", anonymous);
            intent.putExtra("strategy", strategy);
            setResult(2, intent);
            finish();
        }
//


    }


    /*private void commit() {
        String  content = contentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            CommonTool.showToast("请输入内容");
            return;
        }
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        params.put("content", content);
        params.put("tid", getIntent().getIntExtra("id",-1));
        showLoadingDialog();
        HttpTool.postObject(UrlConfig.addComment, params, BaseBean.class, new HttpTool.HttpListener() {
            //            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    CommonTool.showToast("评论成功,等待管理员审核！");
                    setResult(1);
                    finish();
                } else {
                    CommonTool.showToast(bean.msg);
                }
            }

            @Override
            public void onFailed(String msg) {
                hideLoadingDialog();
                CommonTool.showToast(msg);
            }
        });
    }*/


    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        super.onDestroy();
    }
}