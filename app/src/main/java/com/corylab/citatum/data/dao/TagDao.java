package com.corylab.citatum.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.corylab.citatum.data.entity.EntityTag;

import java.util.List;

/**
 * Data Access Object (DAO) для работы с таблицей tags_table.
 * Предоставляет методы для вставки, обновления, удаления и извлечения данных из таблицы tags_table.
 */
@Dao
public interface TagDao {

    /**
     * Вставляет один или несколько тегов в таблицу.
     *
     * @param tag Теги для вставки.
     */
    @Insert
    void insertAll(EntityTag... tag);

    /**
     * Обновляет информацию о теге в таблице.
     *
     * @param tag Тег для обновления.
     */
    @Update
    void update(EntityTag tag);

    /**
     * Удаляет тег из таблицы.
     *
     * @param tag Тег для удаления.
     */
    @Delete
    void delete(EntityTag tag);

    /**
     * Извлекает все теги из таблицы tags_table в виде списка.
     * Теги сортируются по идентификатору (uid).
     *
     * @return Живые данные (LiveData) списка всех тегов.
     */
    @Query("SELECT * FROM tags_table ORDER BY uid")
    LiveData<List<EntityTag>> getAll();

    /**
     * Извлекает тег из таблицы tags_table по указанному идентификатору (uid).
     *
     * @param id Идентификатор тега.
     * @return Тег с указанным идентификатором (uid).
     */
    @Query("SELECT * FROM tags_table WHERE uid = :id")
    EntityTag getTagById(int id);
}