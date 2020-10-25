package com.wenqicode.community.dto;

import com.wenqicode.community.model.User;
import lombok.Data;

/**
 * @author Wenqi Liang
 * @date 2020/10/7
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
