package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class FollowerItem {

    @SerializedName("followerId")
    @Expose
    protected Integer followerId;
    @SerializedName("userId")
    @Expose
    protected Integer userId;
    @SerializedName("followeeId")
    @Expose
    protected Integer followeeId;
    @SerializedName("isStillFollowing")
    @Expose
    protected Boolean isStillFollowing;
    @SerializedName("commonFolloweeCount")
    @Expose
    protected String commonFolloweeCount;
    @SerializedName("profileViews")
    @Expose
    protected String profileViews;
    @SerializedName("questionsPosted")
    @Expose
    protected String questionsPosted;
    @SerializedName("upvotedCount")
    @Expose
    protected int upvotedCount;
    @SerializedName("answersLikeCount")
    @Expose
    protected int answersLikeCount;
    @SerializedName("postsLikeCount")
    @Expose
    protected int postsLikeCount;
    @SerializedName("commentsPosted")
    @Expose
    protected String commentsPosted;
    @SerializedName("createdBy")
    @Expose
    protected String createdBy;
    @SerializedName("createdDate")
    @Expose
    protected long createdDate;
    @SerializedName("updatedBy")
    @Expose
    protected String updatedBy;
    @SerializedName("updatedDate")
    @Expose
    protected long updatedDate;
    @SerializedName("subscribe")
    @Expose
    protected String subscribe;
    @SerializedName("firstName")
    @Expose
    protected String firstName;
    @SerializedName("lastName")
    @Expose
    protected String lastName;
    @SerializedName("profilePicURL")
    @Expose
    protected String profilePicURL;
    @SerializedName("loggedInUserFollowing")
    @Expose
    protected Boolean loggedInUserFollowing;
    @SerializedName("followerUserName")
    @Expose
    protected String followerUserName;
    @SerializedName("followeeUserName")
    @Expose
    protected String followeeUserName;

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

    public String getCommonFolloweeCount() {
        return commonFolloweeCount;
    }

    public void setCommonFolloweeCount(String commonFolloweeCount) {
        this.commonFolloweeCount = commonFolloweeCount;
    }

    public String getProfileViews() {
        return profileViews;
    }

    public void setProfileViews(String profileViews) {
        this.profileViews = profileViews;
    }

    public String getQuestionsPosted() {
        return questionsPosted;
    }

    public void setQuestionsPosted(String questionsPosted) {
        this.questionsPosted = questionsPosted;
    }

    public int getUpvotedCount() {
        return upvotedCount;
    }

    public void setUpvotedCount(int upvotedCount) {
        this.upvotedCount = upvotedCount;
    }

    public int getAnswersLikeCount() {
        return answersLikeCount;
    }

    public void setAnswersLikeCount(int answersLikeCount) {
        this.answersLikeCount = answersLikeCount;
    }

    public int getPostsLikeCount() {
        return postsLikeCount;
    }

    public void setPostsLikeCount(int postsLikeCount) {
        this.postsLikeCount = postsLikeCount;
    }

    public String getCommentsPosted() {
        return commentsPosted;
    }

    public void setCommentsPosted(String commentsPosted) {
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

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
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

    public Boolean getLoggedInUserFollowing() {
        return loggedInUserFollowing;
    }

    public void setLoggedInUserFollowing(Boolean loggedInUserFollowing) {
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