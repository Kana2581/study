package com.example.demo.mapper;

import com.example.demo.entity.Account;
import com.example.demo.entity.SimpleUserInformation;
import com.example.demo.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
//    @Select("select * from user_information where uid =(select uid from account where account=)")
//    public Account findUserInformationByAccount(@Param("account") String account);
    @Select("select uid,name,portrait from user_information where uid =(select uid from account where account=#{account})")
    public SimpleUserInformation findSimpleUserInformationByAccount(@Param("account") String account);
    @Select("select * from user_information where uid =#{uid}")
    public UserInformation findUserInformationByUid(@Param("uid") int uid);
    @Update("update user_information set portrait=#{portrait} where uid=#{uid}")
    public boolean editPortrait(@Param("uid") int uid,@Param("portrait") String portrait);
    @Update("update user_information set name=#{name},birthday=#{birthday},phone=#{phone},address=#{address},profile=#{profile} where uid=#{uid}")
    public boolean editUserInformationExcludePortrait(UserInformation userInformation);
}
