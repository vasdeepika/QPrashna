package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class UserResult {

    @SerializedName("id")
    @Expose
    protected Integer id;
    @SerializedName("userName")
    @Expose
    protected String userName;
    @SerializedName("status")
    @Expose
    protected String status;
    @SerializedName("firstName")
    @Expose
    protected String firstName;
    @SerializedName("lastName")
    @Expose
    protected String lastName;
    @SerializedName("email")
    @Expose
    protected String email;
    @SerializedName("country")
    @Expose
    protected String country;
    @SerializedName("state")
    @Expose
    protected String state;
    @SerializedName("city")
    @Expose
    protected String city;
    @SerializedName("zip")
    @Expose
    protected String zip;
    @SerializedName("lastFeedAccessTime")
    @Expose
    protected long lastFeedAccessTime;
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
    @SerializedName("profilePicURL")
    @Expose
    protected String profilePicURL;
    @SerializedName("hash")
    @Expose
    protected String hash;
    @SerializedName("facebookFollowersCount")
    @Expose
    protected Integer facebookFollowersCount;
    @SerializedName("ipAddress")
    @Expose
    protected String ipAddress;
    @SerializedName("upvoteThresholdCount")
    @Expose
    protected Integer upvoteThresholdCount;
    @SerializedName("trendValue")
    @Expose
    protected Integer trendValue;
    @SerializedName("twitterFollowersCount")
    @Expose
    protected Integer twitterFollowersCount;
    @SerializedName("resetPasswordState")
    @Expose
    protected Integer resetPasswordState;
    @SerializedName("passwordExpirydate")
    @Expose
    protected long passwordExpirydate;
    @SerializedName("proxy")
    @Expose
    protected Boolean proxy;
    @SerializedName("superUser")
    @Expose
    protected Boolean superUser;
    @SerializedName("username")
    @Expose
    protected String username;

    public static final String PROFILE = "profile";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public long getLastFeedAccessTime() {
        return lastFeedAccessTime;
    }

    public void setLastFeedAccessTime(long lastFeedAccessTime) {
        this.lastFeedAccessTime = lastFeedAccessTime;
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

    public String getProfilePicURL() {
        return profilePicURL;
    }

    public void setProfilePicURL(String profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getFacebookFollowersCount() {
        return facebookFollowersCount;
    }

    public void setFacebookFollowersCount(Integer facebookFollowersCount) {
        this.facebookFollowersCount = facebookFollowersCount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getUpvoteThresholdCount() {
        return upvoteThresholdCount;
    }

    public void setUpvoteThresholdCount(Integer upvoteThresholdCount) {
        this.upvoteThresholdCount = upvoteThresholdCount;
    }

    public Integer getTrendValue() {
        return trendValue;
    }

    public void setTrendValue(Integer trendValue) {
        this.trendValue = trendValue;
    }

    public Integer getTwitterFollowersCount() {
        return twitterFollowersCount;
    }

    public void setTwitterFollowersCount(Integer twitterFollowersCount) {
        this.twitterFollowersCount = twitterFollowersCount;
    }

    public Integer getResetPasswordState() {
        return resetPasswordState;
    }

    public void setResetPasswordState(Integer resetPasswordState) {
        this.resetPasswordState = resetPasswordState;
    }

    public long getPasswordExpirydate() {
        return passwordExpirydate;
    }

    public void setPasswordExpirydate(Integer passwordExpirydate) {
        this.passwordExpirydate = passwordExpirydate;
    }

    public Boolean getProxy() {
        return proxy;
    }

    public void setProxy(Boolean proxy) {
        this.proxy = proxy;
    }

    public Boolean getSuperUser() {
        return superUser;
    }

    public void setSuperUser(Boolean superUser) {
        this.superUser = superUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
