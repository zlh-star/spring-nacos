package com.example.springboottest.model;


import java.util.Date;

public class UserCondition {
    private String filter;
    private String sorter;
    private String pageIndex;
    private int page;
    private int rows;
    private int pageBegin;
    private int pageEnd;
    private String pageNo;
    private String pageSize;
    private String accountId;
    private String accountName;
    private String password;
    private Date createData;

    private int begin = 0;
    private int end = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPageBegin() {
        return pageBegin;
    }

    public void setPageBegin(int pageBegin) {
        this.pageBegin = pageBegin;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public int getBegin() {
        // 实现起点
        return pageBegin - 1;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        // 实现截止点
        return rows;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getFilter() {
        return filter;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public String getSorter() {
        return sorter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public Date getCreateData() {
        return createData;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }
}
