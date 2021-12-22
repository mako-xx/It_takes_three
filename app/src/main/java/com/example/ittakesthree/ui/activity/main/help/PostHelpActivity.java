package com.example.ittakesthree.ui.activity.main.help;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.base.BaseActivity;

public class PostHelpActivity extends BaseActivity implements View.OnClickListener {
    private TextView common_title;
    private EditText contentEt;
    private TextView commitBtn;

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
        common_title.setText("发帖求助");
        contentEt = (EditText) findViewById(R.id.contentEt);
        commitBtn = (TextView) findViewById(R.id.commitBtn);
        setListener();
    }


    public void setListener() {
        commitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.commitBtn) {
            commit();
        }
    }


    private void commit() {
        String  content = contentEt.getText().toString();
        if (TextUtils.isEmpty(content)) {
            CommonTool.showToast("请输入内容");
            return;
        }
//        HttpParams params = new HttpParams();
//        params.put("uid", Constants.userBean.getId());
//        params.put("content", content);
//        showLoadingDialog();
//        HttpTool.postObject(UrlConfig.addHelp, params, BaseBean.class, new HttpTool.HttpListener() {
//            //            @Override
//            public void onComplected(Object... result) {
//                BaseBean bean = (BaseBean) result[0];
//                if (bean.code == 0) {
//                    CommonTool.showToast("发布成功,等待管理员审核！");
//                    setResult(1);
//                    finish();
//                } else {
//                    CommonTool.showToast(bean.msg);
//                }
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                hideLoadingDialog();
//                CommonTool.showToast(msg);
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        hideLoadingDialog();
        super.onDestroy();
    }

}
