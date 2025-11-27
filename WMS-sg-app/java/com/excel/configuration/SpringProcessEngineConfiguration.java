package com.excel.configuration;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.CommandInterceptor;

public class SpringProcessEngineConfiguration extends ProcessEngineConfigurationImpl{

	@Override
	protected CommandInterceptor createTransactionInterceptor() {
		return null;
	}

}
