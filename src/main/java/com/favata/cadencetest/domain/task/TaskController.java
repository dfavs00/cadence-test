package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import com.favata.cadencetest.domain.entity.TaskUser;
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

    @GetMapping("getAllUsers")
    public ResponseEntity<List<TaskUser>> GetAllUsersTasks() {
        try {
            return ResponseEntity.ok(taskService.GetAllUsersTasks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("admin")
    public ResponseEntity<Task> AdminCreateTask(@RequestBody TaskDto taskDto) {
        try {
            return ResponseEntity.ok(taskService.CreateTask(taskDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
