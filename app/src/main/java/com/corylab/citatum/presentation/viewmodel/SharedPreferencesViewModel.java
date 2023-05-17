package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.corylab.citatum.data.repository.SharedPreferencesRepository;

public class SharedPreferencesViewModel extends AndroidViewModel {

    private final SharedPreferencesRepository repository;

    public SharedPreferencesViewModel(@NonNull Application application) {
        super(application);
        repository = new SharedPreferencesRepository(application);
    }

    public void setString(String key, String value) {
        repository.setString(key, value);
    }

    public String getString(String key, String defaultValue) {
        return repository.getString(key, defaultValue);
    }

    public void setLong(String key, Long value) {
        repository.setLong(key, value);
    }

    public Long getLong(String key, Long defaultValue) {
        return repository.getLong(key, defaultValue);
    }
}