package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.corylab.citatum.data.repository.SharedPreferencesRepository;

/**
 * ViewModel для работы с SharedPreferences.
 */
public class SharedPreferencesViewModel extends AndroidViewModel {

    private final SharedPreferencesRepository repository;

    public SharedPreferencesViewModel(@NonNull Application application) {
        super(application);
        repository = new SharedPreferencesRepository(application);
    }

    /**
     * Устанавливает строковое значение в SharedPreferences.
     *
     * @param key   ключ
     * @param value значение
     */
    public void setString(String key, String value) {
        repository.setString(key, value);
    }

    /**
     * Получает строковое значение из SharedPreferences.
     *
     * @param key          ключ
     * @param defaultValue значение по умолчанию
     * @return строковое значение
     */
    public String getString(String key, String defaultValue) {
        return repository.getString(key, defaultValue);
    }

    /**
     * Устанавливает целочисленное значение в SharedPreferences.
     *
     * @param key   ключ
     * @param value значение
     */
    public void setLong(String key, Long value) {
        repository.setLong(key, value);
    }

    /**
     * Получает целочисленное значение из SharedPreferences.
     *
     * @param key          ключ
     * @param defaultValue значение по умолчанию
     * @return целочисленное значение
     */
    public Long getLong(String key, Long defaultValue) {
        return repository.getLong(key, defaultValue);
    }
}