package com.example.soundflows.ForegroundService;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.example.soundflows.Activity.PlaySongActivity;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class NotificationService extends Service {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Song mSong;
    PlaySongActivity playSongActivity = new PlaySongActivity();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("Notification", "Notification onCreate");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Song song = (Song) bundle.get(UserConstant.OBJECT_SONG);

            if (song != null){
                this.mSong = song;
                startMusic(song);

                sendNotification(song);
            }
        }

        int actionMusic = intent.getIntExtra("action_music_service", 0);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void startMusic(Song song) {
        try {
            mediaPlayer.setDataSource(song.getLinkSong());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }

    private void handleActionMusic(int action) {
        switch (action) {
            case UserConstant.BTN_PAUSE:
                pauseMusic();
                break;

            case UserConstant.BTN_RESUME:
                resumeMusic();
                break;

            case UserConstant.BTN_CLEAR:
                stopSelf();
                break;
        }
    }

    private void resumeMusic() {
        try {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()){
                mediaPlayer.start();
                sendNotification(mSong);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        try{
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                sendNotification(mSong);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void sendNotification(Song song) {
        Intent intent = new Intent(this, PlaySongActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                                                                0,
                                                                intent,
                                                                PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_layout_notifiction);

        Notification notification = new NotificationCompat.Builder(this, UserConstant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play)
                .setContentIntent(pendingIntent)
                .setCustomBigContentView(remoteViews)
                .setSound(null)
                .build();

        remoteViews.setTextViewText(R.id.tv_title_song, song.getNameSong());
        remoteViews.setTextViewText(R.id.tv_title_singer, song.getSinger());
        remoteViews.setImageViewResource(R.id.img_song, R.drawable.logo_icon);
        remoteViews.setImageViewResource(R.id.img_clear, R.drawable.ic_clear);
        remoteViews.setImageViewResource(R.id.img_pause_or_play, R.drawable.ic_play_playlist);

        if (mediaPlayer.isPlaying()) {

            remoteViews.setOnClickPendingIntent(R.id.img_pause_or_play,
                    getPendingIntent(getApplicationContext(), UserConstant.BTN_PAUSE));

            remoteViews.setImageViewResource(R.id.img_pause_or_play, R.drawable.ic_pause_notify);
        } else {
            remoteViews.setOnClickPendingIntent(R.id.img_pause_or_play,
                    getPendingIntent(getApplicationContext(), UserConstant.BTN_RESUME));

            remoteViews.setImageViewResource(R.id.img_pause_or_play, R.drawable.ic_play_playlist);
        }

        remoteViews.setOnClickPendingIntent(R.id.img_clear,
                getPendingIntent(this, UserConstant.BTN_CLEAR));

        startForeground(UserConstant.ID_NOTIFICATION, notification);

    }

    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("action_music", action);

        return PendingIntent.getBroadcast(context.getApplicationContext(),
                action,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Notification", "Notification onDestroy");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
