package com.example.bloodpressurelog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    Switch aSwitch, aSwitch2, aSwitch3;
    TimePicker timePicker;
    Button okayButton;
    TextView textViewTime, textViewTime2, textViewTime3;

    int hour, minute;
    int hour2, minute2;
    int hour3, minute3;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    boolean isAlarmOn, isAlarmOn2, isAlarmOn3;
    String savedAlarmTime,savedAlarmTime2,savedAlarmTime3;

    AlarmManager alarmManager;
    NotificationChannel notificationChannel;
    Calendar calendar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        aSwitch = (Switch) findViewById(R.id.aSwitch);
        aSwitch2 = (Switch)findViewById(R.id.aSwitch2);
        aSwitch3 = (Switch)findViewById(R.id.aSwitch3);

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        textViewTime2 = (TextView) findViewById(R.id.textViewTime2);
        textViewTime3 = (TextView) findViewById(R.id.textViewTime3);

        aSwitch2.setVisibility(View.INVISIBLE);
        aSwitch3.setVisibility(View.INVISIBLE);
        textViewTime2.setVisibility(View.INVISIBLE);
        textViewTime3.setVisibility(View.INVISIBLE);

        setTitle("Reminder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        okayButton = (Button) findViewById(R.id.okayButton);

        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        savedAlarmTime = sharedPreferences.getString("time", "");
        isAlarmOn = sharedPreferences.getBoolean("isAlarmOn", false);
        textViewTime.setText(savedAlarmTime);
        aSwitch.setChecked(isAlarmOn);

        savedAlarmTime2 = sharedPreferences.getString("time2", "");
        isAlarmOn2 = sharedPreferences.getBoolean("isAlarmOn2", false);
        textViewTime2.setText(savedAlarmTime2);
        aSwitch2.setChecked(isAlarmOn2);

        savedAlarmTime3 = sharedPreferences.getString("time3", "");
        isAlarmOn3 = sharedPreferences.getBoolean("isAlarmOn3", false);
        textViewTime3.setText(savedAlarmTime3);
        aSwitch3.setChecked(isAlarmOn3);

        calendar = Calendar.getInstance();

        alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Intent activityIntent = new Intent(this, FormActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.heart_black)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.bp_log_icon))
                .setContentTitle("Blood Pressure ALert")
                .setContentText("Take Blood Pressure Reading")
                .setContentIntent(activityPendingIntent)
                .setChannelId("111")
                .setAutoCancel(true);
        Notification myNotification = builder.build();


        Intent broadcasteIntent = new Intent(this, MyBroadcasteReceiver.class);
        broadcasteIntent.putExtra(MyBroadcasteReceiver.NOTIFICATION_ID, 1);
        broadcasteIntent.putExtra(MyBroadcasteReceiver.NOTIFICATION, myNotification);
        final PendingIntent broadcastePendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, broadcasteIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel("111","News", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("This is news category");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            Log.i("yog", "in Build");
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b==true){
                    Log.i("yog", "ON");
                    timePicker.setVisibility(View.VISIBLE);
                    okayButton.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        okayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hour = timePicker.getHour();
                                minute = timePicker.getMinute();
                                savedAlarmTime = Integer.toString(hour) + ":" + Integer.toString(minute);
                                textViewTime.setText(savedAlarmTime);


                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, hour);
                                calendar.set(Calendar.MINUTE, minute);

                                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                        AlarmManager.INTERVAL_DAY, broadcastePendingIntent);

                                editor.putString("time", savedAlarmTime);
                                editor.putBoolean("isAlarmOn", true);
                                editor.commit();

                                Log.i("yog", Integer.toString(hour));
                                timePicker.setVisibility(View.INVISIBLE);
                                okayButton.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
                else{
                    Log.i("yog", "OFF");
                    timePicker.setVisibility(View.INVISIBLE);
                    okayButton.setVisibility(View.INVISIBLE);

                    alarmManager.cancel(broadcastePendingIntent);

                    editor.putBoolean("isAlarmOn", false);
                    editor.commit();
                }
            }
        });

        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Log.i("yog", "ON");
                    timePicker.setVisibility(View.VISIBLE);
                    okayButton.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        okayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hour2 = timePicker.getHour();
                                minute2 = timePicker.getMinute();
                                savedAlarmTime2 = Integer.toString(hour2) + ":" + Integer.toString(minute2);
                                textViewTime2.setText(savedAlarmTime2);


                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, hour2);
                                calendar.set(Calendar.MINUTE, minute2);

                                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                        AlarmManager.INTERVAL_FIFTEEN_MINUTES, broadcastePendingIntent);

                                editor.putString("time2", savedAlarmTime2);
                                editor.putBoolean("isAlarmOn2", true);
                                editor.commit();

                                timePicker.setVisibility(View.INVISIBLE);
                                okayButton.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
                else{
                    Log.i("yog", "OFF");
                    timePicker.setVisibility(View.INVISIBLE);
                    okayButton.setVisibility(View.INVISIBLE);

                    alarmManager.cancel(broadcastePendingIntent);

                    editor.putBoolean("isAlarmOn2", false);
                    editor.commit();
                }
            }
        });

        aSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    timePicker.setVisibility(View.VISIBLE);
                    okayButton.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        okayButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hour3 = timePicker.getHour();
                                minute3 = timePicker.getMinute();
                                savedAlarmTime3 = Integer.toString(hour3) + ":" + Integer.toString(minute3);
                                textViewTime3.setText(savedAlarmTime3);


                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, hour3);
                                calendar.set(Calendar.MINUTE, minute3);

                                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                        AlarmManager.INTERVAL_FIFTEEN_MINUTES, broadcastePendingIntent);

                                editor.putString("time3", savedAlarmTime3);
                                editor.putBoolean("isAlarmOn3", true);
                                editor.commit();

                                timePicker.setVisibility(View.INVISIBLE);
                                okayButton.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }
                else{
                    timePicker.setVisibility(View.INVISIBLE);
                    okayButton.setVisibility(View.INVISIBLE);

                    alarmManager.cancel(broadcastePendingIntent);

                    editor.putBoolean("isAlarmOn3", false);
                    editor.commit();
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}