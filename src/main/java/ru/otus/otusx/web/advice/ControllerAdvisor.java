package ru.otus.otusx.web.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.otus.otusx.logic.exception.ApplicationError;
import ru.otus.otusx.logic.exception.NotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApplicationError> handleNotFoundException(NotFoundException exc) {
        return ResponseEntity.status(NOT_FOUND).body(new ApplicationError(exc));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        return ResponseEntity.status(BAD_REQUEST).body(new ApplicationError(exc));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApplicationError> handleHttpMessageNotReadableException(HttpMessageNotReadableException exc) {
        return ResponseEntity.status(BAD_REQUEST).body(new ApplicationError(exc));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApplicationError> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exc) {
        return ResponseEntity.status(BAD_REQUEST).body(new ApplicationError(exc));
    }
}
