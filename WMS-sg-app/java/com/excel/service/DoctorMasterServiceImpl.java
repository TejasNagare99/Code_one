package com.excel.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.BulkUpldBean;
import com.excel.bean.DoctorBean;
import com.excel.model.CustomerMaster;
import com.excel.model.DivMaster;
import com.excel.model.DoctorMaster;
import com.excel.model.EmployeeDetails;
import com.excel.model.FieldStaff;
import com.excel.model.Hcp_prod_details;
import com.excel.repository.DoctorRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.TransactionalRepository;

@Service
public class DoctorMasterServiceImpl implements DoctorMasterService{

	@Autowired DoctorRepository doctorRepository;
	@Autowired private FieldStaffRepository fieldStaffRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<DivMaster> getStandardDivisionList() throws Exception {
		
		return this.doctorRepository.getStandardDivisionList();
	}
	
	@Override
	public List<FieldStaff> getDoctorsForFieldStaffEntry(String divId) {
		return this.doctorRepository.getDoctorsForFieldStaffEntry(divId);
	}
	
	@Override
	public List<DoctorMaster> getDoctorsForFieldStaff(String  empId) throws Exception {
		// TODO Auto-generated method stub
		return this.doctorRepository.getDoctorsForFieldStaff(empId);
	}
	
	@Override
	public DoctorMaster getDoctorsDetails(Long docId) throws Exception{
		
		return this.doctorRepository.getDoctorsDetails(docId);
	}
	
