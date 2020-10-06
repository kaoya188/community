package com.wenqicode.community.dto;

import com.wenqicode.community.model.User;
import lombok.Data;

/**
 * @author Wenqi Liang
 * @date 2020/10/1
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
