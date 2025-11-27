package com.excel.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.stylesheets.MediaList;

import com.excel.bean.Hsn_and_Tax_Bean;
import com.excel.bean.ProductBean;
import com.excel.bean.ProductMasterBean;
import com.excel.model.FieldStaff;
import com.excel.model.FieldStaff_;
import com.excel.model.GrnHeader;
import com.excel.model.SG_d_parameters_details;
import com.excel.model.SmpItem;
import com.excel.model.SmpItem_;
import com.excel.model.TaxMaster;
import com.excel.model.V_SmpItem;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class ProductMasterRepositoryImpl implements ProductMasterRepository,MedicoConstants{
	
	@Autowired EntityManagerFactory emf;
	
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public SmpItem getObjectById(Long smpProdId) throws Exception {
		return this.entityManager.find(SmpItem.class, smpProdId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmpItem> getProductListForGRN(String companyCode,
			Long subCompId, Long prodType, Long prodGroup, Long divId) throws Exception {
		System.out.println("companyCode::"+companyCode);
		System.out.println("subCompId::"+subCompId);
		System.out.println("prodType::"+prodType);
		System.out.println("prodGroup::"+prodGroup);
		System.out.println("divId::"+divId);
		EntityManager em = null;
		List<SmpItem> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select s.smp_prod_name as smp_prod_name, s.smp_prod_id as smp_prod_id from SmpItem s inner join PRODUCTWISE_TAX_MASTER p ");
			sb.append("  on s.smp_prod_cd=p.PROD_CODE ");
			sb.append("  join div_master dv on dv.div_id = s.SMP_STD_DIV_ID");
			sb.append(" where s.smp_company = :comp_cd and s.smp_subcomp_id = (case :sub_comp_id when 0 then smp_subcomp_id else ");
			sb.append(" :sub_comp_id end) and s.smp_sa_prod_group =(case :prod_grp when 0 then smp_sa_prod_group else ");
			sb.append(" :prod_grp end) and s.smp_std_div_id =(case :div_id when 0 then smp_std_div_id else :div_id end) ");
			sb.append(" and s.smp_prod_type_id =(case :prod_type when 0 then smp_prod_type_id else :prod_type end) ");
			sb.append(" and s.smp_status='A'  and dv.DIV_status='A' group by s.smp_prod_name,s.smp_prod_id order by s.smp_prod_name ");
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("comp_cd", companyCode);
			query.setParameter("sub_comp_id", subCompId);
			query.setParameter("prod_grp", prodGroup);
			query.setParameter("div_id", divId);
			query.setParameter("prod_type", prodType);
			
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				for (Tuple t : tuples) {
					SmpItem object = new SmpItem();
					object.setSmp_prod_id(Long.valueOf(t.get("smp_prod_id", Integer.class)));
					object.setSmp_prod_name(t.get("smp_prod_name", String.class));
					list.add(object);
				}
			}
		} finally {
			if(em != null) { em.close(); }
		}
		if(list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductMasterBean> getProductDetailByProdId(String companyCode, Long locId, Long ProdId,String stkType) throws Exception{
		EntityManager em = null;
		List<ProductMasterBean> list = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			System.out.println("stkType : "+stkType);
			if(stkType!=null && stkType.trim().equals("05")) {
				sb.append(" select s.smp_prod_cd as prod_cd, s.smp_prod_name as product_name,");
				sb.append(" isnull(b.batch_id,0) as batch_id, b.batch_no as batch_no,  ");
				sb.append(" b.batch_narration as narration, convert(date, b.batch_mfg_dt, 103) as mfg_dt,");
				sb.append(" convert(date, b.batch_expiry_dt, 103) as exp_dt,  isnull(b.batch_rate,s.smp_rate) as rate,");
				sb.append(" s.gcma_req_ind as gcmaInd,s.gcma_number as gcmaCode ,convert(date, s.gcma_aproval_dt, 103) as gcmaApprDt, convert(date, s.gcma_expiry_dt, 103) as gcmaExpDt, ");   
				sb.append(" convert(varchar, s.promo_expiry_accept_ind) as promo_expiry_ind,");
				sb.append(" convert(varchar, s.smp_expiry_rq_ind) as exp_ind, convert(varchar, s.smp_batch_rq_ind) as batch_ind,");
				sb.append(" b.batch_costing_rate  from SmpItem s left join SmpBatch b on b.batch_prod_id=s.smp_prod_id and b.batch_loc_id=:loc_id and b.batch_status = 'A'  ");
				sb.append(" where s.smp_company = :comp_cd");
				sb.append(" and s.smp_prod_id = :prod_id");
				sb.append(" AND  GETDATE()+S.SMP_SHORT_EXPIRY_DAYS >= b.BATCH_EXPIRY_DT");
				sb.append(" ORDER BY s.smp_prod_name ASC");
			}
			else {
				sb.append(" select s.smp_prod_cd as prod_cd, s.smp_prod_name as product_name, isnull(b.batch_id,0) as batch_id, b.batch_no as batch_no, ");
				sb.append(" b.batch_narration as narration, convert(date, b.batch_mfg_dt, 103) as mfg_dt, convert(date, b.batch_expiry_dt, 103) as exp_dt,  isnull(b.batch_rate,s.smp_rate) as rate, ");
				sb.append(" s.gcma_req_ind as gcmaInd,s.gcma_number as gcmaCode ,convert(date, s.gcma_aproval_dt, 103) as gcmaApprDt, convert(date, s.gcma_expiry_dt, 103) as gcmaExpDt,   ");
				sb.append(" convert(varchar, s.promo_expiry_accept_ind) as promo_expiry_ind, convert(varchar, s.smp_expiry_rq_ind) as exp_ind, convert(varchar, s.smp_batch_rq_ind) as batch_ind,b.batch_costing_rate  from SmpItem s left join SmpBatch b on b.batch_prod_id=s.smp_prod_id and b.batch_loc_id=:loc_id and b.batch_status = 'A' ");
				sb.append(" where s.smp_company = :comp_cd and s.smp_prod_id = :prod_id ORDER BY s.smp_prod_name ASC ");
			}
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("loc_id", locId);
			query.setParameter("prod_id", ProdId);
			query.setParameter("comp_cd", companyCode);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ProductMasterBean object = null;
				for (Tuple t : tuples) {
					object = new ProductMasterBean();
					object.setProd_cd(t.get("prod_cd", String.class));
					object.setProduct_name(t.get("product_name", String.class));
					object.setBatch_id(Long.parseLong(t.get("batch_id", Integer.class).toString()));
					object.setBatch_no(t.get("batch_no", String.class));
					object.setNarration(t.get("narration", String.class));
					object.setMfg_dt(t.get("mfg_dt", Date.class) ==null ? 0l :  t.get("mfg_dt", Date.class).getTime());
					object.setExp_dt(t.get("exp_dt", Date.class) ==null ? 0l : t.get("exp_dt", Date.class).getTime());
					object.setRate(t.get("rate", BigDecimal.class));
					object.setPromo_expiry_ind(t.get("promo_expiry_ind", String.class));
					object.setExp_ind(t.get("exp_ind", String.class));
					object.setBatch_ind(t.get("batch_ind", String.class));
					object.setGcmaInd(String.valueOf(t.get("gcmaInd", Character.class)));
					object.setGcmaCode(t.get("gcmaCode", String.class));
					object.setGcmaApprDate(t.get("gcmaApprDt", Date.class) == null ? 0l : t.get("gcmaApprDt", Date.class).getTime());
					object.setGcmaExpDate(t.get("gcmaExpDt", Date.class) == null ? 0l : t.get("gcmaExpDt", Date.class).getTime());
					object.setBatch_costing_rate(t.get("batch_costing_rate", BigDecimal.class));
					list.add(object);
				}
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public BigDecimal getRateByProdId(Long ProdId) throws Exception {
		EntityManager em = null;
		BigDecimal rate = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
			Root<SmpItem> root = criteriaQuery.from(SmpItem.class);
			criteriaQuery.select(builder.max(root.get(SmpItem_.display_rate)))
			.where(builder.equal(root.get(SmpItem_.smp_prod_id), ProdId));
			rate = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if (em != null) { em.close(); }
		}
		return rate;
	}
	
	@Override
	public SmpItem getDivIdByProdId(Long ProdId) throws Exception {
		EntityManager em = null;
		SmpItem smpitem = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SmpItem> criteriaQuery = builder.createQuery(SmpItem.class);
			Root<SmpItem> root = criteriaQuery.from(SmpItem.class);
			criteriaQuery.multiselect((root.get(SmpItem_.smp_std_div_id)),
					(root.get(SmpItem_.display_rate)))
			.where(builder.equal(root.get(SmpItem_.smp_prod_id), ProdId));
			smpitem = em.createQuery(criteriaQuery).getResultList().get(0);
		} finally {
			if (em != null) { em.close(); }
		}
		return smpitem;
	}
	
	@Override
	public List<SmpItem> getProdListByProdIds(List<Long> prodIds) throws Exception {
		EntityManager em = null;
		List<SmpItem> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<SmpItem> criteriaQuery = builder.createQuery(SmpItem.class);
			Root<SmpItem> root = criteriaQuery.from(SmpItem.class);
			criteriaQuery.multiselect((root.get(SmpItem_.smp_prod_id)),
					(root.get(SmpItem_.smp_sa_prod_group)))
			.where(root.get(SmpItem_.smp_prod_id).in(prodIds));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<ProductBean> getUmo() throws Exception {
		EntityManager em = null;
		List<ProductBean> list = new ArrayList<ProductBean>();
		String umoName;
		try {
			em = emf.createEntityManager();
			String q="select UOM_ID,UOM_DISP_NM from UOM where UOM_status='A' order by UOM_DISP_NM asc";
			Query query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				
				for (Tuple t : tuples) {
					ProductBean pb=new ProductBean();
					pb.setUmo(t.get("UOM_ID",Integer.class).toString());
					pb.setUmoName(t.get("UOM_DISP_NM",String.class));
					list.add(pb);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<ProductMasterBean> getPack() {
		EntityManager em = null;
		//List<Object> list = new ArrayList<Object>();
		List<ProductMasterBean> beanlist = new ArrayList<ProductMasterBean>();
		String packName;
		try {
			em = emf.createEntityManager();
			String q="select PACK_ID,PACK_DISP_NM from PACKMASTER where PACK_status='A' order by PACK_DISP_NM asc";
			Query query = em.createNativeQuery(q,Tuple.class);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				ProductMasterBean bean = new ProductMasterBean();
				for (Tuple t : tuples) {
					bean = new ProductMasterBean();
					bean.setPack_id(t.get("PACK_ID",Integer.class).longValue());
					bean.setPack_disp_nm(t.get("PACK_DISP_NM",String.class));
					beanlist.add(bean);
					//packName=t.get("PACK_DISP_NM",String.class);
					//list.add(packName);
				}
				if(!beanlist.isEmpty() && beanlist.size() > 0)
					return beanlist;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return beanlist;
	}
	
	
	
	@Override
	public List<Object> getProductDetailForProductwiseTaxMaster(String divId,String prodTypeId,String subCompId,String hsnCode) {
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,HSN_CODE from smpitem " + 
					"WHERE SMP_STD_DIV_ID =:divId  AND SMP_PROD_TYPE_ID =:prodTypeId AND (rtrim(:hsnCode) IS NOT NULL and hsn_code<>'') " + 
					"AND SMP_SUBCOMP_ID =:subCompId AND SMP_STATUS = 'A' order by smp_prod_name";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("divId", divId);
			query.setParameter("prodTypeId", prodTypeId);
			query.setParameter("subCompId", subCompId);
			query.setParameter("hsnCode", hsnCode);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SmpItem si=new SmpItem();
					si.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
					si.setSmp_prod_cd(t.get("SMP_PROD_CD",String.class));
					si.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
					si.setHsn_code(t.get("HSN_CODE",String.class));
					list.add(si);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<Object> getHSNCode(String prodCode,String Company) {
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select SMP_PROD_ID,SMP_PROD_CD,SMP_PROD_NAME,HSN_CODE from smpitem " + 
					"WHERE SMP_PROD_CD =:prodCode and smp_company=:Company";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("prodCode", prodCode);
			query.setParameter("Company", Company);
			
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SmpItem si=new SmpItem();
					si.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
					si.setSmp_prod_cd(t.get("SMP_PROD_CD",String.class));
					si.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
					si.setHsn_code(t.get("HSN_CODE",String.class));
					list.add(si);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	public List<Object> getTaxDetailForProductwiseTaxMaster(String prodCode,String compCode,String hsnCode) {
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		String packName;
		try {
			em = emf.createEntityManager();
			String q="SELECT PM.SMP_PROD_CD AS PROD_CODE, P.SGprmdet_id STATE_ID, P.SGprmdet_nm STATE_NAME, PT.TAX_CD TAX_CODE, "
					+ "tm.tax_id,tm.tax_cd taxCode, tm.sgst,tm.cgst,tm.igst,tm.add_tax,tm.surch,tm.cess,tm.to_tax,  "
					+ "CASE WHEN PT.OCTROI_OUTPUT_PARAM IS NULL THEN NULL " + 
					" WHEN PT.OCTROI_OUTPUT_PARAM = 'NNNNNN' AND PT.OCTROI_INPUT_PARAM = 'NNNNNN' " + 
					" THEN 'N' ELSE 'Y' END OCTROI,  PT.OCTROI_INPUT_PARAM, PT.OCTROI_OUTPUT_PARAM, " + 
					" PT.OLD_TAX_CD, PT.PROD_DISC, PT.TAX_DATE  , pt.company_cd   " + 
					" FROM  tax_master tm,PRODUCTWISE_TAX_MASTER PT RIGHT OUTER JOIN SG_d_parameters_details P  ON P.SGprmdet_id = PT.STATE_ID and pt.COMPANY_CD=:compcd " + 
					" AND PROD_CODE =:prodCode , smpItem PM  WHERE P.SGprmdet_SGprm_id = '2' AND PM.SMP_PROD_CD =:prodCode  " + 
					" and pm.SMP_STATUS = 'A' and  pm.SMP_COMPANY=:compcd and tm.STATE_ID=P.SGprmdet_id and tm.HSN_CODE=:hsnCode ORDER BY SGprmdet_nm";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("prodCode", prodCode);
			query.setParameter("hsnCode", hsnCode);
			query.setParameter("compcd", compCode);
			
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Object obj[]=new Object[11];
					obj[0]=t.get("STATE_ID",Integer.class);
					obj[1]=t.get("STATE_NAME",String.class);
					obj[2]=t.get("taxCode",String.class);
					obj[3]=t.get("sgst",BigDecimal.class);
					obj[4]=t.get("cgst",BigDecimal.class);
					obj[5]=t.get("igst",BigDecimal.class);
					obj[6]=t.get("add_tax",BigDecimal.class);
					obj[7]=t.get("surch",BigDecimal.class);
					obj[8]=t.get("cess",BigDecimal.class);
					obj[9]=t.get("to_tax",BigDecimal.class);
					obj[10]=t.get("tax_id",Integer.class);
					list.add(obj);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	public List<Object> getAllActiveProductDetailForProductModify() {
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
					"from v_SMPITEM  where SMP_status='A' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
			Query query = em.createNativeQuery(q,Tuple.class);
			//query.setParameter("status", status);

			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					V_SmpItem vsi=new V_SmpItem();
					vsi.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
					vsi.setSmp_prod_cd(t.get("SMP_PROD_CD",String.class));
					vsi.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
					vsi.setDiv_disp_nm(t.get("DIV_DISP_NM",String.class));
					vsi.setSa_group_name(t.get("SA_GROUP_NAME",String.class));
					vsi.setSampling_type(t.get("SMP_SAMPLING_TYPE",String.class));
					vsi.setSmp_prod_type(t.get("SMP_PROD_TYPE",String.class));
					vsi.setSubcomp_nm(t.get("SubComp_Nm",String.class));
					list.add(vsi);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<Object> getProductDetailByTextParameterForProductModify(String name,String parameter,String text) {
		EntityManager em = null;
		List<Object> list = new ArrayList<Object>();
		try {
			em = emf.createEntityManager();
			Query query = null;
			if(name.equals("NM")) {
				System.out.println("detail : "+name+"...."+parameter+"..."+text);
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and SMP_PROD_NAME "+parameter+"  '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("DV")) {
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and DIV_DISP_NM "+parameter+"  '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("GP")){
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and SA_GROUP_NAME "+parameter+" '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("CD")) {
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and SMP_PROD_CD "+parameter+" '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("ST")) {
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and SMP_SAMPLING_TYPE "+parameter+" '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("PT")) {
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and SMP_PROD_TYPE "+parameter+" '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			if(name.equals("SN")) {
				String q="select SMP_PROD_ID,SMP_PROD_NAME, DIV_DISP_NM,SA_GROUP_NAME,SMP_PROD_CD , SMP_SAMPLING_TYPE,SMP_PROD_TYPE,SubComp_Nm " + 
						"from v_SMPITEM  where SMP_status='A' and SubComp_Nm "+parameter+" '%"+text+"%' order by SMP_SubComp_id,Smp_std_div_id,Smp_prod_type,Smp_prod_name";
				query = em.createNativeQuery(q,Tuple.class);
			}
			
			

			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					V_SmpItem vsi=new V_SmpItem();
					vsi.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
					vsi.setSmp_prod_cd(t.get("SMP_PROD_CD",String.class));
					vsi.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
					vsi.setDiv_disp_nm(t.get("DIV_DISP_NM",String.class));
					vsi.setSa_group_name(t.get("SA_GROUP_NAME",String.class));
					vsi.setSampling_type(t.get("SMP_SAMPLING_TYPE",String.class));
					vsi.setSmp_prod_type(t.get("SMP_PROD_TYPE",String.class));
					vsi.setSubcomp_nm(t.get("SubComp_Nm",String.class));
					list.add(vsi);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<SmpItem> getProductDetailById(String id) {
		EntityManager em = null;
		List<SmpItem> list = new ArrayList<SmpItem>();
		System.out.println("selected prod id : "+id);
		try {
			em = emf.createEntityManager();
			Query query = null;
				
			String q="from SmpItem WHERE smp_prod_id =:id";
			query = em.createQuery(q);
			query.setParameter("id", Long.valueOf(id));
			list=query.getResultList();
			System.out.println(list.get(0).getSmp_prod_name());
			System.out.println(list.get(0).getSmp_subcomp_id());
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	@Override
	public List<Object> getSampleProductList(Long divId,String compcd) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("Div"+divId);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			//sb.append(" SELECT SI.SMP_PROD_ID,SI.SMP_PROD_NAME FROM SMPITEM SI WHERE ( SI.SMP_STD_DIV_ID=:divId OR SI.SMP_STD_DIV_ID ");
			//sb.append(" IN (SELECT PROD_DIV_ID FROM DIVMAP WHERE BASE_DIV_ID=:divId)) AND SMP_PROD_TYPE='SAMPLE' AND SMP_SUBCOMP_ID IN (1,3) AND SMP_STATUS='A' order by SMP_PROD_NAME");
			sb.append(" SELECT SI.smp_prod_id, SI.smp_prod_name,SI.SMP_PROD_ID,CASE WHEN SUBSTRING(SI.SMP_ERP_PROD_CD,1,1) = 'F' THEN SI.SMP_ERP_PROD_CD ELSE SI.SMP_PROD_CD END AS SMP_PROD_CD   ");
			sb.append(" FROM smpitem SI WHERE ( SI.smp_std_div_id =:divId ) OR (SI.smp_std_div_id IN ");
			if(compcd.trim().equals(PAL)) {
				sb.append(" (SELECT prod_div_id FROM   divmap_ES WHERE  base_div_id =:divId))  AND smp_subcomp_id IN ( 1, 3 )AND smp_status = 'A' ORDER  BY smp_prod_name");
			}
			else {
				sb.append(" (SELECT prod_div_id FROM   divmap WHERE  base_div_id =:divId))  AND smp_subcomp_id IN ( 1, 3 )AND smp_status = 'A' ORDER  BY smp_prod_name");

			}
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ProductMasterBean object = null;
				for (Tuple t : tuples) {
					object = new ProductMasterBean();
					object.setProduct_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setProduct_name(t.get("SMP_PROD_NAME", String.class));
					object.setProd_cd(object.getProduct_name()!=null && object.getProduct_name().length()<30?object.getProduct_name():object.getProduct_name().substring(0,18)+" ("+t.get("SMP_PROD_CD", String.class)+")");
					list.add(object);
				}
				System.out.println("list "+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<SmpItem> getProdListByDivPrdType(String comp_cd, Long prod_type_id, Long div_id) throws Exception {
		// TODO Auto-generated method stub
		EntityManager em = null;
		List<SmpItem> prodList=null;
		StringBuffer sql;
		SmpItem object = null;
		
		try {
			em = emf.createEntityManager();
			String comcd = comp_cd.trim();
			System.out.println("Comp_cd :"+comcd);
			System.out.println("prod_type :"+prod_type_id);
			System.out.println("div_id :"+div_id);
			
			sql = new StringBuffer();
			sql.append("select smp_prod_id, smp_prod_name from SMPITEM  ");
			sql.append("where SMP_COMPANY = :comp_cd ");
			sql.append("and SMP_STD_DIV_ID =(case :div_id when 0 then SMP_STD_DIV_ID else :div_id end) ");
			sql.append("and SMP_PROD_TYPE_ID =(case :prod_type when 0 then SMP_PROD_TYPE_ID else :prod_type end) ");
			sql.append("AND SMP_status='A' order by smp_prod_name");
			
			Query query = em.createNativeQuery(sql.toString(),Tuple.class);
			query.setParameter("comp_cd", comcd);
			query.setParameter("div_id", div_id);
			query.setParameter("prod_type", prod_type_id);
			
			List<Tuple> tuples = query.getResultList();
			System.out.println("list ::"+tuples.size());
			
			if (tuples != null && !tuples.isEmpty()) {
				prodList = new ArrayList<>();
				
				for (Tuple t : tuples) {
					object = new SmpItem();
					object.setSmp_prod_name(t.get("smp_prod_name", String.class));
					object.setSmp_prod_id(Long.parseLong(t.get("smp_prod_id", Integer.class).toString()));
					
					prodList.add(object);
				}
				
				
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally{
			em.close();
		}
		return prodList;
	}
	
	
	//Itemwise Stock Ledger
		@Override
		public List<SmpItem> getAllActiveItemList() {
			EntityManager em = null;
			List<SmpItem> list = new ArrayList<SmpItem>();
			try {
				em = emf.createEntityManager();
				String q="select SMP_PROD_ID,SMP_PROD_NAME from smpitem WHERE smp_status='A' order by smp_prod_name";
				Query query = em.createNativeQuery(q,Tuple.class);
				List<Tuple> tuples = query.getResultList();			
				if (tuples != null && !tuples.isEmpty()) {
					for (Tuple t : tuples) {
						SmpItem si=new SmpItem();
						si.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
						si.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
						list.add(si);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
				} finally {
					if (em != null) { em.close(); }
			}
			return list;
		}
		@Override
		public List<SmpItem> getItemListByDivId(String divId) {
			EntityManager em = null;
			List<SmpItem> list = new ArrayList<SmpItem>();
			try {
				em = emf.createEntityManager();
			//	String q="select SMP_PROD_ID,ltrim(SMP_PROD_NAME) SMP_PROD_NAME from smpitem WHERE smp_status='A' and smp_std_div_id=:divId order by ltrim(SMP_PROD_NAME)";
				StringBuffer sql = new StringBuffer();
				sql.append("select SMP_PROD_ID,ltrim(SMP_PROD_NAME) SMP_PROD_NAME from smpitem WHERE smp_status='A'");  
				if(!divId.equals("0")) {
				sql.append(" and smp_std_div_id=:divId");  
				}
				sql.append(" order by ltrim(SMP_PROD_NAME)");
				Query query = em.createNativeQuery(sql.toString(),Tuple.class);
				if(!divId.equals("0")) {
				query.setParameter("divId", divId);
				}
				List<Tuple> tuples = query.getResultList();			
				if (tuples != null && !tuples.isEmpty()) {
					for (Tuple t : tuples) {
						SmpItem si=new SmpItem();
						si.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
						si.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
						list.add(si);
					}
					if(!list.isEmpty() && list.size() > 0)
						return list;
					}
				} finally {
					if (em != null) { em.close(); }
			}
			return list;
		}
	@Override
	public List<SmpItem> getItemByCode(String prodCode) {
		EntityManager em = null;
		List<SmpItem> list = new ArrayList<SmpItem>();
		try {
			em = emf.createEntityManager();
			String q="select SMP_PROD_ID,SMP_PROD_NAME from smpitem WHERE smp_status='A' "
					+ " and smp_prod_cd=:prodCode order by smp_prod_name";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("prodCode", prodCode);
			List<Tuple> tuples = query.getResultList();			
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					SmpItem si=new SmpItem();
					si.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class)));
					si.setSmp_prod_name(t.get("SMP_PROD_NAME",String.class));
					list.add(si);
				}
				if(!list.isEmpty() && list.size() > 0)
					return list;
				}
			} finally {
				if (em != null) { em.close(); }
		}
		return list;
	}
	
    @Override
	public SmpItem getProductMasterObjByCode(String product_code)throws Exception {
		EntityManager em = null;
		SmpItem smpitem = null;
		System.out.println("product_code "+product_code);
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpItem> criteriaQuery = builder.createQuery(SmpItem.class);
			Root<SmpItem> root = criteriaQuery.from(SmpItem.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(SmpItem_.smp_prod_cd), product_code),builder.equal(root.get(SmpItem_.smp_status), "A"));
			smpitem = entityManager.createQuery(criteriaQuery).getResultList().get(0);
		} finally {
			if (em != null) { em.close(); }
		}
		return smpitem;
	}
    
	@Override
	public String genereateProductCode(String subCompyCode,String date) throws Exception {
		System.out.println("code "+subCompyCode);
		System.out.println("date "+date);
		String prodCode=null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select :subCompyCode+:date+ ");  
			sql.append(" RIGHT ('0000'+ CAST( isnull( max(convert(numeric,rtrim(SUBSTRING(smp_prod_cd,12,4))))+1,1) as varchar),4 ) prodcd");
			sql.append(" from SMPITEM");
			sql.append(" where SMP_PROD_TYPE_ID in ( select sgprmdet_id from SG_d_parameters_details , sg_m_parameters where SGprm_nm = 'Product Types' and  SGprm_id = SGprmdet_SGprm_id and sgprmdet_nm != 'Sample' )");
			sql.append(" and LEFT( SMP_PROD_CD,3) =:subCompyCode   ");
			sql.append(" and SUBSTRING( SMP_PROD_CD , 4,8 ) =:date  ");
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter("subCompyCode", subCompyCode.trim());
			query.setParameter("date", date.trim());
			prodCode= query.getSingleResult().toString();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
		}
		return prodCode;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteLockProduct(String user_id) throws Exception{
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("delete from Prod_lock");
			if(user_id!=null)
				buffer.append("  where user_id=:user_id ");
					
			Query query=entityManager.createNativeQuery(buffer.toString());
			if(user_id!=null)
				query.setParameter("user_id", user_id);
			query.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}
	}
 

	@Override
	public List<SmpItem> checkUniqueErpProductCode(String erpprodCode, String subCompany) {
		//Boolean data=null;
		EntityManager em = null;
		List<SmpItem> list = null;
		SmpItem item=null;
		try {
			System.out.println("erpprodCode"+erpprodCode);
			System.out.println("subCompany"+subCompany);
			em = emf.createEntityManager();
			String q="select SMP_PROD_ID,SMP_PROD_CD,SMP_COMPANY,SMP_ERP_PROD_CD from smpitem where SMP_ERP_PROD_CD=:erpprodCode AND SMP_SubComp_id=:subCompany";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("erpprodCode", erpprodCode);
			query.setParameter("subCompany", subCompany);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				//data=true;
				item = new SmpItem();
				list = new ArrayList<>();
				for(Tuple t:tuples) {
					item.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class).toString()));
					list.add(item);
				}
			}
//			else {
//				data=false;
//			}
			
			System.out.println("list :"+tuples.size());
		}
		finally {
			em.close();
		}
		return list;
	}
	
	@Override
	public List<SmpItem> checkUniqueProductCode(String prodCode,String subCompany) {
		Boolean data=null;
		EntityManager em = null;
		List<SmpItem> list = null;
		SmpItem item = null;
		try {
			em = emf.createEntityManager();
			String q="select SMP_PROD_ID,SMP_PROD_CD,SMP_COMPANY,SMP_PROD_NAME,SMP_PACK_ID,SMP_ALTER_NAME,SMP_LAUNCH_DT,SMP_DISCONT_DT,SMP_UOM_ID,SMP_PROD_TYPE from smpitem where SMP_PROD_CD=:prodCode AND SMP_SubComp_id=:subCompany";
			Query query = em.createNativeQuery(q,Tuple.class);
			query.setParameter("prodCode", prodCode);
			query.setParameter("subCompany", subCompany);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				//data=true;
				list = new ArrayList<>();
				for(Tuple t : tuples) {
					item = new SmpItem();
					item.setSmp_prod_id(Long.valueOf(t.get("SMP_PROD_ID",Integer.class).toString()));
					list.add(item);
				}
				
			}
			//else {data=false;}
		}
		finally {
			em.close();
		}
		return list;
	}

	@Override
	public List<Object> getSampleProductListEmergency(Long divId, String compcode) throws Exception {
		EntityManager em = null;
		List<Object> list = null;
		System.out.println("Div"+divId);
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			//sb.append(" SELECT SI.SMP_PROD_ID,SI.SMP_PROD_NAME FROM SMPITEM SI WHERE ( SI.SMP_STD_DIV_ID=:divId OR SI.SMP_STD_DIV_ID ");
			//sb.append(" IN (SELECT PROD_DIV_ID FROM DIVMAP WHERE BASE_DIV_ID=:divId)) AND SMP_PROD_TYPE='SAMPLE' AND SMP_SUBCOMP_ID IN (1,3) AND SMP_STATUS='A' order by SMP_PROD_NAME");
			sb.append(" SELECT SI.smp_prod_id, SI.smp_prod_name,SI.SMP_PROD_ID,CASE WHEN SUBSTRING(SI.SMP_ERP_PROD_CD,1,1) = 'F' THEN SI.SMP_ERP_PROD_CD ELSE SI.SMP_PROD_CD END AS SMP_PROD_CD   ");
			sb.append(" FROM smpitem SI WHERE ( SI.smp_std_div_id =:divId ) OR (SI.smp_std_div_id IN ");
		
			if(compcode.trim().equals(PAL)) {
				sb.append(" (SELECT prod_div_id FROM   DIVMAP_ES WHERE  base_div_id =:divId))  AND smp_subcomp_id IN ( 1, 3 )AND smp_status = 'A' ORDER  BY smp_prod_name");
			}
			else {
				sb.append(" (SELECT prod_div_id FROM   divmap WHERE  base_div_id =:divId))  AND smp_subcomp_id IN ( 1, 3 )AND smp_status = 'A' ORDER  BY smp_prod_name");
			}
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("divId", divId);
			List<Tuple> tuples = query.getResultList();
			
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ProductMasterBean object = null;
				for (Tuple t : tuples) {
					object = new ProductMasterBean();
					object.setProduct_id(Long.valueOf(t.get("SMP_PROD_ID", Integer.class)));
					object.setProduct_name(t.get("SMP_PROD_NAME", String.class));
					object.setProd_cd(object.getProduct_name()!=null && object.getProduct_name().length()<30?object.getProduct_name():object.getProduct_name().substring(0,18)+" ("+t.get("SMP_PROD_CD", String.class)+")");
					list.add(object);
				}
				System.out.println("list "+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public SmpItem getProductIdBySmpProdCode(String smp_prod_code) throws Exception {
		SmpItem smpItem = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SmpItem> query = builder.createQuery(SmpItem.class);
			Root<SmpItem> root = query.from(SmpItem.class);
			query.multiselect(root.get(SmpItem_.smp_prod_id));
			query.where(builder.and(builder.equal(root.get(SmpItem_.smp_prod_cd),smp_prod_code)));
			
			smpItem = entityManager.createQuery(query).getSingleResult();
		}
		catch(Exception e) {
			throw e;
		}
		return smpItem;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteproductwise_tax_master_by_procode(String product_code)  throws Exception  {
		
		int i = 0;
		Boolean deleted = false;

		Query query = null;
		String q=" delete from PRODUCTWISE_TAX_MASTER where  PROD_CODE=:product_code";
		query = entityManager.createNativeQuery(q);
		query.setParameter("product_code", product_code);
		i = query.executeUpdate();

		System.out.println(i);
		if (i > 0) {
			deleted = true;
		} else {
			deleted = false;
		}
	}

	@Override
	public List<Hsn_and_Tax_Bean> getAllHsnCode() {
		
		List<Hsn_and_Tax_Bean> hsn_beans=new ArrayList<>();
		List<Tuple > tuples_list=new ArrayList<>();
		
		Query query = null;

		String q="select distinct HSN_CODE,LAST_MOD_DATE,SGST from TAX_MASTER  order by LAST_MOD_DATE desc";
		query = entityManager.createNativeQuery(q,Tuple.class);

		tuples_list =  query.getResultList();
		
		if(tuples_list.size()>0) {
			
			for(Tuple t :tuples_list) {		
				
				Hsn_and_Tax_Bean hsn_Bean=	new Hsn_and_Tax_Bean();
				hsn_Bean.setHsn_code(	t.get("HSN_CODE").toString().trim());
				hsn_Bean.setSgst_tax(t.get("SGST").toString().trim());	
			
				hsn_beans.add(hsn_Bean);
			}
		
		
		}
		
		return hsn_beans;
	}
	
}
