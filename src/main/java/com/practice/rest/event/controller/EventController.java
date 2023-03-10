package com.practice.rest.event.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.practice.rest.event.EventResource;
import com.practice.rest.event.EventValidator;
import com.practice.rest.event.domain.Event;
import com.practice.rest.event.domain.EventDto;
import com.practice.rest.event.domain.EventRepository;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = HAL_JSON_VALUE)
@RestController
public class EventController {

  private final EventRepository eventRepository;
  private final ModelMapper modelMapper;
  private final EventValidator eventValidator;

  @PostMapping
  public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
    if (errors.hasErrors()) { // 바인딩 시 에러 발생 -> Bad Request
      return ResponseEntity.badRequest().build();
    }

    eventValidator.validate(eventDto, errors);
    if (errors.hasErrors()) { // Valid 로 잡아내기 어려운 로직에서 잘못된 입력 에러 발생 -> Bad Request
      return ResponseEntity.badRequest().build();
    }

    Event event = modelMapper.map(eventDto, Event.class);
    Event newEvent = eventRepository.save(event);

    URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();

    /**
     * EventResource 를 빼서 여러 링크를 추가할 수 있다.
     */
    EventResource eventResource = new EventResource(newEvent);
    eventResource.add(linkTo(EventController.class)
        .withRel("query-events")); // = /api/events
    eventResource.add(linkTo(EventController.class)
        .slash(newEvent.getId()).withRel("update-event")); // = /api/events/{id}
    eventResource.add(linkTo(EventController.class)
        .slash(newEvent.getId()).withSelfRel()); // = /api/events/{id}

    // Hateoas 를 위해 EventResource 에 담아서 반환해줘야한다.
    return ResponseEntity.created(createdUri).body(eventResource);
  }

}
