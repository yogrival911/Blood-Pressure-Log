package com.example.bloodpressurelog;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecordRepository {

    private RecordDao recordDao;
    private LiveData<List<Record>> allRecords;

    public RecordRepository(Application application){
        RoomDatabaseRecord db = RoomDatabaseRecord.getDatabaseInstance(application);
        recordDao = db.recordDao();
        allRecords = recordDao.getAll();
    }

    public LiveData<List<Record>> getAllRecords() {
        return allRecords;
    }

    public void insert(Record record){
        insertAsyncTask myInsertTask = new insertAsyncTask(recordDao);
        myInsertTask.execute(record);
    }

    public void delete(Record record){
        deleteAsyncTask myDeleteTask = new deleteAsyncTask(recordDao);
        myDeleteTask.execute(record);
    }

    public void deleteAll(){
        deleteAllAsyncTask myDeleteTask = new deleteAllAsyncTask(recordDao);
        myDeleteTask.execute();
    }



    class deleteAsyncTask extends AsyncTask<Record, Void, Void> {
        RecordDao recordDao;

        public deleteAsyncTask(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected Void doInBackground(Record... records) {
            recordDao.delete(records[0]);
            return null;
        }
    }

    class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        RecordDao recordDao;

        public deleteAllAsyncTask(RecordDao recordDao) {
            this.recordDao =recordDao ;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            recordDao.deleteAll();
            return null;
        }
    }


    class insertAsyncTask extends AsyncTask<Record, Void, Void>{
        RecordDao recordDao;

        public insertAsyncTask(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected Void doInBackground(Record... records) {
            recordDao.insert(records[0]);
            return null;
        }
    }


}
