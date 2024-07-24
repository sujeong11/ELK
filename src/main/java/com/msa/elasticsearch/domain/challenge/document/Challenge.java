package com.msa.elasticsearch.domain.challenge.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "challenge")
public class Challenge {

  @Id
  @Field(type = FieldType.Long)
  private Long id;

  @Field(type = FieldType.Text)
  private String name;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private OffsetDateTime createdAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private OffsetDateTime updatedAt;

  @Field(type = FieldType.Date, format = DateFormat.date_time)
  private OffsetDateTime deletedAt;
}
