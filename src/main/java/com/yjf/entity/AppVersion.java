package com.yjf.entity;


import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "app_version")
public class AppVersion {
    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "id")
    //插入操作自动注入id
    private Integer id;

    private Integer platform;

    private String versionNo;

    private Integer forceUpdate;

    private String downPath;

    private Float size;

    private String appExplain;

    private String createDate;

    private String updateDate;

    private String delFlag;

    private String createBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getAppExplain() {
        return appExplain;
    }

    public void setAppExplain(String appExplain) {
        this.appExplain = appExplain;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "id=" + id +
                ", platform=" + platform +
                ", versionNo='" + versionNo + '\'' +
                ", forceUpdate=" + forceUpdate +
                ", downPath='" + downPath + '\'' +
                ", size=" + size +
                ", appExplain='" + appExplain + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}