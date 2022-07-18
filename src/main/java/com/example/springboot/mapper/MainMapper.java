package com.example.springboot.mapper;


import com.example.springboot.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MainMapper {

    @Select("select * from user where username = #{username}")
    User findUserByName(String username);


}
