package com.teachmall.content.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.model.po.Teachplan;

import java.util.List;


public interface TeachplanMapper extends BaseMapper<Teachplan> {
    public List<TeachplanDto> selectTreeNodes(long courseId);

}
