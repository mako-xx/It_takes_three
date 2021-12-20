package com.example.ittakesthree.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/** 评论及其照片 */

public class CommentWithPhotos {

    @Embedded
    public Comment comment;
    @Relation(
            parentColumn = "cid",
            entityColumn = "cid"
    )
    public List<Photo> photos;
}
