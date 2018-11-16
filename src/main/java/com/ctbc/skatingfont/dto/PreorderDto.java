package com.ctbc.skatingfont.dto;

import javax.persistence.Column;

/**
 * Created by JieChen on 2018/11/14.
 */
public class PreorderDto {
    private Integer id;
    private String dat;
    private String sessionsName;
    private String startTime;
    private String endTime;
    private Integer reserved = 0;
    //剩餘人數
    private Integer remaining =0;

    public PreorderDto() {
    }

    public PreorderDto(Integer id, String dat, String sessionsName, String startTime, String endTime, Integer reserved, Integer remaining) {
        this.id = id;
        this.dat = dat;
        this.sessionsName = sessionsName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reserved = reserved;
        this.remaining = remaining;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getSessionsName() {
        return sessionsName;
    }

    public void setSessionsName(String sessionsName) {
        this.sessionsName = sessionsName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    @Override
    public String toString() {
        return "PreorderDto{" +
                "id=" + id +
                ", dat='" + dat + '\'' +
                ", sessionsName='" + sessionsName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", reserved=" + reserved +
                ", remaining=" + remaining +
                '}';
    }
}
