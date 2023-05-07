package com.example.demo.mapper;

import com.example.demo.entity.Account;
import org.apache.ibatis.annotations.*;

import java.sql.Date;

@Mapper
public interface AuthMapper {
    @Select("select * from account where account =#{account} or mail=#{account}")
    public Account findAccountByAccountOrMail(@Param("account") String account);

    @Select("select * from account where uid=#{uid}")
    public Account findAccountByUid(@Param("uid") String uid);
    @Select("select create_time,role from account where uid=#{uid}")
    public Account findCreateTimeByUid(@Param("uid") String uid);
    @Insert("insert into account(account,password,mail) value(#{account},#{password},#{mail})")
    public Boolean addAccount(@Param("account") String account,@Param("password") String password,@Param("mail") String mail);
    @Insert("insert into account(account,password,mail,role) value(#{account},#{password},#{mail},#{role})")
    public Boolean addAccountIncludeRole(@Param("account") String account,@Param("password") String password,@Param("mail") String mail,@Param("role") String role);

    @Update("update account set role=#{role} where uid=#{uid}")
    public Boolean editRoleByUId(@Param("uid")String uid,@Param("role")String role);

    @Update("update account set mail=#{mail} where uid=#{uid}")
    public Boolean editMailByUId(@Param("mail")String mail,@Param("uid")String uid);

    @Update("update account set account=#{account},password=#{password} where uid=#{uid}")
    public Boolean editAccountByUidUserVersion(@Param("account") String account,@Param("password") String password,@Param("uid")String uid);


    @Update("update account set account=#{account},password=#{password},mail=#{mail},role=#{role} where uid=#{uid}")
    public Boolean editAccountByUId(@Param("account") String account,@Param("password") String password,@Param("mail") String mail,@Param("role") String role,@Param("uid")String uid);

    @Delete("delete from account where uid=#{uid}")
    public Boolean dropAccountByUid(@Param("uid")String uid);
}
