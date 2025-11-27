package com.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.MessageBean;
import com.excel.model.Message;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoResources;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired private TransactionalRepository transactionalRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(MessageBean bean) throws Exception {
//		Message message = new Message();
//		message.setCompany_cd(bean.getCompanyCode());
//		message.setFiletype(bean.getUploadType());
//		message.setFs_id(bean.getEmployeeName());
//		message.setLevel_code(bean.getEmployeeType());
//		message.setMessage(bean.getMessage());
//		//message.setPath();
//		message.setValidfrom(MedicoResources.convert_DD_MM_YYYY_toDate(bean.getStartDate()));
//		message.setValidtill(MedicoResources.convert_DD_MM_YYYY_toDate(bean.getEndDate()));
//		transactionalRepository.persist(message);
	}
	
}
