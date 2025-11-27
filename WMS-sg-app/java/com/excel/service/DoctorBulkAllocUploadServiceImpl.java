package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.BulkDoctorPdf;
import com.excel.bean.BulkDoctorPdf.ProductDetails;
import com.excel.bean.BulkUpldBean;
import com.excel.bean.DoctorBulkUploadBean;
import com.excel.bean.HcpBulkUploadErrorBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.exception.MedicoException;
import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.Blk_Challans_Generated_Log;
import com.excel.model.Blk_hcp_req_doctors;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.Blk_hcp_req_products;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.FinYear;
import com.excel.model.Location;
import com.excel.model.SmpItem;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.TerrMaster;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.DoctorBulkAllocUploadRepository;
import com.excel.repository.PeriodMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.excel.utility.ReportFormats;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
@Primary
@Service
public class DoctorBulkAllocUploadServiceImpl implements DoctorBulkAllocUploadService,MedicoConstants {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired private DoctorBulkAllocUploadRepository doctorbulkallocuploadrepository;
	@Autowired private FinancialYearService financialyearservice;
	@Autowired private TransactionalRepository transactionalRepository;
	@Autowired private FieldStaffService fieldStaffService;
	@Autowired private ProductMasterService productMasterService;
	@Autowired private SpecialAllocationService specialAllocationService;
	@Autowired private DivisionMasterService divMasterService;
	@Autowired private PeriodMasterRepository periodMastService;
	@Autowired private UserMasterService userMasterService;
	@Autowired private EmailTranWiseService emailtranwiseservice;
	@Autowired private LocationService locationService;
	@Autowired private TaskMasterService taskMasterService;	
	@Autowired private RuntimeService runtimeService;
	@Autowired private ProductMasterService productmasterservice;
	@Autowired
	private CompanyMasterRepository companyMasterRepositoryImpl;
	
	@Autowired
	private TerritoryService terrService;
	
