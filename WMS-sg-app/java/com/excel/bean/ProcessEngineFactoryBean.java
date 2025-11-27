package com.excel.bean;

import org.activiti.engine.DynamicBpmnService;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngineInfo;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class ProcessEngineFactoryBean implements ProcessEngine{

	@Autowired ProcessEngineConfigurationImpl pec;
	@Autowired ApplicationContext applicationContext;
	
	public void setProcessEngineConfiguration(ProcessEngineConfigurationImpl pec) {
		this.pec=pec;
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
		
	}

	@Override
	public RepositoryService getRepositoryService() {
		return pec.getRepositoryService();
	}

	@Override
	public RuntimeService getRuntimeService() {
		return pec.getRuntimeService();
	}

	@Override
	public FormService getFormService() {
		return pec.getFormService();
	}

	@Override
	public TaskService getTaskService() {
		return pec.getTaskService();
	}

	@Override
	public HistoryService getHistoryService() {
		return pec.getHistoryService();
	}

	@Override
	public IdentityService getIdentityService() {
		return pec.getIdentityService();
	}

	@Override
	public ManagementService getManagementService() {
		return pec.getManagementService();
	}

	@Override
	public DynamicBpmnService getDynamicBpmnService() {
		return pec.getDynamicBpmnService();
	}

	@Override
	public ProcessEngineConfiguration getProcessEngineConfiguration() {
		return pec.getProcessEngineConfiguration();
	}

	@Override
	public String getName() {
		return pec.getClass().getName();
	}

	@Override
	public void close() {
		
		
	}

}
