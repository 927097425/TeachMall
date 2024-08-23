package com.teachmall.content.service;

import com.teachmall.content.model.dto.CoursePreviewDto;
import com.teachmall.content.model.po.CoursePublish;

public interface CoursePublishService {



   public CoursePreviewDto getCoursePreviewInfo(Long courseId);
   public void commitAudit(Long companyId,Long courseId);

   public void publish(Long companyId,Long courseId);

    CoursePublish getCoursePublish(Long courseId);
    CoursePublish getCoursePublishCache(Long courseId);
}