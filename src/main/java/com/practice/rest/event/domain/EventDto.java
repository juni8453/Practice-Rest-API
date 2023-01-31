package com.practice.rest.event.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

  @NotEmpty
  private String name;

  @NotEmpty
  private String description;

  @NotNull
  private LocalDateTime beginEnrollmentDateTime; // 이벤트 등록 시작 시간

  @NotNull
  private LocalDateTime closeEnrollmentDateTime; // 이벤트 등록 종료 시간

  @NotNull
  private LocalDateTime beginEventDateTime; // 이벤트 시작 시간

  @NotNull
  private LocalDateTime endEventDateTime; // 이벤트 종료 시간

  private String location; // 장소 (없다면 온라인 모임)

  @Min(0)
  private int limitOfEnrollment;
}
