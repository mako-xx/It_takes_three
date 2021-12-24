package com.example.ittakesthree.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.ProvidedTypeConverter;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ittakesthree.convert.Converter;
import com.example.ittakesthree.dao.BaggageDao;
import com.example.ittakesthree.dao.CommentDao;
import com.example.ittakesthree.dao.PhotoDao;
import com.example.ittakesthree.dao.UserDao;
import com.example.ittakesthree.pojo.Baggage;
import com.example.ittakesthree.pojo.Comment;
import com.example.ittakesthree.pojo.Photo;
import com.example.ittakesthree.pojo.User;

/** 数据库 */
@Database(entities = {User.class, Comment.class, Photo.class, Baggage.class}, version = 3, exportSchema = false)
@TypeConverters({Converter.class})

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final String DB_NAME = "data.db";

    public static synchronized AppDatabase getInstance(Context context)
    {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract UserDao userDao();

    public abstract CommentDao commentDao();

    public abstract PhotoDao photoDao();

    public abstract BaggageDao baggageDao();
}
