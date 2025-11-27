package com.excel.repository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.AUTO_GRN_PROCUDEURE_BEAN;
import com.excel.bean.BeansForCheckCstrf_Error_messages;
import com.excel.bean.Parameter;
import com.excel.bean.Stk_Grn_upload_err;
import com.excel.model.AUTO_TRANSFER;
import com.excel.model.Annual_sample_plan_view_report_cons;
import com.excel.model.CSPTRF;
import com.excel.model.GrnDetails;
import com.excel.model.GrnDetails_;
import com.excel.model.GrnHeader;
import com.excel.model.GrnHeader_;
import com.excel.model.PpgNextNumberCust;
import com.excel.model.Tax_data_for_grn;
import com.excel.model.V_GRN_Header;
import com.excel.model.V_Grn_Details;
import com.excel.model.V_Grn_Details_;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;

@Repository
public class GrnRepositoryImpl implements GrnRepository, MedicoConstants {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private EntityManagerFactory emf;
	static String newFilePath = null;

	@Autowired
	TransactionalRepository transactionalRepository;

	@Override
	public GrnHeader getObjectById(Long grnId) throws Exception {
		return this.entityManager.find(GrnHeader.class, grnId);
	}

	@Override
	public GrnDetails getObjectByDetailId(Long grnDetailId) throws Exception {
		return this.entityManager.find(GrnDetails.class, grnDetailId);
	}

	@Override
	public Long getMaxGrnId() throws Exception {
		EntityManager em = null;
		Long grnId = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
			Root<GrnHeader> root = criteriaQuery.from(GrnHeader.class);
			criteriaQuery.select(builder.max(root.get(GrnHeader_.grnId)));
			grnId = em.createQuery(criteriaQuery).getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return grnId;
	}

	@Override
	public BigDecimal getTotalGrnValueByGrnId(Long grnId, Long grnDetailId) throws Exception {
		// EntityManager em = null;
		BigDecimal value = null;
		try {
			// em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
			Root<GrnDetails> root = criteriaQuery.from(GrnDetails.class);
			if (grnDetailId == null) {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.grnd_value)))
						.where(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
								builder.equal(root.get(GrnDetails_.grnd_status), "A"));
			} else {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.grnd_value))).where(
						builder.and(builder.equal(root.get(GrnDetails_.grndGrnId), grnId)),
						builder.and(builder.notEqual(root.get(GrnDetails_.grndId), grnDetailId)));
			}

			value = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			throw e;
		}
