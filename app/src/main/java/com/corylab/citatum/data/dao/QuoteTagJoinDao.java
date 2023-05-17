package com.corylab.citatum.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.corylab.citatum.data.entity.EntityQuote;
import com.corylab.citatum.data.entity.EntityTag;
import com.corylab.citatum.data.entity.QuoteTagJoin;

import java.util.List;

/**
 * Data Access Object (DAO) для работы с таблицей quote_tag_join, которая связывает цитаты (quotes_table) и теги (tags_table).
 * Предоставляет методы для вставки, удаления и извлечения связей между цитатами и тегами.
 */
@Dao
public interface QuoteTagJoinDao {

    /**
     * Вставляет связь между цитатой и тегом в таблицу quote_tag_join.
     * Если такая связь уже существует (совпадение по идентификаторам цитаты и тега), игнорирует операцию вставки.
     *
     * @param quoteTagJoin Связь между цитатой и тегом для вставки.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(QuoteTagJoin quoteTagJoin);

    /**
     * Удаляет связь между цитатой и тегом из таблицы quote_tag_join.
     *
     * @param quoteTagJoin Связь между цитатой и тегом для удаления.
     */
    @Delete
    void delete(QuoteTagJoin quoteTagJoin);

    /**
     * Извлекает все теги, связанные с указанной цитатой, из таблицы tags_table.
     *
     * @param quoteId Идентификатор цитаты.
     * @return Живые данные (LiveData) списка тегов для указанной цитаты.
     */
    @Query("SELECT * FROM tags_table INNER JOIN quote_tag_join ON tags_table.uid = quote_tag_join.tag_id WHERE quote_tag_join.quote_id = :quoteId")
    LiveData<List<EntityTag>> getTagsForQuote(int quoteId);

    /**
     * Извлекает все цитаты, связанные с указанным тегом, из таблицы quotes_table.
     *
     * @param tagId Идентификатор тега.
     * @return Живые данные (LiveData) списка цитат для указанного тега.
     */
    @Query("SELECT * FROM quotes_table INNER JOIN quote_tag_join ON quotes_table.uid = quote_tag_join.quote_id WHERE quote_tag_join.tag_id = :tagId")
    LiveData<List<EntityQuote>> getQuotesForTag(int tagId);
}