package com.yjf.entity;

/**
 * @author 余俊锋
 * @date 2020/10/26 10:35
 * @Description
 */
public class QualificationCondition {
    private String startDate;
    private String endDate;
    private String type;
    private String check;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
