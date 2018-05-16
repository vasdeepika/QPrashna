package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowUserResponse {

    @SerializedName("followerId")
    @Expose
    private Integer followerId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("followeeId")
    @Expose
    private Integer followeeId;
    @SerializedName("isStillFollowing")
    @Expose
    private Boolean isStillFollowing;
    @SerializedName("commonFolloweeCount")
    @Expose
    private Object commonFolloweeCount;
    @SerializedName("profileViews")
    @Expose
    private Object profileViews;
    @SerializedName("questionsPosted")
    @Expose
    private Object questionsPosted;
    @SerializedName("upvotedCount")
    @Expose
    private Object upvotedCount;
    @SerializedName("answersLikeCount")
    @Expose
    private Object answersLikeCount;
    @SerializedName("postsLikeCount")
    @Expose
    private Object postsLikeCount;
    @SerializedName("commentsPosted")
    @Expose
    private Object commentsPosted;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    private long updatedDate;
    @SerializedName("subscribe")
    @Expose
    private Object subscribe;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("profilePicURL")
    @Expose
    private String profilePicURL;
    @SerializedName("loggedInUserFollowing")
    @Expose
    private Object loggedInUserFollowing;
    @SerializedName("followerUserName")
    @Expose
    private String followerUserName;
    @SerializedName("followeeUserName")
    @Expose
    private String followeeUserName;

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(Integer followeeId) {
        this.followeeId = followeeId;
    }

    public Boolean getIsStillFollowing() {
        return isStillFollowing;
    }

    public void setIsStillFollowing(Boolean isStillFollowing) {
        this.isStillFollowing = isStillFollowing;
    }

    public Object getCommonFolloweeCount() {
        return commonFolloweeCount;
    }

    public void setCommonFolloweeCount(Object commonFolloweeCount) {
        this.commonFolloweeCount = commonFolloweeCount;
    }

    public Object getProfileViews() {
        return profileViews;
    }

    public void setProfileViews(Object profileViews) {
        this.profileViews = profileViews;
    }

    public Object getQuestionsPosted() {
        return questionsPosted;
    }

    public void setQuestionsPosted(Object questionsPosted) {
        this.questionsPosted = questionsPosted;
    }

    public Object getUpvotedCount() {
        return upvotedCount;
    }

    public void setUpvotedCount(Object upvotedCount) {
        this.upvotedCount = upvotedCount;
    }

    public Object getAnswersLikeCount() {
        return answersLikeCount;
    }

    public void setAnswersLikeCount(Object answersLikeCount) {
        this.answersLikeCount = answersLikeCount;
    }

    public Object getPostsLikeCount() {
        return postsLikeCount;
    }

    public void setPostsLikeCount(Object postsLikeCount) {
        this.postsLikeCount = postsLikeCount;
    }

    public Object getCommentsPosted() {
        return commentsPosted;
    }

    public void setCommentsPosted(Object commentsPosted) {
        this.commentsPosted = commentsPosted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Object subscribe) {
        this.subscribe = subscribe;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public Object getLoggedInUserFollowing() {
        return loggedInUserFollowing;
    }

    public void setLoggedInUserFollowing(Object loggedInUserFollowing) {
        this.loggedInUserFollowing = loggedInUserFollowing;
    }

    public String getFollowerUserName() {
        return followerUserName;
    }

    public void setFollowerUserName(String followerUserName) {
        this.followerUserName = followerUserName;
    }

    public String getFolloweeUserName() {
        return followeeUserName;
    }

    public void setFolloweeUserName(String followeeUserName) {
        this.followeeUserName = followeeUserName;
    }

}