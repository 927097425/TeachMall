package com.teachmall.messagesdk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.teachmall.messagesdk.model.po.MqMessage;

import java.util.List;


public interface MqMessageService extends IService<MqMessage> {


    public List<MqMessage> getMessageList(int shardIndex, int shardTotal,  String messageType,int count);


    public MqMessage addMessage(String messageType,String businessKey1,String businessKey2,String businessKey3);

    public int completed(long id);


    public int completedStageOne(long id);
    public int completedStageTwo(long id);
    public int completedStageThree(long id);
    public int completedStageFour(long id);

    /**
     * @description 查询阶段状态
     * @param id
     * @return int

    */
    public int getStageOne(long id);
    public int getStageTwo(long id);
    public int getStageThree(long id);
    public int getStageFour(long id);

}
