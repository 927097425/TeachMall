package com.teachmall.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teachmall.base.exception.TeachmallException;
import com.teachmall.content.mapper.TeachplanMapper;
import com.teachmall.content.mapper.TeachplanMediaMapper;
import com.teachmall.content.model.dto.BindTeachplanMediaDto;
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

import java.time.LocalDateTime;
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
    @Transactional
    @Override
    public void moveupTeachplan(Long id) {
        Teachplan target = teachplanMapper.selectById(id);
        if(target==null) return;
        Long courseid = target.getCourseId();
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseid);
        int count = 1;
        TeachplanDto predto =null;
        for(TeachplanDto teachplanDto:teachplanDtos){

            if(target.getParentid() == 0){
                if(teachplanDto.getId().equals(id)){
                    if(count==1)throw new TeachmallException("已经是最上层了无法再继续上移");
                    else{
                        teachplanDto.setOrderby(predto.getOrderby());
                        if(predto!=null)predto.setOrderby(count);
                        Teachplan teachplan = new Teachplan();
                        BeanUtils.copyProperties(teachplanDto,teachplan);
                        teachplanMapper.updateById(teachplan);
                        BeanUtils.copyProperties(predto,teachplan);
                        teachplanMapper.updateById(teachplan);
                        return;
                    }
                }
                else{
                    teachplanDto.setOrderby(count);
                }

            }
            else if(target.getParentid().equals(teachplanDto.getId())){
                List<TeachplanDto> children = teachplanDto.getTeachPlanTreeNodes();
                Teachplan prechild = null;
                int i = 1;
                for(TeachplanDto child :children){
                    if(child.getId().equals(id)){
                        if(i==1)throw new TeachmallException("已经是最上层了无法再继续上移");
                        else{
                            child.setOrderby(prechild.getOrderby());
                            if(prechild!=null)prechild.setOrderby(i);
                            Teachplan teachplan = new Teachplan();
                            BeanUtils.copyProperties(child,teachplan);
                            teachplanMapper.updateById(teachplan);
                            BeanUtils.copyProperties(prechild,teachplan);
                            teachplanMapper.updateById(teachplan);
                            return;
                        }

                    }
                    else{
                        teachplanDto.setOrderby(i);
                    }
                    prechild = child;
                    i++;
                }
            }
            predto = teachplanDto;
            count++;
        }

    }

    @Override
    public void movedownTeachplan(Long id) {
        Teachplan target = teachplanMapper.selectById(id);
        if(target==null) return;
        Long courseid = target.getCourseId();
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseid);
        int count = 1;
        TeachplanDto predto =null;
        for(TeachplanDto teachplanDto:teachplanDtos){
            if(target.getParentid() == 0){
                if(teachplanDto.getId().equals(id)&&count==teachplanDtos.size())throw new TeachmallException("已经是最下层了无法再继续下移");
                if(predto != null && predto.getId().equals(id)){
                    teachplanDto.setOrderby(predto.getOrderby());
                    predto.setOrderby(count);
                    Teachplan teachplan = new Teachplan();
                    BeanUtils.copyProperties(teachplanDto,teachplan);
                    teachplanMapper.updateById(teachplan);
                    BeanUtils.copyProperties(predto,teachplan);
                    teachplanMapper.updateById(teachplan);
                    return;

                }
                else{
                    teachplanDto.setOrderby(count);
                }

            }
            else if(target.getParentid().equals(teachplanDto.getId())){
                List<TeachplanDto> children = teachplanDto.getTeachPlanTreeNodes();
                Teachplan prechild = null;
                int i = 1;
                for(TeachplanDto child :children){
                    if(child.getId().equals(id)&&i == children.size())throw new TeachmallException("已经是最下层了无法再继续下移");
                    if(prechild!=null&&prechild.getId().equals(id)){
                        child.setOrderby(prechild.getOrderby());
                        prechild.setOrderby(i);
                        Teachplan teachplan = new Teachplan();
                        BeanUtils.copyProperties(child,teachplan);
                        teachplanMapper.updateById(teachplan);
                        BeanUtils.copyProperties(prechild,teachplan);
                        teachplanMapper.updateById(teachplan);
                        return;

                    }
                    else{
                        teachplanDto.setOrderby(i);
                    }
                    prechild = child;
                    i++;
                }
            }
            predto = teachplanDto;
            count++;
        }
    }

    @Transactional
    @Override
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto) {
        //教学计划id
        Long teachplanId = bindTeachplanMediaDto.getTeachplanId();
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(teachplan==null){
            TeachmallException.cast("教学计划不存在");
        }
        Integer grade = teachplan.getGrade();
        if(grade!=2){
            TeachmallException.cast("只允许第二级教学计划绑定媒资文件");
        }
        //课程id
        Long courseId = teachplan.getCourseId();

        //先删除原来该教学计划绑定的媒资
        teachplanMediaMapper.delete(new LambdaQueryWrapper<TeachplanMedia>().eq(TeachplanMedia::getTeachplanId,teachplanId));

        //再添加教学计划与媒资的绑定关系
        TeachplanMedia teachplanMedia = new TeachplanMedia();
        teachplanMedia.setCourseId(courseId);
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setMediaFilename(bindTeachplanMediaDto.getFileName());
        teachplanMedia.setMediaId(bindTeachplanMediaDto.getMediaId());
        teachplanMedia.setCreateDate(LocalDateTime.now());
        teachplanMediaMapper.insert(teachplanMedia);
        return teachplanMedia;
    }

    @Override
    public void deleteAssociation(Long teachPlanId, String mediaId) {
        teachplanMediaMapper.deleteByPlanId(teachPlanId,mediaId);
    }


}
