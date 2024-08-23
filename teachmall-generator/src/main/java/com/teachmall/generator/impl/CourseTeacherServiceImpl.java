package com.teachmall.generator.impl;

import com.teachmall.generator.CourseTeacherService;
import com.teachmall.generator.model.po.CourseTeacher;
import com.teachmall.generator.mapper.CourseTeacherMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程-教师关系表 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {

}
