package com.gt.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @author GTsung
 * @date 2022/1/29
 */
public class TestCreate {

    /**
     * 使用activiti的默認方式進行創建表
     */
    @Test
    public void testCreateTable() {
        // 默認讀取activiti.cfg.xml，會創建表
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//        System.out.println(processEngine);

        // 使用自定義
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        // 獲取流程運行管理類
        processEngine.getRuntimeService();
        // hi的表
        processEngine.getHistoryService();
        // 資源的表
        processEngine.getRepositoryService();
    }
}
