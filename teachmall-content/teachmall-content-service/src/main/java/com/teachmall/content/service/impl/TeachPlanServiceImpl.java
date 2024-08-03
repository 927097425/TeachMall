package com.teachmall.content.service.impl;

import com.teachmall.content.mapper.TeachplanMapper;
import com.teachmall.content.mapper.TeachplanMediaMapper;
import com.teachmall.content.model.dto.TeachplanDto;
import com.teachmall.content.model.po.Teachplan;
import com.teachmall.content.model.po.TeachplanMedia;
import com.teachmall.content.service.TeachPlanService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Arg;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 陈靖国
 * @Description: 教学计划实现类
 * @Version: 1.0
 */

@Service
@AllArgsConstructor
public class TeachPlanServiceImpl implements TeachPlanService {
    private final TeachplanMapper teachplanMapper;
    private final TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {

        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Transactional
    @Override
    public void UpdateTeachplan(TeachplanDto teachplanDto) {
        Teachplan teachplan = new Teachplan();
        BeanUtils.copyProperties(teachplanDto,teachplan);
        teachplanMapper.updateById(teachplan);
        TeachplanMedia teachplanMedia = teachplanDto.getTeachplanMedia();
        //更新媒资信息
        if(teachplanMedia!=null)teachplanMediaMapper.updateById(teachplanMedia);
        List<TeachplanDto> treeNodes= teachplanDto.getTeachPlanTreeNodes();

        if(treeNodes!=null) {
            for (TeachplanDto treeNode : treeNodes) {
                Teachplan node = new Teachplan();
                BeanUtils.copyProperties(treeNode, node);
                teachplanMapper.updateById(node);
                TeachplanMedia nodeMedia = treeNode.getTeachplanMedia();
                if(nodeMedia!=null)teachplanMediaMapper.updateById(nodeMedia);
            }
        }

    }

    @Override
    public void insertTeachplan(TeachplanDto teachplanDto) {
        int count = teachplanMapper.getTeachplanCount(teachplanDto)+1;
        Teachplan teachplan = new Teachplan();
        BeanUtils.copyProperties(teachplanDto,teachplan);
        teachplan.setOrderby(count);
        teachplanMapper.insert(teachplan);
        TeachplanMedia teachplanMedia = teachplanDto.getTeachplanMedia();
        //更新媒资信息
        if(teachplanMedia!=null)teachplanMediaMapper.insert(teachplanMedia);
        List<TeachplanDto> treeNodes= teachplanDto.getTeachPlanTreeNodes();

        if(treeNodes!=null) {
            for (TeachplanDto treeNode : treeNodes) {
                Teachplan node = new Teachplan();
                BeanUtils.copyProperties(treeNode, node);
                teachplanMapper.insert(node);
                TeachplanMedia nodeMedia = treeNode.getTeachplanMedia();
                if(nodeMedia!=null)teachplanMediaMapper.insert(nodeMedia);
            }
        }

    }

}
