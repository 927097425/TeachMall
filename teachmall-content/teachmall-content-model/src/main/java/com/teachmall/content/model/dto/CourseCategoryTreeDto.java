package com.teachmall.content.model.dto;

import com.teachmall.content.model.po.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 陈靖国
 * @CreateTime: 2024-08-02
 * @Description: 课程分类查询数据传输对象
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    List<CourseCategory> childrenTreeNodes;
//    id,name,label,parentid,isshow,orderby,isleaf

    public CourseCategoryTreeDto(CourseCategory courseCategory, List<CourseCategory> childrenTreeNodes){
        super(courseCategory.getId(),courseCategory.getName(),courseCategory.getLabel(),courseCategory.getParentid(),courseCategory.getIsShow(),courseCategory.getOrderby(),courseCategory.getIsLeaf());
        this.childrenTreeNodes = childrenTreeNodes;
    }
}
