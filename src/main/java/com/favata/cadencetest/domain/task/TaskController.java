package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import com.favata.cadencetest.domain.entity.TaskUser;
import com.favata.cadencetest.domain.entity.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("getByUser/{userId}")
    public ResponseEntity<List<Task>> GetTasksByUser(@PathVariable("userId") String userId) {
        try {
            return ResponseEntity.ok(taskService.GetTasksByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> GetTasks() {
        try {
            return ResponseEntity.ok(taskService.GetTasks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<List<TaskUser>> GetAllUsersTasks() {
        try {
            return ResponseEntity.ok(taskService.GetAllUsersTasks());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("admin")
    public ResponseEntity<Task> AdminCreateTask(@RequestBody TaskDto taskDto) {
        log.info("TASK_DTO: {}", taskDto);
        try {
            return ResponseEntity.ok(taskService.CreateTask(taskDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> UserCompleteTask(@PathVariable("id") String taskId) {
        try {
            taskService.CompleteUserTask(taskId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> UserDeleteTask(@PathVariable("id") String taskId) {
        try {
            taskService.DeleteUserTask(taskId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("workflows")
    public ResponseEntity<List<Workflow>> GetAllWorkflows() {
        try {
            return ResponseEntity.ok(taskService.GetAllWorkflows());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
