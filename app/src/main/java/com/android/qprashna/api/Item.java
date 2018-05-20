package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

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
    @SerializedName("timeAgo")
    @Expose
    private String timeAgo;
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
    @SerializedName("updateTime")
    @Expose
    private long updateTime;
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
    @SerializedName("feedTitle")
    @Expose
    private String feedTitle;
    @SerializedName("feedPostId")
    @Expose
    private Integer feedPostId;
    @SerializedName("feedPostText")
    @Expose
    private String feedPostText;
    @SerializedName("feedPostBy")
    @Expose
    private String feedPostBy;
    @SerializedName("feedPostByUserId")
    @Expose
    private Integer feedPostByUserId;
    @SerializedName("feedPostLikeCount")
    @Expose
    private Integer feedPostLikeCount;
    @SerializedName("feedPostCommentCount")
    @Expose
    private Integer feedPostCommentCount;
    @SerializedName("feedPostDislikeCount")
    @Expose
    private Integer feedPostDislikeCount;
    @SerializedName("feedPostedByUserName")
    @Expose
    private String feedPostedByUserName;
    @SerializedName("feedQuestionAnswerId")
    @Expose
    private Integer feedQuestionAnswerId;
    @SerializedName("feedAnswerText")
    @Expose
    private String feedAnswerText;
    @SerializedName("answerInsertTime")
    @Expose
    private long answerInsertTime;
    @SerializedName("answerEditCount")
    @Expose
    private Integer answerEditCount;

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

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
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

    public void setInsertTime(Integer insertTime) {
        this.insertTime = insertTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public long getQuestionInsertTime() {
        return questionInsertTime;
    }

    public void setQuestionInsertTime(Integer questionInsertTime) {
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

    public boolean getUpvoted() {
        return Boolean.valueOf(upvoted);
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

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public Integer getFeedPostId() {
        return feedPostId;
    }

    public void setFeedPostId(Integer feedPostId) {
        this.feedPostId = feedPostId;
    }

    public String getFeedPostText() {
        return feedPostText;
    }

    public void setFeedPostText(String feedPostText) {
        this.feedPostText = feedPostText;
    }

    public String getFeedPostBy() {
        return feedPostBy;
    }

    public void setFeedPostBy(String feedPostBy) {
        this.feedPostBy = feedPostBy;
    }

    public Integer getFeedPostByUserId() {
        return feedPostByUserId;
    }

    public void setFeedPostByUserId(Integer feedPostByUserId) {
        this.feedPostByUserId = feedPostByUserId;
    }

    public Integer getFeedPostLikeCount() {
        return feedPostLikeCount;
    }

    public void setFeedPostLikeCount(Integer feedPostLikeCount) {
        this.feedPostLikeCount = feedPostLikeCount;
    }

    public Integer getFeedPostCommentCount() {
        return feedPostCommentCount;
    }

    public void setFeedPostCommentCount(Integer feedPostCommentCount) {
        this.feedPostCommentCount = feedPostCommentCount;
    }

    public Integer getFeedPostDislikeCount() {
        return feedPostDislikeCount;
    }

    public void setFeedPostDislikeCount(Integer feedPostDislikeCount) {
        this.feedPostDislikeCount = feedPostDislikeCount;
    }

    public String getFeedPostedByUserName() {
        return feedPostedByUserName;
    }

    public void setFeedPostedByUserName(String feedPostedByUserName) {
        this.feedPostedByUserName = feedPostedByUserName;
    }

    public Integer getFeedQuestionAnswerId() {
        return feedQuestionAnswerId;
    }

    public void setFeedQuestionAnswerId(Integer feedQuestionAnswerId) {
        this.feedQuestionAnswerId = feedQuestionAnswerId;
    }

    public String getFeedAnswerText() {
        return feedAnswerText;
    }

    public void setFeedAnswerText(String feedAnswerText) {
        this.feedAnswerText = feedAnswerText;
    }

    public long getAnswerInsertTime() {
        return answerInsertTime;
    }

    public void setAnswerInsertTime(Integer answerInsertTime) {
        this.answerInsertTime = answerInsertTime;
    }

    public Integer getAnswerEditCount() {
        return answerEditCount;
    }

    public void setAnswerEditCount(Integer answerEditCount) {
        this.answerEditCount = answerEditCount;
    }

}
