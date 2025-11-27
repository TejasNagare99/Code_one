package com.excel.restcontroller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.excel.bean.StockTransferBean;
import com.excel.model.Company;
import com.excel.model.FinYear;
import com.excel.model.Location;
import com.excel.model.Period;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.Stock_Transfer_Header;
import com.excel.service.FinancialYearService;
import com.excel.service.LocationService;
import com.excel.service.ParameterService;
import com.excel.service.PeriodMasterService;
import com.excel.service.ProdLockService;
import com.excel.service.StockTransferService;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@RestController
@RequestMapping("/rest")
public class StockTransferRestController implements MedicoConstants {

	@Autowired LocationService locationService;
	@Autowired PeriodMasterService periodMasterService;
	@Autowired ParameterService parameterService;
	@Autowired StockTransferService stockTransferService;
	@Autowired ProdLockService prodLockService;
	@Autowired private FinancialYearService financialYearService;

	@GetMapping("/get-data-for-stk-trf-entry")
	public Map<String, Object> getDataForStkTrfEntry(@RequestParam String empId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<SG_d_parameters_details> dispatchParametersList = this.parameterService.getDispatchParameters();
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			System.out.println("Sub Comp Id"+location.getLoc_subcomp_id());
			System.out.println("Loc Id"+location.getLoc_id());
			List<Location> recLocations=this.locationService.getAllSubCompanies().stream()
					.filter(value -> value.getLoc_id().compareTo(location.getLoc_id())!=0
					&& value.getLoc_subcomp_id().compareTo(location.getLoc_subcomp_id()) == 0
							).collect(Collectors.toList());
			
			String trfType="Sending Depot-Receiving Depot";
			
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Period period = this.periodMasterService.getPeriodMasterByCompany(companyCode);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date today = new Date();
			Date taskDate = MedicoResources.convert_YYYY_MM_DD_HH_MM_SS_toDate(period.getPeriod_end_date());
			String stkTrfDate="";
			if (today.compareTo(taskDate) > 0) {
				stkTrfDate=dateFormat.format(taskDate);
			} else {
				stkTrfDate=dateFormat.format(today);
			}
			
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("sendLocStateId", location.getLoc_state_id());
			map.put("recLocations", recLocations);
			map.put("trfType", trfType);
			map.put("stkTrfDate", stkTrfDate);
			map.put("dispatchParametersList", dispatchParametersList); 
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-product-by-send-rec-loc")
	public Map<String, Object> getProdcutsBySendRecLoc(@RequestParam String sendLocId,@RequestParam String sendSubCompId,
			@RequestParam String sendLocStateId,@RequestParam String nilGstInd,@RequestParam String stockType) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<StockTransferBean> list=stockTransferService.getProductListBySendrecLoc(Long.valueOf(sendLocId), Long.valueOf(sendSubCompId),
					Long.valueOf(sendLocStateId), nilGstInd,stockType);
			map.put("prodList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-stock-types")
	public Map<String, Object> getStockTypes() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("stockTypes", this.parameterService.getStockTypes());
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/get-stock-types-list")
	public Map<String, Object> getStockTypeswithall() {
		Map<String, Object> map = new HashMap<>();
		try {
			List<SG_d_parameters_details> list = null;
			list =  this.parameterService.getStockTypes();
			SG_d_parameters_details loc=new SG_d_parameters_details();
			loc.setSgprmdet_nm("0");
			loc.setSgprmdet_disp_nm("All");
			list.add(0, loc);
			map.put("stockTypes", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	@GetMapping("/get-batch-details-by-prod")
	public Map<String, Object> getBatchDetailsForProd( @RequestParam String dptLocId, @RequestParam String sendLocId,@RequestParam String prodId,
			@RequestParam String tranType,@RequestParam String stockType) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Object> list=this.stockTransferService.getbatchByProdAndLoc(Long.valueOf(sendLocId), Long.valueOf(prodId),tranType,stockType);
			List<Object> lis =  stockTransferService.getSgstIgst(Long.valueOf(dptLocId), Long.valueOf(prodId));
			BigDecimal total_billable_stock = BigDecimal.ZERO;
			BigDecimal withheld_stock = BigDecimal.ZERO;
			List<StockTransferBean> batchList=new ArrayList<StockTransferBean>();
			List<Long> batchIds=new ArrayList<Long>();
			List<BigDecimal> batchTrfRates=new ArrayList<BigDecimal>();
			List<BigDecimal> batchStocks=new ArrayList<BigDecimal>();
			
			BigDecimal taxrate = new BigDecimal(0);

			Object tax_object[] = (Object[]) lis.get(0);
			 taxrate = (BigDecimal) tax_object[1];

			
			StockTransferBean bean=null;
			for (Object objArr : list) {
				Object object[] = (Object[]) objArr;
				BigDecimal billable_stock = (((BigDecimal)object[6]).add(((BigDecimal)object[7])).subtract(((BigDecimal)object[8]))
						.subtract(((BigDecimal)object[9])));
				total_billable_stock = total_billable_stock.add(billable_stock);
				withheld_stock=withheld_stock.add(((BigDecimal)object[9]));
				bean=new StockTransferBean();
				bean.setBatch_id(Long.valueOf((Integer) object[0]));
				bean.setProd_id(Long.valueOf((Integer) object[1]));
				bean.setBatch_no((String) object[2]);
				bean.setExpiry_date((String) object[3]);
				bean.setLoc_id(Long.valueOf((Integer) object[4]));
				bean.setTrfRate((BigDecimal) object[5]);
				bean.setOpen_stock((BigDecimal) object[6]);
				bean.setIn_stock((BigDecimal) object[7]);
				bean.setOut_stock((BigDecimal) object[8]);
				bean.setWitheld_qty((BigDecimal) object[9]);
				bean.setMfg_date((String) object[10]);
				bean.setTaxRate(taxrate);
				bean.setStock(billable_stock);
				bean.setSold_qty(BigDecimal.ZERO);
				bean.setFree_qty(BigDecimal.ZERO);
				batchList.add(bean);
				batchIds.add(Long.valueOf((Integer) object[0]));
				batchTrfRates.add((BigDecimal) object[5]);
				batchStocks.add(billable_stock);
			}
			map.put("batchList", batchList);
			map.put("batchIds", batchIds);
			map.put("batchTrfRates", batchTrfRates);
			map.put("batchStocks", batchStocks);
			map.put("billable_stk", total_billable_stock);
			map.put("withheld_stk", withheld_stock);
			map.put("total_stock", total_billable_stock.add(withheld_stock));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-stk-trf")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveStkTrf(@RequestBody StockTransferBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> stkTrfMap = new HashMap<>();
		try {
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			bean.setNilGstStn(company.getNil_gst_stn());
			bean.setFreeGoodsStn(company.getFree_goods_stn());
			if(bean.getSaveMode().equals("MODIFY")) {
				this.stockTransferService.modifyStock(bean);
				stkTrfMap.put(DATA_SAVED, true);
				stkTrfMap.put(RESPONSE_MESSAGE, "Record Deleted Successfully!");
			} else if(bean.getSaveMode().equals("DELETE_TRF")) {
				this.stockTransferService.deleteStockTrf(bean);
				stkTrfMap.put(DATA_SAVED, true);
				stkTrfMap.put(RESPONSE_MESSAGE, "Stock Transfer Deleted Successfully!");
			} else {
				stkTrfMap = this.stockTransferService.saveStkTrf(bean);
				stkTrfMap.put(DATA_SAVED, true);
				stkTrfMap.put(RESPONSE_MESSAGE, "Record Added Successfully!");
			}
			System.out.println("done----------");
		} catch (Exception e) {
			e.printStackTrace();
			stkTrfMap.put(DATA_SAVED, false);
			stkTrfMap.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			try {
				this.prodLockService.deleteProdLockByUserId(bean.getEmpId());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return stkTrfMap;
	}
	
	@GetMapping("/get-saved-prod-list")
	public Map<String, Object> getSavedProdDetails(@RequestParam String headerId,@RequestParam String sendLocId) {
		System.out.println(headerId+"  "+sendLocId);
		Map<String, Object> map = new HashMap<>();
		try {
			List<StockTransferBean> list=stockTransferService.getSTProdDetails(Long.valueOf(headerId), Long.valueOf(sendLocId));
			map.put("savedProdList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}

	@GetMapping("/get-data-for-stk-trf-modify")
	public Map<String, Object> getDataForStkTrfModify(@RequestParam String empId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			List<Location> recLocations=this.locationService.getAllSubCompanies().stream()
					.filter(value -> value.getLoc_id().compareTo(location.getLoc_id())!=0
					&& value.getLoc_subcomp_id().compareTo(location.getLoc_subcomp_id()) == 0
							).collect(Collectors.toList());
			
			String trfType="Sending Depot-Receiving Depot";
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("sendLocStateId", location.getLoc_state_id());
			map.put("recLocations", recLocations);
			map.put("trfType", trfType);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-trans-no-list")
	public Map<String, Object> getTransNoList(@RequestParam String sendLocId,@RequestParam String recLocId,@RequestParam String trfDate,@RequestParam String stkTrfType) {
		Map<String, Object> map = new HashMap<>();
		boolean isData=false;
		String saNs="";
		try {
			saNs=stkTrfType.equals("85") ? SA : NS;
			List<Stock_Transfer_Header> transNoList=this.stockTransferService.getDtlByDateAndLoc(Long.valueOf(sendLocId), Long.valueOf(recLocId), MedicoResources.convert_DD_MM_YYYY_toDate(trfDate), saNs);
			isData=(transNoList == null ? false : true);
			map.put("transNoList", transNoList);
			map.put("isData", isData);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-add-product-by-send-rec-loc")
	public Map<String, Object> getAddProdcutsBySendRecLoc(@RequestParam String sendLocId,@RequestParam String sendSubCompId,
			@RequestParam String sendLocStateId,@RequestParam String nilGstInd,@RequestParam String stockType,@RequestParam String trfId) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<StockTransferBean> list=stockTransferService.getAddProdListForStkMod(Long.valueOf(sendLocId), trfId, Long.valueOf(sendSubCompId), nilGstInd, Long.valueOf(sendLocStateId), stockType);
			map.put("prodList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-batch-details-by-prod-delete")
	public Map<String, Object> getBatchDetailsForProdDelete(@RequestParam String dptLocId,@RequestParam String stktrfNo,@RequestParam String prodId) {
		Map<String, Object> map = new HashMap<>();
		try {
			List<Object> list=this.stockTransferService.getStockTransferDtForTrfIDAndProdID(Long.valueOf(stktrfNo), Long.valueOf(prodId));
			List<Object> lis =  stockTransferService.getSgstIgst(Long.valueOf(dptLocId), Long.valueOf(prodId));

			BigDecimal total_billable_stock = BigDecimal.ZERO;
			BigDecimal withheld_stock = BigDecimal.ZERO;
			BigDecimal total_billed = BigDecimal.ZERO;
			BigDecimal total_free = BigDecimal.ZERO;
			BigDecimal total_val = BigDecimal.ZERO;
			
			List<StockTransferBean> batchList=new ArrayList<StockTransferBean>();
			List<Long> batchIds=new ArrayList<Long>();
			List<BigDecimal> batchTrfRates=new ArrayList<BigDecimal>();
			List<BigDecimal> batchStocks=new ArrayList<BigDecimal>();
			List<Long> detailIds=new ArrayList<Long>();
			String prodStockMap="";
			String prodStockMap2="";
			StockTransferBean bean=null;
			BigDecimal taxrate = new BigDecimal(0);

			Object tax_object[] = (Object[]) lis.get(0);
			 taxrate = (BigDecimal) tax_object[1];

			
			for (Object objArr : list) {
				Object object[] = (Object[]) objArr;
				total_billable_stock = total_billable_stock.add((BigDecimal) object[3]);
				total_billed = total_billed.add((BigDecimal) object[5]);
				total_val = total_val.add(((BigDecimal) object[5]).multiply((BigDecimal) object[4]));
				withheld_stock = withheld_stock.add((BigDecimal) object[7]);
				total_free = total_free.add((BigDecimal) object[8]);
				bean=new StockTransferBean();
				bean.setBatch_id(((BigDecimal) object[0]).longValue());
				bean.setBatch_no((String) object[1]);
				bean.setExpiry_date((String) object[2]);
				bean.setStock((BigDecimal) object[3]);
				bean.setTrfRate((BigDecimal) object[4]);
				bean.setSold_qty((BigDecimal) object[5]);
				bean.setFree_qty((BigDecimal) object[8]);
				bean.setDetailId(Long.valueOf((Integer)object[6]));
				bean.setTaxRate(taxrate);
				bean.setTot_val(total_val);
				batchList.add(bean);
				batchIds.add(((BigDecimal)object[0]).longValue());
				batchTrfRates.add((BigDecimal) object[4]);
				batchStocks.add(total_billable_stock);
				detailIds.add(Long.valueOf((Integer)object[6]));
			//	prodStockMap=(String)object[9] +","+Long.valueOf((Integer)object[10]);
				prodStockMap=(String)object[9];
				prodStockMap2=""+(Integer)object[10];
				bean.setMfg_date((String) object[11]);

			}
			map.put("batchList", batchList);
			map.put("batchIds", batchIds);
			map.put("batchTrfRates", batchTrfRates);
			map.put("batchStocks", batchStocks);
			map.put("detailIds", detailIds);
			map.put("prodStockMap", prodStockMap);
			map.put("prodStockMap2", prodStockMap2);
			
			map.put("billable_stk", total_billable_stock);
			map.put("withheld_stk", withheld_stock);
			map.put("tot_billed", total_billed);
			map.put("tot_free", total_free);
			map.put("tot_val", total_val);
			map.put("total_stock", total_billable_stock.add(withheld_stock));
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-saved-prod-list-for-delete")
	public Map<String, Object> getSavedProdDetailsForDelete(@RequestParam String headerId,@RequestParam String sendLocId) {
		System.out.println(headerId+"  "+sendLocId);
		Map<String, Object> map = new HashMap<>();
		try {
			List<StockTransferBean> list=stockTransferService.getSTProdDetailsForDelete(Long.valueOf(headerId), Long.valueOf(sendLocId));
			map.put("savedProdList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-data-for-stk-trf-lr-entry")
	public Map<String, Object> getDataForStkTrflREntry(@RequestParam String empId,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			Location location = this.locationService.getLocationNameByEmployeeId(empId);
			List<Location> recLocations=this.locationService.getAllSubCompanies().stream()
					.filter(value -> value.getLoc_id().compareTo(location.getLoc_id())!=0
					&& value.getLoc_subcomp_id().compareTo(location.getLoc_subcomp_id()) == 0
							).collect(Collectors.toList());
			
			
			List<FinYear> financialYears=null;
			LinkedHashMap<String, String> finYears = new LinkedHashMap<String, String>();
			String from="";
			String to="";
			
			financialYears=financialYearService.getAllFinancialYears(companyCode);
			for (FinYear f : financialYears) {
				from = f.getFin_start_date().toString().substring(8,10) + "-" + f.getFin_start_date().toString().substring(5,7) + 
						"-" + f.getFin_start_date().toString().substring(0,4);
				to   = f.getFin_end_date().toString().substring(8,10) + "-" + f.getFin_end_date().toString().substring(5,7) + 
						"-" + f.getFin_end_date().toString().substring(0,4);
				finYears.put(f.getFin_year(), from + " To " + to);
			}
			map.put("finYearMap", finYears);
			map.put("financialYears", financialYears);
			
			map.put("sendLocName", location.getLoc_nm());
			map.put("sendSubCompId", location.getLoc_subcomp_id());
			map.put("sendLocId", location.getLoc_id());
			map.put("sendLocStateId", location.getLoc_state_id());
			map.put("recLocations", recLocations);
			map.put("motList", this.parameterService.getParaDetailsByParaType("MODE OF TRANSPORT"));
			map.put("consignList", this.parameterService.getParaDetailsByParaType("CONSIGNMENT TYPE"));			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@GetMapping("/get-stk-trf-list")
	public Map<String, Object> getStkTrfList(@RequestParam String curr_fin_yr,@RequestParam String fin_yr,
			@RequestParam String sendLocId,@RequestParam String recLocId,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String year_flag = "C";
		try {
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			if(Long.valueOf(curr_fin_yr).compareTo(Long.valueOf(fin_yr))!=0){
				year_flag = "P";
			}
			List<Object> list=this.stockTransferService.getStockTransferHdForLREntry(Long.valueOf(recLocId), Long.valueOf(sendLocId), 
					year_flag, Long.valueOf(fin_yr), companyCode);
			map.put("stkTrfList", list);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return map;
	}
	
	@PostMapping("/save-stk-trf-lr")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveStkTrfLr(@RequestBody StockTransferBean bean, HttpSession session, HttpServletRequest request) {
		Map<String, Object> stkTrfMap = new HashMap<>();
		try {
			Company company=(Company) session.getServletContext().getAttribute(COMPANY_DATA);
			String companyCode = session.getServletContext().getAttribute(COMPANY_CODE).toString();
			bean.setIpAddress(request.getRemoteAddr());
			bean.setCompanyCode(companyCode);
			bean.setStock_at_cfa(company.getStock_at_cfa().equalsIgnoreCase("Y") ? true : false);
			this.stockTransferService.saveStkTrfLr(bean);
			stkTrfMap.put(DATA_SAVED, true);
			stkTrfMap.put(RESPONSE_MESSAGE, "LR entry done successfully for the selected Transfer no.");
		} catch (Exception e) {
			stkTrfMap.put(DATA_SAVED, false);
			stkTrfMap.put(RESPONSE_MESSAGE, "Error Occurred !! Error Code : "+new CodifyErrors().getErrorCode(e));
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} 
		return stkTrfMap;
	}

}
