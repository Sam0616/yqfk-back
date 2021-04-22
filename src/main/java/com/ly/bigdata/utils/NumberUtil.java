package com.ly.bigdata.utils;

public class NumberUtil {

    public static String getForNumber() {
        int a = (int) Math.floor(Math.random() * 10);
        int b = (int) Math.floor(Math.random() * 10);
        int c = (int) Math.floor(Math.random() * 10);
        int d = (int) Math.floor(Math.random() * 10);
        String s = a+""+b+c+d;
        return s;
    }
}
