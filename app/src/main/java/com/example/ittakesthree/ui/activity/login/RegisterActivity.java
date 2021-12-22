package com.example.ittakesthree.ui.activity.login;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ittakesthree.R;
import com.example.ittakesthree.dao.BaggageDao;
import com.example.ittakesthree.dao.CommentDao;
import com.example.ittakesthree.dao.UserDao;
import com.example.ittakesthree.database.AppDatabase;
import com.example.ittakesthree.pojo.User;
import com.example.ittakesthree.tools.CommonTool;
import com.example.ittakesthree.ui.activity.base.BaseActivity;

import java.util.List;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


    private AppDatabase db;
    private UserDao userDao;
    private CommentDao commentDao;
    private BaggageDao baggageDao;

    private TextView titleTv;
    private EditText accountEt;
    private EditText nameEt;
    private EditText phoneEt;
    private EditText editPass;
    private EditText editRepass;
    private TextView sex0;
    private TextView sex1;
    private TextView butOk;
    private int sex = 0;//0男 1女
    private String account, name, phone, pwd, repwd;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regieter);
//    }

    @Override
    public int initLayout() {
        return R.layout.activity_regieter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        titleTv = findViewById(R.id.titleTv);

        titleTv.setText("注册");
        /**
         * ni chen
         */
        accountEt = findViewById(R.id.accountEt);
        /**
         * yong hu ming
         */
        nameEt = findViewById(R.id.nameEt);
        /**
         * phone
         */
        phoneEt = findViewById(R.id.phoneEt);
        /**
         * password
         */
        editPass = findViewById(R.id.edit_pass);
        /**
         * re password
         */
        editRepass = findViewById(R.id.edit_repass);
        /**
         * sex
         */
        sex0 = findViewById(R.id.sex0);
        sex1 = findViewById(R.id.sex1);
        butOk = findViewById(R.id.but_ok);
        sex0.setOnClickListener(this);
        sex1.setOnClickListener(this);
        butOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_ok) {
            regist();
        } else if (view.getId() == R.id.sex0) {
            sex0.setBackgroundResource(R.drawable.corner_choice);
            sex1.setBackgroundResource(R.drawable.corner_unchoice);
            sex = 0;
        } else if (view.getId() == R.id.sex1) {
            sex0.setBackgroundResource(R.drawable.corner_unchoice);
            sex1.setBackgroundResource(R.drawable.corner_choice);
            sex = 1;
        }
    }

    public void regist() {
        account = accountEt.getText().toString().trim();
        name = nameEt.getText().toString().trim();
        phone = phoneEt.getText().toString().trim();
        pwd = editPass.getText().toString().trim();
        repwd = editRepass.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            CommonTool.showToast("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            CommonTool.showToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            CommonTool.showToast("请输入电话");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            CommonTool.showToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(repwd)) {
            CommonTool.showToast("请输入确认密码");
            return;
        }
        if (!pwd.equals(repwd)) {
            CommonTool.showToast("密码两次输入不一致");
            return;
        }
        boolean flag;
        if(sex == 0){
            flag = true;
        }else {
            flag = false;
        }
        User user = new User(name,pwd,account,phone,null,flag,null);

        db = AppDatabase.getInstance(getApplicationContext());
        userDao = db.userDao();
        userDao.save(user);

        List<User> lists;
        lists = userDao.loadAll();
        for (int i = 0;i<lists.size();i++){
            Log.e("0",lists.get(i).toString());
        }

        Toast.makeText(this,"successfully---------------------",Toast.LENGTH_LONG).show();

        finish();

    }

    public AppDatabase getDb() {
        return db;
    }
}