package com.msa.elasticsearch.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

  UNHANDLED(1000, "알 수 없는 서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

  ELASTICSEARCH_REQUEST_FAILED(1000, "elasticsearch와 통신해 작업을 처리하는 과정에서 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  private final Integer code;
  private final String errorMessage;
  private final HttpStatus httpStatus;
}
