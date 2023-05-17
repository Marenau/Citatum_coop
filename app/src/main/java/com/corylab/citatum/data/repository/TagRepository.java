package com.corylab.citatum.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.corylab.citatum.data.dao.TagDao;
import com.corylab.citatum.data.database.AppRoomDatabase;
import com.corylab.citatum.data.entity.EntityTag;
import com.corylab.citatum.data.model.Tag;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class TagRepository {

    private final TagDao tagDao;
    private final LiveData<List<Tag>> tags;

    public TagRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        tagDao = db.tagDao();
        tags = Transformations.map(tagDao.getAll(), entities -> entities.stream().map(EntityTag::toTag).collect(Collectors.toList()));
    }

    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    public void insertTag(Tag tag) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.insertAll(new EntityTag(tag.getName())));
    }

    public void deleteTag(Tag tag) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.delete(toTagEntity(tag)));
    }

    public void updateTag(Tag tag) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.update(toTagEntity(tag)));
    }

    public Tag getTagById(int id) {
        try {
            EntityTag temp = AppRoomDatabase.databaseReadExecutor.submit(() -> tagDao.getTagById(id)).get();
            return temp.toTag();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    private EntityTag toTagEntity(Tag tag) {
        EntityTag temp = new EntityTag(tag.getName());
        temp.uid = tag.getUid();
        return temp;
    }
}
