package vn.com.dsk.demo.features.kafka.demo;

import lombok.Data;

@Data
public class ChatContent {
    private String userId;
    private String content;

    @Override
    public String toString() {
        return userId + ", " + content;
    }
}
