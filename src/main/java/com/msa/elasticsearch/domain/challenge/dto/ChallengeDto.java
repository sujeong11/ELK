package com.msa.elasticsearch.domain.challenge.dto;

import com.msa.elasticsearch.domain.challenge.document.Challenge;

import java.time.OffsetDateTime;

public record ChallengeDto(
    Long id,
    String name,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime deletedAt
) {

  public static ChallengeDto from(Challenge challenge) {
    return new ChallengeDto(
        challenge.getId(),
        challenge.getName(),
        challenge.getCreatedAt(),
        challenge.getUpdatedAt(),
        challenge.getDeletedAt()
    );
  }
}
