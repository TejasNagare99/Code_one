package com.excel.configuration;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.excel.service.AllocationConsolidationService;
import com.excel.service.AllocationConsolidationServiceImpl;
import com.excel.service.AllocationService;
import com.excel.service.AllocationServiceImpl;
import com.excel.service.ArtSchmDelReqServiceImpl;
import com.excel.service.ArticleSchemeDeliveryReqService;
import com.excel.service.Article_Scheme_master_Service;
import com.excel.service.Article_Scheme_master_Service_Impl;
import com.excel.service.DispatchService;
import com.excel.service.DispatchServiceImpl;
import com.excel.service.DoctorBulkAllocUploadService;
import com.excel.service.DoctorBulkAllocUploadServiceImpl;
import com.excel.service.GRNService;
import com.excel.service.GRNServiceImpl;
import com.excel.service.IAAService;
import com.excel.service.IAAServiceImpl;
import com.excel.service.SockistBulkAllocUploadService;
import com.excel.service.SockistBulkAllocUploadServiceImpl;
import com.excel.service.SpecialAllocationService;
import com.excel.service.SpecialAllocationServiceImpl;
import com.excel.service.StockWithdrawalService;
import com.excel.service.StockWithdrawalServiceImpl;
import com.google.gson.Gson;

@Configuration
public class ActivitiConfig {
	/*
	 * @Autowired DispatchService dispatchService;
	 */
	@Bean(name = "processEngine")
	public ProcessEngineFactoryBean processEngine(
			@Qualifier("processEngineConfiguration") SpringProcessEngineConfiguration pec,
			ApplicationContext applicationContext) throws Exception {
		ProcessEngineFactoryBean pe = new ProcessEngineFactoryBean();
		pe.setProcessEngineConfiguration(pec);
		/* pe.setApplicationContext(applicationContext); */

		return pe;
	}

	@Primary
	@Bean(name = "processEngineConfiguration")
	public SpringProcessEngineConfiguration getProcessEngineConfiguration(
			@Qualifier("dataSource") DataSource dataSource,
			@Qualifier("transactionManager") PlatformTransactionManager transactionManager,
			@Qualifier("applicationContext") ApplicationContext context) {
		SpringProcessEngineConfiguration pec = new SpringProcessEngineConfiguration();

		pec.setDataSource(dataSource);
		pec.setTransactionManager(transactionManager);
		pec.setDatabaseSchemaUpdate("true");
		pec.setAsyncExecutorEnabled(true);
		pec.setAsyncExecutorActivate(true);
		/* pec.setJobExecutorActivate(true); */
		pec.setDeploymentMode("single-resource");

		/*
		 * pec.setApplicationContext(context);
		 */

		// pec.setTransactionManager(transactionManager);
		// pec.setApplicationContext(context);

//        Map<Class<?>, SessionFactory> sessionFactories = new HashMap<Class<?>, SessionFactory>();
//
//        sessionFactories.put(CustomUserEntityManager.class, userEntityManagerFactory);
//        sessionFactories.put(CustomGroupEntityManager.class, groupEntityManagerFactory);
//
//        pec.setSessionFactories(sessionFactories);

		return pec;
	}

	@Bean(name = "runtimeService")
	public RuntimeService getRuntimeService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
		return processEngine.getProcessEngineConfiguration().getRuntimeService();
	}

	@Bean(name = "repositoryService")
	public RepositoryService getRepositoryService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
		return processEngine.getProcessEngineConfiguration().getRepositoryService();
	}

	@Bean(name = "taskService")
	public TaskService geTaskService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
		return processEngine.getProcessEngineConfiguration().getTaskService();
	}

	@Bean(name = "historyService")
	public HistoryService getHistoryService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
		return processEngine.getProcessEngineConfiguration().getHistoryService();
	}

	@Bean(name = "managementService")
	public ManagementService getManagementService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
		return processEngine.getProcessEngineConfiguration().getManagementService();
	}

	@Bean(name = "identityService")
	public IdentityService getIdentityService(@Qualifier("processEngine") ProcessEngineFactoryBean processEngine) {
		return processEngine.getProcessEngineConfiguration().getIdentityService();
	}

	@Bean(name = "dispatchService")
	public DispatchService getDispatchService() {
		DispatchService dis = new DispatchServiceImpl();
		return dis;
	}

	@Bean(name = "grnService")
	public GRNService getGrnService() {
		GRNService grn = new GRNServiceImpl();
		return grn;
	}

	@Bean(name = "allocationService")
	public AllocationService getAllocationService() {
		AllocationService alloc = new AllocationServiceImpl();
		return alloc;
	}

	@Bean(name = "iAAService")
	public IAAService getIaaService() {
		IAAService iaa = new IAAServiceImpl();
		return iaa;
	}

	@Bean(name = "specialAllocationService")
	public SpecialAllocationService getSpecialAllocationService() {
		SpecialAllocationService special = new SpecialAllocationServiceImpl();
		return special;
	}

	@Bean(name = "stockWithdrawalService")
	public StockWithdrawalService getStockWithdrawalService() {
		StockWithdrawalService stk = new StockWithdrawalServiceImpl();
		return stk;
	}

	@Bean(name = "allocationConsolidationService")
	public AllocationConsolidationService getAllocationConsolidationService() {
		AllocationConsolidationService cons = new AllocationConsolidationServiceImpl();
		return cons;
	}

	@Bean(name = "doctorbulkallocuploadservice")
	public DoctorBulkAllocUploadService getDoctorBulkAllocUploadService() {
		DoctorBulkAllocUploadService cons = new DoctorBulkAllocUploadServiceImpl();
		return cons;
	}

	@Bean(name = "stockistbulkuploadservice")
	public SockistBulkAllocUploadService getStockistBulkAllocUploadService() {
		SockistBulkAllocUploadService cons = new SockistBulkAllocUploadServiceImpl();
		return cons;
	}
	
	@Bean(name = "artSchDelReqService")
	public ArticleSchemeDeliveryReqService getArtScmDelReqService() {
		ArticleSchemeDeliveryReqService cons = new ArtSchmDelReqServiceImpl();
		return cons;
	}
	
	@Bean(name = "schemeMasterService")
	public Article_Scheme_master_Service getSchemeMasterService() {
		Article_Scheme_master_Service cons = new Article_Scheme_master_Service_Impl();
		return cons;
	}

	@Bean(name = "restTemplate")
	public RestTemplate getRestTemplate() {
		RestTemplate cons = new RestTemplate();
		return cons;
	}

	@Bean(name = "gson")
	public Gson getGson() {
		Gson cons = new Gson();
		return cons;
	}

}