package com.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Stock_Statement_Non_Sale;
import com.excel.model.Stock_Statement_Non_Sale_VET;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.HeaderPageEvent;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.excel.utility.Utility;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class StockStatementNonSaleablePDFServiceImpl implements StockStatementNonSaleablePDFService{
	@Autowired private UserMasterService usermasterservice;
	@Autowired
	Batch_Master_AuditTrail_PDF_ServiceImpl lockcode;
	UserMasterRepository userMasterRepository;
	StringBuffer path;
	final private int limit=15;
	float[] bodyColWidth = new float[] { 0.6f, 2f, 0.6f,0.6f, 0.6f,    0.6f, 0.6f, 0.6f,0.6f, 0.6f,     0.6f, 0.6f, 0.6f,0.6f};
	
	
	
	@Override
	public String GenerateStockStatementPDF(List<Stock_Statement_Non_Sale> list,String from_date,String to_date,String location_name,int div_id,int depot_id,String zero_ind,
			String prodType_id,String prod_name,String stockType_id,String userid,String companyname,String username,String empId) throws Exception {
		String today = Utility.convertSmpDatetoString(new Date());
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
		String time = df.format(new Date());
		
		String filepath = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		
		String user_name = username;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "StockStatementNS" + timevar + ".pdf";
		
		 path = new StringBuffer(filepath).append("\\");
			path.append("files\\" +filename);
		File file1 = new File(filepath);
		try {
			if (!file1.exists()) {
				if (file1.mkdirs()) {
					
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			
			} catch (Exception e) {
				throw new RuntimeException();
			}
		
		
		File docfile = new File(path.toString());
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4.rotate());
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 20, 825, 580);
		writer.setBoxSize("header", rect);
		 writer = usermasterservice.generatePDFlock(userid, writer);
		HeaderPageEvent event = new HeaderPageEvent();
		writer.setPageEvent(event);
		document.open();
		boolean isnew = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {			
			PdfPTable bodytable = null;
			//------------------------------------------
			String old_div="", new_div="";		
			String new_stock="", old_stock="";	
			String new_prod="", old_prod="";
			
			BigDecimal ptot_open_stk = BigDecimal.ZERO;
			BigDecimal ptot_out_iaa = BigDecimal.ZERO;
			BigDecimal ptot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal ptot_out_total = BigDecimal.ZERO;
			BigDecimal ptot_close_stk = BigDecimal.ZERO;
			
			
			BigDecimal gtot_open_stk = BigDecimal.ZERO;
			BigDecimal gtot_out_iaa = BigDecimal.ZERO;
			BigDecimal gtot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal gtot_out_total = BigDecimal.ZERO;
			BigDecimal gtot_close_stk = BigDecimal.ZERO;
			
			BigDecimal side_tot_inward = BigDecimal.ZERO;
			BigDecimal side_tot_outward = BigDecimal.ZERO;
			//------------------------------------------

					
			
			int rows = 0;
			isnew = true;			
			
			for (Stock_Statement_Non_Sale dataList : list) {	
				new_div=dataList.getTeam();
				new_stock=dataList.getStock_type_desc();
				new_prod=dataList.getProduct_name();
				 side_tot_inward = BigDecimal.ZERO;
				 side_tot_outward = BigDecimal.ZERO;
				 
				 
				 
				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(comp_name,user_name,today,rows, from_date, to_date, location_name, time));
					isnew = false;
					bodytable = new PdfPTable(14);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
				if(!old_stock.equalsIgnoreCase(new_stock) || (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div)) ){
					if(!old_stock.equalsIgnoreCase("")){
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(("Product Total : "),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_open_stk.toString()),true,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_iaa.toString()),true,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_stk_wth.toString()),true,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_total.toString()),true,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_close_stk.toString()),true,true,20));
					 
					 ptot_open_stk = BigDecimal.ZERO;
					 ptot_out_iaa = BigDecimal.ZERO;
					 ptot_out_stk_wth = BigDecimal.ZERO;
					 ptot_out_total = BigDecimal.ZERO;
					 ptot_close_stk = BigDecimal.ZERO;
					
					rows++;
					}
				}			
				if(!old_div.equalsIgnoreCase(new_div)){
					bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+new_div, 14, BaseColor.BLUE));
					rows++;
				}
				if(!old_stock.equalsIgnoreCase(new_stock) || (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div)) ){
					bodytable.addCell(PdfStyle.createDatachangeCell("Stock : "+new_stock, 14, BaseColor.RED));
					rows++;
				}
				side_tot_inward=dataList.getGrn_rej().add(dataList.getIaa_in_value());
				side_tot_outward=dataList.getSw_out_value().add(dataList.getIaa_out_value());
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getProduct_code().length()>8 ? dataList.getProduct_code().substring(0, 8) : dataList.getProduct_code()),false,true,20));
				if(!new_prod.equalsIgnoreCase(old_prod) || (new_prod.equalsIgnoreCase(old_prod) && !old_div.equalsIgnoreCase(new_div)) || (new_prod.equalsIgnoreCase(old_prod) && !old_stock.equalsIgnoreCase(new_stock)) ){
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getProduct_name().length()>100 ? dataList.getProduct_name().substring(0, 100) : dataList.getProduct_name()),false,true,20));
				}else{
				bodytable.addCell(PdfStyle.createValueCellWithHeight((""),false,true,20));
				}
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getPack_disp_nm().length()>100 ? dataList.getPack_disp_nm().substring(0, 100) : dataList.getPack_disp_nm()),false,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getBatch_no().length()>100 ? dataList.getBatch_no().substring(0, 100) : dataList.getBatch_no()),false,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getBatch_open_stock().toString()),true,true,20));
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getOpen_stock_value().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getGrn_rej().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getIaa_in().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((side_tot_inward.toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getIaa_out().toString()),true,true,20));
				
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getSw_out().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((side_tot_outward.toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getClos_qty().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getClos_value().toString()),true,true,20));
				
				
//				Calculations
				
				ptot_open_stk=ptot_open_stk.add(dataList.getOpen_stock_value());
				ptot_out_iaa=ptot_out_iaa.add(dataList.getIaa_out_value());
				ptot_out_stk_wth=ptot_out_stk_wth.add(dataList.getSw_out_value());
				ptot_out_total=ptot_out_total.add(side_tot_outward);
				ptot_close_stk=ptot_close_stk.add(dataList.getClos_value());
				
				gtot_open_stk=gtot_open_stk.add(dataList.getOpen_stock_value());
				gtot_out_iaa=gtot_out_iaa.add(dataList.getIaa_out_value());
				gtot_out_stk_wth=gtot_out_stk_wth.add(dataList.getSw_out_value());
				gtot_out_total=gtot_out_total.add(side_tot_outward);
				gtot_close_stk=gtot_close_stk.add(dataList.getClos_value());
				
				
				old_div=new_div;
				old_stock=new_stock;
				old_prod=new_prod;
				
				rows++;	 
				
				
	
			}
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(("Product Total : "),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_open_stk.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_iaa.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_stk_wth.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_total.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_close_stk.toString()),true,true,20));
			 
			 ptot_open_stk = BigDecimal.ZERO;
			 ptot_out_iaa = BigDecimal.ZERO;
			 ptot_out_stk_wth = BigDecimal.ZERO;
			 ptot_out_total = BigDecimal.ZERO;
			 ptot_close_stk = BigDecimal.ZERO;
			
		
			
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(("Grand  Total :"),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_open_stk.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_out_iaa.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_out_stk_wth.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_out_total.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_close_stk.toString()),true,true,20));
			
			
			 
			newPage(column, bodytable, rows);
			document.newPage();
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (file != null) {
					file.close();
				}
				if (os != null) {
					os.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		System.out.println("PATH$%#$#$$$$#$$" + path + "\\files\\" + filename);
		return filename;
	}
	
	private PdfPTable createHeader(String comp_name, String userName, String date, int rows,String from_date,String to_date,String location_name,String time)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Non Saleable Stock Statement", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			
			PdfPTable h1 = new PdfPTable(4);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {2f, 2f, 2f, 2f}); 
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Report Dated : "+date+"  Time :" +time ,0,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Location :"+location_name,1,5,9,18,false));
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("User : "+userName,2,5,9,18,false));			
			h1.addCell(PdfStyle.createUltimateCellWithoutBorder("Period  : "+from_date+" to "+to_date,2,5,9,18,false));	
			
			PdfPTable bodyHeader = new PdfPTable(14);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			
			PdfPCell cell1 = PdfStyle.createLabelCellWithBGColor("Product Details",0.7f);
			cell1.setColspan(2);	
			
			PdfPCell cell2 = PdfStyle.createLabelCellWithBGColor("Pack",0.7f);
			PdfPCell cell3 = PdfStyle.createLabelCellWithBGColor("Batch/AR No",0.7f);
			PdfPCell cell4 = PdfStyle.createLabelCellWithBGColor("Opening Stock",0.7f);
			cell4.setColspan(2);	
			
			PdfPCell cell5 = PdfStyle.createLabelCellWithBGColor("Inward",0.7f);
			cell5.setColspan(3);	
			
			PdfPCell cell6 = PdfStyle.createLabelCellWithBGColor("Outward",0.7f);
			cell6.setColspan(3);	
			
			PdfPCell cell7 = PdfStyle.createLabelCellWithBGColor("Closing Stock",0.7f);
			cell7.setColspan(2);	
			
			PdfPTable table_in = new PdfPTable(14);
			table_in.setWidthPercentage(100);
			table_in.setWidths(bodyColWidth);
			table_in.addCell(cell1);
			table_in.addCell(cell2);
			table_in.addCell(cell3);
			table_in.addCell(cell4);
			table_in.addCell(cell5);
			table_in.addCell(cell6);
			table_in.addCell(cell7);
			
			PdfPCell cell_Main = new PdfPCell(table_in);
			cell_Main.setColspan(14);
			bodyHeader.addCell(cell_Main);
			/*
			bodyHeader.addCell(table_in);
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan2("Product Details",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Pack",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Batch/AR No",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan2("Opening Stock",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan3("Inward",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan3("Outward",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan2("Closing Stock",0.7f));*/
			
			/*bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan2("Product Details",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Pack",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Batch/AR No",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan2("Opening Stock",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan3("Inward",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan3("Outward",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColorColspan2("Closing Stock",0.7f));*/
			
			
			
			
			
			//---------------------------------------------------------------------

			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Code",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Desc",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor(" ",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor(" ",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Qty",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Value",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("GRN  (Reject)",0.7f));
			
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IAA",0.7f));	
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Tot",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("IAA",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Stk Wdl",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Tot",0.7f));

			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Qty",0.7f));
			bodyHeader.addCell(PdfStyle.createLabelCellWithBGColor("Value",0.7f));


	
			
			rows++;
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;
	}

	public PdfPTable newPage(ColumnText column, PdfPTable bodytable, int rows) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 20, 825, 485);
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(14);		
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
			bodytable = new PdfPTable(14);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(this.bodyColWidth);

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	@Override
	public String GenerateStockStatementPDF_VET(List<Stock_Statement_Non_Sale_VET> list, String from_date,
			String to_date, String location_name, int div_id, int depot_id, String zero_ind, String prodType_id,
			String prod_name, String stockType_id, String userid, String companyname, String username, String empId)
			throws Exception {
		String today = Utility.convertSmpDatetoString(new Date());
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
		String time = df.format(new Date());
		
		String filepath = MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		
		String user_name = username;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "StockStatementNS" + timevar + ".pdf";
		
		 path = new StringBuffer(filepath).append("\\");
			path.append("files\\" +filename);
		File file1 = new File(filepath);
		try {
			if (!file1.exists()) {
				if (file1.mkdirs()) {
					
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			
			} catch (Exception e) {
				throw new RuntimeException();
			}
		
		
		File docfile = new File(path.toString());
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4.rotate());
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 20, 825, 580);
		writer.setBoxSize("header", rect);
		 writer = usermasterservice.generatePDFlock(userid, writer);
		HeaderPageEvent event = new HeaderPageEvent();
		writer.setPageEvent(event);
		document.open();
		boolean isnew = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {			
			PdfPTable bodytable = null;
			//------------------------------------------
			String old_div="", new_div="";		
			String new_stock="", old_stock="";	
			String new_prod="", old_prod="";
			
			BigDecimal ptot_open_stk = BigDecimal.ZERO;
			BigDecimal ptot_out_iaa = BigDecimal.ZERO;
			BigDecimal ptot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal ptot_out_total = BigDecimal.ZERO;
			BigDecimal ptot_close_stk = BigDecimal.ZERO;
			
			
			BigDecimal gtot_open_stk = BigDecimal.ZERO;
			BigDecimal gtot_out_iaa = BigDecimal.ZERO;
			BigDecimal gtot_out_stk_wth = BigDecimal.ZERO;
			BigDecimal gtot_out_total = BigDecimal.ZERO;
			BigDecimal gtot_close_stk = BigDecimal.ZERO;
			
			BigDecimal side_tot_inward = BigDecimal.ZERO;
			BigDecimal side_tot_outward = BigDecimal.ZERO;
			//------------------------------------------

					
			
			int rows = 0;
			isnew = true;			
			
			for (Stock_Statement_Non_Sale_VET dataList : list) {	
				new_div=dataList.getTeam();
				new_stock=dataList.getStock_type_desc();
				new_prod=dataList.getProduct_name();
				 side_tot_inward = BigDecimal.ZERO;
				 side_tot_outward = BigDecimal.ZERO;
				 
				 
				 
				if (rows >= limit) {
					bodytable = newPage(column, bodytable, rows);
					document.newPage();
					rows = 0;
				}

				if (isnew) {
					event.setHeader(createHeader(comp_name,user_name,today,rows, from_date, to_date, location_name, time));
					isnew = false;
					bodytable = new PdfPTable(14);
					bodytable.setWidthPercentage(100);
					bodytable.setWidths(bodyColWidth);					
				}
				if(!old_stock.equalsIgnoreCase(new_stock) || (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div)) ){
					if(!old_stock.equalsIgnoreCase("")){
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(("Product Total : "),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_open_stk.toString()),true,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_iaa.toString()),true,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_stk_wth.toString()),true,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_total.toString()),true,true,20));
						
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
						bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_close_stk.toString()),true,true,20));
					 
					 ptot_open_stk = BigDecimal.ZERO;
					 ptot_out_iaa = BigDecimal.ZERO;
					 ptot_out_stk_wth = BigDecimal.ZERO;
					 ptot_out_total = BigDecimal.ZERO;
					 ptot_close_stk = BigDecimal.ZERO;
					
					rows++;
					}
				}			
				if(!old_div.equalsIgnoreCase(new_div)){
					bodytable.addCell(PdfStyle.createDatachangeCell("Division : "+new_div, 14, BaseColor.BLUE));
					rows++;
				}
				if(!old_stock.equalsIgnoreCase(new_stock) || (old_stock.equalsIgnoreCase(new_stock) && !old_div.equalsIgnoreCase(new_div)) ){
					bodytable.addCell(PdfStyle.createDatachangeCell("Stock : "+new_stock, 14, BaseColor.RED));
					rows++;
				}
				side_tot_inward=dataList.getGrn_rej().add(dataList.getIaa_in_value());
				side_tot_outward=dataList.getSw_out_value().add(dataList.getIaa_out_value());
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getProduct_code().length()>8 ? dataList.getProduct_code().substring(0, 8) : dataList.getProduct_code()),false,true,20));
				if(!new_prod.equalsIgnoreCase(old_prod) || (new_prod.equalsIgnoreCase(old_prod) && !old_div.equalsIgnoreCase(new_div)) || (new_prod.equalsIgnoreCase(old_prod) && !old_stock.equalsIgnoreCase(new_stock)) ){
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getProduct_name().length()>100 ? dataList.getProduct_name().substring(0, 100) : dataList.getProduct_name()),false,true,20));
				}else{
				bodytable.addCell(PdfStyle.createValueCellWithHeight((""),false,true,20));
				}
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getPack_disp_nm().length()>100 ? dataList.getPack_disp_nm().substring(0, 100) : dataList.getPack_disp_nm()),false,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getBatch_no().length()>100 ? dataList.getBatch_no().substring(0, 100) : dataList.getBatch_no()),false,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getBatch_open_stock().toString()),true,true,20));
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getOpen_stock_value().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getGrn_rej().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getIaa_in().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((side_tot_inward.toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getIaa_out().toString()),true,true,20));
				
				
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getSw_out().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((side_tot_outward.toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getClos_qty().toString()),true,true,20));
				bodytable.addCell(PdfStyle.createValueCellWithHeight((dataList.getClos_value().toString()),true,true,20));
				
				
