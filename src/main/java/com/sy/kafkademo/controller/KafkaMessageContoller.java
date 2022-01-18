package com.sy.kafkademo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author 孙宇
 * @date 2021/11/22 17:08
 */
@Slf4j
@RestController
@RequestMapping(value = "/test")
public class KafkaMessageContoller {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/send1")
    public void send1(@RequestParam(value = "text") String text) {
        //向test主题发送消息
        kafkaTemplate.send("test",text);
    }

    @GetMapping("/send2")
    public void send2(@RequestParam(value = "text") String text) {
        //发送消息
        List<String> list = Arrays.asList(text.split(","));
        for (String msg : list){
            ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send("test", msg);
            send.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("-生产者发送消息失败"+throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    log.info("-生产者发送消息成功"+stringObjectSendResult.toString()+"时间：");
                }
            });
        }
    }
}
