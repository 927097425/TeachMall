package com.teachmall.messagesdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teachmall.messagesdk.model.po.MqMessageHistory;
import com.teachmall.messagesdk.service.MqMessageHistoryService;
import com.teachmall.messagesdk.mapper.MqMessageHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory> implements MqMessageHistoryService {

}
