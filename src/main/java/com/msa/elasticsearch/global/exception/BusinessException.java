package com.msa.elasticsearch.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final Integer code;
  private final String message;
  private final HttpStatus httpStatus;

  public BusinessException(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getErrorMessage();
    this.httpStatus = errorCode.getHttpStatus();
  }

  public BusinessException(ErrorCode errorCode, String optionalMessage) {
    this.code = errorCode.getCode();
    this.message = errorCode.getErrorMessage() + " " + optionalMessage;
    this.httpStatus = errorCode.getHttpStatus();
  }
}
