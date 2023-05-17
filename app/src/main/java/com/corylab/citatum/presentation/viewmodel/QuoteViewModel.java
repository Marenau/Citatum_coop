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

/**
 * ViewModel для управления цитатами.
 */
public class QuoteViewModel extends AndroidViewModel {

    private final QuoteRepository repository;
    private final LiveData<List<Quote>> quotes;
    private final MutableLiveData<List<Quote>> filterQuotes;

    /**
     * Конструктор класса.
     *
     * @param application Application объект
     */
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

    /**
     * Вставляет новую цитату.
     *
     * @param quote объект Quote для вставки
     */
    public void insert(Quote quote) {
        repository.insertQuote(quote);
    }

    /**
     * Удаляет цитату.
     *
     * @param quote объект Quote для удаления
     */
    public void delete(Quote quote) {
        repository.deleteQuote(quote);
    }

    /**
     * Обновляет информацию о цитате.
     *
     * @param quote объект Quote для обновления
     */
    public void update(Quote quote) {
        repository.updateQuote(quote);
    }

    /**
     * Получает максимальный идентификатор цитаты.
     *
     * @return максимальный идентификатор цитаты
     */
    public int getMaxId() {
        return repository.getQuoteMaxId();
    }

    /**
     * Получает цитату по идентификатору.
     *
     * @param uid идентификатор цитаты
     * @return объект Quote
     */
    public Quote getQuoteByUid(int uid) {
        return repository.getQuoteByUid(uid);
    }

    /**
     * Получает список из трех цитат.
     *
     * @return LiveData объект со списком цитат
     */
    public LiveData<List<Quote>> getThreeQuotes() {
        return repository.getThreeQuotes();
    }

    /**
     * Получает список цитат, добавленных в закладки.
     *
     * @return LiveData объект со списком цитат
     */
    public LiveData<List<Quote>> getBookmarkedQuotes() {
        return repository.getBookmarkedQuotes();
    }

    /**
     * Получает список удаленных цитат.
     *
     * @return LiveData объект со списком цитат
     */
    public LiveData<List<Quote>> getRemovedQuotes() {
        return repository.getRemovedQuotes();
    }

    /**
     * Получает список всех активных цитат.
     *
     * @return LiveData объект со списком цитат
     */
    public LiveData<List<Quote>> getAllActive() {
        return repository.getAllActive();
    }

    /**
     * Удаляет устаревшие цитаты.
     */
    public void removeOutdatedQuotes() {
        repository.removeOutdatedQuotes(System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000));
    }

    /**
     * Фильтрует данные цитат по заданному запросу.
     *
     * @param query строка запроса для фильтрации
     */
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

    /**
     * Получает отфильтрованный список цитат.
     *
     * @return LiveData объект со списком отфильтрованных цитат
     */
    public LiveData<List<Quote>> getFilterQuotes() {
        return filterQuotes;
    }
}
