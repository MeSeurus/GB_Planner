package com.canban.web.core.services;

import com.canban.web.core.dto.TaskDetails;
import com.canban.web.core.entities.Event;
import com.canban.web.core.entities.Task;
import com.canban.web.core.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> findTaskByUsername(String username) {
        return taskRepository.findTasksByUserName(username);
    }

    public void createTask(String username, TaskDetails taskDetails) {
        Task task = Task.taskBuilder()
                .title(taskDetails.getTitle())
                .content(taskDetails.getContent())
                .username(taskDetails.getUsername())
                .eventDate(taskDetails.getEventDate())
                .kanbanName(taskDetails.getKanbanName())
                .build();
        taskRepository.save(task);
    }

    public List<Event> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }
}
