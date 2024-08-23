package com.teachmall.content.service.impl;

import com.teachmall.base.exception.TeachmallException;
import com.teachmall.content.mapper.CourseTeacherMapper;
import com.teachmall.content.model.dto.CourseTeacherInfoDto;
import com.teachmall.content.model.po.CourseTeacher;
import com.teachmall.content.service.CourseTeacherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return teacherInfo;
    }

    @Override
    public CourseTeacher addTeacherInfo(CourseTeacherInfoDto courseTeacherInfoDto) {
        CourseTeacher courseTeacher = new CourseTeacher();
        BeanUtils.copyProperties(courseTeacherInfoDto,courseTeacher);
        courseTeacher.setCreateDate(LocalDateTime.now());
        courseTeacherMapper.insert(courseTeacher);
        return courseTeacher;
    }

    @Override
    public CourseTeacher updateTeacherInfo(CourseTeacher courseTeacher) {
        int i = courseTeacherMapper.updateById(courseTeacher);
        if(i<0) throw new TeachmallException("修改教师数据失败");
        return courseTeacher;
    }

    @Override
    public void deleteTeacherInfo(Long teacherid) {
        int i = courseTeacherMapper.deleteById(teacherid);
        if(i<0) throw new TeachmallException("删除失败");

    }

}
