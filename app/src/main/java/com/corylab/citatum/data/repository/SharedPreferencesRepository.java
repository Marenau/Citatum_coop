package com.corylab.citatum.data.repository;

import android.app.Application;

import com.corylab.citatum.data.datasource.SharedPreferencesSource;

public class SharedPreferencesRepository {

    private final SharedPreferencesSource sharedPreferencesSource;

    public SharedPreferencesRepository(Application application) {
        sharedPreferencesSource = new SharedPreferencesSource(application);
    }

    public void setString(String key, String value) {
        sharedPreferencesSource.setString(key, value);
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferencesSource.getString(key, defaultValue);
    }

    public void setLong(String key, Long value) {
        sharedPreferencesSource.setLong(key, value);
    }

    public Long getLong(String key, Long defaultValue) {
        return sharedPreferencesSource.getLong(key, defaultValue);
    }
}
