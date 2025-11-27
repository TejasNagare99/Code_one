package com.excel.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.CrmSchemeMasterBean;
import com.excel.model.CrmSchemeMaster;
import com.excel.model.CustomerMaster;
import com.excel.model.SG_d_parameters_details;
import com.excel.repository.ParameterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;

@Service
public class CrmSchemeMasterServiceImpl implements CrmSchemeMasterService, MedicoConstants {
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired ParameterRepository parameterrepository;
	
	
	// Added By Omkar
		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public CrmSchemeMaster SaveCRMMaster(CrmSchemeMasterBean bean, HttpSession session) throws Exception {
			CrmSchemeMaster crmscheme = null;
			try {
				String companycode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
				System.out.println("CRM Master Loc id  :" + bean.getLocation());
				System.out.println("COMPANY_CD :" + companycode);
				System.out.println("CRM_SCHEME_CODE : " + bean.getSchemeCode());
				System.out.println("CRM_SCHEME_NAME  :" + bean.getSchemeName());
				System.out.println("CRM_SCHEME_DESCR  :" + bean.getSchemeDescription());
				System.out.println("VALID_FROM_DT :" + bean.getFrom());
				System.out.println("VALID_TO_DT  :" + bean.getTo());
				System.out.println("CRM_SCH_TYPE :" + bean.getSchemeType());
				System.out.println("CUSTOMER_TYPE :" + bean.getApplicableTo());

				if (bean.getApplicableTo().equals("D")) {
					System.out.println("DOC_CURRENT_BIZ_FROM  :" + bean.getCurrentRange());
					System.out.println("DOC_CURRENT_BIZ_TO  :" + bean.getCurrentRange1());
					System.out.println("DOC_EXPECTED_BIZ_FROM  :" + bean.getExpectedRange());
					System.out.println("DOC_EXPECTED_BIZ_TO  :" + bean.getExpectedRange1());
				} else {
					System.out.println("CUST_CURRENT_BIZ_FROM  :" + bean.getCurrentRange());
					System.out.println("CUST_CURRENT_BIZ_TO  :" + bean.getCurrentRange1());
					System.out.println("CUST_EXPECTED_BIZ_FROM  :" + bean.getExpectedRange());
					System.out.println("CUST_EXPECTED_BIZ_TO  :" + bean.getExpectedRange1());
				}
				System.out.println("DOCTOR_TYPE  :" + bean.getDocType());
				System.out.println("DOCTOR_SPECIALITY  :" + bean.getDocSpeciality());
				System.out.println("DOCTOR_CATEGORY :" + bean.getDocCategory());

				System.out.println("ROUTE_TYPE  :" + bean.getRouteType());
				System.out.println("SCHEME_EXPE_ITEM :" + bean.getExpenseItem());
				System.out.println("SCHEME_EXP_ITEM_QTY  :" + bean.getQuantity());
				System.out.println("SCHEME_EXP_ITEM_RATE  :" + bean.getRate());
				System.out.println("SCHEME_EXP_VALUE :" + bean.getValue());
				System.out.println("TOLERANCE_PERC :" + bean.getTolerance());
				System.out.println("DELETED :" + "N");

				System.out.println("LAST_MOD_BY :" + bean.getUsername());
				System.out.println("LAST_MOD_DATE :" + new Date());
				System.out.println("CONFIRMED :" + "N");

				crmscheme = new CrmSchemeMaster();

				crmscheme.setCompany_cd(companycode);
				
				 crmscheme.setCrm_scheme_id(0l);
				crmscheme.setCrm_scheme_code(bean.getSchemeCode());
				crmscheme.setCrm_scheme_name(bean.getSchemeName());
				crmscheme.setValid_from_dt(bean.getFrom());
				crmscheme.setValid_to_dt(bean.getTo());
				crmscheme.setCrm_sch_type(bean.getSchemeType());
				crmscheme.setCustomer_type(bean.getApplicableTo());
				

				if (bean.getApplicableTo().equals("D")) {
 
					crmscheme.setDoctor_type(bean.getDocType());
					crmscheme.setDoctor_speciality(bean.getDocSpeciality());
					crmscheme.setDoctor_category(bean.getDocCategory());
					crmscheme.setDoc_current_biz_from(Long.valueOf(bean.getCurrentRange()) );
					crmscheme.setDoc_current_biz_to(Long.valueOf(bean.getCurrentRange1()) );
					crmscheme.setDoc_expected_biz_from(Long.valueOf(bean.getExpectedRange()) );
					crmscheme.setDoc_expected_biz_to(Long.valueOf(bean.getExpectedRange1()) );
					
					// set zero
					crmscheme.setCust_current_biz_from(0l);
					crmscheme.setCust_current_biz_to(0l);
					crmscheme.setCust_expected_biz_from(0l);
					crmscheme.setCust_expected_biz_to(0l);
					crmscheme.setLoc_id(0l);
					
				} else {
					crmscheme.setDoctor_type("");
					crmscheme.setDoctor_speciality("");
					crmscheme.setDoctor_category("");

					crmscheme.setCust_current_biz_from(Long.valueOf(bean.getCurrentRange()));
					crmscheme.setCust_current_biz_to(Long.valueOf(bean.getCurrentRange1()) );
					crmscheme.setCust_expected_biz_from(Long.valueOf(bean.getExpectedRange()) );
					crmscheme.setCust_expected_biz_to(Long.valueOf(bean.getExpectedRange1()) );
					crmscheme.setLoc_id(bean.getLocation());
					
					//set zero
					crmscheme.setDoc_current_biz_from(0l);
					crmscheme.setDoc_current_biz_to(0l);
					crmscheme.setDoc_expected_biz_from(0l);
					crmscheme.setDoc_expected_biz_to(0l);
				}

				crmscheme.setRoute_type(bean.getRouteType());

				crmscheme.setScheme_expe_item(bean.getExpenseItem());
				crmscheme.setScheme_exp_item_qty(bean.getQuantity());
				crmscheme.setScheme_exp_item_rate(bean.getRate());
				crmscheme.setScheme_exp_value(bean.getValue());
				crmscheme.setTolerance_perc(bean.getTolerance());
				crmscheme.setDeleted("N");
				crmscheme.setLast_mod_by(bean.getUsername());
				crmscheme.setLast_mod_date(new Date());
				crmscheme.setCancelled("N");
				crmscheme.setCrm_scheme_descr(bean.getSchemeDescription());
				crmscheme.setApproval_status("A");

				transactionalRepository.persist(crmscheme);

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
				
			}
			return crmscheme;
		}
		
		@Override
		public List<SG_d_parameters_details> getDocClass() throws Exception {
			// TODO Auto-generated method stub
			return parameterrepository.getDocClass();
		}
		
		@Override
		public List<SG_d_parameters_details> getSchemeType() throws Exception {
			// TODO Auto-generated method stub
			return parameterrepository.getSchemeType();
		}
		
		@Override
		public List<CrmSchemeMaster> getScheme_Code() throws Exception{
			return parameterrepository.getScheme_Code();
			
		}

}
