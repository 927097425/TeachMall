package com.teachmall.content.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 陈靖国
 * @Description: 教师数据传输对象
 * @Version: 1.0
 */

@Data
@NoArgsConstructor
public class CourseTeacherInfoDto {
    private Long courseId;

    /**
     * 教师标识
     */
    private String teacherName;

    /**
     * 教师职位
     */
    private String position;

    /**
     * 教师简介
     */
    private String introduction;
}
