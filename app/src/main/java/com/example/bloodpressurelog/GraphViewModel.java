package com.example.bloodpressurelog;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GraphViewModel extends AndroidViewModel {
    RecordRepository recordRepository;
    private LiveData<List<Record>> allRecord;
    public GraphViewModel(@NonNull Application application) {
        super(application);
        recordRepository = new RecordRepository(application);
        allRecord = recordRepository.getAllRecords();
    }

    public LiveData<List<Record>> getAllRecord() {
        return allRecord;
    }
}
