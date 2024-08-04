package com.teachmall.content.api;

import com.teachmall.content.model.dto.AddCourseDto;
import com.teachmall.content.model.dto.CourseBaseInfoDto;
import com.teachmall.content.model.dto.CourseTeacherInfoDto;
import com.teachmall.content.model.dto.QueryCourseParamsDto;
import com.teachmall.content.model.po.CourseBase;
import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import com.teachmall.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "课程相关接口")
@RestController
@AllArgsConstructor
public class CourseBaseInfoController {
    private final CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("查询课程列表")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParamsDto);
        return courseBasePageResult;
    }
    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated AddCourseDto addCourseDto){
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);

    }

    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }
    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated CourseBaseInfoDto courseBaseInfoDto){
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,courseBaseInfoDto);

    }
    @ApiOperation("删除课程基础信息")
    @DeleteMapping("/course/{courseid}")
    public void deleteCourse(@PathVariable Long courseid){
        courseBaseInfoService.deleteCourse(courseid);
    }

}
