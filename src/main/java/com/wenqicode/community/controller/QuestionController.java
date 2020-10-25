package com.wenqicode.community.controller;

import com.wenqicode.community.dto.CommentDTO;
import com.wenqicode.community.dto.QuestionDTO;
import com.wenqicode.community.enums.CommentTypeEnum;
import com.wenqicode.community.service.CommentService;
import com.wenqicode.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Wenqi Liang
 * @date 2020/10/3
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model) {
        // 获取文章内容
        QuestionDTO questionDTO = questionService.getById(id);
        //
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        // 获取文章评论数
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        // 累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

}
