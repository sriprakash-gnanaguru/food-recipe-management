package com.food.recipe.manage.common.exception;

import com.food.recipe.manage.adapter.out.http.domain.OutputResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientResponseException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<OutputResponse> processException(Exception exception) {
        log.error("An error has occurred", exception);
        return processException(exception, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<OutputResponse> processException(ServiceException exception) {
        log.error("An error has occurred", exception);
        return processException(exception, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<OutputResponse> processException(BusinessException exception) {
        log.error("An error has occurred", exception);
        return processException(exception, INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<OutputResponse> processException(Exception exception, HttpStatus statusCode) {
        OutputResponse response = OutputResponse.builder()
                .errorDescr(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, statusCode);
    }

}
