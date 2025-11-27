package com.excel.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.StockistMasterBean;
import com.excel.model.CustomerMaster;
import com.excel.model.Sfa_Customer_Allocation;
import com.excel.model.Sfa_Customer_Master;
import com.excel.repository.StockistMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;

@Service
public class StockistMasterServiceImpl implements StockistMasterService, MedicoConstants{
	
	@Autowired
	private TransactionalRepository transactionalRepository;
	
	@Autowired
	private StockistMasterRepository stockistmasterrepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(StockistMasterBean bean) throws Exception {
		CustomerMaster customermaster=null;
//		PrimaryDetails primarydetails = bean.getPrimary();
//		GstDetails gstdetails = bean.getGst();
//		ShippingDetails shippingdetails = bean.getShipping();
//		String cust_code ="SAL001";
		
		//Save Data
		if (bean.getMode().equalsIgnoreCase(ENTRY_MODE)) {
			
		    customermaster = new CustomerMaster();
			customermaster.setCompany_cd(bean.getCompanyCode());
			customermaster.setLoc_id(bean.getPrimary().getCfaLocId());
			customermaster.setErp_plant_code(bean.getPrimary().getErpPlantCode());
			customermaster.setCust_cd("0");
			
			//Primary Details
			customermaster.setErp_cust_cd(bean.getPrimary().getErpcustCode());
			customermaster.setEnt_cust_cd(bean.getPrimary().getEntCustCode());
			customermaster.setCust_name_billto(bean.getPrimary().getCust_name());
			customermaster.setShort_name_billto(bean.getPrimary().getShortName());
			customermaster.setAddr1(bean.getPrimary().getAdd1());
			customermaster.setAddr2(bean.getPrimary().getAdd2());
			customermaster.setAddr3(bean.getPrimary().getAdd3());
			customermaster.setAddr4(bean.getPrimary().getAdd4() != null ? bean.getPrimary().getAdd4() : "");
			customermaster.setDestination(bean.getPrimary().getDestination() != null ? bean.getPrimary().getDestination() : "");
			customermaster.setPin_code(bean.getPrimary().getPincode());		
			customermaster.setState_code(bean.getPrimary().getStateId());
			customermaster.setMobile(bean.getPrimary().getPhone());
			customermaster.setEmail(bean.getPrimary().getEmail());
			customermaster.setTransporter(bean.getPrimary().getTransporter());
			customermaster.setStd_narration(bean.getPrimary().getStdNarration());
			customermaster.setInv_type(bean.getPrimary().getInvType());
			customermaster.setAllow_grn(bean.getPrimary().getAllowGRNInd());
			customermaster.setContact_person(bean.getPrimary().getContactPerson());
			customermaster.setBilling_status(bean.getPrimary().getBillStatus());
			customermaster.setTo_10cr(bean.getPrimary().getTurnOver());
			customermaster.setDiscontinued(bean.getPrimary().getDiscontinue());
			
			//Shipping Details
			customermaster.setCust_name_shipto(bean.getShipping().getShipCustomer());
			customermaster.setAddr1_shipto(bean.getShipping().getSadd1());
			customermaster.setAddr2_shipto(bean.getShipping().getSadd2());
			customermaster.setAddr3_shipto(bean.getShipping().getSadd3());
			customermaster.setAddr4_shipto(bean.getShipping().getSadd4());
			customermaster.setDestination_shipto(bean.getShipping().getShipDestination());
			customermaster.setPin_code_shipto(bean.getShipping().getPinCode());
			customermaster.setState_code_shipto(bean.getShipping().getStateCode());
			
			//GST Details
			customermaster.setGstin_no(bean.getGst().getGstNo());
			customermaster.setGstin_valid_from(bean.getGst().getGstValidfrom());
			customermaster.setGstin_valid_to(bean.getGst().getGstValidTo());
			customermaster.setDrug_lic1(bean.getGst().getDrug1() != null ? bean.getGst().getDrug1() : "");
			customermaster.setDrug_lic2(bean.getGst().getDrug2() != null ? bean.getGst().getDrug2() : "");
			customermaster.setDrug_lic3(bean.getGst().getDrug3() != null ? bean.getGst().getDrug3() : "");
			customermaster.setDl1_valid_dt(bean.getGst().getDrug1Valid());
			customermaster.setDl2_valid_dt(bean.getGst().getDrug2Valid());
			customermaster.setDl3_valid_dt(bean.getGst().getDrug3Valid());
			customermaster.setPan_no(bean.getGst().getPanNo());
//			customermaster.setTel_no(bean.getGst().getTelNo());
			customermaster.setTel_no("");
			
			
			customermaster.setCreated_by(bean.getEmpId());
			customermaster.setCreated_date(new Date());
			customermaster.setLast_mod_by(bean.getEmpId());
			customermaster.setLast_mod_date(new Date());;
			
			transactionalRepository.persist(customermaster);
			customermaster.setCust_cd("000"+customermaster.getCust_id());
			transactionalRepository.update(customermaster);
			
			
		}else {
			 customermaster = stockistmasterrepository.getById(bean.getCust_Id());
			System.out.println("cust id " + bean.getCust_Id());
			
			//Primary Details
			customermaster.setErp_cust_cd(bean.getPrimary().getErpcustCode());
			customermaster.setEnt_cust_cd(bean.getPrimary().getEntCustCode());
			customermaster.setCust_name_billto(bean.getPrimary().getCust_name());
			customermaster.setLoc_id(bean.getPrimary().getCfaLocId());
			customermaster.setShort_name_billto(bean.getPrimary().getShortName());
			customermaster.setAddr1(bean.getPrimary().getAdd1());
			customermaster.setAddr2(bean.getPrimary().getAdd2());
			customermaster.setAddr3(bean.getPrimary().getAdd3());
			customermaster.setAddr4(bean.getPrimary().getAdd4() != null ? bean.getPrimary().getAdd4() : "");
			customermaster.setDestination(bean.getPrimary().getDestination() != null ? bean.getPrimary().getDestination() : "");
			customermaster.setPin_code(bean.getPrimary().getPincode());		
			customermaster.setState_code(bean.getPrimary().getStateId());
			customermaster.setMobile(bean.getPrimary().getPhone());
			customermaster.setEmail(bean.getPrimary().getEmail());
			customermaster.setTransporter(bean.getPrimary().getTransporter());
			customermaster.setStd_narration(bean.getPrimary().getStdNarration());
			customermaster.setInv_type(bean.getPrimary().getInvType());
			customermaster.setAllow_grn(bean.getPrimary().getAllowGRNInd());
			customermaster.setContact_person(bean.getPrimary().getContactPerson());
			customermaster.setBilling_status(bean.getPrimary().getBillStatus());
			customermaster.setTo_10cr(bean.getPrimary().getTurnOver());
			customermaster.setDiscontinued(bean.getPrimary().getDiscontinue());
			
			//Shipping Details
			customermaster.setCust_name_shipto(bean.getShipping().getShipCustomer());
			customermaster.setAddr1_shipto(bean.getShipping().getSadd1());
			customermaster.setAddr2_shipto(bean.getShipping().getSadd2());
			customermaster.setAddr3_shipto(bean.getShipping().getSadd3());
			customermaster.setAddr4_shipto(bean.getShipping().getSadd4());
			customermaster.setDestination_shipto(bean.getShipping().getShipDestination());
			customermaster.setPin_code_shipto(bean.getShipping().getPinCode());
			customermaster.setState_code_shipto(bean.getShipping().getStateCode());
			
			//GST Details
			customermaster.setGstin_no(bean.getGst().getGstNo());
			customermaster.setGstin_valid_from(bean.getGst().getGstValidfrom());
			customermaster.setGstin_valid_to(bean.getGst().getGstValidTo());
			customermaster.setDrug_lic1(bean.getGst().getDrug1() != null ? bean.getGst().getDrug1() : "");
			customermaster.setDrug_lic2(bean.getGst().getDrug2() != null ? bean.getGst().getDrug2() : "");
			customermaster.setDrug_lic3(bean.getGst().getDrug3() != null ? bean.getGst().getDrug3() : "");
			customermaster.setDl1_valid_dt(bean.getGst().getDrug1Valid());
			customermaster.setDl2_valid_dt(bean.getGst().getDrug2Valid());
			customermaster.setDl3_valid_dt(bean.getGst().getDrug3Valid());
			customermaster.setPan_no(bean.getGst().getPanNo());
//			customermaster.setTel_no(bean.getGst().getTelNo());
			
			
			customermaster.setCreated_by(bean.getEmpId());
			customermaster.setCreated_date(new Date());
			customermaster.setLast_mod_by(bean.getEmpId());
			customermaster.setLast_mod_date(new Date());
			customermaster.setCust_cd("000"+customermaster.getCust_id());
			transactionalRepository.update(customermaster);
			
		}
		
		// SFA_CUSTOMER_MASTER
		Sfa_Customer_Master sfaCustomerMaster = stockistmasterrepository.getCustomerMasterById(customermaster.getCust_id());
		if (sfaCustomerMaster == null) {
			System.out.println("Record is not in sfa customer master");
			sfaCustomerMaster = new Sfa_Customer_Master();
			sfaCustomerMaster.setCust_id(customermaster.getCust_id());
			sfaCustomerMaster.setLoc_id(bean.getPrimary().getCfaLocId());
			sfaCustomerMaster.setTransporter_id(1l);
			sfaCustomerMaster.setState_id(Long.valueOf(bean.getPrimary().getStateId()));
			sfaCustomerMaster.setTin_valid_dt(null);
			sfaCustomerMaster.setCst_valid_dt(null);
			sfaCustomerMaster.setDl1_valid_dt(bean.getGst().getDrug1Valid());
			sfaCustomerMaster.setDl2_valid_dt(bean.getGst().getDrug2Valid());
			sfaCustomerMaster.setDl3_valid_dt(bean.getGst().getDrug3Valid());
			sfaCustomerMaster.setCst_perc(5l);
			sfaCustomerMaster.setInt_perc(5l);
			sfaCustomerMaster.setForm_type_id(1l);
			sfaCustomerMaster.setTot_credit_lmt(50000l);
			sfaCustomerMaster.setDeleted(N);
			sfaCustomerMaster.setCust_cd(customermaster.getCust_cd());
			sfaCustomerMaster.setCust_name(bean.getPrimary().getCust_name());
			sfaCustomerMaster.setShort_name(bean.getPrimary().getShortName());
			sfaCustomerMaster.setAddr1(bean.getPrimary().getAdd1());
			sfaCustomerMaster.setAddr2(bean.getPrimary().getAdd2());
			sfaCustomerMaster.setAddr3(bean.getPrimary().getAdd3());
			sfaCustomerMaster.setAddr4(bean.getPrimary().getAdd4() != null ? bean.getPrimary().getAdd4() : "");
			sfaCustomerMaster.setInv_type_id(1l);
			sfaCustomerMaster.setDestination(bean.getPrimary().getDestination());
			sfaCustomerMaster.setTin_no("");
			sfaCustomerMaster.setCst_no("");
			sfaCustomerMaster.setDrug_lic1(bean.getGst().getDrug1());
			sfaCustomerMaster.setDrug_lic2(bean.getGst().getDrug2());
			sfaCustomerMaster.setDrug_lic3(bean.getGst().getDrug3());
			sfaCustomerMaster.setPan_no(bean.getGst().getPanNo());
			sfaCustomerMaster.setBank_name("");
			sfaCustomerMaster.setBank_addr1("");
			sfaCustomerMaster.setBank_addr2("");
			sfaCustomerMaster.setBank_addr3("");
//			sfaCustomerMaster.setTel_no(bean.getGst().getTelNo());
			sfaCustomerMaster.setTel_no("");
			sfaCustomerMaster.setFax("");
			sfaCustomerMaster.setEmail(bean.getPrimary().getEmail());
			sfaCustomerMaster.setTransporter(bean.getPrimary().getTransporter());
			sfaCustomerMaster.setGl_account("");
			sfaCustomerMaster.setErp_cust_cd(bean.getPrimary().getErpcustCode());
			sfaCustomerMaster.setMfr_party("");
			sfaCustomerMaster.setCst_rim_ind("");
			sfaCustomerMaster.setPay_mode("");
			sfaCustomerMaster.setAiocd("");
			sfaCustomerMaster.setNarration(bean.getPrimary().getStdNarration());
			sfaCustomerMaster.setCompany_cd(bean.getCompanyCode());
			sfaCustomerMaster.setRowid(UUID.randomUUID().toString());
			sfaCustomerMaster.setLast_mod_dt(new Date());

			transactionalRepository.persist(sfaCustomerMaster);
		} else {
			System.out.println("Record is in sfa customer master");
			sfaCustomerMaster.setCust_id(customermaster.getCust_id());
			sfaCustomerMaster.setLoc_id(bean.getPrimary().getCfaLocId());
			sfaCustomerMaster.setTransporter_id(1l);
			sfaCustomerMaster.setState_id(Long.valueOf(bean.getPrimary().getStateId()));
			sfaCustomerMaster.setTin_valid_dt(null);
			sfaCustomerMaster.setCst_valid_dt(null);
			sfaCustomerMaster.setDl1_valid_dt(bean.getGst().getDrug1Valid());
			sfaCustomerMaster.setDl2_valid_dt(bean.getGst().getDrug2Valid());
			sfaCustomerMaster.setDl3_valid_dt(bean.getGst().getDrug3Valid());
			sfaCustomerMaster.setCst_perc(5l);
			sfaCustomerMaster.setInt_perc(5l);
			sfaCustomerMaster.setForm_type_id(1l);
			sfaCustomerMaster.setTot_credit_lmt(50000l);
			sfaCustomerMaster.setDeleted(N);
			sfaCustomerMaster.setCust_cd(customermaster.getCust_cd());
			sfaCustomerMaster.setCust_name(bean.getPrimary().getCust_name());
			sfaCustomerMaster.setShort_name(bean.getPrimary().getShortName());
			sfaCustomerMaster.setAddr1(bean.getPrimary().getAdd1());
			sfaCustomerMaster.setAddr2(bean.getPrimary().getAdd2());
			sfaCustomerMaster.setAddr3(bean.getPrimary().getAdd3());
			sfaCustomerMaster.setAddr4(bean.getPrimary().getAdd4() != null ? bean.getPrimary().getAdd4() : "");
			sfaCustomerMaster.setInv_type_id(1l);
			sfaCustomerMaster.setDestination(bean.getPrimary().getDestination());
			sfaCustomerMaster.setTin_no("");
			sfaCustomerMaster.setCst_no("");
			sfaCustomerMaster.setDrug_lic1(bean.getGst().getDrug1());
			sfaCustomerMaster.setDrug_lic2(bean.getGst().getDrug2());
			sfaCustomerMaster.setDrug_lic3(bean.getGst().getDrug3());
			sfaCustomerMaster.setPan_no(bean.getGst().getPanNo());
			sfaCustomerMaster.setBank_name("");
			sfaCustomerMaster.setBank_addr1("");
			sfaCustomerMaster.setBank_addr2("");
			sfaCustomerMaster.setBank_addr3("");
//			sfaCustomerMaster.setTel_no(bean.getGst().getTelNo());
			sfaCustomerMaster.setFax("");
			sfaCustomerMaster.setEmail(bean.getPrimary().getEmail());
			sfaCustomerMaster.setTransporter(bean.getPrimary().getTransporter());
			sfaCustomerMaster.setGl_account("");
			sfaCustomerMaster.setErp_cust_cd(bean.getPrimary().getErpcustCode());
			sfaCustomerMaster.setMfr_party("");
			sfaCustomerMaster.setCst_rim_ind("");
			sfaCustomerMaster.setPay_mode("");
			sfaCustomerMaster.setAiocd("");
			sfaCustomerMaster.setNarration(bean.getPrimary().getStdNarration());
			sfaCustomerMaster.setCompany_cd(bean.getCompanyCode());
			sfaCustomerMaster.setRowid(UUID.randomUUID().toString());
			sfaCustomerMaster.setLast_mod_dt(new Date());
			transactionalRepository.update(sfaCustomerMaster);
		}

		// Sfa_Customer_Allocation
		Sfa_Customer_Allocation sfaCustomerAllocation = stockistmasterrepository
				.getCustomerAllocationById(customermaster.getCust_id());

		if (sfaCustomerAllocation == null) {
			System.out.println("Record is not in sfa customer allocation");
			sfaCustomerAllocation = new Sfa_Customer_Allocation();
			sfaCustomerAllocation.setCust_id(customermaster.getCust_id());
			sfaCustomerAllocation.setAlloc_id(1l);
			sfaCustomerAllocation.setCust_cd(customermaster.getCust_cd());
			sfaCustomerAllocation.setCompany_cd(bean.getCompanyCode());
			sfaCustomerAllocation.setDiv_id(33l);
			sfaCustomerAllocation.setCust_type_id(1l);
			sfaCustomerAllocation.setAppnt_dt(null);
			sfaCustomerAllocation.setCurr_status(A);
			sfaCustomerAllocation.setDiscont_dt(null);
			sfaCustomerAllocation.setAlloc_valid_from_dt(null);
			sfaCustomerAllocation.setAlloc_valid_to_dt(null);
			sfaCustomerAllocation.setOld_alloc_id(null);
			sfaCustomerAllocation.setErp_party_cd(customermaster.getCust_cd());
			sfaCustomerAllocation.setSales_rep_id(2457l);
			sfaCustomerAllocation.setHq_id(15418l);
			sfaCustomerAllocation.setPool_ind(N);
			sfaCustomerAllocation.setCredit_limit(500000l);
			sfaCustomerAllocation.setStop_billing_days(null);
			sfaCustomerAllocation.setCredit_days(null);
			sfaCustomerAllocation.setGrace_days(null);
			sfaCustomerAllocation.setDoc_type_id(null);
			sfaCustomerAllocation.setCash_disc(null);
			sfaCustomerAllocation.setCash_disc_days(null);
			sfaCustomerAllocation.setAdd_disc(null);
			sfaCustomerAllocation.setTrade_disc(null);
			sfaCustomerAllocation.setRate_type_id(null);
			sfaCustomerAllocation.setDeleted(N);
			sfaCustomerAllocation.setInvoicing_limit(null);
			sfaCustomerAllocation.setSubac(null);
			sfaCustomerAllocation.setMfr_party(null);
			sfaCustomerAllocation.setNsubac(null);
			sfaCustomerAllocation.setAlt_div_id(33l);
			sfaCustomerAllocation.setInv_group(0l);
			sfaCustomerAllocation.setRowid(UUID.randomUUID().toString());
			sfaCustomerAllocation.setFs_map_code(null);

			transactionalRepository.persist(sfaCustomerAllocation);
		} else {
			System.out.println("Record is present in sfa customer allocation");
			sfaCustomerAllocation.setCust_id(customermaster.getCust_id());
			sfaCustomerAllocation.setAlloc_id(1l);
			sfaCustomerAllocation.setCust_cd(customermaster.getCust_cd());
			sfaCustomerAllocation.setCompany_cd(bean.getCompanyCode());
			sfaCustomerAllocation.setDiv_id(33l);
			sfaCustomerAllocation.setCust_type_id(1l);
			sfaCustomerAllocation.setAppnt_dt(null);
			sfaCustomerAllocation.setCurr_status(A);
			sfaCustomerAllocation.setDiscont_dt(null);
			sfaCustomerAllocation.setAlloc_valid_from_dt(null);
			sfaCustomerAllocation.setAlloc_valid_to_dt(null);
			sfaCustomerAllocation.setOld_alloc_id(null);
			sfaCustomerAllocation.setErp_party_cd(customermaster.getCust_cd());
			sfaCustomerAllocation.setSales_rep_id(2457l);
			sfaCustomerAllocation.setHq_id(15418l);
			sfaCustomerAllocation.setPool_ind(N);
			sfaCustomerAllocation.setCredit_limit(500000l);
			sfaCustomerAllocation.setStop_billing_days(null);
			sfaCustomerAllocation.setCredit_days(null);
			sfaCustomerAllocation.setGrace_days(null);
			sfaCustomerAllocation.setDoc_type_id(null);
			sfaCustomerAllocation.setCash_disc(null);
			sfaCustomerAllocation.setCash_disc_days(null);
			sfaCustomerAllocation.setAdd_disc(null);
			sfaCustomerAllocation.setTrade_disc(null);
			sfaCustomerAllocation.setRate_type_id(null);
			sfaCustomerAllocation.setDeleted(N);
			sfaCustomerAllocation.setInvoicing_limit(null);
			sfaCustomerAllocation.setSubac(null);
			sfaCustomerAllocation.setMfr_party(null);
			sfaCustomerAllocation.setNsubac(null);
			sfaCustomerAllocation.setAlt_div_id(33l);
			sfaCustomerAllocation.setInv_group(0l);
			sfaCustomerAllocation.setRowid(UUID.randomUUID().toString());
			sfaCustomerAllocation.setFs_map_code(null);

			transactionalRepository.update(sfaCustomerAllocation);
		}
	}
	
	@Override
	public CustomerMaster getById(Long custId) throws Exception {
		return stockistmasterrepository.getById(custId);
	}
	
	@Override
	public List<CustomerMaster> getcustNameListByCmpCd(String companyCode) throws Exception {
		return stockistmasterrepository.getcustNameListByCmpCd(companyCode);
	}
	
	@Override
	public List<CustomerMaster> getCustnamesToCheck() throws Exception{
		return stockistmasterrepository.getCustnamesToCheck();
		
	}

}
