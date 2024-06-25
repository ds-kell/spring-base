package vn.com.dsk.demo.features.kafka.demo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.features.kafka.config.Greeting;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "test-topic", groupId = "123abc")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }

    @KafkaListener(topics = "greeting", groupId = "foo")
    public void consume(Greeting greeting) {
        System.out.println("Received message: " + greeting);
    }
}
