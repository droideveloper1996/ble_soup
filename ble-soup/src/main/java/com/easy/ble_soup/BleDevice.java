package com.easy.ble_soup;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;


public class BleDevice {

    private ScanBundle scanBundle;
    private BluetoothDevice mBluetoothDevice;
    private BleDeviceCallback mDeviceCallback;
    private BluetoothGatt mBluetoothGatt;
    private Handler mHandler;

    public BleDevice(ScanBundle scanBundle, BluetoothDevice mBluetoothDevice) {
        this.scanBundle = scanBundle;
        this.mBluetoothDevice = mBluetoothDevice;
    }

    public ScanBundle getScanBundle() {
        return scanBundle;
    }

    public void setScanBundle(ScanBundle scanBundle) {
        this.scanBundle = scanBundle;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice mBluetoothDevice) {
        this.mBluetoothDevice = mBluetoothDevice;
    }


    public boolean connect(Context mCtx, BleDeviceCallback mDeviceCallback, final Handler handler) {
        this.mDeviceCallback = mDeviceCallback;
        mBluetoothGatt = mBluetoothDevice.connectGatt(mCtx, false, gattCallback);
        this.mHandler = handler;
        return mBluetoothGatt != null;
    }

    public void autoConnect(Context mCtx, BleDeviceCallback mDeviceCallback) {
        this.mDeviceCallback = mDeviceCallback;
        mBluetoothDevice.connectGatt(mCtx, true, gattCallback);
    }

    public void autoConnectBatch() {

    }

    public void discoverServices() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBluetoothGatt.discoverServices();
            }
        });
    }

    public void write(String data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                byte[] inp = data.getBytes();
//                mBluetoothGatt.writeCharacteristic(inp);
            }
        });
    }


    public void disconnect() {
        mBluetoothGatt.disconnect();
    }


    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                gatt.close();
                mDeviceCallback.onDeviceDisconnected();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            mDeviceCallback.onServicesDiscovered();

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            mDeviceCallback.onCharacteristicRead();
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            mDeviceCallback.onCharacteristicWrite();

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            mDeviceCallback.onCharacteristicsChanged();

        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            mDeviceCallback.onDescriptorRead();

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            mDeviceCallback.onDescriptorWrite();
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            mDeviceCallback.onMTUChanged();

        }
    };
}



