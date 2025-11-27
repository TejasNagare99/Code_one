package com.excel.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.model.AllocReqDet;
import com.excel.model.AllocReqHeader;
import com.excel.model.Alloc_Detail;
import com.excel.model.Alloc_Header;
import com.excel.model.Alloc_Header_;
import com.excel.model.Alloc_Temp;
import com.excel.model.Alloc_gen_dt;
import com.excel.model.Alloc_gen_hd;

@Repository
public class AllocHeaderRepositoryImpl implements AllocHeaderRepository{	
	
	@Autowired private EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	@Autowired TransactionalRepository transactionalRepository;
	@Autowired AllocDetailRepository  allocDetailRepository;
	@Autowired AllocationRepository  allocationRepository;


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long setMainDetail(Set<Long> set, List<Alloc_gen_dt> detailList, String stk_cfa_ind,Alloc_gen_hd header)throws Exception {
		Long  cycle = 1L;
		boolean cycle_flag=false;
		Long smpId=this.allocationRepository.getSmpId();
		try {
			//Alloc_Temp temp=null;
			boolean flag=false;
			
			for(Long fs_id:set){
				Alloc_Header alloc_header = null;
				flag=true;
				for (Alloc_gen_dt alloc_Temp : detailList) {
					//temp=alloc_Temp;
					if(fs_id.equals(alloc_Temp.getMsr_id())){
						if(flag){
							boolean isNew=false;
							if(header.getAlloc_type().trim().equals("A")){
								alloc_header=this.getAlloc_HeaderObj(alloc_Temp.getPeriod_code(), alloc_Temp.getCompany(),
										alloc_Temp.getFin_year().toString(),alloc_Temp.getMsr_id(),String.valueOf(header.getAlloc_type()));
								if(alloc_header!=null){
									alloc_header.setAlloc_mod_usr_id(alloc_Temp.getUser_id());
									alloc_header.setAlloc_mod_dt(new Date());
									alloc_header.setAlloc_mod_ip_add(alloc_Temp.getUpd_ip_add());
									alloc_header.setStock_at_cfa(stk_cfa_ind);
									transactionalRepository.update(alloc_header);
									
									
								}else{
									isNew=true;
								}
							}else{
								isNew=true;
							}
							if(isNew){

								alloc_header=new Alloc_Header();
								StringBuffer alloc_no_const=new StringBuffer("A000000000000000");
								alloc_header.setAlloc_smp_id(smpId);
								alloc_header.setAlloc_no(alloc_no_const.toString());
								alloc_header.setAlloc_company(alloc_Temp.getCompany());
								alloc_header.setAlloc_fstaff_id(alloc_Temp.getMsr_id());
								alloc_header.setAlloc_fin_year(alloc_Temp.getFin_year().toString());
								alloc_header.setAlloc_period_code(alloc_Temp.getPeriod_code());
								alloc_header.setAlloc_ins_usr_id(alloc_Temp.getUser_id());
								alloc_header.setAlloc_ins_ip_add(alloc_Temp.getUpd_ip_add());
								alloc_header.setAlloc_status(alloc_Temp.getStatus().charAt(0));
								alloc_header.setStock_at_cfa(stk_cfa_ind);
								alloc_header.setAlloc_type(header.getAlloc_type());
								alloc_header.setAlloc_appr_status('A');
								transactionalRepository.persist(alloc_header);
								Long id=alloc_header.getAlloc_id();
								int length=id.toString().length();
								alloc_no_const.replace(alloc_no_const.length()-length, alloc_no_const.length(),id.toString());
								alloc_header.setAlloc_no(alloc_no_const.toString());
								transactionalRepository.update(alloc_header);
								
								//save alloc_remark
								/*
								 * rem_save_query.setParameter("ALLOC_GEN_HD_ID",
								 * alloc_Temp.getAlloc_header_id()); rem_save_query.setParameter("ALLOC_ID",
								 * alloc_header.getAlloc_id()); rem_save_query.setParameter("REMARK",
								 * app_remark); rem_save_query.executeUpdate();
								 */
							}
							flag=false;
						}
						Alloc_Detail alloc_Detail = null;
						boolean isNew=false;
						if(header.getAlloc_type().trim().equals("A")){
							if(cycle_flag==false){
								Long integer = allocDetailRepository.getCycle(alloc_Temp.getDiv_id(), alloc_Temp.getPeriod_code(), alloc_Temp.getCompany(),
									alloc_Temp.getFin_year().toString());
								if (integer != null) {
									cycle = integer + 1;
								}else{
									cycle=0L;
								}
								cycle_flag=true;
							}
						}
						if(alloc_Temp.getAlloc_mode().equals("A")){
							
							alloc_Detail=allocDetailRepository.getAlloc_DetailObj(alloc_Temp.getDiv_id(),
									alloc_Temp.getPeriod_code(), alloc_Temp.getCompany(),
									alloc_Temp.getFin_year().toString(),alloc_Temp.getMsr_id(),alloc_Temp.getProd_id(),alloc_Temp.getAlloc_mode());
							if(alloc_Detail!=null){
								alloc_Detail.setAllocdtl_curr_alloc_qty(alloc_Detail.getAllocdtl_curr_alloc_qty().add(BigDecimal.valueOf(alloc_Temp.getAlloc_qty_msr())));
								alloc_Detail.setAllocdtl_mod_usr_id(alloc_Temp.getUser_id());
								alloc_Detail.setAllocdtl_mod_ip_add(alloc_Temp.getUser_id());
								alloc_Detail.setAllocdtl_mod_dt(new Date());
								//alloc_Detail.setAllocdtl_cycle(alloc_Temp.getCycle());
								alloc_Detail.setAllocdtl_cycle(Integer.valueOf(cycle.toString()));
								transactionalRepository.update(alloc_Detail);
							}else{
								isNew=true;
							}
						}else{
							isNew=true;
						}
						if(isNew){
							alloc_Detail = new Alloc_Detail();
							alloc_Detail.setAlloc_Header(alloc_header);
							alloc_Detail.setAllocdtl_smpd_id(BigDecimal.ZERO.longValue());
							alloc_Detail.setAllocdtl_fin_year(alloc_Temp.getFin_year().toString());
							alloc_Detail.setAllocdtl_period_code(alloc_Temp.getPeriod_code());
							alloc_Detail.setAllocdtl_company(alloc_Temp.getCompany());
							alloc_Detail.setAllocdtl_fstaff_id(alloc_Temp.getMsr_id());
							alloc_Detail.setAllocdtl_div_id(alloc_Temp.getDiv_id());
							alloc_Detail.setAllocdtl_prod_id(alloc_Temp.getProd_id());
							alloc_Detail.setAllocdtl_alloc_type(alloc_Temp.getAlloc_mode());
							alloc_Detail.setAllocdtl_cycle(Integer.valueOf(cycle.toString()));
							alloc_Detail.setAllocdtl_rate(BigDecimal.ZERO);
							alloc_Detail.setAllocdtl_alloc_qty(BigDecimal.valueOf(alloc_Temp.getAlloc_qty_msr()));
							alloc_Detail.setAllocdtl_curr_alloc_qty(BigDecimal.valueOf(alloc_Temp.getAlloc_qty_msr()));
							alloc_Detail.setAllocdtl_supply_qty(BigDecimal.ZERO);
							alloc_Detail.setAllocdtl_alt_div_id(alloc_Temp.getAlt_div_id());
							alloc_Detail.setAllocdtl_inv_grp(alloc_Temp.getDiv_id());
							alloc_Detail.setAllocdtl_ins_usr_id(alloc_Temp.getUser_id());
							alloc_Detail.setAllocdtl_ins_ip_add(alloc_Temp.getUser_id());
							alloc_Detail.setAllocdtl_status(alloc_Temp.getStatus().charAt(0));
							alloc_Detail.setAllocdtl_blk_flg("N");
							alloc_Detail.setAllocdtl_phy_qty(BigDecimal.ZERO);
							alloc_Detail.setAllocdtl_appr_status('A');
							alloc_Detail.setAllocdtl_alloc_type(alloc_Temp.getAlloc_mode());
							transactionalRepository.persist(alloc_Detail);
						}
						
					}
				}
			}
		
			
		} catch (Exception e) {
			throw e;
		}
		return cycle;
	}
	
