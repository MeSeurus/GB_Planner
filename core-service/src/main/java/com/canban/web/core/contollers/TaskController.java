package com.canban.web.core.contollers;

import com.canban.api.core.TaskDto;
import com.canban.web.core.dto.TaskDetailsRq;
import com.canban.web.core.enums.Priority;
import com.canban.web.core.enums.State;
import com.canban.web.core.mapper.TaskMapper;
import com.canban.web.core.services.TaskService;
import com.canban.web.core.validators.TaskValidator;
import io.swagger.v3.oas.annotations.Operation;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@Tag(name = "Задачи", description = "Методы работы с задачами")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskValidator taskValidator;

    @GetMapping()
    @Operation(
            summary = "Запрос на получение всех заданий по имени пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskDto.class))
                    )
            }
    )
    public List<TaskDto> findEventsByUsername(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        return taskService.findTaskByUsername(username).stream().map(taskMapper::entityToDto).collect(Collectors.toList());
    }

    @PostMapping()
    @Operation(
            summary = "Запрос на создание нового задания",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )

    public void createTask(@RequestHeader @Parameter(description = "Список пользователей", required = true) String username, @RequestBody TaskDetailsRq taskDetailsRq){
        taskValidator.validate(taskDetailsRq);
        taskService.createTask(username, taskDetailsRq);

    }

    @PatchMapping("/change/title")
    public void changeTitle(@RequestBody Long id, @RequestBody String title){
        taskService.changeTitle(id, title);
    }

    @PatchMapping("/change/content")
    public void changeContent(@RequestBody Long id, @RequestBody String content){
        taskService.changeContent(id, content);
    }

    @PatchMapping("/change/username")
    public void changeUsername(@RequestParam Long id, @RequestParam String username){
        taskValidator.validateUser(id, username);
        taskService.changeUsername(id, username);
    }

    @PatchMapping("/change/begin_date")
    public void changeBeginDate(@RequestBody Long id, @RequestBody LocalDateTime beginDate){
        taskService.changeBeginDate(id, beginDate);
    }

    @PatchMapping("/change/end_date")
    public void changeEndDate(@RequestBody Long id, @RequestBody LocalDateTime endDate){
        taskService.changeEndDate(id, endDate);
    }

    @PatchMapping("/change/state")
    public void changeEndDate(@RequestBody Long id, @RequestBody State state){
        taskService.changeState(id, state);
    }

    @PatchMapping("/change/priority")
    public void changePriority(@RequestBody Long id, @RequestBody Priority priority){
        taskService.changePriority(id, priority);
    }

    @PatchMapping("/change/actual_end_date")
    public void changeActualEndDate(@RequestBody Long id, @RequestBody LocalDateTime beginDate){
        taskService.changeActualEndDate(id, beginDate);
    }

    @PatchMapping("/change/kanban_name")
    public void changeKanbanName(@RequestBody Long id, @RequestBody String kanbanName){
        taskService.changeKanbanName(id, kanbanName);
    }
}