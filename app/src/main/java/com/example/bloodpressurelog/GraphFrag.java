package com.example.bloodpressurelog;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphFrag extends Fragment {
    LineChart lineChart;
    RecordViewModel recordViewModel;
    ListFragment listFragment;
    List<Record> recordList = new ArrayList<>();
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
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(List<Record> records) {
                recordList = records;
                Log.i("yogin", recordList.toString());
                recordList = records;

                List<Entry> dataValsSys = new ArrayList<>();
                List<Entry> dataValsDia = new ArrayList<>();
                for (int i = 0; i < recordList.size(); i++) {
                    int sys = Integer.parseInt(recordList.get(i).getSys());
                    int dia = Integer.parseInt(recordList.get(i).getDia());
                    float sysF = sys;
                    float diaF = dia;
                    float x = i;
                    dataValsSys.add(new Entry(x, sysF));
                    dataValsDia.add(new Entry(x, diaF));
                }

                lineChart = (LineChart) view.findViewById(R.id.lineChart);
                //Log.i("yog", recordList.toString());

                LineDataSet lineDataSetDia = new LineDataSet(dataValsDia, "Diastolic");
                LineDataSet lineDataSetSys = new LineDataSet(dataValsSys, "Systolic");
             //   lineDataSetSys.setCircleColor(Color.RED);
                lineDataSetSys.setColors(R.color.colorPrimaryDark);
                lineDataSetSys.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSetSys.setHighlightEnabled(true);
                lineDataSetSys.setLineWidth(2);
                lineDataSetSys.setColor(Color.GREEN);
               // lineDataSetSys.setCircleColor(Color.RED);
              //  lineDataSetSys.setCircleRadius(6);
              //  lineDataSetSys.setCircleHoleRadius(3);
                lineDataSetSys.setDrawHighlightIndicators(true);
                lineDataSetSys.setHighLightColor(Color.GREEN);
                lineDataSetSys.setValueTextSize(6);
                lineDataSetSys.setValueTextColor(Color.DKGRAY);

//                lineDataSetDia.setCircleColor(Color.RED);
                lineDataSetDia.setColors(Color.BLUE);
                lineDataSetDia.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSetDia.setHighlightEnabled(true);
                lineDataSetDia.setLineWidth(2);
                lineDataSetDia.setColor(Color.BLUE);
//                lineDataSetDia.setCircleColor(Color.RED);
//                lineDataSetDia.setCircleRadius(6);
//                lineDataSetDia.setCircleHoleRadius(3);
                lineDataSetDia.setDrawHighlightIndicators(true);
                lineDataSetDia.setHighLightColor(Color.GREEN);
                lineDataSetDia.setValueTextSize(6);
                lineDataSetDia.setValueTextColor(Color.DKGRAY);

                lineChart.getDescription().setText("Blood Pressure Chart");
                lineChart.getDescription().setTextSize(12);
                lineChart.setDrawMarkers(true);
                lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                lineChart.animateY(1000);
                lineChart.getXAxis().setGranularityEnabled(true);
                lineChart.getXAxis().setGranularity(1.0f);
                lineChart.getXAxis().setLabelCount(lineDataSetSys.getEntryCount());

                lineChart.getXAxis().setDrawGridLines(false); // hide background grid lines
                lineChart.getAxisLeft().setDrawGridLines(false);
                lineChart.getAxisRight().setDrawGridLines(false);



                List<ILineDataSet> iLineDataSets = new ArrayList<>();

                iLineDataSets.add(lineDataSetSys);
                iLineDataSets.add(lineDataSetDia);
                LineData lineData = new LineData(iLineDataSets);

                LimitLine ll1 = new LimitLine(140f, "");
                ll1.setLineWidth(1f);
                ll1.enableDashedLine(20f, 10f, 0f);
                ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                ll1.setTextSize(10f);

                LimitLine ll2 = new LimitLine(70f, "");
                ll2.setLineWidth(1f);
                ll2.enableDashedLine(20f, 10f, 0f);
                ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                ll2.setTextSize(10f);

                YAxis leftAxis = lineChart.getAxisLeft();

                leftAxis.addLimitLine(ll1);

                leftAxis.addLimitLine(ll2);

                lineChart.setData(lineData);
                lineChart.invalidate();

            }
        });

        return view;
    }
}
