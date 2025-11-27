package com.excel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excel.bean.FieldStaffBean;
import com.excel.bean.TerritoryBean;
import com.excel.bean.TerritoryData;
import com.excel.model.Abolish_Terr_Csv;
import com.excel.model.Abolish_Terr_Log;
import com.excel.model.DG_TERRMAST_UPLOAD_FOR_GCO;
import com.excel.model.Dg_AbolishTerr_Codes;
import com.excel.model.DivField;
import com.excel.model.FieldStaff;
import com.excel.model.Location;
import com.excel.model.Scheme_Summary;
import com.excel.model.TERR_MASTER_UPLOAD_TEMPLATE;
import com.excel.model.Team;
import com.excel.model.TerrMaster;
import com.excel.model.V_Terr_Master;
import com.excel.repository.GoApptiveRepository;
import com.excel.repository.LocationRepository;
import com.excel.repository.TerritoryRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.utility.MedicoConstants;
import com.excel.utility.ReportFormats;
import com.excel.utility.Utility;

import java.util.LinkedHashMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Service
public class TerritoryServiceImpl implements TerritoryService, MedicoConstants {
	@Autowired
	TerritoryRepository territoryRepository;
	@Autowired
	TransactionalRepository transactionalRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	GoApptiveRepository goApptiveRepository;
	@Autowired
	DivisionMasterService divMastService;

	@Override
	public List<DivField> getLevelForTerritory(String divId) {
		return this.territoryRepository.getLevelForTerritory(divId);
	}

	@Override
	public List<DivField> getLevelForTerritoryModify(String divId, String fsCategory) {
		return this.territoryRepository.getLevelForTerritoryModify(divId, fsCategory);
	}

	@Override
	public Boolean checkUniqueTerrCode(String divId, String terrCode) {
		return this.territoryRepository.checkUniqueTerrCode(divId, terrCode);
	}

	@Override
	public List<TerrMaster> getTerrCodeList(String level, String divId, String trainingInd) {
		return this.territoryRepository.getTerrCodeList(level, divId, trainingInd);
	}

	@Override
	public List<V_Terr_Master> getAllTerrCodeList(String company) {
		return this.territoryRepository.getAllTerrCodeList(company);
	}

//	@Override
//	public List<FieldStaff> getRbm(String levelCode,String trainingInd) {
//		return this.territoryRepository.getRbm(levelCode,trainingInd);
//	}
	@Override
	public List<TerrMaster> getMgr(String levelCode, String trainingInd, String divId) {
		return this.territoryRepository.getMgr(levelCode, trainingInd, divId);
	}

