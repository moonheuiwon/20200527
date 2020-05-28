package com.example.a20200527.utils;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerUtil {

    private static final String BASE_URL = "http://15.165.177.142";

    public interface JsonResponseHandler {
        void onResponse(JSONObject json);
    }
    public static void postRequestLogin(Context context, String email, String pw, final JsonResponseHandler handler) {

         OkHttpClient client = new OkHttpClient();

        final RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build();

        Request request = new Request.Builder()
                .url(BASE_URL+"/user")
                .post(requestBody)
//                .header() //  헤더가 필요하다면 이 시점에서 첨부.
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("서버연결실패","로그인 기능 실패");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String body = response.body().string();

                Log.d("서버 연결 성공", body);

                try {
                    JSONObject jsonObject = new JSONObject(body);

                    if (handler != null) {
                        handler.onResponse(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public static void getRequestDuplicatedCheck(Context context, String input, String checkType, final JsonResponseHandler handler) {
        OkHttpClient client = new OkHttpClient();

//        GET메쏘드 - 파라미터들이 모두 주소에 같이 적힌다.
//        요청할때 파라미터를 주소에 모두 적어줘야한다.

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL+"/user_check").newBuilder();
        urlBuilder.addEncodedQueryParameter("type", checkType);
        urlBuilder.addEncodedQueryParameter("value", input);

        String completeUrl = urlBuilder.build().toString();
        Log.d("완성된URL", completeUrl);

        final Request request = new Request.Builder()
                .url(completeUrl)
//                .header()
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("서버연결실패", e.toString());
//                e.printStackTrace(); // 어디가 오류났는지 보여주는
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();

                try {
                    JSONObject json = new JSONObject(body);

                    if (handler != null) {
                        handler.onResponse(json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("body", body);
            }
        });

    }
}
