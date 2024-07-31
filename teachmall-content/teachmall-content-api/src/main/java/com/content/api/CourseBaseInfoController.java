package com.content.api;

import com.content.api.dto.QueryCourseParamsDto;
import com.content.api.po.CourseBase;
import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseBaseInfoController {
    @RequestMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){
        return null;
    }

}
