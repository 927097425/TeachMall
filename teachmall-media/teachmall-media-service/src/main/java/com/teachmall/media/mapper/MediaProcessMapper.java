package com.teachmall.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teachmall.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MediaProcessMapper extends BaseMapper<MediaProcess> {
    List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);
    int startTask(@Param("id") long id);

}
