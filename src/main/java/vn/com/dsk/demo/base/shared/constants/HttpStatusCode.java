package vn.com.dsk.demo.base.shared.constants;

import lombok.Getter;

@Getter
public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    REQUEST_TIMEOUT(408),
    INTERNAL_SERVER_ERROR(500);
    private final int value;

    HttpStatusCode(int value) {
        this.value = value;
    }

}