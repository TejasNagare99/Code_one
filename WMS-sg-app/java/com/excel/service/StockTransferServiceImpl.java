package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.StockTransferBean;
import com.excel.bean.TaxCalculationBean;
import com.excel.bean.TaxParamBean;
import com.excel.exception.MedicoException;
import com.excel.model.BatchStockLog;
import com.excel.model.BatchStockLogNS;
import com.excel.model.Location;
import com.excel.model.SWV_Header;
import com.excel.model.SmpBatch;
import com.excel.model.SmpBatchNS;
import com.excel.model.SmpItem;
import com.excel.model.Stock_Transfer_Details;
import com.excel.model.Stock_Transfer_Header;
import com.excel.model.Trans_Key_Master;
import com.excel.repository.BatchMasterRepository;
import com.excel.repository.BatchStockLogRepository;
import com.excel.repository.CalculateGstRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.ProductMasterRepository;
import com.excel.repository.StockTransferRepository;
import com.excel.repository.TransKeyMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.Utility;

@Service
public class StockTransferServiceImpl implements StockTransferService,MedicoConstants {
	
	@Autowired StockTransferRepository stockTransferRepository;
	@Autowired LocationRepository locationRepository;
	@Autowired ProductMasterRepository productMasterRepository;
	@Autowired TransKeyMasterRepository transKeyMasterRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired BatchMasterRepository batchMasterRepository;
	@Autowired CalculateGstRepository calculateGstRepository;
	@Autowired CalculateGstService calculateGstService;
	@Autowired BatchStockLogRepository batchStockLogRepository;

	
	@Override
	public List<StockTransferBean> getProductListBySendrecLoc(Long sendLocId, Long sendSubCompId, Long sendStateId,
			String nilGstInd,String stockType)  throws Exception{
		return stockTransferRepository.getProductListBySendrecLoc(sendLocId, sendSubCompId, sendStateId, nilGstInd,stockType);
	}

	@Override
	public List<Object> getbatchByProdAndLoc(Long sendLocId, Long prodId,String tranType,String stockType)  throws Exception{
		return stockTransferRepository.getbatchByProdAndLoc(sendLocId, prodId,tranType,stockType);
	}
	
