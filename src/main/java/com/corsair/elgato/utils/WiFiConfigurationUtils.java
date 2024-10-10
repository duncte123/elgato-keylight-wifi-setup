package com.corsair.elgato.utils;

import com.corsair.elgato.model.WiFiData;
import com.fasterxml.jackson.databind.ObjectMapper;
import kotlin.collections.ArraysKt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class WiFiConfigurationUtils {
    private final byte[] INIT_VECTOR = {4, -97, 111, 17, 73, -58, -8, 75, 27, 20, -111, 60, 113, -23, -51, -66};

    private byte[] getEncryptionKey(int hardwareBoardType, int firmwareBuildNumber) {
        return new byte[]{76, -76, (byte) hardwareBoardType, (byte) (hardwareBoardType >> 8), -80, -22, -35, -18, -21, 42, 3, -118, 49, (byte) firmwareBuildNumber, (byte) (firmwareBuildNumber >> 8), 86};
    }

    public final byte[] encryptWiFiConfiguration(String passphrase, String ssid, int securityType, int firmwareBuildNumber, int hardwareBoardType) {
        try {
            byte[] bytes = new ObjectMapper().writeValueAsBytes(new WiFiData(passphrase, ssid, securityType));
            int length = bytes.length % 16;
            if (length != 0) {
                int i4 = 16 - length;
                byte[] bArr = new byte[i4];
                for (int i5 = 0; i5 < i4; i5++) {
                    bArr[i5] = 0;
                }
                bytes = ArraysKt.plus(bytes, bArr);
            }
            byte[] padding = new byte[16];
            for (int i6 = 0; i6 < 16; i6++) {
                padding[i6] = this.INIT_VECTOR[new Random().nextInt(16)];
            }

//            byte[] plus = ArraysKt.plus(padding, bytes);

            // TODO: test this impl as replacement for kotlin stuff (how do I even have that??)
            byte[] plus = Arrays.copyOf(padding, padding.length + bytes.length);
            System.arraycopy(bytes, 0, plus, padding.length, bytes.length);

            StringBuilder sb = new StringBuilder();
            for (byte valueOf : plus) {
                String format = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(valueOf)}, 1));
                sb.append(format);
            }

            System.out.println(sb);

            byte[] encryptionKey = getEncryptionKey(hardwareBoardType, firmwareBuildNumber);
            StringBuilder sb2 = new StringBuilder();
            for (byte valueOf2 : encryptionKey) {
                String format2 = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(valueOf2)}, 1));
                sb2.append(format2);
            }

            System.out.println(sb2);

            byte[] encrypt = encrypt(encryptionKey, plus);
            StringBuilder sb3 = new StringBuilder();
            for (byte valueOf3 : encrypt) {
                String format3 = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(valueOf3)}, 1));
                sb3.append(format3);
            }

            System.out.println(sb3);

            return encrypt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
        instance.init(1, secretKeySpec, new SecureRandom());
        return instance.doFinal(bArr2);
    }

    private byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
        instance.init(2, secretKeySpec, new SecureRandom());
        return instance.doFinal(bArr2);
    }
}
