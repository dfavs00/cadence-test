package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import com.favata.cadencetest.domain.entity.TaskUser;
import com.favata.cadencetest.domain.entity.User;
import com.favata.cadencetest.domain.entity.Workflow;
import com.favata.cadencetest.domain.user.UserRepository;
import com.favata.cadencetest.domain.workflow.WorkflowRepository;
import com.favata.cadencetest.workflow.*;
import com.uber.cadence.WorkflowExecution;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private WorkflowClient workflowClient;

    @Override
    public Task CreateTask(TaskDto taskDto) {
        if (userRepository.GetUsers().isEmpty()) {
            log.error("ERROR: must be active users to create a task!");
            throw new RuntimeException("There must be active users to create a new task!");
        }

        Task createdTask = taskRepository.CreateTask(taskDto);


        // Run create workflow
        TaskWorkflow workflow = workflowClient.newWorkflowStub(
                TaskWorkflow.class,
                new WorkflowOptions.Builder()
                        .setExecutionStartToCloseTimeout(Duration.ofHours(24))
                        .setTaskList(WorkflowConstants.TASK_WORKFLOW_TASK_LIST)
                        .build()
        );
        Arguments arguments = new Arguments(null, createdTask.getId());
        WorkflowExecution execution = WorkflowClient.start(workflow::create, arguments);

        log.info("Started new Task workflow with ID {} and input {}", execution.workflowId, arguments);

        workflowRepository.CreateWorkflow(execution.workflowId, createdTask.getId());

        return createdTask;
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
    public Task UpdateTask(TaskDto taskDto) {
        return taskRepository.UpdateTask(taskDto);
    }

    @Override
    public Task DeleteTask(String taskId) {
        return taskRepository.DeleteTask(taskId);
    }

    @Override
    public void CompleteUserTask(String taskId) {
        RunProcessActionWorkflow(TaskActionType.COMPLETE, taskId);
    }

    @Override
    public void DeleteUserTask(String taskId) {
        RunProcessActionWorkflow(TaskActionType.DELETE, taskId);
    }

    private void RunProcessActionWorkflow(TaskActionType taskActionType, String taskId) {
        Workflow workflow = workflowRepository.GetWorkflowByTaskId(taskId);
        TaskWorkflow existingWorkflow = workflowClient.newWorkflowStub(TaskWorkflow.class, workflow.getId());
        TaskAction taskAction = new TaskAction(taskActionType, taskId);
        existingWorkflow.processTaskAction(taskAction);
    }

    public List<Workflow> GetAllWorkflows() {
        return workflowRepository.GetWorkflows();
    }
}
