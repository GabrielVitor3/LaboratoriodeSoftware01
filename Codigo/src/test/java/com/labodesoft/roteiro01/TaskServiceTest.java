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
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListAll() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "Task 1", false));
        taskList.add(new Task(2L, "Task 2", true));

        when(taskRepository.findAll()).thenReturn(taskList);

        ResponseEntity<List<Task>> response = taskController.listAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskList, response.getBody());
    }

    @Test
    void testCreate() {
        Task task = new Task(1L, "Task 1", false);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        ResponseEntity<Task> response = taskController.create(task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void testUpdate() {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Existing Task", false);
        Task updatedTask = new Task(taskId, "Updated Task", true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.update(taskId, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTask.getDescription(), response.getBody().getDescription());
        assertEquals(updatedTask.getCompleted(), response.getBody().getCompleted());
    }

    @Test
    void testDeleteById() {
        Long taskId = 1L;

        ResponseEntity<HttpStatus> response = taskController.deleteById(taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testMarkAsCompleted() {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Existing Task", false);
        Task completedTask = new Task(taskId, "Existing Task", true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(completedTask);

        ResponseEntity<Task> response = taskController.markAsCompleted(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().getCompleted());
    }
}
