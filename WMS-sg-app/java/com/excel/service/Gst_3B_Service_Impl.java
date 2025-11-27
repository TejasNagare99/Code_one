package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.Gst3bUploadJsonBean;
import com.excel.bean.Gst3bUploadJsonBean.EligibleITCInner;
import com.excel.bean.Gst3bUploadJsonBean.InterSupplyInner;
import com.excel.bean.Gst3bUploadJsonBean.InterestAndLateFeesDetails;
import com.excel.bean.Gst3bUploadJsonBean.InwardSupplyDetailsInner;
import com.excel.bean.Gst3bUploadJsonBean.SupplyDetailsInner;
import com.excel.model.GST3B_5;
import com.excel.model.GST3B_Eligible_Itc;
import com.excel.model.GST3B_Supplies_Liable_Bean;
import com.excel.model.GST3B_Supplies_Liable_Bean_2;
import com.excel.repository.Gst_3B_Repository;
import com.excel.utility.PdfStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class Gst_3B_Service_Impl implements Gst_3B_Service {
	
	@Autowired Gst_3B_Repository gst_3b_repository;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<GST3B_Supplies_Liable_Bean> getDataForSuppliesLiable(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		return gst_3b_repository.getDataForSuppliesLiable(fin_year_flag, fin_id, comp_cd, st_dt, end_dt, loc_id);
	}
	
	@Override
	public List<GST3B_Supplies_Liable_Bean_2> getDataForSuppliesLiableTable2(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		return gst_3b_repository.getDataForSuppliesLiableTable2(fin_year_flag, fin_id, comp_cd, st_dt, end_dt, loc_id);
	}
	
	@Override
	public List<GST3B_Eligible_Itc> getDataForEligibleItc(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		return gst_3b_repository.getDataForEligibleItc(fin_year_flag, fin_id, comp_cd, st_dt, end_dt, loc_id);
	}
	
	@Override
	public List<GST3B_5> getDataForExemptInwardSupplies(String fin_year_flag, String fin_id, String comp_cd,
			String st_dt, String end_dt, String loc_id) throws Exception {
		return gst_3b_repository.getDataForExemptInwardSupplies(fin_year_flag, fin_id, comp_cd, st_dt, end_dt, loc_id);
	}
	
	@Override
	public File generatePdfForDownload(List<GST3B_Supplies_Liable_Bean> sup_liab_tab31,
			List<GST3B_Supplies_Liable_Bean_2> table_32, List<GST3B_Eligible_Itc> eligible_itc_list,List<GST3B_5> exempt_inward,
            String period_code, String gst_in, String period_name, String path,
			String erp_fin_year, String depot_name) throws Exception {
		period_name = period_name.replaceAll("\\s+", "");
		long timevar = new Date().getTime();
		String filename = period_name + "_" + erp_fin_year + "_" + gst_in + "_GSTR3B.pdf";
		String actualpath = path+filename;
		File docfile = new File(actualpath);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, file);
		try {
			document.open();
		
			PdfPTable mainTable = new PdfPTable(1);
			PdfPTable header_table = new PdfPTable(4);
			PdfPTable Table_3_1 = new PdfPTable(6);
			PdfPTable Table_3_2 = new PdfPTable(7);
			PdfPTable Table_4 = new PdfPTable(5);
			PdfPTable Table_5 = new PdfPTable(3);
			PdfPCell Cell = null;
			
			mainTable.setWidthPercentage(100);
			Cell = new PdfPCell(new Phrase("GSTR 3B  Dated : "+ sdf.format(new Date())));
			Cell.setBorderWidthTop(0.25f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			
			mainTable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,false,false,false,false));
			
			//add header data
			header_table.setTotalWidth(new float[] {1f,1f,1f,0.7f});
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("GSTIN",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(gst_in,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Year",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(erp_fin_year,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
			
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Legal name of the registered person",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(depot_name,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Month",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			header_table.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(period_name,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
			
			Cell = new PdfPCell(header_table);
			Cell.setBorderWidthTop(0f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			mainTable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,false,false,false,false,false));
			
			//add 3.1 data
			Table_3_1.setTotalWidth(new float[] {2.5f,1f,1f,1f,1f,1f});
			BigDecimal tot_tax = BigDecimal.ZERO;
			BigDecimal int_tax = BigDecimal.ZERO;
			BigDecimal cen_tax = BigDecimal.ZERO;
			BigDecimal state_tax = BigDecimal.ZERO;
			BigDecimal cess = BigDecimal.ZERO;
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("3.1 Details of Outward Supplies and inward supplies liable to reverse charge",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,6,1,true,true,false,false,false));
			//add headings
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Nature of Supplies",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Total Taxable value",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Integrated Tax",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Central Tax",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("State/UT Tax",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Cess",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			
			for(GST3B_Supplies_Liable_Bean sup_liab : sup_liab_tab31) {
				
				Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(sup_liab.getSupply(),Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(sup_liab.getTaxable()==null?"0.00":sup_liab.getTaxable().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(sup_liab.getIgst()==null?"0.00":sup_liab.getIgst().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(sup_liab.getCgst()==null?"0.00":sup_liab.getCgst().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(sup_liab.getSgst()==null?"0.00":sup_liab.getSgst().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(
						sup_liab.getCess()==null || sup_liab.getCess().signum() == 0 ?"0.00":sup_liab.getCess().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				
				if(sup_liab.getTaxable()!=null)tot_tax = tot_tax.add(sup_liab.getTaxable());
				if(sup_liab.getIgst()!=null)int_tax = int_tax.add(sup_liab.getIgst());
				if(sup_liab.getCgst()!=null)cen_tax = cen_tax.add(sup_liab.getCgst());
				if(sup_liab.getSgst()!=null)state_tax = state_tax.add(sup_liab.getSgst());
				if(sup_liab.getCess()!=null)cess = cess.add(sup_liab.getCess());
			}
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Total",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(tot_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(int_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(cen_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(state_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_3_1.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(cess ==null || cess.signum() == 0 ?"0.00":cess.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			
			Cell = new PdfPCell(Table_3_1);
			Cell.setBorderWidthTop(0f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			mainTable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,false,false,false,false,false));
			
			
			//add 4 data
			Table_4.setWidthPercentage(75);
			Table_4.setTotalWidth(new float[] {2.5f,1f,1f,1f,1f});
			
			BigDecimal A_elig_int_tax = BigDecimal.ZERO;
			BigDecimal A_elig_cen_tax = BigDecimal.ZERO;
			BigDecimal A_elig_state_tax = BigDecimal.ZERO;
			BigDecimal A_elig_cess = BigDecimal.ZERO;
			
			BigDecimal B_elig_int_tax = BigDecimal.ZERO;
			BigDecimal B_elig_cen_tax = BigDecimal.ZERO;
			BigDecimal B_elig_state_tax = BigDecimal.ZERO;
			BigDecimal B_elig_cess = BigDecimal.ZERO;
			
			Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("4. Eligible ITC",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,5,1,true,true,false,false,false));
			//add headings
			Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Details",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Integrated Tax",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Central Tax",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("State/UT Tax",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Cess",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,25f,true,0,1,true,true,false,false,false));
			
			char old_indicator =' ';
			char new_indicator =' ';
			 
			for(GST3B_Eligible_Itc elig_itc : eligible_itc_list ) {
				new_indicator = elig_itc.getInd().trim().charAt(0);
				if(new_indicator!=old_indicator) {
					if(new_indicator == 'B') {
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("(A) ITC Available (Whether in full or part)",Element.ALIGN_LEFT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_int_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_cen_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_state_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_cess==null || A_elig_cess.signum() == 0 ?"0.00":A_elig_cess.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
					}
					if(new_indicator == 'D') {
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("(B) ITC Reversed",Element.ALIGN_LEFT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(B_elig_int_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(B_elig_cen_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(B_elig_state_tax.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(B_elig_cess==null || B_elig_cess.signum() == 0 ?"0.00":B_elig_cess.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" (C) Net ITC Available (A)-(B)",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_int_tax.subtract(B_elig_int_tax).toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_cen_tax.subtract(B_elig_cen_tax).toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_state_tax.subtract(B_elig_state_tax).toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(A_elig_cess.subtract(B_elig_cess)==null || A_elig_cess.subtract(B_elig_cess).signum() == 0 ?"0.00":A_elig_cess.subtract(B_elig_cess).toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
					
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("(D) Other Details",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
						Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,4,1,true,true,false,false,false));
						}
				}
				Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(elig_itc.getDetails(),Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(elig_itc.getIgst()==null?"0.00":elig_itc.getIgst().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(elig_itc.getCgst()==null?"0.00":elig_itc.getCgst().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(elig_itc.getSgst()==null?"0.00":elig_itc.getSgst().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_4.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(elig_itc.getCess()==null || elig_itc.getCess().signum() == 0 ?"0.00":elig_itc.getCess().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				if(new_indicator == 'A') {
					if(elig_itc.getIgst()!=null)A_elig_int_tax = A_elig_int_tax.add(elig_itc.getIgst());
					if(elig_itc.getCgst()!=null)A_elig_cen_tax = A_elig_cen_tax.add(elig_itc.getCgst());
					if(elig_itc.getSgst()!=null)A_elig_state_tax = A_elig_state_tax.add(elig_itc.getSgst());
					if(elig_itc.getCess()!=null)A_elig_cess = A_elig_cess.add(elig_itc.getCess());
				}
				if(new_indicator == 'B') {
					if(elig_itc.getIgst()!=null)B_elig_int_tax = B_elig_int_tax.add(elig_itc.getIgst());
					if(elig_itc.getCgst()!=null)B_elig_cen_tax = B_elig_cen_tax.add(elig_itc.getCgst());
					if(elig_itc.getSgst()!=null)B_elig_state_tax = B_elig_state_tax.add(elig_itc.getSgst());
					if(elig_itc.getCess()!=null)B_elig_cess = B_elig_cess.add(elig_itc.getCess());
				}
				old_indicator = new_indicator;
			}
			
			Cell = new PdfPCell(Table_4);
			Cell.setBorderWidthTop(0f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			
			//mainTable.addCell(Table_4);
			mainTable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,false,false,false,false,false));
			
			//add table 5
			Table_5.setWidthPercentage(75f);
			Table_5.setTotalWidth(new float[] {2.5f,1f,1f});
			BigDecimal exempt_inter = BigDecimal.ZERO;
			BigDecimal exempt_intra = BigDecimal.ZERO;
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("5. Values of exempt, Nil-rated and non-GST inward supplies"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,3,1,true,true,false,false,false));
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Nature of Supplies",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Inter-State Supplies",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Intra-State Supplies",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			
			for(GST3B_5 gst3b5 : exempt_inward) {
				Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(gst3b5.getDetails(),Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(gst3b5.getInterstate_supply().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(gst3b5.getIntrastate_supply().toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
				
				exempt_inter = exempt_inter.add(gst3b5.getInterstate_supply());
				exempt_intra = exempt_intra.add(gst3b5.getIntrastate_supply());
			}
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Total",Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,false,0,1,true,true,false,false,false));
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(exempt_inter.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			Table_5.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(exempt_intra.toPlainString(),Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			
			
			Cell = new PdfPCell(Table_5);
			Cell.setBorderWidthTop(0f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			
			mainTable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,false,true,false,false,false));
			document.add(mainTable);
			
			
			document.newPage();
			mainTable = new PdfPTable(1);
			mainTable.setWidthPercentage(100);
			
			
			mainTable.setWidthPercentage(100);
			Cell = new PdfPCell(new Phrase("GSTR 3B"));
			Cell.setBorderWidthTop(0.25f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			
			mainTable.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(" ",Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,false,0,1,true,false,false,false,false));
			
			
			//add table 3.2
			Table_3_2.setWidthPercentage(75f);
			Table_3_2.setTotalWidth(new float[] {3f,1f,1f,1f,1f,1f,1f});
			BigDecimal unreg_tot_tax = BigDecimal.ZERO;
			BigDecimal unreg_int_tax = BigDecimal.ZERO;
			BigDecimal com_tot_tax = BigDecimal.ZERO;
			BigDecimal com_int_tax = BigDecimal.ZERO;
			BigDecimal uin_tot_tax = BigDecimal.ZERO;
			BigDecimal uin_int_tax = BigDecimal.ZERO;
			
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("3.2  Of the supplies shown in 3.1 (a), details of inter-state supplies made to unregistered persons, composition taxable person and UIN holders"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,7,1,true,true,false,false,false));
			
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Place of Supply"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,2,true,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Supplies made to Unregistered Persons"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,2,1,true,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Supplies made to Composition Taxable Persons"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,2,1,true,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Supplies made to UIN holders"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,2,1,true,true,false,false,false));
			
			for(int i = 0 ; i<3;i++) {
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Total Taxable value"
						,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Amount of Integrated Tax"
						,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,true,true,false,false,false));
			}
			
			for(GST3B_Supplies_Liable_Bean_2 supl_liab_2 : table_32) {
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getPlace_supply()
						,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
				
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getUnreg_taxable().toPlainString()
						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getUnreg_igst().toPlainString()
						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getReg_taxable().toPlainString()
						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getReg_igst().toPlainString()
						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
//				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getUin_taxable().toPlainString()
//						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
//			
//				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getUin_igst().toPlainString()
//						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
				
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getUin_taxable().signum() == 0  ? "0.00" :
					supl_liab_2.getUin_taxable().toPlainString()
						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
				
				Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(supl_liab_2.getUin_igst().signum() == 0 ? "0.00" :
					supl_liab_2.getUin_igst().toPlainString()
						,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
				
				
				unreg_tot_tax = unreg_tot_tax.add(supl_liab_2.getUnreg_taxable());
				unreg_int_tax = unreg_int_tax.add(supl_liab_2.getUnreg_igst());
				com_tot_tax = com_tot_tax.add(supl_liab_2.getReg_taxable());
				com_int_tax = com_int_tax.add(supl_liab_2.getReg_igst());
				uin_tot_tax = uin_tot_tax.add(supl_liab_2.getUin_taxable())  ;
				uin_int_tax = uin_int_tax.add(supl_liab_2.getUin_igst());
			}
			
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder("Total"
					,Element.ALIGN_CENTER,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(unreg_tot_tax.signum() == 0 ? "0.00" :
				unreg_tot_tax.toPlainString()
					,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(unreg_int_tax.signum() == 0 ? "0.00" :
				unreg_int_tax.toPlainString()
					,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(com_tot_tax.signum() == 0 ? "0.00" :
				com_tot_tax.toPlainString()
					,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(com_int_tax.signum() == 0 ? "0.00" :
				com_int_tax.toPlainString()
					,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(uin_tot_tax.signum() == 0 ? "0.00" :
				uin_tot_tax.toPlainString()
					,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));
			Table_3_2.addCell(PdfStyle.createUltimateCellWithBorderWithDisableBorder(uin_int_tax.signum() == 0 ? "0.00" :
				uin_int_tax.toPlainString()
					,Element.ALIGN_RIGHT,Element.ALIGN_BASELINE,7,14f,true,0,1,false,true,false,false,false));

			Cell = new PdfPCell(Table_3_2);
			Cell.setBorderWidthTop(0f);
			Cell.setBorderWidthRight(0.25f);
			Cell.setBorderWidthLeft(0.25f);
			Cell.setBorderWidthBottom(0.25f);
			Cell.setPadding(10f);
			mainTable.addCell(Cell);
			//mainTable.addCell(Table_3_2);
			document.add(mainTable);
		}
		catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(document != null)document.close();
		}
		return docfile;
	}
	
	@Override
	public String generateJsonForDownload(List<GST3B_Supplies_Liable_Bean> sup_liab_tab31,
			List<GST3B_Supplies_Liable_Bean_2> table_32, List<GST3B_Eligible_Itc> eligible_itc_list,List<GST3B_5> exempt_inward,
			String period_code,
			String gst_in, String period_name, String path, String erp_fin_year) throws Exception {
		// TODO Auto-generated method stub
		String actualpath = null;
		period_name=period_name.replace(" ", "");
		String jsonStr;
		String filepath = period_name + "_" + erp_fin_year + "-" + gst_in + "-Details.json";
		try {
			Gst3bUploadJsonBean gst3buploadbean = new Gst3bUploadJsonBean();
			gst3buploadbean.setGstin(gst_in);
			gst3buploadbean.setRet_period(period_code + erp_fin_year);
			if (sup_liab_tab31 != null && sup_liab_tab31.size() > 0) {
				for (GST3B_Supplies_Liable_Bean sup_liab_tab_31 : sup_liab_tab31) {

					// mapping from db ind value to json
					// ind 1 --> osup_det//ind 2 --> osup_zero//ind 3 --> osup_nil_exmp//ind 4 -->
					// isup_rev//ind 5 --> osup_nongst

					SupplyDetailsInner sup_det = new SupplyDetailsInner(
							sup_liab_tab_31.getTaxable() != null ? sup_liab_tab_31.getTaxable().doubleValue() : 0,
							sup_liab_tab_31.getIgst() != null ? sup_liab_tab_31.getIgst().doubleValue() : 0,
							sup_liab_tab_31.getCgst() != null ? sup_liab_tab_31.getCgst().doubleValue() : 0,
							sup_liab_tab_31.getSgst() != null ? sup_liab_tab_31.getSgst().doubleValue() : 0,
							sup_liab_tab_31.getCess() != null ? sup_liab_tab_31.getCess().doubleValue() : 0);
					if (sup_liab_tab_31.getInd().equals(1l)) {
						gst3buploadbean.getSup_details().setOsup_det(sup_det);
					}
					if (sup_liab_tab_31.getInd().equals(2l)) {
						gst3buploadbean.getSup_details().setOsup_zero(sup_det);
					}
					if (sup_liab_tab_31.getInd().equals(3l)) {
						gst3buploadbean.getSup_details().setOsup_nil_exmp(sup_det);
					}
					if (sup_liab_tab_31.getInd().equals(4l)) {
						gst3buploadbean.getSup_details().setIsup_rev(sup_det);
					}
					if (sup_liab_tab_31.getInd().equals(5l)) {
						gst3buploadbean.getSup_details().setOsup_nongst(sup_det);
					}
				}
			}
			if (table_32 != null && table_32.size() > 0) {
				for (GST3B_Supplies_Liable_Bean_2 tab_32_bean : table_32) {
					String state_code = tab_32_bean.getPlace_supply().trim().substring(0, 2);
					if (tab_32_bean.getUnreg_taxable().compareTo(BigDecimal.ZERO) > 0
							&& tab_32_bean.getUnreg_igst().compareTo(BigDecimal.ZERO) > 0) {
						gst3buploadbean.getInter_sup().getUnreg_details()
								.add(new InterSupplyInner(state_code, tab_32_bean.getUnreg_taxable().doubleValue(),
										tab_32_bean.getUnreg_igst().doubleValue()));
					}
					if (tab_32_bean.getReg_taxable().compareTo(BigDecimal.ZERO) > 0
							&& tab_32_bean.getReg_igst().compareTo(BigDecimal.ZERO) > 0) {
						gst3buploadbean.getInter_sup().getComp_details().add(new InterSupplyInner(state_code,
								tab_32_bean.getReg_taxable().doubleValue(), tab_32_bean.getReg_igst().doubleValue()));
					}
					if (tab_32_bean.getUin_taxable().compareTo(BigDecimal.ZERO) > 0
							&& tab_32_bean.getUin_igst().compareTo(BigDecimal.ZERO) > 0) {
						gst3buploadbean.getInter_sup().getUin_details().add(new InterSupplyInner(state_code,
								tab_32_bean.getUin_taxable().doubleValue(), tab_32_bean.getUin_igst().doubleValue()));
					}

				}
			}

			BigDecimal itc_avail_totals_igst = BigDecimal.ZERO;
			BigDecimal itc_avail_totals_cgst = BigDecimal.ZERO;
			BigDecimal itc_avail_totals_sgst = BigDecimal.ZERO;
			BigDecimal itc_avail_totals_cess = BigDecimal.ZERO;

			BigDecimal itc_rev_totals_igst = BigDecimal.ZERO;
			BigDecimal itc_rev_totals_cgst = BigDecimal.ZERO;
			BigDecimal itc_rev_totals_sgst = BigDecimal.ZERO;
			BigDecimal itc_rev_totals_cess = BigDecimal.ZERO;

			if (eligible_itc_list != null && eligible_itc_list.size() > 0) {
				for (GST3B_Eligible_Itc elig_itc_obj : eligible_itc_list) {

					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("A1")) {
						gst3buploadbean.getItc_elg().getItc_avl()[0] = new EligibleITCInner("IMPG",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("A2")) {
						gst3buploadbean.getItc_elg().getItc_avl()[1] = new EligibleITCInner("IMPS",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("A3")) {
						gst3buploadbean.getItc_elg().getItc_avl()[2] = new EligibleITCInner("ISRC",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("A4")) {
						gst3buploadbean.getItc_elg().getItc_avl()[3] = new EligibleITCInner("ISD",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("A5")) {
						gst3buploadbean.getItc_elg().getItc_avl()[4] = new EligibleITCInner("OTH",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}

					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("B1")) {
						gst3buploadbean.getItc_elg().getItc_rev()[0] = new EligibleITCInner("RUL",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("B2")) {
						gst3buploadbean.getItc_elg().getItc_rev()[1] = new EligibleITCInner("OTH",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}

					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("D1")) {
						gst3buploadbean.getItc_elg().getItc_inelg()[0] = new EligibleITCInner("RUL",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("D2")) {
						gst3buploadbean.getItc_elg().getItc_inelg()[1] = new EligibleITCInner("RUL",
								elig_itc_obj.getIgst() != null ? elig_itc_obj.getIgst().doubleValue() : 0,
								elig_itc_obj.getCgst() != null ? elig_itc_obj.getCgst().doubleValue() : 0,
								elig_itc_obj.getSgst() != null ? elig_itc_obj.getSgst().doubleValue() : 0,
								elig_itc_obj.getCess() != null ? elig_itc_obj.getCess().doubleValue() : 0);
					}

					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("A1")
							|| elig_itc_obj.getInd().trim().equalsIgnoreCase("A2")
							|| elig_itc_obj.getInd().trim().equalsIgnoreCase("A3")
							|| elig_itc_obj.getInd().trim().equalsIgnoreCase("A4")
							|| elig_itc_obj.getInd().trim().equalsIgnoreCase("A5")) {
						if(elig_itc_obj.getIgst()!=null)
						itc_avail_totals_igst = itc_avail_totals_igst.add(elig_itc_obj.getIgst());
						if(elig_itc_obj.getCgst()!=null)
						itc_avail_totals_cgst = itc_avail_totals_igst.add(elig_itc_obj.getCgst());
						if(elig_itc_obj.getSgst()!=null)
						itc_avail_totals_sgst = itc_avail_totals_igst.add(elig_itc_obj.getSgst());
						if(elig_itc_obj.getCess()!=null)
						itc_avail_totals_cess = itc_avail_totals_igst.add(elig_itc_obj.getCess());
					}
					if (elig_itc_obj.getInd().trim().equalsIgnoreCase("B1")
							|| elig_itc_obj.getInd().trim().equalsIgnoreCase("B2")) {
						if(elig_itc_obj.getIgst()!=null)
						itc_rev_totals_igst = itc_rev_totals_igst.add(elig_itc_obj.getIgst());
						if(elig_itc_obj.getCgst()!=null)
						itc_rev_totals_cgst = itc_rev_totals_igst.add(elig_itc_obj.getCgst());
						if(elig_itc_obj.getSgst()!=null)
						itc_rev_totals_sgst = itc_rev_totals_igst.add(elig_itc_obj.getSgst());
						if(elig_itc_obj.getCess()!=null)
						itc_rev_totals_cess = itc_rev_totals_igst.add(elig_itc_obj.getCess());
					}
				}
				// itc net A totals - B totals
				gst3buploadbean.getItc_elg()
						.setItc_net(new InterestAndLateFeesDetails(
								(itc_avail_totals_igst.subtract(itc_rev_totals_igst)).doubleValue(),
								(itc_avail_totals_cgst.subtract(itc_rev_totals_cgst)).doubleValue(),
								(itc_avail_totals_sgst.subtract(itc_rev_totals_sgst)).doubleValue(),
								(itc_avail_totals_cess.subtract(itc_rev_totals_cess)).doubleValue()));
			}
			
			if(exempt_inward!=null && exempt_inward.size()>0) {
				for(GST3B_5 exe_inward : exempt_inward) {
					if(exe_inward.equals("1")) {
						gst3buploadbean.getInward_sup().getIsup_details()[0] 
								= new InwardSupplyDetailsInner("GST", exe_inward.getInterstate_supply().doubleValue(), exe_inward.getIntrastate_supply().doubleValue());
					}
					else if(exe_inward.equals("2")) {
						gst3buploadbean.getInward_sup().getIsup_details()[1] 
								= new InwardSupplyDetailsInner("NONGST", exe_inward.getInterstate_supply().doubleValue(), exe_inward.getIntrastate_supply().doubleValue());
				
					}
				}
				
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			 jsonStr = mapper.writeValueAsString(gst3buploadbean);
			System.out.println("jsonStr:::::::::::::" + jsonStr);
			// write to file at theis point

			actualpath = path + filepath;

			@SuppressWarnings("unused")
			File ff = null;
			ff = new File(actualpath);
			File file = new File(path);
			try {
				if (!file.exists()) {
					if (file.mkdir()) {
					} else {
						throw new RuntimeException("Could not create directory");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException();
			}
			// Writing to a file
			mapper.writeValue(new File(actualpath.toString()), gst3buploadbean);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return jsonStr;
	}

}
