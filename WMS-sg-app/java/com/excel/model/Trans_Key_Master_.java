package com.excel.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Trans_Key_Master.class)
public class Trans_Key_Master_ {
	
	public static volatile SingularAttribute<SmpBatch, Long> slno;
	public static volatile SingularAttribute<SmpBatch, String> company_cd;
	public static volatile SingularAttribute<SmpBatch, Long> loc_id;
	public static volatile SingularAttribute<SmpBatch, Long> inv_grp_id;
	public static volatile SingularAttribute<SmpBatch, Long> tran_type_id;
	public static volatile SingularAttribute<SmpBatch, String> prefix;
	public static volatile SingularAttribute<SmpBatch, Character> out_state_ind;
	public static volatile SingularAttribute<SmpBatch, String> last_num;
	public static volatile SingularAttribute<SmpBatch, String> heading;
	public static volatile SingularAttribute<SmpBatch, String> fin_year;
	public static volatile SingularAttribute<SmpBatch, Long> post_gl_id;
	public static volatile SingularAttribute<SmpBatch, String> lc;
	public static volatile SingularAttribute<SmpBatch, String> type;
	public static volatile SingularAttribute<SmpBatch, Long> stock_point_id;
	
}
