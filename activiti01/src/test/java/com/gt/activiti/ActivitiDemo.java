package com.gt.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * @author GTsung
 * @date 2022/1/29
 */
public class ActivitiDemo {

    @Test
    public void testDeployment() {
        // 1.創建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 2.獲取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 3.使用service進行流程部署，定義一個流程的名字，將bpmn和png部署到數據中
        Deployment deployment = repositoryService.createDeployment()
                .name("myEvection")
                .addClasspathResource("bpmn/evection.bpmn")
                .addClasspathResource("bpmn/evection.png")
                .deploy();
        // 4.輸出部署信息
        System.out.println("流程部署id-" + deployment.getId());
        System.out.println("流程部署名稱-" + deployment.getName());

        // 操作了兩張表
        // act_re_deployment表示部署流程表
        // act_re_procdef類似于記錄
        // act_re_deployment與act_re_procdef是一對多的關係
    }

    @Test
    public void testStartProcess() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 獲取runtimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 根據流程定義的id啓動
        ProcessInstance myEvection = runtimeService.startProcessInstanceByKey("myEvection");
        System.out.println("流程定義id=" + myEvection.getProcessDefinitionId());
        System.out.println("流程實例id=" + myEvection.getId());
        System.out.println("當前活動的id" + myEvection.getActivityId());

        // 操作表:
        // act_re_deployment表示部署流程表
        // act_re_procdef流程定義
        // act_ge_bytearray: png和bpmn文件記錄
        // act_ge_property:
        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // select * from ACT_RE_PROCDEF where KEY_ = ? and (TENANT_ID_ = '' or TENANT_ID_ is null) and VERSION_ =
        // (select max(VERSION_) from ACT_RE_PROCDEF where KEY_ = ? and (TENANT_ID_ = '' or TENANT_ID_ is null))
        // select * from ACT_RE_DEPLOYMENT where ID_ = ?
        // select * from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_ = ? order by NAME_ asc
        // select * from ACT_RE_PROCDEF where DEPLOYMENT_ID_ = ? and KEY_ = ? and (TENANT_ID_ = '' or TENANT_ID_ is null)
        // select * from ACT_PROCDEF_INFO where PROC_DEF_ID_ = ?
        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // update ACT_GE_PROPERTY SET REV_ = ?, VALUE_ = ? where NAME_ = ? and REV_ = ?
        // insert into ACT_HI_TASKINST
        // insert into ACT_HI_PROCINST
        // insert into ACT_HI_ACTINST
        // insert into ACT_HI_IDENTITYLINK
        // insert into ACT_RU_EXECUTION
        // insert into ACT_RU_TASK
        // insert into ACT_RU_IDENTITYLINK
    }

    /**
     * 查詢個人待執行任務
     */
    @Test
    public void testFindPersonalTaskList() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        // 根據流程key和任務負責人查詢任務
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("adams")
                .list();
        // select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_
        // WHERE RES.ASSIGNEE_ = ? and D.KEY_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?
        tasks.forEach(task -> {
            System.out.println("流程實例id-" + task.getProcessInstanceId());
            System.out.println("任務id-" + task.getId());
            System.out.println("任務負責人-" + task.getAssignee());
            System.out.println("任務名稱-" + task.getName());
        });

        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // select * from ACT_GE_PROPERTY where NAME_ = ?
    }

    /**
     * 完成個人任務
     */
    @Test
    public void completeTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        // 根據任務id完成任務
        taskService.complete("2505");
        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // select * from ACT_RU_TASK where ID_ = ?
        // select * from ACT_RE_PROCDEF where ID_ = ?
        // select * from ACT_RE_DEPLOYMENT where ID_ = ?
        // select * from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_ = ? order by NAME_ asc
        // select * from ACT_RE_PROCDEF where DEPLOYMENT_ID_ = ? and KEY_ = ? and (TENANT_ID_ = '' or TENANT_ID_ is null)
        // select * from ACT_PROCDEF_INFO where PROC_DEF_ID_ = ?
        // select * from ACT_RU_TASK where PARENT_TASK_ID_ = ?
        // select * from ACT_RU_IDENTITYLINK where TASK_ID_ = ?
        // select * from ACT_RU_VARIABLE where TASK_ID_ = ?
        // select * from ACT_HI_TASKINST where ID_ = ?
        // select * from ACT_RU_EXECUTION where ID_ = ?
        // select * from ACT_HI_ACTINST RES where EXECUTION_ID_ = ? and ACT_ID_ = ? and END_TIME_ is null
        // select * from ACT_GE_PROPERTY where NAME_ = ?
        // update ACT_GE_PROPERTY SET REV_ = ?, VALUE_ = ? where NAME_ = ? and REV_ = ?
        //  select * from ACT_RU_EXECUTION where ID_ = ?
        // select * from ACT_RU_IDENTITYLINK where PROC_INST_ID_ = ?
        // insert into ACT_HI_TASKINST
        // insert into ACT_HI_ACTINST
        // insert into ACT_HI_IDENTITYLINK
        //  insert into ACT_RU_TASK
        // update ACT_HI_TASKINST set PROC_DEF_ID_ = ?, EXECUTION_ID_ = ?, NAME_ = ?, PARENT_TASK_ID_ = ?, DESCRIPTION_ = ?, OWNER_ = ?, ASSIGNEE_ = ?, CLAIM_TIME_ = ?, END_TIME_ = ?, DURATION_ = ?, DELETE_REASON_ = ?,
        // TASK_DEF_KEY_ = ?, FORM_KEY_ = ?, PRIORITY_ = ?, DUE_DATE_ = ?, CATEGORY_ = ? where ID_ = ?
        // update ACT_HI_ACTINST set EXECUTION_ID_ = ?, ASSIGNEE_ = ?, END_TIME_ = ?, DURATION_ = ?, DELETE_REASON_ = ? where ID_ = ?
        // update ACT_RU_EXECUTION
        // delete from ACT_RU_TASK
    }

    /**
     * 完成其他審批人的任務，查詢完成
     */
    @Test
    public void completeOtherTask() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("jane") // 完成誰的任務 分別為adams、jane、jesus、marry
                .singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void testQueryProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        // 查詢當前所有的流程定義
        List<ProcessDefinition> definitionList = processDefinitionQuery.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion().desc().list();
        for (ProcessDefinition definition : definitionList) {
            System.out.println("流程定義id-" + definition.getId());
            System.out.println("流程定義名稱-" + definition.getName());
            System.out.println("流程定義key-" + definition.getKey());
            System.out.println("流程定義version-" + definition.getVersion());
        }
    }
}
