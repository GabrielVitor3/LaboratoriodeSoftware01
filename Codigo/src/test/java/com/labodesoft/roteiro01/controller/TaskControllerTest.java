package com.labodesoft.roteiro01.controller;

import com.labodesoft.roteiro01.entity.Task;
import com.labodesoft.roteiro01.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

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
    void listAll() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(taskRepository.findAll()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = taskController.listAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Task", response.getBody().get(0).getDescription());
    }

    @Test
    void listAllNoContent() {
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Task>> response = taskController.listAll();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void create() {
        Task newTask = new Task("New Task");
        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        ResponseEntity<Task> response = taskController.create(newTask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Task", response.getBody().getDescription());
    }

    @Test
    void update() {
        Task updatedTask = new Task("Updated Task");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.update(1L, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Task", response.getBody().getDescription());
        assertTrue(response.getBody().getCompleted());
    }

    @Test
    void updateNotFound() {
        Task updatedTask = new Task("Updated Task");
        updatedTask.setCompleted(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.update(1L, updatedTask);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteById() {
        doNothing().when(taskRepository).deleteById(1L);

        ResponseEntity<HttpStatus> response = taskController.deleteById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdException() {
        doThrow(new RuntimeException()).when(taskRepository).deleteById(1L);

        ResponseEntity<HttpStatus> response = taskController.deleteById(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void markAsCompleted() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        ResponseEntity<Task> response = taskController.markAsCompleted(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getCompleted());
    }

    @Test
    void markAsCompletedNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Task> response = taskController.markAsCompleted(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
