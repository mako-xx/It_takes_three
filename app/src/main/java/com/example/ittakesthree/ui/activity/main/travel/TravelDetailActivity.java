package com.example.ittakesthree.ui.activity.main.travel;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.example.ittakesthree.MainActivity;
import com.example.ittakesthree.MyApplication;
import com.example.ittakesthree.R;
import com.example.ittakesthree.WeatherSearchActivity;
import com.example.ittakesthree.dao.CommentDao;
import com.example.ittakesthree.data.CommentBean;
import com.example.ittakesthree.data.TravelBean;
import com.example.ittakesthree.database.AppDatabase;
import com.example.ittakesthree.map.RouteMapActivity;
import com.example.ittakesthree.pojo.Comment;
import com.example.ittakesthree.pojo.Contentlist;
import com.example.ittakesthree.pojo.PicList;
import com.example.ittakesthree.ui.activity.base.BaseActivity;
import com.example.ittakesthree.ui.adapter.CommentListAdapter;
import com.example.ittakesthree.ui.adapter.MyLunboPagerAdapter;
import com.example.ittakesthree.ui.fragment.MainFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@GlideModule
public class TravelDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleTv;
    private TextView rightTv;
    private TextView line;
    private ListView listView;
    private TextView but;
    private RelativeLayout bannerLayout;
    private ViewPager vp;
    private LinearLayout dot;
    private TextView nameTv;
    private TextView contentTv;
    private TextView ctimeTv;
    private TextView biaotiTv;
    private TextView navi;
    private TextView weather;
    private ImageView zanIv;
    private String id;
    private Contentlist spot;
    private MyLunboPagerAdapter pagerAdapter;
    private ArrayList<ImageView> viewlist = new ArrayList<>();
    private boolean isRunning = true;
    private CommentListAdapter adapter;
    private static ArrayList<Comment> comments = new ArrayList<>();
    public static String spotName = "";

    @Override
    public int initLayout() {
        return R.layout.activity_traveldetail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {



        id = getIntent().getStringExtra("id");
        titleTv = findViewById(R.id.titleTv);
        titleTv.setText("景点详情");

        View header = LayoutInflater.from(this).inflate(R.layout.header_traveldetail, null);
        bannerLayout = header.findViewById(R.id.bannerLayout);

        vp = header.findViewById(R.id.vp);
        dot = header.findViewById(R.id.dot);
        biaotiTv = header.findViewById(R.id.biaotiTv);
        nameTv = header.findViewById(R.id.nameTv);
        ctimeTv = header.findViewById(R.id.ctimeTv);
        contentTv = header.findViewById(R.id.contentTv);
        zanIv = header.findViewById(R.id.zanIv);
        listView = findViewById(R.id.listView);
        adapter = new CommentListAdapter(this, comments);
        listView.setAdapter(adapter);
        listView.addHeaderView(header);
        but = findViewById(R.id.but);
        navi = findViewById(R.id.navigate);
        weather = findViewById(R.id.weather);
        bindData();
        getBanner();
        pagerAdapter = new MyLunboPagerAdapter(viewlist);

        vp.setAdapter(pagerAdapter);
        zanIv.setOnClickListener(this);
        but.setOnClickListener(this);
        navi.setOnClickListener(this);
        weather.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but:
                Intent intent = new Intent(this, PostCommentActivity.class);
                intent.putExtra("id", id);
                //intent.putExtra("author", )
                startActivityForResult(intent, 1);

                break;

            case R.id.navigate:
                Intent intent1 = new Intent(this, RouteMapActivity.class);
                //MainFragment mainFragment =
                double endLat = Double.parseDouble(spot.getLocation().getLat());
                double endLon = Double.parseDouble(spot.getLocation().getLon());
                double startLat = MainFragment.getLatitude();
                double startLon = MainFragment.getLatitude();
                intent1.putExtra("endLat", endLat);
                intent1.putExtra("endLon", endLon);
                intent1.putExtra("startLat", MainFragment.getLatitude());
                intent1.putExtra("startLon", MainFragment.getLongitude());
                startActivity(intent1);
                break;

            case R.id.weather:
                Intent intent2 = new Intent(this, WeatherSearchActivity.class);

                intent2.putExtra("cityId", spot.getProId());
                intent2.putExtra("cityName", (spot.getCityName() == null ? spot.getProName() : spot.getCityName()) + "市");
                startActivity(intent2);
                break;



        }
    }

    /*private void zan() {
        showLoadingDialog();
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        params.put("tid", travelBean.getId());
        HttpTool.postObject(travelBean.getIszan() > 0 ? UrlConfig.delZan : UrlConfig.zan, params, BaseBean.class, new HttpTool.HttpListener() {

            @Override
            public void onComplected(Object... result) {
                hideLoadingDialog();
                initData();
            }

            @Override
            public void onFailed(String msg) {
                hideLoadingDialog();
                CommonTool.showToast(msg);
            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == 2) {
            String content = data.getStringExtra("content");
            short score = Short.parseShort(data.getStringExtra("score"));
            boolean anonymous = data.getBooleanExtra("anonymous", false);
            boolean startegy = data.getBooleanExtra("strategy", false);
            Comment comment = new Comment(content, score, MainActivity.uid, anonymous, spot.getName(), startegy);
            AppDatabase db = AppDatabase.getInstance(this);
            CommentDao commentDao = db.commentDao();
            commentDao.save(comment);
            if(!comment.isAnonymous())
                comments.add(comment);
            pagerAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*
    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        params.put("id", id);
        HttpTool.postObject(UrlConfig.getTravelDetail, params, TravelResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                TravelResonseBean bean = (TravelResonseBean) result[0];
                travelBean = bean.data;
                bindData();
                getComment();
            }

            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }*/
    /*
    protected void getComment() {
        HttpParams params = new HttpParams();
        params.put("tid", id);
        HttpTool.postList(UrlConfig.listByTid, params, new TypeToken<List<CommentBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<CommentBean> temp = (List<CommentBean>) result[0];
                beans.clear();
                beans.addAll(temp);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }*/


   private void bindData() {

       if(id == null || id.equals(""))
           return;

       for (int i = 0;i< MainFragment.spots.size();i++){
           if(MainFragment.spots.get(i).getId().equals(id)){
               spot = MainFragment.spots.get(i);
               break;
           }
       }

       if (spot == null) return;
       //getBanner();
       //beginLunbo();
       biaotiTv.setText(spot.getName());
       contentTv.setText(spot.getSummary());
       nameTv.setText("地址:" + spot.getAddress());
       //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
       //String ctime = simpleDateFormat.format(new Date(travelBean.getCreatetime()));
       //ctimeTv.setText(ctime);
     /*  if (travelBean.getIszan() > 0) {
           zanIv.setImageResource(R.mipmap.party_zan);
       } else {
           zanIv.setImageResource(R.mipmap.party_nozan);
       }*/
   }


