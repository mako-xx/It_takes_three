package com.example.ittakesthree.ui.activity.main.self;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.AnswerBean;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.base.BaseListActivity;
import com.example.ittakesthree.ui.activity.main.help.HelpDetailActivity;
import com.example.ittakesthree.ui.adapter.MyAnswerListAdapter;
import com.example.ittakesthree.ui.view.dialog.CustomDialog;

import java.util.ArrayList;

public class MyAnswerListActivity extends BaseListActivity {

    private ArrayList<AnswerBean> beans = new ArrayList<>();
    MyAnswerListAdapter adapter;

    @Override
    protected void initView() {
        TextView textView = findViewById(R.id.titleTv);
        textView.setText("我解答的问题");
        super.initView();
        adapter = new MyAnswerListAdapter(this, beans);
        listView.setAdapter(adapter);
        pullToRefreshView.setIsFooter(false);
        findViewById(R.id.but_ok).setVisibility(View.GONE);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomDialog dialog = new CustomDialog(MyAnswerListActivity.this);
                dialog.bindCommonTipLayout("提示", "是否确认删除", "取消", "确认", new CustomDialog.DialogCommonFinishIF() {
                    @Override
                    public void onDialogFinished(Object... objects) {
                        int flag = Integer.parseInt(objects[0].toString());
                        if (flag == 2) {
                            del(beans.get(i).getId());
                        }
                    }
                });
                CommonTool.setDialog(MyAnswerListActivity.this, dialog, Gravity.CENTER, R.style.dialogWindowAnimButtomToTop, 1, 1);

                return true;
            }
        });
    }

    @Override
    protected void getData() {
//        HttpParams params = new HttpParams();
//        params.put("uid", Constants.userBean.getId());
//        HttpTool.postList(UrlConfig.getMyAnswer, params, new TypeToken<List<AnswerBean>>() {
//        }.getType(), new HttpTool.HttpListener() {
//
//            @Override
//            public void onComplected(Object... result) {
//                pullToRefreshView.onFooterRefreshComplete();
//                pullToRefreshView.onHeaderRefreshComplete();
//                List<AnswerBean> temp = (List<AnswerBean>) result[0];
//                if (temp == null || temp.size() == 0) CommonTool.showToast("没有更多了");
//                beans.clear();
//                beans.addAll(temp);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                pullToRefreshView.onFooterRefreshComplete();
//                pullToRefreshView.onHeaderRefreshComplete();
//                CommonTool.showToast(msg);
//            }
//        });
    }
    protected void del(int id) {
//        HttpParams params = new HttpParams();
//        params.put("id", id);
//        HttpTool.postObject(UrlConfig.delAnswer, params, BaseBean.class, new HttpTool.HttpListener() {
//
//            @Override
//            public void onComplected(Object... result) {
//                BaseBean baseBean= (BaseBean) result[0];
//                if(baseBean.code==0){
//                    CommonTool.showToast("删除成功");
//                    initData();
//                }else{
//                    CommonTool.showToast(baseBean.msg);
//                }
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                pullToRefreshView.onFooterRefreshComplete();
//                pullToRefreshView.onHeaderRefreshComplete();
//                CommonTool.showToast(msg);
//            }
//        });
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, HelpDetailActivity.class);
        intent.putExtra("id", beans.get(i).getHid());
        startActivity(intent);
    }

    @Override
    public int initLayout() {
        return 1;
    }
}
