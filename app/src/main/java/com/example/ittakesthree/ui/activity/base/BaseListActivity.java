package com.example.ittakesthree.ui.activity.base;

import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.ui.refresh.BasePullToRefreshView;
import com.example.ittakesthree.ui.refresh.PullToRefreshView;

public abstract class BaseListActivity extends BaseActivity implements BasePullToRefreshView.OnHeaderRefreshListener, BasePullToRefreshView.OnFooterRefreshListener, AdapterView.OnItemClickListener {
    protected PullToRefreshView pullToRefreshView;
    protected ListView listView;
    protected int page = 1, limit = 10;

    @Override
    protected void initView() {
        pullToRefreshView = findViewById(R.id.pullToRefreshView);
        listView = findViewById(R.id.listView);
        setListener();

    }


    @Override
    protected void initData() {
        getData();
    }

    protected void setListener() {
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onFooterRefresh(BasePullToRefreshView view) {
        page++;
        getData();
    }

    @Override
    public void onHeaderRefresh(BasePullToRefreshView view) {
        page = 1;
        view.onHeaderRefreshBegin();
        getData();
    }

    protected abstract void getData();
}

