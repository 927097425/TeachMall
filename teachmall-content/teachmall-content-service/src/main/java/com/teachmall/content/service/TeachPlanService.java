package com.teachmall.content.service;

import com.teachmall.content.model.dto.BindTeachplanMediaDto;
import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.model.po.TeachplanMedia;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description: 教学计划服务层
 * @Version: 1.0
 */
public interface TeachPlanService {
    public List<TeachplanDto> findTeachplanTree(long courseId);

    public void UpdateTeachplan(TeachplanDto teachplanDto);
    public void insertTeachplan(TeachplanDto teachplanDto);

    void deleteCharacter(Long id);

    void moveupTeachplan(Long id);

    void movedownTeachplan(Long id);
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);
    public void deleteAssociation(Long teachPlanId, String mediaId);
}
