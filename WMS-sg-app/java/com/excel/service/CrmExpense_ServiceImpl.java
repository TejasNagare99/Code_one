package com.excel.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.CrmErrorBean;
import com.excel.bean.CrmTdsBean;
import com.excel.model.Company;
import com.excel.model.Crm_GenDtl;
import com.excel.model.Crm_GenHd;
import com.excel.model.Period;
import com.excel.model.Period_Tds;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Sfa_Field_Master;
import com.excel.model.Sfa_Geographical_Master;
import com.excel.model.Sfa_Retail_Consumer;
import com.excel.model.Sub_Company;
import com.excel.model.TdsApplicableProduct;
import com.excel.model.TdsApplicableStatementReport;
import com.excel.model.Tds_Crm_Expense;
import com.excel.model.Tds_Crm_Upload;
import com.excel.model.Tds_crm_Images;
import com.excel.repository.CrmExpenseRepository;
import com.excel.repository.ParameterRepository;
import com.excel.repository.PeriodMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.ReportFormats;

@Service
public class CrmExpense_ServiceImpl implements CrmExpense_Service, MedicoConstants {

	@Autowired
	CrmExpenseRepository crmexpenserepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	PeriodMasterService periodmasterservice;
	@Autowired
	CompanyMasterService companyMasterService;
	@Autowired
	PeriodMasterRepository periodmasterrepository;
	@Autowired
	FinancialYearService financialyearservice;
	@Autowired
	ParameterRepository parameterrepository;

	static String newFilePath = null;

	@Override
	public List<Sfa_Geographical_Master> gethq() {
		// TODO Auto-generated method stub
		return crmexpenserepository.getHQ();
	}

	@Override
	public List<Sfa_Field_Master> getse(Long geog_lvl1_hq) {
		// TODO Auto-generated method stub
		return crmexpenserepository.getSe(geog_lvl1_hq);
	}

