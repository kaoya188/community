package com.wenqicode.community.dto;

import lombok.Data;

/**
 * 评论内容传输对象
 *
 * @author Wenqi Liang
 * @date 2020/10/6
 */
@Data
public class CommentCreateDTO {

    private Long parentId;
    private String content;
    private Integer type;

}
