package com.wenqicode.community.mapper;

import com.wenqicode.community.model.Comment;
import com.wenqicode.community.model.Question;
import org.apache.ibatis.annotations.Param;

public interface CommentExtMapper {
    /**
     * 并发安全地修改阅读数, 具体需参看sql
     * @return
     */
    int incCommentCount(@Param("comment") Comment comment);

}