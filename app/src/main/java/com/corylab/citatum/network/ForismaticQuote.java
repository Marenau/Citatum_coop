package com.corylab.citatum.network;

/**
 * Модель данных для представления цитаты из Forismatic API.
 */
public class ForismaticQuote {

    private final String quoteText;
    private final String quoteAuthor;

    /**
     * Конструктор класса.
     *
     * @param quoteText   Текст цитаты.
     * @param quoteAuthor Автор цитаты.
     */
    public ForismaticQuote(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    /**
     * Получить текст цитаты.
     *
     * @return Текст цитаты.
     */
    public String getQuoteText() {
        return quoteText;
    }

    /**
     * Получить автора цитаты.
     *
     * @return Автор цитаты.
     */
    public String getQuoteAuthor() {
        return quoteAuthor;
    }
}