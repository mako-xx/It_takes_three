package com.example.ittakesthree.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ittakesthree.util.UUIDUtil;

import java.util.Date;

/** 行李类 */
@Entity(tableName = "t_baggage")
public class Baggage {

    @PrimaryKey
    @NonNull
    /** 标识码 */
    private String bid;

    /** 名称 */
    @ColumnInfo(name = "name")
    private String name;

    /** 入库时间 */
    @ColumnInfo(name = "date")
    private Date date;

    /** 用户 */
    @ColumnInfo(name = "uid")
    private String uid;

    public Baggage(String name, Date date, String uid) {
        bid = UUIDUtil.generateUID();
        this.name = name;
        this.date = date;
        this.uid = uid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Baggage{" +
                "bid='" + bid + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", uid='" + uid + '\'' +
                '}';
    }
}
