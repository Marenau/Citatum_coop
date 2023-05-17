package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.corylab.citatum.data.entity.QuoteTagJoin;
import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.data.repository.QuoteTagJoinRepository;

import java.util.List;

public class QuoteTagJoinViewModel extends AndroidViewModel {

    private final QuoteTagJoinRepository repository;

    public QuoteTagJoinViewModel(Application application) {
        super(application);
        repository = new QuoteTagJoinRepository(application);
    }

    public void insert(QuoteTagJoin quoteTagJoin) {
        repository.insert(quoteTagJoin);
    }

    public void delete(QuoteTagJoin quoteTagJoin) {
        repository.delete(quoteTagJoin);
    }

    public LiveData<List<Tag>> getTagsForQuote(int id) {
        return repository.getTagsForQuote(id);
    }

    public LiveData<List<Quote>> getQuotesForTag(int id) {
        return repository.getQuotesForTag(id);
    }
}
