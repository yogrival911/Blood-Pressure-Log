package com.example.bloodpressurelog;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcasteReceiver extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context, "Broadcaste Received", Toast.LENGTH_SHORT).show();
        Log.i("yog", "Alarm Broadcaste Triggered");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(400);
        MediaPlayer mp = MediaPlayer.create(context, R.raw.alarm);
        mp.start();

    }
}
