package com.practice.rest.event.controller;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.practice.rest.event.domain.Event;
import com.practice.rest.event.domain.EventRepository;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = HAL_JSON_VALUE)
@RestController
public class EventController {

  private final EventRepository eventRepository;

  @PostMapping
  public ResponseEntity createEvent(@RequestBody Event event) {
    Event newEvent = eventRepository.save(event);
    URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();

    return ResponseEntity.created(createdUri).body(event);
  }

}