	public List<Sfa_Retail_Consumer> getAddress(Long se, String custType) {
		return crmexpenserepository.getcustomerAddRetailer(se, custType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String SaveCRMEntry(CrmTdsBean bean) throws Exception {
		// TODO Auto-generated method stub
		String msg = "";
		try {
			String subcomp = companyMasterService.getSubCompanyList(bean.getUsername()).get(0).getSubcomp_code();
			System.out.println("CRM UserName  :" + bean.getUsername());
			System.out.println("CRM subcompcode  :" + subcomp);
			System.out.println("CRM Finyr  :" + bean.getFinyear());
			System.out.println("CRM custType   :" + bean.getCusttype());
			System.out.println("CRM HQID  :" + bean.getHq());
			System.out.println("CRM FsId  :" + bean.getSe());
			Sfa_Field_Master sfafs = crmexpenserepository.getFsById(Long.valueOf(bean.getSe()));
			System.out.println("CRM DivId  :" + sfafs.getDiv_id());
			System.out.println("CRM custid  :" + bean.getCustid());
			System.out.println("CRM Custname  :" + bean.getCustname());
			System.out.println("CRM Addr1  :" + bean.getAddr1());
			System.out.println("CRM Addr2  :" + bean.getAddr2());
			System.out.println("CRM Addr3  :" + bean.getAddr3());
			System.out.println("CRM Addr4  :" + bean.getAddr4());
			System.out.println("CRM getCity  :" + bean.getCity());
			System.out.println("CRM pincode  :" + bean.getPincode());
			System.out.println("CRM mobile  :" + bean.getTelno());
			System.out.println("CRM email  :" + bean.getEmail());
			System.out.println("CRM panno  :" + bean.getPanno());
			System.out.println("CRM HCP  :" + bean.getHcpno());
			System.out.println("CRM exps date  :"
					+ MedicoResources.convertUtilDateToString_YYYY_MM_DD_HH_MM_SS(bean.getTrandate()));
			System.out.println("CRM tran No  :" + bean.getTransactionno());
			System.out.println("CRM expsdt  :" + bean.getExpensedetail());
			System.out.println("CRM Qty  :" + bean.getQty());
			System.out.println("CRM Rate  :" + bean.getRate());
			System.out.println("CRM Value  :" + bean.getVal());
			System.out.println("CRM TDS  :" + bean.getTdspaid());
			System.out.println("CRM Intrest  :" + bean.getIntrestpaid());
			System.out.println("CRM Penalty  :" + bean.getPenaltypaid());

			System.out.println("CRM GrossVal  :" + bean.getGrossval());
			System.out.println("CRM GrossValInd  :" + bean.getGrossvalind());
			System.out.println("CRM getGrossupperc  :" + bean.getGrossupperc());

			Period_Tds periodtds = periodmasterrepository.getTdsPeriodByDate(bean.getTrandate());

			Tds_Crm_Expense tdscrm = new Tds_Crm_Expense();
			tdscrm.setCrm_sub_comp_code(subcomp);
			tdscrm.setCrm_finyear(periodtds.getPeriod_fin_year());
			tdscrm.setCrm_date(new Date());
			tdscrm.setCrm_cust_type(bean.getCusttype());
			tdscrm.setCrm_hq_id(Long.valueOf(bean.getHq()));
			tdscrm.setCrm_fs_id(Long.valueOf(bean.getSe()));
			tdscrm.setCrm_div_id(sfafs.getDiv_id());
			tdscrm.setCrm_cust_id(bean.getCustid());
			tdscrm.setCrm_cust_name(bean.getCustname());
			tdscrm.setCrm_cust_addr1(bean.getAddr1());
			tdscrm.setCrm_cust_addr2(bean.getAddr2());
			tdscrm.setCrm_cust_addr3(bean.getAddr3());
			tdscrm.setCrm_cust_addr4(bean.getAddr4());
			tdscrm.setCrm_cust_city(bean.getCity());
			tdscrm.setCrm_cust_pincode(bean.getPincode());
			tdscrm.setCrm_cust_mobile(bean.getTelno());
			tdscrm.setCrm_cust_email(bean.getEmail());
			tdscrm.setCrm_cust_panno(bean.getPanno());
			tdscrm.setCrm_cust_hcp_no(bean.getHcpno());
			tdscrm.setCrm_exps_date(MedicoResources.convertUtilDateToString_YYYY_MM_DD_HH_MM_SS(bean.getTrandate()));
			tdscrm.setCrm_acc_tran_no(bean.getTransactionno());
			tdscrm.setCrm_exps_details(bean.getExpensedetail());
			tdscrm.setCrm_qty(new BigDecimal(bean.getQty()));
			tdscrm.setCrm_rate(new BigDecimal(bean.getRate()));
			tdscrm.setCrm_value(new BigDecimal(bean.getVal()));
			tdscrm.setCrm_tds_paid(new BigDecimal(bean.getTdspaid()));
			tdscrm.setCrm_confirm("N");
			tdscrm.setCrm_ins_by(bean.getUsername());
			tdscrm.setLast_mod_dt(new Date());
			tdscrm.setCrm_tds_interest(new BigDecimal(bean.getIntrestpaid()));
			tdscrm.setCrm_tds_penalty(new BigDecimal(bean.getPenaltypaid()));
			tdscrm.setGrossup_crm_value(
					bean.getGrossval() != null ? new BigDecimal(bean.getGrossval()) : BigDecimal.ZERO);
			tdscrm.setGrossup_ind(bean.getGrossvalind());
			tdscrm.setGrossup_percentage(new BigDecimal(bean.getGrossupperc() == null ? "0" : bean.getGrossupperc()));
			tdscrm.setCrm_gen_id(bean.getCrmgenid());
			tdscrm.setCrm_gen_dtl_id(bean.getCrmgendtlid());
			transactionalRepository.persist(tdscrm);

			Tds_crm_Images tdsimage = null;
			if (bean.getImagefiles() != null) {
				for (Entry<String, Object> entry : bean.getImagefiles().entrySet()) {
					System.out.println("MapKey = " + entry.getKey() + ", MapValue = " + entry.getValue());

					tdsimage = new Tds_crm_Images();
					tdsimage.setCrm_id(tdscrm.getCrm_id());
					tdsimage.setFin_year_id(Long.valueOf(tdscrm.getCrm_finyear()));
					tdsimage.setView_path(entry.getKey());
					tdsimage.setImage_path((entry.getValue()).toString());
					transactionalRepository.persist(tdsimage);
				}
			}
			msg = "Saved Succcessfully";
			// TDS SUMMARY TABLE
//			String Tablename = "TDS_CRM_MASTER_ANNUAL_"+String.format("%02d",Integer.parseInt(tdscrm.getCrm_sub_comp_code()));
//			Period pr = periodmasterservice.getCurrentPeriod();
//			
//			Object[] obj = (Object[]) crmexpenserepository.findObjByCustType(Tablename, bean.getCustid(), bean.getCusttype());
//			
//			if(obj==null) {
//				crmexpenserepository.SaveTds_crm_master_annual_(tdscrm, Tablename, pr);
//			}
//			else {
//				crmexpenserepository.UpdateAnnualTdsCrm(Tablename, bean.getCustid(), bean.getCusttype(), obj, tdscrm, pr);
//			}

		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return msg;
	}

	@Override
	public List<Tds_Crm_Expense> getviewByEmpId(String empId) throws Exception {
		// TODO Auto-generated method stub
		return crmexpenserepository.getviewByEmpId(empId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String UpdateCRM(CrmTdsBean bean) throws Exception {
		// TODO Auto-generated method stub
		Tds_crm_Images tdsimage = null;
		String msg = "";
		try {
			crmexpenserepository.UpdateCRM(bean);
			// Deleting Images
			crmexpenserepository.deleteImagesByCrmId(Long.valueOf(bean.getCrm_id()));
			// new Images
			for (Entry<String, Object> entry : bean.getImagefiles().entrySet()) {
				System.out.println("MapKey = " + entry.getKey() + ", MapValue = " + entry.getValue());

				tdsimage = new Tds_crm_Images();
				tdsimage.setCrm_id(Long.valueOf(bean.getCrm_id()));
				tdsimage.setFin_year_id(Long.valueOf(bean.getFinyear()));
				tdsimage.setView_path(entry.getKey());
				tdsimage.setImage_path((entry.getValue()).toString());
				transactionalRepository.persist(tdsimage);
			}
			// old Images
			for (Tds_crm_Images td : bean.getUpdateimagefiles()) {
				tdsimage = new Tds_crm_Images();
				tdsimage.setCrm_id(Long.valueOf(bean.getCrm_id()));
				tdsimage.setFin_year_id(Long.valueOf(bean.getFinyear()));
				tdsimage.setView_path(td.getView_path());
				tdsimage.setImage_path(td.getImage_path());
				transactionalRepository.persist(tdsimage);
			}
			msg = "Updated Successfully!";
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return msg;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String confirmCrm(String ids) throws Exception {
		// TODO Auto-generated method stub
		Tds_Crm_Expense tdscrm = null;
		String msg = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (ids.contains("[") || ids.contains("]")) {
				ids = ids.replaceAll("\\[", "").replaceAll("\\]", "");
			}

			String[] idlist = ids.split(",");

			System.out.println("idlist :" + ids);
			for (int i = 0; i < idlist.length; i++) {
				tdscrm = crmexpenserepository.getObjById(Long.valueOf(idlist[i]));

				// TDS SUMMARY TABLE
				Sub_Company subcomp = companyMasterService.getsubcomCompanyByCode(tdscrm.getCrm_sub_comp_code());
				String tablename = "TDS_CRM_MASTER_ANNUAL_" + String.format("%02d", subcomp.getSubcomp_id());
				// Period pr = periodmasterservice.getCurrentPeriod();

				Period_Tds pr = periodmasterservice.getTdsPeriodByDate(sdf.parse(tdscrm.getCrm_exps_date()));

				Object[] obj = (Object[]) crmexpenserepository.findObjByCustType(tablename,
						tdscrm.getCrm_cust_hcp_no().toString(), tdscrm.getCrm_cust_type());

				if (obj == null) {
					crmexpenserepository.SaveTds_crm_master_annual_(tdscrm, tablename, pr);
				} else {
					crmexpenserepository.UpdateAnnualTdsCrm(tablename, tdscrm.getCrm_cust_hcp_no(),
							tdscrm.getCrm_cust_type(), obj, tdscrm, pr);
				}

				tdscrm.setCrm_confirm("Y");
				transactionalRepository.update(tdscrm);
			}
			msg = "Confirmed !";
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return msg;
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
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@Override
	public List<Tds_crm_Images> geImageBycrmId(Long crmId) throws Exception {
		// TODO Auto-generated method stub

		return crmexpenserepository.geImageBycrmId(crmId);
	}

	public List<TdsApplicableStatementReport> getTdsSummaryReport(String sub_comp, String custType, Character tdsReq,
			String Pan_req) {
		List<TdsApplicableStatementReport> summ = crmexpenserepository.getTdsSummarryData(sub_comp, custType, tdsReq);
		if (custType.equals("WHL")) {
			List<TdsApplicableStatementReport> summ1 = null;
			if (Pan_req.equals("Y")) {
				summ1 = summ.stream().filter(x -> !x.getHcp_pan_number().equals("")).collect(Collectors.toList());
			} else {
				summ1 = summ.stream().filter(x -> x.getHcp_pan_number().equals("")).collect(Collectors.toList());
			}
			return summ1;
		} else {
			return summ;
		}
	}

	public List<TdsApplicableProduct> getTdsDetail(String hcp_cust_id, String emp_id, HttpSession session)
			throws Exception {
		Date d = new Date();
		Company company = (Company) session.getServletContext().getAttribute(COMPANY_DATA);

		Period p = periodmasterrepository.getPeriodMasterByCompany(company.getCompany());
		String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();

		//String monthcd = financialyearservice.getCurrentFinancialYear(companyCode).getFin_year();
		
		String monthcd = financialyearservice.getFinYearForTDSVet(companyCode).getFin_year();
		System.out.println("monthcd " + monthcd);
		String cd = companyMasterService.getSubCompanyByEmpId(emp_id).getSubcomp_code().trim();
		List<TdsApplicableProduct> modlist = crmexpenserepository.getTdsDetailData(cd, hcp_cust_id, monthcd);

		return modlist;

	}

	@Override
	public List<SG_d_parameters_details> getTdsCrmPercentageParameter() throws Exception {
		// TODO Auto-generated method stub
		return parameterrepository.getTdsCrmPercentageParameter();
	}

	@Override
	public List<Sfa_Field_Master> getBulkse(String geog_lvl1_hq) {
		// TODO Auto-generated method stub
		return parameterrepository.getBulkse(geog_lvl1_hq);
	}

	@Override
	public List<Sfa_Retail_Consumer> getBulkAddress(String se, String custType) {
		// TODO Auto-generated method stub
		return crmexpenserepository.getBulkAddress(se, custType);
	}

	@Override
	public List<Sfa_Retail_Consumer> getDataForCrmTemplate(String custids, String se, String custType)
			throws Exception {
		// TODO Auto-generated method stub
		return crmexpenserepository.getDataForCrmTemplate(custids, se, custType);
	}

	@Override
	public String GenerateCrmTemplateUpload(List<Sfa_Retail_Consumer> list, String custtype) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
		String filename = "CRM_" + custtype + "_" + sdf2.format(new Date());// +"_"+ new Date().getTime() + ".xlsx";
		String filepath = REPORT_FILE_PATH + filename;
		System.out.println("filepath:::::::::" + filepath);
		SXSSFWorkbook workbook = null;
		try {
			String MaxSerial = crmexpenserepository.getMaxCrmSerialNumber(filename);
			if (MaxSerial != null) {
				System.out.println("MaxSerial : " + MaxSerial);
				String[] name = MaxSerial.split("\\.");

				String serial = name[0].substring((name[0]).length() - 3);

				if (serial.length() == 3) {
					filename = "CRM_" + custtype + "_" + sdf2.format(new Date()) + "_" + "00"
							+ (Integer.parseInt(serial) + 1) + ".xlsx";
				} else {
					throw new Exception("Serial Long");
				}
			} else {
				filename = "CRM_" + custtype + "_" + sdf2.format(new Date()) + "_" + "001" + ".xlsx";
			}
			filepath = REPORT_FILE_PATH + filename;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			workbook = new SXSSFWorkbook(1);
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}

			SXSSFSheet sheet = workbook.createSheet("CRM Template");

			Row row = null;
			Cell cell = null;
			int rowCount = 0;
			int colCount = 0;
			CellStyle columnHeadingStyle = ReportFormats.columnHeadingNew(workbook);

			String headings[] = { "Customer Type", "Fs_id", "HQ", "SE", "Customer", "Addr1", "Addr2", "Addr3", "Addr4",
					"City", "Pincode", "Mobile", "Email", "PAN No", "HCP Unique No", "Expense Date",
					"Accounting Transaction Number", "Expense Details", "Qty", "Rate", "Value", "Gross Up Ind ?",
					"TDS Percentage", "Gross Value", "TDS Paid if any", "Interest paid if any", "Penalty paid if any" };

			row = sheet.createRow(rowCount);
			for (String heading : headings) {
				cell = ReportFormats.cellnew(row, colCount, heading, columnHeadingStyle);
				colCount++;
			}
			rowCount++;
			colCount = 0;

			for (Sfa_Retail_Consumer se : list) {
				row = sheet.createRow(rowCount);
				cell = ReportFormats.cellnew(row, colCount, custtype, null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getFsid().toString(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getLocname(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getFs_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getCust_name(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getAddr1(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getAddr2(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getAddr3(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getAddr4(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getCity(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getPincode(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getTel_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getEmail(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getPan_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, se.getHcp_no(), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, sdf.format(new Date()), null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, " ", null);
				colCount++;

				cell = ReportFormats.cellnew(row, colCount, " ", null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;
				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;
				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;

				cell = ReportFormats.cell(row, colCount, "N", null);
				colCount++;
				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;

				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;
				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;
				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;
				cell = ReportFormats.cellNum(row, colCount, 0d, null);
				colCount++;

				colCount = 0;
				rowCount++;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return filename;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void writeCrmDownloadFileLog(List<Sfa_Retail_Consumer> list, String custtype, String empid, String filename)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			List<Sub_Company> subcomplist = companyMasterService.getSubCompanyList(empid);
			Period_Tds ptds = periodmasterservice.getTdsPeriodByDate(new Date());
			// Save CrmGenHd
			Crm_GenHd hd = new Crm_GenHd();
			hd.setCrm_gen_date(new Date());
			hd.setCrm_sub_comp_code(subcomplist.get(0).getSubcomp_code());
			hd.setCrm_cust_type(custtype);
			hd.setCrm_finyear(ptds.getPeriod_fin_year());
			hd.setCrm_month(ptds.getPeriod_code());
			hd.setCrm_cycle(1l);
			hd.setUser_id(empid);
			hd.setStatus("A");
			hd.setFile_upload("N");
			hd.setFile_download("Y");
			hd.setCrm_appr_status("E");
			hd.setCrm_doc_no(filename);
			hd.setUpd_date(new Date());
			hd.setUpd_ip_add("");
			transactionalRepository.persist(hd);

			for (Sfa_Retail_Consumer s : list) {
				Crm_GenDtl dt = new Crm_GenDtl();

				dt.setCrm_gen_id(hd.getCrm_gen_id());
				dt.setCrm_gen_date(hd.getCrm_gen_date());
				dt.setCrm_cycle(hd.getCrm_cycle());
				dt.setCrm_sub_comp_code(hd.getCrm_sub_comp_code());
				dt.setCrm_finyear(hd.getCrm_finyear());
				dt.setCrm_month(hd.getCrm_month());
				dt.setCrm_date(new Date());
				dt.setCrm_hq_id(s.getLocid());
				dt.setCrm_fs_id(s.getFsid());
				dt.setCrm_div_id(s.getDivid());
				dt.setCrm_cust_id(s.getCust_id());
				dt.setCrm_cust_name(s.getCust_name());
				dt.setCrm_cust_addr1(s.getAddr1());
				dt.setCrm_cust_addr2(s.getAddr2());
				dt.setCrm_cust_addr3(s.getAddr3());
				dt.setCrm_cust_addr4(s.getAddr4());
				dt.setCrm_cust_city(s.getCity());
				dt.setCrm_cust_pincode(s.getPincode());
				dt.setCrm_cust_mobile(s.getTel_no());
				dt.setCrm_cust_email(s.getEmail());
				dt.setCrm_cust_panno(s.getPan_no());
				dt.setCrm_cust_hcp_no(s.getHcp_no());
				dt.setCrm_exps_date(new Date());
				dt.setCrm_acc_tran_no("");
				dt.setCrm_exps_details("");
				dt.setCrm_qty(BigDecimal.ZERO);
				dt.setCrm_rate(BigDecimal.ZERO);
				dt.setCrm_value(BigDecimal.ZERO);
				dt.setCrm_tds_paid(BigDecimal.ZERO);
				dt.setCrm_tds_penalty(BigDecimal.ZERO);
				dt.setCrm_tds_interest(BigDecimal.ZERO);
				dt.setGrossup_crm_value(BigDecimal.ZERO);
				dt.setGrossup_percentage(BigDecimal.ZERO);
				dt.setGrossup_ind("N");
				dt.setCrm_confirm("N");
				dt.setLast_mod_dt(new Date());
				dt.setLast_mod_by(empid);
				dt.setCrm_cust_type(hd.getCrm_cust_type());
				transactionalRepository.persist(dt);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String uploadCrmFile(MultipartFile file, String finyr, String company, String username) throws Exception {
		// TODO Auto-generated method stub
		String msg = null;
		try {
			File xlsxFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
			file.transferTo(xlsxFile);

			Workbook work = WorkbookFactory.create(xlsxFile);
			XSSFSheet sheet = (XSSFSheet) work.getSheetAt(0);

			int index = 0;
			XSSFRow headerRow = sheet.getRow(index);
			XSSFRow row = null;

			index = 1;

			Crm_GenHd crmgenhd = crmexpenserepository.getCrmGenhd(file.getOriginalFilename());
			// List<Crm_GenDtl>crmdtl
			// =crmexpenserepository.getCrmGenDt(crmgenhd.getCrm_gen_id());
			if (crmgenhd == null) {
				msg = "Uploading incorrect file, please upload system generated file";
				throw new Exception("Uploading incorrect file, please upload system generated file");
			}
			outer: while (sheet.getRow(index) != null) {
				row = sheet.getRow(index);

				CrmTdsBean bean = new CrmTdsBean();
				// bean.setSubcomp(companyMasterService.getSubCompanyList(username).get(0).getSubcomp_code());
				bean.setUsername(username);
				bean.setFinyear(finyr);
				bean.setCusttype(row.getCell(0).toString());
				bean.setHq(crmexpenserepository.getHqId(row.getCell(2).toString()).toString());

				// Long seid = crmexpenserepository.getFsId(row.getCell(3).toString());
				bean.setSe(new BigDecimal(row.getCell(1).toString()).setScale(0).toString());
				Long custid = null;
				if (!company.trim().equalsIgnoreCase(PFZ)) {
					custid = crmexpenserepository.getcustIdforHcpType(bean.getCusttype(),
							new BigDecimal(row.getCell(14).toString()).setScale(0).toString(),
							Long.valueOf(bean.getSe()));
				} else {
					custid = crmexpenserepository.getcustIdforHcpType(bean.getCusttype(), row.getCell(14).toString(),
							Long.valueOf(bean.getSe()));
				}
				bean.setCustid(custid.toString());

				bean.setCustname(row.getCell(4).toString());
				bean.setDivid(crmexpenserepository.getFsById(Long.valueOf(bean.getSe())).getDiv_id());
				bean.setAddr1(row.getCell(5) == null ? null : row.getCell(5).toString());
				bean.setAddr2(row.getCell(6) == null ? null : row.getCell(6).toString());
				bean.setAddr3(row.getCell(7) == null ? null : row.getCell(7).toString());
				bean.setAddr4(row.getCell(8) == null ? null : row.getCell(8).toString());

				bean.setCity(row.getCell(9) == null ? null : row.getCell(9).toString());
				bean.setPincode(row.getCell(10) == null ? null : row.getCell(10).toString());
				bean.setTelno(row.getCell(11) == null ? "" : row.getCell(11).toString());
				bean.setEmail(row.getCell(12) == null ? null : row.getCell(12).toString());
				bean.setPanno(row.getCell(13) == null ? null : row.getCell(13).toString());

				if (!company.trim().equalsIgnoreCase(PFZ)) {
					bean.setHcpno(new BigDecimal(row.getCell(14).toString()).setScale(0).toString());
				} else {
					bean.setHcpno(row.getCell(14).toString());
				}

				bean.setTrandate(MedicoResources.convert_DD_MM_YYYY_toDate_(row.getCell(15).toString()));
				bean.setTransactionno(row.getCell(16) == null ? null : row.getCell(16).toString());
				bean.setExpensedetail(row.getCell(17) == null ? null : row.getCell(17).toString());
				bean.setQty(row.getCell(18).toString());
				bean.setRate(row.getCell(19).toString());
				bean.setVal(row.getCell(20).toString());
				bean.setGrossvalind(row.getCell(21).toString());
				bean.setGrossupperc(row.getCell(22).toString());
				bean.setGrossval(row.getCell(23).toString());
				bean.setTdspaid(row.getCell(24).toString());
				bean.setIntrestpaid(row.getCell(25).toString());
				bean.setPenaltypaid(row.getCell(26).toString());
				bean.setCrmgenid(crmgenhd.getCrm_gen_id());

//				Crm_GenDtl crmgendtl= crmexpenserepository.getCrmGenDtl(crmgenhd.getCrm_gen_id(),
//						bean.getCusttype(), Long.valueOf(bean.getHq()), seid,Long.valueOf(bean.getDivid()), custid);
//				bean.setCrmgendtlid(crmgendtl.getCrm_gen_dtl_id());

				// Save CRM
				this.SaveCRMEntry(bean);

				// Update Intermediate Table
//				crmgendtl.setCrm_qty(new BigDecimal(bean.getQty()));
//				crmgendtl.setCrm_rate(new BigDecimal(bean.getRate()));
//				crmgendtl.setCrm_value(new BigDecimal(bean.getVal()));
//				crmgendtl.setGrossup_crm_value(new BigDecimal(bean.getGrossval()));
//				crmgendtl.setCrm_tds_paid(new BigDecimal(bean.getTdspaid()));
//				crmgendtl.setCrm_tds_interest(new BigDecimal(bean.getIntrestpaid()));
//				crmgendtl.setCrm_tds_penalty(new BigDecimal(bean.getPenaltypaid()));
//				crmgendtl.setCrm_acc_tran_no(bean.getTransactionno());
//				crmgendtl.setCrm_exps_details(bean.getExpensedetail());
//				crmgendtl.setCrm_exps_date(bean.getTrandate());
//			
//				crmgendtl.setGrossup_percentage(new BigDecimal(bean.getGrossupperc()));
//				crmgendtl.setGrossup_crm_value(new BigDecimal(bean.getGrossval()));
//				crmgendtl.setGrossup_ind(bean.getGrossvalind());

//				transactionalRepository.update(crmgendtl);

				index++;
			}

			crmgenhd.setFile_upload("Y");
			transactionalRepository.update(crmgenhd);

			msg = "SAVED";
		} catch (Exception e) {
			// msg ="FAILED";
			throw e;
			// TODO: handle exception
		}
		return msg;
	}

	@Override
	public List<Crm_GenHd> getCrmHdrByUserId(String userId) throws Exception {
		return crmexpenserepository.getCrmHdrByUserId(userId);
	}

	@Override
	public List<Tds_Crm_Expense> getTdsExpsByCrmGenId(Long crmGenId) throws Exception {
		return crmexpenserepository.getTdsExpsByCrmGenId(crmGenId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void confirmTdsCrmExpensesByGenHdrAndDtl(Long crmGenId, List<Long> genDtlIdsList) throws Exception {
		Tds_Crm_Expense tds_crm_exps = null;
		List<Long> id = new ArrayList<Long>();
		List<Tds_Crm_Expense> list = new ArrayList<>();

		try {
			String ids = null;
//			for(Long genDtlId : genDtlIdsList) {
//				tds_crm_exps = this.crmexpenserepository.getTdsCrmExpenseGenDtlByDtlAndHdrId(crmGenId, genDtlId);
//				tds_crm_exps.setCrm_confirm("Y");
//				this.transactionalRepository.update(tds_crm_exps);
//				id.add(tds_crm_exps.getCrm_id());
//			}

			list = crmexpenserepository.getTdsCrmExpenseByGenHdrId(crmGenId);
			for (Tds_Crm_Expense tds : list) {
				id.add(tds.getCrm_id());
			}
			ids = StringUtils.join(id, ',');
			System.out.println("ids : " + ids);
			this.confirmCrm(ids);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Map<String, Object> validateAndSetBeanForSave(MultipartFile file, String finyr, String company,
			String username) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		// crmBean
		CrmTdsBean bean = new CrmTdsBean();
		List<CrmTdsBean> list = new ArrayList<CrmTdsBean>();
		// flag
		boolean isok = false;
		boolean validCustType = false;
		// crmErrorBean
		CrmErrorBean errorbean = new CrmErrorBean();
		List<CrmErrorBean> errorlist = new ArrayList<CrmErrorBean>();
		Double val;
		Boolean isValZero = false;
		Double tdsPaid;
		Boolean isRowValid = false;
		Boolean isTdsPaidZero = false;
		String seperator = "|| ";
		List<String> custType = new ArrayList<String>();
		custType.add("WHL");
		custType.add("RTL");
		custType.add("HCP");
		String detailedErrorMsg = "";
		Long count = null;
		try {
			File xlsxFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
			file.transferTo(xlsxFile);

			boolean flag = crmexpenserepository.checkfilename(xlsxFile.getName());
			if (flag) {
				isok = false;
				isRowValid = false;
				validCustType = false;
				detailedErrorMsg = "File Already Uploaded";
				errorbean = new CrmErrorBean();
				errorbean.setErrors("");
				errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				errorlist.add(errorbean);
			}

			Workbook work = WorkbookFactory.create(xlsxFile);
			XSSFSheet sheet = (XSSFSheet) work.getSheetAt(0);

			int index = 0;
			XSSFRow headerRow = sheet.getRow(index);
			XSSFRow row = null;

			index = 1;
			// isok = true; // Temp
			// System.out.println("sheet.getRow(index"+sheet.getRow(index));
			while (sheet.getRow(index) != null) {
				isRowValid = true;
				isValZero = false;
				isTdsPaidZero = false;
				count = null;
				validCustType = true;
				errorbean = new CrmErrorBean();
				errorbean.setErrors("");
				row = sheet.getRow(index);
				bean = new CrmTdsBean();
				// validate and set Bean
				bean.setCusttype(row.getCell(0) == null ? "" : row.getCell(0).toString());
				bean.setHcpno(row.getCell(1) == null ? ""
						: (row.getCell(1).toString().contains(".") ? row.getCell(1).toString().split("\\.")[0]
								: row.getCell(1).toString()));
				bean.setCustname(row.getCell(2) == null ? "" : row.getCell(2).toString());
				bean.setTrandate(row.getCell(3) == null ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate_(row.getCell(3).toString()));
				bean.setTransactionno(row.getCell(4) == null ? "" : row.getCell(4).toString());
				bean.setExpensedetail(row.getCell(5) == null ? "" : row.getCell(5).toString());
				bean.setQty(row.getCell(6) == null ? "0"
						: row.getCell(6).toString().trim().equals("") ? "0" : row.getCell(6).toString().trim());
				bean.setRate(row.getCell(7) == null ? "0"
						: row.getCell(7).toString().trim().equals("") ? "0" : row.getCell(7).toString().trim());
				bean.setVal(row.getCell(8) == null ? "0"
						: row.getCell(8).toString().trim().equals("") ? "0" : row.getCell(8).toString().trim());
				bean.setGrossvalind(row.getCell(9) == null ? "N"
						: row.getCell(9).toString().trim().equals("") ? "N" : row.getCell(9).toString().trim());
				bean.setGrossupperc(row.getCell(10) == null ? "0"
						: row.getCell(10).toString().trim().equals("") ? "0" : row.getCell(10).toString().trim());
				bean.setGrossval(row.getCell(11) == null ? "0"
						: row.getCell(11).toString().trim().equals("") ? "0" : row.getCell(11).toString().trim());
				bean.setTdspaid(row.getCell(12) == null ? "0"
						: row.getCell(12).toString().trim().equals("") ? "0" : row.getCell(12).toString().trim());
				bean.setIntrestpaid(row.getCell(13) == null ? "0"
						: row.getCell(13).toString().trim().equals("") ? "0" : row.getCell(13).toString().trim());
				bean.setPenaltypaid(row.getCell(14) == null ? "0"
						: row.getCell(14).toString().trim().equals("") ? "0" : row.getCell(14).toString().trim());
				bean.setFinyear(finyr);
				bean.setCompany(company);
				bean.setUsername(username);
				bean.setPanno(row.getCell(15) == null ? "NA"
						: row.getCell(15).toString().trim().equals("") ? "NA" : row.getCell(15).toString().trim());
				// validating bean
				// check custtype
				if (bean.getCusttype().isEmpty() || bean.getCusttype().trim().length() == 0) {
					isRowValid = false;
					validCustType = false;
					detailedErrorMsg = "Customer Type is empty";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else if (custType.indexOf(bean.getCusttype().trim()) == -1) {
					isRowValid = false;
					validCustType = false;
					detailedErrorMsg = "Customer Type is invalid";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				}
				// check cust code
				if (bean.getHcpno().isEmpty() || bean.getHcpno().trim().length() == 0) {
					isRowValid = false;
					detailedErrorMsg = "Customer Code is empty";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else if (bean.getHcpno().trim().length() > 20) {
					isRowValid = false;
					detailedErrorMsg = "Customer Code must be less than or equal 20 characters";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else if (validCustType) {
					// fire queries and check//cust type is valid

					if (bean.getCusttype().trim().equals("WHL")) {
						count = crmexpenserepository.checkIfCustomerExists(bean.getHcpno());
					} else if (bean.getCusttype().trim().equals("RTL")) {
						count = crmexpenserepository.checkIfRetailerExists(bean.getHcpno());
					} else if (bean.getCusttype().trim().equals("HCP")) {
						count = crmexpenserepository.checkIfDoctorExists(bean.getHcpno());
					}

					if (count == null || count.equals(0L)) {
						isRowValid = false;
						detailedErrorMsg = "No record found for Customer Code: " + bean.getHcpno();
						errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
					}

				}

				// check customer field
				if (bean.getCustname().isEmpty() || bean.getCustname().trim().length() == 0) {
					isRowValid = false;
					detailedErrorMsg = "Customer field is empty";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else if (bean.getCustname().trim().length() > 50) {
					isRowValid = false;
					detailedErrorMsg = "Customer  must be less than or equal 50 characters";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				}

				// check tran no
				if (bean.getTransactionno().isEmpty() || bean.getTransactionno().trim().length() == 0) {
					isRowValid = false;
					detailedErrorMsg = "Acc Transaction No is empty";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else if (bean.getTransactionno().trim().length() > 20) {
					isRowValid = false;
					detailedErrorMsg = "Acc Transaction No must be less than or equal 20 characters";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				}

				// check value is not empty
				if (bean.getVal().isEmpty() || bean.getVal().trim().length() == 0) {
					isRowValid = false;
					detailedErrorMsg = "Value is empty";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else {
					try {
						val = Double.valueOf(bean.getVal());
						if (val.equals(0D))
							isValZero = true;
					} catch (NumberFormatException n1) {
						System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(n1));// uncomment
																										// asneeded --
																										// System.out.println("Error
																										// Occurred :" +
																										// new
																										// CodifyErrors().getErrorCode(e));//
																										// uncomment
																										// asneeded --;
						isRowValid = false;
						detailedErrorMsg = "Error while parsing Value as a number";
						errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
					}
				}

				// check if tds paid is not empty
				if (bean.getTdspaid().isEmpty() || bean.getTdspaid().trim().length() == 0) {
					isRowValid = false;
					detailedErrorMsg = "TDS Paid is empty";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				} else {
					try {
						tdsPaid = Double.valueOf(bean.getTdspaid());
						if (tdsPaid.equals(0D))
							isTdsPaidZero = true;
					} catch (NumberFormatException n2) {
						System.out.println("Error Occurred :" + new CodifyErrors().getErrorCode(n2));// uncomment
																										// asneeded --
																										// System.out.println("Error
																										// Occurred :" +
																										// new
																										// CodifyErrors().getErrorCode(e));//
																										// uncomment
																										// asneeded --;
						isRowValid = false;
						detailedErrorMsg = "Error while parsing TDS Paid as a number";
						errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
					}
				}

				if (isValZero && isTdsPaidZero) {
					isRowValid = false;
					detailedErrorMsg = "TdsPaid or Value must be greater than zero";
					errorbean.setErrors(errorbean.getErrors().concat(seperator + detailedErrorMsg));
				}

				if (!isRowValid) {
					// row is false
					errorbean.setCustname(bean.getCustname());
					errorbean.setHcp_no(bean.getHcpno());
					errorbean.setIndex_no(index);

					errorlist.add(errorbean);
					isok = false;
				}

				list.add(bean);

				index++;
			}
			System.out.println("errorlist : " + errorlist.size());
			if (!isok)
				map.put("errorlist", errorlist);

			map.put("isok", isok);
			map.put("tdslist", list);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> SaveDirectUpload(List<CrmTdsBean> list, String filename) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "FAILED";
		Tds_Crm_Upload tdstemp = null;
		List<Tds_Crm_Upload> errorlist = new ArrayList<Tds_Crm_Upload>();
		boolean flag = false;
		List<CrmErrorBean> errorbeanlist = new ArrayList<CrmErrorBean>();
		try {

			System.out.println("In Save : " + list.size() + " Rows to save For File " + filename);
			// truncate temp table
			crmexpenserepository.truncateCrmTemplate();
			// save temp table
			String username = null;
			String finyear = null;
			String custtype = null;
			int i = 0;
			for (CrmTdsBean bean : list) {
				i++;
				tdstemp = new Tds_Crm_Upload();
				tdstemp.setCustomertype(bean.getCusttype());
				tdstemp.setCustomercode(bean.getHcpno());
				tdstemp.setCustomer(bean.getCustname());
				tdstemp.setAccountingtransactionnumber(bean.getTransactionno());
				tdstemp.setExpensedetails(bean.getExpensedetail());
				tdstemp.setExpensedate(bean.getTrandate());
				tdstemp.setQty(new BigDecimal(bean.getQty()));
				tdstemp.setRate(new BigDecimal(bean.getRate()));
				tdstemp.setValue(new BigDecimal(bean.getVal()));
				tdstemp.setGrossupind(bean.getGrossvalind());
				tdstemp.setGrossvalue(new BigDecimal(bean.getGrossval()));
				tdstemp.setTdspaid(new BigDecimal(bean.getTdspaid()));
				tdstemp.setTds_percentage(bean.getGrossupperc());
				tdstemp.setInterestpaid(new BigDecimal(bean.getIntrestpaid()));
				tdstemp.setPenaltypaid(new BigDecimal(bean.getPenaltypaid()));
				tdstemp.setFilename(filename);
				tdstemp.setPan_no(bean.getPanno());
				tdstemp.setUploaded(N);

				username = bean.getUsername();
				finyear = bean.getFinyear();
				custtype = bean.getCusttype();
				transactionalRepository.persist(tdstemp);
			}
			System.out.println("i : " + i);
			// call procedure
			String subcomp = companyMasterService.getSubCompanyList(username).get(0).getSubcomp_code();
			crmexpenserepository.callcrmExpenseProcedure(custtype, subcomp, finyear, new Date(), username);
			// check uploaded = Y
			flag = crmexpenserepository.checkuploadedstatus();
			// write log
			crmexpenserepository.setArchiveTable();

			if (flag) {
				msg = "SUCCESS";
			} else {
				errorlist = crmexpenserepository.checkForUnuploadedRecords(filename);

				for (Tds_Crm_Upload up : errorlist) {
					CrmErrorBean bean = new CrmErrorBean();
					bean.setHcp_no(up.getCustomercode());
					bean.setCustname(up.getCustomer());
					bean.setErrors("Record Not Uploaded");
					bean.setIndex_no(up.getRowid().intValue());
					errorbeanlist.add(bean);
				}
			}
			// truncate temp table
			crmexpenserepository.truncateCrmTemplate();

			map.put("STATUS", msg);
			map.put("errorList", errorbeanlist);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return map;
	}

}
