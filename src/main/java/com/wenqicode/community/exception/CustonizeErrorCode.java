package com.wenqicode.community.exception;

public enum CustonizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("你所找的问题不在了, 要不要换个试试?");

    private String message;

    CustonizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
