package com.example.bloodpressurelog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FragmentStateAdapter pagerAdapter;
    FloatingActionButton fab;
    RecordViewModel recordViewModel;
    List<Record> recordList = new ArrayList<>();
    private static final int CREATE_FILE = 1;
    private static final int SHOW_FILE =2;
    PdfDocument myPdfDocument;
    List<Uri> uriList;
    AlertDialog.Builder alertDialogBuilder;
    Bitmap bitmap, scaleBitmap, lineBitmapMain, lineScaleBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bp_log_icon);
        scaleBitmap = Bitmap.createScaledBitmap(bitmap,200,200,false);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                startActivity(intent);
            }
        });


        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        recordViewModel.getAllRecord().observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                recordList=records;
            }
        });

        uriList = new ArrayList<>();

        final String[] tabTitles = {"List", "Graph", "Status"};


        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        //TabLayoutMediator(tabLayout, viewPager2
        TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy =new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        };
        TabLayoutMediator mediator;
        viewPager2 = findViewById(R.id.viewPager2);
        pagerAdapter = new DemoFragAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater ();
        inflater.inflate ( R.menu.main_menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.pdf:
                if(recordList.size()!=0){
                    Toast.makeText(this,"Pdf", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("application/pdf");
                    intent.putExtra(Intent.EXTRA_TITLE, "Blood Pressure Log.pdf");
                    startActivityForResult(intent, CREATE_FILE);
                }
                else{
                    Toast.makeText(this,"Data not available", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialogBuilderPdf = new AlertDialog.Builder(this);
                    alertDialogBuilderPdf.setIcon(R.drawable.warning_icon);
                    alertDialogBuilderPdf.setTitle("Data not available!");
                    alertDialogBuilderPdf.setMessage("Please log readings to save data as PDF file");
                    alertDialogBuilderPdf.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialogBuilderPdf.create().show();
                }


                break;
            case R.id.reminder:
                Intent remindActivity = new Intent(this, ReminderActivity.class);
                startActivity(remindActivity);
                break;

            case R.id.deleteAll:
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setIcon(R.drawable.warning_icon);
                alertDialogBuilder.setTitle("Warning!");
                alertDialogBuilder.setMessage("Do you want to delete all readings ?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recordViewModel.deleteAll();
                        Toast.makeText(getApplicationContext(), "All readings Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.create().show();
                break;

            case R.id.deleteSelected:
                Intent intentDelete = new Intent(this, SelectedDeleteActivity.class);
                startActivity(intentDelete);
                break;

            case R.id.explained:
                Intent intentExplain = new Intent(this, ExplainedActivity.class);
                startActivity(intentExplain);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            if (data != null) {
                // Perform operations on the document using its URI.
                Log.i("yog", data.getData().toString());

                myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                int pageWidth = 1240;
                int pageHeight = 1754;
                int leftMargin = 50;
                int rightMargin = 50;
                int topMargin = 400;
                int bottomMargin = 50;
                int rowWidth = 50;
                int linewidth = 16;
                int numberWidth = leftMargin+20;
                int dateWidth = numberWidth + 100;
                int timeWidth = dateWidth + 150;
                int sysDiaWidth = timeWidth + 200;
                int pulseWidth = sysDiaWidth + 150;
                int armWidth = pulseWidth + 150;
                int remarkWidth = armWidth + 200;

                int totalReadings = recordList.size();
                int perPageReadings = 25;
                int batches = totalReadings/perPageReadings;
                if(totalReadings % perPageReadings != 0){
                    batches++;
                    for(int m=0;m<25-(totalReadings % perPageReadings);m++){
                        recordList.add(new Record("","","","","","","","","","","","","",""));
                    }
                }
                int k=0;
                int fifty=25;
               for(int j=0 ; j < batches; j++){
                   PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth,pageHeight ,j).create();
                   PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

                   Canvas canvas = myPage.getCanvas();
                   myPaint.setTextSize(40);
                   myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                   canvas.drawText("Blood Pressure Readings",timeWidth+100 , topMargin-250, myPaint);

                   myPaint.setTextSize(22);
                   myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                   canvas.drawText("Generated by Blood Pressure Log Android App  ",timeWidth+100 , topMargin-200, myPaint);
                   canvas.drawText("Name : ___________________",numberWidth , topMargin-100, myPaint);

                   myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                   canvas.drawLine(leftMargin,topMargin-40,pageWidth - rightMargin,topMargin-40,myPaint);

                   canvas.drawLine(leftMargin,topMargin+20,pageWidth - rightMargin,topMargin+20,myPaint);

                   canvas.drawText("Sr.No.",numberWidth, topMargin, myPaint);
                   canvas.drawText("Date",dateWidth, topMargin, myPaint);
                   canvas.drawText("Time",timeWidth, topMargin, myPaint);
                   canvas.drawText("Sys / Dia",sysDiaWidth, topMargin, myPaint);
                   canvas.drawText("Pulse Rate",pulseWidth, topMargin, myPaint);
                   canvas.drawText("Arm/Posture",armWidth, topMargin, myPaint);
                   canvas.drawText("Remark",remarkWidth, topMargin, myPaint);
//           ------------------vertical lines (below)-----------------
                   canvas.drawLine(leftMargin,topMargin-40,leftMargin,pageHeight-bottomMargin-40, myPaint);

                   canvas.drawLine(numberWidth+70,topMargin-40,numberWidth+70,pageHeight-bottomMargin-40, myPaint);
                   canvas.drawLine(dateWidth+110,topMargin-40,dateWidth+110,pageHeight-bottomMargin-40, myPaint);
                   canvas.drawLine(timeWidth+140,topMargin-40,timeWidth+140,pageHeight-bottomMargin-40, myPaint);
                   canvas.drawLine(sysDiaWidth+120,topMargin-40,sysDiaWidth+120,pageHeight-bottomMargin-40, myPaint);
                   canvas.drawLine(pulseWidth+120,topMargin-40,pulseWidth+120,pageHeight-bottomMargin-40, myPaint);
                   canvas.drawLine(armWidth+160,topMargin-40,armWidth+160,pageHeight-bottomMargin-40, myPaint);

                   canvas.drawLine(pageWidth - rightMargin,topMargin-40,pageWidth - rightMargin,pageHeight-bottomMargin-40, myPaint);
//          ----------------------------------------------------------

//                canvas.drawBitmap(scaleBitmap,2100,250f,myPaint);

                   myPaint.setTextSize(20);
                   myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                    int iWidth = 0;
                   for(int i=k; i<fifty; i++){
                       canvas.drawText(i+1+".",numberWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawText(recordList.get(i).getDate(),dateWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawText(recordList.get(i).getTime(),timeWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawText(recordList.get(i).getSys() + " / " + recordList.get(i).getDia(),sysDiaWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawText(recordList.get(i).getPulse(),pulseWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawText(recordList.get(i).getPosition()+" / " + recordList.get(i).getPosture(),armWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawText(recordList.get(i).getRemark(),remarkWidth, topMargin + rowWidth + iWidth * rowWidth, myPaint);
                       canvas.drawLine(leftMargin,topMargin + rowWidth + iWidth * rowWidth + linewidth,pageWidth - rightMargin,topMargin + rowWidth + iWidth * rowWidth + linewidth, myPaint);
                       iWidth++;
                   }
                   k=k+25;
                   fifty = fifty+25;
                   myPdfDocument.finishPage(myPage);
               }



                uriList.add(data.getData());
                try {
                    Uri uri = data.getData();
                    OutputStream os = getContentResolver().openOutputStream(uri);
                    myPdfDocument.writeTo(os);
                    os.close();
                    myPdfDocument.close();
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e){
                    Log.i("yog", "error");
                }


            }
        }


        if (requestCode == SHOW_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(data.getData(), "application/pdf");

                Log.i("yog","show pdf"+data.toString());

            }
        }
    }
}