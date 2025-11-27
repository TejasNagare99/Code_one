package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationUploadBean;
import com.excel.bean.FieldStaffAllocationUploadBean;
import com.excel.bean.FieldstaffAllocationUploadXlsxBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocReqHeader;
import com.excel.model.DoctorMaster;
import com.excel.model.FieldStaff;
import com.excel.model.Pg_Doc_Alloc_Template;
import com.excel.model.SmpItem;
import com.excel.repository.DoctorUploadRepository;
import com.excel.repository.FieldstaffAllocationUploadRepository;
import com.excel.repository.ParameterRepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Service
public class FieldStaffAllocationUploadServiceImpl implements FieldStaffAllocationUploadService,MedicoConstants{
	@Autowired DoctorUploadRepository doctorUploadRepository;
	@Autowired ParameterRepository parameterrepository;
	@Autowired FieldstaffAllocationUploadRepository fieldstaffallocationuploadrepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired ProductMasterRepository productMasterRepository;
	@Autowired FieldStaffService fieldstaffservice;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveuploadalFeldstaffAllocationXlsx(FieldStaffAllocationUploadBean bean) throws Exception {
		// TODO Auto-generated method stub	
		Map<String, Object> map = new HashMap<>();
		DataFormatter df = new DataFormatter();
		String message=null;
		try {
			if (bean.getCsvuploaded().equals("Y")) {
				String division = bean.getDivision_id();
				StringBuffer buffer = new StringBuffer("File not uploaded successfully because of the following errors :- <br><br>");
				boolean isFileWrong = false;
				FieldStaff fieldstaff=null;
				File xlsxFile = new File(System.getProperty("java.io.tmpdir")+"/"+bean.getFileUpload().getOriginalFilename());
				bean.getFileUpload().transferTo(xlsxFile);
				
				Long alloc_cycle=this.doctorUploadRepository.getAllocationCycleNumberPg(bean.getPeriod_id(), bean.getFinYear());
				String alloc_uniq_no=bean.getFinYear()+bean.getPeriod_id()+String.format("%05d", alloc_cycle);

				Workbook work=WorkbookFactory.create(xlsxFile);
				XSSFSheet sheet=(XSSFSheet) work.getSheetAt(0);
				int k=1;
				XSSFRow headerRow=sheet.getRow(k);
				XSSFRow row=null;
			//	Long stateId =0l;
				try {
				k++;
				
				outer: while (sheet.getRow(k) != null) {
					row=sheet.getRow(k);
					Long doc_id=null;
					Long doc_fs_id=null;
					
					FieldstaffAllocationUploadXlsxBean xlsxBean = new FieldstaffAllocationUploadXlsxBean();
					xlsxBean.setAlloc_uniq_no(alloc_uniq_no);
					xlsxBean.setMgrMapcode(df.formatCellValue(row.getCell(0)));
					xlsxBean.setHcpName(row.getCell(2).toString());
					xlsxBean.setSpeciallity(row.getCell(3).toString());
					xlsxBean.setAddress(row.getCell(4).toString().trim().length()>300?
							row.getCell(4).toString().trim().substring(1,299):row.getCell(4).toString().trim());
					xlsxBean.setCity(row.getCell(5).toString().length()<45?row.getCell(5).toString():row.getCell(5).toString().substring(0, 45));
					xlsxBean.setPincode(df.formatCellValue(row.getCell(6)));
					xlsxBean.setMobile(df.formatCellValue(row.getCell(7)));
					xlsxBean.setEmail(row.getCell(8)!=null?row.getCell(8).toString():null);
					xlsxBean.setCluster(row.getCell(9).toString());

					if(row.getCell(10)== null ||row.getCell(10).toString()== "") {
						xlsxBean.setState("GOA");
					}
					else{
					xlsxBean.setState(row.getCell(10).toString().toUpperCase().trim());
					}
					
//					List<String> states = parameterrepository.getstates();
//					System.out.println("xlsxBean.getStatename()"+states.toString());
					
//					if(!states.contains(xlsxBean.getState())) {
//						String errMsg="Invalid State";
//					//	this.writeErrorLog(xlsxBean, errMsg);
//					throw new MedicoException("Invalid State");
//						}
//					stateId= parameterrepository.getStateByStateName(xlsxBean.getState().toUpperCase().trim()).getSg_prmdet_id();

					//checking fieldstaff x exist or not
					fieldstaff=this.fieldstaffallocationuploadrepository.getFieldStaffByMapCode2(xlsxBean.getMgrMapcode(),Long.valueOf(bean.getDivision_id()));

					
					if(xlsxBean.getAddress()==null || xlsxBean.getAddress().trim().equals("")) {
						String errMsg="Please check address length.Its is empty";
					//	this.writeErrorLog(xlsxBean, errMsg);
					//	continue;
						throw new MedicoException("Please check address length.Its is empty");
					}
					
					if(xlsxBean.getAddress().length()>220) {
						String errMsg="Please check address length.Its greater than 220";
					//	this.writeErrorLog(xlsxBean, errMsg);
					//	continue;
						throw new MedicoException("Please check address length.Its greater than 220");
					}
					
					
					//write doctor if not present
					if(fieldstaff==null) {
						FieldStaff fs = new FieldStaff();
						fs.setFstaff_code("0");
						fs.setCompany(bean.getComp_code());
						fs.setClass_id(0L);
						fs.setCity_id(0l);
						fs.setCf_idwyth(0L);
						fs.setCf_idpipl(0L);
						fs.setCf_idpfz(0L);
					    fs.setDesignation("TSE");
				//		fs.setDist_code(staff.getDist_code()==null ?"":staff.getDist_code());
						fs.setEmail(xlsxBean.getEmail());
						fs.setFs_div_id(Long.valueOf(bean.getDivision_id()));
						fs.setFullAddr(xlsxBean.getAddress());
						fs= this.updateAddress(fs, xlsxBean.getCity(), xlsxBean.getPincode());
						fs.setFstaff_addr_upd_cycle(0L);
						fs.setFstaff_alt_name(xlsxBean.getHcpName());
						//fs.setFstaff_code(fb.getAlternateName());
						fs.setFstaff_destination(xlsxBean.getCity());
						fs.setFstaff_disp_cycle(0L);
						fs.setFstaff_display_name(xlsxBean.getHcpName());
						fs.setFstaff_dom_exp("0");
						fs.setFstaff_emp_num("0");
						fs.setFstaff_erp_code("0");
						fs.setFstaff_erp_loc_code("0");
						
						fs.setFstaff_ins_dt(new Date());
						fs.setFstaff_ins_ip_add("0");
						fs.setFstaff_ins_usr_id("0");
						fs.setFstaff_joining_date(new Date());
						fs.setFstaff_loc_id(0l);
						fs.setLevel_code("001");
						fs.setFstaff_map_code1("0");
						fs.setFstaff_map_code2("0");
						fs.setFstaff_terr_id("0");
						fs.setFstaff_terr_code("");
						fs.setFstaff_terrname("");

						fs.setFstaff_mgr3_id(0l);
						fs.setFstaff_mgr4_id(0l);
						fs.setFstaff_mgr5_id(0l);
						fs.setFstaff_mgr6_id(0l);
						fs.setFstaff_mobile(xlsxBean.getMobile());
						fs.setFstaff_mod_ip_add("");
						fs.setFstaff_mod_usr_id("");
						fs.setFstaff_name(xlsxBean.getHcpName());
						fs.setFstaff_pan_no("0");
						fs.setFstaff_pmp_perf(BigDecimal.ZERO);
						fs.setFstaff_pool_ind("0");
						fs.setRegion_code("0");
						fs.setFstaff_samp_disp_ind("Y");
						fs.setFstaff_samp_sup_loc(0L);
						fs.setStatus("A");
						fs.setFstaff_map_code2(xlsxBean.getMgrMapcode());
						if(xlsxBean.getState().trim().toUpperCase().equals(MAHARASHTRA)) {
							
							fs.setFstaff_state_id(183l);//Maha
							
							if(bean.getDivision_id().trim().equals("1")) {
								fs.setHq_id(1008l);
								fs.setFstaff_mgr1_id(31154l);
								fs.setFstaff_mgr2_id(31152l);
								
							}
							else if(bean.getDivision_id().trim().equals("2")){
								fs.setHq_id(1010l);
								fs.setFstaff_mgr1_id(31158l);
								fs.setFstaff_mgr2_id(31156l);
							}
							else {
								fs.setHq_id(0l);
								fs.setFstaff_mgr1_id(0l);
								fs.setFstaff_mgr2_id(0l);
							}
						}
						else {
							fs.setFstaff_state_id(198l);//Else
							
							if(bean.getDivision_id().trim().equals("1")) {
								fs.setHq_id(1009l);
								fs.setFstaff_mgr1_id(31155l);
								fs.setFstaff_mgr2_id(31153l);
							}
							else if(bean.getDivision_id().trim().equals("2")){
								fs.setHq_id(1011l);
								fs.setFstaff_mgr1_id(31159l);
								fs.setFstaff_mgr2_id(31157l);
							}
							else {
								fs.setHq_id(0l);
								fs.setFstaff_mgr1_id(0l);
								fs.setFstaff_mgr2_id(0l);
							}
						}
						fs.setFstaff_tel_no("0");
						fs.setFstaff_transporter("0");
						fs.setFstaff_training("0");
						fs.setType_id(0L);
						fs.setFstaff_zip(xlsxBean.getPincode());
						if(bean.getDivision_id().trim().equals("1")) {
							fs.setFstaff_zone_id(1768l);//RX1
						}
						else if(bean.getDivision_id().trim().equals("2")){
							fs.setFstaff_zone_id(1786l);//RX2
						}
						else {
							fs.setFstaff_zone_id(0l);//Else
						}
						fs.setFstaff_zonename("");
						fs.setFstaff_type_ind_id(0L);
						fs.setFstaff_mcl_no(" ");
						fs.setFstaff_requestor_fstaff_id(0l);
						fs.setFstaff_prod_sub_type_allowed("");
						fs.setTeam_code("0");
						fs.setFs_category("F");
						fs.setCf_idpfz(17l);
						fs.setFstaff_ins_dt(new Date());
						fs.setFstaff_ins_ip_add(bean.getIp_address());
						fs.setFstaff_ins_usr_id(bean.getUserid());
						
						transactionalRepository.persist(fs);
						
						fieldstaff = fs;
						
						for(Long sub_comp_id:CFA_LINKAGE) {
							//Long requestor_loc_id=fieldstaffservice.getCfa_id(fieldstaff.getFstaff_id(),sub_comp_id);
							Long loc_id=fieldstaffservice.getCfa_id(fs.getFstaff_id(),sub_comp_id);
							System.out.println("loc_id "+loc_id);
							if(loc_id==null || loc_id==0L) {
								 fieldstaffservice.FsCfaLinkage(fs.getFstaff_id(),fs.getCf_idpfz(),new AllocReqHeader(),sub_comp_id);
							}
						}
					}
					else {
						fieldstaff.setFullAddr(xlsxBean.getAddress());
						fieldstaff= this.updateAddress(fieldstaff, xlsxBean.getCity(), xlsxBean.getPincode());
						transactionalRepository.update(fieldstaff);
					}
					
					//Qty starts from index 10
					int i=11;
					int count =0;  
					while (row.getCell(i) != null) {
						count++;
						
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
								temp.setCustid((df.formatCellValue(row.getCell(0)).toString().trim()));
								temp.setHcpname(row.getCell(2).toString().trim());
								temp.setSpeciality(row.getCell(3).toString().trim());
								temp.setAddress(row.getCell(4).toString().trim());
								temp.setCity(row.getCell(5).toString().length()<45?row.getCell(5).toString():row.getCell(5).toString().substring(0, 45));
								temp.setPincode(df.formatCellValue(row.getCell(6)).toString().trim());
								temp.setMobile(df.formatCellValue(row.getCell(7)).toString().trim());
								temp.setEmailid(row.getCell(8)!=null?row.getCell(8).toString().trim():null);
								temp.setCluster(row.getCell(9).toString().trim());
								temp.setAlloc_type("A");
								if(row.getCell(10)== null || row.getCell(10).toString().trim().equals("") ) {
									temp.setState_name("GOA");
								}
								else {
									temp.setState_name(row.getCell(10).toString().trim());
								}
									temp.setState_id(fieldstaff.getFstaff_state_id());
								
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
									temp.setDoc_id(0l);
									temp.setDoc_fs_id(fieldstaff.getFstaff_id());
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
					FileUtils.copyFile(xlsxFile, fileToCreate);
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
					throw e;
					
					
				}
			}
		}
		catch (Exception e) {
			System.out.println("Outer Catch");
			
			System.out.println("RESPOP : "+map.get(RESPONSE_MESSAGE).toString());
			throw e;
		}
		return map;
	}

	public FieldStaff updateAddress(FieldStaff fstaff,String city,String pincode) throws Exception {
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
		if(fstaff.getFullAddr()!=null) {
			str=fstaff.getFullAddr().trim();
			list= Arrays.asList(str.split(" "));
			System.out.println("doctor.getDoc_full_adr() :::::: "+fstaff.getFullAddr());
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
		fstaff.setFstaff_addr1(address1);
		fstaff.setFstaff_addr2(address2);
		fstaff.setFstaff_addr3(address3);
		fstaff.setFstaff_addr4(city+" "+pincode);
		
	//	this.transactionalRepository.update(doctor);
		
		}
		return fstaff;
	}
}
