package com.demo.example.utils;

import java.io.IOException;

/**
 * UrlUtils
 *
 * @author xuxiang
 * @since 17/10/8
 */
public class UrlUtils {

    public static String encryption(Long pageId) {
        String idstr = String.valueOf(pageId << 2);
        String pageCode = UrlUtils.encode(idstr.getBytes());

        return pageCode;
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    public static byte[] decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bt;
    }

    /**
     * 加码
     *
     * @param bt
     * @return string
     */
    public static String encode(byte[] bt) {
        String str = null;
        try {
            sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            str = encoder.encodeBuffer(bt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }
}
