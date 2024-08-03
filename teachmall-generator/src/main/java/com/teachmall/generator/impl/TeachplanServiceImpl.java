package com.teachmall.generator.impl;

import com.teachmall.generator.TeachplanService;
import com.teachmall.generator.model.po.Teachplan;
import com.teachmall.generator.mapper.TeachplanMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程计划 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {

}
