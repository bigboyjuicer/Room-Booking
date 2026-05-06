package api.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentNotFoundException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(DepartmentNotFoundException.class);

    public DepartmentNotFoundException(String message, int id) {
        super(message);
        log.error("Department with this id not found (id: {})", id);
    }
}
