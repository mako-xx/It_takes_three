package com.example.ittakesthree.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/** 用户相册数据类 */
public class UserWithPhotos {

    @Embedded
    public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "author"
    )
    public List<Photo> photos;
}
