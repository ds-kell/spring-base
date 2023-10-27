package vn.com.dsk.demo.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ServiceException extends RuntimeException {

    private String errMsg;

    public ServiceException() {
    }

    public ServiceException(String errMsg, String message) {
        super(message, null);
        this.errMsg = errMsg;
    }
}
