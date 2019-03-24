package com.app.tabletopdiceroller.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.app.tabletopdiceroller.Objects.Roll;

// Update version after altering the database
@Database(entities = {Roll.class}, version = 1)
public abstract class RollDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "rolls_db";

    private static RollDatabase instance;

    static RollDatabase getInstance(final Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        RollDatabase.class,
                        DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract RollDAO getRollDAO();

}
