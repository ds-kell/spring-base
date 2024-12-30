package vn.com.dsk.demo.base.adapter.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import vn.com.dsk.demo.base.shared.constants.HttpStatusCode;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    @Builder.Default
    protected HttpStatusCode statusCode = null;

    protected String message = null;

    protected Object data = null;

    public static Response of (HttpStatusCode statusCode, String message, Object data) {
        return Response.builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }

}