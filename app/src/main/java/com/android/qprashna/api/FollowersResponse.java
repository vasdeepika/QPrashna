package com.android.qprashna.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class FollowersResponse {

    public static final String KEY = "followers";
    @SerializedName("items")
    @Expose
    protected List<FollowerItem> items = null;
    @SerializedName("currentCount")
    @Expose
    protected Integer currentCount;
    @SerializedName("maxPKValue")
    @Expose
    protected Integer maxPKValue;
    @SerializedName("totalNumberOfRecords")
    @Expose
    protected Integer totalNumberOfRecords;
    @SerializedName("pageId")
    @Expose
    protected Integer pageId;
    @SerializedName("totalNumberOfPages")
    @Expose
    protected Integer totalNumberOfPages;

    public List<FollowerItem> getItems() {
        return items;
    }

    public void setItems(List<FollowerItem> items) {
        this.items = items;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public Integer getMaxPKValue() {
        return maxPKValue;
    }

    public void setMaxPKValue(Integer maxPKValue) {
        this.maxPKValue = maxPKValue;
    }

    public Integer getTotalNumberOfRecords() {
        return totalNumberOfRecords;
    }

    public void setTotalNumberOfRecords(Integer totalNumberOfRecords) {
        this.totalNumberOfRecords = totalNumberOfRecords;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(Integer totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

}
