package com.excel.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.excel.bean.GoApptiveTerrMasterBean;
import com.excel.bean.GoapptiveTeamBrandbean;
import com.excel.model.GoApptiveDspDetails;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoResources;

@Repository
public class GoApptiveRepositoryImpl implements GoApptiveRepository{
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<GoApptiveTerrMasterBean> getgoaptiveterrmasterdata(Long terrid) throws Exception {
		// TODO Auto-generated method stub
		List<GoApptiveTerrMasterBean> list =null;
		GoApptiveTerrMasterBean object = null;
		EntityManager em = null;
		try {
			StringBuffer sb = new StringBuffer();
			em = emf.createEntityManager();
			
			sb.append(" SELECT DIVISION,TERR_DIV_ID,TERR_CODE,TERR_ID,TERR_NAME,");
			sb.append(" TERR_LEVEL_CODE,TERR_LEVEL,TERR_TEAM_CODE,TEAM_NAME,TERR_STATE_CODE,STATE,TERR_POOL_IND,TERR_HQ_CODE,TERR_HQ_NAME");
			sb.append(" ,TERR_MGR1_TERRCODE,TERR_MGR2_TERRCODE,TERR_MGR3_TERRCODE,TERR_MGR4_TERRCODE,TERR_MGR5_TERRCODE,TERR_MGR6_TERRCODE,TERR_DOM_EXP,TERR_ZONECD,TERR_REGIONCD");
			sb.append(" ,TERR_DISTRICTCD,TERR_DESIGNATION,TERR_CFA_LOC_PFZ,TERR_CFA_LOC_PIPL,TERR_TRAINING,THIRDPARTY_DIV,MAIN_MAST_UPDATED,DATE_TIME_UPDATED,STATE_ID,TERR_CFA_LOC_PFZ_ID");
			sb.append(" ,TERR_CFA_LOC_PIPL_ID,ZONE_ID,HQ_ID,TERR_TEAM_ID");
			sb.append(" FROM(SELECT DV.DIV_DESC DIVISION,TM.TERR_CODE,TM.TERR_ID,TM.FIN_YEAR_ID,TM.COMPANY");
			sb.append(" ,TM.TERR_DIV_ID,TM.TERR_LEVEL_CODE,DF.LEVEL_NAME TERR_LEVEL,TM.TEAM_CODE TERR_TEAM_CODE,T.TEAM_NAME,TMSTCD.SGPRMDET_TEXT1 TERR_STATE_CODE,TM.TERR_NAME,TM.TERR_POOL_IND,HQ.HQ_CODE TERR_HQ_CODE");
			sb.append("  ,HQ.HQ_NAME TERR_HQ_NAME,TMDM.TERR_CODE TERR_MGR1_TERRCODE,TMRM.TERR_CODE TERR_MGR2_TERRCODE,TMSM.TERR_CODE TERR_MGR3_TERRCODE,");
			sb.append("  TMMG4.TERR_CODE TERR_MGR4_TERRCODE,TMMG5.TERR_CODE TERR_MGR5_TERRCODE,TMMG6.TERR_CODE TERR_MGR6_TERRCODE,");
			sb.append("  TM.TERR_DOM_EXP,TMZONE.SGPRMDET_NM TERR_ZONECD,TM.TERR_REGIONCD,TM.TERR_DISTRICTCD,TM.TERR_DESIGNATION,DLPFZ.DPTLOC_NAME TERR_CFA_LOC_PFZ");
			sb.append(" ,DLPIPL.DPTLOC_NAME TERR_CFA_LOC_PIPL,TM.TERR_TRAINING,'GOAPTIVE' THIRDPARTY_DIV,'Y' MAIN_MAST_UPDATED,GETDATE() DATE_TIME_UPDATED,");
			sb.append(" TMSTCD.SGPRMDET_ID STATE_ID,TMSTCD.SGPRMDET_DISP_NM STATE,DLPFZ.DPTLOC_ID TERR_CFA_LOC_PFZ_ID");
			sb.append(" ,DLPIPL.DPTLOC_ID TERR_CFA_LOC_PIPL_ID,TMZONE.SGPRMDET_ID ZONE_ID,HQ.HQ_ID HQ_ID,TM.TERR_TEAM_ID TERR_TEAM_ID,TT.TERR_CODE TT_TERR_CODE ");
			sb.append(" FROM TERR_MASTER TM LEFT JOIN HQMASTER HQ ON HQ.HQ_ID = TM.TERR_HQ_ID");
			sb.append(" LEFT JOIN TERR_MASTER TMDM ON TMDM.TERR_ID=TM.TERR_MGR1_ID");
			sb.append(" LEFT JOIN TERR_MASTER TMRM ON TMRM.TERR_ID=TM.TERR_MGR2_ID");
			sb.append(" LEFT JOIN TERR_MASTER TMSM ON TMSM.TERR_ID=TM.TERR_MGR3_ID");
			sb.append(" LEFT JOIN TERR_MASTER TMMG4 ON TMMG4.TERR_ID=TM.TERR_MGR4_ID");
			sb.append("  LEFT JOIN TERR_MASTER TMMG5 ON TMMG5.TERR_ID=TM.TERR_MGR5_ID");
			sb.append(" LEFT JOIN TERR_MASTER TMMG6 ON TMMG6.TERR_ID=TM.TERR_MGR6_ID");

			sb.append(" LEFT JOIN SG_D_PARAMETERS_DETAILS TMSTCD ON TMSTCD.SGPRMDET_ID=TM.TERR_STATE_ID");
			sb.append("  LEFT JOIN SG_D_PARAMETERS_DETAILS TMZONE ON TMZONE.SGPRMDET_ID=TM.TERR_ZONE_ID");
			sb.append(" LEFT JOIN DEPOT_LOCATIONS DLPFZ ON DLPFZ.DPTLOC_ID=TM.TERR_CFA_LOC");
			sb.append("  LEFT JOIN DEPOT_LOCATIONS DLPIPL ON DLPIPL.DPTLOC_ID=TM.TERR_CFA3_LOC3");
		    sb.append(" LEFT JOIN TEAM T ON T.TEAM_CODE=TM.TEAM_CODE");
			sb.append(" JOIN DIV_MASTER DV ON DV.DIV_ID=TM.TERR_DIV_ID");
			sb.append(" LEFT OUTER JOIN DIV_FIELD DF ON DF.DIV_ID = TM.TERR_DIV_ID AND DF.LEVEL_CODE = TM.TERR_LEVEL_CODE AND DF.FS_CATEGORY = 'F' AND STATUS = 'A'");
			sb.append(" left join TEMP_TERRMAS tt on tt.terr_code=tm.terr_code ");
			sb.append(" WHERE  DV.GOAPTIVE_IND='Y' AND TM.TERR_STATUS='A'");
			sb.append(" )DTL");
			sb.append(" where tt_terr_code is  null");
			sb.append(" ORDER BY DIVISION,TERR_CODE");
			
			Query query = em.createNativeQuery(sb.toString(), Tuple.class);
			//query.setParameter("terrId", terrid);
			List<Tuple> tuples = query.getResultList();
			
			System.out.println("list : "+tuples.size());
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<GoApptiveTerrMasterBean>();
				for (Tuple t : tuples) {
					object = new GoApptiveTerrMasterBean();
					object.setTerr_code(t.get("TERR_CODE", String.class));
					object.setTerr_id(Long.valueOf(t.get("TERR_ID",Integer.class)));
					//object.setFin_year_id(Long.valueOf(t.get("FIN_YEAR_ID",String.class).trim()));
					//object.setCompany(t.get("COMPANY",String.class));
					object.setTerr_div_id(Long.valueOf(t.get("TERR_DIV_ID",Integer.class)));
					object.setTerr_level_code(t.get("TERR_LEVEL_CODE",String.class));
					object.setTerr_team_code(t.get("TERR_TEAM_CODE",String.class));
					object.setTerr_state_code(t.get("TERR_STATE_CODE",String.class));
					object.setTerr_name(t.get("TERR_NAME",String.class));
					object.setTerr_pool_ind(String.valueOf(t.get("TERR_POOL_IND",Character.class)));
					object.setTerr_hq_code(t.get("TERR_HQ_CODE",String.class));
					object.setTerr_hq_name(t.get("TERR_HQ_NAME",String.class));
					object.setTerr_mgr1_terrcode(t.get("TERR_MGR1_TERRCODE",String.class));
					object.setTerr_mgr2_terrcode(t.get("TERR_MGR2_TERRCODE",String.class));
					object.setTerr_mgr3_terrcode(t.get("TERR_MGR3_TERRCODE",String.class));
					object.setTerr_mgr4_terrcode(t.get("TERR_MGR4_TERRCODE",String.class));
					object.setTerr_mgr5_terrcode(t.get("TERR_MGR5_TERRCODE",String.class));
					object.setTerr_mgr6_terrcode(t.get("TERR_MGR6_TERRCODE",String.class));
					object.setTerr_dom_exp(String.valueOf(t.get("TERR_DOM_EXP",Character.class)));
					object.setTerr_zonecd(t.get("TERR_ZONECD",String.class));
					object.setTerr_regioncd(t.get("TERR_REGIONCD",String.class));
					object.setTerr_districtcd(t.get("TERR_DISTRICTCD",String.class));
					object.setTerr_designation(t.get("TERR_DESIGNATION",String.class));
					object.setTerr_cfa_loc_pfz(t.get("TERR_CFA_LOC_PFZ",String.class));
					object.setTerr_cfa_loc_pipl(t.get("TERR_CFA_LOC_PIPL",String.class));
					object.setTerr_training(String.valueOf(t.get("TERR_TRAINING",Character.class)));
					object.setThirdparty_div(t.get("THIRDPARTY_DIV",String.class));
					object.setMain_mast_updated(String.valueOf(t.get("Main_MAST_UPDATED",String.class)));
					object.setDate_time_updated(t.get("DATE_TIME_UPDATED")!=null?t.get("DATE_TIME_UPDATED",Date.class).toString():null);
					object.setStateid(t.get("STATE_ID",Integer.class)==null?null:Long.valueOf(t.get("STATE_ID",Integer.class)));
					object.setTerr_cfa_loc_pfz_id(t.get("TERR_CFA_LOC_PFZ_ID",Integer.class)==null?null:Long.valueOf(t.get("TERR_CFA_LOC_PFZ_ID",Integer.class)));
					object.setTerr_cfa_loc_pipl_id(t.get("TERR_CFA_LOC_PIPL_ID",Integer.class)==null?null:Long.valueOf(t.get("TERR_CFA_LOC_PIPL_ID",Integer.class)));
					object.setZoneid(Long.valueOf(t.get("ZONE_ID",Integer.class))==null?null:Long.valueOf(t.get("ZONE_ID",Integer.class)));
       				object.setHq_id(t.get("HQ_ID",Integer.class)==null?null:Long.valueOf(t.get("HQ_ID",Integer.class)));
					object.setTerr_team_id(Long.valueOf(t.get("TERR_TEAM_ID",Integer.class)));
					list.add(object);
				}
			}
			if(list!=null) {
				System.out.println("Size "+list.size());
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public void insertgoaptivedata(List<String> terrCodes) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO TEMP_TERRMAS select GETDATE() DATE_TIME,GETDATE() DATE_TIME_RECD,'N' TERR_NEW_CHANGED,TERR_CODE,TERR_ID,FIN_YEAR_ID,COMPANY");
			sb.append(",TERR_DIV_ID,TERR_LEVEL_CODE,TERR_TEAM_CODE,TERR_STATE_CODE,TERR_NAME,TERR_POOL_IND,TERR_HQ_CODE");
			sb.append(",TERR_HQ_NAME,TERR_MGR1_TERRCODE,TERR_MGR2_TERRCODE,TERR_MGR3_TERRCODE,TERR_MGR4_TERRCODE,TERR_MGR5_TERRCODE,TERR_MGR6_TERRCODE");
			sb.append(",TERR_DOM_EXP,TERR_ZONECD,TERR_REGIONCD,TERR_DISTRICTCD,TERR_DESIGNATION,TERR_CFA_LOC_PFZ,TERR_CFA_LOC_PIPL,TERR_TRAINING,THIRDPARTY_DIV");
			sb.append(",Main_MAST_UPDATED,DATE_TIME_UPDATED,STATE_ID,TERR_CFA_LOC_PFZ_ID,TERR_CFA_LOC_PIPL_ID,ZONE_ID,HQ_ID,TERR_TEAM_ID");
			sb.append(" from(select tm.TERR_CODE,tm.TERR_ID,tm.FIN_YEAR_ID,tm.COMPANY,tm.TERR_DIV_ID,tm.TERR_LEVEL_CODE,tm.TEAM_CODE TERR_TEAM_CODE");
			sb.append(",tmstcd.SGprmdet_text1 TERR_STATE_CODE,tm.TERR_NAME,tm.TERR_POOL_IND,hq.hq_code TERR_HQ_CODE,hq.hq_name TERR_HQ_NAME,tmdm.terr_code TERR_MGR1_TERRCODE");
			sb.append(",tmrm.terr_code TERR_MGR2_TERRCODE,tmsm.terr_code TERR_MGR3_TERRCODE,'' TERR_MGR4_TERRCODE,'' TERR_MGR5_TERRCODE,'' TERR_MGR6_TERRCODE,tm.TERR_DOM_EXP");
			sb.append(",SUBSTRING(tmzone.SGprmdet_nm,1,10) TERR_ZONECD,tm.TERR_REGIONCD,tm.TERR_DISTRICTCD,tm.TERR_DESIGNATION,SUBSTRING(dlpfz.DPTLOC_NAME,1,30) TERR_CFA_LOC_PFZ");
			sb.append(",SUBSTRING(dlpipl.DPTLOC_NAME,1,30) TERR_CFA_LOC_PIPL,tm.TERR_TRAINING,'GOAPT' THIRDPARTY_DIV,'Y' Main_MAST_UPDATED,GETDATE() DATE_TIME_UPDATED,NULL STATE_ID");
			sb.append(",NULL TERR_CFA_LOC_PFZ_ID,NULL TERR_CFA_LOC_PIPL_ID,NULL ZONE_ID,NULL HQ_ID,NULL TERR_TEAM_ID,TT.TERR_CODE TT_TERR_CODE");
			sb.append(" from terr_master tm");
			sb.append(" left join hqmaster hq on hq.hq_id = tm.terr_hq_id");
			sb.append(" left join terr_master tmdm on tmdm.terr_id=tm.terr_mgr1_id");
			sb.append(" left join terr_master tmrm on tmrm.terr_id=tm.terr_mgr2_id");
			sb.append(" left join terr_master tmsm on tmsm.terr_id=tm.terr_mgr3_id");
			sb.append(" left join SG_d_parameters_details tmstcd on tmstcd.sgprmdet_id=tm.terr_state_id");
			sb.append(" left join SG_d_parameters_details tmzone on tmzone.sgprmdet_id=tm.terr_zone_id");
			sb.append(" left join DEPOT_LOCATIONS dlpfz on dlpfz.DPTLOC_ID=tm.TERR_CFA_LOC");
			sb.append(" left join DEPOT_LOCATIONS dlpipl on dlpipl.DPTLOC_ID=tm.TERR_CFA3_LOC3");
			sb.append(" left join TEMP_TERRMAS tt on tt.terr_code=tm.terr_code");
			sb.append(" join div_master dv on dv.div_id=tm.terr_div_id ");
			sb.append(" where  dv.GOAPTIVE_IND='Y' AND tm.TERR_CODE in(:terrCodes) AND TM.TERR_STATUS='A' ");
			sb.append(" )dtl where tt_terr_code is null");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("terrCodes",terrCodes);
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	@Override
	public List<GoapptiveTeamBrandbean> getgoaptiveteambranddata() throws Exception {
		// TODO Auto-generated method stub
		List<GoapptiveTeamBrandbean> list=null;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			StringBuffer sb = new StringBuffer();
			sb.append(" select dtl.fin_year_id,dtl.company,dtl.div_id,dtl.team_id,dtl.sa_prod_group_name,dtl.slno,dtl.TT_DIV_TEAM_BRAND");
			sb.append(" FROM");
			sb.append(" ( select dtb.fin_year_id,dtb.company,dtb.div_id,tm.team_code team_id,dtb.sa_prod_group_name,dtb.slno,");
			sb.append(" rtrim(convert(varchar(10),tt.div_id))+rtrim(convert(varchar(10),tt.team_id))+tt.SA_PROD_GROUP_NAME TT_DIV_TEAM_BRAND");
			sb.append(" from DIV_TEAM_BRAND dtb");
			sb.append(" left join team tm on tm.team_id=dtb.team_id");
			sb.append(" left join TEMP_TEAMBRAND tt on rtrim(convert(varchar(10),tt.div_id))+rtrim(convert(varchar(10),tt.team_id))+tt.SA_PROD_GROUP_NAME=rtrim(convert(varchar(10),dtb.div_id))+rtrim(convert(varchar(10),dtb.team_id))+dtb.SA_PROD_GROUP_NAME");
			sb.append(" )DTL");
			sb.append(" where dtl.TT_DIV_TEAM_BRAND is null");
			
			Query query = em.createNativeQuery(sb.toString(),Tuple.class);
			List<Tuple> tuples = query.getResultList();
			
			System.out.println("list : "+tuples.size());
			
			list = new ArrayList<GoapptiveTeamBrandbean>();
			if (tuples != null && !tuples.isEmpty()) {
				GoapptiveTeamBrandbean object = null;
				for (Tuple t : tuples) {
					object = new GoapptiveTeamBrandbean();
					object.setSlno(Long.valueOf(t.get("slno", Integer.class)));
					object.setFin_year_id(Long.valueOf(t.get("fin_year_id",String.class).trim()));
					object.setCompany(t.get("company",String.class));
					object.setDivid(Long.valueOf(t.get("div_id",Integer.class)));
					object.setTeamid(t.get("team_id",String.class));
					object.setSa_prod_groupname(t.get("sa_prod_group_name",String.class));
					object.setTt_div_team_brand(t.get("TT_DIV_TEAM_BRAND",String.class));
					
					list.add(object);
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public void insertTeamBrandData(List<String> terrCodes) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO TEMP_TEAMBRAND");
			sb.append(" select GETDATE() DATE_TIME,GETDATE() DATE_TIME_RECD,dtl1.fin_year_id,dtl1.company");
			sb.append(" ,dtl1.div_id,dtl1.team_id,dtl1.sa_prod_group_name,dtl1.SLNO,'N' Main_MAST_UPDATED");
			sb.append(" ,NULL DATE_TIME_UPDATED");
			sb.append(" FROM ( select dtl.fin_year_id,dtl.company,dtl.div_id,dtl.team_id,dtl.sa_prod_group_name,dtl.slno");
			sb.append(" from ( select dtb.fin_year_id,dtb.company,dtb.div_id,dtb.team_id,dtb.sa_prod_group_name,dtb.slno,");
			sb.append(" rtrim(convert(varchar(10),tt.div_id))+rtrim(convert(varchar(10),tt.team_id))+tt.SA_PROD_GROUP_NAME TT_DIV_TEAM_BRAND");
			sb.append(" from DIV_TEAM_BRAND dtb left join TEMP_TEAMBRAND tt on rtrim(convert(varchar(10),tt.div_id))+rtrim(convert(varchar(10),tt.team_id))+tt.SA_PROD_GROUP_NAME=rtrim(convert(varchar(10),dtb.div_id))+rtrim(convert(varchar(10),dtb.team_id))+dtb.SA_PROD_GROUP_NAME");
			sb.append(" )DTL");
			sb.append(" where dtl.TT_DIV_TEAM_BRAND is null");
			sb.append(" )DTL1");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("terrCodes",terrCodes);
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	@Override
	public List<GoApptiveDspDetails> pushDispToGoapptive(Date stdt, Date endt) throws Exception {
		// TODO Auto-generated method stub
		List<GoApptiveDspDetails> list = null;
		EntityManager em = null;
		try {
			System.out.println("pvfrmdt"+ MedicoResources.convertUtilDateToString_DD_MM_YYYY(stdt));
			System.out.println("pvtodt "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(endt));
			
			em = emf.createEntityManager();
			StoredProcedureQuery callDisptachSumDetReport  = em.createNamedStoredProcedureQuery("callsampledisptogoaptive");
		
			callDisptachSumDetReport.setParameter("pvfrmdt",MedicoResources.convertUtilDateToString_DD_MM_YYYY(stdt));//MedicoResources.convertUtilDateToString_DD_MM_YYYY(stdt)
			callDisptachSumDetReport.setParameter("pvtodt",MedicoResources.convertUtilDateToString_DD_MM_YYYY(endt)
					);//MedicoResources.convertUtilDateToString_DD_MM_YYYY(endt)
			list = callDisptachSumDetReport.getResultList();
			System.out.println("list of dispatch details ::"+list.size());
			if (list != null && !list.isEmpty()) {
				return list;
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		 finally {
			if(em != null) { em.close(); }
		}
		
			return null;
		}
	
	@Override
	public void insertDspData(List<Long> dspIds) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO DSP_SHARED_TEMP");
			sb.append(" (DATE_TIME,TERR_CODE,TERR_DIVISION,TERR_TEAM_CODE,DSP_ID,DSP_FIN_YEAR,DSP_PERIOD_CODE,");
			sb.append(" DSP_CHALLAN_NO,DSP_CHALLAN_DT,DSP_LR_NO,DSP_LR_DT,DSP_TRANSPORTER,DSPFSTAFF_NAME,DSPFSTAFF_EMPLOYEENO,");
			sb.append(" DSPDTL_PROD_NAME,DSPDTL_BATCH_NO,DSP_EXPIRY_DT,DSPDTL_DISP_QTY,DSPDTL_RATE,DSP_WT,DSP_CASES,");
			sb.append(" DSPFSTAFF_ADDR1,DSPFSTAFF_ADDR2,DSPFSTAFF_ADDR3,DSPFSTAFF_ADDR4,DSPFSTAFF_DESTINATION,DSPFSTAFF_TELNO,");
			sb.append(" DSPFSTAFF_MOBILE,DSPFSTAFF_EMAIL,DSPDTL_ID)");//,DSPDTL_RESENT
			sb.append(" SELECT DTL.DATE_TIME,DTL.TERR_CODE,DTL.TERR_DIVISION,DTL.TERR_TEAM_CODE,DTL.DSP_ID,DTL.DSP_FIN_YEAR,");
			sb.append(" DTL.DSP_PERIOD_CODE,DTL.DSP_CHALLAN_NO,DTL.DSP_CHALLAN_DT,DTL.DSP_LR_NO,DTL.DSP_LR_DT,");
			sb.append(" DTL.DSP_TRANSPORTER,DTL.DSPFSTAFF_NAME,DTL.DSPFSTAFF_EMPLOYEENO,DTL.DSPDTL_PROD_NAME,");
			sb.append(" DTL.DSPDTL_BATCH_NO,DTL.DSP_EXPIRY_DT,DTL.DSPDTL_DISP_QTY,DTL.DSPDTL_RATE,DTL.DSP_WT,DTL.DSP_CASES,");
			sb.append(" DTL.DSPFSTAFF_ADDR1,DTL.DSPFSTAFF_ADDR2,DTL.DSPFSTAFF_ADDR3,DTL.DSPFSTAFF_ADDR4,DTL.DSPFSTAFF_DESTINATION,");
			sb.append(" DTL.DSPFSTAFF_TELNO,DTL.DSPFSTAFF_MOBILE,DTL.DSPFSTAFF_EMAIL,DTL.DSPDTL_ID");//,DTL.DSPDTL_RESENT
			sb.append(" FROM");
			sb.append(" (SELECT");
			sb.append(" GETDATE() DATE_TIME,FS.FSTAFF_TERR_CODE TERR_CODE,DV.DIV_ID  TERR_DIVISION,FS.TEAM_CODE TERR_TEAM_CODE,");
			sb.append(" DH.DSP_ID,DH.DSP_FIN_YEAR,DH.DSP_PERIOD_CODE,DH.DSP_CHALLAN_NO,DH.DSP_CHALLAN_DT,");
			sb.append(" DH.DSP_LR_NO,DH.DSP_LR_DT,DH.DSP_TRANSPORTER,DH.DSPFSTAFF_NAME,DH.DSPFSTAFF_EMPLOYEENO,");
			sb.append(" SI.SMP_PROD_NAME DSPDTL_PROD_NAME,SB.BATCH_NO DSPDTL_BATCH_NO,SB.BATCH_EXPIRY_DT DSP_EXPIRY_DT,");
			sb.append(" DD.DSPDTL_DISP_QTY,DD.DSPDTL_RATE,DH.DSP_WT,DH.DSP_CASES,DH.DSPFSTAFF_ADDR1,DH.DSPFSTAFF_ADDR2,");
			sb.append(" DH.DSPFSTAFF_ADDR3,DH.DSPFSTAFF_ADDR4,DH.DSPFSTAFF_DESTINATION,DH.DSPFSTAFF_TELNO,DH.DSPFSTAFF_MOBILE,");
			sb.append(" DH.DSPFSTAFF_EMAIL,DD.DSPDTL_ID,DG.DSP_ID GOAPTIVE_DSP_ID");//,ISNULL(DH.DSPDTL_RESENT,'N') DSPDTL_RESENT
			sb.append(" FROM DISPATCH_HEADER DH");
			sb.append(" JOIN DISPATCH_DETAIL DD ON DH.DSP_ID=DD.DSPDTL_DSP_ID");
			sb.append("  LEFT OUTER JOIN  DSP_SHARED_TEMP DG ON DG.DSP_ID = DH.DSP_ID AND DG.DSP_FIN_YEAR = DH.DSP_FIN_YEAR");
			sb.append("  LEFT JOIN SMPITEM SI ON SI.SMP_PROD_ID = DSPDTL_PROD_ID");
			sb.append("  LEFT JOIN SMPBATCH SB ON SB.BATCH_ID = DSPDTL_BATCH_ID");
			sb.append(" LEFT JOIN FIELDSTAFF FS ON FS.FSTAFF_ID=DH.DSP_FSTAFF_ID");
			sb.append(" LEFT JOIN DIV_MASTER DV ON DV.DIV_ID=DH.DSP_DIV_ID");
			sb.append(" WHERE dh.dsp_id in(:dspIds)");
			sb.append("  AND (DH.DSP_LR_NO IS NOT NULL OR DH.DSP_LR_NO<>'' ) AND (DH.DSP_LR_DT IS NOT NULL)");
			sb.append(" AND DH.DSP_STATUS='A' AND DD.DSPDTL_STATUS='A' AND DH.DSP_APPR_STATUS='A'");
			sb.append("  )DTL");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("dspIds",dspIds);
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void pushDataToMainFiedldstaff() throws Exception {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("GOAPPTIVE_FIELDSTAFF_UPDATE");
		query.execute();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteGoapptiveTerritory(String terr_code,Long div_id) throws Exception{
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" delete from TEMP_TERRMAS  where TERR_CODE='").append(terr_code).append("'");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			throw e;
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateRecords() {
		EntityManager em = null;
		Date date=new Date();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String query="update TEMP_FSTAFFMAS set MAIN_MAST_UPDATED='D' where MAIN_MAST_UPDATED='N' ";
			em.createNativeQuery(query)
			.executeUpdate();
			em.getTransaction().commit();
		}
		catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if(em != null) { em.close(); }
		}
	}
		
}
