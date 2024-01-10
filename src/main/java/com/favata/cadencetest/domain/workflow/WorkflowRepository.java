package com.favata.cadencetest.domain.workflow;

import com.favata.cadencetest.domain.entity.Workflow;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class WorkflowRepository {
    static class WorkflowStorage {
        private final HashMap<String, Workflow> idMap = new HashMap<>();
        private final HashMap<String, String> taskIdToWorkflowIdMap = new HashMap<>();
    }

    private final WorkflowStorage workflowStorage = new WorkflowStorage();

    public Workflow CreateWorkflow(String workflowId, String taskId) {
        Workflow newWorkflow = new Workflow(workflowId, taskId);
        workflowStorage.idMap.put(workflowId, newWorkflow);
        workflowStorage.taskIdToWorkflowIdMap.put(taskId, workflowId);
        return newWorkflow;
    }

    public Workflow GetWorkflowById(String workflowId) {
        return workflowStorage.idMap.get(workflowId);
    }

    public Workflow GetWorkflowByTaskId(String taskId) {
        String workflowId = workflowStorage.taskIdToWorkflowIdMap.get(taskId);
        return workflowStorage.idMap.get(workflowId);
    }

    public List<Workflow> GetWorkflows() {
        return new ArrayList<>(workflowStorage.idMap.values());
    }
}
