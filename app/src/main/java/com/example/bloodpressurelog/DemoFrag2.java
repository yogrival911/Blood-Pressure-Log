package com.example.bloodpressurelog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DemoFrag2 extends Fragment {
    List<Record> recordList;
    int total, normal, elevated, stage1, stage2, emergency;
    TextView textViewTotal,textViewNormal, textViewElevated, textViewStage1, textViewStage2, textViewEmergency;
    RecordViewModel recordViewModel;
    PieChart pieChart;
    ImageView share;
    ConstraintLayout conLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_frag_2, container, false);

        pieChart =(PieChart)view.findViewById(R.id.pieChart);
        recordList= new ArrayList<>();
        textViewTotal = (TextView)view.findViewById(R.id.textViewTotal);
        textViewNormal = (TextView)view.findViewById(R.id.textViewNormal);
        textViewElevated = (TextView)view.findViewById(R.id.textViewElevated);
        textViewStage1 = (TextView)view.findViewById(R.id.textViewStage1);
        textViewStage2 = (TextView)view.findViewById(R.id.textViewStage2);
        textViewEmergency = (TextView)view.findViewById(R.id.textViewEmergency);
        share = (ImageView)view.findViewById(R.id.share);
        conLayout = (ConstraintLayout)view.findViewById(R.id.conLayout);
//        share.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View view) {
//                Bitmap bitmap = Bitmap.createBitmap(pieChart.getWidth(),pieChart.getHeight(),Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                Drawable drawable = pieChart.getBackground();
//                if(drawable != null){
//                    drawable.draw(canvas);
//                }
//                else {
//                    canvas.drawColor(Color.WHITE);
//                }
//                pieChart.draw(canvas);
//                File file = new File(getActivity().getExternalCacheDir(),"background.png");
//                try {
//                    FileOutputStream fileOutputStream = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
//                    fileOutputStream.flush();
//                    fileOutputStream.close();
//                    file.setReadable(true,true);
//                    Log.i("yog", "setRead");
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//                    intent.setType(("image/png"));
//                    startActivity(Intent.createChooser(intent, "Share via"));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        recordViewModel = ViewModelProviders.of(getActivity()).get(RecordViewModel.class);
        recordViewModel.getAllRecord().observe(getActivity(), new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                recordList=records;
                total=recordList.size();
                normal=0;
                elevated=0;
                stage1=0;
                stage2=0;
                emergency=0;
                Log.i("yog",Integer.toString(recordList.size()));
                for(int i =0; i<recordList.size(); i++){
                    int sys = Integer.parseInt(recordList.get(i).getSys());
                    int dia = Integer.parseInt(recordList.get(i).getDia());
                    if(sys<120 && dia<80){
                        normal++;
                    }
                    if((sys>=120 && sys<=129) && dia<80){
                        elevated++;

                    }
                    if((sys>130 && sys<139) || (dia>80 && dia<=89)){
                        stage1++;
                    }
                    if((sys>=140 && sys<180) || (dia>=90 && dia<=120)){
                        stage2++;
                    }
                    if(sys>=180 || dia>120){
                        emergency++;
                    }
                }

//                int normalPer = (normal/total)*100;
//                int elevatedPer = (elevated/total)*100;
//                int stage1Per = (stage1/total)*100;
//                int stage2Per = (stage2/total)*100;
//                float emergencyPer = (emergency/total)*100;
//
//                float normalF = (float)normalPer;
//                float elevatedF =(float) elevatedPer;
//                float stage1F = (float) stage1Per;
//                float stage2F = (float) stage2Per;
//                float emergencyF = (float)emergencyPer;

//                float normalPer = (float)((normal/total)*100);
//                float elevatedPer = (float)((elevated/total)*100);
//                float stage1Per = (float)((stage1/total)*100);
//                float stage2Per = (float)((stage2/total)*100);
//                float emergencyPer = (float)((emergency/total)*100);

                float normalF = normal;
                float elevatedF = elevated;
                float stage1F = stage1;
                float stage2F = stage2;
                float emergencyF = emergency;
                PieEntry pieEntryNormal = new PieEntry(normalF,0);
                pieEntryNormal.setLabel("Normal");

                PieEntry pieEntryElevated = new PieEntry(elevatedF,1);
                pieEntryElevated.setLabel("Elevated");

                PieEntry pieEntryStage1 = new PieEntry(stage1F,2);
                pieEntryStage1.setLabel("Stage-1");

                PieEntry pieEntryStage2 = new PieEntry(stage2F,3);
                pieEntryStage2.setLabel("Stage-2");

                PieEntry pieEntryEmergency = new PieEntry(emergencyF,4);
                pieEntryEmergency.setLabel("Emergency");


                List<PieEntry> dataValsPie = new ArrayList<>();
                dataValsPie.add(pieEntryNormal);
                dataValsPie.add(pieEntryElevated);
                dataValsPie.add(pieEntryStage1);
                dataValsPie.add(pieEntryStage2);
                dataValsPie.add(pieEntryEmergency);

                PieDataSet pieDataSet = new PieDataSet(dataValsPie,"");
                int[] MY_COLORS = {
                        Color.  rgb(0,194,111),
                        Color. rgb(218,222,4),
                        Color. rgb(235,166,28),
                        Color. rgb(235,97,28),
                        Color. rgb(235,59,28)
                };

                ArrayList<Integer> colors = new ArrayList<>();

                for(int c: MY_COLORS) colors.add(c);

                pieDataSet.setColors(colors);
                PieData pieData = new PieData(pieDataSet);
                pieChart.animateXY(1000,1000);
                pieChart.setDrawEntryLabels(false);
                pieChart.setCenterText("Pie chart");
                pieChart.setEntryLabelTextSize(6);
                pieChart.getDescription().setText("");
                pieChart.setData(pieData);
                pieChart.invalidate();

                textViewTotal.setText(String.valueOf(total));
                textViewNormal.setText(String.valueOf(normal));
                textViewElevated.setText(String.valueOf(elevated));
                textViewStage1.setText(String.valueOf(stage1));
                textViewStage2.setText(String.valueOf(stage2));
                textViewEmergency.setText(String.valueOf(emergency));

            }
        });

        return  view;
    }
}
