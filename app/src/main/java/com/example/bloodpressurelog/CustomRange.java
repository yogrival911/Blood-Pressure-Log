package com.example.bloodpressurelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomRange extends AppCompatActivity {
SharedPreferences sharedpreferences;
EditText editTextLowSys,editTextLowDia,editTextNormalSys,editTextNormalDia,editTextElevatedSys,editTextElevatedDia;
EditText editTextHigh1Sys,editTextHigh1Dia,editTextHigh2Sys,editTextHigh2Dia,editTextEmerSys,editTextEmerDia;
Button savePrefButton, defaultPrefButton;
int lowSys, lowDia,normalSys,normalDia,elevatedSys,elevatedDia,high1Sys,high1Dia,high2Sys,high2Dia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_range);

        editTextLowSys = (EditText)findViewById(R.id.editTextLowSys);
        editTextLowDia = (EditText)findViewById(R.id.editTextLowDia);
        editTextNormalSys = (EditText)findViewById(R.id.editTextNormalSys);
        editTextNormalDia = (EditText)findViewById(R.id.editTextNormalDia);
        editTextElevatedSys = (EditText)findViewById(R.id.editTextElevatedSys);
        editTextElevatedDia = (EditText)findViewById(R.id.editTextElevatedDia);
        editTextHigh1Sys = (EditText)findViewById(R.id.editTextHigh1Sys);
        editTextHigh1Dia = (EditText)findViewById(R.id.editTextHigh1Dia);
        editTextHigh2Sys = (EditText)findViewById(R.id.editTextHigh2Sys);
        editTextHigh2Dia = (EditText)findViewById(R.id.editTextHigh2Dia);
        editTextEmerSys = (EditText)findViewById(R.id.editTextEmerSys);
        editTextEmerDia = (EditText)findViewById(R.id.editTextEmerDia);

        savePrefButton = (Button)findViewById(R.id.savePrefButton);
        defaultPrefButton = (Button)findViewById(R.id.defaultPrefButton);

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

//        int n = sharedPreferences.getInt("lowSys", 90);
//        Log.i("yogshare", "custom "+n);
        editTextLowSys.setText(""+lowSys);
        editTextLowDia.setText(""+lowDia);

        editTextNormalSys.setText(""+normalSys);
        editTextNormalDia.setText(""+normalDia);

        editTextElevatedSys.setText(""+elevatedSys);
        editTextElevatedDia.setText(""+elevatedDia);

        editTextHigh1Sys.setText(""+high1Sys);
        editTextHigh1Dia.setText(""+high1Dia);

        editTextHigh2Sys.setText(""+high2Sys);
        editTextHigh2Dia.setText(""+high2Dia);

        editTextEmerSys.setText(""+high2Sys);
        editTextEmerDia.setText(""+high2Dia);

        savePrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eLowSys = editTextLowSys.getText().toString();
                String eLowDia = editTextLowDia.getText().toString();
                String eNorSys = editTextNormalSys.getText().toString();
                String eNorDia = editTextNormalDia.getText().toString();
                String eEleSys = editTextElevatedSys.getText().toString();
                String eEleDia = editTextElevatedDia.getText().toString();
                String eH1Sys = editTextHigh1Sys.getText().toString();
                String eH1Dia = editTextHigh1Dia.getText().toString();
                String eH2Sys = editTextHigh2Sys.getText().toString();
                String eH2Dia = editTextHigh2Dia.getText().toString();
                if(eLowSys.matches("")||eLowDia.matches("")||eNorSys.matches("")||eNorDia.matches("")||
                    eEleSys.matches("")||eEleDia.matches("")||eH1Sys.matches("")||eH1Dia.matches("")||
                    eH2Sys.matches("")||eH2Dia.matches("")){
                    Log.i("yogcustom", "empty");
                    Toast.makeText(getApplicationContext(), "Field can not be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    int editLowSys = Integer.parseInt(editTextLowSys.getText().toString());
                    int editLowDia = Integer.parseInt(editTextLowDia.getText().toString());
                    int editNormalSys = Integer.parseInt(editTextNormalSys.getText().toString());
                    int editNormalDia = Integer.parseInt(editTextNormalDia.getText().toString());
                    int editElevatedSys = Integer.parseInt(editTextElevatedSys.getText().toString());
                    int editElevatedDia = Integer.parseInt(editTextElevatedDia.getText().toString());
                    int editHigh1Sys = Integer.parseInt(editTextHigh1Sys.getText().toString());
                    int editHigh1Dia = Integer.parseInt(editTextHigh1Dia.getText().toString());
                    int editHigh2Sys = Integer.parseInt(editTextHigh2Sys.getText().toString());
                    int editHigh2Dia = Integer.parseInt(editTextHigh2Dia.getText().toString());

                    if((editNormalSys>editLowSys)&&(editNormalDia>editLowDia)&&(editElevatedSys>editNormalSys)&&(editElevatedDia>editNormalDia)&&(editHigh1Sys>editElevatedSys)&&(editHigh1Dia>editElevatedDia)&&(editHigh2Sys>editHigh1Sys)&&(editHigh2Dia>editHigh1Dia)){
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//                    int editLowSys = Integer.valueOf(editTextLowSys.getText().toString());
//                    editor.putInt("lowSys",editLowSys );
//                    editor.commit();
//                    finish();

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("lowSys",editLowSys);
                        editor.putInt("lowDia",editLowDia);
                        editor.putInt("normalSys",editNormalSys);
                        editor.putInt("normalDia",editNormalDia);
                        editor.putInt("elevatedSys",editElevatedSys);
                        editor.putInt("elevatedDia",editElevatedDia);
                        editor.putInt("high1Sys",editHigh1Sys);
                        editor.putInt("high1Dia",editHigh1Dia);
                        editor.putInt("high2Dia",editHigh2Sys);
                        editor.putInt("high2Dia",editHigh2Dia);
                        editor.commit();
                        finish();

                        Log.i("yogvalid", "valid");
                    }
                    else{

                        Log.i("yogvalid", "invalid");
                        Toast.makeText(getApplicationContext(),"Invalid Value", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        defaultPrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor2 = sharedpreferences.edit();
                editor2.putInt("lowSys",90);
                editor2.putInt("lowDia",60);
                editor2.putInt("normalSys",120);
                editor2.putInt("normalDia",75);
                editor2.putInt("elevatedSys",130);
                editor2.putInt("elevatedDia",80);
                editor2.putInt("high1Sys",140);
                editor2.putInt("high1Dia",90);
                editor2.putInt("high2Dia",180);
                editor2.putInt("high2Dia",120);
                editor2.commit();

                editTextLowSys.setText(""+90);
                editTextLowDia.setText(""+60);

                editTextNormalSys.setText(""+120);
                editTextNormalDia.setText(""+75);

                editTextElevatedSys.setText(""+130);
                editTextElevatedDia.setText(""+80);

                editTextHigh1Sys.setText(""+140);
                editTextHigh1Dia.setText(""+90);

                editTextHigh2Sys.setText(""+180);
                editTextHigh2Dia.setText(""+120);

                editTextEmerSys.setText(""+180);
                editTextEmerDia.setText(""+120);
            }
        });

    }
}