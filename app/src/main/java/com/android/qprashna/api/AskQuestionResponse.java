package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AskQuestionResponse {

    @SerializedName("feedId")
    @Expose
    private Integer feedId;
    @SerializedName("feedType")
    @Expose
    private String feedType;
    @SerializedName("feedQuestionId")
    @Expose
    private Integer feedQuestionId;
    @SerializedName("questionUpvoteCount")
    @Expose
    private Integer questionUpvoteCount;
    @SerializedName("feedQuestionText")
    @Expose
    private String feedQuestionText;
    @SerializedName("feedCleanQuestionText")
    @Expose
    private String feedCleanQuestionText;
    @SerializedName("feedQuestionAskedBy")
    @Expose
    private String feedQuestionAskedBy;
    @SerializedName("feedQuestionAskedByName")
    @Expose
    private String feedQuestionAskedByName;
    @SerializedName("feedQuestionAskedTO")
    @Expose
    private String feedQuestionAskedTO;
    @SerializedName("feedQuestionAskedTOName")
    @Expose
    private String feedQuestionAskedTOName;
    @SerializedName("feedAnswerLikeCount")
    @Expose
    private Integer feedAnswerLikeCount;
    @SerializedName("feedAnswerCommentCount")
    @Expose
    private Integer feedAnswerCommentCount;
    @SerializedName("feedAnswerDislikeCount")
    @Expose
    private Integer feedAnswerDislikeCount;
    @SerializedName("insertTime")
    @Expose
    private long insertTime;
    @SerializedName("questionInsertTime")
    @Expose
    private long questionInsertTime;
    @SerializedName("answerDisliked")
    @Expose
    private String answerDisliked;
    @SerializedName("answerLiked")
    @Expose
    private String answerLiked;
    @SerializedName("postDisliked")
    @Expose
    private String postDisliked;
    @SerializedName("postLiked")
    @Expose
    private String postLiked;
    @SerializedName("upvoted")
    @Expose
    private String upvoted;
    @SerializedName("isBookMarked")
    @Expose
    private Boolean isBookMarked;
    @SerializedName("feedQuestionAskedByUserName")
    @Expose
    private String feedQuestionAskedByUserName;
    @SerializedName("feedQuestionAskedToUserName")
    @Expose
    private String feedQuestionAskedToUserName;

    public Integer getFeedId() {
        return feedId;
    }

    public void setFeedId(Integer feedId) {
        this.feedId = feedId;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public Integer getFeedQuestionId() {
        return feedQuestionId;
    }

    public void setFeedQuestionId(Integer feedQuestionId) {
        this.feedQuestionId = feedQuestionId;
    }

    public Integer getQuestionUpvoteCount() {
        return questionUpvoteCount;
    }

    public void setQuestionUpvoteCount(Integer questionUpvoteCount) {
        this.questionUpvoteCount = questionUpvoteCount;
    }

    public String getFeedQuestionText() {
        return feedQuestionText;
    }

    public void setFeedQuestionText(String feedQuestionText) {
        this.feedQuestionText = feedQuestionText;
    }

    public String getFeedCleanQuestionText() {
        return feedCleanQuestionText;
    }

    public void setFeedCleanQuestionText(String feedCleanQuestionText) {
        this.feedCleanQuestionText = feedCleanQuestionText;
    }

    public String getFeedQuestionAskedBy() {
        return feedQuestionAskedBy;
    }

    public void setFeedQuestionAskedBy(String feedQuestionAskedBy) {
        this.feedQuestionAskedBy = feedQuestionAskedBy;
    }

    public String getFeedQuestionAskedByName() {
        return feedQuestionAskedByName;
    }

    public void setFeedQuestionAskedByName(String feedQuestionAskedByName) {
        this.feedQuestionAskedByName = feedQuestionAskedByName;
    }

    public String getFeedQuestionAskedTO() {
        return feedQuestionAskedTO;
    }

    public void setFeedQuestionAskedTO(String feedQuestionAskedTO) {
        this.feedQuestionAskedTO = feedQuestionAskedTO;
    }

    public String getFeedQuestionAskedTOName() {
        return feedQuestionAskedTOName;
    }

    public void setFeedQuestionAskedTOName(String feedQuestionAskedTOName) {
        this.feedQuestionAskedTOName = feedQuestionAskedTOName;
    }

    public Integer getFeedAnswerLikeCount() {
        return feedAnswerLikeCount;
    }

    public void setFeedAnswerLikeCount(Integer feedAnswerLikeCount) {
        this.feedAnswerLikeCount = feedAnswerLikeCount;
    }

    public Integer getFeedAnswerCommentCount() {
        return feedAnswerCommentCount;
    }

    public void setFeedAnswerCommentCount(Integer feedAnswerCommentCount) {
        this.feedAnswerCommentCount = feedAnswerCommentCount;
    }

    public Integer getFeedAnswerDislikeCount() {
        return feedAnswerDislikeCount;
    }

    public void setFeedAnswerDislikeCount(Integer feedAnswerDislikeCount) {
        this.feedAnswerDislikeCount = feedAnswerDislikeCount;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    public long getQuestionInsertTime() {
        return questionInsertTime;
    }

    public void setQuestionInsertTime(long questionInsertTime) {
        this.questionInsertTime = questionInsertTime;
    }

    public String getAnswerDisliked() {
        return answerDisliked;
    }

    public void setAnswerDisliked(String answerDisliked) {
        this.answerDisliked = answerDisliked;
    }

    public String getAnswerLiked() {
        return answerLiked;
    }

    public void setAnswerLiked(String answerLiked) {
        this.answerLiked = answerLiked;
    }

    public String getPostDisliked() {
        return postDisliked;
    }

    public void setPostDisliked(String postDisliked) {
        this.postDisliked = postDisliked;
    }

    public String getPostLiked() {
        return postLiked;
    }

    public void setPostLiked(String postLiked) {
        this.postLiked = postLiked;
    }

    public String getUpvoted() {
        return upvoted;
    }

    public void setUpvoted(String upvoted) {
        this.upvoted = upvoted;
    }

    public Boolean getIsBookMarked() {
        return isBookMarked;
    }

    public void setIsBookMarked(Boolean isBookMarked) {
        this.isBookMarked = isBookMarked;
    }

    public String getFeedQuestionAskedByUserName() {
        return feedQuestionAskedByUserName;
    }

    public void setFeedQuestionAskedByUserName(String feedQuestionAskedByUserName) {
        this.feedQuestionAskedByUserName = feedQuestionAskedByUserName;
    }

    public String getFeedQuestionAskedToUserName() {
        return feedQuestionAskedToUserName;
    }

    public void setFeedQuestionAskedToUserName(String feedQuestionAskedToUserName) {
        this.feedQuestionAskedToUserName = feedQuestionAskedToUserName;
    }

}

