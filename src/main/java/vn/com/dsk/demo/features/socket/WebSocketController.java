package vn.com.dsk.demo.features.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/message")
    @SendTo("/topic/response")
    public String processMessage(String message) {
        return "Received message: " + message;
    }
}
