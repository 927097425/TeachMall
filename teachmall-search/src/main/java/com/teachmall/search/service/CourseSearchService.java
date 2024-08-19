package com.teachmall.search.service;

import com.teachmall.base.model.PageParams;
import com.teachmall.search.dto.SearchCourseParamDto;
import com.teachmall.search.dto.SearchPageResultDto;
import com.teachmall.search.po.CoursePublish;


public interface CourseSearchService {




    SearchPageResultDto<CoursePublish> queryCoursePublish(PageParams pageParams, SearchCourseParamDto searchCourseParamDto);
}
