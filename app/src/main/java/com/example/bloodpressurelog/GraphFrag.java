package com.example.bloodpressurelog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphFrag extends Fragment {
    LineChart lineChart;
    RecordViewModel recordViewModel;
    ListFragment listFragment;
    List<Record> recordList=new ArrayList<>();
    MyAdapter adapter;

    public void setRecordList(List<Record> recordListPassed) {
        recordList = recordListPassed;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_frag, container, false);

//        listFragment =new ListFragment();
//        adapter = new MyAdapter(getContext());
//        recordList = adapter.getRecordList();
          //recordList = new ArrayList<>();
//          listFrag = new ListFrag();
//          recordList = listFrag.getFragRecordList();
        recordViewModel = ViewModelProviders.of(getActivity()).get(RecordViewModel.class);
//
        recordViewModel.getAllRecord().observe(getActivity(), new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                recordList = records;
                Log.i("yogin", recordList.toString());
                recordList = records;

                List<Entry> dataVals = new ArrayList<>();
                for(int i=0;i<recordList.size(); i++){
                    int sys = Integer.parseInt(recordList.get(i).getSys());
                    int dia = Integer.parseInt(recordList.get(i).getDia());
                    float sysF = sys;
                    float x = i;
                    dataVals.add(new Entry(x,sysF));
                }

                lineChart = (LineChart)view.findViewById(R.id.lineChart);
                    //Log.i("yog", recordList.toString());

                    LineDataSet lineDataSet = new LineDataSet(dataVals, "Systolic");
                    List<ILineDataSet> iLineDataSets = new ArrayList<>();

                    iLineDataSets.add(lineDataSet);
                    LineData lineData = new LineData(iLineDataSets);

                    lineChart.setData(lineData);
                    lineChart.invalidate();

            }
        });

        return view;
    }
}
