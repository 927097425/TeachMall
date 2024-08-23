package com.teachmall.generator.impl;

import com.teachmall.generator.CourseCategoryService;
import com.teachmall.generator.model.po.CourseCategory;
import com.teachmall.generator.mapper.CourseCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程分类 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

}
