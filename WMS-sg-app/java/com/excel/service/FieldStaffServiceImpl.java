package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.FieldStaffBean;
import com.excel.model.AllocReqHeader;
import com.excel.model.DivField;
import com.excel.model.DoctorMaster;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaffCsvDownload;
import com.excel.model.FieldStaffList;
import com.excel.model.FieldStaffListNew;
import com.excel.model.Fieldstaff_After_mobile_No_Update_CheckList;
import com.excel.model.Fieldstaff_Detail;
import com.excel.model.Fieldstaff_mobileno_update_log;
import com.excel.model.V_Fieldstaff;
import com.excel.model.V_G_FieldStaff;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.restcontroller.FieldStaffRestController;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

@Service
public class FieldStaffServiceImpl implements FieldStaffService,MedicoConstants{
	
	@Autowired private FieldStaffRepository fieldStaffRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Autowired
	private FieldStaffRestController fieldstaffrestcontroller;
	@Autowired
	private ReportService reportService;
	@Autowired
	private FieldMasterDownloadService fieldMasterDownloadService;
	
	@Override
	public List<FieldStaffList> getFieldStaffList(String emp_id, Long div_id) throws Exception {
		return fieldStaffRepository.getFieldStaffList(emp_id, div_id);
	}

	@Override
	public V_Fieldstaff getDivListByEmpId(String fstaff_samp_disp_ind, Long fstaff_id) throws Exception {
		return fieldStaffRepository.getDivListByEmpId(fstaff_samp_disp_ind, fstaff_id);
	}

	@Override
	public FieldStaff getObjectByStaffId(Long staff_id) throws Exception {
		
		return fieldStaffRepository.getObjectByStaffId(staff_id);
	}
	@Override
	public List<FieldStaff> getFieldStaffsByEmpIdAndHasResigned(Long divId, boolean hasResigned, String companyCode)
			throws Exception {
		return fieldStaffRepository.getFieldStaffsByEmpIdAndHasResigned(divId, hasResigned, companyCode);
	}

	@Override
	public Long getDivIdByFsId(Long fstaff_id) throws Exception {
		return this.fieldStaffRepository.getDivIdByFsId(fstaff_id);
	}

	@Override
	public List<Object> getNoOfStaffByStaffwiseTraining(String lvlCode, String divId, String status, String allocMode,
			String cfalocId, String dist, String fstaffType, String alloc_id, String period_code, String alloc_cycle,
			String prodtype, String fin_year, String company,String sub_team_code) {
		return this.fieldStaffRepository.getNoOfStaffByStaffwiseTraining(lvlCode, divId, status, allocMode, cfalocId, dist, fstaffType, alloc_id, period_code, alloc_cycle, prodtype, fin_year, company,sub_team_code);
	}
	
