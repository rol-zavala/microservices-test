package com.test.at.account_transaction_service.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> notFound(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,String>> business(BusinessException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("code","SALDO_NO_DISPONIBLE","message", ex.getMessage()));
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String,String>> badReq(Exception ex){
        return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
    }
}