	@Override
	public List<Object> getSgstIgst(Long dptLocId,Long prodId)  throws Exception{
		
	 return	stockTransferRepository.getSgstIgst(dptLocId,prodId);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveStkTrf(StockTransferBean stockBean) throws Exception {
		Map<String, Object> returnMap = new HashMap<>();
		Stock_Transfer_Header ostockHd = null;
		Location send_location_obj = null;
		Location recv_location_obj = null;
		String gst_type_ind = "";
		stockBean.setApplyZeroGST(false);
		SmpItem product_Master = null;
		
		if(stockBean.getTranType().equals(STK_TRF_SA)) {
			stockBean.setStk_trf_tran_type(STK_TRF_SA);
		} else {
			stockBean.setStk_trf_tran_type(STK_TRF_NS);
		}
		
		send_location_obj = this.locationRepository.getObjectById(stockBean.getSendLocId());// sending loc's state
		recv_location_obj = this.locationRepository.getObjectById(stockBean.getRecLocation()); // receiving loc's state
		stockBean.setSendState(send_location_obj.getLoc_state_id());
		stockBean.setRecState(recv_location_obj.getLoc_state_id());
		if(stockBean.getGstInd().equalsIgnoreCase("Y")){
			if(send_location_obj.getGst_req_ind().equalsIgnoreCase("Y")){
				if(send_location_obj.getLoc_state_id().compareTo(recv_location_obj.getLoc_state_id())==0){
					gst_type_ind = "G";
					if(send_location_obj.getGst_reg_no()!="" && 
							send_location_obj.getGst_reg_no().equalsIgnoreCase(recv_location_obj.getGst_reg_no())){
						stockBean.setApplyZeroGST(true);
						stockBean.setGst_doc_type("BS");
    					stockBean.setGst_reverse_chg("N");
					}else if (recv_location_obj.getGst_reg_no()!=null && !recv_location_obj.getGst_reg_no().equalsIgnoreCase("")){
						stockBean.setGst_doc_type("SN");
    					stockBean.setGst_reverse_chg("N");
					}else{
						stockBean.setGst_doc_type("SR");
    					stockBean.setGst_reverse_chg("Y");
					}
				}else{
					gst_type_ind = "I";
					if(recv_location_obj.getGst_reg_no()!=null && !recv_location_obj.getGst_reg_no().equalsIgnoreCase("")){
    					stockBean.setGst_doc_type("IN");
    					stockBean.setGst_reverse_chg("N");
    				}else{
    					stockBean.setGst_doc_type("IR");
    					stockBean.setGst_reverse_chg("Y");
    				}
				}
				String gst_t =  this.calculateGstRepository.getGST_Doc_type_Para_code(stockBean.getStk_trf_tran_type(),
						stockBean.getGst_doc_type());
    			stockBean.setGst_doc_type(gst_t);
			}
		}
		stockBean.setGstTypeInd(gst_type_ind);
		
		product_Master = this.productMasterRepository.getObjectById(stockBean.getProductId());
		ostockHd=this.saveStkHeader(stockBean);
		
		Stock_Transfer_Details ostockdt = null;	
    	SmpBatch sendBatch = null;
    	SmpBatchNS sendBatchNs=null;
    	BigDecimal free_qty=BigDecimal.ZERO;
		
    	for(int i=0; i<stockBean.getBatchIds().size(); i++){
    		if(stockBean.getBatchBilledQtys().get(i).compareTo(BigDecimal.ZERO)==1){
    			free_qty=stockBean.getBatchFreeQtys().get(i)!=null?stockBean.getBatchFreeQtys().get(i):BigDecimal.ZERO;
    			ostockdt = new Stock_Transfer_Details();
    			if(stockBean.getTranType().equals(STK_TRF_SA)) {
    				sendBatch = this.batchMasterRepository.getObjectById(stockBean.getBatchIds().get(i));
					/*
					 * Boolean recvBatch =
					 * this.batchMasterRepository.checkDuplicateRecordStk(stockBean.getRecLocation()
					 * , sendBatch.getBatch_no(), stockBean.getProductId()); if(!recvBatch){
					 * this.createbatch(sendBatch,stockBean.getTrfDate(),stockBean.getRecLocation(),
					 * stockBean.getEmpId(),stockBean.getIpAddress()); }
					 */
    			} else {
    				sendBatchNs = this.batchMasterRepository.getNSObjectByIdAndStocktype(stockBean.getBatchIds().get(i),stockBean.getProductId(),stockBean.getSendLocId(),stockBean.getStockTypeId());
					/*
					 * Boolean recvBatch =
					 * this.batchMasterRepository.checkDuplicateRecordStkNs(stockBean.getRecLocation
					 * (), sendBatchNs.getBatch_no(),
					 * stockBean.getProductId(),stockBean.getStockTypeId()); if(!recvBatch){
					 * this.createbatchNS(sendBatchNs,stockBean.getTrfDate(),stockBean.
					 * getRecLocation(),stockBean.getEmpId(),stockBean.getIpAddress()); }
					 */
    				ostockdt.setStock_type(stockBean.getStockTypeId());
    				ostockdt.setStock_type_id(stockBean.getStockTypeDetId());
    			}
    			
    			ostockdt.setBatch_id(stockBean.getBatchIds().get(i));
	    		ostockdt.setRate(stockBean.getBatchTrfRates().get(i));
	    		ostockdt.setRate_id(0L);
	    		ostockdt.setProd_id(stockBean.getProductId());
	    		ostockdt.setDiv_id(product_Master.getSmp_std_div_id());
	    		ostockdt.setTax_id(0L);    		
	    		ostockdt.setRate_dt_id(0L);
	    		ostockdt.setSold_qty(stockBean.getBatchBilledQtys().get(i));
	    		ostockdt.setFree_qty(free_qty);
	    		ostockdt.setRepl_qty(BigDecimal.ZERO);
	    		ostockdt.setComp_cd(stockBean.getCompanyCode());
	    		ostockdt.setFin_year_id(Long.valueOf(stockBean.getCurrFinYear()));
	    		ostockdt.setWeight(BigDecimal.ZERO);
	    		ostockdt.setVolume(BigDecimal.ZERO);    		
	    		ostockdt.setStockHeader(ostockHd);
	    		ostockdt.setTRFDTL_ins_dt(new Date());
	    		ostockdt.setTRFDTL_ins_usr_id(stockBean.getEmpId());
	    		ostockdt.setTRFDTL_ins_ip_add(stockBean.getIpAddress());
	    		ostockdt.setTRFDTL_status("A");
	    		
	    		BigDecimal cgst_rate = BigDecimal.ZERO;
				BigDecimal sgst_rate = BigDecimal.ZERO;
				BigDecimal igst_rate =	BigDecimal.ZERO;				
				Object[] obj = new Object[12];
	    		if(stockBean.getGstInd().equalsIgnoreCase("Y")){
					
					if(!stockBean.getApplyZeroGST()){
						TaxParamBean taxparam =calculateGstRepository.getTaxParamsByStateIdAndProdId(send_location_obj.getLoc_state_id(),stockBean.getProductId());
						obj[0]=taxparam.getProd_code();
					    obj[1]=taxparam.getOutput_tax_param();
					    obj[2]=taxparam.getSt_vat();
					    obj[3]=taxparam.getCst_rt();
					    obj[4]=taxparam.getSurch();
					    obj[5]=taxparam.getIc_chgs();
					    obj[6]=taxparam.getCess();
					    obj[7]=taxparam.getTo_tax();
					    obj[8]=taxparam.getProd_disc();
					    obj[9]=taxparam.getCgst();
					    obj[10]=taxparam.getIgst();
					    obj[11]=taxparam.getSgst();
						cgst_rate = obj[9]!=null?new BigDecimal(obj[9].toString()):BigDecimal.ZERO;
						igst_rate =	obj[10]!=null?new BigDecimal(obj[10].toString()):BigDecimal.ZERO;
						sgst_rate = obj[11]!=null?new BigDecimal(obj[11].toString()):BigDecimal.ZERO;
					}else {
						obj[1]="GSNYNNNNNNNNNNNN";
					}
										
					TaxCalculationBean invoice_Posting = this.calculateGstService.generateGstValues(
							DISPATCH, 					//TODO need to confirm this
							stockBean.getGstTypeInd(),							//gst_type_ind
							stockBean.getBatchBilledQtys().get(i), 		//billedQty
							free_qty, 								//freeQty
							BigDecimal.ZERO,						//replQty
							stockBean.getBatchTrfRates().get(i),				//rate
							BigDecimal.ZERO,						//party discount
							obj[1].toString(),						//Out_Tax_Param,
							null, 									//In_Tax_Param
							cgst_rate,								//cgst_rate
							igst_rate,								//igst_rate
							sgst_rate, 								//sgst_rate
							BigDecimal.ZERO,						//Surch_Rate
							BigDecimal.ZERO,						//Cess_Rate
							BigDecimal.ZERO,						//TOT_Rate
							BigDecimal.ZERO,						//Prod_Disc
							BigDecimal.ZERO,						//Octroi_Rate
							"000",									//Cust_Type
							BigDecimal.ZERO, 						//mrp_diff
							BigDecimal.ZERO,						//trade discount
							stockBean.getCompanyCode(),								//Comp cd
							BigDecimal.ZERO,						//mrp rate
							BigDecimal.ZERO,
							BigDecimal.ZERO);						//SS RATE
					
					//set gst variables in Stock transfer detail model
					ostockdt.setGoods_value(invoice_Posting.getGoods_value());
					ostockdt.setTaxable_amt(invoice_Posting.getTaxable_amt_billed());
					ostockdt.setTax_amt_billed(invoice_Posting.getTaxable_amt_billed());
					ostockdt.setTax_param(obj[1].toString());
					ostockdt.setSgst_rate(invoice_Posting.getSgst_rate());
					ostockdt.setSgst_bill_amt(invoice_Posting.getSgst_bill_amount());
					ostockdt.setCgst_rate(invoice_Posting.getCgst_rate());
					ostockdt.setCgst_bill_amt(invoice_Posting.getCgst_bill_amount());
					ostockdt.setIgst_rate(invoice_Posting.getIgst_rate());
					ostockdt.setIgst_bill_amt(invoice_Posting.getIgst_bill_amount());
					ostockdt.setVat_cst_ind(ostockHd.getVat_cst_ind());
					ostockdt.setGst_reverse_chg(ostockHd.getGst_reverse_chg());
					ostockdt.setGst_doc_type(ostockHd.getGst_doc_type());
					ostockdt.setHsn_code(product_Master.getHsn_code());
	    		}
	    		
		    	this.transactionalRepository.persist(ostockdt);
	    		
	    		ostockHd.setGoods_value(ostockHd.getGoods_value().add(ostockdt.getGoods_value()));
	    		ostockHd.setTaxable_amt(ostockHd.getTaxable_amt().add(ostockdt.getTaxable_amt()));					
	    		ostockHd.setTax_amt_billed(ostockHd.getTax_amt_billed().add(ostockdt.getTax_amt_billed()));
				ostockHd.setSgst_bill_amt(ostockHd.getSgst_bill_amt().add(ostockdt.getSgst_bill_amt()));
				ostockHd.setCgst_bill_amt(ostockHd.getCgst_bill_amt().add(ostockdt.getCgst_bill_amt()));
				ostockHd.setIgst_bill_amt(ostockHd.getIgst_bill_amt().add(ostockdt.getIgst_bill_amt()));
				
				this.transactionalRepository.update(ostockHd);
				
				//TODO FREE_GOODS_STN
				if(stockBean.getFreeGoodsStn().equalsIgnoreCase("Y")){
					if(free_qty.compareTo(BigDecimal.ZERO)==1 ||
							stockBean.getBatchTrfRates().get(i).compareTo(BigDecimal.ZERO)<=0){
						if(ostockHd.getFree_separate_challan().equalsIgnoreCase("N")){
							//only one time
							Trans_Key_Master trans_key_master =this.transKeyMasterRepository.getTransObj(stockBean.getSendLocId(), 
									stockBean.getCurrFinYear(), stockBean.getCompanyCode(), Long.valueOf(stockBean.getStk_trf_tran_type()), "FR");
							if(trans_key_master == null)
								throw new MedicoException("Trans Key Master not mapped");
							long currno = Long.parseLong(trans_key_master.getLast_num());
							ostockHd.setFree_dispatch_no(trans_key_master.getPrefix().trim()+ stockBean.getCurrFinYear().substring(2) + String.format("%05d", currno));
							ostockHd.setFree_separate_challan("Y");
							//update trans_key_master
							trans_key_master.setLast_num(String.format("%015d", currno+1l));
							this.transactionalRepository.update(trans_key_master);
						}
						ostockHd.setTotal_free_qty(ostockHd.getTotal_free_qty().add(free_qty));
					}
				}
				
				// updating batch master
				if(stockBean.getTranType().equals(STK_TRF_SA)) {
					batchUpdate(sendBatch, ostockdt.getSold_qty().add(free_qty));
					
					// update or insert batch_stock_log
					
					BatchStockLog odatewise=this.batchStockLogRepository.getRecordByProdIdBatchIdLocIdTranType(stockBean.getBatchIds().get(i), stockBean.getProductId(),
							ostockHd.getLoc_id(), ostockHd.getTrf_dt(), STK_TRF_SA);
					
					setDateWiseStockSaleable(odatewise, sendBatch, product_Master, stockBean.getCurrFinYear(), 
			        		ostockHd.getTrf_dt(), stockBean.getSendLocId(), ostockdt.getSold_qty().add(free_qty).negate(),
			        		stockBean.getBatchTrfRates().get(i), STK_TRF_SA,
			        		stockBean.getCompanyCode(),stockBean.getEmpId(),stockBean.getIpAddress());
				} else {
					
					batchUpdateNS(sendBatchNs, ostockdt.getSold_qty().add(free_qty));
					
					BatchStockLogNS odatewise=this.batchStockLogRepository.getNSRecordByProdIdNew(stockBean.getProductId(),stockBean.getBatchIds().get(i),
							ostockHd.getLoc_id(), ostockHd.getTrf_dt(), stockBean.getStockTypeId(),STK_TRF_NS);
					setDateWiseStockSaleableNS(odatewise, sendBatchNs, product_Master, stockBean.getCurrFinYear(), 
			        		ostockHd.getTrf_dt(), stockBean.getSendLocId(), ostockdt.getSold_qty().add(free_qty).negate(),
			        		stockBean.getBatchTrfRates().get(i), STK_TRF_NS,
			        		stockBean.getCompanyCode(),stockBean.getEmpId(),stockBean.getIpAddress());
					
				}
				
	    	}
    	}
    	
    	returnMap.put("headerId", ostockHd.getTrf_id());
    	returnMap.put("trans_no", stockBean.getTrans_no());
		return returnMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Stock_Transfer_Header saveStkHeader(StockTransferBean stockBean) throws Exception {
		Stock_Transfer_Header ostockHd=null;
		if(stockBean.getHeaderId() == 0){
			ostockHd = new Stock_Transfer_Header();
	    	ostockHd.setUser_id(0L);
	    	ostockHd.setCancelled("N");
	    	ostockHd.setTrf_dt(MedicoResources.convert_DD_MM_YYYY_toDate(stockBean.getTrfDate()));
	    	ostockHd.setRec_loc_id(stockBean.getRecLocation());
	    	ostockHd.setTrf_type("001");
	    	ostockHd.setParty_id(stockBean.getRecLocation());
	    	ostockHd.setLoc_id(stockBean.getSendLocId());  
	    	ostockHd.setSend_stock_point_id(0L);
	    	ostockHd.setRecv_stock_point(0L);
	    	if(stockBean.getSendState().compareTo(stockBean.getRecState())==0)
	    		ostockHd.setVat_yn("Y");
	    	else
	    		ostockHd.setVat_yn("N");
	    	
	    	ostockHd.setComp_cd(stockBean.getCompanyCode());
			ostockHd.setAccntg_co_cd(stockBean.getCompanyCode());
			ostockHd.setFin_year_id(Long.valueOf(stockBean.getCurrFinYear()));
			ostockHd.setVat_cst_ind(stockBean.getGstTypeInd());
			ostockHd.setGst_reverse_chg(stockBean.getGst_reverse_chg());
			ostockHd.setGst_doc_type(stockBean.getGst_doc_type());
			
			ostockHd.setTrfhdr_ins_dt(new Date());
			ostockHd.setTrfhdr_ins_usr_id(stockBean.getEmpId());
			ostockHd.setTrfhdr_ins_ip_add(stockBean.getIpAddress());
			ostockHd.setTrfhdr_ins_usr_id(stockBean.getEmpId());
			ostockHd.setTrfhdr_status("A");
			ostockHd.setStock_sa_or_ns(stockBean.getTranType().equals(STK_TRF_SA) ? SA : NS);
			
			String key_type="IN";
			if(stockBean.getNilGstStn().equalsIgnoreCase("Y")){
				if(stockBean.getNilGstInd().equalsIgnoreCase("Y")){
					key_type="BS";
					stockBean.setApplyZeroGST(true);
					stockBean.setGst_doc_type("BS");
					stockBean.setGst_reverse_chg("N");
					ostockHd.setNilgst_separate_challan("Y");
				}
			}
			
			//set transfer number
			Trans_Key_Master trans_key_master =this.transKeyMasterRepository.getTransObj(stockBean.getSendLocId(), 
					stockBean.getCurrFinYear(), stockBean.getCompanyCode(), Long.valueOf(stockBean.getStk_trf_tran_type()), key_type);
			if(trans_key_master == null)
				throw new MedicoException("Trans Key Master not mapped");
			
			long currno = Long.parseLong(trans_key_master.getLast_num());
			String trans_no = trans_key_master.getPrefix().trim()+ stockBean.getCurrFinYear().substring(2) + String.format("%05d", currno);		
			ostockHd.setTrf_no(trans_no);
			stockBean.setTrans_no(trans_no);
			
			//update trans_key_master
			trans_key_master.setLast_num(String.format("%015d", currno+1l));
			transactionalRepository.update(trans_key_master);
			
			transactionalRepository.persist(ostockHd);
		}else{
			ostockHd = this.stockTransferRepository.getObjectById(stockBean.getHeaderId());
		}
		return ostockHd;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createbatch(SmpBatch sendBatch, String tran_date, Long loc_id, String user_id, String ip_addr) throws Exception {
		SmpBatch smpBatch = new SmpBatch();
		smpBatch.setBatch_prod_id(sendBatch.getBatch_prod_id());
		smpBatch.setBatch_loc_id(loc_id);
		smpBatch.setBatch_no(sendBatch.getBatch_no());
		smpBatch.setBatch_mfg_dt(sendBatch.getBatch_mfg_dt());
		smpBatch.setBatch_expiry_dt(sendBatch.getBatch_expiry_dt());
		smpBatch.setBatch_physical_stock(0L);
		smpBatch.setBatch_narration(sendBatch.getBatch_narration().isEmpty() ? "": sendBatch.getBatch_narration());
		smpBatch.setBatch_mfg_loc_id(sendBatch.getBatch_mfg_loc_id());
		smpBatch.setBatch_rate(sendBatch.getBatch_rate());
		smpBatch.setBatch_costing_rate(sendBatch.getBatch_costing_rate() == null ? BigDecimal.ZERO
				: sendBatch.getBatch_costing_rate());
		smpBatch.setBatch_mktg_rate(sendBatch.getBatch_mktg_rate()== null ? BigDecimal.ZERO
				: sendBatch.getBatch_mktg_rate());
		smpBatch.setBatch_nrv(sendBatch.getBatch_nrv() == null ? BigDecimal.ZERO
				: sendBatch.getBatch_nrv());
		smpBatch.setBatch_display_rate(sendBatch.getBatch_display_rate() == null ? BigDecimal.ZERO
				: sendBatch.getBatch_display_rate());
		smpBatch.setBatch_open_stock(0L);
		smpBatch.setBatch_in_stock(BigDecimal.ZERO);
		smpBatch.setBatch_out_stock(BigDecimal.ZERO);
		smpBatch.setBatch_exclude_loc("N");
		smpBatch.setBatch_exclude_PARTY("N");
		smpBatch.setBatch_with_held_qty(BigDecimal.ZERO);
		smpBatch.setBatch_erp_batch_cd(sendBatch.getBatch_no());
		smpBatch.setBatch_ins_usr_id(user_id);
		smpBatch.setBatch_mod_usr_id("");
		smpBatch.setBatch_ins_dt(new Date());
		smpBatch.setBatch_ins_ip_add(ip_addr);
		smpBatch.setBatch_mod_ip_add("");
		smpBatch.setBatch_status("A");
		this.transactionalRepository.persist(smpBatch);
 	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void batchUpdate(SmpBatch obatch, BigDecimal sold_qty) throws Exception {
		BigDecimal batch_out_stk = obatch.getBatch_out_stock() == null ? BigDecimal.ZERO : obatch.getBatch_out_stock();
		obatch.setBatch_out_stock(batch_out_stk.add(sold_qty));
		this.transactionalRepository.update(obatch);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setDateWiseStockSaleable(BatchStockLog dtStock, SmpBatch sendBatch, SmpItem product_Master,
			String fin_year, Date trf_dt, Long send_loc_id, BigDecimal billed_qty, BigDecimal rate, String tran_type,
			String comp_cd, String user_id, String ip_addr) throws Exception {
		if (dtStock != null) {
			// update
			dtStock.setBtchstklg_qty(billed_qty.add(dtStock.getBtchstklg_qty()==null?BigDecimal.ZERO:dtStock.getBtchstklg_qty()));
			dtStock.setBtchstklg_value(dtStock.getBtchstklg_qty().multiply(rate));
			dtStock.setBtchstklg_mod_dt(new Date());
			dtStock.setBtchstklg_mod_usr_id(user_id);
			dtStock.setBtchstklg_mod_ip_add(ip_addr);
			this.transactionalRepository.update(dtStock);
		}else{
			// insert
			dtStock = new BatchStockLog();
			dtStock.setBtchstklg_fin_year(fin_year);
			dtStock.setBtchstklg_batch_id(sendBatch.getBatch_id());
			dtStock.setBtchstklg_date(trf_dt);
			dtStock.setBtchstklg_div_id(product_Master.getSmp_std_div_id());
			dtStock.setBtchstklg_loc_id(send_loc_id);
			dtStock.setBtchstklg_prod_id(product_Master.getSmp_prod_id());
			dtStock.setBtchstklg_tran_type(tran_type);
			dtStock.setBtchstklg_qty(billed_qty);
			dtStock.setBtchstklg_value(billed_qty.multiply(rate));
			dtStock.setBtchstklg_ins_dt(new Date());
			dtStock.setBtchstklg_ins_usr_id(user_id);
			dtStock.setBtchstklg_ins_ip_add(ip_addr);
			dtStock.setBtchstklg_mod_usr_id("");
			dtStock.setBtchstklg_mod_ip_add("");
			dtStock.setBtchstklg_status("A");
			this.transactionalRepository.persist(dtStock);
		}
		
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void createbatchNS(SmpBatchNS sendBatch, String tran_date, Long loc_id, String user_id, String ip_addr)
			throws Exception {
		SmpBatchNS smpBatch = new SmpBatchNS();
		smpBatch.setBatch_id(sendBatch.getBatch_id());
		smpBatch.setStock_type(sendBatch.getStock_type());
		smpBatch.setBatch_prod_id(sendBatch.getBatch_prod_id());
		smpBatch.setBatch_loc_id(loc_id);
		smpBatch.setBatch_no(sendBatch.getBatch_no());
		smpBatch.setBatch_mfg_dt(sendBatch.getBatch_mfg_dt());
		smpBatch.setBatch_expiry_dt(sendBatch.getBatch_expiry_dt());
		smpBatch.setBatch_physical_stock(0L);
		smpBatch.setBatch_narration(sendBatch.getBatch_narration().isEmpty() ? "": sendBatch.getBatch_narration());
		smpBatch.setBatch_mfg_loc_id(sendBatch.getBatch_mfg_loc_id());
		smpBatch.setBatch_rate(sendBatch.getBatch_rate());
		smpBatch.setBatch_costing_rate(sendBatch.getBatch_costing_rate() == null ? BigDecimal.ZERO
				: sendBatch.getBatch_costing_rate());
		smpBatch.setBatch_mktg_rate(sendBatch.getBatch_mktg_rate()== null ? BigDecimal.ZERO
				: sendBatch.getBatch_mktg_rate());
		smpBatch.setBatch_nrv(sendBatch.getBatch_nrv() == null ? BigDecimal.ZERO
				: sendBatch.getBatch_nrv());
		smpBatch.setBatch_display_rate(sendBatch.getBatch_display_rate() == null ? BigDecimal.ZERO
				: sendBatch.getBatch_display_rate());
		smpBatch.setBatch_open_stock(0L);
		smpBatch.setBatch_in_stock(BigDecimal.ZERO);
		smpBatch.setBatch_out_stock(BigDecimal.ZERO);
		smpBatch.setBatch_exclude_loc("N");
		smpBatch.setBatch_exclude_PARTY("N");
		smpBatch.setBatch_with_held_qty(BigDecimal.ZERO);
		smpBatch.setBatch_erp_batch_cd(sendBatch.getBatch_no());
		smpBatch.setBatch_ins_usr_id(user_id);
		smpBatch.setBatch_mod_usr_id("");
		smpBatch.setBatch_ins_dt(new Date());
		smpBatch.setBatch_ins_ip_add(ip_addr);
		smpBatch.setBatch_mod_ip_add("");
		smpBatch.setBatch_status("A");
		this.transactionalRepository.persist(smpBatch);
 	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void batchUpdateNS(SmpBatchNS obatch, BigDecimal sold_qty) throws Exception {
		BigDecimal batch_out_stk = obatch.getBatch_out_stock() == null ? BigDecimal.ZERO : obatch.getBatch_out_stock();
		obatch.setBatch_out_stock(batch_out_stk.add(sold_qty));
		this.transactionalRepository.update(obatch);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setDateWiseStockSaleableNS(BatchStockLogNS dtStock, SmpBatchNS sendBatch, SmpItem product_Master,
			String fin_year, Date trf_dt, Long send_loc_id, BigDecimal billed_qty, BigDecimal rate, String tran_type,
			String comp_cd, String user_id, String ip_addr) throws Exception {
		if (dtStock != null) {
			// update
			dtStock.setBtchstklg_qty(billed_qty.add(dtStock.getBtchstklg_qty()==null?BigDecimal.ZERO:dtStock.getBtchstklg_qty()));
			dtStock.setBtchstklg_value(dtStock.getBtchstklg_qty().multiply(rate));
			dtStock.setBtchstklg_mod_dt(new Date());
			dtStock.setBtchstklg_mod_usr_id(user_id);
			dtStock.setBtchstklg_mod_ip_add(ip_addr);
			this.transactionalRepository.update(dtStock);
		}else{
			// insert
			dtStock = new BatchStockLogNS();
			dtStock.setBtchstklg_stock_type(sendBatch.getStock_type());
			dtStock.setBtchstklg_fin_year(fin_year);
			dtStock.setBtchstklg_batch_id(sendBatch.getBatch_id());
			dtStock.setBtchstklg_date(trf_dt);
			dtStock.setBtchstklg_div_id(product_Master.getSmp_std_div_id());
			dtStock.setBtchstklg_loc_id(send_loc_id);
			dtStock.setBtchstklg_prod_id(product_Master.getSmp_prod_id());
			dtStock.setBtchstklg_tran_type(tran_type);
			dtStock.setBtchstklg_qty(billed_qty);
			dtStock.setBtchstklg_value(billed_qty.multiply(rate));
			dtStock.setBtchstklg_ins_dt(new Date());
			dtStock.setBtchstklg_ins_usr_id(user_id);
			dtStock.setBtchstklg_ins_ip_add(ip_addr);
			dtStock.setBtchstklg_mod_usr_id("");
			dtStock.setBtchstklg_mod_ip_add("");
			dtStock.setBtchstklg_status("A");
			this.transactionalRepository.persist(dtStock);
		}
		
		
	}

	@Override
	public List<StockTransferBean> getSTProdDetails(Long header_id, Long sendLocId) throws Exception {
		return stockTransferRepository.getSTProdDetails(header_id, sendLocId);
	}

	@Override
	public List<Stock_Transfer_Header> getDtlByDateAndLoc(Long send_loc_id, Long rec_loc_id, Date date,
			String stkTrfType) throws Exception {
		return stockTransferRepository.getDtlByDateAndLoc(send_loc_id, rec_loc_id, date, stkTrfType);
	}

	@Override
	public List<StockTransferBean> getAddProdListForStkMod(Long sendLocId, String trf_id, Long subCompId,
			String nilGstInd, Long sendStateId, String stockType) throws Exception {
		return stockTransferRepository.getAddProdListForStkMod(sendLocId, trf_id, subCompId, nilGstInd, sendStateId, stockType);
	}

	@Override
	public List<Object> getStockTransferDtForTrfIDAndProdID(Long trfId, Long prodId) throws Exception {
		return stockTransferRepository.getStockTransferDtForTrfIDAndProdID(trfId, prodId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modifyStock(StockTransferBean stockBean) throws Exception {
    	SmpItem product_Master = null;
    	SmpBatch smpBatch = null;
    	SmpBatchNS smpBatchNs = null;
    	Stock_Transfer_Details stObj = null;
    	BatchStockLog odatewise = null;
    	BatchStockLogNS odatewiseNs=null;
    	Location send_location_obj = null;
    	Location recv_location_obj = null;
		String gst_type_ind = null;
		
    	Stock_Transfer_Header ostock = this.stockTransferRepository.getObjectById(stockBean.getHeaderId());
		product_Master = this.productMasterRepository.getObjectById(stockBean.getProductId());
		
		if(stockBean.getSaveMode().equalsIgnoreCase("MODIFY_DETAILS")){
    		send_location_obj = this.locationRepository.getObjectById(stockBean.getSendLocId());// sending loc's state
    		recv_location_obj = this.locationRepository.getObjectById(stockBean.getRecLocation()); // receiving loc's state
    		stockBean.setSendState(send_location_obj.getLoc_state_id());
    		stockBean.setRecState(recv_location_obj.getLoc_state_id());
    		if(stockBean.getGstInd().equalsIgnoreCase("Y")){
    			if(send_location_obj.getGst_req_ind().equalsIgnoreCase("Y")){
    				if(send_location_obj.getLoc_state_id().compareTo(recv_location_obj.getLoc_state_id())==0){
    					gst_type_ind = "G";
    					if(send_location_obj.getGst_reg_no()!="" && send_location_obj.getGst_reg_no().equalsIgnoreCase(recv_location_obj.getGst_reg_no())){
    						stockBean.setApplyZeroGST(true);
    						stockBean.setGst_doc_type("BS");
        					stockBean.setGst_reverse_chg("N");
    					}else if (recv_location_obj.getGst_reg_no()!=null && !recv_location_obj.getGst_reg_no().equalsIgnoreCase("")){
    						stockBean.setGst_doc_type("SN");
        					stockBean.setGst_reverse_chg("N");
    					}else{
    						stockBean.setGst_doc_type("SR");
        					stockBean.setGst_reverse_chg("Y");
    					}
    				}else{
    					gst_type_ind = "I";
    					if(recv_location_obj.getGst_reg_no()!=null && !recv_location_obj.getGst_reg_no().equalsIgnoreCase("")){
        					stockBean.setGst_doc_type("IN");
        					stockBean.setGst_reverse_chg("N");
        				}else{
        					stockBean.setGst_doc_type("IR");
        					stockBean.setGst_reverse_chg("Y");
        				}
    				}
    				String gst_t =  this.calculateGstRepository.getGST_Doc_type_Para_code(stockBean.getStk_trf_tran_type(),stockBean.getGst_doc_type());
        			stockBean.setGst_doc_type(gst_t);
    			}
    		}
    		stockBean.setGstTypeInd(gst_type_ind);
    	}
		
		BigDecimal free_qty=BigDecimal.ZERO;
    	Stock_Transfer_Details stins=null;
    	
    	for(int i=0; i<stockBean.getBatchIds().size(); i++){
    		
    		if(stockBean.getTranType().equals(STK_TRF_SA)) {
        		smpBatch = this.batchMasterRepository.getObjectById(stockBean.getBatchIds().get(i));
    		} else {
    			smpBatchNs = this.batchMasterRepository.getNSObjectByIdAndStocktype(stockBean.getBatchIds().get(i),stockBean.getProductId(),stockBean.getSendLocId(),stockBean.getStockTypeId());
    		}
    		
    		//deleting the old qty from BATCH_MASTER, DB, STOCK_CURR
    		if(stockBean.getHeaderId() > 0 && stockBean.getDetailIds().get(i) > 0){
    			
    			// STOCK_TRANSFER_DETAILS for the original TRF_ID and TRF_SL_NO
    			stObj = this.stockTransferRepository.getDetailById(stockBean.getDetailIds().get(i));
    			
    			
    			if(stockBean.getTranType().equals(STK_TRF_SA)) {
    			batchUpdate(smpBatch, (stObj.getSold_qty().add(stObj.getFree_qty())).negate());
    			
    			 odatewise=this.batchStockLogRepository.getRecordByProdIdBatchIdLocIdTranType(stockBean.getBatchIds().get(i), stockBean.getProductId(),
    					 stockBean.getSendLocId(), 
    					 ostock.getTrf_dt()// stockBean.getTrfDateModify() 
    					 , STK_TRF_SA);
    		
				setDateWiseStockSaleable(odatewise, smpBatch, product_Master, stockBean.getCurrFinYear(), 
							ostock.getTrf_dt()// stockBean.getTrfDateModify()
						 , stockBean.getSendLocId(), stObj.getSold_qty().add(stObj.getFree_qty()),
		        		stockBean.getBatchTrfRates().get(i), STK_TRF_SA,
		        		stockBean.getCompanyCode(),stockBean.getEmpId(),stockBean.getIpAddress());
    			} else {
    				
    				batchUpdateNS(smpBatchNs, (stObj.getSold_qty().add(stObj.getFree_qty())).negate());
					
    				odatewiseNs=this.batchStockLogRepository.getNSRecordByProdIdNew(stockBean.getProductId(),stockBean.getBatchIds().get(i),
    						stockBean.getSendLocId(),
    						ostock.getTrf_dt()//stockBean.getTrfDateModify()
    						, stockBean.getStockTypeId(),STK_TRF_NS);
    				
					setDateWiseStockSaleableNS(odatewiseNs, smpBatchNs, product_Master, stockBean.getCurrFinYear(), 
								ostock.getTrf_dt()//stockBean.getTrfDateModify()
							, stockBean.getSendLocId(), stObj.getSold_qty().add(stObj.getFree_qty()),
			        		stockBean.getBatchTrfRates().get(i), STK_TRF_NS,
			        		stockBean.getCompanyCode(),stockBean.getEmpId(),stockBean.getIpAddress());
    			}
			  	stObj.setTRFDTL_status("I");
			  	stObj.setTRFDTL_mod_dt(new Date());
			  	stObj.setTRFDTL_mod_ip_add(stockBean.getIpAddress());
			  	stObj.setTRFDTL_mod_usr_id(stockBean.getEmpId());
			  	this.transactionalRepository.update(stObj);
			  	
			  //remove values from header
	    		ostock.setGoods_value(ostock.getGoods_value().subtract(stObj.getGoods_value()));
	    		ostock.setTaxable_amt(ostock.getTaxable_amt().subtract(stObj.getTaxable_amt()));					
	    		ostock.setTax_amt_billed(ostock.getTax_amt_billed().subtract(stObj.getTax_amt_billed()));
	    		ostock.setTax_amt_free(ostock.getTax_amt_free().subtract(stObj.getTax_amt_free()));
				ostock.setSgst_bill_amt(ostock.getSgst_bill_amt().subtract(stObj.getSgst_bill_amt()));
				ostock.setCgst_bill_amt(ostock.getCgst_bill_amt().subtract(stObj.getCgst_bill_amt()));
				ostock.setIgst_bill_amt(ostock.getIgst_bill_amt().subtract(stObj.getIgst_bill_amt()));
				ostock.setTotal_free_qty(ostock.getTotal_free_qty().subtract(stObj.getFree_qty()));
    		}
    		//end - deleting the old qty from BATCH_MASTER, DB
    		
    		if(stockBean.getSaveMode().equalsIgnoreCase("MODIFY_DETAILS")){
    			
    			if(stockBean.getBatchBilledQtys().get(i).compareTo(BigDecimal.ZERO)==1){
				  	
				  	stins = new Stock_Transfer_Details();
				  	free_qty=stockBean.getBatchFreeQtys()!=null ? stockBean.getBatchFreeQtys().get(i):BigDecimal.ZERO;
		    		stins.setStockHeader(ostock);
		    		stins.setDiv_id(product_Master.getSmp_std_div_id());
		    		stins.setProd_id(stockBean.getProductId());
		    		stins.setBatch_id(stockBean.getBatchIds().get(i));
		    		stins.setSold_qty(stockBean.getBatchBilledQtys().get(i));
		    		stins.setFree_qty(free_qty);
		    		stins.setRepl_qty(BigDecimal.ZERO);
		    		stins.setRate(stockBean.getBatchTrfRates().get(i));
		    		stins.setCases(BigDecimal.ZERO);
		    		stins.setRate_id(0L);
		    		stins.setTax_id(0L);
		    		stins.setFin_year_id(ostock.getFin_year_id());
		    		stins.setComp_cd(ostock.getComp_cd());
		    		stins.setRate_dt_id(0L);
		    		stins.setFull_shippers(BigDecimal.ZERO);
		    		stins.setLoose_shippers(BigDecimal.ZERO);
		    		if(stockBean.getTranType().equals(STK_TRF_NS)) {
		    		stins.setStock_type(stockBean.getStockTypeId());
		    		stins.setStock_type_id(stockBean.getStockTypeDetId());
		    		}
		    		BigDecimal cgst_rate = BigDecimal.ZERO;
					BigDecimal sgst_rate = BigDecimal.ZERO;
					BigDecimal igst_rate =	BigDecimal.ZERO;
							
					Object[] obj = new Object[12];
		    		if(stockBean.getGstInd().equalsIgnoreCase("Y")){
						
		    			if(!stockBean.getApplyZeroGST()){
							TaxParamBean taxparam =calculateGstRepository.getTaxParamsByStateIdAndProdId(send_location_obj.getLoc_state_id(),stockBean.getProductId());
							obj[0]=taxparam.getProd_code();
						    obj[1]=taxparam.getOutput_tax_param();
						    obj[2]=taxparam.getSt_vat();
						    obj[3]=taxparam.getCst_rt();
						    obj[4]=taxparam.getSurch();
						    obj[5]=taxparam.getIc_chgs();
						    obj[6]=taxparam.getCess();
						    obj[7]=taxparam.getTo_tax();
						    obj[8]=taxparam.getProd_disc();
						    obj[9]=taxparam.getCgst();
						    obj[10]=taxparam.getIgst();
						    obj[11]=taxparam.getSgst();
							cgst_rate = obj[9]!=null?new BigDecimal(obj[9].toString()):BigDecimal.ZERO;
							igst_rate =	obj[10]!=null?new BigDecimal(obj[10].toString()):BigDecimal.ZERO;
							sgst_rate = obj[11]!=null?new BigDecimal(obj[11].toString()):BigDecimal.ZERO;
						}
											
						TaxCalculationBean invoice_Posting = this.calculateGstService.generateGstValues(
								DISPATCH, 					//TODO need to confirm this
								stockBean.getGstTypeInd(),							//gst_type_ind
								stockBean.getBatchBilledQtys().get(i), 		//billedQty
								free_qty, 								//freeQty
								BigDecimal.ZERO,						//replQty
								stockBean.getBatchTrfRates().get(i),				//rate
								BigDecimal.ZERO,						//party discount
								obj[1].toString(),						//Out_Tax_Param,
								null, 									//In_Tax_Param
								cgst_rate,								//cgst_rate
								igst_rate,								//igst_rate
								sgst_rate, 								//sgst_rate
								BigDecimal.ZERO,						//Surch_Rate
								BigDecimal.ZERO,						//Cess_Rate
								BigDecimal.ZERO,						//TOT_Rate
								BigDecimal.ZERO,						//Prod_Disc
								BigDecimal.ZERO,						//Octroi_Rate
								"000",									//Cust_Type
								BigDecimal.ZERO, 						//mrp_diff
								BigDecimal.ZERO,						//trade discount
								stockBean.getCompanyCode(),								//Comp cd
								BigDecimal.ZERO,						//mrp rate
								BigDecimal.ZERO,
								BigDecimal.ZERO);						//SS RATE
						
						//set gst variables in Stock transfer detail model
						stins.setGoods_value(invoice_Posting.getGoods_value());
						stins.setTaxable_amt(invoice_Posting.getTaxable_amt_billed());
						stins.setTax_amt_billed(invoice_Posting.getTaxable_amt_billed());
						stins.setTax_amt_free(invoice_Posting.getTaxable_amt_free());
						stins.setTax_param(obj[1].toString());
						stins.setSgst_rate(invoice_Posting.getSgst_rate());
						stins.setSgst_bill_amt(invoice_Posting.getSgst_bill_amount());
						stins.setCgst_rate(invoice_Posting.getCgst_rate());
						stins.setCgst_bill_amt(invoice_Posting.getCgst_bill_amount());
						stins.setIgst_rate(invoice_Posting.getIgst_rate());
						stins.setIgst_bill_amt(invoice_Posting.getIgst_bill_amount());
						stins.setVat_cst_ind(ostock.getVat_cst_ind());
						stins.setGst_reverse_chg(ostock.getGst_reverse_chg());
						stins.setGst_doc_type(ostock.getGst_doc_type());
						stins.setHsn_code(product_Master.getHsn_code());
		    		}
		    		
			    	this.transactionalRepository.persist(stins);
		    	
		    		ostock.setGoods_value(ostock.getGoods_value().add(stins.getGoods_value()));
		    		ostock.setTaxable_amt(ostock.getTaxable_amt().add(stins.getTaxable_amt()));					
		    		ostock.setTax_amt_billed(ostock.getTax_amt_billed().add(stins.getTax_amt_billed()));
		    		ostock.setTax_amt_free(ostock.getTax_amt_free().add(stins.getTax_amt_free()));
					ostock.setSgst_bill_amt(ostock.getSgst_bill_amt().add(stins.getSgst_bill_amt()));
					ostock.setCgst_bill_amt(ostock.getCgst_bill_amt().add(stins.getCgst_bill_amt()));
					ostock.setIgst_bill_amt(ostock.getIgst_bill_amt().add(stins.getIgst_bill_amt()));
					
					//TODO FREE_GOODS_STN
					
					if(stockBean.getFreeGoodsStn().equalsIgnoreCase("Y")){
						if(free_qty.compareTo(BigDecimal.ZERO)==1 ||
								stockBean.getBatchTrfRates().get(i).compareTo(BigDecimal.ZERO)<=0){
							if(ostock.getFree_separate_challan().equalsIgnoreCase("N")){
								//only one time
								Trans_Key_Master trans_key_master =this.transKeyMasterRepository.getTransObj(stockBean.getSendLocId(), 
										stockBean.getCurrFinYear(), stockBean.getCompanyCode(), Long.valueOf(stockBean.getStk_trf_tran_type()), "FR");
								if(trans_key_master == null)
									throw new MedicoException("Trans Key Master not mapped");
								long currno = Long.parseLong(trans_key_master.getLast_num());
								ostock.setFree_dispatch_no(trans_key_master.getPrefix().trim()+ stockBean.getCurrFinYear().substring(2) + String.format("%05d", currno));
								ostock.setFree_separate_challan("Y");
								//update trans_key_master
								trans_key_master.setLast_num(String.format("%015d", currno+1l));
								this.transactionalRepository.update(trans_key_master);
							}
							ostock.setTotal_free_qty(ostock.getTotal_free_qty().add(free_qty));
						}
					}
					
					
					if(stockBean.getTranType().equals(STK_TRF_SA)) {
						
					batchUpdate(smpBatch, stins.getSold_qty().add(stins.getFree_qty()));
					
					odatewise=this.batchStockLogRepository.getRecordByProdIdBatchIdLocIdTranType(stockBean.getBatchIds().get(i), stockBean.getProductId(),
	    					 stockBean.getSendLocId(),
	    					 ostock.getTrf_dt()//stockBean.getTrfDateModify(),
	    					 ,STK_TRF_SA);
	    		
					setDateWiseStockSaleable(odatewise, smpBatch, product_Master, stockBean.getCurrFinYear(), 
							 ostock.getTrf_dt()// stockBean.getTrfDateModify()
							 , stockBean.getSendLocId(), stObj.getSold_qty().add(stObj.getFree_qty()).negate(),
			        		stockBean.getBatchTrfRates().get(i), STK_TRF_SA,
			        		stockBean.getCompanyCode(),stockBean.getEmpId(),stockBean.getIpAddress());
					
					} else {
		    				
		    				batchUpdateNS(smpBatchNs, stins.getSold_qty().add(stins.getFree_qty()));
							
		    				odatewiseNs=this.batchStockLogRepository.getNSRecordByProdIdNew(stockBean.getProductId(),stockBean.getBatchIds().get(i),
		    						stockBean.getSendLocId(), 
		    						ostock.getTrf_dt()//stockBean.getTrfDateModify()
		    						, stockBean.getStockTypeId(),STK_TRF_NS);
		    				
							setDateWiseStockSaleableNS(odatewiseNs, smpBatchNs, product_Master, stockBean.getCurrFinYear(), 
									ostock.getTrf_dt()//stockBean.getTrfDateModify()
									, stockBean.getSendLocId(), stObj.getSold_qty().add(stObj.getFree_qty()).negate(),
					        		stockBean.getBatchTrfRates().get(i), STK_TRF_NS,
					        		stockBean.getCompanyCode(),stockBean.getEmpId(),stockBean.getIpAddress());
		    			}
					
    			}
    			
    		}
    		if(ostock.getTotal_free_qty().compareTo(BigDecimal.ZERO)==0){
    			ostock.setFree_dispatch_no("");
    			ostock.setFree_separate_challan("N");
    		}
    		this.transactionalRepository.update(ostock);
    	}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteStockTrf(StockTransferBean stockBean) throws Exception {
		Stock_Transfer_Header ostock = this.stockTransferRepository.getObjectById(stockBean.getHeaderId());
	   	List<Long> prodList = this.stockTransferRepository.getDistProdForTrfId(ostock.getTrf_id());
	   	for(Long prod_id : prodList){
			deleteStockTransferDtForProd(ostock.getTrf_id(), prod_id, "Y", ostock,stockBean);
		}
		ostock.setCancelled("Y");
		ostock.setTrfhdr_status("I");
		ostock.setTrfhdr_mod_dt(new Date());
		ostock.setTrfhdr_mod_ip_add(stockBean.getIpAddress());
		ostock.setTrfhdr_mod_usr_id(stockBean.getEmpId());
		this.transactionalRepository.update(ostock);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteStockTransferDtForProd(Long trf_id, Long prod_id, String fullDelete, Stock_Transfer_Header ostock,StockTransferBean stockBean) throws Exception {
		SmpItem product_Master = null;
		SmpBatch batchMaster = null;
		BatchStockLog odatewise = null;
		SmpBatchNS smpBatchNs = null;
    	BatchStockLogNS odatewiseNs=null;
		List<Stock_Transfer_Details> stdtList=this.stockTransferRepository.deleteStockTransferDtForTrfIDAndProdID(trf_id, prod_id);
		
		for(Stock_Transfer_Details stObj : stdtList){
			product_Master = this.productMasterRepository.getObjectById(stObj.getProd_id());
			
			if(stockBean.getTranType().equals(STK_TRF_SA)) {
				
			batchMaster = this.batchMasterRepository.getObjectById(stObj.getBatch_id());
			if(batchMaster==null)
				throw new MedicoException("Batch Not Found");
			
			batchUpdate(batchMaster, (stObj.getSold_qty().add(stObj.getFree_qty())).negate());
			
			 odatewise=this.batchStockLogRepository.getRecordByProdIdBatchIdLocIdTranType(stObj.getBatch_id(), stObj.getProd_id(),
					 ostock.getLoc_id(), ostock.getTrf_dt(), STK_TRF_SA);
		
			setDateWiseStockSaleable(odatewise, batchMaster, product_Master, ostock.getFin_year_id().toString(), 
					ostock.getTrf_dt(), ostock.getLoc_id(), stObj.getSold_qty().add(stObj.getFree_qty()),
					 stObj.getRate(), STK_TRF_SA,
					 ostock.getComp_cd(),stockBean.getEmpId(),stockBean.getIpAddress());
			
			} else {
				smpBatchNs = this.batchMasterRepository.getNSObjectByIdAndStocktype(stObj.getBatch_id(),stObj.getProd_id(),ostock.getLoc_id(),stObj.getStock_type());
				if(smpBatchNs==null)
					throw new MedicoException("Batch Not Found");
				
				batchUpdateNS(smpBatchNs, (stObj.getSold_qty().add(stObj.getFree_qty())).negate());
				
				odatewiseNs=this.batchStockLogRepository.getNSRecordByProdIdNew(stObj.getProd_id(),stObj.getBatch_id(),
						ostock.getLoc_id(), ostock.getTrf_dt(), stObj.getStock_type(),STK_TRF_NS);
				
				setDateWiseStockSaleableNS(odatewiseNs, smpBatchNs, product_Master, ostock.getFin_year_id().toString(), 
						ostock.getTrf_dt(), ostock.getLoc_id(), stObj.getSold_qty().add(stObj.getFree_qty()),
						stObj.getRate(), STK_TRF_NS,
						ostock.getComp_cd(),stockBean.getEmpId(),stockBean.getIpAddress());
			}
			
			if(!fullDelete.equalsIgnoreCase("Y")) {
				ostock.setFull_shippers(BigDecimal.ZERO);
			  	ostock.setLoose_shippers(BigDecimal.ZERO);
			  	ostock.setWeight(BigDecimal.ZERO);
			  	ostock.setVolume(BigDecimal.ZERO);
    		}
			
			stObj.setTRFDTL_mod_dt(new Date());
    		stObj.setTRFDTL_mod_ip_add(stockBean.getIpAddress());
    		stObj.setTRFDTL_mod_usr_id(stockBean.getEmpId());
    		stObj.setTRFDTL_status("I");
			this.transactionalRepository.update(stObj);
		}
		
		if(!fullDelete.equalsIgnoreCase("Y")) {
			this.transactionalRepository.update(ostock);
		}
	}

	@Override
	public List<StockTransferBean> getSTProdDetailsForDelete(Long header_id, Long sendLocId) throws Exception {
		return stockTransferRepository.getSTProdDetailsForDelete(header_id, sendLocId);
	}

	@Override
	public List<Object> getStockTransferHdForLREntry(Long rec_loc_id, Long send_loc_id, String year_flag,
			Long fin_year_id, String comp_cd) throws Exception {
		return stockTransferRepository.getStockTransferHdForLREntry(rec_loc_id, send_loc_id, year_flag, fin_year_id, comp_cd);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveStkTrfLr(StockTransferBean bean) throws Exception {
		Stock_Transfer_Header stk_header = null;
		for(int i=0;i<bean.getHeaderIds().size();i++) {
			stk_header=this.stockTransferRepository.getObjectById(bean.getHeaderIds().get(i));
			stk_header.setLr_no(bean.getLrnumber());
    		stk_header.setLr_dt(Utility.convertStringtoDate(bean.getLrdate()));
    		stk_header.setLorry_no(bean.getLorryNo());
    		stk_header.setRoad_permit(bean.getRoadPermitNo());
    		stk_header.setF_form_no("NA");
    		stk_header.setFull_shippers(bean.getFullShippers().get(i));
    		stk_header.setLoose_shippers(bean.getLoosePacks().get(i));
    		stk_header.setTransporter_name(bean.getTransporter());
    		stk_header.setTransport_mode(bean.getMot());
    		stk_header.setConsign_type(bean.getConsType());
    		stk_header.setDriver_name(bean.getDriverName());
    		stk_header.setDriver_telno(bean.getDriverPhoneNo());
    		stk_header.setWeight(bean.getWeights().get(i));
    		stk_header.setAuto_grn_auth("0");
    		stk_header.setTrfhdr_mod_ip_add(bean.getIpAddress());
    		stk_header.setTrfhdr_mod_dt(new Date());
			this.transactionalRepository.update(stk_header);
			bean.setHeaderId(bean.getHeaderIds().get(i));
			bean.setTrfDate(Utility.convertDatetoString(stk_header.getTrf_dt()));
			if(bean.getStock_at_cfa()) {
				this.stockTransferRepository.grnInTransitStkTrf(bean);
			}
		}
	}

	

}
