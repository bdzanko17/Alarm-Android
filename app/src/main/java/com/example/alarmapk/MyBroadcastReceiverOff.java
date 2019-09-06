package com.example.alarmapk;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;



public class MyBroadcastReceiverOff extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MyBroadcastReceiver.r.stop();









    }
}
