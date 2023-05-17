package com.corylab.citatum.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.corylab.citatum.data.dao.QuoteDao;
import com.corylab.citatum.data.dao.QuoteTagJoinDao;
import com.corylab.citatum.data.dao.TagDao;
import com.corylab.citatum.data.entity.EntityQuote;
import com.corylab.citatum.data.entity.EntityTag;
import com.corylab.citatum.data.entity.QuoteTagJoin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Абстрактный класс AppRoomDatabase является базой данных Room.
 * Определяет и предоставляет доступ к DAO (Data Access Object) для взаимодействия с таблицами базы данных.
 */
@Database(entities = {EntityQuote.class, EntityTag.class, QuoteTagJoin.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {

    /**
     * Возвращает объект QuoteDao, предоставляющий методы для работы с таблицей quotes_table.
     *
     * @return QuoteDao объект для взаимодействия с таблицей quotes_table.
     */
    public abstract QuoteDao quoteDao();

    /**
     * Возвращает объект TagDao, предоставляющий методы для работы с таблицей tags_table.
     *
     * @return TagDao объект для взаимодействия с таблицей tags_table.
     */
    public abstract TagDao tagDao();

    /**
     * Возвращает объект QuoteTagJoinDao, предоставляющий методы для работы с таблицей quote_tag_join.
     *
     * @return QuoteTagJoinDao объект для взаимодействия с таблицей quote_tag_join.
     */
    public abstract QuoteTagJoinDao quoteTagJoinDao();

    private static volatile AppRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final ExecutorService databaseReadExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Возвращает экземпляр базы данных AppRoomDatabase.
     * Если база данных не существует, создает новый экземпляр с помощью Room.databaseBuilder.
     *
     * @param context Контекст приложения.
     * @return Экземпляр базы данных AppRoomDatabase.
     */
    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppRoomDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}