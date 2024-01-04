package com.favata.cadencetest.workflow;

import com.uber.cadence.workflow.WorkflowMethod;

public interface HelloWorldWorkflow {
    @WorkflowMethod
    void sayHello(String name);
}
