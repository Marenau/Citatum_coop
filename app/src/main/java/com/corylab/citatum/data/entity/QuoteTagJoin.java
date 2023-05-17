package com.corylab.citatum.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

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

    public QuoteTagJoin(int quoteId, int tagId) {
        this.quoteId = quoteId;
        this.tagId = tagId;
    }
}
