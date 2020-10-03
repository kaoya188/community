package com.wenqicode.community.model;

import lombok.Data;

/**
 * @author Wenqi Liang
 * @date 2020/9/29
 */
@Data
public class Question {

    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
