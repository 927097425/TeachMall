package com.teachmall.content.mapper;

import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teachmall.base.model.PageParams;
import com.teachmall.content.model.dto.QueryCourseParamsDto;
import com.teachmall.content.model.po.CourseBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;




public interface CourseBaseMapper extends BaseMapper<CourseBase> {


}
