package com.wenqicode.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wenqi Liang
 * @date 2020/10/2
 */
@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showNext;
    private Boolean showFirstPage;
    private Boolean showEndPage;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();


    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage = totalPage;
        this.page = page;


        // 页码数设置
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }

        }

        // 2. 是否展示上一页的情况
        if (page == 1) {
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        // 3. 是否展示下一页的情 况
        if (page == totalPage) {
            showNext = false;
        }else {
            showNext = true;
        }

        // 4. 是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        // 4. 是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
