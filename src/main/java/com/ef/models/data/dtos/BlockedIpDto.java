package com.ef.models.data.dtos;

public class BlockedIpDto {

    private String ip;
    private String message;

    public BlockedIpDto(String ip, String message){
        this.ip = ip;
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s %s", ip, message);
    }
}
