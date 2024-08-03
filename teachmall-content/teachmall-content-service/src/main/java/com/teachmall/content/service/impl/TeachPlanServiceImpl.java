package com.teachmall.content.service.impl;

import com.teachmall.base.exception.TeachmallException;
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
    @Transactional
    @Override
    public void deleteCharacter(Long id) {
        Teachplan target = teachplanMapper.selectById(id);
        if(target==null) return;
        Long courseid = target.getCourseId();
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseid);

        for(TeachplanDto teachplanDto:teachplanDtos){
            if(target.getParentid() == 0){
                if(teachplanDto.getId().equals(id)){
                    if(teachplanDto.getTeachPlanTreeNodes().size()!=0)throw new TeachmallException("{\"errCode\":\"120409\",\"errMessage\":\"课程计划信息还有子级信息，无法操作\"}");
                }

            }
            else if(target.getParentid().equals(teachplanDto.getId())){
                List<TeachplanDto> children = teachplanDto.getTeachPlanTreeNodes();
                int i = 1;
                for(TeachplanDto child :children){
                    if(!child.getId().equals(id)) {
                        child.setOrderby(i);
                        i += 1;
                    }
                    else {
                        if(child.getTeachplanMedia()!=null)teachplanMediaMapper.deleteById(child.getTeachplanMedia());
                    }
                }
            }
        }
        teachplanMapper.deleteById(id);
    }

}
