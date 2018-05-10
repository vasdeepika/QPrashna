package com.android.qprashna.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class LoginResponse {

    public static final String KEY = "login";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("middleName")
    @Expose
    private String middleName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dateOfBirth")
    @Expose
    private long dateOfBirth;
    @SerializedName("googleID")
    @Expose
    private String googleID;
    @SerializedName("twitterID")
    @Expose
    private String twitterID;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("designation")
    @Expose
    private String designation;
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
    @SerializedName("profilePicURL")
    @Expose
    private String profilePicURL;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    @SerializedName("internalUser")
    @Expose
    private Boolean internalUser;
    @SerializedName("twitterVerified")
    @Expose
    private Boolean twitterVerified;
    @SerializedName("trendValue")
    @Expose
    private Integer trendValue;
    @SerializedName("twitterFollowersCount")
    @Expose
    private Integer twitterFollowersCount;
    @SerializedName("resetPasswordState")
    @Expose
    private Integer resetPasswordState;
    @SerializedName("proxy")
    @Expose
    private Boolean proxy;
    @SerializedName("superUser")
    @Expose
    private Boolean superUser;
    @SerializedName("username")
    @Expose
    private String username;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Integer dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getTwitterID() {
        return twitterID;
    }

    public void setTwitterID(String twitterID) {
        this.twitterID = twitterID;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public void setCreatedDate(Integer createdDate) {
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

    public void setUpdatedDate(Integer updatedDate) {
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getInternalUser() {
        return internalUser;
    }

    public void setInternalUser(Boolean internalUser) {
        this.internalUser = internalUser;
    }

    public Boolean getTwitterVerified() {
        return twitterVerified;
    }

    public void setTwitterVerified(Boolean twitterVerified) {
        this.twitterVerified = twitterVerified;
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