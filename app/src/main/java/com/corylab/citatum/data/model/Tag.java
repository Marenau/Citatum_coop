package com.corylab.citatum.data.model;

import java.util.Objects;

public class Tag {
    private int uid;
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(int uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {return true;}
        if (!(obj instanceof Tag)) {return false;}
        Tag other = (Tag) obj;
        return Objects.equals(name, other.name);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}