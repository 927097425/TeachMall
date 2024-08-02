package com.teachmall.content.api;

import com.teachmall.base.model.PageResult;
import com.teachmall.content.model.dto.CourseCategoryTreeDto;
import com.teachmall.content.model.po.CourseCategory;
import com.teachmall.content.service.CourseCategoryInfoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description: 课程分类查询接口
 * @Version: 1.0
 */

@Api(tags = "课程分类查询接口")
@AllArgsConstructor
@RestController
public class CourseCategoryInfoController {
    public final CourseCategoryInfoService courseCategoryInfoService;
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> selectTreeNodes(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryInfoService.selectTreeNodes();
        return  courseCategoryTreeDtos;
    }
}
