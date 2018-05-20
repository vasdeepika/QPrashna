package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Item {

    @SerializedName("feedId")
    @Expose
    protected Integer feedId;
    @SerializedName("feedType")
    @Expose
    protected String feedType;
    @SerializedName("feedQuestionId")
    @Expose
    protected Integer feedQuestionId;
    @SerializedName("questionUpvoteCount")
    @Expose
    protected Integer questionUpvoteCount;
    @SerializedName("feedQuestionText")
    @Expose
    protected String feedQuestionText;
    @SerializedName("feedCleanQuestionText")
    @Expose
    protected String feedCleanQuestionText;
    @SerializedName("feedQuestionAskedBy")
    @Expose
    protected String feedQuestionAskedBy;
    @SerializedName("feedQuestionAskedByName")
    @Expose
    protected String feedQuestionAskedByName;
    @SerializedName("feedQuestionAskedTO")
    @Expose
    protected String feedQuestionAskedTO;
    @SerializedName("feedQuestionAskedTOName")
    @Expose
    protected String feedQuestionAskedTOName;
    @SerializedName("timeAgo")
    @Expose
    protected String timeAgo;
    @SerializedName("feedAnswerLikeCount")
    @Expose
    protected Integer feedAnswerLikeCount;
    @SerializedName("feedAnswerCommentCount")
    @Expose
    protected Integer feedAnswerCommentCount;
    @SerializedName("feedAnswerDislikeCount")
    @Expose
    protected Integer feedAnswerDislikeCount;
    @SerializedName("insertTime")
    @Expose
    protected long insertTime;
    @SerializedName("updateTime")
    @Expose
    protected long updateTime;
    @SerializedName("questionInsertTime")
    @Expose
    protected long questionInsertTime;
    @SerializedName("answerDisliked")
    @Expose
    protected String answerDisliked;
    @SerializedName("answerLiked")
    @Expose
    protected String answerLiked;
    @SerializedName("postDisliked")
    @Expose
    protected String postDisliked;
    @SerializedName("postLiked")
    @Expose
    protected String postLiked;
    @SerializedName("upvoted")
    @Expose
    protected String upvoted;
    @SerializedName("isBookMarked")
    @Expose
    protected Boolean isBookMarked;
    @SerializedName("feedQuestionAskedByUserName")
    @Expose
    protected String feedQuestionAskedByUserName;
    @SerializedName("feedQuestionAskedToUserName")
    @Expose
    protected String feedQuestionAskedToUserName;
    @SerializedName("feedTitle")
    @Expose
    protected String feedTitle;
    @SerializedName("feedPostId")
    @Expose
    protected Integer feedPostId;
    @SerializedName("feedPostText")
    @Expose
    protected String feedPostText;
    @SerializedName("feedPostBy")
    @Expose
    protected String feedPostBy;
    @SerializedName("feedPostByUserId")
    @Expose
    protected Integer feedPostByUserId;
    @SerializedName("feedPostLikeCount")
    @Expose
    protected Integer feedPostLikeCount;
    @SerializedName("feedPostCommentCount")
    @Expose
    protected Integer feedPostCommentCount;
    @SerializedName("feedPostDislikeCount")
    @Expose
    protected Integer feedPostDislikeCount;
    @SerializedName("feedPostedByUserName")
    @Expose
    protected String feedPostedByUserName;
    @SerializedName("feedQuestionAnswerId")
    @Expose
    protected Integer feedQuestionAnswerId;
    @SerializedName("feedAnswerText")
    @Expose
    protected String feedAnswerText;
    @SerializedName("answerInsertTime")
    @Expose
    protected long answerInsertTime;
    @SerializedName("answerEditCount")
    @Expose
    protected Integer answerEditCount;

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
