package com.android.qprashna.api;

import android.support.v4.util.ArrayMap;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
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

        jsonParams.put("userName", userName);
        jsonParams.put("password", password);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static RequestBody isFollowingRequestBody(int userId, int profileIdToBeView) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("userId", userId);
        jsonParams.put("profileIdToBeView", profileIdToBeView);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static RequestBody getFollowUserRequestBody(int userId, int followeeId) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("userId", userId);
        jsonParams.put("followeeId", followeeId);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static RequestBody askQuestionRequestBody(int userId, int askToUserId, String question) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("originatorId", userId);
        jsonParams.put("askedToUserId", askToUserId);
        jsonParams.put("upvoteCount", 0);
        jsonParams.put("questionStatus", "A");
        jsonParams.put("questionText", question);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static RequestBody getCreateAccountRequestBody(String userName, String password, String firstName, String lastName, String emailAddress) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("userName", userName);
        jsonParams.put("password", password);
        jsonParams.put("firstName",firstName);
        jsonParams.put("lastName", lastName);
        jsonParams.put("email", emailAddress);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static RequestBody updateProfileRequestBody(int id, String firstName, String lastName, String gender, String designation, String state, String country, String dob, String email) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("id",id);
        jsonParams.put("firstName",firstName);
        jsonParams.put("lastName", lastName);
        jsonParams.put("gender", gender);
        jsonParams.put("designation", designation);
        jsonParams.put("country", country);
        jsonParams.put("state", state);
        jsonParams.put("dateOfBirth", dob);
        jsonParams.put("email", email);

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(jsonParams)).toString());
    }

    public static RequestBody updatePasswordRequestBody(int userIdFromSharedPreferences, String currentPassword, String newPassword) {
        Map<String, Object> jsonParams = new ArrayMap<>();

        jsonParams.put("userId",userIdFromSharedPreferences);
        jsonParams.put("oldPassword",currentPassword);
        jsonParams.put("newPassword", newPassword);

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
