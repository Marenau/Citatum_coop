package com.corylab.citatum.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.corylab.citatum.data.entity.EntityQuote;
import com.corylab.citatum.data.entity.EntityTag;
import com.corylab.citatum.data.entity.QuoteTagJoin;

import java.util.List;

@Dao
public interface QuoteTagJoinDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(QuoteTagJoin quoteTagJoin);

    @Delete
    void delete(QuoteTagJoin quoteTagJoin);

    @Query("SELECT * FROM tags_table INNER JOIN quote_tag_join ON tags_table.uid = quote_tag_join.tag_id WHERE quote_tag_join.quote_id = :quoteId")
    LiveData<List<EntityTag>> getTagsForQuote(int quoteId);

    @Query("SELECT * FROM quotes_table INNER JOIN quote_tag_join ON quotes_table.uid = quote_tag_join.quote_id WHERE quote_tag_join.tag_id = :tagId")
    LiveData<List<EntityQuote>> getQuotesForTag(int tagId);
}