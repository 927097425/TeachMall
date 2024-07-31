package com.teachmall.content.api;

import com.teachmall.content.api.dto.QueryCourseParamsDto;
import com.teachmall.content.api.po.CourseBase;
import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "课程相关接口")
@RestController
public class CourseBaseInfoController {
    @ApiOperation("查询课程列表")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){
        return null;
    }

}
