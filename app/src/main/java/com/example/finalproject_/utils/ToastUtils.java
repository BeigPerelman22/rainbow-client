package com.example.finalproject_.utils;

import android.widget.Toast;

public  class ToastUtils {

    public static void showShortToast(String message) {
        Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String message) {
        Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }
}
