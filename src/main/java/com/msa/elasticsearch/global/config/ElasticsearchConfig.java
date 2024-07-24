package com.msa.elasticsearch.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@RequiredArgsConstructor
@Configuration
@EnableElasticsearchRepositories(basePackages = "org.springframework.data.elasticsearch.repository")
public class ElasticsearchConfig {

  private final ObjectMapper objectMapper;

  @Value("${spring.elasticsearch.uris}")
  private String esHost;

  @Bean
  public RestClient restClient() {
    return RestClient.builder(HttpHost.create(esHost)).build();
  }

  @Bean
  public ElasticsearchClient elasticsearchClient() {
    RestClientTransport transport = new RestClientTransport(restClient(), new JacksonJsonpMapper(objectMapper));
    return new ElasticsearchClient(transport);
  }

  @Bean
  public ElasticsearchTemplate elasticsearchTemplate() {
    return new ElasticsearchTemplate(elasticsearchClient());
  }
}
