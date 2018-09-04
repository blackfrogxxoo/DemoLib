package me.black.library.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import me.black.library.LibApp;

public final class UIUtil {

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return LibApp.getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public static Bitmap getBitmap(@DrawableRes int resId) {
        return ((BitmapDrawable) getResources().getDrawable(resId)).getBitmap();
    }

    public static String getString(@StringRes int resId, Object... formatArgus) {
        return getResources().getString(resId, formatArgus);
    }

}