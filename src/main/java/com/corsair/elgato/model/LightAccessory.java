package com.corsair.elgato.model;

import com.corsair.elgato.ClientApi;
import com.corsair.elgato.utils.WiFiConfigurationUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class LightAccessory {
    private final ClientApi clientApi;
    private final WiFiConfigurationUtils wiFiConfigurationUtils = new WiFiConfigurationUtils();

    public LightAccessory(ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    public Future<Void> putWifiInfo(String password, String ssid, int securityType, int firmwareBuildNumber, int hardwareBoardType) {
        final byte[] encryptedStuff = encryptWifiCredentials(password, ssid, securityType, firmwareBuildNumber, hardwareBoardType);

        CompletableFuture<Void> future = this.clientApi.putWifiInfo(encryptedStuff).exceptionally((ex) -> {
            System.out.println("You done fucked m8 " + ex.getMessage());
            return null;
        });
        return future;
    }

    private byte[] encryptWifiCredentials(String passphrase, String ssid, int securityType, int firmwareBuildNumber, int hardwareBoardType) {
        byte[] encryptWiFiConfiguration = wiFiConfigurationUtils.encryptWiFiConfiguration(passphrase, ssid, securityType, firmwareBuildNumber, hardwareBoardType);
        if (encryptWiFiConfiguration != null) {
            return encryptWiFiConfiguration;
        }
        throw new IllegalArgumentException();
    }
}
