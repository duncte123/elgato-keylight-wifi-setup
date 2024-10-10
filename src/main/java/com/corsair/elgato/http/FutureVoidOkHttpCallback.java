package com.corsair.elgato.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class FutureVoidOkHttpCallback extends CompletableFuture<Void> implements Callback {
    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        this.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        this.complete(null);
    }
}
