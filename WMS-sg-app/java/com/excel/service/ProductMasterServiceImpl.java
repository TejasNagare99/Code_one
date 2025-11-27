package com.excel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.excel.bean.Hsn_and_Tax_Bean;
import com.excel.bean.ProductBean;
import com.excel.bean.ProductMasterBean;
import com.excel.bean.ProductwiseTaxMasterBean;
import com.excel.model.SmpItem;
import com.excel.model.Sub_Company;
import com.excel.repository.CompanyMasterRepository;
import com.excel.repository.ErpRepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.Utility;

@Service
public class ProductMasterServiceImpl implements ProductMasterService, MedicoConstants {

	@Autowired
	ProductMasterRepository productMasterRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	CompanyMasterRepository companyMasterRepository;
	@Autowired
	ErpRepository erpRepository;

	
	@Autowired TaxService taxService ;
	@Autowired ProductwiseTaxMasterService productwiseTaxMasterService ;
	
	
	@Override
//	@Cacheable(value="ALLproductType", key="{ '#subCompId','#prodType','#prodGroup','#divId'}")
	
//	@CachePut(value = "ALLproductType", key = "{ '#subCompId','#prodType','#prodGroup','#divId'}")
	public List<SmpItem> getProductListForGRN(String companyCode, Long subCompId, Long prodType, Long prodGroup,
			Long divId) throws Exception {
		return this.productMasterRepository.getProductListForGRN(companyCode, subCompId, prodType, prodGroup, divId);
	}

	@Override

	public List<ProductMasterBean> getProductDetailByProdId(String companyCode, Long locId, Long ProdId, String stkType)
			throws Exception {
		return this.productMasterRepository.getProductDetailByProdId(companyCode, locId, ProdId, stkType);
	}

	@Override
	public List<SmpItem> getProdListByProdIds(List<Long> ProdIds) throws Exception {
		return this.productMasterRepository.getProdListByProdIds(ProdIds);
	}

	@Override
	public SmpItem getObjectById(Long smpProdId) throws Exception {
		return this.productMasterRepository.getObjectById(smpProdId);
	}

	@Override
	public List<ProductBean> getUmo() throws Exception {
		return this.productMasterRepository.getUmo();
	}

	@Override
	public List<ProductMasterBean> getPack() {
		return this.productMasterRepository.getPack();
	}

	@Override
	@CacheEvict(value = "ALLproductType", allEntries = true)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveProduct(@RequestBody ProductBean pb, @RequestParam String empId, HttpSession session,
			HttpServletRequest request) throws Exception {
		String ip_addr = request.getRemoteAddr();
		String compCode = (String) session.getServletContext().getAttribute(COMPANY_CODE);
		SmpItem si = new SmpItem();
		si.setAlloc_multiples(Boolean.parseBoolean(pb.getAllocationMultipleIndicator()) ? "Y" : "N");
		si.setAlter_name(pb.getAlternateName());
		si.setBatch_rq_ind(Boolean.parseBoolean(pb.getBatchRequiredIndicator()) ? "Y" : "N");
		si.setDisplay_rate(pb.getDisplayRate() == null || pb.getDisplayRate().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getDisplayRate()));
		si.setDiv_rq_ind(Boolean.parseBoolean(pb.getDivisionRequiredIndicator()) ? "Y" : "N");
		si.setErp_prod_cd(
				pb.getErpProductCode() == null || pb.getErpProductCode().isEmpty() ? null : pb.getErpProductCode());
		si.setExpiry_rq_ind(Boolean.parseBoolean(pb.getExpiryRequiredIndicator()) ? "Y" : "N");

		si.setForm_id(pb.getForm1() == null ? 0L : Long.valueOf(pb.getForm1()));

		si.setHsn_code(pb.getHsnCode());
		si.setInv_grp_id(0L);
		// si.setInvoice_grp_id();
		si.setSmp_subcomp_id(pb.getSubCompany());
		si.setLaunch_dt(pb.getLaunchDate() == null ? null : pb.getLaunchDate());
		si.setMargin_perc(BigDecimal.ZERO);
		si.setMax_alloc_qty(new BigDecimal(pb.getAllocationQuantityMax()));
		si.setMin_alloc_qty(new BigDecimal(pb.getAllocationQuantityMin()));
		si.setStd_alloc_qty(new BigDecimal(pb.getAllocationQuantityStandard()));
		si.setOld_prod_name(pb.getProductName());
		System.out.println("promo input is :" + Boolean.parseBoolean(pb.getPromoInput()));
		si.setPromo_expiry_accept_ind(pb.getPromoInput() == null ? "" : pb.getPromoInput());
		si.setSampling_type(pb.getSamplingType());
		si.setShipper_qty(pb.getShipperVolume() == null || pb.getShipperVolume().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getShipperVolume()));
		si.setSmp_abc_ind(pb.getAbcIndicator());
		si.setSmp_cog_exe_rate(
				pb.getCostOfGoodsExciseRate() == null || pb.getCostOfGoodsExciseRate().isEmpty() ? BigDecimal.ZERO
						: new BigDecimal(pb.getCostOfGoodsExciseRate()));
		si.setSmp_cog_rate(pb.getCostOfGoodsRate() == null || pb.getCostOfGoodsRate().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getCostOfGoodsRate()));
		si.setSmp_company(compCode);
		si.setSmp_costing_rate(pb.getCostingRate() == null || pb.getCostingRate().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getCostingRate()));
		si.setSmp_discont_dt(pb.getDiscontinuedDate() == null ? null : pb.getDiscontinuedDate());
