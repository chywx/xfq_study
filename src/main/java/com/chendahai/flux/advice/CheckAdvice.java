package com.chendahai.flux.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class CheckAdvice {
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException e){
        return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    // 校验异常转换为字符串
    private String toStr(WebExchangeBindException ex){
        return ex.getFieldErrors().stream()
                .map(e -> e.getField() + ":" +e.getDefaultMessage())
                .reduce("",(s1,s2) -> s1 + "\n" +s2);
    }
}
