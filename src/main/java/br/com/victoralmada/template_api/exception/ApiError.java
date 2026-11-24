package br.com.victoralmada.template_api.exception;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class ApiError {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final Map<String, String> fields;

    public ApiError(LocalDateTime timestamp, int status, String error, String message) {
        this(timestamp, status, error, message, null);
    }

    public ApiError(LocalDateTime timestamp, int status, String error, String message, Map<String, String> fields) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.fields = fields;
    }

}
