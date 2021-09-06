package com.easy.ble_soup;

import android.util.Log;

abstract public class BleDeviceCallback {

    private static final String TAG = "BleDeviceCallback";

    /**
     * @param
     * @throws
     */
    public void onCharacteristicWrite() {
        Log.d(TAG, "onCharacteristicWrite: ");
    }

    /**
     * @param
     * @throws
     */
    public void onCharacteristicRead() {
        Log.d(TAG, "onCharacteristicRead: ");
    }

    /**
     * @param
     * @throws
     */
    public void onCharacteristicsChanged() {
        Log.d(TAG, "onCharacteristicsChanged: ");
    }

    /**
     * @param
     * @throws
     */
    public void onNotificationStateChange() {
        Log.d(TAG, "onNotificationStateChange: ");
    }

    /**
     * @param
     * @throws
     */
    public void onFailedServiceDiscovery() {
        Log.d(TAG, "onFailedServiceDiscovery: ");
    }

    /**
     * @param
     * @throws
     */
    public void onServicesDiscovered() {
        Log.d(TAG, "onServicesDiscovered: ");
    }

    /**
     * @param
     * @throws
     */
    public void onMTUChanged() {
        Log.d(TAG, "onMTUChanged: ");
    }

    /**
     * @param
     * @throws
     */
    public void onWriteFailed() {
        Log.d(TAG, "onWriteFailed: ");
    }

    /**
     * @param
     * @throws
     */
    public void onDescriptorWrite() {
        Log.d(TAG, "onDescriptorWrite: ");
    }

    /**
     * @param
     * @throws
     */
    public void onDescriptorRead() {
        Log.d(TAG, "onDescriptorRead: ");
    }
    
    
    public void onDeviceDisconnected(){
        Log.d(TAG, "onConnectionStateChange: Connection State Changed");
    }

}
