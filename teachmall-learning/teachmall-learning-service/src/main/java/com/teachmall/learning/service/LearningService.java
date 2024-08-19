package com.teachmall.learning.service;

import com.teachmall.base.model.RestResponse;

public interface LearningService {

/**
 * @description 获取教学视频
 * @param courseId 课程id
 * @param teachplanId 课程计划id
 * @param mediaId 视频文件id
*/
public RestResponse<String> getVideo(String userId, Long courseId, Long teachplanId, String mediaId);
}