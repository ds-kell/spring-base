package vn.com.dsk.demo.base.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EntityNotFoundException extends RuntimeException {

    private String entityName;

    private String attribute;

    public EntityNotFoundException() {
        super("error.api.entity-not-found", null);
    }

    public EntityNotFoundException(String entityName, String attribute) {
        super("error.api.entity-not-found", null);
        this.entityName = entityName;
        this.attribute = attribute;
    }

}
