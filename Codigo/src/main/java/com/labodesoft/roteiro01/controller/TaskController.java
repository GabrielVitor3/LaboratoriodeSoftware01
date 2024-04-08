package com.labodesoft.roteiro01.controller;

import com.labodesoft.roteiro01.entity.Task;
import com.labodesoft.roteiro01.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
    public class TaskController {
        @Autowired
        TaskRepository taskRepository;
        @GetMapping("/task")


        @Operation(summary = "Lista todas as tarefas da lista")
        public ResponseEntity<List<Task>> listAll() {
            try{
                List<Task> taskList = new ArrayList<Task>();
                taskRepository.findAll().forEach(taskList::add);
                if(taskList.isEmpty()){
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(taskList, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    @PostMapping
    @Operation(summary = "Cria uma nova tarefa")
    public ResponseEntity<Task> create(@RequestBody Task task) {
        try {
            Task _task = taskRepository.save(new Task(task.getDescription()));
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tarefa pelo ID")
    public ResponseEntity<Task> update(@PathVariable("id") Long id, @RequestBody Task task) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isPresent()) {
            Task _task = taskData.get();
            _task.setDescription(task.getDescription());
            _task.setCompleted(task.getCompleted());
            return new ResponseEntity<>(taskRepository.save(_task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma tarefa pelo ID")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        try {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/complete")
    @Operation(summary = "Marca uma tarefa como concluída pelo ID")
    public ResponseEntity<Task> markAsCompleted(@PathVariable("id") Long id) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isPresent()) {
            Task task = taskData.get();
            task.setCompleted(true); // Marca a tarefa como concluída
            return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    }
