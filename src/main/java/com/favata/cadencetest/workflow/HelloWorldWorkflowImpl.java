package com.favata.cadencetest.workflow;

import com.uber.cadence.workflow.Workflow;
import org.slf4j.Logger;

public class HelloWorldWorkflowImpl implements HelloWorldWorkflow {
    private final static Logger logger = Workflow.getLogger(HelloWorldWorkflowImpl.class);

    @Override
    public void sayHello(String name) {
        logger.info("Hello " + name + "!");
    }
}
