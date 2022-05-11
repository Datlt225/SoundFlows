package com.example.soundflows.ForegroundService;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.soundflows.constant.UserConstant;

public class Notification extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(UserConstant.CHANNEL_ID,
                                            UserConstant.NOTIFICATION_NAME,
                                            NotificationManager.IMPORTANCE_DEFAULT);

            channel.setSound(null, null);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
