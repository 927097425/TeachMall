package com.teachmall.content.api;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teachmall.content.model.dto.CourseTeacherInfoDto;
import com.teachmall.content.model.po.CourseCategory;
import com.teachmall.content.model.po.CourseTeacher;
import com.teachmall.content.service.CourseTeacherService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("查询教师")
    @GetMapping("/list/{courseid}")
    public List<CourseTeacher> getTeacherInfo(@PathVariable Long courseid){

        List<CourseTeacher> courseTeachers = courseTeacherService.getTeacherInfo(courseid);
        return courseTeachers;
    }
    @ApiOperation("添加教师")
    @PostMapping
    public CourseTeacher addTeacherInfo(@RequestBody CourseTeacherInfoDto courseTeacherInfoDto){
        CourseTeacher courseTeacher = courseTeacherService.addTeacherInfo(courseTeacherInfoDto);
        return courseTeacher;
    }
    @ApiOperation("修改教师")
    @PutMapping
    public CourseTeacher updateTeacherInfo(@RequestBody CourseTeacher courseTeacher){
        return courseTeacherService.updateTeacherInfo(courseTeacher);
    }
    @ApiOperation("删除教师")
    @DeleteMapping("/course/{courseid}/{teacherid}")
    public void deleteTeacherInfo(@PathVariable Long courseid,@PathVariable Long teacherid){
        courseTeacherService.deleteTeacherInfo(teacherid);
    }

}