	@Override
	public List<TerrMaster> getMgrTwo(String mgrId) {
		return this.territoryRepository.getMgrTwo(mgrId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveTerritory(@RequestBody TerritoryBean tb, HttpServletRequest request) throws Exception {
		TerrMaster tm = new TerrMaster();
		tm.setCompany(tb.getCompany());
		tm.setFin_year_id(Long.valueOf(tb.getFinYear()));

		tm.setTerr_code(tb.getTerritoryCode());
		tm.setTerr_designation(tb.getDesignation());
		tm.setTerr_districtcd(tb.getDistrict() == null ? "" : tb.getDistrict());
		tm.setTerr_div_id(Long.valueOf(tb.getDivision()));
		tm.setTerr_dom_exp(tb.getDomesticExport());
		tm.setTerr_hq_id(Long.valueOf(tb.getHq()));
		tm.setTERR_ins_dt(new Date());
		tm.setTERR_ins_ip_add(request.getRemoteAddr());
		tm.setTERR_ins_usr_id(tb.getEmpId());
		tm.setTerr_level_code(tb.getTerritoryLevelCode());

		tm.setTerr_mgr1_id(tb.getAbm() == null ? 0L : Long.valueOf(tb.getAbm()));
		tm.setTerr_mgr2_id(tb.getRbm() == null ? 0L : Long.valueOf(tb.getRbm()));
		tm.setTerr_mgr3_id(tb.getZbm() == null ? 0L : Long.valueOf(tb.getZbm()));
		tm.setTerr_mgr4_id(tb.getDsm() == null ? 0L : Long.valueOf(tb.getDsm()));
		tm.setTerr_mgr5_id(tb.getSbuHead() == null ? 0L : Long.valueOf(tb.getSbuHead()));
		tm.setTerr_mgr6_id(0L);

		tm.setTerr_name(tb.getTerritoryName());
		tm.setTerr_pool_ind(tb.getPoolIndicator());
		tm.setTerr_region_id(0L);
		tm.setTerr_regioncd(tb.getRegion() == null ? "" : tb.getRegion());

		tm.setTerr_sbu_id(Long.valueOf(tb.getSbu()));
		tm.setTerr_state_id(Long.valueOf(tb.getState()));
		tm.setTerr_team_id(tb.getTeam() == null ? 0 : Long.valueOf(tb.getTeam()));
		tm.setTerr_training(tb.getTrainingInd());

		// tm.setTERR_mod_dt("");
		tm.setTERR_mod_ip_add("");
		tm.setTERR_mod_usr_id("");

		tm.setTerr_cfa_loc1(tb.getCfa1() == null ? 0L : Long.valueOf(tb.getCfa1()));
		tm.setTerr_cfa_loc2(tb.getCfa2() == null ? 0L : Long.valueOf(tb.getCfa2()));
		tm.setTerr_cfa_loc3(tb.getCfa3() == null ? 0L : Long.valueOf(tb.getCfa3()));

		tm.setTerr_zone_id(Long.valueOf(tb.getZone()));

		// Added
		tm.setTeam_code(tb.getTeamcode() == null ? "0" : tb.getTeamcode());
		tm.setTerr_status("A");
		//////////////////////////

		this.transactionalRepository.persist(tm);

	}

	@Override
	public List<TerrMaster> getTerrCodeListForModify(String level, String divId, String trainingInd) {
		return this.territoryRepository.getTerrCodeListForModify(level, divId, trainingInd);
	}

	@Override
	public void updateTerritory(String terrId, TerritoryBean tb, HttpServletRequest request) throws Exception {
		TerrMaster tm = this.territoryRepository.getObjectByTerrId(Long.valueOf(terrId));
		tm.setCompany(tb.getCompany());
		tm.setFin_year_id(Long.valueOf(tb.getFinYear()));
		tm.setTerr_code(tb.getTerritoryCode());
		tm.setTerr_designation(tb.getDesignation());
		tm.setTerr_districtcd(tb.getDistrict() == null ? "" : tb.getDistrict());
		tm.setTerr_div_id(Long.valueOf(tb.getDivision()));
		tm.setTerr_dom_exp(tb.getDomesticExport());
		tm.setTerr_hq_id(Long.valueOf(tb.getHq()));
//		tm.setTERR_ins_dt(new Date());
//		tm.setTERR_ins_ip_add(request.getRemoteAddr());
//		tm.setTERR_ins_usr_id(tb.getEmpId());
		tm.setTerr_level_code(tb.getTerritoryLevelCode());

		tm.setTerr_mgr1_id(tb.getAbm() == null ? 0L : Long.valueOf(tb.getAbm()));
		tm.setTerr_mgr2_id(tb.getRbm() == null ? 0L : Long.valueOf(tb.getRbm()));
		tm.setTerr_mgr3_id(tb.getZbm() == null ? 0L : Long.valueOf(tb.getZbm()));
		tm.setTerr_mgr4_id(tb.getDsm() == null ? 0L : Long.valueOf(tb.getDsm()));
		tm.setTerr_mgr5_id(tb.getSbuHead() == null ? 0L : Long.valueOf(tb.getSbuHead()));
		tm.setTerr_mgr6_id(0L);

		tm.setTerr_name(tb.getTerritoryName());
		tm.setTerr_pool_ind(tb.getPoolIndicator());
		tm.setTerr_region_id(0L);
		tm.setTerr_regioncd(tb.getRegion() == null ? "" : tb.getRegion());

		tm.setTerr_sbu_id(Long.valueOf(tb.getSbu()));
		tm.setTerr_state_id(Long.valueOf(tb.getState()));
		tm.setTerr_team_id(tb.getTeam() == null ? 0 : Long.valueOf(tb.getTeam()));
		tm.setTerr_training(tb.getTrainingInd());

		tm.setTERR_mod_dt(new Date());
		tm.setTERR_mod_ip_add(request.getRemoteAddr());
		tm.setTERR_mod_usr_id(tb.getEmpId());

		tm.setTerr_cfa_loc1(tb.getCfa1() == null ? 0L : Long.valueOf(tb.getCfa1()));
		tm.setTerr_cfa_loc2(tb.getCfa2() == null ? 0L : Long.valueOf(tb.getCfa2()));
		tm.setTerr_cfa_loc3(tb.getCfa3() == null ? 0L : Long.valueOf(tb.getCfa3()));

		tm.setTerr_zone_id(Long.valueOf(tb.getZone()));

		///////////////////////// added
		tm.setTeam_code(tb.getTeamcode() == null ? "0" : tb.getTeamcode());
		tm.setTerr_status(tb.getStatus());

		this.transactionalRepository.update(tm);
		this.goApptiveRepository.deleteGoapptiveTerritory(tm.getTerr_code(), tm.getTerr_div_id());
	}

	@Override
	public List<V_Terr_Master> getTextParameterTerrDetail(String company, String name, String parameter, String text) {
		return this.territoryRepository.getTextParameterTerrDetail(company, name, parameter, text);
	}

	@Override
	public List<TerrMaster> getTerrDetailById(String terrId) {
		return this.territoryRepository.getTerrDetailById(terrId);
	}
//	@Override
//	public List<DivField> getLevelForTerritoryModify() {
//		return this.territoryRepository.getLevelForTerritoryModify();
//	}

	@Override
	public Team getTeamCodeByTeamId(String teamId) throws Exception {
		// TODO Auto-generated method stub
		return territoryRepository.getTeamCodeByTeamId(teamId);
	}

	@Override
	public Long getTeamIdByFsId(String fsid) throws Exception {
		// TODO Auto-generated method stub
		return territoryRepository.getTeamIdByFsId(fsid);
	}

	@Override
	public Long getTeamIdByHqId(String hqid) throws Exception {
		// TODO Auto-generated method stub
		return territoryRepository.getTeamIdByHqId(hqid);
	}

	@Override
	public TerrMaster getTerrDetailByTerrCode(String terr_code, Long terr_div_id) {
		// TODO Auto-generated method stub
		return territoryRepository.getTerrDetailByTerrCode(terr_code, terr_div_id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveAbolishTerrCsv(List<TerritoryData> territoryDataList, String fileName, String username)
			throws Exception {
		try {
			int count = 2;

			for (TerritoryData td : territoryDataList) {
				Abolish_Terr_Csv abolishTerrCsv = new Abolish_Terr_Csv();
				abolishTerrCsv.setDiv_name(td.getTeamName());
				abolishTerrCsv.setTerr_code(td.getTerritoryCode());
				abolishTerrCsv.setRemarks(td.getRemarks());
				abolishTerrCsv.setAbol_fieldstaff_ind(td.getAbol_fieldstaff_ind());
				abolishTerrCsv.setFilename(fileName);
				abolishTerrCsv.setAbol_ins_dt(new Date());
				abolishTerrCsv.setAbol_ins_usr_id(username);
				abolishTerrCsv.setAbol_status("E");

				if (!td.getTeamName().isEmpty()) {
					try {
						Long divId = divMastService.getDivIdByName(td.getTeamName().trim());
						abolishTerrCsv.setDiv_id(divId);
					} catch (Exception e) {
						abolishTerrCsv.setDiv_id(0L);
					}
				}
				abolishTerrCsv.setCsv_rownum(count++);
				this.transactionalRepository.persist(abolishTerrCsv);
			}
		} catch (Exception e) {
			throw e;

		}

	}

	@Override
	public Map<String, Object> validateCsvFile(MultipartFile file, String username) throws Exception {
		Map<String, Object> response = new HashMap<>();
		Map<Integer, List<String>> errorMap = new LinkedHashMap<>();
		Map<Integer, List<String>> terrMap = new LinkedHashMap<>();
		Map<Integer, List<String>> terrFieldMap = new LinkedHashMap<>();
		Map<Integer, List<String>> terrWithoutFieldMap = new LinkedHashMap<>();
		List<TerritoryData> territoryDataList = new ArrayList<>();
		String fileName = file.getOriginalFilename();
		Long csvCount = 0L;
		Long errCount = 0L;
		Long terrCount = 0L;
		Long terrFieldCount = 0L;
		Long terrWithoutFieldCount = 0L;

		try {

			if (this.territoryRepository.isFileExist(file.getOriginalFilename())) {
				response.put("FILE_EXIST", "Y");
				response.put("message", "file already uploaded ");
				return response;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			boolean isHeader = true;

			while ((line = reader.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}

				String[] columns = line.split(",");

				String[] paddedColumns = Utility.padCsvColumns(columns, 4);

				TerritoryData data = new TerritoryData();
				data.setTeamName(!paddedColumns[0].isEmpty() ? paddedColumns[0] : "");
				data.setTerritoryCode(!paddedColumns[1].isEmpty() ? paddedColumns[1] : "");
				data.setAbol_fieldstaff_ind(!paddedColumns[2].isEmpty() ? paddedColumns[2] : "");
				data.setRemarks(!paddedColumns[3].isEmpty() ? paddedColumns[3] : "");
				territoryDataList.add(data);
				csvCount++;
			}

			saveAbolishTerrCsv(territoryDataList, fileName, username);

			List<Dg_AbolishTerr_Codes> list = this.territoryRepository.getAbolishTerrCsvCount(fileName);

			errCount = this.territoryRepository.errCount(fileName, "Y");
			terrCount = this.territoryRepository.errCount(fileName, "T");
			terrFieldCount = this.territoryRepository.errCount(fileName, "B");
			terrWithoutFieldCount = this.territoryRepository.errCount(fileName, "NF");

			List<Abolish_Terr_Log> errorList = this.territoryRepository.getReport(fileName, "Y");
			List<Abolish_Terr_Log> terrList = this.territoryRepository.getReport(fileName, "T");
			List<Abolish_Terr_Log> terrFieldList = this.territoryRepository.getReport(fileName, "B");
			List<Abolish_Terr_Log> terrWithoutFieldList = this.territoryRepository.getReport(fileName, "NF");

			for (Abolish_Terr_Log record : errorList) {
				int csvRowNum = record.getCsv_rownum();

				List<String> data = new ArrayList<>();
				data.add(record.getDiv_name());
				data.add(record.getTerr_code());
				data.add(record.getErr_msg());
				data.add(record.getIns_usr_name());

				errorMap.put(csvRowNum, data);
			}

			for (Abolish_Terr_Log record : terrList) {
				int csvRowNum = record.getCsv_rownum();

				List<String> data = new ArrayList<>();
				data.add(record.getDiv_name());
				data.add(record.getTerr_code());
				data.add(record.getErr_msg());
				data.add(record.getIns_usr_name());

				terrMap.put(csvRowNum, data);
			}

			for (Abolish_Terr_Log record : terrFieldList) {
				int csvRowNum = record.getCsv_rownum();

				List<String> data = new ArrayList<>();
				data.add(record.getDiv_name());
				data.add(record.getTerr_code());
				data.add(record.getErr_msg());
				data.add(record.getIns_usr_name());

				terrFieldMap.put(csvRowNum, data);
			}

			for (Abolish_Terr_Log record : terrWithoutFieldList) {
				int csvRowNum = record.getCsv_rownum();

				List<String> data = new ArrayList<>();
				data.add(record.getDiv_name());
				data.add(record.getTerr_code());
				data.add(record.getErr_msg());
				data.add(record.getIns_usr_name());

				terrWithoutFieldMap.put(csvRowNum, data);
			}

			String reportExcelFileName = this.genarateXlsxReport(errorMap, terrMap, terrFieldMap, terrWithoutFieldMap,fileName.substring(0, fileName.length() - 4),
					csvCount,errCount,terrCount, terrFieldCount,terrWithoutFieldCount);
			
			File dir = new File(new File(MedicoConstants.TERR_CSV_PATH) + "//Uploaded TerrCSV");
			if (!dir.exists()) {
				dir.mkdir();
			}

			File savedFile = new File(dir, fileName);
			file.transferTo(savedFile);

			response.put("csvCount", csvCount);
			response.put("errCount", errCount);
			response.put("terrCount", terrCount);
			response.put("terrFieldCount", terrFieldCount);
			response.put("terrWithoutFieldCount", terrWithoutFieldCount);
			response.put("fileName", reportExcelFileName);

		} catch (Exception e) {
			throw e;
		}

		return response;
	}
	
	
	private String genarateXlsxReport(Map<Integer, List<String>> errorMap, Map<Integer, List<String>> terrMap,
			Map<Integer, List<String>> terrFieldMap, Map<Integer, List<String>> terrWithoutFieldMap, String filename, 
			Long csvCount, Long errCount, Long terrCount, Long terrFieldCount, Long terrWithoutFieldCount) throws IOException {
		
		String fileName = "Report_For__" + filename+ ".xlsx";
		String filepath = REPORT_FILE_PATH + fileName;
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}
						
			XSSFFont font =  workbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
		    
			CellStyle header = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.AQUA, HorizontalAlignment.CENTER,
					VerticalAlignment.CENTER, false, BorderStyle.THIN);
			header.setFont(font);

			CellStyle colorStyle = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.LIGHT_YELLOW,
					HorizontalAlignment.LEFT, VerticalAlignment.CENTER, false, BorderStyle.THIN);

			CellStyle colorRight = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.LIGHT_YELLOW,
					HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, false, BorderStyle.THIN);

			CellStyle data = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.WHITE, HorizontalAlignment.LEFT,
					VerticalAlignment.CENTER, false, BorderStyle.THIN);

			CellStyle dataRight = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.WHITE,
					HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, false, BorderStyle.THIN);
			
