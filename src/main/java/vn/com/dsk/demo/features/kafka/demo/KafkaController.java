package vn.com.dsk.demo.features.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.demo.features.kafka.config.Greeting;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestParam("topic") String topic, @RequestBody String message) {
        kafkaProducerService.sendMessage(topic, message);
    }

    @PostMapping("/send-greeting")
    public void sendGreetingMessage(@RequestParam("topic") String topic, @RequestBody Greeting greeting) {
        kafkaProducerService.sendGreetingMessage(topic, greeting);
    }

    @PostMapping("/send-multiType")
    public void sendMultiTypeMessage(@RequestParam("topic") String topic, @RequestBody Object message) {
        kafkaProducerService.sendMultiTypeMessage(topic, message);
    }
}
