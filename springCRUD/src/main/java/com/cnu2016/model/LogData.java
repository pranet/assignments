package com.cnu2016.model;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by pranet on 11/07/16.
 */
public class LogData {
    private DateTime timeStamp;
    private String ipAddress;
    private String url;
    private String parametersString;
    private int id;
    private Integer responseCode;
    private String requestType;
    private Long requestTime;
    public LogData(DateTime timeStamp, String ipAddress, String url, String parametersString, Integer responseCode, String requestType, Long requestTime) {
        this.timeStamp = timeStamp;
        this.ipAddress = ipAddress;
        this.url = url;
        this.parametersString = parametersString;
        this.responseCode = responseCode;
        this.requestTime = requestTime;
        this.requestType = requestType;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public DateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(DateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParametersString() {
        return parametersString;
    }

    public void setParametersString(String parametersString) {
        this.parametersString = parametersString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
