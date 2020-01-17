package aie.sss.database.notification;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotificationDDoa {
    @Query("SELECT * FROM Notifications")
    LiveData<List<Notification>> all();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notification notification);
    @Query("SELECT * FROM Notifications WHERE date=:time AND name=:title")
    Notification getByTime(String time,String title);
    @Query("DELETE FROM Notifications")
    void deleteAll();
}
