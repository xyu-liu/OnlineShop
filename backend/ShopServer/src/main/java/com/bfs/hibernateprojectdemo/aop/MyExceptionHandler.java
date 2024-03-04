package com.bfs.hibernateprojectdemo.aop;

import com.bfs.hibernateprojectdemo.dto.common.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@ControllerAdvice
public class MyExceptionHandler {

    /*@ExceptionHandler(NullPointerException.class)
    public DataResponse handleNullException(NullPointerException e){
        return DataResponse.builder()
                .success(false)
                .message("The argument ID is invalid")
                .build();
    }*/

    @ExceptionHandler(RepeatedOperationException.class)
    public DataResponse handleNullException(RepeatedOperationException e){
        return DataResponse.builder()
                .success(false)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public DataResponse handleAccessException(AccessDeniedException e){
        return DataResponse.builder()
                .success(false)
                .message("Wrong authentication")
                .build();
    }

}
