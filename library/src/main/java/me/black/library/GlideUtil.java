package me.black.library;

import android.widget.ImageView;

public class GlideUtil {
    public static void load(ImageView imageView, int resId, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(resId)
                .error(resId)
                .into(imageView);
    }
}
