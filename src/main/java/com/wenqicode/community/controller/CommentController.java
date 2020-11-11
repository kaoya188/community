package com.wenqicode.community.controller;

import com.wenqicode.community.dto.CommentCreateDTO;
import com.wenqicode.community.dto.CommentDTO;
import com.wenqicode.community.dto.ResultDTO;
import com.wenqicode.community.enums.CommentTypeEnum;
import com.wenqicode.community.exception.CustomizeErrorCode;
import com.wenqicode.community.mapper.CommentMapper;
import com.wenqicode.community.model.Comment;
import com.wenqicode.community.model.User;
import com.wenqicode.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 评论
 *
 * @author Wenqi Liang
 * @date 2020/10/6
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request) {
        // 验证登录用户
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        // 评论内容不能为null
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setCommentator(1L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentService.insert(comment, user);

        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable("id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }





}
