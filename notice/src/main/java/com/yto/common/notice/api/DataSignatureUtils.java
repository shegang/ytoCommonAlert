/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yto.common.notice.api;

//import cn.yto.announcement.manager.request.QueryList;
//import cn.yto.announcement.manager.request.SectionQuery;
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.codec.binary.Base64;
//
//import javax.sound.midi.SoundbankResource;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 秘钥算法工具类
 * @ClassName AESUtil
 * @Author xiekun
 * @date 2020.09.15 15:33
 */
public  class DataSignatureUtils {

    /**
     * utf-8
     */
    public static final String CHARSET_UTF8 = "utf-8";
    /**
     * 私有构造器
     */
    private DataSignatureUtils() {
    }

    /**
     * 数据签名
     *
     * @param content   需要签名的内容
     * @param secretKey 秘钥
     * @return String 数字签名
     */
    public static String getDataSignature(final String content, final String secretKey) {
        String dataDigest;
        String signContent = content + secretKey;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signContent.getBytes(CHARSET_UTF8));

//            dataDigest = new String(Base64.encodeBase64(md.digest()), CHARSET_UTF8);
            dataDigest = Base64.encodeToString(md.digest(),Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataDigest;
    }
    public static String getDataSignature(final String content, final String secretKey, final String currentDate) {
        String dataDigest;
        String signContent = content + secretKey + currentDate;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signContent.getBytes(CHARSET_UTF8));
//            dataDigest = new String(Base64.encodeBase64(md.digest()), CHARSET_UTF8);
            dataDigest = Base64.encodeToString(md.digest(),Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataDigest;
    }

//    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
////        System.out.println(getDataSignature("BZB000002158","TF5a7@nDH5"));
//        System.out.println(MD5LogerCase("zxf123"));
//        String s = "{\"offset\":3,\"page\":0,\"sectionCode\":\"sec89V4nWKm\",\"userCode\":\"01653893\"}";
//        String dataSignature = getDataSignature(s, "2690d6b105", "20210329134900");
//        System.out.println(dataSignature);
//    }

    /**
     * md5加密
     */
    public static String MD5LogerCase(String content) {
        return getMD5(content).toLowerCase();
    }

    /**
     * md5加密转大写
     */
    public static String getMD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转为16进制
     *
     * @param bytes
     * @return
     */
    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}

