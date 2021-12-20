package com.example.ittakesthree.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.ittakesthree.pojo.Comment;
import com.example.ittakesthree.pojo.CommentWithPhotos;

import java.util.List;

@Dao
public interface CommentDao {

    /** 存储任意数量评论,覆盖式
     * @param comments 评论
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Comment... comments);

    /**
     * 更新评论 没什么用（直接用save）
     * */
    @Update
    @Deprecated
    void update(Comment... comments);

    /**
     * 删除评论
     * @param comments 一组评论
     * */
    @Delete
    void delete(Comment... comments);

    /** 根据编号加载指定评论
     * @param cid 评论id
     * */
    @Query("select * from t_comment where cid = :cid")
    Comment loadByCid(String cid);

    /**
     * 根据用户id加载评论(没什么用,建议用userdao下的loadUserComment)
     * */
    @Deprecated
    @Query("select * from t_comment where author = :uid")
    List<Comment> loadByUser(String uid);

    /**
     * 查询指定评论中的照片
     * @param cid 评论id
     * @return 照片
     * */
    @Query("select * from t_comment where cid = :cid")
    @Transaction
    CommentWithPhotos loadCommentPhoto(String cid);

}
