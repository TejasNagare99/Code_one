package com.excel.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
//import dao.PrePrintedChallanDao;
//import hibernate_model.ViewPrePrintedDetailChallan;

import org.springframework.beans.factory.annotation.Autowired;

import com.excel.model.ViewPrePrintedDetailChallan;
import com.excel.service.PreprintedDetailedChallanService;

public class Challan_Summary_Print {
	int add=0;
	public String callMain(int division,int loc,String frm_challan,String to_challan,String prodtype,String rep_type,List<ViewPrePrintedDetailChallan> challans){
		FileOutputStream os=null;
		PrintStream ps=null;
		StringBuffer output = new StringBuffer();
		//String pathName = "D:\\Challan\\summary.txt";
		//File file = new	File(pathName);
		String path= MedicoConstants.REPORT_FILE_PATH;
		
		long timevar=new Date().getTime();
		String filename="summary"+timevar+".txt";
		File fileas = new File(path+"\\files\\");
		fileas.mkdirs();
		File file = new File(path+"\\files\\"+filename);
		
		try {
			Challan_Summary_Print print = new Challan_Summary_Print();
			/*List<ViewPrePrintedDetailChallan> challans = new PrePrintedChallanDao()
					.getViewPrePrintedSummaryChallan(1, 9, "PIP  1400001",
							"PIP  1400010");*/
			/*
			 * List<ViewPrePrintedDetailChallan> challans = new PrePrintedChallanDao().
			 * .getViewPrePrintedSummaryChallan(division, loc, frm_challan,
			 * to_challan,prodtype,rep_type);
			 */
			Set<String> set = new LinkedHashSet<String>();
			for (ViewPrePrintedDetailChallan challan : challans) {
				set.add(challan.getDoc_no()+challan.getProduct_type());
			}
			boolean isnew = false;
			int capacity = 36;
			int page=0;
			int countpage=0;
			for (String num : set) {
				/* header */
				isnew = true;
				page++;
				ViewPrePrintedDetailChallan footer = null;
				int count = 0;
				BigDecimal total_amt=new BigDecimal(0);
				System.out.println();
				if(countpage!=0){
					output.append(addLine(2));
				}
				countpage++;
				for (ViewPrePrintedDetailChallan challan : challans) {
					if (num.equals(challan.getDoc_no()+challan.getProduct_type())) {
						//count++;
						if (isnew) {
							footer = challan;
							print.createHeader(challan, output,page);
							isnew = false;

						}
						BigDecimal length=new BigDecimal(challan.getProduct_desc().length());
						BigDecimal j= length.divide(new BigDecimal(35+add),RoundingMode.CEILING).setScale(0,RoundingMode.CEILING);
						
						for(int i=0;i<j.intValue();i++){
							count++;
							//if(i==0){
								if(challan.getCode()!=null){
									if(challan.getCode().length()>6*i){
										output.append(checkNullLength(challan.getCode().substring(6*i),
											6, 'L'));//58
									}else{
										output.append(addspace(6));//7
									}
									output.append(addspace(1));//7
									//output.append(checkNullLength(challan.getCode(), 6, 'L'));//6
								}else{
									output.append(addspace(7));//7
								}
								
							/*}else{
								
							}*/
							
							if(challan.getProduct_desc()!=null){
								if(challan.getProduct_desc().length()>(35+add)*i){
									output.append(checkNullLength(challan.getProduct_desc().substring((35+add)*i),
											 35+add, 'L'));//58
								}else{
									output.append(addspace(35+add));
								}
							}else{
								output.append(addspace(35+add));
							}
							/*output.append(checkNullLength(
									challan.getProduct_desc().substring(35+add*i) , 35+add, 'L'));//42*/
							output.append(addspace(1));//43
							
							//System.out.println(challan.getBatch_no());
							if(challan.getBatch_no()!=null){
								if(challan.getBatch_no().length()>15*i){
									output.append(checkNullLength(challan.getBatch_no().substring(15*i),
										15, 'L'));//58
								}else{
									output.append(addspace(15));
								}
							}else{
								output.append(addspace(15));
							}
							
							if(i==0){
								
								output.append(addspace(1));//59
								output.append(challan.getMfg_yr() != null ? checkNullLength(
										challan.getMfg_yr().toString(), 5, 'L')
										: addspace(5));//64
								output.append(addspace(1));//65
								output.append(checkNullLength(challan.getExpiry_date(),
										8, 'L'));//73
								//output.append(addspace(1));
								/*output.append(challan.getCases() != null ? checkNullLength(
										challan.getCases()
												.setScale(0, RoundingMode.HALF_UP)
												.toString(), 7, 'L') : addspace(7));//80*/
								output.append(checkNullLength("",7,'L'));
								//output.append(" ");
								output.append(challan.getTotal_qty() != null ? checkNullLength(
										challan.getTotal_qty()
												.setScale(0, RoundingMode.HALF_UP)
												.toString(), 8, 'R') : addspace(8));//88
								output.append(addspace(1));//89
								output.append(challan.getRate() != null ? checkNullLength(
										challan.getRate()
												.setScale(2, RoundingMode.HALF_UP)
												.toString(), 8, 'R') : addspace(8));//97
								output.append(addspace(1));//98
								output.append(challan.getValue() != null ? checkNullLength(
										challan.getValue()
												.setScale(2, RoundingMode.HALF_UP)
												.toString(), 9, 'R') : addspace(9));
								total_amt=total_amt.add(challan.getValue()
										.setScale(2, RoundingMode.HALF_UP));
							}
	
							output.append("\n");
							//System.out.println(count - capacity);
							if (count - capacity > 0) {
								page++;
								output.append("\f");
								print.createHeader(challan, output,page);
								count = 0;
							}
						}
					}
					
				}
				// if (num.equals(footer.getDoc_no())) {
				try {
					for(int i=0;i<64-(19+count);i++){
						output.append(addLine(1));
					}
					//output.append(addLine(1));
					System.out.println(capacity);
					System.out.println(count);
					System.out.println(capacity-count);
					output.append(addspace(21));
					/*output.append(footer.getTot_dsp_cases() != null ? checkNullLength(
							footer.getTot_dsp_cases()
									.setScale(0, RoundingMode.HALF_UP)
									.toString(), 9, 'L') : addspace(9));//30*/
					output.append(checkNullLength("", 9, 'L'));//30
					output.append(addspace(1));//31
					/*output.append(footer.getWeigh() != null ? checkNullLength(
							new BigDecimal(footer.getWeigh())
							.setScale(2, RoundingMode.HALF_UP)
							.toString(), 7, 'L') : addspace(7));//38*/
					output.append(addspace(7));//38*/
					output.append(addspace(1));//39
					output.append(addspace(8));//freight 47
					output.append(addspace(1));//48
					output.append(checkNullLength(footer.getDsp_lorry_no(), 14,
							'L'));//62
					output.append(addspace(1));//63
					output.append(checkNullLength(footer.getDsp_transporter(),
							25, 'L'));//88
					output.append(addspace(10));//98
					output.append(checkNullLength(total_amt.setScale(2, RoundingMode.HALF_UP).toString(), 9, 'R'));//112
					
					output.append(addLine(3));

					output.append(addspace(21));//35
					output.append(checkNullLength(footer.getPermit_no(),
							8, 'L'));//88
					output.append(addspace(1));
					
					String doc_no=footer.getShip_doc_no()!=null?footer.getShip_doc_no():"";
					String doc_date=footer.getShip_doc_date()!=null?footer.getShip_doc_date():"";
					output.append(checkNullLength(doc_no+" "+doc_date, 15,
							'L'));//50
					total_amt=BigDecimal.ZERO;
					//output.append("\n");
					
					//output.append("\f");
					output.append(addLine(4));
					page=0;
				} catch (Exception e) {
					System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
				}
				// }

			}
			
			//String pathName = "D:\\Challan\\detail.txt";
			/*File file = new	File(pathName);
			file.mkdirs();
			file.createNewFile();*/
			 
			
			file.getParentFile().mkdir();
			file.createNewFile();
			os = new FileOutputStream(file);
			ps = new PrintStream(os);
			
			ps.println(output);
			/*FileWriter out = new FileWriter(file);
			out.write(output.toString());
			out.write(0x0D); // CR
			out.close(); */
			
			
		} catch (Exception e) {
			System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
		} finally {
			try {
				if(os!=null){
					os.close();
				}
				if(ps!=null){
					ps.close();
				}
			} catch (IOException e) {
				System.out.println("Error Occurred :" +e.getMessage());// uncomment asneeded --;
			}
		}System.out.println("dot matrix file created "+filename);
		return filename;
	}

