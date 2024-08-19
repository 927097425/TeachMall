package com.teachmall.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teachmall.base.model.PageParams;
import com.teachmall.search.dto.SearchCourseParamDto;
import com.teachmall.search.dto.SearchPageResultDto;
import com.teachmall.search.mapper.CoursePublishMapper;
import com.teachmall.search.po.CoursePublish;
import com.teachmall.search.service.CourseSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CourseSearchServiceImpl implements CourseSearchService {
    @Autowired
    CoursePublishMapper coursePublishMapper;



//    PageResult<CourseBase> pageResult = new PageResult<>(selectResult.getRecords(),selectResult.getTotal(),selectResult.getPages(),selectResult.getSize());
//        return pageResult;
    @Override
    public SearchPageResultDto<CoursePublish> queryCoursePublish(PageParams pageParams, SearchCourseParamDto searchCourseParamDto) {
        LambdaQueryWrapper<CoursePublish> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper
                .like(StringUtils.isNotEmpty(searchCourseParamDto.getKeywords()),CoursePublish::getName,searchCourseParamDto.getKeywords())
                .eq(StringUtils.isNotEmpty(searchCourseParamDto.getMt()),CoursePublish::getMt,searchCourseParamDto.getMt())
                .eq(StringUtils.isNotEmpty(searchCourseParamDto.getSt()),CoursePublish::getSt,searchCourseParamDto.getSt());
        Page<CoursePublish> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        Page<CoursePublish> selectResult = coursePublishMapper.selectPage(page,new QueryWrapper<>());
        SearchPageResultDto<CoursePublish> result = new SearchPageResultDto<>(selectResult.getRecords(),selectResult.getTotal(),selectResult.getPages(),selectResult.getSize());

        return result;
    }
}
