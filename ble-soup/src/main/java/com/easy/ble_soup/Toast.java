package com.easy.ble_soup;

import android.content.Context;

public class Toast {

    private static Toast mToast;

    private Toast() {

    }

    public static Toast getInstance() {

        if (mToast == null) {
            mToast = new Toast();
        }
        return mToast;
    }

    public void show(Context mCtx, String message) {
        android.widget.Toast.makeText(mCtx,message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
