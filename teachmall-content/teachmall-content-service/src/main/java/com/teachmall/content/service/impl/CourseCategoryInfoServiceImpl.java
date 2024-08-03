package com.teachmall.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teachmall.content.mapper.CourseCategoryMapper;
import com.teachmall.content.model.dto.CourseCategoryTreeDto;
import com.teachmall.content.model.po.CourseCategory;
import com.teachmall.content.service.CourseCategoryInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 陈靖国
 * @Description: 课程分类服务实现类
 * @Version: 1.0
 */

@Service
@AllArgsConstructor
public class CourseCategoryInfoServiceImpl implements CourseCategoryInfoService {
    public final CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> selectTreeNodes() {
        List<CourseCategory> courseCategories = courseCategoryMapper.selectList(new QueryWrapper<>());
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = new ArrayList<>();
        for(CourseCategory courseCategory : courseCategories){

            List<CourseCategory> children = courseCategories.stream().filter(e->e.getParentid().equals(courseCategory.getId())).collect(Collectors.toList());
            if(courseCategory.getId().equals("1")||children.size()==0) continue;
            CourseCategoryTreeDto courseCategoryTreeDto = new CourseCategoryTreeDto(courseCategory,children);
            courseCategoryTreeDtos.add(courseCategoryTreeDto);

        }
        return courseCategoryTreeDtos;
    }
}
