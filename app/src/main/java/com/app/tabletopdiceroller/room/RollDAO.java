package com.app.tabletopdiceroller.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.app.tabletopdiceroller.Objects.Roll;

import java.util.List;

@Dao
public interface RollDAO {

    //Method for inserting rolls
    @Insert
    long[] insertRolls(Roll[] rolls);

    //Method for querying rolls
    @Query("SELECT * FROM favorites")
    LiveData<List<Roll>> getRolls();

    //Method for deleting rolls
    // Returns number of rows deleted
    @Delete
    int delete(Roll[] rolls);

    //Method for updating rolls
    // Returns how many rolls were affected by the update
    @Update
    int update(Roll[] rolls);
}