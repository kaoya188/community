package com.wenqicode.community.mapper;

import com.wenqicode.community.model.Question;
import com.wenqicode.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    /**
     * 并发安全地修改阅读数, 具体需参看sql
     * @param record
     * @return
     */
    int incView(@Param("record") Question record);

    /**
     * 修改评论数
     * @param question
     * @return
     */
    int incComment(@Param("record")Question question);

}