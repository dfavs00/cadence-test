package com.favata.cadencetest.workflow;


import com.favata.cadencetest.activity.TaskActivities;
import com.favata.cadencetest.domain.entity.Task;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.client.WorkflowException;
import com.uber.cadence.common.RetryOptions;
import com.uber.cadence.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class TaskWorkflowImpl implements TaskWorkflow {

    private final TaskActivities activities = Workflow.newActivityStub(
            TaskActivities.class,
            new ActivityOptions.Builder()
                    .setScheduleToCloseTimeout(Duration.ofSeconds(30))
                    .setRetryOptions(
                            new RetryOptions.Builder()
                                    .setInitialInterval(Duration.ofSeconds(1))
                                    .setBackoffCoefficient(2.0)
                                    .setMaximumAttempts(10)
                                    .build())
                    .setTaskList(WorkflowConstants.TASK_WORKFLOW_TASK_LIST)
                    .build());

    private final Queue<TaskAction> taskActionQueue = new LinkedList<>();
    private Task task = null;

    @Override
    public void create(Arguments arguments) {
        try {
            // Verify task exists and assign task to a user
            task = activities.GetTask(arguments.getTaskId());
            Task assignedTask = activities.AssignTask(task.getId());
            log.info("Newly assigned task {}", assignedTask);

            while (task != null && !task.isCompleted()) {
                Workflow.await(() -> !updateQueuesAreEmpty());
                processTaskActionQueue();
            }
        } catch (WorkflowException e) {
            log.error(e.getMessage());
        } finally {
            log.info("Task: {} completed", arguments.getTaskId());
        }
    }

    @Override
    public void processTaskAction(TaskAction taskAction) {
        taskActionQueue.add(taskAction);
    }

    private boolean updateQueuesAreEmpty() {
        return taskActionQueue.isEmpty();
    }

    private void processTaskActionQueue() {
        while (!taskActionQueue.isEmpty()) {
            final TaskAction taskAction = taskActionQueue.poll();
            switch (taskAction.getType()) {
                case COMPLETE:
                    task = activities.CompleteTask(task.getId());
                    break;
                case DELETE:
                    activities.DeleteTask(task.getId());
                    task = null;
                    break;
            }
        }
    }
}
