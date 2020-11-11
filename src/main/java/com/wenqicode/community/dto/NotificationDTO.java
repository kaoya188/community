package com.wenqicode.community.dto;

import com.wenqicode.community.model.User;
import lombok.Data;

/**
 * 通知DTO
 *
 * @author Wenqi Liang
 * @date 2020/10/27
 */
@Data
public class NotificationDTO {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    /**
     * 通知者, 回复问题的人
     */
    private String notifierName;
    /**
     * 被回复的标题
     */
    private String outerTitle;
    private String type;
}
