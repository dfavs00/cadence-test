package com.favata.cadencetest.activity;

import com.favata.cadencetest.domain.entity.Task;
import com.favata.cadencetest.domain.entity.TaskUser;
import com.favata.cadencetest.domain.entity.User;
import com.favata.cadencetest.domain.task.TaskService;
import com.favata.cadencetest.domain.user.UserService;
import com.uber.cadence.activity.ActivityMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskActivitiesImpl implements TaskActivities {

    @Autowired
    private TaskService taskService;

    @Override
    public Task AssignTask(String taskId) {
        // Assign a task to the user with the least amount of tasks, otherwise give to the lowest user
        List<TaskUser> taskUsers = taskService.GetAllUsersTasks();
        TaskUser lowestTaskUser = taskUsers.stream().reduce((tu1, tu2) ->
                tu1.getTasks().size() <= tu2.getTasks().size() ? tu1 : tu2).orElse(null);

        if (lowestTaskUser != null) {
            return taskService.AssignTask(taskId, lowestTaskUser.getUser().getId());
        } else {
            throw new RuntimeException("Unable to assign task to a user");
        }
    }

    @Override
    public Task CompleteTask(String taskId) {
        return taskService.CompleteTask(taskId);
    }

    @Override
    public Task GetTask(String taskId) {
        return taskService.GetTask(taskId);
    }

    @Override
    public Task DeleteTask(String taskId) {
        return taskService.DeleteTask(taskId);
    }
}
