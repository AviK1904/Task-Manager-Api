package com.aviralproject.TaskManager.service.impl;

import com.aviralproject.TaskManager.dto.AddTaskRequestdto;
import com.aviralproject.TaskManager.dto.Taskdto;
import com.aviralproject.TaskManager.entity.Task;
import com.aviralproject.TaskManager.entity.User;
import com.aviralproject.TaskManager.exception.TaskNotFoundException;
import com.aviralproject.TaskManager.exception.UserNotFoundException;
import com.aviralproject.TaskManager.repository.TaskRepository;
import com.aviralproject.TaskManager.repository.UserRepository;
import com.aviralproject.TaskManager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;



    @Override
    public Page<Taskdto> getAllTasks(int page, int size, String sortBy, String order) {
        Sort.Direction direction= order.equalsIgnoreCase("asc")?Sort.Direction.ASC:Sort.Direction.DESC;
        Pageable pageable= PageRequest.of(page,size, Sort.by(direction, sortBy));

        return taskRepository.findAll(pageable)
                .map(task -> new Taskdto(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getCreatedAt(),
                        task.getUpdatedAt(),
                        task.getAssignedTo().getTimezone()
                ));
    }

    @Override
    public Taskdto getTaskById(Long id) {
        Task task= taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("task not found by ID: "+id));
//        return new Taskdto(task.getId(),task.getTitle(),task.getDescription(),task.getStatus(),task.getCreatedAt(),task.getUpdatedAt());
        return modelMapper.map(task,Taskdto.class);
    }

    @Override
    public void deleteTaskById(Long id) {
        if(!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task does not exist by id "+id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Taskdto updateTask(Long id, AddTaskRequestdto request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + id));

        User user = userRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getAssignedToId()));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setUpdatedAt(Instant.now());
        task.setAssignedTo(user);

        Task updated = taskRepository.save(task);
        return modelMapper.map(updated, Taskdto.class);
    }

    @Override
    public Taskdto createNewTask(AddTaskRequestdto request) {
        User user = userRepository.findById(request.getAssignedToId())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getAssignedToId()));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        task.setAssignedTo(user);

        Task saved = taskRepository.save(task);
        return modelMapper.map(saved, Taskdto.class);
    }
}
