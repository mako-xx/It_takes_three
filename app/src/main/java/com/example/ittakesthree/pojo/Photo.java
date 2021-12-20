package com.example.ittakesthree.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.ittakesthree.util.UUIDUtil;

import java.util.Date;

@Entity(tableName = "t_photo", indices = {@Index({"author", "cid"})})
public class Photo {

    /** 标识码 */
    @PrimaryKey
    @NonNull
    private String pid;

    /** 照片文件路径 */
    @ColumnInfo(name = "file_path")
    private String filepath;

    /** 发布者id */
    @ColumnInfo(name = "author")
    private String author;

    /** 照片种类 */
    @ColumnInfo(name = "type")
    private String type;

    /** 评论 */
    @ColumnInfo(name = "cid")
    private String cid;

    /** 发布时间 */
    @ColumnInfo(name = "publish")
    private Date publish;

    public Photo(String filepath, String author, String type, String cid) {
        pid = UUIDUtil.generateUID();
        this.filepath = filepath;
        this.author = author;
        this.type = type;
        this.cid = cid;
        publish = new Date();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Date getPublish() {
        return publish;
    }

    public void setPublish(Date publish) {
        this.publish = publish;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
