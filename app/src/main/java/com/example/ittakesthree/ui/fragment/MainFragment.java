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
import com.example.ittakesthree.data.TravelBean;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.main.travel.TravelDetailActivity;
import com.example.ittakesthree.ui.adapter.MyLunboPagerAdapter;
import com.example.ittakesthree.ui.adapter.TravelListAdapter;
import com.example.ittakesthree.ui.refresh.BasePullToRefreshView;
import com.example.ittakesthree.ui.refresh.PullToRefreshView;
import com.example.ittakesthree.ui.view.bezier.BezierBannerDot;

import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends Fragment implements BasePullToRefreshView.OnHeaderRefreshListener, BasePullToRefreshView.OnFooterRefreshListener, TravelListAdapter.ZanIF {
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

    public static ArrayList<TravelBean> beans = new ArrayList<>();
    private TravelListAdapter adapter;
    private String keyword="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        getData();
        return view;
    }

    protected void initView() {
        TextView titleTv = view.findViewById(R.id.titleTv);
        titleTv.setText("旅行攻略");
        rightTv = view.findViewById(R.id.rightTv);
        rightTv.setVisibility(View.VISIBLE);
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
        adapter = new TravelListAdapter(getActivity(), beans, this);
        listView.setAdapter(adapter);


        setListener();

    }

    protected void setListener() {
        pullToRefreshView.setOnFooterRefreshListener(this);
        pullToRefreshView.setOnHeaderRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //if (Constants.userBean.getType() == 1) {
                    Intent intent = new Intent(getActivity(), TravelDetailActivity.class);
                    intent.putExtra("id", beans.get(i).getId());
                    startActivity(intent);
                //}

            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.ittakesthree.ui.activity.main.travel.PostActivity.class);
                startActivity(intent);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword=searchEt.getText().toString();
                page=1;
                //getData();
            }
        });
    }

    private void getBanner() {
        int banners[] = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};
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
        //getData();
    }

    @Override
    public void onHeaderRefresh(BasePullToRefreshView view) {
        page = 1;
        view.onHeaderRefreshBegin();
        //getData();
    }


    private static void getData() {
//        HttpParams params = new HttpParams();
//        params.put("page", page);
//        params.put("limit", limit);
//        params.put("keyword",keyword);
//        params.put("uid", Constants.userBean.getId());
//        HttpTool.postList(UrlConfig.travel_list, params, new TypeToken<List<TravelBean>>() {
//        }.getType(), new HttpTool.HttpListener() {
//
//            @Override
//            public void onComplected(Object... result) {
//                pullToRefreshView.onFooterRefreshComplete();
//                pullToRefreshView.onHeaderRefreshComplete();
//                List<TravelBean> temp = (List<TravelBean>) result[0];
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
        TravelBean travelBean1 = new TravelBean();
        long time = new Date().getTime();
        travelBean1.setCreatetime(time);
        travelBean1.setContent(description[0]);
        travelBean1.setUname("jia yuan");
        travelBean1.setTitle("佛山");
        travelBean1.setPic(R.drawable.banner1);
        travelBean1.setId(11);
        beans.add(travelBean1);
    }


    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    @Override
    public void zan(int position) {

    }

    private static String[] description = {"洱海，古称昆明池、洱河、叶榆泽等。位于云南省大理白族自治州大理市。一般湖水面积约246平方千米（一说251平方公里），" +
            "蓄水量约29.5亿立方米，呈狭长形，北起洱源县南端，南止大理市下关，南北长40公里，是仅次于滇池的云南第二大湖，中国淡水湖中居第7位。洱海形成于冰河时代末期，" +
            "其成因主要是沉降侵蚀，属高原构造断陷湖泊，海拔1972米。\n" +
            "洱海，属澜沧江流域，系其支流漾濞江支流西洱河上源。湖水由西洱河流经大理市区下关，向西汇入漾濞江。\n" +
            "洱海具有供水、农灌、发电、调节气候、渔业、航运、旅游七大主要功能，洱海西面有点苍山横列如屏，东面有玉案山环绕衬托，空间环境优美。 [1] \n" +
            "1981年经云南省人民政府批准建立苍山洱海自然保护区，1994年晋升为国家级，主要保护对象为高原淡水湖泊及水生动植物、南北动植物过渡带自然景观、冰川遗迹，面积79700公顷。","" +
            "佛山，广东省辖地级市。 [1]  特大城市。 [104]  佛山地处广东省中部、珠三角腹地，毗邻港澳、" +
            "东接广州、南邻中山，与广州共同构成“广佛都市圈”，大力推进广佛同城化合作，打造国际大都市区，是珠江三角洲城市之一、粤港澳大湾区重要节点城市，" +
            "“广佛肇经济圈”、“珠江—西江经济带”的重要组成部分。行政区划面积3797.72平方千米。 [101]  " +
            "截至2018年，全市下辖5个区。根据第七次人口普查数据，截至2020年11月1日零时，佛山市常住人口为949.8863万人。 [99] \n" +
            "佛山是广府文化的核心区域，有粤剧、陶瓷、剪纸、秋色等传统文化，佛山是国家历史文化名城， [2-3]  历史上是中国天下四聚、四大名镇之一，" +
            "有陶艺之乡、武术之乡、粤剧之乡之称， [4]  是中国龙舟龙狮文化名城，粤剧发源地，广府文化发源地、兴盛地、传承地。"};


   /* @Override
    public void zan(int position) {
        ((BaseActivity) getActivity()).showLoadingDialog();
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        params.put("tid", beans.get(position).getId());
        HttpTool.postObject(beans.get(position).getIszan() > 0 ? UrlConfig.delZan : UrlConfig.zan, params, BaseBean.class, new HttpTool.HttpListener() {

            @Override
            public void onComplected(Object... result) {
                ((BaseActivity) getActivity()).hideLoadingDialog();
                page = 1;
                getData();
            }

            @Override
            public void onFailed(String msg) {
                ((BaseActivity) getActivity()).hideLoadingDialog();
                CommonTool.showToast(msg);
            }
        });
    }*/
}
