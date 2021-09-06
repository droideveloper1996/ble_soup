package com.easy.ble_soup;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.ParcelUuid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class BleScanner {

    private static final String TAG = "BleScanner";


    private BluetoothAdapter mAdapter;
    private BleDevice bleDevice;
    private Context mCtx;
    private boolean isScanning = false;
    private Handler handler;
    BluetoothLeScanner mLeScanner;
    public static final String SCAN_WITH_FILTER = "scan_with_filter";
    public static final String SCAN_WITHOUT_FILTER = "scan_without_filter";
    public static final String SCAN_BY_DEVICE_NAME = "scan_by_device_name";
    BleScanCallback mScanCallback;

    public BleScanner(final Context mCtx, final Handler handler, BleScanCallback mScanCallback) {
        this.mCtx = mCtx;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.handler = handler;
        this.mScanCallback = mScanCallback;
    }

    private boolean isLeSupported() {

        if (mCtx.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissions() {
        String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(mCtx, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mCtx, permissions, 2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private BluetoothLeScanner getLeScanner() throws BleException {
        if (isLeSupported()) {
            return mAdapter.getBluetoothLeScanner();
        }
        throw new BleException("Le Scanner is not supported");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startScan() throws BleException {

        requestPermissions();

        if (!mAdapter.isEnabled()) {
            throw new BleException("Adapter is turned off");
        }

        mLeScanner = getLeScanner();
        isScanning = true;
        mLeScanner.startScan(callback);
    }

    /***
     *
     * @param settings
     * @throws BleException
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startScan(ScanSettings settings) throws BleException {
        requestPermissions();
        if (!mAdapter.isEnabled()) {
            throw new BleException("Adapter is turned off");
        }
        mLeScanner = getLeScanner();
        isScanning = true;
        mLeScanner.startScan(null, settings, callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    /**
     * @param macAddress
     * @throws BleException
     * ArrayList of macaddress
     */
    public void startMacScan(@NonNull ArrayList<String> macAddress) throws BleException {
        requestPermissions();
        if (!mAdapter.isEnabled()) {
            throw new BleException("Adapter is turned off");
        }
        if (macAddress.size() == 0) {
            throw new BleException("Requires Non null macAdd array");
        }
        ArrayList<ScanFilter> filters = new ArrayList<>();
        for (String macAdd : macAddress) {
            filters.add(new ScanFilter.Builder().setDeviceName(macAdd).build());
        }
        ScanSettings scanSettings  = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            scanSettings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                    .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                    .setReportDelay(0L)
                    .build();
        }

        mLeScanner = getLeScanner();
        isScanning = true;
        mLeScanner.startScan(filters, scanSettings, callback);
    }

    /***
     *
     * @param service
     * @param chars
     * @throws BleException
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startUUIDScan(ArrayList<String> service, ArrayList<String> chars) throws BleException {

        ArrayList<ScanFilter> scanFilters = new ArrayList<>();

        for (String uuid : service) {
            ParcelUuid parcelUUID = ParcelUuid.fromString(uuid);
            ScanFilter scanFilter = new ScanFilter.Builder().setServiceUuid(parcelUUID).build();
            scanFilters.add(scanFilter);
        }
        for (String uuid : chars) {
            ParcelUuid parcelUUID = ParcelUuid.fromString(uuid);
            ScanFilter scanFilter = new ScanFilter.Builder().setServiceUuid(parcelUUID).build();
            scanFilters.add(scanFilter);
        }
        requestPermissions();
        if (!mAdapter.isEnabled()) {
            throw new BleException("Adapter is turned off");
        }
        ScanSettings scanSettings = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            scanSettings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                    .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                    .setReportDelay(0L)
                    .build();
        }

        mLeScanner = getLeScanner();
        isScanning = true;
        mLeScanner.startScan(scanFilters, scanSettings, callback);
    }

    public boolean isScanning() {
        return isScanning;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stopScan(long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLeScanner.stopScan(callback);
            }
        }, delay);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScanCallback callback = new ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            ScanBundle scanBundle = new ScanBundle(result.getTxPower(), result.getRssi());
            BleDevice bleDevice = new BleDevice(scanBundle, result.getDevice());
            mScanCallback.onDeviceDiscovered(bleDevice);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

}
