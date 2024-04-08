package com.labodesoft.roteiro01.service;

import com.labodesoft.roteiro01.entity.Task;
import com.labodesoft.roteiro01.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskRepository.findAll().forEach(taskList::add);
        return taskList;
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task task) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isPresent()) {
            Task _task = taskData.get();
            _task.setDescription(task.getDescription());
            _task.setCompleted(task.getCompleted());
            return taskRepository.save(_task);
        } else {
            return null; // Ou você pode lançar uma exceção aqui, se preferir
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task markTaskAsCompleted(Long id) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isPresent()) {
            Task task = taskData.get();
            task.setCompleted(true); // Marca a tarefa como concluída
            return taskRepository.save(task);
        } else {
            return null; // Ou você pode lançar uma exceção aqui, se preferir
        }
    }
}
