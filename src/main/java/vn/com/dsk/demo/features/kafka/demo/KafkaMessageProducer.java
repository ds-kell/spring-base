//package vn.com.dsk.demo.features.kafka.demo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.util.Properties;
//
//@Slf4j
//public class KafkaMessageProducer {
//
//    public static void main(String[] args) {
//
//        String WSL_IP_ADDRESS = "172.29.149.235";
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, WSL_IP_ADDRESS + ":9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
//
//        String topic = "test-topic";
//        String key = "key1";
//        String value = "Hello, Kafka!";
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
//
//        try {
//            producer.send(record);
//            System.out.println("Message sent to topic " + topic);
//        } catch (Exception e) {
//            log.error("Can't send message", e);
//        } finally {
//            producer.close();
//        }
//    }
//}
