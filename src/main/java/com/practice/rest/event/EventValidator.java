package com.practice.rest.event;

import com.practice.rest.event.domain.EventDto;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {

  /**
   * @Valid 로 검증하기 힘든 로직을 따로 검증 로직을 구현해 검증할 수 있다.
   */
  public void validate(EventDto eventDto, Errors errors) {
    LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

    if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
        endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
        endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {

      errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
    }
  }
}
