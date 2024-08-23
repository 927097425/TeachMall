package com.teachmall.content.service;

import com.teachmall.content.model.dto.CourseTeacherInfoDto;
import com.teachmall.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description:
 * @Version: 1.0
 */
public interface CourseTeacherService {
    List<CourseTeacher> getTeacherInfo(Long courseid);

    CourseTeacher addTeacherInfo(CourseTeacherInfoDto courseTeacherInfoDto);

    CourseTeacher updateTeacherInfo(CourseTeacher courseTeacher);

    void deleteTeacherInfo(Long teacherid);
}
