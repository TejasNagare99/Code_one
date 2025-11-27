package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.TransporterBean;
import com.excel.model.Company;
import com.excel.model.HQ_Master;
import com.excel.model.Transporter_master;
import com.excel.repository.LrEntryRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;

@Service
public class TransporterMasterServiceImpl implements TransporterMasterService,MedicoConstants{
	@Autowired LrEntryRepository lrEntryRepository;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired
	private CompanyMasterService companyMasterService;
	@Override
	public String saveTransDetail(TransporterBean transBean, String empId, HttpServletRequest request,
			String companyCode) throws Exception {
		List<Tuple> list = null;
		
		String ip_addr = request.getRemoteAddr();
		Transaction txn=null;
		String trans_code;
		String msg = null;
		try {
			String cfa_to_stk = request.getServletContext().getAttribute(CFA_TO_STK_IND).toString();
			cfa_to_stk = cfa_to_stk.trim();
			
			Company company = companyMasterService.getCompanyDetails();
		if(transBean.getEntryModify().equals("Entry")) {
			list= lrEntryRepository.getlistsize(transBean);
			if(list.size()==0) {
		Transporter_master tran_Master =new Transporter_master();
		tran_Master.setTrans_company(companyCode);
		tran_Master.setTransporter_name(transBean.getTranName());
		tran_Master.setTransporter_alter_name(transBean.getAltName());
		tran_Master.setTrans_ins_usr_id(empId);
		tran_Master.setTrans_ins_dt(new Date());
		tran_Master.setTrans_mod_dt(null);
		tran_Master.setTrans_ins_ip_add(ip_addr);
		System.out.println("ip_addr::::"+ip_addr);
		tran_Master.setTrans_pers_resp1(transBean.getRepName());
		tran_Master.setTrans_pers_resp2(transBean.getAltRepName());
		tran_Master.setTrans_email1(transBean.getEmail());
		tran_Master.setTrans_email2(transBean.getAltEmail());
		tran_Master.setTrans_contact_no1(transBean.getContNum());
		tran_Master.setTrans_contact_no2(transBean.getAltContNum());
		tran_Master.setTrans_website_name(transBean.getWebName());
		tran_Master.setTrans_gst_reg_no(transBean.getGst_reg_no());
		
		if(cfa_to_stk.equalsIgnoreCase("Y"))
		{
			tran_Master.setLoc_id(transBean.getLoc_id());			
		}else {
			tran_Master.setLoc_id("0");		
		}
		
		this.transactionalRepository.persist(tran_Master);

		System.out.println("geenrated transporter id is :"+tran_Master.getTrans_id());
		trans_code=String.format("%03d",tran_Master.getTrans_id());
		System.out.println("new trans code is: "+trans_code);
		tran_Master.setTrans_code(trans_code);
		tran_Master.setTrans_status("A");
		this.transactionalRepository.persist(tran_Master);
		msg = "Successfully Saved";
			}
			else {
				msg = "Transporter Name Already Exist";
			}
		}
		else {

		//	list= lrEntryRepository.getlistsize(transBean);
		//	if(list.size()==0) {
			Transporter_master tran_Master =this.lrEntryRepository.getByObjectId(Long.valueOf(transBean.getTranId()));
			tran_Master.setTransporter_name(transBean.getTranName());
			tran_Master.setTransporter_alter_name(transBean.getAltName());
		//	tran_Master.setTrans_ins_usr_id(empId);
		//	tran_Master.setTrans_ins_dt(new Date());
		//	tran_Master.setTrans_ins_ip_add(ip_addr);
			tran_Master.setTrans_mod_dt(new Date());
			tran_Master.setTrans_mod_ip_add(ip_addr);
			tran_Master.setTrans_mod_usr_id(empId);
			tran_Master.setTrans_pers_resp1(transBean.getRepName());
			tran_Master.setTrans_pers_resp2(transBean.getAltRepName());
			tran_Master.setTrans_email1(transBean.getEmail());
			tran_Master.setTrans_email2(transBean.getAltEmail());
			tran_Master.setTrans_contact_no1(transBean.getContNum());
			tran_Master.setTrans_contact_no2(transBean.getAltContNum());
			tran_Master.setTrans_website_name(transBean.getWebName());
			tran_Master.setTrans_gst_reg_no(transBean.getGst_reg_no());
			
			if(cfa_to_stk.equalsIgnoreCase("Y"))
			{
				tran_Master.setLoc_id(transBean.getLoc_id());			
			}else {
				tran_Master.setLoc_id("0");		
			}					
			
//			tran_Master.setLoc_id(transBean.getLo?c_id());
			this.transactionalRepository.update(tran_Master);

			System.out.println("geenrated transporter id is :"+tran_Master.getTrans_id());
			trans_code=String.format("%03d",tran_Master.getTrans_id());
			System.out.println("new trans code is: "+trans_code);
			tran_Master.setTrans_code(trans_code);
			this.transactionalRepository.update(tran_Master);
			msg = "Modified Successfully";
//			}
//			else {
//				msg = "Transporter Name Already Exist";
//			}
		}
		}
		catch (Exception e) {
			throw e;
		}
		return msg;
	}
	@Override
	public List<Transporter_master> gettransportermaster_new() throws Exception {
		// TODO Auto-generated method stub
		return lrEntryRepository.gettransportermaster_new();
	}
	@Override
	public List<Transporter_master> gettransportermasterForLocation(Long loc_id) throws Exception {
		return lrEntryRepository.gettransportermasterForLocation(loc_id);
	}
	
	

}
