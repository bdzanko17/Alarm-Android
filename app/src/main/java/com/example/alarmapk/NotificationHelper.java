package com.example.alarmapk;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

class NotificationHelper extends ContextWrapper {
        public static final String channelID = "channelID";
        public static final String channelName = "Channel Name";

        private NotificationManager mManager;

    public NotificationHelper(Context base) {
            super(base);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel();
            }
        }

        @TargetApi(Build.VERSION_CODES.O)
        private void createChannel() {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

            getManager().createNotificationChannel(channel);
        }

        public NotificationManager getManager() {
            if (mManager == null) {
                mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }

            return mManager;
        }

    final Intent snoozeIntent = new Intent(this, MyBroadcastReceiverOff.class);
    final PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);


        public NotificationCompat.Builder getChannelNotification() {
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle("Alarm!")
                    .setContentText("Your AlarmManager is working.")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .addAction(R.drawable.ic_launcher_background, "TURN OFF",
                            snoozePendingIntent);
        }

}
