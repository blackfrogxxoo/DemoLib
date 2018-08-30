package me.black.library.util;

import me.black.library.LibApp;

public class DeviceUtil {
    public static int dp2px(float dp) {
        return (int) (LibApp.getContext().getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
