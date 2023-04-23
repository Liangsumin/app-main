package com.xuexiang.xuidemo.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static String convertToUtf8(String str) {
        return new String(str.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8);
    }

    public static String convertToUtf16(String str) {
        return new String(str.getBytes(StandardCharsets.UTF_16),StandardCharsets.UTF_16);
    }

    public static String convertToGBK(String str) {
        try {
            return new String(str.getBytes("GB2312"),"GB2312");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
