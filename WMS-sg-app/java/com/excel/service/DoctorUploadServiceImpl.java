package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationUploadBean;
import com.excel.bean.DoctorBean;
import com.excel.bean.DoctorUploadXlsxBean;
import com.excel.bean.RuleBasedAllocationBean;
import com.excel.exception.MedicoException;
import com.excel.model.DoctorMaster;
import com.excel.model.FieldStaff;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.Pg_Doc_Alloc_Template_Error;
import com.excel.model.SmpItem;
import com.excel.repository.DoctorRepository;
import com.excel.repository.DoctorUploadRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.ParameterRepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;
@Service
public class DoctorUploadServiceImpl implements DoctorUploadService,MedicoConstants{
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired DoctorRepository doctorRepository;
	@Autowired DoctorMasterService doctorMasterService;
	@Autowired ProductMasterRepository productMasterRepository;
	@Autowired FieldStaffService feldStaffService;
	@Autowired FieldStaffRepository fieldStaffRepository;
	@Autowired DoctorUploadRepository doctorUploadRepository;
	@Autowired ParameterRepository parameterrepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveuploadalDoctorAllocation(AllocationUploadBean bean) {
		Map<String, Object> map = new HashMap<>();
		String message=null;
		try {
			
			if (bean.getCsvuploaded().equals("Y")) {
				StringBuffer buffer = new StringBuffer("File not uploaded successfully because of the following errors :- <br><br>");
				boolean isFileWrong = false;
				File csvFile = new File(System.getProperty("java.io.tmpdir")+"/"+bean.getFileUpload().getOriginalFilename());
				//bean.getFileUpload().transferTo(csvFile);
				Long alloc_cycle=this.doctorUploadRepository.getAllocationCycleNumberPg(bean.getPeriod_id(), bean.getFinYear());
				String alloc_uniq_no=bean.getFinYear()+bean.getPeriod_id()+String.format("%05d", alloc_cycle);;
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";
				try {
					br = new BufferedReader(new FileReader(csvFile));
					line = br.readLine();
					System.out.println("line "+line);
					String[] headers = line.split(cvsSplitBy);
					
					outer: while ((line = br.readLine()) != null) {
						Long doc_id=null;
						Long doc_fs_id=null;
						String[] strings = line.split(cvsSplitBy);
						DoctorMaster doctor=this.doctorRepository.getDoctorsByMclNumber(Long.valueOf(strings[0]));
						if(doctor==null) {
							DoctorBean docm =new DoctorBean();
							docm.setEmpId("");
							docm.setIpaddress("");
							docm.setFieldstaffcategory("0");
							docm.setFstaff_emp_num("");  
							docm.setDocclass('0');
							docm.setStatus('A');
							docm.setDocspeciality(strings[2]);
							docm.setFullName(strings[1]);  
							docm.setFirstName("");
							docm.setLastName("");
							docm.setMiddleName("");
							docm.setMclNo(strings[0]);
							docm.setAddress1("");
							docm.setAddress2("");
							docm.setAddress3("");
							docm.setAddress4("");
							docm.setCity(strings[4]);
							docm.setPinCode(strings[5]);
							docm.setTelephone1(strings[6]);
							//docm.setDoc_phone2(dbbean.getTelephone2());
							docm.setEmail(strings[7].toString());
							docm.setPrefix("DR");
							docm.setDoc_full_add(strings[3]);
							doctorMasterService.savDocDetail(docm);
						}
						doc_id=doctor.getDoc_id();
						doc_fs_id=doctor.getDoc_fstaff_id();
						System.out.println("DOC_FS_ID  "+doc_fs_id);
						if(doc_fs_id==null) {
							System.out.println("IN IF DOC_FS_ID  "+doc_fs_id);
							FieldStaff staff=fieldStaffRepository.getObjectByStaffId(doctor.getFstaff_id());
							doc_fs_id=feldStaffService.setFiledStafForPgDoctor(staff, doctor);
							doctor.setDoc_fstaff_id(doc_fs_id);
							this.transactionalRepository.update(doctor);
						}
					
						for (int i = 9; i < headers.length; i++) {
							if(Integer.valueOf(strings[i])!=0){
								Pg_Doc_Alloc_Template temp = new Pg_Doc_Alloc_Template();
								temp.setFin_year(bean.getFinYear());
								temp.setPeriod_code(bean.getPeriod_id());
								temp.setAlloc_uniq_no(alloc_uniq_no);
								temp.setCustid((strings[0]));
								temp.setHcpname(strings[1]);
								temp.setSpeciality(strings[2]);
								temp.setAddress(strings[3]);
								temp.setCity(strings[4]);
								temp.setPincode(strings[5]);
								temp.setMobile(strings[6]);
								temp.setEmailid(strings[7]);
								temp.setCluster(strings[8]);
								temp.setQty(strings[i]);
								System.out.println("headers[i]"+headers[i]);
								SmpItem item = this.productMasterRepository.getProductMasterObjByCode(headers[i].substring(headers[i].indexOf("|") + 1, headers[i].lastIndexOf("|")));
			                    temp.setProd_code(item.getSmp_prod_cd());
								temp.setProd_id(item.getSmp_prod_id());
								temp.setDoc_id(doc_id);
								temp.setDoc_fs_id(doc_fs_id);
								temp.setAlloc_cycle(alloc_cycle);
								this.transactionalRepository.persist(temp);
							}
							
						}

					}
					if (isFileWrong) {
						message = buffer.toString();
					} else {
						
						File dir = new File(new File(MedicoConstants.UPLOADED_CSV)+ "//Uploaded CSV");
						if (!dir.exists()) {
							dir.mkdir();
						}
						File fileToCreate = new File(dir, bean.getFileUploadFileName());
						FileUtils.copyFile(csvFile, fileToCreate);
						map.put(DATA_SAVED, true);
						map.put(RESPONSE_MESSAGE,"File Uploaded Successfully-1");
						map.put("UNIQUE_NUMBER",alloc_uniq_no);
					}

				} catch (Exception e) {
					map.put(DATA_SAVED, true);
					map.put(RESPONSE_MESSAGE,"File not uploaded successfully");
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				} finally {
					if (br != null)
						br.close();

				}

			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createAllocation(AllocationUploadBean bean,String alloc_uniq_no) throws Exception {
		this.doctorUploadRepository.generateAllocationPg(bean.getComp_code(), bean.getFinYear(),bean.getPeriod_id(), alloc_uniq_no,bean.getUserid(),bean.getIp_address());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveuploadalDoctorAllocationXlsx(AllocationUploadBean bean) throws Exception{
		Map<String, Object> map = new HashMap<>();
		DataFormatter df = new DataFormatter();
		String message=null;
		
		String mclNo = "";
		String HCPName = "";
		String Address = "";
		try {
			System.out.println("PERIOD_ID "+bean.getPeriod_id());
			
			if (bean.getCsvuploaded().equals("Y")) {
				String division = bean.getDivision_id();
				StringBuffer buffer = new StringBuffer("File not uploaded successfully because of the following errors :- <br><br>");
				boolean isFileWrong = false;
				DoctorMaster doctor=null;
				File csvFile = new File(System.getProperty("java.io.tmpdir")+"/"+bean.getFileUpload().getOriginalFilename());
				bean.getFileUpload().transferTo(csvFile);
				Long alloc_cycle=this.doctorUploadRepository.getAllocationCycleNumberPg(bean.getPeriod_id(), bean.getFinYear());
				String alloc_uniq_no=bean.getFinYear()+bean.getPeriod_id()+String.format("%05d", alloc_cycle);
				Workbook work=WorkbookFactory.create(csvFile);
				XSSFSheet sheet=(XSSFSheet) work.getSheetAt(0);
				int k=1;
				XSSFRow headerRow=sheet.getRow(k);
				XSSFRow row=null;
				Long stateId =0l;
				try {
					k++;
					
					outer: while (sheet.getRow(k) != null) {
						
						row=sheet.getRow(k);
						Long doc_id=null;
						Long doc_fs_id=null;
						//setting xls bean
						DoctorUploadXlsxBean xlsxBean=new DoctorUploadXlsxBean();
						xlsxBean.setAlloc_uniq_no(alloc_uniq_no);
						xlsxBean.setCustId(df.formatCellValue(row.getCell(0)));
						mclNo = xlsxBean.getCustId();
						xlsxBean.setCustId1(xlsxBean.getCustId());
						xlsxBean.setHcpname(row.getCell(2).toString());
						HCPName = xlsxBean.getHcpname();
						xlsxBean.setHcpnames(xlsxBean.getHcpname());
						xlsxBean.setSpeciality(row.getCell(3).toString());
						xlsxBean.setAddress(row.getCell(4).toString().trim().length()>300?
								row.getCell(4).toString().trim().substring(1,299):row.getCell(4).toString().trim());
						Address=xlsxBean.getAddress().length()>25?xlsxBean.getAddress().substring(0,25):xlsxBean.getAddress();
						xlsxBean.setCity(row.getCell(5).toString());
						
						xlsxBean.setCity(row.getCell(5).toString().length()<45?row.getCell(5).toString():row.getCell(5).toString().substring(0, 45));
						xlsxBean.setPincode(df.formatCellValue(row.getCell(6)));
						xlsxBean.setMobile(df.formatCellValue(row.getCell(7)));
						xlsxBean.setEmailid(row.getCell(8)!=null?row.getCell(8).toString():null);
						xlsxBean.setCluster(row.getCell(9).toString());
						if(row.getCell(10)== null ||row.getCell(10).toString()== "") {
							xlsxBean.setStatename("GOA");
						}
						else{
						xlsxBean.setStatename(row.getCell(10).toString().toUpperCase().trim());
						}
						List<String> states = parameterrepository.getstates();
						System.out.println("xlsxBean.getStatename()"+states.toString());
						
						if(!states.contains(xlsxBean.getStatename())) {
							String errMsg="Invalid State";
							this.writeErrorLog(xlsxBean, errMsg);
						throw new MedicoException("Invalid State");
							}
						
						stateId= parameterrepository.getStateByStateName(xlsxBean.getStatename().toUpperCase().trim()).getSg_prmdet_id();
						
						
						//checking doctor x exist or not
						doctor=this.doctorRepository.getDoctorsByMclNumber(Long.valueOf(xlsxBean.getCustId()));
						//checking address length
					
						
						
						
						if(xlsxBean.getAddress()==null || xlsxBean.getAddress().trim().equals("")) {
							String errMsg="Please check address length.Its is empty";
							this.writeErrorLog(xlsxBean, errMsg);
						//	continue;
							throw new MedicoException("Please check address length.Its is empty");
						}
						
						if(xlsxBean.getAddress().length()>220) {
							String errMsg="Please check address length.Its greater than 220";
							this.writeErrorLog(xlsxBean, errMsg);
						//	continue;
							throw new MedicoException("Please check address length.Its greater than 220");
						}
						
						
						//write doctor if not present
						if(doctor==null) {
							DoctorBean docm =new DoctorBean();
							docm.setCompanyCode(bean.getComp_code());
							docm.setEmpId("0");
							docm.setIpaddress("");
							docm.setFieldstaffcategory("1");
							docm.setFstaff_emp_num("");
							docm.setDocclass('0');
							docm.setStatus('A');
							docm.setDocspeciality(xlsxBean.getSpeciality());
							docm.setFullName(xlsxBean.getHcpname());  
							docm.setFirstName(xlsxBean.getHcpname());
							docm.setLastName("");
							docm.setMiddleName("");
							docm.setMclNo(xlsxBean.getCustId());
							docm.setAddress1("");
							docm.setAddress2("");
							docm.setAddress3("");
							docm.setAddress4("");
							docm.setCity(xlsxBean.getCity());
							docm.setPinCode(xlsxBean.getPincode());
							docm.setTelephone1(xlsxBean.getMobile());
							//docm.setDoc_phone2(dbbean.getTelephone2());
							docm.setEmail(xlsxBean.getEmailid()!=null?xlsxBean.getEmailid():null);
							docm.setPrefix("DR");
							docm.setDoc_full_add(xlsxBean.getAddress());
							docm.setStatename(xlsxBean.getStatename());
							docm.setStateId(stateId);
							
							if(division.equals("1")) {
								if(stateId.compareTo(183l)!=0 || !xlsxBean.getStatename().trim().equals("MAHARASHTRA")) {
									docm.setFstaff_id("4");
								}
								else {
									docm.setFstaff_id("188");
								}
								}
								//else if(bean.getDivision_id()=="2") {
								else if(division.equals("2")) {
									System.out.println("inside division 2"+xlsxBean.getCustId());
									if(stateId.compareTo(183l)!=0 || !xlsxBean.getStatename().trim().equals("MAHARASHTRA")) {
										docm.setFstaff_id("564");
									}
									else {
										docm.setFstaff_id("805");
								}	
								}
								else {
									System.out.println("other then 1 & 2 ");
									
								}
							
							
							doctor=doctorMasterService.savDocDetail(docm);
							
							//update address
							this.updateAddress(doctor);
						}
						
						doc_id=doctor.getDoc_id();
						doc_fs_id=doctor.getDoc_fstaff_id();
						if(doc_fs_id==null) {
							System.out.println("	System.out.println(doctor.getDoc_full_adr());"+xlsxBean.getCity());
							FieldStaff staff=fieldStaffRepository.getFstaffbyFsMclNoAndDivId(doctor.getMcl_no(),
									Long.valueOf(bean.getDivision_id()));
						//	staff.setFstaff_state_id(stateId);
					//		this.transactionalRepository.update(staff);
							if(staff==null) {
								FieldStaff fstaff=fieldStaffRepository.getObjectByStaffId(doctor.getFstaff_id());
								fstaff.setFs_div_id(Long.valueOf(division));
								doc_fs_id=feldStaffService.setFiledStafForPgDoctor(fstaff, doctor);
								System.out.println("DocFsId : "+doc_fs_id);
								doctor.setDoc_fstaff_id(doc_fs_id);
							}
							
							
							doctor.setState_name(xlsxBean.getStatename());
							doctor.setState_id(stateId);
							doctor.setDoc_full_adr(xlsxBean.getAddress());
							doctor.setDoc_address4(xlsxBean.getCity()+"-"+xlsxBean.getPincode());
							doctor.setDoc_city(xlsxBean.getCity());
							doctor.setDoc_pincode(xlsxBean.getPincode());
						
							this.updateAddress(doctor);
							this.transactionalRepository.update(doctor);
							
							
						}
						else {
							System.out.println("*****	System.out.println(doctor.getDoc_full_adr());"+xlsxBean.getCity());
							//FieldStaff staff=fieldStaffRepository.getObjectByStaffId(doctor.getDoc_fstaff_id())
							FieldStaff staff=fieldStaffRepository.getFstaffbyFsMclNoAndDivId(doctor.getMcl_no(),
									Long.valueOf(bean.getDivision_id()));

							if(staff!=null) {
								System.out.println(staff.getFstaff_id());
								System.out.println("-----------------------------------------");
								System.out.println("req"+staff.getFstaff_requestor_fstaff_id());
							
								staff.setFstaff_state_id(stateId);
								staff.setFstaff_name(doctor.getDoc_fname());
								staff.setFstaff_display_name(doctor.getDoc_fname());
								staff.setFstaff_alt_name(doctor.getDoc_fname());
								staff.setFstaff_addr1(doctor.getDoc_address1());
								staff.setFstaff_addr2(doctor.getDoc_address2());
								staff.setFstaff_addr3(doctor.getDoc_address3());
							
								//staff.setFstaff_state_id(doctor.getState_id());
								staff.setFstaff_addr4(doctor.getDoc_city()+"-"+doctor.getDoc_pincode());
								staff.setFstaff_destination(doctor.getDoc_pincode());
								staff.setFstaff_mobile(xlsxBean.getMobile());
								this.transactionalRepository.update(staff);
								
							
							}
							else {
								FieldStaff fstaff=fieldStaffRepository.getObjectByStaffId(doctor.getFstaff_id());
								fstaff.setFs_div_id(Long.valueOf(division));
								doc_fs_id=feldStaffService.setFiledStafForPgDoctor(fstaff, doctor);
								System.out.println("DocFsId : "+doc_fs_id);
								doctor.setDoc_fstaff_id(doc_fs_id);
							}
							
							
							doctor.setState_name(xlsxBean.getStatename());
							doctor.setState_id(stateId);
							doctor.setDoc_full_adr(xlsxBean.getAddress());
							doctor.setDoc_address4(xlsxBean.getCity()+"-"+xlsxBean.getPincode());
							doctor.setDoc_city(xlsxBean.getCity());
							doctor.setDoc_pincode(xlsxBean.getPincode());
						
							this.updateAddress(doctor);
							this.transactionalRepository.update(doctor);
							
							// fieldstaff Master Update of existing doctor
							
							// select * from fieldstaff where fstaff_id = doc_fs_id 
							//update fstaff_addr1,fstaffAddr2,Fstaff_Adr3,Fstaff_addr4,FSTaff_destination,FSTAFF_MOBILE,FSTAFF_EMAIL,FSTAFF_STATE_ID,
							
							
						}
						//Qty starts from index 10
						int i=11;
						int count =0;  
						while (row.getCell(i) != null) {
							count++;
//							System.out.println("row.getCell(i)"+row.getCell(i));
//							System.out.println("df.formatCellValue(row.getCell(i)"+df.formatCellValue(row.getCell(i)));
//							System.out.println("row.getCell(i)!=null && Integer.valueOf(df.formatCellValue(row.getCell(i))"+Integer.valueOf(df.formatCellValue(row.getCell(i))));
//							
						  
							 String  qty ;
						      if((row.getCell(i))!=null && !(row.getCell(i)).toString().trim().equals("")) {
						    	     qty = df.formatCellValue(row.getCell(i)); 
						      }
						      else {
						    	  qty="0";
						      }
							 
							if(row.getCell(i)!=null && Long.valueOf(qty).compareTo(0l)>0){
								
								Pg_Doc_Alloc_Template temp = new Pg_Doc_Alloc_Template();
							
								temp.setPri_no( new BigDecimal(row.getCell(1).toString().trim()).setScale(1).longValue());
								temp.setFin_year(bean.getFinYear());
								temp.setPeriod_code(bean.getPeriod_id());
								temp.setAlloc_uniq_no(alloc_uniq_no);
								temp.setCustid((row.getCell(0).toString()));
								temp.setHcpname(row.getCell(2).toString().trim());
								temp.setSpeciality(row.getCell(3).toString().trim());
								temp.setAddress(row.getCell(4).toString().trim());
								temp.setCity(row.getCell(5).toString().length()<45?row.getCell(5).toString():row.getCell(5).toString().substring(0, 45));
								temp.setPincode(df.formatCellValue(row.getCell(6)).toString().trim());
								temp.setMobile(df.formatCellValue(row.getCell(7)).toString().trim());
								temp.setEmailid(row.getCell(8)!=null?row.getCell(8).toString().trim():null);
								temp.setCluster(row.getCell(9).toString().trim());
								temp.setAlloc_type("D");
								if(row.getCell(10)== null || row.getCell(10).toString().trim().equals("") ) {
									temp.setState_name("GOA");
								}
								else {
								temp.setState_name(row.getCell(10).toString().trim());
								}
								temp.setState_id(stateId);
								
								System.out.println("row.getCell(i)"+row.getCell(i));
								if(row.getCell(i)== null || row.getCell(i).toString().trim().equals("") ) 
							{
									temp.setQty("0");
								}
								else {
								temp.setQty(row.getCell(i).toString());
								}
								SmpItem item = this.productMasterRepository.getProductMasterObjByCode(df.formatCellValue(headerRow.getCell(i)));
								if (Long.valueOf(bean.getDivision_id()) != item.getSmp_std_div_id()) {
									map.put(DATA_SAVED, false);
									map.put(RESPONSE_MESSAGE,"Selected division is not correct : "+bean.getDivision_id());
									//return map;
								}
			                    temp.setProd_code(item.getSmp_prod_cd());
								temp.setProd_id(item.getSmp_prod_id());
								temp.setDoc_id(doc_id);
								temp.setDoc_fs_id(doc_fs_id);
								temp.setAlloc_cycle(alloc_cycle);
								temp.setAlloc_date(new Date());
								System.out.println("persist@@"+temp.getHcpname().toString());
								
								this.transactionalRepository.persist(temp);
								
							}
							i++;
						}
						k++;
						if(count>100) {
							this.transactionalRepository.flush();
							System.out.println("flush");
							count =0;
						}
					}
					if (isFileWrong) {
						message = buffer.toString();
					} else {
						
						File dir = new File(new File(MedicoConstants.UPLOADED_CSV)+ "//Uploaded CSV");
						if (!dir.exists()) {
							dir.mkdir();
						}
						File fileToCreate = new File(dir, bean.getFileUploadFileName());
						FileUtils.copyFile(csvFile, fileToCreate);
						map.put(DATA_SAVED, true);
						map.put(RESPONSE_MESSAGE,"File Uploaded Successfully");
						map.put("UNIQUE_NUMBER",alloc_uniq_no);
					}

				} catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
					System.out.println("inner Catch");
					map.put(DATA_SAVED, true);
					map.put(RESPONSE_MESSAGE,"File Uploaded Successfully-2");
					System.out.println("file sucesd@@"+alloc_uniq_no);
					map.put("UNIQUE_NUMBER",alloc_uniq_no);
				//	throw e;
					
					
				} finally {
					

				}

			}
			doctorUploadRepository.updateDocFsIdsforDoctorUpload(Long.valueOf(bean.getDivision_id()), map.get("UNIQUE_NUMBER").toString());
		} catch (Exception e) {
			System.out.println("Outer Catch");
			map.put(DATA_SAVED, false);
			
			map.put(RESPONSE_MESSAGE," MCL :"+mclNo
					+" HCP NAME : "+HCPName+" Address : "+Address);
			System.out.println("RESPOP : "+map.get(RESPONSE_MESSAGE).toString());
			//throw e;
		}
		return map;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void writeErrorLog(DoctorUploadXlsxBean xlsxBean,String msg) throws Exception {
		Pg_Doc_Alloc_Template_Error temp = new Pg_Doc_Alloc_Template_Error();
		temp.setAlloc_uniq_no(xlsxBean.getAlloc_uniq_no());
		temp.setCustid(xlsxBean.getCustId());
		temp.setHcpname(xlsxBean.getHcpname());
		temp.setSpeciality(xlsxBean.getSpeciality());
		temp.setAddress(xlsxBean.getAddress());
		temp.setCity(xlsxBean.getCity());
		temp.setPincode(xlsxBean.getPincode());
		temp.setMobile(xlsxBean.getMobile());
		temp.setEmailid(xlsxBean.getEmailid());
		temp.setCluster(xlsxBean.getCluster());
		temp.setQty("0");
		temp.setProd_code("0");
		temp.setProd_id(0l);
		temp.setDoc_id(null);
		temp.setDoc_fs_id(null);
		temp.setAlloc_date(new Date());
		temp.setError_msg(msg);
		this.transactionalRepository.persist(temp);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteDoctorUploadByUniqueNumber(Long alloc_uniq_no) throws Exception {
		this.doctorUploadRepository.deleteDoctorUploadByUniqueNumber(alloc_uniq_no);
	}
	
	@Override
	public List<RuleBasedAllocationBean> approvalListForSelfAppr(String empId, String user_role, String locId) {
		// TODO Auto-generated method stub
		return doctorUploadRepository .approvalLisForSelfAppr(empId, user_role, locId);
	}
	@Override
	public Map<String, Object> getProductWiseStock(long division, long period, String up_status, String ucycle,Long alloctempHdId)
			throws Exception {
		// TODO Auto-generated method stub
		return doctorUploadRepository.getProductWiseStock(division, period, up_status, ucycle,alloctempHdId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateApprovalStatus(Long alloc_uniq_no,String status) throws Exception {
	
		this.doctorUploadRepository.updateApprovalStatus(alloc_uniq_no, status);
		//this.doctorUploadRepository.updateAllocDetailApprovalStatus(alloc_uniq_no, status);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAddress(DoctorMaster doctor) throws Exception {
		String address1="";		
		String address2="";
		String address3="";
		String address4="";
		String temp1="";
		String temp2="";
		String temp3="";
		String temp4="";
		boolean flag1=true;
		boolean flag2=false;
		boolean flag3=false;
		boolean flag4=false;
		String str;
		List<String> list;
		if(doctor.getDoc_full_adr()!=null) {
			str=doctor.getDoc_full_adr().trim();
			list= Arrays.asList(str.split(" "));
			System.out.println("doctor.getDoc_full_adr() :::::: "+doctor.getDoc_full_adr());
		int k=0;
		for(String s:list) {
			if(flag1==true) {
				temp1=temp1+s;
			}
			if(flag2==true) {
				temp2=temp2+s;
			}
			if(flag3==true) {
				temp3=temp3+s;
			}
			if(flag4==true) {
				temp4=temp4+s;
			}
		    if(temp1.length()<55){
				address1=address1+s+" ";
				temp1=address1;
				flag1=true;
				flag2=false;
				flag3=false;
				flag4=false;
			}else if(temp2.length()<55) {
				address2=address2+s+" ";
				temp2=address2;
				flag1=false;
				flag2=true;
				flag3=false;
				flag4=false;
			}else if(temp3.length()<55) {
				address3=address3+s+" ";
				temp3=address3;
				flag1=false;
				flag2=false;
				flag3=true;
				flag4=false;
			}else{
					/*
					 * address4=address4+s+" "; temp4=address4; flag1=false; flag2=false;
					 * flag3=false; flag4=true;
					 */
			}
			k++;
			
		}
		doctor.setDoc_address1(address1);
		doctor.setDoc_address2(address2);
		doctor.setDoc_address3(address3);
	    doctor.setDoc_address4(address4);
		
		this.transactionalRepository.update(doctor);
		}
	}
	
	 
	//--------------------------------------------------------------------------------------------------------------------
		@Override
		public List<Pg_Doc_Alloc_Template> getListofPgDocAllocTemp(String allocuniquenum) throws Exception {
			// TODO Auto-generated method stub
			return doctorUploadRepository.getListofPgDocAllocTemp(allocuniquenum);
		}

		@Override
		public List<Pg_Doc_Alloc_Template_Error> getListofPgDocAllocTempErrorList(String allocuniquenum) throws Exception {
			// TODO Auto-generated method stub
			return doctorUploadRepository.getListofPgDocAllocTempErrorList(allocuniquenum);
		}

		@Override
		public String generateDoctorUploadReport(List<Pg_Doc_Alloc_Template> list,
				List<Pg_Doc_Alloc_Template_Error> ErrorList, String companyname) throws Exception {
			System.out.println("Inside excel doc upload ");
			String filename = "DoctorUploadReport" + new Date().getTime() + ".xlsx";
			String filepath = REPORT_FILE_PATH + filename;
			XSSFWorkbook workbook = null;
			System.out.println("Inside excel doc upload 2 ");
			try {
				System.out.println("In Try");
				workbook = new XSSFWorkbook();
				File f = new File(REPORT_FILE_PATH);
				if (!f.exists()) {
					if (f.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
				XSSFSheet sheet = workbook.createSheet("Doctor Upload Report");
				
				XSSFRow row = null;
				XSSFCell cell = null;
				int rowCount = 0;
				int colCount = 0;
				
				String headings[] = { "Sr. No.", "Aloc Unique No","Cust Id" , "Hcp Name","Speciality","Address","City",
						"Pincode","Mobile","Email Id","Cluster","Product Code","Product Id","Quantity","Doc Id","Doc Fs Id",
						"Processed","Processed Date","Alloc Id","Alloc Cycle","Period Code","Financial Year"};
				
				XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
				XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
				XSSFCellStyle cellAlignment = ReportFormats.setCellAlignment(workbook, ALIGN_RIGHT);
				
				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
				cell.setCellValue(companyname);
				cell.setCellStyle(headingCellStyle);
				rowCount++;
				
				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
				cell.setCellValue("Doctor Upload Report");
				cell.setCellStyle(headingCellStyle);
//				rowCount++;

//				row = sheet.createRow(rowCount);
//				cell = row.createCell(colCount);
//				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//				cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//				cell.setCellStyle(headingCellStyle);
				
				rowCount += 1;
				row = sheet.createRow(rowCount);
				
				for (String heading : headings) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					colCount++;
				}
				
				rowCount += 1;
				colCount=0;
				
//				row = sheet.createRow(rowCount);
//				cell = row.createCell(colCount);
//				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + 14));
//				cell.setCellValue("Master Challan details");
//				cell.setCellStyle(headingCellStyle);
//				
//				cell = row.createCell(colCount+15);
//				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount+15, colCount + 34));
//				cell.setCellValue("Individual Challan details");
//				cell.setCellStyle(headingCellStyle);
//				
//				sheet.groupRow(0, 2);
//				sheet.createFreezePane(7, 5);
//				
				rowCount += 1;
				int count=1;
				for (Pg_Doc_Alloc_Template data : list) {
					colCount = 0;
					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, String.valueOf(count), cellAlignment);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getAlloc_uniq_no(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getCustid().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getHcpname(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getSpeciality(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getAddress(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getCity(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getPincode(), null);
					colCount++;
					
//					cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(), cellAlignment);
//					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getMobile()==null ?"":data.getMobile(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getCluster(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getProd_code(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, data.getProd_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getQty(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getDoc_id()==null?"":data.getDoc_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getDoc_fs_id()==null?"":data.getDoc_fs_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getProcessed()==null?"":data.getProcessed(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getProcessed_date()==null?"":data.getProcessed_date(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getAlloc_id()==null?"":data.getAlloc_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getAlloc_cycle()==null?"":data.getAlloc_cycle().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getPeriod_code()==null?"":data.getPeriod_code(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, data.getFin_year()==null?"":data.getFin_year(), null);
					colCount++;
					
					rowCount++;
					count++;
				}
				
				//---------error excel
				sheet = workbook.createSheet("Doctor Upload Error Report");
				rowCount=0;
				colCount=0;
				
				String headings1[] = { "Sr. No.", "Aloc Unique No","Cust Id" , "Hcp Name","Speciality","Address","City",
						"Pincode","Mobile","Email Id","Cluster","Product Code","Product Id","Quantity","Doc Id","Doc Fs Id",
						"PG Slno","Error Msg","Error Date"};
				
				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
				cell.setCellValue(companyname);
				cell.setCellStyle(headingCellStyle);
				rowCount++;
				
				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
				cell.setCellValue("Doctor Upload Error Report");
				cell.setCellStyle(headingCellStyle);
			//	rowCount++;

//				row = sheet.createRow(rowCount);
//				cell = row.createCell(colCount);
//				sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount + headings.length - 1));
//				cell.setCellValue("Period: "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getStartDate())+" to "+MedicoResources.convertUtilDateToString_DD_MM_YYYY_(bean.getEndDate()));
//				cell.setCellStyle(headingCellStyle);
				
				rowCount ++;
				colCount=0;
				row = sheet.createRow(rowCount);
				
				for (String heading : headings1) {
					cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
					colCount++;
				}
				
				rowCount ++;
				colCount=0;
				
				//rowCount += 1;
				int countError=1;
				for (Pg_Doc_Alloc_Template_Error ErrorData : ErrorList) {
					colCount = 0;
					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, String.valueOf(countError), cellAlignment);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getAlloc_uniq_no(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getCustid(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getHcpname(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getSpeciality(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getAddress(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getCity(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getPincode(), null);
					colCount++;
					
//					cell = ReportFormats.cellNum(row, colCount, data.getSumdsp_total_goods_val() == null ? 0l : data.getSumdsp_total_goods_val().doubleValue(), cellAlignment);
//					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getMobile(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getCluster(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getProd_code(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, ErrorData.getProd_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getQty(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getDoc_id()==null?"":ErrorData.getDoc_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getDoc_fs_id()==null?"":ErrorData.getDoc_fs_id().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getPg_slno()==null?"":ErrorData.getPg_slno().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getError_msg()==null?"":ErrorData.getError_msg().toString(), null);
					colCount++;
					
					cell = ReportFormats.cell(row, colCount, ErrorData.getError_date()==null?"":ErrorData.getError_date().toString(), null);
					colCount++;
					
					rowCount++;
					count++;
				}
				FileOutputStream fileOutputStream = new FileOutputStream(filepath);
				workbook.write(fileOutputStream);
			}
			catch (Exception e) {
				System.out.println("In Catch");
				// TODO: handle exception
				throw e;
			}
			finally {
				System.out.println("In Finnaly");
				if (workbook != null) { workbook.close(); }
			}
			return filepath;
		}
		
		@Override
		public List<RuleBasedAllocationBean> approvalDetails(Long period,Long unique_number) throws Exception {
			
			return this.doctorUploadRepository.approvalDetails(period, unique_number);
		}
		
		@Override
		public Map<String,Object> validateDoctorallocation(AllocationUploadBean bean) throws SQLException,Exception{
			System.out.println("inside validateDoctorallocation ");
		
			Map<String,Object> vmap=new HashMap<String, Object>();
			DataFormatter df = new DataFormatter();
			boolean flag=false;
			String message;
			String custId ="";
			String hcpName = "";
			String addr ="";
			try {
				if (bean.getCsvuploaded().equals("Y")) { 
					StringBuffer buffer = new StringBuffer("File not uploaded successfully because of the following errors :- <br><br>");
				
					File csvFile = new File(System.getProperty("java.io.tmpdir")+"/"+bean.getFileUpload().getOriginalFilename());
					bean.getFileUpload().transferTo(csvFile);
					Long alloc_cycle=this.doctorUploadRepository.getAllocationCycleNumberPg(bean.getPeriod_id(), bean.getFinYear());
					String alloc_uniq_no=bean.getFinYear()+bean.getPeriod_id()+String.format("%05d", alloc_cycle);
					Workbook work=WorkbookFactory.create(csvFile);
					XSSFSheet sheet=(XSSFSheet) work.getSheetAt(0);
					int k=1;
					XSSFRow headerRow=sheet.getRow(k);
					XSSFRow row=null;
					List<String> states = parameterrepository.getstates();
					
					
					k++;
					vmap.put("Errflag",false);
					outer: while (sheet.getRow(k) != null) {
						row=sheet.getRow(k);
						//setting xls bean
						DoctorUploadXlsxBean xlsxBean=new DoctorUploadXlsxBean();
						xlsxBean.setAlloc_uniq_no(alloc_uniq_no);
						xlsxBean.setCustId(df.formatCellValue(row.getCell(0)));
						custId = xlsxBean.getCustId();
						xlsxBean.setHcpname(row.getCell(2).toString());
						hcpName = xlsxBean.getHcpname();
						xlsxBean.setSpeciality(row.getCell(3).toString());
						xlsxBean.setAddress(row.getCell(4).toString().trim().length()>299?
								row.getCell(4).toString().trim().substring(0,299):row.getCell(4).toString().trim());
						addr =xlsxBean.getAddress().length()>25? xlsxBean.getAddress().substring(0,25):xlsxBean.getAddress();
						xlsxBean.setCity(row.getCell(5).toString());
						xlsxBean.setPincode(df.formatCellValue(row.getCell(6)));
						xlsxBean.setMobile(df.formatCellValue(row.getCell(7)));
						xlsxBean.setEmailid(row.getCell(8)!=null?row.getCell(8).toString():null);
						xlsxBean.setCluster(row.getCell(9).toString());
						if(row.getCell(10)== null || row.getCell(10).toString()=="") {
							xlsxBean.setStatename("GOA");
						}
						else{
						xlsxBean.setStatename(row.getCell(10).toString().toUpperCase());
						}
				
					
						if(!states.contains(xlsxBean.getStatename())) {
							flag=true;
							String errMsg="Invalid State";
							this.writeErrorLog(xlsxBean, errMsg);
							vmap.put(ERRORMSG,errMsg);
							vmap.put("Errflag",true);
							}	
							
						if(xlsxBean.getAddress()==null || xlsxBean.getAddress().trim().equals("")) {
							flag=true;
							String errMsg="Please check address length.Its is empty";
							this.writeErrorLog(xlsxBean, errMsg);
							vmap.put(ERRORMSG,errMsg);
							vmap.put("Errflag",true);
						}
						
						if(xlsxBean.getAddress().length()>220) {
							flag=true;
							String errMsg="Please check address length.Its greater than 220";
							this.writeErrorLog(xlsxBean, errMsg);
							vmap.put(ERRORMSG,errMsg);
							vmap.put("Errflag",true);
						}
	
						k++;
					}	
				
//					if(flag) {
//						System.out.println("@insideFlag");
//						message = buffer.toString();
//					}
				}		
			}
			catch(SQLException e) {
				vmap.put("Errflag",true);
				vmap.put("MCLNO",custId);
				vmap.put("HCPNAME",hcpName);
				vmap.put("ADDR",addr);
				throw e;
			}
			catch(Exception e) {
				vmap.put("Errflag",true);
				vmap.put("MCLNO",custId);
				vmap.put("HCPNAME",hcpName);
				vmap.put("ADDR",addr);
				throw e;
			}
			
			return vmap;
		}

		@Override
		public void updateDocFsIdsforDoctorUpload(Long divId, String uniqueno) throws Exception {
			// TODO Auto-generated method stub
			doctorUploadRepository.updateDocFsIdsforDoctorUpload(divId, uniqueno);
		}
}
