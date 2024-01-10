package com.favata.cadencetest.activity;

import com.favata.cadencetest.domain.entity.Task;
import com.uber.cadence.activity.ActivityMethod;

public interface TaskActivities {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 30)
    Task AssignTask(String taskId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 30)
    Task CompleteTask(String taskId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 30)
    Task GetTask(String taskId);

    @ActivityMethod(scheduleToCloseTimeoutSeconds = 30)
    Task DeleteTask(String taskId);
}
