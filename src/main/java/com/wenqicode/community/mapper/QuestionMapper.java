package com.wenqicode.community.mapper;

import com.wenqicode.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
