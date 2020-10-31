package com.yjf.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "work_order")
public class WorkOrder {
    @Id
    private Integer id;

    private String code;

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "transport_user_id")
    private Integer transportUserId;

    @Column(name = "recipient_user_id")
    private Integer recipientUserId;

    /**
     * 0：待运输            1：运输中            2：验收
     */
    private Integer status;

    /**
     * 数据创建时间,在数据新增时设置
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 数据修改时间,在数据新增时和修改时设置
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    @Column(name = "del_flag")
    private String delFlag;

    @Column(name = "create_by")
    private String createBy;

    @Transient
    private String createUserName;
    @Transient
    private String transportUserName;
    @Transient
    private String recipientUsername;
    @Transient
    private String createOfficeName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getTransportUserName() {
        return transportUserName;
    }

    public void setTransportUserName(String transportUserName) {
        this.transportUserName = transportUserName;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getCreateOfficeName() {
        return createOfficeName;
    }

    public void setCreateOfficeName(String createOfficeName) {
        this.createOfficeName = createOfficeName;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return create_user_id
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return transport_user_id
     */
    public Integer getTransportUserId() {
        return transportUserId;
    }

    /**
     * @param transportUserId
     */
    public void setTransportUserId(Integer transportUserId) {
        this.transportUserId = transportUserId;
    }

    /**
     * @return recipient_user_id
     */
    public Integer getRecipientUserId() {
        return recipientUserId;
    }

    /**
     * @param recipientUserId
     */
    public void setRecipientUserId(Integer recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    /**
     * 获取0：待运输            1：运输中            2：验收
     *
     * @return status - 0：待运输            1：运输中            2：验收
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0：待运输            1：运输中            2：验收
     *
     * @param status 0：待运输            1：运输中            2：验收
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取数据创建时间,在数据新增时设置
     *
     * @return create_date - 数据创建时间,在数据新增时设置
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置数据创建时间,在数据新增时设置
     *
     * @param createDate 数据创建时间,在数据新增时设置
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取数据修改时间,在数据新增时和修改时设置
     *
     * @return update_date - 数据修改时间,在数据新增时和修改时设置
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置数据修改时间,在数据新增时和修改时设置
     *
     * @param updateDate 数据修改时间,在数据新增时和修改时设置
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取删除标记（0：正常；1：删除；2：审核；）
     *
     * @return del_flag - 删除标记（0：正常；1：删除；2：审核；）
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记（0：正常；1：删除；2：审核；）
     *
     * @param delFlag 删除标记（0：正常；1：删除；2：审核；）
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}