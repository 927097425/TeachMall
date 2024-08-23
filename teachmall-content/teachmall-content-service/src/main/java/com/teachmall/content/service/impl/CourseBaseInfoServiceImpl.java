package com.teachmall.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teachmall.base.exception.TeachmallException;
import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import com.teachmall.content.mapper.*;
import com.teachmall.content.model.dto.AddCourseDto;
import com.teachmall.content.model.dto.CourseBaseInfoDto;
import com.teachmall.content.model.dto.QueryCourseParamsDto;
import com.teachmall.content.model.po.CourseBase;
import com.teachmall.content.model.po.CourseCategory;
import com.teachmall.content.model.po.CourseMarket;
import com.teachmall.content.service.CourseBaseInfoService;
import com.teachmall.content.service.CourseTeacherService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: 陈靖国
 * @Description: 课程管理业务的实现类
 * @Version: 1.0
 */

@Service
@AllArgsConstructor
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    private final CourseBaseMapper courseBaseMapper;
    private final CourseMarketMapper courseMarketMapper;
    private final CourseCategoryMapper courseCategoryMapper;
    private final CourseTeacherMapper courseTeacherMapper;
    private final TeachplanMapper teachplanMapper;
    private final TeachplanMediaMapper teachplanMediaMapper;

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

    @Override
    @Transactional

    public CourseBaseInfoDto createCourseBase(Long companyId,AddCourseDto dto) {

        //合法性校验
        if (StringUtils.isBlank(dto.getName())) {
            throw new TeachmallException("课程名称为空");
        }

        if (StringUtils.isBlank(dto.getMt())) {
            throw new TeachmallException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getSt())) {
            throw new TeachmallException("课程分类为空");
        }

        if (StringUtils.isBlank(dto.getGrade())) {
            throw new TeachmallException("课程等级为空");
        }

        if (StringUtils.isBlank(dto.getTeachmode())) {
            throw new TeachmallException("教育模式为空");
        }

        if (StringUtils.isBlank(dto.getUsers())) {
            throw new TeachmallException("适应人群为空");
        }

        if (StringUtils.isBlank(dto.getCharge())) {
            throw new TeachmallException("收费规则为空");
        }
        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto,courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //设置发布状态
        courseBaseNew.setStatus("203001");
        //机构id
        courseBaseNew.setCompanyId(companyId);
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);
        if(insert<=0){
            throw new RuntimeException("新增课程基本信息失败");
        }
        //向课程营销表保存课程营销信息
        //课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        Long courseId = courseBaseNew.getId();
        BeanUtils.copyProperties(dto,courseMarketNew);
        courseMarketNew.setId(courseId);
        int i = saveCourseMarket(courseMarketNew);
        if(i<=0){
            throw new RuntimeException("保存课程营销信息失败");
        }
        //查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(courseId);

    }
    //保存课程营销信息
    private int saveCourseMarket(CourseMarket courseMarketNew){
        //收费规则
        String charge = courseMarketNew.getCharge();
        if(StringUtils.isBlank(charge)){
            throw new TeachmallException("收费规则没有选择");
        }
        //收费规则为收费
        if(charge.equals("201001")){
            if(courseMarketNew.getPrice() == null || courseMarketNew.getPrice().floatValue()<=0){
                throw new TeachmallException("课程的价格不能为空并且必须大于0");
            }
        }
        //根据id从课程营销表查询
        CourseMarket courseMarketObj = courseMarketMapper.selectById(courseMarketNew.getId());
        if(courseMarketObj == null){
            return courseMarketMapper.insert(courseMarketNew);
        }else{
            BeanUtils.copyProperties(courseMarketNew,courseMarketObj);
            courseMarketObj.setId(courseMarketNew.getId());
            return courseMarketMapper.updateById(courseMarketObj);
        }
    }
    //根据课程id查询课程基本信息，包括基本信息和营销信息
    public CourseBaseInfoDto getCourseBaseInfo(long courseId){

        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;


    }
    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, CourseBaseInfoDto dto) {

        //课程id
        Long courseId = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if(courseBase==null){
            TeachmallException.cast("课程不存在");
        }

        //校验本机构只能修改本机构的课程
        if(!courseBase.getCompanyId().equals(companyId)){
            TeachmallException.cast("本机构只能修改本机构的课程");
        }
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());

        BeanUtils.copyProperties(dto,courseMarket);
        int i = courseBaseMapper.updateById(courseBase);

        saveCourseMarket(courseMarket);

        //查询课程信息
        CourseBaseInfoDto courseBaseInfo = this.getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }
    @Transactional
    @Override
    public void deleteCourse(Long courseid) {

//        删除课程相关的基本信息
        courseBaseMapper.deleteById(courseid);
//        营销信息
        courseMarketMapper.deleteById(courseid);
//        课程计划
        teachplanMediaMapper.deleteByCourseId(courseid);
        teachplanMapper.deleteByCourseId(courseid);
//        课程教师信息
        courseTeacherMapper.deleteByCourseId(courseid);
    }

}
