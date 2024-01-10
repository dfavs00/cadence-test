package com.favata.cadencetest.domain.task;

import com.favata.cadencetest.domain.entity.Task;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String id;
    private String assignedUserId;
    private String description;
    private boolean isCompleted;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.assignedUserId = task.getAssignedUserId();
        this.description = task.getDescription();
        this.isCompleted = task.isCompleted();
    }
}
