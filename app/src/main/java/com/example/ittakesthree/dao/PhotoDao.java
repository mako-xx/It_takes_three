package com.example.ittakesthree.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ittakesthree.pojo.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    /**
     * 存储照片类
     * @param photos 照片
     * */
    @Insert
    void save(Photo... photos);

    /**
     * 删除照片
     * @param photos 指定照片
     * */
    @Delete
    void delete(Photo... photos);

    /**
     * 更新照片信息
     * @param photos 指定照片
     * */
    @Update
    void update(Photo... photos);

    /**
     * 根据照片标识码查询照片
     * @param pid 照片id
     * @return 照片
     * */
    @Query("select * from t_photo where pid = :pid")
    List<Photo> loadByPid(String pid);

    /**
     * 根据照片发布者查询照片
     * @param uid 发布者id
     * @return 照片
     * */
    @Query("select * from t_photo where author = :uid")
    List<Photo> loadByAuthor(String uid);

    /**
     * 查找指定评论的照片
     * @param cid 评论id
     * @return 照片
     */
    @Query("select * from t_photo where cid = :cid")
    List<Photo> loadByCid(String cid);
}
