package com.excel.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "P_iu_dispatch_java")
@NamedStoredProcedureQuery(
		name="callSaveManualDispatch",
		procedureName = "p_iu_dispatch_java",
	//	procedureName = "p_iu_dispatch_java_TKEY",
		resultClasses = P_iu_dispatch_java.class,
		parameters = {
				@StoredProcedureParameter(name="pcDSP_COMPANY", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_FIN_YEAR", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_PERIOD_CODE", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="piDSP_LOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_ALLOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_SMP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_FSTAFF_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_DIV_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_RECVG_LOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_STATE_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pcDSP_prev_alloc_flg", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvDSP_usr_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvDSP_ip_add", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_status", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="psiDISP_CYCLE", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_ID_OUT", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="piDSP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSPDTL_PROD_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSPDTL_BATCH_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pncDSPDTL_DISP_QTY", mode = ParameterMode.IN, type = BigDecimal.class),
				@StoredProcedureParameter(name="pncDSPDTL_RATE", mode = ParameterMode.IN, type = BigDecimal.class),
				@StoredProcedureParameter(name="piDSPDTL_ALLOCDTL_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSPDTL_ALT_DIV_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piSUMDSP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piSUMDSP_ID_OUT", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="pcaction", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcStatus_flag", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="piprv_smpqty", mode = ParameterMode.IN, type = BigDecimal.class),
				@StoredProcedureParameter(name="piprv_allocid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piprv_allocdtlid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pvchallan_msg", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvchallan_PERIOD_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pvDSPchallan_no_out", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="pvSMchallan_no_out", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="pvDSP_APPR_REQ", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="Pdirect_to_pso_ind", mode = ParameterMode.IN, type = String.class),
		}
)

@NamedStoredProcedureQuery(
		name="callSaveManualDispatchTkey",
	//	procedureName = "p_iu_dispatch_java",
		procedureName = "p_iu_dispatch_java_TKEY",
		resultClasses = P_iu_dispatch_java.class,
		parameters = {
				@StoredProcedureParameter(name="pcDSP_COMPANY", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_FIN_YEAR", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_PERIOD_CODE", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="piDSP_LOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_ALLOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_SMP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_FSTAFF_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_DIV_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_RECVG_LOC_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_STATE_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pcDSP_prev_alloc_flg", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvDSP_usr_id", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvDSP_ip_add", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcDSP_status", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="psiDISP_CYCLE", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSP_ID_OUT", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="piDSP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSPDTL_PROD_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSPDTL_BATCH_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pncDSPDTL_DISP_QTY", mode = ParameterMode.IN, type = BigDecimal.class),
				@StoredProcedureParameter(name="pncDSPDTL_RATE", mode = ParameterMode.IN, type = BigDecimal.class),
				@StoredProcedureParameter(name="piDSPDTL_ALLOCDTL_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piDSPDTL_ALT_DIV_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piSUMDSP_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piSUMDSP_ID_OUT", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="pcaction", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pcStatus_flag", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="piprv_smpqty", mode = ParameterMode.IN, type = BigDecimal.class),
				@StoredProcedureParameter(name="piprv_allocid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="piprv_allocdtlid", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pvchallan_msg", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="pvchallan_PERIOD_ID", mode = ParameterMode.IN, type = Long.class),
				@StoredProcedureParameter(name="pvDSPchallan_no_out", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="pvSMchallan_no_out", mode = ParameterMode.OUT, type = String.class),
				@StoredProcedureParameter(name="pvDSP_APPR_REQ", mode = ParameterMode.IN, type = String.class),
				@StoredProcedureParameter(name="Pdirect_to_pso_ind", mode = ParameterMode.IN, type = String.class),
		}
)
public class P_iu_dispatch_java {
	
	@Id
	@Column(name="@piDSP_ID_OUT")
	private Long piDSP_ID_OUT;
	
	@Column(name="@piSUMDSP_ID_OUT")
	private Long piSUMDSP_ID_OUT;
	
	@Column(name="@pvDSPchallan_no_out")
	private String pvDSPchallan_no_out;
	
	@Column(name="@pvSMchallan_no_out")
	private String pvSMchallan_no_out;

	public Long getPiDSP_ID_OUT() {
		return piDSP_ID_OUT;
	}

	public void setPiDSP_ID_OUT(Long piDSP_ID_OUT) {
		this.piDSP_ID_OUT = piDSP_ID_OUT;
	}

	public Long getPiSUMDSP_ID_OUT() {
		return piSUMDSP_ID_OUT;
	}

	public void setPiSUMDSP_ID_OUT(Long piSUMDSP_ID_OUT) {
		this.piSUMDSP_ID_OUT = piSUMDSP_ID_OUT;
	}

	public String getPvDSPchallan_no_out() {
		return pvDSPchallan_no_out;
	}

	public void setPvDSPchallan_no_out(String pvDSPchallan_no_out) {
		this.pvDSPchallan_no_out = pvDSPchallan_no_out;
	}

	public String getPvSMchallan_no_out() {
		return pvSMchallan_no_out;
	}

	public void setPvSMchallan_no_out(String pvSMchallan_no_out) {
		this.pvSMchallan_no_out = pvSMchallan_no_out;
	}
	
	
	
	
}
