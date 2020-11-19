package com.example.nuyooni;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuyooni.Service.AlarmService;
import com.example.nuyooni.Service.BluetoothBroadcastReceiver;
import com.example.nuyooni.Service.BluetoothService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private static final String TAG = "Bluetooth Function: ";

    ImageView mIvToggle;
    TextView tv;
    static SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        mIvToggle = findViewById(R.id.bluetoothBTN);
        tv = findViewById(R.id.blueTV);

        sharedPrefs = getSharedPreferences("com.example.newprotestrunning", MODE_PRIVATE);
        if(sharedPrefs.getBoolean("imgToggle", false)){
            mIvToggle.setActivated(!mIvToggle.isActivated());
            Log.d("Debug : ", "(Check)Button State : "+sharedPrefs.getBoolean("imgToggle", false));
            tv.setText("ปิดการแจ้งเตือน");
        } else {
            tv.setText("เปิดการแจ้งเตือน");
        }

        ImageButton add = (ImageButton)findViewById(R.id.imageButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , Profile.class);
                startActivity(i);
            }

        });

    }

    private void createNotificationChannel(){
        String channelId = "101";
        String channelName = "Bluetooth Service";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName,importance);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); // will be shown in lock screen
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void blueFunction(){
        BluetoothBroadcastReceiver bbr = new BluetoothBroadcastReceiver();
        BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
        Intent intent = new Intent(this, BluetoothService.class);

        if(bAdapter == null){ //check device are support bluetooth
            Toast.makeText(getApplicationContext(),"เครื่องมือนี้ไม่รองรับระบบบลูทูธ",Toast.LENGTH_LONG).show();
        } else {
            if(!bAdapter.isEnabled()){ //check bluetooth are enable
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else{
                //find device to pair
                Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();

                if (pairedDevices.size() > 0) {
                    // There are paired devices. Get the name and address of each paired device.
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        Log.d(TAG,"Device Name - " + deviceName + "Device Address - " + deviceName);
                    }
                }

                if(!sharedPrefs.getBoolean("imgToggle", false)){
                    startService(intent);
                    mIvToggle.setActivated(!mIvToggle.isActivated());
                    tv.setText("ปิดการแจ้งเตือน");
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean("imgToggle", true);
                    editor.apply();
                    Log.d("Debug : ", "Button State 1 : "+sharedPrefs.getBoolean("imgToggle", false));
                }else {
                    stopService(intent);
                    mIvToggle.setActivated(!mIvToggle.isActivated());
                    tv.setText("เปิดการแจ้งเตือน");
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putBoolean("imgToggle", false);
                    editor.apply();
                    Log.d("Debug : ", "Button State 2 : "+sharedPrefs.getBoolean("imgToggle", false));
                }
            }
        }
    } //end bluetooth func

    public void logOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClickBluetooth(View view) {
        blueFunction();
    }

    public void start(View view) {
        startService(new Intent(this, AlarmService.class));
    }

    public void stop(View view) {
        stopService(new Intent(this, AlarmService.class));
    }
}