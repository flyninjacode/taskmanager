package com.taskmanager.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class PasswordHashUtil {

    @NotNull
    public static String getPasswordHash(@NotNull final String password) {
        @Nullable MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        byte[] digest = md.digest();
        @NotNull String passHash = Arrays.toString(digest);
        return passHash;
    }

    @Nullable
    public static String md5(@Nullable final String md5) {
        if (md5 == null) return null;
        try {
            @NotNull final MessageDigest md =
                    MessageDigest.getInstance("MD5");
            @NotNull final byte[] array = md.digest(md5.getBytes());
            @NotNull final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
