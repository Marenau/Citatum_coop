package com.corylab.citatum.presentation.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.corylab.citatum.data.entity.QuoteTagJoin;
import com.corylab.citatum.data.model.Quote;
import com.corylab.citatum.data.model.Tag;
import com.corylab.citatum.data.repository.QuoteTagJoinRepository;

import java.util.List;

/**
 * ViewModel для управления связью между цитатами и тегами.
 */
public class QuoteTagJoinViewModel extends AndroidViewModel {

    private final QuoteTagJoinRepository repository;

    /**
     * Конструктор класса.
     *
     * @param application Application объект
     */
    public QuoteTagJoinViewModel(Application application) {
        super(application);
        repository = new QuoteTagJoinRepository(application);
    }

    /**
     * Вставляет связь между цитатой и тегом.
     *
     * @param quoteTagJoin объект QuoteTagJoin для вставки
     */
    public void insert(QuoteTagJoin quoteTagJoin) {
        repository.insert(quoteTagJoin);
    }

    /**
     * Удаляет связь между цитатой и тегом.
     *
     * @param quoteTagJoin объект QuoteTagJoin для удаления
     */
    public void delete(QuoteTagJoin quoteTagJoin) {
        repository.delete(quoteTagJoin);
    }

    /**
     * Получает список тегов для данной цитаты.
     *
     * @param id идентификатор цитаты
     * @return LiveData объект со списком тегов
     */
    public LiveData<List<Tag>> getTagsForQuote(int id) {
        return repository.getTagsForQuote(id);
    }

    /**
     * Получает список цитат для данного тега.
     *
     * @param id идентификатор тега
     * @return LiveData объект со списком цитат
     */
    public LiveData<List<Quote>> getQuotesForTag(int id) {
        return repository.getQuotesForTag(id);
    }
}