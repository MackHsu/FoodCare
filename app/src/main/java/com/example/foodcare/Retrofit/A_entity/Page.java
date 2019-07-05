package com.example.foodcare.Retrofit.A_entity;


import java.io.Serializable;

public class Page implements Serializable {

    //TODO 当前页
    private int pageIndex = 1;

    //是否为最后一页的标志
    private boolean end;

    //页大小
    private int pageSize = 10;
    //记录的位置
    private int start;

    public Page() {
    }


    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
