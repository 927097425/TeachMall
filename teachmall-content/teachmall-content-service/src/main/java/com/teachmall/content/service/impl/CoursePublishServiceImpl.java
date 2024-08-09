package com.teachmall.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.teachmall.base.exception.TeachmallException;
import com.teachmall.base.utils.StringUtil;
import com.teachmall.content.mapper.CourseBaseMapper;
import com.teachmall.content.mapper.CourseMarketMapper;
import com.teachmall.content.mapper.CoursePublishPreMapper;
import com.teachmall.content.model.dto.CourseBaseInfoDto;
import com.teachmall.content.model.dto.CoursePreviewDto;
import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.model.po.CourseBase;
import com.teachmall.content.model.po.CourseMarket;
import com.teachmall.content.model.po.CoursePublishPre;
import com.teachmall.content.service.CourseBaseInfoService;
import com.teachmall.content.service.CoursePublishService;
import com.teachmall.content.service.TeachPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CoursePublishServiceImpl implements CoursePublishService {

 @Autowired
 CourseBaseInfoService courseBaseInfoService;

 @Autowired
 TeachPlanService teachplanService;
 @Autowired
 CourseBaseMapper courseBaseMapper;
 @Autowired
 CoursePublishPreMapper coursePublishPreMapper;
 @Autowired
 CourseMarketMapper courseMarketMapper;
 @Override
 public CoursePreviewDto getCoursePreviewInfo(Long courseId) {

  //课程基本信息、营销信息
  CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);

  //课程计划信息
  List<TeachplanDto> teachplanTree= teachplanService.findTeachplanTree(courseId);

  CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
  coursePreviewDto.setCourseBase(courseBaseInfo);
  coursePreviewDto.setTeachplans(teachplanTree);
  return coursePreviewDto;
 }

 @Override
 public void commitAudit(Long companyId, Long courseId) {

  //约束校验
  CourseBase courseBase = courseBaseMapper.selectById(courseId);
  //课程审核状态
  String auditStatus = courseBase.getAuditStatus();
  //当前审核状态为已提交不允许再次提交
  if("202003".equals(auditStatus)){
   TeachmallException.cast("当前为等待审核状态，审核完成可以再次提交。");
  }
  //本机构只允许提交本机构的课程
  if(!courseBase.getCompanyId().equals(companyId)){
   TeachmallException.cast("不允许提交其它机构的课程。");
  }

  //课程图片是否填写
  if(StringUtil.isEmpty(courseBase.getPic())){
   TeachmallException.cast("提交失败，请上传课程图片");
  }

  //添加课程预发布记录
  CoursePublishPre coursePublishPre = new CoursePublishPre();
  //课程基本信息加部分营销信息
  CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
  BeanUtils.copyProperties(courseBaseInfo,coursePublishPre);
  //课程营销信息
  CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
  //转为json
  String courseMarketJson = JSON.toJSONString(courseMarket);
  //将课程营销信息json数据放入课程预发布表
  coursePublishPre.setMarket(courseMarketJson);

  //查询课程计划信息
  List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
  if(teachplanTree.size()<=0){
   TeachmallException.cast("提交失败，还没有添加课程计划");
  }
  //转json
  String teachplanTreeString = JSON.toJSONString(teachplanTree);
  coursePublishPre.setTeachplan(teachplanTreeString);

  //设置预发布记录状态,已提交
  coursePublishPre.setStatus("202003");
  //教学机构id
  coursePublishPre.setCompanyId(companyId);
  //提交时间
  coursePublishPre.setCreateDate(LocalDateTime.now());
  CoursePublishPre coursePublishPreUpdate = coursePublishPreMapper.selectById(courseId);
  if(coursePublishPreUpdate == null){
   //添加课程预发布记录
   coursePublishPreMapper.insert(coursePublishPre);
  }else{
   coursePublishPreMapper.updateById(coursePublishPre);
  }

  //更新课程基本表的审核状态
  courseBase.setAuditStatus("202003");
  courseBaseMapper.updateById(courseBase);
 }
}