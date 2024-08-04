package com.teachmall.content.service;

import com.teachmall.content.model.po.CourseTeacher;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description:
 * @Version: 1.0
 */
public interface CourseTeacherService {
    List<CourseTeacher> getTeacherInfo(Long courseid);
}
