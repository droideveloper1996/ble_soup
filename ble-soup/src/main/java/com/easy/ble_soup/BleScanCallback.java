package com.easy.ble_soup;

abstract public class BleScanCallback {


    public void onDeviceDiscovered(BleDevice bleDevice) {
    }

    public void onScanFinished() {
    }

    public void onScanFailed() {
    }

    public void onDeviceConnected() {
    }

    public void onBondLost() {
    }

    public void onBondingFailed() {
    }

    public void onBondSucceed() {
    }

    public void onAdapterStateChange() {
    }

    public void onDeviceDisconnected(){

    }


}
