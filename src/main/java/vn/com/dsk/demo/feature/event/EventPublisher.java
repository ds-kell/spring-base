package vn.com.dsk.demo.feature.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String message){
        applicationEventPublisher.publishEvent(new MessageEvent(this, message));
    }

}
