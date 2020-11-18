package com.example.bloodpressurelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {
RecordViewModel recordViewModel;
List<Record> recordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        recordList = new ArrayList<>();

        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        recordViewModel.getAllRecord().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                recordList = records;
            }
        });

    }
}