package com.excel.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excel.bean.TaxParamBean;
import com.excel.model.GstDocType;
import com.excel.model.GstDocType_;

@Repository
public class CalculateGstRepositoryImpl implements CalculateGstRepository{
	
	@Autowired EntityManagerFactory emf;
	
	
	@Override
	public TaxParamBean getTaxParamsByStateIdAndProdId(Long state_id, Long prod_id) throws Exception {
    	//System.out.println("stste_id::::::::::::::"+state_id+":::::::::::::::"+prod_id);

		System.out.println("state_id "+state_id+" prod_id "+prod_id);
		EntityManager em = null;
		TaxParamBean taxParam = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer(" SELECT pwtm.prod_code as prod_code,tm.output_tax_param as output_tax_param, tm.st_vat as st_vat , tm.cst_rt as cst_rt, tm.surch as surch,");
			sb.append("tm.ic_chgs as ic_chgs, tm.cess as cess, tm.to_tax as to_tax, ISNULL(pwtm.prod_disc,0) AS prod_disc, ");
			sb.append(" ISNULL(tm.cgst,0) AS cgst, ISNULL(tm.igst,0) AS igst, ISNULL(tm.sgst,0) AS sgst, tm.tax_id as tax_id ");
			sb.append(" FROM ProductwiseTaxMaster pwtm,TaxMaster tm, SmpItem prod ");
			sb.append(" WHERE pwtm.state_id =:state_id AND prod.smp_prod_id =:prod_id AND pwtm.prod_code = prod.smp_prod_cd AND tm.tax_cd = pwtm.tax_cd ");
			Query query = em.createQuery(sb.toString(),Tuple.class);
			query.setParameter("state_id", state_id);
			query.setParameter("prod_id", prod_id);
			List<Tuple> tuples = query.getResultList();
			
			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					taxParam = new TaxParamBean();
					taxParam.setProd_code(t.get("prod_code",String.class));
					taxParam.setOutput_tax_param(t.get("output_tax_param",String.class));
					taxParam.setSt_vat(t.get("st_vat",BigDecimal.class));
					taxParam.setCst_rt(t.get("cst_rt",BigDecimal.class));
					taxParam.setSurch(t.get("surch",BigDecimal.class));
					taxParam.setSurch(t.get("ic_chgs",BigDecimal.class));
					taxParam.setSurch(t.get("cess",BigDecimal.class));
					taxParam.setSurch(t.get("to_tax",BigDecimal.class));
					taxParam.setSurch(t.get("prod_disc",BigDecimal.class));
					taxParam.setCgst(t.get("cgst",BigDecimal.class));
					taxParam.setIgst(t.get("igst",BigDecimal.class));
					taxParam.setSgst(t.get("sgst",BigDecimal.class));
					taxParam.setTax_id(t.get("tax_id",Long.class));
					
				}
			if(taxParam != null)
				return taxParam;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return taxParam;
	}
	
	@Override
	public String getGST_Doc_type_Para_code(String tran_type, String gst_doc_type) throws Exception {
		EntityManager em = null;
		List<GstDocType> list = null;
		String para_code = "";
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<GstDocType> query = builder.createQuery(GstDocType.class);
			Root<GstDocType> root = query.from(GstDocType.class);
			query.multiselect(root.get(GstDocType_.gst_type), root.get(GstDocType_.para_code)).where(
					builder.and(builder.equal(root.get(GstDocType_.tran_type), tran_type)),
					builder.and(builder.equal(root.get(GstDocType_.gst_type), gst_doc_type))
					);
			list = em.createQuery(query).getResultList();
		
			if(list.size()>0)
				para_code = list.get(0).getPara_code();
		} finally {
			if(em != null) { em.close(); }
		}
		return para_code;
	}
}
