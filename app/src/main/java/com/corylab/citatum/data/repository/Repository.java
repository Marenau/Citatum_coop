package com.corylab.citatum.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.corylab.citatum.data.dao.QuoteDao;
import com.corylab.citatum.data.dao.QuoteTagJoinDao;
import com.corylab.citatum.data.dao.TagDao;
import com.corylab.citatum.data.database.QuoteRoomDatabase;
import com.corylab.citatum.data.entity.EntityQuote;
import com.corylab.citatum.data.entity.EntityTag;
import com.corylab.citatum.data.entity.QuoteTagJoin;
import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.data.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Repository {
    private QuoteDao quoteDao;
    private TagDao tagDao;
    private QuoteTagJoinDao quoteTagJoinDao;
    private LiveData<List<Quote>> quotes;
    private LiveData<List<Tag>> tags;

    public Repository(Application application) {
        QuoteRoomDatabase quoteDB = QuoteRoomDatabase.getDatabase(application);
        quoteDao = quoteDB.quoteDao();
        tagDao = quoteDB.tagDao();
        quoteTagJoinDao = quoteDB.quoteTagJoinDao();
        quotes = Transformations.map(quoteDao.getAll(), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
        tags = Transformations.map(tagDao.getAll(), entities -> entities.stream().map(EntityTag::toTag).collect(Collectors.toList()));
    }

    public LiveData<List<Quote>> getQuotes() {
        return quotes;
    }

    public void insertQuote(Quote quote) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.insertAll(new
                EntityQuote(quote.getTitle(), quote.getAuthor(), quote.getText(), quote.getDate(), quote.getPageNumber(), quote.getBookmarkFlag(), quote.getRemovedFlag())));
    }

    public void deleteQuote(Quote quote) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.delete(toQuoteEntity(quote)));
    }

    public void updateQuote(Quote quote) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> quoteDao.update(toQuoteEntity(quote)));
    }

    public int getQuoteMaxId() {
        try {
            return QuoteRoomDatabase.databaseReadExecutor.submit(() -> quoteDao.getMaxId()).get();
        } catch (ExecutionException | InterruptedException e) {
            return 0;
        }
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

    public Quote getQuoteByUid(int uid) {
        try {
            EntityQuote temp = QuoteRoomDatabase.databaseReadExecutor.submit(() -> quoteDao.getQuoteById(uid)).get();
            return temp.toQuote();
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }

    private EntityQuote toQuoteEntity(Quote quote) {
        EntityQuote temp = new EntityQuote(quote.getTitle(), quote.getAuthor(), quote.getText(), quote.getDate(), quote.getPageNumber(), quote.getBookmarkFlag(), quote.getRemovedFlag());
        temp.uid = quote.getUid();
        return temp;
    }

    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    public void insertTag(Tag tag) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.insertAll(new EntityTag(tag.getName())));
    }

    public void deleteTag(Tag tag) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.delete(toTagEntity(tag)));
    }

    public void updateTag(Tag tag) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> tagDao.update(toTagEntity(tag)));
    }

    public Tag getTagById(int id) {
        try {
            EntityTag temp = QuoteRoomDatabase.databaseReadExecutor.submit(() -> tagDao.getTagById(id)).get();
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

    public void insert(QuoteTagJoin quoteTagJoin) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> quoteTagJoinDao.insert(quoteTagJoin));
    }

    public void delete(QuoteTagJoin quoteTagJoin) {
        QuoteRoomDatabase.databaseWriteExecutor.execute(() -> quoteTagJoinDao.delete(quoteTagJoin));
    }

    public LiveData<List<Tag>> getTagsForQuote(int id) {
        return Transformations.map(quoteTagJoinDao.getTagsForQuote(id), entities -> entities.stream().map(EntityTag::toTag).collect(Collectors.toList()));
    }

    public LiveData<List<Quote>> getQuotesForTag(int id) {
        return Transformations.map(quoteTagJoinDao.getQuotesForTag(id), entities -> entities.stream().map(EntityQuote::toQuote).collect(Collectors.toList()));
    }
}