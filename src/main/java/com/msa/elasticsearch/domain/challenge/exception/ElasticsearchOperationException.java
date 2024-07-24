package com.msa.elasticsearch.domain.challenge.exception;

import com.msa.elasticsearch.global.exception.BusinessException;
import com.msa.elasticsearch.global.exception.ErrorCode;

public class ElasticsearchOperationException extends BusinessException {

  public ElasticsearchOperationException() {
    super(ErrorCode.ELASTICSEARCH_REQUEST_FAILED);
  }
}
