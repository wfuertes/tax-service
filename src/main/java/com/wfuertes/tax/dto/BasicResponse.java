package com.wfuertes.tax.dto;

public class BasicResponse {

    private int status;
    private String message;

    public BasicResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
