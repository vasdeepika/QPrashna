package com.android.qprashna.api;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QPrashnaApis {
    @POST("login/performLogin") // login function api
    Observable<LoginResponse> login(@Body RequestBody loginRequestbody);

    @POST("user/create") // create account api
    Observable<LoginResponse> createAccount(@Body RequestBody loginRequestbody);

    @GET("71/feeds/0/10") // Get Feeds api
    Observable<FeedsResponse> getFeeds();

}
