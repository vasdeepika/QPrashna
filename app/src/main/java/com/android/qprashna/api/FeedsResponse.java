package com.android.qprashna.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class FeedsResponse {
    
    public static final String KEY = "feeds"
;
    @SerializedName("items")
    @Expose
    protected List<Item> items = null;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
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