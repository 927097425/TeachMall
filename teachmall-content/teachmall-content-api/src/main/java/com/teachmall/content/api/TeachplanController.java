package com.teachmall.content.api;

import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.service.TeachPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
@AllArgsConstructor
public class TeachplanController {
    private final TeachPlanService teachPlanService;
    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){
        return teachPlanService.findTeachplanTree(courseId);
    }

}