package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.excel.bean.ReportBean;
import com.excel.model.ProductMasterAttrib;
import com.excel.utility.ExcelFormat;
import com.excel.utility.MedicoConstants;

@Service
public class ProductMasterDownloadServiceImpl implements ProductMasterDownloadService {

	@Override
	public String GenerateProductMasterReport(ReportBean bean, List<ProductMasterAttrib> DataList) throws Exception {
		System.out.println("ProductMasterAction.excelDownload()");
		File ff = null;
		HSSFWorkbook wb = null;
		StringBuffer path = null;
		long l = new Date().getTime();
		String fileName = "productMaster_" + l + ".xls";
		String filePath = MedicoConstants.REPORT_FILE_PATH;
		path = new StringBuffer(filePath).append("\\");
		path.append(fileName);
		ff = new File(path.toString());
		System.out.println("ProductMasterAction.getExcel()");
		try {
			Iterator<ProductMasterAttrib> it = DataList.iterator();

			 wb = new HSSFWorkbook();

			HSSFSheet ws = wb.createSheet("PRODUCT MASTER");

			ExcelFormat excelFormat = new ExcelFormat();
			CellStyle tabHeading = excelFormat.tabHeading(wb);
			CellStyle decimalformat = excelFormat.decimalformat(wb);
			CellStyle doubleformat = excelFormat.doubleformat(wb);
			CellStyle dataleft = excelFormat.dataleft(wb);
			CellStyle dataright = excelFormat.dataright(wb);

			HSSFRow row = null;
			HSSFCell cell = null;
			int rowCount = 0, col = 0;

			row = ws.createRow(rowCount);
			cell = row.createCell(col);
			cell.setCellValue("DIV_DISP_NM");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PROD_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PROD_CD");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_COMPANY");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PROD_NAME");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PACK_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ALTER_NAME");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_LAUNCH_DT");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_DISCONT_DT");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_INV_GRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_UOM_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_FORM_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SCH_IND");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_DIV_RQ_IND");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_EXPIRY_RQ_IND");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_BATCH_RQ_IND");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ALLOC_MULTIPLES");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ABC_IND");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_STOCK_DAYS");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PROD_TYPE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SAMPLING_TYPE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SHELF_LIFE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_MFG_LOC_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SHORT_EXPIRY_DAYS");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PROD_BATCH_SIZE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ERP_PROD_CD");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SA_PROD_GROUP");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_THP_GRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_THP_SGRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_MOLE_GRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_MOLE_SGRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PMT_GRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PMT_SGRP_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_QTY_SHIPPER");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_QTY_BOX");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_QTY_STRIP");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_MAX_ALLOC_QTY");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_MIN_ALLOC_QTY");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_STD_ALLOC_QTY");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SHIPPER_VOL");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SHIPPER_NWT");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SHIPPER_GWT");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_EXCLUDE_LOC");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_EXCLUDE_PARTY");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_SPEC_DIV_IND");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_COG_RATE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_COG_EXE_RATE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_RATE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_COSTING_RATE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_MKTG_RATE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_NRV");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_DISPLAY_RATE");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_STD_DIV_ID");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ins_usr_id");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_mod_usr_id");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ins_dt");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_mod_dt");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_ins_ip_add");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_mod_ip_add");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_status");
			cell.setCellStyle(tabHeading);
			col++;

			cell = row.createCell(col);
			cell.setCellValue("SMP_PROD_NAME_OLD");
			cell.setCellStyle(tabHeading);
			col++;

			rowCount++;

			it = DataList.iterator();
			while (it.hasNext()) {
				ProductMasterAttrib common = it.next();
				col = 0;

				row = ws.createRow(rowCount);
				cell = row.createCell(col);
				cell.setCellValue(common.getDIV_DISP_NM());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_PROD_ID() == null ? "0" : common.getSMP_PROD_ID()));
				cell.setCellStyle(decimalformat);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_PROD_CD());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_COMPANY());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_PROD_NAME());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue( common.getSMP_PACK_ID());
				cell.setCellStyle(dataleft);
				col++;

				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_ALTER_NAME());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_LAUNCH_DT());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_DISCONT_DT());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getINV_GRP_ID() == null ? "0" : common.getINV_GRP_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_UOM_ID() == null ? "0" : common.getSMP_UOM_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_FORM_ID() == null ? "0" : common.getSMP_FORM_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_SCH_IND());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_DIV_RQ_IND());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_EXPIRY_RQ_IND());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_BATCH_RQ_IND());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_ALLOC_MULTIPLES());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_ABC_IND());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_STOCK_DAYS() == null ? "0" : common.getSMP_STOCK_DAYS()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_PROD_TYPE());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_SAMPLING_TYPE());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_SHELF_LIFE());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_MFG_LOC_ID() == null ? "0" : common.getSMP_MFG_LOC_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(
						common.getSMP_SHORT_EXPIRY_DAYS() == null ? "0" : common.getSMP_SHORT_EXPIRY_DAYS()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(
						common.getSMP_PROD_BATCH_SIZE() == null ? "0" : common.getSMP_PROD_BATCH_SIZE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_PROD_CD());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_SA_PROD_GROUP() == null ? "0" : common.getSMP_SA_PROD_GROUP()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_THP_GRP_ID() == null ? "0" : common.getSMP_THP_GRP_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_THP_SGRP_ID() == null ? "0" : common.getSMP_THP_SGRP_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_MOLE_GRP_ID() == null ? "0" : common.getSMP_MOLE_GRP_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_MOLE_SGRP_ID() == null ? "0" : common.getSMP_MOLE_SGRP_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Integer.parseInt(common.getSMP_PMT_GRP_ID() == null ? "0" : common.getSMP_PMT_GRP_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_PMT_SGRP_ID() == null ? "0" : common.getSMP_PMT_SGRP_ID());
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_QTY_SHIPPER() == null ? "0" : common.getSMP_QTY_SHIPPER()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_QTY_BOX() == null ? "0" : common.getSMP_QTY_BOX()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_QTY_STRIP() == null ? "0" : common.getSMP_QTY_STRIP()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_MAX_ALLOC_QTY() == null ? "0" : common.getSMP_MAX_ALLOC_QTY()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_MIN_ALLOC_QTY() == null ? "0" : common.getSMP_MIN_ALLOC_QTY()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_STD_ALLOC_QTY() == null ? "0" : common.getSMP_STD_ALLOC_QTY()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_SHIPPER_VOL() == null ? "0" : common.getSMP_SHIPPER_VOL()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_SHIPPER_NWT() == null ? "0" : common.getSMP_SHIPPER_NWT()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_SHIPPER_GWT() == null ? "0" : common.getSMP_SHIPPER_GWT()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_EXCLUDE_LOC());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_EXCLUDE_PARTY());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_SPEC_DIV_IND());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_COG_RATE() == null ? "0" : common.getSMP_COG_RATE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_COG_EXE_RATE() == null ? "0" : common.getSMP_COG_EXE_RATE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_RATE() == null ? "0" : common.getSMP_RATE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_COSTING_RATE() == null ? "0" : common.getSMP_COSTING_RATE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_MKTG_RATE() == null ? "0" : common.getSMP_MKTG_RATE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_NRV() == null ? "0" : common.getSMP_NRV()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_DISPLAY_RATE() == null ? "0" : common.getSMP_DISPLAY_RATE()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(Double.parseDouble(common.getSMP_STD_DIV_ID() == null ? "0" : common.getSMP_STD_DIV_ID()));
				cell.setCellStyle(decimalformat);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_ins_usr_id());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_mod_usr_id());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_ins_dt());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_mod_dt());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_ins_ip_add());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_mod_ip_add());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_status());
				cell.setCellStyle(dataleft);
				col++;
				
				cell = row.createCell(col);
				cell.setCellValue(common.getSMP_PROD_NAME_OLD());
				cell.setCellStyle(dataleft);
				col++;

				rowCount++;
			}

		} finally {
			 FileOutputStream fileOut = new FileOutputStream(path.toString());
			 wb.write(fileOut);
			    fileOut.close();
			    wb.close();
		}
		return fileName;
	}

}