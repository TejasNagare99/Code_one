package com.excel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.UserBean;
import com.excel.model.EmailFrom;
import com.excel.repository.BrandRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.SendMail;

import net.bytebuddy.utility.RandomString;

@Service
public class BrandServiceImpl implements BrandService , MedicoConstants{
	
	@Autowired
	private BrandRepository brandrepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> saveBrand(String brandName, String emp_Id) throws Exception {
		Map<String, Object> map = new HashMap<>();
		try {

			this.brandrepository.saveBrand(brandName,emp_Id);
			map.put("dataSaved", true);

		} catch (Exception p) {
			p.printStackTrace();
		}

		return map;
	}
	
	@Override
	public List<Object> getBrandList(String prefix) throws Exception {
		return brandrepository.getBrandList(prefix);
	}

}
