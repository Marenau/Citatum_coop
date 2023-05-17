package com.corylab.citatum.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.corylab.citatum.data.dao.QuoteDao;
import com.corylab.citatum.data.database.AppRoomDatabase;
import com.corylab.citatum.data.entity.EntityQuote;
import com.corylab.citatum.data.model.Quote;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Репозиторий для работы с цитатами.
 */
public class QuoteRepository {

    private final QuoteDao quoteDao;
    private final LiveData<List<Quote>> quotes;

    /**
     * Конструктор класса QuoteRepository.
     *
     * @param application Контекст приложения.
     */
    public QuoteRepository(Application application) {
        AppRoomDatabase quoteDB = AppRoomDatabase.getDatabase(application);
        quoteDao = quoteDB.quoteDao();
        quotes = Transformations.map(quoteDao.getAll(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    /**
     * Получить список всех цитат.
     *
     * @return LiveData со списком всех цитат.
     */
    public LiveData<List<Quote>> getQuotes() {
        return quotes;
    }

    /**
     * Вставить новую цитату.
     *
     * @param quote Цитата для вставки.
     */
    public void insertQuote(Quote quote) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.insertAll(new EntityQuote(
                quote.getTitle(), quote.getAuthor(), quote.getText(), quote.getDate(),
                quote.getPageNumber(), quote.getBookmarkFlag(), quote.getRemovedFlag())));
    }

    /**
     * Удалить цитату.
     *
     * @param quote Цитата для удаления.
     */
    public void deleteQuote(Quote quote) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.delete(toQuoteEntity(quote)));
    }

    /**
     * Обновить цитату.
     *
     * @param quote Цитата для обновления.
     */
    public void updateQuote(Quote quote) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.update(toQuoteEntity(quote)));
    }

    /**
     * Получить максимальный идентификатор цитаты.
     *
     * @return Максимальный идентификатор цитаты.
     */
    public int getQuoteMaxId() {
        try {
            return AppRoomDatabase.databaseReadExecutor.submit(quoteDao::getMaxId).get();
        } catch (ExecutionException | InterruptedException e) {
            return 0;
        }
    }

    /**
     * Получить список трех случайных цитат.
     *
     * @return LiveData со списком трех случайных цитат.
     */
    public LiveData<List<Quote>> getThreeQuotes() {
        return Transformations.map(quoteDao.getThreeQuotes(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    /**
     * Получить список цитат с закладками.
     *
     * @return LiveData со списком цитат с закладками.
     */
    public LiveData<List<Quote>> getBookmarkedQuotes() {
        return Transformations.map(quoteDao.getBookmarkedQuotes(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }
    /**
     * Получить список удаленных цитат.
     *
     * @return LiveData со списком удаленных цитат.
     */
    public LiveData<List<Quote>> getRemovedQuotes() {
        return Transformations.map(quoteDao.geRemovedQuotes(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    /**
     * Получить список всех активных цитат.
     *
     * @return LiveData со списком всех активных цитат.
     */
    public LiveData<List<Quote>> getAllActive() {
        return Transformations.map(quoteDao.getAllActive(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    /**
     * Удалить устаревшие цитаты по заданному временному штампу.
     *
     * @param outdatedTimestamp Временной штамп для удаления устаревших цитат.
     */
    public void removeOutdatedQuotes(long outdatedTimestamp) {
        AppRoomDatabase.databaseReadExecutor.execute(() -> quoteDao.removeOutdatedQuotes(outdatedTimestamp));
    }

    /**
     * Получить цитату по ее идентификатору.
     *
     * @param uid Идентификатор цитаты.
     * @return Цитата с заданным идентификатором или null, если цитата не найдена.
     */
    public Quote getQuoteByUid(int uid) {
        try {
            EntityQuote temp = AppRoomDatabase.databaseReadExecutor.submit(() -> quoteDao.getQuoteById(uid)).get();
            return temp.toQuote();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    /**
     * Преобразует объект Quote в объект EntityQuote.
     *
     * @return Объект EntityQuote, созданный на основе полей Quote.
     */
    private EntityQuote toQuoteEntity(Quote quote) {
        EntityQuote temp = new EntityQuote(quote.getTitle(), quote.getAuthor(), quote.getText(), quote.getDate(),
                quote.getPageNumber(), quote.getBookmarkFlag(), quote.getRemovedFlag());
        temp.uid = quote.getUid();
        temp.removeDate = quote.getRemovedDate();
        return temp;
    }
}
