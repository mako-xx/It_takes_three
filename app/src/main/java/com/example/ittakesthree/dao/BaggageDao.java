package com.example.ittakesthree.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.ittakesthree.pojo.Baggage;
import com.example.ittakesthree.pojo.UserWithBaggages;

import java.util.List;

@Dao
public interface BaggageDao {

    /** 存储行李，覆盖式
     * @param baggage 行李
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Baggage... baggage);

    /** 删除行李
     * @param baggages 要删的行李
     * */
    @Delete
    void delete(Baggage... baggages);


}
