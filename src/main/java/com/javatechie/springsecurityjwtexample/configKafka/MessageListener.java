package com.javatechie.springsecurityjwtexample.configKafka;

import com.javatechie.springsecurityjwtexample.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = KafkaConstants.TOPIC_PRODUCER, groupId = KafkaConstants.GROUP_ID)
    public void consume(Message message) {
        System.out.println("sending via kafka listener.." + message);
        template.convertAndSend("/topic/group", message);
    }
}
