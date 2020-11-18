package com.example.bloodpressurelog;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Record.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseRecord extends RoomDatabase {
    public abstract RecordDao recordDao();
    public static RoomDatabaseRecord INSTANCE;

    static RoomDatabaseRecord getDatabaseInstance(final Context context){
        if(INSTANCE==null){
            synchronized (RoomDatabaseRecord.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),RoomDatabaseRecord.class, "reading_record")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

static  RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
        super.onOpen(db);
        new PopulateDbAsync(INSTANCE).execute();
    }
};
static  class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
    RecordDao mRecordDao;
    PopulateDbAsync(RoomDatabaseRecord db){
        mRecordDao = db.recordDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mRecordDao.deleteAll();
        Record record = new Record("44", "66","pulse","posture", "position", "breakfast", "lunch", "dinner", "med", "salt", "sym", "remark", "date", "time");
        mRecordDao.insert(record);
        return null;
    }
}

}
