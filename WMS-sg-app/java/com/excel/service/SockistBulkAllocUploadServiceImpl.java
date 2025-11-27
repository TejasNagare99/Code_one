package com.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
import com.excel.bean.HcpBulkUploadErrorBean;
import com.excel.bean.SpecialAllocationBean;
import com.excel.bean.StkBlkCsvUpldBean;
import com.excel.bean.StockistBulkUploadBean;
import com.excel.bean.BulkDoctorPdf.ProductDetails;
import com.excel.exception.MedicoException;
import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.BlkStkReqProducts;
import com.excel.model.BlkStkReqTemp;
import com.excel.model.Blk_hcp_req_hdr;
import com.excel.model.BulkStkReqHeader;
import com.excel.model.BulkStkReqStockists;
import com.excel.model.CustomerMaster;
import com.excel.model.FinYear;
import com.excel.model.Location;
import com.excel.model.SmpItem;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.repository.CustomerMasterRepo;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.StockistBulkRepo;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;
@Primary
@Service
public class SockistBulkAllocUploadServiceImpl implements SockistBulkAllocUploadService, MedicoConstants {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private FinancialYearService financialyearservice;
	@Autowired
	private StockistBulkRepo stockBulkRepo;
	@Autowired
	private CustomerMasterRepo custMasterRepo;
	@Autowired
	private TransactionalRepository transactionalRepository;
	@Autowired
	private ProductMasterService productMasterService;
	@Autowired
	private DivisionMasterService divMasterService;
	@Autowired
	private FieldStaffRepository fstaffRepo;
	@Autowired
	private DoctorBulkAllocUploadServiceImpl docBulkAllocService;
	@Autowired
	private EmailTranWiseService emailtranwiseservice;
	@Autowired
	private LocationService locationService;
	@Autowired
	private TaskMasterService taskMasterService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private SpecialAllocationService specialAllocationService;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveBulkStockistUpload(StockistBulkUploadBean stockistBulkUploadBean) throws Exception {
		BulkStkReqHeader blk_stk_req_hdr = null;
		Map<String, Object> map = new HashedMap<>();
		try {
			FinYear fin = financialyearservice.getCurrentFinancialYear(stockistBulkUploadBean.getCompany());
			stockistBulkUploadBean.setBlk_csv_gen_date(null);
			stockistBulkUploadBean.setBlk_csvgen_usr_id("");
			stockistBulkUploadBean.setBlk_status("N");
			if (stockistBulkUploadBean.getTransactionMode().trim().equals("M")) {
				blk_stk_req_hdr = stockBulkRepo.getObjById(stockistBulkUploadBean.getReqIdForModify());
			} else {
				blk_stk_req_hdr = this.saveblk_stk_req_hdr(stockistBulkUploadBean, fin);
			}

			BulkStkReqStockists blk_stk_req_stockists = null;
			for (int i = 0; i < stockistBulkUploadBean.getStkDetails().size(); i++) {
				blk_stk_req_stockists = new BulkStkReqStockists();

				blk_stk_req_stockists.setBlk_stk_req_id(blk_stk_req_hdr.getBlk_stk_req_id());
				blk_stk_req_stockists.setBlk_stk_req_date(new Date());
				blk_stk_req_stockists.setBlk_stk_req_no(blk_stk_req_hdr.getBlk_stk_req_no());
				blk_stk_req_stockists.setFin_year_id(Long.valueOf(fin.getFin_year()));
				blk_stk_req_stockists.setCust_id(stockistBulkUploadBean.getStkDetails().get(i).getCust_id());
				blk_stk_req_stockists.setCust_name(stockistBulkUploadBean.getStkDetails().get(i).getStk_name());
				blk_stk_req_stockists.setSap_inv_no(stockistBulkUploadBean.getStkDetails().get(i).getSap_inv_number());
				blk_stk_req_stockists.setSap_inv_date(stockistBulkUploadBean.getStkDetails().get(i).getSap_inv_date());
				blk_stk_req_stockists
						.setSap_schm_remarks(stockistBulkUploadBean.getStkDetails().get(i).getSap_inv_remarks());
				blk_stk_req_stockists.setCompany(stockistBulkUploadBean.getCompany());
				blk_stk_req_stockists.setErp_cust_cd(stockistBulkUploadBean.getStkDetails().get(i).getErp_cust_cd());
				blk_stk_req_stockists.setServ_req_no(stockistBulkUploadBean.getStkDetails().get(i).getSap_inv_number());
				blk_stk_req_stockists.setRequestor_id(stockistBulkUploadBean.getFieldStaffId());
				blk_stk_req_stockists.setStk_ins_usr_id(stockistBulkUploadBean.getEmpId());
				blk_stk_req_stockists.setStk_mod_usr_id("");
				blk_stk_req_stockists.setStk_ins_dt(new Date());
				blk_stk_req_stockists.setStk_mod_dt(new Date());
				blk_stk_req_stockists.setStk_ins_ip_add(stockistBulkUploadBean.getBlk_ip_add());
				blk_stk_req_stockists.setStk_mod_ip_add("");
				blk_stk_req_stockists.setStk_status("A");
				transactionalRepository.persist(blk_stk_req_stockists);
				map.put("reqNo", blk_stk_req_hdr.getBlk_stk_req_no());
				map.put("reqId", blk_stk_req_hdr.getBlk_stk_req_id());
			}
		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BulkStkReqHeader saveblk_stk_req_hdr(StockistBulkUploadBean stockistBulkUploadBean, FinYear fin)
			throws Exception {
		BulkStkReqHeader blk_stk_req_hdr = null;
		try {
			blk_stk_req_hdr = new BulkStkReqHeader();
			String num = (stockistBulkUploadBean.getDivnm().substring(0, 3)) + "-"
					+ String.format("%02d", (new Date().getMonth() + 1)) + (fin.getFin_year().substring(2, 4));
			blk_stk_req_hdr.setBlk_stk_req_date(new Date());
			blk_stk_req_hdr.setBlk_stk_req_no(num);
			blk_stk_req_hdr.setFin_year_id(Long.valueOf(fin.getFin_year()));
			blk_stk_req_hdr.setCompany(stockistBulkUploadBean.getCompany());
			blk_stk_req_hdr.setNo_of_stockists(
					stockistBulkUploadBean.getNoOfStockist() == null ? 0l : stockistBulkUploadBean.getNoOfStockist());
			blk_stk_req_hdr.setBlkstk_ins_usr_id(stockistBulkUploadBean.getEmpId());
			System.out.println("stockistBulkUploadBean.getLocId():::"+stockistBulkUploadBean.getLocationId());
			blk_stk_req_hdr.setLoc_id(stockistBulkUploadBean.getLocationId());
			blk_stk_req_hdr.setBlkstk_ins_dt(new Date());
			blk_stk_req_hdr.setBlkstk_mod_dt(null);
			blk_stk_req_hdr.setBlkstk_ins_ip_add(stockistBulkUploadBean.getBlk_ip_add());
			blk_stk_req_hdr.setBlkstk_mod_ip_add("");
			blk_stk_req_hdr.setBlkstk_mod_usr_id("");
			blk_stk_req_hdr.setBlkstk_status(stockistBulkUploadBean.getBlk_status());
			blk_stk_req_hdr.setBlkstk_csv_name("");
			blk_stk_req_hdr.setBlkstk_csv_gen_date(new Date());
			blk_stk_req_hdr.setBlkstk_csvgen_usr_id("");
			blk_stk_req_hdr.setBlkstk_appr_ind("E");
			blk_stk_req_hdr.setStk_mst_type(stockistBulkUploadBean.getStkMasterToBeUsed());
			blk_stk_req_hdr.setFile_download("N");
			blk_stk_req_hdr.setFile_upload("N");
			blk_stk_req_hdr.setBlkstk_confirm_ind("N");
			transactionalRepository.persist(blk_stk_req_hdr);
			blk_stk_req_hdr.setBlk_stk_req_no(blk_stk_req_hdr.getBlk_stk_req_no() + "-"
					+ String.format("%05d", blk_stk_req_hdr.getBlk_stk_req_id()));
			transactionalRepository.update(blk_stk_req_hdr);
		} catch (Exception e) {
			throw e;
		}
		return blk_stk_req_hdr;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveBulkStockistproducts(StockistBulkUploadBean stockistBulkUploadBean)
			throws Exception {
		BlkStkReqProducts blk_stk_req_products = null;
		BulkStkReqHeader blk_stk_req_hdr = null;
		Map<String, Object> map = new HashedMap<>();
		try {
			FinYear fin = financialyearservice.getCurrentFinancialYear(stockistBulkUploadBean.getCompany());
			stockistBulkUploadBean.setBlk_csv_gen_date(null);
			stockistBulkUploadBean.setBlk_csvgen_usr_id("");
			stockistBulkUploadBean.setBlk_status("N");

			// stockistBulkUploadBean.setBlk_mod_ip_add("");
			if (stockistBulkUploadBean.getTransactionMode().trim().equals("M")) {
				blk_stk_req_hdr = stockBulkRepo.getObjById(stockistBulkUploadBean.getReqIdForModify());
			} else {
				blk_stk_req_hdr = this.saveblk_stk_req_hdr(stockistBulkUploadBean, fin);
			}

			// insert blk_stk_req_products here
			for (int i = 0; i < stockistBulkUploadBean.getProdqtyModel().size(); i++) {
				blk_stk_req_products = new BlkStkReqProducts();
				blk_stk_req_products.setBlk_stk_req_id(blk_stk_req_hdr.getBlk_stk_req_id());
				blk_stk_req_products.setBlk_stk_req_date(new Date());
				blk_stk_req_products.setBlk_stk_req_no(blk_stk_req_hdr.getBlk_stk_req_no());
				blk_stk_req_products.setFin_year_id(Long.valueOf(fin.getFin_year()));
				blk_stk_req_products.setCompany(stockistBulkUploadBean.getCompany());
				blk_stk_req_products.setProd_id(stockistBulkUploadBean.getProdqtyModel().get(i).getProdId());
				blk_stk_req_products.setErp_prod_cd(stockistBulkUploadBean.getProdqtyModel().get(i).getProdcode());
				blk_stk_req_products.setProd_name(stockistBulkUploadBean.getProdqtyModel().get(i).getProdname());
				blk_stk_req_products
						.setStd_qty(new BigDecimal(stockistBulkUploadBean.getProdqtyModel().get(i).getQty()));
				blk_stk_req_products.setPrd_ins_usr_id(stockistBulkUploadBean.getEmpId());
				blk_stk_req_products.setPrd_mod_usr_id(null);
				blk_stk_req_products.setPrd_ins_dt(new Date());
				blk_stk_req_products.setPrd_mod_dt(null);
				blk_stk_req_products.setPrd_ins_ip_add(stockistBulkUploadBean.getBlk_ip_add());
				blk_stk_req_products.setPrd_mod_ip_add(null);
				blk_stk_req_products.setPrd_status("Y");
				blk_stk_req_products.setSmp_prod_cd(stockistBulkUploadBean.getProdqtyModel().get(i).getProdcode());
				transactionalRepository.persist(blk_stk_req_products);
			}
			map.put("reqNo", blk_stk_req_hdr.getBlk_stk_req_no());
			map.put("reqId", blk_stk_req_hdr.getBlk_stk_req_id());
		} catch (Exception e) {
			throw e;
		}
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> modifyBulkUploadStockists(StockistBulkUploadBean stockistBulkUploadBean)
			throws Exception {
		stockBulkRepo.deleteBulkStockistUploadBeforeModify(stockistBulkUploadBean.getReqIdForModify());
		return this.saveBulkStockistUpload(stockistBulkUploadBean);
	}

	@Override
	public List<BulkStkReqHeader> getReqNoByStatus(String status, String iscnfrmd) throws Exception {
		return stockBulkRepo.getReqNoByStatus(status, iscnfrmd);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void ConfirmStockistBlkAlloc(Long reqId, String empId, String ipaddr) throws Exception {
		BulkStkReqHeader hdr = null;
		try {
			hdr = stockBulkRepo.getBlkStkReqHdrById(reqId);
			if (hdr == null) {
				throw new MedicoException("Request Not Found");
			}
			hdr.setBlkstk_confirm_ind("A");
			hdr.setBlkstk_mod_dt(new Date());
			hdr.setBlkstk_mod_usr_id(empId);
			hdr.setBlkstk_mod_ip_add(ipaddr);
			transactionalRepository.update(hdr);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> modifyBulkStkUploadProducts(StockistBulkUploadBean stockistBulkUploadBean)
			throws Exception {
		stockBulkRepo.deleteBulkStockistProductUploadBeforeModify(stockistBulkUploadBean.getReqIdForModify());
		return this.saveBulkStockistproducts(stockistBulkUploadBean);
	}

	@Override
	public List<Object[]> getHdrAndStksForCsvDownload(Long blkStkReqHdrId) throws Exception {
		return stockBulkRepo.getHdrAndStksForCsvDownload(blkStkReqHdrId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getExcelForBulkUpload(List<Object[]> hdrAndStkList, List<Object[]> prodList, String empId,
			BulkStkReqHeader blk_stk_header) throws Exception {
		StringBuffer path = null;
		String fileName = "";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		XSSFWorkbook workbook = null;
		try {
			fileName = "StockistBulkUpload" + new Date().getTime() + ".xlsx";
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
				throw e;
			}

			XSSFSheet sheet = workbook.createSheet(fileName);
			// lock the sheet
			sheet.protectSheet("password");
			// allow resizing of columns
			sheet.lockFormatColumns(false);
			// unlock cells after locking entire sheet
			XSSFCellStyle unlockedCellStyle = workbook.createCellStyle();
			unlockedCellStyle.setLocked(false);
			// array to unlock particular columns
			// cust master case editable values
			List<Integer> cust_master_editable_cells = Arrays.asList(15, 16, 17, 18, 19);// also anything after 23
			// no master case editable values
			List<Integer> no_master_locked_cells = Arrays.asList(0, 1, 2);// all others are editable

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;

			String[] header = null;
			header = new String[24 + prodList.size()];
			header[0] = "STK_BULK_ID";
			header[1] = "BLK_STK_REQ_NO";
			header[2] = "BLK_STK_REQ_DATE";
			header[3] = "SG_CUST_CD";
			header[4] = "ERP_CUST_CD";
			header[5] = "CUST_NAME_BILLTO";
			header[6] = "ENT_CUST_CD";
			header[7] = "Address_1_ShipTo";
			header[8] = "Address_2_ShipTo";
			header[9] = "Address_3_ShipTo";
			header[10] = "Address_4_ShipTo";
			header[11] = "Destination_ShipTo";
			header[12] = "STK_PIN_CODE";
			header[13] = "STK_Mobile";
			header[14] = "STK_Email";
			header[15] = "Observer1_Email";
			header[16] = "Observer2_Email";
			header[17] = "Observer3_Email";
			header[18] = "Observer4_Email";
			header[19] = "Observer5_Email";
			header[20] = "SERV_REQ_NO";
			header[21] = "SAP_INV_NO";
			header[22] = "SAP_INV_DATE";
			header[23] = "SAP_SCHM_REMARKS";
			for (int i = 1; i <= prodList.size(); i++) {
				header[23 + i] = prodList.get(i - 1)[1].toString() + " - " + prodList.get(i - 1)[2].toString();
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

			if (blk_stk_header.getStk_mst_type().trim().equals("M")) {
				for (Object[] objArr : hdrAndStkList) {
					colCount = 0;

					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, objArr[0] == null ? "" : objArr[0].toString(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, objArr[1] == null ? "" : objArr[1].toString(), null);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(df.format(sdf1.parse(objArr[2].toString())));
					colCount++;

					for (int i = 3; i <= 21; i++) {

						cell = ReportFormats.cell(row, colCount, objArr[i] == null ? "" : objArr[i].toString(),
								cust_master_editable_cells.contains(i) ? unlockedCellStyle : null);
						colCount++;
					}

					cell = row.createCell(colCount);
					cell.setCellValue(df.format(sdf1.parse(objArr[22].toString())));
					colCount++;

					cell = ReportFormats.cell(row, colCount, objArr[23] == null ? "" : objArr[23].toString(), null);
					colCount++;

					for (int i = 0; i < prodList.size(); i++) {
						cell = row.createCell(colCount);
						cell.setCellValue(Long.valueOf((prodList.get(i)[3]).toString()));
						// product qty fields need to be editable
						cell.setCellStyle(unlockedCellStyle);
						colCount++;
					}

					rowCount++;
					count++;
				}
			} else {
				// no master case
				for (int j = 0; j < blk_stk_header.getNo_of_stockists(); j++) {
					colCount = 0;

					row = sheet.createRow(rowCount);

					cell = ReportFormats.cell(row, colCount, blk_stk_header.getBlk_stk_req_id().toString(), null);
					colCount++;

					cell = ReportFormats.cell(row, colCount, blk_stk_header.getBlk_stk_req_no().toString(), null);
					colCount++;

					cell = row.createCell(colCount);
					cell.setCellValue(df.format(sdf1.parse(blk_stk_header.getBlk_stk_req_date().toString())));
					colCount++;

					for (int i = 3; i <= 23; i++) {
						cell = ReportFormats.cell(row, colCount, "", unlockedCellStyle);
						colCount++;
					}

					for (int i = 0; i < prodList.size(); i++) {
						cell = row.createCell(colCount);
						cell.setCellValue(Long.valueOf((prodList.get(i)[3]).toString()));
						// product qty fields need to be editable
						cell.setCellStyle(unlockedCellStyle);
						colCount++;
					}

					rowCount++;
					count++;
				}
			}

			FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
			workbook.write(fileOutputStream);
			workbook.close();
			fileOutputStream.close();

			blk_stk_header.setBlkstk_csvgen_usr_id(empId);
			blk_stk_header.setBlkstk_csv_gen_date(new Date());
			blk_stk_header.setBlkstk_csv_name(fileName);
			blk_stk_header.setFile_download("Y");
			transactionalRepository.update(blk_stk_header);

		} catch (Exception e) {
			throw e;
		} finally {
			if (workbook != null)
				workbook.close();
		}

		return fileName;
	}

	@Override
	public List<BulkStkReqHeader> getRequestListFromBlkStkHdr(boolean isgen) throws Exception {
		return stockBulkRepo.getRequestListFromBlkStkHdr(isgen);
	}

	// read from excel methods

	// get product ids from prod codes
	private Long[] getproductIdsForStockistBulkUploadExcel(XSSFRow headerRow) throws Exception {
		String prodname = null;
		Long no_of_products = 0L;
		String[] prodnamearr = new String[2];
		Long prodId = null;
		Long[] productIds = new Long[10];
		String prodcode = null;
		for (int i = 1; i <= 10; i++) {
			if (headerRow.getCell(23 + i) != null) {
				prodname = headerRow.getCell(23 + i).toString();
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
		return productIds;
	}

	// get StkBlkCsvUpldBean with data
	private StkBlkCsvUpldBean getBeanWithData(XSSFRow currRow, Long[] productIds) throws Exception {

		StkBlkCsvUpldBean stkblkCsvUpldBean = new StkBlkCsvUpldBean();
		stkblkCsvUpldBean.setStk_bulk_id(Double.valueOf(currRow.getCell(0).toString()).longValue());
		stkblkCsvUpldBean.setBlk_stk_req_no(currRow.getCell(1).toString());
		stkblkCsvUpldBean.setBlk_stk_req_date(currRow.getCell(2).toString());
		stkblkCsvUpldBean.setSg_cust_cd(currRow.getCell(3).toString());
		stkblkCsvUpldBean.setErp_cust_cd(currRow.getCell(4).toString());
		stkblkCsvUpldBean.setCust_name_billto(currRow.getCell(5).toString());
		stkblkCsvUpldBean.setEnt_cust_cd(currRow.getCell(6).toString());
		stkblkCsvUpldBean.setAddress_1_shipto(currRow.getCell(7).toString());
		stkblkCsvUpldBean.setAddress_2_shipto(currRow.getCell(8).toString());
		stkblkCsvUpldBean.setAddress_3_shipto(currRow.getCell(9).toString());
		stkblkCsvUpldBean.setAddress_4_shipto(currRow.getCell(10).toString());
		stkblkCsvUpldBean.setDestination_shipto(currRow.getCell(11).toString());
		stkblkCsvUpldBean.setStk_pin_code(currRow.getCell(12).toString());
		stkblkCsvUpldBean.setStk_mobile(currRow.getCell(13).toString());
		stkblkCsvUpldBean.setStk_email(currRow.getCell(14).toString());
		stkblkCsvUpldBean.setObserver1_email(currRow.getCell(15).toString());
		stkblkCsvUpldBean.setObserver2_email(currRow.getCell(16).toString());
		stkblkCsvUpldBean.setObserver3_email(currRow.getCell(17).toString());
		stkblkCsvUpldBean.setObserver4_email(currRow.getCell(18).toString());
		stkblkCsvUpldBean.setObserver5_email(currRow.getCell(19).toString());
		stkblkCsvUpldBean.setServ_req_no(currRow.getCell(20).toString());
		stkblkCsvUpldBean.setSap_inv_no(currRow.getCell(21).toString());
		stkblkCsvUpldBean.setSap_inv_date(currRow.getCell(22).toString());
		stkblkCsvUpldBean.setSap_schm_remarks(currRow.getCell(23).toString());

		CustomerMaster custMast = this.custMasterRepo.getCustIdByCustCd(stkblkCsvUpldBean.getSg_cust_cd());
		if (custMast != null)
			stkblkCsvUpldBean.setCustId(custMast.getCust_id());

		// also set the product fields
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				stkblkCsvUpldBean.setProd_id1(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty1(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 1) {
				stkblkCsvUpldBean.setProd_id2(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty2(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 2) {
				stkblkCsvUpldBean.setProd_id3(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty3(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 3) {
				stkblkCsvUpldBean.setProd_id4(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty4(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 4) {
				stkblkCsvUpldBean.setProd_id5(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty5(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 5) {
				stkblkCsvUpldBean.setProd_id6(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty6(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 6) {
				stkblkCsvUpldBean.setProd_id7(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty7(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 7) {
				stkblkCsvUpldBean.setProd_id8(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty8(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 8) {
				stkblkCsvUpldBean.setProd_id9(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty9(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
			if (i == 9) {
				stkblkCsvUpldBean.setProd_id10(productIds[i] == null ? 0 : productIds[i]);
				stkblkCsvUpldBean
						.setProd_qty10(currRow.getCell(24 + i) == null || currRow.getCell(24 + i).toString().isEmpty()
								? BigDecimal.ZERO
								: BigDecimal.valueOf(Double.valueOf(currRow.getCell(24 + i).toString())));
			}
		}

		return stkblkCsvUpldBean;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> validateExcelUploadForTemp(MultipartFile file, Long locId) throws Exception {
		Workbook work = null;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		boolean fileHasErrors = false;
		String filename = null;
		Map<Integer, List<String>> errorListMap = new HashMap<Integer, List<String>>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<StkBlkCsvUpldBean> stockistBulkUpload = new ArrayList<StkBlkCsvUpldBean>();
		Long[] productIds = null;
		try {
			List<String> errorList = new ArrayList<>();
			File xlsxFile = new File("D:\\sg\\tempStkFile" + "\\" + file.getOriginalFilename());
			file.transferTo(xlsxFile);
			XSSFRow currRow = null;
			work = WorkbookFactory.create(xlsxFile);
			XSSFSheet sheet = (XSSFSheet) work.getSheetAt(0);
			int index = 1;
			// get header
			BulkStkReqHeader blk_stk_header = null;

			// check for number of valid product codes and get the product ids from them
			XSSFRow headerRow = sheet.getRow(0);

			if (headerRow == null) {
				throw new MedicoException("Invalid Data Format.Please check submitted data.");
			}

			// get product ids for entered products
			productIds = this.getproductIdsForStockistBulkUploadExcel(headerRow);

			while (sheet.getRow(index) != null) {

				errorList = new ArrayList<>();
				currRow = sheet.getRow(index);

				if (index == 1) {
					blk_stk_header = this.stockBulkRepo
							.getBlkStkReqHdrById(Double.valueOf(currRow.getCell(0).toString()).longValue());
					if (blk_stk_header == null) {
						throw new MedicoException("Invalid header Id entered");
					}
				}

				StkBlkCsvUpldBean stkblkCsvUpldBean = this.getBeanWithData(currRow, productIds);

				System.out.println("rownum::::" + index);
				System.out.println("data::::" + stkblkCsvUpldBean.toString());
				Set<ConstraintViolation<StkBlkCsvUpldBean>> violations = validator.validate(stkblkCsvUpldBean);
				if (violations.size() > 0) {
					violations.forEach(vol -> System.out.println("Error::>" + vol.getMessage()));
					errorList.addAll(violations.stream().map(vol -> vol.getMessage()).collect(Collectors.toList()));
					fileHasErrors = true;
				}
				// validate important fields manually
				// in no master case
				// check for valid sg_cust_cd and correct erp_cust_cd 
				
				if(blk_stk_header.getStk_mst_type().equalsIgnoreCase("N") && currRow.getCell(3)!=null && currRow.getCell(4)!=null) {
					CustomerMaster custMaster =   this.custMasterRepo.getErpCustCdByCustCdAndLocation(currRow.getCell(3).toString(),locId);
					if(custMaster == null) {
						errorList.add("Invalid Customer Code : Customer Code is incorrect or Customer is not attached to your location.");
					}
					else if(custMaster.getErp_cust_cd().trim().equals(currRow.getCell(4).toString().trim())) {
						errorList.add("Invalid ERP Customer Code");
					}
				}
				// collect all these errors and add it to the error list map
				// if no errors are found then add this data to the success data file
				// else clear the success data file if it has any records to decrease memory
				// consumption
				if (fileHasErrors) {
					errorListMap.put(index, errorList);
					if (!stockistBulkUpload.isEmpty())
						stockistBulkUpload.clear();
				} else
					stockistBulkUpload.add(stkblkCsvUpldBean);
				index++;
			}

			returnMap.put("hasErrors", fileHasErrors);
			if (!fileHasErrors) {
				// put data beans
				returnMap.put("hdrRecord", blk_stk_header);
				returnMap.put("data", stockistBulkUpload);
			} else {
				//create excel and return file name
				filename = this.bulkUploadErrorFile(errorListMap,file.getName());
				returnMap.put("filename",filename);
				returnMap.put("errorListMap", errorListMap);
			}
		} finally {
			if (work != null)
				work.close();
		}
		return returnMap;
	}
	
	
	private String bulkUploadErrorFile(Map<Integer, List<String>> errListMap,String upld_filename) throws Exception{
		String filename =  "bulkStkUploadErrorFile" + new Date().getTime() + ".xlsx";
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
			XSSFSheet sheet = workbook.createSheet("bulk-Stockist-Upload-Error-File");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			
			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Errors in Upload XLSX File with name : " + upld_filename);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, colCount, colCount+3));
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("RowNum");
			colCount++;
//			cell = row.createCell(colCount);
//			cell.setCellValue("Warning");
//			colCount++;
			cell = row.createCell(colCount);
			cell.setCellValue("Error List");
			colCount++;

			for (Map.Entry<Integer,List<String>> entry : errListMap.entrySet()) {

				rowCount += 1;
				colCount = 0;

				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				cell.setCellValue(entry.getKey());
				colCount++;

//				if (list.getWarning().size() > 0) {
//					for (int i = 0; i < list.getWarning().size(); i++) {
//						cell = row.createCell(colCount);
//						cell.setCellValue(list.getWarning().get(i));
//						colCount++;
//					}
//				} else {
//					cell = row.createCell(colCount);
//					cell.setCellValue("");
//					colCount++;
//				}
				for (int i = 0; i < entry.getValue().size(); i++) {
					cell = row.createCell(colCount);
					cell.setCellValue(entry.getValue().get(i));
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveDataToBulkStkReqTemp(String ip_addr, Long requestorFstaffId, BulkStkReqHeader blk_stk_header,
			List<StkBlkCsvUpldBean> data_for_temp_table, String filename, String empId) throws Exception {

		Long terrId = this.fstaffRepo.getTerrIdByFsId(requestorFstaffId);

		for (StkBlkCsvUpldBean stkUpldBean : data_for_temp_table) {
			BlkStkReqTemp blkStkTemp = new BlkStkReqTemp();
			blkStkTemp.setBlk_csv_name(filename);
			blkStkTemp.setBlk_stk_req_date(sdf.parse(stkUpldBean.getBlk_stk_req_date()));
			blkStkTemp.setBlk_stk_req_id(stkUpldBean.getStk_bulk_id());
			blkStkTemp.setBlk_stk_req_no(stkUpldBean.getBlk_stk_req_no());
			blkStkTemp.setBlk_upl_date(new Date());
			blkStkTemp.setBlk_upl_user(empId);
			blkStkTemp.setCompany(blk_stk_header.getCompany());
			blkStkTemp.setCust_cd(stkUpldBean.getSg_cust_cd());
			blkStkTemp.setCust_id(stkUpldBean.getCustId());
			blkStkTemp.setCust_name(stkUpldBean.getCust_name_billto());
			blkStkTemp.setEmail1(stkUpldBean.getObserver1_email());
			blkStkTemp.setEmail2(stkUpldBean.getObserver2_email());
			blkStkTemp.setEmail3(stkUpldBean.getObserver3_email());
			blkStkTemp.setEmail4(stkUpldBean.getObserver4_email());
			blkStkTemp.setEmail5(stkUpldBean.getObserver5_email());
			blkStkTemp.setErp_cust_cd(stkUpldBean.getErp_cust_cd());
			blkStkTemp.setFin_year_id(blk_stk_header.getFin_year_id().toString());

			blkStkTemp.setProd_id1(stkUpldBean.getProd_id1());
			blkStkTemp.setProd1_qty(stkUpldBean.getProd_qty1());
			blkStkTemp.setProd_id2(stkUpldBean.getProd_id2());
			blkStkTemp.setProd2_qty(stkUpldBean.getProd_qty2());
			blkStkTemp.setProd_id3(stkUpldBean.getProd_id3());
			blkStkTemp.setProd3_qty(stkUpldBean.getProd_qty3());
			blkStkTemp.setProd_id4(stkUpldBean.getProd_id4());
			blkStkTemp.setProd4_qty(stkUpldBean.getProd_qty4());
			blkStkTemp.setProd_id5(stkUpldBean.getProd_id5());
			blkStkTemp.setProd5_qty(stkUpldBean.getProd_qty5());
			blkStkTemp.setProd_id6(stkUpldBean.getProd_id6());
			blkStkTemp.setProd6_qty(stkUpldBean.getProd_qty6());
			blkStkTemp.setProd_id7(stkUpldBean.getProd_id7());
			blkStkTemp.setProd7_qty(stkUpldBean.getProd_qty7());
			blkStkTemp.setProd_id8(stkUpldBean.getProd_id8());
			blkStkTemp.setProd8_qty(stkUpldBean.getProd_qty8());
			blkStkTemp.setProd_id9(stkUpldBean.getProd_id9());
			blkStkTemp.setProd9_qty(stkUpldBean.getProd_qty9());
			blkStkTemp.setProd_id10(stkUpldBean.getProd_id10());
			blkStkTemp.setProd10_qty(stkUpldBean.getProd_qty10());

			blkStkTemp.setRequest_date(blkStkTemp.getBlk_stk_req_date());
			blkStkTemp.setRequest_no(blkStkTemp.getBlk_stk_req_no());
			blkStkTemp.setRequestor_id(requestorFstaffId);
			blkStkTemp.setSap_inv_date(sdf.parse(stkUpldBean.getSap_inv_date()));
			blkStkTemp.setSap_inv_no(stkUpldBean.getSap_inv_no());
			blkStkTemp.setSap_schm_remarks(stkUpldBean.getSap_schm_remarks());

			blkStkTemp.setTmp_ins_dt(new Date());
			blkStkTemp.setTmp_ins_ip_add(ip_addr);
			blkStkTemp.setTmp_ins_usr_id(empId);

			blkStkTemp.setAddress1(stkUpldBean.getAddress_1_shipto());
			blkStkTemp.setAddress2(stkUpldBean.getAddress_2_shipto());
			blkStkTemp.setAddress3(stkUpldBean.getAddress_3_shipto());
			blkStkTemp.setAddress4(stkUpldBean.getAddress_4_shipto());

			blkStkTemp.setCust_phone_no(stkUpldBean.getStk_mobile());
			blkStkTemp.setCust_email(stkUpldBean.getStk_email());
			blkStkTemp.setCust_pin_code(stkUpldBean.getStk_pin_code());

			blkStkTemp.setTerritory_id(terrId);

			this.transactionalRepository.persist(blkStkTemp);
		}

		blk_stk_header.setFile_upload("Y");
		this.transactionalRepository.update(blk_stk_header);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public AllocReqHeader setAllocReqHdrFromStkBlkTempRecordsAndSave(BlkStkReqTemp blkStkTempRecord, String finYear,
			String ipaddress, String periodCode, Long divId) throws Exception {
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
			header.setReq_ins_user_id(blkStkTempRecord.getBlk_upl_user());
			header.setReq_mod_dt(null);
			header.setReq_mod_ip_add(null);
			header.setReq_mod_user_id(null);

			header.setCompany(blkStkTempRecord.getCompany());
			String div_name = this.divMasterService.getDivNameByDivID(divId);
			header.setDivision(div_name);
			header.setRequestor_id(blkStkTempRecord.getRequestor_id());
			header.setFs_id(blkStkTempRecord.getRequestor_id());
			header.setTerritory_id(blkStkTempRecord.getTerritory_id());
			header.setRequest_no("S-" + "00000");// will be updated after saving
			header.setAlloc_type("S");
			header.setRequest_type("S");
			header.setFreight_value(BigDecimal.ZERO);
			header.setRecipient_type("S");
			header.setRecipient_name(blkStkTempRecord.getCust_name());
			header.setMcl_number(blkStkTempRecord.getErp_cust_cd());
			header.setRecipient_address1(blkStkTempRecord.getAddress1());
			header.setRecipient_address2(blkStkTempRecord.getAddress2());
			header.setRecipient_address3(blkStkTempRecord.getAddress3());
			header.setRecipient_address4(blkStkTempRecord.getAddress4());
			header.setRecipient_city("");
			header.setRecipient_pin(blkStkTempRecord.getCust_pin_code());
			header.setRecipient_phone(blkStkTempRecord.getCust_phone_no());
			header.setRecipient_email(blkStkTempRecord.getCust_email());
			header.setReq_remarks(blkStkTempRecord.getReq_remarks());
			header.setReq_total_value(BigDecimal.ZERO);
			header.setReq_status('E');
			header.setAlloc_appr_status('E');
			header.setService_req_no(blkStkTempRecord.getSap_inv_no());
			header.setDoc_letter("N");

			header.setSrt_number(blkStkTempRecord.getSap_inv_no());
			header.setSrt_date(blkStkTempRecord.getSap_inv_date());
			header.setSrt_remark(blkStkTempRecord.getSap_schm_remarks());

			header.setHcp_name(blkStkTempRecord.getCust_name());
			header.setBulk_upload_date(blkStkTempRecord.getBlk_upl_date());
			header.setBlk_hcp_req_id(blkStkTempRecord.getBlk_stk_req_id());
			header.setBlk_csv_name(blkStkTempRecord.getBlk_csv_name());
			header.setBlk_hcp_req_no(blkStkTempRecord.getBlk_stk_req_no());
			header.setHcp_unique_id(blkStkTempRecord.getCust_cd());
			header.setAddress1(blkStkTempRecord.getAddress1());
			header.setAddress2(blkStkTempRecord.getAddress2());
			header.setAddress3(blkStkTempRecord.getAddress3());
			header.setAddress4(blkStkTempRecord.getAddress4());
			header.setCity("");
			header.setPhone(blkStkTempRecord.getCust_phone_no());
			header.setHcp_email(blkStkTempRecord.getCust_email());
			header.setAddress_chg("N");
			header.setEntry_type("S");
			System.out.println("header-->\n" + header.toString());
			this.transactionalRepository.persist(header);
			header.setRequest_no("S-" + header.getAlloc_req_id());
			this.transactionalRepository.update(header);
			return header;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void pushStkBulkRecordsToAllocTables(Long bulkReqId, String finYear, String ipaddress, String periodCode,
			Long divId) throws Exception {
		SimpleDateFormat dd_MM_YYYY = new SimpleDateFormat("dd-mm-yyyyy");
		List<String> mailList = new ArrayList<String>();
		try {
			// change this query to get list of values
			List<BlkStkReqTemp> blkTempList = this.stockBulkRepo.getStkBlkTempRecordsByBlkHdrId(bulkReqId);
			AllocReqHeader header = null;
			AllocReqDet alloc_details = null;
			SpecialAllocationBean prodBean = null;
			if (blkTempList != null && !blkTempList.isEmpty()) {
				for (BlkStkReqTemp tempRecords : blkTempList) {
					header = this.setAllocReqHdrFromStkBlkTempRecordsAndSave(tempRecords, finYear, ipaddress,
							periodCode, divId);

					if (header != null) {
						String compCode = tempRecords.getCompany();

						// can use functionality of doc upload since it is the same
						if (tempRecords.getProd1_qty().compareTo(BigDecimal.ZERO) > 0)
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id1(),
									tempRecords.getProd1_qty());

						if (tempRecords.getProd2_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id2(),
									tempRecords.getProd2_qty());
						}
						if (tempRecords.getProd3_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id3(),
									tempRecords.getProd3_qty());
						}
						if (tempRecords.getProd4_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id4(),
									tempRecords.getProd4_qty());
						}
						if (tempRecords.getProd5_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id5(),
									tempRecords.getProd5_qty());
						}
						if (tempRecords.getProd6_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id6(),
									tempRecords.getProd6_qty());
						}
						if (tempRecords.getProd7_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id7(),
									tempRecords.getProd7_qty());
						}
						if (tempRecords.getProd8_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id8(),
									tempRecords.getProd8_qty());
						}
						if (tempRecords.getProd9_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId, tempRecords.getProd_id9(),
									tempRecords.getProd9_qty());
						}
						if (tempRecords.getProd10_qty().compareTo(BigDecimal.ZERO) > 0) {
							this.docBulkAllocService.saveAllocReqDet(header, compCode, divId,
									tempRecords.getProd_id10(), tempRecords.getProd10_qty());
						}

						// also save mails in the mail tables
						if (tempRecords.getEmail1() != null && tempRecords.getEmail1().trim().length() > 0) {
							mailList.add(tempRecords.getEmail1());
						}

						if (tempRecords.getEmail2() != null && tempRecords.getEmail2().trim().length() > 0) {
							mailList.add(tempRecords.getEmail2());
						}

						if (tempRecords.getEmail3() != null && tempRecords.getEmail3().trim().length() > 0) {
							mailList.add(tempRecords.getEmail3());
						}

						if (tempRecords.getEmail4() != null && tempRecords.getEmail4().trim().length() > 0) {
							mailList.add(tempRecords.getEmail4());
						}

						if (tempRecords.getEmail5() != null && tempRecords.getEmail5().trim().length() > 0) {
							mailList.add(tempRecords.getEmail5());
						}

						if (mailList != null && !mailList.isEmpty()) {
							emailtranwiseservice.saveEmailTranWise(mailList, "SBU", header.getAlloc_req_id(),
									header.getFin_year());
							mailList = new ArrayList<String>();
						}
						tempRecords.setAlloc_req_hdr_id(header.getAlloc_req_id());
						this.transactionalRepository.update(tempRecords);

						BulkStkReqHeader bulkStkReqHdr = this.entityManager.find(BulkStkReqHeader.class, bulkReqId);
						bulkStkReqHdr.setBlkstk_status("Y");
						this.transactionalRepository.update(bulkStkReqHdr);
					} else {
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
	public void selfApprovalStockistUpload(String reqId, String userRole, String empId, HttpServletRequest request,
			HttpSession session) throws Exception {
		List<Object> fstaffDtls = new ArrayList<Object>();
		BulkStkReqHeader hdr = null;
		BlkStkReqProducts prd = null;
		SmpItem smp = null;
		String approval = "";
		Long allocId;
		try {
			System.out.println("userRole : " + userRole);
			String ip_addr = request.getRemoteAddr();
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			String is_live = session.getServletContext().getAttribute(IS_LIVE).toString();
			hdr = this.stockBulkRepo.getObjById(Long.valueOf(reqId));

			List<TaskMaster> masters = null;
			if (hdr.getBlkstk_appr_ind().equals("E")) {
				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				Location location = locationService.getLocationNameByEmployeeId(empId);
				if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
					approval = MedicoConstants.STK_BLK_APPR_PIPL;
				} else {
					approval = MedicoConstants.STK_BLK_APPR_PFZ;
				}

				prd = stockBulkRepo.getProductsDetailsById(hdr.getBlk_stk_req_id()).get(0);

				if (prd.getSmp_prod_type().equals("Sample")) {
					allocId = 2l;
					masters = taskMasterService.getTask(null, null, null, approval, null, null, TASK_FLOW, allocId,
							null);
				} else {
					allocId = 1l;
					masters = taskMasterService.getTask(null, null, null, approval, null, null, TASK_FLOW, allocId,
							null);
				}

				if (masters.size() == 0) {
					throw new MedicoException("Task master record not found");
				}
				System.out.println("TASK " + masters.get(0).getTask_id());
				TaskMaster taskMaster = masters.get(0);
				List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
				if (task_Master_Dtls.size() == 0) {
					throw new MedicoException("Task master detail record not found");
				}

				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("initiator", empId);
				variables.put("initiator_desc", empId);
				variables.put("initiator_email", "");
				variables.put("tran_ref_id", hdr.getBlk_stk_req_id());
				variables.put("tran_type", approval);
				variables.put("inv_group", null);
				variables.put("loc_id", 0);
				variables.put("cust_id", 0L);
				variables.put("tran_no", hdr.getBlk_stk_req_no());
				variables.put("company_code", companyCode);
				variables.put("totaltask", masters.size());
				variables.put("amount", BigDecimal.ZERO);
				variables.put("escalatetime", null);
				variables.put("fin_year_id", financialyearservice.getCurrentFinancialYear(companyCode).getFin_year());
				variables.put("stock_point_id", 0);
				variables.put("doc_type", "SPB");
				variables.put("task_flow", TASK_FLOW);
				variables.put("remark", "");
				// if (header.getAlloc_type().trim().equals("D"))
				variables.put("fs_id", allocId);// for doctor request //variables.put("fs_id", allocId);
				// else
				// variables.put("fs_id", allocId); // for emergecy supply/meeting
				variables.put("approval_type", null);

				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("stockistBlkUpldAllocation",
						variables);
			}
			hdr.setBlkstk_appr_ind("F");
			this.transactionalRepository.update(hdr);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void finalApproval(Long blkReqId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) throws Exception {
		BulkStkReqHeader header = null;
		List<AllocReqHeader> reqlst = null;
		try {
			System.out.println("In Final Approval Of Blk Upload");

			System.out.println("BlkReqId : " + blkReqId);

			header = this.stockBulkRepo.getBlkStkReqHdrById(blkReqId);
			if (isapproved.equalsIgnoreCase("A")) {
				header.setBlkstk_appr_ind("A");
				header.setBlkstk_mod_dt(new Date());
				header.setBlkstk_mod_usr_id(last_approved_by);
				this.transactionalRepository.update(header);
				reqlst = specialAllocationService.getAllocReqHdsBy_BlkReqNo(header.getBlk_stk_req_no());
				for (AllocReqHeader hdr : reqlst) {
					hdr.setAlloc_appr_status('A');
					hdr.setReq_mod_dt(new Date());
					hdr.setReq_mod_user_id(last_approved_by);
					this.transactionalRepository.update(hdr);
				}

				stockBulkRepo.FinalAllocationApprovalProc(header.getBlk_stk_req_id(),
						last_approved_by, "0");
			} else {
				header.setBlkstk_appr_ind("D");
				header.setBlkstk_mod_dt(new Date());
				header.setBlkstk_mod_usr_id(last_approved_by);
				this.transactionalRepository.update(header);
				reqlst = specialAllocationService.getAllocReqHdsBy_BlkReqNo(header.getBlk_stk_req_no());
				for (AllocReqHeader hdr : reqlst) {
					hdr.setAlloc_appr_status('D');
					hdr.setReq_mod_dt(new Date());
					hdr.setReq_mod_user_id(last_approved_by);
					this.transactionalRepository.update(hdr);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public BulkStkReqHeader getObjById(Long reqId) throws Exception {
		return stockBulkRepo.getObjById(reqId);
	}

	@Override
	public List<BulkStkReqStockists> getBulkStockistDetailsByHdrReqId(Long hdBlkStkId) throws Exception {
		return stockBulkRepo.getBulkStockistDetailsByHdrReqId(hdBlkStkId);
	}

	@Override
	public List<BlkStkReqProducts> getProductsDetailsByBulkHeaderId(Long Id) throws Exception {
		return stockBulkRepo.getProductsDetailsByBulkHeaderId(Id);
	}

	@Override
	public String getBulkCsvNameByBulkTempId(Long blkStkTempId) throws Exception {
		return stockBulkRepo.getBulkCsvNameByBulkTempId(blkStkTempId);
	}

	@Override
	public String stockistBulkUploadExcelToPdf(String name) throws Exception {
		Workbook workbook = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			workbook = new XSSFWorkbook(new FileInputStream("D:/sg/tempStkFile/" + name));

			Sheet sheet = workbook.getSheetAt(0);

			List<BulkDoctorPdf> bulkDoctorPdfList = new ArrayList<>();

			List<ProductDetails> prodDetailsList = null;

			BulkDoctorPdf bean = new BulkDoctorPdf();

			Row headerRow = sheet.getRow(0);

			for (Row row : sheet) {

				if (row.getRowNum() == 0) {
					continue; 
				}
				
				if(row.getCell(0)!=null && !row.getCell(0).toString().equals("")) {
				bean = new BulkDoctorPdf();
				System.out.println("row.getCell(0).toString() : "+row.getCell(0).toString());
				bean.setBulk_unique_id(row.getCell(0).toString());
				bean.setSg_employee_no(row.getCell(3).toString());//sg cust cd
				bean.setHcp_unique_id(row.getCell(6).toString());//ent cust cd
				bean.setHcp_name(row.getCell(5).toString());//cust name bill to
				bean.setMcl_number(row.getCell(4).toString());//erp cust cd
				bean.setSrt_number(row.getCell(21).toString());//SAP INV NO	
				bean.setSrt_date(row.getCell(22).toString());//SAP REQ DT
				bean.setSrt_remarks(row.getCell(20).toString());//SERV_REQ_NO
				bean.setReq_remarks(row.getCell(23).toString());//SAP SCHM REMARKS
				bean.setRequest_no(row.getCell(1).toString());//BLK STK REQ NO
				bean.setRequest_date(row.getCell(2).toString());//BLK STK REQ DATE
				bean.setObserver1_email(row.getCell(15).toString());
				bean.setObserver2_email(row.getCell(16).toString());
				bean.setObserver3_email(row.getCell(17).toString());
				bean.setObserver4_email(row.getCell(18).toString());
				bean.setObserver5_email(row.getCell(19).toString());
				
				bean.setAddress1(row.getCell(7).toString());
				bean.setAddress2(row.getCell(8).toString());
				bean.setAddress3(row.getCell(9).toString());
				bean.setAddress4(row.getCell(10).toString());
				bean.setCity(row.getCell(11).toString());//destination
				bean.setPin_code(row.getCell(12).toString());
				bean.setMobile(row.getCell(13).toString());
				bean.setHcp_email(row.getCell(14).toString());

				prodDetailsList = new ArrayList<ProductDetails>();
				for (int i = 1; i <= 20; i++) {

					if (headerRow.getCell(23 + i) != null && headerRow.getCell(23 + i).toString().trim().length() > 0) {

						if (new BigDecimal((row.getCell(23 + i)).toString()).longValue() > 0l) {
							ProductDetails prodDets = new ProductDetails();
							prodDets.setProduct_name(headerRow.getCell(23 + i).toString());
							prodDets.setQty(row.getCell(23 + i).toString());
							prodDetailsList.add(prodDets);
						}

					}
				}
				bean.setProductDetails(prodDetailsList);

				bulkDoctorPdfList.add(bean);
				}
			}

//			try {
//				String fileName = this.doctorBulkUploadPdf(bulkDoctorPdfList);
//				if (fileName != null) {
//					return fileName;
//				} else {
//					throw new Exception("PDF File Generation failed!!");
//				}
//			} catch (Exception e) {
//				throw e;
//			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return null;

	}
	
	

}
