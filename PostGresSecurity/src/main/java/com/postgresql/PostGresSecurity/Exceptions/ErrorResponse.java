package com.postgresql.PostGresSecurity.Exceptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;
@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String message;

    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
