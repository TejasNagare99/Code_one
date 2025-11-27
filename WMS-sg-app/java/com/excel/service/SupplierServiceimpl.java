package com.excel.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.excel.bean.SupplierBean;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Supplier;
import com.excel.repository.SupplierRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;


@Service
public class SupplierServiceimpl implements SupplierService{
	
	@Autowired SupplierRepository supplierRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired ParameterService parameterService;

	@Override
	public List<Supplier> getSuppliersBySupplierType(String supplierType, Long locId, Long subCompId) throws Exception {
		return this.supplierRepository.getSuppliersBySupplierType(supplierType, locId, subCompId);
	}

	@Override
	public List<Object> getAllActiveSupplier(String subComp) {
		return this.supplierRepository.getAllActiveSupplier(subComp);
	}
	
	@Override
	public List<SG_d_parameters_details> getSupplierTypeForSupplierEntry() {
		return this.parameterService.getSupplierTypeForSupplierEntry();
	}

	@Override
	public List<SG_d_parameters_details> getStateForSupplierEntry() throws Exception{
		return this.parameterService.getState();
	}
	@Override
	public Boolean checkUniqueSupplier(String SupName, String SupType, String SubCompany) {
		return this.supplierRepository.checkUniqueSupplier(SupName, SupType, SubCompany);
		
	}
	@Override
	public Object checkUniqueGST(@RequestParam String Gst){
		return supplierRepository.checkUniqueGST(Gst);
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveSupplier(SupplierBean supplierBean,HttpServletRequest request,String empId,String gst) throws Exception{
		String ip_addr = request.getRemoteAddr();
		Supplier supp=new Supplier();
		System.out.println("gst no is :"+supplierBean.getgSTNumber()); 
		supp.setGst_reg_no(supplierBean.getgSTNumber()==null || supplierBean.getgSTNumber().equals("0")?null:supplierBean.getgSTNumber());
		if(supplierBean.getgSTNumber()==null || supplierBean.getgSTNumber().equals("0") || supplierBean.getgSTNumber().isEmpty()) {
			supp.setGst_req_ind("N");
		}
		else {
			supp.setGst_req_ind("Y");
		}
		
		supp.setSup_SubComp_id(Long.valueOf(Integer.parseInt(supplierBean.getSubCompany())));
		supp.setSup_nm(supplierBean.getSupplierName());
		supp.setSup_type(supplierBean.getType());
		supp.setSup_cst_no(supplierBean.getcSTNumber());
		supp.setSup_tin_no(supplierBean.gettINNumber());
		supp.setSup_tan_no(supplierBean.gettANNumber());
		
		supp.setSup_pan_no(supplierBean.getpANNumber());
		supp.setSup_status(supplierBean.getStatus());
		supp.setState_id(Long.valueOf(supplierBean.getState()));
		supp.setSup_map_code(supplierBean.getMappedERPCode());
		
		supp.setSup_address1(supplierBean.getAddress1());
		supp.setSup_address2(supplierBean.getAddress2());
		supp.setSup_address3(supplierBean.getAddress3());
		
		supp.setSup_ins_dt(new Date());
		supp.setSup_ins_usr_id(empId);
		supp.setSup_ins_ip_add(ip_addr);
		
	
		this.transactionalRepository.persist(supp);
	}
	@Override
	public List<Supplier> getAllActiveSupplierList(String status){
		return this.supplierRepository.getAllActiveSupplierList(status);
	}
	@Override
	public List<Supplier> getTextParameterSupplierList(String status ,String name, String parameter, String text){
		return this.supplierRepository.getTextParameterSupplierList(status,name, parameter, text);
	}

	@Override
	public List<Supplier> getSupplierDetailById(String supId) {
		return this.supplierRepository.getSupplierDetailById(supId);
	}

	@Override
	public void updateSupplier(String supId,SupplierBean supplierBean, HttpServletRequest request, String empId) {
		String ip_addr = request.getRemoteAddr();
		try {
			Supplier supp=this.supplierRepository.getObjectById(Long.valueOf(supId));
			System.out.println("gst no is :"+supplierBean.getgSTNumber()); 
			supp.setGst_reg_no(supplierBean.getgSTNumber()==null || supplierBean.getgSTNumber().equals("0")?null:supplierBean.getgSTNumber());
			supp.setGst_req_ind(supplierBean.getgSTNumber()==null || supplierBean.getgSTNumber().equals("0")?"N":"Y");
			
			supp.setSup_SubComp_id(Long.valueOf(Integer.parseInt(supplierBean.getSubCompany())));
			supp.setSup_nm(supplierBean.getSupplierName());
			supp.setSup_type(supplierBean.getType());
			supp.setSup_cst_no(supplierBean.getcSTNumber());
			supp.setSup_tin_no(supplierBean.gettINNumber());
			supp.setSup_tan_no(supplierBean.gettANNumber());
			
			supp.setSup_pan_no(supplierBean.getpANNumber());
			supp.setSup_status(supplierBean.getStatus());
			supp.setState_id(Long.valueOf(supplierBean.getState()));
			supp.setSup_map_code(supplierBean.getMappedERPCode());
			
			supp.setSup_address1(supplierBean.getAddress1());
			supp.setSup_address2(supplierBean.getAddress2());
			supp.setSup_address3(supplierBean.getAddress3());
			
			supp.setSup_mod_dt(new Date());
			supp.setSup_mod_usr_id(empId);
			supp.setSup_mod_ip_add(ip_addr);
			
//			supp.setSup_ins_dt(new Date());
//			supp.setSup_ins_usr_id(empId);
//			supp.setSup_ins_ip_add(ip_addr);
			
		
			this.transactionalRepository.update(supp);
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}

	@Override
	public Boolean checkUniqueSupplierNameForModify(String supName, String supType, String subCompany, String supId) {
		return this.supplierRepository.checkUniqueSupplierNameForModify(supName, supType, subCompany, supId);
	}
}
