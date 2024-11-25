package api.controller;

import api.util.MyCustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<MyCustomResponse> handleValidationException(MethodArgumentNotValidException mex) {
        Map<String, String> errorsDescription = new HashMap<>();
        mex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            errorsDescription.put(fieldName, error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new MyCustomResponse(false, "Validation error occurred", null, errorsDescription));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyCustomResponse> handleInvalidFormatException(Exception ex) {
        return ResponseEntity.badRequest().body(new MyCustomResponse(false, ex.getMessage(), null, null));
    }

}
