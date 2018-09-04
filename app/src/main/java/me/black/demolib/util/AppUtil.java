package me.black.demolib.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import me.black.library.util.LogUtil;

/**
 * Created by xunao on 16/8/23.
 * App相关工具类
 */
public class AppUtil {
    private static String TAG = "ApkUtil";

    private AppUtil() {
    }

    /**
     * 获取App版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    public static String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager.getRunningTasks(1) == null || activityManager.getRunningTasks(1).size() == 0) {
            return "Default";
        }
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        runningActivity = runningActivity.substring(runningActivity.lastIndexOf(".") + 1, runningActivity.length());
        LogUtil.d(TAG, "=>getRunningActivityName " + runningActivity);
        return runningActivity;
    }

    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = new PackageInfo(); // 如果为null， 可能返回的就是Null，要闪退
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 获取ip地址
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }
    public static String getSha1(Context paramContext) {
        String paramString = paramContext.getPackageName();
        List localList = paramContext.getPackageManager().getInstalledPackages(64);
        LogUtil.d(TAG, "大小：" + localList.size());
        Iterator localIterator = localList.iterator();
        PackageInfo localPackageInfo;
        do {
            if (!localIterator.hasNext())
                return null;
            localPackageInfo = (PackageInfo) localIterator.next();
        } while (!localPackageInfo.packageName.equals(paramString));
        Object localObject = "";
        label155:
        try {
            android.content.pm.Signature[] arrayOfSignature = localPackageInfo.signatures;
            X509Certificate localX509Certificate = (X509Certificate) CertificateFactory
                    .getInstance("X.509").generateCertificate(
                            new ByteArrayInputStream(arrayOfSignature[0]
                                    .toByteArray()));
            LogUtil.d(TAG, "签名为" + arrayOfSignature[0].toCharsString());
            String str = getFingerprintAsString(localX509Certificate);
            localObject = str;
            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0; ; i++) {
                if (i >= ((String) localObject).length()) {
                    return localStringBuffer.toString();
                }
                localStringBuffer.append(((String) localObject).charAt(i));
                if ((i > 0) && (i % 2 == 1)
                        && (i < -1 + ((String) localObject).length()))
                    localStringBuffer.append(":");
            }
        } catch (CertificateException localCertificateException) {
            break label155;
        }
        return null;
    }

    static String getFingerprintAsString(X509Certificate paramX509Certificate) {
        try {
            String str = Hex
                    .encode(generateSHA1Fingerprint(paramX509Certificate
                            .getEncoded()));
            return str;
        } catch (CertificateEncodingException localCertificateEncodingException) {
        }
        return null;
    }

    static byte[] generateSHA1Fingerprint(byte[] paramArrayOfByte) {
        try {
            byte[] arrayOfByte = MessageDigest.getInstance("SHA1").digest(
                    paramArrayOfByte);
            return arrayOfByte;
        } catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
        }
        return null;
    }
    /**
     * 判断android SDK 版本是否大于等于5.0
     * @return
     */
    public static boolean isAndroid5() {

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
    static class Hex {
        public static String encode(byte[] paramArrayOfByte) {
            LogUtil.d("Hex", "SHA1的字节长度：" + paramArrayOfByte.length);
            char[] arrayOfChar = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65,
                    66, 67, 68, 69, 70};
            StringBuilder localStringBuilder = new StringBuilder(
                    2 * paramArrayOfByte.length);
            for (int i = 0; ; i++) {
                if (i >= paramArrayOfByte.length)
                    return localStringBuilder.toString();
                localStringBuilder
                        .append(arrayOfChar[((0xF0 & paramArrayOfByte[i]) >> 4)]);
                localStringBuilder
                        .append(arrayOfChar[(0xF & paramArrayOfByte[i])]);
            }
        }
    }
}
