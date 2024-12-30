package vn.com.dsk.demo.base.adapter.wrappers;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.dsk.demo.base.shared.constants.HttpStatusCode;

@UtilityClass
public class ResponseUtils {

    /**
     * Response api
     */
    public ResponseEntity<Response> created(Object data) {
        return new ResponseEntity<>(Response.of(HttpStatusCode.CREATED, "Created", data), HttpStatus.CREATED);
    }

    public ResponseEntity<Response> ok(String msg) {
        return ResponseEntity.ok(Response.of(HttpStatusCode.OK, msg, null));
    }

    public ResponseEntity<Response> ok(Object data) {
        return ResponseEntity.ok(Response.of(HttpStatusCode.CREATED, "Success", data));
    }

    public ResponseEntity<Response> ok(String msg, Object data) {
        return ResponseEntity.ok(Response.of(HttpStatusCode.CREATED, msg, data));
    }

    public ResponseEntity<Response> ok(HttpStatusCode statusCode, String msg, Object data) {
        return ResponseEntity.ok(Response.of(statusCode, msg, data));
    }

    /**
     * Response error
     */
    public ResponseEntity<ErrorResponse> badRequest(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorResponse> unauthorized(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ErrorResponse> methodNotSupported(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    public ResponseEntity<ErrorResponse> mediaTypeNotSupported(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public ResponseEntity<ErrorResponse> internalErr(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}