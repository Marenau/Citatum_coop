package com.corylab.citatum.data.datasource;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс SharedPreferencesSource представляет источник данных, использующий SharedPreferences для хранения значений.
 * Предоставляет методы для сохранения и извлечения значений типа String и Long.
 */
public class SharedPreferencesSource {

    private static final String PREF_NAME = "appPrefs";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    /**
     * Создает новый экземпляр SharedPreferencesSource с использованием указанного контекста.
     *
     * @param context Контекст приложения.
     */
    public SharedPreferencesSource(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Сохраняет значение типа String по указанному ключу.
     *
     * @param key   Ключ для сохранения значения.
     * @param value Значение типа String для сохранения.
     */
    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Извлекает значение типа String по указанному ключу.
     *
     * @param key          Ключ для извлечения значения.
     * @param defaultValue Значение по умолчанию, которое будет возвращено, если значение не найдено.
     * @return Значение типа String, связанное с указанным ключом, или значение по умолчанию, если значение не найдено.
     */
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * Сохраняет значение типа Long по указанному ключу.
     *
     * @param key   Ключ для сохранения значения.
     * @param value Значение типа Long для сохранения.
     */
    public void setLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Извлекает значение типа Long по указанному ключу.
     *
     * @param key          Ключ для извлечения значения.
     * @param defaultValue Значение по умолчанию, которое будет возвращено, если значение не найдено.
     * @return Значение типа Long, связанное с указанным ключом, или значение по умолчанию, если значение не найдено.
     */
    public Long getLong(String key, Long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }
}