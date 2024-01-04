package com.favata.cadencetest.worker;

import com.favata.cadencetest.workflow.HelloWorldWorkflowImpl;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CadenceConfig {

    @Bean
    public WorkflowClient defaultWorkflowClient() {
        log.info("Creating Default Workflow Client");
        return WorkflowClient.newInstance(
                new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                WorkflowClientOptions.newBuilder().setDomain("test-domain").build()
        );
    }

    @Bean
    public WorkerFactory defaultWorkerFactory(WorkflowClient workflowClient) {
        log.info("Creating Default Worker Factory");
        return WorkerFactory.newInstance(workflowClient);
    }

    @Bean
    public Worker defaultWorker(WorkerFactory workerFactory) {
        log.info("Creating Default Worker");
        final Worker defaultWorker = workerFactory.newWorker("HelloWorldTaskList");
        defaultWorker.registerWorkflowImplementationTypes(HelloWorldWorkflowImpl.class);

        workerFactory.start();
        return defaultWorker;
    }
}
