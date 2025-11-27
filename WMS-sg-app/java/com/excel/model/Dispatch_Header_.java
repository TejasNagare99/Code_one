package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Dispatch_Header.class)
public class Dispatch_Header_ {
	public static volatile SingularAttribute<Dispatch_Header, String> dspChallanNo;
	public static volatile SingularAttribute<Dispatch_Header, Long> dsp_sum_dsp_id;
	public static volatile SingularAttribute<Dispatch_Header, Long> blk_hcp_req_id;
	public static volatile SingularAttribute<Dispatch_Header, Long> dsp_cycle;
	public static volatile SingularAttribute<Dispatch_Header, Long> dsp_smp_id;
}
