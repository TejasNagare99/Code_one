package com.excel.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Gst3bUploadJsonBean {

	private String gstin;
	private String ret_period;
	private SupplyDetails sup_details;
	private EligibleITC itc_elg;
	private InwardSupply inward_sup;
	private InterestAndLateFees intr_ltfee;
	private InterSupply inter_sup;

	static public class SupplyDetailsInner {

		private Double txval;

		private Double iamt;

		private Double camt;

		private Double samt;

		private Double csamt;

		public SupplyDetailsInner(Double txval, Double iamt, Double camt, Double samt, Double csamt) {
			this.txval = txval;
			this.iamt = iamt;
			this.camt = camt;
			this.samt = samt;
			this.csamt = csamt;
		}

		public SupplyDetailsInner() {
			this.txval = 0.00;
			this.iamt = 0.00;
			this.camt = 0.00;
			this.samt = 0.00;
			this.csamt = 0.00;
		}

		public Double getTxval() {
			return txval;
		}

		public void setTxval(Double txval) {
			this.txval = txval;
		}

		public Double getIamt() {
			return iamt;
		}

		public void setIamt(Double iamt) {
			this.iamt = iamt;
		}

		public Double getCamt() {
			return camt;
		}

		public void setCamt(Double camt) {
			this.camt = camt;
		}

		public Double getSamt() {
			return samt;
		}

		public void setSamt(Double samt) {
			this.samt = samt;
		}

		public Double getCsamt() {
			return csamt;
		}

		public void setCsamt(Double csamt) {
			this.csamt = csamt;
		}

	}

	static public class SupplyDetails {
		private SupplyDetailsInner osup_det;
		private SupplyDetailsInner osup_zero;
		private SupplyDetailsInner osup_nil_exmp;
		private SupplyDetailsInner isup_rev;
		private SupplyDetailsInner osup_nongst;

		public SupplyDetails() {
			this.osup_det = new SupplyDetailsInner();
			this.osup_zero = new SupplyDetailsInner();
			this.osup_nil_exmp = new SupplyDetailsInner();
			this.isup_rev = new SupplyDetailsInner();
			this.osup_nongst = new SupplyDetailsInner();
		}

		public SupplyDetailsInner getOsup_det() {
			return osup_det;
		}

		public void setOsup_det(SupplyDetailsInner osup_det) {
			this.osup_det = osup_det;
		}

		public SupplyDetailsInner getOsup_zero() {
			return osup_zero;
		}

		public void setOsup_zero(SupplyDetailsInner osup_zero) {
			this.osup_zero = osup_zero;
		}

		public SupplyDetailsInner getOsup_nil_exmp() {
			return osup_nil_exmp;
		}

		public void setOsup_nil_exmp(SupplyDetailsInner osup_nil_exmp) {
			this.osup_nil_exmp = osup_nil_exmp;
		}

		public SupplyDetailsInner getIsup_rev() {
			return isup_rev;
		}

		public void setIsup_rev(SupplyDetailsInner isup_rev) {
			this.isup_rev = isup_rev;
		}

		public SupplyDetailsInner getOsup_nongst() {
			return osup_nongst;
		}

		public void setOsup_nongst(SupplyDetailsInner osup_nongst) {
			this.osup_nongst = osup_nongst;
		}

	}

	static public class EligibleITCInner {

		private String ty;

		private Double iamt;

		private Double camt;

		private Double samt;

		private Double csamt;

		public EligibleITCInner(String ty) {
			this.ty = ty;
			this.iamt = 0.00;
			this.camt = 0.00;
			this.samt = 0.00;
			this.csamt = 0.00;
		}
		
		

		public EligibleITCInner(String ty, Double iamt, Double camt, Double samt, Double csamt) {
			this.ty = ty;
			this.iamt = iamt;
			this.camt = camt;
			this.samt = samt;
			this.csamt = csamt;
		}



		public String getTy() {
			return ty;
		}

		public void setTy(String ty) {
			this.ty = ty;
		}

		public Double getIamt() {
			return iamt;
		}

		public void setIamt(Double iamt) {
			this.iamt = iamt;
		}

		public Double getCamt() {
			return camt;
		}

		public void setCamt(Double camt) {
			this.camt = camt;
		}

		public Double getSamt() {
			return samt;
		}

		public void setSamt(Double samt) {
			this.samt = samt;
		}

		public Double getCsamt() {
			return csamt;
		}

		public void setCsamt(Double csamt) {
			this.csamt = csamt;
		}

	}

	static public class EligibleITC {
		public EligibleITCInner[] itc_avl;
		public EligibleITCInner[] itc_rev;
		public InterestAndLateFeesDetails itc_net;
		public EligibleITCInner[] itc_inelg;

		public EligibleITC() {
			this.itc_avl = new EligibleITCInner[5];
			this.itc_avl[0] = new EligibleITCInner("IMPG");
			this.itc_avl[1] = new EligibleITCInner("IMPS");
			this.itc_avl[2] = new EligibleITCInner("ISRC");
			this.itc_avl[3] = new EligibleITCInner("ISD");
			this.itc_avl[4] = new EligibleITCInner("OTH");

			this.itc_rev = new EligibleITCInner[2];
			this.itc_rev[0] = new EligibleITCInner("RUL");
			this.itc_rev[1] = new EligibleITCInner("OTH");

			this.itc_net = new InterestAndLateFeesDetails();

			this.itc_inelg = new EligibleITCInner[2];
			this.itc_inelg[0] = new EligibleITCInner("RUL");
			this.itc_inelg[1] = new EligibleITCInner("OTH");

		}

		public EligibleITCInner[] getItc_avl() {
			return itc_avl;
		}

		public void setItc_avl(EligibleITCInner[] itc_avl) {
			this.itc_avl = itc_avl;
		}

		public EligibleITCInner[] getItc_rev() {
			return itc_rev;
		}

		public void setItc_rev(EligibleITCInner[] itc_rev) {
			this.itc_rev = itc_rev;
		}

		public InterestAndLateFeesDetails getItc_net() {
			return itc_net;
		}

		public void setItc_net(InterestAndLateFeesDetails itc_net) {
			this.itc_net = itc_net;
		}

		public EligibleITCInner[] getItc_inelg() {
			return itc_inelg;
		}

		public void setItc_inelg(EligibleITCInner[] itc_inelg) {
			this.itc_inelg = itc_inelg;
		}

	}

	static public class InwardSupply {
		private InwardSupplyDetailsInner[] isup_details;

		public InwardSupply() {
			this.isup_details = new InwardSupplyDetailsInner[2];
			this.isup_details[0] = new InwardSupplyDetailsInner("GST");
			this.isup_details[1] = new InwardSupplyDetailsInner("NONGST");
		}

		public InwardSupplyDetailsInner[] getIsup_details() {
			return isup_details;
		}

		public void setIsup_details(InwardSupplyDetailsInner[] isup_details) {
			this.isup_details = isup_details;
		}

	}

	static public class InwardSupplyDetailsInner {

		private String ty;

		private Double inter;

		private Double intra;
		
		

		public InwardSupplyDetailsInner(String ty, Double inter, Double intra) {
			this.ty = ty;
			this.inter = inter;
			this.intra = intra;
		}

		public InwardSupplyDetailsInner(String ty) {
			this.ty = ty;
			this.inter = 0.00;
			this.intra = 0.00;
		}

		public String getTy() {
			return ty;
		}

		public void setTy(String ty) {
			this.ty = ty;
		}

		public Double getInter() {
			return inter;
		}

		public void setInter(Double inter) {
			this.inter = inter;
		}

		public Double getIntra() {
			return intra;
		}

		public void setIntra(Double intra) {
			this.intra = intra;
		}
	}

	static public class EmptyObject {

	}

	static public class InterestAndLateFees {

		private InterestAndLateFeesDetails intr_details;
		private Object ltfee_details;

		public InterestAndLateFees() {
			this.intr_details = new InterestAndLateFeesDetails();
			this.ltfee_details = new EmptyObject();
		}

		public InterestAndLateFeesDetails getIntr_details() {
			return intr_details;
		}

		public void setIntr_details(InterestAndLateFeesDetails intr_details) {
			this.intr_details = intr_details;
		}

		public Object getLtfee_details() {
			return ltfee_details;
		}

		public void setLtfee_details(Object ltfee_details) {
			this.ltfee_details = ltfee_details;
		}
	}

	static public class InterestAndLateFeesDetails {

		private Double iamt;

		private Double camt;

		private Double samt;

		private Double csamt;
		
		

		public InterestAndLateFeesDetails(Double iamt, Double camt, Double samt, Double csamt) {
			this.iamt = iamt;
			this.camt = camt;
			this.samt = samt;
			this.csamt = csamt;
		}

		public InterestAndLateFeesDetails() {
			this.iamt = 0.00;
			this.camt = 0.00;
			this.samt = 0.00;
			this.csamt = 0.00;
		}

		public Double getIamt() {
			return iamt;
		}

		public void setIamt(Double iamt) {
			this.iamt = iamt;
		}

		public Double getCamt() {
			return camt;
		}

		public void setCamt(Double camt) {
			this.camt = camt;
		}

		public Double getSamt() {
			return samt;
		}

		public void setSamt(Double samt) {
			this.samt = samt;
		}

		public Double getCsamt() {
			return csamt;
		}

		public void setCsamt(Double csamt) {
			this.csamt = csamt;
		}

	}

	static public class InterSupply {
		private List<InterSupplyInner> unreg_details;
		private List<InterSupplyInner> comp_details;
		private List<InterSupplyInner> uin_details;

		public InterSupply() {
			this.unreg_details = new ArrayList<InterSupplyInner>();
			this.comp_details = new ArrayList<InterSupplyInner>();
			this.uin_details = new ArrayList<InterSupplyInner>();
		}

		public List<InterSupplyInner> getUnreg_details() {
			return unreg_details;
		}

		public void setUnreg_details(List<InterSupplyInner> unreg_details) {
			this.unreg_details = unreg_details;
		}

		public List<InterSupplyInner> getComp_details() {
			return comp_details;
		}

		public void setComp_details(List<InterSupplyInner> comp_details) {
			this.comp_details = comp_details;
		}

		public List<InterSupplyInner> getUin_details() {
			return uin_details;
		}

		public void setUin_details(List<InterSupplyInner> uin_details) {
			this.uin_details = uin_details;
		}

	}

	static public class InterSupplyInner {

		private String pos;

		private Double txval;

		private Double iamt;

		public InterSupplyInner(String pos, Double txval, Double iamt) {
			this.pos = pos;
			this.txval = txval;
			this.iamt = iamt;
		}

		public String getPos() {
			return pos;
		}

		public void setPos(String pos) {
			this.pos = pos;
		}

		public Double getTxval() {
			return txval;
		}

		public void setTxval(Double txval) {
			this.txval = txval;
		}

		public Double getIamt() {
			return iamt;
		}

		public void setIamt(Double iamt) {
			this.iamt = iamt;
		}

	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getRet_period() {
		return ret_period;
	}

	public void setRet_period(String ret_period) {
		this.ret_period = ret_period;
	}

	public SupplyDetails getSup_details() {
		return sup_details;
	}

	public void setSup_details(SupplyDetails sup_details) {
		this.sup_details = sup_details;
	}

	public EligibleITC getItc_elg() {
		return itc_elg;
	}

	public void setItc_elg(EligibleITC itc_elg) {
		this.itc_elg = itc_elg;
	}

	public InwardSupply getInward_sup() {
		return inward_sup;
	}

	public void setInward_sup(InwardSupply inward_sup) {
		this.inward_sup = inward_sup;
	}

	public InterestAndLateFees getIntr_ltfee() {
		return intr_ltfee;
	}

	public void setIntr_ltfee(InterestAndLateFees intr_ltfee) {
		this.intr_ltfee = intr_ltfee;
	}

	public InterSupply getInter_sup() {
		return inter_sup;
	}

	public void setInter_sup(InterSupply inter_sup) {
		this.inter_sup = inter_sup;
	}

	public Gst3bUploadJsonBean() {
		this.gstin = " ";
		this.ret_period = " ";
		this.sup_details = new SupplyDetails();
		this.itc_elg = new EligibleITC();
		this.inward_sup = new InwardSupply();
		this.intr_ltfee = new InterestAndLateFees();
		this.inter_sup = new InterSupply();
	}

}
