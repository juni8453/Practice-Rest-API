package com.practice.rest.event.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EventTest {

  @Test
  @DisplayName("Event Builder 가 있다면 성공한다.")
  void builder() {
    Event event = Event.builder().build();

    assertThat(event).isNotNull();
  }

  @Test
  @DisplayName("Event Entity 는 Java Bean 규약을 준수한다.")
  void javaBean() {
    String name = "Event";
    String description = "Spring";

    Event event = new Event();
    event.setName("Event");
    event.setDescription("Spring");

    assertThat(event.getName()).isEqualTo(name);
    assertThat(event.getDescription()).isEqualTo(description);
  }
}