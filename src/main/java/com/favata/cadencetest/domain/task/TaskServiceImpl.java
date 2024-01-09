package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import com.favata.cadencetest.domain.entity.TaskUser;
import com.favata.cadencetest.domain.entity.User;
import com.favata.cadencetest.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Task CreateTask(TaskDto taskDto) {
        return taskRepository.CreateTask(taskDto);
    }

    @Override
    public Task GetTask(String taskId) {
        return taskRepository.GetTaskById(taskId);
    }

    @Override
    public List<Task> GetTasks() {
        return taskRepository.GetTasks();
    }

    @Override
    public List<TaskUser> GetAllUsersTasks() {
        List<TaskUser> taskUsers = new ArrayList<>();
        List<User> users = userRepository.GetUsers();
        users.forEach(user -> {
            TaskUser newTaskUser = new TaskUser(user, taskRepository.GetTasksByUserId(user.getId()));
            taskUsers.add(newTaskUser);
        });
        return taskUsers;
    }

    @Override
    public List<Task> GetTasksByUserId(String userId) {
        return taskRepository.GetTasksByUserId(userId);
    }

    @Override
    public Task CompleteTask(String taskId) {
        TaskDto taskDto = new TaskDto(taskRepository.GetTaskById(taskId));
        taskDto.setCompleted(true);
        return taskRepository.UpdateTask(taskDto);
    }

    @Override
    public Task AssignTask(String taskId, String userId) {
        TaskDto taskDto = new TaskDto(taskRepository.GetTaskById(taskId));
        taskDto.setAssignedUserId(userId);
        return taskRepository.UpdateTask(taskDto);
    }

    @Override
    public Task UpdateTask(TaskDto taskDto){
        return taskRepository.UpdateTask(taskDto);
    }

    @Override
    public Task DeleteTask(String taskId) {
        return taskRepository.DeleteTask(taskId);
    }
}
