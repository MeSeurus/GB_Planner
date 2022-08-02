package com.canban.web.analytics.controllers;

import com.canban.api.analytics.TasksAnalyticsDto;
import com.canban.web.analytics.services.TasksAnalyticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks/analytics")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@Tag(name = "Аналитика задач", description = "Методы работы с аналитикой задач")
public class TasksAnalyticsController {

    private final TasksAnalyticsService tasksAnalyticsService;

    @PostMapping
    public void addEventsAnalytics(@RequestBody List<TasksAnalyticsDto> tasksAnalyticsDtoList) {
        tasksAnalyticsService.addTasksAnalytics(tasksAnalyticsDtoList);
    }

}
