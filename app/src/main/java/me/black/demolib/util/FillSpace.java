package me.black.demolib.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 请特别注意，对于任何 Transformation 子类，包括 BitmapTransformation，你都有三个方法你 必须 实现它们，
 * 以使得磁盘和内存缓存正确地工作：
 * equals()
 * hashCode()
 * updateDiskCacheKey
 * 如果你的 Transformation 没有参数，通常使用一个包含完整包限定名的 static final String 来作为一个 ID，
 * 它可以构成 hashCode() 的基础，并可用于更新 updateDiskCacheKey() 传入的 MessageDigest。如果你的
 * Transformation 需要参数而且它会影响到 Bitmap 被变换的方式，它们也必须被包含到这三个方法中。
 */
public class FillSpace extends BitmapTransformation {
    private static final String ID = "com.bumptech.glide.transformations.FillSpace";
    private static byte[] ID_BYTES;
    static {
        try {
            ID_BYTES = ID.getBytes(STRING_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform.getWidth() == outWidth && toTransform.getHeight() == outHeight) {
            return toTransform;
        }

        return Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, /*filter=*/ true);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FillSpace;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
