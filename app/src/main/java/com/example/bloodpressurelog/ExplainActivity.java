package com.example.bloodpressurelog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        setTitle("Guide");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ExpandableTextView expandNumberMean = (ExpandableTextView) findViewById(R.id.numberMean);
        expandNumberMean.setText(getResources().getString(R.string.number_mean));
        expandNumberMean.setTrimLength(25);

        ExpandableTextView expandNormal = (ExpandableTextView) findViewById(R.id.normalVal);
        expandNormal.setText(getResources().getString(R.string.normal));
        expandNormal.setTrimLength(24);

        ExpandableTextView elevated = (ExpandableTextView) findViewById(R.id.elevated);
        elevated.setText(getResources().getString(R.string.elevated));
        elevated.setTrimLength(23);

        ExpandableTextView high1 = (ExpandableTextView) findViewById(R.id.high1);
        high1.setText(getResources().getString(R.string.high1));
        high1.setTrimLength(27);

        ExpandableTextView high2 = (ExpandableTextView) findViewById(R.id.high2);
        high2.setText(getResources().getString(R.string.high2));
        high2.setTrimLength(27);

        ExpandableTextView emergency = (ExpandableTextView) findViewById(R.id.emergency);
        emergency.setText(getResources().getString(R.string.emergency));
        emergency.setTrimLength(9);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}