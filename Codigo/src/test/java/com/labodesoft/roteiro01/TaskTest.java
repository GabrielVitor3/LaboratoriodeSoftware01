//package com.labodesoft.roteiro01;
//
//import com.labodesoft.roteiro01.controller.TaskController;
//import com.labodesoft.roteiro01.entity.Task;
//import com.labodesoft.roteiro01.enums.Type;
//import com.labodesoft.roteiro01.repository.TaskRepository;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class TaskTest {
//    private TaskRepository taskRepository;
//    private TaskController taskController;
//
//
//
//    // Testes para a classe Task
//    @Test
//    public void testTaskConstructor() {
//        Task task = new Task("Sample Task");
//        Assertions.assertNotNull(task);
//        assertEquals("Sample Task", task.getDescription());
//    }
//
//    @Test
//    public void testTaskStatus_DataType_PastDue() {
//        Task task = new Task();
//        task.setType(Type.DATA);
//        task.setDueDate(LocalDate.now().minusDays(1)); // Due date is yesterday
//        String status = task.getStatus();
//        assertEquals("1 dia de atraso", status);
//    }
//
//    @Test
//    public void testTaskStatus_PrazoType_Completed() {
//        Task task = new Task();
//        task.setType(Type.PRAZO);
//        task.setDueDays(2);
//        task.setCompleted(true);
//        String status = task.getStatus();
//        assertEquals("Concluída", status);
//    }
//
//    // Testes para a classe TaskController
//    @Test
//    public void testListAll_EmptyList() {
//        List<Task> tasks = new ArrayList<>();
//        when(taskRepository.findAll()).thenReturn(tasks);
//
//        ResponseEntity<List<Task>> response = taskController.listAll();
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        assertTrue(response.getBody().isEmpty());
//    }
//
//
//
//    @Test
//    public void testUpdateTask_NotFound() {
//        Long taskId = 1L;
//        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());
//
//        ResponseEntity<Task> response = taskController.update(taskId, new Task("Updated Task"));
//
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//    }
//
//    // Você pode adicionar mais testes para os outros métodos do TaskController
//}
