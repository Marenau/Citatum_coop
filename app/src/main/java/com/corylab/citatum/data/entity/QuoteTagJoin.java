package com.corylab.citatum.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * Класс QuoteTagJoin представляет сущность связи между цитатами и тегами в базе данных.
 * Аннотация @Entity указывает, что этот класс представляет таблицу в базе данных с именем "quote_tag_join".
 * Он также определяет внешние ключи, связывающие эту таблицу с таблицами EntityQuote и EntityTag.
 */
@Entity(tableName = "quote_tag_join",
        primaryKeys = {"quote_id", "tag_id"},
        foreignKeys = {
                @ForeignKey(entity = EntityQuote.class,
                        parentColumns = "uid",
                        childColumns = "quote_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = EntityTag.class,
                        parentColumns = "uid",
                        childColumns = "tag_id",
                        onDelete = ForeignKey.CASCADE)
        })
public class QuoteTagJoin {
    @ColumnInfo(name = "quote_id")
    public int quoteId;
    @ColumnInfo(name = "tag_id")
    public int tagId;

    /**
     * Создает новый экземпляр класса QuoteTagJoin с указанными идентификаторами цитаты и тега.
     *
     * @param quoteId Идентификатор цитаты.
     * @param tagId   Идентификатор тега.
     */
    public QuoteTagJoin(int quoteId, int tagId) {
        this.quoteId = quoteId;
        this.tagId = tagId;
    }
}