package com.wenqicode.community.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wenqi Liang
 * @date 2020/9/27
 */
@Data
public class GithubUser implements Serializable {

    private static final long serialVersionUID = 1;

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

}
