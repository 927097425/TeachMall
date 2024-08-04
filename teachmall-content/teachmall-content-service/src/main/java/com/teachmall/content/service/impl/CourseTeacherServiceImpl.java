package com.teachmall.content.service.impl;

import com.teachmall.content.mapper.CourseTeacherMapper;
import com.teachmall.content.model.po.CourseTeacher;
import com.teachmall.content.service.CourseTeacherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description: 实现类
 * @Version: 1.0
 */
@AllArgsConstructor
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    private final CourseTeacherMapper courseTeacherMapper;
    @Override
    public List<CourseTeacher> getTeacherInfo(Long courseid) {
        List<CourseTeacher> teacherInfo = courseTeacherMapper.getTeacherInfo(courseid);
        return null;
    }
}
