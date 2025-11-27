package com.excel.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.StkBlkCsvUpldBean;
import com.excel.bean.StockistBulkUploadBean;
import com.excel.exception.MedicoException;
import com.excel.model.BlkStkReqTemp;
import com.excel.model.Blk_hcq_req_temp;
import com.excel.model.BulkStkReqHeader;
import com.excel.model.DivMaster;
import com.excel.model.Period;
import com.excel.repository.CustomerMasterRepo;
import com.excel.repository.DivMasterRepository;
import com.excel.repository.FieldStaffRepository;
import com.excel.repository.StockistBulkRepo;
import com.excel.service.AllocationService;
import com.excel.service.DoctorBulkAllocUploadService;
import com.excel.service.PeriodMasterService;
import com.excel.service.SockistBulkAllocUploadService;
import com.excel.utility.MedicoConstants;

@RestController
@RequestMapping("/rest/stk-blk")
public class StockistBulkUploadController implements MedicoConstants {

	@Autowired
	private DivMasterRepository divMasterRepository;
	@Autowired
	private AllocationService allocationService;
	@Autowired
	private StockistBulkRepo stockBulkRepo;
	@Autowired
	private CustomerMasterRepo custMasterRepo;
	@Autowired
	private SockistBulkAllocUploadService stkBulkAllocUploadService;
	@Autowired
	private FieldStaffRepository fstaffRepo;
	@Autowired
	private PeriodMasterService periodMasterService;
	@Autowired
	private DoctorBulkAllocUploadService docBlkAllocUpldService;

	private Long loc_id = 13L;

	@GetMapping("/onload")
	public Map<String, Object> getOnLoadData(@RequestParam String empId, HttpSession session) {
		Map<String, Object> map = null;
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			map = new HashMap<String, Object>();
			map.put("productTypes", this.allocationService.getPlanningType(empId));

			Long divId = null;
			Long req_fstaff_id = null;

			if (comp_code.trim().equalsIgnoreCase(PFZ)) {
				divId = PFZ_DIV_ID_FOR_STOCKIST_BULK_UPLD_FSTAFF;
				req_fstaff_id = PFZ_REQUESTOR_ID_FOR_STK_BLK_UPLD_FSTAFF;
			}

			DivMaster div = this.divMasterRepository.getById(divId);
			map.put("divId", divId);
			map.put("divnm", div.getDiv_disp_nm());

			// map.put("fieldStaff",
			// this.stockBulkRepo.getFieldStaffAttchedToLocation(loc_id, this.divId));
			map.put("fieldStaff", this.fstaffRepo.getObjectByStaffId(req_fstaff_id));

			map.put("ReqNoList", this.stkBulkAllocUploadService.getReqNoByStatus("E", N));

			// later to be fetched from session
			map.put("stockistList", this.custMasterRepo.getCustomersForLocation(loc_id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/save-bulk-upload-stockist")
	public Map<String, Object> saveDoctorBulkUpload(@RequestBody StockistBulkUploadBean stockistBulkUploadBean,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			stockistBulkUploadBean.setBlk_ip_add(request.getRemoteAddr());
			stockistBulkUploadBean.setCompany(companyCode);
			map = stkBulkAllocUploadService.saveBulkStockistUpload(stockistBulkUploadBean);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, false);
		}
		return map;
	}

	@PostMapping("/save-stk-bulk-alloc-product")
	public Map<String, Object> SaveAllocBulkUpload(@RequestBody StockistBulkUploadBean stockistBulkUploadBean,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			stockistBulkUploadBean.setCompany(comp_code);
			stockistBulkUploadBean.setBlk_ip_add(request.getRemoteAddr());
			map = stkBulkAllocUploadService.saveBulkStockistproducts(stockistBulkUploadBean);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put(DATA_SAVED, false);
		}
		return map;

	}

