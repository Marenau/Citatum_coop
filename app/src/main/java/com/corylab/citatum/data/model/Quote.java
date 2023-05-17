package com.corylab.citatum.data.model;

import java.util.Objects;

/**
 * Класс Quote представляет модель цитаты.
 * Цитата имеет заголовок, автора, текст, дату, номер страницы,
 * флаг закладки, флаг удаления и дату удаления.
 */
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

    /**
     * Создает новый экземпляр класса Quote с указанными значениями.
     *
     * @param uid          Идентификатор цитаты.
     * @param title        Заголовок цитаты.
     * @param author       Автор цитаты.
     * @param text         Текст цитаты.
     * @param date         Дата цитаты.
     * @param pageNumber   Номер страницы.
     * @param bookmarkFlag Флаг закладки.
     * @param removedFlag  Флаг удаления.
     * @param removedDate  Дата удаления.
     */
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

    /**
     * Создает новый экземпляр класса Quote с указанными значениями.
     *
     * @param title       Заголовок цитаты.
     * @param author      Автор цитаты.
     * @param text        Текст цитаты.
     * @param date        Дата цитаты.
     * @param pageNumber  Номер страницы.
     */
    public Quote(String title, String author, String text, long date, int pageNumber) {
        this.title = title;
        this.author = author;
        this.text = text;
        this.date = date;
        this.pageNumber = pageNumber;
    }

    /**
     * Создает новый экземпляр класса Quote с значениями по умолчанию.
     */
    public Quote() {
        uid = -1;
        bookmarkFlag = 0;
        removedFlag = 0;
    }

    /**
     * Создает новый экземпляр класса Quote на основе существующей цитаты.
     *
     * @param quote Существующая цитата.
     */
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

    /**
     * Проверяет, равен ли данный объект цитате.
     *
     * @param obj объект, с которым сравнивается
     * @return {@code true} если объект равен данной цитате, {@code false} в противном случае.
     */
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

    /**
     * Возвращает идентификатор цитаты.
     *
     * @return Идентификатор цитаты.
     */
    public int getUid() {
        return uid;
    }

    /**
     * Устанавливает идентификатор цитаты.
     *
     * @param uid Идентификатор цитаты.
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * Возвращает заголовок цитаты.
     *
     * @return Заголовок цитаты.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает заголовок цитаты.
     *
     * @param title Заголовок цитаты.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает автора цитаты.
     *
     * @return Автор цитаты.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Устанавливает автора цитаты.
     *
     * @param author Автор цитаты.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Возвращает текст цитаты.
     *
     * @return Текст цитаты.
     */
    public String getText() {
        return text;
    }

    /**
     * Устанавливает текст цитаты.
     *
     * @param text Текст цитаты.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Возвращает дату цитаты.
     *
     * @return Дата цитаты.
     */
    public long getDate() {
        return date;
    }

    /**
     * Устанавливает дату цитаты.
     *
     * @param date Дата цитаты.
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * Возвращает номер страницы.
     *
     * @return Номер страницы.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Устанавливает номер страницы.
     *
     * @param pageNumber Номер страницы.
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Возвращает флаг закладки.
     *
     * @return Флаг закладки.
     */
    public int getBookmarkFlag() {
        return bookmarkFlag;
    }

    /**
     * Устанавливает флаг закладки.
     *
     * @param bookmarkFlag Флаг закладки.
     */
    public void setBookmarkFlag(int bookmarkFlag) {
        this.bookmarkFlag = bookmarkFlag;
    }

    /**
     * Возвращает флаг удаления.
     *
     * @return Флаг удаления.
     */
    public int getRemovedFlag() {
        return removedFlag;
    }

    /**
     * Устанавливает флаг удаления.
     *
     * @param removedFlag Флаг удаления.
     */
    public void setRemovedFlag(int removedFlag) {
        this.removedFlag = removedFlag;
    }

    /**
     * Возвращает дату удаления.
     *
     * @return Дата удаления.
     */
    public long getRemovedDate() {
        return removedDate;
    }

    /**
     * Устанавливает дату удаления.
     *
     * @param removedDate Дата удаления.
     */
    public void setRemovedDate(long removedDate) {
        this.removedDate = removedDate;
    }
}