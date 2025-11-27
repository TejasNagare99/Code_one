
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
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.AllocationBean;
import com.excel.bean.ErpBatchBean;
import com.excel.bean.ErpDispatchBean;
import com.excel.bean.ErpDispatchCancel;
import com.excel.bean.ErpProductBean;
import com.excel.bean.ErpStockAdjBean;
import com.excel.bean.ErpStockwithdrawalBean;
import com.excel.bean.Erp_Wms_Iaa_confirmationbean;
import com.excel.model.Erp_Batch_stk_reco;
import com.excel.model.Erp_Batch_stk_reco_quarantine;
import com.excel.utility.CodifyErrors;
import com.excel.bean.ErpGrnBean;
import com.excel.bean.ErpGrnConfirmationBean;
import com.excel.bean.ErpIaaGrn_bean;
import com.excel.bean.ErpNormal_Iaabean;

@Repository
public class ErpRepositoryImpl implements ErpRepository{
	@Autowired EntityManagerFactory emf;
	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public List<ErpProductBean> getProducts(String divid) {
		EntityManager em = null;
		List<ErpProductBean> list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" select distinct productcode,Organization,Product_Type,Storage_Condition,productname,UOM,productcategory,description,batchno,expirydate, ");
			sb.append(" brand,Weight,UOM_for_Weight,Shelf_Height,Shelf_Width,Shelf_Depth ");
			sb.append(" FROM ( ");
			sb.append(" SELECT C.erp_Comp_nm Organization ,ISNULL( PTYP.SGprmdet_Text2,' ' ) Product_Type,STG.SGprmdet_Text1 Storage_Condition,I.SMP_PROD_CD productcode, ");
			sb.append(" I.SMP_PROD_NAME productname,  'EACH' UOM,ISNULL( PTYP.SGprmdet_Text2,' ' ) productcategory,I.SMP_PROD_NAME description,'TRUE' batchno, ");
			sb.append(" 'TRUE' expirydate,ISNULL(UPPER(BR.SA_GROUP_NAME),'NA') brand,ISNULL(I.SMP_WEIGHT_STRIP,0) Weight,'KILOGRAM' UOM_for_Weight , ");
			sb.append(" ISNULL(I.SMP_HEIGHT_BOX,0) Shelf_Height,ISNULL(I.SMP_WIDTH_BOX,0) Shelf_Width,ISNULL(I.SMP_DEPTH_BOX,0) Shelf_Depth, BPR.productcode PR_CD  ");
			sb.append(" from sub_COMPANY C , SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID ");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details UOM ON UOM.SGprmdet_id = I.SMP_UOM_ID ");
			sb.append(" LEFT OUTER JOIN SAPRODGRP BR ON BR.SA_PROD_GROUP = I.SMP_SA_PROD_GROUP ");
			sb.append(" LEFT OUTER JOIN ERP_PRODUCT BPR ON BPR.productcode = I.SMP_PROD_CD ");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details PTYP ON PTYP.SGprmdet_id = I.SMP_PROD_TYPE_ID ");
			sb.append(" WHERE I.SMP_SubComp_id = C.SubComp_id ");
			sb.append(" AND C.SubComp_status ! = 'I' and I.smp_status='A'");
			sb.append(" ) d ");
	    	sb.append(" WHERE PR_CD IS NULL ");
			
