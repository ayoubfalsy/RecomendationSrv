package com.javatechie.springsecurityjwtexample.entitiesStatus;

public class Posts {
    private long lngId;
    private String strPays;
    private String strType;
    private int lngPage;
    private int lngSize;

    public Posts() {
    }


    public long getLngId() {
        return lngId;
    }

    public String getStrPays() {
        return strPays;
    }

    public String getStrType() {
        return strType;
    }

    public int getLngPage() {
        return lngPage;
    }

    public int getLngSize() {
        return lngSize;
    }

    public void setLngId(long lngId) {
        this.lngId = lngId;
    }

    public void setStrPays(String strPays) {
        this.strPays = strPays;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public void setLngPage(int lngPage) {
        this.lngPage = lngPage;
    }

    public void setLngSize(int lngSize) {
        this.lngSize = lngSize;
    }
}
