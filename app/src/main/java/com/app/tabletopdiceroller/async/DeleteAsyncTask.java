package com.app.tabletopdiceroller.async;

import android.os.AsyncTask;

import com.app.tabletopdiceroller.Objects.Roll;
import com.app.tabletopdiceroller.room.RollDAO;

public class DeleteAsyncTask extends AsyncTask<Roll, Void, Void> {

    private RollDAO rollDao;

    public DeleteAsyncTask(RollDAO dao) {
        rollDao = dao;
    }

    @Override
    protected Void doInBackground(Roll... rolls) {
        rollDao.delete(rolls);
        return null;
    }
}
