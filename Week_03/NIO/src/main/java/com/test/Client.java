package com.test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8081/")
                .build();
        Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        System.out.println(response.body().string());
    }
}
