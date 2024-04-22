package com.labodesoft.roteiro01;

import com.labodesoft.roteiro01.controller.TaskController;
import com.labodesoft.roteiro01.entity.Task;
import com.labodesoft.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAllTasks() {
        // Mocking data
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", false));
        tasks.add(new Task(2L, "Task 2", true));

        when(taskRepository.findAll()).thenReturn(tasks);

        // Calling the controller method
        ResponseEntity<List<Task>> response = taskController.listAll();

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testCreateTask() {
        // Mocking data
        Task task = new Task(1L, "New Task", false);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Calling the controller method
        ResponseEntity<Task> response = taskController.create(task);

        // Verifying the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }
    @Test
    public void testUpdateTask() {
        // Mocking data
        Task existingTask = new Task(1L, "Existing Task", false);
        Task updatedTask = new Task(1L, "Updated Task", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Calling the controller method
        ResponseEntity<Task> response = taskController.update(1L, updatedTask);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask.getDescription(), response.getBody().getDescription());
        assertTrue(response.getBody().isCompleted());
    }

    @Test
    public void testMarkAsCompleted() {
        // Mocking data
        Task task = new Task(1L, "Task", false);
        Task completedTask = new Task(1L, "Task", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(completedTask);

        // Calling the controller method
        ResponseEntity<Task> response = taskController.markAsCompleted(1L);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isCompleted());
    }


}
