package com.corsair.elgato;

import com.corsair.elgato.http.FutureOkHttpCallback;
import com.corsair.elgato.http.FutureVoidOkHttpCallback;
import com.corsair.elgato.model.AccessoryData;
import okhttp3.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ClientApi {
    private final String address;
    private final OkHttpClient httpClient = new OkHttpClient();

    public ClientApi(String address) {
        this.address = address;
    }

    public CompletableFuture<AccessoryData> getAccessoryInfo() {
        final Request request = new Request.Builder()
                .url(address + "/elgato/accessory-info")
                .header("Accept", "application/json")
                .get()
                .build();

        final FutureOkHttpCallback<AccessoryData> callback = new FutureOkHttpCallback<>(AccessoryData.class);

        this.httpClient.newCall(request).enqueue(callback);

        return callback;
    }

    public CompletableFuture<Void> putWifiInfo(byte[] configuration) {
        final Request request = new Request.Builder()
                .url(address + "/elgato/wifi-info")
                .header("Content-Type", "application/octet-stream")
                .put(RequestBody.create(configuration, MediaType.parse("application/octet-stream")))
                .build();

        final FutureVoidOkHttpCallback callback = new FutureVoidOkHttpCallback();

        this.httpClient.newCall(request).enqueue(callback);

        return callback;
    }
}
