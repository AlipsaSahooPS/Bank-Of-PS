package com.example.bankapp.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<ApiErrorResponse> customerNotFound(){
        ApiErrorResponse apiResponse =new ApiErrorResponse
                .ApiErrorResponseBuilder().withDetail("Not able to find customer record")
                .withMessage("Not a valid user id.Please provide a valid user id or contact system admin.")
                .withError_code("404").withStatus(HttpStatus.NOT_FOUND)
                .atTime(LocalDateTime.now(ZoneOffset.UTC)).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpStatus status){
        List<String> errorMsg= ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        ApiErrorResponse response = new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status).withDetail("not valid arguments")
                .withMessage(errorMsg.toString()).withError_code("406")
                .withError_code(HttpStatus.NOT_ACCEPTABLE.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC)).build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(CustomRestServiceException.class)
    protected ResponseEntity<Object> handleCustomAPIException(CustomRestServiceException ex, HttpStatus status) {
        ApiErrorResponse response =new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status).withDetail("custom exception")
                .withMessage(ex.getLocalizedMessage()).withError_code("503")
                .withError_code(HttpStatus.SERVICE_UNAVAILABLE.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC)).build();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomAPIException(Exception ex, HttpStatus status) {
        ApiErrorResponse response =new ApiErrorResponse.ApiErrorResponseBuilder()
                .withStatus(status).withDetail("Something went wrong")
                .withMessage(ex.getLocalizedMessage()).withError_code("502")
                .withError_code(HttpStatus.BAD_GATEWAY.name())
                .atTime(LocalDateTime.now(ZoneOffset.UTC)).build();
        return new ResponseEntity<>(response, response.getStatus());
    }
}
