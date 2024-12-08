package com.example.vipa.exception;

import com.example.vipa.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class MainExceptionHandler {

    private static final Map<String, String> psqlExceptionMap = Map.of(
            "uk_favorite_post", "Объявление уже в избранном.",
            "uk_cart_post", "Объявление уже в корзине."
    );

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleException(AlreadyExistException exception) {
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.ALREADY_REPORTED;
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exception) {
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(BadCredentialsException exception) {
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handleException(PasswordMismatchException exception) {
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handleException(PSQLException exception) {
        String errorMessage = exception.getMessage();
        for (Map.Entry<String, String> entry : psqlExceptionMap.entrySet()) {
            if (errorMessage.contains(entry.getKey())) {
                errorMessage = entry.getValue();
            }
        }
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(response, status);
    }
}
