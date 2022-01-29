package com.gt.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @author GTsung
 * @date 2022/1/29
 */
public class ActivitiDemo2 {

    @Test
    public void deployProcessByZip() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 獲取zip
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("流程部署id-" + deploy.getId());
        System.out.println("流程部署名稱-" + deploy.getName());
    }
}
