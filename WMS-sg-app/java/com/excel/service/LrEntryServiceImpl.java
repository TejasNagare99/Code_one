package com.excel.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.LrEntryBean;
import com.excel.bean.LrEntryDataBean;
import com.excel.bean.Lr_download_Execl_Bean;
import com.excel.bean.TRF_BEAN_stocktransfer_download_trdata;
import com.excel.model.Dispatch_Header;
import com.excel.model.Dispatch_Header_arc;
import com.excel.model.Location;
import com.excel.model.Lr_csv_upload_java_proc_recised_filter;
import com.excel.model.Lrcsv_RevisedDownload;
import com.excel.model.Lrcsv_RevisedDownload_SG;
import com.excel.model.Sum_Disp_Header;
import com.excel.model.Sum_Disp_Header_arc;
import com.excel.model.Transporter_master;
import com.excel.repository.DispatchRepository;
import com.excel.repository.LrEntryRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;

@Service
public class LrEntryServiceImpl implements LrEntryService, MedicoConstants {

	@Autowired
	LrEntryRepository lrEntryRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	DispatchRepository dispatchRepository;

	@Override
	public List<LrEntryDataBean> getLrcsvdownload_Revised_data(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			String role, String user, List<String> loc, List<String> div, List<String> dest, String trans,String cfa_to_stk_ind,String companyCode)
			throws Exception {
		
		
		List<LrEntryDataBean> list = new ArrayList<>();
		LrEntryDataBean bean = null;
		String old_mst_no = "old";
		String new_mst_no = "new";
		try {
			
			List<Lrcsv_RevisedDownload> downloadList = this.lrEntryRepository.getLrcsvdownload_Revised_data(strdate,
					enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind, stk_at_cfa_option, temp, deptloc, loc, div, dest,cfa_to_stk_ind);
			
			
			

			
			
			for (Lrcsv_RevisedDownload detail : downloadList) {
				new_mst_no = detail.getMst_stn_no();

				if (!new_mst_no.equals(old_mst_no)) {
					if (role.equals(ROLE_COUR)) {
						if (detail.getMst_transporter().toUpperCase().trim().equals(user.toUpperCase().trim())) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							if (companyCode.equalsIgnoreCase(PFZ)) {								
								bean.setTransport_mode(detail.getMst_transport_mode());
								bean.setDtl_transport_mode(detail.getDtl_transport_mode());
								
							}
							list.add(bean);
						}
					} else {
						if (trans.equalsIgnoreCase(detail.getMst_transporter())) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							if (companyCode.equalsIgnoreCase(PFZ)) {								
								bean.setTransport_mode(detail.getMst_transport_mode());
								bean.setDtl_transport_mode(detail.getDtl_transport_mode());
							}

							list.add(bean);
						}
						if (trans.equals("All")) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							if (companyCode.equalsIgnoreCase(PFZ)) {								
								bean.setTransport_mode(detail.getMst_transport_mode());
								bean.setDtl_transport_mode(detail.getDtl_transport_mode());
							}
							list.add(bean);
						}
					}
				}

