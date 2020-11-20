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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        final String[] tabTitles = {"List", "Graph", "Monday"};


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

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setMessage("Do you want to delete all records ?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recordViewModel.deleteAll();
                Toast.makeText(getApplicationContext(), "All Records Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_SHORT).show();
            }
        });


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
                Toast.makeText(this,"Pdf", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");
                startActivityForResult(intent, CREATE_FILE);

                break;
            case R.id.reminder:
                Intent remindActivity = new Intent(this, ReminderActivity.class);
                startActivity(remindActivity);
                break;
            case R.id.deleteAll:
                alertDialogBuilder.create();
                alertDialogBuilder.show();

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
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(2480,3508 ,1).create();
                PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

                Canvas canvas = myPage.getCanvas();
                myPaint.setTextSize(30);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                canvas.drawText("Sr.No.",200f, 200f, myPaint);
                canvas.drawText("Date",350f, 200f, myPaint);
                canvas.drawText("Time",550f, 200f, myPaint);
                canvas.drawText("Sys / Dia",700f, 200f, myPaint);

                myPaint.setTextSize(20);
                myPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                for(int i=0; i<recordList.size(); i++){
                    canvas.drawText(i+1+".",210f, 244f+i*44f, myPaint);
                    canvas.drawText(recordList.get(i).getDate(),320f, 244f+i*44f, myPaint);
                    canvas.drawText(recordList.get(i).getTime(),550f, 244f+i*44f, myPaint);
                    canvas.drawText(recordList.get(i).getSys() + " / " + recordList.get(i).getDia(),700f, 244f+i*44f, myPaint);
                }

                myPdfDocument.finishPage(myPage);

                uriList.add(data.getData());
                try {
                    Uri uri = data.getData();
                    OutputStream os = getContentResolver().openOutputStream(uri);
                    myPdfDocument.writeTo(os);
                    os.close();
                    myPdfDocument.close();
                    Log.i("yog", "saved");
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