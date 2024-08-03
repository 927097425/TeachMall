package com.teachmall.content.service.impl;

import com.teachmall.content.mapper.TeachplanMapper;
import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.service.TeachPlanService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Arg;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description: 教学计划实现类
 * @Version: 1.0
 */

@Service
@AllArgsConstructor
public class TeachPlanServiceImpl implements TeachPlanService {
    private final TeachplanMapper teachplanMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }
}
