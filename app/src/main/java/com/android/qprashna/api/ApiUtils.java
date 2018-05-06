package com.android.qprashna.api;

import android.support.v4.util.ArrayMap;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    public static QPrashnaApis getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://qprashna.com/rest/")
                .build();

        return retrofit.create(QPrashnaApis.class);
    }

    public static RequestBody getLoginRequestBody(String userName, String password) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        //put something inside the map, could be null
        jsonParams.put("userName", userName);
        jsonParams.put("password", password);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static String getErrorMessage(String errorResponse) {
        try {
            JSONObject jsonObject = new JSONObject(errorResponse);
            return jsonObject.getJSONObject("errorInfo").getString("errorMessage");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