			System.out.println("Product "+sb.toString());
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				ErpProductBean object = null;
				for (Tuple t : tuples) {
					object = new ErpProductBean();
					object.setOrg(t.get("Organization", String.class));
					object.setProductcode(t.get("productcode", String.class));
					object.setProductname(t.get("productname", String.class));
					object.setUOM(t.get("UOM", String.class));
					object.setProductcategory(t.get("productcategory", String.class));
					object.setDescription(t.get("description", String.class));
					object.setBatchno(t.get("batchno", String.class));
					object.setExpirydate(t.get("expirydate",String.class));		
					//manufactoring date here
					object.setBrand(t.get("brand", String.class));
					object.setWeight(t.get("Weight", BigDecimal.class));
					object.setUomforWeight(t.get("UOM_for_Weight", String.class));
					object.setShelfHeight(t.get("Shelf_Height", BigDecimal.class));
					object.setShelfWidth(t.get("Shelf_Width", BigDecimal.class));
					object.setShelfDepth(t.get("Shelf_Depth", BigDecimal.class));
					object.setProducttype(t.get("Product_Type", String.class));
					object.setStorageCondition(t.get("Storage_Condition", String.class));
					list.add(object);
				}
			System.out.println("ProductList "+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<ErpBatchBean> getBatch() {
		EntityManager em = null;
		List<ErpBatchBean> list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select Organization,SKU_Code,Lot_Name,Manufacturing_Date,Shelf_life_in_days,Expiration_Date,Status ");
			sb.append(" from ( ");
			sb.append(" SELECT C.erp_Comp_nm Organization ,I.SMP_PROD_CD SKU_CODE,B.BATCH_NO Lot_Name, ");
			sb.append(" Convert(CHAR(8), B.BATCH_MFG_DT,112) Manufacturing_Date, ");
			sb.append(" I.SMP_SHELF_LIFE Shelf_life_in_days,");
			sb.append(" Convert(CHAR(8), B.BATCH_EXPIRY_DT , 112 ) Expiration_Date, ");
			sb.append(" 'RESTRICTED' Status , BR.LOT_NAME SENT_LOT ");
			sb.append(" from sub_COMPANY C , SMPITEM I INNER JOIN SMPBATCH B ON B.BATCH_PROD_ID = I.SMP_PROD_ID AND B.BATCH_LOC_ID in (13,14)");
			sb.append(" LEFT OUTER JOIN ERP_BATCH BR ON BR.SKU_CODE = I.SMP_PROD_CD AND BR.Lot_Name = B.BATCH_NO ");
			sb.append(" WHERE I.SMP_SubComp_id = C.SubComp_id ");
			sb.append(" and C.SubComp_status ! = 'I' and I.smp_status='A' ");
			sb.append(" ) D ");
			sb.append(" WHERE D.SENT_LOT IS NULL ");
			System.out.println("Batch "+sb.toString());
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				ErpBatchBean object = null;
				for (Tuple t : tuples) {
					object = new ErpBatchBean();
					object.setOrg(t.get("Organization", String.class));
					object.setSku(t.get("SKU_Code", String.class));
					object.setLot(t.get("Lot_Name", String.class));
					object.setManufacturingDate(t.get("Manufacturing_Date", String.class));
					object.setExpiryDate(t.get("Expiration_Date", String.class));
					object.setShelf_life_in_days(Long .valueOf(t.get("Shelf_life_in_days", Integer.class)));		
					object.setStatus(t.get("Status", String.class));
					list.add(object);
				}
			System.out.println("BatchList "+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<ErpGrnBean> getGrn(String year) {
		EntityManager em = null;
		List<ErpGrnBean> list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT Organization,Product_Type,Storage_Condition,Document_Type,Document_No,Document_Date,Vendor_Name, ");
			sb.append(" Warehouse,PO_Reference_NO,Reference_No,Line_Num,SKU_Code,Qty,Lot_No,Expiry_Date,Lot_Printing_Name, ");
			sb.append(" Manfacturing_date,complete ");
			sb.append(" FROM ( ");
			sb.append(" SELECT C.erp_Comp_nm Organization,ISNULL( PTYP.SGprmdet_Text2,' ' ) Product_Type,STG.SGprmdet_Text1 Storage_Condition, ");
			sb.append(" CASE WHEN GTYP.SGprmdet_nm IN ( '02','03','04' ) THEN 'SALES RETURN' ELSE 'GRN' END  Document_Type, ");
			sb.append(" GH.GRN_ID Document_No,Convert(CHAR(8), GH.GRN_DT,112)  Document_Date,SM.SUP_NM Vendor_Name, ");
			sb.append(" CASE WHEN GH.GRN_STOCK_SA_OR_NS = 'SA' THEN ISNULL(GD.STOCK_TYPE,'01') ELSE ISNULL(GD.STOCK_TYPE,'04')  END Warehouse, ");
			sb.append(" GH.GRN_PO_NO PO_Reference_NO,GH.GRN_FIN_YEAR+'~'+GH.GRN_NO Reference_No,GD.GRND_ID  Line_Num,I.SMP_PROD_CD SKU_Code,GD.GRND_QTY Qty, ");
			sb.append(" B.BATCH_NO  Lot_No,CONVERT( CHAR(8) , B.BATCH_EXPIRY_DT , 112 ) Expiry_Date,B.BATCH_NO Lot_Printing_Name, ");
			sb.append(" CONVERT( CHAR(8) , B.BATCH_MFG_DT,112 ) Manfacturing_date,' ' complete , BR.GRN_ID BR_GRNID ");
			sb.append(" FROM GRN_DETAILS GD , Sub_Company C , SMPBatch B ,   ");
			sb.append(" GRN_HEADER GH LEFT OUTER JOIN SG_d_parameters_details GTYP ON GTYP.SGprmdet_id = GH.GRN_TYPE_ID   ");
			sb.append(" LEFT OUTER JOIN SUPPLIER SM ON SM.SUP_ID = GH.GRN_SUPPLIER_ID ");
			sb.append(" LEFT OUTER JOIN ERP_GRN BR ON BR.GRN_FIN_YEAR = GH.GRN_FIN_YEAR AND BR.GRN_ID = GH.GRN_ID, ");
			sb.append(" SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID ");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details PTYP ON PTYP.SGprmdet_id = I.SMP_PROD_TYPE_ID ");
			sb.append(" WHERE GH.GRN_FIN_YEAR =:year AND GH.GRN_ID = GD.GRND_GRN_ID AND GD.GRND_FIN_YEAR = GH.GRN_FIN_YEAR ");
			sb.append(" AND GD.GRND_PROD_ID = I.SMP_PROD_ID AND I.SMP_SubComp_id = C.SubComp_id ");
			sb.append(" AND GD.GRND_BATCH_ID = B.BATCH_ID AND GH.GRN_CONFIRM in('Y')  ) D ");
			sb.append(" WHERE BR_GRNID IS NULL ");
			sb.append(" ORDER BY Document_No,Line_Num ");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		    query.setParameter("year", year);
		    List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				ErpGrnBean object = null;
				for (Tuple t : tuples) {
					object = new ErpGrnBean();
					object.setOrganization(t.get("Organization", String.class));
					object.setProdtype(t.get("Product_Type", String.class));
					object.setStorageCondition(t.get("Storage_Condition", String.class));
					object.setDocumentType(t.get("Document_Type", String.class));
					object.setWarehouse(t.get("Warehouse", String.class));
					object.setPoRefNo(t.get("PO_Reference_NO", String.class));
					object.setRefNo(t.get("Reference_No", String.class));	
					object.setLineNo(t.get("Line_Num", Integer.class).toString());
					object.setSku(t.get("SKU_Code", String.class));
					object.setQty(t.get("Qty", BigDecimal.class));
					object.setLotNo(t.get("Lot_No", String.class));
					object.setExpiryDate(t.get("Expiry_Date", String.class));
					object.setLotPrintingName(t.get("Lot_Printing_Name", String.class));
					object.setManfacturingDate(t.get("Manfacturing_date", String.class));
					object.setComplete(t.get("complete", String.class));
					
					object.setDocument_no(t.get("Document_No", Integer.class).toString()); 
					object.setDocument_date(t.get("Document_Date", String.class));
					object.setVendor_name(t.get("Vendor_Name", String.class));
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
	public List<ErpGrnConfirmationBean> getGrnConfirmation(String year) {
		EntityManager em = null;
		List<ErpGrnConfirmationBean> list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT Organization,Product_Type,Storage_Condition,'GRN' confirmationFlag , Reason_Code,");
			sb.append(" IBD_No,Description,Product,Batch,Quantity,STOCK_TYPE,reference_no");
			sb.append(" FROM (");
			sb.append(" SELECT C.erp_Comp_nm Organization,ISNULL( PTYP.SGprmdet_Text2,' ' ) Product_Type,STG.SGprmdet_Text1 Storage_Condition,");
			sb.append(" '13' Reason_Code,GH.GRN_ID IBD_No,SPACE(25) Description,I.SMP_PROD_CD Product,B.BATCH_NO Batch,GD.GRND_QTY Quantity ,");
			sb.append(" CASE WHEN GH.GRN_STOCK_SA_OR_NS = 'SA' THEN ISNULL(GD.STOCK_TYPE,'01') ELSE ISNULL(GD.STOCK_TYPE,'04')  END STOCK_TYPE,");
			sb.append(" BR.GRN_ID BR_GRNID,GH.GRN_FIN_YEAR+'~'+GH.GRN_NO reference_no");
			sb.append(" FROM GRN_DETAILS GD , Sub_Company C , SMPBatch B ,");
			sb.append(" GRN_HEADER GH LEFT OUTER JOIN ERP_GRN_CONFIRM BR ON BR.GRN_FIN_YEAR = GH.GRN_FIN_YEAR AND BR.GRN_ID = GH.GRN_ID ,");
			sb.append(" SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details PTYP ON PTYP.SGprmdet_id = I.SMP_PROD_TYPE_ID");
			sb.append(" WHERE GH.GRN_FIN_YEAR = :year AND GH.GRN_CONFIRM='Y' AND GRN_STATUS='A' AND GH.GRN_APPR_STATUS  IN('A','D') AND GH.GRN_ID = GD.GRND_GRN_ID AND GD.GRND_FIN_YEAR = GH.GRN_FIN_YEAR");
			sb.append(" AND GD.GRND_PROD_ID = I.SMP_PROD_ID AND I.SMP_SubComp_id = C.SubComp_id");
			sb.append(" AND GD.GRND_BATCH_ID = B.BATCH_ID ) D");
			sb.append(" WHERE BR_GRNID IS NULL");
			sb.append(" ORDER BY IBD_NO,Product,Batch");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				  query.setParameter("year", year);
		    List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				
				ErpGrnConfirmationBean object = null;
				for (Tuple t : tuples) {
					object = new ErpGrnConfirmationBean();
					object.setOrganization(t.get("Organization", String.class));
				    object.setReason_code(t.get("Reason_Code", String.class));
				    object.setIbd_no(t.get("IBD_No", Integer.class).toString());
				    object.setDescription(t.get("Description", String.class));
				    object.setProduct(t.get("Product", String.class));
				    object.setBatch(t.get("Batch", String.class));
				    object.setQuantity(t.get("Quantity", BigDecimal.class));
				    object.setStockType(t.get("STOCK_TYPE", String.class));
				    object.setConfirmationFlag(t.get("confirmationFlag", String.class));
				    object.setStorage_condition(t.get("Storage_Condition", String.class));
				    object.setReference_no(t.get("reference_no", String.class));
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
	public List<ErpDispatchBean> getDispatch(String year) {
		EntityManager em = null;
		List<ErpDispatchBean> list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT Organization,Product_Type,Storage_Condition,ALLOC_TYPE,Document_Type,Business_Partner,Ship_To_,Bill_To,Warehouse,");
			sb.append(" OBD_Header_Num,Reference_No_PO,Order_Rec_DateTime,Expected_Delivery_DateTime,Description,");
			sb.append(" OBD_Line_Item_Num,Product,Sales_Order_Qty,Lot_No,Expirydate");
			sb.append(" FROM (");
			sb.append(" SELECT C.erp_Comp_nm Organization,ISNULL( PTYP.SGprmdet_Text2,' ' ) Product_Type,STG.SGprmdet_Text1 Storage_Condition,");
			sb.append(" AH.ALLOC_TYPE,");
			sb.append(" CASE WHEN AH.ALLOC_TYPE = 'M' THEN '1'");
			sb.append(" WHEN AH.ALLOC_TYPE = 'D' THEN '3'");
			sb.append(" WHEN AH.ALLOC_TYPE = 'E' THEN '4' ELSE '2' /*ADDL*/ END Document_Type,");
			sb.append(" DSPFSTAFF_DISPLAYNAME Business_Partner,DSPFSTAFF_ADDR1+DSPFSTAFF_ADDR2+DSPFSTAFF_ADDR3 Ship_To_,");
			sb.append(" DSPFSTAFF_ADDR1+DSPFSTAFF_ADDR2+DSPFSTAFF_ADDR3 Bill_To,/*L.LOC_NM*/ '01' Warehouse,");
			sb.append(" DH.DSP_ID OBD_Header_Num,DH.DSP_SUM_CHALLAN_NO+'~'+DH.DSP_CHALLAN_NO Reference_No_PO,");//DH.DSP_SUM_CHALLAN_NO+'~'+
			sb.append(" CONVERT(CHAR(8),AH.ALLOC_INS_DT,112) Order_Rec_DateTime,CONVERT(CHAR(8),DH.DSP_DT,112)  Expected_Delivery_DateTime,");
			sb.append(" SPACE(50) Description,DD.DSPDTL_ID OBD_Line_Item_Num,I.SMP_PROD_CD Product,DD.DSPDTL_DISP_QTY Sales_Order_Qty,");
			sb.append(" b.batch_no Lot_No,CONVERT(char(8),B.BATCH_EXPIRY_DT,112) Expirydate, BR.DSP_ID BR_DSPID");
			sb.append(" FROM Sub_Company C , DISPATCH_DETAIL DD, SMPBatch B , location L ,");
			sb.append(" SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID");
			 sb.append(" LEFT OUTER JOIN SG_d_parameters_details PTYP ON PTYP.SGprmdet_id = I.SMP_PROD_TYPE_ID,");
			sb.append(" DISPATCH_HEADER DH LEFT OUTER JOIN ALLOC_HEADER AH ON DH.DSP_ALLOC_ID = AH.ALLOC_ID");
			 sb.append(" LEFT OUTER JOIN ERP_DISPATCH BR ON BR.DSP_FIN_YEAR = DH.DSP_FIN_YEAR AND BR.DSP_ID = DH.DSP_ID");
			sb.append("  WHERE DH.DSP_FIN_YEAR =:year AND DSP_Status!='I' AND DH.DSP_APPR_STATUS IN ( 'F' ) AND DH.DSP_ID = DD.DSPDTL_DSP_ID AND DD.DSPDTL_FIN_YEAR = DH.DSP_FIN_YEAR");
			sb.append(" AND DD.DSPDTL_PROD_ID = I.SMP_PROD_ID AND I.SMP_SubComp_id = C.SubComp_id AND DD.DSPDTL_BATCH_ID = B.BATCH_ID");
			sb.append(" AND DH.DSP_LOC_ID = L.LOC_ID ) D");
			sb.append(" WHERE BR_DSPID IS NULL");
			sb.append(" ORDER BY Organization,Reference_No_PO");
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
		    List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				ErpDispatchBean object = null;
				for (Tuple t : tuples) {
					object = new ErpDispatchBean();
					object.setOrganization(t.get("Organization", String.class));
					object.setProduct_type(t.get("Product_Type", String.class));
					object.setStorage_condition(t.get("Storage_Condition", String.class));
					object.setDocument_type(t.get("Document_Type", String.class));
					object.setBusiness_partner(t.get("Business_Partner", String.class));
					object.setShip_to_(t.get("Ship_To_", String.class));
					object.setBill_to(t.get("Bill_To", String.class));
					object.setWarehouse(t.get("Warehouse", String.class));
					object.setObd_header_num(t.get("OBD_Header_Num", Integer.class).toString());
					object.setReference_no_po(t.get("Reference_No_PO", String.class));
					object.setOrder_rec_datetime(t.get("Order_Rec_DateTime", String.class));
					object.setExpected_delivery_datetime(t.get("Expected_Delivery_DateTime", String.class));
					object.setDescription(t.get("Description", String.class));
					object.setObd_line_item_num(t.get("OBD_Line_Item_Num", Integer.class).toString());
					object.setProduct(t.get("Product", String.class));
					object.setSales_order_qty(t.get("Sales_Order_Qty",BigDecimal.class));
					object.setLot_no(t.get("Lot_No", String.class));
					object.setExpirydate(t.get("Expirydate",String.class));
					list.add(object);
				}
			System.out.println("Dispatchs "+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}

	@Override
	public List<ErpDispatchCancel> getDispatchCancel(String year) {
		EntityManager em = null;
		List<ErpDispatchCancel>  list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT Organization,Product_Type,Storage_Condition,Document_Type,Business_Partner,Ship_To_,Bill_To,Warehouse,");
			sb.append(" OBD_Header_Num,Reference_No_PO,Order_Rec_DateTime,Expected_Delivery_DateTime,Description");
			sb.append(" FROM ( ");
			sb.append(" SELECT  C.erp_Comp_nm Organization,I.SMP_PROD_TYPE Product_Type,STG.SGprmdet_Text1 Storage_Condition,");
			sb.append(" CASE WHEN AH.ALLOC_TYPE = 'M' THEN '1' ");
			sb.append(" WHEN AH.ALLOC_TYPE = 'D' THEN '3' ");
			sb.append(" WHEN AH.ALLOC_TYPE = 'E' THEN '4' ELSE '2' /*ADDL*/ END Document_Type,");
			sb.append(" DSPFSTAFF_NAME Business_Partner,DSPFSTAFF_ADDR1+DSPFSTAFF_ADDR2+DSPFSTAFF_ADDR3 Ship_To_,");
			sb.append(" DSPFSTAFF_ADDR1+DSPFSTAFF_ADDR2+DSPFSTAFF_ADDR3 Bill_To,L.LOC_NM Warehouse,");
			sb.append(" DH.DSP_ID OBD_Header_Num,DH.DSP_SUM_CHALLAN_NO+'~'+DH.DSP_CHALLAN_NO Reference_No_PO,");
			sb.append(" AH.ALLOC_INS_DT Order_Rec_DateTime,DH.DSP_DT  Expected_Delivery_DateTime,");
			sb.append(" SPACE(50) Description , BR.DSP_ID BR_DSPID");
			sb.append(" FROM Sub_Company C , DISPATCH_DETAIL DD, SMPBatch B , location L , ");
			sb.append(" SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID, ");
			sb.append(" DISPATCH_HEADER DH LEFT OUTER JOIN ALLOC_HEADER AH ON DH.DSP_ALLOC_ID = AH.ALLOC_ID ");
			sb.append(" LEFT OUTER JOIN ERP_DISPATCH_CANCEL BR ON BR.DSP_FIN_YEAR = DH.DSP_FIN_YEAR AND BR.DSP_ID = DH.DSP_ID");
			sb.append(" WHERE DH.DSP_FIN_YEAR = :year AND DH.DSP_status = 'I' AND DH.DSP_ID = DD.DSPDTL_DSP_ID AND DD.DSPDTL_FIN_YEAR = DH.DSP_FIN_YEAR");
			sb.append(" AND DD.DSPDTL_PROD_ID = I.SMP_PROD_ID AND I.SMP_SubComp_id = C.SubComp_id AND DD.DSPDTL_BATCH_ID = B.BATCH_ID ");
			sb.append(" AND DH.DSP_LOC_ID = L.LOC_ID ) D");
			sb.append(" WHERE BR_DSPID IS NULL ");
			sb.append(" GROUP BY Organization,Product_Type,Storage_Condition,Document_Type,Business_Partner,Ship_To_,Bill_To,Warehouse,");
			sb.append(" OBD_Header_Num,Reference_No_PO,Order_Rec_DateTime,Expected_Delivery_DateTime,Description");
			sb.append(" ORDER BY Organization,Reference_No_PO");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		    query.setParameter("year", year);
		    List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				ErpDispatchCancel object = null;
				for (Tuple t : tuples) {
					object = new ErpDispatchCancel();
					object.setOrganization(t.get("Organization", String.class));
					object.setProduct_type(t.get("Product_Type", String.class));
					object.setStorage_condition(t.get("Storage_Condition", String.class));
					object.setDocument_type(t.get("Document_Type", String.class));
					object.setBusiness_partner(t.get("Business_Partner", String.class));
					object.setShip_to_(t.get("Ship_To_", String.class));
					object.setBill_to(t.get("Bill_To", String.class));
					object.setWarehouse(t.get("Warehouse", String.class));
					object.setObd_header_num(t.get("OBD_Header_Num", Integer.class).toString());
					object.setReference_no_po(t.get("Reference_No_PO", String.class));
					object.setOrder_rec_datetime(t.get("Order_Rec_DateTime", Date.class).toString());
					//object.setExpected_delivery_datetime(t.get("Expected_Delivery_DateTime", String.class));
					object.setDescription(t.get("Description", String.class));
					list.add(object);
				}
			System.out.println("ErpDispatchCancel "+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	public List<ErpStockAdjBean> getStockAdj(String year) {
		EntityManager em = null;
		List<ErpStockAdjBean> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" SELECT SH.STKADJ_ID Adjustmnet_No,ISNULL( SH.GRN_ID,0) WMS_Reference_No,");
			sb.append(" CASE WHEN SH.STKADJ_APPR_STATUS = 'A' THEN 'YES' ELSE 'NO' END IAA_Confirmation_Flag,");
			sb.append(" L.LOC_NM Location ,SPACE(50) Description,CONVERT(VARCHAR,IM.REASON_ID)+' - ' + IM.REASON_NM ReasonCode,");
			sb.append(" OTPR.SMP_PROD_CD Out_Product,OTBT.BATCH_NO Out_Batch,SD.ADJDTL_OUT_QTY Out_Qty,SD.ADJDTL_OUT_STK_TYPE Out_Stock_Type,");
			sb.append(" INPR.SMP_PROD_CD In_Product,INBT.BATCH_NO In_Batch,SD.ADJDTL_IN_QTY In_Qty,SD.ADJDTL_IN_STK_TYPE bIn_Stock_Type,");
			sb.append(" BR.STKADJ_ID BR_STKAJID");
			sb.append(" FROM STOCK_ADJ_DETAILS SD , SMPITEM INPR , SMPITEM OTPR,SMPBatch INBT, SMPBatch OTBT , ");
			sb.append(" LOCATION L , IAA_MAP IM , STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID");
			sb.append(" WHERE SH.STKADJ_FIN_YEAR= :year AND  ( SH.ERP_CREATED IS NULL OR SH.ERP_CREATED = 'N' ) ");
			sb.append(" AND SH.STKADJ_ID = SD.ADJDTL_STKADJ_ID AND SH.STKADJ_FIN_YEAR = SD.ADJDTL_FIN_YEAR ");
			sb.append(" AND SD.ADJDTL_OUT_ITEM_ID = OTPR.SMP_PROD_ID AND SD.ADJDTL_OUT_BATCH_ID = OTBT.BATCH_ID");
			sb.append(" AND SD.ADJDTL_IN_ITEM_ID = INPR.SMP_PROD_ID AND SD.ADJDTL_IN_BATCH_ID = INBT.BATCH_ID");
			sb.append(" AND SH.STKADJ_LOC_ID = L.LOC_ID");
			sb.append(" AND SD.ADJDTL_REASON_ID = IM.Reason_ID ");
			sb.append(" AND BR.STKADJ_ID IS NULL");
			sb.append(" ORDER BY SH.STKADJ_ID,SD.ADJDTL_ID");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
				  query.setParameter("year", year);
		    List<Tuple> tuples = query.getResultList();
			if (tuples != null && !tuples.isEmpty()) {
				list = new ArrayList<>();
				ErpStockAdjBean object = null;
				for (Tuple t : tuples) {
					object = new ErpStockAdjBean();
					object.setAdjustmnet_no(t.get("Adjustmnet_No", Integer.class).toString());
					object.setWms_reference_no(t.get("WMS_Reference_No", Integer.class).toString());
					object.setIaa_confirmation_flag(t.get("IAA_Confirmation_Flag", String.class));
					object.setLocation(t.get("Location", String.class));
					object.setDescription(t.get("Description", String.class));
					object.setReasoncode(t.get("ReasonCode", String.class));
					object.setOut_product(t.get("Out_Product", String.class));
					object.setOut_batch(t.get("Out_Batch", String.class));
					object.setOut_qty(t.get("Out_Qty", BigDecimal.class));
					object.setOut_stock_type(t.get("Out_Stock_Type", String.class));
					object.setIn_product(t.get("In_Product", String.class));
					object.setIn_batch(t.get("In_Batch", String.class));
					object.setIn_qty(t.get("In_Qty", BigDecimal.class));
					object.setIn_stock_type(t.get("bIn_Stock_Type", String.class));
					object.setBr_stkajid(Long.valueOf(t.get("BR_STKAJID", Integer.class)));
					list.add(object);
				}
				System.out.println("getProductDetails list::::"+list.size());
			if(!list.isEmpty() && list.size() > 0)
				return list;
	
			}
	
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpProduct(String divid,List<String> failedProducts) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			System.out.println("Product "+failedProducts);
			sb.append(" INSERT INTO ERP_PRODUCT ");
			sb.append(" select Organization,Product_Type,Storage_Condition,productcode,productname,UOM,productcategory,description,batchno,expirydate, ");
			sb.append(" brand,Weight,UOM_for_Weight,Shelf_Height,Shelf_Width,Shelf_Depth,DATE_OF_SENDING  ");
			sb.append(" FROM ( ");
			sb.append(" SELECT C.erp_Comp_nm Organization ,ISNULL( PTYP.SGprmdet_Text2,' ' ) Product_Type,STG.SGprmdet_Text1 Storage_Condition,I.SMP_PROD_CD productcode, ");
			sb.append(" I.SMP_PROD_NAME productname, 'EACH' UOM,ISNULL( PTYP.SGprmdet_Text2,' ' ) productcategory,I.SMP_PROD_NAME description,'Y' batchno, ");
			sb.append(" 'Y' expirydate,ISNULL(UPPER(BR.SA_GROUP_NAME),'NA') brand,ISNULL(I.SMP_WEIGHT_STRIP,0) Weight,'KILOGRAM' UOM_for_Weight , ");
			sb.append(" ISNULL(I.SMP_HEIGHT_BOX,0) Shelf_Height,ISNULL(I.SMP_WIDTH_BOX,0) Shelf_Width,ISNULL(I.SMP_DEPTH_BOX,0) Shelf_Depth, BPR.productcode PR_CD,GETDATE() DATE_OF_SENDING ");
			sb.append(" from sub_COMPANY C , SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID ");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details UOM ON UOM.SGprmdet_id = I.SMP_UOM_ID ");
			sb.append(" LEFT OUTER JOIN SAPRODGRP BR ON BR.SA_PROD_GROUP = I.SMP_SA_PROD_GROUP ");
			sb.append(" LEFT OUTER JOIN ERP_PRODUCT BPR ON BPR.productcode = I.SMP_PROD_CD ");
			sb.append(" LEFT OUTER JOIN SG_d_parameters_details PTYP ON PTYP.SGprmdet_id = I.SMP_PROD_TYPE_ID ");
			sb.append(" WHERE I.SMP_SubComp_id = C.SubComp_id AND C.SubComp_status ! = 'I' and I.smp_status='A'");
			sb.append(" AND I.SMP_PROD_CD not in(:failedProducts) ");
			sb.append(" ) d ");
			sb.append(" WHERE PR_CD IS NULL ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("failedProducts", failedProducts);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpBatch(List<String> failedBatch) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" INSERT INTO ERP_BATCH( Organization,SKU_Code,Lot_Name,Manufacturing_Date,Shelf_life_in_days,Expiration_Date,Status,DATE_OF_SENDING ) ");
			sb.append(" select Organization,SKU_Code,Lot_Name,Manufacturing_Date,Shelf_life_in_days,Expiration_Date,Status,GETDATE() DATE_OF_SENDING  ");
			sb.append(" from ( ");
			sb.append(" SELECT C.erp_Comp_nm Organization ,I.SMP_PROD_CD SKU_CODE,B.BATCH_NO Lot_Name, ");
			sb.append(" Convert(CHAR(8), B.BATCH_MFG_DT,112) Manufacturing_Date, ");
			sb.append(" I.SMP_SHELF_LIFE Shelf_life_in_days, ");
			sb.append(" Convert(CHAR(8), B.BATCH_EXPIRY_DT , 112 ) Expiration_Date, ");
			sb.append(" 'RESTRICTED' Status , BR.LOT_NAME SENT_LOT  ");
			sb.append(" from sub_COMPANY C , SMPITEM I INNER JOIN SMPBATCH B ON B.BATCH_PROD_ID = I.SMP_PROD_ID AND B.BATCH_LOC_ID  in(13,14)");
			sb.append(" LEFT OUTER JOIN ERP_BATCH BR ON BR.SKU_CODE = I.SMP_PROD_CD AND BR.Lot_Name = B.BATCH_NO ");
			sb.append(" WHERE I.SMP_SubComp_id = C.SubComp_id and C.SubComp_status ! = 'I' and I.smp_status='A'");
			sb.append(" and B.BATCH_NO not in(:failedBatch) ");
			sb.append(" ) D ");
			sb.append(" WHERE D.SENT_LOT IS NULL ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("failedBatch", failedBatch);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpGrn(List<String> list,String year) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO ERP_GRN( GRN_ID,GRN_NO,GRN_DT,GRN_COMPANY,GRN_FIN_YEAR,DATE_OF_SENDING ) ");
			sb.append(" SELECT GH.GRN_ID,GH.GRN_FIN_YEAR+'~'+GH.GRN_NO,GH.GRN_DT,GH.GRN_COMPANY,GH.GRN_FIN_YEAR,GETDATE() DATE_OF_SENDING ");
			sb.append(" FROM GRN_HEADER GH LEFT OUTER JOIN ERP_GRN BR ON BR.GRN_FIN_YEAR = GH.GRN_FIN_YEAR AND BR.GRN_ID = GH.GRN_ID ");
			sb.append(" WHERE GH.GRN_FIN_YEAR = '"+year+"' ");
			sb.append(" AND BR.GRN_ID IS NULL  AND GH.GRN_CONFIRM in('Y') AND GH.GRN_ID  in(:list)");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpGrnComfirm(List<String> list,String year) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			
			sb.append(" INSERT INTO ERP_GRN_CONFIRM( GRN_ID,GRN_NO,GRN_DT,GRN_COMPANY,GRN_FIN_YEAR,DATE_OF_SENDING  ) ");
			sb.append(" SELECT GH.GRN_ID,GH.GRN_FIN_YEAR+'~'+GH.GRN_NO,GH.GRN_DT,GH.GRN_COMPANY,GH.GRN_FIN_YEAR,GETDATE() DATE_OF_SENDING  ");
			sb.append(" FROM GRN_HEADER GH LEFT OUTER JOIN ERP_GRN_CONFIRM BR ON BR.GRN_FIN_YEAR = GH.GRN_FIN_YEAR AND BR.GRN_ID = GH.GRN_ID ");
			sb.append(" WHERE GH.GRN_FIN_YEAR = '"+year+"' AND GH.GRN_APPR_STATUS in('A','D') AND GH.GRN_CONFIRM='Y' ");
			sb.append(" AND  BR.GRN_ID IS NULL AND GH.GRN_ID  in(:list)");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpDispatch(String year,List<String> list) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO ERP_DISPATCH ( DSP_ID,DSP_NO,DSP_DT,DSP_COMPANY,DSP_FIN_YEAR,DATE_OF_SENDING) ");
			sb.append(" SELECT DH.DSP_ID,DH.DSP_NO,DH.DSP_DT,DH.DSP_COMPANY,DH.DSP_FIN_YEAR,GETDATE() DATE_OF_SENDING ");
			sb.append(" FROM DISPATCH_HEADER DH LEFT OUTER JOIN ERP_DISPATCH BR ON BR.DSP_FIN_YEAR = DH.DSP_FIN_YEAR AND BR.DSP_ID = DH.DSP_ID ");
			sb.append(" WHERE DH.DSP_FIN_YEAR = '"+year+"' AND DH.DSP_APPR_STATUS = 'F'  ");
			sb.append(" AND BR.DSP_ID IS NULL AND DH.DSP_ID in(:list)");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpDispatchCancel(List<String> list,String year) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO ERP_DISPATCH_CANCEL( DSP_ID,DSP_NO,DSP_DT,DSP_COMPANY,DSP_FIN_YEAR,DATE_OF_SENDING)");
			sb.append(" SELECT DH.DSP_ID,DH.DSP_NO,DH.DSP_DT,DH.DSP_COMPANY,DH.DSP_FIN_YEAR,GETDATE() DATE_OF_SENDING ");
			sb.append(" FROM DISPATCH_HEADER DH LEFT OUTER JOIN ERP_DISPATCH_CANCEL BR ON BR.DSP_FIN_YEAR = DH.DSP_FIN_YEAR AND BR.DSP_ID = DH.DSP_ID");
			sb.append(" WHERE DH.DSP_FIN_YEAR = '"+year+"' AND DH.DSP_status = 'I'");
			sb.append(" AND BR.DSP_ID IS NULL AND DH.DSP_ID  in(:list)");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateErpStockAdj(String year) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
//			sb.append(" INSERT INTO ERP_STKADJ( STKADJ_ID,STKADJ_DATE,STKADJ_NO,STKADJ_COMPANY,STKADJ_FIN_YEAR )");
//			sb.append(" SELECT SH.STKADJ_ID,SH.STKADJ_DATE,SH.STKADJ_NO,SH.STKADJ_COMPANY,SH.STKADJ_FIN_YEAR ");
//			sb.append(" FROM STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID");
//			sb.append(" WHERE SH.STKADJ_FIN_YEAR= '"+year+"' AND  BR.STKADJ_ID IS NULL");
			
			sb.append(" INSERT INTO ERP_STKADJ( STKADJ_ID,STKADJ_DATE,STKADJ_NO,STKADJ_COMPANY,STKADJ_FIN_YEAR,DATE_OF_SENDING )");
			sb.append(" SELECT SH.STKADJ_ID,SH.STKADJ_DATE,SH.STKADJ_NO,SH.STKADJ_COMPANY,SH.STKADJ_FIN_YEAR,GETDATE() DATE_OF_SENDING  ");
			sb.append(" FROM STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID");
			sb.append(" WHERE SH.STKADJ_FIN_YEAR= '"+year+"' ");
			sb.append(" AND  ( SH.ERP_CREATED IS NULL OR SH.ERP_CREATED = 'N' ) ");
			sb.append(" AND  BR.STKADJ_ID IS NULL");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}



	//from here
	@Override
	public List<ErpStockwithdrawalBean> getErpstockwithdrawaldata(String year) throws Exception {
		// TODO Auto-generated method stub
		List<ErpStockwithdrawalBean>  list = new ArrayList<>();
		
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SWV_ID,Organization,Product_Type,Storage_Condition,Document_Type,Business_Partner,Ship_To_,Bill_To,Warehouse, ");
		sb.append(" OBD_Header_Num,Reference_No_PO,Order_Rec_DateTime,Expected_Delivery_DateTime,Description, ");
		sb.append(" OBD_Line_Item_Num,Product,Sales_Order_Qty,Lot_No,Expirydate ");
		sb.append(" FROM ( ");
		sb.append(" SELECT SWH.SWV_ID,C.erp_Comp_nm Organization,ISNULL( PTYP.SGprmdet_Text2,' ' ) Product_Type,STG.SGprmdet_Text1 Storage_Condition, ");
		sb.append(" 'SW' Document_Type, ");
		sb.append(" SWH.SWV_SENDER_NAME Business_Partner,SWH.SWV_SENDER_ADDRESS1+SWH.SWV_SENDER_ADDRESS2+SWH.SWV_SENDER_ADDRESS3 Ship_To_, ");
		sb.append(" SWH.SWV_SENDER_ADDRESS1+SWH.SWV_SENDER_ADDRESS2+SWH.SWV_SENDER_ADDRESS3 Bill_To,SWD.SWVDTL_STOCK_TYPE Warehouse, ");
		sb.append(" SWH.SWV_ID OBD_Header_Num,SWH.SWV_CHALLAN_NO Reference_No_PO, ");
		sb.append(" CONVERT(CHAR(8),SWH.SWV_DSP_DT,112) Order_Rec_DateTime,CONVERT(CHAR(8),SWH.SWV_DSP_DT,112)  Expected_Delivery_DateTime, ");
		sb.append(" SPACE(50) Description,SWD.SWVDTL_ID OBD_Line_Item_Num,I.SMP_PROD_CD Product,SWD.SWVDTL_DISP_QTY Sales_Order_Qty, ");
		sb.append(" b.batch_no Lot_No,CONVERT(char(8),B.BATCH_EXPIRY_DT,112) Expirydate, BR.SWV_ID BR_DSPID ");
		sb.append(" FROM Sub_Company C , SWV_detail SWD, SMPBatch B , ");
		sb.append(" SWV_HEADER SWH LEFT OUTER JOIN ERP_STOCKWTH BR ON BR.SWV_FIN_YEAR = SWH.SWV_FIN_YEAR AND BR.SWV_ID = SWH.SWV_ID , ");
		sb.append(" SMPITEM I LEFT OUTER JOIN SG_d_parameters_details STG ON STG.SGprmdet_id = I.STORAGE_TYPE_ID ");
		sb.append(" LEFT OUTER JOIN SG_d_parameters_details PTYP ON PTYP.SGprmdet_id = I.SMP_PROD_TYPE_ID ");
		sb.append(" WHERE SWH.SWV_FIN_YEAR  =:year AND SWH.SWV_APPR_STATUS IN ('A' ) AND SWH.SWV_ID = SWD.SWVDTL_SWV_ID AND SWD.SWVDTL_FIN_YEAR = SWH.SWV_FIN_YEAR ");
		sb.append(" AND SWD.SWVDTL_PROD_ID = I.SMP_PROD_ID AND I.SMP_SubComp_id = C.SubComp_id AND SWD.SWVDTL_BATCH_ID = B.BATCH_ID ");
		sb.append(" ) D ");
		sb.append(" WHERE BR_DSPID IS NULL ");
		sb.append(" ORDER BY Organization,Reference_No_PO,OBD_Line_Item_Num ");
		
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		  query.setParameter("year", year);
		  List<Tuple> tuples = query.getResultList();
		  if (tuples != null && !tuples.isEmpty()) {
			  ErpStockwithdrawalBean object = null;
		for (Tuple t : tuples) {
			object = new ErpStockwithdrawalBean();
		
			object.setHeader_id(t.get("SWV_ID",Integer.class).toString());
			object.setOrganization(t.get("Organization",String.class));
			object.setProduct_type(t.get("Product_Type",String.class));
			object.setStorage_Condition(t.get("Storage_Condition",String.class));
			object.setDocument_Type(t.get("Document_Type",String.class));
			object.setBuisness_Partner(t.get("Business_Partner",String.class).trim());
			object.setShip_To(t.get("Ship_To_",String.class));
			object.setBill_To(t.get("Bill_To",String.class));
			object.setObd_Header_num(t.get("OBD_Header_Num",Integer.class));
			object.setRef_no_po(t.get("Reference_No_PO",String.class));
			object.setOrder_rec_dateTime(t.get("Order_Rec_DateTime",String.class));
			object.setExpected_Delivery_dateTime(t.get("Expected_Delivery_DateTime",String.class));
			object.setDescription(t.get("Description",String.class).trim());
			object.setObd_Line_Item_num(t.get("OBD_Line_Item_Num",Integer.class));
			object.setProduct(t.get("Product",String.class));
			object.setSales_Order_Quantity(t.get("Sales_Order_Qty",BigDecimal.class));
			object.setLot_no(t.get("Lot_No",String.class).trim());
			object.setExp_Date(t.get("Expirydate",String.class));
			object.setWarehouse(t.get("Warehouse", String.class));
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
	public void update_erpstockwithdrawal(List<String> list,String year) throws Exception {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO ERP_STOCKWTH ( SWV_ID,SWV_NO,SWV_DT,SWV_COMPANY,SWV_FIN_YEAR,DATE_OF_SENDING) ");
			sb.append(" SELECT SWH.SWV_ID,SWH.SWV_CHALLAN_NO,SWH.SWV_CHALLAN_DT,SWH.SWV_COMPANY,SWH.SWV_FIN_YEAR,GETDATE() DATE_OF_SENDING ");
			sb.append(" FROM SWV_HEADER SWH LEFT OUTER JOIN ERP_STOCKWTH BR ON BR.SWV_FIN_YEAR = SWH.SWV_FIN_YEAR AND BR.SWV_ID = SWH.SWV_ID ");
			sb.append(" WHERE SWH.SWV_FIN_YEAR='"+year+"' AND SWH.SWV_APPR_STATUS IN ('A' ) ");
			sb.append(" AND BR.SWV_ID IS NULL AND SWH.SWV_ID in(:list)");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	
	@Override
	public List<ErpNormal_Iaabean> getErp_NormalIaadata(String year) throws Exception {
		// TODO Auto-generated method stub
		List<ErpNormal_Iaabean>  list = new ArrayList<>();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT SH.STKADJ_NO,SH.STKADJ_ID Adjustmnet_No,ISNULL( GH.GRN_NO,SH.GRN_ID) WMS_Reference_No,");
			sb.append(" CASE WHEN SH.STKADJ_APPR_STATUS = 'A' THEN 'Y' ELSE 'N' END IAA_Confirmation_Flag,");
			sb.append(" C.erp_Comp_nm Organization  ,");
			sb.append(" SPACE(50) Description,CONVERT(VARCHAR,IM.REASON_ID) ReasonCode,");
			sb.append(" OSTG.SGprmdet_Text1 OUT_Storage_Condition,");
			sb.append(" OTPR.SMP_PROD_CD Out_Product,OTBT.BATCH_NO Out_Batch,SD.ADJDTL_OUT_QTY Out_Qty,SD.ADJDTL_OUT_STK_TYPE Out_Stock_Type,");
			sb.append(" ISTG.SGprmdet_Text1 IN_Storage_Condition,");
			sb.append(" INPR.SMP_PROD_CD In_Product,INBT.BATCH_NO In_Batch,SD.ADJDTL_IN_QTY In_Qty,SD.ADJDTL_IN_STK_TYPE In_Stock_Type,SH.STKADJ_LOC_ID");
			sb.append(" FROM Sub_Company C , STOCK_ADJ_DETAILS SD , SMPITEM INPR LEFT OUTER JOIN SG_d_parameters_details ISTG ON ISTG.SGprmdet_id = INPR.STORAGE_TYPE_ID ,");
			sb.append(" SMPITEM OTPR LEFT OUTER JOIN SG_d_parameters_details OSTG ON OSTG.SGprmdet_id = OTPR.STORAGE_TYPE_ID ,");
			sb.append(" SMPBatch INBT, SMPBatch OTBT ,");
			sb.append(" IAA_MAP IM , STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID");
			sb.append(" LEFT OUTER JOIN GRN_HEADER GH ON SH.GRN_ID = GH.GRN_ID ");           
			sb.append(" WHERE ( SH.GRN_ID=0 or GH.GRN_ID is null  ) AND SH.STKADJ_APPR_STATUS = 'A'");
			sb.append(" AND SH.STKADJ_FIN_YEAR=:year AND  ( SH.ERP_CREATED IS NULL OR SH.ERP_CREATED = 'N' )");
			sb.append(" AND SH.STKADJ_ID = SD.ADJDTL_STKADJ_ID AND SH.STKADJ_FIN_YEAR = SD.ADJDTL_FIN_YEAR");
			sb.append(" AND SD.ADJDTL_OUT_ITEM_ID = OTPR.SMP_PROD_ID AND SD.ADJDTL_OUT_BATCH_ID = OTBT.BATCH_ID");
			sb.append(" AND SD.ADJDTL_IN_ITEM_ID = INPR.SMP_PROD_ID AND SD.ADJDTL_IN_BATCH_ID = INBT.BATCH_ID");
			sb.append(" AND INPR.SMP_SubComp_id = C.SubComp_id");
			sb.append(" AND SD.ADJDTL_REASON_ID = IM.Reason_ID");
			sb.append(" AND  BR.STKADJ_ID IS NULL ");
			sb.append(" ORDER BY SH.STKADJ_ID,SD.ADJDTL_ID");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.setParameter("year", year);
			  List<Tuple> tuples = query.getResultList();
			  System.out.println("list : "+tuples.size());
			  if (tuples != null && !tuples.isEmpty()) {
				 
				ErpNormal_Iaabean object = null;
				for (Tuple t : tuples) {
					object = new ErpNormal_Iaabean();
					object.setOrganization(t.get("Organization",String.class));
					object.setAdjustment_No(Long.valueOf(t.get("Adjustmnet_No",Integer.class).toString()));
					object.setWms_Reference_No(t.get("WMS_Reference_No",String.class));
					object.setIaa_Confirmation_flag(t.get("IAA_Confirmation_Flag",String.class));
					object.setDescription(t.get("Description",String.class));
					object.setReasonCode(t.get("ReasonCode",String.class));
					object.setOut_Product(t.get("Out_Product",String.class));
					object.setOut_Batch(t.get("Out_Batch",String.class));
					object.setOut_Qty(t.get("Out_Qty",BigDecimal.class));
					object.setOut_Stock_type(t.get("Out_Stock_Type",String.class));
					object.setIn_Product(t.get("In_Product",String.class));
					object.setIn_Batch(t.get("In_Batch",String.class));
					object.setIn_Qty(t.get("In_Qty",BigDecimal.class));
					object.setIn_Stock_type(t.get("In_Stock_Type",String.class));
					object.setDocument_no(t.get("STKADJ_NO",String.class));
					object.setStorage_condition(t.get("OUT_Storage_Condition",String.class));
					object.setLocation(String.valueOf(t.get("STKADJ_LOC_ID",Integer.class)));
					list.add(object);
				}
			
			  } 
		}catch (Exception e) {
			// TODO: handle exception
			  throw e;
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpNormalIaadata(List<String> list,String year) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO ERP_STKADJ( STKADJ_ID,STKADJ_DATE,STKADJ_NO,STKADJ_COMPANY,STKADJ_FIN_YEAR,DATE_OF_SENDING ) ");
			sb.append(" SELECT SH.STKADJ_ID,SH.STKADJ_DATE,SH.STKADJ_NO,SH.STKADJ_COMPANY,SH.STKADJ_FIN_YEAR,GETDATE() DATE_OF_SENDING ");
			sb.append(" FROM STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID ");
			sb.append(" WHERE ( SH.GRN_ID IS NULL OR SH.GRN_ID = 0 ) AND SH.STKADJ_APPR_STATUS = 'A' ");
			sb.append(" AND SH.STKADJ_FIN_YEAR= '"+year+"' AND  ( SH.ERP_CREATED IS NULL OR SH.ERP_CREATED = 'N' ) ");
			sb.append(" AND  BR.STKADJ_ID IS NULL AND SH.STKADJ_ID not in(:list)");
			System.out.println("sb "+sb.toString());
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	public List<ErpIaaGrn_bean> getErp_IaaGrndata(String year) throws Exception {
		// TODO Auto-generated method stub
		
		List<ErpIaaGrn_bean> list = new ArrayList<>();
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SH.STKADJ_NO,SH.STKADJ_ID Adjustmnet_No,ISNULL( GH.GRN_NO,SH.GRN_ID) WMS_Reference_No,");
		sb.append(" CASE WHEN SH.STKADJ_APPR_STATUS = 'A' THEN 'Y' ELSE 'N' END IAA_Confirmation_Flag,");
		sb.append(" C.erp_Comp_nm Organization  ,");
		sb.append(" SPACE(50) Description,CONVERT(VARCHAR,IM.REASON_ID) ReasonCode,");
		sb.append(" OSTG.SGprmdet_Text1 OUT_Storage_Condition,");
		sb.append(" OTPR.SMP_PROD_CD Out_Product,OTBT.BATCH_NO Out_Batch,SD.ADJDTL_OUT_QTY Out_Qty,SD.ADJDTL_OUT_STK_TYPE Out_Stock_Type,");
		sb.append(" ISTG.SGprmdet_Text1 IN_Storage_Condition,");
		sb.append(" INPR.SMP_PROD_CD In_Product,INBT.BATCH_NO In_Batch,SD.ADJDTL_IN_QTY In_Qty,SD.ADJDTL_IN_STK_TYPE In_Stock_Type");
		sb.append(" FROM Sub_Company C , STOCK_ADJ_DETAILS SD , SMPITEM INPR LEFT OUTER JOIN SG_d_parameters_details ISTG ON ISTG.SGprmdet_id = INPR.STORAGE_TYPE_ID ,");
		sb.append(" SMPITEM OTPR LEFT OUTER JOIN SG_d_parameters_details OSTG ON OSTG.SGprmdet_id = OTPR.STORAGE_TYPE_ID ,");
		sb.append(" SMPBatch INBT, SMPBatch OTBT ,");
		sb.append(" IAA_MAP IM , STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID");
		sb.append(" LEFT OUTER JOIN GRN_HEADER GH ON SH.GRN_ID = GH.GRN_ID");
		sb.append(" WHERE ( SH.GRN_ID > 0  ) AND SH.STKADJ_APPR_STATUS = 'A'");
		sb.append(" AND SH.STKADJ_FIN_YEAR=:year AND  ( SH.ERP_CREATED IS NULL OR SH.ERP_CREATED = 'N' )");
		sb.append(" AND SH.STKADJ_ID = SD.ADJDTL_STKADJ_ID AND SH.STKADJ_FIN_YEAR = SD.ADJDTL_FIN_YEAR");
		sb.append(" AND SD.ADJDTL_OUT_ITEM_ID = OTPR.SMP_PROD_ID AND SD.ADJDTL_OUT_BATCH_ID = OTBT.BATCH_ID");
		sb.append(" AND SD.ADJDTL_IN_ITEM_ID = INPR.SMP_PROD_ID AND SD.ADJDTL_IN_BATCH_ID = INBT.BATCH_ID");
		sb.append(" AND INPR.SMP_SubComp_id = C.SubComp_id");
		sb.append(" AND SD.ADJDTL_REASON_ID = IM.Reason_ID AND  BR.STKADJ_ID IS NULL");
		sb.append(" ORDER BY SH.STKADJ_ID,SD.ADJDTL_ID");
		
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("year", year);
		  List<Tuple> tuples = query.getResultList();
		  System.out.println("list : "+tuples.size());
		  if (tuples != null && !tuples.isEmpty()) {
			  ErpIaaGrn_bean object = null;
			for (Tuple t : tuples) {
				object = new ErpIaaGrn_bean();
				object.setAdjustment_No(Long.valueOf(t.get("Adjustmnet_No",Integer.class).toString()));
				object.setWms_Reference_No(t.get("WMS_Reference_No",String.class));
				object.setIaa_Confirmation_flag(t.get("IAA_Confirmation_Flag",String.class));
				object.setOrganization(t.get("Organization",String.class));
				object.setDescription(t.get("Description",String.class));
				object.setReasonCode(t.get("ReasonCode",String.class));
				object.setOut_Product(t.get("Out_Product",String.class));
				object.setOut_Batch(t.get("Out_Batch",String.class));
				object.setOut_Qty(t.get("Out_Qty",BigDecimal.class));
				object.setOut_Stock_type(t.get("Out_Stock_Type",String.class));
				object.setOut_storage_condition(t.get("OUT_Storage_Condition",String.class));
				object.setIn_Product(t.get("In_Product",String.class));
				object.setIn_Batch(t.get("In_Batch",String.class));
				object.setIn_Qty(t.get("In_Qty",BigDecimal.class));
				object.setIn_Stock_type(t.get("In_Stock_Type",String.class));
				object.setIn_storage_condition(t.get("In_Storage_Condition",String.class));
				object.setStk_adj_no(t.get("STKADJ_NO",String.class));
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update_erpIaaGrndata(List<String> list,String year) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO ERP_STKADJ( STKADJ_ID,STKADJ_DATE,STKADJ_NO,STKADJ_COMPANY,STKADJ_FIN_YEAR,DATE_OF_SENDING ) ");
			sb.append(" SELECT SH.STKADJ_ID,SH.STKADJ_DATE,SH.STKADJ_NO,SH.STKADJ_COMPANY,SH.STKADJ_FIN_YEAR,GETDATE() DATE_OF_SENDING  ");
			sb.append(" FROM STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID ");
			sb.append(" WHERE ( SH.GRN_ID > 0  ) AND SH.STKADJ_APPR_STATUS = 'A' ");
			sb.append(" AND SH.STKADJ_FIN_YEAR= '"+year+"' AND  ( SH.ERP_CREATED IS NULL OR SH.ERP_CREATED = 'N' ) ");
			sb.append(" AND  BR.STKADJ_ID IS NULL AND SH.STKADJ_ID not in(:list)");
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("list", list);
			query.executeUpdate();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Erp_Wms_Iaa_confirmationbean> getErp_Wms_Iaa_Confirmationdata(String year) throws Exception {
		// TODO Auto-generated method stub
		List<Erp_Wms_Iaa_confirmationbean> list = new ArrayList<>();
		try {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT SH.STKADJ_NO,SH.STKADJ_FIN_YEAR+'~'+SH.ERP_IAA_NO WMS_Reference_No,SH.STKADJ_ID Adjustmnet_No , ");
		sb.append(" CASE WHEN SH.STKADJ_APPR_STATUS = 'A' THEN 'Y' ELSE 'N' END IAA_Confirmation_Flag, ");
		sb.append(" C.erp_Comp_nm Organization  , ");
		sb.append(" SPACE(50) Description,CONVERT(VARCHAR,IM.REASON_ID) ReasonCode, ");
		sb.append(" OSTG.SGprmdet_Text1 OUT_Storage_Condition, ");
		sb.append(" OTPR.SMP_PROD_CD Out_Product,OTBT.BATCH_NO Out_Batch,SD.ADJDTL_OUT_QTY Out_Qty,SD.ADJDTL_OUT_STK_TYPE Out_Stock_Type, ");
		sb.append(" ISTG.SGprmdet_Text1 IN_Storage_Condition, ");
		sb.append(" INPR.SMP_PROD_CD In_Product,INBT.BATCH_NO In_Batch,SD.ADJDTL_IN_QTY In_Qty,SD.ADJDTL_IN_STK_TYPE In_Stock_Type ");
		sb.append(" FROM Sub_Company C , STOCK_ADJ_DETAILS SD , SMPITEM INPR LEFT OUTER JOIN SG_d_parameters_details ISTG ON ISTG.SGprmdet_id = INPR.STORAGE_TYPE_ID, ");
		sb.append(" SMPITEM OTPR LEFT OUTER JOIN SG_d_parameters_details OSTG ON OSTG.SGprmdet_id = OTPR.STORAGE_TYPE_ID, ");
		sb.append(" SMPBatch INBT, SMPBatch OTBT , ");
		sb.append(" IAA_MAP IM , STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID ");
		sb.append(" WHERE SH.ERP_CREATED = 'Y'  AND SD.STKADJ_APPR_STATUS IN ( 'A' , 'D' ) ");
		sb.append(" AND SH.STKADJ_FIN_YEAR=:year ");
		sb.append(" AND SH.STKADJ_ID = SD.ADJDTL_STKADJ_ID AND SH.STKADJ_FIN_YEAR = SD.ADJDTL_FIN_YEAR ");
		sb.append(" AND SD.ADJDTL_OUT_ITEM_ID = OTPR.SMP_PROD_ID AND SD.ADJDTL_OUT_BATCH_ID = OTBT.BATCH_ID ");
		sb.append(" AND SD.ADJDTL_IN_ITEM_ID = INPR.SMP_PROD_ID AND SD.ADJDTL_IN_BATCH_ID = INBT.BATCH_ID ");
		sb.append(" AND INPR.SMP_SubComp_id = C.SubComp_id ");
		sb.append(" AND SD.ADJDTL_REASON_ID = IM.Reason_ID ");
		sb.append(" AND BR.STKADJ_ID IS NULL ");
		sb.append(" ORDER BY SH.STKADJ_ID,SD.ADJDTL_ID ");
		
		Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
		query.setParameter("year", year);
		  List<Tuple> tuples = query.getResultList();
		  System.out.println("list : "+tuples.size());
		  if (tuples != null && !tuples.isEmpty()) {
			  Erp_Wms_Iaa_confirmationbean object = null;
		for (Tuple t : tuples) {
			object = new Erp_Wms_Iaa_confirmationbean();
			object.setOrganization(t.get("Organization",String.class));
			object.setAdjustment_No(Long.valueOf(t.get("Adjustmnet_No",Integer.class).toString()));
			object.setWms_Reference_No(Long.valueOf(t.get("WMS_Reference_No",String.class)));
			object.setIaa_Confirmation_flag(t.get("IAA_Confirmation_Flag",String.class));
			object.setDescription(t.get("Description",String.class));
			object.setReasonCode(t.get("ReasonCode",String.class));
			object.setOut_Product(t.get("Out_Product",String.class));
			object.setOut_Batch(t.get("Out_Batch",String.class));
			object.setOut_Qty(t.get("Out_Qty",BigDecimal.class));
			object.setOut_Stock_type(t.get("Out_Stock_Type",String.class));
			object.setIn_Product(t.get("In_Product",String.class));
			object.setIn_Batch(t.get("In_Batch",String.class));
			object.setIn_Qty(t.get("In_Qty",BigDecimal.class));
			object.setIn_Stock_type(t.get("In_Stock_Type",String.class));
			object.setMedico_iaa_no(t.get("STKADJ_NO",String.class));
			object.setOut_storage_condition(t.get("OUT_Storage_Condition",String.class));
			object.setIn_storage_condition(t.get("IN_Storage_Condition",String.class));
			list.add(object);
		}
		  }

		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}

	@Override
	public void update_erpWms_Iaa_Confirmationdata(List<String> list,String year) throws Exception {
		// TODO Auto-generated method stub
		try {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO ERP_STKADJ( STKADJ_ID,STKADJ_DATE,STKADJ_NO,STKADJ_COMPANY,STKADJ_FIN_YEAR,DATE_OF_SENDING ) ");
		sb.append(" SELECT SH.STKADJ_ID,SH.STKADJ_DATE,SH.STKADJ_FIN_YEAR+'~'+SH.STKADJ_NO,SH.STKADJ_COMPANY,SH.STKADJ_FIN_YEAR,GETDATE() DATE_OF_SENDING  ");
		sb.append(" FROM STOCK_ADJ_HEADER SH LEFT OUTER JOIN ERP_STKADJ BR ON BR.STKADJ_FIN_YEAR = SH.STKADJ_FIN_YEAR AND BR.STKADJ_ID = SH.STKADJ_ID ");
		sb.append(" WHERE SH.ERP_CREATED = 'Y'  AND SH.STKADJ_APPR_STATUS IN ( 'A' , 'D' ) ");
		sb.append(" AND SH.STKADJ_FIN_YEAR= '"+year+"' ");
		sb.append(" AND  BR.STKADJ_ID IS NULL AND BR.STKADJ_ID not in(:list)");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("list", list);
		query.executeUpdate();
		
		}
		catch (Exception e) {
			throw e;
		}

	}
	
	
	
	
	@Override
	public void Truncate_BatchstkReco() throws Exception {
		// TODO Auto-generated method stub
		
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" Truncate Table BATCH_STK_RECO ");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void call_batch_stk_reco_pfz_proc(String date) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" exec Batch_stock_reco_pfz '"+date+"' ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	public void Push_Batch_stk_reco_data_to_arc() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO BATCH_STK_RECO_ARC SELECT slno,reco_date,Fin_year,Organization,Product_code,Product_name,Batch_no,Stock_type,qty,Medico_org,Medico_prod_code,Medico_prod_name, ");
			sb.append(" Medico_batch_no,Medico_stk_type,Medico_qty,Difference  FROM BATCH_STK_RECO ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@Override
	public void push_Batch_stk_reco_quarantine_data_to_arc() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" INSERT INTO BATCH_STK_RECO_QUARANTINE_ARC SELECT slno,reco_date,Fin_year,Organization,Product_code,Product_name,Batch_no,Stock_type,qty,Medico_org,Medico_prod_code,Medico_prod_name, ");
			sb.append(" Medico_batch_no,Medico_stk_type,Medico_qty,Difference  FROM BATCH_STK_RECO_QUARANTINE ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void Truncate_Batch_stk_reco_Quarantine() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" Truncate Table BATCH_STK_RECO_QUARANTINE ");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
	}

	@Override
	public void call_batch_stk_reco_quarantine_proc(String date) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" exec BATCH_STOCK_RECO_QUARANTINE_PFZ '"+date+"' ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@Override
	public void push_Tran_wise_data_to_arc() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO TRAN_WISE_RECO_ARC SELECT slno,reco_from,reco_to,fin_year,organization,tran_id,tran_type,transaction_no,tran_date,");
			sb.append("in_prod_code,in_product_name,in_batch_no,in_stock_type,in_qty,out_prod_code,out_product_name,out_batch_no,out_stock_type,out_qty,");
			sb.append("med_tran_id,med_tran_type,med_transaction_no,med_tran_date,med_in_prod_code,med_in_batch_no,med_in_stk_type,med_in_qty,med_out_prod_code,");
			sb.append("med_out_batch_no,med_out_stk_type,med_out_qty FROM TRAN_WISE_RECO ");
			
			Query query = entityManager.createNativeQuery(sb.toString(),Tuple.class);
			query.executeUpdate();
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void Truncate_Tran_wise_reco() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" Truncate Table TRAN_WISE_RECO ");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
	}

	@Override
	public void call_Erp_Tran_wise_data(String from_dt, String to_dt, String fin_year) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" exec TRANSACTION_RECO_PFZ '"+from_dt+"','"+to_dt+"','"+fin_year+"' ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void push_Tran_wise_Quarantine_data_to_arc() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO TRAN_WISE_QUARANTINE_RECO_ARC SELECT slno,reco_from,reco_to,fin_year,organization,tran_id,tran_type,transaction_no,tran_date,");
			sb.append("in_prod_code,in_product_name,in_batch_no,in_stock_type,in_qty,out_prod_code,out_product_name,out_batch_no,out_stock_type,out_qty,");
			sb.append("med_tran_id,med_tran_type,med_transaction_no,med_tran_date,med_in_prod_code,med_in_batch_no,med_in_stk_type,med_in_qty,med_out_prod_code,");
			sb.append("med_out_batch_no,med_out_stk_type,med_out_qty FROM TRAN_WISE_QUARANTINE_RECO ");
			
			Query query = entityManager.createNativeQuery(sb.toString(),Tuple.class);
			query.executeUpdate();
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void Truncate_Tran_wise_Quarantine_reco() throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" Truncate Table TRAN_WISE_QUARANTINE_RECO ");
			
			Query query = entityManager.createNativeQuery(sb.toString(), Tuple.class);
			query.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		
	}

	@Override
	public void call_Erp_Tran_wise_Quarantine_data(String from_dt, String to_dt, String fin_year) throws Exception {
		// TODO Auto-generated method stub
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" exec TRANSACTION_RECO_QUARANTINE_PFZ '"+from_dt+"','"+to_dt+"','"+fin_year+"' ");
			
			Query query = entityManager.createNativeQuery(sb.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@Override
	public Long getCycleNumber(Date date) throws Exception {
		EntityManager em = null;
		Long  count = null;
		try {
			   
			StringBuffer sb = new StringBuffer();
			sb.append(" select ISNULL(max(a.CYCLE),0) from Erp_status_log a where  ");
			sb.append(" send_date=:send_date " );
			Query query = entityManager.createNativeQuery(sb.toString());
			query.setParameter("send_date",date);
			count = Long.valueOf(query.getSingleResult().toString());
			
			System.out.println("Count "+count);
			
	
		}catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		finally {
			if (em != null) { em.close(); }
		}
		return count+1;
	}
	
	
	@Override
	public List<Erp_Batch_stk_reco> getdataforstockreco() throws Exception {
		// TODO Auto-generated method stub
		List<Erp_Batch_stk_reco> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select slno,reco_date,fin_year,organization,product_code,product_name,batch_no,stock_type,Qty,"); 
			sb.append(" medico_org,medico_prod_code,medico_prod_name,medico_batch_no,Medico_stk_type,Medico_qty,difference");
			sb.append(" from BATCH_STK_RECO");
			Query query = entityManager.createNativeQuery(sb.toString(),Erp_Batch_stk_reco.class);
			
			list = query.getResultList();
			
			System.out.println("list : "+list.size());
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return list;
	}
	
	@Override
	public List<Erp_Batch_stk_reco_quarantine> getstockRecoQuarantinereportdata() {
		EntityManager em = null;
		List<Erp_Batch_stk_reco_quarantine> list = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from BATCH_STK_RECO_QUARANTINE ");
			Query query = entityManager.createNativeQuery(sb.toString(),Erp_Batch_stk_reco_quarantine.class);
			list = query.getResultList();
		} finally {
			if (em != null) { em.close(); }
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteErpProduct(String product_code) throws Exception{
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(" delete from ERP_PRODUCT  where productcode='").append(product_code).append("'");
			Query query = entityManager.createNativeQuery(buffer.toString());
			query.executeUpdate();
			
		}catch (Exception e) {
			throw e;
		}
	}
	
}

