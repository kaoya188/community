package com.wenqicode.community.controller;

import com.wenqicode.community.dto.PaginationDTO;
import com.wenqicode.community.dto.QuestionDTO;
import com.wenqicode.community.mapper.QuestionMapper;
import com.wenqicode.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Wenqi Liang
 * @date 2020/10/3
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }

}