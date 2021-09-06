package com.easy.ble_soup;

public class ScanBundle {

    private int mTxPower;
    private int mRSSI;
    private double distance;

    public ScanBundle(int mTxPower, int mRSSI) {
        this.mTxPower = mTxPower;
        this.mRSSI = mRSSI;
        this.distance = approximateDistance(mRSSI, mTxPower)*100;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getmTxPower() {
        return mTxPower;
    }

    public void setmTxPower(int mTxPower) {
        this.mTxPower = mTxPower;
    }

    public int getmRSSI() {
        return mRSSI;
    }

    public void setmRSSI(int mRSSI) {
        this.mRSSI = mRSSI;
    }


    private double approximateDistance(int rssi, int Power) {
        if (rssi == 0 || Power == 0) return -1;
        double ratio2 = Power - rssi;
        double ratio2_linear = Math.pow(10, ratio2 / 10);
        double y = 0;
        double r = Math.sqrt(ratio2_linear);

        double ratio = rssi * 1.0 / Power;
        if (ratio < 1.0) {
            y = Math.pow(ratio, 10);
        } else {
            y = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
        }
        return y * 10;


    }
}

