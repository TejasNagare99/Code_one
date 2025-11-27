package com.excel.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.excel.bean.ProductBean;
import com.excel.bean.ProductwiseTaxMasterBean;
import com.excel.model.ProductwiseTaxMaster;
import com.excel.model.SmpItem;
import com.excel.repository.ProductwiseTaxMasterRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.CodifyErrors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ProductwiseTaxMasterServiceImpl implements ProductwiseTaxMasterService{
	@Autowired TransactionalRepository transactionalaRepository;
	@Autowired ProductwiseTaxMasterRepository productwiseTaxMasterRepository;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public ProductwiseTaxMaster getObjectById(String prodCode) throws Exception {
		return this.entityManager.find(ProductwiseTaxMaster.class, prodCode);
	}
	@Override
	public Boolean checkDetail(String prodCode,String Company) {
		return this.productwiseTaxMasterRepository.checkDetail(prodCode, Company);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Object saveProduct(@RequestBody ProductwiseTaxMasterBean pwtb,HttpSession session,List<Object> stateIdList, List<Object> taxCodeList) {
		Map<String, Object> map = new HashMap<String, Object>();
		Boolean dataSaved = false;
		// Boolean dataPresent=false;
		try {
			ProductwiseTaxMaster pwtm = null;
			for (int i = 0; i < stateIdList.size(); i++) {
				// check
				// true get
				pwtm = this.productwiseTaxMasterRepository.checkData(pwtb.getProdCode(), stateIdList.get(i).toString(),
						taxCodeList.get(i).toString());
				boolean isfound = true;
				System.err.println("isfound:::"+isfound);
				if(pwtm==null) {
					isfound = false;
					pwtm = new ProductwiseTaxMaster();
				}
				pwtm.setCompany_cd(pwtb.getCompany());
				pwtm.setDeleted("N");
				pwtm.setLast_mod_by(pwtb.getEmpId());
				pwtm.setLast_mod_date(new Date());
				pwtm.setOctroi_input_param("NNNNN");
				pwtm.setOctroi_output_param("NNNNN");
				pwtm.setOld_tax_cd("");
				pwtm.setProd_code(pwtb.getProdCode() == null ? "" : pwtb.getProdCode());
				pwtm.setProd_disc(BigDecimal.ZERO);
				pwtm.setState_id(stateIdList.get(i) == null ? 0 : Long.valueOf(stateIdList.get(i).toString()));
				System.out.println("..." + taxCodeList.get(i).toString());
				pwtm.setTax_cd(taxCodeList.get(i) == null ? "" : taxCodeList.get(i).toString());
				pwtm.setTax_class_id(0);
				pwtm.setTax_date(new Date());

				if (isfound) {
					this.transactionalaRepository.update(pwtm);
				} else {
					this.transactionalaRepository.persist(pwtm);
				}

			}
			dataSaved = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" + e.getMessage());// uncomment asneeded --;
		}
		map.put("dataSaved", dataSaved);
		return map;
	}
}
