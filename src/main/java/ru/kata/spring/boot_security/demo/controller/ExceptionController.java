package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.configs.ExceptionHandler.NoSuchUserException;
import ru.kata.spring.boot_security.demo.configs.ExceptionHandler.UserIncorrectData;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handlerException(NoSuchUserException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }
}
