package vn.com.dsk.demo.features.kafka.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.features.kafka.model.Greeting;
import vn.com.dsk.demo.features.kafka.demo.ChatContent;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "default", groupId = "default")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = "greeting", groupId = "greeting")
    public void consume(Greeting greeting) {
        System.out.println("Received message: " + greeting);
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void consume(ChatContent chatContent) {
        System.out.println("Received message: " + chatContent);
    }
}
