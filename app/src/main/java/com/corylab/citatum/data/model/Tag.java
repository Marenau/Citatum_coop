package com.corylab.citatum.data.model;

import java.util.Objects;

/**
 * Модель тега.
 */
public class Tag {
    private int uid;
    private String name;

    /**
     * Конструктор класса Tag.
     *
     * @param name Название тега.
     */
    public Tag(String name) {
        this.name = name;
    }

    /**
     * Конструктор класса Tag.
     *
     * @param uid  Уникальный идентификатор тега.
     * @param name Название тега.
     */
    public Tag(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    /**
     * Проверяет, равен ли данный объект тегу.
     *
     * @param obj Объект для сравнения.
     * @return true, если объект равен тегу, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) obj;
        return Objects.equals(name, other.name);
    }

    /**
     * Возвращает уникальный идентификатор тега.
     *
     * @return Уникальный идентификатор тега.
     */
    public int getUid() {
        return uid;
    }

    /**
     * Устанавливает уникальный идентификатор тега.
     *
     * @param uid Уникальный идентификатор тега.
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * Возвращает название тега.
     *
     * @return Название тега.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название тега.
     *
     * @param name Название тега.
     */
    public void setName(String name) {
        this.name = name;
    }
}