package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.excel.bean.FinalApprovalLogBean;
import com.excel.utility.MedicoConstants;
import com.excel.utility.MedicoResources;
import com.excel.utility.PdfStyle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class FinalApprovalLogPdfServiceImpl implements FinalApprovalLogPdfService, MedicoConstants {

	@Override
	public String generateFinalLogApprovalPdf(List<FinalApprovalLogBean> list) throws Exception {
		
		String fileName = "Final Approval Log" + new Date().getTime() + ".pdf";
		String filepath = PDF_FILE_PATH + fileName;
		
		try {
			
			File directory = new File(PDF_FILE_PATH);
		    if (! directory.exists()) {
		        directory.mkdir();
		    }
		    
		    File docfile = new File(filepath);
			OutputStream file = new FileOutputStream(docfile);
			Document document = new Document(PageSize.A4,5,5,5,5);
			
			final float[] content_col_width={1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f};
			
			PdfWriter writer = PdfWriter.getInstance(document, file);
			document.open();
			
			PdfPTable main_table = new PdfPTable(1);
			main_table.setWidthPercentage(100);
			
			PdfPCell main_cell = new PdfPCell();
			main_cell.setLeft(0);
			//main_cell.disableBorderSide(Rectangle.BOTTOM);
			main_cell.setPaddingBottom(5);
			
			
			
			
//			final float[] body_col_width={1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f};
			PdfPTable table = new PdfPTable(content_col_width);
			table.setWidthPercentage(100);
			
			PdfPCell cell = PdfStyle.createValueCellWithBorderwithColor("Medico SG - Approval Log",10, 1, 30f,BaseColor.LIGHT_GRAY);
			cell.setColspan(12);
			cell.setBorder(0);
			table.addCell(cell);
			
			cell = PdfStyle.createValueCellWithBorderwithColor("Date : "+MedicoResources.convertUtilDateToString_DD_MM_YYYY(new Date()),10, 1, 30f,BaseColor.LIGHT_GRAY);
			cell.setColspan(2);
			cell.setBorder(0);
			table.addCell(cell);
			
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Transaction Type",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approval Type",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Alloc Req No.",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Request Date",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Product Type",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Employee ID",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Employee Name",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Email ID \n of Approver",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approval \n Status Email",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approval Received \n from IP Address",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approver \n User ID",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Date & Time at which \n Approver Received Email",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Date & Time at which \n Approver made of approval",6, 1, 30f,BaseColor.LIGHT_GRAY));
			table.addCell(PdfStyle.createValueCellWithBorderwithColor("Transaction Ref",6, 1, 30f,BaseColor.LIGHT_GRAY));
			
			String oldApprReqNo = list.get(0).getAlloc_request_no();
			String newApprReqNo = "";
			int count = 0;
			for (FinalApprovalLogBean f : list) {
				
				
				
				newApprReqNo = f.getAlloc_request_no();
				if (!oldApprReqNo.equals(newApprReqNo)) {
					cell = PdfStyle.createValueCellWithBorder("", 14, 3, 20f);
					cell.setColspan(14);
					table.addCell(cell);
				}
				oldApprReqNo = newApprReqNo;
				
				table.addCell(PdfStyle.createValueCellWithBorder(f.getTran_type(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getApproval_type(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getAlloc_request_no(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(MedicoResources.convertUtilDateToString_DD_MM_YYYY(f.getAlloc_request_date()), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getProduct_type(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getEmployee_id(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getEmployee_name(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getEmailid_of_approver(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getApproval_status(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getAppr_recd_from_ip(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getApprover_user_id(), 6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getApproval_email_send_datetime()==null?"":
						MedicoResources.convert_DD_MM_YYYY_toDateAndTime(f.getApproval_email_send_datetime().toString()),
						6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getApproval_email_send_datetime()==null?"":
						MedicoResources.convert_DD_MM_YYYY_toDateAndTime(f.getApprover_made_approval_date_time().toString()),
						6, 0, 20f));
				table.addCell(PdfStyle.createValueCellWithBorder(f.getTransaction_ref().toString(), 6, 0, 20f));
				
				
				if (count == 20) {
					main_cell.addElement(table);
				    main_table.addCell(main_cell);
				    document.add(main_table);
				    
				    document.newPage();
				    main_table.flushContent();
				    table.flushContent();
				    main_table = new PdfPTable(1);
					main_table.setWidthPercentage(100);
					
				    
				    // Create a new table for the next page
				    table = new PdfPTable(content_col_width);
				    table.setWidthPercentage(100);
				    
				    table.addCell(PdfStyle.createValueCellWithBorderwithColor("Transaction Type",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approval Type",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Alloc Req No.",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Request Date",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Product Type",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Employee ID",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Employee Name",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Email ID \n of Approver",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approval \n Status Email",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approval Received \n from IP Address",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Approver \n User ID",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Date & Time at which \n Approver Received Email",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Date & Time at which \n Approver made of approval",6, 1, 30f,BaseColor.LIGHT_GRAY));
					table.addCell(PdfStyle.createValueCellWithBorderwithColor("Transaction Ref",6, 1, 30f,BaseColor.LIGHT_GRAY));
					
				    
				    // Reinitialize main_cell to add the new table
				    main_cell = new PdfPCell();
				    main_cell.setLeft(0);
				    //main_cell.disableBorderSide(Rectangle.BOTTOM);
				    main_cell.setPaddingBottom(5);
				    //main_cell.addElement(table);
				    
				    count = 0;
				}
				count++;
			}
			
			
			
			

			
			
			main_cell.addElement(table);
			
			main_table.addCell(main_cell);
			
			document.add(main_table);
			document.close();
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	
	

	
	
	
}
