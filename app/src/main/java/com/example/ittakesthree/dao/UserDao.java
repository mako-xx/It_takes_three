package com.example.ittakesthree.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.ittakesthree.pojo.User;
import com.example.ittakesthree.pojo.UserWithBaggages;
import com.example.ittakesthree.pojo.UserWithComments;
import com.example.ittakesthree.pojo.UserWithPhotos;

import java.util.List;

/** 用户dao层 */
@Dao
public interface UserDao {

    /** 插入任意数量用户
     * @param users 一组用户
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(User... users);

    @Insert
    void insertUserList(List<User> users);

    /**
     * 删除用户
     * @param user 指定用户
     * */
    @Delete
    void delete(User user);

    /**
     * 根据用户名删除用户
     * @param username 指定用户
     * */
    @Query("delete from t_user where username = :username")
    void deleteByUsername(String username);

    /** 删除一组用户 */
    @Delete
    void deleteUsers(List<User> users);

    /** 更新用户信息(没什么用，直接save即可) */
    @Update
    @Deprecated
    void updateUser(User user);

    /** 加载所有的用户
     * @return 数据库内所有用户的信息
     * */
    @Query("select * from t_user")
    List<User> loadAll();

    /** 根据用户名查询用户
     * @param username 用户名
     * @return 指定用户
     * */
    @Query("select * from t_user where username = :username")
    User loadUserByUsername(String username);

    /**
     * 根据uid查询用户
     * @param uid 用户id
     * @return 指定用户
     * */
    @Query("select * from t_user where uid = :uid")
    User loadUserByUid(String uid);

    /**
     * 查询用户发表的评论
     * @param uid 用户id
     * @return 用户评论
     * */
    @Query("select * from t_user where uid = :uid")
    @Transaction
    UserWithComments loadUserComments(String uid);

    /**
     * 查询用户相册
     * @param uid 用户id
     * @return 用户相册
     * */
    @Query("select * from t_user where uid = :uid")
    @Transaction
    UserWithPhotos loadUserPhotos(String uid);

    /** 加载用户的行李
     * @param uid 用户id
     * @return 用户的行李
     * */
    @Query("select * from t_user where uid = :uid")
    @Transaction
    UserWithBaggages loadUserBaggages(String uid);


}
