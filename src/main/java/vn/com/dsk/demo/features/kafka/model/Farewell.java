package vn.com.dsk.demo.features.kafka.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Farewell {

    private String message;
    private Integer remainingMinutes;

    public Farewell() {
    }

    public Farewell(String message, Integer remainingMinutes) {
        this.message = message;
        this.remainingMinutes = remainingMinutes;
    }

    @Override
    public String toString() {
        return message + ". In " + remainingMinutes + "!";
    }

}