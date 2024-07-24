package com.msa.elasticsearch.global.exception;

import com.msa.elasticsearch.global.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(BusinessException ex) {
    log.error("[Business_Exception]: {}", convertExceptionStackTraceToString(ex));

    return ResponseEntity
        .status(ex.getHttpStatus())
        .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("[Exception]: {}", convertExceptionStackTraceToString(ex));

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse(
            ErrorCode.UNHANDLED.getCode(),
            ErrorCode.UNHANDLED.getErrorMessage()
        ));
  }

  private static String convertExceptionStackTraceToString(Exception ex) {
    StringWriter stringWriter = new StringWriter();
    ex.printStackTrace(new PrintWriter(stringWriter));
    return String.valueOf(stringWriter);
  }
}
