package ru.otus.otusx.logic.exception;

import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public class ApplicationError {
    private final String message;
    private final String type;

    public ApplicationError(MethodArgumentNotValidException exc) {
        this.message = validationMessage(exc.getMessage());
        this.type = exc.getClass().getTypeName();
    }

    public ApplicationError(RuntimeException exc) {
        this.message = exc.getMessage();
        this.type = exc.getClass().getTypeName();
    }

    private String validationMessage(String message) {
        var error = message.split("default message \\[")[2].trim();
        return error.substring(0, error.length() - 2);
    }
}
