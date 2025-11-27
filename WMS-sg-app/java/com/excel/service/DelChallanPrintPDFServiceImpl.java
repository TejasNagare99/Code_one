package com.excel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.bean.PrintBean;
import com.excel.configuration.JwtProvider;
import com.excel.model.Usermaster;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.excel.utility.PdfStyle;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class DelChallanPrintPDFServiceImpl implements DelChallanPrintPDFService{
	@Autowired
	private UserMasterRepository userMasterRepository;
	@Autowired private UserMasterService usermasterservice;
	@Autowired private JwtProvider tokenProvider;
	@Autowired private UserMasterService userMasterService;
	
	int add = 0;
	private String path;
	private int limit = 8;
	private float[] bodyTableWidth = new float[] { 5.6f, 5.6f };
	@Override
	public String getPdf(List<PrintBean> challans,HttpSession session,HttpServletRequest request) throws Exception {

		String path = MedicoConstants.PDF_FILE_PATH;
		System.out.println("size " + challans.size());
		// System.out.println("path 11 : " + path);
		

			FileOutputStream os = null;
			PrintStream ps = null;
			long timevar = new Date().getTime();
			File file1 =  new File(path + "\\files\\");
			file1.mkdirs();
			String filename = "DeliveryChallanLabel" + timevar + ".pdf";
			// System.out.println("filename 11 " + filename);
			File docfile = new File(path + "\\files\\" + filename);
			System.out.println("Hii 1");
			OutputStream file = new FileOutputStream(docfile);
			System.out.println("Hii 2");
			Document document = new Document(PageSize.A4);
			System.out.println("Hii 3");
			try {
			PdfWriter writer = PdfWriter.getInstance(document, file);
			System.out.println("Hii ");
			String uname = this.tokenProvider.getUsernameFromRequest(request);String empId = this.userMasterService.getEmpIdByUsername(uname);

			System.out.println("userid " + empId);
			 writer = usermasterservice.generatePDFlock(empId, writer);
			document.open();
			document.setMarginMirroringTopBottom(false);
			ColumnText column = new ColumnText(writer.getDirectContent());

			// PdfPTable maintable1 = new PdfPTable(2);
			// maintable1.setWidthPercentage(100);
			PdfPTable bodytable = new PdfPTable(2);
			bodytable.getDefaultCell().setPadding(0.0f);
			bodytable.setWidthPercentage(100);
			bodytable.setWidths(bodyTableWidth);
			int i = 0, box = 0, super_box = 0;

			PdfPCell hCell1 = PdfStyle.createUltimateCellWithoutBorder("      Invoice# : ", 0, 5, 11, 18, true);
			hCell1.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell2 = PdfStyle.createUltimateCellWithoutBorder("      Date : ", 0, 5, 11, 18, true);
			hCell2.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell3 = PdfStyle.createUltimateCellWithoutBorder("      Customer : ", 0, 5, 11, 18, true);
			hCell3.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell4 = PdfStyle.createUltimateCellWithoutBorder("      BU : ", 0, 5, 11, 18, true);
			hCell4.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell5 = PdfStyle.createUltimateCellWithoutBorder("      Location : ", 0, 5, 11, 18, true);
			hCell5.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell6 = PdfStyle.createUltimateCellWithoutBorder("      Docket No : ", 0, 5, 11, 18, true);
			hCell6.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell7 = PdfStyle.createUltimateCellWithoutBorder("      Courier Name : ", 0, 5, 11, 18, true);
			hCell7.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell hCell8 = PdfStyle.createUltimateCellWithoutBorder("      Box No : ", 0, 5, 11, 18, true);
			hCell8.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell emptyLabel = PdfStyle.createUltimateCellWithoutBorder(" ", 0, 5, 11, 18, true);
			PdfPCell emptyValue = PdfStyle.createUltimateCellWithoutBorder("", 0, 5, 11, 18, true);

			for (PrintBean list : challans) {
				box = list.getCases();
				super_box = super_box + box;
				if (box > 0) {
					// System.out.println("box::"+box+"::i::"+i);
					// create internal table

					// row 1
					PdfPCell vCell1 = PdfStyle.createUltimateCellWithoutBorder(list.getSumdsp_challan_no(), 0, 5, 11,
							18, false);
					vCell1.setHorizontalAlignment(Element.ALIGN_LEFT);

					// row 2
					PdfPCell vCell2 = PdfStyle.createUltimateCellWithoutBorder(list.getSumdsp_challan_dt(), 0, 5, 11,
							18, false);
					vCell2.setHorizontalAlignment(Element.ALIGN_LEFT);

					// row 3
					String cust_name = list.getCfa();
					String str_1 = "", str_2 = "";
					if (cust_name.length() > 20) {
						str_1 = cust_name.substring(0, 20);
						str_2 = cust_name.substring(20);
						// addExtraRow=false;
					} else {
						str_1 = cust_name;
					}

					PdfPCell vCell3_A = PdfStyle.createUltimateCellWithoutBorder(str_1, 0, 5, 11, 18, false);
					vCell3_A.setHorizontalAlignment(Element.ALIGN_LEFT);

					PdfPCell vCell3_B = PdfStyle.createUltimateCellWithoutBorder(str_2, 0, 5, 11, 18, false);
					vCell3_B.setHorizontalAlignment(Element.ALIGN_LEFT);

					// row 4
					PdfPCell vCell4 = PdfStyle.createUltimateCellWithoutBorder(list.getTeam(), 0, 5, 11, 18, false);
					vCell4.setHorizontalAlignment(Element.ALIGN_LEFT);

					// row 5
					PdfPCell vCell5 = PdfStyle.createUltimateCellWithoutBorder(list.getDptdestination(), 0, 5, 11, 18,
							false);
					vCell5.setHorizontalAlignment(Element.ALIGN_LEFT);

					// row 6
					PdfPCell vCell6 = PdfStyle.createUltimateCellWithoutBorder(list.getSumdsp_lr_no(), 0, 5, 11, 18,
							false);
					vCell6.setHorizontalAlignment(Element.ALIGN_LEFT);

					// row 7
					PdfPCell vCell7 = PdfStyle.createUltimateCellWithoutBorder(list.getTransporter(), 0, 5, 11, 18,
							false);
					vCell7.setHorizontalAlignment(Element.ALIGN_LEFT);

					for (int m = 1; m <= box; m++) {
						i++;
						PdfPTable internalBox = new PdfPTable(2);
						internalBox.setWidths(new float[] { 1.9f, 3.7f });
						internalBox.addCell(emptyLabel);
						internalBox.addCell(emptyValue);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(emptyValue);
						internalBox.addCell(hCell1);
						internalBox.addCell(vCell1);
						internalBox.addCell(hCell2);
						internalBox.addCell(vCell2);
						internalBox.addCell(hCell3);
						internalBox.addCell(vCell3_A);
						internalBox.addCell(emptyLabel);
						internalBox.addCell(vCell3_B);
						internalBox.addCell(hCell4);
						internalBox.addCell(vCell4);
						internalBox.addCell(hCell5);
						internalBox.addCell(vCell5);
						internalBox.addCell(hCell6);
						internalBox.addCell(vCell6);
						internalBox.addCell(hCell7);
						internalBox.addCell(vCell7);
						internalBox.addCell(hCell8);

						PdfPCell vCell8 = PdfStyle.createValueCellWithoutBorder(m + " of " + box, false, true);
						vCell8.setHorizontalAlignment(Element.ALIGN_LEFT);

						internalBox.addCell(vCell8);

						/*
						 * internalBox.addCell(emptyLabel); internalBox.addCell(emptyValue);
						 * internalBox.addCell(emptyLabel); internalBox.addCell(emptyValue);
						 */

						PdfPCell iCell = new PdfPCell(internalBox);
						iCell.setBorder(0);
						bodytable.addCell(iCell);

						// System.err.println("m::"+m+"i::"+i);
						if (limit == i) {
							column.setSimpleColumn(10, 10, 580, 820);
							column.addElement(bodytable);
							column.go();
							document.newPage();

							bodytable = new PdfPTable(2);
							bodytable.getDefaultCell().setPadding(0.0f);
							bodytable.setWidthPercentage(100);
							bodytable.setWidths(bodyTableWidth);
							i = 0;
						}

						/* i++; */
					}

					if (limit == i) {
						column.setSimpleColumn(10, 10, 580, 820);
						column.addElement(bodytable);
						column.go();
						document.newPage();

						bodytable = new PdfPTable(2);
						bodytable.getDefaultCell().setPadding(0.0f);
						bodytable.setWidthPercentage(100);
						bodytable.setWidths(bodyTableWidth);
						i = 0;
					}

				}

			}
			if (super_box % 2 != 0) {
				PdfPTable internalBox = new PdfPTable(2);
				internalBox.setWidths(new float[] { 1.9f, 3.7f });
				internalBox.addCell(emptyLabel);
				internalBox.addCell(emptyValue);
				PdfPCell iCell = new PdfPCell(internalBox);
				iCell.setBorder(0);
				bodytable.addCell(iCell);
			}
			column.setSimpleColumn(10, 10, 580, 820);
			column.addElement(bodytable);
			column.go();
			document.newPage();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			filename = "";
//			throw e;

		} catch (Throwable e1) {
			// System.out.println("Error Occurred :"+new
			// CodifyErrors().getErrorCode(e1));//uncomment asneeded -- System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
//			throw e1;
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
				e.printStackTrace();
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}
		return filename;

	}

}
