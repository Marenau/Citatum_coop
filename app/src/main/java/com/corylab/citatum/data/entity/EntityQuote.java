package com.corylab.citatum.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.corylab.citatum.data.model.Quote;

@Entity(tableName = "quotes_table")
public class EntityQuote {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    public Integer uid = null;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "author")
    public String author;
    @ColumnInfo(name = "text")
    public String text;
    @ColumnInfo(name = "date")
    public long date;
    @ColumnInfo(name = "page_number")
    public int pageNumber;
    @ColumnInfo(name = "bookmark_flag")
    public int bookmarkFlag;
    @ColumnInfo(name = "remove_flag")
    public int removeFlag;
    @ColumnInfo(name = "remove_date")
    public long removeDate;

    public EntityQuote(String title, String author, String text, long date, int pageNumber, int bookmarkFlag, int removeFlag) {
        this.title = title;
        this.author = author;
        this.text = text;
        this.date = date;
        this.pageNumber = pageNumber;
        this.bookmarkFlag = bookmarkFlag;
        this.removeFlag = removeFlag;
    }

    public Quote toQuote() {
        return new Quote(uid, title, author, text, date, pageNumber, bookmarkFlag, removeFlag, removeDate);
    }
}
