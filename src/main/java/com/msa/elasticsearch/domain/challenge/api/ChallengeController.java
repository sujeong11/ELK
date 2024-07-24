package com.msa.elasticsearch.domain.challenge.api;

import com.msa.elasticsearch.domain.challenge.dto.ChallengeDto;
import com.msa.elasticsearch.domain.challenge.service.ChallengeService;
import com.msa.elasticsearch.domain.challenge.service.SearchLoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "챌린지 검색 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

  private final ChallengeService challengeService;
  private final SearchLoggingService searchLoggingService;

  @Operation(
      summary = "특정 검색어가 포함된 챌린지 검색",
      description = """
          <p>검색어를 전달 받아 해당 검색어가 이름에 포함되는 챌린지 목록을 조회합니다.</p>
          <ul>
            <li>searchAfter: 검색을 시작할 챌린지 document의 PK</li>
            <li>size: 검색 개수</li>
          </ul>
          """
  )
  @GetMapping("/search")
  public List<ChallengeDto> searchByName(
      @RequestParam(name = "name") String name,
      @RequestParam(name = "searchAfter") int searchAfter,
      @RequestParam(name = "size") int size
  ) {
    searchLoggingService.logSearchQuery(name);
    return challengeService.searchByName(name, searchAfter, size);
  }

  @Operation(
      summary = "실시간 인기 검색어 10개 조회",
      description = "실시간으로 가장 많이 검색되고 있는 검색어 10개를 조회해 반환합니다."
  )
  @GetMapping("/search/popularity")
  public List<String> getPopularChallenge() {
    return challengeService.getPopularSearches();
  }
}
