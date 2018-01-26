package com.aidchain.utils;

import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.util.Arrays;


public class Utils {
    /**
     * Deprecated, use Apache Commons DigestUtils directly instead.
     * @param input string to hash
     * @return the hash
     */
    @Deprecated
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //apply hash to transaction
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            //convert hash to hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff + aHash);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(Arrays.toString(e.getStackTrace()));
        }

    }

    public static String getJson(Object obj) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setPrettyPrinting().create().toJson(obj);
    }

}
