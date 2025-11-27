package com.excel.service;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.excel.bean.ErpProductBean;
import com.excel.bean.GoApptiveFieldstaffBean;
import com.excel.bean.GoApptiveTerrMasterBean;
import com.excel.bean.GoapptiveTeamBrandbean;
import com.excel.exception.MedicoException;
import com.excel.model.FieldStaff;
import com.excel.model.GoApptiveDspDetails;
import com.excel.model.TempFieldMaster;
import com.excel.model.TerrMaster;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.GoApptiveRepository;
import com.excel.repository.TerritoryRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoResources;
import com.excel.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class GoApptiveServiceImpl implements GoApptiveService {

	@Autowired RestTemplate restTemplate;
	@Autowired GoApptiveRepository goApptiveRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired private FieldStaffRepository fieldStaffRepository;
	@Autowired TerritoryRepository territoryRepository;
	
	
	public static String url="http://pfizerupdates.channelitix.com";
	SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sdf2  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	SimpleDateFormat sdf3  = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getGoaptiveTerrMaster() {
		Map<String, Object> map=null;
		int count=0;
		try {
				List<GoApptiveTerrMasterBean> bean = this.goApptiveRepository.getgoaptiveterrmasterdata(6608l);
				List<String> successList=null;
				JSONArray finalObject = new JSONArray();
				JSONObject object=null;
				int i=0;
				if(bean!=null && bean.size()>0) {
					for(GoApptiveTerrMasterBean territory:bean) {
						object=new JSONObject();
				//		System.out.println("TERR_ID "+territory.getTerr_id());
						object.put("TERR_CODE",territory.getTerr_code().trim().replaceAll("\u00A0", ""));//PIL
						object.put("TERR_NEW_CHANGED","N");
						object.put("TERR_DIVISION",territory.getTerr_div_id());
						object.put("TERR_TEAM_CODE",territory.getTerr_team_code().trim());
						object.put("TERR_LEVEL_CODE",territory.getTerr_level_code().trim());
						object.put("TERR_NAME",territory.getTerr_name().trim());
						object.put("TERR_STATE_CODE",territory.getTerr_state_code().trim());
						object.put("TERR_POOL_IND",territory.getTerr_pool_ind().trim());
						object.put("TERR_HQ_CODE",territory.getTerr_hq_code());
						object.put("TERR_HQ_NAME",territory.getTerr_hq_name());
						object.put("TERR_MGR1_TERRCODE",territory.getTerr_mgr1_terrcode()==null?null:territory.getTerr_mgr1_terrcode().trim());
						object.put("TERR_MGR2_TERRCODE",territory.getTerr_mgr2_terrcode()==null?null:territory.getTerr_mgr2_terrcode().trim());
						object.put("TERR_MGR3_TERRCODE",territory.getTerr_mgr3_terrcode()==null?null:territory.getTerr_mgr3_terrcode().trim());
						object.put("TERR_MGR4_TERRCODE",territory.getTerr_mgr4_terrcode()==null?null:territory.getTerr_mgr4_terrcode().trim());
						object.put("TERR_MGR5_TERRCODE",territory.getTerr_mgr5_terrcode()==null?null:territory.getTerr_mgr5_terrcode().trim());
						object.put("TERR_DOM_EXP" ,territory.getTerr_dom_exp());
					    object.put("TERR_ZONECODE" ,territory.getTerr_zonecd());
					    object.put("TERR_REGIONCD" ,territory.getTerr_regioncd().trim().replaceAll("\u00A0", ""));
					    object.put("TERR_DISTRICTCD",territory.getTerr_districtcd());
					    object.put("TERR_DESIGNATION" ,territory.getTerr_designation());
					    object.put("TERR_CFA_LOC_PFZ" ,territory.getTerr_cfa_loc_pfz()==null?null:territory.getTerr_cfa_loc_pfz());
					    object.put("TERR_CFA_LOC_PIPL",territory.getTerr_cfa_loc_pipl()==null?null:territory.getTerr_cfa_loc_pipl());
					    object.put("TERR_TRAINING" ,territory.getTerr_training());
					    finalObject.put(object);
					}
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					//interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
					restTemplate.setInterceptors(interceptors);
				//	System.out.println("finalObject "+finalObject);
					String result = restTemplate.postForObject(this.url+"/territoryDetails",request,String.class);
				//	System.out.println("result" +result);
					JSONObject resultJson = new JSONObject(result);
					//Insert into Erp_product
					String territories=resultJson.get("territories").toString();
					System.out.println("territories "+territories);
					List<GoApptiveTerrMasterBean.TerritoryResponse> response = new Gson().fromJson(territories, new TypeToken<List<GoApptiveTerrMasterBean.TerritoryResponse>>() {}.getType());
					successList=new ArrayList<>();
					for(GoApptiveTerrMasterBean.TerritoryResponse terr:response) {
						if(terr.getStatus().trim().equalsIgnoreCase("success")){
							successList.add(terr.getTERR_CODE());
						}
					}

					if(successList!=null && successList.size()!=0) {
						this.goApptiveRepository.insertgoaptivedata(successList);
					}
				}
		}
		catch (Exception e) {
			count=0;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getDispatchDetailsTerrMaster() {
		Map<String, Object> map=null;
		int count=0;
		try {
				List<GoApptiveDspDetails> bean = this.goApptiveRepository.pushDispToGoapptive(new Date(),new Date());
				JSONArray finalObject = new JSONArray();
				JSONObject object=null;
				List<Long> successList=null;
				int i=0;
				Long newdspId = 0l;
				Long olddspId = 0l;
				if(bean!=null && bean.size()>0) {
					System.out.println("Size "+bean.size());
					for(GoApptiveDspDetails dispatch:bean) {
						newdspId = dispatch.getDsp_id();
						if(newdspId.compareTo(olddspId)!=0 && olddspId.compareTo(0l)!=0) {
							break;
						}
						object=new JSONObject();
						object.put("TERR_CODE",dispatch.getTerr_code().trim());//PIL
						object.put("TERR_DIVISION",dispatch.getTerr_division());
						object.put("TERR_TEAM_CODE",dispatch.getTerr_team_code());
						object.put("DSP_ID",dispatch.getDsp_id());
						object.put("DSP_FIN_YEAR",dispatch.getDsp_fin_year());
						object.put("DSP_PERIOD_CODE",dispatch.getDsp_period_code());
						object.put("DSP_CHALLAN_NO",dispatch.getDsp_challan_no());
						object.put("DSP_CHALLAN_DT",dispatch.getDsp_challan_dt());
						object.put("DSP_LR_NO",dispatch.getDsp_challan_no());
						object.put("DSP_LR_DT",dispatch.getDsp_lr_dt());
						object.put("DSP_TRANSPORTER",dispatch.getDsp_transporter());
						object.put("DSPFSTAFF_NAME",dispatch.getDspfstaff_name());
						object.put("DSP_TERR_CODE",dispatch.getTerr_code().trim());
						object.put("DSPFSTAFF_EMPLOYEENO",dispatch.getDspfstaff_employeeno());
						object.put("DSPDTL_PROD_NAME",dispatch.getDspdtl_prod_name());
						object.put("DSPDTL_BATCH_NO",dispatch.getDspdtl_batch_no());
						object.put("DSP_EXPIRY_DT" ,dispatch.getDsp_expiry_dt());
					    object.put("DSPDTL_DISP_QTY" ,dispatch.getDspdtl_disp_qty());
					    object.put("DSPDTL_RATE" ,dispatch.getDspdtl_rate());
					    object.put("DSP_WT",dispatch.getDsp_wt());
					    object.put("DSP_CASES" ,dispatch.getCases());
					    object.put("DSPFSTAFF_ADDR1",dispatch.getDspfstaff_addr1()!=null && !dispatch.getDspfstaff_addr1().isEmpty()?dispatch.getDspfstaff_addr1().trim().replaceAll("\u00A0", ""):"");
					    object.put("DSPFSTAFF_ADDR2",dispatch.getDspfstaff_addr2()!=null && !dispatch.getDspfstaff_addr2().isEmpty()?dispatch.getDspfstaff_addr2().trim().replaceAll("\u00A0", ""):"");
					    object.put("DSPFSTAFF_ADDR3",dispatch.getDspfstaff_addr3()!=null && !dispatch.getDspfstaff_addr3().isEmpty()?dispatch.getDspfstaff_addr3().trim().replaceAll("\u00A0", ""):"");
					    object.put("DSPFSTAFF_ADDR4",dispatch.getDspfstaff_addr4()!=null && !dispatch.getDspfstaff_addr4().isEmpty()?dispatch.getDspfstaff_addr4().trim().replaceAll("\u00A0", ""):"");
					    object.put("DSPFSTAFF_DESTINATION",dispatch.getDspfstaff_destination());
					    object.put("DSPFSTAFF_MOBILE" ,dispatch.getDspfstaff_mobile());
					    object.put("DSPFSTAFF_EMAIL" ,dispatch.getDspfstaff_email());
					    object.put("DSPDTL_ID" ,dispatch.getDspdtl_id());
					    object.put("DSPDTL_RESENT" ,dispatch.getDspdtl_resent());
					    object.put("DELIVERED_DATE_TIME" ,MedicoResources.convertUtilDateToString(new Date()));
					    object.put("DSP_TYPE" ,dispatch.getSmp_prod_type());
					    finalObject.put(object);
					    olddspId=newdspId;
					}
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					String reqactualpath1 = "D:\\sg\\goapttivereq\\goapptivereq__"+new Date().getTime()+".txt";
					FileWriter reqfile1 = new FileWriter(reqactualpath1);
					reqfile1.write(request.toString());
					reqfile1.close(); 
					
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					restTemplate.setInterceptors(interceptors);
					//System.out.println("finalObject "+finalObject);
					String reqactualpath = "D:\\sg\\goapttivereq\\goapptivereq"+new Date().getTime()+".txt";
					FileWriter reqfile = new FileWriter(reqactualpath);
					reqfile.write(finalObject.toString());
					reqfile.close(); 
					
					String result = restTemplate.postForObject(this.url+"/deliveryDetails",request,String.class);
				//	System.out.println("result::: " +result);
					
					JSONObject resultJson = new JSONObject(result);
					//Insert 
					String deliveries=resultJson.get("deliveries").toString();
					String actualpath = "D:\\sg\\goapttiveres\\goapptive"+new Date().getTime()+".txt";
					FileWriter file = new FileWriter(actualpath);
					file.write(result);
					file.close(); 
				//	System.out.println("deliveries "+deliveries);
					List<GoApptiveDspDetails.DispatchResponse> response = new Gson().fromJson(deliveries, new TypeToken<List<GoApptiveDspDetails.DispatchResponse>>() {}.getType());
					successList=new ArrayList<>();
					for(GoApptiveDspDetails.DispatchResponse dispatch:response) {
						if(dispatch.getStatus().trim().equalsIgnoreCase("success")){
							if(!successList.contains(dispatch.getDSP_ID())){
								successList.add(dispatch.getDSP_ID());
							}
						}
					}
					System.out.println("list size :::"+successList.size());
					if(successList!=null && successList.size()!=0) {
						this.goApptiveRepository.insertDspData(successList);
					}
				}
			
		}
		catch (Exception e) {
			count=0;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int getTeamBrandDetails(String year) {
//		Map<String, Object> map=null;
		int count=0;
		try {
				List<GoapptiveTeamBrandbean> bean = this.goApptiveRepository.getgoaptiveteambranddata();
				JSONArray finalObject = new JSONArray();
				JSONObject object=null;
				List<String> successList=null;
				if(bean!=null && bean.size()>0) {
					for(GoapptiveTeamBrandbean data:bean) {
						object=new JSONObject();
						object.put("TERR_DIVISION",data.getDivid());
						object.put("TERR_TEAM_CODE",data.getTeamid());
						object.put("SA_PROD_GROUP_ID",data.getSa_prod_groupname());
						object.put("SLNO",data.getSlno());
					    finalObject.put(object);
					}
					HttpEntity<String> request = new HttpEntity<>(finalObject.toString());
					ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
					interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
					//interceptors.add(new HeaderRequestInterceptor("Authorization", "Basic " + this.key));
					restTemplate.setInterceptors(interceptors);
					System.out.println("finalObject "+finalObject);
					String result = restTemplate.postForObject(this.url+"/teamBrandMapping",request,String.class);
					System.out.println("result" +result);
					JSONObject resultJson = new JSONObject(result);
					//Insert
					String teams=resultJson.get("teams").toString();
					System.out.println("teams "+teams);
					List<GoapptiveTeamBrandbean.TeamBrandResponse> response = new Gson().fromJson(teams, new TypeToken<List<GoapptiveTeamBrandbean.TeamBrandResponse>>() {}.getType());
					successList=new ArrayList<>();
					for(GoapptiveTeamBrandbean.TeamBrandResponse team:response) {
						if(team.getStatus().trim().equalsIgnoreCase("success")){
							successList.add(team.getSLNO().toString());
						}
					}
					this.goApptiveRepository.insertTeamBrandData(successList);
				}
			
		}
		catch (Exception e) {
			count=0;
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return count;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public  void  addGoApptiveFieldstaff(GoApptiveFieldstaffBean details) throws Exception {
		List<TerrMaster> territory=this.territoryRepository.getTerrDetailByTerrCode(details.getTerr_code());
		if(territory==null || territory.size()==0)
			throw new MedicoException("Territory details not found");
		
		if(details.getFs_new_changed().trim().equals("N")) {
			List<FieldStaff> list=this.fieldStaffRepository.getFieldStaffByEmployeeNumber(details.getFstaff_employee_no(),territory.get(0).getTerr_div_id());
			if(list!=null && list.size()>0)
				throw new MedicoException("Duplicate Employee Number");
			List<FieldStaff> fstaffList=this.fieldStaffRepository.getFieldStaffByTerrCode(details.getTerr_code());
			if(fstaffList!=null && fstaffList.size()>0)
				throw new MedicoException("Territory Already attached to active fieldstaff");
		}
		if(details.getFstaff_pincode()==null || details.getFstaff_pincode().length()!=6)
			throw new MedicoException("Pin code should be of 6 character" );
		
	
		if(details.getFs_new_changed().trim().equals("C")) {
			List<FieldStaff> list=this.fieldStaffRepository.getFieldStaffByEmployeeNumber(details.getFstaff_employee_no(),territory.get(0).getTerr_div_id());
			if(list==null) {
				throw new MedicoException("Employee is Not Present in System" );
			}
		}
		
		TempFieldMaster fieldMaster=new TempFieldMaster();
		fieldMaster.setDate_time(new Date());
		fieldMaster.setDate_time_recd(new Date());
		fieldMaster.setTerr_code(details.getTerr_code());
		fieldMaster.setFs_new_changed(details.getFs_new_changed());
		fieldMaster.setFin_year_id(2022l);
		fieldMaster.setCompany(details.getCompany_code());
		fieldMaster.setFstaff_name(details.getFstaff_alt_name());//territory name
		fieldMaster.setFstaff_display_name(details.getFstaff_display_name());
		fieldMaster.setFstaff_employee_no(details.getFstaff_employee_no());
		fieldMaster.setFstaff_addr1(details.getFstaff_addr1());
		fieldMaster.setFstaff_addr2(details.getFstaff_addr2());
		fieldMaster.setFstaff_addr3(details.getFstaff_addr3());
		fieldMaster.setFstaff_addr4(details.getFstaff_addr4());
		fieldMaster.setFstaff_destination(details.getFstaff_destination());
		fieldMaster.setFstaff_pincode(details.getFstaff_pincode());
		fieldMaster.setFstaff_mobile(details.getFstaff_mobile());
		fieldMaster.setFstaff_mobile2(details.getFstaff_mobile2());
		fieldMaster.setFstaff_email(details.getFstaff_email());
		fieldMaster.setFstaff_tel_no(details.getFstaff_tel_no());
		fieldMaster.setFstaff_transporter(details.getFstaff_transporter());
		fieldMaster.setFstaff_resigned(details.getFstaff_resigned());
		fieldMaster.setFstaff_joining_date(details.getFstaff_joining_date());
		fieldMaster.setFstaff_leaving_date(details.getFstaff_leaving_date());
		fieldMaster.setFstaff_samp_disp_ind("Y");
		fieldMaster.setThirdparty_div(territory.get(0).getTerr_div_id());
		fieldMaster.setFstaff_transporter(details.getFstaff_transporter());
		fieldMaster.setTerr_id(territory.get(0).getTerr_id());
		//territory details
		fieldMaster.setFstaff_div_id(territory.get(0).getTerr_div_id());
		fieldMaster.setState_id(territory.get(0).getTerr_state_id());
		fieldMaster.setCfa_loc_pfz_id(territory.get(0).getTerr_cfa_loc1());
		fieldMaster.setCfa_loc_pipl_id(territory.get(0).getTerr_cfa_loc3());
		fieldMaster.setZone_id(territory.get(0).getTerr_zone_id());
		fieldMaster.setHq_id(territory.get(0).getTerr_hq_id());
		fieldMaster.setTeam_code(territory.get(0).getTeam_code());
		fieldMaster.setFstaff_level_code(territory.get(0).getTerr_level_code());
		fieldMaster.setMgr1_terr_code(details.getMgr1_terr_code());
		fieldMaster.setMgr2_terr_code(details.getMgr2_terr_code());
		fieldMaster.setMgr3_terr_code(details.getMgr3_terr_code());
		fieldMaster.setMgr4_terr_code(details.getMgr4_terr_code());
		fieldMaster.setMgr5_terr_code(details.getMgr5_terr_code());
		fieldMaster.setMgr6_terr_code(details.getMgr6_terr_code());
		fieldMaster.setMain_mast_updated("N");
		System.out.println("leaving date "+details.getFstaff_leaving_date());
		System.out.println("Joing "+details.getFstaff_joining_date());
		this.transactionalRepository.persist(fieldMaster);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void pushDataToMainFiedldstaff() throws Exception {
		// TODO Auto-generated method stub
		this.goApptiveRepository.pushDataToMainFiedldstaff();
	}
	@Override
	public List<GoApptiveDspDetails> pushDispToGoapptive(Date stdt, Date endt) throws Exception {
		// TODO Auto-generated method stub
		return goApptiveRepository.pushDispToGoapptive(stdt, endt);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateRecords() {
		goApptiveRepository.updateRecords();
	}

//	@Override
//	public List<TerrMaster> getTerrDetailByTerrCode(String terr_code) {
//		// TODO Auto-generated method stub
//		return territoryRepository.getTerrDetailByTerrCode(terr_code);
//	}

}
