package com.favata.cadencetest.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class TaskAction {
    private TaskActionType type;
    private String taskId;
}
