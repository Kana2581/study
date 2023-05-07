package com.example.demo.mapper;


import com.example.demo.entity.Pdf;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PdfMapper {
    @Insert("insert into pdf(pid,pdf,title) value(#{pid},#{pdf},#{title})")
    public Boolean addPdf(@Param("pid") int pid, @Param("pdf") String pdf,@Param("title")String title);
    @Select("select cwid,title,pdf,time from pdf where pid=#{pid}")
    public Pdf[] findPdfsByPid(@Param("pid") int pid);
}
