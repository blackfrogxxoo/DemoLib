package me.black.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

public class CallUtil {
    @SuppressLint("MissingPermission")
    public static void call(Context context, String number) {
        if(!RegularUtil.isNumber(number)) {

            return;
        }
        AndPermission.with(context)
                .runtime()
                .permission(Permission.CALL_PHONE)
                .onGranted(o -> {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    context.startActivity(intent);
                })
                .onDenied(o -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                    context.startActivity(intent);
                })
                .start();
    }
}
