package com.teachmall.content.service;

import com.teachmall.content.model.dto.CoursePreviewDto;

public interface CoursePublishService {



   public CoursePreviewDto getCoursePreviewInfo(Long courseId);
   public void commitAudit(Long companyId,Long courseId);

}