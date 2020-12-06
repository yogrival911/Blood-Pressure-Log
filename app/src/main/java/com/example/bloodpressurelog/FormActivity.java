package com.example.bloodpressurelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity {
    EditText editTextSys, editTextDia,editTextPulse, editTextBreakFast, editTextLunch, editTextDinner, editTextMed,editTextSym, editTextSalt, editTextRemark;
    Button buttonSubmit;
    TextView textViewPop;
    Spinner spinnerArm, spinnerPosture;
    RecordViewModel recordViewModel;
    SharedPreferences sharedpreferences;
    int lowSys, lowDia,normalSys,normalDia,elevatedSys,elevatedDia,high1Sys,high1Dia,high2Sys,high2Dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);

        spinnerArm = (Spinner)findViewById(R.id.spinnerArm);
        spinnerPosture = (Spinner)findViewById(R.id.spinnerPosture);

        editTextSys = (EditText)findViewById(R.id.editTextSys);
        editTextDia = (EditText)findViewById(R.id.editTextDia);
        editTextPulse =(EditText)findViewById(R.id.editTextPulse);
        editTextBreakFast = (EditText)findViewById(R.id.editTextBreakFast);
        editTextLunch = (EditText)findViewById(R.id.editTextLunch);
        editTextDinner = (EditText)findViewById(R.id.editTextDinner);
        editTextMed = (EditText)findViewById(R.id.editTextMed);
        editTextSalt = (EditText)findViewById(R.id.editTextSalt);
        editTextSym = (EditText)findViewById(R.id.editTextSym);
        editTextRemark = (EditText)findViewById(R.id.editTextRemark);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        textViewPop = (TextView) findViewById(R.id.textViewPop);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        lowSys = sharedpreferences.getInt("lowSys", 90);
        lowDia = sharedpreferences.getInt("lowDia", 60);

        normalSys = sharedpreferences.getInt("normalSys", 120);
        normalDia = sharedpreferences.getInt("normalDia", 75);

        elevatedSys = sharedpreferences.getInt("elevatedSys", 130);
        elevatedDia = sharedpreferences.getInt("elevatedDia", 80);

        high1Sys = sharedpreferences.getInt("high1Sys", 140);
        high1Dia = sharedpreferences.getInt("high1Dia", 90);

        high2Sys = sharedpreferences.getInt("high2Sys", 180);
        high2Dia = sharedpreferences.getInt("high2Dia", 120);


        editTextSys.requestFocus();

        editTextSys.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editTextSys.getText().toString().length()==3){
                    editTextDia.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextDia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int count = editTextDia.getText().toString().length();
                if(count>=2){
                    int sys = Integer.parseInt(editTextSys.getText().toString());
                    int dia = Integer.parseInt(editTextDia.getText().toString());

                    if(sys<=lowSys || dia<=lowDia){
                        textViewPop.setText("BP is Low");
                    }

                    if((sys>lowSys && sys<=normalSys) &&(dia>lowDia && dia<=normalDia)){
                        textViewPop.setText("BP is Normal");
                        textViewPop.setTextColor(Color.parseColor("#2ECC71"));
                    }
                    if((sys>normalSys && sys<=elevatedSys) &&(dia>normalDia && dia<=elevatedDia)){
                        textViewPop.setText("Elevated BP");
                        textViewPop.setTextColor(Color.parseColor("#F7DC6F"));
                    }
                    if((sys>normalSys && sys<=elevatedSys) && (dia>lowDia && dia<=normalDia)){
                        textViewPop.setText("Elevated BP");
                        textViewPop.setTextColor(Color.parseColor("#F7DC6F"));
                    }
                    if((sys>elevatedSys && sys<=high1Sys) || (dia>elevatedDia && dia<=high1Dia)){
                        textViewPop.setText("High BP (Stage-1)");
                        textViewPop.setTextColor(Color.parseColor("#F39C12"));
                    }
                    if((sys>high1Sys && sys<=high2Sys) || (dia>high1Dia && dia<=high2Dia)){
                        textViewPop.setText("High BP (Stage-2)");
                        textViewPop.setTextColor(Color.parseColor("#E67E22"));
                    }
                    if(sys>high2Sys || dia>high2Dia){
                        textViewPop.setText("EMERGENCY");
                        textViewPop.setTextColor(Color.parseColor("#E74C3C"));
                    }
                    if(count==3){
                        editTextBreakFast.requestFocus();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sysH = editTextSys.getText().toString();
                String  diaL = editTextDia.getText().toString();
                if(!sysH.equals("") && !diaL.equals("")){

                    String spinArm = spinnerArm.getSelectedItem().toString();
                    String spinPosture = spinnerPosture.getSelectedItem().toString();
                    String pulse = editTextPulse.getText().toString();
                    String breakfast = editTextBreakFast.getText().toString();
                    String lunch = editTextLunch.getText().toString();
                    String dinner = editTextDinner.getText().toString();
                    String med = editTextMed.getText().toString();
                    String salt = editTextSalt.getText().toString();
                    String sym = editTextSym.getText().toString();
                    String remark = editTextRemark.getText().toString();

                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-YY");
                    String date = sdfDate.format(new Date());
                    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
                    String time = sdfTime.format(new Date());

                    Record newRecord = new Record(sysH,diaL,pulse, spinPosture, spinArm,breakfast,lunch,dinner,med, salt, sym,remark,date,time);

                    recordViewModel.insert(newRecord);
                    finish();
                }
                else{
                    Toast.makeText(FormActivity.this, "Please Enter Data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}