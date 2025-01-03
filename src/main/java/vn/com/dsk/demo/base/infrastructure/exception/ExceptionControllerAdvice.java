package vn.com.dsk.demo.base.infrastructure.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.com.dsk.demo.base.adapter.wrappers.ErrorResponse;
import vn.com.dsk.demo.base.adapter.wrappers.ResponseUtils;
import vn.com.dsk.demo.base.shared.constants.HttpStatusCode;

import java.io.FileNotFoundException;


@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        if (e instanceof AuthenticationException) {
            log.error("AuthenticationException", e);
            return ResponseUtils.unauthorized(ErrorResponse.of(HttpStatusCode.UNAUTHORIZED, e.getMessage()));
        } else if (e instanceof FileNotFoundException) {
            log.error("FileNotFoundException", e);
            return ResponseUtils.internalErr(ErrorResponse.of(HttpStatusCode.NO_CONTENT, e.getMessage()));
        } else if (e instanceof EntityNotFoundException) {
            log.error("EntityNotFoundException", e);
            return ResponseUtils.internalErr(ErrorResponse.of(HttpStatusCode.NO_CONTENT, e.getMessage()));
        } else if (e instanceof DataAccessException) {
            log.error("DataAccessException", e);
            return ResponseUtils.internalErr(ErrorResponse.of(HttpStatusCode.BAD_REQUEST, e.getMessage()));
        } else {
            log.error("Exception", e);
            return ResponseUtils.internalErr(ErrorResponse.of(HttpStatusCode.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}