package com.example.bloodpressurelog;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphFrag extends Fragment {
    LineChart lineChartSys;
    LineChart lineChartDia;
    ScatterChart scatterChart;
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

        recordViewModel = ViewModelProviders.of(getActivity()).get(RecordViewModel.class);
//
        recordViewModel.getAllRecord().observe(getActivity(), new Observer<List<Record>>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChanged(List<Record> records) {
                recordList = records;
                Log.i("yogin", recordList.toString());
                recordList = records;
                Collections.reverse(recordList);
                List<Entry> dataValsSys = new ArrayList<>();
                List<Entry> dataValsDia = new ArrayList<>();
                List<Entry> dataValsScatter = new ArrayList<>();
                for (int i = 0; i < recordList.size(); i++) {
                    int sys = Integer.parseInt(recordList.get(i).getSys());
                    int dia = Integer.parseInt(recordList.get(i).getDia());
                    float sysF = sys;
                    float diaF = dia;
                    float x = i;
                    dataValsSys.add(new Entry(x, sysF));
                    dataValsDia.add(new Entry(x, diaF));
                    dataValsScatter.add(new Entry(diaF, sysF));
                }

                lineChartSys = (LineChart) view.findViewById(R.id.lineChartSys);
                lineChartDia = (LineChart) view.findViewById(R.id.lineChartDia);
//                scatterChart = (ScatterChart)view.findViewById(R.id.scatterChart);
                //Log.i("yog", recordList.toString());

                LineDataSet lineDataSetDia = new LineDataSet(dataValsDia, "Diastolic");
                LineDataSet lineDataSetSys = new LineDataSet(dataValsSys, "Systolic");

//---------------systolic chart-------------------------

                lineDataSetSys.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSetSys.setHighlightEnabled(true);
                lineDataSetSys.setLineWidth(0.5f);
                lineDataSetSys.setColor(R.color.colorPrimaryDark);
                lineDataSetSys.setDrawHighlightIndicators(true);
                lineDataSetSys.setHighLightColor(Color.GREEN);
                lineDataSetSys.setValueTextSize(6);
                lineDataSetSys.setValueTextColor(Color.DKGRAY);
                lineDataSetSys.setDrawFilled(true);
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.gradient_graph);
                lineDataSetSys.setFillDrawable(drawable);

                LineData lineDataSys = new LineData(lineDataSetSys);

                lineChartSys.getDescription().setText("Systolic Chart");
                lineChartSys.getDescription().setTextSize(8);
                lineChartSys.setDrawMarkers(true);
                lineChartSys.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                lineChartSys.getAxisRight().setEnabled(false);
                lineChartSys.animateX(1000);
                lineChartSys.getXAxis().setGranularityEnabled(true);
                lineChartSys.getXAxis().setGranularity(1.0f);

                lineChartSys.getXAxis().setDrawGridLines(false); // hide background grid lines
                lineChartSys.getAxisLeft().setDrawGridLines(false);
                lineChartSys.getAxisRight().setDrawGridLines(false);

                LimitLine ll1 = new LimitLine(140f, "");
                ll1.setLineWidth(1f);
                ll1.enableDashedLine(20f, 10f, 0f);
                ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                ll1.setTextSize(10f);

                YAxis leftAxis = lineChartSys.getAxisLeft();
                leftAxis.addLimitLine(ll1);
                lineChartSys.setData(lineDataSys);
                lineChartSys.invalidate();

//---------------Diastolic chart-------------------------

                lineDataSetDia.setColors(Color.BLUE);
                lineDataSetDia.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSetDia.setHighlightEnabled(true);
                lineDataSetDia.setLineWidth(0.5f);
                lineDataSetDia.setColor(R.color.colorPrimaryDark);
                lineDataSetDia.setDrawHighlightIndicators(true);
                lineDataSetDia.setHighLightColor(Color.GREEN);
                lineDataSetDia.setValueTextSize(6);
                lineDataSetDia.setValueTextColor(Color.DKGRAY);

                lineDataSetDia.setDrawFilled(true);
                lineDataSetDia.setFillDrawable(drawable);

                LineData lineDataDia = new LineData(lineDataSetDia);

                lineChartDia.getDescription().setText("Diastolic Chart");
                lineChartDia.getDescription().setTextSize(8);
                lineChartDia.setDrawMarkers(true);
                lineChartDia.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                lineChartDia.getAxisRight().setEnabled(false);
                lineChartDia.animateX(1000);
                lineChartDia.getXAxis().setGranularityEnabled(true);
                lineChartDia.getXAxis().setGranularity(1.0f);
//                lineChartDia.getXAxis().setLabelCount(lineDataSetSys.getEntryCount());

                lineChartDia.getXAxis().setDrawGridLines(false); // hide background grid lines
                lineChartDia.getAxisLeft().setDrawGridLines(false);
                lineChartDia.getAxisRight().setDrawGridLines(false);

                LimitLine ll2 = new LimitLine(70f, "");
                ll2.setLineWidth(1f);
                ll2.enableDashedLine(20f, 10f, 0f);
                ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                ll2.setTextSize(10f);

                YAxis leftAxisDia = lineChartDia.getAxisLeft();
                leftAxisDia.addLimitLine(ll2);
                leftAxisDia.setAxisMaximum(100f);

                lineChartDia.setData(lineDataDia);
                lineChartDia.invalidate();

//---------------Scatter chart-------------------------
//
//                ScatterDataSet scatterDataSet = new ScatterDataSet(dataValsScatter,"Label");
//                scatterDataSet.setColor(Color.RED);
//                ScatterData scatterData = new ScatterData(scatterDataSet);

//                XAxis xAxisScatter = scatterChart.getXAxis();
//                YAxis yAxisScatter = scatterChart.getAxisLeft();
//                xAxisScatter.setAxisMaximum(100f);
//                yAxisScatter.setAxisMaximum(200f);
//
//
//                scatterChart.getXAxis().setDrawGridLines(false);
//                scatterChart.getAxisLeft().setDrawGridLines(false);
//                scatterChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//                scatterChart.getDescription().setText("Systolic/Diastolic");

//                scatterChart.setData(scatterData);
//                scatterChart.invalidate();
            }
        });
        return view;
    }
}
