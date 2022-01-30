package com.gt.activiti;

import com.gt.activiti.pojo.Evection;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GTsung
 * @date 2022/1/30
 */
public class TestVariables {

    @Test
    public void startProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String key = "myEvection2";
        Map<String, Object> map = new HashMap<>();
        Evection evection = new Evection();
        evection.setNum(2d);
        map.put("evection", evection);
        map.put("assignee0", "adams");
        map.put("assignee1", "jane");
        map.put("assignee2", "jesus");
        map.put("assignee3", "marry");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
        System.out.println("流程實例名稱=" + processInstance.getName());
        System.out.println("流程定義id=" + processInstance.getProcessDefinitionId());

    }

    @Test
    public void completTask() {
        //任务id
        String key = "myEvection2";
        // 任务负责人
        String assingee = "adams";
        // 获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 创建TaskService
        TaskService taskService = processEngine.getTaskService();
        // 完成任务前，需要校验该负责人可以完成当前任务
        // 校验方法：
        // 根据任务id和任务负责人查询当前任务，如果查到该用户有权限，就完成
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(assingee)
                .singleResult();
        if (task != null) {
            taskService.complete(task.getId());
            System.out.println("任务执行完成");
        }
    }

}
