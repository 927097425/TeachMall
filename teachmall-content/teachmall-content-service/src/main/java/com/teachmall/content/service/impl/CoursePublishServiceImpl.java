package com.teachmall.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.teachmall.base.exception.CommonError;
import com.teachmall.base.exception.TeachmallException;
import com.teachmall.base.utils.StringUtil;
import com.teachmall.content.mapper.CourseBaseMapper;
import com.teachmall.content.mapper.CourseMarketMapper;
import com.teachmall.content.mapper.CoursePublishMapper;
import com.teachmall.content.mapper.CoursePublishPreMapper;
import com.teachmall.content.model.dto.CourseBaseInfoDto;
import com.teachmall.content.model.dto.CoursePreviewDto;
import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.model.po.CourseBase;
import com.teachmall.content.model.po.CourseMarket;
import com.teachmall.content.model.po.CoursePublish;
import com.teachmall.content.model.po.CoursePublishPre;
import com.teachmall.content.service.CourseBaseInfoService;
import com.teachmall.content.service.CoursePublishService;
import com.teachmall.content.service.TeachPlanService;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CoursePublishServiceImpl implements CoursePublishService {

 @Autowired
 CourseBaseInfoService courseBaseInfoService;
 @Autowired
 CoursePublishMapper coursePublishMapper;
 @Autowired
 TeachPlanService teachplanService;
 @Autowired
 CourseBaseMapper courseBaseMapper;
 @Autowired
 CoursePublishPreMapper coursePublishPreMapper;
 @Autowired
 CourseMarketMapper courseMarketMapper;

 @Autowired
 RabbitTemplate rabbitTemplate;
 @Autowired
 RedisTemplate redisTemplate;
@Autowired
 RedissonClient redissonClient;

 @Override
 public CoursePreviewDto getCoursePreviewInfo(Long courseId) {

  //课程基本信息、营销信息
  CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);

  //课程计划信息
  List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);

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
  if ("202003".equals(auditStatus)) {
   TeachmallException.cast("当前为等待审核状态，审核完成可以再次提交。");
  }
  //本机构只允许提交本机构的课程
  if (!courseBase.getCompanyId().equals(companyId)) {
   TeachmallException.cast("不允许提交其它机构的课程。");
  }

  //课程图片是否填写
  if (StringUtil.isEmpty(courseBase.getPic())) {
   TeachmallException.cast("提交失败，请上传课程图片");
  }

  //添加课程预发布记录
  CoursePublishPre coursePublishPre = new CoursePublishPre();
  //课程基本信息加部分营销信息
  CourseBaseInfoDto courseBaseInfo = courseBaseInfoService.getCourseBaseInfo(courseId);
  BeanUtils.copyProperties(courseBaseInfo, coursePublishPre);
  //课程营销信息
  CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
  //转为json
  String courseMarketJson = JSON.toJSONString(courseMarket);
  //将课程营销信息json数据放入课程预发布表
  coursePublishPre.setMarket(courseMarketJson);

  //查询课程计划信息
  List<TeachplanDto> teachplanTree = teachplanService.findTeachplanTree(courseId);
  if (teachplanTree.size() <= 0) {
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
  if (coursePublishPreUpdate == null) {
   //添加课程预发布记录
   coursePublishPreMapper.insert(coursePublishPre);
  } else {
   coursePublishPreMapper.updateById(coursePublishPre);
  }

  //更新课程基本表的审核状态
  courseBase.setAuditStatus("202003");
  courseBaseMapper.updateById(courseBase);
 }

 @Transactional
 @Override
 public void publish(Long companyId, Long courseId) {

  //约束校验
  //查询课程预发布表
  CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
  if (coursePublishPre == null) {
   TeachmallException.cast("请先提交课程审核，审核通过才可以发布");
  }
  //本机构只允许提交本机构的课程
  if (!coursePublishPre.getCompanyId().equals(companyId)) {
   TeachmallException.cast("不允许提交其它机构的课程。");
  }


  //课程审核状态
  String auditStatus = coursePublishPre.getStatus();
  //审核通过方可发布
  if (!"202004".equals(auditStatus)) {
   TeachmallException.cast("操作失败，课程审核通过方可发布。");
  }

  //保存课程发布信息
  saveCoursePublish(courseId);

  //保存消息表
  saveCoursePublishMessage(courseId);

  //删除课程预发布表对应记录
  coursePublishPreMapper.deleteById(courseId);

 }


 private void saveCoursePublish(Long courseId) {
  //整合课程发布信息
  //查询课程预发布表
  CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
  if (coursePublishPre == null) {
   TeachmallException.cast("课程预发布数据为空");
  }

  CoursePublish coursePublish = new CoursePublish();

  //拷贝到课程发布对象
  BeanUtils.copyProperties(coursePublishPre, coursePublish);
  coursePublish.setStatus("203002");
  CoursePublish coursePublishUpdate = coursePublishMapper.selectById(courseId);
  if (coursePublishUpdate == null) {
   coursePublishMapper.insert(coursePublish);
  } else {
   coursePublishMapper.updateById(coursePublish);
  }
  //更新课程基本表的发布状态
  CourseBase courseBase = courseBaseMapper.selectById(courseId);
  courseBase.setStatus("203002");
  courseBaseMapper.updateById(courseBase);

 }


 private void saveCoursePublishMessage(Long courseId) {
   //使用微服务加mq实现 ToDo
   String exchange = "coursepub.fanout";
   rabbitTemplate.convertAndSend(exchange,"",String.valueOf(courseId));

  }
@Override
 public CoursePublish getCoursePublish(Long courseId){
  CoursePublish coursePublish = coursePublishMapper.selectById(courseId);
  return coursePublish ;
 }

 @Override
 public CoursePublish getCoursePublishCache(Long courseId) {
  Object jsonobj = redisTemplate.opsForValue().get(courseId);
  CoursePublish coursePublish;
  //设控制解决缓存穿透问题
  if(jsonobj!=null) {
   String jsonstring = jsonobj.toString();
    if (jsonstring.equals("null")) return null;
    coursePublish = JSON.parseObject(jsonobj.toString(),CoursePublish.class);
    return coursePublish;
  }
  else {
    RLock lock= redissonClient.getLock("courselock:"+courseId);
    lock.lock();
    try {
      Object  jsonObj = redisTemplate.opsForValue().get(courseId);
      if(jsonObj!=null){
       String jsonString = jsonObj.toString();
       coursePublish = JSON.parseObject(jsonString, CoursePublish.class);
       return coursePublish;
      }
     log.info("get data from mysql database");
      coursePublish = coursePublishMapper.selectById(courseId);
     redisTemplate.opsForValue().set(courseId,JSON.toJSONString(coursePublish),30, TimeUnit.SECONDS);
     return coursePublish;
    }finally {
     lock.unlock();
    }
  }


 }

}