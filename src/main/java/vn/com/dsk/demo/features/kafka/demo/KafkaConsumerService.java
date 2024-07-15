package vn.com.dsk.demo.features.kafka.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.features.kafka.config.Greeting;

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
}
