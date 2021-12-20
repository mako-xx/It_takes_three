package com.example.ittakesthree.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ittakesthree.util.UUIDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 用户实体类 */
@Entity(indices = {@Index(value = {"username", "email"}, unique = true)}, tableName = "t_user")
public class User {
    /** 唯一标识码 */
    @PrimaryKey
    @NonNull
    private String uid;

    /** 用户名 */
    @ColumnInfo(name = "username")

    private String username;

    /** 密码 */
    @ColumnInfo(name = "password")
    private String password;

    /** 昵称 */
    @ColumnInfo(name = "nickname")
    private String nickname;

    /** 邮箱 */
    @ColumnInfo(name = "email")
    private String email;

    /** 生日 */
    @ColumnInfo(name = "birthday")
    private Date birthday;

    /** 签名 */
    @ColumnInfo(name = "signature")
    private String signature = "";

    /** 性别 */
    @ColumnInfo(name = "sex")
    private boolean sex;

    /** 角色 */
    @ColumnInfo(name = "role", defaultValue = "user")
    private String role;


    public User(String username, String password, String nickname, String email, Date birthday, boolean sex, String role) {
        uid = UUIDUtil.generateUID();
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.role = role;
    }

    /** 获取用户uid
     * @return 用户表示码
     * */
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /** 获取用户名
     * @return 用户名
     * */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     * @param username 新用户名
     * */
    public void setUsername(String username) {
        this.username = username;
    }

    /** 获取密码
     * @return 密码
     * */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password 新密码
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称
     * @return 昵称
     * */
    public String getNickname() {
        return nickname;
    }

    /** 设置昵称
     * @param nickname 新昵称
     * */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /** 获取电子邮箱
     * @return 电子邮箱
     * */
    public String getEmail() {
        return email;
    }

    /** 设置邮箱
     * @param email 新邮箱
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取生日
     * @return 生日
     * */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     * @param birthday 新生日
     * */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取签名
     * @return 当前签名
     * */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置个性签名
     * @param signature 新签名
     * */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
