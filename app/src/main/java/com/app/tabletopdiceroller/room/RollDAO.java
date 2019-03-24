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

    @Insert
    long[] insertRolls(Roll[] rolls);

    /**
     * Returns all roll objects in the database
     * @return list of roll objects in the database
     */
    @Query("SELECT * FROM favorites")
    LiveData<List<Roll>> getRolls();

    @Delete
    int delete(Roll[] rolls);
}
