package com.excel.restcontroller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.DispatchBean;
import com.excel.bean.IAABean;
import com.excel.bean.StockWithdrawalBean;
import com.excel.exception.MedicoException;
import com.excel.model.Company;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.SWV_Header;
import com.excel.model.SmpItem;
import com.excel.model.Supplier;
import com.excel.model.V_SWV_Detail;
import com.excel.model.V_SWV_Header;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.StockWithdrawalService;
import com.excel.service.UserMasterService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class StockWithdrawalRestController implements MedicoConstants {

	@Autowired StockWithdrawalService stockWithdrawalService;
	@Autowired LocationService locationService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired ParameterService parameterService;
	@Autowired private UserMasterService userMasterService;
	@GetMapping("/get-data-for-stk-withdrawal-entry")
	public Map<String, Object> getDataForSwEntry(HttpServletRequest request,
			HttpSession session) {
		
		
		
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date today = new Date();
			Date taskDate = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
			String trnDate="";
			Long receipt_datetime=0l;
			if (today.compareTo(taskDate) > 0) {
				trnDate=dateFormat.format(taskDate);
				receipt_datetime = taskDate.getTime();
			} else {
				trnDate=dateFormat.format(today);
				receipt_datetime = today.getTime();
			}
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("trnDate", trnDate);
			map.put("receipt_datetime", receipt_datetime);
			map.put("period_alt_name", period.getPeriod_alt_name());
			map.put("stkWthTypeList", this.parameterService.getParaDetailsByParaType("WITHDRAWAL_TYPE"));
			map.put("stockTypes", this.parameterService.getStockTypes());
			map.put("stateList", this.parameterService.getParaDetailsByParaType("State"));
			map.put("prodList", this.stockWithdrawalService.getProdListForSWV(location.getLoc_id()));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-supplier-by-type")
	public Map<String, Object> getSupplierByType(@RequestParam String supplier_type,@RequestParam String sub_comp_id) {
		Map<String, Object> map = new HashMap<>();
		try {
			System.out.println("supplier_type "+supplier_type);
			System.out.println("sub_comp_id "+sub_comp_id);
			List<Supplier> supplierList=this.stockWithdrawalService.getSuppliersBySupplierType(supplier_type,Long.valueOf(sub_comp_id));
			map.put("supplierList", supplierList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-supplier-addr-by-id")
	public Map<String, Object> getSupplierAddrById(@RequestParam String sup_id) {
		Map<String, Object> map = new HashMap<>();
		try {
			Supplier supplier=this.stockWithdrawalService.supplierAddrById(Long.valueOf(sup_id));
			map.put("supplier", supplier);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-batch-details-by-prod-stock-type")
	public Map<String, Object> getBatchDetailsByProdStockType(@RequestParam String prod_id,@RequestParam String stk_type,
			@RequestParam String loc_id,@RequestParam String swv_id,@RequestParam String action) {
		Map<String, Object> map = new HashMap<>();
		List<StockWithdrawalBean> batchList=null;
		try {
			if(action.equalsIgnoreCase("S")) {
				batchList=this.stockWithdrawalService.getBatchDetailsByProdStkType(Long.valueOf(loc_id), stk_type, Long.valueOf(prod_id));
				if(batchList !=null) {
				List<BigDecimal> batchStocks=batchList.stream().map(value -> value.getStock()).collect(Collectors.toList());
				List<Long> batchIds=batchList.stream().map(value -> value.getBatch_id()).collect(Collectors.toList());
				List<Long> detailIds=batchList.stream().map(value -> value.getDetail_id()).collect(Collectors.toList());
				List<BigDecimal> rates=batchList.stream().map(value -> value.getRate()).collect(Collectors.toList());
				map.put("batchStocks", batchStocks);
				map.put("batchIds", batchIds);
				map.put("detailIds", detailIds);
				map.put("rates", rates);
				map.put("batchQtys", new BigDecimal[batchIds.size()]);
				map.put("batchCases", new BigDecimal[batchIds.size()]);
				map.put("batchStocksAftWth", batchStocks);
				map.put("divId", batchList.get(0).getDivId());
				}
			} else {
				batchList=this.stockWithdrawalService.getModBatchDetailsByProdStkType(Long.valueOf(loc_id), stk_type, Long.valueOf(prod_id),Long.valueOf(swv_id));
				if(batchList !=null) {
				List<BigDecimal> batchStocks=batchList.stream().map(value -> value.getStock()).collect(Collectors.toList());
				List<Long> batchIds=batchList.stream().map(value -> value.getBatch_id()).collect(Collectors.toList());
				List<Long> detailIds=batchList.stream().map(value -> value.getDetail_id()).collect(Collectors.toList());
				List<BigDecimal> rates=batchList.stream().map(value -> value.getRate()).collect(Collectors.toList());
				List<BigDecimal> qtys=batchList.stream().map(value -> value.getQty()).collect(Collectors.toList());
				List<Integer> cases=batchList.stream().map(value -> value.getCases()).collect(Collectors.toList());
				List<BigDecimal> stkAfterWth=batchList.stream().map(value -> value.getStock().subtract(value.getQty())).collect(Collectors.toList());
				map.put("batchStocks", batchStocks);
				map.put("batchIds", batchIds);
				map.put("detailIds", detailIds);
				map.put("rates", rates);
				map.put("batchQtys", qtys);
				map.put("batchCases", cases);
				map.put("batchStocksAftWth", stkAfterWth);
				map.put("divId", batchList.get(0).getDivId());
				}
			}
			map.put("batchList", batchList);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-stk-withdrawal")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveStkWth(@RequestBody StockWithdrawalBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			if(bean.getAction().equalsIgnoreCase("M") || bean.getAction().equalsIgnoreCase("D")) {
				this.stockWithdrawalService.deleteSWVDetail(bean);
			}
			if(!bean.getAction().equalsIgnoreCase("D")) {
				map=this.stockWithdrawalService.saveStkWth(bean);
				map.put(RESPONSE_MESSAGE, "Record Saved Successfully!");
			}
			if(bean.getAction().equalsIgnoreCase("M")) {
				map.put(RESPONSE_MESSAGE, "Record Modified Successfully!");
			} else if(bean.getAction().equalsIgnoreCase("D")){
				map.put(RESPONSE_MESSAGE, "Record Deleted Successfully!");
			}
			map.put(DATA_SAVED, true);
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-stk-wth-saved-prod")
	public Map<String, Object> getSavedProdDetails(@RequestParam String headerId) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<V_SWV_Detail> list=stockWithdrawalService.getSWVDetailsBySWVId(Long.valueOf(headerId));
			SWV_Header obj=this.stockWithdrawalService.getSWVHeaderById(Long.valueOf(headerId));
			map.put("savedProdList", list);
			map.put("file1", obj.getSwv_img1());
			map.put("file2", obj.getSwv_img2());
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-stk-wth-list")
	public Map<String, Object> getStkWthList(@RequestParam String user_type,
			@RequestParam String fromDate,@RequestParam String toDate,HttpSession session, 	
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			List<V_SWV_Header> list=stockWithdrawalService.getSWVListForModify(location.getLoc_id(), companyCode, empId, user_type, fromDate, toDate);
			List<Long> headerIds=list.stream().map(value -> value.getSwv_id()).collect(Collectors.toList());
			map.put("stkWthList", list);
			map.put("headerIds", headerIds);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/delete-stk-wth")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> deleteStkWth(@RequestParam String headerId,HttpSession session,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			StockWithdrawalBean bean=new StockWithdrawalBean();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setHeaderId(Long.valueOf(headerId));
			bean.setEmpId(empId);
			this.stockWithdrawalService.deleteStkWth(bean);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Record deleted Successfully!");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-data-for-stk-withdrawal-modify")
	public Map<String, Object> getDataForSwModify(@RequestParam String headerId,	HttpServletRequest request,
			HttpSession session) {
		
		
		String empId = null;
		try {
			empId = this.userMasterService.getEmployeeIdFromRequest(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<>();
		try {
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			V_SWV_Header swv_Header=this.stockWithdrawalService.getSWVHeaderDetailsById(Long.valueOf(headerId));
			Period period = this.periodMasterService.getPeriodByPeriodCode(swv_Header.getSwv_period_code(),swv_Header.getSwv_fin_year());
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("swv_Header", swv_Header);
			map.put("period_alt_name", period.getPeriod_alt_name());
		//	map.put("stockTypes", this.parameterService.getStockTypes());
		//	map.put("prodList", this.stockWithdrawalService.getProdListForSWV(location.getLoc_id()));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-mod-prod-list")
	public Map<String, Object> getModProdList(@RequestParam String action,@RequestParam String locId,@RequestParam String headerId) {
		Map<String, Object> map = new HashMap<>();
		try {
			if(action.equalsIgnoreCase("S")) {
				map.put("prodList", this.stockWithdrawalService.getProdListForSWV(Long.valueOf(locId)));
			} else {
				map.put("prodList", this.stockWithdrawalService.getModProdListForSWV(Long.valueOf(locId),Long.valueOf(headerId)));
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-stock-types-for-sw")
	public Map<String, Object> getStockTypeForSw(@RequestParam String action,@RequestParam String prodId,@RequestParam String headerId) {
		Map<String, Object> map = new HashMap<>();
		try {
			if(action.equalsIgnoreCase("S")) {
				map.put("stockTypes", this.parameterService.getStockTypes());
			} else {
				map.put("stockTypes", this.stockWithdrawalService.getStockTypeListByProd(Long.valueOf(headerId),Long.valueOf(prodId)));
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-stk-withdrawal-self-approval")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> stkWthSelfApprove(@RequestBody StockWithdrawalBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			this.stockWithdrawalService.stkWthSelfApproval(bean);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Stock Withdrawal forwarded Successfully.");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return map;
	}
	
	@GetMapping("/get-data-for-stk-withdrawal-lr-entry")
	public Map<String, Object> getDataForSwLrEntry(
			HttpSession session,HttpServletRequest request) {
		
		
		Map<String, Object> map = new HashMap<>();
		try {
			
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			List<Object> list=this.stockWithdrawalService.getChallanForLr(location.getLoc_id());
			List<Long> headerIds=new ArrayList<>();
			for(Object oo : list) {
				Object[] obj=(Object[]) oo;
				Integer id=(Integer) obj[0];
				headerIds.add(Long.valueOf(id));
			}

			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("list", list);
			map.put("headerIds", headerIds);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-stk-withdrawal-lr")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> stkWthLrSave(@RequestBody StockWithdrawalBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			String empId = this.userMasterService.getEmployeeIdFromRequest(request);
			bean.setEmpId(empId);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			this.stockWithdrawalService.stkWthLr(bean);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "LR Saved Successfully.");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return map;
	}
	
	@PostMapping("/stk-wth-upload")
	public Map<String, Object> stkWthUpload(@RequestParam MultipartFile file,@RequestParam MultipartFile file2,@RequestParam String headerId,HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<MultipartFile> files=new ArrayList<>();
			files.add(file);
			files.add(file2);
			this.stockWithdrawalService.stkWthFileUpload(files, headerId);
			map.put(DATA_SAVED, true);
			map.put(RESPONSE_MESSAGE, "Files Uploaded Successfully.");
		} catch (Exception e) {
			map.put(DATA_SAVED, false);
			map.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			
		}
		return map;
	}
}
