package com.canban.web.core.contollers;

import com.canban.api.core.EventDto;
import com.canban.api.errors.FieldsValidationError;
import com.canban.web.core.dto.EventDetailsForSearchRq;
import com.canban.web.core.dto.EventDetailsRq;
import com.canban.web.core.mapper.EventMapper;
import com.canban.web.core.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    private final EventService eventService;

    private final EventMapper eventMapper;

    @Operation(
            summary = "Запрос на получение всех ивентов пользователя с возможностью фильтрации",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @PostMapping()
    public List<EventDto> searchAllEvents(
            @RequestHeader @Parameter(description = "Имя пользователя", required = true) String username,
            @RequestBody @Parameter(description = "Модель для поиска событий", required = false) EventDetailsForSearchRq eventDetailsForSearchRq) {
        return eventService.searchAllEvents(username, eventDetailsForSearchRq)
                .stream()
                .map(e -> eventMapper.entityToDto(e))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    @Operation(
            summary = "Запрос на создание нового события",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Ошибка валидации", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = FieldsValidationError.class))
                    )
            }
    )
    public void createEvent(@RequestHeader @Parameter(description = "Список пользователей", required = true) String username,
                            @RequestBody @Parameter(description = "Модель деталей события", required = true) EventDetailsRq eventDetailsRq) {
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

//    @PostMapping("/{id}/{username}")
//    public void addUserToEvent(@RequestHeader String username, @PathVariable("id") Long id, @PathVariable("username") String usernameToAdd) {
//        eventService.addUserToEvent(usernameToAdd, id);
//    }

//    @DeleteMapping("/{id}/{username}")
//    public void removeUserFromEvent(@RequestHeader String username, @PathVariable("id") Long id, @PathVariable("username") String usernameToRemove) {
//        eventService.removeUserFromEvent(usernameToRemove, id);
//    }

    @PatchMapping("/change/title")
    @Operation(
            summary = "Запрос на изменения названия события по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Событие не найдено", responseCode = "404"
                    )
            }
    )
    public void changeTitle(@RequestBody @Parameter(description = "Модель события", required = true) EventDto requestBody) {
        eventService.changeTitle(requestBody.getId(), requestBody.getTitle());
    }

    @PatchMapping("/change/content")
    @Operation(
            summary = "Запрос на изменения описания события по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Событие не найдено", responseCode = "404"
                    )
            }
    )
    public void changeContent(@RequestBody @Parameter(description = "Модель события", required = true) EventDto requestBody) {
        eventService.changeContent(requestBody.getId(), requestBody.getContent());
    }

    @PatchMapping("/change/begin_date")
    @Operation(
            summary = "Запрос на изменения начала события по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Событие не найдено", responseCode = "404"
                    )
            }
    )
    public void changeBeginDate(@RequestBody @Parameter(description = "Модель события", required = true) EventDto requestBody) {
        eventService.changeBeginDate(requestBody.getId(), requestBody.getBeginDate());
    }

    @PatchMapping("/change/end_date")
    @Operation(
            summary = "Запрос на изменения окончания события по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Событие не найдено", responseCode = "404"
                    )
            }
    )
    public void changeEndDate(@RequestBody @Parameter(description = "Модель события", required = true) EventDto requestBody) {
        eventService.changeEndDate(requestBody.getId(), requestBody.getEndDate());
    }

}