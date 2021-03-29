package com.stormnet.net.utils.numbers;

public class NumbersUtils {

    public static Long parseLong(String longStr) {
        try{
            return Long.parseLong(longStr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toString(Long value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
