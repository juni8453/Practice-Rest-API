package com.practice.rest.event.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.rest.event.domain.Event;
import java.time.LocalDateTime;
import org.hamcrest.Matchers;
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
  void createEvent() throws Exception {
    Event event = Event.builder()
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
            .content(mapper.writeValueAsString(event)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("id").value(Matchers.not(100)))
        .andExpect(jsonPath("free").value(Matchers.not(true)));

  }
}
