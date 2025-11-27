package com.excel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.TaxBean;
import com.excel.model.TaxMaster;
import com.excel.repository.TaxRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;


@Service
public class TaxServiceImpl implements TaxService{
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired TaxRepository taxRepository;
	
//	@Override
//	public void saveTax(TaxBean tb) {
//		String inTaxParam = null;
//		String outTaxParam = null;
//		try {
//			System.out.println("generated IN value is :");
//			System.out.println(tb.getTaxTypeIn()+
//			tb.getTaxCalculationMethodIn()+
//			tb.getTaxOnFreeGoodsIn()+
//			tb.getConsiderDiscountInTaxCalculationIn()+
//			tb.getAddOctroiToTaxableAmountsIn()+
//			tb.getRecomputeRateToIncludeAllTaxesIn()+
//			tb.getAddInputVatToSaleRateIn()+
//			tb.getCalculateExciseIn()+
//			tb.getCalculateIcChargesIn()+
//			tb.getCalculateIcChargesForInterstateBillingIn()+
//			tb.getCalculateIncidentalExpensesIn()+
//			tb.getAllowProductDiscountIn()+
//			tb.getAllowCashDiscountIn()+
//			tb.getCalculateSurchargeInLieuOfCstIn()+
//			tb.getCalculateTurnoverTaxIn()+
//			tb.getCalculateTurnoverTaxForInterstateBillingIn());
//			
//			if(tb.getTaxTypeIn()!=null) {
//			inTaxParam=""+tb.getTaxTypeIn()+
//					tb.getTaxCalculationMethodIn()+
//					tb.getTaxOnFreeGoodsIn()+
//					tb.getConsiderDiscountInTaxCalculationIn()+
//					tb.getAddOctroiToTaxableAmountsIn()+
//					tb.getRecomputeRateToIncludeAllTaxesIn()+
//					tb.getAddInputVatToSaleRateIn()+
//					tb.getCalculateExciseIn()+
//					tb.getCalculateIcChargesIn()+
//					tb.getCalculateIcChargesForInterstateBillingIn()+
//					tb.getCalculateIncidentalExpensesIn()+
//					tb.getAllowProductDiscountIn()+
//					tb.getAllowCashDiscountIn()+
//					tb.getCalculateSurchargeInLieuOfCstIn()+
//					tb.getCalculateTurnoverTaxIn()+
//					tb.getCalculateTurnoverTaxForInterstateBillingIn()+"";
//			}
//			
//			System.out.println("generated OUT value is :");
//			System.out.println(tb.getTaxTypeOut()+
//			tb.getTaxCalculationMethodOut()+
//			tb.getTaxOnFreeGoodsOut()+
//			tb.getConsiderDiscountInTaxCalculationOut()+
//			tb.getAddOctroiToTaxableAmountsOut()+
//			tb.getRecomputeRateToIncludeAllTaxesOut()+
//			tb.getAddInputVatToSaleRateOut()+
//			tb.getCalculateExciseOut()+
//			tb.getCalculateIcChargesOut()+
//			tb.getCalculateIcChargesForInterstateBillingOut()+
//			tb.getCalculateIncidentalExpensesOut()+
//			tb.getAllowProductDiscountOut()+
//			tb.getAllowCashDiscountOut()+
//			tb.getCalculateSurchargeInLieuOfCstOut()+
//			tb.getCalculateTurnoverTaxOut()+
//			tb.getCalculateTurnoverTaxForInterstateBillingOut());
//			
//			if(tb.getTaxTypeOut()!=null) {
//			outTaxParam=""+tb.getTaxTypeOut()+
//					tb.getTaxCalculationMethodOut()+
//					tb.getTaxOnFreeGoodsOut()+
//					tb.getConsiderDiscountInTaxCalculationOut()+
//					tb.getAddOctroiToTaxableAmountsOut()+
//					tb.getRecomputeRateToIncludeAllTaxesOut()+
//					tb.getAddInputVatToSaleRateOut()+
//					tb.getCalculateExciseOut()+
//					tb.getCalculateIcChargesOut()+
//					tb.getCalculateIcChargesForInterstateBillingOut()+
//					tb.getCalculateIncidentalExpensesOut()+
//					tb.getAllowProductDiscountOut()+
//					tb.getAllowCashDiscountOut()+
//					tb.getCalculateSurchargeInLieuOfCstOut()+
//					tb.getCalculateTurnoverTaxOut()+
//					tb.getCalculateTurnoverTaxForInterstateBillingOut()+"";
//			}
//			
//			TaxMaster tm=new TaxMaster();
//			tm.setInput_tax_param(inTaxParam);
//			tm.setOutput_tax_param(outTaxParam);
//			
//			//System.out.println("...."+Long.parseLong(tb.getCgst()));
//			tm.setCgst(new BigDecimal(tb.getCgst()));
//			tm.setIgst(new BigDecimal(tb.getIgst()));
//			tm.setSgst(new BigDecimal(tb.getSgst()));
//			
//			tm.setAdd_surch(BigDecimal.ZERO);
//			tm.setAdd_tax(tb.getAdditionalTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getAdditionalTax()));
//			tm.setCess(tb.getCess()==null?BigDecimal.ZERO:new BigDecimal(tb.getCess()));
//			
//			tm.setCompany_cd(tb.getCompany());
//			tm.setHsn_code(tb.getHsnCode());
//			tm.setEffect_dt(tb.getEffectiveFrom());
//			tm.setLast_mod_by(tb.getEmpId());
//			tm.setLast_mod_date(new Date());
//			tm.setState_id(Long.valueOf(tb.getState()));
//			tm.setSurch(new BigDecimal(tb.getSurcharge()));
//			tm.setTax_descr(tb.getDescription());
//			
//			
//			tm.setCst_rt(BigDecimal.ZERO);
//			//tm.setDeleted("");
//			//tm.setErp_cst_cd("");
//			//tm.setErp_tax_cd("");
//			//tm.setErp_vat_cd("");
//			tm.setIc_chgs(BigDecimal.ZERO);
//			//tm.setOld_tax_cd();
//			tm.setPurch_addsurch(BigDecimal.ZERO);
//			tm.setPurch_addtax(BigDecimal.ZERO);
//			tm.setPurch_cess(BigDecimal.ZERO);
//			tm.setPurch_cst_rt(BigDecimal.ZERO);
//			tm.setPurch_cst_rt(BigDecimal.ZERO);
//			tm.setPurch_ic_chg(BigDecimal.ZERO);
//			tm.setPurch_resaletax(BigDecimal.ZERO);
//			tm.setPurch_st_vat(BigDecimal.ZERO);
//			tm.setPurch_surch(BigDecimal.ZERO);
//			tm.setPurch_totax(BigDecimal.ZERO);
//			tm.setRelease_tax(BigDecimal.ZERO);
//			tm.setSt_vat(BigDecimal.ZERO);
//			tm.setTo_tax(tb.getTurnoverTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getTurnoverTax()));
//			//tm.setValid_upto_dt();
//			tm.setTax_cd("");
//			
//			this.transactionalRepository.persist(tm);
//			
//			String taxCode=String.format("%05d",tm.getTax_id());
//			System.out.println("generated tax code is :"+taxCode);
//			tm.setTax_cd(taxCode);
//			this.transactionalRepository.persist(tm);
//			
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//	}
	
	
	@Override
	public void saveTax(List<Integer> taxIdList, TaxBean tb, List<Integer> stateIdList) {
		String inTaxParam = null;
		String outTaxParam = null;
		try {
			
			System.out.println("generated IN value is :");
			System.out.println(tb.getTaxTypeIn()+
			tb.getTaxCalculationMethodIn()+
			tb.getTaxOnFreeGoodsIn()+
			tb.getConsiderDiscountInTaxCalculationIn()+
			tb.getAddOctroiToTaxableAmountsIn()+
			tb.getRecomputeRateToIncludeAllTaxesIn()+
			tb.getAddInputVatToSaleRateIn()+
			tb.getCalculateExciseIn()+
			tb.getCalculateIcChargesIn()+
			tb.getCalculateIcChargesForInterstateBillingIn()+
			tb.getCalculateIncidentalExpensesIn()+
			tb.getAllowProductDiscountIn()+
			tb.getAllowCashDiscountIn()+
			tb.getCalculateSurchargeInLieuOfCstIn()+
			tb.getCalculateTurnoverTaxIn()+
			tb.getCalculateTurnoverTaxForInterstateBillingIn());
			
			if(tb.getTaxTypeIn()!=null) {
			inTaxParam=""+tb.getTaxTypeIn()+
					tb.getTaxCalculationMethodIn()+
					tb.getTaxOnFreeGoodsIn()+
					tb.getConsiderDiscountInTaxCalculationIn()+
					tb.getAddOctroiToTaxableAmountsIn()+
					tb.getRecomputeRateToIncludeAllTaxesIn()+
					tb.getAddInputVatToSaleRateIn()+
					tb.getCalculateExciseIn()+
					tb.getCalculateIcChargesIn()+
					tb.getCalculateIcChargesForInterstateBillingIn()+
					tb.getCalculateIncidentalExpensesIn()+
					tb.getAllowProductDiscountIn()+
					tb.getAllowCashDiscountIn()+
					tb.getCalculateSurchargeInLieuOfCstIn()+
					tb.getCalculateTurnoverTaxIn()+
					tb.getCalculateTurnoverTaxForInterstateBillingIn()+"";
			}
			
			System.out.println("generated OUT value is :");
			System.out.println(tb.getTaxTypeOut()+
			tb.getTaxCalculationMethodOut()+
			tb.getTaxOnFreeGoodsOut()+
			tb.getConsiderDiscountInTaxCalculationOut()+
			tb.getAddOctroiToTaxableAmountsOut()+
			tb.getRecomputeRateToIncludeAllTaxesOut()+
			tb.getAddInputVatToSaleRateOut()+
			tb.getCalculateExciseOut()+
			tb.getCalculateIcChargesOut()+
			tb.getCalculateIcChargesForInterstateBillingOut()+
			tb.getCalculateIncidentalExpensesOut()+
			tb.getAllowProductDiscountOut()+
			tb.getAllowCashDiscountOut()+
			tb.getCalculateSurchargeInLieuOfCstOut()+
			tb.getCalculateTurnoverTaxOut()+
			tb.getCalculateTurnoverTaxForInterstateBillingOut());
			
			if(tb.getTaxTypeOut()!=null) {
			outTaxParam=""+tb.getTaxTypeOut()+
					tb.getTaxCalculationMethodOut()+
					tb.getTaxOnFreeGoodsOut()+
					tb.getConsiderDiscountInTaxCalculationOut()+
					tb.getAddOctroiToTaxableAmountsOut()+
					tb.getRecomputeRateToIncludeAllTaxesOut()+
					tb.getAddInputVatToSaleRateOut()+
					tb.getCalculateExciseOut()+
					tb.getCalculateIcChargesOut()+
					tb.getCalculateIcChargesForInterstateBillingOut()+
					tb.getCalculateIncidentalExpensesOut()+
					tb.getAllowProductDiscountOut()+
					tb.getAllowCashDiscountOut()+
					tb.getCalculateSurchargeInLieuOfCstOut()+
					tb.getCalculateTurnoverTaxOut()+
					tb.getCalculateTurnoverTaxForInterstateBillingOut()+"";
			}
			
			
			
			for(int i=0;i<stateIdList.size();i++) {
			
			TaxMaster tm=new TaxMaster();
			tm.setInput_tax_param(inTaxParam);
			tm.setOutput_tax_param(outTaxParam);
			
			//System.out.println("...."+Long.parseLong(tb.getCgst()));
			tm.setCgst(new BigDecimal(tb.getCgst()));
			tm.setIgst(new BigDecimal(tb.getIgst()));
			tm.setSgst(new BigDecimal(tb.getSgst()));
			
			tm.setAdd_surch(BigDecimal.ZERO);
			tm.setAdd_tax(tb.getAdditionalTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getAdditionalTax()));
			tm.setCess(tb.getCess()==null?BigDecimal.ZERO:new BigDecimal(tb.getCess()));
			
			tm.setCompany_cd(tb.getCompany());
			tm.setHsn_code(tb.getHsnCode());
			tm.setEffect_dt(tb.getEffectiveFrom());
			tm.setLast_mod_by(tb.getEmpId());
			tm.setLast_mod_date(new Date());
			
			tm.setState_id(Long.valueOf(stateIdList.get(i)));
			
			tm.setSurch(new BigDecimal(tb.getSurcharge()));
			tm.setTax_descr(tb.getDescription());
			
			
			tm.setCst_rt(BigDecimal.ZERO);
			//tm.setDeleted("");
			//tm.setErp_cst_cd("");
			//tm.setErp_tax_cd("");
			//tm.setErp_vat_cd("");
			tm.setIc_chgs(BigDecimal.ZERO);
			//tm.setOld_tax_cd();
			tm.setPurch_addsurch(BigDecimal.ZERO);
			tm.setPurch_addtax(BigDecimal.ZERO);
			tm.setPurch_cess(BigDecimal.ZERO);
			tm.setPurch_cst_rt(BigDecimal.ZERO);
			tm.setPurch_cst_rt(BigDecimal.ZERO);
			tm.setPurch_ic_chg(BigDecimal.ZERO);
			tm.setPurch_resaletax(BigDecimal.ZERO);
			tm.setPurch_st_vat(BigDecimal.ZERO);
			tm.setPurch_surch(BigDecimal.ZERO);
			tm.setPurch_totax(BigDecimal.ZERO);
			tm.setRelease_tax(BigDecimal.ZERO);
			tm.setSt_vat(BigDecimal.ZERO);
			tm.setTo_tax(tb.getTurnoverTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getTurnoverTax()));
			//tm.setValid_upto_dt();
			tm.setTax_cd("");
			
			this.transactionalRepository.persist(tm);
			
			String taxCode=String.format("%05d",tm.getTax_id());
			System.out.println("generated tax code is :"+taxCode);
			tm.setTax_cd(taxCode);
			this.transactionalRepository.persist(tm);
			
			}
			this.updateTax(taxIdList, tb, stateIdList);
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	
	
	
	@Override
	public Boolean checkUniqueDesc(String stateId,String hsnCode) {
		return this.taxRepository.checkUniqueDesc(stateId, hsnCode);
	}
	@Override
	public List<Object> getTaxDesc(String taxId,String hsnCode) {
		return this.taxRepository.getTaxDesc(taxId, hsnCode);
	}
	@Override
	public List<TaxMaster> getHsnCodeListByStateId(String stateId) {
		return this.taxRepository.getHsnCodeListByStateId(stateId);
	}
//	@Override
//	public void updateTax(List<Integer> taxIdList,TaxBean tb,List<Integer> stateIdList) {
//		
//		int taxId = 0;int stateId = 0;
//		System.out.println("tax id list"+taxIdList);
//		
//		String inTaxParam = null;
//		String outTaxParam = null;
//		try {
//			System.out.println("generated IN value is :");
//			System.out.println(tb.getTaxTypeIn()+
//			tb.getTaxCalculationMethodIn()+
//			tb.getTaxOnFreeGoodsIn()+
//			tb.getConsiderDiscountInTaxCalculationIn()+
//			tb.getAddOctroiToTaxableAmountsIn()+
//			tb.getRecomputeRateToIncludeAllTaxesIn()+
//			tb.getAddInputVatToSaleRateIn()+
//			tb.getCalculateExciseIn()+
//			tb.getCalculateIcChargesIn()+
//			tb.getCalculateIcChargesForInterstateBillingIn()+
//			tb.getCalculateIncidentalExpensesIn()+
//			tb.getAllowProductDiscountIn()+
//			tb.getAllowCashDiscountIn()+
//			tb.getCalculateSurchargeInLieuOfCstIn()+
//			tb.getCalculateTurnoverTaxIn()+
//			tb.getCalculateTurnoverTaxForInterstateBillingIn());
//			
//			if(tb.getTaxTypeIn()!=null) {
//			inTaxParam=""+tb.getTaxTypeIn()+
//					tb.getTaxCalculationMethodIn()+
//					tb.getTaxOnFreeGoodsIn()+
//					tb.getConsiderDiscountInTaxCalculationIn()+
//					tb.getAddOctroiToTaxableAmountsIn()+
//					tb.getRecomputeRateToIncludeAllTaxesIn()+
//					tb.getAddInputVatToSaleRateIn()+
//					tb.getCalculateExciseIn()+
//					tb.getCalculateIcChargesIn()+
//					tb.getCalculateIcChargesForInterstateBillingIn()+
//					tb.getCalculateIncidentalExpensesIn()+
//					tb.getAllowProductDiscountIn()+
//					tb.getAllowCashDiscountIn()+
//					tb.getCalculateSurchargeInLieuOfCstIn()+
//					tb.getCalculateTurnoverTaxIn()+
//					tb.getCalculateTurnoverTaxForInterstateBillingIn()+"";
//			}
//			
//			System.out.println("generated OUT value is :");
//			System.out.println(tb.getTaxTypeOut()+
//			tb.getTaxCalculationMethodOut()+
//			tb.getTaxOnFreeGoodsOut()+
//			tb.getConsiderDiscountInTaxCalculationOut()+
//			tb.getAddOctroiToTaxableAmountsOut()+
//			tb.getRecomputeRateToIncludeAllTaxesOut()+
//			tb.getAddInputVatToSaleRateOut()+
//			tb.getCalculateExciseOut()+
//			tb.getCalculateIcChargesOut()+
//			tb.getCalculateIcChargesForInterstateBillingOut()+
//			tb.getCalculateIncidentalExpensesOut()+
//			tb.getAllowProductDiscountOut()+
//			tb.getAllowCashDiscountOut()+
//			tb.getCalculateSurchargeInLieuOfCstOut()+
//			tb.getCalculateTurnoverTaxOut()+
//			tb.getCalculateTurnoverTaxForInterstateBillingOut());
//			
//			if(tb.getTaxTypeOut()!=null) {
//			outTaxParam=""+tb.getTaxTypeOut()+
//					tb.getTaxCalculationMethodOut()+
//					tb.getTaxOnFreeGoodsOut()+
//					tb.getConsiderDiscountInTaxCalculationOut()+
//					tb.getAddOctroiToTaxableAmountsOut()+
//					tb.getRecomputeRateToIncludeAllTaxesOut()+
//					tb.getAddInputVatToSaleRateOut()+
//					tb.getCalculateExciseOut()+
//					tb.getCalculateIcChargesOut()+
//					tb.getCalculateIcChargesForInterstateBillingOut()+
//					tb.getCalculateIncidentalExpensesOut()+
//					tb.getAllowProductDiscountOut()+
//					tb.getAllowCashDiscountOut()+
//					tb.getCalculateSurchargeInLieuOfCstOut()+
//					tb.getCalculateTurnoverTaxOut()+
//					tb.getCalculateTurnoverTaxForInterstateBillingOut()+"";
//			}
//			for(int i=0;i<taxIdList.size();i++) {
//				try {
//				taxId=taxIdList.get(i);
//				System.out.println("tax id :"+taxId);
//			
//				TaxMaster tm=taxRepository.getObjectById(Long.valueOf(taxId));
//				tm.setInput_tax_param(inTaxParam);
//				tm.setOutput_tax_param(outTaxParam);
//				
//				//System.out.println("...."+Long.parseLong(tb.getCgst()));
//				tm.setCgst(new BigDecimal(tb.getCgst()));
//				tm.setIgst(new BigDecimal(tb.getIgst()));
//				tm.setSgst(new BigDecimal(tb.getSgst()));
//				
//				tm.setAdd_surch(BigDecimal.ZERO);
//				tm.setAdd_tax(tb.getAdditionalTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getAdditionalTax()));
//				tm.setCess(tb.getCess()==null?BigDecimal.ZERO:new BigDecimal(tb.getCess()));
//				
//				tm.setCompany_cd(tb.getCompany());
//				tm.setHsn_code(tb.getHsnCode());
//				tm.setEffect_dt(tb.getEffectiveFrom());
//				tm.setLast_mod_by(tb.getEmpId());
//				tm.setLast_mod_date(new Date());
//				//tm.setState_id(Long.valueOf(tb.getState()));
//				tm.setSurch(new BigDecimal(tb.getSurcharge()));
//				tm.setTax_descr(tb.getDescription());
//				
//				
//				tm.setCst_rt(BigDecimal.ZERO);
//				//tm.setDeleted("");
//				//tm.setErp_cst_cd("");
//				//tm.setErp_tax_cd("");
//				//tm.setErp_vat_cd("");
//				tm.setIc_chgs(BigDecimal.ZERO);
//				//tm.setOld_tax_cd();
//				tm.setPurch_addsurch(BigDecimal.ZERO);
//				tm.setPurch_addtax(BigDecimal.ZERO);
//				tm.setPurch_cess(BigDecimal.ZERO);
//				tm.setPurch_cst_rt(BigDecimal.ZERO);
//				tm.setPurch_cst_rt(BigDecimal.ZERO);
//				tm.setPurch_ic_chg(BigDecimal.ZERO);
//				tm.setPurch_resaletax(BigDecimal.ZERO);
//				tm.setPurch_st_vat(BigDecimal.ZERO);
//				tm.setPurch_surch(BigDecimal.ZERO);
//				tm.setPurch_totax(BigDecimal.ZERO);
//				tm.setRelease_tax(BigDecimal.ZERO);
//				tm.setSt_vat(BigDecimal.ZERO);
//				tm.setTo_tax(tb.getTurnoverTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getTurnoverTax()));
//				//tm.setValid_upto_dt();
//				tm.setTax_cd("");
//				
//				this.transactionalRepository.update(tm);
//				
//				String taxCode=String.format("%05d",tm.getTax_id());
//				System.out.println("generated tax code is :"+taxCode);
//				tm.setTax_cd(taxCode);
//				this.transactionalRepository.update(tm);
//				}
//				catch (Exception e) {
//					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//				}
//			}
//			for(int i=0;i<stateIdList.size();i++) {
//				try {
//					stateId=stateIdList.get(i);
//					System.out.println("state id :"+stateId);
//			
//				TaxMaster tm=new TaxMaster();
//				tm.setInput_tax_param(inTaxParam);
//				tm.setOutput_tax_param(outTaxParam);
//				
//				tm.setCgst(new BigDecimal(tb.getCgst()));
//				tm.setIgst(new BigDecimal(tb.getIgst()));
//				tm.setSgst(new BigDecimal(tb.getSgst()));
//				
//				tm.setAdd_surch(BigDecimal.ZERO);
//				tm.setAdd_tax(tb.getAdditionalTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getAdditionalTax()));
//				tm.setCess(tb.getCess()==null?BigDecimal.ZERO:new BigDecimal(tb.getCess()));
//				
//				tm.setCompany_cd(tb.getCompany());
//				tm.setHsn_code(tb.getHsnCode());
//				tm.setEffect_dt(tb.getEffectiveFrom());
//				tm.setLast_mod_by(tb.getEmpId());
//				tm.setLast_mod_date(new Date());
//				tm.setState_id(Long.valueOf(stateId));
//				tm.setSurch(new BigDecimal(tb.getSurcharge()));
//				tm.setTax_descr(tb.getDescription());
//				
//				
//				tm.setCst_rt(BigDecimal.ZERO);
//				//tm.setDeleted("");
//				//tm.setErp_cst_cd("");
//				//tm.setErp_tax_cd("");
//				//tm.setErp_vat_cd("");
//				tm.setIc_chgs(BigDecimal.ZERO);
//				//tm.setOld_tax_cd();
//				tm.setPurch_addsurch(BigDecimal.ZERO);
//				tm.setPurch_addtax(BigDecimal.ZERO);
//				tm.setPurch_cess(BigDecimal.ZERO);
//				tm.setPurch_cst_rt(BigDecimal.ZERO);
//				tm.setPurch_cst_rt(BigDecimal.ZERO);
//				tm.setPurch_ic_chg(BigDecimal.ZERO);
//				tm.setPurch_resaletax(BigDecimal.ZERO);
//				tm.setPurch_st_vat(BigDecimal.ZERO);
//				tm.setPurch_surch(BigDecimal.ZERO);
//				tm.setPurch_totax(BigDecimal.ZERO);
//				tm.setRelease_tax(BigDecimal.ZERO);
//				tm.setSt_vat(BigDecimal.ZERO);
//				tm.setTo_tax(tb.getTurnoverTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getTurnoverTax()));
//				//tm.setValid_upto_dt();
//				tm.setTax_cd("");
//				
//				this.transactionalRepository.persist(tm);
//				
//				String taxCode=String.format("%05d",tm.getTax_id());
//				System.out.println("generated tax code is :"+taxCode);
//				tm.setTax_cd(taxCode);
//				this.transactionalRepository.persist(tm);
//				}
//				catch (Exception e) {
//					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//				}
//			}
//		}
//		catch (Exception e) {
//			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//		}
//	}
	
	
	@Override
	public void updateTax(List<Integer> taxIdList,TaxBean tb,List<Integer> stateIdList) {
		
		int taxId = 0;int stateId = 0;
		System.out.println("tax id list"+taxIdList);
		
		String inTaxParam = null;
		String outTaxParam = null;
		try {
			System.out.println("generated IN value is :");
			System.out.println(tb.getTaxTypeIn()+
			tb.getTaxCalculationMethodIn()+
			tb.getTaxOnFreeGoodsIn()+
			tb.getConsiderDiscountInTaxCalculationIn()+
			tb.getAddOctroiToTaxableAmountsIn()+
			tb.getRecomputeRateToIncludeAllTaxesIn()+
			tb.getAddInputVatToSaleRateIn()+
			tb.getCalculateExciseIn()+
			tb.getCalculateIcChargesIn()+
			tb.getCalculateIcChargesForInterstateBillingIn()+
			tb.getCalculateIncidentalExpensesIn()+
			tb.getAllowProductDiscountIn()+
			tb.getAllowCashDiscountIn()+
			tb.getCalculateSurchargeInLieuOfCstIn()+
			tb.getCalculateTurnoverTaxIn()+
			tb.getCalculateTurnoverTaxForInterstateBillingIn());
			
			if(tb.getTaxTypeIn()!=null) {
			inTaxParam=""+tb.getTaxTypeIn()+
					tb.getTaxCalculationMethodIn()+
					tb.getTaxOnFreeGoodsIn()+
					tb.getConsiderDiscountInTaxCalculationIn()+
					tb.getAddOctroiToTaxableAmountsIn()+
					tb.getRecomputeRateToIncludeAllTaxesIn()+
					tb.getAddInputVatToSaleRateIn()+
					tb.getCalculateExciseIn()+
					tb.getCalculateIcChargesIn()+
					tb.getCalculateIcChargesForInterstateBillingIn()+
					tb.getCalculateIncidentalExpensesIn()+
					tb.getAllowProductDiscountIn()+
					tb.getAllowCashDiscountIn()+
					tb.getCalculateSurchargeInLieuOfCstIn()+
					tb.getCalculateTurnoverTaxIn()+
					tb.getCalculateTurnoverTaxForInterstateBillingIn()+"";
			}
			
			System.out.println("generated OUT value is :");
			System.out.println(tb.getTaxTypeOut()+
			tb.getTaxCalculationMethodOut()+
			tb.getTaxOnFreeGoodsOut()+
			tb.getConsiderDiscountInTaxCalculationOut()+
			tb.getAddOctroiToTaxableAmountsOut()+
			tb.getRecomputeRateToIncludeAllTaxesOut()+
			tb.getAddInputVatToSaleRateOut()+
			tb.getCalculateExciseOut()+
			tb.getCalculateIcChargesOut()+
			tb.getCalculateIcChargesForInterstateBillingOut()+
			tb.getCalculateIncidentalExpensesOut()+
			tb.getAllowProductDiscountOut()+
			tb.getAllowCashDiscountOut()+
			tb.getCalculateSurchargeInLieuOfCstOut()+
			tb.getCalculateTurnoverTaxOut()+
			tb.getCalculateTurnoverTaxForInterstateBillingOut());
			
			if(tb.getTaxTypeOut()!=null) {
			outTaxParam=""+tb.getTaxTypeOut()+
					tb.getTaxCalculationMethodOut()+
					tb.getTaxOnFreeGoodsOut()+
					tb.getConsiderDiscountInTaxCalculationOut()+
					tb.getAddOctroiToTaxableAmountsOut()+
					tb.getRecomputeRateToIncludeAllTaxesOut()+
					tb.getAddInputVatToSaleRateOut()+
					tb.getCalculateExciseOut()+
					tb.getCalculateIcChargesOut()+
					tb.getCalculateIcChargesForInterstateBillingOut()+
					tb.getCalculateIncidentalExpensesOut()+
					tb.getAllowProductDiscountOut()+
					tb.getAllowCashDiscountOut()+
					tb.getCalculateSurchargeInLieuOfCstOut()+
					tb.getCalculateTurnoverTaxOut()+
					tb.getCalculateTurnoverTaxForInterstateBillingOut()+"";
			}
			for(int i=0;i<taxIdList.size();i++) {
				try {
				taxId=taxIdList.get(i);
				System.out.println("tax id :"+taxId);
			
				TaxMaster tm=taxRepository.getObjectById(Long.valueOf(taxId));
				
				System.err.println(tm);
				tm.setInput_tax_param(inTaxParam);
				tm.setOutput_tax_param(outTaxParam);
				
				//System.out.println("...."+Long.parseLong(tb.getCgst()));
				tm.setCgst(new BigDecimal(tb.getCgst()));
				tm.setIgst(new BigDecimal(tb.getIgst()));
				tm.setSgst(new BigDecimal(tb.getSgst()));
				
				tm.setAdd_surch(BigDecimal.ZERO);
				tm.setAdd_tax(tb.getAdditionalTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getAdditionalTax()));
				tm.setCess(tb.getCess()==null?BigDecimal.ZERO:new BigDecimal(tb.getCess()));
				
				tm.setCompany_cd(tb.getCompany());
				tm.setHsn_code(tb.getHsnCode());
				tm.setEffect_dt(tb.getEffectiveFrom());
				tm.setLast_mod_by(tb.getEmpId());
				tm.setLast_mod_date(new Date());
				//tm.setState_id(Long.valueOf(tb.getState()));
				tm.setSurch(new BigDecimal(tb.getSurcharge()));
				tm.setTax_descr(tb.getDescription());
				
				
				tm.setCst_rt(BigDecimal.ZERO);
				//tm.setDeleted("");
				//tm.setErp_cst_cd("");
				//tm.setErp_tax_cd("");
				//tm.setErp_vat_cd("");
				tm.setIc_chgs(BigDecimal.ZERO);
				//tm.setOld_tax_cd();
				tm.setPurch_addsurch(BigDecimal.ZERO);
				tm.setPurch_addtax(BigDecimal.ZERO);
				tm.setPurch_cess(BigDecimal.ZERO);
				tm.setPurch_cst_rt(BigDecimal.ZERO);
				tm.setPurch_cst_rt(BigDecimal.ZERO);
				tm.setPurch_ic_chg(BigDecimal.ZERO);
				tm.setPurch_resaletax(BigDecimal.ZERO);
				tm.setPurch_st_vat(BigDecimal.ZERO);
				tm.setPurch_surch(BigDecimal.ZERO);
				tm.setPurch_totax(BigDecimal.ZERO);
				tm.setRelease_tax(BigDecimal.ZERO);
				tm.setSt_vat(BigDecimal.ZERO);
				tm.setTo_tax(tb.getTurnoverTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getTurnoverTax()));
				//tm.setValid_upto_dt();
				tm.setTax_cd("");
				
				this.transactionalRepository.update(tm);
				
				String taxCode=String.format("%05d",tm.getTax_id());
				System.out.println("generated tax code is :"+taxCode);
				tm.setTax_cd(taxCode);
				this.transactionalRepository.update(tm);
				}
				catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			}
			
