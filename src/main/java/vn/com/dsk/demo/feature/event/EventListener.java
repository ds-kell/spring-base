package vn.com.dsk.demo.feature.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<MessageEvent> {

    @Override
    public void onApplicationEvent(MessageEvent event) {
        System.out.println("Message event" + event.getMessage());
    }

    @Async
    @org.springframework.context.event.EventListener
    public void listenEvent(MessageEvent messageEvent) throws InterruptedException{
        Thread.sleep(2000);
        System.out.println("Message event" + messageEvent.getMessage());

    }
}
