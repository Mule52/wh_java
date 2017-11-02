package com.ef.models.data.dtos;

import java.sql.Timestamp;

public final class HttpLogDto {

    private int id;
    private Timestamp createdOn;
    private String ip;
    private String httpMethod;
    private String httpStatusCode;
    private String userAgent;

    @Override
    public String toString() {
        return String.format("%d, %s", id, ip);
    }

    @Override
    public boolean equals(Object inputObject) {
        boolean isEqual = false;

        // Check if both objects are same
        if (this == inputObject) {
            isEqual = true;
        } else if (inputObject != null && getClass() == inputObject.getClass()) {
            final HttpLogDto inputStudent = (HttpLogDto) inputObject;
            if (this.getId() == inputStudent.getId()) {
                isEqual = true;
            }
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public HttpLogDto() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
