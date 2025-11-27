package com.excel.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excel.bean.ProductBean;
import com.excel.bean.TeamBrandMappingBean;
import com.excel.exception.MedicoException;
import com.excel.model.DIV_TEAM_BRAND;
import com.excel.model.DivMaster;
import com.excel.model.Team;
import com.excel.model.Usermaster;
import com.excel.repository.ReportRepository;
import com.excel.repository.TeamBrandMappingRepository;
import com.excel.repository.TransactionalRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.ApachePoiExcelFormat;
import com.excel.utility.MedicoConstants;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class TeamBrandMappingServiceImpl implements TeamBrandMappingService {
	@Autowired
	private TransactionalRepository transactionalRepository;
	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private TeamBrandMappingRepository teambrandmappingrepository;
	@Autowired private UserMasterRepository usermasterrepository;
	@Autowired private UserMasterService usermasterservice;

	@Override
	public List<Team> getTeamCodeData(String divId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getTeamCodeData(divId);
	}

	@Override
	public List<DivMaster> getDivIdList() throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getDivIdList();
	}

	@Override
	public List<Long> getSelectedBrandList(String empId, String divId,String teamId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getSelectedBrandList(empId,divId,teamId);
	}

	@Override
	public List<Team> getSelectedTeamId(String divId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getSelectedTeamId(divId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DIV_TEAM_BRAND saveTeamData(TeamBrandMappingBean bean, String companyname, String userId, String ip_addr)
			throws Exception {

		DIV_TEAM_BRAND team = null;
		reportRepository.getDeleteDivDataForTeamBrand(bean.getTeamCode());
				for (int i = 0; i < bean.getBrandlist().size(); i++) {
					if(bean.getBrandlist().get(i)!=0) {
						team=new DIV_TEAM_BRAND();
						team.setFin_year_id(bean.getFinYears());
						
						team.setSa_ins_dt(new Date());
						team.setCompany(companyname);
						team.setSa_ins_usr_id(userId);
						team.setSa_ins_ip_add(ip_addr);
						
						team.setSa_prod_group(bean.getBrandlist().get(i).toString());
						team.setSa_status("A");
						team.setTeam_id(Long.valueOf(bean.getTeamCode()));
						this.transactionalRepository.persist(team);
					}
				}
		
		return team;
	}

	@Override
	public List<TeamBrandMappingBean> getTeamBrandReportdata(TeamBrandMappingBean bean) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getTeamBrandReportdata(bean);
	}

	@Override
	public String generate_team_brand_Report(List<TeamBrandMappingBean> lst,String company,String empId) throws Exception {
		// TODO Auto-generated method stub
		String filename ;
		Workbook wwbook =null;
		File ff=null;
		try {
				String comp_name= company;
				long l = new Date().getTime();
				filename = "Team_Brand_Mapping_Report_" + l + ".xlsx";
			
				String filePath = MedicoConstants.REPORT_FILE_PATH ;
				
				StringBuffer path = null;
				 path = new StringBuffer(filePath).append("\\");
					path.append(filename );
				File file = new File(filePath);
				try {
					if (!file.exists()) {
						if (file.mkdirs()) {
						} else {
							throw new RuntimeException("Could not create directory");
						}
					}
					} catch (Exception e) {
						throw new RuntimeException();
					}
				ff = new File(path.toString());
				wwbook = new XSSFWorkbook();
				Sheet wsheet = wwbook.createSheet("Team_brand_mapping");
		
				
				wsheet.createFreezePane(0, 4);
				
				int col = 0;
				short row = 0;
				
				String[] heading = {"COMPANY","DIVISION","TEAM CODE","TEAM NAME","BRAND NAME","STATUS"};
				
				ApachePoiExcelFormat xlsxExcelFormat = new ApachePoiExcelFormat();
				CellStyle sheetHeading = xlsxExcelFormat.SheetHeading(wwbook);
				CellStyle columnHeading = xlsxExcelFormat.columnHeading(wwbook);
				columnHeading.setVerticalAlignment(VerticalAlignment.CENTER);
				CellStyle divisionHeading = xlsxExcelFormat.columnSubHeading(wwbook);
				
				CellStyle columnHeading2 = xlsxExcelFormat.columnHeading(wwbook);
				columnHeading2.setWrapText(true);
				
				CellStyle decimal =   wwbook.createCellStyle();
				decimal.setAlignment(HorizontalAlignment.RIGHT);
				decimal.setDataFormat(wwbook.createDataFormat().getFormat("0.00"));
				
				CellStyle datestyle =   wwbook.createCellStyle();
				datestyle.setAlignment(HorizontalAlignment.RIGHT);
				
				CellStyle lastline =   wwbook.createCellStyle();
				lastline.setBorderTop(BorderStyle.THIN);
				
				XSSFRow hrow = (XSSFRow) wsheet.createRow(row);
			
				XSSFCell cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue(company);
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));

				row++;
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				cell.setCellStyle(sheetHeading);
				cell.setCellValue("Checklist of Team Brand Mapping Report");
				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));
				row++;
				
