package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.BatchMasterBean;
import com.excel.model.Location;
import com.excel.model.SmpBatch;
import com.excel.model.ViewSmpBatch;
import com.excel.repository.BatchMasterRepository;
import com.excel.repository.ErpRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.TransactionalRepository;
import com.google.gson.Gson;

@Service
public class BatchMasterServiceImpl implements BatchMasterService {
	@Autowired
	BatchMasterRepository batchMasterRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	ErpRepository erpRepository;
	@Autowired
	LocationRepository locationRepository;

	@Override
	public boolean getSmpBatchByBatchNumbar(String batchNumber) throws Exception {
		return this.batchMasterRepository.getSmpBatchByBatchNumbar(batchNumber);

	}

	@Override
	public SmpBatch getObjectById(Long batchId) throws Exception {
		return this.batchMasterRepository.getObjectById(batchId);
	}

	@Override
	public List<SmpBatch> getBatchListByProdId(String prodId) {
		// TODO Auto-generated method stub
		return this.batchMasterRepository.getBatchListByProdId(prodId);
	}

	@Override
	public Boolean checkDuplicateRecordStk(Long rec_loc_id, String batch_no, Long product_id) throws Exception {
		return batchMasterRepository.checkDuplicateRecordStk(rec_loc_id, batch_no, product_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveBatch(BatchMasterBean bean) throws Exception {

		List<Location> locs_without_this_batch = null;
		for (int i = 0; i < bean.getBatchNos().size(); i++) {
			if (!bean.getBatchNos().get(i).trim().equalsIgnoreCase("")) {
				
				locs_without_this_batch = this.locationRepository.getLocationsWithoutBatch(bean.getBatchNos().get(i),
						bean.getProductId(),bean.getLocationId());
				
				
				SmpBatch smpBatch = new SmpBatch();
				smpBatch.setBatch_prod_id(bean.getProductId());
				smpBatch.setBatch_loc_id(bean.getLocationId());
				smpBatch.setBatch_no(bean.getBatchNos().get(i));
				smpBatch.setBatch_mfg_dt(bean.getMfgDates().get(i));
				smpBatch.setBatch_expiry_dt(bean.getExpDates().get(i));
				smpBatch.setBatch_physical_stock(bean.getPhStks().get(i).longValue());
				smpBatch.setBatch_narration(bean.getNarrations().get(i).isEmpty() ? "" : bean.getNarrations().get(i));
				smpBatch.setBatch_mfg_loc_id(bean.getMfgLocs().get(i));
				smpBatch.setBatch_rate(bean.getRates().get(i));
				smpBatch.setBatch_costing_rate(
						bean.getCosRates().get(i) == null ? BigDecimal.ZERO : bean.getCosRates().get(i));
				smpBatch.setBatch_mktg_rate(
						bean.getMkgRates().get(i) == null ? BigDecimal.ZERO : bean.getMkgRates().get(i));
				smpBatch.setBatch_nrv(bean.getNrvs().get(i) == null ? BigDecimal.ZERO : bean.getNrvs().get(i));
				smpBatch.setBatch_display_rate(
						bean.getDisplayRates().get(i) == null ? BigDecimal.ZERO : bean.getDisplayRates().get(i));
				smpBatch.setBatch_open_stock(
						bean.getOpnStks().get(i) == null ? 0 : bean.getOpnStks().get(i).longValue());
				smpBatch.setBatch_in_stock(BigDecimal.ZERO);
				smpBatch.setBatch_out_stock(BigDecimal.ZERO);
				smpBatch.setBatch_exclude_loc("N");
				smpBatch.setBatch_exclude_PARTY("N");
				smpBatch.setBatch_with_held_qty(
						bean.getWthQtys().get(i) == null ? BigDecimal.ZERO : bean.getWthQtys().get(i));
				smpBatch.setBatch_erp_batch_cd(bean.getBatchNos().get(i));
				smpBatch.setBatch_ins_usr_id(bean.getEmpId());
				smpBatch.setBatch_mod_usr_id("");
				smpBatch.setBatch_ins_dt(new Date());
				smpBatch.setBatch_ins_ip_add(bean.getIpAddress());
				smpBatch.setBatch_mod_ip_add("");
				smpBatch.setBatch_status("A");
				this.transactionalRepository.persist(smpBatch);

				//all other locations batch creation will be in seperate transaction so even if it fails bat
				Gson gson = new Gson();
			    SmpBatch deepCopybatch = gson.fromJson(gson.toJson(smpBatch), SmpBatch.class);
				System.out.println("locs_without_this_batch::"+locs_without_this_batch.size());
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
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void createSmpBatchBeanForOtherLcations(SmpBatch smpBatch_original, Long locId) throws Exception {
		// check if batch already exists
		boolean batch_exists = this.checkDuplicateRecordStk(locId,smpBatch_original.getBatch_no(),
				smpBatch_original.getBatch_prod_id());
		System.out.println("batch status "+batch_exists+" for " + locId);
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
	public List<ViewSmpBatch> getBatchListByFilter(String search_by, String param_id, String search_text, int loc_id)
			throws Exception {
		return batchMasterRepository.getBatchListByFilter(search_by, param_id, search_text, loc_id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modifyBatch(BatchMasterBean bean) throws Exception {
		SmpBatch smpBatch = this.batchMasterRepository.getObjectById(bean.getBatchId());
		smpBatch.setBatch_mfg_dt(bean.getMfgDate());
		smpBatch.setBatch_expiry_dt(bean.getExpDate());
		smpBatch.setBatch_mfg_loc_id(bean.getMfgLoc());
		smpBatch.setBatch_rate(bean.getRate());
		smpBatch.setBatch_display_rate(bean.getDisplayRate() == null ? BigDecimal.ZERO : bean.getDisplayRate());
		smpBatch.setBatch_open_stock(bean.getOpnStk() == null ? 0 : bean.getOpnStk().longValue());
		smpBatch.setBatch_with_held_qty(bean.getWthQty() == null ? BigDecimal.ZERO : bean.getWthQty());
		smpBatch.setBatch_physical_stock(bean.getPhStk().longValue());
		smpBatch.setBatch_costing_rate(bean.getCosRate() == null ? BigDecimal.ZERO : bean.getCosRate());
		smpBatch.setBatch_mktg_rate(bean.getMkgRate() == null ? BigDecimal.ZERO : bean.getMkgRate());
		smpBatch.setBatch_nrv(bean.getNrv() == null ? BigDecimal.ZERO : bean.getNrv());
		smpBatch.setBatch_mod_usr_id(bean.getEmpId());
		smpBatch.setBatch_mod_dt(new Date());
		smpBatch.setBatch_mod_ip_add(bean.getIpAddress());
		smpBatch.setBatch_narration(bean.getNarration().isEmpty() ? "" : bean.getNarration());
		this.transactionalRepository.update(smpBatch);
	}

	@Override
	public List<SmpBatch> getBatchListByProdId_and_LocId(String prodId, String emp_loc) {
	
		
		return this.batchMasterRepository .getBatchListByProdId_and_LocId(prodId,emp_loc);
	}
}
