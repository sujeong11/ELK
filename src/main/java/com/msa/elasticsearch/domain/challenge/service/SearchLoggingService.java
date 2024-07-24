package com.msa.elasticsearch.domain.challenge.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.msa.elasticsearch.domain.challenge.exception.ElasticsearchOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchLoggingService {

  private final ElasticsearchClient elasticsearchClient;

  public void logSearchQuery(String searchWord) {
    Map<String, Object> jsonMap = new HashMap<>();
    jsonMap.put("search_word", searchWord);
    jsonMap.put("timestamp", OffsetDateTime.now());

    IndexRequest<Map<String, Object>> indexRequest = new IndexRequest.Builder<Map<String, Object>>()
        .index("search_logs")
        .document(jsonMap)
        .build();

    try {
      elasticsearchClient.index(indexRequest);
    } catch (Exception ex) {
      throw new ElasticsearchOperationException();
    }
  }
}
