package com.wenqicode.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Wenqi Liang
 * @date 2020/10/20
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
