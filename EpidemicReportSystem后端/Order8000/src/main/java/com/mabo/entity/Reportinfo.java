package com.mabo.entity;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;

/**
 * (Reportinfo)实体类
 *
 * @author makejava
 * @since 2022-06-29 22:54:47
 */


public class Reportinfo implements Serializable {
    private static final long serialVersionUID = -28417384535687720L;

    private String reportdate;

    private String realreportdate;

    private String bodytemperature;

    private String isconfirm;

    private String isarea;

    private String address;

    private String uameid;

    public Reportinfo(String reportdate, String realreportdate, String bodytemperature, String isconfirm, String isarea, String address, String uameid) {
        this.reportdate = reportdate;
        this.realreportdate = realreportdate;
        this.bodytemperature = bodytemperature;
        this.isconfirm = isconfirm;
        this.isarea = isarea;
        this.address = address;
        this.uameid = uameid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getRealreportdate() {
        return realreportdate;
    }

    public void setRealreportdate(String realreportdate) {
        this.realreportdate = realreportdate;
    }

    public String getBodytemperature() {
        return bodytemperature;
    }

    public void setBodytemperature(String bodytemperature) {
        this.bodytemperature = bodytemperature;
    }

    public String getIsconfirm() {
        return isconfirm;
    }

    public void setIsconfirm(String isconfirm) {
        this.isconfirm = isconfirm;
    }

    public String getIsarea() {
        return isarea;
    }

    public void setIsarea(String isarea) {
        this.isarea = isarea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUameid() {
        return uameid;
    }

    public void setUameid(String uameid) {
        this.uameid = uameid;
    }

    @Override
    public String toString() {
        return
                " 用户名:" + uameid+
                "<br>上报日期:" + realreportdate +
                "<br> 体温:" + bodytemperature +
                "<br> 是否确诊:" + isconfirm +
                "<br> 是否中高风险地区:" + isarea +
                "<br> 所在地=" + address;
    }
}

