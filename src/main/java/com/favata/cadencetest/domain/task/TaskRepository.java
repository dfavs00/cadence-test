package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {

    static class TaskStorage {
        private final HashMap<String, Task> idMap = new HashMap<>();
    }

    private final TaskStorage taskStorage = new TaskStorage();

    public Task CreateTask(TaskDto taskDto) {
        String newId = UUID.randomUUID().toString();
        Task newTask = new Task(newId, null, taskDto.getDescription(), false);
        taskStorage.idMap.put(newId, newTask);
        return newTask;
    }

    public Task GetTaskById(String id) {
        return taskStorage.idMap.get(id);
    }

    public List<Task> GetTasks() {
        return new ArrayList<>(taskStorage.idMap.values());
    }

    public List<Task> GetTasksByUserId(String userId) {
        List<Task> tasks = GetTasks();
        return tasks.stream().filter(task ->
                task.getAssignedUserId() != null && task.getAssignedUserId().equals(userId)).collect(Collectors.toList());
    }

    public Task UpdateTask(TaskDto taskDto) {
        Task oldTask = taskStorage.idMap.get(taskDto.getId());
        Task newTask = new Task(taskDto);
        taskStorage.idMap.put(oldTask.getId(), newTask);
        return newTask;
    }

    public Task DeleteTask(String taskId) {
        Task oldTask = new Task(taskStorage.idMap.get(taskId));
        taskStorage.idMap.remove(taskId);
        return oldTask;
    }
}
