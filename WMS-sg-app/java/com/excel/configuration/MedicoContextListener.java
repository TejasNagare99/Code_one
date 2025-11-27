package com.excel.configuration;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import com.excel.model.Company;
import com.excel.service.ArticleSchemeDeliveryReqService;
import com.excel.service.Article_Scheme_master_Service;
import com.excel.service.CompanyMasterService;
import com.excel.service.ProductMasterService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Configuration
public class MedicoContextListener implements ServletContextAware, MedicoConstants {

	@Autowired
	private CompanyMasterService companyMasterService;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private ProductMasterService productMasterService;
	@Autowired
	private Article_Scheme_master_Service article_Scheme_master_Service;
	@Autowired
	private ArticleSchemeDeliveryReqService artSchmDelServi;

	@Override
	public void setServletContext(ServletContext context) {
		try {
			companyMasterService.UpdateCompanydetails(); /// Added to set Ind 'N' For Test DB
			userMasterService.unlockAllUsers();
//			productMasterService.deleteLockProduct(null);

			Company company = companyMasterService.getCompanyDetails();
			
			context.setAttribute(COMPANY_DATA, company);
			context.setAttribute(COMPANY_CODE, company.getCompany());
			context.setAttribute(APPROVAL_LINK, company.getWeb_site());
			context.setAttribute(IS_LIVE, company.getWms_is_live());
			context.setAttribute(CFA_TO_STK_IND, company.getCfa_to_stk_ind());
			
			if( company.getCfa_to_stk_ind().equals("Y")) {
				// release all locked customers cfa to stk
				artSchmDelServi.unlockAllCustomers();
				// release all locked products cfa to stk
				article_Scheme_master_Service.unlock_all_article_product();
			}

			final String approvalLink = companyMasterService.getCompanyDetails().getWeb_site();
			System.out.println("Approval LINK :: " + approvalLink);
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
	}

}
