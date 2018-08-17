package me.black.library.glide;

import android.widget.ImageView;

import me.black.library.LibApp;

//import me.black.library.GlideApp;

public class GlideUtil {
    public static void load(ImageView imageView, int resId, String url) {
        GlideApp.with(LibApp.getContext())
                .load(url)
                .centerCrop()
                .placeholder(resId)
                .error(resId)
                .into(imageView);
    }
}
