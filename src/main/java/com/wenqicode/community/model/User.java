package com.wenqicode.community.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 接收数据库对象
 *
 * @author Wenqi Liang
 * @date 2020/9/27
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1;

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