//
//				hrow = (XSSFRow) wsheet.createRow(row);
//				cell = hrow.createCell(col);
//				cell.setCellStyle(sheetHeading);
//				cell.setCellValue("Report Dated : "+ Utility.convertSmpDatetoString(new Date()));
//				wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));
//				col++;
//				row++;
				col = 0;
				
				hrow = (XSSFRow) wsheet.createRow(row);
				int i = 0;
				for(String a: heading){
					cell = hrow.createCell(col);
					if(i==9 || i==10) {
					cell.setCellStyle(columnHeading2);
					}
					else {
						cell.setCellStyle(columnHeading);
					}
					cell.setCellValue(a);
					col++;
					i++;
				}
				row++;
				col = 0;
				String oldDiv="a";
				String newDiv="";
				for(TeamBrandMappingBean lr : lst) {
					newDiv=lr.getDivision();
					if(!newDiv.equalsIgnoreCase(oldDiv) ) {
					hrow = (XSSFRow) wsheet.createRow(row);
					cell = hrow.createCell(col);
					cell.setCellStyle(divisionHeading);
					cell.setCellValue("DIVISION : "+lr.getDivision());
					wsheet.addMergedRegion(new CellRangeAddress(row, row, col, heading.length-1));
					row ++;
					col=0;
					}
      				hrow = (XSSFRow) wsheet.createRow(row);
//					cell = hrow.createCell(col);
//					//cell.setCellStyle(sheetHeading);
//					cell.setCellValue(lr.getFinYears());
//					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getCompany());
					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getDivision());
					col++;
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getTeam_code());
					col++;
					
					cell = hrow.createCell(col);
				//	cell.setCellStyle(datestyle);
					cell.setCellValue(lr.getTeam_name());
					col++;
					
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(sheetHeading);
					cell.setCellValue(lr.getSa_group_name());
					col++;
					
					
					cell = hrow.createCell(col);
					//cell.setCellStyle(datestyle);
					cell.setCellValue(lr.getStatus());
					col++;
					row ++;
					col=0;
					
					oldDiv = newDiv;
				}
				
				hrow = (XSSFRow) wsheet.createRow(row);
				cell = hrow.createCell(col);
				for(String a: heading){
					cell = hrow.createCell(col);
					cell.setCellStyle(lastline);
					col++;
				}
				
				FileOutputStream fileOut = new FileOutputStream(ff);
				wwbook.write(fileOut);
				
				filename=usermasterservice.generateExcellock(path.toString(), filename,empId,".xlsx");

				fileOut.close();

				System.out.println("Team Brand Mapping Excel Created::::");
				
			}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return filename;
	}

	@Override
	public List<TeamBrandMappingBean> getTeamCodeDataForTeamBrandMapping(String divId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getTeamCodeDataForTeamBrandMapping(divId);
	}
	
	@Override
	public Team getTeamCodeByTeamId(String teamId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getTeamCodeByTeamId(teamId);
	}
	@Override
	public String getTeamCodeDataForTeamMaster(String divId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getTeamCodeDataForTeamMaster(divId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String saveTeamMasterData(TeamBrandMappingBean bean) throws Exception,MedicoException {
		List<Tuple> list = null;
		String msg = null;
		try {
			list=teambrandmappingrepository.getDuplicateTeamCode(bean.getDivId(),bean.getTeam_code());
			System.out.println();
	     if(bean.getEntryModify().equals("Entry")) {
		Team team=new Team();
		if(list.size()==0) {
		team.setTeam_code(bean.getTeam_code());
		}else {
			msg = "Team Code Already Exist";
			throw new MedicoException("Team Code Already Exist");
		}
		team.setTeam_name(bean.getTeam_name());
		team.setTeam_shortname(bean.getShortTeamName());
		System.out.println("bean.getDivId():::"+bean.getDivId());
		team.setDiv_id(Long.valueOf(bean.getDivId()));
		team.setTeam_status(bean.getStatus());
		this.transactionalRepository.persist(team);
		msg = "Successfully Saved";
	  }else {
		  Team team=this.teambrandmappingrepository.getSelectedObjectByTeamId(Long.valueOf(bean.getTeams()));
		  System.out.println("team:::"+team);
		  System.out.println("name::"+bean.getTeam_name());
		  System.out.println("short name:::"+bean.getShortTeamName());
		  System.out.println("team code:::"+bean.getTeam_code());
		  
		  if(!team.getTeam_code().trim().equals(bean.getTeam_code().trim())) {
			  
			  if(teambrandmappingrepository.checkTeamcode(bean.getTeam_code())) {
				
				  msg = "Team Code Already Exist";
				  throw new MedicoException("Team Code Already Exist");
			  }
			  else {
				  team.setTeam_code(bean.getTeam_code());
			  }
		  }

		
		  team.setTeam_name(bean.getTeam_name());
		  team.setTeam_shortname(bean.getShortTeamName());
		  team.setDiv_id(Long.valueOf(bean.getDivId()));
		  team.setTeam_status(bean.getStatus());
		  this.transactionalRepository.update(team);
		  msg = "Modified Successfully ";
	  }
		}catch(Exception e) {
			throw e;
		}
		return msg;
	}

	@Override
	public List<Team> getTeamNamesForTeamMaster(String divId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getTeamNamesForTeamMaster(divId);
	}

	@Override
	public List<Tuple> getDuplicateTeamCode(String divId, String team_code) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getDuplicateTeamCode(divId, team_code);
	}

	@Override
	public List<Tuple> getDuplicateTeamName(String divId, String team_name) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getDuplicateTeamName(divId,team_name);
	}

	@Override
	public List<Tuple> getDuplicateTeamShortName(String divId, String shortTeamName) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getDuplicateTeamShortName(divId,shortTeamName);
	}
	
	@Override
	public List<TeamBrandMappingBean> getBrandsForTeamBrandMapping(String team_code) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getBrandsForTeamBrandMapping(team_code);
	}

	@Override
	public List<ProductBean> getProductListProductMaster(String divId) throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getProductListProductMaster(divId);
	}

	@Override
	public List<DivMaster> getDivIdListproductDivMapping() throws Exception {
		// TODO Auto-generated method stub
		return teambrandmappingrepository.getDivIdListproductDivMapping();
	}
}
