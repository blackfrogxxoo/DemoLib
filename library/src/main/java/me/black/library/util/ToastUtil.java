package me.black.library.util;

import android.widget.Toast;

import me.black.library.LibApp;

public class ToastUtil {
    public static void showShort(String text) {
        show(text, Toast.LENGTH_SHORT);
    }
    public static void showLong(String text) {
        show(text, Toast.LENGTH_LONG);
    }
    public static void showShort(int textResId) {
        show(textResId, Toast.LENGTH_SHORT);
    }
    public static void showLong(int textResId) {
        show(textResId, Toast.LENGTH_LONG);
    }
    private static void show(String text, int length) {
        Toast.makeText(LibApp.getContext(), text, length).show();
    }
    private static void show(int textResId, int length) {
        Toast.makeText(LibApp.getContext(), textResId, length).show();
    }
}
