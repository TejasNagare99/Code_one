package com.excel.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.GRNBean;
import com.excel.bean.TaxCalculationBean;
import com.excel.bean.TaxParamBean;
import com.excel.exception.MedicoException;
import com.excel.model.AUTO_TRANSFER;
import com.excel.model.BatchStockLog;
import com.excel.model.BatchStockLogNS;
import com.excel.model.Company;
import com.excel.model.GrnDetails;
import com.excel.model.GrnHeader;
import com.excel.model.Location;
import com.excel.model.P_ui_AutoGrn;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.Supplier;
import com.excel.model.TaskMaster;
import com.excel.model.Task_Master_Dtl;
import com.excel.model.Tax_data_for_grn;
import com.excel.model.V_Grn_Details;
import com.excel.repository.BatchMasterRepository;
import com.excel.repository.BatchStockLogRepository;
import com.excel.repository.CalculateGstRepository;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.GrnRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.ParameterRepository;
import com.excel.repository.PeriodMasterRepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.SupplierRepository;
import com.excel.repository.SystemParameterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.SendMail;
import com.excel.utility.Utility;
import com.google.gson.Gson;

@Service
public class GRNServiceImpl implements GRNService, MedicoConstants {
	@Autowired
	EmailTranwiseHelpService emailHelpService;
	@Autowired
	GrnRepository grnRepository;
	@Autowired
	SystemParameterRepository systemParameterRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	ProductMasterRepository productMasterRepository;
	@Autowired
	BatchMasterRepository batchMasterRepository;
	@Autowired
	BatchStockLogRepository batchStockLogRepository;
	@Autowired
	ParameterRepository parameterRepository;
	@Autowired
	CalculateGstService calculateGstService;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	CalculateGstRepository calculateGstRepository;
	@Autowired
	TaskMasterService taskMasterService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	SendMail sendMail;
	@Autowired
	LocationService locationService;
	@Autowired
	PeriodMasterRepository periodMasterRepository;
	@Autowired
	CompanyMasterRepository companyMasterRepository;
	@Autowired
	UserMasterService usermasterservice;
	@Autowired
	SendMail sm;
	@Autowired
	CompanyMasterService companyMasterService;
	@Autowired
	EmailTranWiseService emailtranwiseservice;
	@Autowired
	ParameterService parameterService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveGRN(GRNBean bean) throws Exception {

		BigDecimal valueForGrn = null;
		System.out.println("stockTypeId" + bean.getStockTypeId() + "getStockType" + bean.getStockType()
				+ "getStockTypeForNS" + bean.getStockTypeForNS());
		Map<String, Object> map = new HashMap<>();
		System.out.println("bean.getGrnId()" + bean.getGrnId());
		System.out.println("bean.getGrnDetailId()" + bean.getGrnDetailId());
		System.out.println("bean.getQuantity()" + bean.getQuantity());
		System.out.println("bean.getRate()" + bean.getRate());
		BigDecimal grnTotalValue = BigDecimal.ZERO;
		BigDecimal grnTotalValue_PUR = BigDecimal.ZERO;
		BigDecimal customsDutyTotalValue = BigDecimal.ZERO;
		BigDecimal discountTotalValue_PUR = BigDecimal.ZERO;
		BigDecimal rateForGrn = null;
		
		

		if (bean.getPurchase_rate_entry_indicator().trim().equals("Y"))
			rateForGrn = bean.getBatch_costing_rate();
		else
			rateForGrn = bean.getRate();
//		
//		if(!bean.isModifyHeader()) {
//				if(bean.getGrnId().compareTo(0L) == 0) {
//					grnTotalValue = bean.getQuantity().multiply(bean.getRate());
//					grnTotalValue_PUR = bean.getQuantity().multiply(bean.getBatch_costing_rate());
//				} else if(bean.getGrnId().compareTo(0L) != 0 && bean.getGrnDetailId().compareTo(0L) == 0) {
//					grnTotalValue = bean.getQuantity().multiply(bean.getRate()).add((this.grnRepository.getTotalGrnValueByGrnId(bean.getGrnId(), null)));
//					grnTotalValue_PUR = bean.getQuantity().multiply(bean.getBatch_costing_rate()).add((this.grnRepository.getTotal_Pur_GrnValueByGrnId(bean.getGrnId(), null)));
//				} else if(bean.getGrnId().compareTo(0L) != 0 && bean.getGrnDetailId().compareTo(0L) != 0) {
//					grnTotalValue = bean.getQuantity().multiply(bean.getRate()).add((this.grnRepository.getTotalGrnValueByGrnId(bean.getGrnId(), null)));
//					grnTotalValue_PUR = bean.getQuantity().multiply(bean.getBatch_costing_rate()).add((this.grnRepository.getTotal_Pur_GrnValueByGrnId(bean.getGrnId(), null)));
//				}
//		}
		GrnDetails detail = null;
		GrnHeader header = this.saveGrnHeader(bean);
		System.out.println("header StockTypeid " + header.getGrn_stock_type_id());
		if (!bean.isModifyHeader()) {
			detail = this.saveGrnDetail(bean, header);
			if (detail != null) {
				if (bean.getStockType().equals(SA)) {
					SmpBatch batch = this.saveSmpBatch(detail, bean);

					if (bean.getBatch_id().compareTo(0L) == 0) {
						detail.setGrnd_batch_id(batch.getBatch_id());
						// this.transactionalRepository.update(detail);
					}
					detail.setStock_type_id(bean.getGrnStockTypeId());
					this.saveBatchStockLog(bean, detail, header);
				} else if (bean.getStockType().equals(NS)) {

					SmpBatchNS batch = this.saveSmpBatchNonSalable(detail, bean);
					if (bean.getBatch_id().compareTo(0L) == 0) {
						detail.setGrnd_batch_id(batch.getBatch_id());

						// this.transactionalRepository.update(detail);
					}
					detail.setStock_type(batch.getStock_type());
					detail.setStock_type_id(header.getGrn_stock_type_id());

					this.saveBatchStockLogNonSalable(bean, detail, header);
				}

			} else {
				throw new MedicoException("GRN Details not found");
			}
		}

//		if(!bean.isModifyHeader()) {
//			if(bean.getGrnId().compareTo(0L) == 0) {
//				grnTotalValue = bean.getQuantity().multiply(bean.getRate());
//				grnTotalValue_PUR = bean.getQuantity().multiply(bean.getBatch_costing_rate());
//			} else if(bean.getGrnId().compareTo(0L) != 0 && bean.getGrnDetailId().compareTo(0L) == 0) {
//				grnTotalValue = this.grnRepository.getTotalGrnValueByGrnId(bean.getGrnId(), null);
//				grnTotalValue_PUR = this.grnRepository.getTotal_Pur_GrnValueByGrnId(bean.getGrnId(), null);
//			} else if(bean.getGrnId().compareTo(0L) != 0 && bean.getGrnDetailId().compareTo(0L) != 0) {
//				grnTotalValue = this.grnRepository.getTotalGrnValueByGrnId(bean.getGrnId(), null);
//				grnTotalValue_PUR = this.grnRepository.getTotal_Pur_GrnValueByGrnId(bean.getGrnId(), null);
//			}
//		}

		if (!bean.isModifyHeader()) {
			if (bean.getGrnId().compareTo(0L) == 0) {
				grnTotalValue = bean.getQuantity().multiply(bean.getRate());
				grnTotalValue_PUR = bean.getQuantity().multiply(bean.getBatch_costing_rate());
				customsDutyTotalValue = bean.getCustomDuty() == null ? BigDecimal.ZERO : bean.getCustomDuty();
				discountTotalValue_PUR = bean.getDiscount() == null ? BigDecimal.ZERO : bean.getDiscount();
			} else if (bean.getGrnId().compareTo(0L) != 0 && bean.getGrnDetailId().compareTo(0L) == 0) {
				grnTotalValue = this.grnRepository.getTotalGrnValueByGrnId(bean.getGrnId(), null);
				grnTotalValue_PUR = this.grnRepository.getTotal_Pur_GrnValueByGrnId(bean.getGrnId(), null);
				customsDutyTotalValue = this.grnRepository.getTotalCustDutyValueByGrnId(bean.getGrnId(), null);
				discountTotalValue_PUR = this.grnRepository.getTotalDiscountValueByGrnId(bean.getGrnId(), null);
			} else if (bean.getGrnId().compareTo(0L) != 0 && bean.getGrnDetailId().compareTo(0L) != 0) {
				grnTotalValue = this.grnRepository.getTotalGrnValueByGrnId(bean.getGrnId(), null);
				grnTotalValue_PUR = this.grnRepository.getTotal_Pur_GrnValueByGrnId(bean.getGrnId(), null);
				customsDutyTotalValue = this.grnRepository.getTotalCustDutyValueByGrnId(bean.getGrnId(), null);
				discountTotalValue_PUR = this.grnRepository.getTotalDiscountValueByGrnId(bean.getGrnId(), null);
			}
		}

		if (!bean.isModifyHeader()) {
			header.setGrn_total_value(grnTotalValue);
			header.setGrn_total_value_pur(grnTotalValue_PUR);
		}

		header.setGrn_mod_usr_id(bean.getEmpId());
		header.setGrn_mod_dt(new Date());
		header.setGrn_mod_ip_add(bean.getIpAddress());
		this.transactionalRepository.update(header);
		System.out.println("Header " + header);

		if (!bean.isModifyHeader()) {
			System.out.println("BEFORE GST " + header);
			this.updateForGST(detail, header, "PFZ");
			System.out.println("AFTER GST " + header);
		}

		if (bean.getEmailreqInd().equals(Y)) {
			List<String> emails = bean.getEmailList();
			if (emails != null && !emails.isEmpty()) {
				emailtranwiseservice.DeleteEmailTranwiseByTranId(GRN_, header.getGrnId(), header.getGrn_fin_year());
				emailtranwiseservice.saveEmailTranWise(emails, GRN_, header.getGrnId(), header.getGrn_fin_year());
				this.emailHelpService.saveMailsToHelpTable(emails, GRN_, header.getGrn_fin_year());
			}
		}
		if (bean.getPurchase_rate_entry_indicator().trim().equals("Y")) {
			valueForGrn = header.getGrn_total_value_pur();
		} else {
			valueForGrn = header.getGrn_total_value();
		}
		map.put("headerId", header.getGrnId());
		map.put("grnNumber", header.getGrn_no());
		map.put("grnValue", valueForGrn);

		// map.put("grnDetailId", detail.getGrndId());
		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public GrnHeader saveGrnHeader(GRNBean bean) throws Exception {
		GrnHeader header = null;
		if (bean.getGrnId().compareTo(0L) == 0) {
			header = new GrnHeader();
			
			
			Long maxGrnId = this.grnRepository.getMaxGrnId();
			// header.setGrn_id(0L);
			header.setGrn_no(this.grnRepository.getGrnNumber("GRN_NO", "GRN_HEADER", "G",
					"GRN_FIN_YEAR= '" + bean.getFinYear() + "' and GRN_LOC_ID= " + bean.getLoc_id()
							+ " and GRN_COMPANY= '" + bean.getCompanyCode() + "'"));
			
			
			System.out.println("grn_no" + header.getGrn_no());
			header.setGrnd_sl_no((maxGrnId == null ? 0 : maxGrnId) + 1L);
			header.setGrn_company(bean.getCompanyCode());
			header.setGrn_fin_year(bean.getFinYear());
			header.setGrn_period_code(bean.getPeriodCd());
			header.setGrn_loc_id(bean.getLoc_id());
			header.setGrn_dt(
					bean.getGrnDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGrnDate()));
			header.setGrn_supplier_id(bean.getSendingLocId() != 0l ? bean.getSendingLocId()
					: this.grnRepository.getSupplierForReturnFromField(bean.getSubCompId()));
			header.setGrn_transfer_no(bean.getChallanNo());
			header.setGrn_transfer_dt(bean.getChallanDate() == null ? null
					: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getChallanDate()));
			header.setGrn_lr_no(bean.getLrNumber());
			header.setGrn_lr_dt(
					bean.getLrDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
			header.setGrn_lorry_no(bean.getLorryRegNumber());
			header.setGrn_purchase_id(0L);
			header.setGrn_sending_loc_id(8L);
			header.setGrn_trf_type("0");
			header.setGrn_trf_dt(
					bean.getLrDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
			header.setGrn_in_transit("N");
			// header.setGrn_auto_grn_dt();
			header.setGrn_auto_grn_auth("");
			header.setGrn_locked_yn("");
			header.setGrn_erp_grn_cd("");
			header.setGrn_erp_stktfr_cd("");
			header.setGrn_delivery_no("");
			header.setGrn_f_form_no("");
			header.setGrn_consignment_type("");
			header.setGrn_full_shippers(BigDecimal.ZERO);
			header.setGrn_loose_shippers(BigDecimal.ZERO);
			header.setGrn_weight(BigDecimal.ZERO);
			header.setGrn_volume(BigDecimal.ZERO);
			header.setGrn_transport_mode("");
			header.setGrn_octroi(bean.getGrnOctroi());
			header.setGrn_ins_usr_id(bean.getEmpId());
			header.setGrn_mod_usr_id("");
			header.setGrn_mod_ip_add("");
			header.setGrn_ins_dt(new Date());
			header.setGrn_mod_dt(null);
			header.setGrn_ins_ip_add(bean.getIpAddress()); // ??
			header.setGrn_mod_ip_add(null);
			header.setGrn_status(A);
			// Am_m_System_Parameter param =
			// this.systemParameterRepository.getSpValueBySpName("Grn_APPR_REQ").get(0);
			// header.setGrn_appr_req(Long.parseLong(param.getSp_value()));
			if (bean.getProdType().equalsIgnoreCase("205")) {
				header.setGrn_appr_req(1l);
			} else {
				header.setGrn_appr_req(2l);
			}
			header.setGrn_appr_acq(0L);
			header.setGrn_appr_status(E);
			header.setGrn_po_no(bean.getPoNumber());
			header.setGrn_po_date(
					bean.getPoDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getPoDate()));
//			header.setGcma_code(bean.getGcmaCode());
//			header.setGcma_appr_date(bean.getGcmaAppDate() == null ? null : bean.getGcmaAppDate() == "" ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGcmaAppDate()));
//			header.setGcma_expiry_date(bean.getGcmaExpDate() == null ? null : bean.getGcmaExpDate() == "" ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGcmaExpDate()));
			header.setGrn_fin_year(bean.getFinYear());
			header.setGrn_type_id(bean.getGrnType()); // ??
			header.setGrn_doctype_id(bean.getSupplierType());
			header.setReturn_from_fs_id(
					bean.getFs_id() == null ? 0L : bean.getFs_id().compareTo(0L) == 0 ? 0L : bean.getFs_id());
			header.setGrn_stock_sa_or_ns(bean.getStockType());
			header.setGrn_appr_cycle("");
			header.setRemarks("");
			header.setGrn_lbl_print("N");
			header.setSgst_bill_amt(BigDecimal.ZERO);
			header.setCgst_bill_amt(BigDecimal.ZERO);
			header.setIgst_bill_amt(BigDecimal.ZERO);
			header.setGst_reverse_chg("");
			header.setGst_doc_type("");
			header.setText1("");
			header.setText2("");
			header.setValue1(BigDecimal.ZERO);
			header.setValue2(BigDecimal.ZERO);
			header.setGrn_stock_type_id(0L);
			header.setGrn_total_value(BigDecimal.ZERO);
			header.setGrn_total_value_pur(BigDecimal.ZERO);
			header.setGrn_stock_type_id(bean.getGrnStockTypeId());
			header.setDsp_challan_no(bean.getDspChallanNo());
			header.setGrn_confirm("N");
			header.setTransporter_name(bean.getTransportername());
			header.setGrn_ref_no(bean.getGrnRefNo());
			
			header.setVehicle_recd_time(
					bean.getVehicleReceivedTime() == null  || bean.getVehicleReceivedTime().isEmpty()  ? null : MedicoResources.convert_DD_MM_YYYY_HH_MM_toDate(bean.getVehicleReceivedTime()));
			
			
			this.transactionalRepository.persist(header);
		} else if (!bean.getSaveMode().equals(ENTRY_MODE) && bean.isModifyHeader()) {
			header = this.grnRepository.getObjectById(bean.getGrnId());
			header.setGrn_company(bean.getCompanyCode());
			header.setGrn_supplier_id(bean.getSendingLocId() != 0l ? bean.getSendingLocId()
					: this.grnRepository.getSupplierForReturnFromField(bean.getSubCompId()));
			header.setGrn_transfer_no(bean.getChallanNo());
			header.setGrn_transfer_dt(bean.getChallanDate() == null ? null
					: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getChallanDate()));
			header.setGrn_lr_no(bean.getLrNumber());
			header.setGrn_lr_dt(
					bean.getLrDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
			header.setGrn_lorry_no(bean.getLorryRegNumber());
			header.setGrn_sending_loc_id(8L);
			header.setGrn_trf_type("0");
			header.setGrn_trf_dt(
					bean.getLrDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
			header.setGrn_erp_grn_cd("");
			header.setGrn_erp_stktfr_cd("");
			header.setGrn_delivery_no("");
			header.setGrn_f_form_no("");
			header.setGrn_consignment_type("");
			header.setGrn_full_shippers(BigDecimal.ZERO);
			header.setGrn_loose_shippers(BigDecimal.ZERO);
			header.setGrn_weight(BigDecimal.ZERO);
			header.setGrn_volume(BigDecimal.ZERO);
			header.setGrn_transport_mode("");
			header.setGrn_octroi(bean.getGrnOctroi());
			header.setGrn_mod_usr_id(bean.getEmpId());
			header.setGrn_mod_dt(new Date());
			header.setGrn_mod_ip_add(bean.getIpAddress());
			header.setGrn_po_no(bean.getPoNumber());
			header.setGrn_po_date(
					bean.getPoDate() == null ? null : MedicoResources.convert_DD_MM_YYYY_toDate(bean.getPoDate()));
			header.setDsp_challan_no(bean.getDspChallanNo());
			header.setTransporter_name(bean.getTransportername());
			header.setGrn_ref_no(bean.getGrnRefNo());
			header.setVehicle_recd_time(
					bean.getVehicleReceivedTime() == null || bean.getVehicleReceivedTime().isEmpty()? null : MedicoResources.convert_DD_MM_YYYY_HH_MM_toDate(bean.getVehicleReceivedTime()));
			this.transactionalRepository.update(header);
		} else if (bean.getGrnId().compareTo(0L) != 0) {
			header = this.grnRepository.getObjectById(bean.getGrnId());
		}
		return header;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public GrnDetails saveGrnDetail(GRNBean bean, GrnHeader header) throws Exception {
		GrnDetails details = null;
		BigDecimal rateForGrn = null;
		if (bean.getPurchase_rate_entry_indicator().trim().equals("Y"))
			rateForGrn = bean.getBatch_costing_rate();
		else
			rateForGrn = bean.getRate();
		if (bean.getGrnDetailId().compareTo(0L) == 0 && !bean.isModifyDetails()) {
			details = new GrnDetails();
			details.setGrndGrnId(header.getGrnId());
			// details.setGrnd_grn_id(null);
			details.setGrnd_sl_no(0L);
			details.setGrnd_company(header.getGrn_company());
			details.setGrnd_loc_id(header.getGrn_loc_id());
			details.setGrnd_fin_year(header.getGrn_fin_year());
			details.setGrnd_period_code(header.getGrn_period_code());
			details.setGrnd_div_id(this.productMasterRepository.getDivIdByProdId(bean.getItemId()).getSmp_std_div_id());
			details.setGrnd_prod_id(bean.getItemId());
			details.setGrnd_batch_id(bean.getBatch_id().compareTo(0L) == 0 ? 0L : bean.getBatch_id());
			details.setGrnd_qty(bean.getQuantity());
			details.setGrnd_rate(bean.getRate());
			details.setGrnd_pur_rate(bean.getBatch_costing_rate());
			details.setGrnd_ins_usr_id(bean.getEmpId());
			details.setGrnd_mod_usr_id("");
			details.setGrnd_ins_dt(new Date());
			// details.setGrnd_mod_dt(null);
			details.setGrnd_ins_ip_add(bean.getIpAddress()); // ??
			details.setGrnd_mod_ip_add("");
			details.setGrnd_status(A);
			details.setGrnd_returnfrom(""); // ??
			details.setGrnd_challanno(""); // ??
			details.setGrnd_noofboxes(bean.getNumberOfBoxes() == null ? 0L : bean.getNumberOfBoxes());
			details.setGrnd_value(bean.getQuantity().multiply(bean.getRate()));
			details.setGrnd_value_pur(bean.getQuantity().multiply(bean.getBatch_costing_rate()));
			details.setGrnd_image_path("");
			details.setGrnd_bin_id(bean.getBinNumber() == null ? 0L : bean.getBinNumber());
			details.setGrnd_appr_status(E);
			details.setGrnd_image_moved("");
			details.setSgst_rate(BigDecimal.ZERO);
			details.setSgst_bill_amt(BigDecimal.ZERO);
			details.setCgst_rate(BigDecimal.ZERO);
			details.setCGST_BILL_AMT(BigDecimal.ZERO);
			details.setIgst_rate(BigDecimal.ZERO);
			details.setIgst_bill_amt(BigDecimal.ZERO);
			details.setGst_reverse_chg("");
			details.setGst_doc_type("");
			details.setText1("Customs Duty");
			details.setText2("Discount");
			details.setValue1(bean.getCustomDuty() == null ? BigDecimal.ZERO : bean.getCustomDuty());
			details.setValue2(bean.getDiscount() == null ? BigDecimal.ZERO : bean.getDiscount());
			details.setStock_type(bean.getStockTypeForNS());
			if (header.getGrn_stock_sa_or_ns().equalsIgnoreCase("SA")) {
				details.setStock_type("01");
			}
			details.setRemark(bean.getRemark());
			details.setGcma_req_ind(bean.getGcmaInd());
			details.setGcma_code(bean.getGcmaCode());
			details.setGcma_appr_date(bean.getGcmaAppDate() == null ? null
					: bean.getGcmaAppDate() == "" ? null
							: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGcmaAppDate()));
			details.setGcma_expiry_date(bean.getGcmaExpDate() == null ? null
					: bean.getGcmaExpDate() == "" ? null
							: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGcmaExpDate()));
			this.transactionalRepository.persist(details);
			// this.updateForGST(details, header,"PFZ");
		} else if (bean.getGrnDetailId().compareTo(0L) > 0 && bean.isModifyDetails()) {
			details = this.grnRepository.getObjectByDetailId(bean.getGrnDetailId());
			this.reverseBatchBeforeModify(details, header);
			details.setGrnd_prod_id(bean.getItemId());
			details.setGrnd_batch_id(bean.getBatch_id().compareTo(0L) == 0 ? 0L : bean.getBatch_id());
			details.setGrnd_qty(details.getGrnd_qty().add(bean.getQuantity()));
			details.setGrnd_rate(bean.getRate());
			details.setGrnd_pur_rate(bean.getBatch_costing_rate());
			// details.setGrnd_ins_usr_id(bean.getEmpId());
			details.setGrnd_mod_usr_id(bean.getEmpId());
			// details.setGrnd_ins_dt(new Date());
			details.setGrnd_mod_dt(new Date());
			// details.setGrnd_ins_ip_add(bean.getIpAddress()); // ??
			details.setGrnd_mod_ip_add(bean.getIpAddress());
			details.setGrnd_returnfrom(null); // ??
			details.setGrnd_challanno(null); // ??
			details.setGrnd_noofboxes(bean.getNumberOfBoxes() == null ? 0L : bean.getNumberOfBoxes());

			details.setGrnd_value(details.getGrnd_qty().multiply(bean.getRate()));
			details.setGrnd_value_pur(details.getGrnd_qty().multiply(bean.getBatch_costing_rate()));
			details.setGrnd_bin_id(bean.getBinNumber() == null ? 0L : bean.getBinNumber());
			details.setText1("Customs Duty");
			details.setText2("Discount");
			details.setValue1(bean.getCustomDuty() == null ? BigDecimal.ZERO : bean.getCustomDuty());
			details.setValue2(bean.getDiscount() == null ? BigDecimal.ZERO : bean.getDiscount());
			details.setRemark(bean.getRemark());
			details.setGcma_req_ind(bean.getGcmaInd());
			details.setGcma_code(bean.getGcmaCode());
			details.setGcma_appr_date(bean.getGcmaAppDate() == null ? null
					: bean.getGcmaAppDate() == "" ? null
							: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGcmaAppDate()));
			details.setGcma_expiry_date(bean.getGcmaExpDate() == null ? null
					: bean.getGcmaExpDate() == "" ? null
							: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getGcmaExpDate()));
			this.transactionalRepository.update(details);
			// this.updateForGST(details, header,"PFZ");
		}
		return details;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void createSmpBatchBeanForOtherLcations(SmpBatch smpBatch_original, Long locId) throws Exception {
		// check if batch already exists
		boolean batch_exists = this.batchMasterRepository.checkDuplicateRecordStk(locId,
				smpBatch_original.getBatch_no(), smpBatch_original.getBatch_prod_id());
		System.out.println("batch status " + batch_exists + " for " + locId);
		if (batch_exists) {
			return;
		}
		SmpBatch smpBatch = new SmpBatch();
		smpBatch.setBatch_prod_id(smpBatch_original.getBatch_prod_id());
		smpBatch.setBatch_loc_id(locId);
		smpBatch.setBatch_no(smpBatch_original.getBatch_no());
		smpBatch.setBatch_mfg_dt(smpBatch_original.getBatch_mfg_dt());
		smpBatch.setBatch_expiry_dt(smpBatch_original.getBatch_expiry_dt());
		smpBatch.setBatch_physical_stock(0L);
		smpBatch.setBatch_narration(smpBatch_original.getBatch_narration());
		smpBatch.setBatch_mfg_loc_id(smpBatch_original.getBatch_mfg_loc_id());
		smpBatch.setBatch_rate(smpBatch_original.getBatch_rate());
		smpBatch.setBatch_costing_rate(smpBatch_original.getBatch_costing_rate());
		smpBatch.setBatch_mktg_rate(smpBatch_original.getBatch_mktg_rate());
		smpBatch.setBatch_nrv(smpBatch_original.getBatch_nrv());
		smpBatch.setBatch_display_rate(smpBatch_original.getBatch_display_rate());
		smpBatch.setBatch_open_stock(0L);
		smpBatch.setBatch_in_stock(BigDecimal.ZERO);
		smpBatch.setBatch_out_stock(BigDecimal.ZERO);
		smpBatch.setBatch_exclude_loc("N");
		smpBatch.setBatch_exclude_PARTY("N");
		smpBatch.setBatch_with_held_qty(BigDecimal.ZERO);
		smpBatch.setBatch_erp_batch_cd(smpBatch_original.getBatch_erp_batch_cd());
		smpBatch.setBatch_ins_usr_id(smpBatch_original.getBatch_ins_usr_id());
		smpBatch.setBatch_mod_usr_id("");
		smpBatch.setBatch_ins_dt(new Date());
		smpBatch.setBatch_ins_ip_add(smpBatch_original.getBatch_ins_ip_add());
		smpBatch.setBatch_mod_ip_add("");
		smpBatch.setBatch_status("A");
		this.transactionalRepository.persist(smpBatch);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SmpBatch saveSmpBatch(GrnDetails detail, GRNBean bean) throws Exception {
		SmpBatch batch = null;
		if (bean.getBatch_id().compareTo(0L) == 0) {
			batch = new SmpBatch();
			batch.setBatch_prod_id(detail.getGrnd_prod_id());
			batch.setBatch_loc_id(detail.getGrnd_loc_id());
			batch.setBatch_no(
					((bean.getAllowBatchCreateInd() == N && detail.getGrnd_company() == PFZ) ? bean.getBatchNumber()
							: (bean.getAllowBatchCreateInd() == N && detail.getGrnd_company() != PFZ) ? "No Batch"
									: bean.getBatchNumber()));
			batch.setBatch_mfg_dt((bean.getAllowBatchCreateInd() == N) ? null
					: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getMfgDate()));
			batch.setBatch_expiry_dt((bean.getAllowBatchCreateInd() == N) ? null
					: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getExpDate()));
			batch.setBatch_narration(bean.getRemark());
			batch.setBatch_rate(bean.getRate());
			batch.setBatch_display_rate(this.productMasterRepository.getRateByProdId(bean.getItemId()));
			batch.setBatch_open_stock(0L);
			batch.setBatch_in_stock(detail.getGrnd_qty());
			batch.setBatch_with_held_qty(detail.getGrnd_qty());
			batch.setBatch_ins_usr_id(bean.getEmpId());
			batch.setBatch_ins_dt(new Date());
			batch.setBatch_ins_ip_add(bean.getIpAddress()); // ??
			batch.setBatch_status(A);
			batch.setBatch_physical_stock(0L);
			batch.setBatch_mfg_loc_id(0L);
			batch.setBatch_costing_rate(detail.getGrnd_pur_rate());
			batch.setBatch_mktg_rate(BigDecimal.ZERO);
			batch.setBatch_nrv(BigDecimal.ZERO);
			batch.setBatch_out_stock(BigDecimal.ZERO);
			batch.setBatch_exclude_loc("");
			batch.setBatch_exclude_PARTY("");
			batch.setBatch_exclude_PARTY("");
			this.transactionalRepository.persist(batch);

			// add batches for all locations
			if (!batch.getBatch_no().equalsIgnoreCase("No Batch")) {
				List<Location> locs_without_this_batch = this.locationRepository.getLocationsWithoutBatch(
						batch.getBatch_no(), batch.getBatch_prod_id(), batch.getBatch_loc_id());
				if (locs_without_this_batch != null && locs_without_this_batch.size() > 0) {
					Gson gson = new Gson();
					SmpBatch deepCopybatch = gson.fromJson(gson.toJson(batch), SmpBatch.class);
					// save batches for all other locations
					System.out.println("locs_without_this_batch::" + locs_without_this_batch.size());
					locs_without_this_batch.forEach(loc -> {
						System.out.println("id::" + loc.getLoc_id());
						try {
							this.createSmpBatchBeanForOtherLcations(deepCopybatch, loc.getLoc_id());
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}
			}

		} else {
			batch = this.batchMasterRepository.getObjectById(bean.getBatch_id());
			if (this.batchMasterRepository.checkIfBatchStockIsNegative(batch.getBatch_id(), batch.getBatch_prod_id(),
					batch.getBatch_loc_id()))
				throw new MedicoException("Stock negative, cannot update");
			if (this.batchMasterRepository.checkIfBatchStockIsNegativeByBatchIdProdIdLocId(batch.getBatch_id(),
					batch.getBatch_prod_id(), batch.getBatch_loc_id()))
				throw new MedicoException("Stock negative, cannot update");
			batch.setBatch_rate(bean.getRate());
			batch.setBatch_display_rate(this.productMasterRepository.getRateByProdId(bean.getItemId()));
			batch.setBatch_narration(bean.getRemark());
			batch.setBatch_mod_usr_id(bean.getEmpId());
			batch.setBatch_mod_dt(new Date());
			batch.setBatch_mod_ip_add(bean.getIpAddress()); // ??
			batch.setBatch_status(A);
			// if(bean.getGrnDetailId().compareTo(0L) == 0) {
			batch.setBatch_in_stock(batch.getBatch_in_stock().add(detail.getGrnd_qty()));
			batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().add(detail.getGrnd_qty()));
			// } else {
			// detail=this.grnRepository.getObjectByDetailId(bean.getGrnDetailId());
			// batch.setBatch_in_stock(batch.getBatch_in_stock().subtract(detail.getGrnd_qty()).add(detail.getGrnd_qty().add(bean.getQuantity())));
			// batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().subtract(detail.getGrnd_qty()).add(detail.getGrnd_qty().add(bean.getQuantity())));
			// }
			this.transactionalRepository.update(batch);
		}
		return batch;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBatchStockLog(GRNBean bean, GrnDetails detail, GrnHeader header) throws Exception {
		BatchStockLog batchStockLog = this.batchStockLogRepository.getRecordByProdIdNew(detail.getGrnd_prod_id(),
				detail.getGrnd_batch_id(), detail.getGrnd_loc_id(), header.getGrn_dt(), GRN);
		if (batchStockLog != null) {
			batchStockLog.setBtchstklg_mod_dt(MedicoResources
					.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date()))); // ??
			batchStockLog.setBtchstklg_mod_usr_id(bean.getEmpId());
			batchStockLog.setBtchstklg_mod_ip_add(bean.getIpAddress()); // ??
			batchStockLog.setBtchstklg_status(A);
			System.out.println("Detail Id " + bean.getGrnDetailId());
			if (bean.getGrnDetailId().compareTo(0L) == 0) {
				batchStockLog.setBtchstklg_qty(batchStockLog.getBtchstklg_qty().add(bean.getQuantity()));
				batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
			} else {
				detail = this.grnRepository.getObjectByDetailId(bean.getGrnDetailId());
//				batchStockLog.setBtchstklg_qty(batchStockLog.getBtchstklg_qty().subtract(detail.getGrnd_qty()).add(detail.getGrnd_qty().add(bean.getQuantity())));
//				batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
				batchStockLog.setBtchstklg_qty(batchStockLog.getBtchstklg_qty().add(detail.getGrnd_qty()));
				batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
			}
			this.transactionalRepository.update(batchStockLog);
		} else {
			batchStockLog = new BatchStockLog();
			batchStockLog.setBtchstklg_fin_year(detail.getGrnd_fin_year());
			batchStockLog.setBtchstklg_date(MedicoResources
					.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(header.getGrn_dt())));
			batchStockLog.setBtchstklg_loc_id(detail.getGrnd_loc_id());
			batchStockLog.setBtchstklg_prod_id(detail.getGrnd_prod_id());
			batchStockLog.setBtchstklg_batch_id(detail.getGrnd_batch_id());
			batchStockLog.setBtchstklg_div_id(detail.getGrnd_div_id());
			batchStockLog.setBtchstklg_tran_type(GRN);
			batchStockLog.setBtchstklg_qty(detail.getGrnd_qty());
			batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
			batchStockLog.setBtchstklg_ins_usr_id(detail.getGrnd_ins_usr_id());
			batchStockLog.setBtchstklg_mod_usr_id("");
			batchStockLog.setBtchstklg_ins_dt(MedicoResources
					.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date())));
			// batchStockLog.setBtchstklg_mod_dt(null);
			batchStockLog.setBtchstklg_ins_ip_add(bean.getIpAddress()); // ??
			batchStockLog.setBtchstklg_mod_ip_add("");
			batchStockLog.setBtchstklg_status(A);
			this.transactionalRepository.persist(batchStockLog);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public SmpBatchNS saveSmpBatchNonSalable(GrnDetails detail, GRNBean bean) throws Exception {
		SmpBatchNS batch = null;

		batch = this.batchMasterRepository.getNSObjectByIdAndStocktype(detail.getGrnd_batch_id(),
				detail.getGrnd_prod_id(), detail.getGrnd_loc_id(),
				bean.getStockTypeForNS() != null && bean.getStockTypeForNS() != "" ? bean.getStockTypeForNS()
						: STOCK_TYPE_07);

		// System.out.println("stockType::::28 "+batch.getStock_type());
		// System.out.println("batch:::"+batch.getBatch_id());
		if (bean.getBatch_id().compareTo(0L) == 0 || batch == null) {
			batch = new SmpBatchNS();
			batch.setBatch_id(bean.getBatch_id());
			batch.setBatch_rate(bean.getRate());
			batch.setBatch_prod_id(detail.getGrnd_prod_id());
			batch.setBatch_loc_id(detail.getGrnd_loc_id());
			batch.setBatch_no(
					((bean.getAllowBatchCreateInd() == N && detail.getGrnd_company() == PFZ) ? bean.getBatchNumber()
							: (bean.getAllowBatchCreateInd() == N && detail.getGrnd_company() != PFZ) ? "No Batch"
									: bean.getBatchNumber()));
			batch.setBatch_mfg_dt((bean.getAllowBatchCreateInd() == N) ? null
					: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getMfgDate()));
			batch.setBatch_expiry_dt((bean.getAllowBatchCreateInd() == N) ? null
					: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getExpDate()));
			batch.setBatch_narration("");
			batch.setBatch_rate(bean.getRate());
			batch.setBatch_display_rate(this.productMasterRepository.getRateByProdId(bean.getItemId()));
			batch.setBatch_open_stock(0L);
			batch.setBatch_in_stock(detail.getGrnd_qty());
			batch.setBatch_with_held_qty(detail.getGrnd_qty());
			batch.setBatch_ins_usr_id(bean.getEmpId());
			batch.setBatch_ins_dt(new Date());
			batch.setBatch_ins_ip_add(bean.getIpAddress()); // ??
			batch.setBatch_status(A);
			batch.setStock_type(
					bean.getStockTypeForNS() != null && bean.getStockTypeForNS() != "" ? bean.getStockTypeForNS()
							: null);
			// System.out.println("bean.getStockTypeForNS()::"+bean.getStockTypeForNS()+"::"+"setStock_type");
			batch.setBatch_physical_stock(0L);
			batch.setBatch_mfg_loc_id(0L);
			batch.setBatch_costing_rate(detail.getGrnd_pur_rate());
			batch.setBatch_mktg_rate(BigDecimal.ZERO);
			batch.setBatch_nrv(BigDecimal.ZERO);
			batch.setBatch_out_stock(BigDecimal.ZERO);
			batch.setBatch_exclude_loc("");
			batch.setBatch_exclude_PARTY("");
			batch.setBatch_exclude_PARTY("");
			this.transactionalRepository.persist(batch);
		} else {
			if (this.batchMasterRepository.checkIfNSBatchStockIsNegative(batch.getBatch_id(), batch.getBatch_prod_id(),
					batch.getBatch_loc_id(), batch.getStock_type()))
				throw new MedicoException("Stock negative, cannot update");
			if (this.batchMasterRepository.checkIfNSBatchStockIsNegativeByBatchIdProdIdLocId(batch.getBatch_id(),
					batch.getBatch_prod_id(), batch.getBatch_loc_id(), batch.getStock_type()))
				throw new MedicoException("Stock negative, cannot update");
			batch.setBatch_rate(bean.getRate());
			batch.setBatch_display_rate(this.productMasterRepository.getRateByProdId(bean.getItemId()));
			batch.setBatch_narration(bean.getRemark());
			batch.setBatch_mod_usr_id(bean.getEmpId());
			batch.setBatch_mod_dt(new Date());
			batch.setBatch_mod_ip_add(bean.getIpAddress()); // ??
			batch.setBatch_status(A);
			batch.setStock_type(
					bean.getStockTypeForNS() != null && bean.getStockTypeForNS() != "" ? bean.getStockTypeForNS()
							: null);
			// System.out.println("bean.getStockTypeForNS()"+bean.getStockTypeForNS()+"::"+"Stock_type"+batch.getStock_type());
//			if(bean.getGrnDetailId().compareTo(0L) == 0) {
//				batch.setBatch_in_stock(batch.getBatch_in_stock().add(bean.getQuantity()));
//				batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().add(bean.getQuantity()));
//			} else {
//				batch.setBatch_in_stock(batch.getBatch_in_stock().add(bean.getQuantity()));
//				batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().add(bean.getQuantity()));
//			}
			batch.setBatch_in_stock(batch.getBatch_in_stock().add(detail.getGrnd_qty()));
			batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().add(detail.getGrnd_qty()));

			this.transactionalRepository.update(batch);
		}
		return batch;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBatchStockLogNonSalable(GRNBean bean, GrnDetails detail, GrnHeader header) throws Exception {
		BatchStockLogNS batchStockLog = this.batchStockLogRepository.getNSRecordByProdIdNew(detail.getGrnd_prod_id(),
				detail.getGrnd_batch_id(), detail.getGrnd_loc_id(), header.getGrn_dt(),
				bean.getStockTypeForNS() != null && bean.getStockTypeForNS() != "" ? bean.getStockTypeForNS()
						: STOCK_TYPE_07,
				GRN);
		if (batchStockLog != null) {
			batchStockLog.setBtchstklg_mod_dt(MedicoResources
					.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date()))); // ??
			batchStockLog.setBtchstklg_mod_usr_id(bean.getEmpId());
			batchStockLog.setBtchstklg_mod_ip_add(bean.getIpAddress()); // ??
			batchStockLog.setBtchstklg_status(A);
			batchStockLog.setBtchstklg_stock_type(
					bean.getStockTypeForNS() != null && bean.getStockTypeForNS() != "" ? bean.getStockTypeForNS()
							: STOCK_TYPE_07);
			// System.out.println("bean.getStockTypeForNS()::"+bean.getStockTypeForNS()+"::"+"Btchstklg_stock_type"+batchStockLog.getBtchstklg_stock_type());
			System.out.println("Detail Id " + bean.getGrnDetailId());
			if (bean.getGrnDetailId().compareTo(0L) == 0) {
				batchStockLog.setBtchstklg_qty(batchStockLog.getBtchstklg_qty().add(bean.getQuantity()));
				batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
			} else {
				detail = this.grnRepository.getObjectByDetailId(bean.getGrnDetailId());
//				batchStockLog.setBtchstklg_qty(batchStockLog.getBtchstklg_qty().subtract(detail.getGrnd_qty()).add(detail.getGrnd_qty().add(bean.getQuantity())));
//				batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
				batchStockLog.setBtchstklg_qty(batchStockLog.getBtchstklg_qty().add(detail.getGrnd_qty()));
				batchStockLog.setBtchstklg_value(detail.getGrnd_rate().multiply((batchStockLog.getBtchstklg_qty())));
			}
			this.transactionalRepository.update(batchStockLog);
		} else {
			batchStockLog = new BatchStockLogNS();
			batchStockLog.setBtchstklg_fin_year(detail.getGrnd_fin_year());
			batchStockLog.setBtchstklg_date(MedicoResources
					.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(header.getGrn_dt())));
			batchStockLog.setBtchstklg_loc_id(detail.getGrnd_loc_id());
			batchStockLog.setBtchstklg_prod_id(detail.getGrnd_prod_id());
			batchStockLog.setBtchstklg_batch_id(detail.getGrnd_batch_id());
			batchStockLog.setBtchstklg_tran_type(GRN);
			batchStockLog.setBtchstklg_qty(detail.getGrnd_qty());
			batchStockLog.setBtchstklg_value(detail.getGrnd_qty().multiply(detail.getGrnd_rate()));
			batchStockLog.setBtchstklg_ins_usr_id(detail.getGrnd_ins_usr_id());
			batchStockLog.setBtchstklg_mod_usr_id("");
			batchStockLog.setBtchstklg_ins_dt(MedicoResources
					.convert_DD_MM_YYYY_toDate(MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date())));
			// batchStockLog.setBtchstklg_mod_dt(null);
			batchStockLog.setBtchstklg_ins_ip_add(bean.getIpAddress()); // ??
			batchStockLog.setBtchstklg_mod_ip_add("");
			batchStockLog.setBtchstklg_status(A);
			batchStockLog.setBtchstklg_stock_type(
					bean.getStockTypeForNS() != null && bean.getStockTypeForNS() != "" ? bean.getStockTypeForNS()
							: STOCK_TYPE_07);
			batchStockLog.setBtchstklg_div_id(detail.getGrnd_div_id()); // ??
			this.transactionalRepository.persist(batchStockLog);
		}
	}

	@Override
	public List<V_Grn_Details> getGrnDetailViewByGrnId(Long grnId) throws Exception {
		return this.grnRepository.getGrnDetailViewByGrnId(grnId);
	}

	@Override
	public boolean checkIfBatchExistsInGrnDetail(Long grnId, Long prodId, Long batchId) throws Exception {
		return this.grnRepository.checkIfBatchExistsInGrnDetail(grnId, prodId, batchId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BigDecimal deleteGrnDetailById(Long grnDetailId, String empId) {
		BigDecimal returnValueForGrnDetDel = null;
		try {

			SG_d_parameters_details pur_rate_param = this.parameterService
					.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN");

			GrnDetails detail = this.grnRepository.getObjectByDetailId(grnDetailId);
			
			if (detail != null) {
				
				BigDecimal grnvalue = this.grnRepository.getTotalGrnValueByGrnId(detail.getGrndGrnId(), null);
				
				System.err.println("subs :: " + grnvalue.subtract(detail.getGrnd_value()) + "::: " + detail.getGrnd_value());
				grnvalue = grnvalue.subtract(detail.getGrnd_value());

				
				BigDecimal grn_pur_value = this.grnRepository.getTotal_Pur_GrnValueByGrnId(detail.getGrndGrnId(), null);
				grn_pur_value = grn_pur_value.subtract(detail.getGrnd_value_pur());
				// System.out.println("grnvalue"+grnvalue);

				
				GrnHeader header = this.grnRepository.getObjectById(detail.getGrndGrnId());
				SG_d_parameters_details parameter = this.parameterRepository
						.getObjectById(header.getGrn_stock_type_id());

				if (header.getGrn_stock_sa_or_ns().equals(SA)) {
					SmpBatch batch = this.batchMasterRepository.getObjectById(detail.getGrnd_batch_id());
					if (batch != null) {
						if (this.batchMasterRepository.checkIfBatchStockIsNegative(batch.getBatch_id(),
								batch.getBatch_prod_id(), batch.getBatch_loc_id()))
							throw new MedicoException("Stock negative, cannot update");
						if (this.batchMasterRepository.checkIfBatchStockIsNegativeByBatchIdProdIdLocId(
								batch.getBatch_id(), batch.getBatch_prod_id(), batch.getBatch_loc_id()))
							throw new MedicoException("Stock negative, cannot update");
						batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().subtract(detail.getGrnd_qty()));
						batch.setBatch_in_stock(batch.getBatch_in_stock().subtract(detail.getGrnd_qty()));
						batch.setBatch_mod_dt(new Date());
						batch.setBatch_mod_ip_add(null);
						batch.setBatch_mod_usr_id(empId);
						this.transactionalRepository.update(batch);
					} else {
						throw new MedicoException("Batch Details not found.");
					}
					BatchStockLog batchLog = this.batchStockLogRepository.getRecordByProdIdNew(detail.getGrnd_prod_id(),
							detail.getGrnd_batch_id(), detail.getGrnd_loc_id(), header.getGrn_dt(), GRN);
					// BatchStockLog batchLog =
					// this.batchStockLogRepository.getRecordByProdIdNew(detail.getGrnd_prod_id(),
					// detail.getGrnd_batch_id(), detail.getGrnd_loc_id());
					if (batchLog != null) {
						batchLog.setBtchstklg_qty(batchLog.getBtchstklg_qty().subtract(detail.getGrnd_qty()));
						batchLog.setBtchstklg_value(batchLog.getBtchstklg_value()
								.subtract((detail.getGrnd_qty().multiply(detail.getGrnd_rate()))));
						batchLog.setBtchstklg_mod_ip_add(null);
						batchLog.setBtchstklg_mod_dt(new Date());
						batchLog.setBtchstklg_mod_usr_id(empId);
						transactionalRepository.update(batchLog);
					} else {
						throw new MedicoException("Batch Stock Log not Found.");
					}
				} else {
					SmpBatchNS batchNS = this.batchMasterRepository.getNSObjectByIdAndStocktype(
							detail.getGrnd_batch_id(), detail.getGrnd_prod_id(), detail.getGrnd_loc_id(),
							parameter.getSgprmdet_nm());
					if (batchNS != null) {
						if (this.batchMasterRepository.checkIfNSBatchStockIsNegative(batchNS.getBatch_id(),
								batchNS.getBatch_prod_id(), batchNS.getBatch_loc_id(), batchNS.getStock_type()))
							throw new MedicoException("Stock negative, cannot update");
						if (this.batchMasterRepository.checkIfNSBatchStockIsNegativeByBatchIdProdIdLocId(
								batchNS.getBatch_id(), batchNS.getBatch_prod_id(), batchNS.getBatch_loc_id(),
								batchNS.getStock_type()))
							throw new MedicoException("Stock negative, cannot update");
						batchNS.setBatch_with_held_qty(batchNS.getBatch_with_held_qty().subtract(detail.getGrnd_qty()));
						batchNS.setBatch_in_stock(batchNS.getBatch_in_stock().subtract(detail.getGrnd_qty()));
						batchNS.setBatch_mod_dt(new Date());
						batchNS.setBatch_mod_ip_add(null);
						batchNS.setBatch_mod_usr_id(empId);
						this.transactionalRepository.update(batchNS);
					} else {
						throw new MedicoException("Batch Details not found.");
					}
					BatchStockLogNS batchLogNS = this.batchStockLogRepository.getNSRecordByProdIdNew(
							detail.getGrnd_prod_id(), detail.getGrnd_batch_id(), detail.getGrnd_loc_id(),
							header.getGrn_dt(), parameter.getSgprmdet_nm(), GRN);
					if (batchLogNS != null) {
						batchLogNS.setBtchstklg_qty(batchLogNS.getBtchstklg_qty().subtract(detail.getGrnd_qty()));
						batchLogNS.setBtchstklg_value(batchLogNS.getBtchstklg_value()
								.subtract((detail.getGrnd_qty().multiply(detail.getGrnd_rate()))));
						batchLogNS.setBtchstklg_mod_ip_add(null);
						batchLogNS.setBtchstklg_mod_dt(new Date());
						batchLogNS.setBtchstklg_mod_usr_id(empId);
						transactionalRepository.update(batchLogNS);
					} else {
						throw new MedicoException("Batch Stock Log not Found.");
					}
				}

		
//				header.setGrn_total_value(grnvalue);
//				header.setGrn_total_value_pur(grn_pur_value);

				detail.setGrnd_status("I");
				detail.setGrnd_appr_status("D");
				transactionalRepository.update(detail);
				
			// added abhishek 08/03/2025
			Tax_data_for_grn taxdata =	this.grnRepository.getTaxData_And_total_value(header.getGrnId());

			header.setGrn_total_value(taxdata.getGrn_total_value());
			header.setGrn_total_value_pur(taxdata.getGen_total_value_pur());
			header.setSgst_bill_amt(taxdata.getSgst_total());
			header.setCgst_bill_amt(taxdata.getCgst_total());
			header.setIgst_bill_amt(taxdata.getIgst_total());
			
			header.setRoundoff(this.calculateRound_Off(taxdata.getGrn_total_value(), taxdata.getSgst_total(), taxdata.getCgst_total(), 
			taxdata.getIgst_total(), header.getValue1(), header.getValue2()));
			transactionalRepository.update(header);

				// this.grnRepository.deleteById(grnDetailId);
				if (pur_rate_param.getSgprmdet_text1().trim().equals("Y")) {
					returnValueForGrnDetDel = grn_pur_value;
				} else {
					returnValueForGrnDetDel = grnvalue;
				}
				return returnValueForGrnDetDel;
			} else {
				throw new MedicoException("GRN Details not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return null;
	}

	@Override
	public void updateDetailOnSelfApproveOfGRN(@RequestParam("grnId") Long grnId, @RequestParam("userName") String user,
			@RequestParam("empEmail") String email, @RequestParam("remark") String remark, HttpServletRequest request,
			List<Long> detailIds, List<BigDecimal> shortQtys, List<BigDecimal> damageQtys, String companyCode)
			throws Exception {
		String ip_addr = request.getRemoteAddr();
		String tranType = null;
		;
		GrnHeader header = grnRepository.getObjectById(grnId);
		Location location = locationService.getLocationNameByEmployeeId(user);

		Company company = companyMasterRepository.getCompanyDetails();

//	if( company.getCfa_to_stk_ind().equals("N")) {
//
////		//old logic 
//		if(MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
//			//if (header.getGrn_appr_req().compareTo(1l) == 0) {
//				tranType = MedicoConstants.GRN_APPR_PIPL_PS;
//			/*} else if (header.getGrn_appr_req().compareTo(2l) == 0) {
//				tranType = MedicoConstants.GRN_APPR_PIPL_PI;
//			}*/
//		}
//		else {
//			//if (header.getGrn_appr_req().compareTo(1l) == 0) {
//				tranType = MedicoConstants.GRN_APPR_PFZ_PS;
//			/*} else if (header.getGrn_appr_req().compareTo(2l) == 0) {
//				tranType = MedicoConstants.GRN_APPR_PFZ_PI;
//			}*/
//		}
//
//	}else {
//		//new 
//		//tranType = MedicoConstants.grn_loc_wise_approval_map.get(location.getLoc_id());
//		//new alternate logic
//		tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(),
//				GRN_FULL_TRAN_NAME, header.getGrn_company());
//		
//	}

		if (company.getCfa_to_stk_ind().equalsIgnoreCase("N")) {
			// old logic
			if (MedicoConstants.PIPL_LOC.contains(location.getLoc_id())) {
				if (header.getGrn_appr_req().compareTo(1l) == 0) {
					tranType = MedicoConstants.GRN_APPR_PIPL_PS;
				} else if (header.getGrn_appr_req().compareTo(2l) == 0) {
					tranType = MedicoConstants.GRN_APPR_PIPL_PI;
				}
			} else {
				if (header.getGrn_appr_req().compareTo(1l) == 0) {
					tranType = MedicoConstants.GRN_APPR_PFZ_PS;
				} else if (header.getGrn_appr_req().compareTo(2l) == 0) {
					
					if(company.company.trim().equalsIgnoreCase("VET")) {
						tranType = MedicoConstants.GRN_APPR_PFZ_PS;
					}else {
						tranType = MedicoConstants.GRN_APPR_PFZ_PI;
					}
					
				}
			}

		} else {
			tranType = this.taskMasterService.getTranTypeByLocIdAndApprovalType(location.getLoc_id(),
					GRN_FULL_TRAN_NAME, header.getGrn_company());
		}

		System.out.println("tranType:::" + tranType);
		if (tranType == null || tranType.isEmpty()) {
			throw new Exception("No approval data found for this location.");
		}

		if (header.getGrn_appr_status().equals("E")) {
			try {

				String TASK_FLOW = MedicoConstants.BPM_LEVELWISE;
				System.out.println("tranType " + tranType);
				List<TaskMaster> masters = taskMasterService.getTask(null, null, null, tranType, null, null, TASK_FLOW,
						null, null);

				if (masters.size() == 0) {
					throw new MedicoException("Task master record not found");
				}
				TaskMaster taskMaster = masters.get(0);
				List<Task_Master_Dtl> task_Master_Dtls = taskMasterService.getTaskDetail(taskMaster.getTask_id());
				if (task_Master_Dtls.size() == 0) {
					throw new MedicoException("Task master detail record not found");
				}

				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("initiator", user);
				variables.put("initiator_desc", user);
				variables.put("initiator_email", email);
				variables.put("tran_ref_id", header.getGrnId());
				variables.put("tran_type", tranType);
				variables.put("inv_group", null);
				variables.put("loc_id", header.getGrn_loc_id());
				variables.put("cust_id", 0L);
				variables.put("fs_id", 0L);
				variables.put("tran_no", header.getGrn_delivery_no());
				variables.put("company_code", "PFZ");
				variables.put("totaltask", masters.size());
				variables.put("amount", BigDecimal.ZERO);
				variables.put("escalatetime", null);
				variables.put("fin_year_id", header.getGrn_fin_year());
				variables.put("stock_point_id", header.getGrn_loc_id());
				variables.put("doc_type", "NS");
				variables.put("task_flow", TASK_FLOW);
				variables.put("remark", remark);
				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("grnApproval", variables);
				/* Status Updated in header */
				System.out.println("appr status changed to F at self Approval");
				grnRepository.updateDetailOnSelfApproveOfGRN(user, grnId, remark, request, detailIds, shortQtys,
						damageQtys);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			}
		}
		// send Mail
		this.sendMail.sendMail(grnId, user, "selfApprove", null, "", "nextApproval",
				Long.valueOf(header.getGrn_fin_year()), tranType, companyCode, GRN_FULL_TRAN_NAME);
	}

//    @Override
//    public void sendMailForGRN(Long grnId,String user,String apptovalType,String taskId,String status,String nextApprovar,Long finYear,String tranType) throws Exception {
//    	System.out.println("grnId "+grnId);
//    	System.out.println("user "+user);
//    	System.out.println("apptovalType "+apptovalType);
//    	List<Object> grnList = grnRepository.getGrnDetailsForMailSend(grnId,user,apptovalType);
//    	Object[] grnArr= (Object[]) grnList.get(0);
//    	String emailEmployee=grnArr[13].toString();
//    	MailBean mail=null;
//    	String link=null;
//    	List<MailBean> list=taskMasterService.getMailSendDetails(grnId,nextApprovar,tranType);
//    	mail=list.get(0);
//    	link="rest/saveApprovalDataList?task_id="+mail.getAct_taskid()+"&decision=approve&userName="+mail.getAssignee_()+"&remark="+mail.getAct_taskid()+"&tran_id="+grnId;
//    	mail.setLink(link);
//    	System.out.println("Link "+link);
//    	System.out.println("mailID "+mail.getEmp_email());
//    	sendMail.sendApprovalMailForGRN(grnList, Arrays.asList("sachin.lokhande@excelsof.com"),status, user,"GRN Forwarded For Approval  ",mail,link,nextApprovar);
//    }
	// Called from Activity after final Approval
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveGrnApproval(Long grnId, String last_approved_by, String comp_cd, String isapproved,
			String motivation) {
		System.out.println("final :approval -----" + grnId + last_approved_by + comp_cd + isapproved + motivation);
		System.out.println("In Approve GRN");
		String user_id = "";
		Map<Long, String> apprMap = new HashMap<Long, String>();

		try {
			comp_cd = companyMasterRepository.getCompanyDetails().getCompany();
			System.out.println("CompCd : " + comp_cd);
			List<GrnDetails> list = grnRepository.getGrnDetailsByGrnId(grnId);
			GrnHeader grnHeader = grnRepository.getObjectById(grnId);
			grnHeader.setGrn_appr_acq(0L);
			grnHeader.setGrn_mod_usr_id(last_approved_by);
			grnHeader.setGrn_mod_ip_add(grnHeader.getGrn_ins_ip_add());
			grnHeader.setGrn_mod_dt(new Date());
//				    grnHeader.setRemarks(motivation);
			if (isapproved.equalsIgnoreCase("A")) {
				System.out.println("------------GRN Approved-------------");
				for (GrnDetails grnDetails : list) {
					System.out.println("Updating batch " + grnDetails.getGrnd_batch_id());
					System.out.println("GRN QTY " + grnDetails.getGrnd_qty());
					if (grnHeader.getGrn_stock_sa_or_ns().equalsIgnoreCase("SA")) {
						grnRepository.updateBatchForGrn(grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_qty());
					} else {
						grnRepository.updateBatchNSForGrn(grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_qty(),
								grnDetails.getGrnd_loc_id(), grnDetails.getStock_type(), A,
								grnDetails.getGrnd_prod_id());
					}
					grnDetails.setGrnd_appr_status("A");
					this.transactionalRepository.update(grnDetails);
				}
				grnHeader.setGrn_appr_status("A");
				grnHeader.setGrn_mod_dt(new Date());
				this.transactionalRepository.update(grnHeader);// Iaa Creation

			} else if (isapproved.equalsIgnoreCase("D")) {
				System.out.println("------------Discard-------------!!");
				for (GrnDetails grnDetails : list) {
					if (!comp_cd.trim().equals(PAL) && !comp_cd.trim().equals(PFZ)) {
						if (grnHeader.getGrn_stock_sa_or_ns().equalsIgnoreCase("SA")) {
							grnRepository.updateBatchQuarantine(grnDetails.getGrnd_batch_id(),
									grnDetails.getGrnd_qty());
						} else {
							grnRepository.updateBatchNSQuarantine(grnDetails.getGrnd_batch_id(),
									grnDetails.getGrnd_qty());
						}
					}
					if (comp_cd.trim().equals(PAL) || comp_cd.trim().equals(PFZ)) {
						grnDetails.setGrnd_appr_status("E");
					} else {
						grnDetails.setGrnd_appr_status("D");
					}

					this.transactionalRepository.update(grnDetails);
				}

				if (comp_cd.trim().equals(PAL) || comp_cd.trim().equals(PFZ)) {
					grnHeader.setGrn_confirm("N");
					grnHeader.setGrn_appr_status("E");

					String useremail = usermasterservice.getUserByEmpId(grnHeader.getGrn_ins_usr_id()).getLd_email();
					System.out.println("Email : " + useremail);
					List<String> tolist = new ArrayList<String>();
					tolist.add(useremail);
					String subject = "GRN -" + grnHeader.getGrn_no() + " Has been Discarded By Manager ON Date : "
							+ MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date());
					sm.grnDiscardNotificationMail(tolist, subject);
				} else {
					grnHeader.setGrn_status("I");
					grnHeader.setGrn_appr_status("D");
				}

				grnHeader.setGrn_mod_dt(new Date());
				this.transactionalRepository.update(grnHeader);
			} else {
				grnHeader.setGrn_appr_acq(0L);
				grnHeader.setGrn_appr_status("E");
				grnHeader.setGrn_appr_cycle(grnHeader.getGrn_appr_cycle() + 1);
				grnHeader.setGrn_mod_dt(new Date());
				this.transactionalRepository.update(grnHeader);
			}
//				    if (isapproved.equalsIgnoreCase("A")) {
//				    	Period period=this.periodMasterRepository.getPeriodMasterByCompany(comp_cd);
//				    	this.grnRepository.IaaCreationInGrnApproval(grnId, grnHeader.getGrn_fin_year(),period.getPeriod_code(), last_approved_by,grnHeader.getGrn_ins_ip_add());
//				    }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
	}

	// TODO add method to calculate gst
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateForGST(GrnDetails grnDetails, GrnHeader grnHeader, String comp_cd) throws Exception {

		SG_d_parameters_details purchase_rate_ind_param_dets = this.parameterRepository
				.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN");

		BigDecimal rateToBeUsed = null;
		if (purchase_rate_ind_param_dets.getSgprmdet_text1().equals("Y")) {
			rateToBeUsed = grnDetails.getGrnd_pur_rate();
		} else
			rateToBeUsed = grnDetails.getGrnd_rate();

		Location recDepot = locationRepository.getObjectById(grnHeader.getGrn_loc_id());
		String rec_gst_req_ind = recDepot.getGst_req_ind() != null ? recDepot.getGst_req_ind().trim() : "N";
		Supplier supplier = supplierRepository.supplierStateGstNo(grnHeader.getGrn_supplier_id());
		if (supplier == null)
			throw new MedicoException("Supplier not found.");
		String sup_gst_req_ind = supplier.getGst_req_ind() != null ? supplier.getGst_req_ind().trim() : "N";
		// apply gst only when gst_req_ind is Y in receiving location and
		// supplier -- Uday Sir
		System.out.println("rec_gst_req_ind " + rec_gst_req_ind);
		System.out.println("sup_gst_req_ind " + sup_gst_req_ind);
		
		if (rec_gst_req_ind.equalsIgnoreCase("Y") && sup_gst_req_ind.equalsIgnoreCase("Y")) {
			Boolean applyZeroGST = false;
			String send_gst_reg_no = supplier.getGst_reg_no();
			String gst_type_ind = "", gst_doc_type = "", gst_reverse_chg = "";

			if (supplier.getState_id().compareTo(recDepot.getLoc_state_id()) == 0) {
				gst_type_ind = "G";
				if (send_gst_reg_no != null && !send_gst_reg_no.equalsIgnoreCase("")) {
					if (send_gst_reg_no.trim().equalsIgnoreCase(recDepot.getGst_reg_no().trim())) {
						gst_doc_type = "BS";
						gst_reverse_chg = "N";
						applyZeroGST = true;
					} else {
						gst_doc_type = "SN";
						gst_reverse_chg = "N";
					}
				} else {
					gst_doc_type = "SR";
					gst_reverse_chg = "Y";
				}
			} else {
				gst_type_ind = "I";
				if (send_gst_reg_no != null && !send_gst_reg_no.equalsIgnoreCase("")) {
					gst_doc_type = "IN";
					gst_reverse_chg = "N";
				} else {
					gst_doc_type = "IR";
					gst_reverse_chg = "Y";
				}
			}
			// tran_type=03

			String gst_t = calculateGstRepository.getGST_Doc_type_Para_code(MedicoConstants.GRN, gst_doc_type);
			if (gst_t == null || gst_t.equalsIgnoreCase("")) {
				throw new MedicoException("GST Doc Type not found.");
			}
			gst_doc_type = gst_t;

			BigDecimal[] detail_sum = new BigDecimal[5];
			Arrays.fill(detail_sum, BigDecimal.ZERO);
			String text1 = grnDetails.getText1();
			String text2 = grnDetails.getText2();
			// get tax param here
			BigDecimal cgst_rate = BigDecimal.ZERO;
			BigDecimal igst_rate = BigDecimal.ZERO;
			BigDecimal sgst_rate = BigDecimal.ZERO;
			Object[] obj = null;
			if (!applyZeroGST) {
				obj = new Object[12];
				TaxParamBean taxparam = calculateGstRepository
						.getTaxParamsByStateIdAndProdId(recDepot.getLoc_state_id(), grnDetails.getGrnd_prod_id());
				System.out.println("Taxparam " + taxparam);
				obj[0] = taxparam.getProd_code();
				obj[1] = taxparam.getOutput_tax_param();
				obj[2] = taxparam.getSt_vat();
				obj[3] = taxparam.getCst_rt();
				obj[4] = taxparam.getSurch();
				obj[5] = taxparam.getIc_chgs();
				obj[6] = taxparam.getCess();
				obj[7] = taxparam.getTo_tax();
				obj[8] = taxparam.getProd_disc();
				obj[9] = taxparam.getCgst();
				obj[10] = taxparam.getIgst();
				obj[11] = taxparam.getSgst();
				cgst_rate = obj[9] != null ? new BigDecimal(obj[9].toString()) : BigDecimal.ZERO;
				igst_rate = obj[10] != null ? new BigDecimal(obj[10].toString()) : BigDecimal.ZERO;
				sgst_rate = obj[11] != null ? new BigDecimal(obj[11].toString()) : BigDecimal.ZERO;
			}

			TaxCalculationBean taxBean = calculateGstService.generateGstValues(MedicoConstants.GRN, gst_type_ind, // gst_type_ind
					grnDetails.getGrnd_qty(), // billedQty
					BigDecimal.ZERO, // freeQty
					BigDecimal.ZERO, // replQty
					rateToBeUsed, // rate//rate may be different based on client requirement
					BigDecimal.ZERO, // party discount
					obj != null ? obj[1].toString() : "NNNNNN", // Out_Tax_Param,
					null, // In_Tax_Param
					cgst_rate, // cgst_rate
					igst_rate, // igst_rate
					sgst_rate, // sgst_rate
					BigDecimal.ZERO, // Surch_Rate
					BigDecimal.ZERO, // Cess_Rate
					BigDecimal.ZERO, // TOT_Rate
					BigDecimal.ZERO, // Prod_Disc
					BigDecimal.ZERO, // Octroi_Rate
					"000", // Cust_Type
					BigDecimal.ZERO, // mrp_diff
					BigDecimal.ZERO, // trade discount
					comp_cd, // Comp cd
					BigDecimal.ZERO, // mrp rate
					BigDecimal.ZERO, // SS RATE
					grnDetails.getValue1().subtract(grnDetails.getValue2())); // inward // charges

			// for modify
			detail_sum[0] = grnHeader.getSgst_bill_amt()
					.subtract(grnDetails.getSgst_bill_amt().setScale(2, RoundingMode.HALF_EVEN));
			detail_sum[1] = grnHeader.getCgst_bill_amt()
					.subtract(grnDetails.getCGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
			detail_sum[2] = grnHeader.getIgst_bill_amt()
					.subtract(grnDetails.getIgst_bill_amt().setScale(2, RoundingMode.HALF_EVEN));
			detail_sum[3] = grnHeader.getValue1().subtract(grnDetails.getValue1().setScale(2, RoundingMode.HALF_EVEN)); // customs
			detail_sum[4] = grnHeader.getValue2().subtract(grnDetails.getValue2().setScale(2, RoundingMode.HALF_EVEN));
			

			grnHeader.setSgst_bill_amt(detail_sum[0]);
			grnHeader.setCgst_bill_amt(detail_sum[1]);
			grnHeader.setIgst_bill_amt(detail_sum[2]);
			grnHeader.setValue1(detail_sum[3]);
			grnHeader.setValue2(detail_sum[4]);
			
			
			
			this.transactionalRepository.update(grnDetails);
			

			grnDetails.setSgst_rate(taxBean.getSgst_rate());
			grnDetails.setCgst_rate(taxBean.getCgst_rate());
			grnDetails.setIgst_rate(taxBean.getIgst_rate());
			grnDetails.setSgst_bill_amt(taxBean.getSgst_bill_amount());
			grnDetails.setCGST_BILL_AMT(taxBean.getCgst_bill_amount());
			grnDetails.setIgst_bill_amt(taxBean.getIgst_bill_amount());
			grnDetails.setGst_reverse_chg(gst_reverse_chg);
			grnDetails.setGst_doc_type(gst_doc_type);

			detail_sum[0] = grnHeader.getSgst_bill_amt()
					.add(grnDetails.getSgst_bill_amt().setScale(2, RoundingMode.HALF_EVEN));
			detail_sum[1] = grnHeader.getCgst_bill_amt()
					.add(grnDetails.getCGST_BILL_AMT().setScale(2, RoundingMode.HALF_EVEN));
			detail_sum[2] = grnHeader.getIgst_bill_amt()
					.add(grnDetails.getIgst_bill_amt().setScale(2, RoundingMode.HALF_EVEN));
			detail_sum[3] = grnHeader.getValue1().add(grnDetails.getValue1().setScale(2, RoundingMode.HALF_EVEN)); // customs
			detail_sum[4] = grnHeader.getValue2().add(grnDetails.getValue2().setScale(2, RoundingMode.HALF_EVEN)); // discount
			this.transactionalRepository.update(grnHeader);

			// update gst in grn header
			grnHeader.setSgst_bill_amt(detail_sum[0]);
			grnHeader.setCgst_bill_amt(detail_sum[1]);
			grnHeader.setIgst_bill_amt(detail_sum[2]);
			grnHeader.setValue1(detail_sum[3]);
			grnHeader.setValue2(detail_sum[4]);
			grnHeader.setText1(text1);
			grnHeader.setText2(text2);
			grnHeader.setGst_reverse_chg(gst_reverse_chg);
			grnHeader.setGst_doc_type(gst_doc_type);

			// find round off
			grnHeader.setRoundoff(this.calculateRound_Off(grnHeader.getGrn_total_value(), detail_sum[0], detail_sum[1],
					detail_sum[2], detail_sum[3], detail_sum[4]));

			System.out.println(grnHeader.getGrn_total_value_pur());
			BigDecimal total_pur_value_after_tax = grnHeader.getGrn_total_value_pur().add(detail_sum[0])
					.add(detail_sum[1]).add(detail_sum[2]).add(detail_sum[3]) // customs duty
					.subtract(detail_sum[4]); // discount
			total_pur_value_after_tax = total_pur_value_after_tax.setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal rounded_value_pur = total_pur_value_after_tax.setScale(0, RoundingMode.HALF_EVEN);
			grnHeader.setRoundoff_pr(rounded_value_pur.subtract(total_pur_value_after_tax));

			// grnHeader.setGrn_mod_dt(new Date());
			this.transactionalRepository.update(grnHeader);
			// updating grnHeader outside this method

		}

	}

	private BigDecimal calculateRound_Off(BigDecimal Grn_total_value, BigDecimal Sgst_bill_amt,
			BigDecimal Cgst_bill_amt, BigDecimal Igst_bill_amt, BigDecimal customs_duty, BigDecimal discount) {
		// TODO Auto-generated method stub

		BigDecimal total_value_after_tax = Grn_total_value.add(Sgst_bill_amt)// Sgst_bill_amt
				.add(Cgst_bill_amt)// Cgst_bill_amt
				.add(Igst_bill_amt)// Igst_bill_amt
				.add(customs_duty) // customs_duty
				.subtract(discount); // discount

		total_value_after_tax = total_value_after_tax.setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal rounded_value = total_value_after_tax.setScale(0, RoundingMode.HALF_EVEN);

		return rounded_value.subtract(total_value_after_tax);

	}

	@Override
	public Map<String, Object> getGrnApprovalHeaderDetail(@RequestParam String empId, @RequestParam String empLoc,
			String role, String compcode) {
		Map<String, Object> map;
		map = grnRepository.getGrnApprovalHeaderDetail(empId, empLoc, role, compcode);
		return map;
	}

	@Override
	public Map<String, Object> getGrnApprovalSelectedDetail(@RequestParam long grnId) {
		Map<String, Object> map;
		map = grnRepository.getGrnApprovalSelectedDetail(grnId);
		return map;

	}

	@Override
	public void updateDetailOnSelfDiscardOfGRN(String empId, long grnId, String remark, HttpServletRequest request,
			String compcd) {
		System.out.println("In Discard GRN");
		try {
			List<GrnDetails> list = grnRepository.getGrnDetailsByGrnId(grnId);
			GrnHeader grnHeader = grnRepository.getObjectById(grnId);
			grnHeader.setGrn_appr_acq(0L);
			grnHeader.setGrn_mod_ip_add(grnHeader.getGrn_ins_ip_add());
			grnHeader.setGrn_mod_dt(new Date());
			System.out.println("------------Discard-------------");
			for (GrnDetails grnDetails : list) {
				if (!compcd.trim().equals(PAL) && !compcd.trim().equals(PFZ)) {
					if (grnHeader.getGrn_stock_sa_or_ns().equalsIgnoreCase("SA")) {
						grnRepository.updateBatchQuarantine(grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_qty());
					} else {
						grnRepository.updateBatchNSQuarantine(grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_qty());
					}
				}

				if (compcd.trim().equals(PAL) || compcd.trim().equals(PFZ)) {
					grnDetails.setGrnd_appr_status("E");
				} else {
					grnDetails.setGrnd_appr_status("D");
				}
				this.transactionalRepository.update(grnDetails);
			}

			if (compcd.trim().equals(PAL) || compcd.trim().equals(PFZ)) {
				grnHeader.setGrn_confirm("N");
				grnHeader.setGrn_appr_status("E");
			} else {
				grnHeader.setGrn_status("I");
				grnHeader.setGrn_appr_status("D");
			}

			grnHeader.setGrn_mod_usr_id(empId);
			grnHeader.setGrn_mod_dt(new Date());
			grnHeader.setGrn_mod_ip_add(request.getRemoteAddr());
			this.transactionalRepository.update(grnHeader);

//			    if (isapproved.equalsIgnoreCase("A")) {
//			    	Period period=this.periodMasterRepository.getPeriodMasterByCompany(comp_cd);
//			    	this.grnRepository.IaaCreationInGrnApproval(grnId, grnHeader.getGrn_fin_year(),period.getPeriod_code(), last_approved_by,grnHeader.getGrn_ins_ip_add());
//			    }
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}

	}

	@Override
	public String uploadFile(MultipartFile file) {
		return grnRepository.uploadFile(file);
	}

	@Override
	public Object viewFile(@RequestParam long grnId) throws Exception {
		return grnRepository.viewFile(grnId);

	}

	@Override
	public void updateDetailOnSelfApproveOfGRN(String empId, long grnId, String remark, HttpServletRequest request) {
		// TODO Auto-generated method stub
		// grnRepository.updateDetailOnSelfApproveOfGRN(empId, grnId, remark, request);
	}

	@Override
	public List getGrnDetailForGrnVoucherPrint(String sdate, String edate, String supId, String locId, String finyr,
			String finyrflg) {
		return this.grnRepository.getGrnDetailForGrnVoucherPrint(sdate, edate, supId, locId, finyr, finyrflg);
	}

	@Override
	public void confirmGrn(Long grnId, String companyCode) throws Exception {
		System.out.println("grnId " + grnId);
		this.grnRepository.confirmGrn(grnId, companyCode);
	}

	@Override
	public Long getSupplierForReturnFromField(Long sub_com_id) throws Exception {

		return this.grnRepository.getSupplierForReturnFromField(sub_com_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String DeleteWholeGrn(Long grnId, String empId) throws Exception {
		// TODO Auto-generated method stub
		GrnHeader header = null;
		try {
			header = this.grnRepository.getObjectById(grnId);
			List<GrnDetails> list = this.grnRepository.getGrnDetailsByGrnId(grnId);
			SG_d_parameters_details parameter = this.parameterRepository.getObjectById(header.getGrn_stock_type_id());

			for (GrnDetails grnDetails : list) {

				if (header.getGrn_stock_sa_or_ns().equals(SA)) {
					SmpBatch batch = this.batchMasterRepository.getObjectById(grnDetails.getGrnd_batch_id());
					if (batch != null) {
						if (this.batchMasterRepository.checkIfBatchStockIsNegative(batch.getBatch_id(),
								batch.getBatch_prod_id(), batch.getBatch_loc_id()))
							throw new MedicoException("Stock negative, cannot update");
						if (this.batchMasterRepository.checkIfBatchStockIsNegativeByBatchIdProdIdLocId(
								batch.getBatch_id(), batch.getBatch_prod_id(), batch.getBatch_loc_id()))
							throw new MedicoException("Stock negative, cannot update");
						batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().subtract(grnDetails.getGrnd_qty()));
						batch.setBatch_in_stock(batch.getBatch_in_stock().subtract(grnDetails.getGrnd_qty()));
						batch.setBatch_mod_dt(new Date());
						batch.setBatch_mod_ip_add(null);
						batch.setBatch_mod_usr_id(empId);
						this.transactionalRepository.update(batch);
					} else {
						throw new MedicoException("Batch Details not found.");
					}
					BatchStockLog batchLog = this.batchStockLogRepository.getRecordByProdIdNew(
							grnDetails.getGrnd_prod_id(), grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_loc_id(),
							header.getGrn_dt(), GRN);
					// BatchStockLog batchLog =
					// this.batchStockLogRepository.getRecordByProdIdNew(grnDetails.getGrnd_prod_id(),
					// grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_loc_id());
					if (batchLog != null) {
						batchLog.setBtchstklg_qty(batchLog.getBtchstklg_qty().subtract(grnDetails.getGrnd_qty()));
						batchLog.setBtchstklg_value(batchLog.getBtchstklg_value()
								.subtract((grnDetails.getGrnd_qty().multiply(grnDetails.getGrnd_rate()))));
						batchLog.setBtchstklg_mod_ip_add(null);
						batchLog.setBtchstklg_mod_dt(new Date());
						batchLog.setBtchstklg_mod_usr_id(empId);
						transactionalRepository.update(batchLog);
					} else {
						throw new MedicoException("Batch Stock Log not Found.");
					}
				} else {
					SmpBatchNS batchNS = this.batchMasterRepository.getNSObjectByIdAndStocktype(
							grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_prod_id(), grnDetails.getGrnd_loc_id(),
							parameter.getSgprmdet_nm());
					if (batchNS != null) {
						if (this.batchMasterRepository.checkIfNSBatchStockIsNegative(batchNS.getBatch_id(),
								batchNS.getBatch_prod_id(), batchNS.getBatch_loc_id(), batchNS.getStock_type()))
							throw new MedicoException("Stock negative, cannot update");
						if (this.batchMasterRepository.checkIfNSBatchStockIsNegativeByBatchIdProdIdLocId(
								batchNS.getBatch_id(), batchNS.getBatch_prod_id(), batchNS.getBatch_loc_id(),
								batchNS.getStock_type()))
							throw new MedicoException("Stock negative, cannot update");
						batchNS.setBatch_with_held_qty(
								batchNS.getBatch_with_held_qty().subtract(grnDetails.getGrnd_qty()));
						batchNS.setBatch_in_stock(batchNS.getBatch_in_stock().subtract(grnDetails.getGrnd_qty()));
						batchNS.setBatch_mod_dt(new Date());
						batchNS.setBatch_mod_ip_add(null);
						batchNS.setBatch_mod_usr_id(empId);
						this.transactionalRepository.update(batchNS);
					} else {
						throw new MedicoException("Batch Details not found.");
					}
					BatchStockLogNS batchLogNS = this.batchStockLogRepository.getNSRecordByProdIdNew(
							grnDetails.getGrnd_prod_id(), grnDetails.getGrnd_batch_id(), grnDetails.getGrnd_loc_id(),
							header.getGrn_dt(), parameter.getSgprmdet_nm(), GRN);
					if (batchLogNS != null) {
						batchLogNS.setBtchstklg_qty(batchLogNS.getBtchstklg_qty().subtract(grnDetails.getGrnd_qty()));
						batchLogNS.setBtchstklg_value(batchLogNS.getBtchstklg_value()
								.subtract((grnDetails.getGrnd_qty().multiply(grnDetails.getGrnd_rate()))));
						batchLogNS.setBtchstklg_mod_ip_add(null);
						batchLogNS.setBtchstklg_mod_dt(new Date());
						batchLogNS.setBtchstklg_mod_usr_id(empId);
						transactionalRepository.update(batchLogNS);
					} else {
						throw new MedicoException("Batch Stock Log not Found.");
					}
				}
				grnDetails.setGrnd_status("I");
				grnDetails.setGrnd_appr_status("D");
				transactionalRepository.update(grnDetails);
			}

			header.setGrn_appr_status("D");
			header.setGrn_status("I");
			transactionalRepository.update(header);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return header.getGrn_no();
	}

	@Override
	public void reverseBatchBeforeModify(GrnDetails detail, GrnHeader header) throws Exception {
		// TODO Auto-generated method stub
		BigDecimal returnValueForGrnDetDel = null;
		try {
			String empId = header.getGrn_ins_usr_id();
			SG_d_parameters_details pur_rate_param = this.parameterService
					.getParameterDataByDisplayNameWithJoin("ACCEPT_PUR_RATE_IN_GRN");
			if (detail != null) {
				BigDecimal grnvalue = this.grnRepository.getTotalGrnValueByGrnId(detail.getGrndGrnId(), null);
				grnvalue = grnvalue.subtract(detail.getGrnd_value());

				BigDecimal grn_pur_value = this.grnRepository.getTotal_Pur_GrnValueByGrnId(detail.getGrndGrnId(), null);
				grn_pur_value = grn_pur_value.subtract(detail.getGrnd_value_pur());
				// System.out.println("grnvalue"+grnvalue);
				SG_d_parameters_details parameter = this.parameterRepository
						.getObjectById(header.getGrn_stock_type_id());
				if (header.getGrn_stock_sa_or_ns().equals(SA)) {
					SmpBatch batch = this.batchMasterRepository.getObjectById(detail.getGrnd_batch_id());
					if (batch != null) {
						if (this.batchMasterRepository.checkIfBatchStockIsNegative(batch.getBatch_id(),
								batch.getBatch_prod_id(), batch.getBatch_loc_id()))
							throw new MedicoException("Stock negative, cannot update");
						if (this.batchMasterRepository.checkIfBatchStockIsNegativeByBatchIdProdIdLocId(
								batch.getBatch_id(), batch.getBatch_prod_id(), batch.getBatch_loc_id()))
							throw new MedicoException("Stock negative, cannot update");
						batch.setBatch_with_held_qty(batch.getBatch_with_held_qty().subtract(detail.getGrnd_qty()));
						batch.setBatch_in_stock(batch.getBatch_in_stock().subtract(detail.getGrnd_qty()));
						batch.setBatch_mod_dt(new Date());
						batch.setBatch_mod_ip_add(null);
						batch.setBatch_mod_usr_id(empId);
						this.transactionalRepository.update(batch);
						System.out.println(batch.toString());
					} else {
						throw new MedicoException("Batch Details not found.");
					}
					BatchStockLog batchLog = this.batchStockLogRepository.getRecordByProdIdNew(detail.getGrnd_prod_id(),
							detail.getGrnd_batch_id(), detail.getGrnd_loc_id(), header.getGrn_dt(), GRN);
					// BatchStockLog batchLog =
					// this.batchStockLogRepository.getRecordByProdIdNew(detail.getGrnd_prod_id(),
					// detail.getGrnd_batch_id(), detail.getGrnd_loc_id());
					if (batchLog != null) {
						batchLog.setBtchstklg_qty(batchLog.getBtchstklg_qty().subtract(detail.getGrnd_qty()));
						batchLog.setBtchstklg_value(batchLog.getBtchstklg_value()
								.subtract((detail.getGrnd_qty().multiply(detail.getGrnd_rate()))));
						batchLog.setBtchstklg_mod_ip_add(null);
						batchLog.setBtchstklg_mod_dt(new Date());
						batchLog.setBtchstklg_mod_usr_id(empId);
						transactionalRepository.update(batchLog);
						System.out.println(batchLog.toString());
					} else {
						throw new MedicoException("Batch Stock Log not Found.");
					}
				} else {
					SmpBatchNS batchNS = this.batchMasterRepository.getNSObjectByIdAndStocktype(
							detail.getGrnd_batch_id(), detail.getGrnd_prod_id(), detail.getGrnd_loc_id(),
							parameter.getSgprmdet_nm());
					if (batchNS != null) {
						if (this.batchMasterRepository.checkIfNSBatchStockIsNegative(batchNS.getBatch_id(),
								batchNS.getBatch_prod_id(), batchNS.getBatch_loc_id(), batchNS.getStock_type()))
							throw new MedicoException("Stock negative, cannot update");
						if (this.batchMasterRepository.checkIfNSBatchStockIsNegativeByBatchIdProdIdLocId(
								batchNS.getBatch_id(), batchNS.getBatch_prod_id(), batchNS.getBatch_loc_id(),
								batchNS.getStock_type()))
							throw new MedicoException("Stock negative, cannot update");
						batchNS.setBatch_with_held_qty(batchNS.getBatch_with_held_qty().subtract(detail.getGrnd_qty()));
						batchNS.setBatch_in_stock(batchNS.getBatch_in_stock().subtract(detail.getGrnd_qty()));
						batchNS.setBatch_mod_dt(new Date());
						batchNS.setBatch_mod_ip_add(null);
						batchNS.setBatch_mod_usr_id(empId);
						this.transactionalRepository.update(batchNS);
					} else {
						throw new MedicoException("Batch Details not found.");
					}
					BatchStockLogNS batchLogNS = this.batchStockLogRepository.getNSRecordByProdIdNew(
							detail.getGrnd_prod_id(), detail.getGrnd_batch_id(), detail.getGrnd_loc_id(),
							header.getGrn_dt(), parameter.getSgprmdet_nm(), GRN);
					if (batchLogNS != null) {
						batchLogNS.setBtchstklg_qty(batchLogNS.getBtchstklg_qty().subtract(detail.getGrnd_qty()));
						batchLogNS.setBtchstklg_value(batchLogNS.getBtchstklg_value()
								.subtract((detail.getGrnd_qty().multiply(detail.getGrnd_rate()))));
						batchLogNS.setBtchstklg_mod_ip_add(null);
						batchLogNS.setBtchstklg_mod_dt(new Date());
						batchLogNS.setBtchstklg_mod_usr_id(empId);
						transactionalRepository.update(batchLogNS);
					} else {
						throw new MedicoException("Batch Stock Log not Found.");
					}
				}

				// not making any changes in header no need to update
				// transactionalRepository.update(header);

//				detail.setGrnd_status("I");
//				detail.setGrnd_appr_status("D");
//				transactionalRepository.update(detail);

				// this.grnRepository.deleteById(grnDetailId);
				if (pur_rate_param.getSgprmdet_text1().trim().equals("Y")) {
					returnValueForGrnDetDel = grn_pur_value;
				} else {
					returnValueForGrnDetDel = grnvalue;
				}
			} else {
				throw new MedicoException("GRN Details not found.");
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		}
	}

//	@Override
//	public Map<String, Object> validationForFileUpload(String ip_Address, String user_id, String currentLocation,
//			String CURR_PERIOD, String finyr, String company, MultipartFile file, String auth, String username)
//			throws IOException {
//
//		List<P_ui_AutoGrn> grnDataFromDb = new ArrayList<>();
//		
//		List<Object> grnDataFromDbObjlist = new ArrayList<>();
//		System.out.println("ip_Address::" + ip_Address + " user_id" + user_id + " currentLocation" + currentLocation
//				+ " CURR_PERIOD" + CURR_PERIOD + " finyr" + finyr + " company " + company);
//		Map<String, Object> map = new HashMap<String, Object>();
//		System.out.println(file.getContentType());
//		String authcode=auth.trim();
//
//		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//		XSSFSheet sheet = workbook.getSheetAt(0);
//		
//		Boolean data_already_saved=false;
//
//		try {
//
//			XSSFRow firstRow = sheet.getRow(1);
//			String stk_Transfer_recve_location_form_the_csv = firstRow.getCell(4).getStringCellValue();
//			String csvAuthCode = new String(firstRow.getCell(28).getStringCellValue());
////				String csvAuthCodes = csvAuthCode.substring(0, csvAuthCode.length());
//
//			System.err.println(stk_Transfer_recve_location_form_the_csv);
//			System.err.println("csvAuthCodes :::" + csvAuthCode);
//			System.err.println("auth::::" + auth);
//
//			
//			if (currentLocation.equals(stk_Transfer_recve_location_form_the_csv)) {
//
//				if (csvAuthCode.equals(auth.trim())) {
//
//					List<AUTO_TRANSFER> csptrf_beans = getAuthFromCsv(file, authcode, username, ip_Address);
//					
//					
//						
//					data_already_saved= grnRepository.savedOrNot(authcode);
//					
//					if(!data_already_saved) {
//						grnDataFromDbObjlist = grnRepository.saveBeans(csptrf_beans, ip_Address, user_id, currentLocation,
//								CURR_PERIOD, finyr, company);
//						 if(grnDataFromDbObjlist.size() == 1) {
//								map.put("Result", new StringBuffer("File Upload Succesfully "));
//								map.put("grnDataFromDb", grnDataFromDbObjlist);
//								map.put("Saved", true);	
//								
//							}
//					}
//					else {
//						map.put("Result", new StringBuffer("File Already Saved "));
////						map.put("Saved", false);
////						map.put("stat", true);
//					}
//					
//				
//						
//					
//		
//						
////
////					if (grnDataFromDbObjlist.size() > 1) {
////						
////					System.out.println(	grnDataFromDbObjlist.contains("TRUE")+" :::: ");
////						
////
////						map.put("Result", new StringBuffer("File already Saved "));
////						map.put("Saved", false);
////						map.put("stat", true);
////						grnDataFromDb.forEach(l -> {
////							l.setCsptrf_trf_date(Utility.getRptDateFormat(l.getCsptrf_trf_date().substring(0, 10)));
////							l.setCsptrf_mfgdt(Utility.getRptDateFormat(l.getCsptrf_mfgdt().substring(0, 10)));
////							l.setCsptrf_expiry_dt(Utility.getRptDateFormat(l.getCsptrf_expiry_dt().substring(0, 10)));
////						});
////						
////						
////						map.put("grnDataFromDb", grnDataFromDbObjlist);
////
////					} 
//
//				} else {
//					map.put("Result", new StringBuffer("Invalid Authorization code"));
//				}
//
//			} else {
//				map.put("Result", new StringBuffer("Receving Location is invalid"));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			map.put("Result", new StringBuffer("Error occures  "));
//		}
//
//		finally {
//			workbook.close();
//		}
//		return map;
//
//	}

	/// changess--for erromssg

	@Override
	public Map<String, Object> validationForFileUpload(String ip_Address, String user_id, String currentLocation,
			String CURR_PERIOD, String finyr, String company, MultipartFile file, String auth, String username)
			throws IOException {

		List<P_ui_AutoGrn> grnDataFromDb = new ArrayList<>();

		List<Object> grnDataFromDbObjlist = new ArrayList<>();
		System.out.println("ip_Address::" + ip_Address + " user_id" + user_id + " currentLocation" + currentLocation
				+ " CURR_PERIOD" + CURR_PERIOD + " finyr" + finyr + " company " + company);
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(file.getContentType());
		String authcode = auth.trim();

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);

		Boolean data_already_saved = false;

		try {

			XSSFRow firstRow = sheet.getRow(1);
			String stk_Transfer_recve_location_form_the_csv = firstRow.getCell(4).getStringCellValue();
			String csvAuthCode = new String(firstRow.getCell(28).getStringCellValue());
//				String csvAuthCodes = csvAuthCode.substring(0, csvAuthCode.length());

			System.err.println(stk_Transfer_recve_location_form_the_csv);
			System.err.println("csvAuthCodes :::" + csvAuthCode);
			System.err.println("auth::::" + auth);

			if (currentLocation.equals(stk_Transfer_recve_location_form_the_csv)) {

				if (csvAuthCode.equals(authcode)) {

					List<AUTO_TRANSFER> csptrf_beans = getAuthFromCsv(file, authcode, username, ip_Address);

					map = grnRepository.savedOrNot(authcode);
					data_already_saved = (Boolean) map.get("SAVED");

					if (!data_already_saved) {
						grnDataFromDbObjlist = grnRepository.saveBeans(csptrf_beans, ip_Address, user_id,
								currentLocation, CURR_PERIOD, finyr, company);
						if (grnDataFromDbObjlist.size() == 1) {
							map.put("Result", new StringBuffer("File Uploaded Successfully "));
							map.put("grnDataFromDb", grnDataFromDbObjlist);
							map.put("Saved", true);
						}
					} else {
						map.put("Result", new StringBuffer("File Already Uploaded "));
						map.put("Saved", true);
						map.put("stat", true);
					}

				} else {
					map.put("Result", new StringBuffer("Invalid Authorization Code"));
				}

			} else {
				map.put("Result", new StringBuffer("Receving Location Is Invalid"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			map.put("Result", new StringBuffer("Error occures  "));
		}

		finally {
			workbook.close();
		}
		return map;

	}

	@Override
	public List<AUTO_TRANSFER> getAuthFromCsv(MultipartFile file, String auth, String username, String ip_Address)
			throws IOException, ParseException {

		List<AUTO_TRANSFER> beans = new ArrayList<>();
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow firstRow = sheet.getRow(1);

		try {

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				AUTO_TRANSFER bean = new AUTO_TRANSFER();

				for (int j = 0; j <= row.getLastCellNum() - 1; j++) {

//
//					if (j == 0) {
//
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_id((long) row.getCell(j).getNumericCellValue());
//						} else {
//							bean.setCsptrf_id(0);
//						}
//					}

//					if (j == 1) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_srlno((long) row.getCell(j).getNumericCellValue());
//						} else {
//							bean.setCsptrf_srlno(0);
//						}
//					}

					if (j == 3) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_sending_loc(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_sending_loc("");
						}
					}

//					if (j == 4) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_sendloc_cd(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_sendloc_cd("");
//						}
//					}

//					if (j == 4) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_recv_loc(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_recv_loc("");
//						}
//					}

					if (j == 5) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_recv_loc(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_recv_loc("");
						}
					}

					if (j == 6) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_trf_no(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_trf_no("");
						}
					}

					if (j == 7) {
						if (row.getCell(j) != null) {

							String Csptrf_trf_dateString = row.getCell(j).getStringCellValue();
							Date Csptrf_trf_date = new SimpleDateFormat("dd/MM/yyyy").parse(Csptrf_trf_dateString);
							bean.setCsptrf_trf_date(Csptrf_trf_date);
						} else {
							bean.setCsptrf_trf_date(null);
						}
					}

					if (j == 8) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_prod_cd(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_prod_cd("");
						}
					}

					if (j == 9) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_batch_no(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_batch_no("");
						}
					}

//					if (j == 10) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_giftind(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_giftind("");
//						}
//					}

					if (j == 11) {
						if (row.getCell(j) != null) {

							String Csptrf_mfgdtString = row.getCell(j).getStringCellValue();
							Date Csptrf_mfgdtDate = new SimpleDateFormat("dd/MM/yyyy").parse(Csptrf_mfgdtString);

							bean.setCsptrf_mfgdt(Csptrf_mfgdtDate);
						} else {
							bean.setCsptrf_mfgdt(null);
						}
					}

					if (j == 12) {
						if (row.getCell(j) != null) {

							String Csptrf_expiry_dtString = row.getCell(j).getStringCellValue();
							Date Csptrf_expiry_dt = new SimpleDateFormat("dd/MM/yyyy").parse(Csptrf_expiry_dtString);

							bean.setCsptrf_expiry_dt(Csptrf_expiry_dt);
						} else {
							bean.setCsptrf_expiry_dt(null);
						}
					}

					if (j == 13) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_qty((row.getCell(j).getNumericCellValue() + ""));
						} else {
							bean.setCsptrf_qty("");
						}
					}

					if (j == 14) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_nocases((long) row.getCell(j).getNumericCellValue());
						} else {
							bean.setCsptrf_nocases(0);
						}
					}

					if (j == 15) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_fullshipper((long) row.getCell(j).getNumericCellValue());
						} else {
							bean.setCsptrf_fullshipper(0);
						}
					}

					if (j == 16) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_trfrate(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
						} else {
							bean.setCsptrf_trfrate(BigDecimal.valueOf(0));
						}
					}

					if (j == 17) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_cograte(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
						} else {
							bean.setCsptrf_trfrate(BigDecimal.valueOf(0));
						}
					}

