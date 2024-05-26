package com.labodesoft.roteiro01.service;

import com.labodesoft.roteiro01.entity.Task;
import com.labodesoft.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setDescription("Test Task");
        task.setCompleted(false);
    }

    @Test
    void getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getDescription());
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getDescription());
    }

    @Test
    void createTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertEquals("Test Task", result.getDescription());
        assertFalse(result.getCompleted());
    }

    @Test
    void updateTask() {
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertEquals("Updated Task", result.getDescription());
        assertTrue(result.getCompleted());
    }

    @Test
    void updateTaskNotFound() {
        Task updatedTask = new Task();
        updatedTask.setDescription("Updated Task");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Task result = taskService.updateTask(1L, updatedTask);

        assertNull(result);
    }

    @Test
    void deleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void markTaskAsCompleted() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.markTaskAsCompleted(1L);

        assertTrue(result.getCompleted());
    }

    @Test
    void markTaskAsCompletedNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Task result = taskService.markTaskAsCompleted(1L);

        assertNull(result);
    }
}
