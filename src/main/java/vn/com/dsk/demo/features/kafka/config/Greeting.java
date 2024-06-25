package vn.com.dsk.demo.features.kafka.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Greeting {

    private String msg;
    private String name;

    public Greeting() {

    }

    public Greeting(String msg, String name) {
        this.msg = msg;
        this.name = name;
    }

    @Override
    public String toString() {
        return msg + ", " + name + "!";
    }
}
