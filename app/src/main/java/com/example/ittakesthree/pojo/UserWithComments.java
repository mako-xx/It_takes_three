package com.example.ittakesthree.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/** 用户评论数据类 */
public class UserWithComments {

    @Embedded
    public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "author"
    )
    public List<Comment> comments;
}
