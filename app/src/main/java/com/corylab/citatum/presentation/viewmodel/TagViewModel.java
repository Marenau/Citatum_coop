package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.data.repository.TagRepository;

import java.util.List;

public class TagViewModel extends AndroidViewModel {

    private final TagRepository repository;
    private final LiveData<List<Tag>> tags;

    public TagViewModel(Application application) {
        super(application);
        repository = new TagRepository(application);
        tags = repository.getTags();
    }

    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    public void insert(Tag tag) {
        repository.insertTag(tag);
    }

    public void delete(Tag tag) {
        repository.deleteTag(tag);
    }

    public void update(Tag tag) {
        repository.updateTag(tag);
    }

    public Tag getTagById(int id) {
        return repository.getTagById(id);
    }
}
