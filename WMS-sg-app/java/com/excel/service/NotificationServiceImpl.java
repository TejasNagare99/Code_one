package com.excel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.NotificationRequestBean;
import com.excel.model.Resign_Notification;
import com.excel.repository.NotificationRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;
@Service
public class NotificationServiceImpl implements NotificationService,MedicoConstants{

	@Autowired private NotificationRepository notificationrepository;
	@Autowired SendMail sendMail;
	@Override
	public List<Resign_Notification> getNotifications(NotificationRequestBean bean) throws Exception {
			List<Resign_Notification> resignFieldstaffs = this.notificationrepository.getResignFieldstaffList(bean.getUsername(), bean.getCompanyCode());
		
			return resignFieldstaffs;
	}
	@Override
	public List<Resign_Notification> getGrnDiscard(NotificationRequestBean bean) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb=new StringBuffer();
			
			sb.append(" Select 'GRN' DocType , gh.grn_id,CONVERT( VARCHAR , GH.GRN_DT , 105 ) Date , GH.GRN_NO DocNumber ,");
			sb.append(" S.SUP_NM Name, 'DISCARDED' Status ,");
			sb.append(" gh.GRN_ins_usr_id,RTRIM(HR.emp_fnm)+RTRIM(HR.emp_Mnm)+RTRIM(HR.emp_Lnm) Creator,");
			sb.append(" CONVERT( VARCHAR , GH.GRN_INS_DT , 105 ) CREATED_ON ,");
			sb.append(" GH.GRN_MOD_usr_id,RTRIM(AP.emp_fnm)+RTRIM(AP.emp_Mnm)+RTRIM(AP.emp_Lnm) Last_Approved_By ,");
			sb.append(" CONVERT( VARCHAR , GH.GRN_MOD_DT , 105 ) MODIFIED_ON ,");
			sb.append(" GH.REMARKS");
			sb.append(" FROM GRN_HEADER GH LEFT OUTER JOIN SUPPLIER S ON S.SUP_ID = GH.GRN_SUPPLIER_ID");
			sb.append(" INNER JOIN hr_m_employee HR ON HR.emp_id = GH.GRN_ins_usr_id");
			sb.append(" INNER JOIN hr_m_employee AP ON AP.emp_id = GH.GRN_MOD_usr_id");
			sb.append(" WHERE GH.GRN_APPR_STATUS = 'D'");
			sb.append(" and GH.GRN_ins_usr_id = :usrid");
			sb.append(" order by GH.grn_dt");
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

	public List<Object[]> getGrnNotifications(String emp_id){
		return this.notificationrepository.getGrnNotifications(emp_id);
}

}
