package com.example.demo.config

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerConfig {

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception) = ResponseEntity.badRequest().body(ex.message)
}