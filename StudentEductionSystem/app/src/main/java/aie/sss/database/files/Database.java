package aie.sss.database.files;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import aie.sss.models.Files;
@androidx.room.Database(entities = {Files.class}, version = 3, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract FileDoa getDoa();
    private static volatile Database INSTANCE;
    public static Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                INSTANCE= Room.databaseBuilder(context,Database.class,"Files").addCallback(callback) .fallbackToDestructiveMigration()
                        .allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }
    private static Callback callback=new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };
}
