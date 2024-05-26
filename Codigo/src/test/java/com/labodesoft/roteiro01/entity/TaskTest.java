package com.labodesoft.roteiro01.entity;

import com.labodesoft.roteiro01.enums.Priority;
import com.labodesoft.roteiro01.enums.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setDescription("Test Task Description");
        task.setCompleted(false);
        task.setType(Type.DATA);
        task.setPriority(Priority.ALTA);
    }

    @Test
    void getStatusConcluida() {
        task.setCompleted(true);
        assertEquals("Concluída", task.getStatus());
    }

    @Test
    void getStatusPrevistaWithoutDueDate() {
        task.setDueDate(null);
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void getStatusPrevistaWithFutureDueDate() {
        task.setDueDate(LocalDate.now().plusDays(5));
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void getStatusPrevistaWithTodayDueDate() {
        task.setDueDate(LocalDate.now());
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void getStatusWithPastDueDate() {
        task.setDueDate(LocalDate.now().minusDays(3));
        assertEquals("3 dias de atraso", task.getStatus());
    }

    @Test
    void getStatusPrazoConcluida() {
        task.setType(Type.PRAZO);
        task.setCompleted(true);
        assertEquals("Concluída", task.getStatus());
    }

    @Test
    void getStatusPrazoPrevistaWithoutDueDays() {
        task.setType(Type.PRAZO);
        task.setDueDays(null);
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void getStatusPrazoPrevistaWithFutureDueDays() {
        task.setType(Type.PRAZO);
        task.setDueDays(5);
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void getStatusPrazoPrevistaWithTodayDueDays() {
        task.setType(Type.PRAZO);
        task.setDueDays(0);
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void getStatusPrazoWithPastDueDays() {
        task.setType(Type.PRAZO);
        task.setDueDays(-3);
        assertEquals("3 dias de atraso", task.getStatus());
    }

    @Test
    void getStatusLivreConcluida() {
        task.setType(Type.LIVRE);
        task.setCompleted(true);
        assertEquals("Concluída", task.getStatus());
    }

    @Test
    void getStatusLivrePrevista() {
        task.setType(Type.LIVRE);
        task.setCompleted(false);
        assertEquals("Prevista", task.getStatus());
    }

    @Test
    void testToString() {
        String expected = "Task [id=1, description=Test Task Description, completed=false]";
        assertEquals(expected, task.toString());
    }
}
