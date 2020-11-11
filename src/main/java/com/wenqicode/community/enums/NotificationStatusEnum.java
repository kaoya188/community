package com.wenqicode.community.enums;

/**
 * @author Wenqi Liang
 * @date 2020/10/26
 */
public enum NotificationStatusEnum {

    UNREAD(0, "未读消息"),
    READ(1, "已读消息"),
    ;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    private int status;
    private String name;

    NotificationStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

}