//		si.setSmp_exclude_loc();
//		si.setSmp_exclude_party();
		si.setSmp_ins_dt(new Date());
		si.setSmp_ins_ip_add(ip_addr);
		si.setSmp_ins_usr_id(empId);
		si.setSmp_mfg_loc_id(pb.getManufacturingLocation() == null || pb.getManufacturingLocation().isEmpty() ? 0
				: Long.valueOf(pb.getManufacturingLocation()));
		si.setSmp_mktg_rate(pb.getMarketingRate() == null || pb.getMarketingRate().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getMarketingRate()));
		si.setSmp_mole_grp_id(pb.getMolecularGroup() == null || pb.getMolecularGroup().isEmpty() ? 0L
				: Long.valueOf(pb.getMolecularGroup()));
		si.setSmp_mole_sgrp_id(pb.getMolecularSubgroup() == null || pb.getMolecularSubgroup().isEmpty() ? 0
				: Long.valueOf(pb.getMolecularSubgroup()));
		si.setSmp_nrv(pb.getNrv() == null || pb.getNrv().isEmpty() ? BigDecimal.ZERO : new BigDecimal(pb.getNrv()));
		si.setSmp_pack_id(Long.valueOf(pb.getPack()));
		si.setSmp_pmt_grp_id(
				pb.getPmtGroup() == null || pb.getPmtGroup().isEmpty() ? 0L : Long.valueOf(pb.getPmtGroup()));
		si.setSmp_pmt_sgrp_id(
				pb.getPmtSubgroup() == null || pb.getPmtSubgroup().isEmpty() ? 0 : Long.valueOf(pb.getPmtSubgroup()));
		si.setSmp_prod_batch_size(
				pb.getProductBatchSize() == null || pb.getProductBatchSize().isEmpty() ? BigDecimal.ZERO
						: new BigDecimal(pb.getProductBatchSize()));
		si.setSmp_prod_cd(pb.getProductCode());
		si.setSmp_prod_name(pb.getProductName() + " - (" + pb.getProductCode() + ")");
		si.setSmp_prod_type(pb.getProductTypeName());
		si.setSmp_prod_type_id(
				pb.getProductType() == null || pb.getProductType().isEmpty() ? 0 : Long.valueOf(pb.getProductType()));
		si.setSmp_qty_box(pb.getQuantityBox() == null || pb.getQuantityBox().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getQuantityBox()));
		si.setSmp_qty_strip(pb.getQuantityStrip() == null || pb.getQuantityStrip().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getQuantityStrip()));
		si.setSmp_rate(new BigDecimal(pb.getRate()));
		si.setSmp_sa_prod_group(Long.valueOf(pb.getBrand()));
		si.setSmp_sch_ind(Boolean.parseBoolean(pb.getScheduleIndicator()) ? "Y" : "N");
		si.setSmp_shelf_life(
				pb.getShelfLife() == null || pb.getShelfLife().isEmpty() ? 0 : Long.valueOf(pb.getShelfLife()));
		si.setSmp_shipper_gwt(pb.getShipperGWT() == null || pb.getShipperGWT().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getShipperGWT()));
		si.setSmp_shipper_nwt(pb.getShipperNWT() == null || pb.getShipperNWT().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getShipperNWT()));
		si.setSmp_shipper_vol(pb.getShipperVolume() == null || pb.getShipperVolume().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getShipperVolume()));
		si.setSmp_short_expiry_days(pb.getShortExpiryDays() == null || pb.getShortExpiryDays().isEmpty() ? 0L
				: Long.valueOf(pb.getShortExpiryDays()));
		si.setSmp_spec_div_ind(Boolean.parseBoolean(pb.getSpecificDivisionIndicator()) ? "Y" : "N");
		si.setSmp_std_div_id(pb.getStandardDivision() == null ? 0L : Long.valueOf(pb.getStandardDivision()));
		si.setSmp_thp_grp_id(pb.getTherapeuticGroup() == null || pb.getTherapeuticGroup().isEmpty() ? 0
				: Long.valueOf(pb.getTherapeuticGroup()));
		si.setSmp_thp_sgrp_id(pb.getTherapeuticSubgroup() == null || pb.getTherapeuticSubgroup().isEmpty() ? 0
				: Long.valueOf(pb.getTherapeuticSubgroup()));
		si.setUom_id(pb.getUmo().equals("") ? 0 : Long.valueOf(pb.getUmo()));
		si.setSmp_stock_days(
				pb.getStockDays() == null || pb.getStockDays().isEmpty() ? 0L : Long.valueOf(pb.getStockDays()));
		si.setStorage_type_id(pb.getStorageTypeId() == null || pb.getStorageTypeId().isEmpty() ? 0
				: Long.valueOf(pb.getStorageTypeId()));
		si.setStorage_type(pb.getStorageTypeName() == null ? "" : pb.getStorageTypeName());
		si.setSmp_supply_type_id(Integer.parseInt(pb.getSupplierType()));
		si.setProd_sub_type_id(Integer.parseInt(pb.getProductSubType()));
		si.setMargin_perc(pb.getMarginPerc() == null || pb.getMarginPerc().isEmpty() ? BigDecimal.ZERO
				: new BigDecimal(pb.getMarginPerc()));

		si.setSmp_status("A");
		si.setSmp_exclude_loc("N");
		si.setSmp_exclude_party("N");
		si.setSmp_mod_usr_id("");
		si.setSmp_mod_ip_add("");
		// si.setSmp_mod_dt();

		// GCMA Detail(19-5-2020)
		System.out.println(
				"GCMA Detail:" + pb.getGcmaRequire() + ".." + pb.getGcmaNumber() + ".." + pb.getGcmaApproveDate());
		si.setGcma_req_ind(pb.getGcmaRequire());
		si.setGcma_number(pb.getGcmaNumber());
		si.setGcma_aproval_dt(pb.getGcmaApproveDate());
		si.setGcma_expiry_dt(pb.getGcmaExpiryDate());
		si.setGcma_not_req_reason(
				pb.getReasonId() == null || pb.getReasonId().isEmpty() ? null : Long.valueOf(pb.getReasonId()));
		this.transactionalRepository.persist(si);

	}

	@Override
	public List<Object> getProductDetailForProductwiseTaxMaster(String divId, String prodTypeId, String subCompId,
			String hsnCode) {
		return this.productMasterRepository.getProductDetailForProductwiseTaxMaster(divId, prodTypeId, subCompId,
				hsnCode);
	}

	@Override
	public List<Object> getTaxDetailForProductwiseTaxMaster(String prodCode, String compCode, String hsnCode) {
		return this.productMasterRepository.getTaxDetailForProductwiseTaxMaster(prodCode, compCode, hsnCode);
	}

	@Override
	public List<Object> getHSNCode(String prodCode, String Company) {
		return this.productMasterRepository.getHSNCode(prodCode, Company);
	}

	@Override
	public List<Object> getAllActiveProductDetailForProductModify() {
		return this.productMasterRepository.getAllActiveProductDetailForProductModify();
	}

	@Override
	public List<Object> getProductDetailByTextParameterForProductModify(@RequestParam String name,
			@RequestParam String parameter, @RequestParam String text) {
		return this.productMasterRepository.getProductDetailByTextParameterForProductModify(name, parameter, text);
	}

	@Override
	public List<SmpItem> getProductDetailById(String id) {
		return this.productMasterRepository.getProductDetailById(id);
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void updateProduct(String ProdId,ProductBean pb,String empId,HttpSession session,HttpServletRequest request) throws Exception {
//		String ip_addr=request.getRemoteAddr();
//		System.out.println("ip add and empid  "+ip_addr+"..."+empId);
//		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
//		SmpItem si;
//		si=this.productMasterRepository.getObjectById(Long.valueOf(ProdId));
//		
//		si.setSmp_mod_dt(new Date());
//		si.setSmp_mod_ip_add(ip_addr);
//		si.setSmp_mod_usr_id(empId);
//		
//		si.setAlloc_multiples(Boolean.parseBoolean(pb.getAllocationMultipleIndicator()) ? "Y" : "N");
//		si.setAlter_name(pb.getAlternateName());
//		si.setBatch_rq_ind(Boolean.parseBoolean(pb.getBatchRequiredIndicator()) ? "Y" : "N");
//		si.setDisplay_rate(pb.getDisplayRate()==null||pb.getDisplayRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getDisplayRate()));
//		si.setDiv_rq_ind(Boolean.parseBoolean(pb.getDivisionRequiredIndicator()) ? "Y" : "N");
//		si.setErp_prod_cd(pb.getErpProductCode()==null||pb.getErpProductCode().isEmpty()?null:pb.getErpProductCode());
//		si.setExpiry_rq_ind(Boolean.parseBoolean(pb.getExpiryRequiredIndicator())? "Y" : "N");
//	
//		si.setForm_id(pb.getForm1()==null?0L:Long.valueOf(pb.getForm1()));
//		
//		si.setHsn_code(pb.getHsnCode());
//		si.setInv_grp_id(0L);
//		//si.setInvoice_grp_id();
//		si.setSmp_subcomp_id(pb.getSubCompany());
//		si.setLaunch_dt(pb.getLaunchDate()==null?null:pb.getLaunchDate());
//		si.setMargin_perc(BigDecimal.ZERO);
//		si.setMax_alloc_qty(new BigDecimal(pb.getAllocationQuantityMax()));
//		si.setMin_alloc_qty(new BigDecimal(pb.getAllocationQuantityMin()));
//		si.setStd_alloc_qty(new BigDecimal(pb.getAllocationQuantityStandard()));
//		si.setOld_prod_name(pb.getProductName());
//		System.out.println("promo input is :"+Boolean.parseBoolean(pb.getPromoInput()));
//		si.setPromo_expiry_accept_ind(pb.getPromoInput()==null?"":pb.getPromoInput());
//		si.setSampling_type(pb.getSamplingType());
//		
//		si.setSmp_abc_ind(pb.getAbcIndicator());
//		si.setSmp_cog_exe_rate(pb.getCostOfGoodsExciseRate()==null || pb.getCostOfGoodsExciseRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getCostOfGoodsExciseRate()));
//		si.setSmp_cog_rate(pb.getCostOfGoodsRate()==null || pb.getCostOfGoodsRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getCostOfGoodsRate()));
//		si.setSmp_company(compCode);
//		si.setSmp_costing_rate(pb.getCostingRate()==null || pb.getCostingRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getCostingRate()));
//		si.setSmp_discont_dt(pb.getDiscontinuedDate()==null?null:pb.getDiscontinuedDate());
////		si.setSmp_exclude_loc();
////		si.setSmp_exclude_party();
//		
//		//si.setSmp_ins_dt(new Date());
//		//si.setSmp_ins_usr_id(empId);
//		//si.setSmp_ins_ip_add(ip_addr);
//		
//		
//		si.setSmp_mfg_loc_id(pb.getManufacturingLocation()==null || pb.getManufacturingLocation().isEmpty()?0:Long.valueOf(pb.getManufacturingLocation()));
//		si.setSmp_mktg_rate(pb.getMarketingRate()==null || pb.getMarketingRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getMarketingRate()));
//		si.setSmp_mole_grp_id(pb.getMolecularGroup() == null || pb.getMolecularGroup().isEmpty()?0L:Long.valueOf(pb.getMolecularGroup()));
//			si.setSmp_mole_sgrp_id(pb.getMolecularSubgroup()==null || pb.getMolecularSubgroup().isEmpty()?0:Long.valueOf(pb.getMolecularSubgroup()));
//		si.setSmp_nrv(pb.getNrv()==null || pb.getNrv().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getNrv()));
//		si.setSmp_pack_id(Long.valueOf(pb.getPack()));
//		si.setSmp_pmt_grp_id(pb.getPmtGroup() == null || pb.getPmtGroup().isEmpty()?0L:Long.valueOf(pb.getPmtGroup()));
//			si.setSmp_pmt_sgrp_id(pb.getPmtSubgroup()==null || pb.getPmtSubgroup().isEmpty() ?0:Long.valueOf(pb.getPmtSubgroup()));
//		si.setSmp_prod_batch_size(pb.getProductBatchSize()==null || pb.getProductBatchSize().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getProductBatchSize()));
//		si.setSmp_prod_cd(pb.getProductCode());
//		
//		
//		System.out.println("old"+si.getSmp_prod_name().trim());
//		System.out.println("new"+pb.getProductName().trim());
//		if(!si.getSmp_prod_name().trim().equals(pb.getProductName().trim())) {
//			String prodName=pb.getProductName()+" - ("+pb.getProductCode()+")";
//			System.out.println(prodName);
//			si.setSmp_prod_name(prodName);
//		}
//		
//		si.setSmp_prod_type(pb.getProductTypeName());
//		si.setSmp_prod_type_id(pb.getProductType()==null || pb.getProductType().isEmpty()?0:Long.valueOf(pb.getProductType()));
//		si.setSmp_qty_box(pb.getQuantityBox()==null || pb.getQuantityBox().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getQuantityBox()));
//		si.setSmp_qty_strip(pb.getQuantityStrip()==null || pb.getQuantityStrip().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getQuantityStrip()));
//		si.setSmp_rate(new BigDecimal(pb.getRate()));
//		si.setSmp_sa_prod_group(Long.valueOf(pb.getBrand()));
//		si.setSmp_sch_ind(Boolean.parseBoolean(pb.getScheduleIndicator())? "Y" : "N");
//		si.setSmp_shelf_life(pb.getShelfLife()==null || pb.getShelfLife().isEmpty()?0:Long.valueOf(pb.getShelfLife()));
//		si.setSmp_shipper_gwt(pb.getShipperGWT()==null || pb.getShipperGWT().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getShipperGWT()));
//		si.setSmp_shipper_nwt(pb.getShipperNWT()==null || pb.getShipperNWT().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getShipperNWT()));
//		//changed on 3 jan 24 4:04 pm
//		//si.setShipper_qty(pb.getShipperVolume()==null || pb.getShipperVolume().isEmpty() ?BigDecimal.ZERO:new BigDecimal(pb.getShipperVolume()));
//		si.setShipper_qty(pb.getQuantityShipper()==null || pb.getQuantityShipper().isEmpty() ?BigDecimal.ZERO:new BigDecimal(pb.getQuantityShipper()));	
//		si.setSmp_shipper_vol(pb.getShipperVolume()==null || pb.getShipperVolume().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getShipperVolume()));
//		si.setSmp_short_expiry_days(pb.getShortExpiryDays()==null || pb.getShortExpiryDays().isEmpty()?0L:Long.valueOf(pb.getShortExpiryDays()));
//		si.setSmp_spec_div_ind(Boolean.parseBoolean(pb.getSpecificDivisionIndicator())? "Y" : "N");
//		si.setSmp_std_div_id(pb.getStandardDivision()==null?0L:Long.valueOf(pb.getStandardDivision()));
//		si.setSmp_thp_grp_id(pb.getTherapeuticGroup()==null || pb.getTherapeuticGroup().isEmpty() ? 0:Long.valueOf(pb.getTherapeuticGroup()));
//			si.setSmp_thp_sgrp_id(pb.getTherapeuticSubgroup()==null || pb.getTherapeuticSubgroup().isEmpty()?0:Long.valueOf(pb.getTherapeuticSubgroup()));
//		si.setUom_id(pb.getUmo().equals("")?0:Long.valueOf(pb.getUmo()));
//		si.setSmp_stock_days(pb.getStockDays()==null || pb.getStockDays().isEmpty()?0L:Long.valueOf(pb.getStockDays()));
//		si.setStorage_type_id(pb.getStorageTypeId()==null || pb.getStorageTypeId().isEmpty()?0:Long.valueOf(pb.getStorageTypeId()));
//		si.setStorage_type(pb.getStorageTypeName()==null?"":pb.getStorageTypeName());
//		si.setSmp_supply_type_id(Integer.parseInt(pb.getSupplierType()));
//		si.setProd_sub_type_id(Integer.parseInt(pb.getProductSubType()));
//		si.setMargin_perc(pb.getMarginPerc()==null||pb.getMarginPerc().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getMarginPerc()));
//		
//		si.setSmp_status("A");
//		si.setSmp_exclude_loc("N");
//		si.setSmp_exclude_party("N");
//		
//		//GCMA Detail(19-5-2020)
//		System.out.println("GCMA Detail:"+pb.getGcmaRequire()+".."+pb.getGcmaNumber()+".."+pb.getGcmaApproveDate());
//		si.setGcma_req_ind(pb.getGcmaRequire());
//		si.setGcma_number(pb.getGcmaNumber());
//		si.setGcma_aproval_dt(pb.getGcmaApproveDate());
//		si.setGcma_expiry_dt(pb.getGcmaExpiryDate());
//		si.setGcma_not_req_reason(pb.getReasonId()==null || pb.getReasonId().isEmpty()?null:Long.valueOf(pb.getReasonId()));
//		
//		this.transactionalRepository.update(si);
//		this.erpRepository.deleteErpProduct(si.getSmp_prod_cd());
//		
//	}
	
	

	@Override
	@CacheEvict(value = "ALLproductType", allEntries = true)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateProduct(String ProdId,ProductBean pb,String empId,HttpSession session,HttpServletRequest request) throws Exception {
		String ip_addr=request.getRemoteAddr();
		System.out.println("ip add and empid  "+ip_addr+"..."+empId);
		String compCode=(String) session.getServletContext().getAttribute(COMPANY_CODE);
		SmpItem si;
		si=this.productMasterRepository.getObjectById(Long.valueOf(ProdId));
		si.setSmp_mod_dt(new Date());
		si.setSmp_mod_ip_add(ip_addr);
		si.setSmp_mod_usr_id(empId);
		
		si.setAlloc_multiples(Boolean.parseBoolean(pb.getAllocationMultipleIndicator()) ? "Y" : "N");
		si.setAlter_name(pb.getAlternateName());
		si.setBatch_rq_ind(Boolean.parseBoolean(pb.getBatchRequiredIndicator()) ? "Y" : "N");
		si.setDisplay_rate(pb.getDisplayRate()==null||pb.getDisplayRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getDisplayRate()));
		si.setDiv_rq_ind(Boolean.parseBoolean(pb.getDivisionRequiredIndicator()) ? "Y" : "N");
		si.setErp_prod_cd(pb.getErpProductCode()==null||pb.getErpProductCode().isEmpty()?null:pb.getErpProductCode());
		si.setExpiry_rq_ind(Boolean.parseBoolean(pb.getExpiryRequiredIndicator())? "Y" : "N");
	
		si.setForm_id(pb.getForm1()==null?0L:Long.valueOf(pb.getForm1()));
		
		si.setHsn_code(pb.getHsnCode());
		si.setInv_grp_id(0L);
		//si.setInvoice_grp_id();
		si.setSmp_subcomp_id(pb.getSubCompany());
		si.setLaunch_dt(pb.getLaunchDate()==null?null:pb.getLaunchDate());
		si.setMargin_perc(BigDecimal.ZERO);
		si.setMax_alloc_qty(new BigDecimal(pb.getAllocationQuantityMax()));
		si.setMin_alloc_qty(new BigDecimal(pb.getAllocationQuantityMin()));
		si.setStd_alloc_qty(new BigDecimal(pb.getAllocationQuantityStandard()));
		si.setOld_prod_name(pb.getProductName());
		System.out.println("promo input is :"+Boolean.parseBoolean(pb.getPromoInput()));
		si.setPromo_expiry_accept_ind(pb.getPromoInput()==null?"":pb.getPromoInput());
		si.setSampling_type(pb.getSamplingType());
		
		si.setSmp_abc_ind(pb.getAbcIndicator());
		si.setSmp_cog_exe_rate(pb.getCostOfGoodsExciseRate()==null || pb.getCostOfGoodsExciseRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getCostOfGoodsExciseRate()));
		si.setSmp_cog_rate(pb.getCostOfGoodsRate()==null || pb.getCostOfGoodsRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getCostOfGoodsRate()));
		si.setSmp_company(compCode);
		si.setSmp_costing_rate(pb.getCostingRate()==null || pb.getCostingRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getCostingRate()));
		si.setSmp_discont_dt(pb.getDiscontinuedDate()==null?null:pb.getDiscontinuedDate());