			for(int i=0;i<stateIdList.size();i++) {
				try {
					stateId=stateIdList.get(i);
					System.out.println("state id :"+stateId);
			
				TaxMaster tm=new TaxMaster();
				tm.setInput_tax_param(inTaxParam);
				tm.setOutput_tax_param(outTaxParam);
				
				tm.setCgst(new BigDecimal(tb.getCgst()));
				tm.setIgst(new BigDecimal(tb.getIgst()));
				tm.setSgst(new BigDecimal(tb.getSgst()));
				
				tm.setAdd_surch(BigDecimal.ZERO);
				tm.setAdd_tax(tb.getAdditionalTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getAdditionalTax()));
				tm.setCess(tb.getCess()==null?BigDecimal.ZERO:new BigDecimal(tb.getCess()));
				
				tm.setCompany_cd(tb.getCompany());
				tm.setHsn_code(tb.getHsnCode());
				tm.setEffect_dt(tb.getEffectiveFrom());
				tm.setLast_mod_by(tb.getEmpId());
				tm.setLast_mod_date(new Date());
				tm.setState_id(Long.valueOf(stateId));
				tm.setSurch(new BigDecimal(tb.getSurcharge()));
				tm.setTax_descr(tb.getDescription());
				
				
				tm.setCst_rt(BigDecimal.ZERO);
				//tm.setDeleted("");
				//tm.setErp_cst_cd("");
				//tm.setErp_tax_cd("");
				//tm.setErp_vat_cd("");
				tm.setIc_chgs(BigDecimal.ZERO);
				//tm.setOld_tax_cd();
				tm.setPurch_addsurch(BigDecimal.ZERO);
				tm.setPurch_addtax(BigDecimal.ZERO);
				tm.setPurch_cess(BigDecimal.ZERO);
				tm.setPurch_cst_rt(BigDecimal.ZERO);
				tm.setPurch_cst_rt(BigDecimal.ZERO);
				tm.setPurch_ic_chg(BigDecimal.ZERO);
				tm.setPurch_resaletax(BigDecimal.ZERO);
				tm.setPurch_st_vat(BigDecimal.ZERO);
				tm.setPurch_surch(BigDecimal.ZERO);
				tm.setPurch_totax(BigDecimal.ZERO);
				tm.setRelease_tax(BigDecimal.ZERO);
				tm.setSt_vat(BigDecimal.ZERO);
				tm.setTo_tax(tb.getTurnoverTax()==null?BigDecimal.ZERO:new BigDecimal(tb.getTurnoverTax()));
				//tm.setValid_upto_dt();
				List<TaxMaster> taxMastersList= new ArrayList<>();
				 taxMastersList =this.taxRepository.get_TaxMaster_Data(tm.getHsn_code(),tm.getState_id());
				System.err.println("taxMastersList  ::;"+taxMastersList);
				
				// if(this. hsn code and state id  is  not exists)
				if(taxMastersList.size()>0) {
					tm.setTax_cd(taxMastersList.get(0).getTax_cd());
					tm.setTax_id(taxMastersList.get(0).getTax_id());
					this.transactionalRepository.update(tm);	
				}
				else {
					tm.setTax_cd("");
					this.transactionalRepository.persist(tm);
					String taxCode=String.format("%05d",tm.getTax_id());
					System.out.println("generated tax code is :"+taxCode);	
					tm.setTax_cd(taxCode);
					this.transactionalRepository.persist(tm);	
					
				}
				
				
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			throw e;
		}
	}
	
	
	
	
	
	
	@Override
	public List<TaxMaster> getAllTaxDetail() {
		return this.taxRepository.getAllTaxDetail();
	}



	@Override
	public Map<String, List<Object>> getAll_States_And_Tax_code(String hsnCode) throws Exception {
		
		
		return this.taxRepository.getAll_States_And_Tax_code(hsnCode);
	}



	@Override
	public List<Object> getAll_tax_code(String hsnCode) {
		
		
		return this.taxRepository.getAll_tax_code(hsnCode);
	}



	@Override
	public Boolean hsn_Exist(String hsnCode) {
		
		return this.taxRepository.hsn_Exist_Or_Not(hsnCode);
		
	}
}
