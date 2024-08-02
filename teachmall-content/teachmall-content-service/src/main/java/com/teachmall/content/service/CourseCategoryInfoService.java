package com.teachmall.content.service;

import com.teachmall.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @Author: 陈靖国
 * @CreateTime: 2024-08-02
 * @Description: 课程分类查询服务层
 * @Version: 1.0
 */
public interface CourseCategoryInfoService {
    public List<CourseCategoryTreeDto> selectTreeNodes();
}
