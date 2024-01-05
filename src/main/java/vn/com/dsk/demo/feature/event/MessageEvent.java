package vn.com.dsk.demo.feature.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MessageEvent extends ApplicationEvent {

    private String message;

    public MessageEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