//		si.setSmp_exclude_loc();
//		si.setSmp_exclude_party();
		
		//si.setSmp_ins_dt(new Date());
		//si.setSmp_ins_usr_id(empId);
		//si.setSmp_ins_ip_add(ip_addr);
		
		
		si.setSmp_mfg_loc_id(pb.getManufacturingLocation()==null || pb.getManufacturingLocation().isEmpty()?0:Long.valueOf(pb.getManufacturingLocation()));
		si.setSmp_mktg_rate(pb.getMarketingRate()==null || pb.getMarketingRate().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getMarketingRate()));
		si.setSmp_mole_grp_id(pb.getMolecularGroup() == null || pb.getMolecularGroup().isEmpty()?0L:Long.valueOf(pb.getMolecularGroup()));
			si.setSmp_mole_sgrp_id(pb.getMolecularSubgroup()==null || pb.getMolecularSubgroup().isEmpty()?0:Long.valueOf(pb.getMolecularSubgroup()));
		si.setSmp_nrv(pb.getNrv()==null || pb.getNrv().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getNrv()));
		si.setSmp_pack_id(Long.valueOf(pb.getPack()));
		si.setSmp_pmt_grp_id(pb.getPmtGroup() == null || pb.getPmtGroup().isEmpty()?0L:Long.valueOf(pb.getPmtGroup()));
			si.setSmp_pmt_sgrp_id(pb.getPmtSubgroup()==null || pb.getPmtSubgroup().isEmpty() ?0:Long.valueOf(pb.getPmtSubgroup()));
		si.setSmp_prod_batch_size(pb.getProductBatchSize()==null || pb.getProductBatchSize().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getProductBatchSize()));
		si.setSmp_prod_cd(pb.getProductCode());
		
		
		System.out.println("old"+si.getSmp_prod_name().trim());
		System.out.println("new"+pb.getProductName().trim());
		if(!si.getSmp_prod_name().trim().equals(pb.getProductName().trim())) {
			String prodName=pb.getProductName()+" - ("+pb.getProductCode()+")";
			System.out.println(prodName);
			si.setSmp_prod_name(prodName);
		}
		
		si.setSmp_prod_type(pb.getProductTypeName());
		si.setSmp_prod_type_id(pb.getProductType()==null || pb.getProductType().isEmpty()?0:Long.valueOf(pb.getProductType()));
		si.setSmp_qty_box(pb.getQuantityBox()==null || pb.getQuantityBox().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getQuantityBox()));
		si.setSmp_qty_strip(pb.getQuantityStrip()==null || pb.getQuantityStrip().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getQuantityStrip()));
		si.setSmp_rate(new BigDecimal(pb.getRate()));
		si.setSmp_sa_prod_group(Long.valueOf(pb.getBrand()));
		si.setSmp_sch_ind(Boolean.parseBoolean(pb.getScheduleIndicator())? "Y" : "N");
		si.setSmp_shelf_life(pb.getShelfLife()==null || pb.getShelfLife().isEmpty()?0:Long.valueOf(pb.getShelfLife()));
		si.setSmp_shipper_gwt(pb.getShipperGWT()==null || pb.getShipperGWT().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getShipperGWT()));
		si.setSmp_shipper_nwt(pb.getShipperNWT()==null || pb.getShipperNWT().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getShipperNWT()));
		//changed on 3 jan 24 4:04 pm
		//si.setShipper_qty(pb.getShipperVolume()==null || pb.getShipperVolume().isEmpty() ?BigDecimal.ZERO:new BigDecimal(pb.getShipperVolume()));
		si.setShipper_qty(pb.getQuantityShipper()==null || pb.getQuantityShipper().isEmpty() ?BigDecimal.ZERO:new BigDecimal(pb.getQuantityShipper()));	
		si.setSmp_shipper_vol(pb.getShipperVolume()==null || pb.getShipperVolume().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getShipperVolume()));
		si.setSmp_short_expiry_days(pb.getShortExpiryDays()==null || pb.getShortExpiryDays().isEmpty()?0L:Long.valueOf(pb.getShortExpiryDays()));
		si.setSmp_spec_div_ind(Boolean.parseBoolean(pb.getSpecificDivisionIndicator())? "Y" : "N");
		si.setSmp_std_div_id(pb.getStandardDivision()==null?0L:Long.valueOf(pb.getStandardDivision()));
		si.setSmp_thp_grp_id(pb.getTherapeuticGroup()==null || pb.getTherapeuticGroup().isEmpty() ? 0:Long.valueOf(pb.getTherapeuticGroup()));
			si.setSmp_thp_sgrp_id(pb.getTherapeuticSubgroup()==null || pb.getTherapeuticSubgroup().isEmpty()?0:Long.valueOf(pb.getTherapeuticSubgroup()));
		si.setUom_id(pb.getUmo().equals("")?0:Long.valueOf(pb.getUmo()));
		si.setSmp_stock_days(pb.getStockDays()==null || pb.getStockDays().isEmpty()?0L:Long.valueOf(pb.getStockDays()));
		si.setStorage_type_id(pb.getStorageTypeId()==null || pb.getStorageTypeId().isEmpty()?0:Long.valueOf(pb.getStorageTypeId()));
		si.setStorage_type(pb.getStorageTypeName()==null?"":pb.getStorageTypeName());
		si.setSmp_supply_type_id(Integer.parseInt(pb.getSupplierType()));
		si.setProd_sub_type_id(Integer.parseInt(pb.getProductSubType()));
		si.setMargin_perc(pb.getMarginPerc()==null||pb.getMarginPerc().isEmpty()?BigDecimal.ZERO:new BigDecimal(pb.getMarginPerc()));
		
		si.setSmp_status("A");
		si.setSmp_exclude_loc("N");
		si.setSmp_exclude_party("N");
		
		//GCMA Detail(19-5-2020)
		System.out.println("GCMA Detail:"+pb.getGcmaRequire()+".."+pb.getGcmaNumber()+".."+pb.getGcmaApproveDate());
		si.setGcma_req_ind(pb.getGcmaRequire());
		si.setGcma_number(pb.getGcmaNumber());
		si.setGcma_aproval_dt(pb.getGcmaApproveDate());
		si.setGcma_expiry_dt(pb.getGcmaExpiryDate());
		si.setGcma_not_req_reason(pb.getReasonId()==null || pb.getReasonId().isEmpty()?null:Long.valueOf(pb.getReasonId()));
		
		this.transactionalRepository.update(si);
		this.erpRepository.deleteErpProduct(si.getSmp_prod_cd());
		
		
		
		this.updateTax_id_And_State_Id(pb.getProductCode(),pb.getHsnCode(),session,empId,compCode);
		
		
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void updateTax_id_And_State_Id(String product_code, String hsnCode, HttpSession session, String empId, String compCode) throws Exception {
		// get the product wise data for the hsncode
		List<Object> tax_code_list=new ArrayList<>();
		List<Object> state_id_list=new ArrayList<>();
	  //this.productMasterRepository.deleteproductwise_tax_master_by_procode(product_code);
		Map<String, List<Object>> map_of_list =taxService.getAll_States_And_Tax_code(hsnCode);
		state_id_list= map_of_list.get("state_list");
		tax_code_list= map_of_list.get("tax_list");
		
		
		ProductwiseTaxMasterBean bean=new ProductwiseTaxMasterBean ();
		bean.setCompany(compCode);
		bean.setEmpId(empId);
		bean.setProdCode(product_code);
		productwiseTaxMasterService.saveProduct(bean, session, state_id_list, tax_code_list);	
		
	}


	@Override
	public List<Object> getSampleProductList(Long divId, String compcd) throws Exception {
		// TODO Auto-generated method stub
		return productMasterRepository.getSampleProductList(divId, compcd);
	}

	@Override
	public List<SmpItem> getProdListByDivPrdType(String comp_cd, Long prod_type, Long div_id) throws Exception {
		// TODO Auto-generated method stub
		return productMasterRepository.getProdListByDivPrdType(comp_cd, prod_type, div_id);
	}

	// Itemwise Stock Ledger(28-4-2020)
	@Override
	public List<SmpItem> getAllActiveItemList() {
		return this.productMasterRepository.getAllActiveItemList();
	}

	@Override
	public List<SmpItem> getItemListByDivId(String divId) {
		return this.productMasterRepository.getItemListByDivId(divId);
	}

	@Override
	public List<SmpItem> getItemByCode(String prodCode) {
		return this.productMasterRepository.getItemByCode(prodCode);
	}

	@Override
	public String genereateProductCode(String subCompId, String smpId) throws Exception {
		System.out.println("smpId " + smpId);
		if (!smpId.equals("0")) {
			SmpItem item = this.productMasterRepository.getObjectById(Long.valueOf(smpId));
			if (item.getSmp_prod_type().equals("Promotional Input")) {
				return item.getSmp_prod_cd();
			}
		}
		Sub_Company company = this.companyMasterRepository.getSubCompanyObjectById(Long.valueOf(subCompId));
		String code = company.getSubcomp_code();
		String date = Utility.convertDatetoStringYYYY_MM_DD(new Date());
		date = date.replaceAll("/", "");

		return this.productMasterRepository.genereateProductCode(code, date);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteLockProduct(String user_id) throws Exception {
		// TODO Auto-generated method stub
		this.productMasterRepository.deleteLockProduct(user_id);
	}

	@Override
	public List<SmpItem> checkUniqueErpProductCode(String erpprodCode, String subCompany) {
		// TODO Auto-generated method stub
		return productMasterRepository.checkUniqueErpProductCode(erpprodCode, subCompany);
	}

	@Override
	public List<SmpItem> checkUniqueProductCode(String prodCode, String subCompany) {
		return this.productMasterRepository.checkUniqueProductCode(prodCode, subCompany);
	}

	@Override
	public List<Object> getSampleProductListEmergency(Long divId, String compcode) throws Exception {
		// TODO Auto-generated method stub
		return this.productMasterRepository.getSampleProductListEmergency(divId, compcode);
	}

	@Override
	public SmpItem getProductIdBySmpProdCode(String smp_prod_code) throws Exception {
		return this.productMasterRepository.getProductIdBySmpProdCode(smp_prod_code);
	}

	@Override
	public List<Hsn_and_Tax_Bean> getAllHsnCode() {
	
		
		return	this.productMasterRepository.getAllHsnCode();
	}

	@Override
//	@Cacheable(value="ALLproductType", key="{ '#subCompId','#prodType','#prodGroup'}")	
//	@CachePut(value = "ALLproductType", key = "{ '#subCompId','#prodType','#prodGroup'}")
	public Object getProductListForGRN_for_medley(String companyCode, Long subCompId, Long prodType, Long prodGroup,
			Long divId) throws Exception {
		// TODO Auto-generated method stub
		return this.productMasterRepository.getProductListForGRN(companyCode, subCompId, prodType, prodGroup, divId);
	}
}
