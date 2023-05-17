package com.corylab.citatum.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.corylab.citatum.data.dao.QuoteTagJoinDao;
import com.corylab.citatum.data.database.AppRoomDatabase;
import com.corylab.citatum.data.entity.EntityQuote;
import com.corylab.citatum.data.entity.EntityTag;
import com.corylab.citatum.data.entity.QuoteTagJoin;
import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.data.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Репозиторий для связи между цитатами и тегами.
 */
public class QuoteTagJoinRepository {

    private final QuoteTagJoinDao quoteTagJoinDao;

    /**
     * Конструктор класса.
     *
     * @param application Объект Application.
     */
    public QuoteTagJoinRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        quoteTagJoinDao = db.quoteTagJoinDao();
    }

    /**
     * Вставить связь между цитатой и тегом.
     *
     * @param quoteTagJoin Объект QuoteTagJoin, представляющий связь между цитатой и тегом.
     */
    public void insert(QuoteTagJoin quoteTagJoin) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteTagJoinDao.insert(quoteTagJoin));
    }

    /**
     * Удалить связь между цитатой и тегом.
     *
     * @param quoteTagJoin Объект QuoteTagJoin, представляющий связь между цитатой и тегом.
     */
    public void delete(QuoteTagJoin quoteTagJoin) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteTagJoinDao.delete(quoteTagJoin));
    }

    /**
     * Получить список тегов для заданной цитаты.
     *
     * @param id Идентификатор цитаты.
     * @return LiveData со списком тегов для заданной цитаты.
     */
    public LiveData<List<Tag>> getTagsForQuote(int id) {
        return Transformations.map(quoteTagJoinDao.getTagsForQuote(id), entities -> entities.stream().map(EntityTag::toTag).collect(Collectors.toList()));
    }

    /**
     * Получить список цитат для заданного тега.
     *
     * @param id Идентификатор тега.
     * @return LiveData со списком цитат для заданного тега.
     */
    public LiveData<List<Quote>> getQuotesForTag(int id) {
        return Transformations.map(quoteTagJoinDao.getQuotesForTag(id), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }
}