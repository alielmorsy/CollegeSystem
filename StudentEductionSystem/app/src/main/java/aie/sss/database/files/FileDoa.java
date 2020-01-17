package aie.sss.database.files;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import aie.sss.models.Files;

@Dao
public interface FileDoa {
    @Query("SELECT * FROM files")
    LiveData<List<Files>> all();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Files file);
    @Delete
    void delete(Files file);

    @Query("DELETE  FROM `Files`")
    void deleteAll();
    @Query("SELECT * FROM `Files` WHERE `name`=:fileName")
    Files get(String fileName);
    @Query("UPDATE `files` SET `downloaded` =1 WHERE `name`=:name ")
    void UPDATEDownload(String name);
}
