package com.corylab.citatum.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.corylab.citatum.data.entity.EntityTag;

import java.util.List;

@Dao
public interface TagDao {
    @Insert
    void insertAll(EntityTag... tag);

    @Update
    void update(EntityTag quote);

    @Delete
    void delete(EntityTag quote);

    @Query("SELECT * FROM tags_table ORDER BY uid")
    LiveData<List<EntityTag>> getAll();

    @Query("SELECT * FROM tags_table WHERE uid = :id")
    EntityTag getTagById(int id);
}