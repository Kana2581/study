package com.example.demo.mapper;

import com.example.demo.entity.IndexProjectInformation;
import com.example.demo.entity.SimpleAccountAndUserInformation;
import com.example.demo.entity.Video;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProjectMapper {
    @Insert("insert into project(title,cover,profile,status,uid) values(#{title},#{cover},#{profile},#{status},#{uid})")
    public boolean loadProject(@Param("title") String title, @Param("cover") String cover, @Param("profile") String profile,@Param("status") int status,@Param("uid") int uid);
    @Select("select project.pid,project.uid,title,cover,time,name,portrait,IFNULL(testamount, 0) as amount from project left join user_information on user_information.uid=project.uid left join (select course_history.pid,count(uid) as testamount from course_history group by pid) as derivedtable on derivedtable.pid=project.pid where project.title like #{search} order by amount desc,time desc limit 12 offset  #{offset};")
    public IndexProjectInformation[] findProjectsByPage(@Param("offset") int offset,@Param("search") String search);
//    @Select("select pid,project.uid,title,cover,time,amount,name,portrait from project left join user_information on user_information.uid=project.uid where project.title like #{search} order by amount desc,time desc limit 12 offset #{offset};")
//    public IndexProjectInformation[] findProjectsByPage(@Param("offset") int offset,@Param("search") String search);
    @Select("select project.pid,project.uid,project.profile,title,cover,time,IFNULL(testamount, 0) as amount,name,project.status,name,portrait from project left join user_information on user_information.uid=project.uid left join (select course_history.pid,count(uid) as testamount from course_history group by pid) as derivedtable on derivedtable.pid=project.pid where project.pid=#{pid}")
    public IndexProjectInformation findProjectByPid(@Param("pid") int pid);
    @Select("select project.pid,uid,title,profile,cover,status,time,IFNULL(testamount, 0) as amount from project  left join (select course_history.pid,count(uid) as testamount from course_history group by pid) as derivedtable on derivedtable.pid=project.pid where uid=#{uid}")
    public IndexProjectInformation[] findProjectsByUid(@Param("uid") int uid);

    @Select("select course_history.pid,name,course_history.uid,last_time as time,title,project.profile,cover,portrait,0 as amount,0 as status from course_history left join project on project.pid=course_history.pid left join user_information on user_information.uid=project.uid where course_history.uid=#{uid} order by last_time desc")
    public IndexProjectInformation[] findCoursesHistoryByUid(@Param("uid") int uid);
    @Select("select count(pid) from project")
    public int countProjects();
    @Select("select uid from project where pid=#{pid}")
    public int findUidByPid(@Param("pid")int pid);

    @Select("select project.pid,project.uid,project.profile,title,cover,time,IFNULL(testamount, 0) as amount,name,project.status,name,portrait from project left join user_information on user_information.uid=project.uid left join (select course_history.pid,count(uid) as testamount from course_history group by pid) as derivedtable on derivedtable.pid=project.pid where project.pid in (select pid from course_history where uid=#{uid})")
    public int findStudentCoursesByUid(@Param("uid")int uid);

    @Select("select account.uid,account,portrait,name,role,birthday,phone,address,mail,create_time from account,user_information where account.uid=user_information.uid;")
    public SimpleAccountAndUserInformation[] findSimpleAccountAndUserInformation();
    @Delete("delete from project where pid=#{pid} and uid=#{uid}")
    public boolean dropProjectByPid(@Param("pid") int pid,@Param("uid") String uid);
    @Update("update project set cover=#{cover} where pid=#{pid} and uid=#{uid}")
    public boolean editCover(@Param("cover") String cover,@Param("pid") int pid,@Param("uid") String uid);
    @Update("update project set title=#{title},profile=#{profile},status=#{status} where pid=#{pid} and uid=#{uid}")
    public boolean editProjectByTitleAndProfile(@Param("title") String title, @Param("profile") String profile,@Param("status") int status,@Param("pid") int pid,@Param("uid") String uid);

}
