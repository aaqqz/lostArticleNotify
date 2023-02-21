package project.toy.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import project.toy.api.exception.GlobalException;
import project.toy.api.response.ErrorResponse;

import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequestException(MethodArgumentNotValidException e) {
        ErrorResponse body = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .validation(new HashMap<>())
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            body.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(400)
                .body(body);

        return response;
    }

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> GlobalException(GlobalException e) {
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(e.getStatusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(e.getStatusCode())
                .body(body);

        return response;
    }
}
