package com.example.bloodpressurelog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RecordViewModel extends AndroidViewModel {
    RecordRepository recordRepository;
    private LiveData<List<Record>> allRecord;
    public RecordViewModel(@NonNull Application application) {
        super(application);
        recordRepository = new RecordRepository(application);
        allRecord = recordRepository.getAllRecords();
    }

    public LiveData<List<Record>> getAllRecord() {
        return allRecord;
    }
    public void insert(Record record){
        recordRepository.insert(record);
    }

    public void delete(Record record){
        recordRepository.delete(record);
    }

    public void deleteAll(){
        recordRepository.deleteAll();
    }
}
