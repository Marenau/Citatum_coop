package com.corylab.citatum.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.corylab.citatum.data.model.Tag;

/**
 * Класс EntityTag представляет сущность тега в базе данных.
 * Аннотация @Entity указывает, что этот класс представляет таблицу в базе данных с именем "tags_table".
 */
@Entity(tableName = "tags_table")
public class EntityTag {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    public Integer uid = null;
    @ColumnInfo(name = "name")
    public String name;

    /**
     * Создает новый экземпляр класса EntityTag с указанным именем тега.
     *
     * @param name Имя тега.
     */
    public EntityTag(String name) {
        this.name = name;
    }

    /**
     * Преобразует объект EntityTag в объект Tag.
     *
     * @return Объект Tag, созданный на основе полей EntityTag.
     */
    public Tag toTag() {
        return new Tag(uid, name);
    }
}
