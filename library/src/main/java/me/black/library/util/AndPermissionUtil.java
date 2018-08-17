package me.black.library.util;

import android.content.Context;
import android.net.Uri;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;

public class AndPermissionUtil {
    public static void request(Context context, Action<List<String>> onGranted, Action<List<String>> onDenied) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(onGranted)
                .onDenied(onDenied)
                .start();
    }

    public static Uri store(Context context, String filePath) {
        File file = new File(filePath);
        Uri compatUri = AndPermission.getFileUri(context, file);
        return compatUri;
    }

    public static void installApk(Context context, String filePath, Action<File> onGranted, Action<File> onDenied) {
        File apkFile = new File(filePath);

        AndPermission.with(context)
                .install()
                .file(apkFile)
                .onGranted(onGranted)
                .onDenied(onDenied)
                .start();
    }

    public static void overlay(Context context, Action<Void> onGranted, Action<Void> onDenied) {
        AndPermission.with(context)
                .overlay()
                .onGranted(onGranted)
                .onDenied(onDenied)
                .start();
    }
}
