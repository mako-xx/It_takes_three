package com.example.ittakesthree.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/** 用户行李数据类 */
public class UserWithBaggages {

    @Embedded
    public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "uid"
    )
    public List<Baggage> baggages;
}
