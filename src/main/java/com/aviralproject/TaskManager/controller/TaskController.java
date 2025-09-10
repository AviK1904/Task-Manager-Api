package com.aviralproject.TaskManager.controller;

import com.aviralproject.TaskManager.dto.AddTaskRequestdto;
import com.aviralproject.TaskManager.dto.Taskdto;
import com.aviralproject.TaskManager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<Taskdto> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order) {
        return taskService.getAllTasks(page, size, sortBy,order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taskdto> getTaskById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Taskdto> createNewTask(@RequestBody AddTaskRequestdto addTaskRequestdto){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createNewTask(addTaskRequestdto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id)
    {
        taskService.deleteTaskById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Taskdto> updateTask(@PathVariable Long id,
                                              @RequestBody AddTaskRequestdto addTaskRequestdto)
    {
        return ResponseEntity.ok(taskService.updateTask(id,addTaskRequestdto));
    }

}