	@Override
	public List<SmpItem> getBrands(Long divId, Long prodtype,String userid) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getBrands(divId, prodtype,userid);
	}

	@Override
	public List<SmpItem> getProductsForBulkUpload(Long divId, Long prodtype, String brands) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getProductsForBulkUpload(divId, prodtype, brands);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveBulkDoctorUpload(DoctorBulkUploadBean doctorBulkUploadBean) throws Exception {
		Blk_hcp_req_hdr blk_hcp_req_hdr = null;
		Map<String,Object> map=new HashedMap<>();
		// adding in to bean
		try {
			FinYear fin =financialyearservice.getCurrentFinancialYear(doctorBulkUploadBean.getCompany());
			doctorBulkUploadBean.setBlk_csv_gen_date(null);
			doctorBulkUploadBean.setBlk_csvgen_usr_id("");
			doctorBulkUploadBean.setBlk_status("N");
			
			//doctorBulkUploadBean.setBlk_mod_ip_add("");
			if(doctorBulkUploadBean.getTransactionMode().trim().equals("M")) {
				blk_hcp_req_hdr = doctorbulkallocuploadrepository.getObjById(doctorBulkUploadBean.getReqIdForModify());
			}
			else {
				blk_hcp_req_hdr =this.saveblk_hcp_req_hdr(doctorBulkUploadBean);
			}
			// insert BLK_HCP_REQ_DOCTORS here 
			Blk_hcp_req_doctors blk_hcp_req_doctors =null;
			for(int i=0;i<doctorBulkUploadBean.getDoctorDetails().size();i++) {
				blk_hcp_req_doctors = new Blk_hcp_req_doctors();
				
				blk_hcp_req_doctors.setBlk_hcp_req_id(blk_hcp_req_hdr.getBlk_hcp_req_id());
				blk_hcp_req_doctors.setBlk_hcp_req_date(new Date());
				blk_hcp_req_doctors.setBlk_mdm_addr_chg(doctorBulkUploadBean.getDoctorDetails().get(i).getAllow_adress_change().trim());
				blk_hcp_req_doctors.setBlk_hcp_req_no(blk_hcp_req_hdr.getBlk_hcp_req_no());
				blk_hcp_req_doctors.setFin_year_id(fin.getFin_year());
				blk_hcp_req_doctors.setDiv_id(doctorBulkUploadBean.getDivId());
				blk_hcp_req_doctors.setHcp_name(doctorBulkUploadBean.getDoctorDetails().get(i).getHcp_name());
				blk_hcp_req_doctors.setSrt_number(doctorBulkUploadBean.getDoctorDetails().get(i).getSrt_number());
				blk_hcp_req_doctors.setSrt_date(doctorBulkUploadBean.getDoctorDetails().get(i).getSrt_date());
				blk_hcp_req_doctors.setSrt_remarks(doctorBulkUploadBean.getDoctorDetails().get(i).getSrt_remarks());
				blk_hcp_req_doctors.setCompany(doctorBulkUploadBean.getCompany());
				blk_hcp_req_doctors.setHcp_unique_id(doctorBulkUploadBean.getDoctorDetails().get(i).getHcp_unique_id());
				// have to genearate from max number
				blk_hcp_req_doctors.setServ_req_no(doctorBulkUploadBean.getDoctorDetails().get(i).getSrt_number());
				blk_hcp_req_doctors.setRequestor_id(doctorBulkUploadBean.getDoctorDetails().get(i).getReq_id());
				blk_hcp_req_doctors.setDoc_ins_usr_id(doctorBulkUploadBean.getEmpId());
				blk_hcp_req_doctors.setDoc_mod_usr_id("");
				blk_hcp_req_doctors.setDoc_ins_dt(new Date());
				blk_hcp_req_doctors.setDoc_mod_dt(new Date());
				blk_hcp_req_doctors.setDoc_ins_ip_add(doctorBulkUploadBean.getBlk_ip_add());
				blk_hcp_req_doctors.setDoc_mod_ip_add("");
				blk_hcp_req_doctors.setDoc_status(doctorBulkUploadBean.getDoctorDetails().get(i).getDoc_status());
				blk_hcp_req_doctors.setMcl_no(doctorBulkUploadBean.getDoctorDetails().get(i).getMcl_no()); 
				blk_hcp_req_doctors.setBlk_req_employee_no(doctorBulkUploadBean.getDoctorDetails().get(i).getFstaff_employee_no());
				transactionalRepository.persist(blk_hcp_req_doctors);
				
				map.put("reqNo", blk_hcp_req_hdr.getBlk_hcp_req_no());
				map.put("reqId", blk_hcp_req_hdr.getBlk_hcp_req_id());
			}

		} catch (Exception e) {
			throw e;
		}
		return map;

	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Blk_hcp_req_hdr saveblk_hcp_req_hdr(DoctorBulkUploadBean doctorBulkUploadBean) throws Exception {

		FinYear fin = financialyearservice.getCurrentFinancialYear(doctorBulkUploadBean.getCompany());
		Blk_hcp_req_hdr blk_hcp_req_hdr = null;
		blk_hcp_req_hdr = new Blk_hcp_req_hdr();
		try {
			String num = (doctorBulkUploadBean.getDivnm().substring(0,3))+
					"-"+String.format("%02d", (new Date().getMonth()+1))+
					(fin.getFin_year().substring(2,4));

			blk_hcp_req_hdr.setBlk_hcp_req_date(new Date());
			blk_hcp_req_hdr.setBlk_hcp_req_no(num);
			blk_hcp_req_hdr.setFin_year_id(fin.getFin_year());
			blk_hcp_req_hdr.setCompany(doctorBulkUploadBean.getCompany());
			blk_hcp_req_hdr.setDiv_id(doctorBulkUploadBean.getDivId());
			blk_hcp_req_hdr.setNo_of_doctors(doctorBulkUploadBean.getNoOfDoctors()==null?0l:doctorBulkUploadBean.getNoOfDoctors());
			blk_hcp_req_hdr.setBlk_ins_usr_id(doctorBulkUploadBean.getEmpId());
			blk_hcp_req_hdr.setBlk_ins_dt(new Date());
			blk_hcp_req_hdr.setBlk_mod_dt(new Date());
			blk_hcp_req_hdr.setBlk_ins_ip_add(doctorBulkUploadBean.getBlk_ip_add());
			blk_hcp_req_hdr.setBlk_mod_ip_add("");
			blk_hcp_req_hdr.setBlk_status(doctorBulkUploadBean.getBlk_status());
			blk_hcp_req_hdr.setBlk_csv_name("");
			blk_hcp_req_hdr.setBlk_csv_gen_date(new Date());
			blk_hcp_req_hdr.setBlk_csvgen_usr_id("");
			blk_hcp_req_hdr.setBlk_appr_ind("E");
			blk_hcp_req_hdr.setDoc_mst_type(doctorBulkUploadBean.getDocMasterToBeUsed());
			blk_hcp_req_hdr.setFile_download("N");
			blk_hcp_req_hdr.setFile_upload("N");
			blk_hcp_req_hdr.setBlk_confirm_ind("N");
			blk_hcp_req_hdr.setDivision(doctorBulkUploadBean.getDivnm());
			transactionalRepository.persist(blk_hcp_req_hdr);
			
			blk_hcp_req_hdr.setBlk_hcp_req_no(blk_hcp_req_hdr.getBlk_hcp_req_no()+"-"+
					String.format("%05d", blk_hcp_req_hdr.getBlk_hcp_req_id()));
			transactionalRepository.update(blk_hcp_req_hdr);
		} catch (Exception e) {
			throw e;
		}
		return blk_hcp_req_hdr;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String,Object> saveBulkDoctorproducts(DoctorBulkUploadBean doctorBulkUploadBean) throws Exception {
		// TODO Auto-generated method stub
		Blk_hcp_req_products blk_hcp_req_products = null;
		Blk_hcp_req_hdr blk_hcp_req_hdr = null;
		Map<String,Object> map=new HashedMap<>();
		try {
			FinYear fin =financialyearservice.getCurrentFinancialYear(doctorBulkUploadBean.getCompany());
			doctorBulkUploadBean.setBlk_csv_gen_date(null);
			doctorBulkUploadBean.setBlk_csvgen_usr_id("");
			doctorBulkUploadBean.setBlk_status("N");
			
			//doctorBulkUploadBean.setBlk_mod_ip_add("");
			if(doctorBulkUploadBean.getTransactionMode().trim().equals("M")) {
				blk_hcp_req_hdr = doctorbulkallocuploadrepository.getObjById(doctorBulkUploadBean.getReqIdForModify());
			}
			else {
				blk_hcp_req_hdr =this.saveblk_hcp_req_hdr(doctorBulkUploadBean);
			}

			// insert Blk_hcp_req_products here
			for (int i = 0; i < doctorBulkUploadBean.getProdqtyModel().size(); i++) {
				blk_hcp_req_products = new Blk_hcp_req_products();
				blk_hcp_req_products.setBlk_hcp_req_id(blk_hcp_req_hdr.getBlk_hcp_req_id());
				blk_hcp_req_products.setBlk_hcp_req_date(new Date());
				blk_hcp_req_products.setBlk_hcp_req_no(blk_hcp_req_hdr.getBlk_hcp_req_no());
				blk_hcp_req_products.setFin_year_id(fin.getFin_year());
				blk_hcp_req_products.setCompany(doctorBulkUploadBean.getCompany());
				blk_hcp_req_products.setProd_id(doctorBulkUploadBean.getProdqtyModel().get(i).getProdId());
				blk_hcp_req_products.setProd_name(doctorBulkUploadBean.getProdqtyModel().get(i).getProdname());
				blk_hcp_req_products.setStd_qty(new BigDecimal(doctorBulkUploadBean.getProdqtyModel().get(i).getQty()));
				blk_hcp_req_products.setPrd_ins_usr_id(doctorBulkUploadBean.getEmpId());
				blk_hcp_req_products.setPrd_mod_usr_id(null);
				blk_hcp_req_products.setPrd_ins_dt(new Date());
				blk_hcp_req_products.setPrd_mod_dt(null);
				blk_hcp_req_products.setPrd_ins_ip_add(doctorBulkUploadBean.getBlk_ip_add());
				blk_hcp_req_products.setPrd_mod_ip_add(null);
				blk_hcp_req_products.setPrd_status("Y");
				blk_hcp_req_products.setSmp_prod_cd(doctorBulkUploadBean.getProdqtyModel().get(i).getProdcode());
				transactionalRepository.persist(blk_hcp_req_products);
			}
			map.put("reqNo", blk_hcp_req_hdr.getBlk_hcp_req_no());
			map.put("reqId", blk_hcp_req_hdr.getBlk_hcp_req_id());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return map;
	}

	@Override
	public Map<String, Object> getListOfHcpsFromFileOrStructuralErrors(String divId, String masterType,
			MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		BufferedReader br = null;
		boolean rowHasError = false;
		boolean fileHasErrors = false;
		boolean fileHasWarnings = false;
		List<BulkUpldBean> linkage = new ArrayList<BulkUpldBean>();
		int rowNum = 0;
		String filename = "";
		BulkUpldBean blkUpldBn = null;
		List<HcpBulkUploadErrorBean> errList = new ArrayList<HcpBulkUploadErrorBean>();
		String[] headers = { "HCP Unique Id", "HCP Name", "Address Change?", "MCL No", "Req Emp No", "SRT No",
				"SRT Date", "SRT Remark" };
		try {
			HcpBulkUploadErrorBean newErrList = null;
			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
			file.transferTo(convFile);
			br = new BufferedReader(new FileReader(convFile));
			String[] header = br.readLine().split(",");
			// start reading file and based on indicator give errors
			String record = "";
			List<Object[]> Hcplist;
			List<Object[]> fslist;
			List<BulkUpldBean> linklist;

			while ((record = br.readLine()) != null) {
				rowNum++;
				newErrList = new HcpBulkUploadErrorBean();
				String[] arr = record.split(",");

				newErrList.setRowNum(rowNum);
				List<String> errListEach = new ArrayList<String>();
				List<String> warnListEach = new ArrayList<String>();
//				errListEach.add("");
//				warnListEach.add("");

				System.out.println("rowNum para " + arr[0] + " " + arr[4]);

				if (masterType.equals("N")) {

					for (int i = 1; i < arr.length; i++) {
						if (arr[i] == null || arr[i].isEmpty()) {
							errListEach.add(headers[i] + " is missing");
							fileHasErrors = true;
						}
					}

					if (arr[4] != null && (arr[4].trim().length() != 0) && !arr[4].isEmpty()) {
						// do further validation
						fslist = doctorbulkallocuploadrepository.checkFstaffByEmplNo(arr[4]);
						if (fslist != null && fslist.size() > 1) {
							// query with division check
							fslist = doctorbulkallocuploadrepository.checkFstaffByEmplNoAndDivCheck(arr[4], divId,masterType);
						}
						if (fslist.size() != 0 && !fslist.isEmpty()) {
							if (!fslist.get(0)[51].toString().equals("A")) {
								fileHasErrors = true;
								errListEach.add("Requestor is Deactivated ");
							}
							if (!fslist.get(0)[2].toString().equals(divId)) {
								fileHasErrors = true;
								errListEach.add("Requestor is not attached to selected division");
							}
							if (!fileHasErrors) {
								// construct bean//add to bean
								blkUpldBn = new BulkUpldBean();
								blkUpldBn.setHcp_unique_id("");
								blkUpldBn.setMcl_no(arr[3]);
								blkUpldBn.setDoc_nm(arr[1]);
								blkUpldBn.setFstaff_disp_name(fslist.get(0)[11].toString());
								blkUpldBn.setFstaff_code(arr[4]);
								blkUpldBn.setFstaff_id(Long.valueOf(fslist.get(0)[0].toString()));
								blkUpldBn.setAllowAddrChange(arr[3].toString().trim());
								blkUpldBn.setSrtNo(arr[5]);
								blkUpldBn.setSrtDate(arr[6]);
								blkUpldBn.setSrtRemark(arr[7]);
								blkUpldBn.setFstaff_employee_no(arr[4].toString());
								linkage.add(blkUpldBn);
							} else {
								linkage.clear();
							}

						} else {
							fileHasErrors = true;
							rowHasError = true;
							errListEach.add("Requestor does not exist");
						}
					}
				} else {
					for (int i = 1; i < arr.length; i++) {
						if (arr[i] == null || arr[i].isEmpty()) {
							errListEach.add(headers[i] + " is missing");
							rowHasError = true;
							fileHasErrors = true;
						}
					}

					System.out.println("else " + masterType);

					if (arr[0] != null && (arr[0].trim().length() != 0) && !arr[0].isEmpty()) {
						Hcplist = doctorbulkallocuploadrepository.checkUniqueHcpid(arr[0]);
						if (Hcplist.size() != 0 && !Hcplist.isEmpty()) {
							if (!Hcplist.get(0)[37].toString().equals("TRUE")) {
								fileHasWarnings = true;
								warnListEach.add("HCP is not Active");
							}

						} else {
							fileHasErrors = true;
							rowHasError = true;
							errListEach.add("HCP does not exist");
						}
					}

					if (arr[4] != null && (arr[4].trim().length() != 0) && !arr[4].isEmpty()) {
						fslist = doctorbulkallocuploadrepository.checkFstaffByEmplNo(arr[4]);
						if (fslist != null && fslist.size() > 1) {
							// query with division check
							fslist = doctorbulkallocuploadrepository.checkFstaffByEmplNoAndDivCheck(arr[4], divId,masterType);
						}
						if (fslist.size() != 0 && !fslist.isEmpty()) {
							if (!fslist.get(0)[51].toString().equals("A")) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add("Requestor is Deactivated ");
							}
							if (!fslist.get(0)[2].toString().equals(divId)) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add("Requestor is not attached to selected division");
							}
						} else {
							fileHasErrors = true;
							rowHasError = true;
							errListEach.add("Requestor does not exist");
						}
					}

					linklist = doctorbulkallocuploadrepository.checklinkHcpandFstaff(arr[0], arr[4], divId);
					if (linklist.size() == 0 && linklist.isEmpty()) {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("HCP is not attached to selected requestor or this link is deactivated");
					} else {
						if (!fileHasErrors) {
							linklist.get(0).setSrtNo(arr[5]);
							linklist.get(0).setSrtDate(arr[6]);
							linklist.get(0).setSrtRemark(arr[7]);
							linklist.get(0).setAllowAddrChange(arr[2].toString().trim());
							linkage.add(linklist.get(0));
						} else
							linkage.clear();
					}

				}
				newErrList.setErrorList(errListEach);
				newErrList.setWarning(warnListEach);

				if (rowHasError) {
					errList.add(newErrList);
				}
			}
			System.out.println("errList " + errList.size());

			if (fileHasErrors || fileHasWarnings) {
				System.out.println("fileHasErrors " + errList.size());
				filename = this.bulkUploadErrorFile(errList);

			}

			map.put("linkage", linkage);
			map.put("filename", filename);
			map.put("fileHasErrors", fileHasErrors);
			map.put("fileHasWarnings", fileHasWarnings);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private String bulkUploadErrorFile(List<HcpBulkUploadErrorBean> errList) throws Exception {
		String filename = "bulkUploadErrorFile" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		try {

			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			XSSFSheet sheet = workbook.createSheet("bulk-Upload-Error-File");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("RowNum");
			colCount++;
			cell = row.createCell(colCount);
			cell.setCellValue("Warning");
			colCount++;
			cell = row.createCell(colCount);
			cell.setCellValue("Error List");
			colCount++;

			for (HcpBulkUploadErrorBean list : errList) {

				rowCount += 1;
				colCount = 0;

				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				cell.setCellValue(list.getRowNum());
				colCount++;

				if (list.getWarning().size() > 0) {
					for (int i = 0; i < list.getWarning().size(); i++) {
						cell = row.createCell(colCount);
						cell.setCellValue(list.getWarning().get(i));
						colCount++;
					}
				} else {
					cell = row.createCell(colCount);
					cell.setCellValue("");
					colCount++;
				}
				for (int i = 0; i < list.getErrorList().size(); i++) {
					cell = row.createCell(colCount);
					cell.setCellValue(list.getErrorList().get(i));
					colCount++;
				}

			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
			System.out.println("filepath " + filename);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	
	@Override
	public List<Object[]> getHdrAndDocListForCsvGeneration(Long reqId) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getHdrAndDocListForCsvGeneration(reqId);
	}

	@Override
	public List<Object[]> getProdListForCsvGeneration(Long reqId) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getProdListForCsvGeneration(reqId);
	}

	@Override
	public String getCsvForBulkUpload(List<Object[]> hdrAndDocList, List<Object[]> prodList) throws Exception {
		CSVWriter writer = null;
		File ff = null;
		StringBuffer path = null;
		String fileName = "";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fileName = "DocBulkUpload" + new Date().getTime() + ".csv";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			System.out.println("filename " + fileName);
			File file = new File(filePath);
			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			FileWriter fwriter = new FileWriter(path.toString());
			writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = null;
			header = new String[31 + prodList.size()];
			header[0] = "BULK_UNIQUE_ID";
			header[1] = "REQUESTOR_ID";
			header[2] = "Address_Change";
			header[3] = "HCP Unique ID";
			header[4] = "HCP_Name";
			header[5] = "MCL_NUMBER";
			header[6] = "SRT Numnber";
			header[7] = "SRT Date";
			header[8] = "SRT Remarks";
			header[9] = "REQ_REMARKS";
			header[10] = "COMPANY";
			header[11] = "DIVISION";
			header[12] = "REQUEST_NO";
			header[13] = "Request Date";
			header[14] = "FS_Code";
			header[15] = "FS_Name";
			header[16] = "Observer1 Email";
			header[17] = "Observer2 Email";
			header[18] = "Observer3 Email";
			header[19] = "Observer4 Email";
			header[20] = "Observer5 Email";
			header[21] = "TERRITORY_ID";
			header[22] = "TERRITORY Name	";
			header[23] = "Address 1";
			header[24] = "Address 2";
			header[25] = "Address 3";
			header[26] = "Address 4";
			header[27] = "City";
			header[28] = "PIN code";
			header[29] = "Mobile";
			header[30] = "HCP Email";
			for (int i = 1; i <= prodList.size(); i++) {
				header[30 + i] = prodList.get(i - 1)[1].toString() + " - " + prodList.get(i - 1)[0].toString();
			}

			List<String[]> li = new ArrayList<String[]>();
			li.add(header);
			for (Object[] objArr : hdrAndDocList) {
				String[] csvarr = new String[30 + prodList.size() + 1];
				csvarr[0] = objArr[0] == null ? "" : objArr[0].toString();
				csvarr[1] = objArr[1] == null ? "" : objArr[1].toString();
				csvarr[2] = "N";// to be changed
				csvarr[3] = objArr[2] == null ? "" : objArr[2].toString();
				csvarr[4] = objArr[3] == null ? "" : objArr[3].toString();
				csvarr[5] = objArr[4] == null ? "" : objArr[4].toString();
				csvarr[6] = objArr[5] == null ? "" : objArr[5].toString();
				csvarr[7] = objArr[6] == null ? "" : objArr[6].toString();
				csvarr[8] = objArr[7] == null ? "" : objArr[7].toString();
				csvarr[9] = objArr[8] == null ? "" : objArr[8].toString();
				csvarr[10] = objArr[9] == null ? "" : objArr[9].toString();
				csvarr[11] = objArr[10] == null ? "" : objArr[10].toString();
				csvarr[12] = objArr[11] == null ? "" : objArr[11].toString();
				csvarr[13] = objArr[12] == null ? "" : df.format((Date) objArr[12]).toString();
				csvarr[14] = objArr[13] == null ? "" : objArr[13].toString();
				csvarr[15] = objArr[14] == null ? "" : objArr[14].toString();
				csvarr[16] = objArr[15] == null ? "" : objArr[15].toString();
				csvarr[17] = objArr[16] == null ? "" : objArr[16].toString();
				csvarr[18] = objArr[17] == null ? "" : objArr[17].toString();
				csvarr[19] = objArr[18] == null ? "" : objArr[18].toString();
				csvarr[20] = objArr[19] == null ? "" : objArr[19].toString();
				csvarr[21] = objArr[20] == null ? "" : objArr[20].toString();
				csvarr[22] = objArr[21] == null ? "" : objArr[21].toString();
				csvarr[23] = objArr[22] == null ? "" : objArr[22].toString().replaceAll(",", ";");
				csvarr[24] = objArr[23] == null ? "" : objArr[23].toString().replaceAll(",", ";");
				csvarr[25] = objArr[24] == null ? "" : objArr[24].toString().replaceAll(",", ";");
				csvarr[26] = objArr[25] == null ? "" : objArr[25].toString().replaceAll(",", ";");
				csvarr[27] = objArr[26] == null ? "" : objArr[26].toString();
				csvarr[28] = objArr[27] == null ? "" : objArr[27].toString();
				csvarr[29] = objArr[28] == null ? "" : objArr[28].toString();
				csvarr[30] = objArr[29] == null ? "" : objArr[29].toString();
				for (int i = 1; i <= prodList.size(); i++) {
					csvarr[30 + i] = "0";
				}
				li.add(csvarr);
			}
			writer.writeAll(li);

			File csvfile = new File(path.toString());
			FileInputStream fileInputStream = new FileInputStream(csvfile);
			writer.close();
		} catch (Exception e) {
			throw e;
		}

		return fileName;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getExcelForBulkUpload(List<Object[]> hdrAndDocList, List<Object[]> prodList, Long bulkReqId,
			String empId) throws Exception {
		StringBuffer path = null;
		String fileName = "";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		XSSFWorkbook workbook = null;
		try {
			fileName = "DocBulkUpload" + new Date().getTime() + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			System.out.println("filename " + fileName);
			File file = new File(filePath);
			workbook = new XSSFWorkbook();
			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			XSSFSheet sheet = workbook.createSheet(fileName);

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String[] header = null;
			header = new String[36 + prodList.size()];
			header[0] = "BULK_UNIQUE_ID";
			header[1] = "REQUESTOR_ID";
			header[2] = "SG_EMPLOYEE_NO (25)";
			header[3] = "Address_Change (1)";
			header[4] = "HCP Unique ID (20)";
			header[5] = "HCP_Name (50)";
			header[6] = "MCL_NUMBER (20)";
			header[7] = "SRT Number (8)";
			header[8] = "SRT Date (dd/mm/yyyy)";
			header[9] = "SRT Remarks (30)";
			header[10] = "REQ_REMARKS (30)";
			header[11] = "COMPANY";
			header[12] = "DIVISION";
			header[13] = "REQUEST_NO";
			header[14] = "Request Date (dd/mm/yyyy)";
			header[15] = "FS_Code";
			header[16] = "FS_Name";
			header[17] = "Observer1 Email (60)";
			header[18] = "Observer2 Email (60)";
			header[19] = "Observer3 Email (60)";
			header[20] = "Observer4 Email (60)";
			header[21] = "Observer5 Email (60)";
			header[22] = "SG TERRITORY Code";
			header[23] = "TERRITORY Name";
			header[24] = "Address 1 (45)";
			header[25] = "Address 2 (45)";
			header[26] = "Address 3 (45)";
			header[27] = "Address 4 (45)";
			header[28] = "City (45)";
			header[29] = "PIN code (10)";
			header[30] = "HCP Mobile (10)";
			header[31] = "HCP Email (60)";
			header[32] = "MDM_EMPLOYEE_NO (7)";
			header[33] = "SG_TERR_CODE";
			header[34] = "MDM_TERR_CODE";
			header[35] = "MDM_TERR_NAME";
			for (int i = 1; i <= prodList.size(); i++) {
				header[35 + i] = prodList.get(i - 1)[1].toString() + " - " + prodList.get(i - 1)[2].toString();
			}

			rowCount += 0;
			row = sheet.createRow(rowCount);

			for (String heading : header) {
				cell = row.createCell(colCount);
				cell.setCellValue(heading);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;
			int count = 1;
			
			XSSFCell prodQtyCell = null;

			for (Object[] objArr : hdrAndDocList) {
				colCount = 0;

				row = sheet.createRow(rowCount);

				cell = ReportFormats.cell(row, colCount, objArr[0] == null ? "" : objArr[0].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[1] == null ? "" : objArr[1].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[32] == null ? "" : objArr[32].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[33] == null ? "" : objArr[33].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[2] == null ? "" : objArr[2].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[3] == null ? "" : objArr[3].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[4] == null ? "" : objArr[4].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[5] == null ? "" : objArr[5].toString(), null);
				colCount++;

				cell= row.createCell(colCount);
				cell.setCellValue(df.format(sdf1.parse(objArr[6].toString())));
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[7] == null ? "" : objArr[7].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[8] == null ? "" : objArr[8].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[9] == null ? "" : objArr[9].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[10] == null ? "" : objArr[10].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[11] == null ? "" : objArr[11].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						objArr[12] == null ? "" : df.format((Date) objArr[12]).toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[13] == null ? "" : objArr[13].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[14] == null ? "" : objArr[14].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[15] == null ? "" : objArr[15].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[16] == null ? "" : objArr[16].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[17] == null ? "" : objArr[17].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[18] == null ? "" : objArr[18].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[19] == null ? "" : objArr[19].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[31] == null ? "" : objArr[31].toString().trim(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						objArr[21] == null ? "" : objArr[21].toString().replaceAll(",", ";"), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						objArr[22] == null ? "" : objArr[22].toString().replaceAll(",", ";"), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						objArr[23] == null ? "" : objArr[23].toString().replaceAll(",", ";"), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						objArr[24] == null ? "" : objArr[24].toString().replaceAll(",", ";"), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[25] == null ? "" : objArr[25].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[26] == null ? "" : objArr[26].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[27] == null ? "" : objArr[27].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[28] == null ? "" : objArr[28].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[29] == null ? "" : objArr[29].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[34] == null ? "" : objArr[34].toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[31] == null ? "" : objArr[31].toString().trim(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, objArr[30] == null ? "" : objArr[30].toString(), null);
				colCount++;
//				.substring(0,8)
				cell = ReportFormats.cell(row, colCount, objArr[36] == null ? "" : objArr[36].toString().substring(9,objArr[36].toString().length()), null);
				colCount++;
				

				
				for (int i = 0; i < prodList.size(); i++) {
					cell = row.createCell(colCount);
					cell.setCellValue(Long.valueOf((prodList.get(i)[3]).toString()));
					colCount++;
				}

				rowCount++;
				count++;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
			workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();
			Blk_hcp_req_hdr blk_hdr = this.entityManager.find(Blk_hcp_req_hdr.class, bulkReqId);
			if (blk_hdr != null) {
				blk_hdr.setBlk_csvgen_usr_id(empId);
				blk_hdr.setBlk_csv_gen_date(new Date());
				blk_hdr.setBlk_csv_name(fileName);
				blk_hdr.setFile_download("Y");
				transactionalRepository.update(blk_hdr);
			}

		} catch (Exception e) {
			throw e;
		}

		return fileName;
	}

	@Override
	public Blk_hcp_req_hdr getBlkHcpReqHdrById(Long hdrId) throws Exception {
		return doctorbulkallocuploadrepository.getBlkHcpReqHdrById(hdrId);
	}

	@SuppressWarnings("deprecation")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveFileToBulkTemp(MultipartFile file, String username) throws Exception {
		Blk_hcp_req_hdr hdrRecord = null;
		Workbook work = null;
		SimpleDateFormat ddMYparser = new SimpleDateFormat("dd/mm/yyyy");
		SimpleDateFormat Y_M_D_parser = new SimpleDateFormat("yyyy-MM-dd");
		String prodname = null;
		String prodcode = null;
		Long prodId = null;
		String[] prodnamearr = null;
		long[] productIds = new long[20];
		Arrays.fill(productIds, 0l);
		int no_of_products = 0;
		TerrMaster tm = null;
		List<Object[]> fslist;
		String ph_no = null;
		String[] split_date = new String[3];
		
		Path copyfrom = null;
		Path target = null;
		
		try {
		//	File xlsxFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
			File xlsxFile = new File("D:\\sg\\tempDocFile" + "\\" + file.getOriginalFilename());
			//file.transferTo(xlsxFile);
					
			work = WorkbookFactory.create(xlsxFile);
			XSSFSheet sheet = (XSSFSheet) work.getSheetAt(0);
			int no_of_rows = sheet.getPhysicalNumberOfRows();

			int index = 0;
			XSSFRow headerRow = sheet.getRow(index);
			XSSFRow row = null;

			index = 1;
			List<Blk_hcq_req_temp> blkTempList = new ArrayList<Blk_hcq_req_temp>();
			blkTempList = this.getBulkTempByBlkHdrId(Double.valueOf(sheet.getRow(1).getCell(0).toString()).longValue());
			if (blkTempList.size() == 0) {
				if (no_of_rows > 1) {
					// find all the products from header row and get their ids
					for (int i = 1; i <= 20; i++) {
						if (headerRow.getCell(35 + i) != null) {
							prodname = headerRow.getCell(35 + i).toString();
							System.out.println("prodname:::" + prodname);
							if (prodname != null && (prodname.trim().length() != 0) && !prodname.isEmpty()) {
								no_of_products++;
								prodnamearr = prodname.split("-");
								prodcode = prodnamearr[prodnamearr.length - 1].trim();
								// function to get product id by prod code
								prodId = this.productMasterService.getProductIdBySmpProdCode(prodcode).getSmp_prod_id();
								productIds[i - 1] = prodId;
							}
						}
					}
					if (no_of_products > 0 && productIds[0] != 0l) {

						hdrRecord = this
								.getBlkHcpReqHdrById(Double.valueOf(sheet.getRow(1).getCell(0).toString()).longValue());

						if (hdrRecord != null) {
							while (sheet.getRow(index) != null) {
								row = sheet.getRow(index);
								System.out.println("index::::::" + index);
								Blk_hcq_req_temp tempTableRow = new Blk_hcq_req_temp();

								tempTableRow.setBlk_hcp_req_id(hdrRecord.getBlk_hcp_req_id());
								split_date = row.getCell(14).toString().split("/");
								tempTableRow.setBlk_hcp_req_date(new Date(Integer.valueOf(split_date[2]) - 1900,
										Integer.valueOf(split_date[1]) - 1,Integer.valueOf(split_date[0])));
								tempTableRow.setBlk_req_employee_no(row.getCell(2).toString().trim());
								tempTableRow.setBlk_csv_name(file.getOriginalFilename());// change this to later
																							// accomodate
																							// generated file
								tempTableRow.setBlk_hcp_req_no(row.getCell(13).toString().trim());
								tempTableRow.setBlk_upl_date(new Date());
								tempTableRow.setBlk_upl_user(username);
								// uncomment later on
								
									
								tempTableRow.setHcp_unique_id(row.getCell(4).toString());
								tempTableRow.setHcp_id_old("0");// check with sir
								tempTableRow.setAlloc_req_hdr_id(0L);// default 0 then once alloc req hdr is made update
																		// this
								tempTableRow.setMcl_number(row.getCell(6) == null ? "" : row.getCell(6).toString());
								tempTableRow.setHcp_name(row.getCell(5).toString());
								tempTableRow.setMdm_address_chg(row.getCell(3).toString().trim());
								tempTableRow.setSrt_number(row.getCell(7).toString());
								
								split_date = row.getCell(8).toString().split("/");
								tempTableRow.setSrt_date(new Date(Integer.valueOf(split_date[2]) - 1900,
										Integer.valueOf(split_date[1]) - 1,Integer.valueOf(split_date[0])));
								
								tempTableRow.setSrt_remarks(row.getCell(9) == null ? " " : row.getCell(9).toString());
								tempTableRow.setCompany(row.getCell(11).toString().trim());
								tempTableRow.setBlk_req_mdm_employee_no(row.getCell(32).toString());
								Long divId = divMasterService.getDivIdByName(row.getCell(12).toString());
								tempTableRow.setDiv_id(divId);// get divid by division code//ask sir to add division
																// code
								
								if(hdrRecord.getDoc_mst_type().trim().equals("M")) {
									tempTableRow
									.setRequestor_id(Double.valueOf(row.getCell(15).toString().trim()).longValue());
									tempTableRow.setFs_id(Double.valueOf(row.getCell(15).toString()).longValue());
								}
									else {
										fslist = doctorbulkallocuploadrepository.checkFstaffByEmployeeNoAndTerritoryCode(
												row.getCell(2).toString(), row.getCell(33).toString(),hdrRecord.getDoc_mst_type());
										if (fslist != null && fslist.size() > 1) {
											// query with division check
											fslist = doctorbulkallocuploadrepository
													.checkFstaffByEmplNoAndDivCheck(row.getCell(2).toString(), divId.toString(),hdrRecord.getDoc_mst_type().trim());
										}
										tempTableRow.setRequestor_id(Long.valueOf(fslist.get(0)[0].toString()));
										tempTableRow.setFs_id(Long.valueOf(fslist.get(0)[0].toString()));
									}
								tempTableRow.setRequest_no(row.getCell(13).toString().trim());// check
								
								split_date = row.getCell(14).toString().split("/");
								tempTableRow.setRequest_date(new Date(Integer.valueOf(split_date[2]) - 1900,
										Integer.valueOf(split_date[1]) - 1,Integer.valueOf(split_date[0])));
								
								tempTableRow.setFs_code(row.getCell(15).toString());

								
								tempTableRow.setEmail1(row.getCell(17).toString());
								tempTableRow.setEmail2(row.getCell(18).toString());
								tempTableRow.setEmail3(row.getCell(19).toString());
						
								tempTableRow.setEmail4(row.getCell(20).toString());
								tempTableRow.setEmail5(row.getCell(21).toString());
								
								tm = this.terrService.getTerrDetailByTerrCode(row.getCell(22).toString(), divId);
								tempTableRow.setTerritory_id(tm.getTerr_id());
								tempTableRow.setHcp_address_1(
										row.getCell(24).toString().length()>45?
												row.getCell(24).toString().substring(0, 44) : row.getCell(24).toString());
								tempTableRow.setHcp_address_2(
										row.getCell(25).toString().length()>45?
												row.getCell(25).toString().substring(0, 44) : row.getCell(25).toString());
								tempTableRow.setHcp_address_3(
										row.getCell(26).toString().length()>45?
												row.getCell(26).toString().substring(0, 44) : row.getCell(26).toString());
								tempTableRow.setHcp_address_4(
										row.getCell(27).toString().length()>45?
												row.getCell(27).toString().substring(0, 44) : row.getCell(27).toString());
								
								tempTableRow.setHcp_city(row.getCell(28).toString());
								tempTableRow.setHcp_pin_code(row.getCell(29).toString().contains(".0")?row.getCell(29).toString().replaceAll("\\.\\d+$", ""):row.getCell(29).toString());
								
								ph_no = row.getCell(30).toString();
								if(ph_no.toUpperCase().contains("E") && (ph_no.charAt(1)=='.' || ph_no.charAt(2)=='.')) {
									//scientific no 
									ph_no = String.format("%.0f", Double.parseDouble(ph_no));
								}
								
								tempTableRow.setHcp_mobile(ph_no);
								tempTableRow.setHcp_email(row.getCell(31).toString());
								tempTableRow.setTerr_code(row.getCell(33).toString());
								// now write for products//all 20
								tempTableRow.setProd_id1(productIds[0]);
								tempTableRow.setProd_id2(productIds[1]);
								tempTableRow.setProd_id3(productIds[2]);
								tempTableRow.setProd_id4(productIds[3]);
								tempTableRow.setProd_id5(productIds[4]);
								tempTableRow.setProd_id6(productIds[5]);
								tempTableRow.setProd_id7(productIds[6]);
								tempTableRow.setProd_id8(productIds[7]);
								tempTableRow.setProd_id9(productIds[8]);
								tempTableRow.setProd_id10(productIds[9]);
								tempTableRow.setProd_id11(productIds[10]);
								tempTableRow.setProd_id12(productIds[11]);
								tempTableRow.setProd_id13(productIds[12]);
								tempTableRow.setProd_id14(productIds[13]);
								tempTableRow.setProd_id15(productIds[14]);
								tempTableRow.setProd_id16(productIds[15]);
								tempTableRow.setProd_id17(productIds[16]);
								tempTableRow.setProd_id18(productIds[17]);
								tempTableRow.setProd_id19(productIds[18]);
								tempTableRow.setProd_id20(productIds[19]);
								int prodInd = 36;
								tempTableRow.setProd1_qty(
										BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								prodInd++;
								if (productIds[1] != 0l) {
									tempTableRow.setProd2_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd2_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[2] != 0l) {
									tempTableRow.setProd3_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd3_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[3] != 0l) {
									tempTableRow.setProd4_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd4_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[4] != 0l) {
									tempTableRow.setProd5_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd5_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[5] != 0l) {
									tempTableRow.setProd6_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd6_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[6] != 0l) {
									tempTableRow.setProd7_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd7_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[7] != 0l) {
									tempTableRow.setProd8_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd8_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[8] != 0l) {
									tempTableRow.setProd9_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd9_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[9] != 0l) {
									tempTableRow.setProd10_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd10_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[10] != 0l) {
									tempTableRow.setProd11_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd11_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[11] != 0l) {
									tempTableRow.setProd12_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd12_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[12] != 0l) {
									tempTableRow.setProd13_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd13_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[13] != 0l) {
									tempTableRow.setProd14_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd14_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[14] != 0l) {
									tempTableRow.setProd15_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd15_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[15] != 0l) {
									tempTableRow.setProd16_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd16_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[16] != 0l) {
									tempTableRow.setProd17_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd17_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[17] != 0l) {
									tempTableRow.setProd18_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd18_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[18] != 0l) {
									tempTableRow.setProd19_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd19_qty(BigDecimal.ZERO);
								}
								prodInd++;
								if (productIds[19] != 0l) {
									tempTableRow.setProd20_qty(
											BigDecimal.valueOf(Double.valueOf(row.getCell(prodInd).toString())));
								} else {
									tempTableRow.setProd20_qty(BigDecimal.ZERO);
								}
								prodInd++;
								System.out.println(tempTableRow.toString());
								this.transactionalRepository.persist(tempTableRow);

								index++;
							}
							// update uploaded indicator
							hdrRecord.setFile_upload("Y");
							this.transactionalRepository.persist(hdrRecord);

						} else {
							throw new MedicoException("Invalid Data Found in File");
						}
					} else {
						throw new MedicoException("Invalid Products Entered");
					}
				} else {
					throw new MedicoException("Given file has no readable rows");
				}
			} else {
				throw new MedicoException("Duplicate File . This transaction is already processed !!");
			}
			
		//	File excelFile = new File(MedicoConstants.BULK_UPLOADED_FILES + "/" + file.getOriginalFilename());
		//	file.transferTo(excelFile);
			
			copyfrom = FileSystems.getDefault().getPath(xlsxFile.getAbsolutePath());
			target = FileSystems.getDefault().getPath(MedicoConstants.BULK_UPLOADED_FILES + "/"  + file.getOriginalFilename());
			Files.copy(copyfrom, target, StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {
			throw e;
		} finally {
			if (work != null)
				work.close();
		}

	}

	@Override
	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdr(boolean isgen) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getRequestListFromBlkHdr(isgen);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void pushBulkRecordsToAllocTables(Long bulkReqId, String finYear, String ipaddress,String periodCode) throws Exception {
		SimpleDateFormat dd_MM_YYYY = new SimpleDateFormat("dd-mm-yyyyy");
		List<String> mailList = new ArrayList<String>();
		try {
			// change this query to get list of values
			List<Blk_hcq_req_temp> blkTempList = this.doctorbulkallocuploadrepository
					.getBlkTempRecordsByBlkHdrId(bulkReqId);
			
			
			AllocReqHeader header = null;
			AllocReqDet alloc_details = null;
			SpecialAllocationBean prodBean = null;
			
			if (blkTempList != null && !blkTempList.isEmpty()) {
				for (Blk_hcq_req_temp tempRecords : blkTempList) {
					header = this.setAllocReqHdrFromBlkTempRecordsAndSave(tempRecords, finYear, ipaddress,periodCode);
					
					if (header != null) {
						String compCode = tempRecords.getCompany();
						Long divId = tempRecords.getDiv_id();
						
						
						if(tempRecords.getProd1_qty().compareTo(BigDecimal.ZERO) > 0)
						this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id1(),tempRecords.getProd1_qty());

						if (tempRecords.getProd2_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id2(),tempRecords.getProd2_qty());
						}
						if (tempRecords.getProd3_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id3(),tempRecords.getProd3_qty());
						}
						if (tempRecords.getProd4_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id4(),tempRecords.getProd4_qty());
						}
						if (tempRecords.getProd5_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id5(),tempRecords.getProd5_qty());
						}
						if (tempRecords.getProd6_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id6(),tempRecords.getProd6_qty());
						}
						if (tempRecords.getProd7_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id7(),tempRecords.getProd7_qty());
						}
						if (tempRecords.getProd8_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id8(),tempRecords.getProd8_qty());
						}
						if (tempRecords.getProd9_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id9(),tempRecords.getProd9_qty());
						}
						if (tempRecords.getProd10_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id10(),tempRecords.getProd10_qty());
						}
						if (tempRecords.getProd11_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id11(),tempRecords.getProd11_qty());
						}
						if (tempRecords.getProd12_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id12(),tempRecords.getProd12_qty());
						}
						if (tempRecords.getProd13_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id13(),tempRecords.getProd13_qty());
						}
						if (tempRecords.getProd14_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id14(),tempRecords.getProd14_qty());
						}
						if (tempRecords.getProd15_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id15(),tempRecords.getProd15_qty());
						}
						if (tempRecords.getProd16_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id16(),tempRecords.getProd16_qty());
						}
						if (tempRecords.getProd17_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id17(),tempRecords.getProd17_qty());
						}
						if (tempRecords.getProd18_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id18(),tempRecords.getProd18_qty());
						}
						if (tempRecords.getProd19_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id19(),tempRecords.getProd19_qty());
						}
						if (tempRecords.getProd20_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id20(),tempRecords.getProd20_qty());
						}
						
						//also save mails in the mail tables
						if(tempRecords.getEmail1()!=null && tempRecords.getEmail1().trim().length()>0) {
							mailList.add(tempRecords.getEmail1());
						}
						
						if(tempRecords.getEmail2()!=null && tempRecords.getEmail2().trim().length()>0) {
							mailList.add(tempRecords.getEmail2());
						}
						
						if(tempRecords.getEmail3()!=null && tempRecords.getEmail3().trim().length()>0) {
							mailList.add(tempRecords.getEmail3());
						}
						
						if(tempRecords.getEmail4()!=null && tempRecords.getEmail4().trim().length()>0) {
							mailList.add(tempRecords.getEmail4());
						}
						
						if(tempRecords.getEmail5()!=null && tempRecords.getEmail5().trim().length()>0) {
							mailList.add(tempRecords.getEmail5());
						}				
						
						if(mailList!=null && !mailList.isEmpty()) {
							emailtranwiseservice.saveEmailTranWise(mailList,"SPB", header.getAlloc_req_id(),header.getFin_year());
							mailList = new ArrayList<String>();
						}
						tempRecords.setAlloc_req_hdr_id(header.getAlloc_req_id());
						this.transactionalRepository.update(tempRecords);
						
						Blk_hcp_req_hdr bulkReqHdr = this.entityManager.find(Blk_hcp_req_hdr.class, bulkReqId);
						bulkReqHdr.setBlk_status("Y");
						this.transactionalRepository.update(bulkReqHdr);
						
					}
					else {
						throw new MedicoException("Invalid data entered. PLease try again.");
					}
				}

			} else {
				throw new MedicoException("Invalid Transaction No. entered. PLease try again.");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AllocReqHeader setAllocReqHdrFromBlkTempRecordsAndSave(Blk_hcq_req_temp blkTempRecord, String finYear,
			String ipaddress,String periodCode) throws Exception {
		AllocReqHeader header = null;
		try {
			header = new AllocReqHeader();
			SimpleDateFormat dd_MM_YYYY = new SimpleDateFormat("dd-mm-yyyyy");
			SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-mm-dd");
			header.setAlloc_req_date(new Date());
			header.setFin_year(finYear);
			header.setPeriod_code(periodCode);

			header.setReq_ins_dt(new Date());
			header.setReq_ins_ip_add(ipaddress);
			header.setReq_ins_user_id(blkTempRecord.getBlk_upl_user());
			header.setReq_mod_dt(null);
			header.setReq_mod_ip_add(null);
			header.setReq_mod_user_id(null);

			header.setCompany(blkTempRecord.getCompany());
			String div_name = this.divMasterService.getDivNameByDivID(blkTempRecord.getDiv_id());
			if(div_name!=null)
				header.setDivision(div_name);
			else 
				throw new MedicoException("Error while processing file - could not parse divison");
			header.setRequestor_id(blkTempRecord.getRequestor_id());
			header.setFs_id(blkTempRecord.getRequestor_id());
			header.setTerritory_id(blkTempRecord.getTerritory_id());
			header.setRequest_no("D-"+"00000");//will be updated after saving
			header.setAlloc_type("D");
			header.setRequest_type("D");
			header.setFreight_value(BigDecimal.ZERO);
			header.setRecipient_type("D");
			header.setRecipient_name(blkTempRecord.getHcp_name());
			header.setMcl_number(blkTempRecord.getMcl_number());
			header.setRecipient_address1(blkTempRecord.getHcp_address_1());
			header.setRecipient_address2(blkTempRecord.getHcp_address_2());
			header.setRecipient_address3(blkTempRecord.getHcp_address_3());
			header.setRecipient_address4(blkTempRecord.getHcp_address_4());
			header.setRecipient_city(blkTempRecord.getHcp_city());
			header.setRecipient_pin(blkTempRecord.getHcp_pin_code());
			header.setRecipient_phone(blkTempRecord.getHcp_mobile());
			header.setRecipient_email(blkTempRecord.getHcp_email());
			header.setReq_remarks(blkTempRecord.getReq_remarks());
			header.setReq_total_value(BigDecimal.ZERO);
			header.setReq_status('E');
			header.setAlloc_appr_status('E');
			header.setService_req_no(blkTempRecord.getSrt_number());
			header.setDoc_letter("N");

			header.setSrt_number(blkTempRecord.getSrt_number());
			header.setSrt_date(blkTempRecord.getSrt_date());
			header.setSrt_remark(blkTempRecord.getSrt_remarks());

			header.setHcp_name(blkTempRecord.getHcp_name());
			header.setBulk_upload_date(blkTempRecord.getBlk_upl_date());
			header.setBlk_hcp_req_id(blkTempRecord.getBlk_hcp_req_id());
			header.setBlk_csv_name(blkTempRecord.getBlk_csv_name());
			header.setBlk_hcp_req_no(blkTempRecord.getBlk_hcp_req_no().trim());
			header.setHcp_unique_id(blkTempRecord.getHcp_unique_id());
			header.setAddress1(blkTempRecord.getHcp_address_1());
			header.setAddress2(blkTempRecord.getHcp_address_2());
			header.setAddress3(blkTempRecord.getHcp_address_3());
			header.setAddress4(blkTempRecord.getHcp_address_4());
			header.setCity(blkTempRecord.getHcp_city());
			header.setPhone(blkTempRecord.getHcp_mobile());
			header.setHcp_email(blkTempRecord.getHcp_email());
			header.setAddress_chg(blkTempRecord.getMdm_address_chg());
			header.setEntry_type("B");
			System.out.println("header-->\n"+header.toString());
			this.transactionalRepository.persist(header);
			header.setRequest_no("D-"+header.getAlloc_req_id());
			this.transactionalRepository.update(header);
			return header;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAllocReqDet(AllocReqHeader header, String compCode, Long divId, Long prodId,BigDecimal prod_qty) throws Exception {
		SpecialAllocationBean prodBean = null;
		try {
			// change this method
			prodBean = this.doctorbulkallocuploadrepository.getProductDetails(divId, prodId,
					compCode);
			if (prodBean != null) {
				AllocReqDet allocDetails = new AllocReqDet();
				allocDetails.setAlloc_req_id(header.getAlloc_req_id());
				allocDetails.setAlloc_req_date(header.getAlloc_req_date());
				allocDetails.setFin_year(header.getFin_year());
				allocDetails.setPeriod_code(header.getPeriod_code());
				allocDetails.setCompany(header.getCompany().trim());
				allocDetails.setDivision(header.getDivision());
				allocDetails.setFs_id(header.getFs_id());
				allocDetails.setTerritory_id(header.getTerritory_id());
				allocDetails.setReqdt_ins_user_id(header.getReq_ins_user_id());
				allocDetails.setReqdt_mod_user_id(null);
				allocDetails.setReqdt_ins_dt(header.getReq_ins_dt());
				allocDetails.setReqdt_mod_dt(null);
				allocDetails.setReqdt_ins_ip_add(header.getReq_ins_ip_add());
				allocDetails.setReqdt_mod_ip_add(null);
				allocDetails.setReqdt_status("E");
				/////////// set prod dets/////////////
				
				allocDetails.setProd_id(prodBean.getProd_id());
				allocDetails.setProd_type(prodBean.getProd_type());
				allocDetails.setProd_sub_type(prodBean.getProd_sub_type());
				allocDetails.setProd_threshold(prodBean.getThreshold());
				allocDetails.setProd_highvalue(prodBean.getHinghvalue());
				allocDetails.setProd_sensitive(prodBean.getSensitive());
				allocDetails.setAlt_div_id(prodBean.getDiv_id());// alt div id
				allocDetails.setAlloc_cycle(1L);
				allocDetails.setAlloc_rate(prodBean.getSmp_rate());
				allocDetails.setAlloc_qty(prod_qty);
				
				allocDetails.setAlloc_value(prod_qty.multiply(prodBean.getSmp_rate()));
				System.out.println("allocDetails-->\n"+allocDetails.toString());
				this.transactionalRepository.persist(allocDetails);
			} else {
				throw new MedicoException("Invalid Data entered for products");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Map<String, Object> deleteBulkDoctorUploadBeforeModify(Long blkreqHdId) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.deleteBulkDoctorUploadBeforeModify(blkreqHdId);
	}

	@Override
	public Map<String, Object> deleteBulkProductUploadBeforeModify(Long blkreqHdId) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.deleteBulkProductUploadBeforeModify(blkreqHdId);
	}
	
	@Override
	public Blk_hcp_req_hdr getObjById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getObjById(Id);
	}

	@Override
	public List<Blk_hcp_req_doctors> getDoctorDetailsById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getDoctorDetailsById(Id);
	}

	@Override
	public List<Blk_hcp_req_products> getProductsDetailsById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getProductsDetailsById(Id);
	}
	
	@Override
	public List<Blk_hcp_req_hdr> getReqNoByStatus(String status,String iscnfrmd) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getReqNoByStatus(status,iscnfrmd);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void selfApprovalDoctorUpload(String reqId,String userRole,
			String empId,HttpServletRequest request,HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		List<Object> fstaffDtls = new ArrayList<Object>();
		Blk_hcp_req_hdr hdr = null;
		Blk_hcp_req_products prd = null;
		SmpItem smp= null;
		String approval = "";
		Long allocId ;
		try {
			System.out.println("userRole : "+userRole);
			String ip_addr = request.getRemoteAddr();
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String is_live = session.getServletContext().getAttribute(IS_LIVE).toString();
			hdr = doctorbulkallocuploadrepository.getBulkRequestHdrByReqNo(reqId);
			
			
			List<TaskMaster> masters = null;
			if (hdr.getBlk_appr_ind().equals("E")) {
				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				Location location = locationService.getLocationNameByEmployeeId(empId);
				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
					approval = MedicoConstants.DBLK_APPR_PIPL;
				} else {
					approval = MedicoConstants.DBLK_APPR_PFZ;
				}

				prd = doctorbulkallocuploadrepository.getProductsDetailsById(hdr.getBlk_hcp_req_id()).get(0);
				
				
				if (prd.getSmp_prod_type().equals("Sample")) {
					allocId=2l;
					masters = taskMasterService.getTask(null, null, null, approval, null, null, TASK_FLOW,
							allocId, null);
				}
				else {
					allocId=1l;
					masters = taskMasterService.getTask(null, null, null, approval, null, null, TASK_FLOW,
							allocId,null);
				}
				
					if (masters.size() == 0) {
						throw new MedicoException("Task master record not found");
					}
					System.out.println("TASK "+masters.get(0).getTask_id());
					TaskMaster taskMaster = masters.get(0);
					List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
					if (task_Master_Dtls.size() == 0) {
						throw new MedicoException("Task master detail record not found");
					}
					
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("initiator", empId);
					variables.put("initiator_desc", empId);
					variables.put("initiator_email", "");
					variables.put("tran_ref_id",hdr.getBlk_hcp_req_id());
					variables.put("tran_type", approval);
					variables.put("inv_group", null);
					variables.put("loc_id", 0);
					variables.put("cust_id", 0L);
					variables.put("tran_no", hdr.getBlk_hcp_req_no());
					variables.put("company_code", companyCode);
					variables.put("totaltask", masters.size());
					variables.put("amount", BigDecimal.ZERO);
					variables.put("escalatetime", null);
					variables.put("fin_year_id", financialyearservice.getCurrentFinancialYear(companyCode).getFin_year());
					variables.put("stock_point_id", 0);
					variables.put("doc_type", "SPB");
					variables.put("task_flow", TASK_FLOW);
					variables.put("remark", "");
				//	if (header.getAlloc_type().trim().equals("D"))
						variables.put("fs_id", allocId);// for doctor request //variables.put("fs_id", allocId);
				//	else
				//		variables.put("fs_id", allocId); // for emergecy supply/meeting
					variables.put("approval_type", null);
				
					ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("doctorBlkUpldAllocation", variables);
			}
			hdr.setBlk_appr_ind("F");
			this.transactionalRepository.update(hdr);
		}
		catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void finalApprovalDoctorUpload(String reqId,String reqno) throws Exception {
//		// TODO Auto-generated method stub
//		try {
//			
//			
//			
//			String empId = null,Ipaddr = null;
//			List<AllocReqHeader>list =specialAllocationService.getAllocReqHdsBy_BlkReqNo(reqno);
//			System.out.println("List : "+list.size());
//			for(AllocReqHeader hd : list) {
//				empId = hd.getReq_ins_user_id();
//				Ipaddr = hd.getReq_ins_ip_add();
//				hd.setAlloc_appr_status('A');
//				transactionalRepository.update(hd);
//			}
//			
//			doctorbulkallocuploadrepository.FinalAllocationApprovalProc(Long.valueOf(reqId), empId, Ipaddr);
//		}
//		catch (Exception e) {
//			throw e;
//			// TODO: handle exception
//		}
//	}

	@Override
	public List<Blk_hcp_req_hdr> getRequestListFromBlkHdrForUploaded(Long divId) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getRequestListFromBlkHdrForUploaded(divId);
	}

	@Override
	public void ConfirmDoctorBlkAlloc(Long reqId,String empId,String ipaddr) throws Exception {
		// TODO Auto-generated method stub
		Blk_hcp_req_hdr hdr = null;
		try {
			hdr = doctorbulkallocuploadrepository.getBlkHcpReqHdrById(reqId);
			
			if(hdr == null) {
				throw new MedicoException("Request Not Found");
			}
			
			hdr.setBlk_confirm_ind("A");
			hdr.setBlk_mod_dt(new Date());
			hdr.setBlk_mod_usr_id(empId);
			hdr.setBlk_mod_ip_add(ipaddr);
			transactionalRepository.update(hdr);
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void selfApprovalDoctorUpload(String reqId) throws Exception {
		// TODO Auto-generated method stub
		try {
		List<AllocReqHeader>list =specialAllocationService.getAllocReqHdsBy_BlkReqNo(reqId);
		
		System.out.println("List : "+list.size());
		for(AllocReqHeader hd : list) {
			hd.setAlloc_appr_status('F');
			this.transactionalRepository.update(hd);
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public String getExcelForBulkUploadNoMaster(Blk_hcp_req_hdr blk_header, List<Object[]> prodList, Long bulkReqId,
			String empId) throws Exception {
		StringBuffer path = null;
		String fileName = "";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		XSSFWorkbook workbook = null;
		try {
			fileName = "DocBulkUpload" + new Date().getTime() + ".xlsx";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			System.out.println("filename " + fileName);
			File file = new File(filePath);
			workbook = new XSSFWorkbook();
			try {
				if (!file.exists()) {
					if (file.mkdirs()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}

			XSSFSheet sheet = workbook.createSheet(fileName);

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

//			 FileWriter fwriter = new FileWriter(path.toString());
//			 writer = new CSVWriter(fwriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
			String[] header = null;
			header = new String[36 + prodList.size()];
			header[0] = "BULK_UNIQUE_ID *";
			header[1] = "REQUESTOR_ID";
			header[2] = "SG_EMPLOYEE_NO (25) *";
			header[3] = "Address_Change (1) *";
			header[4] = "HCP Unique ID (20)";
			header[5] = "HCP_Name (50) *";
			header[6] = "MCL_NUMBER (20) *";
			header[7] = "SRT Number (8) *";
			header[8] = "SRT Date (dd/mm/yyyy) *";
			header[9] = "SRT Remarks (30) ";
			header[10] = "REQ_REMARKS (30)";
			header[11] = "COMPANY ";
			header[12] = "DIVISION ";
			header[13] = "REQUEST_NO";
			header[14] = "Request Date (dd/mm/yyyy)";
			header[15] = "FS_Code";
			header[16] = "Requestor_Name *";
			header[17] = "Observer1 Email (60)";
			header[18] = "Observer2 Email (60)";
			header[19] = "Observer3 Email (60)";
			header[20] = "Observer4 Email (60)";
			header[21] = "Observer5 Email (60)";
			header[22] = "SG TERRITORY Code *";
			header[23] = "TERRITORY Name *";
			header[24] = "Address 1 (45) *";
			header[25] = "Address 2 (45) *";
			header[26] = "Address 3 (45) *";
			header[27] = "Address 4 (45) *";
			header[28] = "City (45) *";
			header[29] = "PIN code (10) *";
			header[30] = "HCP Mobile (10) *";
			header[31] = "HCP Email (60) *";
			header[32] = "MDM_EMPLOYEE_NO (7) *";
			header[33] = "SG TERR CODE *";
			header[34] = "MDM TERR CODE *";
			header[35] = "MDM TERR NAME *";
			for (int i = 1; i <= prodList.size(); i++) {
				header[35 + i] = prodList.get(i - 1)[1].toString() + " - " + prodList.get(i - 1)[2].toString();
			}

			rowCount += 0;
			row = sheet.createRow(rowCount);

			for (String heading : header) {
				cell = row.createCell(colCount);
				cell.setCellValue(heading);
				colCount++;
			}

			rowCount += 1;
			colCount = 0;
			int count = 1;

//			 List<String[]> li = new ArrayList<String[]>();
//			 li.add(header);
			for (int i = 0; i < blk_header.getNo_of_doctors(); i++) {

				colCount = 0;

				row = sheet.createRow(rowCount);
				cell = ReportFormats.cell(row, colCount, blk_header.getBlk_hcp_req_id().toString(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "N", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, blk_header.getCompany(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount,
						this.divMasterService.getDivNameByDivID(blk_header.getDiv_id()), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, blk_header.getBlk_hcp_req_no(), null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, df.format((Date) blk_header.getBlk_hcp_req_date()).toString(),
						null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, "", null);
				colCount++;

				for (int k = 0; k < prodList.size(); k++) {
					cell = row.createCell(colCount);
					cell.setCellValue(Long.valueOf((prodList.get(k)[3]).toString()));
					colCount++;
				}

				rowCount++;
				count++;

			}

			FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
			workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();
			// write to hdr records
			blk_header.setBlk_csvgen_usr_id(empId);
			blk_header.setBlk_csv_gen_date(new Date());
			blk_header.setBlk_csv_name(fileName);
			blk_header.setFile_download("Y");
			transactionalRepository.update(blk_header);

		} catch (Exception e) {
			throw e;
		}

		return fileName;
	}

	@Override
	public List<Blk_hcq_req_temp> getBulkTempByBlkHdrId(Long bulkHdrId) throws Exception {
		// TODO Auto-generated method stub
		return doctorbulkallocuploadrepository.getBulkTempByBlkHdrId(bulkHdrId);
	}

	@Override
	public void finalApproval(Long blkReqId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {
		// TODO Auto-generated method stub
		Blk_hcp_req_hdr header = null;
		List<AllocReqHeader> reqlst = null;
		try {
			System.out.println("In Final Approval Of Blk Upload");
			
			System.out.println("BlkReqId : "+blkReqId);
			
			header = doctorbulkallocuploadrepository.getBlkHcpReqHdrById(blkReqId);
			if (isapproved.equalsIgnoreCase("A")) {
				header.setBlk_appr_ind("A");
				header.setBlk_mod_dt(new Date());
				header.setBlk_mod_usr_id(last_approved_by);
				this.transactionalRepository.update(header);
				reqlst = specialAllocationService.getAllocReqHdsBy_BlkReqNo(header.getBlk_hcp_req_no());
				for(AllocReqHeader hdr : reqlst) {
					hdr.setAlloc_appr_status('A');
					hdr.setReq_mod_dt(new Date());
					hdr.setReq_mod_user_id(last_approved_by);
					this.transactionalRepository.update(hdr);
				}
				
				doctorbulkallocuploadrepository.FinalAllocationApprovalProc(header.getBlk_hcp_req_id(), last_approved_by, "0");
			}
			else {
				header.setBlk_appr_ind("D");
				header.setBlk_mod_dt(new Date());
				header.setBlk_mod_usr_id(last_approved_by);
				this.transactionalRepository.update(header);
				reqlst = specialAllocationService.getAllocReqHdsBy_BlkReqNo(header.getBlk_hcp_req_no());
				for(AllocReqHeader hdr : reqlst) {
					hdr.setAlloc_appr_status('D');
					hdr.setReq_mod_dt(new Date());
					hdr.setReq_mod_user_id(last_approved_by);
					this.transactionalRepository.update(hdr);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<Object[]> getGeneratedRequestList(Long blkReqId) throws Exception {
		return doctorbulkallocuploadrepository.getGeneratedRequestList(blkReqId);
	}

	@Override
	public Map<String, Object> validateExcelUploadForTemp(MultipartFile file) throws Exception {
		Map<String, Object> map = new HashMap<>();
		boolean rowHasError = false;
		boolean fileHasErrors = false;
		boolean fileHasWarnings = false;
		List<BulkUpldBean> linkage = new ArrayList<BulkUpldBean>();
		String filename = "";
		BulkUpldBean blkUpldBn = null;
		List<HcpBulkUploadErrorBean> errList = new ArrayList<HcpBulkUploadErrorBean>();
		String[] headers = { "BULK_UNIQUE_ID", "REQUESTOR_ID", "SG_EMPLOYEE_NO", "Address_Change", "HCP Unique ID",
				"HCP_Name", "MCL_NUMBER", "SRT Number", "SRT Date", "SRT Remarks", "REQ_REMARKS", "COMPANY", "DIVISION",
				"REQUEST_NO", "Request Date", "FS_Code", "Requestor_Name", "Observer1 Email", "Observer2 Email",
				"Observer3 Email", "Observer4 Email", "Observer5 Email", "SG TERRITORY Code", "TERRITORY Name",
				"Address 1", "Address 2", "Address 3", "Address 4", "City", "PIN code", "Mobile", "HCP Email",
				"MDM_EMPLOYEE_NO", "SG TERR CODE", "MDM TERR CODE","MDM TERR NAME" };
		Workbook work = null;
		try {
			HcpBulkUploadErrorBean newErrList = null;
			//File xlsxFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
			File xlsxFile = new File("D:\\sg\\tempDocFile" + "\\" + file.getOriginalFilename());
			file.transferTo(xlsxFile);
			XSSFRow currRow = null;
			work = WorkbookFactory.create(xlsxFile);
			XSSFSheet sheet = (XSSFSheet) work.getSheetAt(0);
			int no_of_rows = sheet.getPhysicalNumberOfRows();
			Blk_hcp_req_hdr hdrRecord = null;
			// start reading file and based on indicator give errors
			List<Object[]> Hcplist;
			List<Object[]> fslist;
			List<BulkUpldBean> linklist;
			String sgTerrCode = null;
			String mdmTerrCode = null;
			int index = 0;
			XSSFRow headerRow = sheet.getRow(index);
			XSSFRow row = null;
			//indexes of cells that are allowed to be empty 
			int[] allowedEmptyarrForMdmFile = {9, 10, 17, 18, 19, 20, 21, 30, 31 };
			int[] allowedEmptyarrForNoMasterFile = { 1, 4, 9,10, 15, 17, 18, 19, 20, 21 };
			index++;
			fileHasErrors = false;
			fileHasWarnings = false;
			List<String> errListEach = null;
			List<String> warnListEach = null;
			boolean ignoreCol = false;
			System.err.println("sheet.getRow(0)::"+sheet.getRow(0));
			while (sheet.getRow(index) != null) {

				rowHasError = false;
				newErrList = new HcpBulkUploadErrorBean();

				newErrList.setRowNum(index);
				errListEach = new ArrayList<String>();
				warnListEach = new ArrayList<String>();

				hdrRecord = this.getBlkHcpReqHdrById(Double.valueOf(sheet.getRow(1).getCell(0).toString()).longValue());
				if (hdrRecord == null) {
					throw new MedicoException("Invalid header Id entered");
				}
				currRow = sheet.getRow(index);
				if (hdrRecord.getDoc_mst_type().equals("N")) {

					// check empty fields in no master case
					System.out.println("headers.length::::" + headers.length);

					for (int i = 0; i < headers.length; i++) {
						ignoreCol = false;
						for (int k = 0; k < allowedEmptyarrForNoMasterFile.length; k++) {
							if (allowedEmptyarrForNoMasterFile[k] == i) {
								ignoreCol = true;
							}
						}
						if (!ignoreCol) {
							if (currRow.getCell(i) == null || currRow.getCell(i).toString().trim().length() == 0) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add(headers[i] + " column is left empty");
							}
						}
					}
					System.err.println("index::"+index);
					System.err.println("dive name   ::: "+currRow.getCell(12).toString());
					Long divId = this.divMasterService.getDivIdByName(currRow.getCell(12).toString());
					if (currRow.getCell(2).toString() != null && (currRow.getCell(2).toString().trim().length() != 0)
							&& !currRow.getCell(2).toString().isEmpty()) {
						// do further validation

						fslist = doctorbulkallocuploadrepository.checkFstaffByEmployeeNoAndTerritoryCode(
								currRow.getCell(2).toString(), currRow.getCell(33).toString(),hdrRecord.getDoc_mst_type());
						
						if (fslist != null && fslist.size() > 1) {
							// query with division check
							fslist = doctorbulkallocuploadrepository
									.checkFstaffByEmplNoAndDivCheck(currRow.getCell(2).toString(), divId.toString(),hdrRecord.getDoc_mst_type());
						}
						if (fslist.size() != 0 && !fslist.isEmpty()) {

							if (!fslist.get(0)[51].toString().equals("A")) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add("Requestor is Deactivated ");
							}

							if (!fslist.get(0)[2].toString()
									.equals(divId.toString())) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add("Requestor is not attached to selected division");
							}
							if (!fileHasErrors) {
							} else {
							}

						} else {
							fileHasErrors = true;
							rowHasError = true;
							errListEach.add("Requestor does not exist or territory is not attached");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("Requestor not entered");
					}

					// check for emp no from mdm and from sg
					if (currRow.getCell(2).toString() != null && (currRow.getCell(2).toString().trim().length() != 0)
							&& !currRow.getCell(2).toString().isEmpty() && currRow.getCell(32).toString() != null
							&& (currRow.getCell(32).toString().trim().length() != 0)
							&& !currRow.getCell(32).toString().isEmpty()) {

						if (!currRow.getCell(2).toString().equals(currRow.getCell(32).toString())) {
							fileHasWarnings = true;
							rowHasError = true;
							warnListEach.add("SG Emplyee No does not match MDM Emplyee No");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("SG Employee No or MDM Employee No is not entered");
					}
					// check for terr code sg and mdm
					if (currRow.getCell(33).toString() != null && (currRow.getCell(33).toString().trim().length() != 0)
							&& !currRow.getCell(33).toString().isEmpty() && currRow.getCell(34).toString() != null
							&& (currRow.getCell(34).toString().trim().length() != 0)
							&& !currRow.getCell(34).toString().isEmpty()) {

						sgTerrCode = currRow.getCell(33).toString();
						mdmTerrCode = currRow.getCell(34).toString();
						if (!sgTerrCode.trim().equals(mdmTerrCode.trim())) {
							fileHasWarnings = true;
							rowHasError = true;
							warnListEach.add("SG Territory Code does not match MDM Territory Code");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("SG Territory Code or MDM Territory Code is not entered");
					}
					
					if (currRow.getCell(23).toString() != null && (currRow.getCell(23).toString().trim().length() != 0)
							&& !currRow.getCell(23).toString().isEmpty() && currRow.getCell(35).toString() != null
							&& (currRow.getCell(35).toString().trim().length() != 0)
							&& !currRow.getCell(35).toString().isEmpty()) {

						
						if (!currRow.getCell(23).toString().equals(currRow.getCell(35).toString())) {
							fileHasWarnings = true;
							rowHasError = true;
							warnListEach.add("SG Territory Name does not match MDM Territory Name");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("SG Territory Name or MDM Territory Name is not entered");
					}

				} else if (hdrRecord.getDoc_mst_type().equals("M")) {

					// check empty fields in no master case
					System.out.println("headers.length::::" + headers.length);

					for (int i = 0; i < headers.length; i++) {
						ignoreCol = false;
						for (int k = 0; k < allowedEmptyarrForNoMasterFile.length; k++) {
							if (allowedEmptyarrForNoMasterFile[k] == i) {
								ignoreCol = true;
							}
						}
						if (!ignoreCol) {
							if (currRow.getCell(i) == null || currRow.getCell(i).toString().trim().length() == 0) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add(headers[i] + " column is left empty");
							}
						}
					}

					if (currRow.getCell(4) != null && currRow.getCell(4).toString() != null
							&& (currRow.getCell(4).toString().trim().length() != 0)
							&& !currRow.getCell(4).toString().isEmpty()) {
						Hcplist = doctorbulkallocuploadrepository.checkUniqueHcpid(currRow.getCell(4).toString());
						if (Hcplist.size() != 0 && !Hcplist.isEmpty()) {
							if (!Hcplist.get(0)[37].toString().equals("TRUE")) {
								fileHasWarnings = true;
								rowHasError = true;
								warnListEach.add("HCP is not Active");
							}

						} else {
							fileHasErrors = true;
							rowHasError = true;
							errListEach.add("HCP does not exist");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("HCP not entered");
					}
					System.out.println("Div_VAL :: " + currRow.getCell(12).toString());
					Long divId = this.divMasterService.getDivIdByName(currRow.getCell(12).toString());
					if (currRow.getCell(2).toString() != null && (currRow.getCell(2).toString().trim().length() != 0)
							&& !currRow.getCell(2).toString().isEmpty()) {
						System.out.println("emplno" + currRow.getCell(2).toString());
						System.out.println("terrcode" + currRow.getCell(22).toString());
						fslist = doctorbulkallocuploadrepository.checkFstaffByEmployeeNoAndTerritoryCode(
								currRow.getCell(2).toString(), currRow.getCell(33).toString(),hdrRecord.getDoc_mst_type());
						if (fslist != null && fslist.size() > 1) {
							// query with division check
							fslist = doctorbulkallocuploadrepository
									.checkFstaffByEmplNoAndDivCheck(currRow.getCell(2).toString(), divId.toString(),hdrRecord.getDoc_mst_type());
						}
						if (fslist.size() != 0 && !fslist.isEmpty()) {
							if (!fslist.get(0)[51].toString().equals("A")) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add("Requestor is Deactivated ");
							}

							if (!fslist.get(0)[2].toString().equals(divId.toString())) {
								fileHasErrors = true;
								rowHasError = true;
								errListEach.add("Requestor is not attached to selected division");
							}
						} else {
							fileHasErrors = true;
							rowHasError = true;
							errListEach.add("Requestor does not exist");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("Requestor not entered");
					}

					linklist = doctorbulkallocuploadrepository.checklinkHcpandFstaff(currRow.getCell(4).toString(),
							currRow.getCell(2).toString(), divId.toString());
					if (linklist.size() == 0 && linklist.isEmpty()) {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("HCP is not attached to selected requestor or this link is deactivated");
					} else {
						if (!fileHasErrors) {
						} else {
						}
					}

					// check for emp no from mdm and from sg
					if (currRow.getCell(2).toString() != null && (currRow.getCell(2).toString().trim().length() != 0)
							&& !currRow.getCell(2).toString().isEmpty() && currRow.getCell(32).toString() != null
							&& (currRow.getCell(32).toString().trim().length() != 0)
							&& !currRow.getCell(32).toString().isEmpty()) {

						if (!currRow.getCell(2).toString().trim().equals(currRow.getCell(32).toString().trim())) {
							fileHasWarnings = true;
							rowHasError = true;
							warnListEach.add("SG Emplyee No does not match MDM Emplyee No");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("SG Employee No or MDM Employee No is not entered");
					}
					// check for terr code sg and mdm
					if (currRow.getCell(33).toString() != null && (currRow.getCell(33).toString().trim().length() != 0)
							&& !currRow.getCell(33).toString().isEmpty() && currRow.getCell(34).toString() != null
							&& (currRow.getCell(34).toString().trim().length() != 0)
							&& !currRow.getCell(34).toString().isEmpty()) {
						System.out.println("currRow.getCell(33).toString()" + currRow.getCell(33).toString());
						System.out.println("currRow.getCell(34).toString()" + currRow.getCell(34).toString());
						sgTerrCode = currRow.getCell(33).toString().trim();
						mdmTerrCode = currRow.getCell(34).toString().trim();
						if (!sgTerrCode.trim().equals(mdmTerrCode.trim())) {
							fileHasWarnings = true;
							rowHasError = true;
							warnListEach.add("SG Territory Code does not match MDM Territory Code");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("SG Territory Code or MDM Territory Code is not entered");
					}
					
					if (currRow.getCell(23).toString() != null && (currRow.getCell(23).toString().trim().length() != 0)
							&& !currRow.getCell(23).toString().isEmpty() && currRow.getCell(35).toString() != null
							&& (currRow.getCell(35).toString().trim().length() != 0)
							&& !currRow.getCell(35).toString().isEmpty()) {

						
						if (!currRow.getCell(23).toString().equals(currRow.getCell(35).toString())) {
							fileHasWarnings = true;
							rowHasError = true;
							warnListEach.add("SG Territory Name does not match MDM Territory Name");
						}
					} else {
						fileHasErrors = true;
						rowHasError = true;
						errListEach.add("SG Territory Name or MDM Territory Name is not entered");
					}

				}

				newErrList.setErrorList(errListEach);

				newErrList.setWarning(warnListEach);

				if (rowHasError) {
					errList.add(newErrList);
				}
				index++;
			}
			System.out.println("errList " + errList.size());

			if (fileHasErrors || fileHasWarnings) {
				System.out.println("fileHasErrors " + errList.size());
				filename = this.bulkUploadErrorFile(errList);
			}

			map.put("linkage", linkage);
			map.put("filename", filename);
			map.put("fileHasErrors", fileHasErrors);
			map.put("fileHasWarnings", fileHasWarnings);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (work != null)
				work.close();
		}
		return map;
	}

	@Override
	public String doctorBulkUploadExcelToPdf(String name) throws Exception {
		Workbook workbook = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			workbook = new XSSFWorkbook(new FileInputStream("D:/sg/bulkUploadedExcel/" + name));

			Sheet sheet = workbook.getSheetAt(0);

			List<BulkDoctorPdf> bulkDoctorPdfList = new ArrayList<>();

			List<ProductDetails> prodDetailsList = null;

			BulkDoctorPdf bean = new BulkDoctorPdf();

			Row headerRow = sheet.getRow(0);

			for (Row row : sheet) {

				if (row.getRowNum() == 0) {
					continue; // Skip the header row
				}
				
				if(row.getCell(0)!=null && !row.getCell(0).toString().equals("")) {
				bean = new BulkDoctorPdf();
				System.out.println("row.getCell(0).toString() : "+row.getCell(0).toString());
				bean.setBulk_unique_id(row.getCell(0).toString());
				bean.setRequester_id(row.getCell(1).toString());
				bean.setSg_employee_no(row.getCell(2).toString());
				bean.setAddress_change(row.getCell(3).toString());
				bean.setHcp_unique_id(row.getCell(4).toString());
				bean.setHcp_name(row.getCell(5).toString());
				bean.setMcl_number(row.getCell(6).toString());
				bean.setSrt_number(row.getCell(7).toString());
				bean.setSrt_date(row.getCell(8).toString());
				bean.setSrt_remarks(row.getCell(9).toString());
				bean.setReq_remarks(row.getCell(10).toString());
				bean.setCompany(row.getCell(11).toString());
				bean.setDivision(row.getCell(12).toString());
				bean.setRequest_no(row.getCell(13).toString());
				bean.setRequest_date(row.getCell(14).toString());
				bean.setFs_code(row.getCell(15).toString());
				bean.setFs_name(row.getCell(16).toString());
				bean.setObserver1_email(row.getCell(17).toString());
				bean.setObserver2_email(row.getCell(18).toString());
				bean.setObserver3_email(row.getCell(19).toString());
				bean.setObserver4_email(row.getCell(20).toString());
				bean.setObserver5_email(row.getCell(21).toString());
				bean.setSg_territory_id(row.getCell(22).toString());
				bean.setTerritory_name(row.getCell(23).toString());
				bean.setAddress1(row.getCell(24).toString());
				bean.setAddress2(row.getCell(25).toString());
				bean.setAddress3(row.getCell(26).toString());
				bean.setAddress4(row.getCell(27).toString());
				bean.setCity(row.getCell(28).toString());
				bean.setPin_code(row.getCell(29).toString());
				bean.setMobile(row.getCell(30).toString());
				bean.setHcp_email(row.getCell(31).toString());
				bean.setMdm_employee_no(row.getCell(32).toString());
				bean.setSg_terr_code(row.getCell(33).toString());
				bean.setMdm_terr_code(row.getCell(34).toString());

				prodDetailsList = new ArrayList<ProductDetails>();
				for (int i = 1; i <= 20; i++) {

					if (headerRow.getCell(35 + i) != null && headerRow.getCell(35 + i).toString().trim().length() > 0) {

						if (new BigDecimal((row.getCell(35 + i)).toString()).longValue() > 0l) {
							ProductDetails prodDets = new ProductDetails();
							prodDets.setProduct_name(headerRow.getCell(35 + i).toString());
							prodDets.setQty(row.getCell(35 + i).toString());
							prodDetailsList.add(prodDets);
						}

					}
				}
				bean.setProductDetails(prodDetailsList);

				bulkDoctorPdfList.add(bean);
				}
			}

			try {
				String fileName = this.doctorBulkUploadPdf(bulkDoctorPdfList);
				if (fileName != null) {
					return fileName;
				} else {
					throw new Exception("PDF File Generation failed!!");
				}
			} catch (Exception e) {

				throw e;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (workbook != null)
				workbook.close();
		}

	}
	
	public String doctorBulkUploadPdf(List<BulkDoctorPdf> bulkDoctorPdfList) throws Exception {

		String fileName = "doctor_bulk_upload" + new Date().getTime() + ".pdf";
		String filepath = PDF_FILE_PATH + fileName;

		System.out.println("filepath:  " + filepath);

		FileOutputStream file = null;
		Document document = null;

		try {

			File docfile = new File(filepath);
			file = new FileOutputStream(docfile);
			document = new Document(PageSize.A4, 5, 5, 5, 5);

			final float[] content_col_width = { 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f };

			PdfWriter writer = PdfWriter.getInstance(document, file);
			document.open();

			PdfPTable main_table = new PdfPTable(1);
			main_table.setWidthPercentage(100);

			int newPage = 0;

			PdfPTable bodytable = new PdfPTable(content_col_width);
			bodytable.setWidthPercentage(100);

			PdfPCell bodycell = new PdfPCell();
			bodycell.disableBorderSide(Rectangle.TOP);
			bodycell.setPadding(0.0f);

			PdfPCell body = null;

			body = PdfStyle.createLabelCellL_A_BorderBold(
					companyMasterRepositoryImpl.getCompanyDetails().getCompany_name(), Element.ALIGN_CENTER, 9, 17f);
			body.setColspan(9);
			bodytable.addCell(body);

			body = PdfStyle.createLabelCellL_A_Border("Bulk Doctor Dispatch Approval for Division "
					+ bulkDoctorPdfList.get(0).getDivision() + " and Request Number "
					+ bulkDoctorPdfList.get(0).getRequest_no() + " Date " + bulkDoctorPdfList.get(0).getRequest_date(),
					Element.ALIGN_CENTER, 9, 17f);
			body.setColspan(9);
			bodytable.addCell(body);

			body = PdfStyle.createLabelCellL_A_BorderBold("", Element.ALIGN_CENTER, 9, 17f);
			body.setColspan(9);
			body.setMinimumHeight(20f);
			bodytable.addCell(body);

			// data

			for (int i = 0; i < bulkDoctorPdfList.size(); i++) {

				bodycell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
				if (bulkDoctorPdfList.size() == 0) {
					bodycell.setFixedHeight(200);
				}

				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("Employee No", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(
						PdfStyle.createLabelCellL_A_BorderBold("Name of the Employe", Element.ALIGN_CENTER, 8, 12f));
				bodytable
						.addCell(PdfStyle.createLabelCellL_A_BorderBold("HCP Unique Id", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("HCP Name", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("Address", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("City", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("SRT Number", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("SRT Date", Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_BorderBold("SRT Remarks", Element.ALIGN_CENTER, 8, 12f));

				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getMdm_employee_no(),
						Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getFs_name(),
						Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getHcp_unique_id(),
						Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getHcp_name(),
						Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(
						bulkDoctorPdfList.get(i).getAddress1() +"  "+ bulkDoctorPdfList.get(i).getAddress2()
						+"  "+ bulkDoctorPdfList.get(i).getAddress3() +"  "+ bulkDoctorPdfList.get(i).getAddress4(),
						Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getCity()+"  "+bulkDoctorPdfList.get(i).getPin_code(),
						Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getSrt_number(),
						Element.ALIGN_CENTER, 8, 12f));

				System.out.println("SRT DATE :: "+bulkDoctorPdfList.get(i).getSrt_date());
				//String[] dt = bulkDoctorPdfList.get(i).getSrt_date().split("-");
				
				//String dt1 = dt[2].trim() + "/" + dt[1].trim() + "/" + dt[0].trim();

				//System.out.println("d1:  " + dt1);

				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getSrt_date(), Element.ALIGN_CENTER, 8, 12f));
				bodytable.addCell(PdfStyle.createLabelCellL_A_Border(bulkDoctorPdfList.get(i).getSrt_remarks(),
						Element.ALIGN_CENTER, 8, 12f));

				int count = 0;

				PdfPCell cell = null;

				for (ProductDetails prodDet : bulkDoctorPdfList.get(i).getProductDetails()) {

					if (count == 0) {

						newPage++;

						cell = new PdfPCell(new Phrase());
						cell = PdfStyle.createLabelCellL_A_BorderBold("Product Name", Element.ALIGN_CENTER, 8, 10f);
						cell.setColspan(8);
						bodytable.addCell(cell);

						cell = new PdfPCell(new Phrase());
						cell = PdfStyle.createLabelCellL_A_BorderBold("Quantity", Element.ALIGN_CENTER, 8, 10f);
						bodytable.addCell(cell);

					}

					cell = new PdfPCell(new Phrase());
					cell = PdfStyle.createLabelCellL_A_Border(prodDet.getProduct_name(), Element.ALIGN_LEFT, 8, 10f);
					cell.setColspan(8);
					bodytable.addCell(cell);

					cell = new PdfPCell(new Phrase());
					cell = PdfStyle.createLabelCellL_A_Border(prodDet.getQty(), Element.ALIGN_RIGHT, 8, 10f);
					bodytable.addCell(cell);

					newPage++;

					count++;
				}

				count = 0;

				cell = new PdfPCell();
				cell.setColspan(11);
				cell.setMinimumHeight(20f);
				bodytable.addCell(cell);

				newPage += 6;

				System.out.println("newPage:  " + newPage);

				if (newPage >= 44) {

					bodycell.addElement(bodytable);
					main_table.addCell(bodycell);
					document.add(main_table);

					document.newPage();

					bodycell = new PdfPCell();
					bodycell.disableBorderSide(Rectangle.TOP);
					bodycell.setPadding(0.0f);
					bodytable = new PdfPTable(content_col_width);
					bodytable.setWidthPercentage(100);
					main_table = new PdfPTable(1);
					main_table.setWidthPercentage(100);

					body = PdfStyle.createLabelCellL_A_BorderBold(
							companyMasterRepositoryImpl.getCompanyDetails().getCompany_name(), Element.ALIGN_CENTER, 9,
							17f);
					body.setColspan(9);
					bodytable.addCell(body);

					body = PdfStyle.createLabelCellL_A_Border(
							"Bulk Doctor Dispatch Approval for Division " + bulkDoctorPdfList.get(0).getDivision()
									+ " and Request Number " + bulkDoctorPdfList.get(0).getRequest_no() + " Date "
									+ bulkDoctorPdfList.get(0).getRequest_date(),
							Element.ALIGN_CENTER, 9, 17f);
					body.setColspan(9);
					bodytable.addCell(body);

					body = PdfStyle.createLabelCellL_A_BorderBold("", Element.ALIGN_CENTER, 9, 17f);
					body.setColspan(9);
					body.setMinimumHeight(20f);
					bodytable.addCell(body);

					newPage = 0;

				}

			}
			
			bodycell.addElement(bodytable);
			main_table.addCell(bodycell);

			document.add(main_table);
			
			try {
				writer.close();
			}
			catch(Exception e) {};
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (file != null) {
					file.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return fileName;
	}
	
	@Override
	public List<Blk_Challans_Generated_Log> getBulkUploadLogDetailList(String finyr,String periodCd) throws Exception {

		return doctorbulkallocuploadrepository.getBulkUploadLogDetailList(finyr,periodCd);
	}

	public String getBulkUploadLogDetailExcel(List<Blk_Challans_Generated_Log> bulkUploadLogList) throws Exception {

		String filename = "bulkUploadLogDetail" + new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		XSSFWorkbook workbook = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {

			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);

			XSSFSheet sheet = workbook.createSheet("Bulk Challans Generated Log");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			String headings[] = {"Row","Financial Year","Period Code","Division","HCP Request No","SRT Number","SRT Date","Total Requests",
					"Product Type","Dispatch Type","From Challan No","To Challan No","Total Challans"};
			
			XSSFCellStyle headingCellStyle = ReportFormats.heading(workbook);
			XSSFCellStyle columnHeadingStyle = ReportFormats.columnHeading(workbook);
			
			XSSFFont boldFont = workbook.createFont();
			boldFont.setBold(true);
			XSSFCellStyle boldStyle = workbook.createCellStyle();
			boldStyle.setFont(boldFont);
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue(companyMasterRepositoryImpl.getCompanyDetails().getCompany_name());
			cell.setCellStyle(headingCellStyle);
			rowCount++;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue("Bulk Challans Generated Log");
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			colCount=0;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, ((colCount + headings.length - 1))));
			cell.setCellValue("Reported On : "+sdf.format(new Date()));
			cell.setCellStyle(headingCellStyle);
			
			rowCount += 1;
			colCount=0;
			
			row = sheet.createRow(rowCount);
			
			for (String heading : headings) {
				cell = ReportFormats.cell(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			
			rowCount += 1;
			colCount=0;
			
			sheet.createFreezePane(0, 3);
			
			String oldreqno="old";
			String newreqno="new";
			
			for (Blk_Challans_Generated_Log data : bulkUploadLogList) {
				colCount = 0;
				newreqno=data.getBlk_hcp_req_no();
				if(!newreqno.equals(oldreqno) && !oldreqno.equals("old")) {
					row = sheet.createRow(rowCount);
					cell = ReportFormats.cell(row, colCount, " ", null);
					sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount,colCount,colCount+13));
					colCount=0;
					rowCount++;
				}
				
				
				row = sheet.createRow(rowCount);
				
				cell = ReportFormats.cell(row, colCount, data.getRow().toString(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_fin_year(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDsp_period_code(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDiv_disp_nm(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getBlk_hcp_req_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_number(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getSrt_date().toString(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getTotal_requests().doubleValue(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getProd_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getDisp_to_type(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getFrom_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cell(row, colCount, data.getTo_challan_no(), null);
				colCount++;
				
				cell = ReportFormats.cellNum(row, colCount, data.getTotal_challans().doubleValue(), null);
				colCount++;
				
				rowCount++;
				colCount=0;
				oldreqno=newreqno;	
			}
			
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) { workbook.close(); }
		}
		
		

		return filename;

	}

}