//					if (j == 18) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_ref_no(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_ref_no("");
//						}
//					}

					if (j == 19) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_lrno(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_lrno("");
						}
					}

					if (j == 20) {
						if (row.getCell(j) != null) {

							String Csptrf_lr_dateString = row.getCell(j).getStringCellValue();
							Date Csptrf_lr_date = new SimpleDateFormat("dd/MM/yyyy").parse(Csptrf_lr_dateString);

							bean.setCsptrf_lr_date(Csptrf_lr_date);
						} else {
							bean.setCsptrf_lr_date(null);
						}
					}

					if (j == 21) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_mfg_map_cd(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_mfg_map_cd("");
						}

					}

//					if (j == 22) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_ponumber(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_ponumber("");
//						}
//					}

//					if (j == 23) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_processed(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_processed("");
//						}
//					}

//					if (j == 24) {
//						if (row.getCell(j) != null) {
//							bean.setCsptrf_err_msg(row.getCell(j).getStringCellValue());
//						} else {
//							bean.setCsptrf_err_msg("");
//						}
//					}

					if (j == 25) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_rmpmrate(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
						} else {
							bean.setCsptrf_rmpmrate(BigDecimal.valueOf(0));
						}
					}

					if (j == 26) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_exciserate(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
						} else {
							bean.setCsptrf_exciserate(BigDecimal.valueOf(0));
						}

					}
					if (j == 27) {
						if (row.getCell(j) != null) {
							bean.setCsptrf_overheadrate(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
						} else {
							bean.setCsptrf_overheadrate((BigDecimal.valueOf(0)));
						}
					}

					if (j == 28) {
						if (row.getCell(j) != null) {
							bean.setAuth_code(row.getCell(j).getStringCellValue());
						} else {
							bean.setAuth_code("");
						}
					}

					if (j == 29) {

						if (row.getCell(j) != null) {
							bean.setCsptrf_transporter_name(row.getCell(j).getStringCellValue());
						} else {
							bean.setCsptrf_transporter_name("");
						}
					}

//					
//					bean.setCsptrf_status("E");
//					bean.setCsptrf_ins_ip_add(ip_Address);
//					bean.setCsptrf_ins_usr_id(username);
//					bean.setCsptrf_ins_dt(new Date());

				}
				beans.add(bean);

			}

			beans.forEach(l -> {
				System.err.println(l);
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
			throw e;
		} finally {

			workbook.close();
		}

		System.err.println(beans.size());

		return beans;
	}

	@Override
	public Map<String, Object> search_auth_Code(String authenticationCode) {

		return grnRepository.search_auth_Code(authenticationCode);
	}

	@Override
	public Map<String, Object> callProcudeureForAutoGrn(String ip_Address, String user_id, String currentLocation,
			String cURR_PERIOD, String finyr, String company, String cSPTRF_SLNO, Map<String, Object> map) {
		try {
			return grnRepository.callProcudeureForAutoGrn(ip_Address, user_id, user_id, currentLocation, cURR_PERIOD,
					finyr, company, cSPTRF_SLNO, map);
		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		}

//		 grnRepository.callProcudeureForAutoGrn(ip_Address, user_id , user_id , currentLocation, cURR_PERIOD , finyr , company,cSPTRF_SLNO,map,2);
		return null;
	}

	@Override
	public String getHeaderRemarks(long grnId) throws Exception {

		return this.grnRepository.getHeaderRemarks(grnId);
	}

}
