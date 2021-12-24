package com.example.ittakesthree.ui.activity.main.self;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ittakesthree.MainActivity;
import com.example.ittakesthree.MyApplication;
import com.example.ittakesthree.R;
import com.example.ittakesthree.dao.UserDao;
import com.example.ittakesthree.database.AppDatabase;
import com.example.ittakesthree.pojo.User;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.base.BaseActivity;
import com.example.ittakesthree.ui.activity.login.LoginActivity;
import com.example.ittakesthree.ui.activity.login.RegisterActivity;

import java.util.List;

public class ChangeInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView titleTv;
    private EditText accountEt;
    private EditText nameEt;
    private EditText phoneEt;
    private EditText editPass;
    private EditText editRepass;
    private TextView sex0;
    private TextView sex1;
    private TextView butOk;
    private boolean sex = false;//0男 1女
    private String account, name, phone, pwd, repwd;
    private AppDatabase db = AppDatabase.getInstance(this);

    @Override
    public int initLayout() {
        return R.layout.activity_change_info;
    }

    @Override
    protected void initView() {
        titleTv = findViewById(R.id.titleTv);
        titleTv.setText("修改资料");
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        editPass = findViewById(R.id.edit_pass);
        editRepass = findViewById(R.id.edit_repass);
        sex0 = findViewById(R.id.sex0);
        sex1 = findViewById(R.id.sex1);
        butOk = findViewById(R.id.but_ok);
        sex0.setOnClickListener(this);
        sex1.setOnClickListener(this);
        butOk.setOnClickListener(this);
        getInfo();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_ok) {
            regist();
        } else if (view.getId() == R.id.sex0) {
            sex0.setBackgroundResource(R.drawable.corner_choice);
            sex1.setBackgroundResource(R.drawable.corner_unchoice);
            sex = false;
        } else if (view.getId() == R.id.sex1) {
            sex0.setBackgroundResource(R.drawable.corner_unchoice);
            sex1.setBackgroundResource(R.drawable.corner_choice);
            sex = true;
        }
    }

    public void regist() {
        name = nameEt.getText().toString().trim();
        phone = phoneEt.getText().toString().trim();
        pwd = editPass.getText().toString().trim();
        repwd = editRepass.getText().toString().trim();



        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,"请输入昵称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"请输入电话", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this,"请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(repwd)) {
            Toast.makeText(this,"请输入确认密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(repwd)) {
            Toast.makeText(this,"密码两次输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }



        commit();

    }


    private void commit() {
        UserDao userDao = db.userDao();
        User user = userDao.loadUserByUid(MainActivity.uid);
        user.setNickname(name);
        user.setEmail(phone);
        user.setPassword(pwd);
        user.setSex(sex);
        userDao.save(user);
        MainActivity.uid = null;
        Toast.makeText(this,"修改成功，请重新登录", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ChangeInfoActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void getInfo() {
//        HttpParams params = new HttpParams();
//        params.put("id", Constants.userBean.getId() );
//        HttpTool.postObject(UrlConfig.getUser, params, UserResonseBean.class, new HttpTool.HttpListener() {
//            @Override
//            public void onComplected(Object... result) {
//                UserResonseBean bean = (UserResonseBean) result[0];
//                Constants.userBean=bean.data;
//                nameEt.setText(Constants.userBean.getName());
//                phoneEt.setText(Constants.userBean.getPhone());
//                editPass.setText(Constants.userBean.getPassword());
//                editRepass.setText(Constants.userBean.getPassword());
//                if (Constants.userBean.getSex() != null) {
//                    sex = Integer.parseInt(Constants.userBean.getSex());
//                    if (sex == 0) {
//                        sex0.setBackgroundResource(R.drawable.corner_choice);
//                        sex1.setBackgroundResource(R.drawable.corner_unchoice);
//                    } else {
//                        sex0.setBackgroundResource(R.drawable.corner_unchoice);
//                        sex1.setBackgroundResource(R.drawable.corner_choice);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailed(String msg) {
//                CommonTool.showToast(msg);
//            }
//        });

        String name = MainActivity.uid;
        RegisterActivity registerActivity = new RegisterActivity();
        UserDao userDao;

        AppDatabase appDatabase = registerActivity.getDb();
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        userDao = appDatabase.userDao();

        List<User> lists;
        lists = userDao.loadAll();
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).getUsername().equals(name)) {
                nameEt.setText(name);
                phoneEt.setText(lists.get(i).getEmail());
                editPass.setText(lists.get(i).getPassword());
                editRepass.setText(lists.get(i).getPassword());
                if(lists.get(i).isSex()){
                    sex0.setSelected(true);
                }else {
                    sex0.setBackgroundResource(R.drawable.corner_unchoice);
                    sex1.setBackgroundResource(R.drawable.corner_choice);
                }
                break;
            }
        }
    }

    @Override
    protected void initData() {

    }


}