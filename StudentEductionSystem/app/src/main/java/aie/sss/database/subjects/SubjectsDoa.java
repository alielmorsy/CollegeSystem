package aie.sss.database.subjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

import aie.sss.models.Subject;

@Dao
public interface SubjectsDoa {
    @Query("SELECT * FROM `subjects`")
    LiveData<List<Subject>> all();
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(Subject subject);
    @Query("SELECT * FROM `subjects` WHERE `subjectName`=:name")
    Subject getbyName(String name);

    @Query("SELECT * FROM `subjects` WHERE `id`=:ID")
    Subject getbyID(String ID);
}
