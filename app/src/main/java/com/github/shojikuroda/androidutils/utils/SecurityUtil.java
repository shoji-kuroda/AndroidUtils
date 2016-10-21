package com.github.shojikuroda.androidutils.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shoji.kuroda on 2016/10/21.
 */

public class SecurityUtil {

    private static WhiteList whiteList = null;

    public static boolean isAccessArrowed(Context context, String packageName) {
        if (whiteList == null) {
            return false;
        }
        String correctHash = whiteList.get(packageName);
        return PackageCertificate.test(context, packageName, correctHash);
    }

    public static WhiteList getWhiteList() {
        if (whiteList == null) {
            whiteList = new WhiteList();
        }
        return whiteList;
    }

    public static class WhiteList {

        private Map<String, String> wlist = new HashMap<>();

        public void add(String packageName, String sha256) {
            if (TextUtils.isEmpty(packageName) || TextUtils.isEmpty(sha256)) {
                return;
            }
            sha256 = sha256.replaceAll(" ", "").replaceAll(":", "");
            if (sha256.length() != 64) {
                return;
            }
            sha256 = sha256.toUpperCase();
            if (sha256.replaceAll("[0-9A-F]+", "").length() != 0) {
                return;
            }
            this.wlist.put(packageName, sha256);
        }

        public String get(String packageName) {
            return this.wlist.get(packageName);
        }
    }

    private static class PackageCertificate {
        public static boolean test(Context context, String packageName, String correctHash) {
            if (TextUtils.isEmpty(correctHash)) {
                return false;
            }
            correctHash = correctHash.replaceAll(" ", "").replaceAll(":", "");
            return correctHash.equals(hash(context, packageName));
        }

        public static String hash(Context context, String packageName) {
            if (TextUtils.isEmpty(packageName)) {
                return null;
            }
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pkginfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                if (pkginfo.signatures.length != 1) {
                    return null;
                }
                Signature sig = pkginfo.signatures[0];
                byte[] cert = sig.toByteArray();
                byte[] sha256 = computeSha256(cert);
                return byte2hex(sha256);
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }

        private static byte[] computeSha256(byte[] data) {
            try {
                return MessageDigest.getInstance("SHA-256").digest(data);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }

        private static String byte2hex(byte[] data) {
            if (data == null) return null;
            final StringBuilder hexadecimal = new StringBuilder();
            for (final byte b : data) {
                hexadecimal.append(String.format("%02X", b));
            }
            return hexadecimal.toString();
        }
    }
}
