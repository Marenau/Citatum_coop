package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.data.repository.TagRepository;

import java.util.List;

/**
 * ViewModel для работы с данными тегов.
 */
public class TagViewModel extends AndroidViewModel {

    private final TagRepository repository;
    private final LiveData<List<Tag>> tags;

    public TagViewModel(Application application) {
        super(application);
        repository = new TagRepository(application);
        tags = repository.getTags();
    }

    /**
     * Получает список всех тегов.
     *
     * @return LiveData со списком тегов
     */
    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    /**
     * Вставляет новый тег.
     *
     * @param tag новый тег
     */
    public void insert(Tag tag) {
        repository.insertTag(tag);
    }

    /**
     * Удаляет тег.
     *
     * @param tag тег для удаления
     */
    public void delete(Tag tag) {
        repository.deleteTag(tag);
    }

    /**
     * Обновляет тег.
     *
     * @param tag обновленный тег
     */
    public void update(Tag tag) {
        repository.updateTag(tag);
    }

    /**
     * Получает тег по его идентификатору.
     *
     * @param id идентификатор тега
     * @return тег с указанным идентификатором, или null, если тег не найден
     */
    public Tag getTagById(int id) {
        return repository.getTagById(id);
    }
}