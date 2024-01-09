package com.favata.cadencetest.domain.entity;

import com.favata.cadencetest.domain.task.TaskDto;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Task {
    @NonNull
    private String id;
    private String assignedUserId;
    private String description;
    private boolean isCompleted;

    public Task (TaskDto taskDto) {
        this.id = taskDto.getId();
        this.assignedUserId = taskDto.getAssignedUserId();
        this.description = taskDto.getDescription();
        this.isCompleted = taskDto.isCompleted();
    }

    public Task (Task task) {
        this.setId(task.getId());
        this.setAssignedUserId(task.getAssignedUserId());
        this.setDescription(task.getDescription());
        this.setCompleted(task.isCompleted());
    }
}
