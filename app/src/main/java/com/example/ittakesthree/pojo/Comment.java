package com.example.ittakesthree.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ittakesthree.util.UUIDUtil;

import java.util.Date;

/** 评论实体类 */
@Entity(tableName = "t_comment")
public class Comment {

    /** 标识码 */
    @PrimaryKey
    @NonNull
    private String cid;

    /** 评论内容 */
    @ColumnInfo(name = "content")
    private String content;

    /** 评分 0-5 */
    @ColumnInfo(name = "score")
    private short score;

    /** 评论用户的id */
    @ColumnInfo(name = "author")
    private String author;

    /** 点赞数 */
    @ColumnInfo(name = "thumbs_up_down", defaultValue = "0")
    private int thumbsUpNum;

    /** 浏览数 */
    @ColumnInfo(name = "browse_num", defaultValue = "0")
    private int browseNum;

    /** 是否匿名发布 */
    @ColumnInfo(name = "is_anonymous", defaultValue = "false")
    private boolean isAnonymous;

    /** 评论的景点 */
    @ColumnInfo(name = "spot")
    private String spot;

    /** 评论类型 */
    @ColumnInfo(name = "type")
    private boolean type;

    /** 发布时间 */
    @ColumnInfo(name = "publish")
    private Date publish;


    public Comment(String content, short score, String author, boolean isAnonymous, String spot, boolean type) {
        cid = UUIDUtil.generateUID();
        this.content = content;
        this.score = score;
        this.author = author;
        this.isAnonymous = isAnonymous;
        this.spot = spot;
        this.type = type;
        publish = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getThumbsUpNum() {
        return thumbsUpNum;
    }

    public void setThumbsUpNum(int thumbsUpNum) {
        this.thumbsUpNum = thumbsUpNum;
    }

    public int getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public Date getPublish() {
        return publish;
    }

    public void setPublish(Date publish) {
        this.publish = publish;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