	public void createHeader(ViewPrePrintedDetailChallan challan,
			StringBuffer output,int page) {
		output.append(addLine(3));
		
		/*4 line*/
		
		output.append(addspace(54+add));
		output.append(checkNullLength("", 3, 'L'));//57
		output.append(addspace(1));//58
		output.append(checkNullLength(challan.getDoc_no(), 10, 'L'));//66
		output.append(addspace(1));//67
		output.append(checkNullLength(challan.getDate(), 10, 'L'));//76
		/*output.append(addspace(10));//76
*/		output.append(addspace(1));//77
		output.append(page);//78

		/*6 line*/
		output.append(addLine(2));
		
		/*7 line*/
		output.append(addspace(2)); //2
		output.append(checkNullLength(challan.getStaff_name(), 50+add, 'L'));//52
		output.append(addspace(4));//56
		output.append(checkNullLength(challan.getCfa_name(), 50+add, 'L'));
		
		
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append(checkNullLength(challan.getStaff_addr1(), 50+add, 'L'));
		output.append(addspace(4));//56
		output.append(checkNullLength(challan.getLoc_add1(), 50+add, 'L'));
		
		/*8 line*/
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append(checkNullLength(challan.getStaff_addr2(), 50+add, 'L'));
		output.append(addspace(4));//56
		output.append(checkNullLength(challan.getLoc_add2(), 50+add, 'L'));
		
		/*9 line*/
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append(checkNullLength(challan.getStaff_addr3(), 50+add, 'L'));
		output.append(addspace(4));//56
		output.append(checkNullLength(challan.getLoc_add3(), 50+add, 'L'));
		
		/*10 line*/
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append(checkNullLength(challan.getStaff_addr4(), 50+add, 'L'));
		output.append(addspace(4));//56
		output.append(checkNullLength(challan.getLoc_add4(), 50+add, 'L'));
		
		output.append(addLine(1));
		output.append(addspace(2)); //2
		//output.append(addspace(50)); //2
		output.append("Drug License 1 : ");
		output.append(checkNullLength(challan.getDptdrug_lic1(), 33+add, 'L'));
		output.append(addspace(4));//56
		output.append("Drug License 1 : ");
		output.append(checkNullLength(challan.getLic1(), 33+add, 'L'));
		
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append("Drug License 2 : ");
		output.append(checkNullLength(challan.getDptdrug_lic2(), 33+add, 'L'));
		output.append(addspace(4));//56
		output.append("Drug License 2 : ");
		output.append(checkNullLength(challan.getLic2(), 33+add, 'L'));
		
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append("CST No.: ");
		output.append(checkNullLength(challan.getDptcst_no(), 41+add, 'L'));
		output.append(addspace(4));//56
		output.append("CST No.: ");
		output.append(checkNullLength(challan.getLoc_cst_no(), 41+add, 'L'));
		
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append("Tin No.: ");
		output.append(checkNullLength(challan.getDptlst_no(), 41+add, 'L'));
		output.append(addspace(4));//56
		output.append("Tin No.: ");
		output.append(checkNullLength(challan.getLoc_tin_no(), 41+add, 'L'));
		/*19 line*/
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append("PAN/CIN No.: ");
		output.append(checkNullLength(challan.getDptpan_no(), 37+add, 'L'));
		
		/*20 line*/
		output.append(addLine(1));
		output.append(addspace(2)); //2
		output.append("Invoice From: ");//14
		output.append(checkNullLength(challan.getFr_num(), 15+add, 'L'));
		output.append(" To: ");//5
		output.append(checkNullLength(challan.getTo_num(), 15+add, 'L'));
		
		output.append(addLine(3));
		page=0;
	}

	public static String checkNullLength(String inputStr, int setLen,
			char LRalign) {
		String addempty = "";
		String finalStr = "";
		if (inputStr != null) {
			int strLen = inputStr.length();
			finalStr = inputStr;
			if (strLen > setLen)
				finalStr = inputStr.substring(0, setLen);
			else if (strLen < setLen) {
				for (int i = 0; i < (setLen - strLen); i++)
					addempty = addempty + " ";
				if (LRalign == 'L')
					finalStr = inputStr + addempty;
				else
					finalStr = addempty + inputStr;
			}
		} else {
			for (int i = 0; i < setLen; i++)
				addempty = addempty + " ";

			finalStr = addempty;
		}

		return finalStr;
	}

	public static String addspace(int space) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < space; i++) {
			buffer.append(" ");
		}
		return buffer.toString();
	}
	
	public static String addLine(int line) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < line; i++) {
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
