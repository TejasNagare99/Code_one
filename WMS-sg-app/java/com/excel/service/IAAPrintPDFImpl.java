package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.configuration.JwtProvider;
import com.excel.model.ViewIAAVoucherPrinting;
import com.excel.repository.UserMasterRepository;
import com.excel.utility.CodifyErrors;
import com.excel.utility.HeaderFooterPageEvent;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class IAAPrintPDFImpl implements IAAPrintPDFService {
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;

@Autowired private JwtProvider tokenProvider;
@Autowired private UserMasterService userMasterService;

	String path;
	final private int limit=16;
	float[] bodyColWidth = new float[] { 0.8f, 2.0f, 2.0f, 4.4f, 1.5f, 2.5f, 2.0f, 2.0f };
	
	@Override
	public String generateIaaPrint(List<ViewIAAVoucherPrinting> list,String companyname,String companycode,HttpSession session,HttpServletRequest request) throws Exception {

		/* ServletContext context = ServletActionContext.getServletContext(); */
		path =MedicoConstants.PDF_FILE_PATH;
		String comp_name = companyname;
		
		FileOutputStream os = null;
		PrintStream ps = null;
		long timevar = new Date().getTime();
		String filename = "IAA" + timevar + ".pdf";
		File docfile1=new File(path + "\\files\\");
		if (!docfile1.exists()) {
			if (docfile1.mkdirs()) {
			} else {
				throw new RuntimeException("Could not create directory.");
			}
		}
		File docfile = new File(path + "\\files\\" + filename);
		OutputStream file = new FileOutputStream(docfile);
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, file);
		Rectangle rect = new Rectangle(20, 28, 580, 830);
		writer.setBoxSize("header", rect);
		rect = new Rectangle(20, 28, 580, 50);
		writer.setBoxSize("footer", rect);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);
		 writer = usermasterservice.generatePDFlock(empId, writer);

		document.open();
		boolean isnew = false;
		ColumnText column = new ColumnText(writer.getDirectContent());
		try {
			List<ViewIAAVoucherPrinting> challans = list;
			Set<Integer> challanset = new LinkedHashSet<Integer>();
			for (ViewIAAVoucherPrinting challan : challans) {
				challanset.add(challan.getStkadj_id());
			}
			PdfPTable bodytable = null;
			
			for (Integer num : challanset){
				int rows = 0;
				int index=0;
				isnew = true;
				PdfPTable header = new PdfPTable(2);
				header.getDefaultCell().setPadding(0.0f);
				header.setWidthPercentage(100);
				Integer new_adj_dtl_id=0, old_adj_dtl_id=0;
				for (ViewIAAVoucherPrinting challan : challans) {
					if (num.compareTo(challan.getStkadj_id())==0) {

						if (rows >= limit) {
							bodytable = newPage(column, bodytable, event, false, rows);
							document.newPage();
							rows = 0;
						}

						if (isnew) {
							event.setHeader(createHeader(challan, comp_name));
							isnew = false;
							bodytable = new PdfPTable(8);
							bodytable.setWidthPercentage(100);
							bodytable.setWidths(bodyColWidth);
							
							PdfPCell remark = PdfStyle.createValueCellWithBottomBorder("          Remarks: "+challan.getRemarks(), false, true);
							remark.setColspan(8);
							bodytable.addCell(remark);
						}
						new_adj_dtl_id = challan.getAdjdtl_id();
						if(new_adj_dtl_id.compareTo(old_adj_dtl_id)!=0){
							PdfPCell sr = PdfStyle.createValueCell(""+(++index), false, true);
							sr.setHorizontalAlignment(1);
							sr.setBorderWidthTop(0.5f);
							PdfPCell reason = PdfStyle.createValueCellWithBottomBorder("Adjustment Reason: "+challan.getReason(), false, true);
							reason.setColspan(7);
							reason.setBorderWidthTop(0.5f);
							bodytable.addCell(sr);
							bodytable.addCell(reason);
						}
						
						bodytable.addCell(PdfStyle.createValueCellWithHeight("", false, true, 27));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(challan.getSmp_prod_type(), false, true, 27));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(challan.getSmp_prod_cd(), false, true, 27));
						String prod_name = challan.getSmp_prod_name();
						if(prod_name.length()>83){
							prod_name = prod_name.substring(0, 82);
						}
						bodytable.addCell(PdfStyle.createValueCellWithHeight(prod_name, false, true, 27));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(challan.getPacking(), false, true, 27));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(challan.getBatch_no(), false, true, 27));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(challan.getOut_qty()!=null ? challan.getOut_qty().toString() : "", true, true, 27));
						bodytable.addCell(PdfStyle.createValueCellWithHeight(challan.getIn_qty()!=null ? challan.getIn_qty().toString() : "", true, true, 27));
						rows++;
						old_adj_dtl_id=new_adj_dtl_id;
					}
					//System.err.println(num + ":Detail:"	+ challan.getProd_name());
				}
				
				newPage(column, bodytable, event, true,rows);
				document.newPage();
			}
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
	
	private PdfPTable createHeader(ViewIAAVoucherPrinting challan, String comp_name)
			throws DocumentException, MalformedURLException, IOException {
		
			PdfPTable header = new PdfPTable(1);  //main header table
			header.getDefaultCell().setPadding(0.0f);
			header.setWidthPercentage(100);
			
			PdfPCell title_1 = PdfStyle.createUltimateCellWithoutBorder(comp_name, 1, 5, 12, 18, false);
			PdfPCell title_2 = PdfStyle.createUltimateCellWithoutBorder("Inventory Adjustment Advice", 1, 5, 12, 18, false);
			header.addCell(title_1);
			header.addCell(title_2);
			header.addCell(PdfStyle.createLabelCellWithoutBorder(""));
			
			PdfPTable h1 = new PdfPTable(4);
			h1.setWidthPercentage(100); 
			h1.setWidths(new float[] {3.8f, 4.2f, 3.8f, 4.2f}); 
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorder("Location/Warehouse:"));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorderNoBold(challan.getLoc_nm()));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorder("IAA Number:"));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorderNoBold(challan.getStkadj_id().toString()));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorder("Date:"));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorderNoBold(challan.getSadj_dt()));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorder("Stock Adj. Ref. No.:"));
			h1.addCell(PdfStyle.createLabelCellLeftWithoutBorderNoBold(challan.getStkadj_no()));
			
			PdfPTable bodyHeader = new PdfPTable(8);  // set body column name
			bodyHeader.setWidthPercentage(100);
			bodyHeader.setWidths(this.bodyColWidth);
			bodyHeader.addCell(PdfStyle.createLabelCell("SNo"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Prod Type"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Prod Code"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Prod Name"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Pack"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Batch"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Qty Out"));
			bodyHeader.addCell(PdfStyle.createLabelCell("Qty In"));
			
			PdfPCell c1 = new PdfPCell(h1);
			c1.setBorder(0);
			header.addCell(c1);
			header.addCell(bodyHeader);
			
			return header;}

	public PdfPTable newPage(ColumnText column, PdfPTable bodytable,
			HeaderFooterPageEvent event, boolean isFooterData,int rows) throws Exception {
		try {
			PdfPCell cell = new PdfPCell();
			column.setSimpleColumn(20, 28, 580, 714);
			
			if(limit-rows>0){
				PdfPTable temp = new PdfPTable(8);
				PdfPTable temp1 = new PdfPTable(8);
				temp.setWidths(this.bodyColWidth);
				temp1.setWidths(this.bodyColWidth);
				
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				temp1.addCell(PdfStyle.createValueCellWithHeight("", false,true, 18));
				
				PdfPCell temp1_cell = new PdfPCell(temp1);
				temp1_cell.setBorder(0);
				temp1_cell.setColspan(8);
				temp.addCell(temp1_cell);
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
				temp.addCell(PdfStyle.createValueCellWithHeight("", false,true, 27));
								
				cell=new PdfPCell(temp);
				cell.setBorder(0);
				cell.setColspan(8);
				int m = limit-rows-4;
				for(int i=0;i<m;i++){
					bodytable.addCell(cell);
				}
			}
			
			cell=new PdfPCell(PdfStyle.createSingleLine());
			cell.setColspan(8);		
			bodytable.addCell(cell);
			
			column.addElement(bodytable);
			column.go();
			bodytable = new PdfPTable(8);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(this.bodyColWidth);

			PdfPTable ftable = new PdfPTable(3);
			ftable.setWidthPercentage(100); 
			ftable.setWidths(new float[] { 6.5f, 4.5f, 5f});
			
			PdfPCell f1 = PdfStyle.createLabelCellLeftWithoutBorder("Prepared By");
			PdfPCell f2 = PdfStyle.createLabelCellLeftWithoutBorder("Approved By");
			PdfPCell f3 = PdfStyle.createLabelCellLeftWithoutBorder("Warehouse Manager");
			f3.setHorizontalAlignment(2);
			ftable.addCell(f1);
			ftable.addCell(f2);
			ftable.addCell(f3);
			cell=new PdfPCell(ftable);
			cell.setBorder(0);
			PdfPTable footertable = new PdfPTable(1);
			footertable.setWidthPercentage(100);
			footertable.addCell(cell);
			event.setFooter(footertable);

		} catch (DocumentException e) {
			throw e;
		}
		return bodytable;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String generateIaaExcel(List<ViewIAAVoucherPrinting> lst, String companyname, String companycode,String empId)
			throws Exception {

		File ff = null;
		XSSFWorkbook wwbook = null;
		StringBuffer path = null;
		String fileName = null;
		XSSFRow row = null;
		XSSFCell cell = null;

		try {
			String _hostPath = MedicoConstants.REPORT_FILE_PATH;
			File file = new File(_hostPath);
			if (!file.exists()) {
				if (file.mkdir()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
			int mergeHeaderLength = 9;
			fileName = "InventoryAdjustmentAdvice" + new Date().getTime()+ ".xlsx";
			path = new StringBuffer(_hostPath).append("\\");
			path.append(fileName );
			
			String comp_name = companyname;
			ff = new File(path.toString());

			wwbook = new XSSFWorkbook();
			XSSFSheet sheet = wwbook.createSheet("Inventory adjustment advice");

			Font fontalignment = wwbook.createFont();
			fontalignment.setFontHeightInPoints((short) 13);
			fontalignment.setBold(true);

			CellStyle alignment = wwbook.createCellStyle();
			alignment.setFont(fontalignment);
			alignment.setBorderBottom(BorderStyle.NONE);
			alignment.setBorderLeft(BorderStyle.NONE);
			alignment.setBorderRight(BorderStyle.NONE);
			alignment.setBorderTop(BorderStyle.NONE);
			alignment.setWrapText(true);
			alignment.setAlignment(HorizontalAlignment.CENTER);
			alignment.setVerticalAlignment(VerticalAlignment.CENTER);
			alignment.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			alignment.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			alignment.setAlignment(HorizontalAlignment.CENTER);

			Font fontcenterAlign = wwbook.createFont();
			fontcenterAlign.setFontHeightInPoints((short) 11);
			CellStyle centerAlign = wwbook.createCellStyle();
			centerAlign.setAlignment(HorizontalAlignment.CENTER);

			Font fontalignment1 = wwbook.createFont();
			fontalignment1.setFontHeightInPoints((short) 11);
			fontalignment1.setBold(true);
			CellStyle alignment1 = wwbook.createCellStyle();
			alignment1.setAlignment(HorizontalAlignment.CENTER);
			alignment1.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
			alignment1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			alignment1.setAlignment(HorizontalAlignment.CENTER);
			alignment1.setBorderBottom(BorderStyle.THIN);
			alignment1.setBorderLeft(BorderStyle.THIN);
			alignment1.setBorderRight(BorderStyle.THIN);
			alignment1.setBorderTop(BorderStyle.THIN);
			alignment1.setFont(fontalignment1);

			Font fontalignment2 = wwbook.createFont();
			fontalignment2.setFontHeightInPoints((short) 11);
			fontalignment2.setBold(true);
			CellStyle alignment2 = wwbook.createCellStyle();
			alignment2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			alignment2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			alignment2.setAlignment(HorizontalAlignment.CENTER);
			alignment2.setBorderBottom(BorderStyle.THIN);
			alignment2.setBorderLeft(BorderStyle.THIN);
			alignment2.setBorderRight(BorderStyle.THIN);
			alignment2.setBorderTop(BorderStyle.THIN);
			alignment2.setFont(fontalignment2);

			Font fontrightAlign = wwbook.createFont();
			fontrightAlign.setFontHeightInPoints((short) 11);
			CellStyle rightAlign = wwbook.createCellStyle();
			rightAlign.setDataFormat(wwbook.createDataFormat().getFormat("##.00"));
			rightAlign.setAlignment(HorizontalAlignment.RIGHT);

			Font fontrightAlignNumber = wwbook.createFont();
			fontrightAlignNumber.setFontHeightInPoints((short) 11);
			CellStyle rightAlignNumber = wwbook.createCellStyle();
			rightAlignNumber.setAlignment(HorizontalAlignment.RIGHT);

			int colcount = 0, rowcount = 0;

			int slno = 1;
			Integer oldadjdtlid = 0;
			Integer newadjdtlid = 0;
			String oldstkadjno = "", newstkadjno = "";
			Boolean check = true;
			Boolean isFalse = false;

			for (ViewIAAVoucherPrinting summary : lst) {
				newstkadjno = summary.getStkadj_no();
				newadjdtlid = summary.getAdjdtl_id();
				if (!newstkadjno.equalsIgnoreCase(oldstkadjno)) {
					check = true;
					if (isFalse) {
						colcount = 0;
						rowcount++;
						rowcount++;

						row = sheet.createRow(rowcount);

						cell = row.createCell(colcount);
						sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
						cell.setCellValue("Prepared By");

						colcount++;
						cell = row.createCell(colcount);
						sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
						cell.setCellValue("Approved By");

						colcount++;
						cell = row.createCell(colcount);
						sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
						cell.setCellValue("");

						colcount++;
						cell = row.createCell(colcount);
						sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
						cell.setCellValue("Warehouse Manager");

						colcount++;
						rowcount += 3;
					}
					isFalse = true;
				}

				if (check) {
					colcount = 0;
					slno = 1;
					row = sheet.createRow(rowcount);

					cell = row.createCell(colcount);
					cell.setCellValue(comp_name);
					sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, mergeHeaderLength));
					cell.setCellStyle(alignment);

					// wsheet.getSettings().setFitHeight(300);

					rowcount++;
					row = sheet.createRow(rowcount);
					cell = row.createCell(colcount);
					cell.setCellValue("Inventory Adjustment Advice");
					sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, mergeHeaderLength));
					cell.setCellStyle(alignment);

					rowcount++;
					row = sheet.createRow(rowcount);
					cell = row.createCell(colcount);
					cell.setCellValue("");
					sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, mergeHeaderLength));
					cell.setCellStyle(alignment);

					colcount = 0;
					rowcount++;
					rowcount++;

					row = sheet.createRow(rowcount);
					cell = row.createCell(colcount);
					cell.setCellValue("Location/Warehouse:");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue(summary.getLoc_nm());
					colcount++;

					colcount += 6;

					cell = row.createCell(colcount);
					cell.setCellValue("IAA Number  :");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue(summary.getStkadj_id());
					colcount++;

					rowcount++;
					colcount = 0;

					row = sheet.createRow(rowcount);
					cell = row.createCell(colcount);
					cell.setCellValue("Stock Adjustment Ref No. :");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue(summary.getStkadj_no());
					colcount++;

					colcount += 6;
					cell = row.createCell(colcount);
					cell.setCellValue("IAA Date    :");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue(summary.getSadj_dt());
					colcount++;

					check = false;

					// Heading
					colcount = 0;
					rowcount++;

					row = sheet.createRow(rowcount);
					cell = row.createCell(colcount);
					cell.setCellValue("SL No.");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Prod Type");
					cell.setCellStyle(alignment1);
					colcount++;
					/*
					 * header = new Label(col++, row, "Type", alignment1); wsheet.addCell(header);
					 */

					cell = row.createCell(colcount);
					cell.setCellValue("Prod Code");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Prod Name");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Pack");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Batch");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Adjustment Reason");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Qty Out");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Qty In");
					cell.setCellStyle(alignment1);
					colcount++;

					cell = row.createCell(colcount);
					cell.setCellValue("Remarks");
					cell.setCellStyle(alignment1);
					colcount++;

				}

				rowcount++;
				colcount = 0;

				row = sheet.createRow(rowcount);

				if (newadjdtlid != oldadjdtlid) {

					// wsheet.mergeCells(col, row, col, row + 1);
					// number = new Number(col++, row, slno++);
					// wsheet.addCell(number);
					cell = row.createCell(colcount);
					cell.setCellValue(slno++);
					colcount++;

				} else {
					colcount++;
				}

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getSmp_prod_type());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getSmp_prod_cd());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getSmp_prod_name());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getPacking());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getBatch_no());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getReason());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getOut_qty().setScale(2, RoundingMode.HALF_UP).doubleValue());
				colcount++;

				cell = row.createCell(colcount);
				cell.setCellValue(summary.getIn_qty().setScale(2, RoundingMode.HALF_UP).doubleValue());
				colcount++;
				/*
				 * header = new Label(col++, row, summary.getAdjdtl_remarks());
				 * wsheet.addCell(header);
				 */

				if (newadjdtlid != oldadjdtlid) {

					// wsheet.mergeCells(col, row, col, row + 1);
					cell = row.createCell(colcount);
					cell.setCellValue(summary.getRemarks());
					colcount++;

				} else {
					colcount++;
				}

				oldadjdtlid = newadjdtlid;
				oldstkadjno = newstkadjno;

			}
			colcount = 0;
			rowcount++;
			rowcount++;

			row = sheet.createRow(rowcount);

			cell = row.createCell(colcount);
			sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
			cell.setCellValue("Prepared By");

			colcount++;
			cell = row.createCell(colcount);
			sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
			cell.setCellValue("Approved By");

			colcount++;
			cell = row.createCell(colcount);
			sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
			cell.setCellValue("");

			colcount++;
			cell = row.createCell(colcount);
			sheet.addMergedRegion(new CellRangeAddress(rowcount, rowcount, colcount, ++colcount));
			cell.setCellValue("Warehouse Manager");

			colcount++;

			FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
			wwbook.write(fileOutputStream);
			fileName=usermasterservice.generateExcellock(path.toString(), fileName,empId,".xlsx");


		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;

		} finally {
			if (wwbook != null) {
				wwbook.close();
			}

		}
		return fileName;
	}

}
