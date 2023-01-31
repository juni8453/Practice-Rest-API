package com.practice.rest.event;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.practice.rest.event.controller.EventController;
import com.practice.rest.event.domain.Event;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

@Getter
public class EventResource extends EntityModel<Event> {

  // TODO 미리 정의되어있는 @JsonUnwrapped 를 활용해 응답 시 중괄호 벗겨내기
//  public EventResource(Event event, Link... links) {
//    super(event, Arrays.asList(links));
//    add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
//  }

  // TODO @JsonUnwrapped 을 직접 정의해서 응답 시 중괄호 벗겨내기
  @JsonUnwrapped
  private final Event event;

  public EventResource(Event event) {
    this.event = event;
  }
}
