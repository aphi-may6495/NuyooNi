package com.example.nuyooni.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.nuyooni.MainActivity;
import com.example.nuyooni.R;

import static androidx.core.app.NotificationCompat.CATEGORY_SERVICE;

public class BluetoothService extends Service {
    public BluetoothService() {
    }

    private static final int ID_SERVICE = 101;
    BluetoothBroadcastReceiver bbr = new BluetoothBroadcastReceiver();
    IntentFilter filter = new IntentFilter();

    @Nullable
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(ID_SERVICE));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("หนู่อยู่นี่")
                .setContentText("ระบบการแจ้งเตือนเป้องกันเด็กหายกำลังทำงานอยู่")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("ระบบการแจ้งเตือนเป้องกันเด็กหายกำลังทำงานอยู่"))
                .setCategory(CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        startForeground(ID_SERVICE, notification);
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        this.registerReceiver(bbr,filter);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(bbr);
        SharedPreferences.Editor editor = getSharedPreferences("com.example.newprotestrunning", MODE_PRIVATE).edit();
        editor.putBoolean("imgToggle", false);
        editor.apply();
        super.onDestroy();
    }

    public void setImageButton(ImageButton imgBTN){
        imgBTN.setActivated(!imgBTN.isActivated());
    }
}