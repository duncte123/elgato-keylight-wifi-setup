package com.corsair.elgato.utils;

import com.corsair.elgato.model.WiFiData;
import com.fasterxml.jackson.databind.ObjectMapper;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

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
        Intrinsics.checkNotNullParameter(passphrase, "passphrase");
        Intrinsics.checkNotNullParameter(ssid, "ssid");
        try {
            byte[] bytes = new ObjectMapper().writeValueAsBytes(new WiFiData(passphrase, ssid, securityType));
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
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
            byte[] plus = ArraysKt.plus(padding, bytes);
            StringBuilder sb = new StringBuilder();
            for (byte valueOf : plus) {
                String format = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(valueOf)}, 1));
                Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                sb.append(format);
            }
            byte[] encryptionKey = getEncryptionKey(hardwareBoardType, firmwareBuildNumber);
            StringBuilder sb2 = new StringBuilder();
            for (byte valueOf2 : encryptionKey) {
                String format2 = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(valueOf2)}, 1));
                Intrinsics.checkNotNullExpressionValue(format2, "format(...)");
                sb2.append(format2);
            }
            byte[] encrypt = encrypt(encryptionKey, plus);
            StringBuilder sb3 = new StringBuilder();
            for (byte valueOf3 : encrypt) {
                String format3 = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(valueOf3)}, 1));
                Intrinsics.checkNotNullExpressionValue(format3, "format(...)");
                sb3.append(format3);
            }
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
        byte[] doFinal = instance.doFinal(bArr2);
        Intrinsics.checkNotNullExpressionValue(doFinal, "doFinal(...)");
        return doFinal;
    }

    private byte[] decrypt(byte[] bArr, byte[] bArr2, byte[] bArr3) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
        instance.init(2, secretKeySpec, new SecureRandom());
        byte[] doFinal = instance.doFinal(bArr2);
        Intrinsics.checkNotNullExpressionValue(doFinal, "doFinal(...)");
        return doFinal;
    }
}
