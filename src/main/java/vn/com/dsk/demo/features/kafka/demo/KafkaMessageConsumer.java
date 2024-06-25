//package vn.com.dsk.demo.features.kafka.demo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//
//@Slf4j
//public class KafkaMessageConsumer {
//
//
//    public static void main(String[] args) {
//        KafkaConsumer<String, String> consumer = getStringStringKafkaConsumer();
//
//        try (consumer) {
//            consumer.subscribe(Collections.singletonList("test-topic"));
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.printf("Received message: key = %s, value = %s, offset = %d, partition = %d%n",
//                            record.key(), record.value(), record.offset(), record.partition());
//                }
//            }
//        } catch (Exception e) {
//            log.error("Can't read message", e);
//        }
//    }
//
//    private static KafkaConsumer<String, String> getStringStringKafkaConsumer() {
//        String WSL_IP_ADDRESS = "172.29.149.235";
//
//        Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, WSL_IP_ADDRESS + ":9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        return new KafkaConsumer<>(props);
//    }
//}
//
