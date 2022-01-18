package com.sy.kafkademo.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author 孙宇
 * @date 2021/11/22 16:41
 * 消息消费者
 */
@Component
@Slf4j
public class KafkaMessageHandler {

    /***
     * 接收消息后手动提交
     *
     * @param record 消费记录
     * @param acknowledgment 确认接收
     * @return void
     * @author: zhihao
     * @date: 8/1/2020
     */

    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void handlerMessage(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
        try {
            //手动接收消息
//            String value = (String) record.value();
//            System.out.println("手动接收<<接收到消息,进行消费>>>"+value);
//            log.info("简单消费：{}-{}-{}", record.topic(), record.partition(), record.value());


            for (ConsumerRecord<?,?> record : records){
                Optional message = Optional.ofNullable(record.value());
                if(message.isPresent()){
                    String msg =(String) message.get();
                    log.info("batchConsumer接收到消息信息：{}",msg);
                }
            }

        } catch (Exception e) {
            log.error("手动接收<<消费异常信息>>>" + e.getMessage());
        } finally {
            //最终提交确认接收到消息  手动提交 offset
            acknowledgment.acknowledge();
        }
    }

//    /***
//     * 接收消息后自动提交 需要配置开启enable-auto-commit: true
//     *
//     * @param message 消息
//     * @return void
//     * @author: zhihao
//     * @date: 8/1/2020
//     */
//    @KafkaListener(topics = "test",groupId = "test-consumer")
//    public void handlerMessage(String message){
//        System.out.println("接收到自动确认消息"+message);
//    }

}
