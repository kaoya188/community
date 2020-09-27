package com.wenqicode.community.mapper;

import com.wenqicode.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Wenqi Liang
 * @date 2020/9/27
 */
@Mapper
public interface UserMapper {

    /**
     * 插入数据
     * @param user
     */
    @Insert("insert into user (NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFIED) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);

    /**
     * 根据token来查询用户
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
