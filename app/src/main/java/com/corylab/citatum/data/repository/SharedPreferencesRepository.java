package com.corylab.citatum.data.repository;

import android.app.Application;

import com.corylab.citatum.data.datasource.SharedPreferencesSource;

/**
 * Репозиторий для работы с SharedPreferences.
 */
public class SharedPreferencesRepository {

    private final SharedPreferencesSource sharedPreferencesSource;

    /**
     * Конструктор класса.
     *
     * @param application Объект Application.
     */
    public SharedPreferencesRepository(Application application) {
        sharedPreferencesSource = new SharedPreferencesSource(application);
    }

    /**
     * Установить значение типа String в SharedPreferences.
     *
     * @param key   Ключ.
     * @param value Значение типа String.
     */
    public void setString(String key, String value) {
        sharedPreferencesSource.setString(key, value);
    }

    /**
     * Получить значение типа String из SharedPreferences.
     *
     * @param key          Ключ.
     * @param defaultValue Значение по умолчанию.
     * @return Значение типа String.
     */
    public String getString(String key, String defaultValue) {
        return sharedPreferencesSource.getString(key, defaultValue);
    }

    /**
     * Установить значение типа Long в SharedPreferences.
     *
     * @param key   Ключ.
     * @param value Значение типа Long.
     */
    public void setLong(String key, Long value) {
        sharedPreferencesSource.setLong(key, value);
    }

    /**
     * Получить значение типа Long из SharedPreferences.
     *
     * @param key          Ключ.
     * @param defaultValue Значение по умолчанию.
     * @return Значение типа Long.
     */
    public Long getLong(String key, Long defaultValue) {
        return sharedPreferencesSource.getLong(key, defaultValue);
    }
}