			XSSFSheet summarySheet = workbook.createSheet("Summary");
			
	        XSSFRow row = null;
	        XSSFCell cell = null;
	        int rowCount = 0;
	        int colCount = 0;

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(colCount++);
	        cell.setCellValue("Uploaded CSV File Name");
	        cell.setCellStyle(header);

	        cell = row.createCell(colCount++);
	        cell.setCellValue(filename);
	        cell.setCellStyle(header);

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue("");
	        row.getCell(1).setCellStyle(dataRight);

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("Csv Row Count");
	        cell.setCellStyle(colorStyle);
	        row.createCell(1).setCellValue(csvCount);
	        row.getCell(1).setCellStyle(colorRight);
	        
	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue("");
	        row.getCell(1).setCellStyle(dataRight);
	        
	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("Territory Abolished");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue(terrCount);
	        row.getCell(1).setCellStyle(dataRight);

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("Territory with FieldStaff Abolished");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue(terrFieldCount);
	        row.getCell(1).setCellStyle(dataRight);

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("Territory without FieldStaff Abolished");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue(terrWithoutFieldCount);
	        row.getCell(1).setCellStyle(dataRight);

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("Errors Count");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue(errCount);
	        row.getCell(1).setCellStyle(dataRight);
	        
	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("");
	        cell.setCellStyle(data);
	        row.createCell(1).setCellValue("");
	        row.getCell(1).setCellStyle(dataRight);