//				Calculations
				
				ptot_open_stk=ptot_open_stk.add(dataList.getOpen_stock_value());
				ptot_out_iaa=ptot_out_iaa.add(dataList.getIaa_out_value());
				ptot_out_stk_wth=ptot_out_stk_wth.add(dataList.getSw_out_value());
				ptot_out_total=ptot_out_total.add(side_tot_outward);
				ptot_close_stk=ptot_close_stk.add(dataList.getClos_value());
				
				gtot_open_stk=gtot_open_stk.add(dataList.getOpen_stock_value());
				gtot_out_iaa=gtot_out_iaa.add(dataList.getIaa_out_value());
				gtot_out_stk_wth=gtot_out_stk_wth.add(dataList.getSw_out_value());
				gtot_out_total=gtot_out_total.add(side_tot_outward);
				gtot_close_stk=gtot_close_stk.add(dataList.getClos_value());
				
				
				old_div=new_div;
				old_stock=new_stock;
				old_prod=new_prod;
				
				rows++;	 
				
				
	
			}
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(("Product Total : "),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_open_stk.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_iaa.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_stk_wth.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_out_total.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((ptot_close_stk.toString()),true,true,20));
			 
			 ptot_open_stk = BigDecimal.ZERO;
			 ptot_out_iaa = BigDecimal.ZERO;
			 ptot_out_stk_wth = BigDecimal.ZERO;
			 ptot_out_total = BigDecimal.ZERO;
			 ptot_close_stk = BigDecimal.ZERO;
			
		
			
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder(("Grand  Total :"),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_open_stk.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_out_iaa.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_out_stk_wth.toString()),true,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_out_total.toString()),true,true,20));
			
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((""),false,true,20));
			bodytable.addCell(PdfStyle.createValueCellWithHeightBoldTopBorder((gtot_close_stk.toString()),true,true,20));
			
			
			 
			newPage(column, bodytable, rows);
			document.newPage();
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (document != null) {
					document.close();
				}
				if (file != null) {
					file.close();
				}
				if (os != null) {
					os.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		System.out.println("PATH$%#$#$$$$#$$" + path + "\\files\\" + filename);
		return filename;
	}


}
