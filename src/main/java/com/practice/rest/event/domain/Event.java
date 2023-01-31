package com.practice.rest.event.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@Entity
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String description;
  private LocalDateTime beginEnrollmentDateTime; // 이벤트 등록 시작 시간
  private LocalDateTime closeEnrollmentDateTime; // 이벤트 등록 종료 시간
  private LocalDateTime beginEventDateTime; // 이벤트 시작 시간
  private LocalDateTime endEventDateTime; // 이벤트 종료 시간
  private String location; // 장소 (없다면 온라인 모임)
  private int limitOfEnrollment;
  private boolean offline;
  private boolean free;

  @Enumerated(EnumType.STRING)
  private EventStatus eventStatus;

}
