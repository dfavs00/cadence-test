package com.favata.cadencetest.workflow;

import com.uber.cadence.workflow.SignalMethod;
import com.uber.cadence.workflow.WorkflowMethod;

public interface TaskWorkflow {
    @WorkflowMethod
    void create(Arguments arguments);

    @SignalMethod
    void processTaskAction(TaskAction taskAction);
}
