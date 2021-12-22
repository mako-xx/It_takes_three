package com.example.ittakesthree.ui.activity.main.goods;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.ittakesthree.R;
import com.example.ittakesthree.data.GoodsBean;
import com.example.ittakesthree.ui.activity.base.BaseActivity;

public class GoodsDetailActivity extends BaseActivity {
    private TextView titleTv;
    private ImageView iv;
    private TextView nameTv;
    private TextView priceTv;
    private TextView numberTv;
    private TextView fenleiTv;
    private TextView changjiaTv;
    private TextView contentTv;

    private GoodsBean goodsBean;

    @Override
    public int initLayout() {
        return R.layout.activity_goodsdetail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("bean");
        titleTv = findViewById(R.id.titleTv);
        titleTv.setText("商品详情");
        iv = findViewById(R.id.iv);
        nameTv = findViewById(R.id.nameTv);
        priceTv = findViewById(R.id.priceTv);
        numberTv = findViewById(R.id.numberTv);
        fenleiTv = findViewById(R.id.fenleiTv);
        changjiaTv = findViewById(R.id.changjiaTv);
        contentTv = findViewById(R.id.contentTv);
//        GlideLoadUtils imageLoader = new GlideLoadUtils(this);
//        imageLoader.loadImage(UrlConfig.BaseURL+goodsBean.getPic(), iv,true);
        nameTv.setText(goodsBean.getName());
        priceTv.setText("¥"+goodsBean.getPrice());
        numberTv.setText("库存:"+goodsBean.getNumber());
        fenleiTv.setText("分类:"+goodsBean.getFname());
        changjiaTv.setText("厂家:"+goodsBean.getChangjia());
        contentTv.setText(goodsBean.getContent());
    }






}