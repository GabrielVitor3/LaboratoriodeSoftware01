package com.labodesoft.roteiro01.entity;

import com.labodesoft.roteiro01.enums.Priority;
import com.labodesoft.roteiro01.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
            private String description;
            private Boolean completed;
            public Task(String description){
        this.description = description;
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private LocalDate dueDate;

    private Integer dueDays;



    public String getStatus() {
        if (type == Type.DATA) {
            if (completed) {
                return "Concluída";
            } else if (dueDate == null) {
                return "Prevista";
            } else if (dueDate.isAfter(LocalDate.now())) {
                // long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
                return "Prevista";
            } else if (dueDate.isEqual(LocalDate.now())) {
                return "Prevista";
            } else {
                long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                return daysLate + " dia" + (daysLate != 1 ? "s" : "") + " de atraso";
            }
        } else if (type == Type.PRAZO) {
            if (completed) {
                return "Concluída";
            } else if (dueDays == null) {
                return "Prevista";
            } else {
                LocalDate deadline = LocalDate.now().plusDays(dueDays);
                if (deadline.isEqual(LocalDate.now())) {
                    return "Prevista";
                } else if (deadline.isAfter(LocalDate.now())) {
                    // long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), deadline);
                    return "Prevista";
                } else {
                    long daysLate = ChronoUnit.DAYS.between(deadline, LocalDate.now());
                    return daysLate + " dia" + (daysLate != 1 ? "s" : "") + " de atraso";
                }
            }
        } else {
            return completed ? "Concluída" : "Prevista";
        }
    }
    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", completed=" +
                completed + "]";
    }
}

