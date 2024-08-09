package com.teachmall.content.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teachmall.content.model.po.TeachplanMedia;


public interface TeachplanMediaMapper extends BaseMapper<TeachplanMedia> {
    void deleteByCourseId(long courseId);

    void deleteByPlanId(Long teachPlanId, String mediaId);
}
