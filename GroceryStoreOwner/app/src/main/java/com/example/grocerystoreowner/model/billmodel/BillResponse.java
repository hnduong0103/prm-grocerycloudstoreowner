package com.example.grocerystoreowner.model.billmodel;

import java.util.List;

public class BillResponse {
    private int pageIndex;
    private int pageSize;
    private int totalItem;
    private int totalPage;
    private List<BillData> data;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<BillData> getData() {
        return data;
    }

    public void setData(List<BillData> data) {
        this.data = data;
    }
}
