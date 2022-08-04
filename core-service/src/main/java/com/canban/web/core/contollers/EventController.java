package com.canban.web.core.contollers;

import com.canban.api.analytics.EventsAnalyticsDtoWithList;
import com.canban.api.core.EventDto;
import com.canban.web.core.dto.EventDetailsRq;
import com.canban.web.core.mapper.EventAnalyticsMapper;
import com.canban.web.core.mapper.EventMapper;
import com.canban.web.core.services.EventService;
import com.canban.web.core.validators.EventValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/events")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@Tag(name = "События", description = "Методы работы с событиями")
@Slf4j
public class EventController {

    private final EventValidator eventValidator;

    private final EventService eventService;

    private final EventMapper eventMapper;

    private final EventAnalyticsMapper eventAnalyticsMapper;

    @GetMapping("/analytics")
    @Operation(
            summary = "Запрос на получение всех событий всех пользователей для микросервиса аналитики за текущее время работы Core-MC",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = EventsAnalyticsDtoWithList.class))
                    )
            }
    )
    public EventsAnalyticsDtoWithList findAllEventsForAnalytics() {
        EventsAnalyticsDtoWithList eventsAnalyticsDtoWithList =
                new EventsAnalyticsDtoWithList(eventService.findAllForAnalytics()
                        .stream()
                        .map(eventAnalyticsMapper::entityToDto)
                        .collect(Collectors.toList()));
        eventService.clearList();
        return eventsAnalyticsDtoWithList;
    }

    @Operation(
            summary = "Запрос на получение всех ивентов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = List.class))
                    )
            }
    )
    @GetMapping()
    public List<EventDto> findAllEventsByUsername(
            @RequestHeader @Parameter(description = "Имя пользователя", required = true) String username,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "max_begin_date", required = false) String maxBeginDate,
            @RequestParam(name = "min_begin_date", required = false) String minBeginDate,
            @RequestParam(name = "max_end_date", required = false) String maxEndDate,
            @RequestParam(name = "min_end_date", required = false) String minEndDate
    ) {
        return eventService.findAll(username, titlePart, maxBeginDate, minBeginDate, maxEndDate, minEndDate)
                .stream()
                .map(e -> eventMapper.entityToDto(e))
                .collect(Collectors.toList());
    }

    @PostMapping()
    @Operation(
            summary = "Запрос на создание нового события",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    public void createEvent(@RequestHeader @Parameter(description = "Список пользователей", required = true) String username, @RequestBody EventDetailsRq eventDetailsRq) {
        eventValidator.validate(eventDetailsRq);
        eventService.createEvent(username, eventDetailsRq);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Запрос на удаление события по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    public void deleteDyId(@PathVariable @Parameter(description = "Идентификатор события", required = true) Long id) {
        eventService.deleteById(id);
    }

    @PostMapping("/{id}/{username}")
    public void addUserToEvent(@RequestHeader String username, @PathVariable("id") Long id, @PathVariable("username") String usernameToAdd) {
        eventService.addUserToEvent(usernameToAdd, id);
    }

    @DeleteMapping("/{id}/{username}")
    public void removeUserFromEvent(@RequestHeader String username, @PathVariable("id") Long id, @PathVariable("username") String usernameToRemove) {
        eventService.removeUserFromEvent(usernameToRemove, id);
    }

    @PatchMapping("/change/title")
    public void changeTitle(@RequestBody EventDto requestBody) {
        eventService.changeTitle(requestBody.getId(), requestBody.getTitle());
    }

    @PatchMapping("/change/content")
    public void changeContent(@RequestBody EventDto requestBody) {
        eventService.changeContent(requestBody.getId(), requestBody.getContent());
    }

    @PatchMapping("/change/begin_date")
    public void changeBeginDate(@RequestBody EventDto requestBody) {
        eventService.changeBeginDate(requestBody.getId(), requestBody.getBeginDate());
    }

    @PatchMapping("/change/end_date")
    public void changeEndDate(@RequestBody EventDto requestBody) {
        eventService.changeEndDate(requestBody.getId(), requestBody.getEndDate());
    }

}