//    private void getBanner() {
//        if (TextUtils.isEmpty(travelBean.getPic())) {
//            bannerLayout.setVisibility(View.GONE);
//        } else {
//            bannerLayout.setVisibility(View.VISIBLE);
//            String[] path = travelBean.getPic().split(",");
//            for (int i = 0; i < path.length; i++) {
//                ImageView imageView = new ImageView(this);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                GlideLoadUtils imageLoader = new GlideLoadUtils(this);
//                imageLoader.loadImage(UrlConfig.BaseURL + path[i], imageView, true);
//                viewlist.add(imageView);
//            }
//            pagerAdapter.notifyDataSetChanged();
//            if (viewlist.size() > 1) {
//                dot.setVisibility(View.VISIBLE);
//                BezierBannerDot bd = new BezierBannerDot(this, getResources().getColor(R.color.white), getResources().getColor(R.color.text_color_999));
//                bd.setRadius(CommonTool.dp2px(6), CommonTool.dp2px(6));
//                bd.setDistance(CommonTool.dp2px(10));
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.gravity = Gravity.CENTER;
//                dot.removeAllViews();
//                dot.addView(bd, layoutParams);
//                bd.attachToViewpager(vp);
//            } else {
//                dot.setVisibility(View.GONE);
//            }
//
//
//        }
//
//
//    }

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

    private void getBanner() {
        int banners[] = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4};
        List<PicList> picLists = spot.getPicList();
        int num = Math.min(picLists.size(), 10);
        if(picLists != null && picLists.size() > 0) {
            for (int i = 0; i < num; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //    imageView.setImageResource(banners[i]);
                Glide.with(this).load(picLists.get(i).getPicUrl()).into(imageView);
                viewlist.add(imageView);
            }
        }
        else
        {
            for(int i = 0; i < banners.length; i++)
            {
                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(banners[i]);
                viewlist.add(imageView);
            }
        }
//        pagerAdapter.notifyDataSetChanged();
//        if (viewlist.size() > 1) {
//            dot.setVisibility(View.VISIBLE);
//            BezierBannerDot bd = new BezierBannerDot(this, getResources().getColor(R.color.white), getResources().getColor(R.color.text_color_999));
//            bd.setRadius(CommonTool.dp2px(6), CommonTool.dp2px(6));
//            bd.setDistance(CommonTool.dp2px(10));
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.gravity = Gravity.CENTER;
//            dot.removeAllViews();
//            dot.addView(bd, layoutParams);
//            bd.attachToViewpager(vp);
//        } else {
//            dot.setVisibility(View.GONE);
//        }

    }


    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }
}