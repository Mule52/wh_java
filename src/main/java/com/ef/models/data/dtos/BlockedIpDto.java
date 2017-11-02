package com.ef.models.data.dtos;

public class BlockedIpDto {

    private String ip;
    private String message;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s %s", ip, message);
    }
}
