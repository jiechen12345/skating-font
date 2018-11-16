package com.ctbc.skatingfont.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by JieChen on 2018/11/15.
 */
@Entity
public class PreOrder {
    @Id
    @Column(length = 12)
    private String id;
    @ManyToOne(targetEntity = Sessions.class)
    private Sessions sessions;
    @Column(length = 12)
    private String preorderDate;
    private String groupName;
    @Column(length = 12)
    private String applicantName;
    private String applicantPhone;
    @Column(length = 150)
    private String applicantEmail;
    @Column(length = 50)
    private Integer groupNum;
    @Column(length = 1)
    //0:送出 1:OTP過 2:審核通過 3:到場??
    private String status;
    public PreOrder() {
    }
    public PreOrder(String id) {
        this.id = id;
    }
    public PreOrder(String id, Sessions sessions, String preorderDate, String groupName, String applicantName, String applicantPhone, String applicantEmail, Integer groupNum) {
        this.id = id;
        this.sessions = sessions;
        this.preorderDate = preorderDate;
        this.groupName = groupName;
        this.applicantName = applicantName;
        this.applicantPhone = applicantPhone;
        this.applicantEmail = applicantEmail;
        this.groupNum = groupNum;
    }

    public PreOrder(String id, Sessions sessions, String preorderDate, String groupName, String applicantName, String applicantPhone, String applicantEmail, Integer groupNum, String status) {
        this.id = id;
        this.sessions = sessions;
        this.preorderDate = preorderDate;
        this.groupName = groupName;
        this.applicantName = applicantName;
        this.applicantPhone = applicantPhone;
        this.applicantEmail = applicantEmail;
        this.groupNum = groupNum;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sessions getSessions() {
        return sessions;
    }

    public void setSessions(Sessions sessions) {
        this.sessions = sessions;
    }

    public String getPreorderDate() {
        return preorderDate;
    }

    public void setPreorderDate(String preorderDate) {
        this.preorderDate = preorderDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
