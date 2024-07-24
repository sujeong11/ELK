package com.msa.elasticsearch.domain.challenge.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.NamedValue;
import com.msa.elasticsearch.domain.challenge.document.Challenge;
import com.msa.elasticsearch.domain.challenge.dto.ChallengeDto;
import com.msa.elasticsearch.domain.challenge.exception.ElasticsearchOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChallengeService {

  private final ElasticsearchClient elasticsearchClient;

  public List<ChallengeDto> searchByName(String name, int searchAfter, int size) {
    Query query = createQuery(name);
    SearchRequest searchRequest = createSearchRequest(query, searchAfter, size, "challenge", "id", SortOrder.Asc);

    SearchResponse<Challenge> searchResponse = executeSearch(searchRequest, Challenge.class);

    return searchResponse.hits().hits().stream()
        .map(Hit::source)
        .filter(Objects::nonNull)
        .map(ChallengeDto::from)
        .collect(Collectors.toList());
  }

  public List<String> getPopularSearches() {
    TermsAggregation termsAggregation = createTermsAggregation("search_word.keyword", 10);
    Aggregation aggregation = Aggregation.of(aggregationBuilder -> aggregationBuilder.terms(termsAggregation));
    SearchRequest searchRequest = createSearchRequestWithAggregation(aggregation, "search_logs");

    SearchResponse<Void> searchResponse = executeSearch(searchRequest, Void.class);

    Aggregate aggregate = searchResponse.aggregations().get("popular_searches");
    StringTermsAggregate termsAggregate = aggregate.sterms();

    return termsAggregate.buckets().array().stream()
        .map(bucket -> bucket.key().stringValue())
        .collect(Collectors.toList());
  }

  private Query createQuery(String name) {
    return Query.of(queryBuilder -> queryBuilder
        .matchPhrasePrefix(matchPhrasePrefixBuilder -> matchPhrasePrefixBuilder
            .field("name")
            .query(name)
        )
    );
  }

  private TermsAggregation createTermsAggregation(String field, int size) {
    return AggregationBuilders.terms()
        .field(field)
        .size(size)
        .order(Collections.singletonList(NamedValue.of("_count", SortOrder.Desc)))
        .build();
  }

  private SearchRequest createSearchRequest(Query query, int searchAfter, int size, String index, String sortField, SortOrder sortOrder) {
    return new SearchRequest.Builder()
        .index(index)
        .query(query)
        .size(size)
        .sort(s -> s.field(f -> f.field(sortField).order(sortOrder)))
        .searchAfter(FieldValue.of(searchAfter))
        .build();
  }

  private SearchRequest createSearchRequestWithAggregation(Aggregation aggregation, String index) {
    return new SearchRequest.Builder()
        .index(index)
        .size(0)
        .aggregations("popular_searches", aggregation)
        .build();
  }

  private <T> SearchResponse<T> executeSearch(SearchRequest searchRequest, Class<T> clazz) {
    try {
      return elasticsearchClient.search(searchRequest, clazz);
    } catch (Exception ex) {
      throw new ElasticsearchOperationException();
    }
  }
}
