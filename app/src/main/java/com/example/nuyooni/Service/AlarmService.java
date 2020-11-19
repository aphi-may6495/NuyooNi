package com.example.nuyooni.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.example.nuyooni.AlarmActivity;
import com.example.nuyooni.R;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    public AlarmService() {}

    @Override
    public IBinder onBind(Intent intent) { throw new UnsupportedOperationException("Not yet implemented"); }

    public void onCreate(){
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.setScreenOnWhilePlaying(true);
        mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);

        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        // For example to set the volume of played media to maximum.
        audioManager.setStreamVolume (AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String CHANNEL_ID = "101";

        Intent notificationIntent = new Intent(this, AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("คำเตือน")
                .setContentText("สัญญาณจากเด็กได้ขาดการเชื่อมต่อ")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("สัญญาณจากเด็กได้ขาดการเชื่อมต่อ"))
                .setSmallIcon(R.drawable.warning)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        mediaPlayer.start();

        long[] pattern = {1000 ,1000 ,1000};

        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createWaveform(pattern, 1));
        }else{
            vibrator.vibrate(pattern,1);
        }

        startForeground(Integer.parseInt(CHANNEL_ID),notification);

        //return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }
}