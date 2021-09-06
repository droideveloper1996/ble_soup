package com.easy.ble;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.easy.ble_soup.BleDevice;
import com.easy.ble_soup.BleDeviceCallback;
import com.easy.ble_soup.BleException;
import com.easy.ble_soup.BleScanCallback;
import com.easy.ble_soup.BleScanner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    BleScanCallback scanCallback;
    Handler handler;
    private static final String TAG = "MainActivity";
    public static final String mac[] = {
            "C4:4F:33:12:D8:65"
    };
    BleScanner scanner;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Hello from ble soup", Toast.LENGTH_SHORT).show();
        handler = new Handler(Looper.getMainLooper());
        scanner = new BleScanner(this, handler, callback);
        try {
            ArrayList<String> deviceMac = new ArrayList<>();
            deviceMac.addAll(Arrays.asList(mac));
            scanner.startMacScan(deviceMac);
//            scanner.startScan();
        } catch (BleException bleException) {
            bleException.printStackTrace();
        }
    }

    BleScanCallback callback = new BleScanCallback() {
        @Override
        public void onDeviceDiscovered(BleDevice bleDevice) {
            super.onDeviceDiscovered(bleDevice);
            Log.d(TAG, "onDiscovered Address: " + bleDevice.getBluetoothDevice().getAddress());
            Log.d(TAG, "onDiscovered Power: " + bleDevice.getScanBundle().getmTxPower());
            Log.d(TAG, "onDiscovered RSSI: " + bleDevice.getScanBundle().getmRSSI());
            Log.d(TAG, "onDiscovered Distance: " + bleDevice.getScanBundle().getDistance() + "meters");
            boolean res = bleDevice.connect(getApplicationContext(), bleDeviceCallback, handler);
            if (res) {
                scanner.stopScan(0);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bleDevice.disconnect();
                    }
                }, 4000);
            }


        }

        @Override
        public void onScanFinished() {
            super.onScanFinished();
        }

        @Override
        public void onScanFailed() {
            super.onScanFailed();
        }

        @Override
        public void onDeviceConnected() {
            super.onDeviceConnected();
        }

        @Override
        public void onBondLost() {
            super.onBondLost();
        }

        @Override
        public void onBondingFailed() {
            super.onBondingFailed();
        }

        @Override
        public void onBondSucceed() {
            super.onBondSucceed();
        }

        @Override
        public void onAdapterStateChange() {
            super.onAdapterStateChange();
        }
    };


    private BleDeviceCallback bleDeviceCallback = new BleDeviceCallback() {
        @Override
        public void onCharacteristicWrite() {
            super.onCharacteristicWrite();
        }

        @Override
        public void onCharacteristicRead() {
            super.onCharacteristicRead();
        }

        @Override
        public void onCharacteristicsChanged() {
            super.onCharacteristicsChanged();
        }

        @Override
        public void onNotificationStateChange() {
            super.onNotificationStateChange();
        }

        @Override
        public void onFailedServiceDiscovery() {
            super.onFailedServiceDiscovery();
        }

        @Override
        public void onServicesDiscovered() {
            super.onServicesDiscovered();
        }

        @Override
        public void onMTUChanged() {
            super.onMTUChanged();
        }

        @Override
        public void onWriteFailed() {
            super.onWriteFailed();
        }

        @Override
        public void onDescriptorRead() {
            super.onDescriptorRead();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onDeviceDisconnected() {
            super.onDeviceDisconnected();
            Log.d(TAG, "onDeviceDisconnected: ");
            ArrayList<String> deviceMac = new ArrayList<>();
            deviceMac.addAll(Arrays.asList(mac));
            try {
                scanner.startMacScan(deviceMac);
            } catch (BleException bleException) {
                bleException.printStackTrace();
            }
        }
    };
}