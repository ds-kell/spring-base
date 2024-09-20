package vn.com.dsk.demo.features.kafka.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.com.dsk.demo.features.kafka.model.Greeting;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Greeting> greetingKafkaTemplate;
    private final KafkaTemplate<String, Object> multiTypeKafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                KafkaTemplate<String, Greeting> greetingKafkaTemplate,
                                KafkaTemplate<String, Object> multiTypeKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.greetingKafkaTemplate = greetingKafkaTemplate;
        this.multiTypeKafkaTemplate = multiTypeKafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendGreetingMessage(String topic, Greeting greeting) {
        greetingKafkaTemplate.send(topic, greeting);
    }

    public void sendMultiTypeMessage(String topic, Object message) {
        multiTypeKafkaTemplate.send(topic, message);
    }
}
