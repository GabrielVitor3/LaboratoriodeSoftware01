package com.labodesoft.roteiro01.mock;

import com.labodesoft.roteiro01.entity.Task;
import org.hibernate.query.Page;

import java.util.ArrayList;
import java.util.List;

public class TaskMock {
    public static Page<Task> createTasks() {

        List<Task> taskList = new ArrayList<>();
        Task task1 = new Task();
        task1.setId(1L);
        task1.setDescription("Tarefa 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setDescription("Tarefa 2");

        taskList.add(task1);
        taskList.add(task2);
        Page<Task> pagedResponse = new PageImpl(taskList);
        return pagedResponse;
    }
}
