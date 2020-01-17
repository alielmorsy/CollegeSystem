package aie.sss.database.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Notification.class}, version = 3, exportSchema = false)
public abstract class BigDatabase extends RoomDatabase {

    public static volatile BigDatabase INSTANCE;
    public abstract NotificationDDoa notificationDoa();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static BigDatabase getInstance(Context c){
        if(INSTANCE==null){
            synchronized (BigDatabase.class){
                INSTANCE= Room.databaseBuilder(c,BigDatabase.class,"Notifications").addCallback(callback) .fallbackToDestructiveMigration()
                        .allowMainThreadQueries().build();
            }
        }
        return INSTANCE;  }


    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);


        }
    };
}
