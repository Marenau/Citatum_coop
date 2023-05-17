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

/**
 * Репозиторий для работы с тегами.
 */
public class TagRepository {

    private final TagDao tagDao;
    private final LiveData<List<Tag>> tags;

    /**
     * Конструктор класса.
     *
     * @param application Объект Application.
     */
    public TagRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        tagDao = db.tagDao();
        tags = Transformations.map(tagDao.getAll(), entities -> entities.stream().map(EntityTag::toTag).collect(Collectors.toList()));
    }

    /**
     * Получить список всех тегов.
     *
     * @return LiveData, содержащий список тегов.
     */
    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    /**
     * Вставить тег в базу данных.
     *
     * @param tag Тег для вставки.
     */
    public void insertTag(Tag tag) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.insertAll(new EntityTag(tag.getName())));
    }

    /**
     * Удалить тег из базы данных.
     *
     * @param tag Тег для удаления.
     */
    public void deleteTag(Tag tag) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.delete(toTagEntity(tag)));
    }

    /**
     * Обновить информацию о теге в базе данных.
     *
     * @param tag Тег для обновления.
     */
    public void updateTag(Tag tag) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.update(toTagEntity(tag)));
    }

    /**
     * Получить тег по его идентификатору.
     *
     * @param id Идентификатор тега.
     * @return Тег, либо null, если тег не найден.
     */
    public Tag getTagById(int id) {
        try {
            EntityTag temp = AppRoomDatabase.databaseReadExecutor.submit(() -> tagDao.getTagById(id)).get();
            return temp.toTag();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    /**
     * Преобразует объект Tag в объект EntityTag.
     *
     * @return Объект EntityTag, созданный на основе полей Tag.
     */
    private EntityTag toTagEntity(Tag tag) {
        EntityTag temp = new EntityTag(tag.getName());
        temp.uid = tag.getUid();
        return temp;
    }
}