	@Override
	public Alloc_Header getAlloc_HeaderObj(String period_code,String company, String fin_year,Long fstaff_id,String alloc_type) {

		EntityManager em = null;
		Alloc_Header header = null;
		List<Alloc_Header> list=null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Alloc_Header> criteriaQuery = builder.createQuery(Alloc_Header.class);
			Root<Alloc_Header> root = criteriaQuery.from(Alloc_Header.class);
			criteriaQuery.select(root)
			.where(builder.equal(root.get(Alloc_Header_.alloc_period_code), period_code),builder.equal(root.get(Alloc_Header_.alloc_company), company)
				,builder.equal(root.get(Alloc_Header_.alloc_fstaff_id), fstaff_id),builder.equal(root.get(Alloc_Header_.alloc_fin_year), fin_year),builder.equal(root.get(Alloc_Header_.alloc_type), alloc_type) );
			list = entityManager.createQuery(criteriaQuery).getResultList();
			if(list!=null && list.size()>0)
				return list.get(0);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return null;
	
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long setDetailsForRulesBasedAllocation(Set<Long> set, List<Alloc_Temp> list, String app_remark, String stk_cfa_ind)throws Exception {
		Long cycle = 1L;
		boolean cycle_flag=false;
		Long smpId=this.allocationRepository.getSmpId();
		try {
			//Alloc_Temp temp=null;
			boolean flag=false;
			int j=0;
			for(Long fs_id:set){
				j++;
				Alloc_Header alloc_header = null;
				flag=true;
				int i=0;
				for (Alloc_Temp alloc_Temp : list) {
					//temp=alloc_Temp;
					if(fs_id.equals(alloc_Temp.getFstaff_id())){
						i++;
						if(flag){
							boolean isNew=false;
						System.out.println("Alloc Temp "+alloc_Temp.getUpload_status());
						System.out.println(alloc_Temp.getPeriod_code());
						System.out.println( alloc_Temp.getCompany());
						System.out.println(alloc_Temp.getFin_year_id());
						System.out.println(alloc_Temp.getFstaff_id());
						if(alloc_Temp.getUpload_status()=='A'){
								alloc_header=this.getAlloc_HeaderObj(alloc_Temp.getPeriod_code(), alloc_Temp.getCompany(),
										alloc_Temp.getFin_year_id().toString(),alloc_Temp.getFstaff_id(),String.valueOf(alloc_Temp.getUpload_status()));
								if(alloc_header!=null){
									alloc_header.setAlloc_mod_usr_id(alloc_Temp.getIns_usr_id());
									alloc_header.setAlloc_mod_dt(new Date());
									alloc_header.setAlloc_mod_ip_add(alloc_Temp.getIns_ip_add());
									alloc_header.setStock_at_cfa(stk_cfa_ind);
									entityManager.merge(alloc_header);
									//this.transactionalRepository.update(alloc_header);
									
								}else{
									isNew=true;
								}
							}else{
								isNew=true;
							}
							if(isNew){
						
								//System.out.println("AllocHeaderDao.setHeader()"+smpID);
								alloc_header=new Alloc_Header();
								StringBuffer alloc_no_const=new StringBuffer("A000000000000000");
								alloc_header.setAlloc_smp_id(smpId);
								alloc_header.setAlloc_no(alloc_no_const.toString());
								alloc_header.setAlloc_company(alloc_Temp.getCompany());
								alloc_header.setAlloc_fstaff_id(alloc_Temp.getFstaff_id());
								alloc_header.setAlloc_fin_year(alloc_Temp.getFin_year_id().toString());
								alloc_header.setAlloc_period_code(alloc_Temp.getPeriod_code());
								alloc_header.setAlloc_ins_usr_id(alloc_Temp.getIns_usr_id());
								alloc_header.setAlloc_ins_ip_add(alloc_Temp.getIns_ip_add());
								alloc_header.setAlloc_status(alloc_Temp.getStatus());
								alloc_header.setStock_at_cfa(stk_cfa_ind);
								alloc_header.setAlloc_type(String.valueOf(alloc_Temp.getUpload_status()));
								alloc_header.setAlloc_appr_status('A');
								entityManager.persist(alloc_header);
							//	this.transactionalRepository.persist(alloc_header);
								Long id=alloc_header.getAlloc_id();
								int length=id.toString().length();
								alloc_no_const.replace(alloc_no_const.length()-length, alloc_no_const.length(),id.toString());
								alloc_header.setAlloc_no(alloc_no_const.toString());
								//this.transactionalRepository.update(alloc_header);
								entityManager.merge(alloc_header);
				
							}
							flag=false;
						}
						Alloc_Detail alloc_Detail = null;
						boolean isNew=false;
						if(alloc_Temp.getUpload_status()=='A'){
							if(cycle_flag==false){
								Long integer = allocDetailRepository.getCycle( alloc_Temp.getDiv_id(), alloc_Temp.getPeriod_code(), alloc_Temp.getCompany(),
																				alloc_Temp.getFin_year_id().toString());
								if (integer != null) {
									cycle = integer + 1L;
								}else{
									cycle=0L;
								}
								cycle_flag=true;
							}
						}
						if(alloc_Temp.getUpload_status()=='A'){
							
							alloc_Detail=allocDetailRepository.getAlloc_DetailObj(alloc_Temp.getDiv_id(),
									alloc_Temp.getPeriod_code(), alloc_Temp.getCompany(),
									alloc_Temp.getFin_year_id().toString(),alloc_Temp.getFstaff_id(),alloc_Temp.getProd_id(),String.valueOf(alloc_Temp.getUpload_status()));
							if(alloc_Detail!=null){
								alloc_Detail.setAllocdtl_curr_alloc_qty(alloc_Detail.getAllocdtl_curr_alloc_qty().add(alloc_Temp.getAlloc_qty()));
								alloc_Detail.setAllocdtl_mod_usr_id(alloc_Temp.getIns_usr_id());
								alloc_Detail.setAllocdtl_mod_ip_add(alloc_Temp.getIns_ip_add());
								alloc_Detail.setAllocdtl_mod_dt(new Date());
								//alloc_Detail.setAllocdtl_cycle(alloc_Temp.getCycle());
								alloc_Detail.setAllocdtl_cycle(Integer.valueOf(cycle.toString()));
								//this.transactionalRepository.update(alloc_Detail);
								entityManager.merge(alloc_Detail);
							}else{
								isNew=true;
							}
						}else{
							isNew=true;
						}
						if(isNew){
							alloc_Detail = new Alloc_Detail();
							alloc_Detail.setAlloc_Header(alloc_header);
							alloc_Detail.setAllocdtl_smpd_id(BigDecimal.ZERO.longValue());
							alloc_Detail.setAllocdtl_fin_year(alloc_Temp.getFin_year_id().toString());
							alloc_Detail.setAllocdtl_period_code(alloc_Temp.getPeriod_code());
							alloc_Detail.setAllocdtl_company(alloc_Temp.getCompany());
							alloc_Detail.setAllocdtl_fstaff_id(alloc_Temp.getFstaff_id());
							alloc_Detail.setAllocdtl_div_id(alloc_Temp.getDiv_id());
							alloc_Detail.setAllocdtl_prod_id(alloc_Temp.getProd_id());
							alloc_Detail.setAllocdtl_alloc_type(alloc_Temp.getAlloc_type());
							alloc_Detail.setAllocdtl_cycle(Integer.valueOf(cycle.toString()));
							alloc_Detail.setAllocdtl_rate(alloc_Temp.getRate());
							alloc_Detail.setAllocdtl_alloc_qty(alloc_Temp.getAlloc_qty());
							alloc_Detail.setAllocdtl_curr_alloc_qty(alloc_Temp.getAlloc_qty());
							alloc_Detail.setAllocdtl_supply_qty(BigDecimal.ZERO);
							alloc_Detail.setAllocdtl_alt_div_id(alloc_Temp.getAlt_div_id());
							alloc_Detail.setAllocdtl_inv_grp(alloc_Temp.getInv_grp());
							alloc_Detail.setAllocdtl_ins_usr_id(alloc_Temp.getIns_usr_id());
							alloc_Detail.setAllocdtl_ins_ip_add(alloc_Temp.getIns_ip_add());
							alloc_Detail.setAllocdtl_status(alloc_Temp.getStatus());
							alloc_Detail.setAllocdtl_blk_flg("N");
							alloc_Detail.setAllocdtl_phy_qty(BigDecimal.ZERO);
							alloc_Detail.setAllocdtl_appr_status('A');
							alloc_Detail.setAllocdtl_alloc_type(String.valueOf(alloc_Temp.getUpload_status()));
							//this.transactionalRepository.persist(alloc_Detail);
							entityManager.persist(alloc_Detail);
						}
						
						if(i == 20) {
							entityManager.flush();
							//i=0;
						}
						System.out.println(i);
					}
				}
				System.out.println(j);
			}
			
		} catch (Exception e) {
			throw e;
		}
		return cycle;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void setAllocDetailsForSpecialAlloc(AllocReqHeader header,List<AllocReqDet> alloc_req_detail,Long fsId,Long smpId) throws Exception {
		
		Alloc_Header alloc_header =null;
		Alloc_Detail alloc_detail=null;
		alloc_header=this.getAlloc_HeaderObj(header.getPeriod_code(),header.getCompany(),header.getFin_year(),fsId,header.getRequest_type());
		if(alloc_header!=null) {
			alloc_header.setAlloc_mod_usr_id(header.getReq_ins_user_id());
			alloc_header.setAlloc_mod_dt(new Date());
			alloc_header.setAlloc_mod_ip_add(header.getReq_ins_ip_add());
			this.transactionalRepository.update(alloc_header);
		}
		else {
			alloc_header =new Alloc_Header();
			alloc_header.setAlloc_company(header.getCompany());
			alloc_header.setAlloc_err_dtl("0");
			alloc_header.setAlloc_fin_year(header.getFin_year());
			alloc_header.setAlloc_fstaff_id(fsId);
			alloc_header.setAlloc_ins_ip_add(header.getReq_ins_ip_add());
			alloc_header.setAlloc_ins_usr_id(header.getReq_ins_user_id());
			alloc_header.setAlloc_mod_dt(new Date());
			alloc_header.setAlloc_mod_ip_add(header.getReq_mod_ip_add());
			alloc_header.setAlloc_no(header.getRequest_no());
			alloc_header.setAlloc_period_code(header.getPeriod_code());
			alloc_header.setAlloc_smp_id(smpId);
			alloc_header.setAlloc_type(header.getRequest_type());
			alloc_header.setAlloc_status('A');
			alloc_header.setStock_at_cfa("0");
			alloc_header.setAlloc_appr_status('A');
			alloc_header.setSrt_number(header.getSrt_number());
			alloc_header.setSrt_date(header.getSrt_date());
			alloc_header.setSrt_remark(header.getSrt_remark());
			this.transactionalRepository.persist(alloc_header);
		}
		for(AllocReqDet detail:alloc_req_detail) {
			
			alloc_detail=allocDetailRepository.getAlloc_DetailObj(detail.getAlt_div_id(),detail.getPeriod_code(),detail.getCompany(),
																 header.getFin_year(),fsId,detail.getProd_id(),header.getRequest_type());
			if(alloc_detail!=null){
				alloc_detail.setAllocdtl_curr_alloc_qty(alloc_detail.getAllocdtl_curr_alloc_qty().add(detail.getAlloc_qty()));
				alloc_detail.setAllocdtl_mod_usr_id(detail.getReqdt_ins_user_id());
				alloc_detail.setAllocdtl_mod_ip_add(detail.getReqdt_ins_ip_add());
				alloc_detail.setAllocdtl_mod_dt(new Date());
				alloc_detail.setAllocdtl_cycle(Integer.valueOf(detail.getAlloc_cycle().toString()));
				this.transactionalRepository.update(alloc_detail);
			}
			else {
				alloc_detail = new Alloc_Detail();
				alloc_detail.setAlloc_Header(alloc_header);
				alloc_detail.setAllocdtl_alloc_qty(detail.getAlloc_qty());
				alloc_detail.setAllocdtl_alloc_type(header.getRequest_type());
				alloc_detail.setAllocdtl_alt_div_id(detail.getAlt_div_id());
				alloc_detail.setAllocdtl_blk_flg("0");
				alloc_detail.setAllocdtl_company(detail.getCompany());
				alloc_detail.setAllocdtl_curr_alloc_qty(detail.getAlloc_qty());
				alloc_detail.setAllocdtl_cycle(Integer.valueOf(detail.getAlloc_cycle().toString()));
				alloc_detail.setAllocdtl_div_id(detail.getAlt_div_id());
				alloc_detail.setAllocdtl_err_dtl("0");
				alloc_detail.setAllocdtl_fin_year(header.getFin_year());
				alloc_detail.setAllocdtl_fstaff_id(fsId);
				alloc_detail.setAllocdtl_ins_ip_add(detail.getReqdt_ins_ip_add());
				alloc_detail.setAllocdtl_ins_usr_id(detail.getReqdt_ins_user_id());
				alloc_detail.setAllocdtl_inv_grp(0L);
				alloc_detail.setAllocdtl_mod_dt(new Date());
				alloc_detail.setAllocdtl_mod_ip_add(detail.getReqdt_ins_user_id());
				alloc_detail.setAllocdtl_mod_usr_id(detail.getReqdt_mod_user_id());
				alloc_detail.setAllocdtl_period_code(detail.getPeriod_code());
				alloc_detail.setAllocdtl_phy_qty(BigDecimal.ZERO);
				alloc_detail.setAllocdtl_prod_id(detail.getProd_id());
				alloc_detail.setAllocdtl_rate(detail.getAlloc_rate());
				alloc_detail.setAllocdtl_smpd_id(detail.getProd_id());
				alloc_detail.setAllocdtl_status('A');
				alloc_detail.setAllocdtl_supply_qty(BigDecimal.ZERO);
				alloc_detail.setAllocdtl_appr_status('A');
				this.transactionalRepository.persist(alloc_detail);
			}
		}
		
	}
	/*
	 * public List<ViewAllocSummary> getViewAllocSummary(long division,String
	 * period_code) throws Exception { List<ViewAllocSummary> allocSummaries = null;
	 * Session session = HibernateUtil.getSessionFactory().openSession(); try {
	 * Query query = session.getNamedQuery("callView_Alloc_Summary_Procedure")
	 * .setParameter("div_Code", division) .setParameter("period_Code",
	 * period_code); allocSummaries = query.list();
	 * System.out.println("AllocHeaderDao.getViewAllocSummary()"+allocSummaries); }
	 * catch (Exception e) { throw e; } finally { session.close(); } return
	 * allocSummaries; }
	 */
}
