package com.corsair.elgato.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class FutureOkHttpCallback<T> extends CompletableFuture<T> implements Callback {
    private final Class<T> classT;

    public FutureOkHttpCallback(Class<T> classT) {
        super();
        this.classT = classT;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        this.completeExceptionally(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.code() == 204) {
            this.complete(null);
            return;
        }

        try (ResponseBody body = response.body()) {
            final InputStream byteStream = body.byteStream();
            final ObjectMapper mapper = new ObjectMapper();
            final T theT = mapper.readValue(byteStream, classT);

            this.complete(theT);
        }
    }
}
