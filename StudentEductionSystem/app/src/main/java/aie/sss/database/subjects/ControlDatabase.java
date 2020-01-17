package aie.sss.database.subjects;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import aie.sss.models.Subject;

@Database(entities = Subject.class, version = 3,exportSchema = false)
public abstract class ControlDatabase extends RoomDatabase {

    private static volatile ControlDatabase INSTANCE;
    private static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

     static ControlDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (ControlDatabase.class) {
                INSTANCE= Room.databaseBuilder(context, ControlDatabase.class, "Subject").addCallback(callback).fallbackToDestructiveMigration().allowMainThreadQueries().build();
            }
        }
        return INSTANCE;
    }

    public abstract SubjectsDoa getDoa();
}
