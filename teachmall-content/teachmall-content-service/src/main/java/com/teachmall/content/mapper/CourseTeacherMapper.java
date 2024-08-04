package com.teachmall.content.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teachmall.content.model.po.CourseTeacher;

import java.util.List;


public interface CourseTeacherMapper extends BaseMapper<CourseTeacher> {

    List<CourseTeacher> getTeacherInfo(Long courseid);
    void deleteByCourseId(Long courseid);
}
