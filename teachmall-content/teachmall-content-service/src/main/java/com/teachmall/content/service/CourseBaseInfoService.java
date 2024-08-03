package com.teachmall.content.service;

import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import com.teachmall.content.model.dto.AddCourseDto;
import com.teachmall.content.model.dto.CourseBaseInfoDto;
import com.teachmall.content.model.dto.QueryCourseParamsDto;
import com.teachmall.content.model.po.CourseBase;

/**
 * @Author: 陈靖国
 * @CreateTime: 2024-08-01
 * @Description: 查询课程信息的接口
 * @Version: 1.0
 */
public interface CourseBaseInfoService {
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
     CourseBaseInfoDto getCourseBaseInfo(long courseId);
     CourseBaseInfoDto updateCourseBase(Long companyId, CourseBaseInfoDto dto);
}
