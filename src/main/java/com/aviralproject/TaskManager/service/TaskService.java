package com.aviralproject.TaskManager.service;

import com.aviralproject.TaskManager.dto.AddTaskRequestdto;
import com.aviralproject.TaskManager.dto.Taskdto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

   Taskdto createNewTask(AddTaskRequestdto addTaskRequestdto);

    Page<Taskdto> getAllTasks(int page, int size, String sortBy, String order);

    Taskdto getTaskById(Long id);

    void deleteTaskById(Long id);

    Taskdto updateTask(Long id, AddTaskRequestdto addTaskRequestdto);
}
