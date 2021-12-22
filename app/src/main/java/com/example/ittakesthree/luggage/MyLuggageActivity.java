package com.example.ittakesthree.luggage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.example.ittakesthree.MyApplication;
import com.example.ittakesthree.luggage.CheckBoxAdapter.ViewCache;
import com.example.ittakesthree.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MyLuggageActivity extends Activity implements OnClickListener {

    private TextView tvSelected;
    private Button btnFan;
    private ListView lv;
    private List<HashMap<String, Object>> list;
    private CheckBoxAdapter cbAdapter;
    private List<String> listStr = new ArrayList<String>();

    private static final String[] m={"D1","D2","D3","D4","D5"};
    private TextView view ;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_luggage);
//        下拉框
        view = (TextView) findViewById(R.id.spinnerText);
        spinner = (Spinner) findViewById(R.id.Spinner01);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

        InitViews();
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            view.setText("选择历史记录："+m[arg2]);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void InitViews() {
        tvSelected = (TextView) findViewById(R.id.tvselected);
        btnFan = (Button) findViewById(R.id.btn_delete);
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<HashMap<String, Object>>();

//       在这里读入数据
        luggageList = MyApplication.luggageItemList;
        if (luggageList != null) {
            for (int i = 0; i < luggageList.size(); i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", luggageList.get(i));
                map.put("boolean", false);//初始化为未选
                list.add(map);
            }
        }//初始化数据

        cbAdapter = new CheckBoxAdapter(this, list);
        lv.setAdapter(cbAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewCache viewCache = (ViewCache) view.getTag();
                viewCache.cb.toggle();
                list.get(position).put("boolean", viewCache.cb.isChecked());

                cbAdapter.notifyDataSetChanged();

                if (viewCache.cb.isChecked()) {//被选中状态
                    listStr.add(list.get(position).get("name").toString());
                } else//从选中状态转化为未选中
                {
                    listStr.remove(list.get(position).get("name").toString());
                }

                tvSelected.setText("已选择了:" + listStr.size() + "项");
            }
        });

        btnFan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                for (int i = 0; i < list.size(); i++) {
                    if ((Boolean) list.get(i).get("boolean")) {//为true
                        listStr.remove(list.get(i).get("name").toString());
                        list.remove(i);
                        i--;
                    }
                }
                cbAdapter.notifyDataSetChanged();
                tvSelected.setText("已选择了:" + listStr.size() + "项");
                break;
        }
    }

    public void scanLuggage(View view)
    {
        Intent intent = new Intent(this, ClassifierActivity.class);
        startActivityForResult(intent, REQUEST_GET_LUGGAGE_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_LUGGAGE_LIST && resultCode == RESULT_OK)
        {
            state = data.getIntExtra("LIST", 0);
            //
            InitViews();
            //
        }
    }

    static final int REQUEST_GET_LUGGAGE_LIST = 1;
    static int state = 0;
    ArrayList<String> luggageList;
}