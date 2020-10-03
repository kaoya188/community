package com.wenqicode.community.mapper;

import com.wenqicode.community.dto.QuestionDTO;
import com.wenqicode.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Wenqi Liang
 * @date 2020/9/29
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)" +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("SELECT * from question limit #{offset}, #{size}")
    List<Question> list(@Param("size") Integer size, @Param("offset") Integer offset);

    @Select("select count(1) from QUESTION")
    Integer countAll();

    @Select("SELECT * from question where creator = #{userId} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param("userId") Integer userId, @Param("size") Integer size, @Param("offset") Integer offset);

    @Select("select count(1) from QUESTION where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("SELECT * from question where id = #{id}")
    Question getById(@Param("id") Integer id);

    @Update("update question set title = #{title}, description = #{description}, tag = #{tag}, GMT_MODIFIED = #{gmtModified} where id = #{id}")
    void update(Question question);
}