	@Override
	public DoctorMaster getDoctorsDetailsByRequestorId(Long docId,Long requestor_id) throws Exception{
		
		return this.doctorRepository.getDoctorsDetailsByRequestorId(docId,requestor_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DoctorMaster savDocDetail(DoctorBean dbbean) throws Exception {
		DoctorMaster docm =new DoctorMaster();
		docm.setCompany(dbbean.getCompanyCode()); ////set company code
		//docm.setdivision(dbbean.getDivision());          //---------div id req 
	//	docm.setDoc_ins_usr_id(dbbean.getUserid());
		docm.setDoc_ins_usr_id(dbbean.getEmpId());
		docm.setDoc_mod_usr_id(null);
		docm.setDoc_ins_ip_add(dbbean.getIpaddress());
		docm.setDoc_mod_ip_add(null);
		docm.setDoc_ins_dt(new Date());
		docm.setDoc_mod_dt(null);
		docm.setFstaff_id(Long.valueOf(dbbean.getFstaff_id()));//dbbean.getFieldstaffcategory()
		//docm.setFstaff_id(Long.valueOf(dbbean.getFstaff_id()));   // // FieldStaffCategory PASS TYPE
		docm.setFstaff_emp_id(dbbean.getFstaff_emp_num());  // FieldStaffCategory PASS ID
		docm.setDoc_class(dbbean.getDocclass());
		docm.setDoc_status(dbbean.getStatus());
		docm.setDoc_speciality(dbbean.getDocspeciality());
		docm.setDoc_name(dbbean.getFullName());    ////---------set full name i.e  first + last 
		docm.setDoc_fname(dbbean.getFirstName());
		docm.setDoc_mname(dbbean.getMiddleName());
		docm.setDoc_lname(dbbean.getLastName());
		docm.setMcl_no(dbbean.getMclNo());
		docm.setDoc_full_adr(dbbean.getDoc_full_add());
		docm.setDoc_address1(dbbean.getAddress1());
		docm.setDoc_address2(dbbean.getAddress2());
		docm.setDoc_address3(dbbean.getAddress3());
		docm.setDoc_address4(dbbean.getAddress4());
		docm.setDoc_city(dbbean.getCity());
		docm.setDoc_pincode(dbbean.getPinCode());
		docm.setDoc_phone(dbbean.getTelephone1());
		docm.setDoc_phone2(dbbean.getTelephone2());
		docm.setDoc_email(dbbean.getEmail());
		docm.setDoc_prefix(dbbean.getPrefix());
		docm.setState_name(dbbean.getStatename());
		docm.setState_id(dbbean.getStateId());
		this.transactionalRepository.persist(docm);
		return docm;
	}
	
	@Override
	public DoctorMaster getDoctorDetailById(String doc_id) throws Exception{
		return this.doctorRepository.getDoctorDetailById(doc_id);
	}

	@Override
	public List<DoctorMaster> getAllActiveDoctorMasterDetail() throws Exception {
		return this.doctorRepository.getAllActiveDoctorMasterDetail();
	}
	
	@Override
	public List<DoctorMaster> getDoctorTextParameterDetail(String name,String parameter,String text,String empId) throws Exception {
		return this.doctorRepository.getDoctorTextParameterDetail(name,parameter,text,empId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDoctorMaster(String doc_id, DoctorBean dbbean, String empId, HttpServletRequest request)
			throws Exception {
		DoctorMaster docm =this.doctorRepository.getByObjectId(Long.valueOf(doc_id));
		if(docm.getFstaff_id()!=Long.parseLong(dbbean.getFieldstaffcategory())) {
			docm.setDoc_fstaff_id(null);
		}
		docm.setDoc_id(docm.getDoc_id());
		docm.setDoc_ins_usr_id(docm.getDoc_ins_usr_id());
		docm.setDoc_mod_usr_id(dbbean.getEmpId());
		docm.setDoc_ins_ip_add(docm.getDoc_ins_ip_add());
		docm.setDoc_mod_ip_add(dbbean.getIpaddress());
		docm.setDoc_ins_dt(docm.getDoc_ins_dt());
		docm.setDoc_mod_dt(new Date());
		docm.setFstaff_id(Long.parseLong(dbbean.getFieldstaffcategory()));   // // FieldStaffCategory PASS TYPE
		docm.setFstaff_emp_id(dbbean.getFstaff_emp_num());  // FieldStaffCategory PASS ID
		docm.setDoc_class(dbbean.getDocclass());
		docm.setDoc_status(dbbean.getStatus());
		docm.setDoc_speciality(dbbean.getDocspeciality());
		docm.setDoc_name(dbbean.getFullName());    ////---------set full name i.e  first + last 
		docm.setDoc_fname(dbbean.getFirstName());
		docm.setDoc_mname(dbbean.getMiddleName());
		docm.setDoc_lname(dbbean.getLastName());
		docm.setDoc_address1(dbbean.getAddress1());
		docm.setDoc_address2(dbbean.getAddress2());
		docm.setDoc_address3(dbbean.getAddress3());
		docm.setDoc_address4(dbbean.getAddress4());
		docm.setDoc_city(dbbean.getCity());
		docm.setDoc_pincode(dbbean.getPinCode());
		docm.setDoc_phone(dbbean.getTelephone1());
		docm.setDoc_phone2(dbbean.getTelephone2());
		docm.setDoc_email(dbbean.getEmail());
		docm.setDoc_prefix(dbbean.getPrefix());
		this.transactionalRepository.update(docm);
		System.out.println("doc fstaff id::::::"+docm.getDoc_fstaff_id());
		if(docm.getDoc_fstaff_id()!=null && docm.getDoc_fstaff_id()!=0) {
			FieldStaff fstaff= this.fieldStaffRepository.getFstaffDetailsFromDocId(docm.getDoc_fstaff_id());
			if(fstaff!=null) {
				fstaff.setFstaff_addr1(dbbean.getAddress1());
				fstaff.setFstaff_addr2(dbbean.getAddress2());
				fstaff.setFstaff_addr3(dbbean.getAddress3());
				fstaff.setFstaff_addr4(dbbean.getAddress4());
				fstaff.setFstaff_mobile(dbbean.getTelephone1());
				fstaff.setFstaff_tel_no(dbbean.getTelephone2());
			    fstaff.setFstaff_zip(dbbean.getPinCode());
			    fstaff.setFstaff_destination(dbbean.getCity());
				fstaff.setEmail(dbbean.getEmail());
				this.transactionalRepository.update(fstaff);
			}
		}
		else {
			System.out.println(":::Doctor doc_fsid is not available or its 0:::");
		}

	}

	@Override
	public FieldStaff getObjectfieldstaffbyId(Long fstaff_id) throws Exception {
		// TODO Auto-generated method stub
		return fieldStaffRepository.getObjectByStaffId(fstaff_id);
	}

	@Override
	public DivMaster getObjectDivmasterbyId(Long div_id) throws Exception {
		// TODO Auto-generated method stub
		 return this.entityManager.find(DivMaster.class, div_id);
	}
	
	public List<EmployeeDetails> getEmpIdByFStaffId(Long fstaff_id) throws Exception {
		return this.doctorRepository.getEmpIdByFStaffId(fstaff_id);
		
	}

	@Override
	public EmployeeDetails getEmployeeDetails(String empId) throws Exception {
		// TODO Auto-generated method stub
		return this.doctorRepository.getEmployeeDetails(empId);
	}

	@Override
	public List<BulkUpldBean> getDoctorsForBulkUpload(String div_id, String fs_id, String compareString)
			throws Exception {
		// TODO Auto-generated method stub
		return this.doctorRepository.getDoctorsForBulkUpload(div_id, fs_id, compareString);
	}

	@Override
	public List<CustomerMaster> getDoctorsFromMdmBySpecialityAndClass(String doc_class, String doc_spec)
			throws Exception {
		return this.doctorRepository.getDoctorsFromMdmBySpecialityAndClass(doc_class, doc_spec);
	}

	@Override
	public List<Hcp_prod_details> getProduct_details(String id) {
		// TODO Auto-generated method stub
		return this.doctorRepository.getProduct_details(id);
	}

}
