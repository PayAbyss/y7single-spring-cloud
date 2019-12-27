/*
 * Copyright 2019 y7
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.y7single.commons.utils;

import com.y7single.commons.enums.DefaultResultCode;
import com.y7single.commons.exceptions.DataEncryptionException;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * @author: y7
 * @date: 2019/12/17 17:25
 * @qq: 88247290
 * @wechat: 88247290
 * @phone: 13562233004
 * @email: 88247290@qq.com
 * @github: https://github.com/PayAbyss
 * @description: unknown
 */
@Slf4j
public final class AESUtil {

    private static final String DEFAULT_CODING = "utf-8";


    /**
     * REVIEW
     *
     * @param data      待加密字符串
     * @param secretKey 秘钥
     * @return 加密后的字符串
     * @author zhumaer
     * @deprecated : 加密
     */
    public static String encrypt(String secretKey, String data) {

        String encryptedData = null;
        try {

            byte[] input = data.getBytes(DEFAULT_CODING);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(secretKey.getBytes(DEFAULT_CODING));
            SecretKeySpec skc = new SecretKeySpec(digest, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skc);

            byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
            encryptedData = parseByte2HexStr(cipherText);
        } catch (Exception e) {
            throw new DataEncryptionException(DefaultResultCode.DATA_DECRYPT_FAILED);
        }

        return encryptedData;
    }


    /**
     * REVIEW
     *
     * @param encryptedData 已加密的字符串
     * @param secretKey     秘钥
     * @return 解密后的字符串
     * @author zhumaer
     * @deprecated : 解密
     */
    public static String decrypt(String encryptedData, String secretKey) {
        String originalData = null;
        try {
            byte[] secretKeyByte = secretKey.getBytes(DEFAULT_CODING);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(secretKeyByte);
            SecretKeySpec secretKeySpec = new SecretKeySpec(digest, "AES");
            Cipher dCipher = Cipher.getInstance("AES");
            dCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] clearByte = dCipher.doFinal(toByte(encryptedData));
            originalData = new String(clearByte);
        } catch (Exception e) {
            throw new DataEncryptionException(DefaultResultCode.DATA_ENCRYPT_FAILED);
        }

        return originalData;
    }

    /**
     * 字符串转字节数组
     */
    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    /**
     * 字节转16进制字符串
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
