package com.example.bloodpressurelog;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Record record);

    @Delete
    void delete(Record record);

    @Query("DELETE FROM reading_record")
    void deleteAll();

    @Query("SELECT * FROM reading_record")
    LiveData<List<Record>> getAll();
}
