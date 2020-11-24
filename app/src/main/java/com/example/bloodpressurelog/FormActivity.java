package com.example.bloodpressurelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity {
    EditText editTextSys, editTextDia,editTextPulse,editTextPosture, editTextPosition, editTextBreakFast, editTextLunch, editTextDinner, editTextMed,editTextSym, editTextSalt, editTextRemark;
    Button buttonSubmit;
    TextView textViewPop;
    RecordViewModel recordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);

        editTextSys = (EditText)findViewById(R.id.editTextSys);
        editTextDia = (EditText)findViewById(R.id.editTextDia);
        editTextPulse =(EditText)findViewById(R.id.editTextPulse);
        editTextPosture = (EditText)findViewById(R.id.editTextPosture);
        editTextPosition = (EditText)findViewById(R.id.editTextPosition);
        editTextBreakFast = (EditText)findViewById(R.id.editTextBreakFast);
        editTextLunch = (EditText)findViewById(R.id.editTextLunch);
        editTextDinner = (EditText)findViewById(R.id.editTextDinner);
        editTextMed = (EditText)findViewById(R.id.editTextMed);
        editTextSalt = (EditText)findViewById(R.id.editTextSalt);
        editTextSym = (EditText)findViewById(R.id.editTextSym);
        editTextRemark = (EditText)findViewById(R.id.editTextRemark);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        textViewPop = (TextView) findViewById(R.id.textViewPop);


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

                    if(sys<120 && dia<80){
                        textViewPop.setText("BP is Normal");
                        textViewPop.setTextColor(Color.parseColor("#2ECC71"));
                    }
                    if(sys>=120 && sys<=129 && dia<80){
                        textViewPop.setText("Elevated BP");
                        textViewPop.setTextColor(Color.parseColor("#F7DC6F"));
                    }
                    if((sys>130 && sys<139) || (dia>80 && dia<=89)){
                        textViewPop.setText("High BP (Stage-1)");
                        textViewPop.setTextColor(Color.parseColor("#F39C12"));
                    }
                    if((sys>=140 && sys<180) || (dia>=90 && dia<=120)){
                        textViewPop.setText("High BP (Stage-2)");
                        textViewPop.setTextColor(Color.parseColor("#E67E22"));
                    }
                    if(sys>=180 && dia>120){
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
                String pulse = editTextPulse.getText().toString();
                String posture = editTextPosture.getText().toString();
                String position = editTextPosition.getText().toString();
                String breakfast = editTextBreakFast.getText().toString();
                String lunch = editTextLunch.getText().toString();
                String dinner = editTextDinner.getText().toString();
                String med = editTextMed.getText().toString();
                String salt = editTextSalt.getText().toString();
                String sym = editTextSym.getText().toString();
                String remark = editTextRemark.getText().toString();

                SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-YY");
                String date = sdfDate.format(new Date());
                SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
                String time = sdfTime.format(new Date());

                Record newRecord = new Record(sysH,diaL,pulse, posture, position,breakfast,lunch,dinner,med, salt, sym,remark,date,time);

                    recordViewModel.insert(newRecord);
                    finish();
            }
        });


    }
}