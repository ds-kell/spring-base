package vn.com.dsk.demo.base.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.com.dsk.demo.base.utils.response.ErrorResponse;
import vn.com.dsk.demo.base.utils.response.Response;
import vn.com.dsk.demo.base.utils.response.ResponseUtils;


@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> exceptionHandler(Exception e) {
        log.error("Exception", e);
        return ResponseUtils.internalErr(ErrorResponse.of(e.getCause().getMessage(), e.getMessage()));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> EntityNotFoundErrorHandler(EntityNotFoundException e) {
        String errKey = e.getMessage();
        String errMsg = "Can not find " + e.getEntityName() + " with attribute: " + e.getAttribute();
        log.error(errKey, errMsg);
        return ResponseUtils.badRequest(ErrorResponse.of(errKey, errMsg));
    }
}
