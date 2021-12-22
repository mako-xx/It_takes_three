package com.example.ittakesthree.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.GoodsBean;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.tools.Constants;
import com.example.ittakesthree.ui.activity.main.goods.GoodsDetailActivity;
import com.example.ittakesthree.ui.adapter.GoodsListAdapter;
import com.example.ittakesthree.ui.adapter.MyLunboPagerAdapter;
import com.example.ittakesthree.ui.refresh.BasePullToRefreshView;
import com.example.ittakesthree.ui.refresh.PullToRefreshView;
import com.example.ittakesthree.ui.view.bezier.BezierBannerDot;

import java.util.ArrayList;

public class GoodsFragment extends Fragment implements BasePullToRefreshView.OnHeaderRefreshListener, BasePullToRefreshView.OnFooterRefreshListener {
    View view;
    //广告图
    private ViewPager vp;
    private LinearLayout dot;
    private MyLunboPagerAdapter pagerAdapter;
    private ArrayList<ImageView> viewlist = new ArrayList<>();
    private boolean isRunning = true;

    protected PullToRefreshView pullToRefreshView;
    protected ListView listView;
    protected int page = 1, limit = 10;
    private TextView rightTv;
    private EditText searchEt;
    private TextView searchBtn;
    private String keyword = "";
    private ArrayList<GoodsBean> beans = new ArrayList<>();
    private GoodsListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        getData();
        return view;
    }

    protected void initView() {
        rightTv = view.findViewById(R.id.rightTv);
        rightTv.setVisibility(View.GONE);
        TextView titleTv = view.findViewById(R.id.titleTv);
        titleTv.setText("亲子商城");

        vp = view.findViewById(R.id.vp);
        dot = view.findViewById(R.id.dot);
        searchEt = view.findViewById(R.id.searchEt);
        searchBtn = view.findViewById(R.id.searchBtn);
        pagerAdapter = new MyLunboPagerAdapter(viewlist);
        vp.setAdapter(pagerAdapter);
        getBanner();
        beginLunbo();
        pullToRefreshView = view.findViewById(R.id.pullToRefreshView);
        listView = view.findViewById(R.id.listView);
        adapter = new GoodsListAdapter(getActivity(), beans);
        listView.setAdapter(adapter);


        setListener();

    }

    protected void setListener() {
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Constants.userBean.getType() == 1) {
                    Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                    intent.putExtra("bean", beans.get(i));
                    startActivity(intent);
                }

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = searchEt.getText().toString();
                page = 1;
                getData();
            }
        });
    }

    private void getBanner() {
        int banners[] = {R.drawable.banner8, R.drawable.banner9, R.drawable.banner10};
        for (int i = 0; i < banners.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(banners[i]);
            viewlist.add(imageView);
        }
        pagerAdapter.notifyDataSetChanged();
        if (viewlist.size() > 1) {
            dot.setVisibility(View.VISIBLE);
            BezierBannerDot bd = new BezierBannerDot(getActivity(), getResources().getColor(R.color.white), getResources().getColor(R.color.text_color_999));
            bd.setRadius(CommonTool.dp2px(6), CommonTool.dp2px(6));
            bd.setDistance(CommonTool.dp2px(10));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            dot.removeAllViews();
            dot.addView(bd, layoutParams);
            bd.attachToViewpager(vp);
        } else {
            dot.setVisibility(View.GONE);
        }

    }

    private void beginLunbo() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (viewlist.size() > 0)
                    vp.setCurrentItem((vp.getCurrentItem() + 1) % viewlist.size());
            }
        };
        // 开启轮询
        Thread thread = new Thread() {
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //下一条
                    handler.sendEmptyMessage(1);
                }
            }
        };
        thread.start();
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


    private void getData() {
//        HttpParams params = new HttpParams();
//        params.put("page", page);
//        params.put("limit", limit);
//        params.put("keyword", keyword);
//        params.put("uid", Constants.userBean.getId());
//        HttpTool.postList(UrlConfig.goods_list, params, new TypeToken<List<GoodsBean>>() {
//        }.getType(), new HttpTool.HttpListener() {
//
//            @Override
//            public void onComplected(Object... result) {
//                pullToRefreshView.onFooterRefreshComplete();
//                pullToRefreshView.onHeaderRefreshComplete();
//                List<GoodsBean> temp = (List<GoodsBean>) result[0];
//                if (temp == null || temp.size() == 0) CommonTool.showToast("没有更多了");
//                if (page == 1) beans.clear();
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


    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }


}