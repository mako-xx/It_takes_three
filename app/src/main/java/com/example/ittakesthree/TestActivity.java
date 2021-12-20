package com.example.ittakesthree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.ittakesthree.dao.BaggageDao;
import com.example.ittakesthree.dao.CommentDao;
import com.example.ittakesthree.dao.UserDao;
import com.example.ittakesthree.database.AppDatabase;
import com.example.ittakesthree.pojo.Baggage;
import com.example.ittakesthree.pojo.Comment;
import com.example.ittakesthree.pojo.User;
import com.example.ittakesthree.pojo.UserWithBaggages;
import com.example.ittakesthree.pojo.UserWithComments;
import com.example.ittakesthree.util.DateUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private AppDatabase db;
    private UserDao userDao;
    private CommentDao commentDao;
    private BaggageDao baggageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        /*
        new Thread() {
            @Override
            public void run() {
                db = AppDatabase.getInstance(getApplicationContext());
                userDao = db.userDao();
                commentDao = db.commentDao();
                baggageDao = db.baggageDao();
                //userDao.save(new User("root", "123", "cxc", "12", new Date(), true, null));
                //final List<User> users = userDao.loadAll();
                //Log.e("TAG", users.get(0).getBirthday().toString());
                userDao.save(new User("root", "123", "cxc", "1", new Date(), false, null));
                final List<User> users = userDao.loadAll();
                User user = users.get(0);
                baggageDao.save(new Baggage("haoye", new Date(), user.getUid()));
                baggageDao.save(new Baggage("ooo", new Date(), user.getUid()));
                final UserWithBaggages userWithBaggages = userDao.loadUserBaggages(user.getUid());
                for (Baggage baggage : userWithBaggages.baggages) {
                    Log.e("INFO", "The user has " + baggage);
                }
            }
        }.start();

         */
        Log.e("TAG",DateUtil.dateToString(new Date()));


    }
}