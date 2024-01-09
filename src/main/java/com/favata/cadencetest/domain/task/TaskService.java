package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import com.favata.cadencetest.domain.entity.TaskUser;

import java.util.List;

public interface TaskService {
    Task CreateTask(TaskDto taskDto);
    Task GetTask(String taskId);
    List<Task> GetTasks();
    List<Task> GetTasksByUserId(String userId);
    List<TaskUser> GetAllUsersTasks();
    Task CompleteTask(String taskId);
    Task AssignTask(String taskId, String userId);
    Task UpdateTask(TaskDto taskDto);
    Task DeleteTask(String taskId);
}