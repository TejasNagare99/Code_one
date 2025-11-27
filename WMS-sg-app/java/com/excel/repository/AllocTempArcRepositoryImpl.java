package com.excel.repository;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.Alloc_Temp_Header;
import com.excel.model.Alloc_Temp_Header_Archive;

@Repository
public class AllocTempArcRepositoryImpl implements AllocTempArcRepository{
	@PersistenceContext private EntityManager entityManager;

	
	@Override
	public boolean checkSGPRMDET_TEXT1() throws Exception {
		EntityManager em = null;
		Long count = 0L;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select SGPRMDET_TEXT1\r\n" + "from SG_d_parameters_details sgd\r\n"
					+ "join SG_m_parameters sgm on sgm.sgprm_id = sgd.SGprmdet_SGprm_id\r\n"
					+ "where UPPER(sgm.SGprm_disp_nm) = 'ALLOC_SHEET_STATENAME_REQ_IND'\r\n"
					+ "ORDER BY sgd.SGPRMDET_NM");
			Query query = entityManager.createNativeQuery(sb.toString());
			String res = query.getSingleResult().toString();
			System.out.println("resss  :: " + res);
			if (res.equals("Y")) {
				return true;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return false;
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveArchieve(String isapproved,Long division,String period,String upload_status,String ucycle,Long alloc_temp_hd_id) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" INSERT INTO ALLOC_TEMP_HEADER_ARC select * from ALLOC_TEMP_HEADER");
			buffer.append(" where alloc_temp_hd_id=").append(alloc_temp_hd_id);
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate(); 
			
			buffer = new StringBuffer();
			buffer.append("INSERT INTO ALLOC_TEMP_ARC SELECT FIN_YEAR,PERIOD_CODE,COMPANY,FSCODE, ");
			buffer.append("FSNAME,FSTAFF_ID,DIV_ID,PROD_ID,PROD_CODE,PROD_NAME,ALLOC_TYPE, ");
			buffer.append("ALLOC_CYCLE,ALLOC_RATE,ALLOC_QTY,MSR_STOCK,FINAL_ALLOCQTY,ALT_DIV_ID, ");
			buffer.append("INV_GRP,ins_usr_id,ins_dt,ins_ip_add,status,'");
			buffer.append(isapproved).append("',Alloc_gen_hd_id,upload_cycle,csv_file_name,team_code,ALLOCDTL_ID,ALLOCDTL_ALLOC_ID,mod_usr_id,mod_dt,mod_ip_add");
			buffer.append(" from ALLOC_TEMP");
			buffer.append(" where PERIOD_CODE='").append(period).append("'");
			buffer.append(" and DIV_ID=").append(division);
			buffer.append(" and upload_status='").append(upload_status).append("'");
			buffer.append(" and upload_cycle=").append(ucycle);
			buffer.append(" and alloc_temp_hd_id=").append(alloc_temp_hd_id);
			query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	@Override
	public boolean isFileExist(String file) throws Exception{
		EntityManager em = null;
		Long count=0L;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Select count(csv_file_name) from ALLOC_TEMP_ARC where csv_file_name='").append(file+"'");
			Query query = entityManager.createNativeQuery(sb.toString());
			count = Long.valueOf((Integer) query.getSingleResult());
			if(count>0)
				return true;
		} finally {
			if (em != null) { em.close(); }
		}
		return false;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveArchieveArchive(String isapproved, Long division, String period, String upload_status,
			String ucycle, Long alloc_temp_hd_id,String empId,String ipAdd) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("from Alloc_Temp_Header where alloc_temp_hd_id = :alloctempId");
			Query query = entityManager.createQuery(buffer.toString());
			query.setParameter("alloctempId", alloc_temp_hd_id);
			Alloc_Temp_Header temphdlist = (Alloc_Temp_Header) query.getSingleResult();
			
			Alloc_Temp_Header_Archive hdarc = new Alloc_Temp_Header_Archive();
			hdarc.setAlloc_temp_hd_id(temphdlist.getAlloc_temp_hd_id());
			hdarc.setFin_year(temphdlist.getFin_year());
			hdarc.setCompany(temphdlist.getCompany());
			hdarc.setPeriod_code(temphdlist.getPeriod_code());
			hdarc.setDiv_id(temphdlist.getDiv_id());
			hdarc.setAlloc_type(temphdlist.getAlloc_type());
			hdarc.setCycle(temphdlist.getCycle());
			hdarc.setUpload_type(temphdlist.getUpload_type());
			hdarc.setUpload_cycle(temphdlist.getUpload_cycle());
			hdarc.setApproval_status(temphdlist.getApproval_status());
			hdarc.setAlloc_xgenheader_id(temphdlist.getAlloc_xgenheader_id());
			hdarc.setAlloc_header_id(temphdlist.getAlloc_header_id());
			hdarc.setCsv_file_name(temphdlist.getCsv_file_name());
			hdarc.setIns_usr_id(temphdlist.getIns_usr_id());
			hdarc.setIns_ip_add(temphdlist.getIns_ip_add());
			hdarc.setApp_acquired(temphdlist.getApp_acquired());
			hdarc.setApp_required(temphdlist.getApp_required());
			hdarc.setApproval_remark(temphdlist.getApproval_remark());
			hdarc.setInput_type(temphdlist.getInput_type());
			hdarc.setTeam_code(temphdlist.getTeam_code());
			hdarc.setNew_mod_date(new Date());
			hdarc.setNew_mod_user(empId);
			hdarc.setSrt_number(temphdlist.getSrt_number());
			hdarc.setSrt_date(temphdlist.getSrt_date());
			hdarc.setSrt_remark(temphdlist.getSrt_remark());
			
			entityManager.persist(hdarc);
			
			
			
//			buffer.append(" INSERT INTO ALLOC_TEMP_HEADER_ARCHIVE select * from ALLOC_TEMP_HEADER");
//			buffer.append(" where alloc_temp_hd_id=").append(alloc_temp_hd_id);
//			Query query = entityManager.createNativeQuery(buffer.toString());
//			query.executeUpdate(); 
		//	Date curdt = new Date();		
			buffer = new StringBuffer();
			buffer.append("INSERT INTO ALLOC_TEMP_ARCHIVE SELECT ALLOC_TEMP_ID,FIN_YEAR,PERIOD_CODE,COMPANY,FSCODE, ");
			buffer.append(" FSNAME,FSTAFF_ID,DIV_ID,PROD_ID,PROD_CODE,PROD_NAME,ALLOC_TYPE, ");
			buffer.append(" ALLOC_CYCLE,ALLOC_RATE,ALLOC_QTY,MSR_STOCK,FINAL_ALLOCQTY,ALT_DIV_ID, ");
			buffer.append(" INV_GRP,ins_usr_id,ins_dt,ins_ip_add,status,'");
			buffer.append(isapproved).append("',upload_cycle,Alloc_gen_hd_id,csv_file_name,alloc_Temp_hd_id,'");
			buffer.append(empId).append("',GETDATE(),'',team_code,ALLOCDTL_ID,ALLOCDTL_ALLOC_ID,mod_usr_id,mod_dt,mod_ip_add");
			buffer.append(" from ALLOC_TEMP");
			buffer.append(" where PERIOD_CODE='").append(period).append("'");
			buffer.append(" and DIV_ID=").append(division);
			buffer.append(" and upload_status='").append(upload_status).append("'");
			buffer.append(" and upload_cycle=").append(ucycle);
			buffer.append(" and alloc_temp_hd_id=").append(alloc_temp_hd_id);
			query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
}
