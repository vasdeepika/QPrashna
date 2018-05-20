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

    @GET("{id}/feeds/0/20") // Get Feeds api
    Observable<FeedsResponse> getFeeds(@Path("id") int id);

    @GET("questions/answered/{id}/0/20") // Get Answered by user
    Observable<FeedsResponse> getAnsweredByMe(@Path("id") int id);

    @GET("questions/yettoanswer/{id}/0/20") // Get UnAnswered by user
    Observable<FeedsResponse> getUnAnsweredByMe(@Header("Cookie") String sessionId, @Path("id") int id);

    @GET("questions/upvotedby/{id}/0/20") // Get my up voted questions
    Observable<FeedsResponse> getMyUpVotedQuestions(@Header("Cookie") String sessionId, @Path("id") int id);

    @GET("questions/askedby/{id}/0/20") // Get asked by user
    Observable<FeedsResponse> getQuestionsAskedByMe(@Header("Cookie") String sessionId, @Path("id") int id);

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

    @GET("follower/list/{id}/0/20") // Followers list
    Observable<FollowersResponse> getFollowers(@Header("Cookie") String sessionId,@Path("id") int id);

    @GET("followee/list/{id}/0/20") // Followers list
    Observable<FollowersResponse> getFollowees(@Header("Cookie") String sessionId,@Path("id") int id);

    @POST("user/update") // update profile
    Observable<LoginResponse> updateProfile(@Header("Cookie") String sessionId, @Body RequestBody updateProfileRequestBody);

    @POST("changepassword") // update profile
    Observable<ChangePasswordResponse> updatePassword(@Header("Cookie") String sessionId, @Body RequestBody updatePasswordRequestBody);
}
