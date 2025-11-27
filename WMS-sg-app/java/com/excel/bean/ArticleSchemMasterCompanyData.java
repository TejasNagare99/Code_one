package com.excel.bean;



public class ArticleSchemMasterCompanyData {

private String 	SubComp_id;
private String  SubComp_Nm ;
private String  SubComp_code ;



public String getSubComp_code() {
	return SubComp_code;
}
public void setSubComp_code(String subComp_code) {
	SubComp_code = subComp_code;
}
public String getSubComp_id() {
	return SubComp_id;
}
public void setSubComp_id(String subComp_id) {
	SubComp_id = subComp_id;
}
public String getSubComp_Nm() {
	return SubComp_Nm;
}
public void setSubComp_Nm(String subComp_Nm) {
	SubComp_Nm = subComp_Nm;
}
@Override
public String toString() {
	return "ArticleSchemMasterCompanyData [SubComp_id=" + SubComp_id + ", SubComp_Nm=" + SubComp_Nm + "]";
}

}
