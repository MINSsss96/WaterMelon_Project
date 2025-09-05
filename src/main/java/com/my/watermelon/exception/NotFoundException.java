package com.my.watermelon.exception;
// 특정 리소스를 찾을 수 없을 때 던지는 예외
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}