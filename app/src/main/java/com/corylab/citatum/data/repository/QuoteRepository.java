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

public class QuoteRepository {

    private final QuoteDao quoteDao;
    private final LiveData<List<Quote>> quotes;

    public QuoteRepository(Application application) {
        AppRoomDatabase quoteDB = AppRoomDatabase.getDatabase(application);
        quoteDao = quoteDB.quoteDao();
        quotes = Transformations.map(quoteDao.getAll(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    public LiveData<List<Quote>> getQuotes() {
        return quotes;
    }

    public void insertQuote(Quote quote) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.insertAll(new
                EntityQuote(quote.getTitle(), quote.getAuthor(), quote.getText(), quote.getDate(), quote.getPageNumber(), quote.getBookmarkFlag(), quote.getRemovedFlag())));
    }

    public void deleteQuote(Quote quote) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.delete(toQuoteEntity(quote)));
    }

    public void updateQuote(Quote quote) {
        AppRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.update(toQuoteEntity(quote)));
    }

    public int getQuoteMaxId() {
        try {
            return AppRoomDatabase.databaseReadExecutor.submit(quoteDao::getMaxId).get();
        } catch (ExecutionException | InterruptedException e) {
            return 0;
        }
    }

    public LiveData<List<Quote>> getThreeQuotes() {
        return Transformations.map(quoteDao.getThreeQuotes(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    public LiveData<List<Quote>> getBookmarkedQuotes() {
        return Transformations.map(quoteDao.getBookmarkedQuotes(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    public LiveData<List<Quote>> getRemovedQuotes() {
        return Transformations.map(quoteDao.geRemovedQuotes(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    public LiveData<List<Quote>> getAllActive() {
        return Transformations.map(quoteDao.getAllActive(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }

    public void removeOutdatedQuotes(long outdatedTimestamp) {
        AppRoomDatabase.databaseReadExecutor.execute(() -> quoteDao.removeOutdatedQuotes(outdatedTimestamp));
    }

    public Quote getQuoteByUid(int uid) {
        try {
            EntityQuote temp = AppRoomDatabase.databaseReadExecutor.submit(() -> quoteDao.getQuoteById(uid)).get();
            return temp.toQuote();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    private EntityQuote toQuoteEntity(Quote quote) {
        EntityQuote temp = new EntityQuote(quote.getTitle(), quote.getAuthor(), quote.getText(), quote.getDate(), quote.getPageNumber(), quote.getBookmarkFlag(), quote.getRemovedFlag());
        temp.uid = quote.getUid();
        temp.removeDate = quote.getRemovedDate();
        return temp;
    }
}
