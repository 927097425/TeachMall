package com.teachmall.learning.service;

import com.teachmall.base.model.PageResult;
import com.teachmall.learning.model.dto.MyCourseTableParams;
import com.teachmall.learning.model.dto.XcChooseCourseDto;
import com.teachmall.learning.model.dto.XcCourseTablesDto;
import com.teachmall.learning.model.po.XcCourseTables;

public interface MyCourseTablesService {

    /**
 * @description 添加选课
 * @param userId 用户id
 * @param courseId 课程id
*/
 public XcChooseCourseDto addChooseCourse(String userId, Long courseId);
 public XcCourseTablesDto getLearningStatus(String userId, Long courseId);
 public boolean saveChooseCourseSuccess(String chooseCourseId);
 public PageResult<XcCourseTables> mycoursetables(MyCourseTableParams params);
}