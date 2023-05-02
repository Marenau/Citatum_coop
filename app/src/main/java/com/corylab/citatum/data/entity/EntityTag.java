package com.corylab.citatum.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.corylab.citatum.data.model.Tag;

@Entity(tableName = "tags_table")
public class EntityTag {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    public Integer uid = null;
    @ColumnInfo(name = "name")
    public String name;

    public EntityTag(String name) {
        this.name = name;
    }

    public Tag toTag() {
        return new Tag(uid, name);
    }
}