package api.controller;

import api.util.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException mex) {
        Map<String, String> errorsDescription = new HashMap<>();
        mex.getBindingResult().getAllErrors().forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            errorsDescription.put(fieldName, error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Validation error occurred", null, errorsDescription));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse> handleInvalidFormatException(ExpiredJwtException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, "JWT is expired", null, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleInvalidFormatException(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage(), null, null));
    }

}