	@PostMapping("/modify-bulk-stockist-upload-stockist")
	public Map<String, Object> ModifyDoctorBulkUpload(@RequestBody StockistBulkUploadBean stockistBulkUploadBean,
			HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put(DATA_SAVED, true);
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			stockistBulkUploadBean.setBlk_ip_add(request.getRemoteAddr());
			stockistBulkUploadBean.setCompany(companyCode);
			map = stkBulkAllocUploadService.modifyBulkUploadStockists(stockistBulkUploadBean);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/modify-bulk-stockist-upload-product")
	public Map<String, Object> ModifyAllocBulkUploadProd(@RequestBody StockistBulkUploadBean bean, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setBlk_ip_add(request.getRemoteAddr());
			bean.setCompany(companyCode);
			map = stkBulkAllocUploadService.modifyBulkStkUploadProducts(bean);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			// TODO: handle exception
			map.put(DATA_SAVED, false);
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/confirmBulkStkAllocation")
	public Map<String, Object> ConfirmBulkAllocation(@RequestParam("bulkReqId") String reqId,
			@RequestParam("empId") String empId, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashedMap<String, Object>();
		try {
			stkBulkAllocUploadService.ConfirmStockistBlkAlloc(Long.valueOf(reqId), empId, request.getRemoteAddr());
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/generate-bulk-upload-csv")
	public Map<String, Object> downloadStkBulkUploadExcel(@RequestParam String empId, @RequestParam String bulkReqNo,
			@RequestParam String bulkReqId) {

		Map<String, Object> map = new HashMap<>();
		List<Object[]> hdrAndStkList = null;
		List<Object[]> prodList = null;
		String filename = null;
		BulkStkReqHeader blk_stk_header = null;
		System.out.println("bulkReqNo::::" + bulkReqNo + ":::bulkReqId:::" + bulkReqId + ":::empId:::" + empId);
		try {
			blk_stk_header = this.stockBulkRepo.getBlkStkReqHdrById(Long.valueOf(bulkReqId.trim()));
			prodList = this.stockBulkRepo.getProdListForCsvGeneration(Long.valueOf(bulkReqId));
			if (blk_stk_header.getStk_mst_type().trim().equals("M")) {
				hdrAndStkList = this.stkBulkAllocUploadService.getHdrAndStksForCsvDownload(Long.valueOf(bulkReqId));
				if (hdrAndStkList != null && prodList != null && !hdrAndStkList.isEmpty() && !prodList.isEmpty()) {
					filename = this.stkBulkAllocUploadService.getExcelForBulkUpload(hdrAndStkList, prodList, empId,
							blk_stk_header);
					if (filename != null) {
						map.put("fileName", filename);
					}
				} else {
					map.put("msg", "No Data Found for this transaction");
				}
			} else if (blk_stk_header.getStk_mst_type().trim().equals("N")) {
				if (prodList != null && !prodList.isEmpty()) {
					filename = this.stkBulkAllocUploadService.getExcelForBulkUpload(hdrAndStkList, prodList, empId,
							blk_stk_header);
					if (filename != null) {
						map.put("fileName", filename);
					}
				} else {
					map.put("msg", "No Data Found for this transaction");
				}
			}
		} catch (Exception e) {
			map.put("msg", "Transaction Failed");
			e.printStackTrace();
		}
		return map;
	}

	@GetMapping("/get-blk-stk-req-list")
	public Map<String, Object> getRequests() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("requestList", this.stkBulkAllocUploadService.getRequestListFromBlkStkHdr(false));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/validate-file-for-stockist-upload")
	public Map<String, Object> validateFileBeforeSaving(@RequestParam MultipartFile file, @RequestParam String username,
			@RequestParam Long locId, HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			System.out.println("file " + file.getOriginalFilename());
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				map = this.stkBulkAllocUploadService.validateExcelUploadForTemp(file, locId);
				if ((boolean) map.get("hasErrors") == true) {
					// file has errors return with error message
					map.put("msg", "File has errors . Please check the attached error file .");
					map.put("DATA_SAVED", N);
				} else {
					// go ahead and save to the table
					BulkStkReqHeader blk_stk_header = (BulkStkReqHeader) map.get("hdrRecord");
					@SuppressWarnings("unchecked")
					List<StkBlkCsvUpldBean> data_for_temp_table = (List<StkBlkCsvUpldBean>) map.get("data");
					this.stkBulkAllocUploadService.saveDataToBulkStkReqTemp(request.getRemoteAddr(),
							PFZ_REQUESTOR_ID_FOR_STK_BLK_UPLD_FSTAFF, blk_stk_header, data_for_temp_table,
							file.getOriginalFilename(), username);
					map.put("msg", "Transaction Succesful");
					map.put("DATA_SAVED", Y);
				}
			} else {
				map.put("msg", "Please upload files in correct format");
			}
		} catch (MedicoException me) {
			map.put("msg", me.getMessage());
			map.put("DATA_SAVED", N);
			me.printStackTrace();
		} catch (Exception e) {
			map.put("msg", "File Processing Failed");
			map.put("DATA_SAVED", N);
			e.printStackTrace();
		}
		return map;
	}

	@GetMapping("/get-onload-data-alloc-generation")
	public Map<String, Object> getOnloadData(HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String comp_code = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(comp_code);
			map.put("monthOfUse",
					this.allocationService.getPeriodListGreaterThanPeriodCode(period.getPeriod_id().toString()));
			map.put("requestList", this.stkBulkAllocUploadService.getRequestListFromBlkStkHdr(true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@PostMapping("/generate-alloc-from-blk-temp")
	public Map<String, Object> generateAllocationFromTemp(@RequestParam("bulkReqId") String bulkReqId,
			@RequestParam("finYear") String finYear, @RequestParam("period_code") String periodCode,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object[]> lst = null;
		try {
			String ip_address = request.getRemoteAddr();
			this.stkBulkAllocUploadService.pushStkBulkRecordsToAllocTables(Long.valueOf(bulkReqId), finYear, ip_address,
					periodCode, PFZ_DIV_ID_FOR_STOCKIST_BULK_UPLD_FSTAFF);
			lst = docBlkAllocUpldService.getGeneratedRequestList(Long.valueOf(bulkReqId));
			map.put("STATUS", "S");
			map.put("msg", "Transaction completed");
			map.put("reqlst", lst);
		} catch (MedicoException e) {
			map.put("STATUS", "F");
			map.put("msg", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("STATUS", "F");
			map.put("msg", "Transaction failed !");
		}
		return map;
	}

	@PostMapping("/temp-self-approval")
	public Map<String, Object> SelfApprovalDoctorBlkAlloc(@RequestParam("bulkReqId") String reqId,
			@RequestParam("EmpId") String empId, @RequestParam("userRole") String userRole, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> map = new HashedMap<String, Object>();
		try {
			stkBulkAllocUploadService.selfApprovalStockistUpload(reqId, userRole, empId, request, session);
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	@GetMapping("/get-stk-blkred-details-By-Id") 
	public Map<String, Object> GetReqDetailsforModify(@RequestParam("reqId") String reqId,
			HttpSession session,HttpServletRequest request) {
		Map<String, Object> map=new HashedMap<String, Object>();
		try {
			map.put("HdrDetails", stkBulkAllocUploadService.getObjById(Long.valueOf(reqId))); 
			map.put("stkDetails", stkBulkAllocUploadService.getBulkStockistDetailsByHdrReqId(Long.valueOf(reqId))); 
			map.put("ProductDetails", stkBulkAllocUploadService.getProductsDetailsByBulkHeaderId(Long.valueOf(reqId))); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@GetMapping("/getStkBlkPdf")
	public Map<String, Object> getPdf(@RequestParam Long tranRefId){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			String csvnm= this.stkBulkAllocUploadService.getBulkCsvNameByBulkTempId(tranRefId);
			if(csvnm!=null) {
				String fileName = stkBulkAllocUploadService.stockistBulkUploadExcelToPdf(csvnm);
				map.put("fileName", fileName);
				map.put(DATA_SAVED, "Y");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put(DATA_SAVED, "N");
		}
		return map;
	}
	
	@GetMapping("/getBlkStkForAutoDispatch")
	public Map<String, Object> getReqForAutoDisp(@RequestParam Long loc_id){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			List<BulkStkReqHeader>  reqList = this.stockBulkRepo.getRequestListFromBlkHdrForUploaded(loc_id);
			map.put("AllocReqList", reqList);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
