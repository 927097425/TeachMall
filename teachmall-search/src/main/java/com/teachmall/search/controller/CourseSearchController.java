package com.teachmall.search.controller;

import com.teachmall.base.model.PageParams;
import com.teachmall.search.dto.SearchCourseParamDto;
import com.teachmall.search.dto.SearchPageResultDto;
import com.teachmall.search.po.CoursePublish;
import com.teachmall.search.service.CourseSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "课程搜索接口",tags = "课程搜索接口")
 @RestController
 @RequestMapping("/course")
public class CourseSearchController {

 @Autowired
 CourseSearchService courseSearchService;


 @ApiOperation("课程搜索列表")
  @GetMapping("/list")
 public SearchPageResultDto<CoursePublish> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto){
     SearchPageResultDto<CoursePublish> courseBasePageResult = courseSearchService.queryCoursePublish(pageParams,searchCourseParamDto);
      return courseBasePageResult;
  }
}
