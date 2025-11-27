package com.excel.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Dispatch_Header;
import com.excel.model.DoctorPodMail;
import com.excel.model.StockistPodMail;
import com.excel.repository.DispatchRepository;
import com.excel.repository.DoctorPodRepository;
import com.excel.repository.TransactionalRepository;
@Service
public class DirectToDoctorServiceImpl implements DirectToDoctorService{
	
	@Autowired TransactionalRepository transactionalrepository;
	@Autowired DispatchRepository dispatchrepository;
	@Autowired DoctorPodRepository doctorpodrepository;
	
	@Override
	@Transactional(propagation  = Propagation.REQUIRED,rollbackFor = Exception.class)
	public String saveDoctorResponse(Long dspId, String action) throws Exception {
		// TODO Auto-generated method stub
		Dispatch_Header dsphd = null;
		String msg="";
		try {
//			DoctorPodMail docpod = doctorpodrepository.getDoctorPodByDspId(dspId);
//			if() {
			dsphd = dispatchrepository.getdspHeaderById(dspId);
			if(dsphd == null) {
				throw new Exception("Dispatch Header Not Found .");
			}
			dsphd.setRecd_by_doctor_flg(action);
			dsphd.setRecd_by_doctor_action_dt(new Date());
			transactionalrepository.update(dsphd);
			
			DoctorPodMail docpod = doctorpodrepository.getDoctorPodByDspId(dspId);
			if(docpod==null) {
				throw new Exception("Doctor Log Not Found");
			}
			
			if(docpod.getRecd_by_doctor_flg()==null && docpod.getRecd_by_doctor_action_dt()==null) {
				docpod.setRecd_by_doctor_flg(action);
				docpod.setRecd_by_doctor_action_dt(new Date());
				transactionalrepository.update(docpod);
			}
			else {
				docpod = new DoctorPodMail();
				docpod.setFin_year(Long.valueOf(dsphd.getDsp_fin_year()));
				docpod.setDsp_id(dspId);
				docpod.setEmail_send_to_doctor_date(new Date());
				docpod.setInsert_dt(new Date());
				docpod.setRecd_by_doctor_flg(action);
				docpod.setRecd_by_doctor_action_dt(new Date());
				transactionalrepository.persist(docpod);
			}
			
			msg  = "Your Response Has Been Saved Successfully!";
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			
		}
		return msg;
	}

	@Override
	@Transactional(propagation  = Propagation.REQUIRED,rollbackFor = Exception.class)
	public String saveStockistResponse(Long dspId, String action) throws Exception {
		// TODO Auto-generated method stub
		Dispatch_Header dsphd = null;
		String msg="";
		try {
//			DoctorPodMail docpod = doctorpodrepository.getDoctorPodByDspId(dspId);
//			if() {
			dsphd = dispatchrepository.getdspHeaderById(dspId);
			if(dsphd == null) {
				throw new Exception("Dispatch Header Not Found .");
			}
			dsphd.setRecd_by_doctor_flg(action);
			dsphd.setRecd_by_doctor_action_dt(new Date());
			transactionalrepository.update(dsphd);
			
			StockistPodMail stkpod = doctorpodrepository.getStockistPodByDspId(dspId);
			if(stkpod==null) {
				throw new Exception("Doctor Log Not Found");
			}
			
			if(stkpod.getRecd_by_doctor_flg()==null && stkpod.getRecd_by_doctor_action_dt()==null) {
				stkpod.setRecd_by_doctor_flg(action);
				stkpod.setRecd_by_doctor_action_dt(new Date());
				transactionalrepository.update(stkpod);
			}
			else {
				stkpod = new StockistPodMail();
				stkpod.setFin_year(Long.valueOf(dsphd.getDsp_fin_year()));
				stkpod.setDsp_id(dspId);
				stkpod.setEmail_send_to_doctor_date(new Date());
				stkpod.setInsert_dt(new Date());
				stkpod.setRecd_by_doctor_flg(action);
				stkpod.setRecd_by_doctor_action_dt(new Date());
				transactionalrepository.persist(stkpod);
			}
			
			msg  = "Your Response Has Been Saved Successfully!";
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally {
			
		}
		return msg;
	}

	
}
