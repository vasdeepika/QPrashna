package com.android.qprashna.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowersResponse {

    @SerializedName("items")
    @Expose
    private List<FollowerItem> items = null;
    @SerializedName("currentCount")
    @Expose
    private Integer currentCount;
    @SerializedName("maxPKValue")
    @Expose
    private Integer maxPKValue;
    @SerializedName("totalNumberOfRecords")
    @Expose
    private Integer totalNumberOfRecords;
    @SerializedName("pageId")
    @Expose
    private Integer pageId;
    @SerializedName("totalNumberOfPages")
    @Expose
    private Integer totalNumberOfPages;

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