				if (role.equals(ROLE_COUR)) {
					if (detail.getDtl_transporter().toUpperCase().trim().equals(user.toUpperCase().trim())) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
						bean.setTrans_gst_reg_no(detail.getDsp_trans_gst_reg_no());
						bean.setVehNo(detail.getDsp_lorry_no());
						if (companyCode.equalsIgnoreCase(PFZ)) {								
							bean.setTransport_mode(detail.getMst_transport_mode());
							bean.setDtl_transport_mode(detail.getDtl_transport_mode());
						}
						list.add(bean);
					}
				} else {
					if (trans.equalsIgnoreCase(detail.getDtl_transporter())) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
						bean.setTrans_gst_reg_no(detail.getDsp_trans_gst_reg_no());
						bean.setVehNo(detail.getDsp_lorry_no());
						if (companyCode.equalsIgnoreCase(PFZ)) {								
							bean.setTransport_mode(detail.getMst_transport_mode());
							bean.setDtl_transport_mode(detail.getDtl_transport_mode());
						}
						list.add(bean);
					}
					if (trans.equals("All")) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
						bean.setTrans_gst_reg_no(detail.getDsp_trans_gst_reg_no());
						bean.setVehNo(detail.getDsp_lorry_no());
						
						if (companyCode.equalsIgnoreCase(PFZ)) {								
							bean.setTransport_mode(detail.getMst_transport_mode());
							bean.setDtl_transport_mode(detail.getDtl_transport_mode());
						}
						list.add(bean);
					}
				}
				old_mst_no = new_mst_no;
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveLrEntry(LrEntryBean bean) throws Exception {
		System.out.println("Sum dsp id " + bean.getSum_dsp_header_no());
		if (bean.getFinYearFlag().equals(CURRENT)) {

			System.out.println("inside current  " + bean.getFinYearFlag());

			System.out.println("bean.getUserid()  " + bean.getUserid());

			Sum_Disp_Header sumHdr = null;
			Dispatch_Header dspHdr = null;
			if (bean.getIsMaster().equalsIgnoreCase("true")) {
				sumHdr = dispatchRepository.getObjectById(Long.valueOf(bean.getSum_dsp_header_no()));
				sumHdr.setSumdsp_lr_no(bean.getLrNumber());
				sumHdr.setSumdsp_lr_dt(bean.getLrDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
				sumHdr.setSumdsp_delivery_date(bean.getExpectedDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getExpectedDate()));
				sumHdr.setSumdsp_totcases(Integer.valueOf(bean.getCaseNo()));
				sumHdr.setSumdsp_wt(bean.getWeight());
				sumHdr.setSumdsp_totwt(bean.getBillableWeight());
				sumHdr.setCourier_expenses(bean.getLrCharges());
				sumHdr.setActual_delivery_date(bean.getChallanRecDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getChallanRecDate()));
				sumHdr.setSumdsp_recd_by(bean.getChallanRecBy());
				sumHdr.setSumdsp_remark(bean.getRemark());
				sumHdr.setWay_bill_no(bean.getWayBillNumber());
				sumHdr.setSumdsp_transporter(bean.getTransporter());
				sumHdr.setSumdsp_mod_dt(new Date());
				sumHdr.setSumdsp_mod_ip_add(bean.getIpaddress());
				sumHdr.setSumdsp_mod_usr_id(bean.getUserid());
				sumHdr.setSumdsp_destination(bean.getDestination());
				
				//change by nilesh 17 Jan
				if(bean.getCompanyCode().equalsIgnoreCase(PFZ)) {
					sumHdr.setTransport_mode(bean.getTransportMode()); 
			
				}
				this.transactionalRepository.update(sumHdr);
			} else {
				dspHdr = dispatchRepository.getDspHeader(Long.valueOf(bean.getSum_dsp_header_no()),
						bean.getChallanNumber());
				dspHdr.setDsp_lr_no(bean.getLrNumber());
				System.out.println("LR Date " + bean.getLrDate());
				dspHdr.setDsp_lr_dt(bean.getLrDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
				dspHdr.setDsp_delivery_date(bean.getExpectedDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getExpectedDate()));
				dspHdr.setDsp_cases(Integer.valueOf(bean.getCaseNo()));
				dspHdr.setDsp_cases(Integer.valueOf(bean.getCaseNo()));
				dspHdr.setDsp_wt(bean.getWeight());
				dspHdr.setDsp_billable_wt(bean.getBillableWeight());
				dspHdr.setCourier_expenses(bean.getLrCharges());
				dspHdr.setActual_delivery_date(bean.getChallanRecDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getChallanRecDate()));
				dspHdr.setDsp_recd_by(bean.getChallanRecBy());
				dspHdr.setDsp_remark(bean.getRemark());
				dspHdr.setWay_bill_no(bean.getWayBillNumber());
				dspHdr.setDsp_transporter(bean.getTransporter());
				dspHdr.setDsp_trans_gst_reg_no(bean.getTrans_gst_reg_no());
				dspHdr.setDsp_mod_dt(new Date());
				dspHdr.setDsp_mod_ip_add(bean.getIpaddress());
				dspHdr.setDsp_mod_usr_id(bean.getUserid());
				dspHdr.setDsp_lorry_no(bean.getVehNo());
				dspHdr.setDspfstaff_destination(bean.getDestination());
				
				//change by nilesh 17 Jan
				if(bean.getCompanyCode().equalsIgnoreCase(PFZ)) {
				dspHdr.setTransport_mode(bean.getTransportMode());
				}
				this.transactionalRepository.update(dspHdr);
			}
		} else {
			Sum_Disp_Header_arc sumHdr_arc = null;
			Dispatch_Header_arc dspHdr_arc = null;

			System.out.println("inside Arc");

			if (bean.getIsMaster().equalsIgnoreCase("true")) {
				sumHdr_arc = dispatchRepository.getObjectById_and_finyr(Long.valueOf(bean.getSum_dsp_header_no()),
						bean.getFinYear());
				sumHdr_arc.setSumdsp_lr_no(bean.getLrNumber());
				sumHdr_arc.setSumdsp_lr_dt(bean.getLrDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
				sumHdr_arc.setSumdsp_delivery_date(bean.getExpectedDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getExpectedDate()));
				sumHdr_arc.setSumdsp_totcases(Integer.valueOf(bean.getCaseNo()));
				sumHdr_arc.setSumdsp_wt(bean.getWeight());
				sumHdr_arc.setSumdsp_totwt(bean.getBillableWeight());
				sumHdr_arc.setCourier_expenses(bean.getLrCharges());
				sumHdr_arc.setActual_delivery_date(bean.getChallanRecDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getChallanRecDate()));
				sumHdr_arc.setSumdsp_recd_by(bean.getChallanRecBy());
				sumHdr_arc.setSumdsp_remark(bean.getRemark());
				sumHdr_arc.setWay_bill_no(bean.getWayBillNumber());

				sumHdr_arc.setSumdsp_transporter(bean.getTransporter());

				sumHdr_arc.setSumdsp_mod_dt(new Date());
				sumHdr_arc.setSumdsp_mod_ip_add(bean.getIpaddress());
				sumHdr_arc.setSumdsp_mod_usr_id(bean.getUserid());
				sumHdr_arc.setSumdsp_destination(bean.getDestination());
				
				//change by nilesh 17 Jan
				if(bean.getCompanyCode().equalsIgnoreCase(PFZ)) {
					sumHdr_arc.setTransport_mode(bean.getTransportMode()); 
			
				}

				
				this.transactionalRepository.update(sumHdr_arc);
			} else {
				dspHdr_arc = dispatchRepository.getDspHeader_forarc(Long.valueOf(bean.getSum_dsp_header_no()),
						bean.getChallanNumber(), bean.getFinYear());
				dspHdr_arc.setDsp_lr_no(bean.getLrNumber());
				System.out.println("LR Date " + bean.getLrDate());
				dspHdr_arc.setDsp_lr_dt(bean.getLrDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getLrDate()));
				dspHdr_arc.setDsp_delivery_date(bean.getExpectedDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getExpectedDate()));
				dspHdr_arc.setDsp_cases(Integer.valueOf(bean.getCaseNo()));
				dspHdr_arc.setDsp_cases(Integer.valueOf(bean.getCaseNo()));
				dspHdr_arc.setDsp_wt(bean.getWeight());
				dspHdr_arc.setDsp_billable_wt(bean.getBillableWeight());
				dspHdr_arc.setCourier_expenses(bean.getLrCharges());
				dspHdr_arc.setActual_delivery_date(bean.getChallanRecDate().equals("Invalid date") ? null
						: MedicoResources.convert_DD_MM_YYYY_toDate(bean.getChallanRecDate()));
				dspHdr_arc.setDsp_recd_by(bean.getChallanRecBy());
				dspHdr_arc.setDsp_remark(bean.getRemark());
				dspHdr_arc.setWay_bill_no(bean.getWayBillNumber());
				dspHdr_arc.setDsp_transporter(bean.getTransporter());
				dspHdr_arc.setDsp_trans_gst_reg_no(bean.getTrans_gst_reg_no());
				dspHdr_arc.setDsp_mod_dt(new Date());
				dspHdr_arc.setDsp_mod_ip_add(bean.getIpaddress());
				dspHdr_arc.setDsp_mod_usr_id(bean.getUserid());
				dspHdr_arc.setDsp_lorry_no(bean.getVehNo());
				dspHdr_arc.setDspfstaff_destination(bean.getDestination());
				
				//change by nilesh 17 Jan
				if(bean.getCompanyCode().equalsIgnoreCase(PFZ)) {
				dspHdr_arc.setTransport_mode(bean.getTransportMode());
				}
				
				
				this.transactionalRepository.update(dspHdr_arc);
			}
		}
	}

	@Override
	public List<Transporter_master> gettransportermaster() throws Exception {
		// TODO Auto-generated method stub
		return lrEntryRepository.gettransportermaster();
	}
	
	@Override
	public Long getLocIdByUsername(String username) throws Exception{
		return lrEntryRepository.getLocIdByUsername(username);
	}
	
	@Override
	public List<Transporter_master> gettransportermasterForLocation(Long loc_id) throws Exception{
		return lrEntryRepository.gettransportermasterForLocation(loc_id);
	}

	@Override
	public List<LrEntryDataBean> getConfirmationofdispatch_data(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest,String cfa_to_stk_ind) throws Exception {
		List<LrEntryDataBean> list = new ArrayList<>();
		LrEntryDataBean bean = null;
		String old_mst_no = "old";
		String new_mst_no = "new";
		try {
			List<Lrcsv_RevisedDownload> downloadList = this.lrEntryRepository.getLrcsvdownload_Revised_data(strdate,
					enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind, stk_at_cfa_option, temp, deptloc, loc, div, dest,cfa_to_stk_ind);
			System.out.println("Total List : " + downloadList.size());
			long i = 0;
			for (Lrcsv_RevisedDownload detail : downloadList) {
				new_mst_no = detail.getMst_stn_no();

				if (detail.getMst_transporter().equalsIgnoreCase("")) {
					if (!new_mst_no.equals(old_mst_no)) {
						bean = new LrEntryDataBean();
						bean.setSrno(i);
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(true);
						bean.setMaster_stn_no(detail.getMst_stn_no());
						bean.setDate_of_stn(MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
						bean.setTeam_name(detail.getTeam_name());
						bean.setTeam_code(detail.getTeam_code());
						bean.setDestination(detail.getMst_destination());
						bean.setTransporter(detail.getMst_transporter());
						bean.setLr_no(detail.getMst_lr_no());
						bean.setLr_date(detail.getMst_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
						bean.setTotal_no_cases(detail.getMst_tot_case());
						bean.setWay_bill_no(detail.getMst_way_bill_no());
						bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
						bean.setWeight(detail.getMst_grs_wght());
						bean.setBillable_wt(detail.getMst_billwght());
						bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getMst_recd_by());
						bean.setRemark(detail.getMst_courier_remark());
						bean.setLr_charges(detail.getMst_courier_expenses());
						list.add(bean);
						i++;
					}
				}

				if (detail.getDtl_transporter().equalsIgnoreCase("")) {
					bean = new LrEntryDataBean();
					bean.setSrno(i);
					bean.setSum_dsp_hd_no(detail.getSum_hd_id());
					bean.setMaster(false);
					bean.setMaster_stn_no(detail.getDtl_chln_no());
					bean.setDate_of_stn(MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
					bean.setTeam_name(detail.getDspfstaff_displayname());
					bean.setTeam_code(detail.getDspfstaff_employeeno());
					bean.setDestination(detail.getDtl_city());
					bean.setTransporter(detail.getDtl_transporter());
					bean.setLr_no(detail.getDtl_lr_no());
					bean.setLr_date(detail.getDtl_lr_dt() == null ? null
							: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
					bean.setTotal_no_cases(detail.getDtl_tot_case());
					bean.setWay_bill_no(detail.getWay_bill_no());
					bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
							: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
					bean.setWeight(detail.getDtl_stn_grswght());
					bean.setBillable_wt(detail.getDtl_stn_billwght());
					bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
							: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
					bean.setChallan_rec_by(detail.getDtl_recd_by());
					bean.setRemark(detail.getDtl_courier_remark());
					bean.setLr_charges(detail.getDtl_courier_expenses());
					list.add(bean);
					i++;
				}
				old_mst_no = new_mst_no;
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;

	}

	@Override
	public void updateConfirmationdispatch(LrEntryBean bean) throws Exception {
		Sum_Disp_Header sumHdr = null;
		Dispatch_Header dspHdr = null;

		Sum_Disp_Header_arc sumHdr_arc = null;
		Dispatch_Header_arc dspHdr_arc = null;

		System.out.println("bean.getHeaderids() " + bean.getSumheaderids().size());
		System.out.println("bean.getChallannos() " + bean.getChallannos().size());
		System.out.println("bean.getIsmaster() " + bean.getIsmaster().size());

		if (bean.getFinYearFlag().equals(MedicoConstants.CURRENT)) {
			int i = 0;

			for (String asd : bean.getSumheaderids()) {

				if (bean.getIsmaster().get(i).equalsIgnoreCase("true")) {
					sumHdr = dispatchRepository.getObjectById(Long.valueOf(bean.getSumheaderids().get(i)));
					sumHdr.setSumdsp_lr_no(sumHdr.getSumdsp_lr_no());
					sumHdr.setSumdsp_lr_dt(sumHdr.getSumdsp_lr_dt());
					sumHdr.setSumdsp_delivery_date(sumHdr.getSumdsp_delivery_date());
					sumHdr.setSumdsp_totcases(sumHdr.getSumdsp_totcases());
					sumHdr.setSumdsp_wt(sumHdr.getSumdsp_wt());
					sumHdr.setSumdsp_totwt(sumHdr.getSumdsp_totwt());
					sumHdr.setCourier_expenses(sumHdr.getCourier_expenses());
					sumHdr.setActual_delivery_date(sumHdr.getActual_delivery_date());
					sumHdr.setSumdsp_recd_by(sumHdr.getSumdsp_recd_by());
					sumHdr.setSumdsp_remark(sumHdr.getSumdsp_remark());
					sumHdr.setWay_bill_no(sumHdr.getWay_bill_no());

					sumHdr.setSumdsp_transporter(bean.getTransporter());
					this.transactionalRepository.update(sumHdr);

					System.out.println("SumHeader Updated");
				} else {
					dspHdr = dispatchRepository.getDspHeader(Long.valueOf(bean.getSumheaderids().get(i)),
							bean.getChallannos().get(i));
					dspHdr.setDsp_lr_no(dspHdr.getDsp_lr_no());
					dspHdr.setDsp_lr_dt(dspHdr.getDsp_lr_dt());
					dspHdr.setDsp_delivery_date(dspHdr.getDsp_delivery_date());
					dspHdr.setDsp_cases(dspHdr.getDsp_cases());
					dspHdr.setDsp_wt(dspHdr.getDsp_wt());
					dspHdr.setDsp_billable_wt(dspHdr.getDsp_billable_wt());
					dspHdr.setCourier_expenses(dspHdr.getCourier_expenses());
					dspHdr.setActual_delivery_date(dspHdr.getActual_delivery_date());
					dspHdr.setDsp_recd_by(dspHdr.getDsp_recd_by());
					dspHdr.setDsp_remark(dspHdr.getDsp_remark());
					dspHdr.setWay_bill_no(dspHdr.getWay_bill_no());

					dspHdr.setDsp_transporter(bean.getTransporter());
					this.transactionalRepository.update(dspHdr);

					System.out.println("disp_Header Updated");
				}
				i++;
			}
		} else {
			int i = 0;

			for (String asd : bean.getSumheaderids()) {

				if (bean.getIsmaster().get(i).equalsIgnoreCase("true")) {
					sumHdr_arc = dispatchRepository.getObjectById_and_finyr(Long.valueOf(bean.getSumheaderids().get(i)),
							bean.getFinYear());
					sumHdr_arc.setSumdsp_lr_no(sumHdr_arc.getSumdsp_lr_no());
					sumHdr_arc.setSumdsp_lr_dt(sumHdr_arc.getSumdsp_lr_dt());
					sumHdr_arc.setSumdsp_delivery_date(sumHdr_arc.getSumdsp_delivery_date());
					sumHdr_arc.setSumdsp_totcases(sumHdr_arc.getSumdsp_totcases());
					sumHdr_arc.setSumdsp_wt(sumHdr_arc.getSumdsp_wt());
					sumHdr_arc.setSumdsp_totwt(sumHdr_arc.getSumdsp_totwt());
					sumHdr_arc.setCourier_expenses(sumHdr_arc.getCourier_expenses());
					sumHdr_arc.setActual_delivery_date(sumHdr_arc.getActual_delivery_date());
					sumHdr_arc.setSumdsp_recd_by(sumHdr_arc.getSumdsp_recd_by());
					sumHdr_arc.setSumdsp_remark(sumHdr_arc.getSumdsp_remark());
					sumHdr_arc.setWay_bill_no(sumHdr_arc.getWay_bill_no());

					sumHdr_arc.setSumdsp_transporter(bean.getTransporter());
					this.transactionalRepository.update(sumHdr_arc);

					System.out.println("SumHeader Updated");
				} else {
					dspHdr_arc = dispatchRepository.getDspHeader_forarc(Long.valueOf(bean.getSumheaderids().get(i)),
							bean.getChallannos().get(i), bean.getFinYear());
					dspHdr_arc.setDsp_lr_no(dspHdr_arc.getDsp_lr_no());
					dspHdr_arc.setDsp_lr_dt(dspHdr_arc.getDsp_lr_dt());
					dspHdr_arc.setDsp_delivery_date(dspHdr_arc.getDsp_delivery_date());
					dspHdr_arc.setDsp_cases(dspHdr_arc.getDsp_cases());
					dspHdr_arc.setDsp_wt(dspHdr_arc.getDsp_wt());
					dspHdr_arc.setDsp_billable_wt(dspHdr_arc.getDsp_billable_wt());
					dspHdr_arc.setCourier_expenses(dspHdr_arc.getCourier_expenses());
					dspHdr_arc.setActual_delivery_date(dspHdr_arc.getActual_delivery_date());
					dspHdr_arc.setDsp_recd_by(dspHdr_arc.getDsp_recd_by());
					dspHdr_arc.setDsp_remark(dspHdr_arc.getDsp_remark());
					dspHdr_arc.setWay_bill_no(dspHdr_arc.getWay_bill_no());

					dspHdr_arc.setDsp_transporter(bean.getTransporter());
					this.transactionalRepository.update(dspHdr_arc);

					System.out.println("disp_Header Updated");
				}
				i++;
			}
		}
	}

	@Override
	public List<LrEntryDataBean> getLrcsvdownload_Revised_Lrbooking(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			String role, String user, List<String> loc, List<String> div, List<String> dest, String trans,String cfa_to_stk_ind)
			throws Exception {
		List<LrEntryDataBean> list = new ArrayList<>();
		LrEntryDataBean bean = null;
		String old_mst_no = "old";
		String new_mst_no = "new";
		try {
			List<Lrcsv_RevisedDownload> downloadList = this.lrEntryRepository.getLrcsvdownload_Revised_data(strdate,
					enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind, stk_at_cfa_option, temp, deptloc, loc, div, dest,cfa_to_stk_ind);
			for (Lrcsv_RevisedDownload detail : downloadList) {
				new_mst_no = detail.getMst_stn_no();

				if (!new_mst_no.equals(old_mst_no)) {

					if (role.equals(ROLE_COUR)) {
						if (detail.getMst_transporter().toUpperCase().trim().equals(user.toUpperCase().trim())
								&& detail.getMst_lr_no().equalsIgnoreCase("")) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							list.add(bean);
						}
					} else {
						if (detail.getMst_lr_no().equalsIgnoreCase("")) {
							if (trans.equalsIgnoreCase(detail.getMst_transporter())) {
								bean = new LrEntryDataBean();
								bean.setSum_dsp_hd_no(detail.getSum_hd_id());
								bean.setMaster(true);
								bean.setMaster_stn_no(detail.getMst_stn_no());
								bean.setDate_of_stn(
										MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
								bean.setTeam_name(detail.getTeam_name());
								bean.setTeam_code(detail.getTeam_code());
								bean.setDestination(detail.getMst_destination());
								bean.setTransporter(detail.getMst_transporter());
								bean.setLr_no(detail.getMst_lr_no());
								bean.setLr_date(detail.getMst_lr_dt() == null ? null
										: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
								bean.setTotal_no_cases(detail.getMst_tot_case());
								bean.setWay_bill_no(detail.getMst_way_bill_no());
								bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
										: MedicoResources.convertUtilDateToString_DD_MM_YYYY(
												detail.getMst_tent_delivery_date()));
								bean.setWeight(detail.getMst_grs_wght());
								bean.setBillable_wt(detail.getMst_billwght());
								bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
										: MedicoResources.convertUtilDateToString_DD_MM_YYYY(
												detail.getMst_actual_delivery_date()));
								bean.setChallan_rec_by(detail.getMst_recd_by());
								bean.setRemark(detail.getMst_courier_remark());
								bean.setLr_charges(detail.getMst_courier_expenses());
								list.add(bean);
							}

							if (trans.equals("All")) {
								bean = new LrEntryDataBean();
								bean.setSum_dsp_hd_no(detail.getSum_hd_id());
								bean.setMaster(true);
								bean.setMaster_stn_no(detail.getMst_stn_no());
								bean.setDate_of_stn(
										MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
								bean.setTeam_name(detail.getTeam_name());
								bean.setTeam_code(detail.getTeam_code());
								bean.setDestination(detail.getMst_destination());
								bean.setTransporter(detail.getMst_transporter());
								bean.setLr_no(detail.getMst_lr_no());
								bean.setLr_date(detail.getMst_lr_dt() == null ? null
										: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
								bean.setTotal_no_cases(detail.getMst_tot_case());
								bean.setWay_bill_no(detail.getMst_way_bill_no());
								bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
										: MedicoResources.convertUtilDateToString_DD_MM_YYYY(
												detail.getMst_tent_delivery_date()));
								bean.setWeight(detail.getMst_grs_wght());
								bean.setBillable_wt(detail.getMst_billwght());
								bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
										: MedicoResources.convertUtilDateToString_DD_MM_YYYY(
												detail.getMst_actual_delivery_date()));
								bean.setChallan_rec_by(detail.getMst_recd_by());
								bean.setRemark(detail.getMst_courier_remark());
								bean.setLr_charges(detail.getMst_courier_expenses());
								list.add(bean);
							}
						}
					}
				}

				if (role.equals(ROLE_COUR)) {
					if (detail.getDtl_transporter().toUpperCase().trim().equals(user.toUpperCase().trim())
							&& detail.getDtl_lr_no().equalsIgnoreCase("")) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
						list.add(bean);
					}
				} else {
					if (detail.getDtl_lr_no()==null || detail.getDtl_lr_no().equalsIgnoreCase("")) {
						if (trans.equalsIgnoreCase(detail.getDtl_transporter())) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(false);
							bean.setMaster_stn_no(detail.getDtl_chln_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
							bean.setTeam_name(detail.getDspfstaff_displayname());
							bean.setTeam_code(detail.getDspfstaff_employeeno());
							bean.setDestination(detail.getDtl_city());
							bean.setTransporter(detail.getDtl_transporter());
							bean.setLr_no(detail.getDtl_lr_no());
							bean.setLr_date(detail.getDtl_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
							bean.setTotal_no_cases(detail.getDtl_tot_case());
							bean.setWay_bill_no(detail.getWay_bill_no());
							bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
							bean.setWeight(detail.getDtl_stn_grswght());
							bean.setBillable_wt(detail.getDtl_stn_billwght());
							bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getDtl_recd_by());
							bean.setRemark(detail.getDtl_courier_remark());
							bean.setLr_charges(detail.getDtl_courier_expenses());
							list.add(bean);
						}

						if (trans.equals("All")) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(false);
							bean.setMaster_stn_no(detail.getDtl_chln_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
							bean.setTeam_name(detail.getDspfstaff_displayname());
							bean.setTeam_code(detail.getDspfstaff_employeeno());
							bean.setDestination(detail.getDtl_city());
							bean.setTransporter(detail.getDtl_transporter());
							bean.setLr_no(detail.getDtl_lr_no());
							bean.setLr_date(detail.getDtl_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
							bean.setTotal_no_cases(detail.getDtl_tot_case());
							bean.setWay_bill_no(detail.getWay_bill_no());
							bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
							bean.setWeight(detail.getDtl_stn_grswght());
							bean.setBillable_wt(detail.getDtl_stn_billwght());
							bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getDtl_recd_by());
							bean.setRemark(detail.getDtl_courier_remark());
							bean.setLr_charges(detail.getDtl_courier_expenses());
							list.add(bean);
						}
					}
				}
				old_mst_no = new_mst_no;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;

	}

	@Override
	public List<Lr_csv_upload_java_proc_recised_filter> getLrcsvdownload_Revised_filter(String strdate, String enddate,
			String tbl_ind, String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp,
			String deptloc, String cfa_to_stk_ind) throws Exception {
		// TODO Auto-generated method stub

		return lrEntryRepository.getLrcsvdownload_Revised_filter(strdate, enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind,
				stk_at_cfa_option, temp, deptloc, cfa_to_stk_ind);
	}

	@Override
	public Location getLocationNameByEmployeeId(String eMP_ID) {

		return lrEntryRepository.getLocationNameByEmployeeId(eMP_ID);
	}

	@Override
	public String getDataForGenExel(List<String> trf_id) throws IOException, ParseException {

		List<Lr_download_Execl_Bean> list = lrEntryRepository.getDataForGenExel(trf_id);
		return genarateExcel(list);
	}

	private String genarateExcel(List<Lr_download_Execl_Bean> list) throws IOException, ParseException {

		String authCode = "";

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();

		XSSFRow roww7 = sheet.createRow(0);

		XSSFCellStyle row7CellStyle = workbook.createCellStyle();

		row7CellStyle.setFillBackgroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
		row7CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		row7CellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		row7CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		row7CellStyle.setAlignment(HorizontalAlignment.CENTER);
		row7CellStyle.setLocked(true);
		row7CellStyle.setWrapText(true);

		XSSFCellStyle row8CellStyle = workbook.createCellStyle();

		row8CellStyle.setBorderTop(BorderStyle.THIN);
		row8CellStyle.setBorderRight(BorderStyle.THIN);
		row8CellStyle.setBorderBottom(BorderStyle.THIN);
		row8CellStyle.setBorderLeft(BorderStyle.THIN);

		row7CellStyle.setBorderTop(BorderStyle.THIN);
		row7CellStyle.setBorderRight(BorderStyle.THIN);
		row7CellStyle.setBorderBottom(BorderStyle.THIN);
		row7CellStyle.setBorderLeft(BorderStyle.THIN);

		addStringCell(roww7, 0, row7CellStyle, "CSPTRF_ID ");
		addStringCell(roww7, 1, row7CellStyle, "CSPTRF_SLNO ");
		addStringCell(roww7, 2, row7CellStyle, "CSPTRF_SENDING_LOC");
		addStringCell(roww7, 3, row7CellStyle, "CSPTRF_SENDLOC_CD");

		addStringCell(roww7, 4, row7CellStyle, "CSPTRF_RECV_LOC ");
		addStringCell(roww7, 5, row7CellStyle, "CSPTRF_RECVLOC_CD ");

		addStringCell(roww7, 6, row7CellStyle, "CSPTRF_TRF_NO ");
		addStringCell(roww7, 7, row7CellStyle, "CSPTRF_TRF_DATE  ");
		addStringCell(roww7, 8, row7CellStyle, "CSPTRF_PROD_CD  ");
		addStringCell(roww7, 9, row7CellStyle, "CSPTRF_BATCH_NO  ");

		addStringCell(roww7, 10, row7CellStyle, "CSPTRF_GIFTIND ");
		addStringCell(roww7, 11, row7CellStyle, "CSPTRF_MFGDT ");
		addStringCell(roww7, 12, row7CellStyle, "CSPTRF_EXPIRY_DT  ");
		addStringCell(roww7, 13, row7CellStyle, "CSPTRF_QTY  ");
		addStringCell(roww7, 14, row7CellStyle, "CSPTRF_NOCASES ");
		addStringCell(roww7, 15, row7CellStyle, "CSPTRF_FULLSHIPPER  ");
		addStringCell(roww7, 16, row7CellStyle, " CSPTRF_TRFRATE ");
		addStringCell(roww7, 17, row7CellStyle, " CSPTRF_COGRATE ");

		addStringCell(roww7, 18, row7CellStyle, "CSPTRF_REF_NO ");
		addStringCell(roww7, 19, row7CellStyle, "CSPTRF_LRNO ");
		addStringCell(roww7, 20, row7CellStyle, "CSPTRF_LR_DATE  ");
		addStringCell(roww7, 21, row7CellStyle, "CSPTRF_MFG_MAP_CD  ");
		addStringCell(roww7, 22, row7CellStyle, "CSPTRF_PONUMBER ");
		addStringCell(roww7, 23, row7CellStyle, "CSPTRF_PROCESSED  ");
		addStringCell(roww7, 24, row7CellStyle, "CSPTRF_ERR_MSG  ");
		addStringCell(roww7, 25, row7CellStyle, "CSPTRF_RMPMRATE  ");

		addStringCell(roww7, 26, row7CellStyle, "CSPTRF_ExciseRATE Number");
		addStringCell(roww7, 27, row7CellStyle, "CSPTRF_OverheadRATE Date");

//		
//		addStringCell(roww7, 28, row7CellStyle, "CSPTRF_ins_usr_id ");
//		addStringCell(roww7, 29, row7CellStyle, "CSPTRF_mod_usr_id  ");
//		addStringCell(roww7, 30, row7CellStyle, "CSPTRF_ins_dt  ");
//		addStringCell(roww7, 31, row7CellStyle, "CSPTRF_mod_dt  ");	
//		addStringCell(roww7, 32, row7CellStyle, "CSPTRF_ins_ip_add ");
//		addStringCell(roww7, 33, row7CellStyle, "CSPTRF_mod_ip_add ");
//		addStringCell(roww7, 34, row7CellStyle, "CSPTRF_status ");

		addStringCell(roww7, 28, row7CellStyle, "AUTH_CODE ");
		addStringCell(roww7, 29, row7CellStyle, "TRANSPORTER_NAME ");

		int rowCount = 1;

		for (Lr_download_Execl_Bean data : list) {

			XSSFRow row = sheet.createRow(rowCount++);
			addLongCell(row, 0, row8CellStyle, data.getCSPTRF_ID());
			addLongCell(row, 1, row8CellStyle, data.getCSPTRF_SLNO());
			addStringCell(row, 2, row8CellStyle, data.getCSPTRF_SENDING_LOC());
			addStringCell(row, 3, row8CellStyle, data.getCSPTRF_SENDLOC_CD());

			addStringCell(row, 4, row8CellStyle, data.getCSPTRF_RECV_LOC());
			addStringCell(row, 5, row8CellStyle, data.getCSPTRF_RECVLOC_CD());

			addStringCell(row, 6, row8CellStyle, data.getCSPTRF_TRF_NO());

			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(data.getCSPTRF_TRF_DATE().substring(0, 10));
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String CSPTRF_TRF_DATE = dateFormat.format(date1);

			addStringCell(row, 7, row8CellStyle, CSPTRF_TRF_DATE);
			addStringCell(row, 8, row8CellStyle, data.getCSPTRF_PROD_CD());
			addStringCell(row, 9, row8CellStyle, data.getCSPTRF_BATCH_NO());
			addStringCell(row, 10, row8CellStyle, data.getCSPTRF_GIFTIND());

			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(data.getCSPTRF_MFGDT().substring(0, 10));
			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			String CSPTRF_MFGDT = dateFormat.format(date2);

			addStringCell(row, 11, row8CellStyle, CSPTRF_MFGDT);

			Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse(data.getCSPTRF_EXPIRY_DT().substring(0, 10));
			DateFormat dateFormat3 = new SimpleDateFormat("dd/MM/yyyy");
			String CSPTRF_EXPIRY_DT = dateFormat.format(date3);

			addStringCell(row, 12, row8CellStyle, CSPTRF_EXPIRY_DT);
			addDoubleCell(row, 13, row8CellStyle, data.getCSPTRF_QTY());
			addLongCell(row, 14, row8CellStyle, data.getCSPTRF_NOCASES());
			addLongCell(row, 15, row8CellStyle, data.getCSPTRF_FULLSHIPPER());
			addDoubleCell(row, 16, row8CellStyle, data.getCSPTRF_TRFRATE());
			addDoubleCell(row, 17, row8CellStyle, data.getCSPTRF_COGRATE());

			addStringCell(row, 18, row8CellStyle, data.getCSPTRF_REF_NO());
			addStringCell(row, 19, row8CellStyle, data.getCSPTRF_LRNO());

			Date date4 = new SimpleDateFormat("yyyy-MM-dd").parse(data.getCSPTRF_LR_DATE().substring(0, 10));
			DateFormat dateFormat4 = new SimpleDateFormat("dd/MM/yyyy");
			String CSPTRF_LR_DATE = dateFormat.format(date4);

			addStringCell(row, 20, row8CellStyle, CSPTRF_LR_DATE);

			addStringCell(row, 21, row8CellStyle, data.getCSPTRF_MFG_MAP_CD());
			addStringCell(row, 22, row8CellStyle, data.getCSPTRF_PONUMBER());
			addStringCell(row, 23, row8CellStyle, data.getCSPTRF_PROCESSED());
			addStringCell(row, 24, row8CellStyle, data.getCSPTRF_ERR_MSG());
			addDoubleCell(row, 25, row8CellStyle, data.getCSPTRF_RMPMRATE());

			addDoubleCell(row, 26, row8CellStyle, data.getCSPTRF_ExciseRATE());
			addDoubleCell(row, 27, row8CellStyle, data.getCSPTRF_OverheadRATE());

			// sendingl+recloc+currentdate
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormatAuth = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss ");
			String strDate = dateFormatAuth.format(date);
			authCode = strDate.replace("-", "");
			authCode = authCode.replace(":", "");
			authCode = authCode.replace(" ", "");

			System.err.println(authCode);

			addStringCell(row, 28, row8CellStyle, authCode);
			addStringCell(row, 29, row8CellStyle, data.getTRANSPORTER_NAME());
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);
		sheet.autoSizeColumn(12);
		sheet.autoSizeColumn(13);
		sheet.autoSizeColumn(14);
		sheet.autoSizeColumn(15);
		sheet.autoSizeColumn(16);
		sheet.autoSizeColumn(17);
		sheet.autoSizeColumn(18);
		sheet.autoSizeColumn(19);
		sheet.autoSizeColumn(20);
		sheet.autoSizeColumn(21);
		sheet.autoSizeColumn(22);
		sheet.autoSizeColumn(23);
		sheet.autoSizeColumn(24);
		sheet.autoSizeColumn(25);
		sheet.autoSizeColumn(26);
		sheet.autoSizeColumn(27);
		sheet.autoSizeColumn(28);
		sheet.autoSizeColumn(29);
		sheet.protectSheet("");

		StringBuffer path = null;

		long l = new Date().getTime();
		String filename = "Stock_Transfer_LR_reports" + l + ".xlsx";
		String filePath = MedicoConstants.REPORT_FILE_PATH;
		path = new StringBuffer(filePath).append("\\");
		path.append(filename);

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path.toString());
			workbook.write(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			out.close();
			workbook.close();
		}

		return filename;

	}

	public static XSSFCell addStringCell(XSSFRow row, int columnIndex, XSSFCellStyle cellStyle, String value) {
		XSSFCell cell = row.createCell(columnIndex);
		cell.setCellStyle(cellStyle);

		if (value == null) {

			value = "";
		}

		cell.setCellValue(value);
		return cell;
	}

	public static XSSFCell addDoubleCell(XSSFRow row, int columnIndex, XSSFCellStyle cellStyle, BigDecimal value) {
		XSSFCell cell = row.createCell(columnIndex);
		cell.setCellStyle(cellStyle);

		if (value == null) {

			value = new BigDecimal(0);
		}
		cell.setCellValue(value.doubleValue());
		return cell;
	}

	public static XSSFCell addLongCell(XSSFRow row, int columnIndex, XSSFCellStyle cellStyle, Long value) {
		XSSFCell cell = row.createCell(columnIndex);
		cell.setCellStyle(cellStyle);

		if (value == null) {

			value = 0L;
		}
		cell.setCellValue(value.doubleValue());
		return cell;
	}

	@Override
	public List<Tuple> getRecevingLocations(String startDate, String endDate, String location_id, String ind,String finyear_flage) {

		return lrEntryRepository.getRecevingLocations(startDate, endDate, location_id, ind,finyear_flage);

	}

	@Override
	public List<Tuple> getgetDataForStockkTransferLrEntry(String startDate, String endDate, List<String> rec_loc,
			String sendLocation, String finyear_flage) {

		StringBuffer reclocations = null;
		System.out.println(rec_loc.size());
		System.out.println(rec_loc);

		if (rec_loc.size() == 1) {
			reclocations = new StringBuffer();
			reclocations.append(rec_loc.get(0));
		} else if (rec_loc.size() > 1) {

			reclocations = new StringBuffer();

			for (int i = 0; i <= rec_loc.size() - 1; i++) {

				if ("0".equals(rec_loc.get(i).toString())) {

					reclocations = new StringBuffer("0");
				} else {

					if (i != rec_loc.size() - 1) {
						reclocations.append(rec_loc.get(i) + ",");
						System.err.println(reclocations + "reclocations ");
					} else {
						reclocations.append(rec_loc.get(i));
					}

				}
				System.err.println(reclocations + "reclocations ");
			}
		}

		return lrEntryRepository.getDataForStockkTransferLrEntry(startDate, endDate, reclocations, sendLocation,finyear_flage);

	}

	@Override
	public List<TRF_BEAN_stocktransfer_download_trdata> getStockTransferList(String startDate, String endDate,
			String locationId, List<String> destinationlist) {

		return lrEntryRepository.getStockTransferList(startDate, endDate, locationId, destinationlist);

	}

	@Override
	public int saveDataForStockTransferLr(LrEntryBean bean) throws Exception {

		return lrEntryRepository.saveDataForStockTransferLr(bean);

	}

	@Override
	public String getDpLocId(String loc_id) throws Exception {
		// TODO Auto-generated method stub
		return lrEntryRepository.getDpLocId(loc_id);
	}

	@Override
	public List<LrEntryDataBean> getLrcsvdownload_Revised_data_for_sg(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			String role, String user, List<String> loc, List<String> div, List<String> dest, String trans,
			String cfa_to_stk_ind, String companyCode) throws Exception {
		
		
		

		List<LrEntryDataBean> list = new ArrayList<>();
		LrEntryDataBean bean = null;
		String old_mst_no = "old";
		String new_mst_no = "new";
		try {
			
			List<Lrcsv_RevisedDownload_SG> downloadList = this.lrEntryRepository.getLrcsvdownload_Revised_data_for_sg(strdate,
					enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind, stk_at_cfa_option, temp, deptloc, loc, div, dest,cfa_to_stk_ind);
			
			
			

			
			
			for (Lrcsv_RevisedDownload_SG detail : downloadList) {
				new_mst_no = detail.getMst_stn_no();

				if (!new_mst_no.equals(old_mst_no)) {
					if (role.equals(ROLE_COUR)) {
						if (detail.getMst_transporter().toUpperCase().trim().equals(user.toUpperCase().trim())) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							
							if (companyCode.equalsIgnoreCase(PFZ)) {								
							bean.setTransport_mode(detail.getMst_transport_mode());
							bean.setDtl_transport_mode(detail.getDtl_transport_mode());
							System.out.println("transport_mode:::"+ detail.getMst_transport_mode());
							System.out.println("detail transport_mode:::"+ detail.getDtl_transport_mode());
							}
							list.add(bean);
						}
					} else {
						if (trans.equalsIgnoreCase(detail.getMst_transporter())) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							
							if (companyCode.equalsIgnoreCase(PFZ)) {								
							bean.setTransport_mode(detail.getMst_transport_mode());
							bean.setDtl_transport_mode(detail.getDtl_transport_mode());
							}
							
							list.add(bean);
						}
						if (trans.equals("All")) {
							bean = new LrEntryDataBean();
							bean.setSum_dsp_hd_no(detail.getSum_hd_id());
							bean.setMaster(true);
							bean.setMaster_stn_no(detail.getMst_stn_no());
							bean.setDate_of_stn(
									MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
							bean.setTeam_name(detail.getTeam_name());
							bean.setTeam_code(detail.getTeam_code());
							bean.setDestination(detail.getMst_destination());
							bean.setTransporter(detail.getMst_transporter());
							bean.setLr_no(detail.getMst_lr_no());
							bean.setLr_date(detail.getMst_lr_dt() == null ? null
									: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
							bean.setTotal_no_cases(detail.getMst_tot_case());
							bean.setWay_bill_no(detail.getMst_way_bill_no());
							bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
							bean.setWeight(detail.getMst_grs_wght());
							bean.setBillable_wt(detail.getMst_billwght());
							bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
									: MedicoResources
											.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
							bean.setChallan_rec_by(detail.getMst_recd_by());
							bean.setRemark(detail.getMst_courier_remark());
							bean.setLr_charges(detail.getMst_courier_expenses());
							
							if (companyCode.equalsIgnoreCase(PFZ)) {								
							bean.setTransport_mode(detail.getMst_transport_mode());
							bean.setDtl_transport_mode(detail.getDtl_transport_mode());
							}
							list.add(bean);
						}
					}
				}

				if (role.equals(ROLE_COUR)) {
					if (detail.getDtl_transporter().toUpperCase().trim().equals(user.toUpperCase().trim())) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
//						bean.setTrans_gst_reg_no(detail.getDsp_trans_gst_reg_no());
//						bean.setVehNo(detail.getDsp_lorry_no());
						
						if (companyCode.equalsIgnoreCase(PFZ)) {								
						bean.setTransport_mode(detail.getMst_transport_mode());
						bean.setDtl_transport_mode(detail.getDtl_transport_mode());
						}
						list.add(bean);
					}
				} else {
					if (trans.equalsIgnoreCase(detail.getDtl_transporter())) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
//						bean.setTrans_gst_reg_no(detail.getDsp_trans_gst_reg_no());
//						bean.setVehNo(detail.getDsp_lorry_no());
						
						if (companyCode.equalsIgnoreCase(PFZ)) {								
						bean.setTransport_mode(detail.getMst_transport_mode());
						bean.setDtl_transport_mode(detail.getDtl_transport_mode());
						}	
						list.add(bean);
					}
					if (trans.equals("All")) {
						bean = new LrEntryDataBean();
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(false);
						bean.setMaster_stn_no(detail.getDtl_chln_no());
						bean.setDate_of_stn(
								MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
						bean.setTeam_name(detail.getDspfstaff_displayname());
						bean.setTeam_code(detail.getDspfstaff_employeeno());
						bean.setDestination(detail.getDtl_city());
						bean.setTransporter(detail.getDtl_transporter());
						bean.setLr_no(detail.getDtl_lr_no());
						bean.setLr_date(detail.getDtl_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
						bean.setTotal_no_cases(detail.getDtl_tot_case());
						bean.setWay_bill_no(detail.getWay_bill_no());
						bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
						bean.setWeight(detail.getDtl_stn_grswght());
						bean.setBillable_wt(detail.getDtl_stn_billwght());
						bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getDtl_recd_by());
						bean.setRemark(detail.getDtl_courier_remark());
						bean.setLr_charges(detail.getDtl_courier_expenses());
//						bean.setTrans_gst_reg_no(detail.getDsp_trans_gst_reg_no());
//						bean.setVehNo(detail.getDsp_lorry_no());
						
						if (companyCode.equalsIgnoreCase(PFZ)) {								
						bean.setTransport_mode(detail.getMst_transport_mode());
						bean.setDtl_transport_mode(detail.getDtl_transport_mode());
						}
						list.add(bean);
					}
				}
				old_mst_no = new_mst_no;
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;

	}
	
	@Override
	public List<LrEntryDataBean> getConfirmationofdispatch_data_for_sg(String strdate, String enddate, String tbl_ind,
			String cfa, String fsid, String stock_at_cfa_ind, String stk_at_cfa_option, String temp, String deptloc,
			List<String> loc, List<String> div, List<String> dest, String cfa_to_stk_ind) throws Exception {
		List<LrEntryDataBean> list = new ArrayList<>();
		LrEntryDataBean bean = null;
		String old_mst_no = "old";
		String new_mst_no = "new";
		try {
			List<Lrcsv_RevisedDownload_SG> downloadList = this.lrEntryRepository.getLrcsvdownload_Revised_data_for_sg(strdate,
					enddate, tbl_ind, cfa, fsid, stock_at_cfa_ind, stk_at_cfa_option, temp, deptloc, loc, div, dest,cfa_to_stk_ind);
			System.out.println("Total List : " + downloadList.size());
			long i = 0;
			for (Lrcsv_RevisedDownload_SG detail : downloadList) {
				new_mst_no = detail.getMst_stn_no();

				if (detail.getMst_transporter().equalsIgnoreCase("")) {
					if (!new_mst_no.equals(old_mst_no)) {
						bean = new LrEntryDataBean();
						bean.setSrno(i);
						bean.setSum_dsp_hd_no(detail.getSum_hd_id());
						bean.setMaster(true);
						bean.setMaster_stn_no(detail.getMst_stn_no());
						bean.setDate_of_stn(MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_stn_dt()));
						bean.setTeam_name(detail.getTeam_name());
						bean.setTeam_code(detail.getTeam_code());
						bean.setDestination(detail.getMst_destination());
						bean.setTransporter(detail.getMst_transporter());
						bean.setLr_no(detail.getMst_lr_no());
						bean.setLr_date(detail.getMst_lr_dt() == null ? null
								: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getMst_lr_dt()));
						bean.setTotal_no_cases(detail.getMst_tot_case());
						bean.setWay_bill_no(detail.getMst_way_bill_no());
						bean.setExp_delivery_date(detail.getMst_tent_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getMst_tent_delivery_date()));
						bean.setWeight(detail.getMst_grs_wght());
						bean.setBillable_wt(detail.getMst_billwght());
						bean.setChallan_rec_date(detail.getMst_actual_delivery_date() == null ? null
								: MedicoResources
										.convertUtilDateToString_DD_MM_YYYY(detail.getMst_actual_delivery_date()));
						bean.setChallan_rec_by(detail.getMst_recd_by());
						bean.setRemark(detail.getMst_courier_remark());
						bean.setLr_charges(detail.getMst_courier_expenses());
						list.add(bean);
						i++;
					}
				}

				if (detail.getDtl_transporter().equalsIgnoreCase("")) {
					bean = new LrEntryDataBean();
					bean.setSrno(i);
					bean.setSum_dsp_hd_no(detail.getSum_hd_id());
					bean.setMaster(false);
					bean.setMaster_stn_no(detail.getDtl_chln_no());
					bean.setDate_of_stn(MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_chln_dt()));
					bean.setTeam_name(detail.getDspfstaff_displayname());
					bean.setTeam_code(detail.getDspfstaff_employeeno());
					bean.setDestination(detail.getDtl_city());
					bean.setTransporter(detail.getDtl_transporter());
					bean.setLr_no(detail.getDtl_lr_no());
					bean.setLr_date(detail.getDtl_lr_dt() == null ? null
							: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_lr_dt()));
					bean.setTotal_no_cases(detail.getDtl_tot_case());
					bean.setWay_bill_no(detail.getWay_bill_no());
					bean.setExp_delivery_date(detail.getDtl_tent_delivery_date() == null ? null
							: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_tent_delivery_date()));
					bean.setWeight(detail.getDtl_stn_grswght());
					bean.setBillable_wt(detail.getDtl_stn_billwght());
					bean.setChallan_rec_date(detail.getDtl_actual_delivery_date() == null ? null
							: MedicoResources.convertUtilDateToString_DD_MM_YYYY(detail.getDtl_actual_delivery_date()));
					bean.setChallan_rec_by(detail.getDtl_recd_by());
					bean.setRemark(detail.getDtl_courier_remark());
					bean.setLr_charges(detail.getDtl_courier_expenses());
					list.add(bean);
					i++;
				}
				old_mst_no = new_mst_no;
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		return list;

	}
	
	

}
