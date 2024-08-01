package com.teachmall.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import com.teachmall.content.mapper.CourseBaseMapper;
import com.teachmall.content.model.dto.QueryCourseParamsDto;
import com.teachmall.content.model.po.CourseBase;
import com.teachmall.content.service.CourseBaseInfoService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: 陈靖国
 * @CreateTime: 2024-08-01
 * @Description: 课程管理业务的实现类
 * @Version: 1.0
 */

@Service
@AllArgsConstructor
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    private final CourseBaseMapper courseBaseMapper;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName())
                .eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus())
                .eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());

        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        Page<CourseBase> selectResult = courseBaseMapper.selectPage(page,queryWrapper);
        PageResult<CourseBase> pageResult = new PageResult<>(selectResult.getRecords(),selectResult.getTotal(),selectResult.getPages(),selectResult.getSize());
        return pageResult;
    }
}
