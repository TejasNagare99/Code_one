package com.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excel.model.Csv_File_Consolidated_List;
import com.excel.model.Csv_File_List;
import com.excel.model.Csv_File_List_Without_state_name;
import com.excel.model.Usermaster;
import com.excel.repository.AllocTempArcRepository;
import com.excel.repository.UserMasterRepository;
import com.excel.security.PasswordManager;
import com.excel.utility.CodifyErrors;
import com.excel.utility.MedicoConstants;
import com.opencsv.CSVWriter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class Allocation_Download_csv_ServiceImpl implements Allocation_Download_csv_Service{

	@Autowired UserMasterRepository usermasterrepository;
	@Autowired
	AllocTempArcRepository allocTempArcRepository;
	@Autowired
	private UserMasterService usermasterservice;
	List<String> productHeader = null;
	
	
	
	@Override
	public String Generate_Allocation_Download_csv(List<Csv_File_List> list, String id,
			String divid, String filename,String empId,String issrt)
			throws Exception {

	String fileName;
		CSVWriter writer = null;
		StringBuffer path = null;
		File ff = null;
		try {
		fileName = filename+".csv";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			 path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			 ff=new File(path.toString());
			System.out.println("filename "+fileName);
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
			FileWriter fileWriter = new FileWriter(path.toString());
			writer = new CSVWriter(fileWriter, ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			
			
			Set<String> set = new TreeSet<String>();
			for (Csv_File_List temp : list) {
				// set.add(temp.getFs_code());
				set.add(temp.getFstaff_id());
			}

			List<List<String>> data =new ArrayList<List<String>>();
			if(issrt.trim().equals("N")) {
				data = setCsvFileDataNewWithoutSRT(set, list,divid);
			}
			else {
				data = setCsvFileDataNew(set, list,divid);
			}
			

			List<String> productheader = productHeader;
			String str[] = new String[productheader.size()];
			
			for (int i = 0; i < str.length; i++) {
				//System.out.println(productheader.get(i).trim());
				str[i] = productheader.get(i).trim();
			}
			
			writer.writeNext(str);

			for (List<String> strings : data) {
				str = new String[strings.size()];
				for (int i = 0; i < str.length; i++) {
					try{
					str[i] = strings.get(i).trim();
				}
					catch(Exception e){
						str[i] = "";
					}
				
				}
				writer.writeNext(str);
				
			}
			
			// writer.writeAll(li);
			    
			    File csvfile = new File(path.toString());
			    FileInputStream  fileInputStream = new FileInputStream(csvfile);
			    writer.close();
			    
			    System.out.println("Csv Generated");
			
			    fileName=usermasterservice.generateExcellock(path.toString(), fileName,empId,".csv");

			
		}catch (Exception e) {
			throw e;
		}
		
		
		
		return fileName;
	}
	
	@Override
	public String Generate_Allocation_Consolidated_Download_csv(List<Csv_File_Consolidated_List> list,
			String id, String divid, String filename,String empId)
			throws Exception {

	String fileName;
		CSVWriter writer = null;
		StringBuffer path = null;
		File ff = null;
		try {
		fileName = filename+".csv";
			String filePath = MedicoConstants.REPORT_FILE_PATH;
			 path = new StringBuffer(filePath).append("\\");
			path.append(fileName);
			 ff=new File(path.toString());
			System.out.println("filename "+fileName);
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
			FileWriter fileWriter = new FileWriter(path.toString());
			writer = new CSVWriter(fileWriter, ',',
					CSVWriter.NO_QUOTE_CHARACTER);
			
			
			Set<String> set = new TreeSet<String>();
			for (Csv_File_Consolidated_List temp : list) {
				// set.add(temp.getFs_code());
				set.add(temp.getFstaff_id());
			}

			List<List<String>> data = setCsvFileConsolidatedDataNew(set, list,divid);
			
			
			List<String> productheader = productHeader;
			String str[] = new String[productheader.size()];
			
			for (int i = 0; i < str.length; i++) {
				//System.out.println(productheader.get(i).trim());
				str[i] = productheader.get(i).trim();
			}
			
			writer.writeNext(str);

			for (List<String> strings : data) {
				str = new String[strings.size()];
				for (int i = 0; i < str.length; i++) {
					try{
					str[i] = strings.get(i).trim();
				}
					catch(Exception e){
						str[i] = "0";
					}
				
				}
				writer.writeNext(str);
				
			}
			
			// writer.writeAll(li);
			    
			    File csvfile = new File(path.toString());
			    FileInputStream  fileInputStream = new FileInputStream(csvfile);
			    writer.close();
			    
			    System.out.println("Csv Generated");
			
			    fileName = usermasterservice.generateExcellock(path.toString(), fileName, empId, ".csv");

		}catch (Exception e) {
			throw e;
		}
		
		return fileName;
	}
	
	
	public List<List<String>> setCsvFileDataNew(Set<String> set,
			List<Csv_File_List> list, String div_id) throws Exception{
		List<List<String>> data = new ArrayList<List<String>>();
	
		
		try {
			//List<Div_Field> div_Field = new Div_Field_Dao().getDivFieldData(div_id);
			boolean flag = false;
			//boolean productflag = true;
			boolean pspiflag = false;
			productHeader = new ArrayList<String>();
			productHeader.add("Header");
			productHeader.add("CFA Location");
			if(allocTempArcRepository.checkSGPRMDET_TEXT1())
			{
				productHeader.add("State");
			}
			productHeader.add("Territory");
			productHeader.add("Sub Company");
			productHeader.add("Employee No.");
			productHeader.add("Year");
			productHeader.add("Month");
			productHeader.add("Division Name");
			productHeader.add("Designation");
			productHeader.add("Fs_Code");
			productHeader.add("Staff Id");
			productHeader.add("Fs_Name");
			productHeader.add("Alloc_Type");
			productHeader.add("SRT_No");
			productHeader.add("SRT_DATE");
			productHeader.add("SRT_REMARK");
			Set<String> productset = new LinkedHashSet<String>();
			//Map<String, Integer> productindex = new TreeMap<String, Integer>();
			//int n = productHeader.indexOf(new String("Alloc_Type"));
			for (Csv_File_List file_List : list) {
				productset.add(file_List.getSmp_prod_name());
			}
			String[] productAry=new String[productset.size()];
			int z=0;
			for (String product : productset) {
				productHeader.add(product);
				productAry[z++]=product;
			}
			List<String> row = null;
			List<String> pspi = null;
			List<String> fnl_stk = null;
			for (String fs_code : set) {
				row = new ArrayList<String>();
				pspi = new ArrayList<String>();
				fnl_stk = new ArrayList<String>();
				String[] record=new String[productset.size()+17];
				String[] recordpspi=new String[productset.size()+17];
				String[] recordfinal=new String[productset.size()+17];
				flag = true;
				pspiflag = false;
				for (Csv_File_List file_List : list) {
					int i=0; 
					// if (file_List.getFs_code().equals(fs_code)) {
					if (file_List.getFstaff_id().equals(fs_code)) {
						if (flag) {
							record[i++]=file_List.getAlloc_gen_id();
							record[i++]=file_List.getDptloc_name();
							if(allocTempArcRepository.checkSGPRMDET_TEXT1())
							{
							record[i++]=file_List.getFstaff_statename();
							}
							record[i++]=file_List.getFstaff_terrname();
							record[i++]=file_List.getSubcomp_company();
							record[i++]=file_List.getFstaff_employee_no();
							record[i++]=file_List.getYear();
							record[i++]=file_List.getPer_code();
							record[i++]=file_List.getDivision();
							record[i++]=file_List.getLevel_brief_name();
							record[i++]=file_List.getFs_code();
							record[i++]=file_List.getFstaff_id();
							record[i++]=file_List.getFs_name();
							record[i++]=file_List.getAlloc_type();
							record[i++]=file_List.getSrt_number();
							record[i++]=file_List.getSrt_date();
							record[i++]=file_List.getSrt_remark();
							
							if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
								System.arraycopy(record, 0, recordpspi, 0, record.length);
								recordpspi[i-1]="PS STOCK";
								pspiflag = true;
							}
							System.arraycopy(record, 0, recordfinal, 0, record.length);
							recordfinal[i-4]="FINAL";
							flag = false;
						}
						BigDecimal gen_qty = new BigDecimal(0);
						BigDecimal pspi_qty = new BigDecimal(0);
						BigDecimal final_qty = new BigDecimal(0);
						for(int k=0;k<productAry.length;k++)
						{
							if(file_List.getSmp_prod_name().equals(productAry[k]))
							{
								//record[k+12]=file_List.getQty().toString();
								if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
									recordpspi[k+13]=file_List.getQty().toString();
									pspi_qty = file_List.getQty();
									gen_qty = file_List.getFstaffd_subcomp_id().subtract(
											file_List.getQty());
								} else {
									gen_qty = file_List.getQty();
									pspi_qty = new BigDecimal(0);
									
								}
								
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								if (file_List.getSmp_prod_type().equals("Sample")) {
									final_qty = (gen_qty.subtract(pspi_qty).divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue(); // change
									recordfinal[k+17]= finalqty.toString();
								} else {
									
									final_qty = (gen_qty.divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue();// change
									recordfinal[k+17]= finalqty.toString();
									
								}
								}
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								Long  genqty = final_qty.longValue();// change
								record[k+17]=genqty.toString();
								}
							}
						}
					}
					
				}
				//productflag = false;
				row=Arrays.asList(record);
				data.add(row);
				fnl_stk=Arrays.asList(recordfinal);
				if (pspiflag){
				pspi=Arrays.asList(recordpspi);
				data.add(pspi);
				}
				data.add(fnl_stk);
				
				
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return data;
	}
	
	
	public List<List<String>> setCsvFileDataNewWithoutSRT(Set<String> set,
			List<Csv_File_List> list, String div_id) throws Exception{
		List<List<String>> data = new ArrayList<List<String>>();
	
		
		try {
			//List<Div_Field> div_Field = new Div_Field_Dao().getDivFieldData(div_id);
			boolean flag = false;
			//boolean productflag = true;
			boolean pspiflag = false;
			productHeader = new ArrayList<String>();
			productHeader.add("Header");
			productHeader.add("CFA Location");
			
			if(allocTempArcRepository.checkSGPRMDET_TEXT1())
			{
				productHeader.add("State");
			}
			productHeader.add("Territory");
			productHeader.add("Sub Company");
			productHeader.add("Employee No.");
			productHeader.add("Year");
			productHeader.add("Month");
			productHeader.add("Division Name");
			productHeader.add("Designation");
			productHeader.add("Fs_Code");
			productHeader.add("Staff Id");
			productHeader.add("Fs_Name");
			productHeader.add("Alloc_Type");
		
			Set<String> productset = new LinkedHashSet<String>();
			//Map<String, Integer> productindex = new TreeMap<String, Integer>();
			//int n = productHeader.indexOf(new String("Alloc_Type"));
			for (Csv_File_List file_List : list) {
				productset.add(file_List.getSmp_prod_name());
			}
			String[] productAry=new String[productset.size()];
			int z=0;
			for (String product : productset) {
				productHeader.add(product);
				productAry[z++]=product;
			}
			List<String> row = null;
			List<String> pspi = null;
			List<String> fnl_stk = null;
			for (String fs_code : set) {
				row = new ArrayList<String>();
				pspi = new ArrayList<String>();
				fnl_stk = new ArrayList<String>();
				String[] record=new String[productset.size()+14];
				String[] recordpspi=new String[productset.size()+14];
				String[] recordfinal=new String[productset.size()+14];
				flag = true;
				pspiflag = false;
				for (Csv_File_List file_List : list) {
					int i=0; 
					// if (file_List.getFs_code().equals(fs_code)) {
					if (file_List.getFstaff_id().equals(fs_code)) {
						if (flag) {
							record[i++]=file_List.getAlloc_gen_id();
							record[i++]=file_List.getDptloc_name();
							if(allocTempArcRepository.checkSGPRMDET_TEXT1())
							{
							record[i++]=file_List.getFstaff_statename();
							}
							record[i++]=file_List.getFstaff_terrname();
							record[i++]=file_List.getSubcomp_company();
							record[i++]=file_List.getFstaff_employee_no();
							record[i++]=file_List.getYear();
							record[i++]=file_List.getPer_code();
							record[i++]=file_List.getDivision();
							record[i++]=file_List.getLevel_brief_name();
							record[i++]=file_List.getFs_code();
							record[i++]=file_List.getFstaff_id();
							record[i++]=file_List.getFs_name();
							record[i++]=file_List.getAlloc_type();
						
							
							if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
								System.arraycopy(record, 0, recordpspi, 0, record.length);
								recordpspi[i-1]="PS STOCK";
								pspiflag = true;
							}
							System.arraycopy(record, 0, recordfinal, 0, record.length);
							recordfinal[i-1]="FINAL";
							flag = false;
						}
						BigDecimal gen_qty = new BigDecimal(0);
						BigDecimal pspi_qty = new BigDecimal(0);
						BigDecimal final_qty = new BigDecimal(0);
						for(int k=0;k<productAry.length;k++)
						{
							if(file_List.getSmp_prod_name().equals(productAry[k]))
							{
								//record[k+12]=file_List.getQty().toString();
								if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
									recordpspi[k+12]=file_List.getQty().toString();
									pspi_qty = file_List.getQty();
									gen_qty = file_List.getFstaffd_subcomp_id().subtract(
											file_List.getQty());
								} else {
									gen_qty = file_List.getQty();
									pspi_qty = new BigDecimal(0);
									
								}
								
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								if (file_List.getSmp_prod_type().equals("Sample")) {
									final_qty = (gen_qty.subtract(pspi_qty).divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue(); // change
									recordfinal[k+14]= finalqty.toString();
								} else {
									
									final_qty = (gen_qty.divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue();// change
									recordfinal[k+14]= finalqty.toString();
									
								}
								}
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								Long  genqty = final_qty.longValue();// change
								record[k+14]=genqty.toString();
								}
							}
						}
					}
					
				}
				//productflag = false;
				row=Arrays.asList(record);
				data.add(row);
				fnl_stk=Arrays.asList(recordfinal);
				if (pspiflag){
				pspi=Arrays.asList(recordpspi);
				data.add(pspi);
				}
				data.add(fnl_stk);
				
				
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return data;
	}
	
	
	
	public List<List<String>> setCsvFileConsolidatedDataNew(Set<String> set,
			List<Csv_File_Consolidated_List> list, String div_id) throws Exception{
		List<List<String>> data = new ArrayList<List<String>>();
	
		
		try {
			//List<Div_Field> div_Field = new Div_Field_Dao().getDivFieldData(div_id);
			boolean flag = false;
			//boolean productflag = true;
			boolean pspiflag = false;
			productHeader = new ArrayList<String>();
			productHeader.add("Header");
			productHeader.add("CFA Location");
			productHeader.add("Territory");
			productHeader.add("Sub Company");
			productHeader.add("Employee No.");
			productHeader.add("Year");
			productHeader.add("Month");
			productHeader.add("Division Name");
			productHeader.add("Designation");
			productHeader.add("Fs_Code");
			productHeader.add("Staff Id");
			productHeader.add("Fs_Name");
			productHeader.add("Alloc_Type");
			Set<String> productset = new LinkedHashSet<String>();
			//Map<String, Integer> productindex = new TreeMap<String, Integer>();
			//int n = productHeader.indexOf(new String("Alloc_Type"));
			for (Csv_File_Consolidated_List file_List : list) {
				productset.add(file_List.getSmp_prod_name());
			}
			String[] productAry=new String[productset.size()];
			int z=0;
			for (String product : productset) {
				productHeader.add(product);
				productAry[z++]=product;
			}
			List<String> row = null;
			List<String> pspi = null;
			List<String> fnl_stk = null;
			for (String fs_code : set) {
				row = new ArrayList<String>();
				pspi = new ArrayList<String>();
				fnl_stk = new ArrayList<String>();
				String[] record=new String[productset.size()+13];
				String[] recordpspi=new String[productset.size()+13];
				String[] recordfinal=new String[productset.size()+13];
				flag = true;
				pspiflag = false;
				for (Csv_File_Consolidated_List file_List : list) {
					int i=0; 
					// if (file_List.getFs_code().equals(fs_code)) {
					if (file_List.getFstaff_id().equals(fs_code)) {
						if (flag) {
							record[i++]=file_List.getAlloc_con_id();
							record[i++]=file_List.getDptloc_name();
							record[i++]=file_List.getFstaff_terrname();
							record[i++]=file_List.getSubcomp_company();
							record[i++]=file_List.getFstaff_employee_no();
							record[i++]=file_List.getYear();
							record[i++]=file_List.getPer_code();
							record[i++]=file_List.getDivision();
							record[i++]=file_List.getLevel_brief_name();
							record[i++]=file_List.getFs_code();
							record[i++]=file_List.getFstaff_id();
							record[i++]=file_List.getFs_name();
							record[i++]=file_List.getAlloc_type();
							
							if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
								System.arraycopy(record, 0, recordpspi, 0, record.length);
								recordpspi[i-1]="PS STOCK";
								pspiflag = true;
							}
							System.arraycopy(record, 0, recordfinal, 0, record.length);
							recordfinal[i-1]="FINAL";
							flag = false;
						}
						BigDecimal gen_qty = new BigDecimal(0);
						BigDecimal pspi_qty = new BigDecimal(0);
						BigDecimal final_qty = new BigDecimal(0);
						for(int k=0;k<productAry.length;k++)
						{
							if(file_List.getSmp_prod_name().equals(productAry[k]))
							{
								//record[k+12]=file_List.getQty().toString();
								if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
									recordpspi[k+12]=file_List.getQty().toString();
									pspi_qty = file_List.getQty();
									gen_qty = file_List.getFstaffd_subcomp_id().subtract(
											file_List.getQty());
								} else {
									gen_qty = file_List.getQty();
									pspi_qty = new BigDecimal(0);
									
								}
								
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								if (file_List.getSmp_prod_type().equals("Sample")) {
									final_qty = (gen_qty.subtract(pspi_qty).divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue(); // change
									recordfinal[k+13]= finalqty.toString();
								} else {
									
									final_qty = (gen_qty.divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue();// change
									recordfinal[k+13]= finalqty.toString();
									
								}
								}
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								Long  genqty = final_qty.longValue();// change
								record[k+13]=genqty.toString();
								}
							}
						}
					}
					
				}
				//productflag = false;
				row=Arrays.asList(record);
				data.add(row);
				fnl_stk=Arrays.asList(recordfinal);
				if (pspiflag){
				pspi=Arrays.asList(recordpspi);
				data.add(pspi);
				}
				data.add(fnl_stk);
				
				
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return data;
	}




	@Override
	public String Generate_Allocation_Download_csv_Without_State_Name(
			List<Csv_File_List_Without_state_name> list, String id, String divid, String filename,
			String empId, String issrt) throws Exception {

		String fileName;
			CSVWriter writer = null;
			StringBuffer path = null;
			File ff = null;
			try {
			fileName = filename+".csv";
				String filePath = MedicoConstants.REPORT_FILE_PATH;
				 path = new StringBuffer(filePath).append("\\");
				path.append(fileName);
				 ff=new File(path.toString());
				System.out.println("filename "+fileName);
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
				FileWriter fileWriter = new FileWriter(path.toString());
				writer = new CSVWriter(fileWriter, ',',
						CSVWriter.NO_QUOTE_CHARACTER);
				
				
				Set<String> set = new TreeSet<String>();
				for (Csv_File_List_Without_state_name temp : list) {
					// set.add(temp.getFs_code());
					set.add(temp.getFstaff_id());
				}

				List<List<String>> data =new ArrayList<List<String>>();
				if(issrt.trim().equals("N")) {
					data = setCsvFileDataNewWithoutSRT_without_state_name(set, list,divid);
				}
				else {
					data = setCsvFileDataNew_without_state_name(set, list,divid);
				}
				

				List<String> productheader = productHeader;
				String str[] = new String[productheader.size()];
				
				for (int i = 0; i < str.length; i++) {
					//System.out.println(productheader.get(i).trim());
					str[i] = productheader.get(i).trim();
				}
				
				writer.writeNext(str);

				for (List<String> strings : data) {
					str = new String[strings.size()];
					for (int i = 0; i < str.length; i++) {
						try{
						str[i] = strings.get(i).trim();
					}
						catch(Exception e){
							str[i] = "";
						}
					
					}
					writer.writeNext(str);
					
				}
				
				// writer.writeAll(li);
				    
				    File csvfile = new File(path.toString());
				    FileInputStream  fileInputStream = new FileInputStream(csvfile);
				    writer.close();
				    
				    System.out.println("Csv Generated");
				
				    fileName=usermasterservice.generateExcellock(path.toString(), fileName,empId,".csv");

				
			}catch (Exception e) {
				throw e;
			}
			
			
			
			return fileName;
		}

	private List<List<String>> setCsvFileDataNew_without_state_name(Set<String> set,
			List<Csv_File_List_Without_state_name> list, String divid) {
		List<List<String>> data = new ArrayList<List<String>>();
	
		
		try {
			//List<Div_Field> div_Field = new Div_Field_Dao().getDivFieldData(div_id);
			boolean flag = false;
			//boolean productflag = true;
			boolean pspiflag = false;
			productHeader = new ArrayList<String>();
			productHeader.add("Header");
			productHeader.add("CFA Location");

			productHeader.add("Territory");
			productHeader.add("Sub Company");
			productHeader.add("Employee No.");
			productHeader.add("Year");
			productHeader.add("Month");
			productHeader.add("Division Name");
			productHeader.add("Designation");
			productHeader.add("Fs_Code");
			productHeader.add("Staff Id");
			productHeader.add("Fs_Name");
			productHeader.add("Alloc_Type");
			productHeader.add("SRT_No");
			productHeader.add("SRT_DATE");
			productHeader.add("SRT_REMARK");
			Set<String> productset = new LinkedHashSet<String>();
			//Map<String, Integer> productindex = new TreeMap<String, Integer>();
			//int n = productHeader.indexOf(new String("Alloc_Type"));
			for (Csv_File_List_Without_state_name file_List : list) {
				productset.add(file_List.getSmp_prod_name());
			}
			String[] productAry=new String[productset.size()];
			int z=0;
			for (String product : productset) {
				productHeader.add(product);
				productAry[z++]=product;
			}
			List<String> row = null;
			List<String> pspi = null;
			List<String> fnl_stk = null;
			for (String fs_code : set) {
				row = new ArrayList<String>();
				pspi = new ArrayList<String>();
				fnl_stk = new ArrayList<String>();
				String[] record=new String[productset.size()+17];
				String[] recordpspi=new String[productset.size()+17];
				String[] recordfinal=new String[productset.size()+17];
				flag = true;
				pspiflag = false;
				for (Csv_File_List_Without_state_name file_List : list) {
					int i=0; 
					// if (file_List.getFs_code().equals(fs_code)) {
					if (file_List.getFstaff_id().equals(fs_code)) {
						if (flag) {
							record[i++]=file_List.getAlloc_gen_id();
							record[i++]=file_List.getDptloc_name();
							record[i++]=file_List.getFstaff_terrname();
							record[i++]=file_List.getSubcomp_company();
							record[i++]=file_List.getFstaff_employee_no();
							record[i++]=file_List.getYear();
							record[i++]=file_List.getPer_code();
							record[i++]=file_List.getDivision();
							record[i++]=file_List.getLevel_brief_name();
							record[i++]=file_List.getFs_code();
							record[i++]=file_List.getFstaff_id();
							record[i++]=file_List.getFs_name();
							record[i++]=file_List.getAlloc_type();
							record[i++]=file_List.getSrt_number();
							record[i++]=file_List.getSrt_date();
							record[i++]=file_List.getSrt_remark();
							
							if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
								System.arraycopy(record, 0, recordpspi, 0, record.length);
								recordpspi[i-1]="PS STOCK";
								pspiflag = true;
							}
							System.arraycopy(record, 0, recordfinal, 0, record.length);
							recordfinal[i-4]="FINAL";
							flag = false;
						}
						BigDecimal gen_qty = new BigDecimal(0);
						BigDecimal pspi_qty = new BigDecimal(0);
						BigDecimal final_qty = new BigDecimal(0);
						for(int k=0;k<productAry.length;k++)
						{
							if(file_List.getSmp_prod_name().equals(productAry[k]))
							{
								//record[k+12]=file_List.getQty().toString();
								if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
									recordpspi[k+13]=file_List.getQty().toString();
									pspi_qty = file_List.getQty();
									gen_qty = file_List.getFstaffd_subcomp_id().subtract(
											file_List.getQty());
								} else {
									gen_qty = file_List.getQty();
									pspi_qty = new BigDecimal(0);
									
								}
								
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								if (file_List.getSmp_prod_type().equals("Sample")) {
									final_qty = (gen_qty.subtract(pspi_qty).divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue(); // change
									recordfinal[k+16]= finalqty.toString();
								} else {
									
									final_qty = (gen_qty.divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue();// change
									recordfinal[k+16]= finalqty.toString();
									
								}
								}
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								Long  genqty = final_qty.longValue();// change
								record[k+16]=genqty.toString();
								}
							}
						}
					}
					
				}
				//productflag = false;
				row=Arrays.asList(record);
				data.add(row);
				fnl_stk=Arrays.asList(recordfinal);
				if (pspiflag){
				pspi=Arrays.asList(recordpspi);
				data.add(pspi);
				}
				data.add(fnl_stk);
				
				
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return data;
	}

	private List<List<String>> setCsvFileDataNewWithoutSRT_without_state_name(Set<String> set,
			List<Csv_File_List_Without_state_name> list, String divid) {
		List<List<String>> data = new ArrayList<List<String>>();
	
		
		try {
			//List<Div_Field> div_Field = new Div_Field_Dao().getDivFieldData(div_id);
			boolean flag = false;
			//boolean productflag = true;
			boolean pspiflag = false;
			productHeader = new ArrayList<String>();
			productHeader.add("Header");
			productHeader.add("CFA Location");
			
//			if(allocTempArcRepository.checkSGPRMDET_TEXT1())
//			{
//				productHeader.add("State");
//			}
//			
			productHeader.add("Territory");
			productHeader.add("Sub Company");
			productHeader.add("Employee No.");
			productHeader.add("Year");
			productHeader.add("Month");
			productHeader.add("Division Name");
			productHeader.add("Designation");
			productHeader.add("Fs_Code");
			productHeader.add("Staff Id");
			productHeader.add("Fs_Name");
			productHeader.add("Alloc_Type");
		
			Set<String> productset = new LinkedHashSet<String>();
			//Map<String, Integer> productindex = new TreeMap<String, Integer>();
			//int n = productHeader.indexOf(new String("Alloc_Type"));
			for (Csv_File_List_Without_state_name file_List : list) {
				productset.add(file_List.getSmp_prod_name());
			}
			String[] productAry=new String[productset.size()];
			int z=0;
			for (String product : productset) {
				productHeader.add(product);
				productAry[z++]=product;
			}
			List<String> row = null;
			List<String> pspi = null;
			List<String> fnl_stk = null;
			for (String fs_code : set) {
				row = new ArrayList<String>();
				pspi = new ArrayList<String>();
				fnl_stk = new ArrayList<String>();
				String[] record=new String[productset.size()+14];
				String[] recordpspi=new String[productset.size()+14];
				String[] recordfinal=new String[productset.size()+14];
				flag = true;
				pspiflag = false;
				for (Csv_File_List_Without_state_name file_List : list) {
					int i=0; 
					// if (file_List.getFs_code().equals(fs_code)) {
					if (file_List.getFstaff_id().equals(fs_code)) {
						if (flag) {
							record[i++]=file_List.getAlloc_gen_id();
							record[i++]=file_List.getDptloc_name();
//							if(allocTempArcRepository.checkSGPRMDET_TEXT1())
//							{
//							record[i++]=file_List.getFstaff_statename();
//							}
							record[i++]=file_List.getFstaff_terrname();
							record[i++]=file_List.getSubcomp_company();
							record[i++]=file_List.getFstaff_employee_no();
							record[i++]=file_List.getYear();
							record[i++]=file_List.getPer_code();
							record[i++]=file_List.getDivision();
							record[i++]=file_List.getLevel_brief_name();
							record[i++]=file_List.getFs_code();
							record[i++]=file_List.getFstaff_id();
							record[i++]=file_List.getFs_name();
							record[i++]=file_List.getAlloc_type();
						
							
							if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
								System.arraycopy(record, 0, recordpspi, 0, record.length);
								recordpspi[i-1]="PS STOCK";
								pspiflag = true;
							}
							System.arraycopy(record, 0, recordfinal, 0, record.length);
							recordfinal[i-1]="FINAL";
							flag = false;
						}
						BigDecimal gen_qty = new BigDecimal(0);
						BigDecimal pspi_qty = new BigDecimal(0);
						BigDecimal final_qty = new BigDecimal(0);
						for(int k=0;k<productAry.length;k++)
						{
							if(file_List.getSmp_prod_name().equals(productAry[k]))
							{
								//record[k+12]=file_List.getQty().toString();
								if (file_List.getAlloc_type().equals("PS/PI STOCK")) {
									recordpspi[k+12]=file_List.getQty().toString();
									pspi_qty = file_List.getQty();
									gen_qty = file_List.getFstaffd_subcomp_id().subtract(
											file_List.getQty());
								} else {
									gen_qty = file_List.getQty();
									pspi_qty = new BigDecimal(0);
									
								}
								
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								if (file_List.getSmp_prod_type().equals("Sample")) {
									final_qty = (gen_qty.subtract(pspi_qty).divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue(); // change
									recordfinal[k+13]= finalqty.toString();
								} else {
									
									final_qty = (gen_qty.divide(
											file_List.getSmp_min_alloc_qty(), 0,
											RoundingMode.CEILING)).multiply(file_List
											.getSmp_min_alloc_qty());
									
									Long finalqty = final_qty.longValue();// change
									recordfinal[k+13]= finalqty.toString();
									
								}
								}
								if (!file_List.getAlloc_type().equals("PS/PI STOCK")) {
								Long  genqty = final_qty.longValue();// change
								record[k+13]=genqty.toString();
								}
							}
						}
					}
					
				}
				//productflag = false;
				row=Arrays.asList(record);
				data.add(row);
				fnl_stk=Arrays.asList(recordfinal);
				if (pspiflag){
				pspi=Arrays.asList(recordpspi);
				data.add(pspi);
				}
				data.add(fnl_stk);
				
				
			}

		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		}
		return data;
	}

}
