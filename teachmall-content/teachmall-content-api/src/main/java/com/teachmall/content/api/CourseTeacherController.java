package com.teachmall.content.api;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teachmall.content.model.po.CourseTeacher;
import com.teachmall.content.service.CourseTeacherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description: 教师管理控制类接口
 * @Version: 1.0
 */


@RestController
@RequestMapping("/courseTeacher")
@AllArgsConstructor
public class CourseTeacherController {
    private final CourseTeacherService courseTeacherService;
    @GetMapping("/list/{courseid}")
    public List<CourseTeacher> getTeacherInfo(@PathVariable Long courseid){

        List<CourseTeacher> courseTeachers = courseTeacherService.getTeacherInfo(courseid);
    }
}