	@Override
	public List<DivField> getLevel(String divId,String ind) {
		return this.fieldStaffRepository.getLevel(divId,ind);
	}
	@Override
	public List<FieldStaff> getAllMgr(String level,String divId,String trainingInd){
		return this.fieldStaffRepository.getAllMgr(level,divId,trainingInd);
	}
	@Override
	public List<FieldStaff> getMgr(String mgrId) {
		return this.fieldStaffRepository.getMgr(mgrId);
	}
	@Override
	public Boolean checkUniqueGenericName(String genName,String divId) {
		return this.fieldStaffRepository.checkUniqueGenericName(genName, divId);
	}
	@Override
	public List<FieldStaff> getRequestor(String divId) {
		return this.fieldStaffRepository.getRequestor(divId);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveFieldStaff(FieldStaffBean fb,String empId,String locId,HttpSession session,HttpServletRequest request) throws Exception {
			FieldStaff fs=new FieldStaff();
			fs.setFstaff_code("0");
			System.out.println("getFstaff_code : "+fs.getFstaff_code().length());
			fs.setCompany(fb.getCompany().trim());
			System.out.println("getCompany : "+fs.getCompany().trim().length());
			fs.setClass_id(fb.getClasss()==null || fb.getClasss().isEmpty()?0L:Long.valueOf(fb.getClasss().trim()));
			fs.setCity_id(fb.getCity()==null || fb.getCity().isEmpty()?0L:Long.valueOf(fb.getCity().trim()));
			fs.setCf_idwyth(fb.getCfa2()==null?0L:Long.valueOf(fb.getCfa2().trim()));
			fs.setCf_idpipl(fb.getCfa3()==null?0L:Long.valueOf(fb.getCfa3().trim()));
			fs.setCf_idpfz(fb.getCfa1()==null?0L:Long.valueOf(fb.getCfa1().trim()));
			fs.setDesignation(fb.getDesignation().trim());
			System.out.println("getDesignation : "+fs.getDesignation().length());
			fs.setDist_code(fb.getDistrictCode()==null || fb.getDistrictCode().isEmpty()?"":fb.getDistrictCode().trim());
			System.out.println("getDist_code : "+fs.getDist_code().length());
			fs.setEmail(fb.getEmail()==null || fb.getEmail().isEmpty()?"":fb.getEmail().trim());
			System.out.println("setEmail : "+fs.getEmail().length());
			fs.setFs_div_id(Long.valueOf(fb.getDivision().trim()));
			fs.setFstaff_addr1(fb.getAddress1().trim());
			System.out.println("setFstaff_addr1 : "+fs.getFstaff_addr1().length());
			fs.setFstaff_addr2(fb.getAddress2()==null || fb.getAddress2().isEmpty()?"":fb.getAddress2().trim());
			System.out.println("setFstaff_addr2 : "+fs.getFstaff_addr2().length());
			fs.setFstaff_addr3(fb.getAddress3()==null || fb.getAddress3().isEmpty()?"":fb.getAddress3().trim());
			System.out.println("setFstaff_addr3 : "+fs.getFstaff_addr3().length());
			fs.setFstaff_addr4(fb.getAddress4()==null || fb.getAddress4().isEmpty()?"":fb.getAddress4().trim());
			System.out.println("setFstaff_addr4 : "+fs.getFstaff_addr4().length());
			fs.setFstaff_addr_upd_cycle(0L);
			fs.setFstaff_alt_name(fb.getAlternateName().trim());
			System.out.println("setFstaff_alt_name : "+fs.getFstaff_alt_name().length());
			//fs.setFstaff_code(fb.getAlternateName());
			fs.setFstaff_destination(fb.getDispatchDestination().trim());
			System.out.println("setFstaff_destination : "+fs.getFstaff_destination().length());
			fs.setFstaff_disp_cycle(0L);
			fs.setFstaff_display_name(fb.getFieldStaffName().trim());
			System.out.println("setFstaff_display_name : "+fs.getFstaff_display_name().length());
			fs.setFstaff_dom_exp(fb.getTeam()==null || fb.getTeam().isEmpty()?"":fb.getTeam().trim());
			System.out.println("setFstaff_dom_exp : "+fs.getFstaff_dom_exp().length());
			fs.setFstaff_emp_num(fb.getEmployeeNo()==null || fb.getEmployeeNo().isEmpty()?"":fb.getEmployeeNo().trim());
			System.out.println("setFstaff_emp_num : "+fs.getFstaff_emp_num().length());
			fs.setFstaff_erp_code(fb.getErpSystemCode()==null || fb.getErpSystemCode().isEmpty()?"":fb.getErpSystemCode().trim());
			System.out.println("setFstaff_erp_code : "+fs.getFstaff_erp_code().length());
			fs.setFstaff_erp_loc_code(fb.getErpSupplyPlantCode()==null || fb.getErpSupplyPlantCode().isEmpty()?"":fb.getErpSupplyPlantCode().trim());
			System.out.println("setFstaff_erp_loc_code : "+fs.getFstaff_erp_loc_code().length());
			fs.setHq_id(fb.getHq()==null?0L:Long.valueOf(fb.getHq().trim()));
			fs.setFstaff_ins_dt(new Date());
			fs.setFstaff_ins_ip_add(request.getRemoteAddr().trim());
			fs.setFstaff_ins_usr_id(empId.trim());
			fs.setFstaff_joining_date(fb.getJoiningDate()==null?null:fb.getJoiningDate());
			fs.setLeaving_date(fb.getLeavingDate()==null?null:fb.getLeavingDate());
			fs.setFstaff_loc_id(Long.valueOf(locId.trim()));
			fs.setLevel_code(fb.getHierarchyLevel().trim());
			fs.setFstaff_map_code1(fb.getMapCode1()==null || fb.getMapCode1().isEmpty()?"":fb.getMapCode1().trim());
			fs.setFstaff_map_code2(fb.getMapCode2()==null || fb.getMapCode2().isEmpty()?"":fb.getMapCode2().trim());
			
			fs.setFstaff_terr_id(fb.getTerrId()==null?"":fb.getTerrId().trim());
			fs.setFstaff_terr_code(fb.getTerritoryCode()==null?"":fb.getTerritoryCode().trim());
			fs.setFstaff_terrname(fb.getTerritoryName()==null?"":fb.getTerritoryName().trim());
			fs.setFstaff_mgr1_id(fb.getAbm()==null?0L:Long.valueOf(fb.getAbm().trim()));
			fs.setFstaff_mgr2_id(fb.getRbm()==null?0L:Long.valueOf(fb.getRbm().trim()));
			fs.setFstaff_mgr3_id(fb.getZbm()==null?0L:Long.valueOf(fb.getZbm().trim()));
			fs.setFstaff_mgr4_id(fb.getDsm()==null?0L:Long.valueOf(fb.getDsm().trim()));
			fs.setFstaff_mgr5_id(fb.getSbuHead()==null?0L:Long.valueOf(fb.getSbuHead().trim()));
			fs.setFstaff_mgr6_id(0L);
			//fs.setFstaff_mgr6_id(fb.getMgr6_id()==null?0L:Long.valueOf(fb.getMgr6_id()));
			
			
			fs.setFstaff_mobile(fb.getMobile().trim());
			//fs.setFstaff_mod_dt();
			fs.setFstaff_mod_ip_add("");
			fs.setFstaff_mod_usr_id("");
			fs.setFstaff_name(fb.getGenericName().trim());
			fs.setFstaff_pan_no(fb.getPanNo()==null || fb.getPanNo().isEmpty()?"":fb.getPanNo().trim());
			fs.setFstaff_pmp_perf(BigDecimal.ZERO);
			fs.setFstaff_pool_ind(fb.getPoolIndicator().trim());
			fs.setRegion_code(fb.getRegionCode()==null || fb.getRegionCode().isEmpty()?"":fb.getRegionCode().trim());
			fs.setFstaff_samp_disp_ind(fb.getDispatchPsPi().trim());
			fs.setFstaff_samp_sup_loc(0L);
			fs.setStatus(fb.getCurrentStatus().trim());
			fs.setFstaff_state_id(Long.valueOf(fb.getState().trim()));
			fs.setFstaff_tel_no(fb.getTelephone()==null || fb.getTelephone().isEmpty()?"":fb.getTelephone().trim());
			fs.setFstaff_transporter(fb.getTransporter()==null || fb.getTransporter().isEmpty()?"":fb.getTransporter().trim());
			fs.setFstaff_training(fb.getTrainingInd()==null?"":fb.getTrainingInd().trim());
//			//fs.setFs_category(fb.getFieldStaffCategory()==null?"":fb.getFieldStaffCategory().trim());
			fs.setFs_category(fb.getFsCategory()==null?"":fb.getFsCategory().trim());
			fs.setType_id(fb.getType()==null || fb.getType().isEmpty()?0L:Long.valueOf(fb.getType().trim()));
			fs.setFstaff_zip(fb.getPinCode().trim());
			fs.setFstaff_zone_id(fb.getZone()==null || fb.getZone().isEmpty()?0L:Long.valueOf(fb.getZone().trim()));
			fs.setFstaff_zonename(fb.getZoneName().trim());
			
			fs.setFstaff_type_ind_id(Long.valueOf(fb.getFieldStaffCategory().trim()));
			fs.setFstaff_mcl_no(fb.getMclNo()==null || fb.getMclNo().isEmpty() ? "":fb.getMclNo().trim());
			fs.setFstaff_requestor_fstaff_id(fb.getRequestor()==null || fb.getRequestor().isEmpty()?0L:Long.valueOf(fb.getRequestor().trim()));
			fs.setAddr_chg_remarks(fb.getRemark());
			fs.setFstaff_mobile2(fb.getMobile2());
			if(fs.getFs_category().equals("T"))
				fs.setFstaff_training("T");
			else
				fs.setFstaff_training("F");
			if(fb.getProductType()!=null) {
				System.out.println("prod type length is :"+fb.getProductType().length);
				String prodSubType[]=fb.getProductType();
				Long pta ;
				String prodSubTypeAllowed=null;
				String combinedProdSubType = "";
				for(int i=0;i<prodSubType.length;i++) {
					if(!prodSubType[i].equals("0")) {
						pta=Long.valueOf(prodSubType[i]);
						prodSubTypeAllowed=String.format("%05d",pta);
						System.out.println(prodSubTypeAllowed);
						combinedProdSubType+=prodSubTypeAllowed;
						fs.setFstaff_prod_sub_type_allowed(combinedProdSubType.trim());
					}
					else {
						System.out.println("product sub type allowed is 0");
						fs.setFstaff_prod_sub_type_allowed("0");
					}
				}
				System.out.println("generated su types are :"+fs.getFstaff_prod_sub_type_allowed());
				
				
			}
			else {
				fs.setFstaff_prod_sub_type_allowed("0");
			}
//			fs.setFstaff_prod_sub_type_allowed("");
			
			fs.setTeam_code(fb.getTeamcode()==null?"0":fb.getTeamcode().trim());
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyJson = gson.toJson(fs);
			System.out.println(prettyJson);
			
			this.transactionalRepository.persist(fs);
			
			fs.getFstaff_id();
			String fs_code=String.format("%010d",fs.getFstaff_id());
			System.out.println("fs code generated is :"+fs_code.trim());
			fs.setFstaff_code(fs_code==null?"":fs_code);
			
			this.transactionalRepository.update(fs);
			
			System.out.println("FS_ID"+fs.getFstaff_id().toString());
			System.out.println("PFZ"+fs.getCf_idpfz().toString());
			System.out.println("PIPL"+fs.getCf_idpipl().toString());
			
			
				
			
			
			StoredProcedureQuery spq  = this.entityManager.createNamedStoredProcedureQuery("FieldstaffMaster_Detail");
			spq.setParameter("pFSTAFF_ID",Integer.valueOf(fs.getFstaff_id().toString()));
			spq.setParameter("pCFAPFZ", Integer.valueOf(fs.getCf_idpfz().toString()));
			spq.setParameter("pCFAPIPL", Integer.valueOf(fs.getCf_idpipl().toString()));
			spq.setParameter("pCFAWYTH", Integer.valueOf(fs.getCf_idwyth().toString()));
			
			spq.executeUpdate();
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateFieldStaff(String fstaffId,FieldStaffBean fb,String empId,String locId,HttpSession session,HttpServletRequest request) throws Exception {
		FieldStaff fs=fieldStaffRepository.getObjectByStaffId(Long.valueOf(fstaffId));
		fs.setFstaff_code("0");
		fs.setFstaff_mod_dt(new Date());
		fs.setFstaff_mod_ip_add(request.getRemoteAddr().trim());
		fs.setFstaff_mod_usr_id(empId.trim());
		fs.setCompany(fb.getCompany().trim());
		fs.setClass_id(fb.getClasss()==null || fb.getClasss().isEmpty()?0L:Long.valueOf(fb.getClasss().trim()));
		fs.setCity_id(fb.getCity()==null || fb.getCity().isEmpty()?0L:Long.valueOf(fb.getCity().trim()));
		fs.setCf_idwyth(fb.getCfa2()==null?0L:Long.valueOf(fb.getCfa2().trim()));
		fs.setCf_idpipl(fb.getCfa3()==null?0L:Long.valueOf(fb.getCfa3().trim()));
		fs.setCf_idpfz(fb.getCfa1()==null?0L:Long.valueOf(fb.getCfa1().trim()));
		fs.setDesignation(fb.getDesignation().trim());
		fs.setDist_code(fb.getDistrictCode()==null || fb.getDistrictCode().isEmpty()?"":fb.getDistrictCode().trim());
		fs.setEmail(fb.getEmail()==null || fb.getEmail().isEmpty()?"":fb.getEmail().trim());
		fs.setFs_div_id(Long.valueOf(fb.getDivision().trim()));
		fs.setFstaff_addr1(fb.getAddress1().trim());
		fs.setFstaff_addr2(fb.getAddress2()==null || fb.getAddress2().isEmpty()?"":fb.getAddress2().trim());
		fs.setFstaff_addr3(fb.getAddress3()==null || fb.getAddress3().isEmpty()?"":fb.getAddress3().trim());
		fs.setFstaff_addr4(fb.getAddress4()==null || fb.getAddress4().isEmpty()?"":fb.getAddress4().trim());
		fs.setFstaff_addr_upd_cycle(0L);
		fs.setFstaff_destination(fb.getDispatchDestination().trim());
		fs.setFstaff_disp_cycle(0L);
		fs.setFstaff_display_name(fb.getFieldStaffName().trim());
		
		//Error  (not getting updated alternate name) fs.setFstaff_alt_name(fb.getAlternateName().trim());
//		fs.setFstaff_dom_exp(fb.getTeam()==null || fb.getTeam().isEmpty()?"":fb.getTeam().trim());
		
		fs.setFstaff_emp_num(fb.getEmployeeNo()==null || fb.getEmployeeNo().isEmpty()?"":fb.getEmployeeNo().trim());
		fs.setFstaff_erp_code(fb.getErpSystemCode()==null || fb.getErpSystemCode().isEmpty()?"":fb.getErpSystemCode().trim());
		fs.setFstaff_erp_loc_code(fb.getErpSupplyPlantCode()==null || fb.getErpSupplyPlantCode().isEmpty()?"":fb.getErpSupplyPlantCode().trim());
		fs.setHq_id(fb.getHq()==null?0L:Long.valueOf(fb.getHq().trim()));
		fs.setFstaff_joining_date(fb.getJoiningDate()==null?null:fb.getJoiningDate());
		fs.setLeaving_date(fb.getLeavingDate()==null?null:fb.getLeavingDate());
		fs.setFstaff_loc_id(Long.valueOf(locId.trim()));
		fs.setLevel_code(fb.getHierarchyLevel().trim());
		fs.setFstaff_map_code1(fb.getMapCode1()==null || fb.getMapCode1().isEmpty()?"":fb.getMapCode1().trim());
		fs.setFstaff_map_code2(fb.getMapCode2()==null || fb.getMapCode2().isEmpty()?"":fb.getMapCode2().trim());

		fs.setFstaff_terr_id(fb.getTerrId()==null?"":fb.getTerrId().trim());
		fs.setFstaff_terr_code(fb.getTerritoryCode()==null?"":fb.getTerritoryCode().trim());
		fs.setFstaff_terrname(fb.getTerritoryName()==null?"":fb.getTerritoryName().trim());

		fs.setFstaff_mgr1_id(fb.getAbm()==null?0L:Long.valueOf(fb.getAbm().trim()));		
		fs.setFstaff_mgr2_id(fb.getRbm()==null?0L:Long.valueOf(fb.getRbm().trim()));
		fs.setFstaff_mgr3_id(fb.getZbm()==null?0L:Long.valueOf(fb.getZbm().trim()));
		fs.setFstaff_mgr4_id(fb.getDsm()==null?0L:Long.valueOf(fb.getDsm().trim()));
		fs.setFstaff_mgr5_id(fb.getSbuHead()==null?0L:Long.valueOf(fb.getSbuHead().trim()));
		fs.setFstaff_mgr6_id(0L);
		fs.setFstaff_mobile(fb.getMobile().trim());
		fs.setFstaff_name(fb.getGenericName().trim());
		fs.setFstaff_pan_no(fb.getPanNo()==null || fb.getPanNo().isEmpty()?"":fb.getPanNo().trim());
		fs.setFstaff_pmp_perf(BigDecimal.ZERO);
		fs.setFstaff_pool_ind(fb.getPoolIndicator().trim());
		fs.setRegion_code(fb.getRegionCode()==null || fb.getRegionCode().isEmpty()?"":fb.getRegionCode().trim());
		fs.setFstaff_samp_disp_ind(fb.getDispatchPsPi().trim());
		fs.setFstaff_samp_sup_loc(0L);
		fs.setStatus(fb.getCurrentStatus().trim());
		if(fs.getStatus().trim().equals("I")) {
			fs.setFstaff_terr_id("0");
		}
		fs.setFstaff_state_id(Long.valueOf(fb.getState().trim()));
		fs.setFstaff_tel_no(fb.getTelephone()==null || fb.getTelephone().isEmpty()?"":fb.getTelephone().trim());
		fs.setFstaff_transporter(fb.getTransporter()==null || fb.getTransporter().isEmpty()?"":fb.getTransporter().trim());
		fs.setFstaff_training(fb.getTrainingInd()==null?"":fb.getTrainingInd().trim());
		fs.setFstaff_alt_name(fb.getAlternateName());
		System.out.println("Binary data truncted here :"+fb.getFieldStaffCategory());
		
/////////		(Binary Data Truncted here ))
//////////		fs.setFs_category(fb.getFieldStaffCategory()==null?"":fb.getFieldStaffCategory().trim());
		
		//updated solution of BDT
		fs.setFs_category(fb.getFsCategory()==null?"":fb.getFsCategory().trim());

		fs.setType_id(fb.getType()==null || fb.getType().isEmpty()?0L:Long.valueOf(fb.getType().trim()));
		fs.setFstaff_zip(fb.getPinCode().trim());
		fs.setFstaff_zone_id(fb.getZone()==null || fb.getZone().isEmpty()?0L:Long.valueOf(fb.getZone().trim()));
//		(no data from UI for zonename) 
		fs.setFstaff_zonename(fb.getZoneName()==null?"":fb.getZoneName().trim());		
		fs.setFstaff_type_ind_id(Long.valueOf(fb.getFieldStaffCategory().trim()));
		fs.setFstaff_mcl_no(fb.getMclNo()==null || fb.getMclNo().isEmpty() ? "":fb.getMclNo().trim());
		fs.setFstaff_requestor_fstaff_id(fb.getRequestor()==null || fb.getRequestor().isEmpty()?0L:Long.valueOf(fb.getRequestor().trim()));
		fs.setAddr_chg_remarks(fb.getRemark());
		fs.setFstaff_mobile2(fb.getMobile2());
		if(fs.getFs_category().equals("T"))
			fs.setFstaff_training("T");
		else
			fs.setFstaff_training("F");
		
		//Newly Added by me
		fs.setTeam_code(fb.getTeamcode().trim());
		
		if(fb.getProductType()!=null) {
			System.out.println("prod type is :"+fb.getProductType());
			String prodSubType[]=fb.getProductType();
			Long pta ;
			String prodSubTypeAllowed=null;
			String combinedProdSubType = "";
			for(int i=0;i<prodSubType.length;i++) {
				if(!prodSubType[i].equals("0")) {
					pta=Long.valueOf(prodSubType[i]);
					prodSubTypeAllowed=String.format("%05d",pta);
					System.out.println(prodSubTypeAllowed);
					combinedProdSubType+=prodSubTypeAllowed;
					fs.setFstaff_prod_sub_type_allowed(combinedProdSubType.trim());
				}
				else if(prodSubType[i].equals("0")){
					System.out.println("Product sub type allowed is 0");
					fs.setFstaff_prod_sub_type_allowed("0");
				}
			}
			System.out.println("generated su types are :"+fs.getFstaff_prod_sub_type_allowed());
			
		}
		else {
			fs.setFstaff_prod_sub_type_allowed("0");
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(fs);
		System.out.println(prettyJson);
		
		this.transactionalRepository.update(fs);
		
		fs.getFstaff_id();
		String fs_code=String.format("%010d",fs.getFstaff_id());
		System.out.println("fs code generated is :"+fs_code.trim());
		fs.setFstaff_code(fs_code==null?"":fs_code);
		

		this.transactionalRepository.update(fs);
		
		System.out.println("fieldMaster:::::"+fs.toString());
		
		System.out.println("FS Hierarchy Level : "+fb.getHierarchyLevel()+"fb.getTerritoryCode() : "+fb.getTerritoryCode());
//Temporary Comment
		if(fb.getCompany().trim().equals("NHP") || fb.getCompany().trim().equals("NIL") || fb.getCompany().trim().equals(VET)) {
			if(fb.getHierarchyLevel().trim().equals("003")) {
				fieldStaffRepository.updateFieldByTerritoryCode(fb.getTerritoryCode().trim().substring(0,2),fb.getHierarchyLevel(),
						request.getRemoteAddr().trim(),empId.trim());
			}
			
			if(fb.getHierarchyLevel().trim().equals("002")) {
				fieldStaffRepository.updateFieldByTerritoryCode(fb.getTerritoryCode().trim().substring(0,3),fb.getHierarchyLevel(),
						request.getRemoteAddr().trim(),empId.trim());
			}
		}
		else {
		if(fb.getHierarchyLevel().trim().equals("003")) {
			fieldStaffRepository.updateFieldByTerritoryCode(fb.getTerritoryCode().trim().substring(0,2),fb.getHierarchyLevel(),
					request.getRemoteAddr().trim(),empId.trim());
		}
		
		if(fb.getHierarchyLevel().trim().equals("002")) {
			fieldStaffRepository.updateFieldByTerritoryCode(fb.getTerritoryCode().trim().substring(0,4),fb.getHierarchyLevel(),
					request.getRemoteAddr().trim(),empId.trim());
		}
		}
		//fs.setFstaff_ins_dt(new Date());
		//fs.setFstaff_ins_ip_add(request.getRemoteAddr().trim());
		//fs.setFstaff_ins_usr_id(empId.trim());
		
		fs.setFstaff_mod_dt(new Date());
		fs.setFstaff_mod_ip_add(request.getRemoteAddr().trim());
		fs.setFstaff_mod_usr_id(empId.trim());
			
//		
//		String id_wyth="";
//		if(fb.getCompany().trim().equals("SNK")) {
//			id_wyth=fs.getCf_idwyth().toString();
//			
//		
//		}else {
//			id_wyth=fs.getCf_idpipl().toString();
//		}
//		
		
		this.Inactive_previous_deatials(fs.getFstaff_id());
		
		System.out.println("FS_ID"+fs.getFstaff_id().toString());
		System.out.println("PFZ"+fs.getCf_idpfz().toString());
		System.out.println("PIPL"+fs.getCf_idpipl().toString());
		System.out.println("WYETH"+fs.getCf_idwyth().toString());
		StoredProcedureQuery spq  = this.entityManager.createNamedStoredProcedureQuery("FieldstaffMaster_Detail");
		
//		@pCFAWYTH
		spq.setParameter("pFSTAFF_ID",Integer.valueOf(fs.getFstaff_id().toString()));
		spq.setParameter("pCFAPFZ", Integer.valueOf(fs.getCf_idpfz().toString()));
		spq.setParameter("pCFAPIPL", Integer.valueOf(fs.getCf_idpipl().toString()));
		spq.setParameter("pCFAWYTH", Integer.valueOf(fs.getCf_idwyth().toString()));
		spq.executeUpdate();
	}
	private void Inactive_previous_deatials(Long fstaff_id) {
	
		
		
	}

	@Override
	public List<FieldStaffBean> getFstaffDetail(String mgrId,String level,String divId) {
		return this.fieldStaffRepository.getFstaffDetail(mgrId, level,divId);
	}

	@Override
	public List<V_G_FieldStaff> getAllActiveFieldStaffDetailList() {
		return this.fieldStaffRepository.getAllActiveFieldStaffDetailList();
	}

	@Override
	public List<V_G_FieldStaff> getTextParameterFstaffDetail(String name, String parameter, String text,String empId) {
		return this.fieldStaffRepository.getTextParameterFstaffDetail(name, parameter, text,empId);
	}

	@Override
	public List<FieldStaff> getFstaffDetailById(String id) {
		return this.fieldStaffRepository.getFstaffDetailById(id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long setFiledStafForSpecialAllocation(AllocReqHeader header,FieldStaff staff,DoctorMaster docMaster) throws Exception {
		FieldStaff fs=new FieldStaff();
		fs.setFstaff_code("0");
		fs.setCompany(header.getCompany());
		fs.setClass_id(0L);
		fs.setCity_id(staff.getCity_id()==null ?0L:staff.getCity_id());
		fs.setCf_idwyth(0L);
		fs.setCf_idpipl(0L);
		fs.setCf_idpfz(0L);
		if(header.getRecipient_type().trim().equalsIgnoreCase("D")) {
			fs.setDesignation("DOCTOR");
			fs.setFs_category("D");
		}else {
			fs.setDesignation(staff.getDesignation());
			fs.setFs_category("E");
		}
		
		fs.setDist_code(staff.getDist_code()==null ?"":staff.getDist_code());
		fs.setEmail(header.getRecipient_email());
		fs.setFs_div_id(staff.getFs_div_id());
		fs.setFstaff_addr1(header.getRecipient_address1()==null || header.getRecipient_address1().isEmpty()?"":header.getRecipient_address1());
		fs.setFstaff_addr2(header.getRecipient_address2()==null || header.getRecipient_address2().isEmpty()?"":header.getRecipient_address2());
		fs.setFstaff_addr3(header.getRecipient_address3()==null || header.getRecipient_address3().isEmpty()?"":header.getRecipient_address3());
		fs.setFstaff_addr4(header.getRecipient_address4()==null || header.getRecipient_address4().isEmpty()?"":header.getRecipient_address4());
		fs.setFstaff_addr_upd_cycle(0L);
		fs.setFstaff_alt_name(header.getRecipient_name());
		
		//fs.setFstaff_code(fb.getAlternateName());
		fs.setFstaff_destination(header.getRecipient_city());
		fs.setFstaff_disp_cycle(0L);
		fs.setFstaff_display_name(header.getRecipient_name());
		fs.setFstaff_dom_exp("0");
		fs.setFstaff_emp_num("0");
		fs.setFstaff_erp_code("0");
		fs.setFstaff_erp_loc_code("0");
		fs.setHq_id(staff.getHq_id()==null?0L:staff.getHq_id());
		fs.setFstaff_ins_dt(new Date());
		fs.setFstaff_ins_ip_add(header.getReq_ins_ip_add());
		fs.setFstaff_ins_usr_id(header.getReq_ins_user_id());
		fs.setFstaff_joining_date(new Date());
		fs.setFstaff_loc_id(staff.getFstaff_loc_id());
		fs.setLevel_code(staff.getLevel_code());
		fs.setFstaff_map_code1("0");
		fs.setFstaff_map_code2("0");
		fs.setFstaff_terr_id("0");
		fs.setFstaff_terr_code("");
		fs.setFstaff_terrname(staff.getFstaff_terrname()==null?"":staff.getFstaff_terrname());
		fs.setFstaff_mgr1_id(staff.getFstaff_mgr1_id());
		fs.setFstaff_mgr2_id(staff.getFstaff_mgr2_id());
		fs.setFstaff_mgr3_id(staff.getFstaff_mgr3_id());
		fs.setFstaff_mgr4_id(staff.getFstaff_mgr4_id());
		fs.setFstaff_mgr5_id(staff.getFstaff_mgr5_id());
		fs.setFstaff_mgr6_id(staff.getFstaff_mgr6_id());
		//fs.setFstaff_mgr6_id(fb.getMgr6_id()==null?0L:Long.valueOf(fb.getMgr6_id()));
		fs.setFstaff_mobile(header.getRecipient_phone());
		//fs.setFstaff_mod_dt();
		fs.setFstaff_mod_ip_add("");
		fs.setFstaff_mod_usr_id("");
		fs.setFstaff_name(header.getRecipient_name());
		fs.setFstaff_pan_no("0");
		fs.setFstaff_pmp_perf(BigDecimal.ZERO);
		fs.setFstaff_pool_ind("0");
		fs.setRegion_code(staff.getRegion_code());
		fs.setFstaff_samp_disp_ind("Y");
		fs.setFstaff_samp_sup_loc(0L);
		fs.setStatus("A");
		fs.setFstaff_state_id(staff.getFstaff_state_id());
		fs.setFstaff_tel_no("0");
		fs.setFstaff_transporter("0");
		fs.setFstaff_training("0");
		fs.setType_id(0L);
		fs.setFstaff_zip(header.getRecipient_pin());
		fs.setFstaff_zone_id(staff.getFstaff_zone_id());
		fs.setFstaff_zonename(staff.getFstaff_zonename());
		fs.setFstaff_type_ind_id(0L);
		fs.setFstaff_mcl_no(header.getMcl_number()==null || header.getMcl_number().isEmpty() ? "":header.getMcl_number());
		fs.setFstaff_requestor_fstaff_id(header.getRequestor_id()==null?0L:header.getRequestor_id());
		fs.setFstaff_prod_sub_type_allowed(staff.getFstaff_prod_sub_type_allowed());
		
		fs.setTeam_code(staff.getTeam_code());
		this.transactionalRepository.persist(fs);
		
		if (header.getCompany().trim().equalsIgnoreCase("MDL") || header.getCompany().trim().equalsIgnoreCase("SNK")) {
			Long requestor_loc_id = this.getCfa_id(staff.getFstaff_id(), 1L);
			Long loc_id = this.getCfa_id(fs.getFstaff_id(), 1L);
			System.out.println("loc_id " + loc_id);
			if (loc_id == null || loc_id == 0L) {
				this.FsCfaLinkage(fs.getFstaff_id(), requestor_loc_id, header, 1L);
			}
		} else {
			for (Long sub_comp_id : CFA_LINKAGE) {
				Long requestor_loc_id = this.getCfa_id(staff.getFstaff_id(), sub_comp_id);
				Long loc_id = this.getCfa_id(fs.getFstaff_id(), sub_comp_id);
				System.out.println("loc_id " + loc_id);
				if (loc_id == null || loc_id == 0L) {
					this.FsCfaLinkage(fs.getFstaff_id(), requestor_loc_id, header, sub_comp_id);
				}
			}
		}
		
		String fs_code=String.format("%010d",fs.getFstaff_id());
		System.out.println("fs code generated is :"+fs_code);
		fs.setFstaff_code(fs_code==null?"":fs_code);
		this.transactionalRepository.update(fs);

		return fs.getFstaff_id();
	}

	@Override
	public Long getCfa_id(Long fstaff_id,Long sub_comp_id) throws Exception{
		System.out.println("fstaff_id "+fstaff_id);
		System.out.println("sub_comp_id "+sub_comp_id);
		EntityManager em = null;
		int loc_id=0;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT  FSTAFFD_LOC_ID FROM FIELDSTAFF_DETAIL WHERE FSTAFFD_FSTAFF_ID=:staff_id and FSTAFFD_SubComp_id=:sub_comp_id");
			Query query = em.createNativeQuery(sb.toString());
			query.setParameter("staff_id", fstaff_id);
			query.setParameter("sub_comp_id", sub_comp_id);
			loc_id = (int) query.getSingleResult();
		}
		catch (NoResultException nre){
			return 0L;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return Long.valueOf(loc_id);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void FsCfaLinkage(Long fstaff_id,Long loc_id,AllocReqHeader header,Long sub_comp_id) throws Exception {
		try {
		Fieldstaff_Detail detail=null;
		detail=new Fieldstaff_Detail();
		detail.setFstaffd_fstaff_id(fstaff_id);	
		detail.setFstaffd_subcomp_id(sub_comp_id);
		detail.setFtaffd_loc_id(loc_id);
		detail.setFstaffd_status("A");
		detail.setFstaffd_ins_dt(header.getReq_ins_dt());
		detail.setFstaffd_ins_ip_add(header.getReq_ins_ip_add());
		detail.setFstaffd_ins_usr_id(header.getReq_ins_user_id());
		detail.setFstaffd_mod_ip_add(header.getReq_ins_ip_add());
		detail.setFstaffd_mod_usr_id(header.getReq_ins_user_id());
		this.transactionalRepository.persist(detail);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	@Override
	public List<FieldStaff> getFieldStaffByDivAndLevelcode(Long fstaff_id, String levelCode, String category)
			throws Exception {
		
		return this.fieldStaffRepository.getFieldStaffByDivAndLevelcode(fstaff_id,levelCode,category);
		
	}
	
	@Override
	public List<FieldStaffListNew> getFieldStaffListNew(String emp_id, Long div_id, String allocType) throws Exception {
		return fieldStaffRepository.getFieldStaffListNew(emp_id, div_id, allocType);
	}

	@Override
	public List<FieldStaff> getNotCfaLinkFieldstaff(Long divId) {
		// TODO Auto-generated method stub
		return fieldStaffRepository.getNotCfaLinkFieldstaff(divId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long setFiledStafForPgDoctor(FieldStaff staff,DoctorMaster docMaster) throws Exception {
		FieldStaff fs=new FieldStaff();
		fs.setFstaff_code("0");
		fs.setCompany(docMaster.getCompany());
		fs.setClass_id(0L);
		fs.setCity_id(staff.getCity_id()==null ?0L:staff.getCity_id());
		fs.setCf_idwyth(0L);
		fs.setCf_idpipl(0L);
		fs.setCf_idpfz(0L);
	    fs.setDesignation("DOCTOR");
		fs.setDist_code(staff.getDist_code()==null ?"":staff.getDist_code());
		fs.setEmail(docMaster.getDoc_email());
		fs.setFs_div_id(staff.getFs_div_id());
		fs.setFstaff_addr1(docMaster.getDoc_address1()==null || docMaster.getDoc_address1().isEmpty()?"":docMaster.getDoc_address1());
		fs.setFstaff_addr2(docMaster.getDoc_address2()==null || docMaster.getDoc_address2().isEmpty()?"":docMaster.getDoc_address2());
		fs.setFstaff_addr3(docMaster.getDoc_address3()==null || docMaster.getDoc_address3().isEmpty()?"":docMaster.getDoc_address3());
		fs.setFstaff_addr4(docMaster.getDoc_address4()==null || docMaster.getDoc_address4().isEmpty()?"":docMaster.getDoc_address4());
		fs.setFstaff_addr_upd_cycle(0L);
		fs.setFstaff_alt_name(docMaster.getDoc_name());
		//fs.setFstaff_code(fb.getAlternateName());
		fs.setFstaff_destination(docMaster.getDoc_city());
		fs.setFstaff_disp_cycle(0L);
		fs.setFstaff_display_name(docMaster.getDoc_name());
		fs.setFstaff_dom_exp("0");
		fs.setFstaff_emp_num("0");
		fs.setFstaff_erp_code("0");
		fs.setFstaff_erp_loc_code("0");
		fs.setHq_id(staff.getHq_id()==null?0L:staff.getHq_id());
		fs.setFstaff_ins_dt(new Date());
		fs.setFstaff_ins_ip_add("0");
		fs.setFstaff_ins_usr_id("0");
		fs.setFstaff_joining_date(new Date());
		fs.setFstaff_loc_id(staff.getFstaff_loc_id());
		fs.setLevel_code(staff.getLevel_code());
		fs.setFstaff_map_code1("0");
		fs.setFstaff_map_code2("0");
		fs.setFstaff_terr_id("0");
		fs.setFstaff_terr_code("");
		fs.setFstaff_terrname(staff.getFstaff_terrname()==null?"":staff.getFstaff_terrname());
		fs.setFstaff_mgr1_id(staff.getFstaff_mgr1_id());
		fs.setFstaff_mgr2_id(staff.getFstaff_mgr2_id());
		fs.setFstaff_mgr3_id(staff.getFstaff_mgr3_id());
		fs.setFstaff_mgr4_id(staff.getFstaff_mgr4_id());
		fs.setFstaff_mgr5_id(staff.getFstaff_mgr5_id());
		fs.setFstaff_mgr6_id(staff.getFstaff_mgr6_id());
		//fs.setFstaff_mgr6_id(fb.getMgr6_id()==null?0L:Long.valueOf(fb.getMgr6_id()));
		fs.setFstaff_mobile(docMaster.getDoc_phone());
		//fs.setFstaff_mod_dt();
		fs.setFstaff_mod_ip_add("");
		fs.setFstaff_mod_usr_id("");
		fs.setFstaff_name(docMaster.getDoc_name());
		fs.setFstaff_pan_no("0");
		fs.setFstaff_pmp_perf(BigDecimal.ZERO);
		fs.setFstaff_pool_ind("0");
		fs.setRegion_code(staff.getRegion_code());
		fs.setFstaff_samp_disp_ind("Y");
		fs.setFstaff_samp_sup_loc(0L);
		fs.setStatus("A");
		fs.setFstaff_state_id(staff.getFstaff_state_id());
		fs.setFstaff_tel_no("0");
		fs.setFstaff_transporter("0");
		fs.setFstaff_training("0");
		fs.setType_id(0L);
		fs.setFstaff_zip(docMaster.getDoc_pincode());
		fs.setFstaff_zone_id(staff.getFstaff_zone_id());
		fs.setFstaff_zonename(staff.getFstaff_zonename());
		fs.setFstaff_type_ind_id(0L);
		fs.setFstaff_mcl_no(docMaster.getMcl_no()==null ||docMaster.getMcl_no().isEmpty() ? "":docMaster.getMcl_no());
		fs.setFstaff_requestor_fstaff_id(staff.getFstaff_id());
		fs.setFstaff_prod_sub_type_allowed("");
		fs.setTeam_code(staff.getTeam_code());
		fs.setFs_category("D");
		fs.setCf_idpfz(staff.getCf_idpfz());
		this.transactionalRepository.persist(fs);
		
		for(Long sub_comp_id:CFA_LINKAGE) {
			Long requestor_loc_id=this.getCfa_id(staff.getFstaff_id(),sub_comp_id);
			Long loc_id=this.getCfa_id(fs.getFstaff_id(),sub_comp_id);
			System.out.println("loc_id "+loc_id);
			if(loc_id==null || loc_id==0L) {
				this.FsCfaLinkage(fs.getFstaff_id(),requestor_loc_id,new AllocReqHeader(),sub_comp_id);
			}
		}
		String fs_code=String.format("%010d",fs.getFstaff_id());
		System.out.println("fs code generated is :"+fs_code);
		fs.setFstaff_code(fs_code==null?"":fs_code);
		this.transactionalRepository.update(fs);

		return fs.getFstaff_id();
	}

	@Override
	public FieldStaff getFsIdByFsCode(String fsCode) throws Exception {
		// TODO Auto-generated method stub
		return fieldStaffRepository.getFsIdByFsCode(fsCode);
	}

	@Override
	public Long getFstaffDivIdForCfa_to_Stk_requestor(Long loc_id) throws Exception {
		return fieldStaffRepository.getFstaffDivIdForCfa_to_Stk_requestor(loc_id);
	}

	@Override
	public Map<String, Object> updateFstaffMobileNo(MultipartFile file, String username, String ipAddress)
			throws Exception {
		Map<String, Object> response = new HashMap<>();

		List<Fieldstaff_mobileno_update_log> fstaffMobileList = new ArrayList<>();
		List<Fieldstaff_mobileno_update_log> emptyFstaffMobileList = new ArrayList<>();
		List<Fieldstaff_mobileno_update_log> errorList = new ArrayList<>();
		List<Fieldstaff_After_mobile_No_Update_CheckList> list = new ArrayList<>();
		String errorFileName = "";

		String reportFileName = null;

		String fileName = file.getOriginalFilename();
		Long csvCount = 0L;

		try {
			if (this.fieldStaffRepository.isFileExist(file.getOriginalFilename())) {
				response.put("FILE_EXIST", "Y");
				return response;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			boolean isHeader = true;

			while ((line = reader.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}

				String[] columns = line.split(",");

				String[] paddedColumns = Utility.padCsvColumns(columns, 9);

				Fieldstaff_mobileno_update_log data = new Fieldstaff_mobileno_update_log();
				data.setFstaff_div_name(paddedColumns[0]);
//				data.setFstaff_level_code(paddedColumns[1]);
				data.setFstaff_desig(paddedColumns[1]);
				data.setFstaff_id(Long.parseLong(paddedColumns[2]));
				data.setFstaff_employee_no(paddedColumns[3]);
				data.setFstaff_terr_code(paddedColumns[4]);
				data.setFstaff_display_name(paddedColumns[5]);
				data.setFstaff_mobile(paddedColumns[6]);
				data.setCsv_ins_date(paddedColumns[7]);
				data.setCsv_mod_dt(paddedColumns[8]);
				data.setFstaff_ins_dt(new Date());
				data.setFstaff_mod_dt(new Date());
				data.setFstaff_ins_ip_add(ipAddress);
				data.setFstaff_mod_ip_add(ipAddress);
				data.setFstaff_ins_usr_id(username);
				data.setFstaff_mod_usr_id(username);

				data.setCsv_file_name(fileName);
				data.setFstaff_status("A");

				if (paddedColumns[6] == null || paddedColumns[6].isEmpty()) {
					emptyFstaffMobileList.add(data);
				} else {
					if (!paddedColumns[6].matches("[\\d+\\-\\s()/]+") ) {
						errorList.add(data);
					} else {
						fstaffMobileList.add(data);

					}

				}
				csvCount++;
			}

			if (fstaffMobileList.size() > 0) {
				fieldstaffrestcontroller.updateFstaffMobile(fstaffMobileList, fileName, username, ipAddress);
				list = this.reportService.getUpdatedFstaffMobileNo(fileName);

				if (list != null) {
					reportFileName = this.fieldMasterDownloadService.generateUpdatedFstaffMobileNoReport(list);
					System.out.println(":::::::filenameForReport:::::::::::" + reportFileName);
				}
				response.put("list", list);
				response.put("ReportFileName",reportFileName);
			}
			
			if(errorList.size() >0) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");

				CSVWriter writer = null;
				File ff = null;
				StringBuffer path = null;
				try {

					errorFileName = "ErrorCsv_file_" + sdf.format(new Date()) + ".csv";
					String filePath = MedicoConstants.REPORT_FILE_PATH;
					path = new StringBuffer(filePath).append("\\");
					path.append(errorFileName);
					ff = new File(path.toString());
					System.out.println("errorFileName " + errorFileName);
					File file1 = new File(filePath);

					try {
						if (!file1.exists()) {
							if (file1.mkdirs()) {
							} else {
								throw new RuntimeException("Could not create directory");
							}
						}
					} catch (Exception e) {
						throw new RuntimeException();
					}

					FileWriter fwriter = new FileWriter(path.toString());
					writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
					String[] header = new String[9];
					header[0] = "DIVISION";
					header[1] = "DESIGNATION";
					header[2] = "FSTAFF ID";
					header[3] = "EMPLOYEE NO";
					header[4] = "TERR CODE";
					header[5] = "DISPLAY NAME";
					header[6] = "MOBILE NO";
					header[7] = "INSERTED DATE";
					header[8] = "MODIFIED DATE";
				
					List<String[]> li = new ArrayList<String[]>();
					li.add(header);
					System.out.println("Number of fstaff" + errorList.size());
					for (Fieldstaff_mobileno_update_log fs : errorList) {
						String[] arr = new String[9];
						arr[0]= fs.getFstaff_div_name();
						arr[1] = fs.getFstaff_desig();
						arr[2] = fs.getFstaff_id().toString();
						arr[3] = fs.getFstaff_employee_no();
						arr[4] = fs.getFstaff_terr_code();
						arr[5] = fs.getFstaff_display_name();
						arr[6] = fs.getFstaff_mobile();		
						arr[7] = fs.getCsv_ins_date();
						arr[8] = fs.getCsv_mod_dt();

						li.add(arr);
					}
					writer.writeAll(li);

					File csvfile = new File(path.toString());
					FileInputStream fileInputStream = new FileInputStream(csvfile);
					writer.close();

				} catch (Exception e) {
					System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
					throw e;
				}
			}


			File dir = new File(new File(MedicoConstants.FSTAFF_CSV_PATH) + "//UploadedCSV");
			if (!dir.exists()) {
				dir.mkdir();
			}

			File savedFile = new File(dir, fileName);
			file.transferTo(savedFile);

			

			response.put("fstaffMobileList", fstaffMobileList.size());
			response.put("emptyFstaffMobileList", emptyFstaffMobileList.size());
			response.put("csvCount", csvCount);
			response.put("errorFileName", errorFileName);
			response.put("errorCount", errorList.size());

		} catch (Exception e) {
			throw e;

		}

		return response;
	}

}
