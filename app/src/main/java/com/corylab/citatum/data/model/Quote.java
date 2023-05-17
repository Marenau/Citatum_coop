package com.corylab.citatum.data.model;

import java.util.Objects;

public class Quote {
    private int uid;
    private String title;
    private String author;
    private String text;
    private long date;
    private int pageNumber;
    private int bookmarkFlag;
    private int removedFlag;
    private long removedDate;

    public Quote(int uid, String title, String author, String text, long date, int pageNumber, int bookmarkFlag, int removedFlag, long removedDate) {
        this.uid = uid;
        this.title = title;
        this.author = author;
        this.text = text;
        this.date = date;
        this.pageNumber = pageNumber;
        this.bookmarkFlag = bookmarkFlag;
        this.removedFlag = removedFlag;
        this.removedDate = removedDate;
    }

    public Quote(String title, String author, String text, long date, int pageNumber) {
        this.title = title;
        this.author = author;
        this.text = text;
        this.date = date;
        this.pageNumber = pageNumber;
    }

    public Quote() {
        uid = -1;
        bookmarkFlag = 0;
        removedFlag = 0;
    }

    public Quote(Quote quote) {
        this.uid = quote.uid;
        this.title = quote.title;
        this.author = quote.author;
        this.text = quote.text;
        this.date = quote.date;
        this.pageNumber = quote.pageNumber;
        this.bookmarkFlag = quote.bookmarkFlag;
        this.removedFlag = quote.removedFlag;
        this.removedDate = quote.removedDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Quote)) {
            return false;
        }
        Quote other = (Quote) obj;
        return Objects.equals(title, other.title) &&
                Objects.equals(author, other.author) &&
                Objects.equals(text, other.text) &&
                Objects.equals(date, other.date) &&
                Objects.equals(pageNumber, other.pageNumber) &&
                bookmarkFlag == other.bookmarkFlag &&
                removedFlag == other.removedFlag &&
                removedDate == other.removedDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getBookmarkFlag() {
        return bookmarkFlag;
    }

    public void setBookmarkFlag(int bookmarkFlag) {
        this.bookmarkFlag = bookmarkFlag;
    }

    public int getRemovedFlag() {
        return removedFlag;
    }

    public void setRemovedFlag(int removedFlag) {
        this.removedFlag = removedFlag;
    }

    public long getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(long removedDate) {
        this.removedDate = removedDate;
    }
}