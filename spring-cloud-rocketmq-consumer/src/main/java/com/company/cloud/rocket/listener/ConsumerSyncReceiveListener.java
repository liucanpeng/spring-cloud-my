package com.company.cloud.rocket.listener;

import com.company.cloud.rocket.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Service;

/**
 * @author bin.li
 * @date 2020/9/6
 */
@Service
@RocketMQMessageListener(consumerGroup = "syncReceive", topic = "sync", selectorExpression = "sendReceive")
@Slf4j
public class ConsumerSyncReceiveListener implements RocketMQReplyListener<String, User> {

    @Override
    public User onMessage(String message) {
        return User.builder().name("张三接收到了：" + message).result(2000).build();
    }
}
