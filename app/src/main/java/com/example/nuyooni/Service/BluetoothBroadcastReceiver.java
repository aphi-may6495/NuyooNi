package com.example.nuyooni.Service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "Bluetooth Function: ";
    BluetoothDevice device;
    @Override
    public void onReceive(Context context, Intent intent) {
        String log = "Action: " + intent.getAction();
        //+ "URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n";
        Log.d(TAG, log);
        //Toast.makeText(context, log, Toast.LENGTH_LONG).show();

        String action = intent.getAction();
        device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            //Device found
            Log.d(TAG,"Device Found");
        }
        else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            //Device is now connected
            Log.d(TAG,"Device are now Connected to " + device.getName() + " || " + device.getAddress());
        }
        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            //Done searching
            Log.d(TAG,"Searching Success");
        }
        else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
            //Device is about to disconnect
            Log.d(TAG,"Device is about to disconnect");
        }
        else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            //Device has disconnected
            Log.d(TAG,"Device has disconnected");
            context.stopService(new Intent(context, BluetoothService.class));
            context.startService(new Intent(context, AlarmService.class));
        }
    }
}