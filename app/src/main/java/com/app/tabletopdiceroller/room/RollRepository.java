package com.app.tabletopdiceroller.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.content.Context;
import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.async.DeleteAsyncTask;
import com.app.tabletopdiceroller.async.InsertAsyncTask;

import java.util.List;

public class RollRepository {

    private RollDatabase rollDatabase;

    public RollRepository(Context context) {
        this.rollDatabase = rollDatabase.getInstance(context);
    }

    public void insertRollTask(Roll roll) {
        new InsertAsyncTask(rollDatabase.getRollDAO()).execute(roll);
    }

    public LiveData<List<Roll>> retrieveRollTask() {
        return rollDatabase.getRollDAO().getRolls();
    }

    public void deleteRoll(Roll roll) {
        new DeleteAsyncTask(rollDatabase.getRollDAO()).execute(roll);
    }
}
