package com.aviralproject.TaskManager.Service;

import com.aviralproject.TaskManager.dto.AddTaskRequestdto;
import com.aviralproject.TaskManager.dto.Taskdto;
import com.aviralproject.TaskManager.entity.Task;
import com.aviralproject.TaskManager.entity.User;
import com.aviralproject.TaskManager.Domain.TaskStatus;
import com.aviralproject.TaskManager.exception.TaskNotFoundException;
import com.aviralproject.TaskManager.exception.UserNotFoundException;
import com.aviralproject.TaskManager.repository.TaskRepository;
import com.aviralproject.TaskManager.repository.UserRepository;
import com.aviralproject.TaskManager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private Task task;
    private AddTaskRequestdto addTaskRequestdto;

    @BeforeEach
    void setUp() {
        // Common setup for user and task objects used across tests
        user = new User();
        user.setId(1L);
        user.setTimezone("Asia/Kolkata");
        user.setFirstName("Test");

        addTaskRequestdto = new AddTaskRequestdto("New Task", "Description", TaskStatus.PENDING, 1L);

        task = new Task();
        task.setId(1L);
        task.setTitle("Existing Task");
        task.setDescription("Existing Description");
        task.setStatus(TaskStatus.PENDING);
        task.setAssignedTo(user);
        task.setCreatedAt(Instant.now());
    }

    @Test
    @DisplayName("createNewTask should create and return a task DTO when user exists")
    void testCreateNewTask_Success() {
        // Arrange
        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle(addTaskRequestdto.getTitle());

        Taskdto expectedDto = new Taskdto(1L, "New Task", "Description", TaskStatus.PENDING, null, null, "Asia/Kolkata");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(modelMapper.map(savedTask, Taskdto.class)).thenReturn(expectedDto);

        // Act
        Taskdto result = taskService.createNewTask(addTaskRequestdto);

        // Assert
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        assertEquals("Asia/Kolkata", result.getAssignedUserTimezone());
        verify(userRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("createNewTask should throw UserNotFoundException when user does not exist")
    void testCreateNewTask_UserNotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        addTaskRequestdto.setAssignedToId(99L);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> taskService.createNewTask(addTaskRequestdto));
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("getTaskById should return a task DTO when task is found")
    void testGetTaskById_Found() {
        // Arrange
        Taskdto expectedDto = new Taskdto(1L, "Existing Task", "Existing Description", TaskStatus.PENDING, null, null, "Asia/Kolkata");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(task, Taskdto.class)).thenReturn(expectedDto);

        // Act
        Taskdto result = taskService.getTaskById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Existing Task", result.getTitle());
        verify(taskRepository).findById(1L);
    }

    @Test
    @DisplayName("getTaskById should throw TaskNotFoundException when task is not found")
    void testGetTaskById_NotFound() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    @DisplayName("getAllTasks should return a paginated list of task DTOs")
    void testGetAllTasks() {
        // Arrange
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(taskPage);

        // Act
        Page<Taskdto> result = taskService.getAllTasks(0, 5, "id", "asc");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Existing Task", result.getContent().get(0).getTitle());
        assertEquals("Asia/Kolkata", result.getContent().get(0).getAssignedUserTimezone());
        verify(taskRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("deleteTaskById should remove the task when it exists")
    void testDeleteTaskById_Success() {
        // Arrange
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        // Act
        taskService.deleteTaskById(1L);

        // Assert
        verify(taskRepository).existsById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteTaskById should throw TaskNotFoundException when task does not exist")
    void testDeleteTaskById_NotFound() {
        // Arrange
        when(taskRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(99L));
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("updateTask should update and return task DTO when task and user exist")
    void testUpdateTask_Success() {
        // Arrange
        AddTaskRequestdto updateRequest = new AddTaskRequestdto("Updated Title", "Updated Desc", TaskStatus.COMPLETED, 1L);
        Taskdto expectedDto = new Taskdto(1L, "Updated Title", "Updated Desc", TaskStatus.COMPLETED, null, null, "Asia/Kolkata");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task); // Return the same task object
        when(modelMapper.map(task, Taskdto.class)).thenReturn(expectedDto);

        // Act
        Taskdto result = taskService.updateTask(1L, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());

        // Use ArgumentCaptor to verify the state of the saved entity
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();
        assertEquals("Updated Title", savedTask.getTitle());
        assertEquals(TaskStatus.COMPLETED, savedTask.getStatus());
    }

    @Test
    @DisplayName("updateTask should throw TaskNotFoundException when task does not exist")
    void testUpdateTask_TaskNotFound() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(99L, addTaskRequestdto));
        verify(userRepository, never()).findById(anyLong());
        verify(taskRepository, never()).save(any(Task.class));
    }
}