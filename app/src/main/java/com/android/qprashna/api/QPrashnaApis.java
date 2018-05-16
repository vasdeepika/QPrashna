package com.android.qprashna.api;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QPrashnaApis {
    @POST("login/performLogin") // login function api
    Observable<Response<LoginResponse>> login(@Body RequestBody loginRequestbody);

    @POST("user/create") // create account api
    Observable<Response<LoginResponse>> createAccount(@Body RequestBody loginRequestbody);

    @GET("{id}/feeds/0/10") // Get Feeds api
    Observable<FeedsResponse> getFeeds(@Path("id") int id);

    @PUT("{id}/question/upvote/{questionId}") // Put upVote or unUpVote count
    Observable<Integer> putUpVote(@Header("Cookie") String sessionId, @Path("id") int id, @Path("questionId") int questionId, @Query("action") String action, @Query("feedId") int feedId);

    @GET("search/all/{search}") // Search persons api
    Observable<UsersResponse> getUsers(@Path("search") String search);

    @POST("isfollowing") // Get user
    Observable<String> isFollowing(@Body RequestBody isFollowingRequestBody);

    @POST("follower/add") // Add follower
    Observable<FollowUserResponse> followUser(@Header("Cookie") String sessionId, @Body RequestBody followUserRequestBody);

    @POST("followee/remove") // Add follower
    Observable<RemoveFolloweeResponse> unFollowUser(@Header("Cookie") String sessionId, @Body RequestBody followUserRequestBody);

    @POST("question/post/") // Add follower
    Observable<AskQuestionResponse> askQuestion(@Header("Cookie") String sessionId, @Body RequestBody askQuestionRequestBody);
}
