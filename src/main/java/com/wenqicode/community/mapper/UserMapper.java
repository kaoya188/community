package com.wenqicode.community.mapper;

import com.wenqicode.community.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @author Wenqi Liang
 * @date 2020/9/27
 */
@Mapper
public interface UserMapper {

    /**
     * 插入数据
     *
     * @param user
     */
    @Insert("insert into user (NAME, ACCOUNT_ID, TOKEN, GMT_CREATE, GMT_MODIFIED, AVATAR_URL) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl} )")
    void insert(User user);

    /**
     * 根据token来查询用户
     *
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("Select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("Select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name}, token = #{token}, AVATAR_URL = #{avatarUrl}, GMT_MODIFIED = #{gmtModified}")
    void update(User dbUser);
}