	        row = summarySheet.createRow(rowCount++);
	        cell = row.createCell(0);
	        cell.setCellValue("Total Count");
	        cell.setCellStyle(colorStyle);
	        row.createCell(1).setCellValue(terrCount+terrFieldCount+terrWithoutFieldCount+errCount);
	        row.getCell(1).setCellStyle(colorRight);

			
	        String[] headers = {"Row No", "Team Name", "Territory Code", "Remarks", "Username"};
	        
	        createSheetWithHeaders(workbook, "Error List", errorMap, headers,filename);
	        createSheetWithHeaders(workbook, "Territory Abolished", terrMap, headers,filename);
	        createSheetWithHeaders(workbook, "Territory Fieldstaff Abolished", terrFieldMap, headers,filename);
	        createSheetWithHeaders(workbook, "Territory Without Fieldstaff Abolished", terrWithoutFieldMap, headers,filename);


			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return fileName;
	
	
	
	}

	
	private void createSheetWithHeaders(XSSFWorkbook workbook, String sheetName, Map<Integer, List<String>> dataMap, String[] headers, String filename) throws Exception {
	    XSSFSheet sheet = workbook.createSheet(sheetName);
	    XSSFRow row = null;
	    XSSFCell cell = null;
	    int rowCount = 0;
	    int colCount = 0;
	    
	    sheet.createFreezePane(0, 2);
	    
	    XSSFFont h_font =  workbook.createFont();
		h_font.setBold(true);
		h_font.setFontHeightInPoints((short)14);
		
		
		XSSFFont font =  workbook.createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short)12);
		
		
		CellStyle heading = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.LIGHT_YELLOW, HorizontalAlignment.CENTER,
		        VerticalAlignment.CENTER, false, BorderStyle.THIN);
		heading.setFont(h_font);

		CellStyle header = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.AQUA, HorizontalAlignment.CENTER,
		        VerticalAlignment.CENTER, false, BorderStyle.THIN);
		header.setFont(font);

		CellStyle data = createCellStyle(workbook, HSSFColor.HSSFColorPredefined.WHITE, HorizontalAlignment.LEFT,
		        VerticalAlignment.CENTER, false, BorderStyle.THIN);
		
		XSSFRow fileNameRow = sheet.createRow(rowCount);
		XSSFCell fileNameCell = fileNameRow.createCell(0);
		fileNameCell.setCellValue("UPLOADED CSV FILE NAME - " + filename);
		sheet.addMergedRegion(new CellRangeAddress(fileNameRow.getRowNum(), fileNameRow.getRowNum(), 0, headers.length - 1));
		fileNameCell.setCellStyle(heading);  
		rowCount++;

		XSSFRow headingRow = sheet.createRow(rowCount);
		XSSFCell errorCell = headingRow.createCell(0);
		errorCell.setCellValue(sheetName);
		sheet.addMergedRegion(new CellRangeAddress(headingRow.getRowNum(), headingRow.getRowNum(), 0, headers.length - 1));
		errorCell.setCellStyle(heading);  
		rowCount++;

	    row = sheet.createRow(rowCount);
	    for (int i = 0; i < headers.length; i++) {
	        cell = row.createCell(i);
	        cell.setCellValue(headers[i]);
	        cell.setCellStyle(header);
	    }

	    rowCount++;

	    for (Map.Entry<Integer, List<String>> entry : dataMap.entrySet()) {
	        Integer rowNum = entry.getKey();
	        List<String> values = entry.getValue();

	        row = sheet.createRow(rowCount);
	        colCount = 0;

	        cell = row.createCell(colCount++);
	        cell.setCellValue(rowNum);
	        cell.setCellStyle(data);

	        for (int i = 0; i < values.size(); i++) {
	            cell = row.createCell(colCount++);
	            cell.setCellValue(values.get(i));
		        cell.setCellStyle(data);
	        }

	        rowCount++;
	    }
	}
	
	
	private CellStyle createCellStyle(Workbook workbook, HSSFColor.HSSFColorPredefined fillColor,
			HorizontalAlignment alignment, VerticalAlignment verticalAlignment, boolean wrapText,
			BorderStyle borderStyle) {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(fillColor.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setAlignment(alignment);
		style.setVerticalAlignment(verticalAlignment);
		style.setWrapText(wrapText);
		style.setBorderBottom(borderStyle);
		style.setBorderLeft(borderStyle);
		style.setBorderTop(borderStyle);
		style.setBorderRight(borderStyle);
		return style;
	}
	@Override
	public Map<String, Object> saveTerritoy_master_csv(MultipartFile file, String username, String divId,
			String ip_addres) throws Exception {
		List<TERR_MASTER_UPLOAD_TEMPLATE> territoryDataList = new ArrayList<>();
		Long csvCount = 2L;
		List<DG_TERRMAST_UPLOAD_FOR_GCO> uploaded_data = new ArrayList<>();
		List<String> violationMessages = new ArrayList<>();
		String fileName = file.getOriginalFilename();
		Map<Long, List<String>> rowViolationsMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		Set<String> terrCodeSet = new HashSet();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Boolean hasError = false;
		String[] paddedColumns = null;
		Map<Long, List<String>> terrFieldMap = new LinkedHashMap<>();
		
		TERR_MASTER_UPLOAD_TEMPLATE bean_check_8_char = new TERR_MASTER_UPLOAD_TEMPLATE();
		
		Boolean eight_char_level_code=false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			boolean isHeader = true;
			while ((line = reader.readLine()) != null) {
				if (isHeader) {
					isHeader = false;
					continue;
				}
				String[] columns = line.split(",");
				paddedColumns = Utility.padCsvColumns(columns, 37);
				TERR_MASTER_UPLOAD_TEMPLATE bean = new TERR_MASTER_UPLOAD_TEMPLATE();
				bean.setTerr_id(1L);
				bean.setFin_year_id(Long.valueOf(paddedColumns[1]));
				bean.setCompany(paddedColumns[3]);
				bean.setTerr_code(paddedColumns[4]);
				bean.setTerr_div_id(Long.valueOf(divId));// div id
				bean.setTerr_level_code(paddedColumns[6]);
				bean.setTerr_team_id(1L);
				bean.setTerr_state_id(!paddedColumns[8].isEmpty() ? Long.valueOf(paddedColumns[8]) : 0L);
				bean.setTerr_name(paddedColumns[9]);
				bean.setTerr_pool_ind("N");
				bean.setTerr_hq_id(!paddedColumns[11].isEmpty() ? Long.valueOf(paddedColumns[11]) : 0L);
				bean.setTerr_mgr1_id(!paddedColumns[12].isEmpty() ? Long.valueOf(paddedColumns[12]) : 0L);
				bean.setTerr_mgr2_id(!paddedColumns[13].isEmpty() ? Long.valueOf(paddedColumns[13]) : 0L);
				bean.setTerr_mgr3_id(!paddedColumns[14].isEmpty() ? Long.valueOf(paddedColumns[14]) : 0L);
				bean.setTerr_mgr4_id(!paddedColumns[15].isEmpty() ? Long.valueOf(paddedColumns[15]) : 0L);
				bean.setTerr_mgr5_id(!paddedColumns[16].isEmpty() ? Long.valueOf(paddedColumns[16]) : 0L);
				bean.setTerr_mgr6_id(!paddedColumns[17].isEmpty() ? Long.valueOf(paddedColumns[17]) : 0L);
				bean.setTerr_dom_exp("D");
				bean.setTerr_region_id(0L);
				bean.setTerr_zone_id(0L);
				bean.setTerr_sbu_id(0L);
				bean.setTerr_regioncd(paddedColumns[22]);
				bean.setTerr_districtcd(paddedColumns[23]);
				bean.setTerr_designation(paddedColumns[24]);
				bean.setTerr_cfa_loc_pfz(paddedColumns[25]);
				bean.setTerr_cfa_loc2(paddedColumns[26]);
				bean.setTerr_cfa3_loc3_pipl(paddedColumns[27]);
				bean.setTerr_training("F");
				bean.setTerr_ins_usr_id(username);
				bean.setTerr_ins_dt(new Date());
				bean.setTerr_ins_ip_add(ip_addres);
				bean.setTeam_code("0");
				bean.setTerr_status("A");
				bean.setCfapfaid(0L);
				bean.setCfapiplid(0L);
				bean.setFilename(file.getOriginalFilename());
				territoryDataList.add(bean);

				
				if(bean.getTerr_code().trim().length()==8 && bean.getTerr_level_code().equals("001")) {
					eight_char_level_code=true;
				}
				csvCount++;
			}

			String terrCodes = territoryDataList.stream().map(bean -> "'" + bean.getTerr_code() + "'")
					.collect(Collectors.joining(", ", "(", ")"));

			// checking terr_code exist in database
			List<String> predefinedTerrCodes = this.territoryRepository.Active_getTerrCodeList_with_div_id(divId,
					terrCodes);
			// getting State ID exist in database
			List<String> state_ids = this.territoryRepository.getValid_State_id();

			csvCount = 2L;
			StringBuffer errorMessage = new StringBuffer();
			Set<String> predefinedTerrCodeSet = new HashSet<>(predefinedTerrCodes);
			Set<String> state_ids_set = new HashSet<>(state_ids);

			for (TERR_MASTER_UPLOAD_TEMPLATE bean : territoryDataList) {
				violationMessages = new ArrayList<>();
				String terrCodefile = bean.getTerr_code().trim();
				errorMessage = new StringBuffer();

				// bean validation
				Set<ConstraintViolation<TERR_MASTER_UPLOAD_TEMPLATE>> violations = validator.validate(bean);

				// tercode duplecate in csv
				if (!terrCodefile.isEmpty() && terrCodeSet.contains(terrCodefile)) {
					errorMessage.append("Territory code '" + terrCodefile + "' is duplicated., ");
				} else {
					terrCodeSet.add(terrCodefile);
				}

				// tercode duplecate in database
				String terrCode = bean.getTerr_code();
				if (predefinedTerrCodeSet.contains(terrCode)) {
					errorMessage.append("Territory code '" + terrCode + "' is Already Exist, ");
				}

				// valid state id code check
				String state_id = bean.getTerr_state_id().toString().trim();
				System.err.println(state_ids_set.contains(state_id));
				if (!state_ids_set.contains(state_id) && !state_id.equals("0")) {
					errorMessage.append("State Id  '" + state_id + "' is Invalid, ");
				}

				// adding bean validation to message
				for (ConstraintViolation<TERR_MASTER_UPLOAD_TEMPLATE> violation : violations) {
					errorMessage.append(violation.getMessage() + ", ");
				}

				if (!errorMessage.toString().isEmpty()) {
					hasError = true;
					violationMessages.add(errorMessage.toString().substring(0, errorMessage.toString().length() - 2));
					rowViolationsMap.put(csvCount, violationMessages);
				}
				csvCount++;
			}

			
			
			System.err.println("bean_check_8_char  :::"+eight_char_level_code);
			if (hasError) {
				map.put("ErrorMap", rowViolationsMap);
				map.put("result", false);
				fileName = this.genarateError_Excel_sheet(rowViolationsMap,
						file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4));
			} else {
				File dir = new File(new File(MedicoConstants.TERR_CSV_PATH) + "//Uploaded Terr-master-entry-CSV");
				if (!dir.exists()) {
					dir.mkdir();
				}
				File savedFile = new File(dir, fileName);
				file.transferTo(savedFile);
				System.err.println(territoryDataList);
				this.saveTerritoryData(territoryDataList);
				uploaded_data = this.territoryRepository.get_Uploaded_list(divId,eight_char_level_code);

				
				
				fileName = this.genarate_CheckList_Excel_sheet(uploaded_data,
						file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4));
				
				
				
				
				map.put("terrMap", terrFieldMap);
				map.put("result", true);
			}

			map.put("file_name", fileName);
			return map;

		} catch (Exception e) {
			throw e;
		}

	}

	private String genarate_CheckList_Excel_sheet(List<DG_TERRMAST_UPLOAD_FOR_GCO> uploaded_data, String filename)
			throws IOException {

		filename= "Check_list_For_"+filename +".xlsx";
		
		String filepath = REPORT_FILE_PATH +filename;
		
		
		
		
		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}

			XSSFSheet sheet = workbook.createSheet("Check List Report");

			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int roughcount1 = 1;

			   
		    XSSFFont h_font =  workbook.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)14);
			
			
			XSSFFont font =  workbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
			
		    
			
		    XSSFCellStyle header = workbook.createCellStyle();
			header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			header.setAlignment(HorizontalAlignment.CENTER);
			header.setVerticalAlignment(VerticalAlignment.CENTER);
			header.setBorderBottom(BorderStyle.THIN);
			header.setBorderLeft(BorderStyle.THIN);
			header.setBorderTop(BorderStyle.THIN);
			header.setBorderRight(BorderStyle.THIN);	
			header.setWrapText(false);
			header.setFont(font);

			
			XSSFCellStyle data = workbook.createCellStyle();
			data.setAlignment(HorizontalAlignment.LEFT);
			data.setVerticalAlignment(VerticalAlignment.CENTER);
			data.setWrapText(false);
			data.setBorderBottom(BorderStyle.THIN);
			data.setBorderLeft(BorderStyle.THIN);
			data.setBorderTop(BorderStyle.THIN);
			data.setBorderRight(BorderStyle.THIN);
			

			String[] headingNames = { "SL No", "Territori Div Name", "Team Name", "Territory Id", "Territory Code",
					"Territory Level Code", "Territory Name", "ABM_TERR_NAME", "RBM_TERR_NAME", "ZBM_TERR_NAME",
					"State ID", "State Name", "Zone Name", "HQ Name", "CFA_PFIZER", "CFA_PIPL" };

			row = sheet.createRow(rowCount);

			for (String heading : headingNames) {
				cell = row.createCell(colCount);
				cell.setCellValue(heading);
				cell.setCellStyle(header);
				colCount++;
			}

			rowCount++;
			for (DG_TERRMAST_UPLOAD_FOR_GCO bean : uploaded_data) {

				colCount = 0;

				row = sheet.createRow(rowCount);

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getRow());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getTerr_div_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getTeam_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getTerr_id());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getTerr_code());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getTerr_level_code());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getTerr_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getAbm_terr_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getRbm_terr_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getZbm_terr_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getState_id());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getState_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getZone_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getHq_name());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getCfa_pfizer());
				cell.setCellStyle(data);
				colCount++;

				cell = row.createCell(colCount);
				cell.setCellValue(bean.getCfa_pipl());
				cell.setCellStyle(data);

				rowCount++;
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return filename;

	}

	private String genarateError_Excel_sheet(Map<Long, List<String>> rowViolationsMap, String filename)
			throws IOException {
		
		filename= "Error_list_For_"+filename +".xlsx";
		
		String filepath = REPORT_FILE_PATH +filename;

		System.out.println("file path :::" + filepath);
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook();
			File f = new File(REPORT_FILE_PATH);
			if (!f.exists()) {
				if (f.mkdirs()) {
				} else {
					throw new RuntimeException("Could not create directory");
				}
			}

			XSSFSheet sheet = workbook.createSheet("Error Report");

			
			
			
			XSSFRow row = null;
			XSSFCell cell = null;
			int rowCount = 0;
			int colCount = 0;
			int roughcount1 = 1;
			
		    
		    XSSFFont h_font =  workbook.createFont();
			h_font.setBold(true);
			h_font.setFontHeightInPoints((short)14);
			
			
			XSSFFont font =  workbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short)12);
			
			
			XSSFCellStyle heading = workbook.createCellStyle();
			heading.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
			heading.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			heading.setAlignment(HorizontalAlignment.CENTER);
			heading.setFont(h_font);
		    
			
		    XSSFCellStyle header = workbook.createCellStyle();
			header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			header.setAlignment(HorizontalAlignment.CENTER);
			header.setVerticalAlignment(VerticalAlignment.CENTER);
			header.setBorderBottom(BorderStyle.THIN);
			header.setBorderLeft(BorderStyle.THIN);
			header.setBorderTop(BorderStyle.THIN);
			header.setBorderRight(BorderStyle.THIN);	
			header.setWrapText(false);
			header.setFont(font);

			
			XSSFCellStyle data = workbook.createCellStyle();
			data.setAlignment(HorizontalAlignment.LEFT);
			data.setVerticalAlignment(VerticalAlignment.CENTER);
			data.setWrapText(false);
			data.setBorderBottom(BorderStyle.THIN);
			data.setBorderLeft(BorderStyle.THIN);
			data.setBorderTop(BorderStyle.THIN);
			data.setBorderRight(BorderStyle.THIN);
			

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, 1));
			cell.setCellValue("File Name : " + filename.substring(0,filename.length()-5) + ".csv");
			cell.setCellStyle(heading);
			rowCount++;

			row = sheet.createRow(rowCount);
			cell = row.createCell(colCount);
			cell.setCellValue("Row Number");
			cell.setCellStyle(header);

			cell = row.createCell(colCount + 1);
			cell.setCellValue("Error Messages");
			cell.setCellStyle(header);

			rowCount++;
			for (Map.Entry<Long, List<String>> entry : rowViolationsMap.entrySet()) {

				colCount = 0;
				Long rowNumber = entry.getKey();
				List<String> violations = entry.getValue();
				System.out.println(rowNumber + " === " + violations.toString());

				row = sheet.createRow(rowCount);
				cell = row.createCell(colCount);
				cell.setCellValue(rowNumber);
				cell.setCellStyle(data);
				colCount++;

				StringBuffer errormessage = new StringBuffer();
				for (String s : violations) {
					errormessage.append(s + ", ");
				}

				cell = row.createCell(colCount);
				cell.setCellValue(errormessage.substring(0, errormessage.length() - 2));
				cell.setCellStyle(data);

				rowCount++;
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filepath);
			workbook.write(fileOutputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		
		
		return filename;

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private void saveTerritoryData(List<TERR_MASTER_UPLOAD_TEMPLATE> territoryDataList) throws Exception {
		for (TERR_MASTER_UPLOAD_TEMPLATE t : territoryDataList) {
			this.transactionalRepository.persist(t);
		}

	}

	@Override
	public Boolean checkFileExist_on_terr_master_upload_template_table(String originalFilename) {
		return this.territoryRepository.checkFileExist_on_terr_master_upload_template_table(originalFilename);

	}
}
