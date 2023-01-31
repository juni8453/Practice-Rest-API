package com.practice.rest.event.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.rest.event.domain.Event;
import com.practice.rest.event.domain.EventDto;
import com.practice.rest.event.domain.EventStatus;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class EventApiControllerTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  // 스프링 부트를 사용하기 때문에 ObjectMapper 가 자동 Bean 으로 등록됐기 때문에 사용 가능
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper mapper;

  @Test
  @DisplayName("정상적으로 이벤트를 생성하는 테스트")
  void createEvent() throws Exception {
    EventDto eventDto = EventDto.builder()
        .name("Spring")
        .description("REST API Development with Spring")
        .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 30, 0, 0))
        .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
        .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
        .endEventDateTime(LocalDateTime.of(2023, 2, 2, 0, 0))
        .limitOfEnrollment(100)
        .location("강남역 4번 출구")
        .build();

    mockMvc.perform(post("/api/events/")
            .contentType(MediaType.APPLICATION_JSON) // JSON 타입 요청을 보낸다.
            .accept(MediaTypes.HAL_JSON_VALUE) // HAL JSON 타입 응답을 원한다.
            .content(mapper.writeValueAsString(eventDto)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("id").value(Matchers.not(100)))
        .andExpect(jsonPath("free").value(Matchers.not(true)));

  }

  // yml or properties 에서 받을 수 없는 프로퍼티를 받는 경우 Bad Request 가 동작하도록 간단하게 설정할 수 있다.
  // 무시하거나 이렇게 예외를 던지는 두 가지 방식 중 알아서 사용하면 된다.
  @Test
  @DisplayName("입력받을 수 없는 값을 사용한 경우 404 예외가 발생하는 테스트")
  void createEvent_Bad_Request() throws Exception {
    Event event = Event.builder()
        .id(100)
        .name("Spring")
        .description("REST API Development with Spring")
        .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 30, 0, 0))
        .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
        .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
        .endEventDateTime(LocalDateTime.of(2023, 2, 2, 0, 0))
        .limitOfEnrollment(100)
        .location("강남역 4번 출구")
        .free(true)
        .offline(false)
        .eventStatus(EventStatus.PUBLISHED)
        .build();

    mockMvc.perform(post("/api/events/")
            .contentType(MediaType.APPLICATION_JSON) // JSON 타입 요청을 보낸다.
            .accept(MediaTypes.HAL_JSON_VALUE) // HAL JSON 타입 응답을 원한다.
            .content(mapper.writeValueAsString(event)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  // 입력 값이 이상한 경우 Bad Request 발생
  @Test
  @DisplayName("입력 값이 비어있는 경우 404 예외가 발생하는 테스트")
  public void createEvent_Bad_Request_Empty_Input() throws Exception {
    EventDto eventDto = EventDto.builder().build();

    mockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(eventDto)))
        .andExpect(status().isBadRequest());
  }

  // 이벤트 시작 시간이 끝나는 시간보다 느린 이상한 값이 들어온 경우 Bad Request 발생
  @Test
  @DisplayName("입력 값이 잘못된 경 경우 404 예외가 발생하는 테스트")
  public void createEvent_Bad_Request_Wrong_Input() throws Exception {
    EventDto eventDto = EventDto.builder()
        .name("Spring")
        .description("REST API Development with Spring")
        .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 30, 0, 0))
        .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
        .beginEventDateTime(LocalDateTime.of(2023, 2, 3, 0, 0))
        .endEventDateTime(LocalDateTime.of(2023, 2, 2, 0, 0))
        .limitOfEnrollment(100)
        .location("강남역 4번 출구")
        .build();

    mockMvc.perform(post("/api/events")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(eventDto)))
        .andExpect(status().isBadRequest());
  }
}