//		finally {
//			if (em != null) { em.close(); }
//		}
		return value;
	}

	@Override
	public String getGrnNumber(String avField_Nm, String avTable_Nm, String avCharDigit, String avwherecondn)
			throws Exception {
		EntityManager em = null;
		String grnNumber = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createStoredProcedureQuery("PPG_NEXT_NUMBER_CUST");

			query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT);

			query.setParameter(1, avField_Nm);
			query.setParameter(2, avTable_Nm);
			query.setParameter(3, avCharDigit);
			query.setParameter(4, avwherecondn);
			query.execute();
			grnNumber = (String) query.getOutputParameterValue(5);
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return grnNumber;
	}

	public String getGrnNumbernew(String avField_Nm, String avTable_Nm, String avCharDigit, String avwherecondn)
			throws Exception {
		EntityManager em = null;
		String grnNumber = null;
		PpgNextNumberCust obj = null;
		try {
			em = emf.createEntityManager();
			StoredProcedureQuery query = em.createNamedStoredProcedureQuery("callPpgNextNumberCust");
			query.setParameter("avField_Nm", avField_Nm);
			query.setParameter("avTable_Nm", avTable_Nm);
			query.setParameter("avCharDigit", avCharDigit);
			query.setParameter("avwherecondn", avwherecondn);
			obj = (PpgNextNumberCust) query.getSingleResult();
			grnNumber = obj.getGrnId();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return grnNumber;
	}

	@Override
	public List<V_Grn_Details> getGrnDetailViewByGrnId(Long grnId) throws Exception {
		EntityManager em = null;
		List<V_Grn_Details> list = null;
		try {
			System.out.println("grnId" + grnId);
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<V_Grn_Details> criteriaQuery = builder.createQuery(V_Grn_Details.class);
			Root<V_Grn_Details> root = criteriaQuery.from(V_Grn_Details.class);
			criteriaQuery.select(root).where(builder.and(builder.equal(root.get(V_Grn_Details_.grndGrnId), grnId)))
					.orderBy(builder.asc(root.get(V_Grn_Details_.grndId)));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public boolean checkIfBatchExistsInGrnDetail(Long grnId, Long prodId, Long batchId) throws Exception {
		EntityManager em = null;
		List<GrnDetails> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<GrnDetails> criteriaQuery = builder.createQuery(GrnDetails.class);
			Root<GrnDetails> root = criteriaQuery.from(GrnDetails.class);
			criteriaQuery.select(root).where(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
					builder.equal(root.get(GrnDetails_.grnd_batch_id), batchId),
					builder.equal(root.get(GrnDetails_.grnd_prod_id), prodId),
					builder.equal(root.get(GrnDetails_.grnd_status), A));
			list = em.createQuery(criteriaQuery).getResultList();
			if (list != null && list.size() > 0) {
				System.out.println("true");
				return true;
			} else {
				System.out.println("false");
				return false;
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Long grndetailId) throws Exception {
		this.entityManager.createQuery(" DELETE FROM GrnDetails AS g WHERE g.grndId = :grndetailId ")
				.setParameter("grndetailId", grndetailId).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateBatchForGrn(Long batch_id, BigDecimal grn_qty) {
		System.out.println("In Batch Update For GRN " + batch_id + " " + grn_qty);
		StringBuffer Query = new StringBuffer();
		Query.append(
				" Update SmpBatch set batch_with_held_qty = batch_with_held_qty - :grn_qty where batch_id = :batch_id ");

		this.entityManager.createQuery(Query.toString()).setParameter("batch_id", batch_id)
				.setParameter("grn_qty", grn_qty).executeUpdate();

	}

	@Override
	public List<GrnDetails> getGrnDetailsByGrnId(Long grn_id) throws Exception {
		EntityManager em = null;
		List<GrnDetails> list = null;
		try {
			em = emf.createEntityManager();
			CriteriaBuilder builder = em.getCriteriaBuilder();
			CriteriaQuery<GrnDetails> criteriaQuery = builder.createQuery(GrnDetails.class);
			Root<GrnDetails> root = criteriaQuery.from(GrnDetails.class);
			criteriaQuery.select(root).where(builder.equal(root.get(GrnDetails_.grndGrnId), grn_id),
					builder.equal(root.get(GrnDetails_.grnd_status), "A"));
			list = em.createQuery(criteriaQuery).getResultList();
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateBatchQuarantine(Long batch_id, BigDecimal qty) {
		System.out.println("Saleable batch_id" + batch_id);
		this.entityManager.createQuery(
				" UPDATE SmpBatch set quanrantine = isnull(quanrantine,0) + :qty,batch_in_stock = isnull(batch_in_stock,0) - :qty,batch_with_held_qty=isnull(batch_with_held_qty,0) - :qty where batch_id = :batch_id ")// ,batch_with_held_qty=isnull(batch_with_held_qty,0)
																																																						// -
																																																						// :qty
				.setParameter("batch_id", batch_id).setParameter("qty", qty).executeUpdate();
	}

	@Override
	public Map<String, Object> getGrnApprovalHeaderDetail(@RequestParam String empId, @RequestParam String empLoc,
			String role, String compcode) {
		Map<String, Object> map = new HashMap<>();
		System.out.println("emp location is: " + empLoc + ".." + empId);
		String loginId = empId;
		List<V_GRN_Header> list = new ArrayList<>();

		EntityManager em = null;

		try {
			String q = null;
			Query query = null;
			em = emf.createEntityManager();
			if ("WAREH".equals(role) || "CFA".equals(role)) {
				if (compcode.trim().equals(PAL)) {
					q = "select grn_id,grn_dt_disp,grn_no,grn_lr_no,grn_lr_dt_disp,grn_transfer_no,grn_transfer_dt_disp,trantype,grn_company from v_GRN_HEADER where GRN_ins_usr_id=:empId and GRN_APPR_STATUS in('E','D') and GRN_CONFIRM='Y' and GRN_LOC_ID=:empLoc order by GRN_NO";
				} else {
					q = "select grn_id,grn_dt_disp,grn_no,grn_lr_no,grn_lr_dt_disp,grn_transfer_no,grn_transfer_dt_disp,trantype,grn_company from v_GRN_HEADER where GRN_ins_usr_id=:empId and GRN_APPR_STATUS in('E') and GRN_CONFIRM='Y' and GRN_LOC_ID=:empLoc order by GRN_NO";
				}
				query = em.createNativeQuery(q, Tuple.class);
				query.setParameter("empId", empId);
				query.setParameter("empLoc", empLoc);
			} else {
				q = "select grn_id,grn_dt_disp,grn_no,grn_lr_no,grn_lr_dt_disp,grn_transfer_no,grn_transfer_dt_disp,trantype,grn_company from v_GRN_HEADER where GRN_ins_usr_id= 'E00040' and GRN_APPR_STATUS='F' and GRN_LOC_ID=:empLoc order by GRN_NO";
				query = em.createNativeQuery(q, Tuple.class);
				query.setParameter("empLoc", empLoc);
			}

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					V_GRN_Header grnHeader = new V_GRN_Header();
					grnHeader.setGrn_id(Long.valueOf(t.get("grn_id", Integer.class)));
					grnHeader.setGrn_dt_disp(t.get("grn_dt_disp", String.class));
					grnHeader.setGrn_no(t.get("grn_no", String.class));
					grnHeader.setGrn_lr_no(t.get("grn_lr_no", String.class));
					grnHeader.setGrn_lr_dt_disp(t.get("grn_lr_dt_disp", String.class));
					grnHeader.setGrn_transfer_no(t.get("grn_transfer_no", String.class));
					grnHeader.setGrn_transfer_dt_disp(t.get("grn_transfer_dt_disp", String.class));
					grnHeader.setTrantype(t.get("trantype", String.class));
					grnHeader.setGrn_company(t.get("grn_company", String.class));
					list.add(grnHeader);
				}
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}

		map.put("loginId", loginId);
		map.put("list", list);
		return map;
	}

	@Override
	public Map<String, Object> getGrnApprovalSelectedDetail(@RequestParam long grnId) {
		System.out.println(grnId);
		Map<String, Object> map = new HashMap<>();
		EntityManager em = null;
		List<V_Grn_Details> list = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			String q = "select REMARK,  grnd_id,smp_prod_cd,smp_prod_name,batch_no,grnd_noofboxes,batch_expiry_dt_disp,batch_mfg_dt_disp,grnd_rate,grnd_qty,value,prod_type_short_nm,short,damage,grnd_pur_rate,value_pur from V_Grn_Details where GRND_GRN_ID=:grnId";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("grnId", grnId);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					V_Grn_Details grnDetails = new V_Grn_Details();
					grnDetails.setGrndId(Long.valueOf(t.get("grnd_id", Integer.class)));
					grnDetails.setSmp_prod_cd(t.get("smp_prod_cd", String.class));
					grnDetails.setSmp_prod_name(t.get("smp_prod_name", String.class));
					grnDetails.setBatch_no(t.get("batch_no", String.class));
					grnDetails.setGrnd_noofboxes(Long.valueOf(t.get("grnd_noofboxes", Integer.class)));
					grnDetails.setBatch_expiry_dt_disp(t.get("batch_expiry_dt_disp", String.class));
					grnDetails.setBatch_mfg_dt_disp(t.get("batch_mfg_dt_disp", String.class));
					grnDetails.setGrnd_rate(t.get("grnd_rate", BigDecimal.class));
					grnDetails.setGrnd_qty(t.get("grnd_qty", BigDecimal.class));
					grnDetails.setValue(t.get("value", BigDecimal.class));
					grnDetails.setProd_type_short_nm(t.get("prod_type_short_nm", String.class));
					grnDetails.setShortQty(t.get("short", BigDecimal.class));
					grnDetails.setDamage(t.get("damage", BigDecimal.class));
					grnDetails.setGrnd_pur_rate(t.get("grnd_pur_rate", BigDecimal.class));
					grnDetails.setValue_pur(t.get("value_pur", BigDecimal.class));
					grnDetails.setRemark(t.get("REMARK", String.class));
					list.add(grnDetails);
				}
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
		map.put("list", list);
		return map;

	}

	@Override
	public void updateDetailOnSelfApproveOfGRN(@RequestParam String empId, @RequestParam long grnId,
			@RequestParam String remark, HttpServletRequest request, List<Long> detailIds, List<BigDecimal> shortQtys,
			List<BigDecimal> damageQtys) {
		EntityManager em = null;
		System.out.println("Remark is " + remark);
		String ip_addr = request.getRemoteAddr();
		Date date = new Date();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String headerQuery = "UPDATE GRN_HEADER set GRN_APPR_STATUS='F',GRN_mod_usr_id=:empId,GRN_mod_dt=:date,GRN_mod_ip_add=:ip_addr,REMARKS=:remark,GRN_CONFIRM=:grn_confirm where grn_id=:grnId";
			em.createNativeQuery(headerQuery).setParameter("grnId", grnId).setParameter("date", date)
					.setParameter("ip_addr", ip_addr).setParameter("empId", empId).setParameter("remark", remark)
					.setParameter("grn_confirm", 'Y').executeUpdate();

			// newFilePath=

			for (int i = 0; i < detailIds.size(); i++) {
				String detailQuery = "UPDATE GRN_DETAILS set GRND_IMAGE_PATH=:newFilePath,SHORT=:shortQty,DAMAGE=:damageQty where GRND_GRN_ID=:grnId and GRND_ID=:detailId";
				em.createNativeQuery(detailQuery).setParameter("newFilePath", newFilePath)
						.setParameter("shortQty", shortQtys.get(i)).setParameter("damageQty", damageQtys.get(i))
						.setParameter("grnId", grnId).setParameter("detailId", detailIds.get(i)).executeUpdate();
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void updateDetailOnSelfDiscardOfGRN(@RequestParam String empId, @RequestParam long grnId,
			@RequestParam String remark, HttpServletRequest request) {
		EntityManager em = null;
		System.out.println("Remark is " + remark);
		String ip_addr = request.getRemoteAddr();
		Date date = new Date();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String query = "update GRN_HEADER set GRN_APPR_STATUS='D',GRN_mod_usr_id=:empId,GRN_mod_dt=:date,GRN_mod_ip_add=:ip_addr,REMARKS=:remark where grn_id=:grnId";
			em.createNativeQuery(query).setParameter("grnId", grnId).setParameter("date", date)
					.setParameter("ip_addr", ip_addr).setParameter("empId", empId).setParameter("remark", remark)
					.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public String uploadFile(MultipartFile file) {

		System.out.println("inside upload file");
		try {
			String rootPath = MedicoConstants.GRN_APPR_FILE_PATH;
			File dir = new File(rootPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File f = new File("file");
			String filename = file.getOriginalFilename().toString();
			System.out.println("orignoal file name:" + filename);
			String newFileString = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
			System.out.println(newFileString);
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFileString));
			stream.write(bytes);
			stream.close();

			newFilePath = newFileString;
			System.out.println("new file path is:" + newFileString);
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return newFilePath;
	}

	@Override
	public Object viewFile(@RequestParam long grnId) throws Exception {
		EntityManager em = null;
		System.out.println("grn id is:" + grnId);
		Map<String, Object> map = new HashMap<>();
		try {
			em = emf.createEntityManager();
			String q = "select GRND_IMAGE_PATH from GRN_DETAILS  where GRND_GRN_ID=:grnId";
			Query query = em.createNativeQuery(q, Tuple.class);
			query.setParameter("grnId", grnId);

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					String filePath = t.get("GRND_IMAGE_PATH", String.class);
					File f = new File(filePath);
					String fileName = f.getName();
					map.put("fileName", fileName);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getGrnDetailsForMailSend(Long grn_id, String last_approved_by, String selfApprove,
			String finyrid) throws Exception {
		System.out.println("grn_id " + grn_id + "last_approved_by" + last_approved_by);
		EntityManager em = null;
		Query query = null;
		StringBuffer buffer = new StringBuffer();
		List<Object> list = null;
		try {
			buffer.append(
					" SELECT gh.grn_id, gh.grn_no,CONVERT(VARCHAR(10), gh.grn_dt, 103) AS GRN_DT,gh.grn_transfer_no  AS Challan_no,");
			buffer.append(
					" gh.grn_supplier_id,su.sup_nm, gd.grnd_prod_id,si.smp_prod_type,si.smp_prod_name,sb.batch_no,");
			buffer.append(
					" gd.grnd_qty, gd.grnd_rate, gd.grnd_value,hrm.emp_email,hrminsert.emp_email emp_email_insert,");
			buffer.append(" ct.remarks,ct.APPR_LEVEL ,  ct1.MAXLEVEL,");
			buffer.append(
					" gd.GCMA_NUMBER, GD.GCMA_APROVAL_DT,CONVERT(VARCHAR(10), GD.GCMA_EXPIRY_DT, 103) AS GCMA_EXPIRY_DT,GD.GCMA_REQ_IND,GD.REMARK GCMA_REMARK,SI.SMP_PROD_CD AS PRODUCT_CODE,SI.SMP_ERP_PROD_CD AS FCODE,");
			buffer.append(
					" CASE WHEN GH.GRN_TYPE_ID=1498 THEN FS.FSTAFF_DISPLAY_NAME ELSE SU.SUP_NM END SUP_OR_FS_NAME,");
			buffer.append(
					" CASE WHEN GH.GRN_TYPE_ID=1498 THEN FS.FSTAFF_EMPLOYEE_NO ELSE SU.sup_map_code END SUP_OR_EMP_CODE,");
			buffer.append(" DV.DIV_DISP_NM,CONVERT(VARCHAR(10), GH.GRN_TRANSFER_DT , 103) AS GRN_TRANSFER_DT ,");
			buffer.append(
					" GH.GRN_PO_NO,GH.GRN_PO_DATE, CONVERT(VARCHAR(10), SB.BATCH_EXPIRY_DT , 103) AS BATCH_EXPIRY_DT ,");
			buffer.append(" GD.GRND_NOOFBOXES NO_OF_CASES, LC.LOC_NM");
			buffer.append(" FROM   grn_header gh");

			buffer.append(" JOIN grn_details gd ON grn_id = grnd_grn_id");
			buffer.append(" JOIN supplier su ON gh.grn_supplier_id = su.sup_id");
			buffer.append(" JOIN smpitem si  ON si.smp_prod_id = gd.grnd_prod_id");
			buffer.append(" JOIN smpbatch sb  ON sb.batch_id = gd.grnd_batch_id");
			buffer.append(" LEFT JOIN DIV_MASTER DV ON DV.DIV_ID=SI.SMP_STD_DIV_ID");
			buffer.append(" LEFT JOIN FIELDSTAFF FS ON FS.FSTAFF_ID=GH.RETURN_FROM_FS_ID");
			buffer.append(" JOIN LOCATION LC ON LC.LOC_ID = GH.GRN_LOC_ID");
			buffer.append(" JOIN custom_process_instance cp ON tran_ref_id =:grnId");
			buffer.append(" JOIN custom_task CT ON act_process_id = cp.process_instance_id");
			buffer.append(" JOIN hr_m_employee hrm ON hrm.emp_id =:emp_id");
			buffer.append(" JOIN hr_m_employee hrminsert ON Rtrim(hrminsert.emp_id) = Rtrim(gh.grn_ins_usr_id)");
			buffer.append(
					" JOIN (SELECT ACT_PROCESS_ID ,MAX(APPR_LEVEL) MAXLEVEL FROM custom_task GROUP BY ACT_PROCESS_ID ) CT1 ON CT1.act_process_id = cp.process_instance_id");
			buffer.append(" WHERE  gh.grn_id =:grnId  AND CP.FIN_YEAR_ID=:finyrid and ct1.MAXLEVEL= CT.APPR_LEVEL  ");
			if (selfApprove.equals("selfAppove"))
				buffer.append("and ct.completed_by=:emp_id");
			query = entityManager.createNativeQuery(buffer.toString());
			query.setParameter("grnId", grn_id);
			query.setParameter("emp_id", last_approved_by);
			query.setParameter("finyrid", finyrid);
			list = query.getResultList();
			System.out.println("LIST */********" + list.size());
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;

	}

	@Override
	public List getGrnDetailForGrnVoucherPrint(String sdate, String edate, String supId, String locId, String finyr,
			String finyrflg) {
		System.out.println("dates in repository is :" + sdate + ".." + edate + "..." + supId + "..." + locId);
		System.out.println("finyr :" + finyr + ".  finyrflg :: " + finyrflg);

		EntityManager em = null;
		Map<String, Object> map = new HashMap<>();
		List list = new ArrayList<>();
		Query query;
		try {
			em = emf.createEntityManager();
			if (supId.equals("0")) {
				StringBuffer q = new StringBuffer();
				if (finyrflg.equals(CURRENT)) {
					q.append(
							"SELECT GH.GRN_ID,GH.GRN_NO,GH.GRN_DT FROM GRN_HEADER GH , GRN_DETAILS GD ,  SUPPLIER S   WHERE  GH.GRN_ID = GD.GRND_GRN_ID AND GH.GRN_LOC_ID =:locId  ");
				} else {
					q.append(
							"SELECT GH.GRN_ID,GH.GRN_NO,GH.GRN_DT FROM GRN_HEADER_ARC GH , GRN_DETAILS_ARC GD ,  SUPPLIER S   WHERE  GH.GRN_ID = GD.GRND_GRN_ID AND GH.GRN_LOC_ID =:locId  ");
					q.append(" AND GH.GRN_FIN_YEAR = GD.GRND_FIN_YEAR");
				}
				q.append(" AND CONVERT(date,GH.GRN_DT,105) >= CONVERT(date,:sdate,105) ");
				q.append(" AND CONVERT(date,GH.GRN_DT,105) <= CONVERT(date,:edate,105) ");
				q.append(" AND GH.GRN_SUPPLIER_ID = S.SUP_ID ");
				q.append(" AND GH.GRN_STATUS='A' AND GH.GRN_LBL_PRINT IN ('Y','N')");
				q.append(" GROUP BY GH.GRN_ID,GH.GRN_NO,GH.GRN_DT  ORDER BY GH.GRN_NO");
				query = em.createNativeQuery(q.toString(), Tuple.class);
				query.setParameter("sdate", sdate);
				query.setParameter("edate", edate);
				query.setParameter("locId", locId);
			} else {
				StringBuffer q = new StringBuffer();
				if (finyrflg.equals(CURRENT)) {
					q.append(
							"SELECT GH.GRN_ID,GH.GRN_NO,GH.GRN_DT FROM GRN_HEADER GH , GRN_DETAILS GD ,  SUPPLIER S   WHERE   GH.GRN_ID = GD.GRND_GRN_ID AND GH.GRN_LOC_ID =:locId ");
				} else {
					q.append(
							"SELECT GH.GRN_ID,GH.GRN_NO,GH.GRN_DT FROM GRN_HEADER_ARC GH , GRN_DETAILS_ARC GD ,  SUPPLIER S   WHERE   GH.GRN_ID = GD.GRND_GRN_ID AND  GH.GRN_LOC_ID =:locId ");
					q.append(" AND GH.GRN_FIN_YEAR = GD.GRND_FIN_YEAR");
				}
				q.append(" AND GH.GRN_SUPPLIER_ID=:supId ");
				q.append(" AND CONVERT(date,GH.GRN_DT,105)>=CONVERT(date,:sdate,105) ");
				q.append(" AND CONVERT(date,GH.GRN_DT,105)<=CONVERT(date,:edate,105) ");
				q.append(" AND GH.GRN_SUPPLIER_ID = S.SUP_ID  ");
				q.append(" AND GH.GRN_STATUS='A' AND GH.GRN_LBL_PRINT IN ('Y','N')  ");
				q.append(" GROUP BY GH.GRN_ID,GH.GRN_NO,GH.GRN_DT  ORDER BY GH.GRN_NO");
				query = em.createNativeQuery(q.toString(), Tuple.class);
				query.setParameter("sdate", sdate);
				query.setParameter("edate", edate);
				query.setParameter("supId", supId);
				query.setParameter("locId", locId);
			}

			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				for (Tuple t : tuples) {
					Object[] obj = new Object[2];
					String grnNo = t.get("GRN_NO", String.class);
					obj[0] = t.get("GRN_NO", String.class);
					obj[1] = t.get("GRN_ID", Integer.class);
					list.add(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateBatchNSForGrn(Long batch_id, BigDecimal grn_qty, Long loc_id, String stock_type, String status,
			Long prodId) throws Exception {
		try {
			System.out.println("In Batch Update For GRN " + batch_id + " " + grn_qty + " " + loc_id + " " + stock_type
					+ " " + status + " " + prodId);
			this.entityManager.createQuery(
					" Update SmpBatchNS set batch_with_held_qty = batch_with_held_qty - :grn_qty where batch_id = :batch_id and batch_loc_id=:batch_loc_id "
							+ "and stock_type=:stock_type and batch_status=:batch_status and batch_prod_id=:batch_prod_id")
					.setParameter("batch_id", batch_id).setParameter("grn_qty", grn_qty)
					.setParameter("batch_loc_id", loc_id).setParameter("stock_type", stock_type)
					.setParameter("batch_status", status).setParameter("batch_prod_id", prodId).executeUpdate();
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateBatchNSQuarantine(Long batch_id, BigDecimal qty) {
		System.out.println("Non Saleable batch_id" + batch_id);
		this.entityManager.createQuery(
				" UPDATE SmpBatchNS set quanrantine = isnull(quanrantine,0) + :qty,batch_in_stock = isnull(batch_in_stock,0) - :qty,batch_with_held_qty=isnull(batch_with_held_qty,0) - :qty where batch_id = :batch_id ")// ,batch_with_held_qty=isnull(batch_with_held_qty,0)
																																																							// -
																																																							// :qty
				.setParameter("batch_id", batch_id).setParameter("qty", qty).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void IaaCreationInGrnApproval(Long grnId, String finYear, String periodCode, String userId, String ipAddress)
			throws Exception {
		try {
			System.out.println("grnId " + grnId);
			System.out.println("year " + finYear);
			System.out.println("Peeriod " + periodCode);
			System.out.println("last_approved_by " + userId);
			System.out.println("grnHeader.getGrn_ins_ip_add() " + ipAddress);
			StoredProcedureQuery procedurecall = entityManager.createStoredProcedureQuery("GRN_IAA");
			procedurecall.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
			procedurecall.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			procedurecall.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			procedurecall.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
			procedurecall.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			procedurecall.setParameter(1, grnId);
			procedurecall.setParameter(2, finYear);
			procedurecall.setParameter(3, periodCode);
			procedurecall.setParameter(4, userId);
			procedurecall.setParameter(5, ipAddress);

			procedurecall.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void confirmGrn(long grnId, String companyCode) {
		EntityManager em = null;
		Date date = new Date();
		String query = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			if (companyCode.trim().equals(PAL)) {
				query = "update GRN_HEADER set GRN_CONFIRM='Y',GRN_APPR_STATUS='E' where grn_id=:grnId";
			} else {
				query = "update GRN_HEADER set GRN_CONFIRM='Y' where grn_id=:grnId";
			}

			em.createNativeQuery(query).setParameter("grnId", grnId).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public Long getSupplierForReturnFromField(Long sub_com_id) throws Exception {
		String sup_id = null;
		System.out.println("sub_com_id " + sub_com_id);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select SUP_ID from supplier where  upper(SUBSTRING(sup_nm,1,17)) ='RETURN FROM FIELD'");
			sb.append(" AND SUP_SUBCOMP_ID=:sub_com_id");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("sub_com_id", sub_com_id);
			sup_id = query.getSingleResult().toString();
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
		}
		return Long.valueOf(sup_id);
	}

	@Override
	public BigDecimal getTotal_Pur_GrnValueByGrnId(Long grnId, Long grnDetailId) throws Exception {
		// EntityManager em = null;
		BigDecimal value = null;
		try {
			// em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
			Root<GrnDetails> root = criteriaQuery.from(GrnDetails.class);
			if (grnDetailId == null) {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.grnd_value_pur)))
						.where(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
								builder.equal(root.get(GrnDetails_.grnd_status), "A"));
			} else {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.grnd_value_pur))).where(
						builder.and(builder.equal(root.get(GrnDetails_.grndGrnId), grnId)),
						builder.and(builder.notEqual(root.get(GrnDetails_.grndId), grnDetailId)));
			}

			value = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			throw e;
		}
//		finally {
//			if (em != null) { em.close(); }
//		}
		return value;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Object> saveBeans(List<AUTO_TRANSFER> csptrf_beans, String ip_Address, String user_id,
			String currentLocation, String cURR_PERIOD, String finyr, String company) throws Exception {

		System.err.println("calling");

		List<Object> autoGrnData = new ArrayList<>();

		truncateTable();
		persistStkIntoAUTO_TRANSFER(csptrf_beans);
		autoGrnData = callProcuderForAutoGrn(ip_Address, user_id, currentLocation, cURR_PERIOD, finyr, company);

		System.err.println(autoGrnData.contains(autoGrnData) + ":::::::");

//		autoGrnData=callprocedure(  ip_Address, user_id,currentLocation, cURR_PERIOD, finyr,  company);
		System.out.println("saved");

		return autoGrnData;
	}

	private List<Object> callProcuderForAutoGrn(String ip_Address, String user_id, String currentLocation,
			String cURR_PERIOD, String finyr, String company) throws Exception {
		List<Stk_Grn_upload_err> autoGrnData = new ArrayList<>();
		List<Object> list = new ArrayList<>();
		try {

			System.out.println("ip ::" + ip_Address + "UserId:::" + user_id + "  Loc:::" + currentLocation
					+ "  cURR_PERIOD:::" + cURR_PERIOD + "fiyr ::: " + finyr + " company:::" + company);

			EntityManager em = emf.createEntityManager();

			StoredProcedureQuery AutoGRN = entityManager.createStoredProcedureQuery("p_iu_AutoGRN_JAVA");

			AutoGRN.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(8, Integer.class, ParameterMode.IN);
			AutoGRN.registerStoredProcedureParameter(9, Integer.class, ParameterMode.IN);

			AutoGRN.setParameter(1, company);
			AutoGRN.setParameter(2, finyr);
			AutoGRN.setParameter(3, cURR_PERIOD); // changed
			AutoGRN.setParameter(4, Integer.parseInt(currentLocation));
			AutoGRN.setParameter(5, user_id);
			AutoGRN.setParameter(6, ip_Address);
			AutoGRN.setParameter(7, "I");
			AutoGRN.setParameter(8, 0);
			AutoGRN.setParameter(9, 0);

			list = AutoGRN.getResultList();

			System.err.println("saved To pocedure And Size of Outcome::::" + list.size());

		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}
		return list;

	}

	public void persistStkIntoAUTO_TRANSFER(List<AUTO_TRANSFER> csptrf_beans) throws Exception {
		for (int i = 0; i < csptrf_beans.size(); i++) {
			AUTO_TRANSFER af = csptrf_beans.get(i);
			System.out.println(af);
			this.transactionalRepository.persist(af);
		}
	}

	private void truncateTable() throws Exception {

		entityManager.createNativeQuery("TRUNCATE TABLE AUTO_TRANSFER").executeUpdate();
	}

	@Override

	public Map<String, Object> search_auth_Code(String authcode) {

		List<CSPTRF> csptrfs = new ArrayList<>();
		String sl_number = "";
		Map<String, Object> map = new HashMap<>();
		try {

			StringBuffer sb = new StringBuffer();

//			sb.append("SELECT * FROM CSPTRF where AUTH_CODE='"+authcode+"' ORDER BY CSPTRF_ID");
			sb.append("SELECT top(1) CSPTRF_SLNO FROM CSPTRF where AUTH_CODE=:authcode ORDER BY CSPTRF_ID");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("authcode", authcode);
			sl_number = (String) query.getSingleResult().toString();
			map.put("SLNO", sl_number);
			map.put("CSPTRF_SLNO", true);
			System.out.println(sl_number);

		} catch (Exception e) {
			map = new HashMap<>();
			map.put("CSPTRF_SLNO", false);
		}

		return map;
	}

	@Override
	public Map<String, Object> checkErrMessages_fro_the_trf_number(String auth) {

		List<Tuple> errortuples = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		StringBuffer sb = new StringBuffer();

		try {
			sb.append(
					"SELECT   CSPTRF_TRF_NO,CONVERT(VARCHAR(10), CSPTRF_TRF_DATE,103)   CSPTRF_TRF_DATE ,CSPTRF_ERR_MSG  FROM CSPTRF where CSPTRF_SLNO IN(SELECT  top(1) CSPTRF_SLNO FROM CSPTRF WHERE AUTH_CODE='"
							+ auth.trim() + "' AND CSPTRF_ERR_MSG<>'' ORDER BY   CSPTRF_ID  desc)");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);

			errortuples = query.getResultList();
			List<BeansForCheckCstrf_Error_messages> error = new ArrayList<>();
			errortuples.forEach(l -> {
				BeansForCheckCstrf_Error_messages bean = new BeansForCheckCstrf_Error_messages();
				bean.setCSPTRF_TRF_NO(l.get(0).toString());
				bean.setCSPTRF_TRF_DATE(l.get(1).toString());
				bean.setCSPTRF_ERR_MSG(l.get(2).toString());

				error.add(bean);
			});

			if (error.size() > 0) {
				map.put("error", true);
				map.put("errorData", error);
			} else {
				map.put("error", false);
			}

		} catch (Exception e) {
			List<BeansForCheckCstrf_Error_messages> error = new ArrayList<>();
			map.put("errorData", error);
			map.put("error", false);
			e.printStackTrace();

		}

		return map;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Map<String, Object> callProcudeureForAutoGrn(String ip_Address, String user_id, String user_id2,
			String currentLocation, String cURR_PERIOD, String finyr, String company, String cSPTRF_SLNO,
			Map<String, Object> map) {

		System.out.println("ip ::" + ip_Address + "UserId:::" + user_id + "  Loc:::" + currentLocation
				+ "  cURR_PERIOD:::" + cURR_PERIOD + "fiyr ::: " + finyr + " company:::" + company + " cSPTRF_SLNO ::: "
				+ cSPTRF_SLNO);

		System.out.println("calling  test");

		List<AUTO_GRN_PROCUDEURE_BEAN> beanslist = new ArrayList<>();
		try {
			String queryString = "exec p_iu_AutoGRN_JAVA '" + company + "','" + finyr + "','" + cURR_PERIOD + "','"
					+ currentLocation + "','" + user_id + "','" + ip_Address + "','A'," + cSPTRF_SLNO + ",0";
			Query query = entityManager.createNativeQuery(queryString, Tuple.class);
			List<Tuple> tuples = query.getResultList();
			System.err.println(tuples.size());

			tuples.forEach(l -> {
				AUTO_GRN_PROCUDEURE_BEAN bean = new AUTO_GRN_PROCUDEURE_BEAN();

				bean.setGRN_NO(l.get(0).toString());

				bean.setGrn_company(l.get(1).toString());

				bean.setRec_loc_id(l.get(2).toString());

				bean.setFin_year(l.get(3).toString());

				bean.setPeriod_code(l.get(4).toString());

				bean.setDiv_id(l.get(5).toString());

				bean.setSmp_prod_cd(l.get(6).toString());

				bean.setSmp_prod_name(l.get(7).toString());

				bean.setBatch_No(l.get(8).toString());
				bean.setQty(l.get(9).toString());

				bean.setRate(l.get(10).toString());

				bean.setUser_id(l.get(11).toString());

				bean.setGrn_ins_time(l.get(12).toString().substring(0, 10));

//		convert_YYYY_MM_DD_toDate

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				Date date = null;
				String grn_ins_dates = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(bean.getGrn_ins_time());
					String formattedDate = formatter.format(date);
					grn_ins_dates = formattedDate.toString();

				} catch (Exception e) {

					e.printStackTrace();
				}

//		convert_YYYY_MM_DD_toDate

				bean.setGrn_ins_time(grn_ins_dates);

				bean.setUser_ip(l.get(13).toString());

				bean.setStatus(l.get(14).toString());

				beanslist.add(bean);

			});

			map.put("AUTO_GRN_PROCUDEURE_BEANs", beanslist);
			map.put("dataSourceGrnBean", true);
			map.put("Result", new StringBuffer("Auto Grn Completed"));

		} catch (Exception e) {
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put("dataSourceGrnBean", false);
		}

		return map;
	}

//		@Override
//		public Boolean savedOrNot(String authcode) {
//		
//			Boolean data_already_saved=false;
//			
//			List<CSPTRF> csptrfs=new ArrayList<>();
//			Map<String, Object> map=new HashMap<>();
//			
//			StringBuffer sb = new StringBuffer();
//
////			sb.append( "SELECT   CSPTRF_TRF_NO,CONVERT(VARCHAR(10), CSPTRF_TRF_DATE,103)   CSPTRF_TRF_DATE ,CSPTRF_ERR_MSG  FROM CSPTRF where CSPTRF_SLNO IN(SELECT  top(1) CSPTRF_SLNO FROM CSPTRF WHERE AUTH_CODE='"+authcode+"' AND CSPTRF_ERR_MSG<>'' ORDER BY   CSPTRF_ID  desc)");
//			
//			try {
//				
//
//			sb.append("select * from csptrf where AUTH_CODE='"+authcode+"'");
//			
//			Query query = entityManager.createNativeQuery(sb.toString(),CSPTRF.class);	
//		
//			csptrfs = query.getResultList();	
//			System.err.println(csptrfs.size()+"  :::::::::::size");
//			
//			if(csptrfs.size()>0) {
//				
//				 data_already_saved=true;
//			}else {
//				data_already_saved=false;
//			}
//			
//			} catch (Exception e) {
//				 data_already_saved=false;
//				e.printStackTrace();
//				
//			}
//			
//			return data_already_saved;
//		}

	// chnages errmsg

	@Override
	public Map<String, Object> savedOrNot(String authcode) {

		Boolean data_already_saved = false;

		List<Tuple> csptrfs = new ArrayList<>();
		List<BeansForCheckCstrf_Error_messages> beans = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();

		StringBuffer sb = new StringBuffer();

//		sb.append( "SELECT   CSPTRF_TRF_NO,CONVERT(VARCHAR(10), CSPTRF_TRF_DATE,103)   CSPTRF_TRF_DATE ,CSPTRF_ERR_MSG  FROM CSPTRF where CSPTRF_SLNO IN(SELECT  top(1) CSPTRF_SLNO FROM CSPTRF WHERE AUTH_CODE='"+authcode+"' AND CSPTRF_ERR_MSG<>'' ORDER BY   CSPTRF_ID  desc)");

		try {

//			sb.append(" select  distinct( CSPTRF_TRF_NO), CSPTRF_TRF_DATE from csptrf where AUTH_CODE='"+authcode+"'");
			sb.append(" select  CSPTRF_PROD_CD, CSPTRF_TRF_NO, CSPTRF_TRF_DATE from csptrf where AUTH_CODE='" + authcode
					+ "'");

			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);

			csptrfs = query.getResultList();
			System.err.println(csptrfs.size() + "  :::::::::::size");

			csptrfs.forEach(l -> {

				BeansForCheckCstrf_Error_messages bean = new BeansForCheckCstrf_Error_messages();

				bean.setCSPTRF_TRF_NO(l.get(1).toString());
				bean.setCSPTRF_TRF_DATE(l.get(2).toString().substring(0, 10));

//			convert_YYYY_MM_DD_toDate

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				Date date = null;
				String transferDate = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(bean.getCSPTRF_TRF_DATE());

					String formattedDate = formatter.format(date);
					transferDate = formattedDate.toString();

					System.err.println(date + "::::date");
					System.err.println(transferDate + ":::::::transferDate");

				} catch (Exception e) {

					e.printStackTrace();
				}

//			convert_YYYY_MM_DD_toDate
				bean.setCSPTRF_TRF_DATE(transferDate);

				bean.setCSPTRF_ERR_MSG("STN No ALREADY EXISTS");
				bean.setCSPTRF_PROD_CD(l.get(0).toString());
				beans.add(bean);

			});

			if (csptrfs.size() > 0) {

				map.put("error", true);
				map.put("errorData", beans);
				data_already_saved = true;

				map.put("SAVED", data_already_saved);
			} else {
				data_already_saved = false;
				map.put("SAVED", data_already_saved);
				map.put("error", false);
			}

		} catch (Exception e) {
			data_already_saved = false;
			map.put("SAVED", data_already_saved);
			map.put("error", false);
			e.printStackTrace();

		}

		return map;
	}

	@Override
	public Map<String, Object> checkSavedOrNot(String authenticationCode) {

		StringBuffer sb = new StringBuffer();
		List<GrnHeader> headers = new ArrayList<>();
		List<BeansForCheckCstrf_Error_messages> beans = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		try {
			sb.append(
					"  select * from GRN_HEADER where  GRN_TRANSFER_NO = (SELECT top(1) CSPTRF_TRF_NO  FROM CSPTRF where   AUTH_CODE='"
							+ authenticationCode + "' ORDER BY CSPTRF_ID)\r\n" + "");
			Query query = entityManager.createNativeQuery(sb.toString(), GrnHeader.class);

			headers = query.getResultList();

			headers.forEach(l -> {

				BeansForCheckCstrf_Error_messages bean = new BeansForCheckCstrf_Error_messages();
				bean.setCSPTRF_ERR_MSG("GRN Transfer Number Already Completed");
				bean.setCSPTRF_TRF_DATE(l.getGrn_trf_dt().toString().substring(0, 10));

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				Date date = null;
				String transferDate = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(bean.getCSPTRF_TRF_DATE());

					String formattedDate = formatter.format(date);
					transferDate = formattedDate.toString();

					System.err.println(date + "::::date");
					System.err.println(transferDate + ":::::::transferDate");

				} catch (Exception e) {

					e.printStackTrace();
				}

//				convert_YYYY_MM_DD_toDate

				bean.setCSPTRF_TRF_DATE(transferDate);

				bean.setCSPTRF_TRF_NO(l.getGrn_transfer_no().toString());
				beans.add(bean);

			});

			if (headers.size() > 0) {

				map.put("Saved", true);
				map.put("errorData", beans);
				map.put("error", true);
			} else {
				map.put("Saved", false);
			}
		} catch (Exception e) {
			map = new HashMap<>();
			map.put("Saved", false);
			e.printStackTrace();
		}

		return map;
	}
	
	@Override
	public BigDecimal getTotalCustDutyValueByGrnId(Long grnId, Long grnDetailId) throws Exception {
		// EntityManager em = null;
		BigDecimal value = null;
		try {
			// em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
			Root<GrnDetails> root = criteriaQuery.from(GrnDetails.class);
			if (grnDetailId == null) {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.value1)))
						.where(builder.and(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
								builder.equal(root.get(GrnDetails_.grnd_status), "A")));
			} else {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.value1))).where(
						builder.and(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
								builder.notEqual(root.get(GrnDetails_.grndId), grnDetailId),
								builder.equal(root.get(GrnDetails_.grnd_status), "A")));
			}

			value = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			throw e;
		}
//		finally {
//			if (em != null) { em.close(); }
//		}
		return value;
	}

	@Override
	public BigDecimal getTotalDiscountValueByGrnId(Long grnId, Long grnDetailId) throws Exception {
		// EntityManager em = null;
		BigDecimal value = null;
		try {
			// em = emf.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<BigDecimal> criteriaQuery = builder.createQuery(BigDecimal.class);
			Root<GrnDetails> root = criteriaQuery.from(GrnDetails.class);
			if (grnDetailId == null) {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.value2)))
						.where(builder.and(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
								builder.equal(root.get(GrnDetails_.grnd_status), "A")));
			} else {
				criteriaQuery.select(builder.sum(root.get(GrnDetails_.value2))).where(
						builder.and(builder.equal(root.get(GrnDetails_.grndGrnId), grnId),
								builder.notEqual(root.get(GrnDetails_.grndId), grnDetailId),
								builder.equal(root.get(GrnDetails_.grnd_status), "A")));
			}

			value = entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			throw e;
		}
//		finally {
//			if (em != null) { em.close(); }
//		}
		return value;
	}

	@Override
	public String getHeaderRemarks(long grnId) throws Exception{
		String remarks = "";
		Map<String, Object> map = new HashMap<>();
		try {

			StringBuffer sb = new StringBuffer();
			sb.append("select REMARKS from GRN_HEADER where GRN_ID=:grnId");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("grnId", grnId);
			remarks = (String) query.getSingleResult().toString();
			System.out.println(remarks);

		} catch (Exception e) {
	
//			e.printStackTrace();
		}

		return remarks;
	}

	@Override
	public Tax_data_for_grn getTaxData_And_total_value(Long grnId) throws Exception {
	List<Tax_data_for_grn> data = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select sum(GRND_VALUE) as grn_total_value,sum(GRND_VALUE_PUR) as gen_total_value_pur,");
			sb.append(" sum(ISNULL(SGST_BILL_AMT,0)) as sgst_total ,	");
			sb.append(" sum(ISNULL(CGST_BILL_AMT,0)) as cgst_total ,");
			sb.append(" sum(ISNULL(IGST_BILL_AMT,0)) as igst_total");
			sb.append(" from GRN_DETAILS where grnd_grn_id = :grnId and 	 GRND_status='A' and grnd_appr_status <> 'D'");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("grnId", grnId);
			
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				data = new ArrayList<>();
				for (Tuple t : tuples) {
					Tax_data_for_grn object = new Tax_data_for_grn();
					object.setSgst_total(t.get("sgst_total",BigDecimal.class));
					object.setCgst_total(t.get("cgst_total",BigDecimal.class));
					object.setIgst_total(t.get("igst_total",BigDecimal.class));
					object.setGen_total_value_pur(t.get("gen_total_value_pur",BigDecimal.class));
					object.setGrn_total_value(t.get("grn_total_value",BigDecimal.class));
					data.add(object);
				}
			}
			
			
			System.err.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data.get(0);
	}

}
