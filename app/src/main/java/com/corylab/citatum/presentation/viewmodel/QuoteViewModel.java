package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.data.repository.QuoteRepository;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuoteViewModel extends AndroidViewModel {

    private final QuoteRepository repository;
    private final LiveData<List<Quote>> quotes;
    private final MutableLiveData<List<Quote>> filterQuotes;

    public QuoteViewModel(@NotNull Application application) {
        super(application);
        repository = new QuoteRepository(application);
        filterQuotes = new MutableLiveData<>(new ArrayList<>());
        quotes = repository.getQuotes();
        quotes.observeForever(quoteList -> {
            List<Quote> actualQuote = new ArrayList<>();
            for (Quote q : quoteList) {
                if (q.getRemovedFlag() != 1)
                    actualQuote.add(q);
            }
            filterQuotes.setValue(actualQuote);
        });
    }

    public void insert(Quote quote) {
        repository.insertQuote(quote);
    }

    public void delete(Quote quote) {
        repository.deleteQuote(quote);
    }

    public void update(Quote quote) {
        repository.updateQuote(quote);
    }

    public int getMaxId() {
        return repository.getQuoteMaxId();
    }

    public Quote getQuoteByUid(int uid) {
        return repository.getQuoteByUid(uid);
    }

    public LiveData<List<Quote>> getThreeQuotes() {
        return repository.getThreeQuotes();
    }

    public LiveData<List<Quote>> getBookmarkedQuotes() {
        return repository.getBookmarkedQuotes();
    }

    public LiveData<List<Quote>> getRemovedQuotes() {
        return repository.getRemovedQuotes();
    }

    public LiveData<List<Quote>> getAllActive() {
        return repository.getAllActive();
    }

    public void removeOutdatedQuotes() {
        repository.removeOutdatedQuotes(System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000));
    }

    public void filterData(String query) {
        query = query.trim().toLowerCase();
        List<Quote> dataList = quotes.getValue();
        List<Quote> filteredDataList = new ArrayList<>();
        for (Quote q : dataList) {
            if (q.getRemovedFlag() != 1 && (q.getTitle().toLowerCase().contains(query) || q.getAuthor().toLowerCase().contains(query) ||
                    q.getText().toLowerCase().contains(query)))
                filteredDataList.add(q);
        }
        filterQuotes.setValue(filteredDataList);
    }

    public LiveData<List<Quote>> getFilterQuotes() {
        return filterQuotes;
    }
}
