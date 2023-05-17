package com.corylab.citatum.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.corylab.citatum.data.entity.EntityQuote;

import java.util.List;

/**
 * Data Access Object (DAO) для работы с таблицей quotes_table.
 * Предоставляет методы для вставки, обновления, удаления и извлечения данных из таблицы quotes_table.
 */
@Dao
public interface QuoteDao {

    /**
     * Вставляет одну или несколько цитат в таблицу.
     *
     * @param quote Цитаты для вставки.
     */
    @Insert
    void insertAll(EntityQuote... quote);

    /**
     * Обновляет информацию о цитате в таблице.
     *
     * @param quote Цитата для обновления.
     */
    @Update
    void update(EntityQuote quote);

    /**
     * Удаляет цитату из таблицы.
     *
     * @param quote Цитата для удаления.
     */
    @Delete
    void delete(EntityQuote quote);

    /**
     * Извлекает все цитаты из таблицы quotes_table в виде списка.
     * Цитаты сортируются по идентификатору (uid).
     *
     * @return Живые данные (LiveData) списка всех цитат.
     */
    @Query("SELECT * FROM quotes_table ORDER BY uid")
    LiveData<List<EntityQuote>> getAll();

    /**
     * Извлекает три последние цитаты, не помеченные для удаления, из таблицы quotes_table.
     * Цитаты сортируются по идентификатору (uid) в обратном порядке.
     *
     * @return Живые данные (LiveData) списка трех последних цитат.
     */
    @Query("SELECT * FROM quotes_table WHERE remove_flag != 1 ORDER BY uid DESC LIMIT 3")
    LiveData<List<EntityQuote>> getThreeQuotes();

    /**
     * Извлекает все активные цитаты (не помеченные для удаления) из таблицы quotes_table.
     * Цитаты сортируются по идентификатору (uid).
     *
     * @return Живые данные (LiveData) списка всех активных цитат.
     */
    @Query("SELECT * FROM quotes_table WHERE remove_flag != 1 ORDER BY uid")
    LiveData<List<EntityQuote>> getAllActive();

    /**
     * Извлекает максимальный идентификатор (uid) из таблицы quotes_table.
     *
     * @return Максимальный идентификатор (uid).
     */
    @Query("SELECT MAX(uid) FROM quotes_table")
    int getMaxId();

    /**
     * Извлекает цитату из таблицы quotes_table по указанному идентификатору (uid).
     *
     * @param id Идентификатор цитаты.
     * @return Цитата с указанным идентификатором (uid).
     */
    @Query("SELECT * FROM quotes_table WHERE uid = :id")
    EntityQuote getQuoteById(int id);

    /**
     * Извлекает все цитаты, помеченные закладкой (bookmark_flag = 1) и не помеченные для удаления (remove_flag != 1),
     * из таблицы quotes_table.
     *
     * @return Живые данные (LiveData) списка цитат с закладкой.
     */
    @Query("SELECT * FROM quotes_table WHERE bookmark_flag = 1 AND remove_flag != 1")
    LiveData<List<EntityQuote>> getBookmarkedQuotes();

    /**
     * Извлекает все цитаты, помеченные для удаления (remove_flag = 1), из таблицы quotes_table.
     *
     * @return Живые данные (LiveData) списка удаленных цитат.
     */
    @Query("SELECT * FROM quotes_table WHERE remove_flag = 1")
    LiveData<List<EntityQuote>> geRemovedQuotes();

    /**
     * Удаляет устаревшие цитаты, помеченные для удаления (remove_flag = 1) и с датой удаления, меньшей или равной заданному
     * значению outdatedTimestamp.
     *
     * @param outdatedTimestamp Значение временной метки, с которым сравнивается дата удаления цитаты.
     */
    @Query("DELETE FROM quotes_table WHERE remove_flag = 1 AND remove_date <= :outdatedTimestamp")
    void removeOutdatedQuotes(long outdatedTimestamp);
}