package com.excel.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.CRMExpenseBean;
import com.excel.model.CRMReqRequestsImage;
import com.excel.model.CRM_Req_Request;
import com.excel.model.CrmSchemeMaster;
import com.excel.model.SG_d_parameters_details;
import com.excel.repository.CrmSchemeRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
@Service
public class CrmSchemeServiceImpl implements CrmSchemeService ,MedicoConstants{
	
	static String newFilePath = null;
	@Autowired private CrmSchemeRepository crmSchemeRepo;
	@Autowired private TransactionalRepository transactionalRepository;
	@Autowired private ParameterService paramService;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CrmSchemeMaster> getCRMSchemeDetails(String companyCode, String crm_sch_type, String curBiz, String expBiz,Long locId,
			String doccls,String docspl) throws Exception {
		return this.crmSchemeRepo.getCRMSchemeDetails(companyCode, crm_sch_type, curBiz, expBiz,locId,doccls,docspl);
	}
	
	@Override
	public Map<String, Object> uploadCrmEntryImage(List<MultipartFile> files) {

		System.out.println("inside upload file");
		String newFile = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String rootPath = MedicoConstants.CRM_FILE_PATH;
			File dir = new File(rootPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			for (MultipartFile file : files) {
				// File f = new File("file");
				String filename = file.getOriginalFilename().toString();
				System.out.println("orignal file name:" + filename);
				String newFileString = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
				// frm here
				String str = file.getOriginalFilename();
				String fileFormat = str.substring(str.lastIndexOf('.') + 1);
				System.out.println("fileFormat:::" + fileFormat);
				String[] arrOfStr = null;
				String str1 = file.getOriginalFilename();
				if (fileFormat.equalsIgnoreCase("jpg")) {
					arrOfStr = str1.split(".jpg");
				} else if (fileFormat.equalsIgnoreCase("jpeg")) {
					arrOfStr = str1.split(".jpeg");
				} else if (fileFormat.equalsIgnoreCase("pdf")) {
					arrOfStr = str1.split(".pdf");
				} else if (fileFormat.equalsIgnoreCase("png")) {
					arrOfStr = str1.split(".png");
				}
				for (String a : arrOfStr) {
					long timevar = new Date().getTime();
					newFile = a + timevar + "." + fileFormat;
					break;
				}
				// till here
				newFileString = dir.getAbsolutePath() + File.separator + newFile;
				System.out.println(newFileString);
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFileString));
				stream.write(bytes);
				stream.close();
				newFilePath = newFileString;
				System.out.println("new file path is:" + newFileString);

				map.put(newFile, newFilePath);
			}
			// map.put("filePath",newFilePath);
			// map.put("fileName",newFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public CRM_Req_Request save(CRMExpenseBean bean) throws Exception {
		CRM_Req_Request save = null;
		try {
			String req_date = bean.getReqDate();
			String schedule_date = MedicoResources.convertUtilDateToString_DD_MM_YYYY(bean.getScheddate());
			CRM_Req_Request crm = new CRM_Req_Request();
			String crmno = crmSchemeRepo.getCRMReqno();
			if (bean.getSavemod().equals("S")) {
				crm.setLoc_id(bean.getStk_loc());
				crm.setCrm_req_company(bean.getCompCode());
				crm.setCrm_req_finyear(bean.getFinyear());
				crm.setCrm_req_date(new Date());
				crm.setCrm_sched_date(convertStringtoDate(schedule_date));
				crm.setCrm_req_cust_type(bean.getCusttype());
				crm.setCrm_req_terr_id(bean.getTerritory() == null ? 0l : Long.valueOf(bean.getTerritory()));
				crm.setCrm_req_fs_id(bean.getFsid()== null ? 0l : bean.getFsid());
				crm.setCrm_req_div_id(bean.getDiv_id()== null ? 0l : bean.getDiv_id());
				crm.setCrm_req_uniq_hcp_id(bean.getCusttype().equalsIgnoreCase("D") ? bean.getCustid() : 0L);
				crm.setCrm_req_cust_id(bean.getCusttype().equalsIgnoreCase("W") ? bean.getCustid() : 0L);
				crm.setCrm_req_retailer_id(0L);
				crm.setCrm_req_crm_name(bean.getCustname());
				crm.setCrm_req_cust_addr1(bean.getAddr1());
				crm.setCrm_req_cust_addr2(bean.getAddr2());
				crm.setCrm_req_cust_addr3(bean.getAddr3());
				crm.setCrm_req_cust_addr4(bean.getAddr4());
				crm.setCrm_req_cust_city(bean.getCity());
				crm.setCrm_req_cust_pincode(bean.getPincode());
				crm.setCrm_req_cust_mobile(bean.getMobile());
				crm.setCrm_req_cust_email(bean.getEmail());
				crm.setCrm_req_cust_panno(bean.getPanno());
				crm.setCrm_req_cust_hcp_no(bean.getHcpno());
				crm.setCrm_req_curr_biz(bean.getCurBiz());
				crm.setCrm_req_expect_biz(bean.getExpBiz());
				crm.setCrm_req_scheme_id(bean.getSchemeid());
				crm.setCrm_req_scheme_type(bean.getSchemetype());
				crm.setCrm_req_exps_details(bean.getSchemeExpsDetails());
				crm.setCrm_req_exps_item(bean.getSchemeExpsItem());
				crm.setCrm_req_qty(bean.getExpqty());
				crm.setCrm_req_rate(bean.getExprate());
				crm.setCrm_req_value(bean.getExpvalue());
				crm.setCrm_req_qty_approv(BigDecimal.ZERO);
				crm.setCrm_req_rate_approv(BigDecimal.ZERO);
				crm.setCrm_req_value_approv(BigDecimal.ZERO);
				crm.setCrm_req_confirm("N");
				crm.setCrm_req_ins_by(bean.getUsername());
				crm.setLast_mod_dt(new Date());
				crm.setLast_mod_by(bean.getUsername());
				crm.setPeriod_id(bean.getPeriod_id());
				crm.setCrm_req_no(crmno);
				crm.setAppr_status("E");
				crm.setUser_id(bean.getUsername());
				transactionalRepository.persist(crm);

				CRMReqRequestsImage crmexpImage = null;
				if (bean.getImagefiles() != null) {
					for (Entry<String, Object> entry : bean.getImagefiles().entrySet()) {
						System.out.println("MapKey = " + entry.getKey() + ", MapValue = " + entry.getValue());

						crmexpImage = new CRMReqRequestsImage();
						crmexpImage.setCrm_id(crm.getCrm_req_id());
						crmexpImage.setFin_year_id(Long.valueOf(crm.getCrm_req_finyear()));
						crmexpImage.setView_path(entry.getKey());
						crmexpImage.setImage_path((entry.getValue()).toString());
						transactionalRepository.persist(crmexpImage);
					}
				}
				save = crm;
			} else {

				CRM_Req_Request data = crmSchemeRepo.getCRMDetailsBySingleid(bean.getCrm_req_id()).get(0);
				data.setCrm_sched_date(convertStringtoDate(schedule_date));
				data.setCrm_req_qty(bean.getExpqty());
				data.setCrm_req_rate(bean.getExprate());
				data.setCrm_req_value(bean.getExpvalue());
				data.setLast_mod_dt(new Date());
				data.setLast_mod_by(bean.getUsername());
				transactionalRepository.update(data);

				List<CRMReqRequestsImage> imglist = crmSchemeRepo
						.geImageBycrmId(Long.valueOf(bean.getCrm_req_id()));
				if (imglist.size() > 0) {
					for (int i = 0; i < imglist.size(); i++) {
						transactionalRepository.delete(imglist.get(i));
					}
				}

				CRMReqRequestsImage crmexpImage = null;
				CRMReqRequestsImage crmoldImage = null;
				if (bean.getImagefiles() != null || bean.getImagefiles().size() > 0) {
					for (Entry<String, Object> entry : bean.getImagefiles().entrySet()) {
						System.out.println("MapKey = " + entry.getKey() + ", MapValue = " + entry.getValue());

						crmexpImage = new CRMReqRequestsImage();
						crmexpImage.setCrm_id(data.getCrm_req_id());
						crmexpImage.setFin_year_id(Long.valueOf(data.getCrm_req_finyear()));
						crmexpImage.setView_path(entry.getKey());
						crmexpImage.setImage_path((entry.getValue()).toString());
						transactionalRepository.persist(crmexpImage);
					}
				}
				if (bean.getUpdateimagefiles() != null || bean.getUpdateimagefiles().size() > 0) {

					for (int i = 0; i < bean.getUpdateimagefiles().size(); i++) {

						System.out.println("View_path = " + bean.getUpdateimagefiles().get(i).getView_path()
								+ ", Image_path = " + bean.getUpdateimagefiles().get(i).getImage_path());

						crmoldImage = new CRMReqRequestsImage();
						crmoldImage.setCrm_id(Long.valueOf(bean.getCrm_req_id()));
						crmoldImage.setFin_year_id(Long.valueOf(data.getCrm_req_finyear()));
						crmoldImage.setView_path(bean.getUpdateimagefiles().get(i).getView_path());
						crmoldImage.setImage_path(bean.getUpdateimagefiles().get(i).getImage_path());
						transactionalRepository.persist(crmoldImage);
					}
				}
				System.out.println("Wel-Come to update");
				save = data;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return save;
	}
	
	public static Date convertStringtoDate(String strDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			if (strDate != null && strDate.trim().length() > 0)
				date = formatter.parse(strDate);
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}

	@Override
	public List<CRM_Req_Request> getviewlist(String userid) throws Exception {
		return crmSchemeRepo.getviewlist(userid);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> crmConfirmation(List<Long> crm_req_id, String finYearId, String user_name,
			String companycode) throws Exception {
		Map<String, Object> map = new HashMap<>();
		String msg = "";
		CRM_Req_Request crm;
		try {

			List<CRM_Req_Request> list = crmSchemeRepo.getCRMDetailsByids(crm_req_id);

			for (int i = 0; i < list.size(); i++) {
				crm = new CRM_Req_Request();
				//for now since there is no approvals page then we are confirming and approving the same
				list.get(i).setAppr_status("A");
				list.get(i).setCrm_req_confirm("Y");
				transactionalRepository.update(list.get(i));
			}

			map.put("msg", "Transaction is saved sucessfully");
		} catch (Exception e) {

			map.put("msg", RESPONSE_ERROR_MESSAGE);
		}

		return map;
	}
	
	
	@Override
	public CRM_Req_Request getmodifyDetailsByid(String crm_req_id) throws Exception {
		CRM_Req_Request list = crmSchemeRepo.getCRMDetailsBySingleid(crm_req_id).get(0);
		CrmSchemeMaster schemedetails = this.entityManager.find(CrmSchemeMaster.class, list.getCrm_req_scheme_id());
		list.setDoc_catg(schemedetails.getDoctor_category().trim());
		list.setDoc_class(schemedetails.getDoctor_type().trim());
		list.setDoc_special(schemedetails.getDoctor_speciality().trim());
		list.setCrm_scheme_descr(schemedetails.getCrm_scheme_descr());
		return list;
	}

	@Override
	public List<CRMReqRequestsImage> geImageBycrmId(Long crmId) throws Exception {
		return crmSchemeRepo.geImageBycrmId(crmId);
	}
	
	

}
