package com.bfs.hibernateprojectdemo.aop;

public class RepeatedOperationException extends RuntimeException{
    public RepeatedOperationException(String message) {
        super(message);
    }

    public RepeatedOperationException() {
    }
}
